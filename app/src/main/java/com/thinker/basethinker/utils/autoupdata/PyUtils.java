package com.thinker.basethinker.utils.autoupdata;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.thinker.basethinker.R;
import com.thinker.basethinker.api.Config;
import com.thinker.basethinker.utils.LogUtils;

import java.io.File;
import java.io.IOException;


/**
 * Created by thinker on 17/12/23.
 */

public class PyUtils {
    private Activity activity;

    public PyUtils(Activity activity) {
        this.activity = activity;
    }

    //    static String checkurl="http://www.pgyer.com/apiv1/app/viewGroup";
    static String checkurl = "https://www.pgyer.com/apiv2/app/check";
    static String downUrl = "https://www.pgyer.com/apiv2/app/check";

    public int getVersion() {
        PackageInfo pkg;
        int versionCode = 0;
        String versionName = "";
        try {
            pkg = activity.getPackageManager().getPackageInfo(activity.getApplication().getPackageName(), 0);
            String appName = pkg.applicationInfo.loadLabel(activity.getPackageManager()).toString();
            versionName = pkg.versionName;
            System.out.println("appName:" + appName);
            System.out.println("versionName:" + versionName);
            versionCode = pkg.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionCode;
    }

    public void check() {
        //当所用app前版本号
        int codeversin = getVersion();
        getLineVersion(checkurl, codeversin);
    }

    public void getLineVersion(String url, final int nowcodeversin) {
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = new FormEncodingBuilder()
                .add("_api_key", Config._api_key)
                .add("appKey", Config.APPID)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String result = response.body().string();
                Gson gson = new Gson();
                PyVersionBean bean = gson.fromJson(result, PyVersionBean.class);
                if (bean == null) {
                    return;
                }
                if (bean.getData() == null) {
                    return;
                }
                if (bean.getData().getBuildVersionNo() == null) {
                    return;
                }
                int lineVersion = Integer.parseInt(bean.getData().getBuildVersionNo());
                LogUtils.e("lineVersion="+lineVersion+"；"+nowcodeversin);
                if (lineVersion > nowcodeversin) {
                    if (checkListener != null) {
                        checkListener.checkSuccess();
                        downUrl = bean.getData().getDownloadURL();
                    }
                }

            }
        });
    }

    public void downUrl() {
        showLoading();
        DownloadUtil.get().download(downUrl,activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                if (pd6 != null) {
                    pd6.dismiss();
                }
                LogUtils.e("下载完成去安装");
                installAPK(file);
            }

            @Override
            public void onDownloading(int progress) {
                if (pd6 != null) {
                    pd6.setProgress(progress);
                }

            }

            @Override
            public void onDownloadFailed() {
                LogUtils.e("下载失败");
                if (pd6 != null) {
                    pd6.dismiss();
                }
                if (checkListener != null) {
                    checkListener.checkSuccess();
                }
            }
        });

    }
    /**
     * 提升读写权限
     * @param filePath 文件路径
     * @return
     * @throws IOException
     */
    public static void setPermission(String filePath)  {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    ////调用系统的安装方法
    private void installAPK(File savedFile) {
        //调用系统的安装方法
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(activity, "com.ligo.anomo.fileprovider", savedFile);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            LogUtils.e("7.0data="+data);
        } else {
            data = Uri.fromFile(savedFile);
            LogUtils.e("111data="+data);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        activity.startActivity(intent);
        activity.finish();
    }

    private ProgressDialog pd6;

    public void showLoading() {
        pd6 = new ProgressDialog(activity);
        pd6.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置水平进度条
        pd6.setCancelable(false);// 设置是否可以通过点击Back键取消
        pd6.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
//        pd6.setIcon(R.mipmap.ic_launcher);// 设置提示的title的图标，默认是没有的
        pd6.setTitle(activity.getString(R.string.toast_23));
        pd6.setMax(100);
        pd6.setMessage(activity.getString(R.string.downloading));
        pd6.show();
    }

    private static CheckListener checkListener;

    public interface CheckListener {
        void checkSuccess();
    }

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }
}
