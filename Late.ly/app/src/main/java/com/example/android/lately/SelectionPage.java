package com.example.android.lately;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by perrycooperman on 3/9/16.
 */
public class SelectionPage extends AppCompatActivity {

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
    ArrayList<String> mSelections;

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
                if (mPolitics) {
                    mPolitics = false;
                    vPolitics.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mPolitics = true;
                    vPolitics.setCardBackgroundColor(R.color.colorPrimaryPolitics);
                }
            }
        });

        vTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTech) {
                    mTech = false;
                    vTech.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mTech = true;
                    vTech.setCardBackgroundColor(R.color.colorPrimaryTech);
                }

            }
        });

        vCareer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCareer) {
                    mCareer = false;
                    vCareer.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mCareer = true;
                    vCareer.setCardBackgroundColor(R.color.colorPrimaryCareer);
                }

            }
        });

        vFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFood) {
                    mFood = false;
                    vFood.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mFood = true;
                    vFood.setCardBackgroundColor(R.color.colorPrimaryFood);
                }

            }
        });

        vDancing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDancing) {
                    mDancing = false;
                    vDancing.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mDancing = true;
                    vDancing.setCardBackgroundColor(R.color.colorPrimaryDancing);
                }

            }
        });

        vAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnimals) {
                    mAnimals = false;
                    vAnimals.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mAnimals = true;
                    vAnimals.setCardBackgroundColor(R.color.colorPrimaryAnimals);
                }

            }
        });

        vLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLanguage) {
                    mLanguage = false;
                    vLanguage.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mLanguage = true;
                    vLanguage.setCardBackgroundColor(R.color.colorPrimaryLanguage);
                }

            }
        });

        vNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNature) {
                    mNature = false;
                    vNature.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mNature = true;
                    vNature.setCardBackgroundColor(R.color.colorPrimaryNature);
                }

            }
        });

        vSports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSports) {
                    mSports = false;
                    vSports.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mSports = true;
                    vSports.setCardBackgroundColor(R.color.colorPrimarySports);
                }

            }
        });

        vDating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDating) {
                    mDating = false;
                    vDating.setCardBackgroundColor(R.color.colorSelection);
                } else {
                    mDating = true;
                    vDating.setCardBackgroundColor(R.color.colorPrimaryDating);
                }

            }
        });
//// TODO: 3/11/16 add arraylist in accordance to cardadapter and put to shared preferences
//        vSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mPolitics) {
//                    mSelections.add()
//                }
//                if (mTech) {
//                    mSelections.add()
//                }
//                if (mCareer) {
//                    mSelections.add()
//                }
//                if (mFood) {
//                    mSelections.add()
//                }
//                if (mDancing) {
//                    mSelections.add()
//                }
//                if (mAnimals) {
//                    mSelections.add()
//                }
//                if (mLanguage) {
//                    mSelections.add()
//                }
//                if (mNature) {
//                    mSelections.add()
//                }
//                if (mSports) {
//                    mSelections.add()
//                }
//                if (mDating) {
//                    mSelections.add()
//                }
//                Intent intent = new Intent(SelectionPage.this, MainActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }
}