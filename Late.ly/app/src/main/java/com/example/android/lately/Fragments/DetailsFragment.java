package com.example.android.lately.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.lately.R;


public class DetailsFragment extends Fragment {

    TextView mDetailsTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        mDetailsTest = (TextView) getActivity().findViewById(R.id.detailsFragmentTextView);
        mDetailsTest.setText("Java works");
        return view;
    }
}