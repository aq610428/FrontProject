package com.car.front.ui.fragment;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.car.front.R;
import com.car.front.adapter.StockAdapter;
import com.car.front.base.BaseFragment;
import com.car.front.bean.DemoBean;
import com.car.front.weight.ExcelUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/6/10
 * @name:出货清单
 */
public class InventoryFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener , View.OnClickListener {
    private View rootView;
    private SwipeToLoadLayout swipeToLoadLayout;
    private boolean isRefresh;
    private int limit = 10;
    private int page = 1;
    private StockAdapter stockAdapter;
    private List<DemoBean> keeps = new ArrayList<>();
    private RecyclerView swipe_target;
    private TextView tv_week,tv_mouth;
    private static String[] title = {"序号", "品名", "数量", "补货"};
    private Button tv_download;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_inventory, container, false);
            initView();
        }
        return rootView;
    }

    private void initView() {
        tv_download = getView(rootView, R.id.tv_download);
        tv_mouth= getView(rootView, R.id.tv_mouth);
        tv_week= getView(rootView, R.id.tv_week);
        swipe_target = getView(rootView, R.id.swipe_target);
        swipeToLoadLayout = getView(rootView, R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        swipe_target.setLayoutManager(layoutManager);
        keeps.add(new DemoBean("1","机油", "20", "否"));
        keeps.addAll(keeps);
        keeps.addAll(keeps);
        keeps.addAll(keeps);
        keeps.addAll(keeps);
        stockAdapter = new StockAdapter(getContext(), keeps);
        swipe_target.setAdapter(stockAdapter);

        tv_mouth.setOnClickListener(this);
        tv_week.setOnClickListener(this);
        tv_download.setOnClickListener(this);
    }


    @Override
    protected void lazyLoad() {

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
    public void permissinSucceed(int code) {
        super.permissinSucceed(code);
        createExcel();
    }


    /*****生成Excel表格****/
    public void createExcel() {
        File file = new File(ExcelUtils.getSDPath() + "/StoreCarBar");
        ExcelUtils.makeDir(file);
        ExcelUtils.initExcel(file.toString() + "/出货清单.xls", title);
        String fileName = ExcelUtils.getSDPath() + "/StoreCarBar/" + "出货清单.xls";
        ExcelUtils.writeObjListToExcel(ExcelUtils.getRecordData(keeps), fileName, getContext());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_download:
                requestPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
                break;
            case R.id.tv_week:
                tv_week.setBackgroundResource(R.mipmap.icon_week);
                tv_mouth.setBackgroundResource(R.mipmap.icon_mouth);
                tv_week.setTextColor(Color.parseColor("#FFFFFF"));
                tv_mouth.setTextColor(Color.parseColor("#5E5E5E"));
                break;
            case R.id.tv_mouth:
                tv_mouth.setTextColor(Color.parseColor("#FFFFFF"));
                tv_week.setTextColor(Color.parseColor("#5E5E5E"));
                tv_mouth.setBackgroundResource(R.mipmap.icon_left_bg);
                tv_week.setBackgroundResource(R.mipmap.icon_mouth);
                break;
        }
    }
}
