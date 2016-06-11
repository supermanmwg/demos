package com.demos.lineandcircle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.demos.R;
import com.demos.lineandcircle.customviews.LineAndCircleView;


public class LineAndCircleActivity extends AppCompatActivity {

    private LineAndCircleView lineAndCircleView;
    private Button startButton;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LineAndCircleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_circle);

        startButton = (Button) findViewById(R.id.start);
        lineAndCircleView = (LineAndCircleView) findViewById(R.id.line_circle);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineAndCircleView.start();
            }
        });
    }


}
