package com.mars.mars.vistara;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.design.widget.TabLayout;

import com.mars.mars.vistara.advertisements.AdFragment;
import com.mars.mars.vistara.search.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PNRetrieveFragment.onPnrRetrievedListener {

    private ViewPager viewPager;
    PNRetrieveFragment pnRetrieveFragment;
    private TabLayout allTabs;
    private ViewPagerAdapter adapter;
    public List<ItemNameCount> allRestaurantsPreference;
    public static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allTabs = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        getSupportActionBar().hide();
        setupViewPager();
        allTabs.setupWithViewPager(viewPager);
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager
            .PERMISSION_GRANTED) {
            String sms = readSms();
            allRestaurantsPreference = Utilities.getCountFromText(getAllRestaurantsList(), sms);
            Log.d(TAG, sms);
        } else {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { "android.permission.READ_SMS" },
                REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String sms = readSms();
        allRestaurantsPreference = Utilities.getCountFromText(getAllRestaurantsList(), sms);
        Log.d(TAG, sms);
    }

    private List<String> getAllRestaurantsList() {
        List<String> allRestaurantsList = new ArrayList<>();
        String item1 = new String("KFC");
        allRestaurantsList.add(item1);
        String item2 = new String("Subway");
        allRestaurantsList.add(item2);
        String item3 = new String("McDonalds");
        allRestaurantsList.add(item3);
        String item4 = new String("Domino's");
        allRestaurantsList.add(item4);
        String item5 = new String("Jio");
        allRestaurantsList.add(item5);
        return allRestaurantsList;
    }

    private String readSms() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        String msgData = "";
        if (cursor.moveToFirst()) {
            do {
                for (int idx = 0; idx < cursor.getColumnCount(); idx++) {
                    if (cursor.getColumnName(idx).equals("body")) {
                        msgData += " " + cursor.getString(idx);
                    }
                }
            }
            while (cursor.moveToNext());
        }
        return msgData;
    }

    @Override
    public void onTripFetched() {
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SearchFragment(), "SEARCH FLIGHTS");
        adapter.addFrag(new PNRetrieveFragment(), "FETCH TRIP");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
}
