package com.car.front.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.car.front.R;
import com.car.front.base.BaseActivity;
import com.car.front.base.BaseApplication;
import com.car.front.bean.CommonalityModel;
import com.car.front.config.Api;
import com.car.front.config.NetWorkListener;
import com.car.front.config.okHttpModel;
import com.car.front.util.Constants;
import com.car.front.util.Md5Util;
import com.car.front.util.SystemTools;
import com.car.front.util.ToastUtil;
import com.car.front.util.Utility;

import org.json.JSONObject;

import java.util.Map;

/**
 * @author: zt
 * @date: 2020/5/20
 * @name:注册
 */
public class RegisterActivity extends BaseActivity implements NetWorkListener {
    private EditText username, et_code, e_password;
    private Button btn_next;
    private TextView btn_code;
    private TextView title_text_tv, title_left_btn;
    private View view1,view2,view3;

    @Override
    protected void initCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
        BaseApplication.activityTaskManager.putActivity("RegisterActivity", this);
    }

    @Override
    protected void initView() {
        view1= getView(R.id.view1);
        view2= getView(R.id.view2);
        view3= getView(R.id.view3);
        btn_code = getView(R.id.btn_code);
        btn_next = getView(R.id.btn_next);
        username = getView(R.id.username);
        et_code = getView(R.id.et_code);
        e_password = getView(R.id.e_password);
        title_text_tv = getView(R.id.title_text_tv);
        title_left_btn = getView(R.id.title_left_btn);
        btn_code.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        title_left_btn.setOnClickListener(this);
        title_text_tv.setText("注册");
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utility.isEmpty(username.getText().toString())){
                    view1.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }else{
                    view1.setBackgroundColor(Color.parseColor("#3F80F4"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        e_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utility.isEmpty(e_password.getText().toString())){
                    view2.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }else{
                    view2.setBackgroundColor(Color.parseColor("#3F80F4"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Utility.isEmpty(et_code.getText().toString())){
                    view3.setBackgroundColor(Color.parseColor("#EEEEEE"));
                }else{
                    view3.setBackgroundColor(Color.parseColor("#3F80F4"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_code:
                checkPhone();
                break;
            case R.id.btn_next:
                query();
                break;
            case R.id.title_left_btn:
                finish();
                break;
        }
    }

    private void checkPhone() {
        String phone = username.getText().toString().trim();
        if (Utility.isEmpty(phone)) {
            ToastUtil.showToast("手机号不能为空");
        } else if (!SystemTools.isMobileNumber(phone)) {
            ToastUtil.showToast("手机号码不合法，请重新输入");
        } else {

            queryCode();
        }
    }

    /**
     * 倒计时显示
     */
    CountDownTimer timer;

    private void countDown() {
        timer = new CountDownTimer(90000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                btn_code.setEnabled(false);
                btn_code.setText("已发送(" + millisUntilFinished / 1000 + ")");
            }

            @Override
            public void onFinish() {
                btn_code.setEnabled(true);
                btn_code.setText("重新获取验证码");

            }
        }.start();

    }

    public void queryCode() {
        String mobile = username.getText().toString();
        String sign = "mobile=" + mobile + "&partnerid=" + Constants.PARTNERID + Constants.SECREKEY;
        showProgressDialog(this, false);
        Map<String, String> params = okHttpModel.getParams();
        params.put("mobile", mobile);
        params.put("apptype", Constants.TYPE);
        params.put("partnerid", Constants.PARTNERID);
        params.put("sign", Md5Util.encode(sign));
        okHttpModel.get(Api.GET_MOBILE, params, Api.GET_MOBILE_ID, this);
    }


    public void query() {
        String mobile = username.getText().toString();
        String password = e_password.getText().toString();
        String code = et_code.getText().toString();
        if (Utility.isEmpty(mobile)) {
            ToastUtil.showToast("手机号不能为空");
        } else if (!SystemTools.isMobileNumber(mobile)) {
            ToastUtil.showToast("手机号码不合法，请重新输入");
        } else if (Utility.isEmpty(mobile)) {
            ToastUtil.showToast("验证码不能为空");
        } else if (Utility.isEmpty(code)) {
            ToastUtil.showToast("验证码不能为空");
        } else if (Utility.isEmpty(password)) {
            ToastUtil.showToast("密码不能为空");
        } else if (password.length() < 6) {
            ToastUtil.showToast("密码不能低于6位");
        } else {
            String sign = "loginname=" + mobile + "&partnerid=" + Constants.PARTNERID + "&password=" + Md5Util.encode(password) + "&vcode=" + code + Constants.SECREKEY;
            showProgressDialog(this, false);
            Map<String, String> params = okHttpModel.getParams();
            params.put("loginname", mobile);
            params.put("partnerid", Constants.PARTNERID);
            params.put("sign", Md5Util.encode(sign));
            params.put("vcode", code);
            params.put("apptype", Constants.TYPE);
            params.put("password", Md5Util.encode(password));
            okHttpModel.get(Api.GET_REGISTER, params, Api.GET_REGISTER_ID, this);
        }
    }


    @Override
    public void onSucceed(JSONObject object, int id, CommonalityModel commonality) {
        if (object != null && commonality != null && !Utility.isEmpty(commonality.getStatusCode())) {
            if (Constants.SUCESSCODE.equals(commonality.getStatusCode())) {
                switch (id) {
                    case Api.GET_MOBILE_ID:
                        ToastUtil.showToast("验证码已发送");
                        countDown();
                        break;
                    case Api.GET_REGISTER_ID:
                        ToastUtil.showToast("注册成功");
                        finish();
                        break;
                }
            } else {
                ToastUtil.showToast(commonality.getErrorDesc());
            }
        }
        stopProgressDialog();
    }

    @Override
    public void onFail() {
        stopProgressDialog();
    }

    @Override
    public void onError(Exception e) {
        stopProgressDialog();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }

    }
}
