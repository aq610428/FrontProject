package com.car.front.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.car.front.R;
import com.car.front.base.BaseActivity1;
import com.car.front.base.BaseApplication;
import com.car.front.bean.StoreInfo;
import com.car.front.glide.GlideUtils;

/**
 * @author: zt
 * @date: 2020/5/28
 * @name:店铺详情
 */
public class StoreDeilActivity extends BaseActivity1 {
    private StoreInfo info;
    private TextView title_text_tv, title_left_btn, text_name, text_contacts, text_phone, text_date, text_adress, text_brand, text_business, text_rescue;
    private ImageView lv_logo;
    private TextView text_album, text_natural;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_storedeil);
        BaseApplication.activityTaskManager.putActivity("StoreDeilActivity",this);
    }

    @Override
    protected void initView() {
        text_album = getView(R.id.text_album);
        text_natural = getView(R.id.text_natural);
        lv_logo = getView(R.id.lv_logo);
        text_name = getView(R.id.text_name);
        text_contacts = getView(R.id.text_contacts);
        text_phone = getView(R.id.text_phone);
        text_date = getView(R.id.text_date);
        text_adress = getView(R.id.text_adress);
        text_brand = getView(R.id.text_brand);
        text_business = getView(R.id.text_business);
        text_rescue = getView(R.id.text_rescue);

        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        text_album.setOnClickListener(this);
        text_natural.setOnClickListener(this);
        title_text_tv.setText("店铺详情");
    }

    @Override
    protected void initData() {
        info = (StoreInfo) getIntent().getSerializableExtra("info");
        if (info != null) {
            text_name.setText(info.getName());
            text_contacts.setText(info.getContactPerson());
            text_phone.setText(info.getPhone());
            text_date.setText(info.getOperTime());
            text_adress.setText(info.getAddress());
            text_brand.setText(info.getBrandName());
            text_business.setText(info.getBusinessScope());
            text_rescue.setText(info.getRescuePhone());
            GlideUtils.setImageUrl(info.getLogo(), lv_logo);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent = null;
        switch (v.getId()) {
            case R.id.title_left_btn:
                finish();
                break;
            case R.id.text_album:
                intent = new Intent(StoreDeilActivity.this, ListPhotoActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                break;
            case R.id.text_natural:
                intent = new Intent(StoreDeilActivity.this, LegalActivity.class);
                intent.putExtra("info", info);
                startActivity(intent);
                break;
        }
    }
}
