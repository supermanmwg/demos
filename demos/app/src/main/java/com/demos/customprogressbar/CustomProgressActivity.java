package com.demos.customprogressbar;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demos.R;
import com.demos.customprogressbar.customviews.CustomProgressBar;


public class CustomProgressActivity extends AppCompatActivity {
    private CustomProgressBar mCustomProgressBar;
    private Button mControl;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomProgressActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress);

        mCustomProgressBar = (CustomProgressBar) findViewById(R.id.custom_progressBar);
        mControl = (Button) findViewById(R.id.control);
        mControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomProgressBar.getVisibility() == View.VISIBLE) {
                    mCustomProgressBar.setVisibility(View.GONE);
                } else {
                    mCustomProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}
