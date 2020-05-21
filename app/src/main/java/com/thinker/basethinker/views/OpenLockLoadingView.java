package com.thinker.basethinker.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.thinker.basethinker.R;


/**
 * Created by farley on 17/5/12.
 * description:开锁的自定义进度view
 */

public class OpenLockLoadingView extends View {
    private int myHeight = 3;//进度条的高度
    private int myWidth = 246;//进度条的总长度
    private float currentValue = 0;//当前进度
    private int themeColor = 0xffED242C;
    private Paint mPaint;
    private Context mContext;

    public OpenLockLoadingView(Context context) {
        this(context, null);
    }

    public OpenLockLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OpenLockLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.OpenLockLoadingViewAttr, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.OpenLockLoadingViewAttr_myHeight) {
                myHeight = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));

            } else if (attr == R.styleable.OpenLockLoadingViewAttr_myWidth) {
                myWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics()));
            }
        }
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);

    }
    private int tamp = 0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        RectF rBg = new RectF(0, 0, dip2px(myWidth), dip2px(myHeight));
        canvas.drawRoundRect(rBg, dip2px(myHeight), dip2px(myHeight), mPaint);
        mPaint.setColor(themeColor);
        int myValue = (int)(currentValue/(float)myWidth*100);
        if (myValue != tamp) {
            onResultReturnLisener.onResult(myValue);
            tamp = myValue;
        }
        int current = dip2px(currentValue);
        RectF rCurrent = new RectF(0, 0, current, dip2px(myHeight));
        canvas.drawRoundRect(rCurrent, dip2px(myHeight), dip2px(myHeight), mPaint);
    }
    private ValueAnimator anim;
    public void startOpen() {
        post(new Runnable() {
            @Override
            public void run() {
                anim = ValueAnimator.ofFloat(myHeight, (int) (myWidth * 0.3));
                anim.setDuration(3000);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        currentValue = value;
                        if (!isOverIt) {
                            invalidate();
                        }
                        if (currentValue == (int) (myWidth * 0.3)) {
                            ValueAnimator anim = ValueAnimator.ofFloat((int) (myWidth * 0.3), (int) (myWidth * 0.6));
                            anim.setDuration(1000);
                            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    float value = (float) animation.getAnimatedValue();
                                    currentValue = value;
                                    if (!isOverIt) {
                                        invalidate();
                                    }
                                    if (currentValue == (int) (myWidth * 0.6)) {
                                        ValueAnimator anim = ValueAnimator.ofFloat((int) (myWidth * 0.6), (int) (myWidth * 0.9));
                                        anim.setDuration(9000);
                                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                            @Override
                                            public void onAnimationUpdate(ValueAnimator animation) {
                                                float value = (float) animation.getAnimatedValue();
                                                currentValue = value;
                                                if (!isOverIt) {
                                                    invalidate();
                                                }
                                            }
                                        });
                                        anim.start();
                                    }
                                }
                            });
                            anim.start();
                        }
                    }
                });
                anim.start();
            }
        });
    }

    private boolean isOverIt = false;//是否开锁成功

    public void setItOver() {
        isOverIt = true;
        anim.cancel();
        ValueAnimator animTamp = ValueAnimator.ofFloat(currentValue, myWidth);
        animTamp.setDuration(300);
        animTamp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                currentValue = value;
                invalidate();
            }
        });
        animTamp.start();
    }
    public void setReset(){
        isOverIt = false;
        currentValue = 0;
        anim.start();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = dip2px(myWidth);
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = dip2px(myHeight);
        }
        setMeasuredDimension(width, height);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    private OnResultReturnLisener onResultReturnLisener;
    public void setOnResultReturnLisener(OnResultReturnLisener onResultReturnLisener){
        this.onResultReturnLisener = onResultReturnLisener;
    }
    public interface OnResultReturnLisener{
        void onResult(int result);
    }
}
