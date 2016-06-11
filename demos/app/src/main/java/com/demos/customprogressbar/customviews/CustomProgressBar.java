package com.demos.customprogressbar.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demos.R;

/**
 * Created by mwg on 16-5-9.
 */
public class CustomProgressBar extends View {
    private final String TAG = "CustomProgressBar";
    private final int TIME_ROTATE_INTERVAL = 50;
    private final int TIME_ROTATE_TIME = (800 / TIME_ROTATE_INTERVAL);

    private TextView mLoadingTv;
    private int progress = 0;
    private Paint mPaint;
    private String loadingText;
    private boolean hasLoadingTv;
    private int firstColor;
    private int secondColor;
    private int loadingTextSize;
    private int backgroundColor;
    private int textColor;
    private int radius;
    private int strokeWidth;
    private boolean isRunning = true;
    private boolean isNext;
    private Thread runningThread;

    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomProgressBar);
        progress = a.getInteger(R.styleable.CustomProgressBar_progress, 0);
        loadingText = a.getString(R.styleable.CustomProgressBar_loading_text);
        hasLoadingTv = a.getBoolean(R.styleable.CustomProgressBar_has_loading_tv, loadingText != null);
        firstColor = a.getColor(R.styleable.CustomProgressBar_first_color, getResources().getColor(R.color.default_first_color));
        secondColor = a.getColor(R.styleable.CustomProgressBar_second_color, getResources().getColor(R.color.default_second_color));
        loadingTextSize = a.getDimensionPixelOffset(R.styleable.CustomProgressBar_loading_text_size, getResources().getDimensionPixelOffset(R.dimen.default_loading_text_size));
        textColor = a.getColor(R.styleable.CustomProgressBar_text_color, getResources().getColor(R.color.default_loading_text_color));
        backgroundColor = a.getColor(R.styleable.CustomProgressBar_background_color, getResources().getColor(R.color.default_background_color));
        radius = a.getDimensionPixelOffset(R.styleable.CustomProgressBar_circle_radius, getResources().getDimensionPixelOffset(R.dimen.default_circle_radius_size));
        strokeWidth = a.getDimensionPixelOffset(R.styleable.CustomProgressBar_stroke_width, getResources().getDimensionPixelOffset(R.dimen.default_stroke_width));
        a.recycle();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setIsRunning(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int padding = Math.max(radius, loadingTextSize / 2) / 2 ;
        int width = radius * 2 + (loadingText != null ? loadingTextSize * loadingText.length() + padding : 0) + 2 * padding;
        int height = padding * 2 + padding * 4;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if(visibility == VISIBLE) {
            setIsRunning(true);
        } else {
            setIsRunning(false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setIsRunning(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int padding = Math.max(radius, loadingTextSize / 2) / 2 ;
        int width = radius * 2 + (loadingText != null ? loadingTextSize * loadingText.length() + padding : 0) + 2 * padding;
        int height = padding * 2 + padding * 4;

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(backgroundColor);

        //set Background
        int left = (getWidth() - width) / 2;
        RectF roundRect = new RectF(left, 0, left + width, height);
        canvas.drawRoundRect(roundRect, 15, 15, mPaint);

        //set text
        mPaint.setColor(textColor);
        mPaint.setTextSize(loadingTextSize);
        float textLeft = padding * 2 + radius * 2;
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        float baseline = (roundRect.bottom + roundRect.top - fontMetrics.top - fontMetrics.bottom) /2;
        if(loadingText != null)
        canvas.drawText(loadingText, 0, loadingText.length(), left + textLeft, baseline, mPaint);

        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        float centerX = left + padding + radius;
        float centerY = height / 2;
        //set circle
        if(isNext){
            mPaint.setColor(firstColor);
            RectF circleRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            canvas.drawCircle(centerX, centerY, radius, mPaint);
            mPaint.setColor(secondColor);
            canvas.drawArc(circleRectF,  progress * 18, progress * 18, false, mPaint);
        } else {
            mPaint.setColor(secondColor);
            RectF circleRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
            canvas.drawCircle(centerX, centerY, radius, mPaint);
            mPaint.setColor(firstColor);
            canvas.drawArc(circleRectF,  progress * 18, progress * 18, false, mPaint);
        }
    }

    private class rotateRunnable implements Runnable {

        @Override
        public void run() {
            while (isRunning) {
                try {
                    Thread.sleep(TIME_ROTATE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                progress ++;
                postInvalidate();
                if(TIME_ROTATE_TIME == progress) {
                    progress = 0;
                    isNext = !isNext;
                }
            }

            Log.d(TAG, "running exit");
        }
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    public void setHasLoadingTv(boolean hasLoadingTv) {
        this.hasLoadingTv = hasLoadingTv;
    }

    public void setSecondColor(int secondColor) {
        this.secondColor = secondColor;
    }

    public void setFirstColor(int firstColor) {
        this.firstColor = firstColor;
    }

    public void setLoadingTextSize(int loadingTextSize) {
        this.loadingTextSize = loadingTextSize;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
        if(isRunning == true) {
            if (runningThread == null || !runningThread.isAlive()) {
                runningThread = new Thread(new rotateRunnable());
                runningThread.start();
            } else {
                Log.d(TAG, "running thread is running");
            }
        }
    }

    public int getProgress() {
        return progress;
    }

    public String getLoadingText() {
        return loadingText;
    }

    public boolean isHasLoadingTv() {
        return hasLoadingTv;
    }

    public int getSecondColor() {
        return secondColor;
    }

    public int getFirstColor() {
        return firstColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public int getLoadingTextSize() {
        return loadingTextSize;
    }

    public int getRadius() {
        return radius;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public boolean isNext() {
        return isNext;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
