package com.mars.mars.vistara;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mars.mars.vistara.restaurants.Restaurant;
import com.mars.mars.vistara.restaurants.RestaurantListFragment;
import com.mars.mars.vistara.search.TripCardItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PNRetrieveFragment extends Fragment {

    private String TAG = getClass().getSimpleName();
    private View rootView;
    private EditText pnrEditText;
    private Button fetchTripBtn;
    RelativeLayout retrievedPNRLayout;
    ProgressBar progressBar;
    onPnrRetrievedListener pnrRetrievedCallBack;
    RestaurantListFragment restaurantFragment;
    TextView bannerTextView;
    List<TripCardItem> trips;
    TextView depDateTextView;

    public interface onPnrRetrievedListener {

        public void onTripFetched();
    }

    public interface onRestaurantsRetrievedListener {

        public void onRestaurantsFetched(List<Restaurant> restaurants);
    }

    public PNRetrieveFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_pnretrieve, container, false);
        pnrEditText = rootView.findViewById(R.id.pnrEditText);
        fetchTripBtn = rootView.findViewById(R.id.fetchTripBtn);
        retrievedPNRLayout = rootView.findViewById(R.id.retrievedPNRLayout);
        bannerTextView = rootView.findViewById(R.id.bannerTextView);
        depDateTextView = rootView.findViewById(R.id.depDate);
        progressBar = rootView.findViewById(R.id.progressBar);
        retrievedPNRLayout.setVisibility(View.GONE);
        restaurantFragment = (RestaurantListFragment)getChildFragmentManager()
            .findFragmentById(R.id.restaurantFragment);
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        fetchTripBtn.setOnClickListener(getFetchTripListener(requestQueue));
        return rootView;
    }

    @NonNull
    private View.OnClickListener getFetchTripListener(final RequestQueue requestQueue) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(pnrEditText.getText().toString())) {
                    Utilities.hideSoftKeyboard(getView(), getActivity());
                    Utilities.showSnackbar(getView(), "Please enter a valid PNR");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, getContext().getString(R.string
                    .pnrdataurl), null, getListener(requestQueue), getErrorListener());
                requestQueue.add(obreq);
            }
        };
    }

    @NonNull
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Utilities.showSnackbar(getView(), "Sorry no trips found");
            }
        };
    }

    @NonNull
    private Response.Listener<JSONObject> getListener(final RequestQueue requestQueue) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d(TAG, response.toString());
                    boolean pnrFound = false;
                    JSONArray pnrList = response.getJSONArray("pnrList");
                    trips = new ArrayList<>();
                    for (int i = 0; i < pnrList.length(); i++) {
                        JSONObject trip = pnrList.getJSONObject(i);
                        if (trip.getString("pnr").equalsIgnoreCase(pnrEditText.getText().toString())) {
                            String depAirportCode = trip.getString("depAirportCode");
                            String arrAirportCode = trip.getString("arrAirportCode");
                            String depDateTime = trip.getString("depDateTime");
                            TripCardItem t = new TripCardItem(depAirportCode, arrAirportCode, depDateTime, "UK 0711");
                            trips.add(t);
                            List<View> viewsToMakeVisible = new ArrayList<>();
                            viewsToMakeVisible.add(bannerTextView);
                            viewsToMakeVisible.add(restaurantFragment.getView().findViewById(R.id.restaurants));
                            JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, getContext().getString(R
                                .string.restaurantDataUrl), null,
                                Utilities.getRestaurantListener(depAirportCode, restaurantFragment,
                                    viewsToMakeVisible, true),
                                Utilities.getRestaurantErrorListener());
                            requestQueue.add(obreq);
                            retrievedPNRLayout.setVisibility(View.VISIBLE);
                            pnrFound = true;
                            long delayTrigger = Utilities.getTimeToSendNotification(Long.valueOf(depDateTime));
                            delayTrigger = 5000;
                            depDateTextView.setText(Utilities.formatMillisToDate(Long.valueOf(depDateTime)));
                            Utilities.scheduleNotification(Utilities.getNotification("KFC, Subway, Dominos",
                                getContext()), delayTrigger, getContext());
                            break;
                        }
                    }
                    if (!pnrFound) {
                        Utilities.showSnackbar(getView(), "Sorry no trips found");
                    }
                    progressBar.setVisibility(View.GONE);
                    Utilities.hideSoftKeyboard(getView(), getActivity());
                } catch (JSONException e) {
                }
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        pnrRetrievedCallBack = (onPnrRetrievedListener)context;
    }
}
