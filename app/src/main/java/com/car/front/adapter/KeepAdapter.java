package com.car.front.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.car.front.R;
import com.car.front.bean.KeepInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/26
 * @name:清单
 */
public class KeepAdapter extends BaseAdapter {
    private List<KeepInfo> inventories = new ArrayList<>();
    private Context mContext;

    public KeepAdapter(Context context, List<KeepInfo> inventories) {
        this.mContext = context;
        this.inventories = inventories;
    }


    @Override
    public int getCount() {
        return inventories.size();
    }

    @Override
    public Object getItem(int position) {
        return inventories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_keep, null);
            vh.text_num = convertView.findViewById(R.id.text_num);
            vh.text_license = convertView.findViewById(R.id.text_license);
            vh.text_brand = convertView.findViewById(R.id.text_brand);
            vh.text_traveled = convertView.findViewById(R.id.text_traveled);
            vh.text_last_traveled = convertView.findViewById(R.id.text_last_traveled);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        KeepInfo inventory = inventories.get(position);
        vh.text_license.setText(inventory.getCarcard());
        vh.text_brand.setText(inventory.getFactory());
        vh.text_num.setText(inventory.getInitmileage() + "");
        vh.text_traveled.setText(inventory.getTotalmileage());
        vh.text_last_traveled.setText(inventory.getLastmaintainmileage());
        return convertView;
    }

    public void setData(List<KeepInfo> keepInfos) {
        this.inventories = keepInfos;
    }

    class ViewHolder {
        private TextView text_num, text_license, text_brand, text_traveled, text_last_traveled;
    }
}
