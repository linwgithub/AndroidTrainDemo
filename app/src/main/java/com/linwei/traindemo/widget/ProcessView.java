package com.linwei.traindemo.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.linwei.traindemo.R;

/**
 * Created by linwei on 2017/11/9.
 */

public class ProcessView extends View {

    int mWidth, mHeight;
    Paint mPaint;
    int doneWitdh = 0;
    Bitmap bitmap = null;
    int bgColor = Color.parseColor("#e3e3e3");
    float process = 0f;
    private boolean mIsAnimte = false;

    public ProcessView(Context context) {
        super(context);
        init();
    }

    public ProcessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProcessView);
        if (typedArray.hasValue(R.styleable.ProcessView_bgColor)){
            bgColor = typedArray.getColor(R.styleable.ProcessView_bgColor, Color.parseColor("#e3e3e3"));
        }
        if (typedArray.hasValue(R.styleable.ProcessView_processSrc)){
            int bitmapSrc = typedArray.getResourceId(R.styleable.ProcessView_processSrc, 0);
            if (bitmapSrc != 0){
                bitmap = BitmapFactory.decodeResource(getResources(), bitmapSrc);
            }
        }
        if (typedArray.hasValue(R.styleable.ProcessView_process)){
            process = typedArray.getFloat(R.styleable.ProcessView_process, 0f);
        }
        if (typedArray.hasValue(R.styleable.ProcessView_isAnime)){
            mIsAnimte = typedArray.getBoolean(R.styleable.ProcessView_isAnime, false);
        }
    }

    public ProcessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttr(context, attrs);
    }

    private void init(){
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (process > 0f){
            setProcess(process, mIsAnimte);
            process = 0;
            return;
        }
        int radius = mHeight / 2;

        mPaint.setColor(bgColor);

        Path pathBg = new Path();
        RectF rectFBg = new RectF(0, 0, mWidth, mHeight);
        pathBg.addRoundRect(rectFBg, radius, radius, Path.Direction.CCW);
        canvas.clipPath(pathBg);

        if (bitmap == null){
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bule_bar);
        }
        Rect srcRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect dirRect = new Rect(0, 0, mWidth, mHeight);

        canvas.drawBitmap(bitmap, srcRect, dirRect, mPaint);


        Path pathBitmap = new Path();

        if (doneWitdh < radius){
            pathBitmap.addRect(new RectF(0, 0, doneWitdh, mHeight), Path.Direction.CW);
            pathBg.op(pathBitmap, Path.Op.DIFFERENCE);
        }else if ((doneWitdh >= radius) && (doneWitdh <= mHeight)){

            Path fillCircleLeft = new Path();
            fillCircleLeft.moveTo(radius, 0);
            fillCircleLeft.quadTo(doneWitdh, radius, radius, mHeight);
            fillCircleLeft.close();
            Path fillCircleRight = new Path();
            fillCircleRight.addArc(new RectF(0, 0, mHeight, mHeight), 90, 180);
            fillCircleRight.getClass();

            fillCircleLeft.op(fillCircleRight, Path.Op.UNION);

            pathBg.op(fillCircleLeft, Path.Op.DIFFERENCE);


        }else {

            pathBitmap.addRoundRect(new RectF(0, 0, doneWitdh, mHeight), radius, radius, Path.Direction.CW );
            pathBg.op(pathBitmap, Path.Op.DIFFERENCE);
        }
        canvas.drawPath(pathBg, mPaint);
    }

    public void setBitmap(int bitmapSrc){
        bitmap = BitmapFactory.decodeResource(getResources(), bitmapSrc);
    }

    public void setProcess(float curProcess, boolean isAnimate){
        this.mIsAnimte = isAnimate;

        if (curProcess == 0f){
            return;
        }
        if (curProcess >= 1f){
            curProcess = 1f;
        }

        if (mIsAnimte){

            int tempDoneWitdh = (int) (curProcess * mWidth);
            ValueAnimator valueAnimator = ValueAnimator.ofInt(0, tempDoneWitdh);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    doneWitdh = (int) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.setDuration((long) (800 * curProcess));
            valueAnimator.start();
        }else {
            doneWitdh = (int) (curProcess * mWidth);
            invalidate();
        }
    }

}
