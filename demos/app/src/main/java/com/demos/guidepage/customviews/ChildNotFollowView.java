package com.demos.guidepage.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by weiguangmeng on 16/3/21.
 */
public class ChildNotFollowView extends LinearLayout {
    private static final String TAG = "ChildNotFollowView";
    private HorizontalScrollView horizontalScrollView;

    public ChildNotFollowView(Context context) {
        super(context);
    }

    public ChildNotFollowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "child is action down");
                horizontalScrollView.requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "child not follow moves");

                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(ev);
    }


    public void setHorizontalScrollView(HorizontalScrollView horizontalScrollView) {
        this.horizontalScrollView = horizontalScrollView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
