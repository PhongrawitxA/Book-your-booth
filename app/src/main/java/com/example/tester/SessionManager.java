package com.example.tester;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;
    public static final String SESSION_USERSESSION = "userLoginSession";
    public static final String SESSION_REMEMBERME = "rememberMe";
    public static final String IS_REMEMBERME = "IsRememberMe";
    public static final String KEY_SESSIONUSERNAME = "username";
    public static final String KEY_SESSIONPASSWORD = "password";
    private static final String IS_LOGIN ="IsLoggedIn";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    public SessionManager(Context _context, String sessionName){
        context =_context;
        userSession =context.getSharedPreferences("sessionName",context.MODE_PRIVATE);
        editor = userSession.edit();
    }

    public void createLoginSession(String username, String password){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.commit();
    }

    public void createRememberMeSession(String username, String password){
        editor.putBoolean(IS_REMEMBERME,true);
        editor.putString(KEY_SESSIONUSERNAME, username);
        editor.putString(KEY_SESSIONPASSWORD, password);
        editor.commit();
    }
    public HashMap<String, String> getRememberMeDetailsFromSession(){
        HashMap<String, String> userData = new HashMap<>();
        userData.put(KEY_USERNAME,userSession.getString(KEY_USERNAME, null));
        userData.put(KEY_PASSWORD,userSession.getString(KEY_PASSWORD,null));
        return userData;
    }
    public boolean checkRememberMe(){
        if (userSession.getBoolean(IS_REMEMBERME,false)){
            return true;
        }
        else {
            return false;
        }
    }

}