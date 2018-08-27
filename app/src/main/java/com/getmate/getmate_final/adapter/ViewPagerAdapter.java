package com.getmate.getmate_final.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.getmate.getmate_final.ProfileFragments.AcheivementViewFragment;
import com.getmate.getmate_final.ProfileFragments.InterestViewFragment;
import com.getmate.getmate_final.ProfileFragments.RecentActivityFragment;

import java.util.ArrayList;


/**
 * Created by Dhruv on 6/24/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter  {
    ArrayList<String> interestB,interestI,interestE,acheivements;
    int[] recent = null;


    public ViewPagerAdapter(FragmentManager fm, ArrayList<String> b,ArrayList<String> i,ArrayList<String> e,
                            ArrayList<String> acheivements, int[] recent) {
        super(fm);
        this.interestB = b;
        this.interestI =i;
        this.interestE = e;
        this.acheivements =acheivements;
        this.recent = recent;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        if (position == 0)
        {
            frag = new RecentActivityFragment(recent);


        }
        else if (position == 1)
        {
            frag = new InterestViewFragment(interestB,interestI,interestE);


        }
        else if (position == 2)
        {
            frag = new AcheivementViewFragment(acheivements);

        }
        return frag;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return 3;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "Recent Activity";
        }
        else if (position == 1)
        {
            title = "Interest";
        }
        else if (position == 2)
        {
            title = "Achievement";
        }
        return title;
    }
}
