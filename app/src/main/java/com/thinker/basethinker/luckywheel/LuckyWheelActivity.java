package com.thinker.basethinker.luckywheel;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinker.basethinker.R;

public class LuckyWheelActivity extends Activity {

    private ImageView head_left;
    private TextView head_title;
    private ImageView img_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lucky_wheel);
        initView();
    }

    private void initView() {
        head_left = (ImageView) findViewById(R.id.head_left);
        head_title = (TextView) findViewById(R.id.head_title);
        img_bg = (ImageView) findViewById(R.id.img_bg);
        head_title.setText(getString(R.string.menu_ll10));
        head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setMyImageView();
    }
    private void setMyImageView(){
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.lucky_wheel_img);
        int height = bitmap.getHeight();
        int width= bitmap.getWidth();
        if (img_bg == null) {
            return ;
        }
        if (img_bg.getScaleType() != ImageView.ScaleType.FIT_XY) {
            img_bg.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        ViewGroup.LayoutParams params = img_bg.getLayoutParams();
        int vw = img_bg.getWidth() - img_bg.getPaddingLeft() - img_bg.getPaddingRight();
        params.height = height;
        img_bg.setLayoutParams(params);
        img_bg.setImageBitmap(bitmap);
    }
}
