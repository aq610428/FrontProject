package com.car.front.adapter;

import android.content.Context;

import com.car.front.R;
import com.car.front.bean.CommodityInfo;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/21
 * @name:CommodityAdapter
 */
public class CommodityAdapter extends AutoRVAdapter {
    public List<CommodityInfo> infos;

    public CommodityAdapter(Context context, List<CommodityInfo> list) {
        super(context, list);
        this.infos = list;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_commodity;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        CommodityInfo info = infos.get(position);
        vh.getTextView(R.id.text_name).setText(info.getName());
        vh.getTextView(R.id.text_Mechanical).setText(info.getMechanical());
        vh.getTextView(R.id.text_stock).setText("库存：" + info.getStock());
        vh.getTextView(R.id.text_price).setText("￥" + info.getOriginalPrice());
    }
}
