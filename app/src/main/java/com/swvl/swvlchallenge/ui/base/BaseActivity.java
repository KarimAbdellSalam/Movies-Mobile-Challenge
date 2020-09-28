package com.swvl.swvlchallenge.ui.base;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;

import com.swvl.swvlchallenge.manager.LanguageControl;
import com.swvl.swvlchallenge.utils.Utils;

import dagger.android.AndroidInjection;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;


/**
 * Created by Karim Abdell Salam on 1/7/2020.
 */

public abstract class BaseActivity<V extends BaseViewModel> extends AppCompatActivity
        implements BaseFragment.Callback, UIHelper {

    public String TAG;
    // this can probably depend on isLoading variable of BaseViewModel,
    // since its going to be common for all the activities
    private ProgressDialog mProgressDialog;
    private ViewDataBinding mViewDataBinding;
    private V mViewModel;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    public abstract void init();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();



    @Override
    public void onFragmentAttached() {
    }

    @Override
    public void onFragmentDetached(String tag) {
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TAG = this.getLocalClassName();
        LanguageControl.adjustLayoutDirections(this);
        performDependencyInjection();
        //   toggleFullscreen(true);
        super.onCreate(savedInstanceState);
        performDataBinding();
        mViewModel.setUIHelper(this);
        init();
        String simpleName = this.getClass().getSimpleName();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void showLoading() {
        hideLoading();
        mProgressDialog = Utils.UI.showLoadingDialog(this);
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public boolean isLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return true;
        } else return false;

    }

    public boolean isNetworkConnected() {
        return Utils.Network.checkInternetConnection(getApplicationContext());
    }

    public void openActivityOnTokenExpire() {
        //todo check me after token expire
        finish();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }



    @Override
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(int messageId) {
        Toast.makeText(getApplicationContext(), getString(messageId), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnake(String message) {
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnake(String message, int action, View.OnClickListener onClickListener) {
        Snackbar.make(getWindow().getDecorView().getRootView(), message, Snackbar.LENGTH_INDEFINITE).setAction(action, onClickListener).show();
    }

    @Override
    public AlertDialog.Builder showAlertDialog(String title, CharSequence[]
            options, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        if (options.length > 0 && onClickListener != null)
            builder.setItems(options, onClickListener);
        builder.show();
        return builder;
    }

    @Override
    public boolean checkPermission(String[] permissions) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkPermissions(permissions);
    }

    private boolean checkPermissions(String[] permissions) {
        boolean allGranted = true;
        for (String permission : permissions
        ) {
            if (ContextCompat.checkSelfPermission(this, permission) != (PackageManager.PERMISSION_GRANTED)) {
                allGranted = false;
            }
        }
        return allGranted;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mViewModel.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hideLoading();
    }

    @Override
    public void onBackClicked() {
        super.onBackPressed();
    }

    @Override
    public void postBack() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        }, 2000);
    }

    @Override
    public void postBack(int after) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        }, after);
        onBackPressed();
    }

    public void toggleFullscreen(boolean fs) {
        if (Build.VERSION.SDK_INT >= 11) {
            // The UI options currently enabled are represented by a bitfield.
            // getSystemUiVisibility() gives us that bitfield.
            int uiOptions = this.getWindow().getDecorView().getSystemUiVisibility();
            int newUiOptions = uiOptions;
            boolean isImmersiveModeEnabled =
                    ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
            if (isImmersiveModeEnabled) {
                Log.i(getPackageName(), "Turning immersive mode mode off. ");
            } else {
                Log.i(getPackageName(), "Turning immersive mode mode on.");
            }

            // Navigation bar hiding:  Backwards compatible to ICS.
            if (Build.VERSION.SDK_INT >= 14) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }

            // Status bar hiding: Backwards compatible to Jellybean
            if (Build.VERSION.SDK_INT >= 16) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            }

            // Immersive mode: Backward compatible to KitKat.
            // Note that this flag doesn't do anything by itself, it only augments the behavior
            // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
            // all three flags are being toggled together.
            // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
            // Sticky immersive mode differs in that it makes the navigation and status bars
            // semi-transparent, and the UI flag does not get cleared when the user interacts with
            // the screen.
            if (Build.VERSION.SDK_INT >= 18) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        } else {
            // for android pre 11
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            if (fs) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            } else {
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            }
            this.getWindow().setAttributes(attrs);
        }

        try {
            // hide actionbar
            if
            (this instanceof AppCompatActivity) {
                if (fs) getSupportActionBar().hide();
                else getSupportActionBar().show();
            } else if
            (Build.VERSION.SDK_INT >= 11) {
                if (fs) getActionBar().hide();
                else getActionBar().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}