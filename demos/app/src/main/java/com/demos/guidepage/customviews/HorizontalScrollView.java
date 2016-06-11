package com.demos.guidepage.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by weiguangmeng on 16/3/20.
 */
public class HorizontalScrollView extends ViewGroup {

    private static final String TAG = "HorizontalScrollView";
    private static final int SCROLL_TIME = 300;
    int mChildIndex = 1;
    int mChildWidth;
    int mChildSize;

    int mLastX;
    int mLastY;
    int mLastInterceptX;
    int mLastInterceptY;
    int mOffset;
    boolean mIsInit = false;

    VelocityTracker mVelocityX;
    Scroller mScroller;

    public HorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        mIsInit = true;
    }

    private void init() {
        mVelocityX = VelocityTracker.obtain();
        mScroller = new Scroller(getContext());
        mOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int action = ev.getAction();
        if(action == MotionEvent.ACTION_DOWN) {
            mLastX = x;
            mLastY = y;
            mLastInterceptX = x;
            mLastInterceptY = y;

            Log.d(TAG, "action down intercepted false");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityX.addMovement(event);
        int action = event.getAction();
        int x = (int) event.getX();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    Log.d(TAG, "scroller is not finish!");
                    mScroller.abortAnimation();
                }
                Log.d(TAG, "get scroll x is " + getScrollX());
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "parent view is moving!");
                int deltaX = x - mLastInterceptX;
                scrollBy(-deltaX, 0);
                break;
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if(mChildIndex == 0 || mChildIndex == 2) {
                    int dx = mChildWidth * 1 - scrollX;
                    setSmoothScroll(dx, 0 ,SCROLL_TIME);
                    mChildIndex = 1;
                    return true;
                }

                mVelocityX.computeCurrentVelocity(1000);
                float velocityX = mVelocityX.getXVelocity();
                int mLastChildIndex = mChildIndex;
                if (Math.abs(velocityX) >= 50) {
                    mChildIndex = velocityX > 0 ? mChildIndex - 1 : mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }

                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildSize - 1));
                int offset = 0;
                if(mLastChildIndex != 0 && mLastChildIndex != mChildSize - 1) {
                    offset =  (mLastChildIndex > mChildIndex ? mOffset :  0 - mOffset);
                }

                if(1 == mChildIndex && 1 == mLastChildIndex) {
                    offset = 0;
                }

                Log.d(TAG, "child index is " + mChildIndex + ", last child index is " + mLastChildIndex
                    + ",offset is " + offset);
                int dx = mChildIndex * mChildWidth - scrollX + offset;
                setSmoothScroll(dx ,0, SCROLL_TIME);

                mVelocityX.clear();  //reset velocity
                break;
        }

        mLastInterceptX = x;
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = 0;
        int measureHeight = 0;

        mChildSize = getChildCount();

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthSpaceMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpaceMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);

        if(mChildSize == 0)  {
            setMeasuredDimension(0, 0);
        } else if(widthSpaceMode == MeasureSpec.AT_MOST && heightSpaceMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * mChildSize;
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (widthSpaceMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * mChildSize;
            setMeasuredDimension(measureWidth, heightSpaceSize);
        } else if (heightSpaceMode == MeasureSpec.AT_MOST) {
            final View childView = getChildAt(0);
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(widthSpaceSize, measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        int childCount = getChildCount();
        mChildSize = childCount;
        mChildWidth = getChildAt(0).getMeasuredWidth();

        for(int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if(childView.getVisibility() != View.GONE) {
                int childWidth = childView.getMeasuredWidth();   //必须用measureWidth,不能用getWidth
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
        Log.d(TAG, "onLayout");
        int time = 0;
        if(mChildIndex  == 1) {
            time = SCROLL_TIME;
        }
        setSmoothScroll(mChildWidth - getScrollX(), 0, time);
    }

    public void setSmoothScroll(int dx, int dy, int time) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, time);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityX.recycle();
        super.onDetachedFromWindow();
    }

    public void goToChild1() {
        int dx = mChildWidth * 0 - getScrollX() - mOffset;
        setSmoothScroll(dx, 0, SCROLL_TIME);
        mChildIndex = 0;
    }

    public void goToChild2() {
        Log.d(TAG, "mOffset is " + mOffset + ", scroll x "  +getScrollX());
        int dx = mChildWidth * 1 - getScrollX();
        setSmoothScroll(dx, 0, SCROLL_TIME);
        mChildIndex = 1;
    }

    public void goToChild3() {
        Log.d(TAG, "mOffset is " + mOffset + ", scroll x "  +getScrollX());
        int dx = mChildWidth * 2 - getScrollX()- mOffset;
        setSmoothScroll(dx, 0, SCROLL_TIME);
        mChildIndex = 2;
    }
}
