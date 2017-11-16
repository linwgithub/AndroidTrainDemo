package com.linwei.traindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.linwei.traindemo.utils.StatusBarHelper;
import com.linwei.traindemo.utils.Utils;
import com.linwei.traindemo.widget.AnimateProcessView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.setStatusbar(this, false, false);
        setContentView(R.layout.activity_main);

    }
}
