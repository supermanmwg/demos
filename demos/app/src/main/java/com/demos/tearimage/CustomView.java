package com.demos.tearimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.demos.R;

/**
 * Created by supermanmwg on 15-12-29.
 */
public class CustomView extends ImageView {

    private float top = 0;
    private float bottom = 0;
    private float right = 0;
    private float left = 0;
    private Paint paint;
    private Path path;
    Point leftTop, rightBottom, leftBottom, rightTop;
    private Bitmap clipBitmap;
    private Bitmap drawBitmap;
    private Context context;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        path = new Path();
        clipBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.haha);
        top = getTop();
        bottom = getBottom();
        right = getRight();
        left= getLeft();
        leftTop = new Point((int)left, (int)top);
        rightBottom = new Point((int)(right), (int)bottom);
        leftBottom = new Point((int)left, (int)bottom);
        rightTop = new Point((int)right, (int)top);
        path.moveTo((left + right) / 2, top);
        path.lineTo((left / 4 + 3 * right / 4), (top + bottom) / 2);
        path.lineTo((left + right) / 2, bottom);
        path.lineTo(leftBottom.x, leftBottom.y);
        path.lineTo(leftTop.x, leftTop.y);
        path.lineTo((left + right) / 2, top);
        path.close();
        paint.setStyle(Paint.Style.STROKE);
        Rect rect = new Rect((int)(left), (int)top, (int)right, (int)bottom);

        canvas.drawBitmap(clipBitmap, null, rect, paint);
        canvas.drawPath(path, paint);

       drawBitmap = Bitmap.createBitmap((int) (left / 4 + 3 * right / 4), (int) bottom, Bitmap.Config.ARGB_4444);
        Canvas canvas1 = new Canvas(drawBitmap);
        canvas1.clipPath(path);
        canvas1.drawBitmap(clipBitmap, null, rect, paint);
        ((ImageView)((TearImageActivity)context).findViewById(R.id.clip_to)).setImageBitmap(drawBitmap);

        drawBitmap = Bitmap.createBitmap((int) (right), (int) bottom, Bitmap.Config.ARGB_4444);
        Canvas canvas2 = new Canvas(drawBitmap);
        Path path2 = new Path();
        path2.moveTo(rightBottom.x, rightBottom.y);
        path2.lineTo(rightTop.x, rightTop.y);
        path2.lineTo((left + right) / 2, top);
        path2.lineTo((left / 4 + 3 * right / 4), (top + bottom) / 2);
        path2.lineTo((left + right) / 2, bottom);
        path2.lineTo(rightBottom.x, rightBottom.y);
        path2.close();
        canvas2.clipPath(path2);
        Rect rect2 = new Rect(0, 0, (int)right, (int)bottom);
        canvas2.drawBitmap(clipBitmap, null, rect2, paint);
        ((ImageView)((TearImageActivity)context).findViewById(R.id.clip_to_right)).setImageBitmap(drawBitmap);
    }

}
