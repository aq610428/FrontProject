package com.car.front.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.car.front.R;
import com.car.front.adapter.OwnerAdapter;
import com.car.front.adapter.TableAdapter;
import com.car.front.base.BaseFragment;
import com.car.front.bean.Bespoke;
import com.car.front.bean.CommonalityModel;
import com.car.front.bean.CustomerInfo;
import com.car.front.bean.UserInfo;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.ocr.camera.CameraActivity;
import com.car.front.ui.activity.ClientActivity;
import com.car.front.ui.activity.CommodityActivity;
import com.car.front.ui.activity.FrontActivity;
import com.car.front.ui.activity.KeepActivity;
import com.car.front.ui.activity.MassageActivity;
import com.car.front.ui.activity.ReturnActivity;
import com.car.front.ui.activity.StoreActivity;
import com.car.front.util.Constants;
import com.car.front.util.FileUtil;
import com.car.front.util.JsonParse;
import com.car.front.util.Md5Util;
import com.car.front.util.SaveUtils;
import com.car.front.util.SystemTools;
import com.car.front.util.Utility;
import com.car.front.weight.EmptyDataView;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import crossoverone.statuslib.StatusUtil;

/****
 *
 * 我的预约
 *
 */
public class CustomerFragment extends BaseFragment implements View.OnClickListener, NetWorkListener, OnLoadMoreListener, OnRefreshListener {
    private View rootView;
    private boolean isPrepared;
    private TextView text_msg;
    private RecyclerView recyclerView, rv_table;
    private OwnerAdapter adapter;
    private List<Bespoke> bespokes = new ArrayList<>();
    private TextView text_tab1, text_tab2, text_tab3, text_tab4, text_num, text_couster, text_master, text_tab5;
    private static final int REQUEST_CODE_LICENSE_PLATE = 122;
    private View view1, view2, view3, view4, view5;
    private int limit = 10;
    private int page = 1;
    private UserInfo info;
    private SwipeToLoadLayout swipeToLoadLayout;
    private boolean isRefresh;
    private LinearLayout ll_term, ll_client;
    private EmptyDataView mNoDataView;
    private String project = "";
    private TableAdapter tableAdapter;
    private List<CustomerInfo> infos;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_customer, container, false);
            initView();
            isPrepared = true;
            lazyLoad();
        }
        return rootView;
    }

    private void initView() {
        rv_table = getView(rootView, R.id.rv_table);
        text_msg = getView(rootView, R.id.text_msg);
        view5 = getView(rootView, R.id.view5);
        text_tab5 = getView(rootView, R.id.text_tab5);
        text_master = getView(rootView, R.id.text_master);
        text_couster = getView(rootView, R.id.text_couster);
        text_num = getView(rootView, R.id.text_num);
        mNoDataView = getView(rootView, R.id.mNoDataView);
        ll_client = getView(rootView, R.id.ll_client);
        ll_term = getView(rootView, R.id.ll_term);
        swipeToLoadLayout = getView(rootView, R.id.swipeToLoadLayout);
        view1 = getView(rootView, R.id.view1);
        view2 = getView(rootView, R.id.view2);
        view3 = getView(rootView, R.id.view3);
        view4 = getView(rootView, R.id.view4);
        text_tab1 = getView(rootView, R.id.text_tab1);
        text_tab2 = getView(rootView, R.id.text_tab2);
        text_tab3 = getView(rootView, R.id.text_tab3);
        text_tab4 = getView(rootView, R.id.text_tab4);
        recyclerView = rootView.findViewById(R.id.mRecyclerView);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        text_tab1.setOnClickListener(this);
        text_tab2.setOnClickListener(this);
        text_tab3.setOnClickListener(this);
        text_tab4.setOnClickListener(this);
        text_tab5.setOnClickListener(this);
        ll_term.setOnClickListener(this);
        ll_client.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        text_msg.setOnClickListener(this);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "DINOT-Bold.ttf");
        text_num.setTypeface(typeface);
        mNoDataView.textView.setText("暂无预约订单");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rv_table.setLayoutManager(gridLayoutManager);
        rv_table.setHasFixedSize(true);
        infos = SystemTools.getListCustomerInfo();
        tableAdapter = new TableAdapter(getContext(), infos);
        rv_table.setAdapter(tableAdapter);
        tableAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = infos.get(position).getName();
                if ("商品管理".equals(name)) {
                    startActivity(new Intent(getContext(), CommodityActivity.class));
                } else if ("回访记录".equals(name)) {
                    startActivity(new Intent(getContext(), ReturnActivity.class));
                } else if ("我的门店".equals(name)) {
                    startActivity(new Intent(getContext(), StoreActivity.class));
                } else {
                    startActivity(new Intent(getContext(), FrontActivity.class));
                }

            }
        });
    }


    /*******查询预约列表********/
    public void query() {
        String sign = "";
        if (!Utility.isEmpty(project)) {
            sign = "memberId=" + info.getId() + "&partnerid=" + Constants.PARTNERID + "&project=" + project + Constants.SECREKEY;
        } else {
            sign = "memberId=" + info.getId() + "&partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        }
        showProgressDialog(getActivity(), false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("limit", limit + "");
        params.put("page", page + "");
        if (!Utility.isEmpty(project)) {
            params.put("project", project + "");
        }
        params.put("memberId", info.getId() + "");
        params.put("apptype", Constants.TYPE);
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_ADVNCE, params, Api.GET_ADVNCE_ID, this);
    }


    /*******查询首页数据
     * @param ********/
    public void queryAmount() {
        String sign = "memberId=" + info.getId() + "&partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        showProgressDialog(getActivity(), false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("apptype", Constants.TYPE);
        params.put("memberId", info.getId());
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_SUM_SAVE, params, Api.GET_SUM, this);
    }


    @Override
    public void onSucceed(JSONObject object, int id, CommonalityModel commonality) {
        if (object != null && commonality != null && !Utility.isEmpty(commonality.getStatusCode())) {
            if (Constants.SUCESSCODE.equals(commonality.getStatusCode())) {
                switch (id) {
                    case Api.GET_ADVNCE_ID:
                        List<Bespoke> infos = JsonParse.getBespokeJson(object);
                        if (infos != null && infos.size() > 0) {
                            setAdapter(infos);
                        } else {
                            if (page == 1 && !isRefresh) {
                                recyclerView.setVisibility(View.GONE);
                                mNoDataView.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                    case Api.GET_SUM:
                        updateVew(object);
                        break;
                }
            }
        }
        stopProgressDialog();
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    private void updateVew(JSONObject jsonObject) {
        if (jsonObject != null && !jsonObject.isNull("result")) {
            JSONObject jsonObject1 = jsonObject.optJSONObject("result");
            text_num.setText(jsonObject1.optString("allsum") + "台");
            text_couster.setText(jsonObject1.optString("carsum") + "");
            text_master.setText(jsonObject1.optString("maintainsum") + "");
        }
    }

    private void setAdapter(List<Bespoke> voList) {
        recyclerView.setVisibility(View.VISIBLE);
        mNoDataView.setVisibility(View.GONE);
        if (!isRefresh) {
            bespokes.clear();
            bespokes.addAll(voList);
            adapter = new OwnerAdapter(this, bespokes);
            recyclerView.setAdapter(adapter);
        } else {
            bespokes.addAll(voList);
            adapter.setData(bespokes);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_msg:
                startActivity(new Intent(getContext(), MassageActivity.class));
                break;
            case R.id.ll_term:
                startActivity(new Intent(getContext(), KeepActivity.class));
                break;
            case R.id.ll_client:
                startActivity(new Intent(getContext(), ClientActivity.class));
                break;
            case R.id.iv_code:
                requestPermission(new String[]{Manifest.permission.CAMERA}, 4);
                break;
            case R.id.text_tab1:
                goneView();
                text_tab1.setTextColor(Color.parseColor("#3F80F4"));
                view1.setVisibility(View.VISIBLE);
                project = "";
                onRefresh();
                break;
            case R.id.text_tab2:
                project = "洗车";
                goneView();
                text_tab2.setTextColor(Color.parseColor("#3F80F4"));
                view2.setVisibility(View.VISIBLE);
                onRefresh();
                break;
            case R.id.text_tab3:
                project = "保养";
                goneView();
                text_tab3.setTextColor(Color.parseColor("#3F80F4"));
                view3.setVisibility(View.VISIBLE);
                onRefresh();
                break;
            case R.id.text_tab4:
                project = "维修";
                goneView();
                text_tab4.setTextColor(Color.parseColor("#3F80F4"));
                view4.setVisibility(View.VISIBLE);
                onRefresh();
                break;
            case R.id.text_tab5:
                project = "救援";
                goneView();
                text_tab5.setTextColor(Color.parseColor("#3F80F4"));
                view5.setVisibility(View.VISIBLE);
                onRefresh();
                break;
        }
    }

    private void goneView() {
        text_tab1.setTextColor(Color.parseColor("#585858"));
        text_tab2.setTextColor(Color.parseColor("#585858"));
        text_tab3.setTextColor(Color.parseColor("#585858"));
        text_tab4.setTextColor(Color.parseColor("#585858"));
        text_tab5.setTextColor(Color.parseColor("#585858"));
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);
        view3.setVisibility(View.GONE);
        view4.setVisibility(View.GONE);
        view5.setVisibility(View.GONE);
    }


    @Override
    public void permissinSucceed(int code) {
        super.permissinSucceed(code);
        if (code == 4) {
            Intent intent = new Intent(getContext(), CameraActivity.class);
            intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, FileUtil.getSaveFile(getContext()).getAbsolutePath());
            intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
            startActivityForResult(intent, REQUEST_CODE_LICENSE_PLATE);
        } else {
            SystemTools.callPhone(getContext(), adapter.phone);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatusUtil.setUseStatusBarColor(getActivity(), Color.parseColor("#3F80F4"));
        info = SaveUtils.getSaveInfo();
        if (info != null) {
            query();
            queryAmount();
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
    }


    @Override
    public void onFail() {
        stopProgressDialog();
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onError(Exception e) {
        stopProgressDialog();
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {
        isRefresh = true;
        page++;
        query();
    }

    @Override
    public void onRefresh() {
        isRefresh = false;
        page = 1;
        query();
    }
}
