package com.demos.guidepage.listeners;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by supermanmwg on 15-12-29.
 */
public class CustomGestureDetectorListener implements GestureDetector.OnGestureListener {

    private static final String TAG = "GestureDetectorListener";
    private View moveView;
    private float translationX;
    private float lastTranslationX;
    private float lastTranslationY;

    public void setMoveView(View v) {
        moveView = v;
    }
    @Override
    public boolean onDown(MotionEvent e) {
        lastTranslationX = ViewHelper.getTranslationX(moveView);
        lastTranslationY = ViewHelper.getTranslationY(moveView);
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
        moveView.setFocusable(false);
        ViewHelper.setTranslationX(moveView, lastTranslationX + e2.getRawX() - e1.getRawX());
        ViewHelper.setTranslationY(moveView, lastTranslationY + e2.getRawY() - e1.getRawY());
        translationX = ViewHelper.getTranslationX(moveView);
        int deltaX = (int)Math.abs(translationX - lastTranslationX);
        if(deltaX > moveView.getWidth() / 2) {
            Log.d(TAG, "delta x is " + deltaX + ", translation x is " + translationX + ", last trans x is " + lastTranslationX);

            ViewHelper.setTranslationX(moveView, 10000);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

}
