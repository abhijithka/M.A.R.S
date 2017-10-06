package com.mars.mars.vistara.advertisements;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mars.mars.vistara.R;
import com.mars.mars.vistara.search.TripCardItem;

import java.util.List;

/**
 * Created by aappukuttan on 10/4/2017.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {

    private List<TripCardItem> tripList;

    public SearchResultAdapter(List<TripCardItem> tripList) {
        this.tripList = tripList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.tripcard, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TripCardItem trip = tripList.get(position);
        holder.flightNumber.setText(trip.getFlightNumber());
        holder.depDate.setText(trip.getDate());
        holder.depAirportCode.setText(trip.getDepAirportCode());
        holder.arrAirportCode.setText(trip.getArrAirportCode());
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView flightNumber, depDate, depAirportCode, arrAirportCode;

        public MyViewHolder(View itemView) {
            super(itemView);
            flightNumber = itemView.findViewById(R.id.flightNumber);
            depDate = itemView.findViewById(R.id.depDate);
            depAirportCode = itemView.findViewById(R.id.depAirportCode);
            arrAirportCode = itemView.findViewById(R.id.arrAirportCode);
        }
    }

    public void setTripList(List<TripCardItem> tripList) {
        this.tripList = tripList;
        notifyDataSetChanged();
    }
}
