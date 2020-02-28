package com.example.miguel.menuledco;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle onOperacionCliente events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public ImageView img0, img1, img2, img3, img4, img5;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.home, container, false);
        // After inflate, you can modify the layout
        img0 = view.findViewById(R.id.home_iv0);
        img1 = view.findViewById(R.id.home_iv1);
        img2 = view.findViewById(R.id.home_iv2);
        img3 = view.findViewById(R.id.home_iv3);
        img4 = view.findViewById(R.id.home_iv4);
        img5 = view.findViewById(R.id.home_iv5);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == img0.getId()) {
                    onButtonPressed(0);
                } else if (view.getId() == img1.getId()) {
                    onButtonPressed(1);
                } else if (view.getId() == img2.getId()) {
                    onButtonPressed(4);
                } else if (view.getId() == img3.getId()) {
                    onButtonPressed(5);
                } else if (view.getId() == img4.getId()) {
                    onButtonPressed(2);
                } else if (view.getId() == img5.getId()) {
                    onButtonPressed(3);
                }
            }
        };
        img0.setOnClickListener(onClickListener);
        img1.setOnClickListener(onClickListener);
        img2.setOnClickListener(onClickListener);
        img3.setOnClickListener(onClickListener);
        img4.setOnClickListener(onClickListener);
        img5.setOnClickListener(onClickListener);

        Picasso.get().load("https://apod.nasa.gov/apod/image/1602/PinnaclesGalaxy_Goh_2400.jpg").into(img0);
        /*Picasso.get().load("https://apod.nasa.gov/apod/image/1602/PinnaclesGalaxy_Goh_2400.jpg").
                centerCrop(Gravity.END).
                resize(400, 400).
                into(img0);*/

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int i) {
        if (mListener != null) {
            mListener.onHomeFragmentInteraction(i);
        }
    }

    @Override
    public void onAttach(Activity context) {
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
     * fragment to allow an onOperacionCliente in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onHomeFragmentInteraction(int i);
    }
}
