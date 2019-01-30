package com.example.hugo.alarmapptest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    /*
    Song licence: public domain
    Source:    http://soundbible.com/1633-Alien-AlarmDrum.html
    Uploaded by user: KevanGC
     */


    //        to make our alarm manager
    private AlarmManager alarmManager;
    private TimePicker timePicker;
    private TextView updateText;
    Context context;
    PendingIntent pendingIntent;
//    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;

//         initialize the alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

//        initialize the time picker
        timePicker = findViewById(R.id.timePicker);

//        initialize the text view
        updateText = findViewById(R.id.updateText);

//         create an instance of a calendar
        final Calendar calendar = Calendar.getInstance();

//         initialize start button
        Button alarm_on = findViewById(R.id.alarmOn);

//        initialize the stop button
        Button alarm_off = findViewById(R.id.alarmOff);

        // create an intent to the Alarm Receiver class
        final Intent intent = new Intent(this.context, Alarm_Receiver.class);

//         create an onClick listener to start the alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

//                     get the int values of the hour and minute for displaying
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

//                     convert the int values to strings
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

//                    corrects display time
                //10:5 -> 10:05
                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                set_alarm_text("Alarm set to: " + hour_string + ":" + minute_string);

//                     put in extra string into intent
//                     tells the clock that you pressed the "alarm on" button
                intent.putExtra("extra", "alarm on");


//                     create a pending intent that delays the intent
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Log.e("Pending intent", "get broadcast context main");

//                     set the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pendingIntent);
                Log.e("alarmManager", "rtc wakeup set, pending intent retrieve wakeup time calender");
            }

        });

        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                method that changes the update text text view
                set_alarm_text("Alarm off!");
//                 cancel the alarm
                alarmManager.cancel(pendingIntent);

//                 put extra string into intent
                intent.putExtra("extra", "alarm off");

//                 stop the ringtone
                sendBroadcast(intent);
                Log.e("Alarm off", "pending intent passed, broadcast sent");

            }
        });

    }

    private void set_alarm_text(String output) {
        updateText.setText(output);
    }


}