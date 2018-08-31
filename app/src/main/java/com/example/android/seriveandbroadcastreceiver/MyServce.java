package com.example.android.seriveandbroadcastreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyServce extends Service {

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public int counter=0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);
        startTimer();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("SERVICE", "onDestroy Call");
    }

    private final WakefulBroadcastReceiver receiver = new WakefulBroadcastReceiver () {
        @Override
        public void onReceive(Context context, Intent intent) {

            if(checkInternet(context))
            {
                notification(context , "Got internet connection");
            }else {
                notification(context , "Check your internet connection");
            }
        }
    };

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("Sensor", "in timer ++++  "+ (counter++));
            }
        };
    }

    /**
     * not needed
     */

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    boolean checkInternet(Context context) {
        BroadcastManager serviceManager = new BroadcastManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    public void notification(Context context , String message){
        String title = context.getString(R.string.app_name);
        Intent intent = new Intent(context , MainActivity.class);
        intent.putExtra("title" , title);
        intent.putExtra("text" , message);
        String text;
        if (message != null && !message.isEmpty()){
            text = message;
        }
        else {
            //text = number;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context , 0 , intent ,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(message)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Creae Notification Manager

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 , builder.build());
    }
}
