package com.mars.mars.vistara;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mars.mars.vistara.restaurants.Restaurant;
import com.mars.mars.vistara.restaurants.RestaurantListFragment;
import com.mars.mars.vistara.restaurants.menu.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by aappukuttan on 10/6/2017.
 */
public class Utilities {

    public static void hideSoftKeyboard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context
            .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar mySnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }

    @NonNull
    public static Response.Listener<JSONObject> getRestaurantListener(final String depAirportCode, final
    RestaurantListFragment restaurantFragment, final List<View> viewsToMakeVisible, final boolean shouldShowCart) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Restaurant> restaurants = parseRestaurantDataAndGetRestaurantsAt(response, depAirportCode);
                    restaurantFragment.setRestaurants(restaurants, shouldShowCart);
                    for (View view : viewsToMakeVisible) {
                        view.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                }
            }
        };
    }

    private static List<Restaurant> parseRestaurantDataAndGetRestaurantsAt(JSONObject response, String depAirportCode)
        throws JSONException {
        List<Restaurant> restaurantsAtAirport = new ArrayList<>();
        JSONArray airportsList = response.getJSONArray("airportsList");
        for (int i = 0; i < airportsList.length(); i++) {
            JSONObject jsonObject = airportsList.getJSONObject(i);
            String airport = jsonObject.getString("code");
            if (airport.equalsIgnoreCase(depAirportCode)) {
                JSONArray restaurants = jsonObject.getJSONArray("restaurants");
                for (int j = 0; j < restaurants.length(); j++) {
                    JSONObject restaurant = restaurants.getJSONObject(j);
                    String name = restaurant.getString("name");
                    String imgUrl = restaurant.getString("icon");
                    JSONArray menuList = restaurant.getJSONArray("menu");
                    List<Menu> menu = new ArrayList<>();
                    for(int k=0; k < menuList.length(); k++) {
                        JSONObject menuObj = menuList.getJSONObject(k);
                        String item = menuObj.getString("item");
                        String price = menuObj.getString("price");
                        String imageUrl = menuObj.getString("imageUrl");
                        Menu m = new Menu(item, Integer.valueOf(price), imageUrl);
                        menu.add(m);
                    }
                    Restaurant restaurantObj = new Restaurant(name, imgUrl);
                    restaurantObj.setMenu(menu);
                    restaurantsAtAirport.add(restaurantObj);
                }
                break;
            }
        }
        return restaurantsAtAirport;
    }

    @NonNull
    public static Response.ErrorListener getRestaurantErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
    }

    public static List<ItemNameCount> getCountFromText(List<String> names, String text) {
        List<ItemNameCount> nameCounts = new ArrayList<>();
        String[] strings = text.split(" ");
        for (String name : names) {
            ItemNameCount nameCount = new ItemNameCount(name, occurence(strings, name));
            nameCounts.add(nameCount);
        }
        Collections.sort(nameCounts, new Comparator<ItemNameCount>() {
            @Override
            public int compare(ItemNameCount itemNameCount, ItemNameCount t1) {
                return t1.count - itemNameCount.count;
            }
        });
        return nameCounts;
    }

    private static int occurence(String[] strings, String search) {
        int count =0;
        for(int i=0;i<strings.length;i++){
            if(strings[i].equalsIgnoreCase(search))
                count++;

        }
        return(count);
    }

    public static String formatMillisToDate(long timeInMillis) {
        Date date = new Date(timeInMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(date);
    }

    public static void scheduleNotification(Notification notification, long delay, Context context) {

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent
            .FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public static Notification getNotification(String content, Context context) {
        Notification.Builder builder = new Notification.Builder(context);
        builder.setColor(Color.parseColor("#3498DB"));
        builder.setContentTitle("You can pre order from your favourite airports now");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.arrow);
        return builder.build();
    }

    public static long getTimeToSendNotification(long timeInMillis) {
        Date currentDate = new Date();
        long twoDays = 172800000;
        long tts = timeInMillis - currentDate.getTime() - twoDays;
        return tts;
    }
}
