package com.charlesdrews.hud.CardsData;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.charlesdrews.hud.CardContentProvider;
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
    ArrayList<Reminder> mData;

    public RemindersRecyclerAdapter(ArrayList<Reminder> data) {
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item, parent, false);
        return new ViewHolder(v, parent.getContext());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Reminder item = mData.get(position);

        holder.mReminderText.setText(item.getReminderText());

        Long dateTimeInMillis = item.getDateTimeInMillis();
        if (dateTimeInMillis > 0) {
            Date dateTime = new Date(dateTimeInMillis);
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, h:mm a", Locale.getDefault());
            holder.mDateTime.setText(formatter.format(dateTime));
            holder.mDateTime.setVisibility(View.VISIBLE);
        } else {
            holder.mDateTime.setVisibility(View.GONE);
        }

        holder.mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.mContext, "Clicked item " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.mContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                launchConfirmDeleteDialog(holder.mContext, item, position);
                return true;
            }
        });
    }

    public void launchConfirmDeleteDialog(final Context context, final Reminder reminder, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("Delete reminder?")
                .setMessage(reminder.getReminderText())
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteReminder(context, reminder.getId());
                        mData.remove(position);
                        notifyDataSetChanged();
                    }
                });
        builder.show();
    }

    public void deleteReminder(Context context, int id) {
        context.getContentResolver().delete(
                Uri.withAppendedPath(CardContentProvider.REMINDERS_URI, String.valueOf(id)),
                null, null
        );
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
