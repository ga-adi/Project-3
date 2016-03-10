package com.example.android.lately;

import com.example.android.lately.Cards.CardAdapter;
import com.example.android.lately.Cards.ParentCard;

import java.util.ArrayList;

/**
 * Created by ShowMe on 3/9/16.
 */
public class Singleton {

    ArrayList<ParentCard> mMainPageArray;
    ArrayList<ParentCard> mPoliticsArray;
    ArrayList<ParentCard> mTechArray;
    ArrayList<ParentCard> mCareerArray;
    ArrayList<ParentCard> mFoodArray;
    ArrayList<ParentCard> mDancingArray;
    ArrayList<ParentCard> mAnimalsArray;
    ArrayList<ParentCard> mLanguageArray;
    ArrayList<ParentCard> mNatureArray;
    ArrayList<ParentCard> mSportsArray;
    ArrayList<ParentCard> mDatingArray;


    private static Singleton singleton = new Singleton();

    public Singleton() {
            this.mMainPageArray = new ArrayList<ParentCard>();
            this.mPoliticsArray = new ArrayList<ParentCard>();
            this.mTechArray = new ArrayList<ParentCard>();
            this.mCareerArray = new ArrayList<ParentCard>();
            this.mFoodArray = new ArrayList<ParentCard>();
            this.mDancingArray = new ArrayList<ParentCard>();
            this.mAnimalsArray = new ArrayList<ParentCard>();
            this.mLanguageArray = new ArrayList<ParentCard>();
            this.mNatureArray = new ArrayList<ParentCard>();
            this.mSportsArray = new ArrayList<ParentCard>();
            this.mDatingArray = new ArrayList<ParentCard>();
    }

    public ArrayList<ParentCard> getArrayList(int TAB) {
        switch (TAB){
            case CardAdapter.TAB_MAINPAGE:
                return mMainPageArray;
            case CardAdapter.TAB_POLITICS:
                return mPoliticsArray;
            case CardAdapter.TAB_TECH:
                return mTechArray;
            case CardAdapter.TAB_CAREER:
                return mCareerArray;
            case CardAdapter.TAB_FOOD:
                return mFoodArray;
            case CardAdapter.TAB_DANCING:
                return mDancingArray;
            case CardAdapter.TAB_ANIMALS:
                return mAnimalsArray;
            case CardAdapter.TAB_LANGUAGE:
                return mLanguageArray;
            case CardAdapter.TAB_NATURE:
                return mNatureArray;
            case CardAdapter.TAB_SPORTS:
                return mSportsArray;
            case CardAdapter.TAB_DATING:
                return mDatingArray;
            default:
                return mMainPageArray;
        }
    }

    public ParentCard getParentCard(int TAB, int position){
        switch (TAB){
            case CardAdapter.TAB_MAINPAGE:
                return mMainPageArray.get(position);
            case CardAdapter.TAB_POLITICS:
                return mPoliticsArray.get(position);
            case CardAdapter.TAB_TECH:
                return mTechArray.get(position);
            case CardAdapter.TAB_CAREER:
                return mCareerArray.get(position);
            case CardAdapter.TAB_FOOD:
                return mFoodArray.get(position);
            case CardAdapter.TAB_DANCING:
                return mDancingArray.get(position);
            case CardAdapter.TAB_ANIMALS:
                return mAnimalsArray.get(position);
            case CardAdapter.TAB_LANGUAGE:
                return mLanguageArray.get(position);
            case CardAdapter.TAB_NATURE:
                return mNatureArray.get(position);
            case CardAdapter.TAB_SPORTS:
                return mSportsArray.get(position);
            case CardAdapter.TAB_DATING:
                return mDatingArray.get(position);
            default:
                return mMainPageArray.get(position);
        }
    }

    public void addParentCard(ParentCard parentCard, int TAB){
        switch (TAB){
            case CardAdapter.TAB_MAINPAGE:
                mMainPageArray.add(parentCard);
            break;
            case CardAdapter.TAB_POLITICS:
                mPoliticsArray.add(parentCard);
            break;
            case CardAdapter.TAB_TECH:
                mTechArray.add(parentCard);
            break;
            case CardAdapter.TAB_CAREER:
                mCareerArray.add(parentCard);
            break;
            case CardAdapter.TAB_FOOD:
                mFoodArray.add(parentCard);
            break;
            case CardAdapter.TAB_DANCING:
                mDancingArray.add(parentCard);
            break;
            case CardAdapter.TAB_ANIMALS:
                mAnimalsArray.add(parentCard);
            break;
            case CardAdapter.TAB_LANGUAGE:
                mLanguageArray.add(parentCard);
            break;
            case CardAdapter.TAB_NATURE:
                mNatureArray.add(parentCard);
            break;
            case CardAdapter.TAB_SPORTS:
                mSportsArray.add(parentCard);
            break;
            case CardAdapter.TAB_DATING:
                mDatingArray.add(parentCard);
            break;
            default:
                mMainPageArray.add(parentCard);
        }
    }

    public static Singleton getInstance() {
        return singleton;
    }

}
