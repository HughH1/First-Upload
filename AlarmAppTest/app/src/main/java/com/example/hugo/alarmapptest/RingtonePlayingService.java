package com.example.hugo.alarmapptest;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Objects;


public class RingtonePlayingService extends Service {
    private MediaPlayer media_song;
    private boolean isRunning;
    int i = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(@NonNull Intent intent, int flags, int startId) {
    i++;

        Log.i("LocalService", "Received start id " + startId + ": " + intent);

//         fetch the extra string from the alarm on/alarm off values
        String state = Objects.requireNonNull(intent.getExtras()).getString("extra");

        Log.e("Ringtone extra is ", state);


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        Set up an intent that goes to the Main Activity
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);


//        Set up a pending intent
        PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0,
                intent1, 0);


        Notification notificationPopup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off!")
                .setContentText("Click me!")
                .setSmallIcon(R.drawable.unset)
                .setContentIntent(pendingIntentMainActivity)
                .setAutoCancel(true)
                .build();

//         this converts the extra strings from the intent
//         to start IDs, values 0 or 1
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start ID is ", state);
                break;
            default:
                startId = 0;
                break;
        }


//         if there is no music playing, and the user pressed "alarm on"
//         music should start playing

        if (!this.isRunning && startId == 1) {
            Log.e("there is no music, ", "and you want to start");
            this.isRunning = true;

//             set up the start command for the notification

//            notificationManager.notify(0, notificationPopup);
            Objects.requireNonNull(notificationManager).notify(0, notificationPopup);
            Log.e("next", "media player is next");

            // play the sound
            media_song = MediaPlayer.create(this, R.raw.thesong);
            media_song.start();

        }

//         if there is music playing, and the user pressed "alarm off"
//         music should stop playing
        else if (this.isRunning && startId == 0) {
            Log.e("there is music, ", "and you want end");

//             stop the ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
        }

//         if there is music playing and the user pressed "alarm on"
//         do nothing
        else if (this.isRunning) {
            Log.e("there is music, ", "and you want start");

            this.isRunning = true;

        }

//         can't think of anything else, just to catch the odd event
        else {
            Log.e("else ", "somehow you reached this");

        }


        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
//         Tell the user we stopped.
        Log.e("on Destroy called", "ta da");

        super.onDestroy();
        this.isRunning = false;
    }


}