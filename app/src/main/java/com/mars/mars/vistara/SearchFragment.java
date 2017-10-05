package com.mars.mars.vistara;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mars.mars.vistara.advertisements.AdFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private EditText depAirportCode;
    private View rootView;
    private AdFragment adFragment;
    private TextView bannerTextView;
    View ads;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        depAirportCode = rootView.findViewById(R.id.depAirportCode);
        bannerTextView = rootView.findViewById(R.id.bannerTextView);
        adFragment = (AdFragment)getChildFragmentManager().findFragmentById(R.id.adFragment);
        ads = adFragment.getView().findViewById(R.id.ads);
        bannerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ads.getVisibility() == View.VISIBLE) {
                    ads.setVisibility(View.GONE);
                } else {
                    ads.setVisibility(View.VISIBLE);
                }
            }
        });
        depAirportCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 3) {
                    bannerTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        return rootView;
    }

}
