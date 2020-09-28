package com.swvl.swvlchallenge.ui.base;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

import com.swvl.swvlchallenge.ApplicationClass;
import com.swvl.swvlchallenge.dagger.ApplicationComponent;

import java.lang.ref.WeakReference;


public abstract class BaseViewModel<N> extends ViewModel implements IViewModel {

    public IResourceProvider resProvider;
    public final ObservableBoolean mIsLoading = new ObservableBoolean(false);
    private WeakReference<N> mNavigator;
    private UIHelper messageHelper;
    private UIHelper uiHelper;

    public BaseViewModel() {
        ApplicationComponent appComponent = getAppComponent();
        if (appComponent != null)
            resProvider = appComponent.getResourceProvider();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
        mIsLoading.notifyChange();
    }

    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public ApplicationComponent getAppComponent() {
        return ApplicationClass.getAppComponent();
    }

    public void setMessageHelper(UIHelper messageHelper) {
        this.messageHelper = messageHelper;
    }

    public @NonNull
    UIHelper getMessageHelper() {
        return messageHelper;
    }

    public void setUIHelper(UIHelper uiHelper) {
        this.uiHelper = uiHelper;
    }

    public UIHelper getUiHelper() {
        return uiHelper;
    }

    public IResourceProvider getResProvider() {
        return resProvider;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }


    public abstract void onPause();

    public abstract void onResume();

    public abstract void onDestroy();

    public void showLoading() {
        UIHelper uiHelper = getUiHelper();
        if (uiHelper != null)
            uiHelper.showLoading();
    }

    public void hideLoading() {
        UIHelper uiHelper = getUiHelper();
        if (uiHelper != null)
            uiHelper.hideLoading();
    }
}