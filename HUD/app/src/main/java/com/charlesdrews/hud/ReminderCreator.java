package com.charlesdrews.hud;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.charlesdrews.hud.CardsData.Reminder;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Creates & launches dialogs to get user input for new reminder, saves reminder, sets notification
 * Created by charlie on 3/10/16.
 */
public class ReminderCreator {
    private WeakReference<Context> mContextRef;
    private EditText mInput;
    private AlertDialog mAlertDialog;
    private Calendar mCurrentTime, mAlarmTime;

    public ReminderCreator(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    public interface OnReminderSubmittedListener {
        void onReminderSubmitted(Reminder reminder);
    }

    public void launchDialog() {
        View view = LayoutInflater.from(mContextRef.get()).inflate(R.layout.add_reminder_dialog, null);
        mInput = (EditText) view.findViewById(R.id.reminder_content);

        mAlertDialog = new AlertDialog.Builder(mContextRef.get())
                .setTitle("Add a reminder")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", null)
                .setNeutralButton("Add Alarm", null)
                .create();

        mAlertDialog.show();

        // set click listeners AFTER showing dialog so that it doesn't auto-dismiss on any click
        mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAlertDialog.dismiss();
                    }
                });

        mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onOK();
                    }
                });

        mAlertDialog.getButton(DialogInterface.BUTTON_NEUTRAL)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onAddAlarm();
                    }
                });
    }

    private void onOK() {
        if (mInput.getText().toString().isEmpty()) {
            mInput.setError("Please enter a reminder");
            mInput.requestFocus();
        } else {
            //TODO - create new ReminderItem
            Reminder reminder = new Reminder(0, "hello", 0L);
            ((MainActivity) mContextRef.get()).onReminderSubmitted(reminder);
            mAlertDialog.dismiss();
        }
    }

    private void onAddAlarm() {
        mCurrentTime = Calendar.getInstance();
        launchDatePicker();
    }

    private void launchDatePicker() {
        final DatePickerDialog datePicker = new DatePickerDialog(
                mContextRef.get(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        mAlarmTime = Calendar.getInstance();
                        mAlarmTime.set(year, monthOfYear, dayOfMonth);
                        launchTimePicker();
                    }
                },
                mCurrentTime.get(Calendar.YEAR),
                mCurrentTime.get(Calendar.MONTH),
                mCurrentTime.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show();
    }

    private void launchTimePicker() {
        final TimePickerDialog timePicker = new TimePickerDialog(
                mContextRef.get(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mAlarmTime.set(
                                mAlarmTime.get(Calendar.YEAR),
                                mAlarmTime.get(Calendar.MONTH),
                                mAlarmTime.get(Calendar.DAY_OF_MONTH),
                                hourOfDay, minute
                        );
                        makeAlarmTimeVisible();
                    }
                },
                mCurrentTime.get(Calendar.HOUR_OF_DAY),
                mCurrentTime.get(Calendar.MINUTE),
                false
        );
        timePicker.show();
    }

    private void makeAlarmTimeVisible() {
        if (mAlarmTime != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, h:mm a", Locale.getDefault());
            String alarmTimeString = " Alarm date/time: " + formatter.format(mAlarmTime.getTime());

            TextView alarmTimeText = (TextView) mAlertDialog.findViewById(R.id.reminder_alarm_time);
            alarmTimeText.setText(alarmTimeString);
            alarmTimeText.setVisibility(View.VISIBLE);
        }
    }
}
