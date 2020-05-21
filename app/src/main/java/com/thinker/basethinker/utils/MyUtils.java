package com.thinker.basethinker.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.app.MyApplication;
import com.thinker.basethinker.bean.AdvImages;
import com.thinker.basethinker.bean.AllSellerBean;
import com.thinker.basethinker.bean.InvateAndShateBean;
import com.thinker.basethinker.bean.OrderBean;
import com.thinker.basethinker.bean.PersonalBean;
import com.thinker.basethinker.bean.SystemParamsBean;
import com.thinker.basethinker.dialog.ShareDialog;
import com.thinker.basethinker.toplayout.AskFeedbackFragment;
import com.thinker.basethinker.toplayout.UsingFragment;

import java.io.File;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
/**
 * Created by farley on 17/5/23.
 * description:
 */

public class MyUtils {
    //"认证状态：0未认证，1认证中，2认证成功，3认证失败"
    public static String PERSONAL_AUTH_STATUS_ITEM0 = "0";
    public static String PERSONAL_AUTH_STATUS_ITEM1 = "1";
    public static String PERSONAL_AUTH_STATUS_ITEM2 = "2";
    public static String PERSONAL_AUTH_STATUS_ITEM3 = "3";
    //支付模式
    public static String PAY_TYPE_ALIPAY = "alipay_app";
    public static String PAY_TYPE_WXPAY = "wx_app";
    public static String PAY_TYPE_STRIPE = "stripe";

    //行程状态/** 状态  10:开锁中 20: 开锁失败 30:行驶中 40:未支付 50:已支付 */
    public static Integer RIDE_STATUS_1 = 10;
    public static Integer RIDE_STATUS_2 = 20;
    public static Integer RIDE_STATUS_3 = 30;
    public static Integer RIDE_STATUS_4 = 40;
    public static Integer RIDE_STATUS_5 = 50;

    /**
     * @param activity
     */
    public static void setToolsStatus(Activity activity, int color) {
        //核心代码.状态栏的沉浸式显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            //给状态栏设置颜色。我设置透明。
            window.setStatusBarColor(color);
            window.setNavigationBarColor(color);
        }
    }
