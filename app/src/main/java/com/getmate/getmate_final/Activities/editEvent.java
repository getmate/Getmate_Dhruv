package com.getmate.getmate_final.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;

import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.Class.event;
import com.getmate.getmate_final.Constant;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.getLocation;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
//make this as same as Scrolling Activiy and add click listener to evert postion use as same as tinder for better look

public class editEvent extends AppCompatActivity implements View.OnClickListener {
    private static final int MAP_ACTIVITY = 2;
    Person me = Constant.me;
    final int INTEREST_SELECTION =1;



    event newEvent =  new event(); //get an instance of this class
    ArrayList<String> interestB = new ArrayList<>();
    ArrayList<String> interestI = new ArrayList<>();
    ArrayList<String> interestE = new ArrayList<>();


    EditText title_et,venue_et,link_et,description_et;
    RadioButton email_rb,phone_rb,fb_rb,linkedin_rb;
    Button map_button,interest_button, date_button,done,time_button;
    int mYear,mMonth,mDay,mHour,mMinute;
    Long date;
    Calendar d_and_t = Calendar.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //create an instance of event class

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewsById();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void findViewsById() {
        title_et = findViewById(R.id.title_ee);
        venue_et = findViewById(R.id.venue_ee);
        link_et = findViewById(R.id.link_ee);
        description_et= findViewById(R.id.description_ee);
        email_rb = findViewById(R.id.email_rb_ee);
        phone_rb =findViewById(R.id.phone_rb_ee);
        fb_rb = findViewById(R.id.fb_rb_ee);
        linkedin_rb=findViewById(R.id.linkedin_rb_ee);
        map_button = findViewById(R.id.show_on_map_ee);
        date_button=findViewById(R.id.date_ee);
        time_button = findViewById(R.id.time_ee);
        interest_button=findViewById(R.id.interest_ee);
        done=findViewById(R.id.done_ee);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        boolean flag = false;


        switch (v.getId()){
            case R.id.done_ee:{
                String title = title_et.getText().toString();
                String description = description_et.getText().toString();
               // String image_url = image_url_et.getText().toString();
                //date is already set in previous dialog

                int creator_uid = me.getUser_id();
                int going_count = 1;
                String venue = venue_et.getText().toString();
                String profilePic;
                String creator_name = me.getName();

                float lat, lan;
                String url = link_et.getText().toString();


            }
            case R.id.email_rb_ee:{
                if(email_rb.isChecked()){
                    newEvent.setEmail(me.getEmail());
                }
                else{  //do nothing here
                    newEvent.setEmail(null);

                }


            }
            case R.id.fb_rb_ee:{
                if(fb_rb.isChecked()){
                    //add email to the created instance of email

                }
                else{  //do nothing here

                }



            }
            case R.id.phone_rb_ee:{
                if(phone_rb.isChecked()){
                    //add email to the created instance of email
                }
                else{  //do nothing here

                }


            }
            case R.id.linkedin_rb_ee:{
                if(phone_rb.isChecked()){
                    //add email to the created instance of email
                }
                else{  //remove this item from list

                }



            }
            case R.id.show_on_map_ee:{

                Intent i = new Intent(getApplicationContext(),MapsActivity.class);

                startActivityForResult(i,MAP_ACTIVITY);
            }
            case R.id.interest_ee:{

                Intent intent = new Intent(editEvent.this, interest_selection.class);
                intent.putExtra("fromEditEvent",1);
                startActivityForResult(intent, INTEREST_SELECTION);


            }



            case R.id.date_ee:{
                //create dialogue box for calendar


                    Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    //launch date picker dialogue

                    DatePickerDialog dpd = new DatePickerDialog(editEvent.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                            d_and_t.set(year,month,dayOfMonth);
                            date=d_and_t.getTimeInMillis();
                            newEvent.setDate(date);
                        }

                    },mYear,mMonth,mDay);
                    dpd.getDatePicker().setMinDate(System.currentTimeMillis());
                    dpd.show();

                }



            case R.id.time_ee:{
                //get current time
                 Calendar c = Calendar.getInstance();
                mHour =c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                //launch Time Picker Dialogue

                TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar newC = Calendar.getInstance();
                        newC.set(mYear,mMonth,mDay,hourOfDay,minute);
                        d_and_t.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        d_and_t.set(Calendar.MINUTE,minute);
                        date = d_and_t.getTimeInMillis();
                        newEvent.setDate(date);
                        //this final date is the timestamp of event creation


                        /*   **** TO CONVERT FROM MILLISECOND TO DATE*****
                        * long yourmilliseconds = 1322018752992l;
                        //1322018752992-Nov 22, 2011 9:25:52 PM

                         SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss,SSS",Locale.US);

                            GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
                        calendar.setTimeInMillis(yourmilliseconds);

                        System.out.println("GregorianCalendar -"+sdf.format(calendar.getTime()));
                            */
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (INTEREST_SELECTION) : {
                if (resultCode == Activity.RESULT_OK) {
                    JSONArray tags = new JSONArray();



                    interestB = data.getStringArrayListExtra("interestB");
                    interestI = data.getStringArrayListExtra("interestI");
                    interestE = data.getStringArrayListExtra("interestE");
                    //use this data
                    for (String tag:interestB) {
                        tags.put(tag);
                    }
                    for (String tag:interestI) {
                        tags.put(tag);
                    }
                    for (String tag:interestE) {
                        tags.put(tag);
                    }
                    newEvent.setTags(tags);

                }
            }
            case (MAP_ACTIVITY):{
                if(resultCode ==Activity.RESULT_OK){

                    getLocation var = new getLocation();
                    Location location1 = var.getLocation();
                    if(location1!=null){
                        float lat = (float)location1.getLatitude();
                        float lon = (float)location1.getLongitude();
                       newEvent.setLat(lat);
                       newEvent.setLon(lon);
                    }
                    else { //save null by default or ask for location at the very starting of an app
                        //this is not going to work at all
                        Location location = Constant.CURRENTLOCATION;
                        float lat = (float) location.getLatitude();
                        float lon = (float) location.getLongitude();
                        me.setR_lat(lat);
                        me.setR_lon(lon);
                    }
                }
            }

        }
    }



}

