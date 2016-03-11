package com.charlesdrews.hud.CardsData;

import android.database.Cursor;

import com.charlesdrews.hud.DatabaseHelper;

import java.util.ArrayList;

/**
 * Contains a list of reminder items, each with a due date/time and text
 * Created by charlie on 3/10/16.
 */
public class RemindersCardData extends CardData {
    private ArrayList<Reminder> mReminderItems;

    public RemindersCardData(CardType type, Cursor cursor) {
        super(type);

        if (cursor != null) {
            mReminderItems = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                mReminderItems.add(new Reminder(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.REMINDERS_COL_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.REMINDERS_COL_TEXT)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelper.REMINDERS_COL_WHEN))
                ));
            }
        } else {
            mReminderItems = new ArrayList<>();
        }
    }

    public ArrayList<Reminder> getReminders() { return mReminderItems; }
}
