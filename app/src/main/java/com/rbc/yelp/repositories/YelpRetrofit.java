package com.rbc.yelp.repositories;

import androidx.annotation.NonNull;

import com.rbc.yelp.BuildConfig;
import com.rbc.yelp.models.SearchResult;
import com.rbc.yelp.services.YelpApi;

import java.io.IOException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YelpRetrofit {

    static final String BASE_URL = "https://api.yelp.com";

    private YelpApi yelpApi;
    private MutableLiveData<SearchResult> searchResultResponseLiveData;

    public YelpRetrofit() {

        searchResultResponseLiveData = new MutableLiveData<>();

        yelpApi = new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new ApiKeyInterceptor())
                        .build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(YelpApi.class);
    }

    private static class ApiKeyInterceptor implements Interceptor {

        @Override
        public @NonNull Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + BuildConfig.API_KEY)
                    .build());
        }
    }

    public void searchYelp(String keyword, String location) {
        yelpApi.search(keyword, location)
                .enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, retrofit2.Response<SearchResult> response) {
                        if (response.body() != null) {
                            searchResultResponseLiveData.postValue(response.body());
                        } else {
                            searchResultResponseLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {
                        searchResultResponseLiveData.postValue(null);
                    }
                });
    }

    public LiveData<SearchResult> getSearchResultLiveData() {
        return searchResultResponseLiveData;
    }

}
