package com.car.front.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.car.front.R;
import com.car.front.adapter.DepositoryAdapter;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;
import com.car.front.bean.CommonalityModel;
import com.car.front.bean.KeepInfo;
import com.car.front.bean.UserInfo;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.util.Constants;
import com.car.front.util.JsonParse;
import com.car.front.util.Md5Util;
import com.car.front.util.SaveUtils;
import com.car.front.util.Utility;
import com.car.front.weight.ClearEditText;
import com.car.front.weight.NoDataView;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: zt
 * @date: 2020/5/20
 * @name:维修开单
 */
public class DepositoryActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, NetWorkListener {
    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    private TextView title_text_tv, title_left_btn, title_right_btn;
    private List<KeepInfo> keepInfos = new ArrayList<>();
    private DepositoryAdapter adapter;
    private String name;
    private UserInfo info;
    private int limit = 10;
    private int page = 1;
    private boolean isRefresh;
    private NoDataView mNoDataView;
    private ClearEditText editText;
    private String cardNum;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_depository);
        info = SaveUtils.getSaveInfo();
        cardNum = getIntent().getStringExtra("name");
        BaseApplication.activityTaskManager.putActivity("DepositoryActivity",this);
    }

    @Override
    protected void initView() {
        editText = getView(R.id.et_search);
        mNoDataView = getView(R.id.mNoDataView);
        mNoDataView.textView.setText("暂无客户列表");
        title_right_btn = getView(R.id.title_right_btn);
        name = getIntent().getStringExtra("title");
        swipeToLoadLayout = getView(R.id.swipeToLoadLayout);
        recyclerView = getView(R.id.swipe_target);
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        title_text_tv.setText(name + "");
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        if (!Utility.isEmpty(cardNum)) {
            editText.setText(cardNum);
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utility.isEmpty(editText.getText().toString())) {
                    query();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {
        if (info != null) {
            if (!Utility.isEmpty(cardNum)) {
                editText.setText(cardNum);
                query1();
            } else {
                query();
            }
        }
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


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_left_btn:
                finish();
                break;
        }
    }


    /*******查询
     * @param ********/
    public void query1() {
        String sign = "cardNum=" + editText.getText().toString() + "&memberId=" + info.getId() + "&partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        showProgressDialog(this, false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("apptype", Constants.TYPE);
        params.put("limit", limit + "");
        params.put("page", page + "");
        params.put("carcard", editText.getText().toString() + "");
        params.put("memberId", info.getId());
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_CAR_SAVE, params, Api.GET_CAR_SAVE_ID, this);
    }


    /*******查询
     * @param ********/
    public void query() {
        String sign = "memberId=" + info.getId() + "&partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        showProgressDialog(this, false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("apptype", Constants.TYPE);
        params.put("limit", limit + "");
        params.put("page", page + "");
        params.put("memberId", info.getId());
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_CAR_SAVE, params, Api.GET_CAR_SAVE_ID, this);
    }


    @Override
    public void onSucceed(JSONObject object, int id, CommonalityModel commonality) {
        if (object != null && commonality != null && !Utility.isEmpty(commonality.getStatusCode())) {
            if (Constants.SUCESSCODE.equals(commonality.getStatusCode())) {
                switch (id) {
                    case Api.GET_CAR_SAVE_ID:
                        List<KeepInfo> infos = JsonParse.getKeepInfo(object);
                        if (infos != null && infos.size() > 0) {
                            setAdapter(infos);
                        } else {
                            if (page == 1 && !isRefresh) {
                                mNoDataView.setVisibility(View.VISIBLE);
                            }
                        }
                        break;
                }
            }
        }
        stopProgressDialog();
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    private void setAdapter(List<KeepInfo> voList) {
        mNoDataView.setVisibility(View.GONE);
        if (!isRefresh) {
            keepInfos.clear();
            keepInfos.addAll(voList);
            adapter = new DepositoryAdapter(this, keepInfos);
            recyclerView.setAdapter(adapter);
        } else {
            keepInfos.addAll(voList);
            adapter.setData(keepInfos);
        }

        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!"客户管理".equals(name)) {
                    Intent intent = new Intent(DepositoryActivity.this, OrderAutonomyActivity.class);
                    intent.putExtra("keep", keepInfos.get(position));
                    intent.putExtra("project", name);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onFail() {
        stopProgressDialog();
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }

    @Override
    public void onError(Exception e) {
        stopProgressDialog();
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);
    }
}
