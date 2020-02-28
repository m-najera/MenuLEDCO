package com.example.miguel.menuledco;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentMan extends Fragment {
//    public static final String ARG_PLANET_NUMBER = "planet_number";

    public FragmentMan() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_menu, container, false);
//        int i = getArguments().getInt(ARG_PLANET_NUMBER);
        String title = "TITULO";
//        String planet = getResources().getStringArray(R.array.planets_array)[i];
//
//        int imageId = getResources().getIdentifier(title.toLowerCase(Locale.getDefault()),"drawable", getActivity().getPackageName());
//        ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
        getActivity().setTitle(title);

        return rootView;
    }
}
