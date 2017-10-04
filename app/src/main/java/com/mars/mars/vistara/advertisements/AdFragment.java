package com.mars.mars.vistara.advertisements;

import android.os.Bundle;
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
        adRecyclerView = rootView.findViewById(R.id.ads);

        // Dummy ads - Start
        List<AdItem> ads = new ArrayList<>();
        AdItem adItem1 = new AdItem("Dominos", "Cheese Pizza");
        ads.add(adItem1);
        AdItem adItem2 = new AdItem("Kitchen", "Biriyani");
        ads.add(adItem2);

        // End

        adAdapter = new AdAdapter(ads);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        adRecyclerView.setLayoutManager(mLayoutManager);
        adRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adRecyclerView.setAdapter(adAdapter);

        adAdapter.notifyDataSetChanged();

        return  rootView;
    }
}
