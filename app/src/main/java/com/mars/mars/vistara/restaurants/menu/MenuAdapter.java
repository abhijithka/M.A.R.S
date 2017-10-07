package com.mars.mars.vistara.restaurants.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mars.mars.vistara.R;

import java.util.List;

/**
 * Created by aappukuttan on 10/4/2017.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private List<Menu> menu;
    private Context context;

    public MenuAdapter(List<Menu> menu, Context context) {
        this.menu = menu;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.menucard, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Menu menuItem = menu.get(position);
        holder.itemName.setText(menuItem.getName());
        holder.itemPrice.setText(Integer.toString(menuItem.getPrice()));
        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItem.increment();
                holder.countEditText.setText(Integer.toString(menuItem.getCount()));
            }
        });
        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItem.decrement();
                holder.countEditText.setText(Integer.toString(menuItem.getCount()));
            }
        });
        Glide.with(context).load(menuItem.getImageUrl())
            .into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return menu.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName, itemPrice;
        public EditText countEditText;
        public ImageView itemImage, plusBtn, minusBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            countEditText = itemView.findViewById(R.id.countEditText);
        }
    }
}
