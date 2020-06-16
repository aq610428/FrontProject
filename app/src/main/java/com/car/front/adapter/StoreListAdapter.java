package com.car.front.adapter;

import android.content.Context;
import android.view.View;

import com.car.front.R;
import com.car.front.bean.StoreInfo;
import com.car.front.glide.GlideUtils;
import com.car.front.ui.activity.StoreListActivity;

import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/28
 * @name:StoreAdapter
 */
public class StoreListAdapter extends AutoRVAdapter {
    private List<StoreInfo> infos;
    private StoreListActivity activity;

    public StoreListAdapter(StoreListActivity activity, List<StoreInfo> list) {
        super(activity, list);
        this.infos = list;
        this.activity = activity;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_store_store;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        StoreInfo info = infos.get(position);
        vh.getTextView(R.id.text_name).setText(infos.get(position).getName());
        GlideUtils.CreateImageRound(infos.get(position).getLogo(), vh.getImageView(R.id.iv_photo), 5);
        vh.getTextView(R.id.tv_address).setText(info.getAddress());
        vh.getTextView(R.id.text_distance).setText(info.getDistance() + "m");

        vh.getTextView(R.id.text_verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.showDialog(info.getId());
            }
        });

    }

    public void setData(List<StoreInfo> infos) {
        this.infos = infos;
    }
}
