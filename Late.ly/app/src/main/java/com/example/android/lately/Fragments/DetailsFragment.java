package com.example.android.lately.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.lately.R;


public class DetailsFragment extends Fragment {

    TextView detailsTest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_details, container, false);


    }

    public void blah(){
        detailsTest = (TextView)getView().findViewById(R.id.blah);
        detailsTest.setText("Bahahahahahahah");



    }

}