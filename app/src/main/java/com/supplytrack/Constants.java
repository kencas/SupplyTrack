package com.supplytrack;

/**
 * Created by Kencas on 05/10/2016.
 */
public class Constants {
    public static final String SHARED_PREF_NAME = "supplytracker";
    public static final String USER_ID = "userid";
    public static final String USER_PHONE = "phoneno";
    public static final String USER_EMAIL= "useremail";
    public static final String IS_LOGGED_IN= "is_logged_in";
    public static final int MAX_LENGTH_CARD_NUMBER_VISA_MASTERCARD = 30;
    public static final int MAX_LENGTH_CARD_NUMBER_AMEX = 17;


    public static final String teaching_base_path = "http://ckc99set.org/omegaconnect/dashboard/app/webroot/teachingss/";
    public static final String videos_base_path = "http://ckc99set.org/omegaconnect/dashboard/app/webroot/videoss/";

    // Milliseconds per second
    private static final int MILLISECONDS_PER_SECOND = 1000;
    // Update frequency in seconds
    private static final int UPDATE_INTERVAL_IN_SECONDS = 60;
    // Update frequency in milliseconds
    public static final long UPDATE_INTERVAL = MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // The fastest update frequency, in seconds
    private static final int FASTEST_INTERVAL_IN_SECONDS = 60;
    // A fast frequency ceiling in milliseconds
    public static final long FASTEST_INTERVAL = MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
    // Stores the lat / long pairs in a text file
    public static final String LOCATION_FILE = "sdcard/location.txt";
    // Stores the connect / disconnect data in a text file
    public static final String LOG_FILE = "sdcard/log.txt";

    public static final String RUNNING = "runningInBackground"; // Recording data in background

    public static final String APP_PACKAGE_NAME = "com.blackcj.locationtracker";

    public static final String CUSTOMER_OBJ = "customer";

    public static final String HOSPITAL_OBJ = "hospital";

    public static final String SUPPLY_OBJ = "supply";

    public static final String AD_OBJ = "ad";

    public static final String WALLET_OBJ = "wallet";

    public static final String COMPETITION_OBJ = "competition";

    public static final String REVIEWTAB_OBJ = "tab";

    public static final String PIX_OBJ = "pix";

    public static final String VERIFIER_OBJ = "verifier";

    public static final String VIDEO_OBJ = "video";

    public static final String GIST_OBJ = "gist";

    public static final String DISCOUNT_OBJ = "discount";

    /**
     * Suppress default constructor for noninstantiability
     */
    private Constants() {
        throw new AssertionError();
    }



    //Post Revie
}
