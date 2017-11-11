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
    private int mTextColor = Color.parseColor("#e3e3e3");
    private Paint mPaint = new Paint();
    private Bitmap mDoneBitmap = null;
    private float mProcess = 0f;
    private boolean mIsAnimte = true;
    private boolean mShowProcessText = true;
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
        mBgColor = typedArray.getColor(R.styleable.AnimateProcessView_bgColor, Color.parseColor("#e3e3e3"));
        mTextColor = typedArray.getColor(R.styleable.AnimateProcessView_processTextColor, Color.parseColor("#e3e3e3"));

        int bitmapSrc = typedArray.getResourceId(R.styleable.AnimateProcessView_processSrc, 0);
        if (bitmapSrc != 0) {
            mDoneBitmap = BitmapFactory.decodeResource(getResources(), bitmapSrc);
        }

        mProcess = typedArray.getFloat(R.styleable.AnimateProcessView_process, 0f);
        mAnimteDuration = typedArray.getInt(R.styleable.AnimateProcessView_animatrDuration, 1000);
        mIsAnimte = typedArray.getBoolean(R.styleable.AnimateProcessView_isAnimete, true);
        mShowProcessText = typedArray.getBoolean(R.styleable.AnimateProcessView_isShowProcessText, true);
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

        int processBarHeight = mHeight;

        if (mShowProcessText) {

            processBarHeight = mHeight / 2;

            Paint textPaint = new Paint();
            textPaint.setColor(mTextColor);
            textPaint.setTextSize((float) (mHeight * 0.4));
            String text = (int) ((float) mDoneViewWitdh * 100 / mWidth) + "%";
            float measureTextSize = textPaint.measureText(text, 0, text.length());
            if ((mDoneViewWitdh + measureTextSize) <= mWidth) {
                if (mDoneViewWitdh > measureTextSize / 2) {
                    canvas.drawText(text, mDoneViewWitdh - measureTextSize / 2, (float) (mHeight * 0.98), textPaint);
                } else {
                    canvas.drawText(text, mDoneViewWitdh, (float) (mHeight * 0.98), textPaint);
                }
            } else {
                canvas.drawText(text, mWidth - measureTextSize, (float) (mHeight * 0.98), textPaint);
            }
        }

        int radius = processBarHeight / 2;


        mPaint.setColor(mBgColor);

        Path pathBg = new Path();
        RectF rectFBg = new RectF(0, 0, mWidth, processBarHeight);
        pathBg.addRoundRect(rectFBg, radius, radius, Path.Direction.CCW);
        canvas.clipPath(pathBg);

        if (mDoneBitmap == null) {
            mDoneBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bule_bar);
        }

        Rect srcRect = new Rect(0, 0, mDoneBitmap.getWidth(), mDoneBitmap.getHeight());
        Rect dirRect = new Rect(0, 0, mWidth, processBarHeight);

//        Rect srcRect = new Rect((int) (mDoneBitmap.getWidth() * (1- ((float)mDoneViewWitdh / mWidth))), 0, mDoneBitmap.getWidth(), mDoneBitmap.getHeight());
//        Rect dirRect = new Rect(0, 0, mDoneViewWitdh, processBarHeight);

        canvas.drawBitmap(mDoneBitmap, srcRect, dirRect, mPaint);


        Path pathBitmap = new Path();

        if (mDoneViewWitdh <= processBarHeight) {
            pathBitmap.addCircle(mDoneViewWitdh - radius, radius, radius, Path.Direction.CW);

        } else {
            pathBitmap.addRoundRect(new RectF(0, 0, mDoneViewWitdh, processBarHeight), radius, radius, Path.Direction.CW);
        }
        pathBg.op(pathBitmap, Path.Op.DIFFERENCE);
        canvas.drawPath(pathBg, mPaint);
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
        valueAnimator.setDuration(mAnimteDuration);
        valueAnimator.start();
    }

    public void setmDoneBitmap(int bitmapSrc) {
        mDoneBitmap = BitmapFactory.decodeResource(getResources(), bitmapSrc);
    }

    public void setTextColor(int color) {
        mTextColor = color;
    }

    public void setShowProcessText(boolean showProcessText){
        this.mShowProcessText = showProcessText;
    }
}
