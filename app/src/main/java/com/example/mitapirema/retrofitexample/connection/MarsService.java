package com.example.mitapirema.retrofitexample.connection;

import com.example.mitapirema.retrofitexample.model.MarsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;


/**
 * Created by mitapirema on 19/03/18.
 */

public interface MarsService {

    String BASE_URL = "http://marsweather.ingenology.com/v1/";

    @GET("latest")
    Observable<MarsResponse> latest();

}
