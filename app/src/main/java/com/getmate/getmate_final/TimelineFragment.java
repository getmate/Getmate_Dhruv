package com.getmate.getmate_final;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Cache.Entry;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getmate.getmate_final.adapter.FeedListAdapterTimeline;
import com.getmate.getmate_final.app.AppController;
import com.getmate.getmate_final.data.FeedItemTimeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TimelineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {

    private static final String TAG = TimelineFragment.class.getSimpleName();
    private ListView listView;
    private FeedListAdapterTimeline feedListAdapterTimeline;
    private List<FeedItemTimeline> feedItems;
    boolean flag_loading = false; // a flag says whether we are loading data?
    int user_id;
    String api_key;
    int a = 0;


    private String URL_FEED = "https://api.androidhive.info/feed/feed.json";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_timeline, container, false);


          listView= (ListView) view.findViewById(R.id.frag1_list);
          feedItems = new ArrayList<FeedItemTimeline>();
          feedListAdapterTimeline=new FeedListAdapterTimeline(this.getActivity(),feedItems);
          listView.setAdapter(feedListAdapterTimeline);

        Context context = this.getActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserLoginData",Context.MODE_PRIVATE);
        api_key = sharedPreferences.getString("api_key",null);
        user_id = sharedPreferences.getInt("user_id",0);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0)
                {
                    if(flag_loading == false)
                    {
                        flag_loading = true;
                        a++;
                        //additems(); a function to load another 20 items
                        getTimeline(user_id,api_key,a);
                    }
                }

            }
        });

        if(entry!=null){
            //fetching existing data from cache
            try {
                String data= new String(entry.data,"UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {

            getTimeline(user_id,api_key,a);


            //TODO:this is impotrant part



            //making fresh JSON request and getting  json
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_FEED,
                    null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    VolleyLog.d(TAG, "Response: " + jsonObject.toString());
                    if (jsonObject != null) {
                        //whether response is recieved or not
                        parseJsonFeed(jsonObject);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    VolleyLog.d(TAG,"Error"+volleyError.getMessage());
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", "Droider");
                    return params;
                }

            };

            AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        }



return view;
    }

    private void parseJsonFeed(JSONObject response){
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

                feedItems.add(item);
            }
            // notify data changes to list adapater
            feedListAdapterTimeline.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }


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

    public void getTimeline(final int user_id,final String api_key,final int a){

        //make JSON OBJECT to get timeline using user_id

        JsonObjectRequest getTimeline = new JsonObjectRequest(Request.Method.GET, URL_FEED, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                    //parse and use this response



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


            }
        }){

            @Override
            protected Map<String, String> getParams()  {
                Map<String, String > params = new HashMap<String, String>();
                params.put("user_id",String.valueOf(user_id));
                params.put("lat",String.valueOf(Constant.CURRENTLOCATION.getLatitude()));
                params.put("lon",String.valueOf(Constant.CURRENTLOCATION.getLongitude()));
                params.put("step",String.valueOf(a)); //This a will say us whether more data be neede to load
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("apiKey", api_key);
                return headers;
            }

        };
    }
}
