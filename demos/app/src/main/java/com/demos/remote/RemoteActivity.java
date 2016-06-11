package com.demos.remote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.demos.R;
import com.facebook.drawee.backends.pipeline.Fresco;

public class RemoteActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,RemoteActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_tear_image);
        final ImageView displayView = (ImageView) findViewById(R.id.display);
        final FirePicView fireView = (FirePicView) findViewById(R.id.fire_view);
        fireView.setDisplayView(displayView);
        fireView.setDrawingCacheEnabled(true);
        fireView.buildDrawingCache(true);
    }
}
