package com.getmate.getmate_final.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.Utils.RoundedImageView;
import com.getmate.getmate_final.adapter.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

//this is profile view when someone else see our profile
public class profile extends AppCompatActivity  {
    Person person = new Person();

     TextView age,gender,no_connections,school,work,name,handle,email,bio,city;
     RoundedImageView profilePic;
     ImageView coverPic,fb_icon,insta_icon,linkedin_icon,wordpress_icon,www;

    String contact_details; //from JSONObject toString();

    ArrayList<String> acheivemets;
    ArrayList<String> interestsB;
    ArrayList<String> interestsI;
    ArrayList<String> interestsE;

    String fb_url;
    String insta_url;
    String link_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //if it is coming form an intent

        Serializable serializable = getIntent().getSerializableExtra("person");
        final Person person1 = (Person) serializable;
        Object o = person1;
        JSONObject jsonPerson = (JSONObject)o;
        parseJsonProfileResponse(jsonPerson);
        /* DEMO JSON PROFILE DATA
        {
name:Dhruv Gajwa,

handle:Random_Nerd,
profilePic:"some_url",
coverPic:"some_url",
email:dhruvg.dakshana16@gmail.com
bio:My life is dull an I don't know what to do so I am doing nothing at all,

user_id:52522,
range:40,
c_lat:41.22222,
c_lon:25.55555,
r_lat:41.2252,
r_lon:25.5252,
school:"Deepti COnvent School"
city:chennai
work:"software developer at Cognizant"
email:dhurvg.dakshana16@gmail.com,
fb_url:www.facebook.com/dhruv.gajwa,
insta_url:www.instagram.com/random___nerd,
linkedin_url:www.linkedin.com/dhruv.gajwa
interestB:[php,MySQL,machine learning,calligraphy],
interestI:[Swimming,Running,Cycling,Android,],
interestE:[nothing],
gender:1,
connections[]:{454,121,54545,78454,5545,8546,4645,46464,8878,54126,7584545,4454754,45545,45544},
recentActivities[]:{545,5468,56551,556123,54542,65121,551225,124512,4521221,65323},
savedItems[]:{55454,568,6898,98556,985644,5556565,5556555,4545245,155455,455455,12232,5542325,23652}, //do net send on profile request

acheivements[]:{"killed a russel viper","touched 14 ft underwater","cycled 100 km in 4 hours"},
}
*/
findViewsById();
setToViews();


    }

    private void findViewsById() {
        name = findViewById(R.id.tvName);
        handle = findViewById(R.id.handle);
       // TextView age,school,work,name,handle,email,bio,city;
        //RoundedImageView profilePic;
        //ImageView coverPic,fb_icon,insta_icon,linkedin_icon,blogger_icon,www;
        work = findViewById(R.id.tvTitle);
        age = findViewById(R.id.tvAge);
        school = findViewById(R.id.tvEducation);
        email = findViewById(R.id.tvEmail);
        city = findViewById(R.id.tvAddress);
        fb_icon  = findViewById(R.id.fb_icon);
        insta_icon = findViewById(R.id.instagram_icon);
        linkedin_icon = findViewById(R.id.linkedin_icon);
        wordpress_icon = findViewById(R.id.wordpress_icon);

    }

    private void setToViews() {
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



        //TabLayout View here
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Recent Activity"));
        tabLayout.addTab(tabLayout.newTab().setText("Interest"));
        tabLayout.addTab(tabLayout.newTab().setText("Achievement"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //ViewPager and other structure here
         ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
         ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager(),person.getInterestsB(),
                 person.getInterestsI(),person.getInterestsE(),person.getAcheivemets(),person.getRecentActivities());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);



    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void parseJsonProfileResponse(JSONObject response){

        try {
            person.setName(response.getString("name"));
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
            ArrayList<String> school1= new ArrayList<>();

            for (int i =0;i<array2.length();i++){

                school1.add(array2.getString(i));
            }
            person.setSchool(school1);


            //Code to fetch work details
            JSONArray array3 = response.getJSONArray("work");
            ArrayList<String> work1 = new ArrayList<>();
            for (int j =0; j<array3.length();j++){
                String work = array3.getString(j);
                work1.add(work);
            }
            person.setWork(work1);
            person.setEmail(response.getString("email"));
            person.setPhone(response.getLong("phone"));
            person.setAddress(response.getString("address"));
            person.setFb_url(response.getString("fb_url"));
            person.setInsta_url(response.getString("insta_url"));
            person.setLink_url(response.getString("link_url"));
            person.setWordpress_url(response.getString("wordpress_url"));

            //fetching interests
            JSONArray interestB = response.getJSONArray("interestsB");
             ArrayList<String> ib = new ArrayList<>();
            for (int k = 0;k<interestB.length();k++){
                ib.add(interestB.optString(k));
            }
            person.setInterestsB(ib);

            ArrayList<String> ii = new ArrayList<>();
            JSONArray interestI = response.getJSONArray("interestsI");

            for (int k = 0;k<interestI.length();k++){
                ii.add(interestI.optString(k));
            }

            person.setInterestsI(ii);

            ArrayList<String> ie = new ArrayList<>();
            JSONArray interestE = response.getJSONArray("interestsE");

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

            Long  DOB = response.getLong("DOB");
            person.setDOB(DOB);

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
}

