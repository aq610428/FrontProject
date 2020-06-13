package com.car.front.adapter;

import android.content.Context;
import android.view.View;
import com.car.front.R;
import com.car.front.bean.CommodityInfo;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zt
 * @date: 2020/5/21
 * @name:CommodityAdapter
 */
public class CommodityAdapter extends AutoRVAdapter {
    public List<CommodityInfo> infos;
    private Map<Integer, CommodityInfo> map;

    public CommodityAdapter(Context context, List<CommodityInfo> list) {
        super(context, list);
        this.infos = list;
        map = new LinkedHashMap<>();
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_commodity;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        CommodityInfo info = infos.get(position);
        vh.getTextView(R.id.text_Mechanical).setText(info.getMechanical());
        vh.getTextView(R.id.text_stock).setText("库存：" + info.getStock());
        vh.getTextView(R.id.text_price).setText("￥" + info.getOriginalPrice());

        if (info.isCheck()){
            vh.getImageView(R.id.iv_select).setImageResource(R.mipmap.icon_pitch);
        }else{
            vh.getImageView(R.id.iv_select).setImageResource(R.mipmap.icon_pitch_nor);
        }

        vh.getImageView(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info.isCheck()){
                    info.setCheck(false);
                    map.remove(position);
                    vh.getImageView(R.id.iv_select).setImageResource(R.mipmap.icon_pitch_nor);
                }else{
                    info.setCheck(true);
                    map.put(position,info);
                    vh.getImageView(R.id.iv_select).setImageResource(R.mipmap.icon_pitch);
                }
            }
        });
    }
}
