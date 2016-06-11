package com.demos.guidepage.listeners;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by weiguangmeng on 16/3/30.
 */
public class CustomTouchListener implements View.OnTouchListener {

    private final static String TAG = "CustomTouchListener";
    private final static int MESSAGE_TRANSLATION = 1;
    private View mTouchView;
    private Scroller mScroller;
    private int mLastX;
    private int mLastY;
    private int mFirstX;
    private int mFirstY;
    private VelocityTracker mVelocityX;
    private static int count = 0;

    private float scrollLength = 1000 * 2;

    public CustomTouchListener(View mTouchView) {
        this.mTouchView = mTouchView;
        this.mScroller = new Scroller(mTouchView.getContext());
        this.mVelocityX = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mVelocityX.addMovement(event);
        int action = event.getAction();
        int x = (int) event.getRawX();  // 如果是getX,会出现闪动
        int y = (int) event.getRawY();
        int deltaX = 0;
        int deltaY = 0;
        Log.d(TAG, "x is " + x + ", y is " + y + ", mLastX is " + mLastX + ", mLastY is " + mLastY);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mFirstX = (int) event.getRawX();
                mFirstY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = x - mLastX;
                deltaY = y - mLastY;
               // Log.d(TAG, "deltaX is " + deltaX + ", deltaY is " + deltaY);
                int translationX = (int) (ViewHelper.getTranslationX(mTouchView)) + deltaX;
                int translationY = (int) (ViewHelper.getTranslationY(mTouchView)) + deltaY;
                ViewHelper.setTranslationX(mTouchView, translationX);
                ViewHelper.setTranslationY(mTouchView, translationY);
                break;
            case MotionEvent.ACTION_UP:
                mVelocityX.computeCurrentVelocity(1000);

                float velocityX =  mVelocityX.getXVelocity();
                float velocityY = mVelocityX.getYVelocity();
                if(Math.abs(velocityX) >= 100) {
                    deltaX = x - mLastX;
                    deltaY = y - mLastY;
                    Log.d(TAG, "deltaX is " + deltaX + ", deltaY is " + deltaY);
                    Log.d(TAG, "velocityX is " + velocityX + ", velocityY is " + velocityY);
                    float scrollX = (x - mFirstX) > 0 ? scrollLength : -scrollLength;
                    float scrollY = (y - mFirstY) > 0 ? scrollLength / 2 : -scrollLength /2;
                    final float translateX = (ViewHelper.getTranslationX(mTouchView)) + scrollX;
                    final float translateY = (ViewHelper.getTranslationY(mTouchView)) + scrollY;
                    final float startX = ViewHelper.getTranslationX(mTouchView);
                    final float startY = ViewHelper.getTranslationY(mTouchView);
                    final float fractionX = translateX /30;
                    final float fractionY = translateY /30;
                    count = 0;
                    final Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            switch (msg.what) {
                                case MESSAGE_TRANSLATION:
                                    count++;
                                    if(count <= 30) {
                                        ViewHelper.setTranslationX(mTouchView, startX + fractionX * count);
                                        ViewHelper.setTranslationY(mTouchView, startY + fractionY * count);
                                        sendEmptyMessageDelayed(MESSAGE_TRANSLATION, 20);
                                    }
                                    break;
                            }
                        }
                    };

                    handler.sendEmptyMessage(MESSAGE_TRANSLATION);
                } else {
                    deltaX = mFirstX - x;
                    deltaY = mFirstY - y;

                    float transX = ViewHelper.getTranslationX(mTouchView) + deltaX;
                    float transY = ViewHelper.getTranslationY(mTouchView) +  deltaY;
                    ViewHelper.setTranslationX(mTouchView, transX);
                    ViewHelper.setTranslationY(mTouchView, transY);
                }

                mVelocityX.clear();
                break;
        }

        mLastX = x;
        mLastY = y;
        return true;
    }
}
