package com.car.front.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.car.front.R;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;
import com.car.front.bean.CommonalityModel;
import com.car.front.bean.Verison;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.util.Constants;
import com.car.front.util.JsonParse;
import com.car.front.util.Md5Util;
import com.car.front.util.SystemTools;
import com.car.front.util.ToastUtil;
import com.car.front.util.Utility;
import com.car.front.weight.UpdateManager;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author: zt
 * @date: 2020/5/27
 * @name:关于我们
 */
public class AboutActivity extends BaseActivity implements NetWorkListener {
    private TextView title_text_tv, title_left_btn;
    private RelativeLayout rl_edition, rl_tab;
    private Verison verison;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_about);
        BaseApplication.activityTaskManager.putActivity("AboutActivity",this);
    }

    @Override
    protected void initView() {
        rl_tab = getView(R.id.rl_tab);
        rl_edition = getView(R.id.rl_edition);
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        title_text_tv.setText("关于我们");
        rl_edition.setOnClickListener(this);
        rl_tab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_left_btn:
                finish();
                break;
            case R.id.rl_edition:
                query();
                break;
            case R.id.rl_tab:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
        }
    }

    /*****检测是否具有读写权限******/
    public void applyPermission() {
        if (Build.VERSION.SDK_INT >= 19) {
            if (checkPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})) {
                UpdateVerison();
            } else {
                requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 3);
            }
        } else {
            UpdateVerison();
        }
    }


    /*****检测是否具有安装未知来源的权限******/
    public void UpdateVerison() {
        new UpdateManager(this).checkForceUpdate(verison);
    }

    public void permissinSucceed(int code) {
        switch (code) {
            case 3:
                UpdateVerison();
                break;
        }
    }


    @Override
    protected void initData() {

    }

    /*******查询首页数据
     * @param ********/
    public void query() {
        String sign = "partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        showProgressDialog(this, false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("apptype", Constants.TYPE);
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_INTERGRAL_VERSION, params, Api.GET_INTERGRAL_VERSION_ID, this);
    }


    @Override
    public void onSucceed(JSONObject object, int id, CommonalityModel commonality) {
        if (object != null && commonality != null && !Utility.isEmpty(commonality.getStatusCode())) {
            if (Constants.SUCESSCODE.equals(commonality.getStatusCode())) {
                switch (id) {
                    case Api.GET_INTERGRAL_VERSION_ID:
                        verison = JsonParse.getVerisonUserInfo(object);
                        if (verison != null) {
                            int code = SystemTools.getAppVersionCode(this);
                            if (!Utility.isEmpty(verison.getVersionIndex())) {
                                int versionCode = Integer.parseInt(verison.getVersionIndex());
                                if (versionCode > code) {
                                    applyPermission();
                                } else {
                                    ToastUtil.showToast("暂无新版本");
                                }
                            }

                        }
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
