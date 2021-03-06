/**
 *
 */
package com.car.front.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import com.car.front.R;
import com.car.front.base.BaseApplication;
import com.car.front.bean.CustomerInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @类名: SystemTools<br>
 * @author： weilai<br>
 * @version：1.0 2015/12/2 0002 10:41 weilai 发布
 */
public final class SystemTools {

    /**
     * 使用迭代器可，正确无误的删除
     *
     * @param list
     * @param element
     * @return
     */
    public static List iteratorRemove(List list, Object element) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object cur = iterator.next();
            if (cur.equals(element)) {
                // 注意！！！这里时Iterator.remove()!!!而不是list.remove()!!!
                iterator.remove();
            }
        }
        return list;
    }

    public static List<CustomerInfo> getListCustomerInfo() {
        List<CustomerInfo> infos = new ArrayList<>();
//        infos.add(new CustomerInfo("维修开单", R.mipmap.ic_repair));
//        infos.add(new CustomerInfo("洗车开单", R.mipmap.ic_wash));
//        infos.add(new CustomerInfo("保养开单", R.mipmap.ic_sale));
//        infos.add(new CustomerInfo("车检开单", R.mipmap.ic_drive));
        infos.add(new CustomerInfo("我的门店", R.mipmap.ic_administration));
        infos.add(new CustomerInfo("商品管理", R.mipmap.ic_wares));
        infos.add(new CustomerInfo("回访记录", R.mipmap.ic_suggest));
        infos.add(new CustomerInfo("中间仓", R.mipmap.ic_front));
        return infos;
    }


    public static List<CustomerInfo> getListCustomerMe() {
        List<CustomerInfo> infos = new ArrayList<>();
        infos.add(new CustomerInfo("消费记录", R.mipmap.icon_trip));
        infos.add(new CustomerInfo("故障信息", R.mipmap.icon_maintenance));
        infos.add(new CustomerInfo("预约记录", R.mipmap.icon_fault));
        infos.add(new CustomerInfo("保养到期", R.mipmap.icon_insurance));
        infos.add(new CustomerInfo("保险数据", R.mipmap.icon_automobile));
        infos.add(new CustomerInfo("行驶排行", R.mipmap.icon_record));
        return infos;
    }


    /**
     * 获取指定文件大小
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if (file.exists()) {
                FileInputStream fis = null;
                fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return size;
    }


    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }


    public static List<String> getList() {
        List<String> list = new ArrayList<>();
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        list.add("http://a0.att.hudong.com/56/12/01300000164151121576126282411.jpg");
        return list;
    }


    public static List<String> getListTools() {
        List<String> list_title = new ArrayList<>();
        list_title.add("企业门牌照");
        list_title.add("企业整体外观照");
        list_title.add("企业内部环境照");
        list_title.add("维修工时收费标准");
        list_title.add("救援工具照");
        return list_title;
    }


    /**
     * 调用第三方浏览器打开
     *
     * @param url 要浏览的资源地址
     */
    public static void openBrowser(Activity activity, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            ComponentName componentName = intent.resolveActivity(activity.getPackageManager());
            activity.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(activity, "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 根据应用包名，跳转到应用市场
     *
     * @param activity    承载跳转的Activity
     * @param packageName 所需下载（评论）的应用包名
     */
    public static void shareAppShop(Activity activity, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "您没有安装应用市场", Toast.LENGTH_SHORT).show();
        }
    }


    public static void hideKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }


    /**
     * 安装apk
     *
     * @param
     * @param file
     */
    public static void installApk(Activity activity, File file) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, "com.car.front.provider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        activity.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 重启整个APP
     * @param context
     */
    public static void restartAPP(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(BaseApplication.getContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * 获取当前应用程序的版本号
     */
    public static int getAppVersionCode(Context context) {
        int versionCode = 1;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        return versionCode;
    }


    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtils.e("VersionInfo" + "Exception    " + e);
        }
        return versionName;
    }


    /**
     * 验证手机号码是否合法
     * 176, 177, 178;
     * 180, 181, 182, 183, 184, 185, 186, 187, 188, 189;
     * 145, 147;
     * 130, 131, 132, 133, 134, 135, 136, 137, 138, 139;
     * 150, 151, 152, 153, 155, 156, 157, 158, 159;
     *
     * "13"代表前两位为数字13,
     * "[0-9]"代表第二位可以为0-9中的一个,
     * "[^4]" 代表除了4
     * "\\d{8}"代表后面是可以是0～9的数字, 有8位。
     */
    public static boolean isMobileNumber(String mobiles) {
        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /******打开地图导航*****/
    public static void openMap(String address, Context mContext) {
        if (isInstallByread("com.autonavi.minimap")) {//判断是否安装高德
            invokingGD(address, mContext);
            return;
        } else if (isInstallByread("com.baidu.BaiduMap")) {//判断是否安装百度
            invokingBD(address, mContext);
            return;
        } else {
            ToastUtil.showToast("您未安装地图软件");
        }

    }


    /**
     * 判断是否安装目标应用
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }


    /****打开高德地图*****/
    public static void invokingGD(String address, Context mContext) {
        //  com.autonavi.minimap这是高德地图的包名
        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse("androidamap://navi?sourceApplication=应用名称&lat=" + "&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        intent.setData(Uri.parse("androidamap://poi?sourceApplication=softname&keywords=" + address));

        if (isInstallByread("com.autonavi.minimap")) {
            mContext.startActivity(intent);
        }
    }


    /****打开高德地图 com.baidu.BaiduMap*****/
    public static void invokingBD(String address, Context mContext) {
        //调起百度地图客户端try {
        Intent intent = new Intent();
        intent.setData(Uri.parse("baidumap://map/geocoder?src=openApiDemo&address=" + address));
        mContext.startActivity(intent); //启动调用
    }


    /****打开高德地图 com.baidu.BaiduMap*****/
    public static void invokingTX(String address, Context mContext) {
        if (checkMapAppsIsExist(mContext, "com.tencent.map")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("qqmap://map/routeplan?type=bus&from=我的位置&fromcoord=0,0"
                    + "&to=" + address
                    + "&policy=1&referer=myapp"));
            mContext.startActivity(intent);
        } else {
            ToastUtil.showToast("腾讯地图未安装");
        }
    }


    /**
     * 检测地图应用是否安装
     *
     * @param context
     * @param packagename
     * @return
     */
    public static boolean checkMapAppsIsExist(Context context, String packagename) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packagename, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }

}
