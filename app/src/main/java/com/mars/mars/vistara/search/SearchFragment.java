package com.mars.mars.vistara.search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mars.mars.vistara.R;
import com.mars.mars.vistara.Utilities;
import com.mars.mars.vistara.advertisements.AdAdapter;
import com.mars.mars.vistara.advertisements.AdFragment;
import com.mars.mars.vistara.advertisements.AdItem;
import com.mars.mars.vistara.advertisements.SearchResultAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText depAirportCode;
    private EditText arrAirportCode;
    private View rootView;
    private AdFragment adFragment;
    private TextView bannerTextView;
    private RecyclerView tripsRecyclerView;
    private SearchResultAdapter searchResultAdapter;
    private Button searchBtn;
    View ads;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        depAirportCode = rootView.findViewById(R.id.depAirportCode);
        arrAirportCode = rootView.findViewById(R.id.arrAirportCode);
        bannerTextView = rootView.findViewById(R.id.bannerTextView);
        searchBtn = rootView.findViewById(R.id.searchBtn);
        adFragment = (AdFragment)getChildFragmentManager().findFragmentById(R.id.adFragment);
        ads = adFragment.getView().findViewById(R.id.ads);
        bannerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.hideSoftKeyboard(view, getActivity());
                if (ads.getVisibility() == View.VISIBLE) {
                    ads.setVisibility(View.GONE);
                } else {
                    ads.setVisibility(View.VISIBLE);
                }
            }
        });
        depAirportCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 3) {
                    bannerTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        populateRecyclerView();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tripsRecyclerView.setVisibility(View.VISIBLE);

            }
        });
        return rootView;
    }

    private void populateRecyclerView() {
        tripsRecyclerView = rootView.findViewById(R.id.searchResults);
        List<TripCardItem> trips = getTripItems(depAirportCode.getText().toString(), arrAirportCode.getText().toString());
        searchResultAdapter = new SearchResultAdapter(trips);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        tripsRecyclerView.setLayoutManager(mLayoutManager);
        tripsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tripsRecyclerView.setAdapter(searchResultAdapter);
    }

    @NonNull
    private List<TripCardItem> getTripItems(String dep, String arr) {
        // Dummy ads - Start
        List<TripCardItem> trips = new ArrayList<>();
        TripCardItem tripCardItem1 = new TripCardItem(dep, arr, "2 Oct 2018", "BG 1201");
        TripCardItem tripCardItem2 = new TripCardItem(dep, arr, "22 Jan 2018", "AF 11");
        TripCardItem tripCardItem3 = new TripCardItem(dep, arr, "18 July 2018", "CD 991");
        TripCardItem tripCardItem4 = new TripCardItem(dep, arr, "5 Dec 2017", "SQ 223");
        trips.add(tripCardItem1);
        trips.add(tripCardItem2);
        trips.add(tripCardItem3);
        trips.add(tripCardItem4);
        return trips;
    }
}
