package com.car.front.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.car.front.R;
import com.car.front.bean.Bespoke;
import com.car.front.bean.Ordered;
import com.car.front.ui.activity.ReturnActivity;
import com.car.front.util.Utility;
import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/15
 * @name:CustomerAdapter
 */
public class ReturnAdapter extends BaseAdapter {
    private List<Bespoke> list;
    private ReturnActivity activity;

    public ReturnAdapter(ReturnActivity activity, List<Bespoke> ordereds) {
        this.activity = activity;
        this.list = ordereds;
    }


    public void setData(List<Bespoke> bespokes) {
        this.list = bespokes;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = View.inflate(activity, R.layout.item_owner1, null);
            vh.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            vh.tv_make = (TextView) convertView.findViewById(R.id.tv_make);
            vh.tv_license = (TextView) convertView.findViewById(R.id.tv_license);
            vh.tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
            vh.text_receiving = convertView.findViewById(R.id.text_receiving);
            vh.tv_model = convertView.findViewById(R.id.tv_model);
            vh.tv_date_string = convertView.findViewById(R.id.tv_date_string);

            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        Bespoke bespoke = list.get(position);
        String time = bespoke.getStringCreateTime();
        if (!Utility.isEmpty(time) && time.length() > 8) {
            String end = time.substring(0, 10);
            String start = time.substring(time.length() - 8, time.length());
            vh.tv_date.setText(end + "  " + start);
        }

        String orderTime = bespoke.getOrderTime();
        if (!Utility.isEmpty(orderTime) && orderTime.length() > 5) {
            String end = orderTime.substring(0, orderTime.length() - 5);
            String start = orderTime.substring(orderTime.length() - 5, orderTime.length());
            vh.tv_date_string.setText(end + "  " + start);
        }

        vh.tv_make.setText(bespoke.getProject() + "");
        vh.tv_model.setText(bespoke.getStoreName());
        vh.tv_license.setText(bespoke.getCarcard() + "");
        vh.tv_phone.setText(bespoke.getMobile() + "");
        vh.tv_make.setText(bespoke.getProject() + "");
        return convertView;
    }


    class ViewHolder {
        private TextView tv_date, tv_make, tv_license, tv_phone, text_receiving, tv_model, tv_date_string;
    }
}
