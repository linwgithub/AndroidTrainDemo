package com.linwei.traindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.linwei.traindemo.utils.StatusBarHelper;
import com.linwei.traindemo.utils.Utils;

public class MainActivity extends AppCompatActivity {

    TextView tvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.setStatusbar(this, false, false);
        setContentView(R.layout.activity_main);
        tvMain = (TextView) findViewById(R.id.tv_main);

        if (Utils.isZh(this)){
            tvMain.setText("中文");
        }else {
            tvMain.setText("Not Chinese");
        }
    }
}
