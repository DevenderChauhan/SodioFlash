package com.thinker.basethinker.bestdeals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thinker.basethinker.R;

public class BestDealsActivity extends Activity {
    private ImageView head_left;
    private TextView head_title;
    private ImageView img_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_deals);
        initView();
    }

    private void initView() {
        head_left = (ImageView) findViewById(R.id.head_left);
        img_bg = (ImageView) findViewById(R.id.img_bg);
        head_title = (TextView) findViewById(R.id.head_title);
        head_title.setText(getString(R.string.menu_ll9));
        head_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setMyImageView();
    }
    private void setMyImageView(){
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.best_deals_img);
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
