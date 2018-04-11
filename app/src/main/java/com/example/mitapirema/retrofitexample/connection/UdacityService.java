package com.example.mitapirema.retrofitexample.connection;

import com.example.mitapirema.retrofitexample.model.UdacityCatalog;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by mitapirema on 19/03/18.
 */

public interface UdacityService {

    @GET("courses")
    Call<UdacityCatalog> listCatalog();

}
