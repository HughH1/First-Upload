package com.example.hugo.alarmapptest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class Alarm_Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.e("onReceive", "Hello");

//         fetch extra strings from the intent
//         tells the app whether the user pressed the alarm on button or the alarm off button

        String get_your_string = Objects.requireNonNull(intent.getExtras()).getString("extra");

        Log.e("What is the key? ", get_your_string);


//         create an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

//         pass the extra string from Receiver to the Ringtone Playing Service
        service_intent.putExtra("extra", get_your_string);


//         start the ringtone service
        context.startService(service_intent);

    }

}
