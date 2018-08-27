package com.getmate.getmate_final.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.Constant;
import com.getmate.getmate_final.MainActivity;
import com.getmate.getmate_final.ParseJsonResponse;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.app.AppController;
import com.getmate.getmate_final.getLocation;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class editProfile extends AppCompatActivity  {
    EditText dateView;
    int mYear,mMonth,mDay,age;
    Long DOB;
    TextView done;
    ImageView profileImage;
    EditText name,Bio;
    EditText scl1,scl2,scl3;
    EditText work1,work2,work3;
    EditText comp1,comp2,comp3;
    EditText phoneNo,fb_url,insta_url,linkedin_url,address;
    Person me = new Person();
     ArrayList<String>  school = null;
    ArrayList<String>  work = null;
    ArrayList<String> company = null;
    ArrayList<String> interestB = new ArrayList<>();
    ArrayList<String> interestI = new ArrayList<>();
    ArrayList<String> interestE = new ArrayList<>();
    Button pickDate,interestbutton,handleButton,locationbutton;
    private static final int INTEREST_SELECTION = 0;
    private static final int UNIQUE_HANDLE =1;
     String unique_handle;
    final String URL_REGISTER_NEW_USER="some url here";
    RequestQueue queue = AppController.getInstance().getRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        findViewsById();


        String mName = getIntent().getStringExtra("name");
        String mEmail = getIntent().getStringExtra("email");
        name.setText(mName);
        //what about email?



        String format = "yyyy-MM-dd";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);//why locale .US?


        //hgetDOB here
        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                //launch date picker dialogue

                DatePickerDialog dpd = new DatePickerDialog(editProfile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar dob = Calendar.getInstance();

                        age = mYear-year;
                        dob.set(year,month,dayOfMonth);
                        DOB=dob.getTimeInMillis();
                        dateView.setText(simpleDateFormat.format(dob.getTime()));
                    }

                },mYear,mMonth,mDay);
                dpd.getDatePicker().setMaxDate(System.currentTimeMillis()); //to set Maximum date
                dpd.show();

            }
        });



        //download an image placeholder , use it and get code form simplified coding to upload an image to server
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here code for getting an image from mobile to server
                //and save th callback link in me.setImageUrl(callback);
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean error_flag = false;
                String error = "";

                if (scl1!=null){
                    school.add(scl1.getText().toString());
                }
                if(scl2!=null){
                    school.add(scl2.getText().toString());
                }
                if(scl3!=null){
                    school.add(scl3.getText().toString());
                }

                me.setSchool(school);

                if(work1!=null && comp1!=null){
                    work.add(work1.getText().toString()+" at "+comp1.getText().toString());


                }
                if(work2!=null && comp2!=null){
                    work.add(work2.getText().toString()+" at "+comp2.getText().toString());

                }
                if(work3!=null && comp3!=null){
                    work.add(work3.getText().toString()+" at "+comp3.getText().toString());

                }
                me.setWork(work);


                if(phoneNo!=null){
                    me.setPhone(Integer.parseInt(phoneNo.getText().toString()));
                }

                if(fb_url!=null){
                    me.setFb_url(fb_url.getText().toString());
                }

                if(insta_url!=null){
                    me.setInsta_url(insta_url.getText().toString());
                }
                if(linkedin_url!=null){
                    me.setLink_url(linkedin_url.getText().toString());
                }
                if(address!=null){
                    me.setAddress(address.getText().toString());
                }

               //create another function to update age every birthday

                //compulsary:name,bio,email,DOB
                if(name==null){
                    error_flag=true;
                    error="name can't be empty";
                }

                else if(Bio==null){
                    error_flag=true;
                    error="bio can't be empty";
                }
                else if(DOB==null){
                    error_flag=true;
                    error="DOB can't be empty";
                }
                me.setName(name.getText().toString());
                me.setBio(Bio.toString());
                me.setDOB(DOB);


                if(!error_flag){
                    registerNewUser();
                }
                if(error_flag){
                    Toast.makeText(getApplicationContext(),"error"+error,Toast.LENGTH_LONG).show();
                }


            }
        });


locationbutton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getLocation var = new getLocation();
        Location location1 = var.getLocation();
        if(location1!=null){
            float lat = (float)location1.getLatitude();
            float lon = (float)location1.getLongitude();
            me.setR_lat(lat);
            me.setR_lon(lon);
            me.setC_lat(lat);
            me.setC_lon(lon);
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
});




        interestbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the SecondActivity
                Intent intent = new Intent(editProfile.this, interest_selection.class);
                startActivityForResult(intent, INTEREST_SELECTION);
            }
        });

        handleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editProfile.this, interest_selection.class);
                startActivityForResult(intent, UNIQUE_HANDLE);
            }
        });

    }//on Create view ends here


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (INTEREST_SELECTION) : {
                if (resultCode == Activity.RESULT_OK) {


                   interestB = data.getStringArrayListExtra("interestB");
                    interestI = data.getStringArrayListExtra("interestI");
                    interestE = data.getStringArrayListExtra("interestE");
                    //use this data
                    me.setInterestsB(interestB);
                    me.setInterestsI(interestE);
                    me.setInterestsE(interestE);
                }
            }
            case (UNIQUE_HANDLE):{
                if(resultCode==Activity.RESULT_OK){
                    unique_handle = data.getStringExtra("handle");
                    me.setHandle(unique_handle);
                    //use this handle
                }
            }
        }
    }

    private void findViewsById() {
        done = findViewById(R.id.done_aep);
        name = findViewById(R.id.name_aep);
        dateView = findViewById(R.id.date);
        Bio = findViewById(R.id.bio_aep);
        scl1= findViewById(R.id.scl1);
        scl2= findViewById(R.id.scl2);
        scl3= findViewById(R.id.scl3);
        work1= findViewById(R.id.work1);
        work2= findViewById(R.id.work2);
        work3= findViewById(R.id.work3);
        fb_url= findViewById(R.id.fb_url_aep);
        insta_url = findViewById(R.id.insta_url_aep);
        linkedin_url= findViewById(R.id.linkedin_url_aep);
        address = findViewById(R.id.address_aep);
        phoneNo = findViewById(R.id.phone_no_aep);
        pickDate = findViewById(R.id.pick_date);
        comp1 = findViewById(R.id.company1);
        comp2 = findViewById(R.id.company2);
        comp3 = findViewById(R.id.company3);
        interestbutton = findViewById(R.id.interest_btn_aep);
        handleButton = findViewById(R.id.handle_btn_aep);
        locationbutton =findViewById(R.id.location_aep);

    }


    private void registerNewUser() {
       ParseJsonResponse p = new ParseJsonResponse();
        final JSONObject object = p.PersonToJson(me);

        JsonObjectRequest request= new JsonObjectRequest(Request.Method.POST, URL_REGISTER_NEW_USER, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                boolean error=false;
                String response="";
                //jsonObject :  {error:false,response:successFully regisstered}
                try {
                    error = jsonObject.getBoolean("error");
                    response=jsonObject.getString("response");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(error){
                    Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(),"successfully registered",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    getApplicationContext().startActivity(i);
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
                //TODO: look into here as what volley supports and what does not
                params.put("person", object.toString());
                return params;
            }
        };
        queue.add(request);
    }
}
