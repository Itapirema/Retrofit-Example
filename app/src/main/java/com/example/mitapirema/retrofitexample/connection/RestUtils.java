package com.example.mitapirema.retrofitexample.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mitapirema on 20/03/18.
 */

public class RestUtils {

    static String BASE_URL = "https://www.udacity.com/public-api/v0/";

    private static Retrofit retrofit;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <S> S createService(Class<S> clazz) {
        return retrofit.create(clazz);
    }
}
