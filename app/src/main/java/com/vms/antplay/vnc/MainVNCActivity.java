package com.vms.antplay.vnc;

import static java.security.AccessController.getContext;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.vms.antplay.R;
import com.vms.antplay.activity.ForgetPassword;
import com.vms.antplay.activity.LoginScreenActivity;
import com.vms.antplay.api.APIClient;
import com.vms.antplay.api.RetrofitAPI;
import com.vms.antplay.model.requestModal.ForgotPassRequestModal;
import com.vms.antplay.model.requestModal.UpdateVmRequestModal;
import com.vms.antplay.model.responseModal.ForgotPassResponseModal;
import com.vms.antplay.model.responseModal.UpdateVmResponseModal;
import com.vms.antplay.timer.BackgroundTimerWorker;
import com.vms.antplay.timer.TimerBackgroundService;
import com.vms.antplay.utils.AppUtils;
import com.vms.antplay.utils.Const;
import com.vms.antplay.utils.SharedPreferenceUtils;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainVNCActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtNickName, edtUserName, edtPassword, edtIpAddress, edtPort;
    TextView txtConnect, txtDisconnect;
    private String TAG = "TIMER";

    private VncDatabase database;
    private ConnectionBean selected;
    private boolean repeaterTextSet = false;
    private long timeRemainingInSec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vnc_activity_main);
        edtNickName = findViewById(R.id.edtNickName);
        // edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtIpAddress = findViewById(R.id.edtIP);
        edtPort = findViewById(R.id.edtPort);
        txtConnect = findViewById(R.id.txtConnect);
        txtDisconnect = findViewById(R.id.txtDisConnect);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        selected = new ConnectionBean();

        //  selected = null;
        setOnClickListener();
        startServiceViaWorker();
    }

    private void setOnClickListener() {
        txtConnect.setOnClickListener(this);
        txtDisconnect.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtConnect:
                canvasStart();
                break;
            case R.id.txtDisConnect:
                stopService();
                break;
            default:
                Toast.makeText(this, "Something went wrong, Please try again later", Toast.LENGTH_SHORT).show();
        }
    }

    private void canvasStart() {
        //  if (selected == null) return;
        ActivityManager.MemoryInfo info = Utils.getMemoryInfo(this);
        if (info.lowMemory) {
            Log.d("VNC", "Low Memory");
            // Low Memory situation.  Prompt.
            Utils.showYesNoPrompt(this, "Continue?", "Android reports low system memory.\nContinue with VNC connection?", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    vnc();
                }
            }, null);
        } else
            vnc();
    }

    private void updateSelectedFromView() {
        if (selected == null) {
            // return;
        }
        selected.setAddress(Constt.IP_ADDRESS);
        Log.d("VNC", "IP : " + Constt.IP_ADDRESS);
        try {
            selected.setPort(Constt.PORT);
            Log.d("VNC", "Port : " + Constt.PORT);
        } catch (NumberFormatException nfe) {

        }
        selected.setNickname(edtNickName.getText().toString());
        Log.d("VNC", "Nick Name : " + edtNickName.getText().toString());
        selected.setUserName(Constt.USERNAME);
        //  selected.setForceFull(groupForceFullScreen.getCheckedRadioButtonId()==R.id.radioForceFullScreenAuto ? BitmapImplHint.AUTO : (groupForceFullScreen.getCheckedRadioButtonId()==R.id.radioForceFullScreenOn ? BitmapImplHint.FULL : BitmapImplHint.TILE));
        selected.setPassword(Constt.PASSWORD);
        Log.d("VNC", "Password : " + edtPassword.getText().toString());
        // selected.setKeepPassword(checkboxKeepPassword.isChecked());
        //  selected.setKeepPassword(true);
        // selected.setUseLocalCursor(checkboxLocalCursor.isChecked());
        selected.setUseLocalCursor(true);
        // selected.setColorModel(((COLORMODEL)colorSpinner.getSelectedItem()).nameString());
        //  selected.setUseWakeLock(checkboxWakeLock.isChecked());
        selected.setUseWakeLock(false);
        // selected.setUseImmersive(checkboxUseImmersive.isChecked());
        //  selected.setUseImmersive(true);
        if (repeaterTextSet) {
            // selected.setRepeaterId(repeaterText.getText().toString());
            selected.setUseRepeater(true);
        } else {
            selected.setUseRepeater(false);
        }
    }

    private void saveAndWriteRecent() {
       /* SQLiteDatabase db = database.getWritableDatabase();
        db.beginTransaction();
        try
        {
            selected.save(db);
            MostRecentBean mostRecent = getMostRecent(db);
            if (mostRecent == null)
            {
                mostRecent = new MostRecentBean();
                mostRecent.setConnectionId(selected.get_Id());
                mostRecent.Gen_insert(db);
            }
            else
            {
                mostRecent.setConnectionId(selected.get_Id());
                mostRecent.Gen_update(db);
            }
            db.setTransactionSuccessful();
        }
        finally
        {
            db.endTransaction();
        }*/
    }


    private void vnc() {
        Log.d("VNC", "Open VNC");
        updateSelectedFromView();
        saveAndWriteRecent();

        startService();

        Intent intent = new Intent(this, VncCanvasActivity.class);
        intent.putExtra(VncConstants.CONNECTION, selected.Gen_getValues());
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(timerBroadCastReceiver, new IntentFilter(Const.COUNTDOWN_BR));
        Log.i(TAG, "Registered broadcast receiver");
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(timerBroadCastReceiver);
        Log.i(TAG, "Unregistered broadcast receiver");
    }

    @Override
    protected void onStop() {
        try {
            unregisterReceiver(timerBroadCastReceiver);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
        Log.d("TIME", "MainActivity Stopped");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
        stopService();
    }

    public void startService() {
        Log.d(TAG, "startService called");
        if (!TimerBackgroundService.isServiceRunning) {
            Intent serviceIntent = new Intent(this, TimerBackgroundService.class);
            serviceIntent.putExtra("inputExtra", "1000");
            //ContextCompat.startForegroundService(this, serviceIntent);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //startForegroundService(new Intent(getApplicationContext(), MyNewService.class));
                ContextCompat.startForegroundService(this, serviceIntent);
            } else {
                //startService(new Intent(getApplicationContext(), MyNewService.class));
                this.startService(serviceIntent);
            }
        }
    }

    public void stopService() {
        Log.d(TAG, "stopService called");
        if (TimerBackgroundService.isServiceRunning) {
            Intent serviceIntent = new Intent(this, TimerBackgroundService.class);
            stopService(serviceIntent);


            long serverRemainingTime = SharedPreferenceUtils.getLong(MainVNCActivity.this, Const.REMAINING_TIME);
            Log.d(TAG, "Server Time (in Sec): "+serverRemainingTime);
            long totalRemainingTimeInSec;
            if (serverRemainingTime > timeRemainingInSec) {
                totalRemainingTimeInSec = serverRemainingTime - timeRemainingInSec;
                Log.d(TAG, "Used Time (in Sec): "+timeRemainingInSec+" Total Remaining Time (in Sec) : "+totalRemainingTimeInSec);
            } else {
                totalRemainingTimeInSec = serverRemainingTime;
            }
            SharedPreferenceUtils.saveLong(MainVNCActivity.this, Const.REMAINING_TIME, totalRemainingTimeInSec);
            callUpdateVM((int)SharedPreferenceUtils.getLong(MainVNCActivity.this, Const.VM_ID),(int) totalRemainingTimeInSec);
        }
    }


    public void startServiceViaWorker() {
        Log.d(TAG, "startServiceViaWorker called");
        String UNIQUE_WORK_NAME = "StartMyServiceViaWorker";
        WorkManager workManager = WorkManager.getInstance(this);

        // As per Documentation: The minimum repeat interval that can be defined is 15 minutes
        // (same as the JobScheduler API), but in practice 15 doesn't work. Using 16 here
        PeriodicWorkRequest request =
                new PeriodicWorkRequest.Builder(
                        BackgroundTimerWorker.class,
                        16,
                        TimeUnit.MINUTES)
                        .build();

        // to schedule a unique work, no matter how many times app is opened i.e. startServiceViaWorker gets called
        // do check for AutoStart permission
        workManager.enqueueUniquePeriodicWork(UNIQUE_WORK_NAME, ExistingPeriodicWorkPolicy.KEEP, request);
    }

    final private BroadcastReceiver timerBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTimer(intent);
        }
    };

    private void updateTimer(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra(Const.COUNTDOWN_TIMER_INTENT, 0);
            Log.d("TIMER", "Countdown seconds remaining (Broad Cast): " + millisUntilFinished / 1000);
            timeRemainingInSec = (millisUntilFinished / 1000) + 1;
            //txtProgress.setText(String.valueOf(remainingSec));
        }
    }

    private void callUpdateVM(int vmId, int timeRemaining) {
        // loadingPB.setVisibility(View.VISIBLE);
        String accessToken = SharedPreferenceUtils.getString(MainVNCActivity.this, Const.ACCESS_TOKEN);
        RetrofitAPI retrofitAPI = APIClient.getRetrofitInstance().create(RetrofitAPI.class);
        UpdateVmRequestModal updateVmRequestModal = new UpdateVmRequestModal(vmId, timeRemaining);
        Call<UpdateVmResponseModal> call = retrofitAPI.updateVM("Bearer " + accessToken,updateVmRequestModal);
        call.enqueue(new Callback<UpdateVmResponseModal>() {
            @Override
            public void onResponse(Call<UpdateVmResponseModal> call, Response<UpdateVmResponseModal> response) {
                if (response.isSuccessful()) {
                    // loadingPB.setVisibility(View.GONE);
                    Log.d(TAG, "" + response.body().getMessage());

                } else {
                    // loadingPB.setVisibility(View.GONE);
                    Log.e(TAG, "Else condition");
                    AppUtils.showToast(Const.no_records, MainVNCActivity.this);
                }
            }

            @Override
            public void onFailure(Call<UpdateVmResponseModal> call, Throwable t) {
                Log.e(TAG, "" + t);
                // loadingPB.setVisibility(View.GONE);
                AppUtils.showToast(Const.something_went_wrong, MainVNCActivity.this);
            }
        });
    }


}