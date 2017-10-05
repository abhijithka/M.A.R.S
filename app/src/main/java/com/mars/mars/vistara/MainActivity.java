package com.mars.mars.vistara;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.mars.mars.vistara.advertisements.AdFragment;

public class MainActivity extends AppCompatActivity implements PNRetrieveFragment.onPnrRetrievedListener {

    AdFragment adFragment;
    PNRetrieveFragment pnRetrieveFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adFragment = (AdFragment) getSupportFragmentManager().findFragmentById(R.id.adFragment);
        pnRetrieveFragment = (PNRetrieveFragment) getSupportFragmentManager().findFragmentById(R.id.pnrRetrieveFragment);
    }

    @Override
    public void onTripFetched() {
        adFragment.getView().findViewById(R.id.ads).setVisibility(View.VISIBLE);
    }
}
