package com.example.taher.localarea;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.HashMap;


public class AccountFragment extends Fragment implements View.OnClickListener {
    private View rootview;
    private Context context;
    private Button updatePositionButton;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Activity baseAct;
    private ProgressBar updatePositionProgress;
    private String lng, lat;
    private boolean stopGettingPosition;
    //Implementing interface LocationListener and defining an inner private class implementing that interface for use
    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location loc) {
            if(stopGettingPosition)
                return;
            lng = ""+loc.getLongitude();
            lat = ""+loc.getLatitude();
//            Toast.makeText(context, "Location changed!", Toast.LENGTH_LONG).show();
//            Toast.makeText(context,"Location changed : Lat: " + loc.getLatitude()+ " Lng: " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
            updatePositionProgress.setVisibility(View.INVISIBLE);

            //Write new data to db
            HashMap<String, String> coordinates = new HashMap<String, String>();
            coordinates.put("id", Home.user.getId()+"");
            coordinates.put("lat", lat);
            coordinates.put("long", lng);
            //Initializing a new connection, and sending data using the hashmap to the service, then processing response
            //through implementing the interface.
            Connection con = new Connection(coordinates, new ConnectionPostListener() {
                @Override
                public void doSomething(String result) {
                    if(result == null)
                        Toast.makeText(context, "Something is wrong!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Location changed!", Toast.LENGTH_LONG).show();
                }
            });
            con.execute(Constants.UPDATEPOSITION);
            stopGettingPosition = true;
        }


        //There's a problem with the these events, they keep getting called back while the user is opening the GPS, so better not do anything here!
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
//            Toast.makeText(context, "providerDisabled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
//            Toast.makeText(context, "providerenabled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) {
            // TODO Auto-generated method stub
//            Toast.makeText(context, "statuschanged", Toast.LENGTH_LONG).show();
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Inflating the activity with the fragment
        rootview = inflater.inflate(R.layout.account_fragment, container, false);

        //Setting up variables and objects
        rootview.setOnClickListener(this);
        baseAct = getActivity();
        context = getContext();
        locationListener = new MyLocationListener();
        lat = new String();
        lng = new String();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        stopGettingPosition = true;
        //Setting the update position button listener and the progress bar
        updatePositionProgress = (ProgressBar) rootview.findViewById(R.id.updatePositionProgress);
        updatePositionProgress.setVisibility(View.INVISIBLE);
        updatePositionButton = (Button) rootview.findViewById(R.id.updatePositionButton);
        updatePositionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkGPS()) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    stopGettingPosition = false;
                    updatePositionProgress.setVisibility(View.VISIBLE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
                } else {
                    Toast.makeText(context, "Enable GPS!", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootview;
    }



    @Override
    public void onClick(View v) {
        //NOT WORKING !!
    }

    private boolean checkGPS() {
        ContentResolver cr = baseAct.getBaseContext().getContentResolver();
        return Settings.Secure.isLocationProviderEnabled(cr, LocationManager.GPS_PROVIDER);

    }


}





