package com.update.ipc.bind_service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.update.ipc.R;


public class BindActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Update BindService";

    Activity activity;
    ServiceConnection mConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_bind);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                BindService.TestBinder  testBinder = (BindService.TestBinder) service;
                BindService bindService = testBinder.getService();
                log("onServiceConnected " + bindService.getName());
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                log("onServiceDisconnected");
            }
        };
        findViewById(R.id.btn).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, BindService.class);
        switch (v.getId()) {
            case R.id.btn:
                startService(intent);
                break;
            case R.id.btn1:
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn2:
                unbindService(mConnection);
                break;
            case R.id.btn3:
                stopService(intent);
//                RxBus.getDefault().post("");
                log("stopService");
                break;
        }
    }

    private void log(String msg) {
        Log.e(TAG, msg);
    }
}
