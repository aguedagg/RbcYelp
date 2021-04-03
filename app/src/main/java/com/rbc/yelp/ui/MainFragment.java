package com.rbc.yelp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rbc.yelp.R;
import com.rbc.yelp.adapters.SectionRecyclerAdapter;
import com.rbc.yelp.models.Business;
import com.rbc.yelp.models.Category;
import com.rbc.yelp.models.SearchResult;
import com.rbc.yelp.models.Section;
import com.rbc.yelp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragment extends Fragment  {
    private MainViewModel mViewModel;
    private SectionRecyclerAdapter adapter;

    private TextInputEditText keywordEditText, locationEditText;
    private TextInputLayout locationBox;
    private Button searchButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayout errorLayout;
    private LinearLayout emptyLayout;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new SectionRecyclerAdapter();

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.init();
        mViewModel.getSearchResultsLiveData().observe(this, new Observer<SearchResult>() {
            @Override
            public void onChanged(SearchResult response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    errorLayout.setVisibility(View.GONE);
                    if (response.getBusinesses().size() > 0) {
                        sortData(response.getBusinesses());
                        emptyLayout.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        emptyLayout.setVisibility(View.VISIBLE);
                    }
                } else {
                    emptyLayout.setVisibility(View.GONE);
                    errorLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void sortData(List<Business> businessList) {
        HashMap<String, List<Business>> map = new HashMap<>();

        List<Category> categories;
        String alias;

        for (Business business: businessList) {
            categories = business.getCategories();
            for (Category c: categories) {
                alias = c.getTitle();
                if (map.containsKey(alias)) {
                    // Category exists
                    map.get(alias).add(business);
                } else {
                    // New category
                    map.put(alias, new ArrayList<Business>( Arrays.asList(business)));
                }
            }
        }

        List<Section> sections = new ArrayList<>();
        for (String s: map.keySet()) {
            sections.add(new Section(s, map.get(s)));
        }

        adapter.setData(sections);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = view.findViewById(R.id.search_result_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        keywordEditText = view.findViewById(R.id.input_search_term);
        locationEditText = view.findViewById(R.id.input_search_location);
        searchButton = view.findViewById(R.id.button_search);
        locationBox = view.findViewById(R.id.outlined_layout_location);
        progressBar = view.findViewById(R.id.progress_bar);
        errorLayout = view.findViewById(R.id.custom_error_view);
        emptyLayout = view.findViewById(R.id.custom_empty_view);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSearchResults();
            }
        });

        return view;
    }

    public void getSearchResults() {

        String keyword = keywordEditText.getEditableText().toString();
        String location = locationEditText.getEditableText().toString();

        // Location is required, keyword optional
        if (location.isEmpty()) {
            locationBox.setError(getResources().getString(R.string.location_error));
        } else {
            locationBox.setHelperText(getResources().getText(R.string.location_helper));
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            mViewModel.searchYelp(keyword, location);
        }
    }
}
