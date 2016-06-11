package com.library;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;

/**
 * Created by weiguangmeng on 16/4/19.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class AnimationEndListener implements Animator.AnimatorListener {
    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
