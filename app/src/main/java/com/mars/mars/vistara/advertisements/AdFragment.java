package com.mars.mars.vistara.advertisements;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mars.mars.vistara.R;

import java.util.ArrayList;
import java.util.List;

public class AdFragment extends Fragment {

    private View rootView;
    private RecyclerView adRecyclerView;
    private AdAdapter adAdapter;

    public AdFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_ad, container, false);
        populateAds();

        adAdapter.notifyDataSetChanged();

        return  rootView;
    }

    private void populateAds() {
        adRecyclerView = rootView.findViewById(R.id.ads);
        List<AdItem> ads = getAdItems();
        adAdapter = new AdAdapter(ads);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        adRecyclerView.setLayoutManager(mLayoutManager);
        adRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adRecyclerView.setAdapter(adAdapter);
    }

    @NonNull
    private List<AdItem> getAdItems() {
        // Dummy ads - Start
        List<AdItem> ads = new ArrayList<>();
        AdItem adItem1 = new AdItem("Dominos", "Cheese Pizza", 100);
        ads.add(adItem1);
        AdItem adItem2 = new AdItem("Punjabi Dhaba", "Roti", 200);
        ads.add(adItem2);
        AdItem adItem3 = new AdItem("Kitchen", "Biriyani", 300);
        ads.add(adItem3);
        // End
        return ads;
    }
}
