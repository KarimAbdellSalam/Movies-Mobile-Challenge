package com.swvl.swvlchallenge.ui.movie;

import com.swvl.swvlchallenge.callbacks.InteractorCallback;
import com.swvl.swvlchallenge.data.model.FlickrResponse;

import java.util.List;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public interface IMovieInteractor {
    void searchPhotos(int currentPage, int perPage, String query, InteractorCallback<FlickrResponse> callback);

    void filterImages(FlickrResponse flickrResponse, String title, InteractorCallback<List<FlickrResponse.Photo>> listInteractorCallback);

}
