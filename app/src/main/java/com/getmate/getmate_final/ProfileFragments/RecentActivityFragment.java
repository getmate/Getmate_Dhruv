package com.getmate.getmate_final.ProfileFragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.app.AppController;
import com.getmate.getmate_final.data.FeedItemTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class RecentActivityFragment extends Fragment {

    String URL_GET_RECENT_ITEMS="some url here";
    ArrayList<FeedItemTimeline> recentFeedItem = new ArrayList<>();
    RecentFeedAdapter recentFeedAdapter;
    int[] recent;// this recent is list of event_id's



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public RecentActivityFragment() {
        // Required empty public constructor
    }
    public RecentActivityFragment(int[] recent) {
       this.recent = recent;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecentActivityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecentActivityFragment newInstance(String param1, String param2) {
        RecentActivityFragment fragment = new RecentActivityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RequestQueue queue = AppController.getInstance().getRequestQueue();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recent_activity, container, false);
        //you will ne given a set of event_id's so u need to get all responses on that basis

         recentFeedAdapter = new RecentFeedAdapter(getActivity(),recentFeedItem);

         // int[] array to JSONArray()
         JSONArray array = new JSONArray(Arrays.asList(recent));

        JSONObject jsonObject= new JSONObject();
        //object with api_key and array of event'sids
        try {
            jsonObject.put("recentActivities",array);

            //jsonObject.put("api_key",api_key);
            //recentActivities:{[545,5468,56551,556123,54542,65121,551225,124512,4521221,65323],api_key = "hghgeiygf"}
        } catch (JSONException e) {
            e.printStackTrace();

        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL_GET_RECENT_ITEMS,
                jsonObject, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        //on response create a parse function
                        parseRecentFeedItems(jsonObject);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(jsonObjectRequest);




        return view;



    }

    private void parseRecentFeedItems(JSONObject response) {
        //this is same as FeedItem Timelien
        try {
            JSONArray feedArray = response.getJSONArray("feed");
            for(int i=0;i<feedArray.length();i++){
                JSONObject feedObj = (JSONObject)feedArray.get(i);

                FeedItemTimeline item = new FeedItemTimeline();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));

                //Image might be null sometimes


                String image = feedObj.isNull("image")?null:feedObj.getString("image");
                item.setImage(image);
                item.setStatus(feedObj.getString("status"));
                item.setProfilePic(feedObj.getString("profilePic"));
                item.setTimeStamp(feedObj.getString("timeStamp"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(feedUrl);

                recentFeedItem.add(item);
            }
            // notify data changes to list adapater
            recentFeedAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }



        //this is an array with responses
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class RecentFeedAdapter  extends BaseAdapter{
        Activity activity;
        Context context;
        ArrayList<FeedItemTimeline> recentFeedItems;
        LayoutInflater layoutInflater;

        public RecentFeedAdapter(Activity activity, ArrayList<FeedItemTimeline> recentFeedItems){
            this.context =activity;
            this.activity = activity;
            this.recentFeedItems = recentFeedItems;

        }

        @Override
        public int getCount() {
            return recentFeedItems.size();
        }

        @Override
        public Object getItem(int position) {
            return recentFeedItems.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(layoutInflater==null){
                layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if(convertView ==null){
                convertView=layoutInflater.inflate(R.layout.feed_item_timeline,null);
            }



            return convertView;
        }
    }
}
