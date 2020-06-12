package com.car.front.adapter;

import android.content.Context;
import android.view.View;

import com.car.front.R;
import com.car.front.bean.DemoBean;
import com.car.front.bean.KeepInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/6/10
 * @name:StockAdapter
 */
public class StockAdapter extends AutoRVAdapter {
    private List<DemoBean> keeps = new ArrayList<>();

    public StockAdapter(Context context, List<DemoBean> list) {
        super(context, list);
        this.keeps = list;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_keep_stock;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        DemoBean keepInfo = keeps.get(position);
        if (position == 0) {
            vh.getLinearLayout(R.id.ll_tab1).setVisibility(View.VISIBLE);
        } else {
            vh.getLinearLayout(R.id.ll_tab1).setVisibility(View.GONE);
        }
        vh.getTextView(R.id.text_license).setText((position+1)+"");
        vh.getTextView(R.id.text_brand).setText(keepInfo.getName());
        vh.getTextView(R.id.text_num).setText(keepInfo.getModel());
        vh.getTextView(R.id.text_traveled).setText(keepInfo.getFaults());


        if (position==keeps.size()-1){
            vh.getTextView(R.id.view).setVisibility(View.VISIBLE);
        }else{
            vh.getTextView(R.id.view).setVisibility(View.GONE);
        }
    }
}
