package com.demos.remote;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demos.R;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by weiguangmeng on 16/1/3.
 */
public class FirePicView extends ViewGroup {
    private ImageView first;
    private SimpleDraweeView background;
    private ImageView foreground;
    private ImageView displayView;

    private float lastTranslationX;
    private float lastTranslationY;

    public FirePicView(Context context) {
        this(context, null);
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public FirePicView(Context context, AttributeSet attrs) {
        super(context, attrs);
      //  setWillNotDraw(false);
        first  = new ImageView(getContext());
        first.setBackgroundColor(Color.RED);
        LinearLayout.LayoutParams alp = new LinearLayout.LayoutParams(300, 400);
        alp.setMargins(0, 0, 0, 0);
        addView(first, alp);

        Uri data = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(R.drawable.bbb_splash))
                .build();

        DraweeController ctrl = Fresco.newDraweeControllerBuilder().setUri(
                data).setTapToRetryEnabled(true).build();

        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();

        background = new SimpleDraweeView(getContext());
        background.setController(ctrl);
        background.setBackgroundColor(Color.BLACK);
        LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(200, 200);
        blp.setMargins(10, 10, 10, 10);
        addView(background, blp);

        foreground = new ImageView(getContext());
        foreground.setBackgroundColor(Color.BLUE);
        LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(300, 400);
        flp.setMargins(40, 40, 40, 40);
        addView(foreground, flp);

        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                lastTranslationX = ViewHelper.getTranslationX(background);
                lastTranslationY = ViewHelper.getTranslationY(background);
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                ViewHelper.setTranslationX(background, lastTranslationX + e2.getRawX() - e1.getRawX());
                ViewHelper.setTranslationY(background, lastTranslationY + e2.getRawY() - e1.getRawY());

                FirePicView.this.setDrawingCacheEnabled(false);
                FirePicView.this.setDrawingCacheEnabled(true);
                Bitmap drawBitmap = FirePicView.this.getDrawingCache();
                displayView.setImageBitmap(drawBitmap);
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });

        background.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if(foreground != null) {
           measureChildWithMargins(foreground, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }

        if(background != null) {
            measureChildWithMargins(background, widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();

        if(first != null) {
            MarginLayoutParams lp = (MarginLayoutParams) first.getLayoutParams();
            int marginLeft = paddingLeft + lp.leftMargin;
            int marginTop = paddingTop + lp.topMargin;
            first.layout(marginLeft, marginTop, marginLeft + this.getMeasuredWidth(), marginTop + this.getMeasuredHeight());
        }

        if(foreground != null) {
            MarginLayoutParams lp = (MarginLayoutParams) foreground.getLayoutParams();
            int marginLeft = paddingLeft + lp.leftMargin;
            int marginTop = paddingTop + lp.topMargin;
            foreground.layout(marginLeft, marginTop, marginLeft + foreground.getMeasuredWidth(), marginTop + foreground.getMeasuredHeight());
        }

        if(background != null) {
            MarginLayoutParams blp = (MarginLayoutParams)background.getLayoutParams();
            int marginLeft = paddingLeft + blp.leftMargin;
            int marginTop = paddingLeft + blp.topMargin;
            background.layout(
                    this.getMeasuredWidth() - background.getMeasuredWidth() - marginLeft,
                    this.getMeasuredHeight() - background.getMeasuredHeight() - marginTop,
                    this.getMeasuredWidth() - marginTop,
                    this.getMeasuredHeight() - marginTop);
            Log.d("haha", "on layout");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("haha", "on Draw");
    }

    public void setDisplayView(ImageView view) {
        this.displayView = view;
    }

}
