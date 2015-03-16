package com.lianigroup.rememberthetahini;

import android.net.Uri;

import com.google.android.gms.location.Geofence;

public final class Constants {

	private Constants() {
		// TODO Auto-generated constructor stub
	}
	public static final String TAG = "RememberTheTahiniApp";
	// Request code to attempt to resolve Google Play services connection failures.
    
	public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    // Timeout for making a connection to GoogleApiClient (in milliseconds).
    public static final long CONNECTION_TIME_OUT_MS = 100;
 // Path for the DataItem containing the last geofence id entered.
    public static final String GEOFENCE_DATA_ITEM_PATH = "/geofenceid";
    public static final Uri GEOFENCE_DATA_ITEM_URI =
            new Uri.Builder().scheme("wear").path(GEOFENCE_DATA_ITEM_PATH).build();
    public static final String KEY_GEOFENCE_ID = "geofence_id";
    public static final String KEY_EXPIRATION_DURATION =
            "com.example.wearable.geofencing.KEY_EXPIRATION_DURATION";
    public static final String KEY_TRANSITION_TYPE =
            "com.example.wearable.geofencing.KEY_TRANSITION_TYPE";
    // The prefix for flattened geofence keys.
    public static final String KEY_PREFIX = "com.example.wearable.geofencing.KEY";


    // For the purposes of this demo, the geofences are hard-coded and should not expire.
    // An app with dynamically-created geofences would want to include a reasonable expiration time.
    public static final long GEOFENCE_EXPIRATION_TIME = Geofence.GEOFENCE_TRANSITION_ENTER;
    
 // Invalid values, used to test geofence storage when retrieving geofences.
    public static final long INVALID_LONG_VALUE = -999l;
    public static final float INVALID_FLOAT_VALUE = -999.0f;
    public static final int INVALID_INT_VALUE = -999;

}
