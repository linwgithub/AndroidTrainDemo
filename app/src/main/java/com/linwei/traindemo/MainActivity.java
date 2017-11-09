package com.linwei.traindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linwei.traindemo.utils.StatusBarHelper;
import com.linwei.traindemo.utils.Utils;
import com.linwei.traindemo.widget.ProcessView;

public class MainActivity extends AppCompatActivity {

    TextView tvMain;
    ProcessView processView;

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

        processView = (ProcessView) findViewById(R.id.view_process);
        findViewById(R.id.btn_main).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processView.setBitmap(R.drawable.green_bar);
                processView.setProcess(1.0f, true);
            }
        });
    }
}
