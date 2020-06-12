package com.car.front.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.car.front.R;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;

/**
 * @author: zt
 * @date: 2020/5/21
 * @name:供货商详情
 */
public class VendorActivity extends BaseActivity {
    private TextView title_text_tv, title_left_btn;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_vendor);
        BaseApplication.activityTaskManager.putActivity("VendorActivity",this);
    }

    @Override
    protected void initView() {
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        title_text_tv.setText("供货商");
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_left_btn:
                finish();
                break;
        }
    }
}
