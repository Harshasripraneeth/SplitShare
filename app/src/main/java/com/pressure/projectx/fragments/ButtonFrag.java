package com.pressure.projectx.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pressure.projectx.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonFrag extends Fragment {


    public ButtonFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttonfrag, container, false);
    }

}
