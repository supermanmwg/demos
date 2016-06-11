package com.library;

import android.animation.Animator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by weiguangmeng on 16/4/19.
 */
public class SwipeHelper implements View.OnTouchListener {
    private static final String TAG = " SwipeHelper";
    private SwipeCard swipeCard;
    private View obserView;
    private int initX;
    private int initY;

    private int mLastX;
    private int mLastY;
    private int mDownX;
    private int mDownY;

    public void register(SwipeCard card, View view, int initX, int initY) {
        swipeCard = card;
        obserView = view;
        initX = initX;
        initY = initY;
        obserView.setOnTouchListener(this);
    }

    public void unregisterObservedView() {
        if (obserView != null) {
            obserView.setOnTouchListener(null);
        }
        obserView = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "swipe is action down");
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - mLastX;
                int dy = y - mLastY;

                int newX = (int) (obserView.getX() + dx);
                int newY = (int) (obserView.getY() + dy);
                obserView.setX(newX);
                obserView.setY(newY);
                break;
            case MotionEvent.ACTION_UP:
                int distanceX = x - mDownX;
                int interval = swipeCard.getWidth() / 3;

                if(Math.abs(distanceX) > interval) {
                    if(distanceX > 0) {
                        swipeRight();
                    } else {
                        swipeLeft();
                    }
                } else {
                    reset();
                }
                break;
        }

        mLastX = x;
        mLastY = y;

        return true;
    }

    private void swipeLeft() {
        obserView.animate()
                .x(-swipeCard.getWidth() - obserView.getWidth())
                .setDuration(500)
                .setListener(new AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        swipeCard.removeTopView();
                    }
                });
    }

    private void swipeRight() {
        obserView.animate()
                .x(swipeCard.getWidth() + obserView.getWidth())
                .setDuration(500)
                .setListener(new AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        swipeCard.removeTopView();
                    }
                });
    }

    private void reset() {
        obserView.animate()
                .x(initX)
                .y(initY)
                .setDuration(500);
    }
}
