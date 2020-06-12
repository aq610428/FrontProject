package com.car.front.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.car.front.MainActivity;
import com.car.front.R;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;
import com.car.front.bean.UserInfo;
import com.car.front.util.SaveUtils;

/***
 *
 * 启动页面
 *
 *
 */
public class StartActivity extends BaseActivity {
    private UserInfo info;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_start);
        BaseApplication.activityTaskManager.putActivity("StartActivity",this);
    }

    @Override
    protected void initView() {
        info = SaveUtils.getSaveInfo();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (info!=null){
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                }else{
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                }

                finish();
            }
        }, 2000);
    }

    @Override
    protected void initData() {

    }
}
