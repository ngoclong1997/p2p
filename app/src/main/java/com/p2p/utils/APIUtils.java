package com.p2p.utils;

import android.content.Context;

public class APIUtils {
    public static final String BASIC_AUTHORIZATION = "Basic Y2xpZW50OnNlY3JldA==";

    public static String getOAuthToken(Context context) {
        return "Bearer " + PrefsUtils.getPreferenceValue(context, PrefsUtils.ACCESS_TOKEN, "");
    }



}
