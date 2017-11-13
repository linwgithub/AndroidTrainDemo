package com.linwei.traindemo.cusview;

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

public class TrainPatin extends View {

    private int mWitdh, mHeight;

    public TrainPatin(Context context) {
        super(context);
    }

    public TrainPatin(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TrainPatin(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        paint.setStrokeWidth(1);
        paint.setColor(Color.RED);
        canvas.drawLine(0, -mHeight / 2, 0, mHeight / 2, paint);
        canvas.drawLine(-mWitdh / 2, 0, mWitdh/2, 0, paint);

        paint.setColor(Color.GREEN);
        canvas.drawLine(-30, 0, -30, mHeight / 2, paint);



        paint.setStrokeWidth(60);
        paint.setColor(Color.BLACK);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPoint(0, 0, paint);


        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(0, 10, 100, 10, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(0, 210, 100, 210, paint);

//        paint.setStrokeCap(Paint.Cap.ROUND);

//        paint.setStyle(Paint.Style.FILL);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint.setColorFilter()

//        paint.setStrokeJoin(Paint.Join.BEVEL);

//        canvas.drawPoint(0, 0, paint);
//        paint.setPathEffect(new DashPathEffect(new float[]{10, 20}, 20));

//        canvas.drawLine(0, 0, 200, 200, paint);
//        canvas.drawLine(200,200, 0, 600, paint);
    }
}
