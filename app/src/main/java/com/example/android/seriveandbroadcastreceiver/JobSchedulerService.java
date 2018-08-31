package com.example.android.seriveandbroadcastreceiver;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

@SuppressLint("NewApi")
public class JobSchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        boolean isService = isMyServiceRunning(MyServce.class);
        if (isService==true){
            notification(getApplicationContext() , "Service Running");
        }else {
            notification(getApplicationContext() , "Service is not Running but starting");
            startService(new Intent(this,MyServce.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    private void notification(Context context , String service) {
        //String title = context.getString(R.string.app_name);
        //Intent intent = new Intent(context , MainActivity.class);
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        //notificationIntent.setData(Uri.parse("market://details?id="+ appPackageName));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notif = new Notification.Builder(getApplicationContext())
                .setContentIntent(pendingIntent)
                .setContentTitle("App Name")
                .setContentText(service)
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon(getBitmap(imageLink))
                //.setStyle(new Notification.BigPictureStyle().bigPicture(getBitmap(imageLink)))
                .build();

        notif.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notif);
    }
}
