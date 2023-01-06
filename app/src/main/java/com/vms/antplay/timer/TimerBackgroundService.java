package com.vms.antplay.timer;

import static android.app.PendingIntent.FLAG_IMMUTABLE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.vms.antplay.R;
import com.vms.antplay.activity.MainActivity;
import com.vms.antplay.utils.Const;

public class TimerBackgroundService extends Service {
    private final static String TAG = "TIMER";
    public static boolean isServiceRunning;
    private String CHANNEL_ID = "NOTIFICATION_CHANNEL";
    Intent broadcastIntent = new Intent(Const.COUNTDOWN_BR);
    CountDownTimer countDownTimer = null;
    long remainingTimeMilliSec;

    public TimerBackgroundService() {
        Log.d(TAG, "constructor called");
        isServiceRunning = false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate called");
        countDownTimer = new CountDownTimer(Const.MAX_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                remainingTimeMilliSec = Const.MAX_TIME - millisUntilFinished;

                broadcastIntent.putExtra(Const.COUNTDOWN_TIMER_INTENT, remainingTimeMilliSec);
                broadcastIntent.putExtra(Const.COUNTDOWN_TIMER_RUNNING, true);
                broadcastIntent.putExtra(Const.COUNTDOWN_TIMER_FINISHED, false);
                sendBroadcast(broadcastIntent);

            }

            @Override
            public void onFinish() {
                Log.d(TAG, "Timer finished");

                broadcastIntent.putExtra(Const.COUNTDOWN_TIMER_FINISHED, true);
                sendBroadcast(broadcastIntent);
                stopForeground(true);
                stopSelf();
            }
        };
        countDownTimer.start();
        createNotificationChannel();
        isServiceRunning = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "This is a Service running in Background");
        String input = intent.getStringExtra("inputExtra");
        Log.d(TAG, "onStartCommand called");
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(Const.NOTIFICATION_TITLE)
                .setContentText(Const.NOTIFICATION_MESSAGE) //Listening for Screen Off/On events
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentIntent(pendingIntent)
                .setColor(getResources().getColor(R.color.black))
                .build();

        startForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String appName = getString(R.string.app_name);
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    appName,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        Log.d(TAG, "onDestroy called");
        isServiceRunning = false;
        stopForeground(true);
        // call MyReceiver which will restart this service via a worker
        broadcastIntent.putExtra(Const.COUNTDOWN_TIMER_RUNNING, false);
        sendBroadcast(broadcastIntent);
        super.onDestroy();
    }
}
