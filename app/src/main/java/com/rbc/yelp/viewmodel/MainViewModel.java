package com.rbc.yelp.viewmodel;

import android.app.Application;

import com.rbc.yelp.models.SearchResult;
import com.rbc.yelp.repositories.YelpRetrofit;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainViewModel extends AndroidViewModel {
    private YelpRetrofit yelpRepository;
    private LiveData<SearchResult> searchResultsLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void init() {
        yelpRepository = new YelpRetrofit();
        searchResultsLiveData = yelpRepository.getSearchResultLiveData();
    }

    public void searchYelp(String keyword, String location) {
        yelpRepository.searchYelp(keyword, location);
    }

    public LiveData<SearchResult> getSearchResultsLiveData() {
        return searchResultsLiveData;
    }
}