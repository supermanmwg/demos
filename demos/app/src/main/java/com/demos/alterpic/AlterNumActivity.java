package com.demos.alterpic;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.demos.R;

public class AlterNumActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     */
    private ImageView topView1;
    private ImageView topView2;
    private ImageView bottomView1;
    private ImageView bottomView2;
    private Animation mAnimation;


    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AlterNumActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alter_num_main);
        topView1 = (ImageView) findViewById(R.id.pic_top_1);
        topView2 = (ImageView) findViewById(R.id.pic_top_2);

        bottomView1 = (ImageView) findViewById(R.id.pic_bottom_1);
        bottomView2 = (ImageView) findViewById(R.id.pic_bottom_2);

        mAnimation = AnimationUtils.loadAnimation(AlterNumActivity.this, R.anim.front_scale);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                topView2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               topView1.setImageResource(topViews[(recLen+1)%10]);
                bottomView2.setVisibility(View.VISIBLE);
                bottomView2.startAnimation(AnimationUtils.loadAnimation( AlterNumActivity.this, R.anim.back_scale));
            }
        });

        handler.postDelayed(runnable, 1000);
    }

    private int[] topViews = 
            new int[]{R.drawable.top_0, R.drawable.top_1, R.drawable.top_2, R.drawable.top_3, 
                      R.drawable.top_4,R.drawable.top_5,R.drawable.top_6, R.drawable.top_7,
                      R.drawable.top_8,R.drawable.top_9};
    private int[] bottomViews =
            new int[]{R.drawable.bottom_0, R.drawable.bottom_1, R.drawable.bottom_2, R.drawable.bottom_3,
                    R.drawable.bottom_4,R.drawable.bottom_5,R.drawable.bottom_6, R.drawable.bottom_7,
                    R.drawable.bottom_8,R.drawable.bottom_9};

    private int recLen = 0;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen++;
            topView2.setVisibility(View.INVISIBLE);
            bottomView2.setVisibility(View.INVISIBLE);
            topView1.setImageResource(topViews[recLen%10]);
            bottomView1.setImageResource(bottomViews[recLen%10]);
            topView2.setImageResource(topViews[(recLen+1)%10]);
            bottomView2.setImageResource(bottomViews[(recLen+1)%10]);
            topView1.startAnimation(mAnimation);
            handler.postDelayed(this, 1000);
        }
    };
}