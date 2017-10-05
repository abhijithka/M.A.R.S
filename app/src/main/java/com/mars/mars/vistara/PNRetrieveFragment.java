package com.mars.mars.vistara;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PNRetrieveFragment extends Fragment {

    private String TAG = getClass().getSimpleName();
    private View rootView;
    private EditText pnrEditText;
    private Button fetchTripBtn;
    RelativeLayout retrievedPNRLayout;
    onPnrRetrievedListener mCallback;

    public interface onPnrRetrievedListener {

        public void onTripFetched();
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
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        fetchTripBtn.setOnClickListener(getFetchTripListener(requestQueue));
        return rootView;
    }

    @NonNull
    private View.OnClickListener getFetchTripListener(final RequestQueue requestQueue) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, getContext().getString(R.string
                    .pnrdataurl), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, response.toString());
                            JSONArray pnrList = response.getJSONArray("pnrList");
                            for (int i = 0; i < pnrList.length(); i++) {
                                JSONObject trip = pnrList.getJSONObject(i);
                                if (trip.getString("pnr").equalsIgnoreCase(pnrEditText.getText().toString())) {
                                    retrievedPNRLayout.setVisibility(View.VISIBLE);
                                    mCallback.onTripFetched();
                                    break;
                                }
                            }
                        } catch (JSONException e) {
                        }
                    }
                },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
                );
                requestQueue.add(obreq);
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (onPnrRetrievedListener)context;
    }
}
