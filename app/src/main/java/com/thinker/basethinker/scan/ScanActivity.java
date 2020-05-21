package com.thinker.basethinker.scan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinker.basethinker.R;
import com.thinker.basethinker.utils.LogUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by thinker on 17/12/22.
 */

public class ScanActivity extends FragmentActivity implements View.OnClickListener {
    private ImageView head_left;
    private TextView head_title;
    private ImageView flash_img;
    private TextView flash_text;
    private boolean isOpen = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
        initView();
    }

    private void initView() {
        head_left = findViewById(R.id.head_left);
        head_title = findViewById(R.id.head_title);
        flash_img = findViewById(R.id.flash_img);
        flash_text = findViewById(R.id.flash_text);
        head_title.setText(getString(R.string.scan_title));
        head_left.setOnClickListener(this);
        flash_img.setOnClickListener(this);
        if (!hasFlash()){
            flash_img.setVisibility(View.GONE);
            flash_text.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_left:
                finish();
                break;
            case R.id.flash_img:
                if (!isOpen){
                    isOpen = true;
                    /**
                     * 关闭闪光灯
                     */
                    CodeUtils.isLightEnable(false);
                    flash_img.setSelected(false);
                    flash_text.setText(getString(R.string.scan_open_flash));
                }else {
                    isOpen = false;
                    /**
                     * 打开闪光灯
                     */
                    CodeUtils.isLightEnable(true);
                    flash_img.setSelected(true);
                    flash_text.setText(getString(R.string.scan_close_flash));
                }
                break;
            default:
                break;
        }
    }
    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            LogUtils.e("result"+result);
            if (!TextUtils.isEmpty(result)) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(CodeUtils.RESULT_STRING, result);
                setResult(RESULT_OK, resultIntent);
                finish();
            }else{
                Toast.makeText(ScanActivity.this,getString(R.string.scan_failed),Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        public void onAnalyzeFailed() {
            Toast.makeText(ScanActivity.this,getString(R.string.scan_failed),Toast.LENGTH_SHORT).show();
            finish();
        }
    };
    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
}
