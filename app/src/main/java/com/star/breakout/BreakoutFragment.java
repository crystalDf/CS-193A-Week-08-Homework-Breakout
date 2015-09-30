package com.star.breakout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BreakoutFragment extends Fragment {

    public static BreakoutFragment newInstance() {
        return new BreakoutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_breakout, container, false);

        return view;
    }

}
