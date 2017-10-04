package com.mars.mars.vistara.advertisements;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mars.mars.vistara.R;

import java.util.List;

/**
 * Created by aappukuttan on 10/4/2017.
 */
public class AdAdapter extends RecyclerView.Adapter<AdAdapter.MyViewHolder> {

    private List<AdItem> adsList;

    public AdAdapter(List<AdItem> adsList) {
        this.adsList = adsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.adcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdItem adItem = adsList.get(position);
        holder.restaurantName.setText(adItem.getRestaurantName());
        holder.itemName.setText(adItem.getItemName());
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView restaurantName, itemName;

        public MyViewHolder(View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            itemName = itemView.findViewById(R.id.itemName);
        }
    }

}
