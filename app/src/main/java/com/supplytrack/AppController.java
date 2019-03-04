package com.supplytrack;

/**
 * Created by Kencas on 04/10/2016.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.supplytrack.model.Customer;
import com.supplytrack.utils.ComplexPreferences;

//import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDexApplication;
//import android.support.multidex.MultiDex;


public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();



    //Creating sharedpreferences object
    //We will store the user data in sharedpreferences
    private SharedPreferences sharedPreferences;

    private static AppController mInstance;

    //private Wallet wallet;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }



    public static AppController getInstance() {
        return mInstance;
    }


    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null)
            sharedPreferences = getSharedPreferences(Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    //This method will clear the sharedpreference
    //It will be called on logout
    public void logout(Context ctx)
    {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "object_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

    //This method will store the user data on sharedpreferences
    //It will be called on login
    public void loginUser(Context ctx, Customer driver) {
        //SharedPreferences.Editor editor = getSharedPreferences().edit();
        //editor.putInt(Constants.USER_ID, id);
        //editor.putString(Constants.USER_PHONE, phoneno);
        //editor.putBoolean(Constants.IS_LOGGED_IN, true);
        //editor.apply();

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "object_prefs", 0);
        complexPreferences.putObject("customer", driver);

        complexPreferences.commit();
    }



   //This method will check whether the user is logged in or not
    public boolean isLoggedIn(Context ctx)
    {
        boolean loggedOn = false;

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "object_prefs", 0);
        Customer customer = complexPreferences.getObject("customer", Customer.class);
        if(customer == null)
        {
            return loggedOn;
        }
        if(customer.loggedOn)
        {
            return loggedOn;
        }
        loggedOn = true;

        return loggedOn;

    }

    public Customer getCustomer(Context ctx)
    {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "object_prefs", 0);
        Customer customer = complexPreferences.getObject("customer", Customer.class);
        return customer;
    }


    public static void saveToPref(Activity activity, String key, Boolean value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static SharedPreferences getPref(Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE);
    }
}