package com.demos.banneranimation.custom;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.demos.R;

/**
 * Created by weiguangmeng on 15/12/31.
 */
public class SnowflakeAnimView extends ViewGroup {

    private ImageView snowFlakeView;
    private Handler handler;
    private Runnable runnable;
    private int snowHeight;
    private int snowWidth;

    public SnowflakeAnimView(Context context) {
        this(context, null);
    }

    public SnowflakeAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        snowFlakeView =(ImageView) LayoutInflater.from(context).inflate(R.layout.snowflake, null);
        addView(snowFlakeView);
        snowWidth = getResources().getDimensionPixelOffset(R.dimen.snowflake_width);
        snowHeight = getResources().getDimensionPixelOffset(R.dimen.snowflake_height);
        final ObjectAnimator down = new ObjectAnimator().ofFloat(snowFlakeView, "rotation", 2);
        down.setDuration(100);
        down.setInterpolator(new AccelerateInterpolator());


        final ObjectAnimator up = new ObjectAnimator().ofFloat(snowFlakeView, "rotation", -2);
        up.setDuration(100);
        up.setInterpolator(new AccelerateInterpolator());

        final AnimatorSet bounce = new AnimatorSet();
        bounce.playSequentially(down, up);
        final AnimatorSet bounce2 = new AnimatorSet();
        final ObjectAnimator transYAnim = new ObjectAnimator().ofFloat(snowFlakeView, "translationY", 0f,1600f);
        final ObjectAnimator transXAnim = new ObjectAnimator().ofFloat(snowFlakeView, "translationX", 0f, 1400f);
        transYAnim.setInterpolator(new AccelerateInterpolator());
        transYAnim.setDuration(5000);
        transXAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        transXAnim.setDuration(5000);
        transYAnim.setRepeatCount(ValueAnimator.INFINITE);
        transXAnim.setRepeatCount(ValueAnimator.INFINITE);
        bounce2.play(transXAnim).with(transYAnim);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(true) {
                    bounce2.start();
                    Log.d("haha", "bounce is starting!");
                }

                handler.postDelayed(this, 5000);
                postInvalidate();
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (null != snowFlakeView) {
            LayoutParams lp = snowFlakeView.getLayoutParams();
            final int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec,
                    getPaddingLeft() + getPaddingRight(), lp.width);
            final int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec,
                    getPaddingTop() + getPaddingBottom(), lp.height);
            snowFlakeView.measure(childWidthMeasureSpec, childHeightMeasureSpec);

        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("haha", "l:" + l + " ,t:" + t + " ,r:" + r + " ,b:" + b);
        Log.d("haha", "snow flake width is " + snowWidth+ ", height is " + snowHeight);
        snowFlakeView.layout(0-snowWidth, 0 -snowHeight, 0, 0);
    }
}
