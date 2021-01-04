package com.yj.app.view.service;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.yj.app.R;
import com.yj.app.databinding.ActivityServiceBinding;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServiceActivity extends AppCompatActivity {

    private ActivityServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service);
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar())
                .setDisplayHomeAsUpEnabled(true);
    }

    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();

    private BlockingQueue<Runnable> runnables = new LinkedBlockingDeque<>();

    private static final int KEEP_ALIVE_TIME =1;

    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    ThreadPoolExecutor executor = new ThreadPoolExecutor(NUMBER_OF_CORES,
            NUMBER_OF_CORES,KEEP_ALIVE_TIME,KEEP_ALIVE_TIME_UNIT,runnables);


    public void testJobService(){
        JobScheduler js = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo jobInfo = new JobInfo.Builder(0,new ComponentName(this,MyJobService.class))
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .build();
    }

    public static class MyJobService extends JobService {

        @Override
        public boolean onStartJob(JobParameters params) {
            return false;
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            return false;
        }
    }

}
