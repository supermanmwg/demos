package com.demos.xfermodeview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.demos.R;

public class ScratchActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ScratchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);
    }
}
