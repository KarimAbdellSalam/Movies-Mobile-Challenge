package com.swvl.swvlchallenge.ui.base;

import android.content.Intent;

/**
 * Created by Karim Abdell Salam on 1/7/2020.
 */
interface IViewModel {

    public void onActivityResult(int requestCode, int resultCode, Intent data);

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
