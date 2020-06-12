package com.car.front.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.car.front.R;
import com.car.front.adapter.OwnerTeAdapter;
import com.car.front.base.BaseFragment;
import com.car.front.bean.DemoBean;
import com.car.front.weight.ExcelUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/6/10
 * @name:车主清单
 */
public class OwnerFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, View.OnClickListener {
    private View rootView;
    private SwipeToLoadLayout swipeToLoadLayout;
    private boolean isRefresh;
    private int limit = 10;
    private int page = 1;
    private OwnerTeAdapter stockAdapter;
    private List<DemoBean> keeps = new ArrayList<>();
    private RecyclerView swipe_target;
    private Button tv_download;
    private static String[] title = {"车牌号", "车主姓名", "车牌品牌", "车辆型号"};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_owner, container, false);
            initView();
        }
        return rootView;
    }

    private void initView() {
        tv_download = getView(rootView, R.id.tv_download);
        swipe_target = getView(rootView, R.id.swipe_target);
        swipeToLoadLayout = getView(rootView, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        tv_download.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        swipe_target.setLayoutManager(layoutManager);
        keeps.add(new DemoBean("鄂QHR945", "李文东", "大众", "朗逸"));
        keeps.addAll(keeps);
        keeps.addAll(keeps);
        keeps.addAll(keeps);
        keeps.addAll(keeps);
        keeps.addAll(keeps);
        stockAdapter = new OwnerTeAdapter(getContext(), keeps);
        swipe_target.setAdapter(stockAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_download:
                requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                break;
        }
    }


    @Override
    public void permissinSucceed(int code) {
        super.permissinSucceed(code);
        createExcel();
    }

    /*****生成Excel表格****/
    public void createExcel() {
        File file = new File(ExcelUtils.getSDPath() + "/StoreCarBar");
        ExcelUtils.makeDir(file);
        ExcelUtils.initExcel(file.toString()  + "/车主清单.xls", title);
        String fileName = ExcelUtils.getSDPath()+ "/StoreCarBar/" +"车主清单.xls";
        ExcelUtils.writeObjListToExcel(ExcelUtils.getRecordData(keeps), fileName, getContext());
    }


    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 2000);
    }


    @Override
    protected void lazyLoad() {

    }



}
