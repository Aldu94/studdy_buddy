package com.studbud.studbud;


import android.Manifest;
import android.Manifest.permission;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

public class GPSLocator extends Profile implements android.location.LocationListener {

    private final Context context;
    private LocationManager locManager;

    private static final long UPDATE_INTERVAL_ONE_SECOND = 1000;
    private static final long UPDATE_INTERVAL = UPDATE_INTERVAL_ONE_SECOND *10;
    private static final long MIN_DISTANCE = 0;

    Location location;
    private boolean isGPSEnabled;


    public GPSLocator(Context context) {
        this.context = context;
    }

    /*
     * Here we will check, if the user has already granted access to the locationservice. As for
     * newer APIs there needs to be a permissioncheck inserted, we have added the a statement to check
     * for the used API as well.
     *
     * Source of the function: http://stackoverflow.com/questions/32491960/android-check-permission-for-locationmanager
     */
    @TargetApi(23)
    public void getLocation(Context context) {
        // Here we have the above mentioned permissioncheck
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        } else {
            try {
                locManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

                isGPSEnabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (!isGPSEnabled) {

                } else {
                    if (isGPSEnabled) {
                        if (location == null) {
                            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_INTERVAL, MIN_DISTANCE, this);
                            if (locManager != null) {
                                location = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    checkForCampusLocation(location);
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*
     * here we can put in our evaluation function which checks how close the user is to
     * the university campus. If he is close enough, this will set the bollean in the Profile
     * class true
     */

    public void checkForCampusLocation(Location location) {
        if (location != null) {
            double lat1 = 48.99821192628251;
            double lng1 = 12.095339521329151;
            double lat2 = location.getLatitude();
            double lng2 = location.getLongitude();
            if (distance(lat1, lng1, lat2, lng2) < 300) {
                setIsOnCampus(true);
            } else {
                setIsOnCampus(false);
            }
        }
    }


    /*
     * Here we calculate the distance using the latitude and longitude of
     * the last known location and the current location
     */
    private double distance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371e3;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double dist = earthRadius * c;

        return dist;
    }

    // method is called when new Location arrived
    @Override
    public void onLocationChanged(Location location) {
        checkForCampusLocation(location);
    }
    //autoimplemented methods - not used
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    //autoimplemented methods - not used
    @Override
    public void onProviderEnabled(String provider) {
    }

    //autoimplemented methods - not used
    @Override
    public void onProviderDisabled(String provider) {
        Intent i = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(i);
    }
}