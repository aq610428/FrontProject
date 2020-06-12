package com.car.front.adapter;

import android.content.Context;

import com.car.front.R;
import com.car.front.bean.CustomerInfo;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/15
 * @name:CustomerAdapter
 */
public class TableAdapter extends AutoRVAdapter {
    private List<CustomerInfo> list;
    private Context mContext;


    public TableAdapter(Context mContext, List<CustomerInfo> data) {
        super(mContext, data);
        this.list = data;
        this.mContext = mContext;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_table;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        CustomerInfo info=list.get(position);
        vh.getTextView(R.id.text_rescue).setText(info.getName());
        vh.getTextView(R.id.text_rescue).setCompoundDrawablesWithIntrinsicBounds(0,info.getDrawable(),0,0);
    }
}
