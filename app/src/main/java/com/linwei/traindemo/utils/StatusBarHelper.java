package com.linwei.traindemo.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import com.linwei.traindemo.R;

/**
 * Created by linwei on 2017/11/7.
 */

public class StatusBarHelper {

    public static void setStatusbar(Activity activity, boolean useThemestatusBarColor, boolean useLightStatusBarUI) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //根据上面设置是否对状态栏单独设置颜色
            if (useThemestatusBarColor) {
                activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));
            } else {
                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !useLightStatusBarUI) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //需要在Activity布局的根节点处加上android:fitsSystemWindows=”true”属性，
        // 要不布局会跑到状态栏和导航栏下面，与导航栏和状态栏重叠
    }

    public static void setStatusBarUIStyle(Activity activity, boolean useLightStatusBarUI) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//android6.0以后可以对状态栏文字颜色和图标进行修改
            if (useLightStatusBarUI) {
                //设置状态栏文字颜色及图标为浅色
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

            }
        } else {
        }
    }
}
