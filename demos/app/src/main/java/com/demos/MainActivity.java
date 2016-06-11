package com.demos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.demos.alterpic.AlterNumActivity;
import com.demos.angryball.AngryBallActivity;
import com.demos.clock.ClockActivity;
import com.demos.customrects.ChartActivity;
import com.demos.guidepage.TantanActivity;
import com.demos.multipletouch.MultipleTouchActivity;
import com.demos.remote.RemoteActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.simulate_tantan)
    public void onTantanClick(View v) {
        TantanActivity.startActivity(MainActivity.this);
    }
    @OnClick(R.id.angry_ball)
    public void onAngryBallClick(View v) {
        AngryBallActivity.startActivity(MainActivity.this);

    }
    @OnClick(R.id.alter_num)
    public void onNumClick(View v) {
        AlterNumActivity.startActivity(MainActivity.this);

    }
    @OnClick(R.id.clock)
    public void onClockClick(View v) {
        ClockActivity.startActivity(MainActivity.this);

    }
    @OnClick(R.id.mosaic)
    public void onMosaicClick(View v) {

    }
    @OnClick(R.id.tear_image)
    public void onTearImageClick(View v) {

    }
    @OnClick(R.id.custom_chart)
    public void onChartClick(View v) {
        ChartActivity.startActivity(MainActivity.this);

    }
    @OnClick(R.id.bigger)
    public void onBiggerClick(View v) {
        MultipleTouchActivity.startActivity(MainActivity.this);

    }
    @OnClick(R.id.custom_fly_ball)
    public void onFlyBallClick(View v) {

    }
    @OnClick(R.id.coursera)
    public void onCourseraClick(View v) {

    }
    @OnClick(R.id.remote_control)
    public void onRemoteControlClick(View v) {

    }
    @OnClick(R.id.flying_snow)
    public void onFlyingSnowClick(View v) {
        RemoteActivity.startActivity(MainActivity.this);
    }
    @OnClick(R.id.scratch_card)
    public void onScratchCardClick(View v) {

    }
    @OnClick(R.id.chat_head)
    public void onChatHeadClick(View v) {

    }
    @OnClick(R.id.custom_progress_bar)
    public void onProgresBarClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
