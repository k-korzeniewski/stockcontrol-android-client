package com.kamilkorzeniewski.stockcontrolclient.retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kamilkorzeniewski.stockcontrolclient.R;

public class TokenService {

    private final RestApiClient restApiClient = RestApiClient.getInstance();

    public static boolean isTokenExpired(Context context){
        final String TOKEN_KEY = context.getString(R.string.token_key);
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(!sharedPreferences.contains(TOKEN_KEY))
            return false;



    }
}
