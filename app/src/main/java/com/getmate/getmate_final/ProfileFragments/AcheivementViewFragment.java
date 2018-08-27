package com.getmate.getmate_final.ProfileFragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.getmate.getmate_final.Class.Person;
import com.getmate.getmate_final.R;

import java.util.ArrayList;


public class AcheivementViewFragment extends Fragment {
    ArrayList<String> acheivements = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AcheivementViewFragment() {

    }
    public  AcheivementViewFragment(ArrayList<String> arrayList){
        this.acheivements = arrayList;

    }

    // TODO: Rename and change types and number of parameters
    public static AcheivementViewFragment newInstance(String param1, String param2) {
        AcheivementViewFragment fragment = new AcheivementViewFragment();
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
        View view= inflater.inflate(R.layout.fragment_acheivement_view, container, false);
        //we already have JSON object , just need to take that data and parse it
        // object me from Constant class has data for this


        ListView listView = view.findViewById(R.id.acheivement_list_view);
        //ArrayList<String> acheivements = new ArrayList();
        AchievementListAdapter acheivementListAdapter =new AchievementListAdapter(getActivity(),acheivements) ;
        listView.setAdapter(acheivementListAdapter);



    return  view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class AchievementListAdapter extends BaseAdapter{
        Activity activity;
        LayoutInflater layoutInflater;
        Context context;
        ArrayList<String> acheivements;
        String acheivement;
        public AchievementListAdapter(Activity activity , ArrayList<String> acheivements){
            this.context = activity;
            this.acheivements=acheivements;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return acheivements.size();
        }


        @Override
        public Object getItem(int position) {
            return acheivements.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (layoutInflater==null ){
                layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            }
            if (convertView==null){
                convertView = layoutInflater.inflate(R.layout.acheivement_item,null );

            }

            acheivement = acheivements.get(position);
            TextView textView = convertView.findViewById(R.id.acheivement_item);
            textView.setText(acheivement);


            return convertView;
        }
    }
}
