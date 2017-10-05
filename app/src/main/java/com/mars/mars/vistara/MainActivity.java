package com.mars.mars.vistara;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.mars.mars.vistara.advertisements.AdFragment;

public class MainActivity extends AppCompatActivity implements PNRetrieveFragment.onPnrRetrievedListener {

    AdFragment adFragment;
    PNRetrieveFragment pnRetrieveFragment;
    public static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adFragment = (AdFragment) getSupportFragmentManager().findFragmentById(R.id.adFragment);
        pnRetrieveFragment = (PNRetrieveFragment) getSupportFragmentManager().findFragmentById(R.id.pnrRetrieveFragment);
        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager
            .PERMISSION_GRANTED) {
            String sms = readSms();
            Log.d(TAG, sms);
        } else  {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"},
                REQUEST_CODE_ASK_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String sms = readSms();
        Log.d(TAG, sms);
    }

    private String readSms() {
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
        String msgData = "";
        if (cursor.moveToFirst()) {
            do {
                for(int idx=0;idx<cursor.getColumnCount();idx++)
                {
                    if (cursor.getColumnName(idx).equals("body")) {
                        msgData += " " + cursor.getString(idx);
                    }
                }
            } while (cursor.moveToNext());
        }
        return msgData;
    }

    @Override
    public void onTripFetched() {
        adFragment.getView().findViewById(R.id.ads).setVisibility(View.VISIBLE);
    }
}
