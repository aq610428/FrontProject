package com.car.front.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.car.front.R;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;

/**
 * @author: zt
 * @date: 2020/5/27
 * @name:帮助中心
 */
public class BasicOtherActivity extends BaseActivity {
    private TextView title_text_tv, title_left_btn;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_basic_other);
        BaseApplication.activityTaskManager.putActivity("BasicActivity", this);
    }

    @Override
    protected void initView() {
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        title_left_btn.setOnClickListener(this);
        title_text_tv.setText("其它问题");
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

    @Override
    protected void initData() {

    }
}
