package com.charlesdrews.hud.CardsData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesdrews.hud.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Bind reminder data to the recycler view in the reminders card
 * Created by charlie on 3/10/16.
 */
public class RemindersRecyclerAdapter extends RecyclerView.Adapter<RemindersRecyclerAdapter.ViewHolder> {
    ArrayList<RemindersCardData.ReminderItem> mData;

    public RemindersRecyclerAdapter(ArrayList<RemindersCardData.ReminderItem> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new ViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final RemindersCardData.ReminderItem item = mData.get(position);

        Date dateTime = new Date(item.getDateTimeInMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, h:mm a", Locale.getDefault());
        holder.mDateTime.setText(formatter.format(dateTime));

        holder.mReminderText.setText(item.getReminderText());

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.mContext, "Clicked: " + item.getReminderText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Context mContext;
        LinearLayout mContainer;
        TextView mDateTime, mReminderText;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            mContext = context;
            mContainer = (LinearLayout) itemView.findViewById(R.id.reminderItemContainer);
            mDateTime = (TextView) itemView.findViewById(R.id.reminderItemDateTime);
            mReminderText = (TextView) itemView.findViewById(R.id.reminderItemText);
        }
    }
}
