package com.linwei.traindemo.utils;

import android.content.Context;

import java.util.Locale;

/**
 * Created by linwei on 2017/11/8.
 */

public class Utils {


    public static boolean isZh(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language.endsWith("zh");
    }
}
