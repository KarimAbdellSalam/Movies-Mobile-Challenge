package com.swvl.swvlchallenge.data.remote;

import com.swvl.swvlchallenge.data.model.FlickrResponse;

import io.reactivex.rxjava3.core.Single;

/**
 * Created by Karim Abdell Salam on 28,September,2020
 */
public interface ApiHelper {
    Single<FlickrResponse> searchImagesByMovieName(String movieName, int page, int perPage);
}
