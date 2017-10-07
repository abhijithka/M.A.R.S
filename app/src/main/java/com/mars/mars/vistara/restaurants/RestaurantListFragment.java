package com.mars.mars.vistara.restaurants;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mars.mars.vistara.ItemNameCount;
import com.mars.mars.vistara.MainActivity;
import com.mars.mars.vistara.R;
import com.mars.mars.vistara.restaurants.menu.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListFragment extends Fragment {

    private View rootView;
    private RecyclerView adRecyclerView;
    private RestaurantAdapter restaurantAdapter;

    private Context context;

    public RestaurantListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        populateAds();
        context = getContext();
        restaurantAdapter.notifyDataSetChanged();
        return rootView;
    }

    private void populateAds() {
        adRecyclerView = rootView.findViewById(R.id.restaurants);
        List<Restaurant> restaurants = getRestaurants();
        restaurantAdapter = new RestaurantAdapter(restaurants, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager
            .VERTICAL, false);
        adRecyclerView.setLayoutManager(mLayoutManager);
        adRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adRecyclerView.setAdapter(restaurantAdapter);
    }

    @NonNull
    private List<Restaurant> getRestaurants() {
        // Dummy ads - Start
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant("KFC",
            "https://res-1.cloudinary.com/wlabs/image/fetch/c_pad,f_auto,q_auto/http://res.cloudinary.com/wlabs/image/upload/lbreotwgorjtfcqgrrop.png");
        restaurants.add(restaurant1);
        Restaurant restaurant2 = new Restaurant("Dominos",
            "https://cache.dominos.com/olo/5_14_2/assets/build/images/promo/dominos_social_logo.jpg");
        restaurants.add(restaurant2);
        Restaurant restaurant3 = new Restaurant("McDonald's",
            "http://www.mcdonalds.co.uk/content/dam/McDonaldsUK/Promotions/Facebook-Share-Image.jpg");
        restaurants.add(restaurant3);
        Restaurant restaurant4 = new Restaurant("Achayyans",
            "https://res-1.cloudinary.com/wlabs/image/fetch/c_pad,f_auto,q_auto/http://res.cloudinary.com/wlabs/image/upload/lbreotwgorjtfcqgrrop.png");
        restaurants.add(restaurant4);
        Restaurant restaurant5 = new Restaurant("Punjabi Dhaba",
            "https://res-1.cloudinary.com/wlabs/image/fetch/c_pad,f_auto,q_auto/http://res.cloudinary.com/wlabs/image/upload/lbreotwgorjtfcqgrrop.png");
        restaurants.add(restaurant5);
        // End
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        List<Restaurant> preferred = filterAndSortPreferred(restaurants);
        restaurantAdapter.setRestaurantsList(preferred);
    }

    private List<Restaurant> filterAndSortPreferred(List<Restaurant> restaurants) {
        List<Restaurant> sortedRestaurants = new ArrayList<>();
        List<ItemNameCount> preferredRestaurants = ((MainActivity) getActivity()).allRestaurantsPreference;
        for(ItemNameCount restaurant : preferredRestaurants) {
            for(Restaurant r : restaurants) {
                if (restaurant.name.equalsIgnoreCase(r.getName())) {
                    sortedRestaurants.add(r);
                }
            }
        }
        return sortedRestaurants;
    }

}
