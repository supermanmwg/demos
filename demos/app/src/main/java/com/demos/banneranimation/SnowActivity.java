package com.demos.banneranimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import com.demos.R;

public class SnowActivity extends AppCompatActivity {

    private TextView helloTv;
    private Handler handler;
    private Runnable runnable;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SnowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        helloTv = (TextView) findViewById(R.id.hello);

        final ObjectAnimator down = new ObjectAnimator().ofFloat(helloTv, "rotation", 2);
        down.setDuration(100);
        down.setInterpolator(new AccelerateInterpolator());


        final ObjectAnimator up = new ObjectAnimator().ofFloat(helloTv, "rotation", -2);
        up.setDuration(100);
        up.setInterpolator(new AccelerateInterpolator());

        final AnimatorSet bounce = new AnimatorSet();
        bounce.setStartDelay(1000);
        bounce.playSequentially(down, up);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                bounce.start();
                Log.d("haha", "bounce is starting!");
                handler.postDelayed(this, 200);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != handler) {
            handler.removeCallbacks(runnable);
        }
    }
}
