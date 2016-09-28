package com.studbud.studbud;

import android.Manifest.permission;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class LocationService extends Service {

    private static final String LOG = LocationService.class.getSimpleName();
    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = 60000;
    private static final int ONE_HOUR = 3600000;
    private static final int TIME_UNTIL_UPDATE = ONE_HOUR / 2;
    private static final float DISTANCE = 500;

    private LocationManager locManager = null;

    private class LocationListener implements android.location.LocationListener {

        Location lastKnownLocation;

        public LocationListener(String provider) {
            Log.e(LOG, " LocationListener " + provider);
            lastKnownLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(LOG, " onLocationChanged: " + location);
            lastKnownLocation.set(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(LOG, " onStatusChanged: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(LOG, " onProviderEnabled: " + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(LOG, " onProviderDisabled: " + provider);
        }
    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(LOG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(LOG, "onCreate");
        initializeLocationManager();
        try {
            locManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, TIME_UNTIL_UPDATE, DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(LOG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(LOG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            locManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, TIME_UNTIL_UPDATE, DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(LOG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(LOG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(LOG, "onDestroy");
        super.onDestroy();
        if (locManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.e(LOG, " no permission to check Location");
                    }
                    locManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(LOG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }

    private void initializeLocationManager() {
        Log.e(LOG, "initializeLocationManager");
        if (locManager == null) {
            locManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}
