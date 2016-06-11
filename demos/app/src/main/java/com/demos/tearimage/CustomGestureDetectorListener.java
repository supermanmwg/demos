package com.demos.tearimage;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by supermanmwg on 15-12-29.
 */
public class CustomGestureDetectorListener implements GestureDetector.OnGestureListener {

    private View moveView;
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
