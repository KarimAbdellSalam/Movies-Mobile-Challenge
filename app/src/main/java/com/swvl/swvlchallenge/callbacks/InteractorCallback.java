package com.swvl.swvlchallenge.callbacks;


/**
 * Created by Karim Abdell Salam on 29,9,2020
 */
public interface InteractorCallback<T> {
    public void onSuccess(T t);

    public void onFailed(Throwable errors);

}
