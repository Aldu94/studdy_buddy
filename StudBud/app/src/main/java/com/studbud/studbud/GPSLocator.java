package com.studbud.studbud;

import android.Manifest;
import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.studbud.studbud.domain.Stopwatch;

import java.security.Timestamp;
import java.util.Timer;

public class GPSLocator extends MainActivity implements android.location.LocationListener {


    private Stopwatch timer;

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e("Längengrad : ", "" + location.getLatitude());
            Log.e("Breitengrad: ", "" + location.getLongitude());

            double lat1 = 48.998583;
            double lng1 =12.094805;
           double lat2 = location.getLatitude();
           double lng2 =  location.getLongitude();

            Log.i("Distance", "" + distance(lat1,lng1,lat2,lng2));

            if(distance(lat1,lng1, lat2, lng2) < 0.5){
                timer.start();
            }else{
                timer.stop();
            }
        }

        long time = timer.getElapsedTimeSecs();

    }


    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 6371;

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist;
    }



    public void updateLocation() {
        GPSLocator gpsl = new GPSLocator();
        LocationManager myLocator = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            myLocator.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsl);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission.ACCESS_FINE_LOCATION}, 0);
        }

    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}