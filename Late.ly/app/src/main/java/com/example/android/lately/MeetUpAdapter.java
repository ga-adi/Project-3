package com.example.android.lately;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by perrycooperman on 3/8/16.
 */
public class MeetUpAdapter extends RecyclerView.Adapter<MeetUpAdapter.MeetUpHolder> {

    List<MeetUpClass> meetUpClassList;

    public MeetUpAdapter(List<MeetUpClass> meetUpClassList) {
        this.meetUpClassList = meetUpClassList;

    }


    @Override
    public MeetUpAdapter.MeetUpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        
        
        return new MeetUpHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MeetUpAdapter.MeetUpHolder holder, int position) {
        
        MeetUpClass blah = meetUpClassList.get(position);
        holder.vGroupName.setText(blah.getGroup());
        holder.vTime.setText(blah.getTime());
        holder.vEventName.setText(blah.getEvent());
        holder.vNumberOfPeople.setText(blah.getNumberOfPeople());
    }

    @Override
    public int getItemCount() {
        return meetUpClassList.size();
    }

    public static class MeetUpHolder extends RecyclerView.ViewHolder {

        TextView vGroupName;
        TextView vTime;
        TextView vEventName;
        TextView vNumberOfPeople;


        public MeetUpHolder(View itemView) {
            super(itemView);

            vGroupName = (TextView) itemView.findViewById(R.id.eventcard_groupname);
            vTime = (TextView) itemView.findViewById(R.id.eventcard_time);
            vEventName = (TextView) itemView.findViewById(R.id.eventcard_eventname);
            vNumberOfPeople = (TextView) itemView.findViewById(R.id.eventcard_attendants);

        }
    }

}

