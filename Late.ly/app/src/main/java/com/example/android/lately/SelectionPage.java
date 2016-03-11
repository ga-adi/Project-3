package com.example.android.lately;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

/**
 * Created by perrycooperman on 3/9/16.
 */
public class SelectionPage extends AppCompatActivity{

    CardView vPolitics;
    CardView vTech;
    CardView vCareer;
    CardView vFood;
    CardView vDancing;
    CardView vAnimals;
    CardView vLanguage;
    CardView vNature;
    CardView vSports;
    CardView vDating;
    CardView vSubmit;
    boolean mPolitics = false;
    boolean mTech = false;
    boolean mCareer = false;
    boolean mFood = false;
    boolean mDancing = false;
    boolean mAnimals = false;
    boolean mLanguage = false;
    boolean mNature = false;
    boolean mSports = false;
    boolean mDating = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection_page);


        vPolitics = (CardView) findViewById(R.id.selectionpage_politics);
        vTech = (CardView) findViewById(R.id.selectionpage_tech);
        vCareer = (CardView) findViewById(R.id.selectionpage_career);
        vFood = (CardView) findViewById(R.id.selectionpage_food);
        vDancing = (CardView) findViewById(R.id.selectionpage_dancing);
        vAnimals = (CardView) findViewById(R.id.selectionpage_animals);
        vLanguage = (CardView) findViewById(R.id.selectionpage_language);
        vNature = (CardView) findViewById(R.id.selectionpage_nature);
        vSports = (CardView) findViewById(R.id.selectionpage_sports);
        vDating = (CardView) findViewById(R.id.selectionpage_dating);
        vSubmit = (CardView) findViewById(R.id.selectionpage_submit);



        vPolitics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPolitics){
                    mPolitics = false;
                    vPolitics.setCardBackgroundColor(Color.GRAY);
                }else{
                    mPolitics = true;
                    vPolitics.setCardBackgroundColor(Color.WHITE);
                }
            }
        });

        vTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTech){
                    mTech = false;
                    vTech.setCardBackgroundColor(Color.GRAY);
                }else{
                    mTech = true;
                    vTech.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCareer){
                    mCareer = false;
                    vCareer.setCardBackgroundColor(Color.GRAY);
                }else{
                    mCareer = true;
                    vCareer.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFood){
                    mFood = false;
                    vFood.setCardBackgroundColor(Color.GRAY);
                }else{
                    mFood = true;
                    vFood.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vDancing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDancing){
                    mDancing = false;
                    vDancing.setCardBackgroundColor(Color.GRAY);
                }else{
                    mDancing = true;
                    vDancing.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnimals){
                    mAnimals = false;
                    vAnimals.setCardBackgroundColor(Color.GRAY);
                }else{
                    mAnimals = true;
                    vAnimals.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage){
                    mLanguage = false;
                    vLanguage.setCardBackgroundColor(Color.GRAY);
                }else{
                    mLanguage = true;
                    vLanguage.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNature){
                    mNature = false;
                    vNature.setCardBackgroundColor(Color.GRAY);
                }else{
                    mNature = true;
                    vNature.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSports){
                    mSports = false;
                    vSports.setCardBackgroundColor(Color.GRAY);
                }else{
                    mSports = true;
                    vSports.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vDating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDating){
                    mDating = false;
                    vDating.setCardBackgroundColor(Color.GRAY);
                }else{
                    mDating = true;
                    vDating.setCardBackgroundColor(Color.WHITE);
                }

            }
        });

        vSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}