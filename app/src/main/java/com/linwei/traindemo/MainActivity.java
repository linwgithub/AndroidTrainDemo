package com.linwei.traindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linwei.traindemo.utils.StatusBarHelper;
import com.linwei.traindemo.utils.Utils;
import com.linwei.traindemo.widget.AnimateProcessView;

public class MainActivity extends AppCompatActivity {

    TextView tvMain;
    AnimateProcessView animateProcessView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.setStatusbar(this, false, false);
        setContentView(R.layout.activity_main);
        tvMain = (TextView) findViewById(R.id.tv_main);

        if (Utils.isZh(this)) {
            tvMain.setText("中文");
        } else {
            tvMain.setText("Not Chinese");
        }

        animateProcessView = (AnimateProcessView) findViewById(R.id.view_process);
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateProcessView.setmDoneBitmap(R.drawable.green_bar);
                animateProcessView.setProcess(1.0f, true, 800);
            }
        });
    }
}
