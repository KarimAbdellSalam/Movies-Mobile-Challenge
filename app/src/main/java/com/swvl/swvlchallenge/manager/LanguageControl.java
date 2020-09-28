package com.swvl.swvlchallenge.manager;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import java.text.NumberFormat;
import java.util.Locale;

public class LanguageControl {
    public static final String ARABIC = "ar";
    public static final String ENGLISH = "en";
    public static final int SIZE = 2;


    public static String convertToHindiNumbers(int no) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("ar", "EG"));
        return nf.format(no);
    }

    public static void forceRTLIfSupported(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            //	public static String[] languagesTypeArray = { "ar", "en", "fr", "tr", "de" };
        }
    }

    public static void forceLTRIfSupported(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            activity.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            //	public static String[] languagesTypeArray = { "ar", "en", "fr", "tr", "de" };
        }
    }

    public static boolean isRTL(Context context) {
        changeLocalViews(LanguageControl.ENGLISH, context);
        return false;
    }

    public static boolean isRTLLocale(Context context) {
        return false;
    }

    public static void setLocale(String locale, Context context) {
        //todo save locale
        changeLocalViews(locale, context);
    }

    public static String getLocale(Context context) {
        //todo get locale from cache
        return "en";
    }

    private static void changeLocalViews(String locale, Context context) {
        Locale myLocale = new Locale(locale);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(myLocale);
        } else {
            conf.locale = myLocale;
        }
        res.updateConfiguration(conf, dm);
    }

    public static void adjustLayoutDirections(Activity context) {
        if (isRTL(context))
            forceRTLIfSupported(context);
        else {
            forceLTRIfSupported(context);
        }
    }

}
