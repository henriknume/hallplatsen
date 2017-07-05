package ex.nme.hallplatsen.services;

import android.content.Context;
import android.content.SharedPreferences;

import ex.nme.hallplatsen.R;

/**
 * Created by nume on 2017-07-05
 */

class TokenStorage {

    public static final String TOKEN_PREFERENCES_FILE = "ex.nme.hallplatsen.token.preferences.file";
    public static final String BEARER_TOKEN = "ex.nme.hallplatsen.bearer.token";

    private Context mContext;

    TokenStorage(Context context){
        this.mContext = context;
    }

    void setToken(String newToken){
        SharedPreferences sharedPref = getSharedPrefs();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(BEARER_TOKEN, newToken);
        editor.commit();
    }

    String getToken(){
        SharedPreferences sharedPref = getSharedPrefs();
        String defaultValue = "NO_TOKEN_AVAILABLE";
        return sharedPref.getString(BEARER_TOKEN, defaultValue);
    }

    private SharedPreferences getSharedPrefs(){
        return mContext.getSharedPreferences(TOKEN_PREFERENCES_FILE, Context.MODE_PRIVATE);
    }
}
