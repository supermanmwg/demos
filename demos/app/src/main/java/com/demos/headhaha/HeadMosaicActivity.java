package com.demos.headhaha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.demos.R;
import com.demos.headhaha.utils.ImageHandle;

public class HeadMosaicActivity extends AppCompatActivity {

    private Bitmap bitmap;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HeadMosaicActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosaic);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.head);
        ((ImageView) findViewById(R.id.head)).setImageBitmap(ImageHandle.handleRectImage(bitmap));
    }
}