/*
    *//**
     * 检测token
     *//*
    public static Boolean checkToken(final Activity activity) {
        if (!MyApplication.getIsBindPhone()) {
            DialogUtils.standerdDialog1(activity, "提示", "请先绑定手机号", new DialogUtils.OnMainClickListener() {
                @Override
                public void onClick() {
                    ActivityController.startBindPhone(activity);
                }
            });
            return true;
        } else {
            return false;
        }
    }

    */

    /**
     * 保存个人信息
     */
    public static void savaPesonData(PersonalBean bean) {
        Gson gson = new Gson();
        String personalData = gson.toJson(bean);
        PreferencesUtils.putString(MyApplication.appContext, "RADISHSAAS_PERSONAL_DATA", personalData);
    }

    /**
     * 获取个人资料
     *
     * @return
     */
    public static PersonalBean getPersonData() {
        Gson gson = new Gson();
        PersonalBean bean = new PersonalBean();
        String personStr = PreferencesUtils.getString(MyApplication.appContext, "RADISHSAAS_PERSONAL_DATA");
        if (!TextUtils.isEmpty(personStr)) {
            bean = gson.fromJson(personStr, PersonalBean.class);
            return bean;
        } else {
            return null;
        }
    }

    /**
     * 保存附近商户信息
     */
    public static void savaSellerData(AllSellerBean allSellerBean) {
        Gson gson = new Gson();
        String result = gson.toJson(allSellerBean);
        PreferencesUtils.putString(MyApplication.appContext, "NEAR_SELLER", result);
    }

    /**
     * 获取附近商户信息
     */
    public static AllSellerBean getSellerData() {
        Gson gson = new Gson();
        AllSellerBean bean = new AllSellerBean();
        String personStr = PreferencesUtils.getString(MyApplication.appContext, "NEAR_SELLER");
        if (!TextUtils.isEmpty(personStr)) {
            bean = gson.fromJson(personStr, AllSellerBean.class);
            return bean;
        } else {
            return null;
        }
    }

    /**
     * 保存系统配置
     *
     * @param systemParamsBean
     */
    public static void savaSystemData(SystemParamsBean systemParamsBean) {
        Gson gson = new Gson();
        String systemData = gson.toJson(systemParamsBean);
        PreferencesUtils.putString(MyApplication.appContext, "RADISHSAAS_SYSTEM_DATA", systemData);
    }
    /**
     * 保存电池信息
     *
     * @param batteryBean
     *//*
    public static void savaBatteryData(BatteryBean batteryBean) {
        Gson gson = new Gson();
        String batteryData = gson.toJson(batteryBean);
        PreferencesUtils.putString(MyApplication.appContext, "BATTERY", batteryData);
    }
    *//**
     * 获取电池信息
     *
     * @return
     *//*
    public static BatteryBean getBatteryData() {
        Gson gson = new Gson();
        BatteryBean bean = new BatteryBean();
        String personStr = PreferencesUtils.getString(MyApplication.appContext, "BATTERY");
        if (!TextUtils.isEmpty(personStr)) {
            bean = gson.fromJson(personStr, BatteryBean.class);
            return bean;
        } else {
            return null;
        }

    }
    */

    /**
     * 获取系统配置
     *
     * @return
     */
    public static SystemParamsBean getSystemData() {
        Gson gson = new Gson();
        SystemParamsBean bean = new SystemParamsBean();
        String personStr = PreferencesUtils.getString(MyApplication.appContext, "RADISHSAAS_SYSTEM_DATA");
        if (!TextUtils.isEmpty(personStr)) {
            bean = gson.fromJson(personStr, SystemParamsBean.class);
            return bean;
        } else {
            return null;
        }
    }

    /**
     * 保存邀请和分享的配置参数
     *
     * @param invateAndShateBean
     */
    public static void savaInvateAndShareData(InvateAndShateBean invateAndShateBean) {
        Gson gson = new Gson();
        String systemData = gson.toJson(invateAndShateBean);
        PreferencesUtils.putString(MyApplication.appContext, "RADISHSAAS_INVATEANDSHARE_DATA", systemData);
    }

    /**
     * 获取邀请和分享的配置参数
     *
     * @return
     */
    public static InvateAndShateBean getInvateAndShareData() {
        Gson gson = new Gson();
        InvateAndShateBean bean = new InvateAndShateBean();
        String personStr = PreferencesUtils.getString(MyApplication.appContext, "RADISHSAAS_INVATEANDSHARE_DATA");
        if (!TextUtils.isEmpty(personStr)) {
            bean = gson.fromJson(personStr, InvateAndShateBean.class);
            return bean;
        } else {
            return null;
        }
    }

    //分享demo
    public static void showShare(Activity activity) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(activity);
    }



    /**
     * 分享QQ
     *
     * @param activity
     *//*
    public static void shareQQ(Activity activity, String title, String shareUrl, String shareContent) {

        OnekeyShare oks = new OnekeyShare();
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        String platform = plat.getName();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(shareUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareContent);

        Resources res = activity.getResources();
        BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.mipmap.ic_launcher);
        Bitmap img = d.getBitmap();

        String fn = "image_test.png";
        String path = activity.getFilesDir() + File.separator + fn;
        try {
            OutputStream os = new FileOutputStream(path);
            img.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
        } catch (Exception e) {
            Log.e("TAG", "", e);
        }
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(path);//确保SDcard下面存在此张图片
        // 启动分享GUI
        oks.show(activity);
    }

    public static void shareQzone(Activity activity, String title, String shareUrl, String shareContent) {

        OnekeyShare oks = new OnekeyShare();
        Platform plat = ShareSDK.getPlatform(QZone.NAME);
        String platform = plat.getName();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(shareUrl);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareContent);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        Resources res = activity.getResources();
        BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.mipmap.ic_launcher);
        Bitmap img = d.getBitmap();

        String fn = "image_test.png";
        String path = activity.getFilesDir() + File.separator + fn;
        try {
            OutputStream os = new FileOutputStream(path);
            img.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
        } catch (Exception e) {
            Log.e("TAG", "", e);
        }

        oks.setImagePath(path);//确保SDcard下面存在此张图片
        SystemParamsBean systemParamsBean = MyUtils.getSystemData();
        if (systemParamsBean != null) {
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(systemParamsBean.getAppName());
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl("");
        }
        // 启动分享GUI
        oks.show(activity);


    }*/
    public static void showWechat(String platform, Activity activity, String title, String shareUrl, String shareContent) {
        Platform.ShareParams sharePlatform = new Platform.ShareParams();
        sharePlatform.setShareType(Platform.SHARE_WEBPAGE);
        sharePlatform.setTitle(title);
        if(shareContent!=null && !shareContent.equalsIgnoreCase("")) {
            sharePlatform.setText(shareContent);
        }
        if(shareUrl!=null && !shareUrl.equalsIgnoreCase("")) {
            sharePlatform.setImageUrl(shareUrl);
            sharePlatform.setImagePath(shareUrl);
        }
        if(shareUrl!=null && !shareUrl.equalsIgnoreCase("")) {
            sharePlatform.setUrl(shareUrl);
        }
        Platform platforma = ShareSDK.getPlatform(platform);
        platforma.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                LogUtils.e("分享成功");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                LogUtils.e("分享失败");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtils.e("分享取消");
            }
        });
        platforma.share(sharePlatform);
    }

    /**
     * public static void showMoment(Activity activity, View view) {
     *//* OnekeyShare oks = new OnekeyShare();
        WechatMoments.ShareParams params = new WechatMoments.ShareParams();
        params.setShareType(Platform.SHARE_IMAGE);
        Platform plat = ShareSDK.getPlatform(WechatMoments.NAME);
        plat.share(params);
        String platform = plat.getName();

        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(title);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareContent);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        if (!TextUtils.isEmpty(shareImg)) {
            oks.setImagePath(shareImg);//确保SDcard下面存在此张图片
        }
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareUrl);


        // 启动分享GUI
        oks.show(activity);*//*
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setSilent(false);
        oks.setPlatform(WechatMoments.NAME);
        oks.setViewToShare(view);
        oks.show(activity);

    } */
    public static void myShareDialog(boolean type, Activity activity, final ShareCancelListener shareCancelListener) {
        ShareDialog dialog = new ShareDialog(activity, new ShareDialog.OnDialogClickListener() {
            @Override
            public void choose(int i) {
                shareCancelListener.mySelect(i);

            }

            @Override
            public void cancel() {
                shareCancelListener.cancel();
            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.dialogWindowAnim);
        if (dialog != null && window != null) {
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams attr = window.getAttributes();
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                attr.width = ViewGroup.LayoutParams.MATCH_PARENT;
                attr.gravity = Gravity.BOTTOM;//设置dialog 在布局中的位置
                window.setAttributes(attr);
            }
        }
    }

    public interface ShareCancelListener {
        void cancel();

        void mySelect(int pos);
    }

    /**
     * 保存list数据
     *//*
    public static String PREFERENCE_NAME = "RadishSaas";//自己命名

    public static <T> void putSeachHistory(Context context, String key, List<T> datalist) {
        *//*if (null == datalist || datalist.size() <= 0)
            return;*//*

        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, strJson);
        editor.commit();
    }

    /**
     * 获取list数据
     *
     * @param context
     * @param tag
     * @return
     *//*
    public static List<AddressEntity> getSeachHistory(Context context, String tag) {
        List<AddressEntity> datalist = new ArrayList<AddressEntity>();
        SharedPreferences settings = context.getSharedPreferences(
                PREFERENCE_NAME, Context.MODE_PRIVATE);
        String strJson = settings.getString(tag, null);
        LogUtils.d("strJson=" + strJson);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<AddressEntity>>() {
        }.getType());
        return datalist;
    }

    /**
     * 旋转动画
     */
    public static void setAnimotion(final View view) {
        AnimationSet animationSet = new AnimationSet(true);
        final RotateAnimation rote = new RotateAnimation(0, 360 * 20, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rote.setDuration(400 * 20);
        animationSet.addAnimation(rote);
        view.startAnimation(animationSet);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.clearAnimation();
                view.setVisibility(View.GONE);
            }
        }, 2000);
    }


    /**
     * 检查用户当前的认证状态
     *//*
    public static Boolean checkStep(Activity activity) {
        int step = MyApplication.getIsIdenty();
        switch (step) {
            case 1:
                return false;
            case 2:
                ActivityController.startRecharge(activity);
                return false;
            case 3:
                ActivityController.startIdentid(activity);
                return false;
            case 4:
                return true;
            default:
                ActivityController.startBindPhone(activity);
                return false;
        }
    }*/
    private static String ADVFILENAME = "nomo";

    public static File fileIsHave() {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String filesName = ADVFILENAME;
        File appDir = new File(file, filesName);

        return appDir;
    }

    public static void deleteMyFiles() {
        File myFile = fileIsHave();
        if (myFile.exists()) {
            deleteAllFilesOfDir(myFile);
        }
    }

    //删除文件夹
    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }
    /**
     * 保存行程
     *
     *//*
    public static void savaTripData(OnGoing_TripBO advImages) {
        Gson gson = new Gson();
        String systemData = gson.toJson(advImages);
        PreferencesUtils.putString(MyApplication.appContext, Config.TIPINGDATA, systemData);
    }
    public static OnGoing_TripBO getTripData() {
        Gson gson = new Gson();
        OnGoing_TripBO bean = new OnGoing_TripBO();
        String personStr = PreferencesUtils.getString(MyApplication.appContext, Config.TIPINGDATA);
        if (!TextUtils.isEmpty(personStr)) {
            bean = gson.fromJson(personStr, OnGoing_TripBO.class);
            return bean;
        } else {
            return null;
        }
    }
    */

    /**
     * 保存广告配置
     */
    public static void savaAdvanceData(AdvImages advImages) {
        Gson gson = new Gson();
        String systemData = gson.toJson(advImages);
        PreferencesUtils.putString(MyApplication.appContext, Config.ADV_IMAGE_DATA, systemData);
    }

    /**
     * 获取广告配置
     *
     * @return
     */
    public static AdvImages getAdvanceData() {
        Gson gson = new Gson();
        AdvImages bean = new AdvImages();
        String personStr = PreferencesUtils.getString(MyApplication.appContext, Config.ADV_IMAGE_DATA);
        if (!TextUtils.isEmpty(personStr)) {
            bean = gson.fromJson(personStr, AdvImages.class);
            return bean;
        } else {
            return null;
        }
    }
