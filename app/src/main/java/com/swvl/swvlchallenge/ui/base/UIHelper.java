package com.swvl.swvlchallenge.ui.base;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public interface UIHelper {

    void showToast(String message);

    void showToast(int messageId);

    public void showLoading();

    public void hideLoading();

    void showSnake(String message);

    void showSnake(String message, int action, View.OnClickListener onClickListener);

    AlertDialog.Builder showAlertDialog(String title, CharSequence[] options, DialogInterface.OnClickListener onClickListener);

    boolean checkPermission(String[] permissions);

    void requestPermissionsSafely(String[] permissions, int requestCode);

    void onBackClicked();

    void postBack();

    void postBack(int after);
}
