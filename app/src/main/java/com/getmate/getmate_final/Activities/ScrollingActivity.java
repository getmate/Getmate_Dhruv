package com.getmate.getmate_final.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.getmate.getmate_final.FeedImageView;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.app.AppController;
import com.getmate.getmate_final.data.FeedItemTimeline;

import java.io.Serializable;

public class ScrollingActivity extends AppCompatActivity {

     Button goToLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //we get data here in "item" which is of type FeedItemTimeline
        Serializable serializable = getIntent().getSerializableExtra("item");
        final FeedItemTimeline item = (FeedItemTimeline)serializable;


        //AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);//to set the image in background
        //TextView email = (TextView)findViewById(R.id.email_sa);
        //email.setText(item.getEmail);



        //feed Image
        FeedImageView imageView = (FeedImageView) findViewById(R.id.image_scrl_activity);
        imageView.setImageUrl(item.getImage(),imageLoader);//check for nullable
        //

        if(item.getImage()!=null){
            imageView.setImageUrl(item.getImage(),imageLoader);
            imageView.setVisibility(View.VISIBLE);
            imageView.setResponseObserver(new FeedImageView.ResponseObserver() {
                @Override
                public void onError() {

                }

                @Override
                public void onSuccess() {

                }
            });
        }
        else {
            imageView.setVisibility(View.GONE);
        }




        //title
        TextView title = (TextView)findViewById(R.id.title_scrlng_act);
        title.setText(item.getName());

        //venue
        TextView venue = (TextView) findViewById(R.id.venue);
        venue.setText(item.getVenue());

        //Go To Link Button
        goToLink =(Button) findViewById(R.id.link_btn);
        //goToLink.setMovementMethod(LinkMovementMethod.getInstance());


        if(item.getUrl()!=null){
            //set a nice background for this
            goToLink.setText(Html.fromHtml("<a href=\""+item.getUrl()+"\">"+item.getUrl()+"</a>"));
            goToLink.setMovementMethod(LinkMovementMethod.getInstance());
            goToLink.setVisibility(View.VISIBLE);


        }



        //""""replace timestamp by date later and show timestamp only in feedtimeline not in this activity
        CharSequence timeage = DateUtils.getRelativeTimeSpanString(Long.parseLong(item.getTimeStamp()),System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);
        Button date = (Button) findViewById(R.id.time_sa);
        date.setText(timeage);

        //going_count
        Button going = (Button)findViewById(R.id.gng_cnt_b);
        going.setText((item.getGoing_count())+" "+"going");
        //set on click listener for this button


        //''''description  convert from status to description
        TextView description = (TextView)findViewById(R.id.description);
        description.setText(item.getStatus());











        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //click to save it and it should be radio button
            }
        });




       // Toast.makeText(getApplicationContext(),"you clicked"+item.getName(),Toast.LENGTH_SHORT).show();
       //log: I got data successfully transferred to this activity


    }
}
