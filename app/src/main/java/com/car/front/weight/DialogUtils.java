package com.car.front.weight;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.car.front.R;
import com.car.front.util.SystemTools;
import com.car.front.util.ToastUtil;


public class DialogUtils {

    public static void showDialog(final String phoneNum, Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        final Dialog dialog = new Dialog(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_layout1, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.title)).setText("温馨提示");
        ((TextView) view.findViewById(R.id.message)).setText("确定退出惠保养?");
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    public static void showMapView(final String address, Activity activity) {
        if (activity == null || activity.isFinishing()) {
            return;
        }
        Dialog dialog = new Dialog(activity, R.style.dialog_bottom_full);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_map, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        dialog.setContentView(view);
        view.findViewById(R.id.rl_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.text_GD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemTools.isInstallByread("com.autonavi.minimap")) {
                    SystemTools.invokingGD(address, activity);
                } else {
                    ToastUtil.showToast("高德地图未安装");
                }
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.text_BD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SystemTools.isInstallByread("com.baidu.BaiduMap")) {
                    SystemTools.invokingBD(address, activity);
                } else {
                    ToastUtil.showToast("百度地图未安装");
                }
                dialog.dismiss();
            }
        });


        view.findViewById(R.id.text_TX).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemTools.invokingTX(address,activity);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
