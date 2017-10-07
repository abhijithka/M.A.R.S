package com.mars.mars.vistara.restaurants;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mars.mars.vistara.MainActivity;
import com.mars.mars.vistara.R;
import com.mars.mars.vistara.restaurants.menu.MenuAdapter;
import com.mars.mars.vistara.restaurants.menu.MenuDialogFragment;

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
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Restaurant restaurant = restaurantsList.get(position);
        Glide.with(context).load(restaurant.getImgUrl())
            .into(holder.restaurantImage);
/*        holder.menuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.menuRecyclerView.getVisibility() == View.VISIBLE) {
                    holder.menuRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    holder.menuRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuDialogFragment menuDialog = new MenuDialogFragment();
                menuDialog.setMenu(restaurant.getMenu());
                menuDialog.show(((MainActivity)context).getSupportFragmentManager(), "menu");
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView restaurantName, menuTextView;
        public ImageView restaurantImage;
        public RecyclerView menuRecyclerView;

        public MyViewHolder(View itemView) {
            super(itemView);
            restaurantImage = itemView.findViewById(R.id.restaurantImage);
            menuTextView = itemView.findViewById(R.id.menuTextView);
            menuRecyclerView = itemView.findViewById(R.id.menuRecyclerView);
        }
    }

    public void setRestaurantsList(List<Restaurant> restaurantsList) {
        this.restaurantsList = restaurantsList;
        notifyDataSetChanged();
    }
}
