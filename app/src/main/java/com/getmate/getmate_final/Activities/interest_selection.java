package com.getmate.getmate_final.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.getmate.getmate_final.Class.ChildInterest;
import com.getmate.getmate_final.Class.ParentInterest;
import com.getmate.getmate_final.MainActivity;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.adapter.InterestAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import static com.getmate.getmate_final.Constant.ALLCHILDINTERESTS;


public class interest_selection extends AppCompatActivity {


    ExpandableListView expandableListView;
    InterestAdapter interestAdapter;
    private int lastExpandedPos =-1;
    ArrayList<ParentInterest> allInterestList;
    HashMap<String,ArrayList<String>> data = new HashMap<>();
    ArrayList<ChildInterest> allchildinterests = new ArrayList<>();
    int isFromeditEvent=0;

    //getIntentData here



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
         isFromeditEvent = getIntent().getIntExtra("fromEditEvent",0);
        setContentView(R.layout.activity_interest_selection);
        expandableListView = (ExpandableListView)findViewById(R.id.exp_listView);
        allInterestList = getData();
        interestAdapter = new InterestAdapter(this,allInterestList);
        expandableListView.setAdapter(interestAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(lastExpandedPos!=-1&&lastExpandedPos!=groupPosition){
                    expandableListView.collapseGroup(groupPosition);
                }
                lastExpandedPos = groupPosition;
            }
        });//look here for bug occures during expansion

        Button button = (Button)findViewById(R.id.button_is);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data = interestAdapter.getData(); //this data is selected interests
                ALLCHILDINTERESTS = allchildinterests;
                //data is hashmap and retrive and send and also get a Toast from it to check working
                ArrayList<String> ib ;
                ArrayList<String> ii ;
                ArrayList<String> ie ;
                ib = data.get("interestB");
                ii = data.get("interestI");
                ie = data.get("interestE");

                if(isFromeditEvent==1){
                    isFromeditEvent =0;//set flag again to zero
                    Intent intent = new Intent();
                    Bundle interestBundle = new Bundle();
                    interestBundle.putStringArrayList("interestB",ib);
                    interestBundle.putStringArrayList("interestI",ii);
                    interestBundle.putStringArrayList("interestE",ie);
                    intent.putExtra("interestBundle",interestBundle);
                    setResult(RESULT_OK,intent);
                    finish();
                }

                Intent i = new Intent(interest_selection.this, MainActivity.class);//to editProfile again
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("interestB",ib);
                bundle.putStringArrayList("interestI",ii);
                bundle.putStringArrayList("interestE",ie);
                i.putExtra("interestS",bundle);

                interest_selection.this.startActivity(i);

                //TODO just for now,later change it to this form once data exchange starts
                /*
                Intent intent = new Intent();
                intent.putStringArrayListExtra("interestB",ib);
                intent.putStringArrayListExtra("interestI",ii);
                intent.putStringArrayListExtra("interestE",ie);
                setResult(RESULT_OK,intent);
                finish();
                */


            }
        });


    }

    public ArrayList<ParentInterest> getData() {

        ParentInterest it1 = new ParentInterest("Sports and Fitness",false,R.mipmap.sports);

        it1.childInterests.add(new ChildInterest("Athletics",false, R.mipmap.athletics));
        it1.childInterests.add(new ChildInterest("Badminton",false,R.mipmap.badminton));
        it1.childInterests.add(new ChildInterest("BasketBall",false,R.mipmap.basktetball));
        it1.childInterests.add(new ChildInterest("Board Skating",false,R.mipmap.boarding));
        it1.childInterests.add(new ChildInterest("Boxing",false,R.mipmap.boxing));
        it1.childInterests.add(new ChildInterest("Circuit Training",false,R.mipmap.circuit));
        it1.childInterests.add(new ChildInterest("Cricket",false,R.mipmap.cricket));
        it1.childInterests.add(new ChildInterest("Cycling",false,R.mipmap.cycling));
        it1.childInterests.add(new ChildInterest("Fencing",false,R.mipmap.fencing));
        it1.childInterests.add(new ChildInterest("Football",false,R.mipmap.football));
        it1.childInterests.add(new ChildInterest("Frisbee",false,R.mipmap.frisbee));
        it1.childInterests.add(new ChildInterest("Golf",false,R.mipmap.golf));
        it1.childInterests.add(new ChildInterest("Handball",false,R.mipmap.handball));
        it1.childInterests.add(new ChildInterest("Hockey",false,R.mipmap.hockey));
        it1.childInterests.add(new ChildInterest("Horse Riding",false,R.mipmap.horseriding));
        it1.childInterests.add(new ChildInterest("Judo",false,R.mipmap.judo));
        it1.childInterests.add(new ChildInterest("Kabaddi",false,R.mipmap.kabaddi));
        it1.childInterests.add(new ChildInterest("Kayating",false,R.mipmap.kayating));
        it1.childInterests.add(new ChildInterest("Kho-Kho",false,R.mipmap.kho));
        it1.childInterests.add(new ChildInterest("Martial Arts",false,R.mipmap.martialarts));
        it1.childInterests.add(new ChildInterest("Mountain Biking",false,R.mipmap.mb));
        it1.childInterests.add(new ChildInterest("Polo",false,R.mipmap.polo));
        it1.childInterests.add(new ChildInterest("Rugby",false,R.mipmap.rugby));
        it1.childInterests.add(new ChildInterest("Running",false,R.mipmap.running));
        it1.childInterests.add(new ChildInterest("Scuba Diving",false,R.mipmap.scuba));
        it1.childInterests.add(new ChildInterest("Surfing",false,R.mipmap.surfing));
        it1.childInterests.add(new ChildInterest("Swimming",false,R.mipmap.swim));
        it1.childInterests.add(new ChildInterest("Table Tennis",false,R.mipmap.tt));
        it1.childInterests.add(new ChildInterest("Tennis",false,R.mipmap.tennis));
        it1.childInterests.add(new ChildInterest("Trekking",false,R.mipmap.trek));
        it1.childInterests.add(new ChildInterest("Trempoline",false,R.mipmap.trem));
        it1.childInterests.add(new ChildInterest("Volleyball",false,R.mipmap.volley));
        it1.childInterests.add(new ChildInterest("Water Polo",false,R.mipmap.waterpolo));
        it1.childInterests.add(new ChildInterest("Weight Lifting",false,R.mipmap.wl));
        it1.childInterests.add(new ChildInterest("Wrestling",false,R.mipmap.wre));
        it1.childInterests.add(new ChildInterest("Zumba",false,R.mipmap.zumba));
        allchildinterests.addAll(it1.getChildInterests());



        ParentInterest it2 = new ParentInterest("Education",false,R.mipmap.edu);
        it2.childInterests.add(new ChildInterest("Arts and Science",false,R.mipmap.art));
        it2.childInterests.add(new ChildInterest("Commerce",false,R.mipmap.edu));
        it2.childInterests.add(new ChildInterest("Medical",false,R.mipmap.medical));
        it2.childInterests.add(new ChildInterest("Agriculture",false,R.mipmap.edu));
        it2.childInterests.add(new ChildInterest("Physical Education",false,R.mipmap.edu));
        it2.childInterests.add(new ChildInterest("Aerospace",false,R.mipmap.aero));
        it2.childInterests.add(new ChildInterest("Astronomy",false,R.mipmap.astro));
        it2.childInterests.add(new ChildInterest("Biotechnology",false,R.mipmap.biot));
        it2.childInterests.add(new ChildInterest("Chemical",false,R.mipmap.chem));
        it2.childInterests.add(new ChildInterest("Civil",false,R.mipmap.civil));
        it2.childInterests.add(new ChildInterest("Computer Science",false,R.mipmap.cs));
        it2.childInterests.add(new ChildInterest("Economics",false,R.mipmap.econ));
        it2.childInterests.add(new ChildInterest("History",false,R.mipmap.history));
        it2.childInterests.add(new ChildInterest("Mechatronics",false,R.mipmap.mechatronics));
        it2.childInterests.add(new ChildInterest("Ocean",false,R.mipmap.ocean));
        it2.childInterests.add(new ChildInterest("Political Science",false,R.mipmap.pt));
        it2.childInterests.add(new ChildInterest("Mathematics",false,R.mipmap.edu));
        it2.childInterests.add(new ChildInterest("Physics",false,R.mipmap.edu));
        it2.childInterests.add(new ChildInterest("Chemistry",false,R.mipmap.edu));
        it2.childInterests.add(new ChildInterest("Electrical",false,R.mipmap.edu));
        it2.childInterests.add(new ChildInterest("Geography",false,R.mipmap.geo));
        it2.childInterests.add(new ChildInterest("3D Designing",false,R.mipmap.threeddesign));
        allchildinterests.addAll(it2.getChildInterests());



        ParentInterest it3 = new ParentInterest("Dance",false,R.mipmap.dance);
        it3.childInterests.add(new ChildInterest("Bharatanatyam",false,R.mipmap.bharatnatyam));
        it3.childInterests.add(new ChildInterest("Kathakali",false,R.mipmap.kathakali));
        it3.childInterests.add(new ChildInterest("Kathak",false,R.mipmap.kathak));
        it3.childInterests.add(new ChildInterest("Kuchipudi",false,R.mipmap.kuchi));
        it3.childInterests.add(new ChildInterest("Odissi",false,R.mipmap.odisi));
        it3.childInterests.add(new ChildInterest("Sattriya",false,R.mipmap.satt));
        it3.childInterests.add(new ChildInterest("Manipuri",false,R.mipmap.mani));
        it3.childInterests.add(new ChildInterest("Mohiniyattam",false,R.mipmap.mohini));
        it3.childInterests.add(new ChildInterest("Ballet",false,R.mipmap.ballet));
        it3.childInterests.add(new ChildInterest("Hip-Hop",false,R.mipmap.hiphop));
        it3.childInterests.add(new ChildInterest("Tap Dance",false,R.mipmap.dance));
        it3.childInterests.add(new ChildInterest("Belly Dance",false,R.mipmap.belly));
        it3.childInterests.add(new ChildInterest("Salsa",false,R.mipmap.salsa));
        it3.childInterests.add(new ChildInterest("Zumba",false,R.mipmap.zumba));
        allchildinterests.addAll(it3.getChildInterests());

        ParentInterest it4 = new ParentInterest("Music",false,R.mipmap.music);
        it4.childInterests.add(new ChildInterest("Mandolin",false,R.mipmap.mandolin));
        it4.childInterests.add(new ChildInterest("Violin",false,R.mipmap.violin));
        it4.childInterests.add(new ChildInterest("Veena",false,R.mipmap.veena));
        it4.childInterests.add(new ChildInterest("Tanpura",false,R.mipmap.tanpura));
        it4.childInterests.add(new ChildInterest("Santoor",false,R.mipmap.santoor));
        it4.childInterests.add(new ChildInterest("Accordion",false,R.mipmap.acc));
        it4.childInterests.add(new ChildInterest("Bassoon",false,R.mipmap.bassoon));
        it4.childInterests.add(new ChildInterest("Cajon",false,R.mipmap.cajon));
        it4.childInterests.add(new ChildInterest("Cello",false,R.mipmap.cello));
        it4.childInterests.add(new ChildInterest("Clarinet",false,R.mipmap.clarinet));
        it4.childInterests.add(new ChildInterest("Double bass",false,R.mipmap.doublebass));
        it4.childInterests.add(new ChildInterest("Drum",false,R.mipmap.drum));
        it4.childInterests.add(new ChildInterest("Flute",false,R.mipmap.flute));
        it4.childInterests.add(new ChildInterest("Guitar",false,R.mipmap.guitar));
        it4.childInterests.add(new ChildInterest("Harmonium",false,R.mipmap.harmonium));
        it4.childInterests.add(new ChildInterest("Piano",false,R.mipmap.piano));
        it4.childInterests.add(new ChildInterest("Saxophone",false,R.mipmap.saxophone));
        it4.childInterests.add(new ChildInterest("Sitar",false,R.mipmap.sitar));
        it4.childInterests.add(new ChildInterest("Tabla",false,R.mipmap.tabla));
        it4.childInterests.add(new ChildInterest("Trombone",false,R.mipmap.trombone));
        it4.childInterests.add(new ChildInterest("Trumet",false,R.mipmap.trumpet));
        it4.childInterests.add(new ChildInterest("Ukulele",false,R.mipmap.ukulele));
        it4.childInterests.add(new ChildInterest("Vocal",false,R.mipmap.vocal));
        it4.childInterests.add(new ChildInterest("Xylophone",false,R.mipmap.xylo));

        allchildinterests.addAll(it4.getChildInterests());
        ParentInterest it5 = new ParentInterest("Film making",false,R.mipmap.film);
        it5.childInterests.add(new ChildInterest("Animator",false,R.mipmap.film));
        it5.childInterests.add(new ChildInterest("Screen Writing",false,R.mipmap.film));
        it5.childInterests.add(new ChildInterest("Stunt and coordination",false,R.mipmap.film));
        it5.childInterests.add(new ChildInterest("Costume designing",false,R.mipmap.film));
        it5.childInterests.add(new ChildInterest("Make Up",false,R.mipmap.film));
        it5.childInterests.add(new ChildInterest("Video editing",false,R.mipmap.film));
        allchildinterests.addAll(it5.getChildInterests());


        ParentInterest it6 = new ParentInterest("Art & Craft",false,R.mipmap.art);
        it6.childInterests.add(new ChildInterest("Spray Painting",false,R.mipmap.spray));
        it6.childInterests.add(new ChildInterest("Sculpture Making",false,R.mipmap.sculpture));
        it6.childInterests.add(new ChildInterest("Sand Art",false,R.mipmap.sand));
        it6.childInterests.add(new ChildInterest("Origami",false,R.mipmap.origami));
        it6.childInterests.add(new ChildInterest("Interior Designing",false,R.mipmap.interiordesign));
        it6.childInterests.add(new ChildInterest("Clay Art",false,R.mipmap.clay));
        it6.childInterests.add(new ChildInterest("Calligraphy",false,R.mipmap.calligraphy));
        it6.childInterests.add(new ChildInterest("Sewing",false,R.mipmap.art));
        it6.childInterests.add(new ChildInterest("Painting",false,R.mipmap.painting));
        it6.childInterests.add(new ChildInterest("Sketching",false,R.mipmap.sketching));
        it6.childInterests.add(new ChildInterest("Knitting",false,R.mipmap.knitting));
        it6.childInterests.add(new ChildInterest("Wall Art",false,R.mipmap.art));

        allchildinterests.addAll(it6.getChildInterests());
        ParentInterest it7 = new ParentInterest("Meditation & Yoga",false,R.mipmap.meditation);
        ParentInterest it8 = new ParentInterest("Photography & Cinematography",false,R.mipmap.photography);
        ParentInterest it9 = new ParentInterest("Language and Culture",false,R.mipmap.culture);
        ParentInterest it10 = new ParentInterest("Cooking",false,R.mipmap.cooking);
        ParentInterest it11 = new ParentInterest("Reading",false,R.mipmap.reading);
        ParentInterest it12 = new ParentInterest("Pets & Wildlife",false,R.mipmap.pets);
        ParentInterest it13 = new ParentInterest("Fashion & Beauty",false,R.mipmap.fashion);
        ParentInterest it14 = new ParentInterest("Volunteering and Survey",false,R.mipmap.voluneering);
        ParentInterest it15 = new ParentInterest("Social Work",false,R.mipmap.social);
        ParentInterest it16 = new ParentInterest("Carrer and Bussiness",false,R.mipmap.business);

        ParentInterest it17 = new ParentInterest("Tech",false,R.mipmap.tech);
        it17.childInterests.add(new ChildInterest("IT",false,R.mipmap.itn));
        it17.childInterests.add(new ChildInterest("Microcontroller & Microprocessor",false,R.mipmap.microc));
        it17.childInterests.add(new ChildInterest("Softwares",false,R.mipmap.softwares));
        it17.childInterests.add(new ChildInterest("Programming Languages",false,R.mipmap.cs));
        allchildinterests.addAll(it7.getChildInterests());
        allchildinterests.addAll(it8.getChildInterests());
        allchildinterests.addAll(it9.getChildInterests());
        allchildinterests.addAll(it10.getChildInterests());
        allchildinterests.addAll(it11.getChildInterests());
        allchildinterests.addAll(it12.getChildInterests());
        allchildinterests.addAll(it13.getChildInterests());
        allchildinterests.addAll(it14.getChildInterests());
        allchildinterests.addAll(it15.getChildInterests());
        allchildinterests.addAll(it16.getChildInterests());

        ArrayList<ParentInterest> allList = new ArrayList<ParentInterest>();
        allList.add(it1);
        allList.add(it2);
        allList.add(it3);
        allList.add(it4);
        allList.add(it5);
        allList.add(it6);
        allList.add(it17);


        allList.add(it16);
        allList.add(it10);
        allList.add(it13);
        allList.add(it9);
        allList.add(it7);
        allList.add(it12);
        allList.add(it8);
        allList.add(it11);
        allList.add(it15);
        allList.add(it14);




        return allList;
    }
}
