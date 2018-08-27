package com.getmate.getmate_final;

/**
 * Created by Dhruv on 6/4/2018.
 */

import android.location.Location;

import com.getmate.getmate_final.Class.ChildInterest;
import com.getmate.getmate_final.Class.Person;

import java.util.ArrayList;


public class Constant {
    // public static final ArrayList<Interestchild> listOfInterest = new ArrayList<Interestchild>();
    public static final ArrayList<ChildInterest> SELECT_INTERESTS = new ArrayList<ChildInterest>();
    public static final Integer BEGINNER = 1 , INTERMEDIATE=2 , EXPERT_LEVEL = 3;
    public static final int parentNochildViewId = 0 , parenthaveChildViewId = 1 , childViewId = 2;
    public static int state = 1 , pstate = 1;
    public static  ArrayList<ChildInterest> ALLCHILDINTERESTS = new ArrayList<ChildInterest>();
    public static Person me = new Person();

    public static  Location CURRENTLOCATION = null ;

}


