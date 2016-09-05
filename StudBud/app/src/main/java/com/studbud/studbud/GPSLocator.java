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

public class GPSLocator extends MainActivity implements android.location.LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.e("Längengrad : ", "" + location.getLatitude());
            Log.e("Breitengrad: ", "" + location.getLongitude());
        }
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