package com.car.front.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.car.front.R;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;
import com.car.front.bean.CommonalityModel;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.util.Constants;
import com.car.front.util.Md5Util;
import com.car.front.util.Utility;

import org.json.JSONObject;
import java.util.Map;

/**
 * @author: zt
 * @date: 2020/5/20
 * @name:我的资产
 */
public class AssetsActivity extends BaseActivity implements NetWorkListener {
    private TextView title_text_tv, title_left_btn;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_assets);
        BaseApplication.activityTaskManager.putActivity("AssetsActivity",this);
        query();
    }

    @Override
    protected void initView() {
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        title_text_tv.setText("我的资产");
    }

    @Override
    protected void initData() {

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


    public void query() {
        String sign = "partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        showProgressDialog(this, false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_COINS, params, Api.GET_COINS_ID, this);
    }


    @Override
    public void onSucceed(JSONObject object, int id, CommonalityModel commonality) {
        if (object != null && commonality != null && !Utility.isEmpty(commonality.getStatusCode())) {
            if (Constants.SUCESSCODE.equals(commonality.getStatusCode())) {
                switch (id) {
                    case Api.GET_COINS_ID:

                        break;
                }
            }
        }
        stopProgressDialog();
    }

    @Override
    public void onFail() {
        stopProgressDialog();
    }

    @Override
    public void onError(Exception e) {
        stopProgressDialog();
    }
}
