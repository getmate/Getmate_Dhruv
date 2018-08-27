package com.getmate.getmate_final.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.getmate.getmate_final.Activities.MapsActivity;
import com.getmate.getmate_final.Activities.ScrollingActivity;
import com.getmate.getmate_final.Activities.profile;
import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.FeedImageView;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.app.AppController;
import com.getmate.getmate_final.data.FeedItemTimeline;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Dhruv on 5/27/2018.
 */

public class FeedListAdapterTimeline extends BaseAdapter{
    private Activity activity;
    private LayoutInflater layoutInflater;
    private List<FeedItemTimeline> feedItemTimelines;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    private Context context  ; //how to get context of adapter class

    Person me = new Person();
    //get from shared reference here(api_key and user_id)

        String s;
    int id;

    RequestQueue queue = AppController.getInstance().getRequestQueue();

    public FeedListAdapterTimeline(Context context){
        this.context = context;
    }






    public FeedListAdapterTimeline(Activity activity, List<FeedItemTimeline> feedItemTimelines){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("createUserData",Context.MODE_PRIVATE);
        s = sharedPreferences.getString("api_key","default key");
        id = sharedPreferences.getInt("user_id",0);
        this.activity =activity;
        this.context=activity;
        this.feedItemTimelines= feedItemTimelines;
    }


    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return feedItemTimelines.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return feedItemTimelines.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(layoutInflater==null){
            layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.feed_item_timeline,null);

        }
        if(imageLoader==null){
            imageLoader=AppController.getInstance().getImageLoader();
        }
        final FeedItemTimeline feedItemTimeline = feedItemTimelines.get(position);




        TextView name=(TextView) convertView.findViewById(R.id.name);
        name.setText(feedItemTimeline.getCreator_name());
        int creator_id=feedItemTimeline.getCreator_id();
        final String URL_GETUSER="url to get user from id";
        final JSONObject object2 = new JSONObject();
        try {
            object2.put("api_key",s);
            object2.put("creator_id",creator_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here start new intent to get to another activity displaying user's activities
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_GETUSER,object2,
                        new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //start new intent here
                        Intent i = new Intent(FeedListAdapterTimeline.this.context,profile.class );
                        Object o = jsonObject;
                        Person p = (Person)o;
                        i.putExtra("person",p);
                        FeedListAdapterTimeline.this.context.startActivity(i);
                        //parse the so received material in extras

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                queue.add(request);

            }
        });
        Button map_button = (Button) convertView.findViewById(R.id.map_btn);
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FeedListAdapterTimeline.this.context, MapsActivity.class);
                //get latitude and longitude from here and show it on map there
                Object o = getItem(position);
                FeedItemTimeline item = (FeedItemTimeline)o ;
                i.putExtra("item",item);
                FeedListAdapterTimeline.this.context.startActivity(i);



            }
        });


        Button count_button = (Button)convertView.findViewById(R.id.count_btn);
        //count_button.setText(feedItemTimeline.getGoing_count());
        count_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //done
                //PUT REQUEST to increase count by 1 in going_count column of events table
                // with row id feedItemsTimeline.getEventId()
                //and add the user_id in going column
                //with api key and the user Id
                //s(api_key)
                //id(user_id)
                //feedItemTimeline.getEventId();

                String URL_SAVE="url_for_this_put request";
                 final String URL_RECENT = "url_to_add_this_to_recent";


                JSONObject object = new JSONObject();
                try {
                    object.put("api_key",s);
                    object.put("user_id",id);
                    object.put("event_id",feedItemTimeline.getEvent_id());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final  JSONObject object1 = new JSONObject();
                try {
                    object1.put("api_key",s);
                    object1.put("user_id",id);
                    object1.put("event_id",feedItemTimeline.getEvent_id());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //request to increase the count by one and add user_id
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT, URL_SAVE, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                             //check for response here and if it is true then
                             // change the button colour and increase the visible count by one

                             //request to add this event_id in recent of event user

                                JsonObjectRequest req1= new JsonObjectRequest(Request.Method.PUT, URL_RECENT, object1,
                                        new Response.Listener<JSONObject>() {
                                            @Override
                                            public void onResponse(JSONObject jsonObject) {
                                                    //added to the recent/going activity

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {

                                    }
                                });
                                queue.add(req1);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                queue.add(req);
            }
        });


        //click to go to details activity
        Button detail_btn = (Button) convertView.findViewById(R.id.dtl_btn);
        detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object o  = getItem(position) ;
                FeedItemTimeline item = (FeedItemTimeline)o;
               // Toast.makeText(context,"you clicked"+item.getName(),Toast.LENGTH_LONG).show();
                Intent i = new Intent(FeedListAdapterTimeline.this.context, ScrollingActivity.class);
                i.putExtra("item",item);
                FeedListAdapterTimeline.this.context.startActivity(i);

            }
        });







        //add click listener for count and save items

        TextView timestamp = (TextView) convertView
                .findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.textUrl);
        NetworkImageView profilePics = (NetworkImageView) convertView.findViewById(R.id.profile_pic);
        FeedImageView feedImageView = (FeedImageView) convertView.findViewById(R.id.feedImage1);





        //converting Timestamp X into age format

        CharSequence timeage = DateUtils.getRelativeTimeSpanString(Long.parseLong(feedItemTimeline.getTimeStamp()),System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);
        timestamp.setText(timeage);

        //checking for status
        if(!TextUtils.isEmpty(feedItemTimeline.getStatus())){    //if not empty
              statusMsg.setText(feedItemTimeline.getStatus());
              statusMsg.setVisibility(View.VISIBLE);
        }
        else {
            statusMsg.setVisibility(View.GONE);
        }

        //checking for null feed URL

        if(feedItemTimeline.getUrl()!=null){
            url.setText(Html.fromHtml("<a href=\""+feedItemTimeline.getUrl()+"\">"+feedItemTimeline.getUrl()+"</a>"));

            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);

        }
        else {
            url.setVisibility(View.GONE);
        }

        profilePics.setImageUrl(feedItemTimeline.getProfilePic(),imageLoader);


        //Feed Image

        if(feedItemTimeline.getImage()!=null){
            feedImageView.setImageUrl(feedItemTimeline.getImage(),imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {

                }

                @Override
                public void onSuccess() {

                }
            });
        }
        else {
            feedImageView.setVisibility(View.GONE);
        }
        return convertView;
    }


}
