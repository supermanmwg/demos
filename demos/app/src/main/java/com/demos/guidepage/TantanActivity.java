package com.demos.guidepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.demos.R;
import com.demos.guidepage.customviews.ChildFollowView;
import com.demos.guidepage.customviews.ChildNotFollowView;
import com.demos.guidepage.customviews.HorizontalScrollView;
import com.demos.guidepage.fragments.anonymousFragment;
import com.demos.guidepage.fragments.freshGuideFragment;
import com.demos.guidepage.fragments.recommedFragment;
import com.demos.guidepage.fragments.settingFragment;
import com.demos.guidepage.fragments.tantanFragment;


public class TantanActivity extends AppCompatActivity  {

    private HorizontalScrollView horizontalScrollView;
    private ChildFollowView middle;
    private ChildNotFollowView first;
    private ChildNotFollowView last;

    private View tantan;
    private View message;
    private View anonymous;
    private View setting;
    private View freshGuide;
    private View recommend;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, TantanActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d("haha", "persistent");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.hide();
        }
        setContentView(R.layout.tantan_main);
        setMiddle(new tantanFragment(), true);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.parent);
        middle = (ChildFollowView) findViewById(R.id.middle);
        first = (ChildNotFollowView) findViewById(R.id.first);
        last = (ChildNotFollowView) findViewById(R.id.last);
        tantan = findViewById(R.id.tantan);
        tantan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TantanActivity.this, "tantan", Toast.LENGTH_SHORT).show();
                setMiddle(new tantanFragment(), false);
                horizontalScrollView.goToChild2();
            }
        });

        message = findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TantanActivity.this, "message", Toast.LENGTH_SHORT).show();
                horizontalScrollView.goToChild3();
            }
        });

        anonymous = findViewById(R.id.anonymous);
        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TantanActivity.this, "anonymous", Toast.LENGTH_SHORT).show();
                setMiddle(new anonymousFragment(), false);
                horizontalScrollView.goToChild2();
            }
        });

        freshGuide = findViewById(R.id.fresher_guide);
        freshGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TantanActivity.this, "freshGuide", Toast.LENGTH_SHORT).show();
                setMiddle(new freshGuideFragment(), false);
                horizontalScrollView.goToChild2();
            }
        });


        setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TantanActivity.this, "setting", Toast.LENGTH_SHORT).show();
                setMiddle(new settingFragment(), false);
                horizontalScrollView.goToChild2();
            }
        });

        recommend = findViewById(R.id.recommend);
        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TantanActivity.this, "recommend", Toast.LENGTH_SHORT).show();
                setMiddle(new recommedFragment(), false);
                horizontalScrollView.goToChild2();
            }
        });

        middle.setHorizontalScrollView(horizontalScrollView);
        first.setHorizontalScrollView(horizontalScrollView);
        last.setHorizontalScrollView(horizontalScrollView);
    }

    private void  setMiddle(Fragment fragment, boolean onStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.middle, fragment);
        if(onStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}
