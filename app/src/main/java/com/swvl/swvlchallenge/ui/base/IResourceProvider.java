package com.swvl.swvlchallenge.ui.base;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public interface IResourceProvider {

    String getString(int resId);

    String getString(int resId, String value);

    int getColor(int colorAccent);
}
