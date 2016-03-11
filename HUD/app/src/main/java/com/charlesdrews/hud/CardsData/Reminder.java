package com.charlesdrews.hud.CardsData;

/**
 * An individual reminder item
 * Created by charlie on 3/10/16.
 */
public class Reminder {
    private int mId;
    private String mReminderText;
    private long mReminderDateTime;

    public Reminder(int id, String reminderText, long dateTimeInMillis) {
        mId = id;
        mReminderText = reminderText;
        mReminderDateTime = dateTimeInMillis;
    }

    public int getId() { return mId; }

    public String getReminderText() { return mReminderText; }

    public long getDateTimeInMillis() { return mReminderDateTime; }
}
