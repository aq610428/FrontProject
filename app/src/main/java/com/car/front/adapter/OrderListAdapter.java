package com.car.front.adapter;

import android.content.Context;

import com.car.front.R;
import com.car.front.bean.StoreInfo;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/19
 * @name:ClientAdapter
 */
public class OrderListAdapter extends AutoRVAdapter {
    private List<StoreInfo> data;


    public OrderListAdapter(Context context, List<StoreInfo> list) {
        super(context, list);
        this.data = list;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_fount_order;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        StoreInfo clientInfo = data.get(position);
        vh.getTextView(R.id.text_name).setText(clientInfo.getName());
    }

}
