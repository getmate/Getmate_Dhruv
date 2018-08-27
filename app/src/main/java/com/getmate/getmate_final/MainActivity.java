package com.getmate.getmate_final;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.getmate.getmate_final.ProfileFragments.AcheivementViewFragment;
import com.getmate.getmate_final.ProfileFragments.InterestViewFragment;
import com.getmate.getmate_final.ProfileFragments.RecentActivityFragment;
import com.getmate.getmate_final.adapter.FeedListAdapterTimeline;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        TimelineFragment.OnFragmentInteractionListener
        ,ProfileFragment.OnFragmentInteractionListener
,GetmateFragment.OnFragmentInteractionListener,
        EventmapFragment.OnFragmentInteractionListener,
        AcheivementViewFragment.OnFragmentInteractionListener
,RecentActivityFragment.OnFragmentInteractionListener,
        InterestViewFragment.OnFragmentInteractionListener,
        com.google.android.gms.location.LocationListener
{
    private ActionBar toolbar;
    private LocationManager locationManager;
    private LocationListener locationListener;
    public Location location1 = null;
    GoogleApiClient mGoogleApiCLient;
    LocationRequest mLocationRequest;
    Location mLastLocation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String  user_id = getIntent().getStringExtra("user_id");

        Context context = getApplicationContext();

        RequestRuntimPermissions();
        //will dexter always ask for runtime permissions?
        //where to add if permission is not granted
        //
        RequestLocationUpdates();

        loadFragment(new TimelineFragment());

        //getLocationByLocationListener(); repeatation of previous one//use this if above doesn't respond

        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Timeline");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//check android version
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) !=  //this checks for request
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }
        else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);


        }

//shared preferences to store api_key and other data
        FeedListAdapterTimeline feedListAdapterTimeline = new  FeedListAdapterTimeline(context);

    }




   /* private void getLocationByLocationListener() {


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Toast.makeText(getApplicationContext(),
                        location.getLatitude()+" "+location.getLongitude(),
                        Toast.LENGTH_LONG).show();
                Constant.setCurrentlocation(location);
                mLastLocation = location;//this is last known location

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {


            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);

            }
        };
    }
    */

    private void RequestRuntimPermissions() {
        Dexter.withActivity(this)
                .withPermissions(   //add required permission here
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            //
                            RequestLocationUpdates();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();


    }

    private void RequestLocationUpdates() { //this will five runtime location

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//check android version
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) !=  //this checks for request
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }
        else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
            //this will have a callback function

        }




    } //this is demp commen t

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;
                    switch (item.getItemId()){
                        case R.id.navigation_shop:
                            toolbar.setTitle("Timeline");
                            fragment = new TimelineFragment();
                            loadFragment(fragment);
                            return true;

                        case R.id.navigation_gifts:
                            toolbar.setTitle("GetMate");
                            fragment = new GetmateFragment(); //8085002230 saku bhua
                            loadFragment(fragment);
                            return true;

                        case R.id.navigation_cart:
                            toolbar.setTitle("EventMaps");
                            fragment = new EventmapFragment();
                            loadFragment(fragment);
                            return true;
                        case R.id.navigation_profile:
                            toolbar.setTitle("Profile");
                            fragment = new ProfileFragment();
                            loadFragment(fragment);
                            return true;
                    }return false;
                }
            };
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.
                            permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                        return;
                    }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

                return;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(),location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_LONG).show();
        Constant.CURRENTLOCATION = location;
        //also how to get last known locarion
        //this will call on location changed listener
        mLastLocation = location; //this will keep eye on change location
    }



}
