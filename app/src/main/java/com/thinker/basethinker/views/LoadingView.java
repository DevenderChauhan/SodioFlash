package com.thinker.basethinker.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by farley on 17/5/11.
 * description:应胡阳同学的要求，开发的一个关于加载的loading图标
 */

public class LoadingView extends View {

    private Paint mPaint;
    private Context mContext;

    private int circleCenterX, circleCenterY;

    private int circleRadius;
    private float moveDisance = 100f;//移动的距离
    private int moveSpeed = 250;//移动的速度
    private int durationMy = 30;//速度

    private final static float RADIUS_RATIO = 2 / 3f;
    private int[] colors = new int[]{
            Color.rgb(251,183,0),
            Color.rgb(249,138,61),
            Color.rgb(242,93,74),
            Color.rgb(248,77,117),
            Color.rgb(202,111,231),
            Color.rgb(0,189,243),
            Color.rgb(83,145,243),
            Color.rgb(0,208,151),
    };

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //处理 wrap_content问题
        int defaultDimension = dip2px(100);

        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultDimension, defaultDimension);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(defaultDimension, heightSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, defaultDimension);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        circleCenterX = w / 2;
        circleCenterY = h / 2;

        //处理padding情况
        circleRadius = (int) (Math.min(Math.min(circleCenterY - getPaddingTop(), circleCenterY - getPaddingBottom()),
                Math.min(circleCenterX - getPaddingLeft(), circleCenterX - getPaddingRight())) * RADIUS_RATIO);

    }

    private float Ax = 0f, Bx = 0f, Cx = 0f, Dx = 0f, Ex = 0f, Fx = 0f, Gx = 0f, Hx = 0f;
    private float Ar = 0f, Br = 0f, Cr = 0f, Dr = 0f, Er = 0f, Fr = 0f, Gr = 0f, Hr = 0f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(dip2px(2));
        canvas.drawCircle(circleCenterX, circleCenterY, circleRadius, mPaint);//画大圆

        canvas.drawCircle(circleCenterX, circleCenterY, dip2px(4), mPaint);//画中心小圆圆

        // 下面是画弧线
        RectF rectF = new RectF(circleCenterX - circleRadius * RADIUS_RATIO, circleCenterY - circleRadius * RADIUS_RATIO,
                circleCenterX + circleRadius * RADIUS_RATIO, circleCenterY + circleRadius * RADIUS_RATIO);

        canvas.drawArc(rectF, 0, 80, false, mPaint);
        canvas.drawArc(rectF, 180, 80, false, mPaint);


        rectF = new RectF(circleCenterX - circleRadius / 2, circleCenterY - circleRadius / 2,
                circleCenterX + circleRadius / 2, circleCenterY + circleRadius / 2);

        canvas.drawArc(rectF, 0, 80, false, mPaint);
        canvas.drawArc(rectF, 180, 80, false, mPaint);*/

        /*for (int i = 0 ;i < colors.length;i++){
            mPaint.setColor(colors[i]);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setStrokeWidth(dip2px(2));
            canvas.drawCircle(circleCenterX, circleCenterY, dip2px(5), mPaint);
            canvas.translate();
        }*/
        mPaint.setColor(colors[0]);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(dip2px(2));
        canvas.drawCircle(circleCenterX + Ax, circleCenterY - Ax, dip2px(Ar), mPaint);
        mPaint.setColor(colors[1]);
        canvas.drawCircle(circleCenterX + Bx, circleCenterY, dip2px(Br), mPaint);
        mPaint.setColor(colors[2]);
        canvas.drawCircle(circleCenterX + Cx, circleCenterY + Cx, dip2px(Cr), mPaint);

        mPaint.setColor(colors[3]);
        canvas.drawCircle(circleCenterX, circleCenterY + Dx, dip2px(Dr), mPaint);

        mPaint.setColor(colors[4]);
        canvas.drawCircle(circleCenterX - Ex, circleCenterY + Ex, dip2px(Er), mPaint);

        mPaint.setColor(colors[5]);
        canvas.drawCircle(circleCenterX - Fx, circleCenterY, dip2px(Fr), mPaint);

        mPaint.setColor(colors[6]);
        canvas.drawCircle(circleCenterX - Gx, circleCenterY - Gx, dip2px(Gr), mPaint);

        mPaint.setColor(colors[6]);
        canvas.drawCircle(circleCenterX, circleCenterY - Hx, dip2px(Hr), mPaint);
    }

    /**
     * float fromXDelta 动画开始的点离当前View X坐标上的差值
     * float toXDelta 动画结束的点离当前View X坐标上的差值
     * float fromYDelta 动画开始的点离当前View Y坐标上的差值
     * float toYDelta 动画开始的点离当前View Y坐标上的差值
     */
    private float sAceg = (float) (moveDisance / Math.sqrt(2));//ACEG这四个点的变化规律
    private float sGeca = 0f;
    private float sBdfh = moveDisance;//ACEG这四个点的变化规律
    private float sHfdb = 0f;

    private float sRrMax = 5f;//小圆点 半径的变化
    private float sRrMin = 0f;

    //开始动画
    public void startAnimator() {
        /*post(new Runnable() {
            @Override
            public void run() {
                RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(-1);
                animation.setDuration(1000);
                animation.setFillAfter(true);
                startAnimation(animation);
            }
        });*/
        closeMyAnim = true;
        this.setVisibility(VISIBLE);
        sAceg = (float) (moveDisance / Math.sqrt(2));//ACEG这四个点的变化规律
        sGeca = 0f;
        sBdfh = moveDisance;//ACEG这四个点的变化规律
        sHfdb = 0f;

        sRrMax = 5f;//小圆点 半径的变化
        sRrMin = 0f;
        post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator anim = ValueAnimator.ofFloat(sGeca, sAceg);
                anim.setDuration(moveSpeed);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        Ax = currentValue;
                        invalidate();
                    }
                });
                anim.start();
                ValueAnimator animA = ValueAnimator.ofFloat(sRrMin, sRrMax);
                animA.setDuration(250);
                animA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        Ar = currentValue;
                        invalidate();
                    }
                });
                animA.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(sHfdb, sBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Bx = currentValue;
                                invalidate();
                                if (Bx == moveDisance) {
                                    xScaleMyView();
                                }
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(sRrMin, sRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Br = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(sGeca, sAceg);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Cx = currentValue;
                                invalidate();
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(sRrMin, sRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Cr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*2);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(sHfdb, sBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Dx = currentValue;
                                invalidate();

                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(sRrMin, sRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Dr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*3);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(sGeca, sAceg);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Ex = currentValue;
                                invalidate();
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(sRrMin, sRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Er = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*4);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(sHfdb, sBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Fx = currentValue;
                                invalidate();

                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(sRrMin, sRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Fr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*5);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(sGeca, sAceg);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Gx = currentValue;
                                invalidate();
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(sRrMin, sRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Gr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*6);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(sHfdb, sBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                    Hx = currentValue;
                                    invalidate();

                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(sRrMin, sRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Hr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*7);
            }
        });
    }
    private float nAceg = 0f;//ACEG这四个点的变化规律
    private float nGeca =(float) (moveDisance / Math.sqrt(2));
    private float nBdfh = 0f;//ACEG这四个点的变化规律
    private float nHfdb = moveDisance;

    private float nRrMax = 0f;//小圆点 半径的变化
    private float nRrMin = 5f;
    private void xScaleMyView() {

        post(new Runnable() {
            @Override
            public void run() {
                ValueAnimator anim = ValueAnimator.ofFloat(nGeca, nAceg);
                anim.setDuration(moveSpeed);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        Ax = currentValue;
                        invalidate();
                    }
                });
                anim.start();
                ValueAnimator animA = ValueAnimator.ofFloat(nRrMin, nRrMax);
                animA.setDuration(250);
                animA.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float currentValue = (float) animation.getAnimatedValue();
                        Ar = currentValue;
                        invalidate();
                    }
                });
                animA.start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(nHfdb, nBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Bx = currentValue;
                                invalidate();
                                if (Bx == 0 && closeMyAnim) {
                                    startAnimator();
                                }
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(nRrMin, nRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Br = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(nGeca, nAceg);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Cx = currentValue;
                                invalidate();
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(nRrMin, nRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Cr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*2);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(nHfdb, nBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Dx = currentValue;
                                invalidate();

                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(nRrMin, nRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Dr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*3);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(nGeca, nAceg);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Ex = currentValue;
                                invalidate();
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(nRrMin, nRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Er = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*4);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(nHfdb, nBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Fx = currentValue;
                                invalidate();

                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(nRrMin, nRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Fr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*5);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(nGeca, nAceg);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Gx = currentValue;
                                invalidate();
                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(nRrMin, nRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Gr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*6);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ValueAnimator anim = ValueAnimator.ofFloat(nHfdb, nBdfh);
                        anim.setDuration(moveSpeed);
                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Hx = currentValue;
                                invalidate();

                            }
                        });
                        anim.start();
                        ValueAnimator animB = ValueAnimator.ofFloat(nRrMin, nRrMax);
                        animB.setDuration(250);
                        animB.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float currentValue = (float) animation.getAnimatedValue();
                                Hr = currentValue;
                                invalidate();
                            }
                        });
                        animB.start();
                    }
                }, durationMy*7);
            }
        });
    }
    private boolean closeMyAnim = true;

    /**
     * 关闭动画
     */
    public void setAnimatorClose(){
        closeMyAnim = false;
        this.setVisibility(GONE);
    }
    public void setPaintColor(int color) {
        mPaint.setColor(color);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
