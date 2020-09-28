package com.swvl.swvlchallenge.ui.base;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.swvl.swvlchallenge.R;

import javax.inject.Inject;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public class ResourceProvider implements IResourceProvider {

    private Context mContext;

    @Inject
    public ResourceProvider(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String getString(int resId) {
        return mContext.getString(resId);
    }

    @Override
    public String getString(int resId, String value) {
        return mContext.getString(resId, value);
    }

    @Override
    public int getColor(int colorAccent) {
        return ContextCompat.getColor(mContext, R.color.colorAccent);
    }
}