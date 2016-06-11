package com.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

/**
 * Created by weiguangmeng on 16/4/19.
 */
public class SwipeCard extends ViewGroup {
    private static String TAG = "SwipeCard";

    private static final float DEFAULT_SCALE = 1.0f;
    private static final float DEFAULT_STATIC_ROTATION = 0;
    private static final float DEFAULT_DYNAMIC_ROTATION = 0;
    private static final int   DEFAULT_STACK_SIZE = 4;
    private static final int   DEFAULT_ANIMATION_DURATION = 500;

    //attributes
    private float scale;
    private float staticRotation;
    private float dynamicRotation;
    private int interval;
    private int stackSize;
    private int duration;

    private Adapter adapter;
    private View topView;
    private SwipeHelper swipeHelper;

    private int mCurrentIndex = 0;

    private boolean mIsRefreshing = true;


    public SwipeCard(Context context) {
        this(context, null);
    }

    public SwipeCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttr(attrs);

        init();
    }

    private void readAttr(AttributeSet attr) {
        TypedArray types = getContext().obtainStyledAttributes(attr, R.styleable.SwipeCard);
        scale = types.getFloat(R.styleable.SwipeCard_scale, DEFAULT_SCALE);
        staticRotation = types.getFloat(R.styleable.SwipeCard_static_rotation, DEFAULT_STATIC_ROTATION);
        dynamicRotation = types.getFloat(R.styleable.SwipeCard_dynamic_rotation, DEFAULT_DYNAMIC_ROTATION);
        interval = types.getInt(R.styleable.SwipeCard_interval, getResources().getDimensionPixelOffset(R.dimen.default_interval));
        stackSize = types.getInt(R.styleable.SwipeCard_stack_size, DEFAULT_STACK_SIZE);
        duration = types.getInt(R.styleable.SwipeCard_animation_duration, DEFAULT_ANIMATION_DURATION);

        types.recycle();
    }

    private void init() {
        setClipChildren(false);
        setClipToPadding(false);
        swipeHelper = new SwipeHelper();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout");
        if(adapter == null || adapter.isEmpty()) {
            mCurrentIndex = 0;
            removeAllViewsInLayout();
        }

       for(int i = getChildCount(); i < stackSize && mCurrentIndex < adapter.getCount(); i++) {
            Log.d(TAG, "i is " + i + ", mCurrent index is " + mCurrentIndex);
            addNewItem();
        }

        reOrderItems();

        mIsRefreshing = false;
    }

    private void addNewItem() {
        View childView = adapter.getView(mCurrentIndex, null, this); //
        childView.setTag(R.id.is_new_card, true); //

        LayoutParams lp = childView.getLayoutParams();  //
        if(null == lp) {
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }

        int width = getWidth() - (getPaddingRight() + getPaddingLeft());
        int height = getHeight() - (getPaddingTop() + getPaddingBottom());

        int widthMode = MeasureSpec.AT_MOST;
        int heightMode = MeasureSpec.AT_MOST;

        if (lp.width == LayoutParams.MATCH_PARENT) {  //
            widthMode = MeasureSpec.EXACTLY;
        } else if (lp.width > 0) {
            widthMode = MeasureSpec.EXACTLY;
            width = lp.width;
        }

        if(lp.height == LayoutParams.MATCH_PARENT) {
            heightMode = MeasureSpec.EXACTLY;
        } else if (lp.height > 0) {
            heightMode = MeasureSpec.EXACTLY;
            height = lp.height;
        }

        int widthSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        int heightSpec = MeasureSpec.makeMeasureSpec(height, heightMode);  //

        childView.measure(widthSpec, heightSpec);

        addViewInLayout(childView, 0, lp, true);   // 0

        mCurrentIndex++;
    }

    private void reOrderItems() {
       int topViewIndex = getChildCount() - 1;
        Log.d(TAG, "child count is " + getChildCount());

        for(int i = getChildCount() - 1; i>=0 ; i--) {
            View childView = getChildAt(i);    //

            int viewPosY = getPaddingTop() + (topViewIndex - i) * interval;
            int viewPosX = (getWidth() - childView.getMeasuredWidth())/2;
            Log.d(TAG, "view pos x is " + viewPosX + ",view pos y is " + viewPosY);
            childView.layout(viewPosX, 0, viewPosX + childView.getMeasuredWidth(), childView.getMeasuredHeight());

            if(topViewIndex == i) {
                swipeHelper.unregisterObservedView();
                topView = childView;
                swipeHelper.register(this, topView, viewPosX, viewPosY);
            }

            boolean isNewPic = (boolean) childView.getTag(R.id.is_new_card);
            if(!mIsRefreshing) {
                if(isNewPic) {
                    childView.setY(viewPosY);
                    childView.setAlpha(0);
                    childView.setScaleX(scale);
                    childView.setScaleY(scale);
                    childView.setTag(R.id.is_new_card, false);
                }

                childView.animate()
                        .alpha(1)
                        .y(viewPosY)
                        .x(viewPosX)
                        .rotation(staticRotation)
                        .scaleX(scale)
                        .scaleY(scale)
                        .setDuration(duration);

            } else {
                childView.setTag(R.id.is_new_card, false);
                childView.setY(viewPosY);
                childView.setScaleX(scale);
                childView.setScaleY(scale);
                childView.setRotation(staticRotation);
            }
        }
    }

    public void removeTopView() {
        removeView(topView);
    }
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }
}
