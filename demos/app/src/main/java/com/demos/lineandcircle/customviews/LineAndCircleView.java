package com.demos.lineandcircle.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weiguangmeng on 16/5/15.
 */
public class LineAndCircleView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private Canvas mCanvas;
    private Paint mPaint;
    private Paint mCirclePaint;
    private SurfaceHolder mHolder;
    private List<float[]> runningPoints = new ArrayList<>();
    private boolean mIsRunning;
    private Path mPath;
    private boolean isStart = false;
    private int startNum = 0;

    public LineAndCircleView(Context context) {
        this(context, null);
    }

    public LineAndCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineAndCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mCirclePaint.setStrokeJoin(Paint.Join.ROUND);
     //   mCirclePaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsRunning = true;
        new Thread(this).start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsRunning = false;
    }

    @Override
    public void run() {
        while (mIsRunning) {
            draw();
        }
    }

    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath, mPaint);
            if(isStart) {
                if(startNum < runningPoints.size()) {
                    mCanvas.drawCircle(runningPoints.get(startNum)[0], runningPoints.get(startNum)[1], 30, mCirclePaint);
                    Thread.sleep(1);
                    startNum ++;
                } else {
                    mCanvas.drawCircle(runningPoints.get(startNum - 1)[0], runningPoints.get(startNum -1)[1], 30, mCirclePaint);
                   // isStart = false;
                }
            }

        }catch (Exception e) {

        }finally {
            if(mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    public void start() {
        isStart = true;
        startNum = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                isStart = false;
                runningPoints.clear();
                runningPoints.add(new float[]{x, y});
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                runningPoints.add(new float[]{x, y});
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }
}
