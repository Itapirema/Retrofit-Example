package com.example.mitapirema.retrofitexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mitapirema.retrofitexample.connection.MarsService;
import com.example.mitapirema.retrofitexample.connection.RestUtils;
import com.example.mitapirema.retrofitexample.connection.UdacityService;
import com.example.mitapirema.retrofitexample.model.Course;
import com.example.mitapirema.retrofitexample.model.Instructor;
import com.example.mitapirema.retrofitexample.model.UdacityCatalog;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Marcelo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btUdacity = (Button) findViewById(R.id.bt_udacity);
        Button btMars = (Button) findViewById(R.id.bt_mars);

        btUdacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testUdacityCatalog();
            }
        });

        btMars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testMarsWeather();
            }
        });

    }

    private void testUdacityCatalog(){

        RestUtils.createService(UdacityService.class).listCatalog().enqueue(new Callback<UdacityCatalog>() {

            @Override
            public void onResponse(Call<UdacityCatalog> call, Response<UdacityCatalog> response) {
                if(!response.isSuccessful()){
                    Log.i(TAG, "Error: "+response.code());
                }else{
                    UdacityCatalog udacityCatalog = response.body();

                    for (Course course : udacityCatalog.courses) {
                        Log.i(TAG, String.format("%s: %s", course.title,course.subtitle));
                        for (Instructor instructor : course.instructors) {
                            Log.i(TAG, instructor.name);
                        }
                        Log.i(TAG, "---------------------------------");
                    }

                }
            }

            @Override
            public void onFailure(Call<UdacityCatalog> call, Throwable t) {

            }
        });
    }




    private void testMarsWeather(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MarsService.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MarsService marsService = retrofit.create(MarsService.class);
        Observable observable = marsService.latest();
        observable.subscribeOn(Schedulers.io());
        observable.observeOn(AndroidSchedulers.mainThread());
        observable.unsubscribeOn(Schedulers.io());
        observable.subscribe(new Observer() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(Object o) {
                Log.i(TAG, "onNext");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e);
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }
}