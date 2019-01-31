package com.example.curr_loc_api;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.LocationListener;
import android.location.LocationManager;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    LocationManager locManage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locManage = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //check if network is enabled
        if (locManage.isProviderEnabled(locManage.NETWORK_PROVIDER))
        {
            locManage.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener()
                    {
                        @Override
                        public void onLocationChanged(Location location) {
                            //get the latitude
                            double my_latitude = location.getLatitude();
                            //get the longitude
                            double my_longitude = location.getLongitude();
                            //start LatLng class
                            LatLng latLng = new LatLng(my_latitude, my_longitude);
                            //object - Geocoder (get addr of those)
                            Geocoder geocoder = new Geocoder(getApplicationContext());

                            try
                            {
                                List<Address> addressList = geocoder.getFromLocation(my_latitude,my_longitude,1);
                                String str = addressList.get(0).getLocality() + ", ";
                                str += addressList.get(0).getCountryName() + ", ";
                                str += addressList.get(0).getPostalCode();

                                mMap.addMarker(new MarkerOptions().position(latLng).title(str));

                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
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

            );
        }
        else if(locManage.isProviderEnabled(locManage.GPS_PROVIDER))
        {
        locManage.requestLocationUpdates(locManage.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //get the latitude
                double my_latitude = location.getLatitude();
                //get the longitude
                double my_longitude = location.getLongitude();
                //start LatLng class
                LatLng latLng = new LatLng(my_latitude, my_longitude);
                //object - Geocoder (get addr of those)
                Geocoder geocoder = new Geocoder(getApplicationContext());

                try
                {
                    List<Address> addressList = geocoder.getFromLocation(my_latitude,my_longitude,1);
                    String str = addressList.get(0).getLocality() + ", ";
                    str += addressList.get(0).getCountryName() + ", ";
                    str += addressList.get(0).getPostalCode();

                    mMap.addMarker(new MarkerOptions().position(latLng).title(str));

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.2f));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
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
        });
        }
        }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;  //google map instance

        // Add a marker in myLoc and move the camera

        /*
        LatLng sydney = new LatLng(-34, 151); //longtitute, altitute for Sydney
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney)); //moving to the location actually
        */
    }
}
