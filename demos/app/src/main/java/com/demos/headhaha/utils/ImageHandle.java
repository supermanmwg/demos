package com.demos.headhaha.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by weiguangmeng on 15/12/21.
 */
public class ImageHandle {

    public static Bitmap handleImageNegative(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int color;
        int r, g, b, a;

        Bitmap bmp = Bitmap.createBitmap(width, height
                , Bitmap.Config.ARGB_8888);

        int[] oldPx = new int[width * height];
        int[] newPx = new int[width * height];
        bm.getPixels(oldPx, 0, width, 0, 0, width, height);

        for (int i = 0; i < width * height; i++) {
            color = oldPx[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r = 255 - r;
            g = 255 - g;
            b = 255 - b;

            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPx[i] = Color.argb(a, r, g, b);
        }
        bmp.setPixels(newPx, 0, width, 0, 0, width, height);
        return bmp;
    }

    public static Bitmap handleRectImage(Bitmap bm) {
        int sumWidth = bm.getWidth();
        int sumHeight = bm.getHeight();
        int space = 8;
        Bitmap bmp = Bitmap.createBitmap(sumWidth, sumHeight
                , Bitmap.Config.ARGB_8888);

        int[] oldPx = new int[sumWidth * sumHeight];
        int[] newPx = new int[sumWidth * sumHeight];
        bm.getPixels(oldPx, 0, sumWidth, 0, 0, sumWidth, sumHeight);


        int columX = sumWidth / space;
        int columY = sumHeight / space;

        for(int i = 0; i < columX; i ++)
            for(int j = 0; j <columY; j++) {
                for(int x = 0; x < space; x++)
                    for(int y = 0; y < space; y++) {
                        if((i * space + x) * sumWidth + (j * space + y) < sumWidth * sumHeight) {
                            newPx[(i * space + x) * sumWidth + (j * space + y)] = oldPx[i * space * sumWidth + j * space];
                        }
                    }
            }

        bmp.setPixels(newPx, 0, sumWidth, 0, 0, sumWidth, sumHeight);

        return bmp;
    }




}
