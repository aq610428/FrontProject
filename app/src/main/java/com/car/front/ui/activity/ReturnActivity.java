package com.car.front.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.car.front.R;
import com.car.front.adapter.ReturnAdapter;
import com.car.front.base.BaseActivity;
import com.car.front.bean.Bespoke;
import com.car.front.bean.CommonalityModel;
import com.car.front.bean.UserInfo;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.util.Constants;
import com.car.front.util.JsonParse;
import com.car.front.util.Md5Util;
import com.car.front.util.SaveUtils;
import com.car.front.util.ToastUtil;
import com.car.front.util.Utility;
import com.car.front.weight.NoDataView;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: zt
 * @date: 2020/6/13
 * @name:回访记录
 */
public class ReturnActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener, NetWorkListener {
    private ListView swipe_target;
    private SwipeToLoadLayout swipeToLoadLayout;
    private TextView title_text_tv, title_left_btn;
    private int limit = 10;
    private int page = 1;
    private boolean isRefresh;
    private UserInfo userInfo;
    private List<Bespoke> ordereds = new ArrayList<>();
    private ReturnAdapter adapter;
    private NoDataView mNoDataView;


    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_return);
        userInfo = SaveUtils.getSaveInfo();
    }

    @Override
    protected void initView() {
        mNoDataView = getView(R.id.mNoDataView);
        swipe_target = getView(R.id.swipe_target);
        swipeToLoadLayout = getView(R.id.swipeToLoadLayout);
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        title_text_tv.setText("回访记录");
    }

    @Override
    protected void initData() {
        swipeToLoadLayout.setRefreshing(true);
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


    /*******查询预约列表********/
    public void query() {
        String sign = "memberId=" + userInfo.getId() + "&partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        showProgressDialog(this, false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("limit", limit + "");
        params.put("page", page + "");
        params.put("memberId", userInfo.getId() + "");
        params.put("apptype", Constants.TYPE);
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_COINS_INTERBILL, params, Api.GET_COINS_INTERBILL_ID, this);
    }


    @Override
    public void onSucceed(JSONObject object, int id, CommonalityModel commonality) {
        if (object != null && commonality != null && !Utility.isEmpty(commonality.getStatusCode())) {
            if (Constants.SUCESSCODE.equals(commonality.getStatusCode())) {
                switch (id) {
                    case Api.GET_COINS_INTERBILL_ID:
                        List<Bespoke> ordereds = JsonParse.getBespokeJson(object);
                        if (ordereds != null && ordereds.size() > 0) {
                            setOrdereds(ordereds);
                        } else {
                            if (page == 1 && !isRefresh) {
                                mNoDataView.setVisibility(View.VISIBLE);
                            } else {
                                ToastUtil.showToast("无更多数据");
                            }
                        }
                        break;
                }
            }
        }
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
        stopProgressDialog();
    }


    /****消费记录***/
    private void setOrdereds(List<Bespoke> list) {
        if (!isRefresh) {
            ordereds.clear();
            ordereds.addAll(list);
            adapter = new ReturnAdapter(this, ordereds);
            swipe_target.setAdapter(adapter);
        } else {
            ordereds.addAll(list);
            adapter.setData(ordereds);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.title_left_btn:
                finish();
                break;
        }
    }

    @Override
    public void onFail() {
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }

    @Override
    public void onError(Exception e) {
        swipeToLoadLayout.setLoadingMore(false);
        swipeToLoadLayout.setRefreshing(false);
    }
}
