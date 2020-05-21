package com.thinker.basethinker.feedback;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinker.basethinker.R;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by farley on 17/6/5.
 * description:
 */

public class FeedBackScanActivity extends FragmentActivity {
    private ImageView title_left_img;//标题左边的图片
    private TextView openFlash;//打开闪光灯
    private TextView code_open_locak;//编码开锁
    private TextView title_right;
    private boolean isOpen = true;
    private TextView title_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_sacn);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        title_text = (TextView) findViewById(R.id.title_text);
        title_left_img = (ImageView) findViewById(com.uuzuche.lib_zxing.R.id.title_left_img);
        title_right = (TextView) findViewById(R.id.title_right);
        code_open_locak = (TextView) findViewById(R.id.code_open_locak);
        openFlash = (TextView) findViewById(com.uuzuche.lib_zxing.R.id.openFlash);
        title_left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_left_img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        title_left_img.setSelected(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        title_left_img.setSelected(false);
                        finish();
                        break;
                }

                return true;
            }
        });
        if (!hasFlash()){
            openFlash.setVisibility(View.GONE);
        }
        openFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen){
                    Log.d("farley","close");
                    isOpen = false;
                    /**
                     * 关闭闪光灯
                     */
                    CodeUtils.isLightEnable(false);
                    openFlash.setSelected(false);
                }else {
                    Log.d("farley","open");
                    isOpen = true;
                    /**
                     * 打开闪光灯
                     */
                    CodeUtils.isLightEnable(true);
                    openFlash.setSelected(true);
                }
            }
        });
        code_open_locak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityController.startCodeOpenLock(FeedBackScanActivity.this);
                finish();
            }
        });
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();
            //https://colours.api.thinker.vc/wx?bike=00046032
            /*String[] codes = result.split("=");
            if (codes.length > 1) {
                result = codes[1];
                ActivityController.startLockOpenning(ScanReturnDataActivity.this, result);
                finish();
            }else{
                ShowToast.show(ScanReturnDataActivity.this,"读取二维码失败");
            }*/
        }

        @Override
        public void onAnalyzeFailed() {
            /*Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
            finish();*/
            Toast.makeText(FeedBackScanActivity.this,getString(R.string.feedback_toast4),Toast.LENGTH_SHORT).show();
        }
    };
    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

}