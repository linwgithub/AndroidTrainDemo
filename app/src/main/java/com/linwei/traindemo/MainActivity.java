package com.linwei.traindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.linwei.traindemo.utils.StatusBarHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarHelper.setStatusbar(this, false, false);
        setContentView(R.layout.activity_main);
    }
}
