package com.example.android.lately;

import com.example.android.lately.Cards.ParentCard;

import java.util.ArrayList;

/**
 * Created by ShowMe on 3/9/16.
 */
public class Singleton {

    ArrayList<ParentCard> parentCardArrayList;

    public Singleton(ArrayList<ParentCard> parentCardArrayList) {
        this.parentCardArrayList = parentCardArrayList;
    }

    public ArrayList<ParentCard> getParentCardArrayList() {
        return parentCardArrayList;
    }

    public void setParentCardArrayList(ArrayList<ParentCard> parentCardArrayList) {
        this.parentCardArrayList = parentCardArrayList;
    }

    public ParentCard getParentCard(int position){
        return parentCardArrayList.get(position);
    }

}