//    public static long toToday(Long tDate){
//        long today = new Date().getTime();
//        return (tDate - today)/1000/60/60/24;
//    }
    private static FragmentManager fragmentManager;

    //控制fragment bottom
    public static void showFragment(Activity activity, boolean isShow, Fragment fragment) {
        fragmentManager = activity.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isShow) {
            transaction.replace(R.id.bottom, fragment);
        } else {
            transaction.remove(fragment);
        }
        // 事务提交
        transaction.commitAllowingStateLoss();
    }

    //只是控制使用中的
    private static UsingFragment usingFragment;

    public static void showUsingFragment(Activity activity, boolean isShow, OrderBean orderBean) {
        fragmentManager = activity.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isShow) {
            usingFragment = new UsingFragment(orderBean);
            transaction.replace(R.id.top, usingFragment);
        } else {
            if (usingFragment != null) {
                transaction.remove(usingFragment);
                usingFragment = null;
            }
        }
        // 事务提交
        transaction.commitAllowingStateLoss();
    }

    private static AskFeedbackFragment askFeedbackFragment;

    //问题反馈
    public static void initAskFragment(Activity activity, boolean isShow) {
        fragmentManager = activity.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isShow) {
            askFeedbackFragment = new AskFeedbackFragment();
            transaction.replace(R.id.top, askFeedbackFragment);
        } else {
            if (askFeedbackFragment != null) {
                transaction.remove(askFeedbackFragment);
                askFeedbackFragment = null;
            }
        }
        // 事务提交
        transaction.commitAllowingStateLoss();
    }

    //跳转google地图
    public static void turnToGoogleMap(Activity activity, Double lat, Double lon) {
        if (Utils.isAvilible(activity, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lon);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            activity.startActivity(mapIntent);
        } else {
            Toast.makeText(activity, activity.getString(R.string.no_map), Toast.LENGTH_LONG).show();
        }
    }

    //计算两天之间的距离
    public static float twoLatlngDistance(LatLng s, LatLng e) {
        if (s == null || e == null) {
            return 0;
        }
        float[] results = new float[3];
        Location.distanceBetween(s.latitude, s.longitude, e.latitude, e.longitude, results);
        return results[0];
    }
    public static LogBean getAppMsgForBean(Context context) {
        String appVersion = "";
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            appVersion = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String myPackageName = context.getPackageName();
        LogBean logBean = new LogBean(myPackageName,"Android",appVersion, Build.MODEL,Build.VERSION.RELEASE);
        String model = "Android/" + Build.MODEL + "/"
                + Build.VERSION.SDK_INT + "/"
                + Build.VERSION.RELEASE + "/" + appVersion;
        ;
        return logBean;
    }
}
