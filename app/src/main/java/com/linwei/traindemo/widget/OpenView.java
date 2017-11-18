package com.linwei.traindemo.widget;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.linwei.traindemo.R;

/**
 * 展开动画
 * Created by linwei on 2017/11/18.
 */

public class OpenView extends View {

    private int mBgColor;

    private Paint mPaint = new Paint();
    private int mWidth, mHeight;

    private int mTempRadius = 0;


    private AnimatorListenerAdapter mAnimatorListenerAdapter = null;

    public OpenView(Context context) {
        super(context);
        init(context, null);
    }

    public OpenView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public OpenView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    public void setBgColor(int color){
        this.mBgColor = color;
    }

    public void setAnimalListener(AnimatorListenerAdapter animatorListenerAdapter){
        mAnimatorListenerAdapter = animatorListenerAdapter;
    }

    public void removeAnimalListener(){
        mAnimatorListenerAdapter = null;
    }

    public void start(int duration){

        int maxRadius = (int) Math.sqrt(Math.pow(mWidth, 2) + Math.pow(mHeight, 2)) / 2 + 10;

        ValueAnimator animator = ValueAnimator.ofInt(0, maxRadius);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTempRadius = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        if (mAnimatorListenerAdapter != null){
            animator.addListener(mAnimatorListenerAdapter);
        }

        animator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private void init(Context context, AttributeSet attrs) {
        mBgColor = Color.parseColor("#00ff00");
        mPaint = new Paint();
        if (attrs == null){
            return;
        }
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OpenView);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(mWidth / 2, mHeight / 2);


        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(0, 0, mTempRadius, mPaint);
    }
}
