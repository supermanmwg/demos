package com.demos.multipletouch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demos.R;

public class MultipleTouchActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,  MultipleTouchActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_touch);
    }
}
