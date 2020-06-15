package com.car.front.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.car.front.R;
import com.car.front.adapter.PhotoNewAdapter;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;
import com.car.front.bean.CommonalityModel;
import com.car.front.bean.FileInfo;
import com.car.front.bean.StoreInfo;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.util.Constants;
import com.car.front.util.JsonParse;
import com.car.front.util.LogUtils;
import com.car.front.util.Md5Util;
import com.car.front.util.ToastUtil;
import com.car.front.util.Utility;
import com.car.front.weight.MediaLoader;
import com.car.front.weight.NoDataView;
import com.car.front.weight.SpaceItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author: zt
 * @date: 2020/5/29
 * @name:门店相册
 */
public class PhotoActivity extends BaseActivity implements NetWorkListener {
    private TextView title_text_tv, title_left_btn, title_right_tv;
    private RecyclerView recyclerView;
    private PhotoNewAdapter adapter;
    private StoreInfo info;
    private List<FileInfo> infoList = new ArrayList<>();
    private int limit = 10;
    private int page = 1;
    private NoDataView mNoDataView;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_photo);
        BaseApplication.activityTaskManager.putActivity("PhotoActivity", this);
    }

    @Override
    protected void initView() {
        mNoDataView = getView(R.id.mNoDataView);
        title_right_tv = getView(R.id.title_right_tv);
        recyclerView = getView(R.id.recyclerView);
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        title_right_tv.setOnClickListener(this);
        title_text_tv.setText("门店相册");
        mNoDataView.textView.setText("您还没有添加门店照片~");
    }

    @Override
    protected void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        info = (StoreInfo) getIntent().getSerializableExtra("info");
        if (info != null) {
            qury();
        }
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
                            setAdapter();
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

    private void setAdapter() {
        recyclerView.addItemDecoration(new SpaceItemDecoration(20, 2));
        adapter = new PhotoNewAdapter(this, infoList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PhotoActivity.this, ListPhotoActivity.class);
                intent.putExtra("infoList", (Serializable) infoList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }




    @Override
    public void onFail() {

    }

    @Override
    public void onError(Exception e) {

    }
}
