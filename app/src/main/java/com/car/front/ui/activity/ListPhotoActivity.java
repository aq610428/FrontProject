package com.car.front.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.car.front.R;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseActivity1;
import com.car.front.base.BaseApplication;
import com.car.front.bean.CommonalityModel;
import com.car.front.bean.FileInfo;
import com.car.front.bean.StoreInfo;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.util.Constants;
import com.car.front.util.JsonParse;
import com.car.front.util.Md5Util;
import com.car.front.util.ToastUtil;
import com.car.front.util.Utility;
import com.car.front.weight.MyViewPagerAdapter;
import com.car.front.weight.NoDataView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import crossoverone.statuslib.StatusUtil;

/**
 * @author: zt
 * @date: 2020/5/29
 * @name:ListPhotoActivity
 */
public class ListPhotoActivity extends BaseActivity implements NetWorkListener {
    private ViewPager viewPager;
    private MyViewPagerAdapter pagerAdapter;
    private List<FileInfo> infoList = new ArrayList<>();
    private TextView text_name;
    private StoreInfo info;
    private int limit = 10;
    private int page = 1;
    private NoDataView mNoDataView;
    private TextView title_text_tv, title_left_btn;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list_photo);
        BaseApplication.activityTaskManager.putActivity("ListPhotoActivity", this);
    }

    @Override
    protected void initView() {
        mNoDataView = findViewById(R.id.mNoDataView);
        text_name = findViewById(R.id.text_name);
        viewPager = findViewById(R.id.viewpager);
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        title_text_tv.setText("门店相册");
    }


    @Override
    protected void initData() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                text_name.setText(infoList.get(i).getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        info = (StoreInfo) getIntent().getSerializableExtra("info");
        if (info != null) {
            qury();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        StatusUtil.setUseStatusBarColor(this, Color.parseColor("#FFFFFF"));
    }

    private void qury() {
        String sign = "partnerid=" + Constants.PARTNERID + "&storeId=" + info.getId() + Constants.SECREKEY;
        showProgressDialog(this, false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("apptype", Constants.TYPE);
        params.put("storeId", info.getId() + "");
        params.put("limit", limit + "");
        params.put("page", page + "");
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_IMAGE, params, Api.GET_IMAGE_ID, this);
    }


    @Override
    public void onSucceed(JSONObject object, int id, CommonalityModel commonality) {
        if (object != null && commonality != null && !Utility.isEmpty(commonality.getStatusCode())) {
            if (Constants.SUCESSCODE.equals(commonality.getStatusCode())) {
                switch (id) {
                    case Api.GET_IMAGE_ID:
                        infoList = JsonParse.getStoreFileJson(object);
                        if (infoList != null && infoList.size() > 0) {
                            mNoDataView.setVisibility(View.GONE);
                            setPage();
                        } else {
                            mNoDataView.setVisibility(View.VISIBLE);
                        }
                        break;
                }
            } else {
                ToastUtil.showToast(commonality.getErrorDesc());
            }
        }
        stopProgressDialog();
    }

    private void setPage() {
        pagerAdapter = new MyViewPagerAdapter(this, infoList);
        viewPager.setAdapter(pagerAdapter);
        text_name.setText(infoList.get(0).getTitle());
    }

    @Override
    public void onFail() {
        stopProgressDialog();
    }

    @Override
    public void onError(Exception e) {
        stopProgressDialog();
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
}
