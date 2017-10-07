package com.mars.mars.vistara.search;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mars.mars.vistara.R;
import com.mars.mars.vistara.Utilities;
import com.mars.mars.vistara.advertisements.AdFragment;
import com.mars.mars.vistara.advertisements.SearchResultAdapter;
import com.mars.mars.vistara.restaurants.RestaurantListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText depAirportCode;
    private EditText arrAirportCode;
    private View rootView;
    private RestaurantListFragment restaurantFragment;
    private LinearLayout bannerLayout;
    private TextView bannerTextView;
    private RecyclerView tripsRecyclerView;
    private SearchResultAdapter searchResultAdapter;
    private Button searchBtn;
    private RecyclerView restaurantsRecyclerView;
    private ImageButton downArrowImgButton;
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
        bannerLayout = rootView.findViewById(R.id.bannerLayout);
        bannerTextView = rootView.findViewById(R.id.bannerTextView);
        downArrowImgButton = rootView.findViewById(R.id.downArrowImgButton);
        searchBtn = rootView.findViewById(R.id.searchBtn);
        restaurantFragment = (RestaurantListFragment)getChildFragmentManager().findFragmentById(R.id.restaurantFragment);
        restaurantsRecyclerView = restaurantFragment.getView().findViewById(R.id.restaurants);
        bannerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.hideSoftKeyboard(view, getActivity());
                if (restaurantsRecyclerView.getVisibility() == View.VISIBLE) {
                    restaurantsRecyclerView.setVisibility(View.GONE);
                    return;
                }
                if (bannerTextView.getVisibility() == View.VISIBLE) {
                    restaurantsRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        depAirportCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 3) {
                    List<View> viewsToMakeVisible = new ArrayList<View>();
                    viewsToMakeVisible.add(bannerLayout);
                    JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, getContext().getString(R
                        .string.restaurantDataUrl), null, Utilities.getRestaurantListener(charSequence.toString(),
                        restaurantFragment, viewsToMakeVisible), Utilities.getRestaurantErrorListener());
                    final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(obreq);
                } else {
                    bannerLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        setUpRecyclerView();
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateAirportCodes()) {
                    Utilities.showSnackbar(getView(), "Please enter a valid departure and arrival airport code");
                } else {
                    List<TripCardItem> trips = getTripItems(depAirportCode.getText().toString(),
                        arrAirportCode.getText().toString());
                    searchResultAdapter.setTripList(trips);
                    tripsRecyclerView.setVisibility(View.VISIBLE);
                }
                Utilities.hideSoftKeyboard(getView(), getContext());
            }
        });
        return rootView;
    }

    private boolean validateAirportCodes() {
        if (TextUtils.isEmpty(depAirportCode.getText().toString())) {
            return false;
        } else {
            String depCode = depAirportCode.getText().toString();
            if (depCode.length() != 3) {
                return false;
            }
        }
        if (TextUtils.isEmpty(arrAirportCode.getText().toString())) {
            return false;
        } else {
            String arrCode = arrAirportCode.getText().toString();
            if (arrCode.length() != 3) {
                return false;
            }
        }
        return true;
    }

    private void setUpRecyclerView() {
        tripsRecyclerView = rootView.findViewById(R.id.searchResults);
        List<TripCardItem> trips = getTripItems("", "");
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
