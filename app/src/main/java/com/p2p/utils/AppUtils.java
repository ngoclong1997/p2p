package com.p2p.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.p2p.R;

public class AppUtils {
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager im =(InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);
    }

}
