package com.car.front.adapter;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.car.front.R;
import com.car.front.bean.Bespoke;
import com.car.front.ui.activity.OrderActivity;
import com.car.front.ui.fragment.CustomerFragment;
import com.car.front.util.DateUtils;
import com.car.front.util.SystemTools;
import com.car.front.util.Utility;
import com.car.front.weight.DialogUtils;

import java.util.List;

/**
 * @author: zt
 * @date: 2020/5/15
 * @name:CustomerAdapter
 */
public class OwnerAdapter extends AutoRVAdapter {
    private List<Bespoke> list;
    private CustomerFragment fragment;
    public String phone = "";


    public OwnerAdapter(CustomerFragment fragment, List<Bespoke> list) {
        super(fragment.getContext(), list);
        this.list = list;
        this.fragment = fragment;
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_owner;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        Bespoke bespoke = list.get(position);
        String orderTime = bespoke.getOrderTime();
        if (!Utility.isEmpty(orderTime) && orderTime.length() > 5) {
            String end = orderTime.substring(0, orderTime.length() - 5);
            String start = orderTime.substring(orderTime.length() - 5, orderTime.length());
            vh.getTextView(R.id.tv_date).setText(end + "  " + start);
        }
        vh.getTextView(R.id.tv_make).setText(bespoke.getProject() + "");
        vh.getTextView(R.id.text_subscribe).setText(bespoke.getStoreName() + "");
        vh.getTextView(R.id.text_address).setText(bespoke.getStoreAddress() + "");
        vh.getTextView(R.id.text_mobile).setText(bespoke.getStorePhone() + "");

        vh.getTextView(R.id.tv_license).setText(bespoke.getCarcard() + "");
        vh.getTextView(R.id.tv_phone).setText(bespoke.getMobile() + "");
        vh.getTextView(R.id.tv_make).setText(bespoke.getProject() + "");
        String createTime = bespoke.getStringCreateTime();
        if (!Utility.isEmpty(createTime) && createTime.length() > 8) {
            String end = createTime.substring(0, createTime.length() - 8);
            String start = createTime.substring(createTime.length() - 8, createTime.length());
            vh.getTextView(R.id.text_order_date).setText(end + "  " + start);
        }

        if ("洗车".equals(bespoke.getProject() + "")) {
            vh.getTextView(R.id.tv_make).setTextColor(Color.parseColor("#3F80F4"));
            vh.getTextView(R.id.tv_phone).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_mobile, 0);
        } else if ("保养".equals(bespoke.getProject())) {
            vh.getTextView(R.id.tv_make).setTextColor(Color.parseColor("#32D1A6"));
            vh.getTextView(R.id.tv_phone).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_green, 0);
        } else if ("维修".equals(bespoke.getProject())) {
            vh.getTextView(R.id.tv_make).setTextColor(Color.parseColor("#FF0808"));
            vh.getTextView(R.id.tv_phone).setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.icon_red, 0);
        }

        TextView tv_phone = vh.getTextView(R.id.tv_phone);
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = tv_phone.getText().toString().trim();
                fragment.requestPermission(new String[]{Manifest.permission.CALL_PHONE}, 3);
            }
        });

        vh.getTextView(R.id.text_mobile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = vh.getTextView(R.id.text_mobile).getText().toString().trim();
                fragment.requestPermission(new String[]{Manifest.permission.CALL_PHONE}, 3);
            }
        });


        TextView text_receiving = vh.getTextView(R.id.text_receiving);
        if (bespoke.getStatus() == 1) {//未接单
            text_receiving.setText("接受预约");
        } else if (bespoke.getStatus() == 3) {//已接单，待完成
            text_receiving.setText("已拒绝预约");
        } else if (bespoke.getStatus() == 2) {//已接单，待完成
            text_receiving.setText("去开单");
        } else if (bespoke.getStatus() == 4 && bespoke.getIntegralFlag() == 1) {//已接单，待完成
            text_receiving.setText("收取积分");
        } else {
            text_receiving.setText("已收取积分");
        }

        text_receiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), OrderActivity.class);
                intent.putExtra("bespoke", bespoke);
                fragment.getContext().startActivity(intent);
            }
        });


        vh.getTextView(R.id.text_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showMapView(bespoke.getStoreAddress(),fragment.getActivity());
            }
        });
    }

    public void setData(List<Bespoke> bespokes) {
        this.list = bespokes;
    }
}
