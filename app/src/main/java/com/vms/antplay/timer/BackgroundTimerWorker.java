package com.vms.antplay.timer;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class BackgroundTimerWorker extends Worker {
    private final static String TAG = "TIMER";
    private final Context context;

    public BackgroundTimerWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork called for: " + this.getId());
        Log.d(TAG, "Service Running: " + TimerBackgroundService.isServiceRunning);
        if (!TimerBackgroundService.isServiceRunning) {
            Log.d(TAG, "starting service from doWork");
            Intent intent = new Intent(this.context, TimerBackgroundService.class);
            ContextCompat.startForegroundService(context, intent);
        }
        return Result.success();
    }

    @Override
    public void onStopped() {
        Log.d(TAG, "onStopped called for: " + this.getId());
        super.onStopped();
    }
}
