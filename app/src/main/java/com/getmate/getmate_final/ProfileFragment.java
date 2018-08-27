package com.getmate.getmate_final;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.Utils.RoundedImageView;
import com.getmate.getmate_final.adapter.ViewPagerAdapter;
import com.getmate.getmate_final.app.AppController;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


/**
 * .make viewpager or tablayout/horizontal list view here
 * how to make profileview when u want to see profiles of other junta
 *
 */
public class ProfileFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener
,GoogleApiClient.ConnectionCallbacks{
    Person person = new Person();

    private final Context context = getContext();

    private OnFragmentInteractionListener mListener;
    GoogleApiClient mGoogleApiCLient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    GoogleMap mMap;
    Marker mCurrentLocationMarker;
    private  String  URL_GETPROFILE = "url to get profile info";
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TextView age,gender,no_connections,school,work,name,handle,email,bio,city;
    RoundedImageView profilePic;
    ImageView coverPic,fb_icon,insta_icon,linkedin_icon,wordpress_icon,www;





    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        View photoHeader = view.findViewById(R.id.photoHeader);

        findViewsById(view);
        setToViews(view);


        //TabLayout View here
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Recent Activity"));
        tabLayout.addTab(tabLayout.newTab().setText("Interest"));
        tabLayout.addTab(tabLayout.newTab().setText("Achievement"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //ViewPager and other structure here
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getFragmentManager(),person.getInterestsB(),
                person.getInterestsI(),person.getInterestsE(),person.getAcheivemets(),person.getRecentActivities());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        JSONObject object =  new JSONObject();
        //object.put("api_key",api_key);
        //object.put("user_id",user_id);

            //making fresh JSON request and getting  json
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_GETPROFILE,
                    object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    VolleyLog.d(TAG, "Response: " + jsonObject.toString());
                    if (jsonObject != null) {
                        parseJsonProfileResponse(jsonObject);
                    }


                }
            },  new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d(TAG,"Error"+volleyError.getMessage());
                }
            });

            AppController.getInstance().addToRequestQueue(jsonObjectRequest);










        //whats is idea behind using cacherequest and when to us
        // try first for cache and if it is null then go for direct volleyRequest
    /*CacheRequest cacheRequest = new CacheRequest(0, url, new Response.Listener<NetworkResponse>() {

        @Override
      public void onResponse(NetworkResponse networkResponse) {
          try {
              final String jsonString = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
              JSONObject jsonObject = null;        //use the above data set yo parse to UI components
              try {
                  jsonObject = new JSONObject(jsonString);
              } catch (JSONException e) {
                  e.printStackTrace();
              }
              try {
                  textView.setText(jsonObject.toString(5));
              } catch (JSONException e) {
                  e.printStackTrace();
              }
              Toast.makeText(context, "onResponse:\n\n" + jsonObject.toString(), Toast.LENGTH_SHORT).show();

          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }
      }
  }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError volleyError) {
          Toast.makeText(context, "onErrorResponse:\n\n" + volleyError.toString(), Toast.LENGTH_SHORT).show();

      }
  });
  requestQueue.add(cacheRequest);

*/













        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            photoHeader.setTranslationZ(6);
            photoHeader.invalidate();
        }

        //MAp fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.imageView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        //JSON OBJECT REQUEST USING VOLLEY TO GET USER DETAILS HERE
      /*  //in json request attach a JSON object with api_key to get data
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL_FEED, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                VolleyLog.d(TAG, "Response: " + jsonObject.toString());
                if (jsonObject != null) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


            }
        });

*/


        return view;
    }
    public void parseJsonProfileResponse(JSONObject response){

        try {  //optSting returns null if it doesn't exists and getString returns exception
            person.setName(response.optString("name"));
            person.setHandle(response.getString("handle"));
            person.setProfilePic(response.getString("profilePic"));
            person.setCoverPic(response.getString("coverPic"));
            person.setBio(response.getString("bio"));
            person.setUser_id(response.getInt("user_id"));
            person.setRange(response.getInt("range"));
            person.setR_lat(response.getLong("lat"));
            person.setR_lon(response.getLong("lon"));

            //To set the school
            JSONArray array2 = response.getJSONArray("school");

            ArrayList<String> school = new ArrayList<>();
            for (int i =0;i<array2.length();i++){

                school.add(array2.getString(i));
            }
            person.setSchool(school);

            //Code to fetch work details
            JSONArray array3 = response.getJSONArray("work");
            ArrayList<String> company = new ArrayList<>();
            ArrayList<String> position = new ArrayList<>();
            for (int j =0; j<array3.length();j++){
                JSONObject work = array3.optJSONObject(j);
                //work is of form {position:software developer,company:GetMate}
                //use key value pair here
                company.add(work.getString("company"));
                position.add(work.getString("position"));

            }


            person.setEmail(response.getString("email"));
            person.setPhone(response.getLong("phone"));
            person.setAddress(response.getString("address"));
            person.setFb_url(response.getString("fb_url"));
            person.setInsta_url(response.getString("insta_url"));
            person.setLink_url(response.getString("link_url"));

            //fetching interests
            JSONArray interestB = response.getJSONArray("interestsB");
            ArrayList<String> ib = new ArrayList<>();
            for (int k = 0;k<interestB.length();k++){
              //  ib[k] = interestB.optString(k);
                ib.add(interestB.optString(k));
            }
            person.setInterestsB(ib);

            JSONArray interestI = response.getJSONArray("interestsI");// it is string array
            //like:[kabaddi,majekarna,pagalpan]

            ArrayList<String> ii = new ArrayList<>();
            for (int k = 0;k<interestI.length();k++){
                //ii[k] = interestI.optString(k);
                ii.add(interestI.optString(k));
            }
            person.setInterestsI(ii);


            JSONArray interestE = response.getJSONArray("interestsE");
            ArrayList<String> ie = new ArrayList<>();

            for (int k = 0;k<interestE.length();k++){
                ie.add(interestE.optString(k));
            }
            person.setInterestsE(ie);

            person.setGender(response.getInt("gender"));

            //loading Connections
            JSONArray connections = response.getJSONArray("connections");
            int[] co = new int[connections.length()];
            for (int k =0;k<connections.length();k++){
                co[k] = connections.optInt(k);
            }
            person.setConnections(co);


            //loading recent activities
            JSONArray recent = response.getJSONArray("recent");
            int[] r = new int[recent.length()];
            for (int k =0;k<recent.length();k++){
                r[k]=recent.optInt(k);
            }
            person.setRecentActivities(r);

            //load acheivements
            JSONArray achvmt = response.getJSONArray("acheivements");

            ArrayList<String> a = new ArrayList<>();
            for (int k = 0;k<achvmt.length();k++){

                a.add(achvmt.optString(k));
            }
            person.setAcheivemets(a);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (ActivityCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0,0),10.9f));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


    }

    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if(mCurrentLocationMarker!= null){
            mCurrentLocationMarker.remove();
        }


        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude())  ;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("yeh mere baap ki jagah hain");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrentLocationMarker = mMap.addMarker(markerOptions);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10.9f));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        if(mGoogleApiCLient!=null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiCLient,
                    (com.google.android.gms.location.LocationListener) this);
        }



    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

    }
    private void findViewsById(View view) {
        name = view.findViewById(R.id.tvName);
        handle = view.findViewById(R.id.handle);
        // TextView age,school,work,name,handle,email,bio,city;
        //RoundedImageView profilePic;
        //ImageView coverPic,fb_icon,insta_icon,linkedin_icon,blogger_icon,www;
        work = view.findViewById(R.id.tvTitle);
        age = view.findViewById(R.id.tvAge);
        school = view.findViewById(R.id.tvEducation);
        email = view.findViewById(R.id.tvEmail);
        city = view.findViewById(R.id.tvAddress);
        fb_icon  = view.findViewById(R.id.fb_icon);
        insta_icon = view.findViewById(R.id.instagram_icon);
        linkedin_icon = view.findViewById(R.id.linkedin_icon);
        wordpress_icon = view.findViewById(R.id.wordpress_icon);

    }

    private void setToViews(View view) {
        name.setText(person.getName());
        handle.setText(person.getHandle());
        String w =null;

        for (int i=0;i<person.getWork().size();i++){
            w = w.concat("\n"+person.getWork().get(i));
        }
        work.setText(w);

        Calendar c= Calendar.getInstance();

        c.setTimeInMillis(person.getDOB());
        age.setText(Calendar.getInstance().YEAR - c.YEAR);


        w=null;
        for (int i=0;i<person.getSchool().size();i++){
            w = w.concat("\n"+person.getSchool().get(i));
        }
        school.setText(w);

        email.setText(person.getEmail());
        city.setText(person.getAddress());


        if(person.getFb_url()!=null){
            fb_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToUrl(person.getFb_url());
                }
            });
        }
        else {
            fb_icon.setVisibility(View.GONE);
        }
        if(person.getInsta_url()!=null){
            insta_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToUrl(person.getInsta_url());
                }
            });
        }
        else {
            insta_icon.setVisibility(View.GONE);
        }

        if(person.getWordpress_url()!=null){
            wordpress_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToUrl(person.getWordpress_url());
                }
            });
        }
        else {
            wordpress_icon.setVisibility(View.GONE);
        }


        if(person.getLink_url()!=null){
            linkedin_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToUrl(person.getLink_url());
                }
            });
        }
        else {
            linkedin_icon.setVisibility(View.GONE);
        }






    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
