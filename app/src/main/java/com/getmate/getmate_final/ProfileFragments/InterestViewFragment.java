package com.getmate.getmate_final.ProfileFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.getmate.getmate_final.Class.ChildInterest;
import com.getmate.getmate_final.R;
import com.getmate.getmate_final.Utils.RoundedImageView;

import java.util.ArrayList;

import static com.getmate.getmate_final.Constant.ALLCHILDINTERESTS;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InterestViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InterestViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterestViewFragment extends Fragment {

    ArrayList<String> interestB,interestI,interestE;
    RecyclerView rvB,rvI,rvE;
    TextView beginner,intermediate,expert;
    //if we do all this by String arraylist then how we will get Image?

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public InterestViewFragment(){}

    public InterestViewFragment( ArrayList<String> b,ArrayList<String> i,ArrayList<String> e)
    {
        this.interestB = b;
        this.interestI =i;
        this.interestE = e;

    }


    public static InterestViewFragment newInstance(String param1, String param2) {
        InterestViewFragment fragment = new InterestViewFragment();
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

        View view= inflater.inflate(R.layout.fragment_interest_view, container, false);
        findViewsById(view);

       //final list of muInterests in ChildInterestArrayForm
        ArrayList<ChildInterest> myinterestB =new ArrayList<>();
        ArrayList<ChildInterest> myinterestI =new ArrayList<>();
        ArrayList<ChildInterest> myinterestE =new ArrayList<>();


        LinearLayoutManager BegLayoutManager = new LinearLayoutManager(getActivity());
        BegLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager IntLayoutManager = new LinearLayoutManager(getActivity());
        IntLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager ExpLayoutManager = new LinearLayoutManager(getActivity());
        ExpLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);




       for (ChildInterest dummy:ALLCHILDINTERESTS) {

            for (int i =0 ; i<interestB.size();i++) {
                if (dummy.getName().equals(interestB.get(i))) {
                    myinterestB.add(dummy);
                }
            }
            for (int i =0 ; i<interestI.size();i++) {
                if (dummy.getName().equals(interestI.get(i))) {
                    myinterestI.add(dummy);
                }
            }
            for (int i =0 ; i<interestE.size();i++) {
                if (dummy.getName().equals(interestE.get(i))) {
                    myinterestE.add(dummy);
                }
            }
        }

        //setting the adapter for recyclerView
        rvB.setAdapter(new RecycelerAdapter(myinterestB));
        rvI.setAdapter(new RecycelerAdapter(myinterestI));
        rvE.setAdapter(new RecycelerAdapter(myinterestE));
       // RecycelerAdapter adapter = new RecycelerAdapter(ALLCHILDINTERESTS);
        //rvB.setAdapter(adapter);
        //rvI.setAdapter(new RecycelerAdapter(ALLCHILDINTERESTS));
        //rvE.setAdapter(new RecycelerAdapter(ALLCHILDINTERESTS));
      //  Toast.makeText(getActivity(),ALLCHILDINTERESTS.size()+"",Toast.LENGTH_LONG).show();
        rvI.setLayoutManager(IntLayoutManager);
        rvE.setLayoutManager(ExpLayoutManager);
        rvB.setLayoutManager(BegLayoutManager);






        return view;
    }

    private void findViewsById(View view) {
    rvE=view.findViewById(R.id.rec_view_e);
        rvI =view.findViewById(R.id.rec_view_i);
        rvB =view.findViewById(R.id.rec_view_b);

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


    public class RecycelerAdapter extends  RecyclerView.Adapter<MyViewHolder>{
        public ArrayList<ChildInterest> list;

        public RecycelerAdapter(ArrayList<ChildInterest> list) {
            this.list = list;
            Log.d("recycler","recycler tak aya system");
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.interest_view_in_interestfragment,parent,false);
            MyViewHolder holder = new MyViewHolder(view);
            Log.d("recycler","createhuaview");
            return holder;
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.interestImage.setImageResource(list.get(position).getImageId());
            holder.textInerest.setText(list.get(position).getName());
            //Toast.makeText(getActivity(),list.get(position).getName()+"kya kuch dikh tha apk?",Toast.LENGTH_LONG).show();
            Log.d("recycler","obBindViewHolder");
        }


        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {


        public RoundedImageView interestImage;
        TextView textInerest ;

        public MyViewHolder(View v){

            super(v);

            interestImage = v.findViewById(R.id.image_interest);
            textInerest = v.findViewById(R.id.text_interest);
            Log.d("recycler","myViewHolder");
        }
    }
}
