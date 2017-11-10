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

public class AnimateProcessView extends View {

    private int mWidth, mHeight;
    private int mDoneViewWitdh = 0;
    private int mBgColor = Color.parseColor("#e3e3e3");
    private Paint mPaint = new Paint();
    private Bitmap mDoneBitmap = null;
    private float mProcess = 0f;
    private boolean mIsAnimte = true;
    private long mAnimteDuration = 1000;

    public AnimateProcessView(Context context) {
        super(context);
    }

    public AnimateProcessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public AnimateProcessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnimateProcessView);
        if (typedArray.hasValue(R.styleable.AnimateProcessView_bgColor)) {
            mBgColor = typedArray.getColor(R.styleable.AnimateProcessView_bgColor, Color.parseColor("#e3e3e3"));
        }
        if (typedArray.hasValue(R.styleable.AnimateProcessView_processSrc)) {
            int bitmapSrc = typedArray.getResourceId(R.styleable.AnimateProcessView_processSrc, 0);
            if (bitmapSrc != 0) {
                mDoneBitmap = BitmapFactory.decodeResource(getResources(), bitmapSrc);
            }
        }
        if (typedArray.hasValue(R.styleable.AnimateProcessView_process)) {
            mProcess = typedArray.getFloat(R.styleable.AnimateProcessView_process, 0f);
        }

        if (typedArray.hasValue(R.styleable.AnimateProcessView_animatrDuration)) {
            mAnimteDuration = typedArray.getInt(R.styleable.AnimateProcessView_animatrDuration, 1000);
        }
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

        if (mProcess > 0f) {
            setProcess(mProcess, mIsAnimte, mAnimteDuration);
            mProcess = 0;
            return;
        }
        int radius = mHeight / 2;

        mPaint.setColor(mBgColor);

        Path pathBg = new Path();
        RectF rectFBg = new RectF(0, 0, mWidth, mHeight);
        pathBg.addRoundRect(rectFBg, radius, radius, Path.Direction.CCW);
        canvas.clipPath(pathBg);

        if (mDoneBitmap == null) {
            mDoneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bule_bar);
        }
        Rect srcRect = new Rect(0, 0, mDoneBitmap.getWidth(), mDoneBitmap.getHeight());
        Rect dirRect = new Rect(0, 0, mWidth, mHeight);

        canvas.drawBitmap(mDoneBitmap, srcRect, dirRect, mPaint);


        Path pathBitmap = new Path();

        if (mDoneViewWitdh < radius) {
            pathBitmap.addRect(new RectF(0, 0, mDoneViewWitdh, mHeight), Path.Direction.CW);
            pathBg.op(pathBitmap, Path.Op.DIFFERENCE);
        } else if ((mDoneViewWitdh >= radius) && (mDoneViewWitdh <= mHeight)) {

            pathBitmap.addArc(new RectF(0, 0, mHeight, mHeight), 90, 180);
            pathBitmap.close();

            Path fillCircleLeft = new Path();
            fillCircleLeft.moveTo(radius, 0);
            fillCircleLeft.quadTo(mDoneViewWitdh, radius, radius, mHeight);
            fillCircleLeft.close();
            pathBitmap.op(fillCircleLeft, Path.Op.UNION);
            pathBg.op(pathBitmap, Path.Op.DIFFERENCE);

        } else {

            pathBitmap.addRoundRect(new RectF(0, 0, mDoneViewWitdh, mHeight), radius, radius, Path.Direction.CW);
            pathBg.op(pathBitmap, Path.Op.DIFFERENCE);
        }
        canvas.drawPath(pathBg, mPaint);
    }

    public void setmDoneBitmap(int bitmapSrc) {
        mDoneBitmap = BitmapFactory.decodeResource(getResources(), bitmapSrc);
    }

    public void setProcess(float process, boolean isAnimate, long duration) {
        this.mIsAnimte = isAnimate;
        this.mAnimteDuration = duration;


        if (process == 0f) {
            mDoneViewWitdh = 0;
            invalidate();
            return;
        }
        if (process >= 1f) {
            process = 1f;
        }

        if (mAnimteDuration <= 0) {
            mDoneViewWitdh = (int) (process * mWidth);
            invalidate();
            return;
        }

        if (!mIsAnimte) {
            mDoneViewWitdh = (int) (mAnimteDuration);
            invalidate();
            return;
        }


        int tempDoneWitdh = (int) (process * mWidth);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, tempDoneWitdh);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mDoneViewWitdh = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration((long) (1000 * process));
        valueAnimator.start();
    }

}