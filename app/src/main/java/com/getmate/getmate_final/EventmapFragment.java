package com.getmate.getmate_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;

import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getmate.getmate_final.Activities.ScrollingActivity;
import com.getmate.getmate_final.app.AppController;
import com.getmate.getmate_final.data.FeedItemTimeline;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import  com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.sql.StatementEvent;


public class EventmapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        GoogleApiClient.OnConnectionFailedListener,
GoogleApiClient.ConnectionCallbacks,
        LocationListener

{
   GoogleApiClient mGoogleApiCLient;
   LocationRequest mLocationRequest;
   Location mLastLocation;
   GoogleMap mMap;
    MarkerOptions markerOptions = new MarkerOptions();
   Marker mCurrentLocationMarker;
   final String URL_MAPFRAG = "Some required url";
   int user_id;
   Context context;

   JSONArray beginner,intermediate,expert;

RequestQueue queue = AppController.getInstance().getRequestQueue();
    private OnFragmentInteractionListener mListener;

    public EventmapFragment() {
        // Required empty public constructor
    }


    public static EventmapFragment newInstance() {
        EventmapFragment fragment = new EventmapFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        SharedPreferences sharedPreferences = this.getActivity().
                getSharedPreferences("UserLoginData", Context.MODE_PRIVATE);

        user_id = sharedPreferences.getInt("api_key",0);
        context = this.getActivity();



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_eventmap, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);

        //make a call to get *all events* nearby and set *a specific colour* for the one you are interested in


        //feed items getJSON arry from TIMELINE FRAGMENTS , take their location and then
        JSONObject object = new JSONObject();
        String api_key = null;
        try {
            object.put("api_key",api_key);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_MAPFRAG, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                     beginner = jsonObject.getJSONArray("beginner");
                     intermediate = jsonObject.getJSONArray("intermediate");
                     expert = jsonObject.getJSONArray("expert");
                    setMarkers();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id",String.valueOf(user_id));
                params.put("lat",String.valueOf(Constant.CURRENTLOCATION.getLatitude()));
                params.put("lon",String.valueOf(Constant.CURRENTLOCATION.getLongitude()));
                return params;
            }
        };

        queue.add(request);
        return view;
    }

    public void parseEventMapFrag(JSONObject jsonObject){
        //parse everthing here
        //also
    }




    protected  synchronized void buildgoogleApiClient(){
        mGoogleApiCLient = new GoogleApiClient.Builder(getActivity()).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).
                addApi(LocationServices.API).
                build();

        mGoogleApiCLient.connect();


    }


    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if(mCurrentLocationMarker!= null){
            mCurrentLocationMarker.remove();
        }

       // mMap.setOnMarkerClickListener(this);
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude())  ;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("yeh mere baap ki jagah hain");

        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        //set bitmap to icons related to their interest
        mCurrentLocationMarker = mMap.addMarker(markerOptions);
        mCurrentLocationMarker.setTag(latLng);




        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.9f));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if(mGoogleApiCLient!=null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiCLient,
                    (com.google.android.gms.location.LocationListener) this);
        }








    }


    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */


    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     *
     */



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates
                    (mGoogleApiCLient, mLocationRequest,
                            (com.google.android.gms.location.LocationListener) this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(googleMap.MAP_TYPE_TERRAIN);


        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildgoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
           buildgoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


    }


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiCLient == null) {
                            buildgoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final int event_id =(int) marker.getTag();
        //make a volley request get data and intent it to Scrolling activity

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                "some url", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                ParseJsonResponse p = new ParseJsonResponse();
                FeedItemTimeline item = new FeedItemTimeline();
                try {
                    item = p.parseEventJsonResponse(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(context, ScrollingActivity.class);
                i.putExtra("item",item);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_id", String.valueOf(event_id));
                return params;
            }
        };
        AppController.getInstance().getRequestQueue().add(request);
        return false;
    }


    public interface OnFragmentInteractionListener {

    }


    public void setMarkers() throws JSONException {
        for (int i = 0;i<beginner.length();i++){

            LatLng latLng = new LatLng(beginner.getJSONObject(i).getDouble("lat"),
                    beginner.getJSONObject(i).getDouble("lon")) ;

            markerOptions.position(latLng);

            markerOptions.title(beginner.getJSONObject(i).getString("title"));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions).setTag(beginner.getJSONObject(i).getInt("event_id"));
        }
        for (int i = 0;i<intermediate.length();i++){

            LatLng latLng = new LatLng(intermediate.getJSONObject(i).getDouble("lat"),
                    intermediate.getJSONObject(i).getDouble("lon")) ;

            markerOptions.position(latLng);
            markerOptions.title(intermediate.getJSONObject(i).getString("title"));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions).setTag(intermediate.getJSONObject(i).getInt("event_id"));
        }

        for (int i = 0;i<expert.length();i++){

            LatLng latLng = new LatLng(expert.getJSONObject(i).getDouble("lat"),
                    expert.getJSONObject(i).getDouble("lon")) ;

            markerOptions.position(latLng);
            markerOptions.title(expert.getJSONObject(i).getString("title"));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions).setTag(expert.getJSONObject(i).getInt("event_id"));
        }
    }
}
