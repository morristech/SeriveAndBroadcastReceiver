package com.example.android.seriveandbroadcastreceiver;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        startService(new Intent(this,MyServce.class));
        setJobScheduler();
    }


    @SuppressLint("NewApi")
    private void  setJobScheduler() {
        ComponentName networkComponentName = new ComponentName(this, JobSchedulerService.class);
        @SuppressLint({"NewApi", "LocalSuppress"}) final JobInfo networkJobInfo = new JobInfo.Builder(51, networkComponentName)
                //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                //.setPeriodic(7000)
                .setPeriodic(7000)
                //.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();
        @SuppressLint({"NewApi", "LocalSuppress"}) JobScheduler networkJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        networkJobScheduler.schedule(networkJobInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
