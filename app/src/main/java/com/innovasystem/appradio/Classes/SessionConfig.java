package com.innovasystem.appradio.Classes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.innovasystem.appradio.Activities.HomeActivity;
import com.innovasystem.appradio.Activities.LoginActivity;
import com.innovasystem.appradio.Utils.Utils;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import static com.innovasystem.appradio.Utils.Utils.encrypt;

public class SessionConfig{
    public static SharedPreferences pref;
    public static SharedPreferences.Editor editor;
    public static Context _context;
    public static final String PREFER_NAME = "account";
    int PRIVATE_MODE = MODE_PRIVATE;

    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String userToken = "token";
    public static final String userEmail = "Email";

    private static SessionConfig sessionConfig;

    public static final String provincia="provincia";
    public static final String AsyncTaskHome="vacio";

    public SessionConfig(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void iniciarConfig(String token,String email) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(userToken, token);
        editor.putString(userEmail, email);
        editor.commit();
    }
    public String DesencriptarToken(String tokenEncrypted){
        return Utils.decrypt(tokenEncrypted);
    }

    public void CrearProvincia(String Provincia){
        editor.putString(provincia, Provincia);
        editor.commit();
    }
    public void AsignarTareahome(String tarea){
        editor.putString(AsyncTaskHome, tarea);
        editor.commit();
    }
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);

            return true;
        }
        return false;
    }
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(userToken, pref.getString(userToken, null));
        user.put(userEmail, pref.getString(userEmail, null));
        return user;
    }

    public String getValue(String key) {
        return pref.getString(key, null);
    }
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }


    public static SessionConfig getSessionConfig(Context c) {
        if(sessionConfig == null){
            sessionConfig= new SessionConfig(c);
        }
        return sessionConfig;
    }//*/
}
