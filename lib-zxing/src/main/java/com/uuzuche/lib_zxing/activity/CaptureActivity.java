package com.uuzuche.lib_zxing.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uuzuche.lib_zxing.R;

/**
 * Initial the camera
 *
 * 默认的二维码扫描Activity
 */
public class CaptureActivity extends AppCompatActivity {
    private ImageView title_left_img;//标题左边的图片
    private TextView title_left_text;//标题左边的文字
    private ImageView openFlash;//打开闪光灯
    private boolean isOpen = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        title_left_img = (ImageView) findViewById(R.id.title_left_img);
        title_left_text = (TextView) findViewById(R.id.title_left_text);
        openFlash = (ImageView) findViewById(R.id.openFlash);
        title_left_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_left_text.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        title_left_img.setSelected(true);
                        title_left_text.setSelected(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        title_left_img.setSelected(false);
                        title_left_text.setSelected(false);
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
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }
    };
    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

}