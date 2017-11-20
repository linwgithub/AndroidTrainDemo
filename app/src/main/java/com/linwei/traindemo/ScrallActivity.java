package com.linwei.traindemo;

import android.annotation.SuppressLint;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class ScrallActivity extends AppCompatActivity {

    private boolean scrollTOBottom = false;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrall);

        final NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.view_scroll);

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()){

                    case MotionEvent.ACTION_MOVE:

                        //滚动高度
                        int scrollY = view.getScrollY();
                        int height = view.getHeight();

                        int firstChildViewHeight = nestedScrollView.getChildAt(0).getMeasuredHeight();

                        //避免多次判断，只在刚滚动到底部的时候执行
                        boolean tempScrollToBottom = Math.abs(scrollY + height - firstChildViewHeight) < 10;
                        if (tempScrollToBottom != scrollTOBottom){
                            scrollTOBottom = tempScrollToBottom;
                            if (scrollTOBottom){
                                Log.e("TAG", "scrolled to bottom");
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }
}
