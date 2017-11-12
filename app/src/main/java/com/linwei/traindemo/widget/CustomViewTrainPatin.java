package com.linwei.traindemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by linwei on 2017/11/12.
 */

public class CustomViewTrainPatin extends View {

    private int mWitdh, mHeight;

    public CustomViewTrainPatin(Context context) {
        super(context);
    }

    public CustomViewTrainPatin(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewTrainPatin(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWitdh = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWitdh / 2, mHeight / 2);

        Paint paint = new Paint();
        paint.setStrokeWidth(60);

//        paint.setStrokeCap(Paint.Cap.BUTT);
//        paint.setStrokeCap(Paint.Cap.SQUARE);
        paint.setStrokeCap(Paint.Cap.ROUND);

//        paint.setStyle(Paint.Style.FILL);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStyle(Paint.Style.STROKE);
//        paint.setColorFilter()

        paint.setColor(Color.BLUE);
        canvas.drawPoint(0, 0, paint);
    }
}
