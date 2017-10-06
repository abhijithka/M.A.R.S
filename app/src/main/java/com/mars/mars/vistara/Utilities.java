package com.mars.mars.vistara;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by aappukuttan on 10/6/2017.
 */
public class Utilities {

    public static void hideSoftKeyboard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context
            .INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSnackbar(View view, String msg) {
        Snackbar mySnackbar = Snackbar.make(view, msg, Snackbar.LENGTH_SHORT);
        mySnackbar.show();
    }
}
