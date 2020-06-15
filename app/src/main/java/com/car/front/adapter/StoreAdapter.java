package com.car.front.adapter;

import android.content.Context;

import com.car.front.R;
import com.car.front.bean.StoreInfo;
import com.car.front.glide.GlideUtils;

import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/28
 * @name:StoreAdapter
 */
public class StoreAdapter extends AutoRVAdapter {
    private List<StoreInfo> infos;

    public StoreAdapter(Context context, List<StoreInfo> list) {
        super(context, list);
        this.infos = list;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_store;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        StoreInfo info = infos.get(position);
        vh.getTextView(R.id.text_name).setText(infos.get(position).getName());
        GlideUtils.CreateImageRound(infos.get(position).getLogo(), vh.getImageView(R.id.iv_photo), 5);
        vh.getTextView(R.id.tv_address).setText("门店地址 " + info.getAddress());
        vh.getTextView(R.id.text_verify).setText("营业时间 " + info.getOperTime());

    }

    public void setData(List<StoreInfo> infos) {
        this.infos = infos;
    }
}
