package com.studbud.studbud;

import android.Manifest.permission;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

/**
 * Created by Der Bar.de on 03.09.2016.
 */
public class GPSLocator extends MainActivity {
    private EditText editTextShowLocation;
    private Button buttonGetLocation;
    private ProgressBar progress;

    private LocationManager locManager;
    private LocationListener locListener = new MyLocationListener();

    private boolean gps_enabled = false;
    private boolean network_enabled = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //editTextShowLocation = (EditText) findViewById(R.id.editTextShowLocation);

        //progress = (ProgressBar) findViewById(R.id.progressBar1);
        progress.setVisibility(View.GONE);

        //buttonGetLocation = (Button) findViewById(R.id.buttonGetLocation);
        //buttonGetLocation.setOnClickListener(this);

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
    }

    /*@Override
    public void onClick(View v) {
        progress.setVisibility(View.VISIBLE);
        // exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        // don't start listeners if no provider is enabled
        if ( !gps_enabled & !network_enabled){
            AlertDialog.Builder builder = new Builder(this);
            builder.setTitle("Attention!");
            builder.setMessage("Standort nicht gefunden. Bitte WiFi oder GPS verwenden!");
            //builder.setPositiveButton("OK", this);
            //builder.setNeutralButton("Cancel", this);
            builder.create().show();
            progress.setVisibility(View.GONE);
        }

        if (gps_enabled) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        }
        if (network_enabled) {
            if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
        }
    }*/

    class MyLocationListener extends Context implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                // This needs to stop getting the location data and save the battery power.
                if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locManager.removeUpdates(locListener);

                String longitude = "Laengengrad: " + location.getLongitude();
                String latitude = "Breitengrad: " + location.getLatitude();
                String altitiude = "Hoehe: " + location.getAltitude();
                String accuracy = "Genaugikeit: " + location.getAccuracy();
                String time = "Zeit: " + location.getTime();

                editTextShowLocation.setText(longitude + "\n" + latitude + "\n" + altitiude + "\n" + accuracy + "\n" + time);
                progress.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (which == DialogInterface.BUTTON_NEUTRAL) {
                editTextShowLocation.setText("Bitte GPS oder WIFI anschalten!");
            } else if (which == DialogInterface.BUTTON_POSITIVE) {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }

        @Override
        public void sendOrderedBroadcast(Intent intent, String receiverPermission) {

        }
    }