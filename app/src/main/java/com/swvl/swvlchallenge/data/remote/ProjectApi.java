package com.swvl.swvlchallenge.data.remote;

import com.swvl.swvlchallenge.data.model.FlickrResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Karim Abdell Salam on 29,September,2020
 */
public interface ProjectApi {
    public interface FlickerApi {
        // Search flicker Images
        @GET("services/rest/")
        Single<FlickrResponse> searchMovieImages(@Query("method") String method,
                                                 @Query("api_key") String apiKey,
                                                 @Query("format") String format,
                                                 @Query("nojsoncallback") int noJsonCallback,
                                                 @Query("text") String text,
                                                 @Query("page") int page,
                                                 @Query("per_page") int per_page);

    }
}
