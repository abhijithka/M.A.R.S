package com.mars.mars.vistara.restaurants;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mars.mars.vistara.R;

import java.util.List;

/**
 * Created by aappukuttan on 10/4/2017.
 */
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private List<Restaurant> restaurantsList;
    private Context context;

    public RestaurantAdapter(List<Restaurant> restaurantsList, Context context) {
        this.restaurantsList = restaurantsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.restaurantcard, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurant restaurant = restaurantsList.get(position);
        holder.restaurantName.setText(restaurant.getName());
        Glide.with(context).load(restaurant.getImgUrl())
            .into(holder.restaurantImage);
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView restaurantName;
        public ImageView restaurantImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
        }
    }

    public void setRestaurantsList(List<Restaurant> restaurantsList) {
        this.restaurantsList = restaurantsList;
        notifyDataSetChanged();
    }
}
