package com.vms.antplay.timer;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import androidx.annotation.Nullable;
import com.vms.antplay.activity.MainActivity;
import com.vms.antplay.utils.Const;

public class TimerBroadcastService extends Service {
    TimerListener timerListener = (TimerListener) new MainActivity();
    private final static String TAG = "TIMER";

    Intent broadcastIntent = new Intent(Const.COUNTDOWN_BR);
    CountDownTimer countDownTimer = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "Timer Started");
        countDownTimer = new CountDownTimer(Const.MAX_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000);
                long remainingTimeMilliSec = Const.MAX_TIME-millisUntilFinished;

                broadcastIntent.putExtra(Const.COUNTDOWN_INTENT, remainingTimeMilliSec);
                sendBroadcast(broadcastIntent);

                //timerListener.remainingTime(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.d(TAG, "Timer finished");
            }
        };
        countDownTimer.start();
    }

    @Override
    public void onDestroy() {
        countDownTimer.cancel();
        Log.d(TAG, "Timer cancelled");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
