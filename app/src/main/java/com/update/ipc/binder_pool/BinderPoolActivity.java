package com.update.ipc.binder_pool;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.update.ipc.R;


public class BinderPoolActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Update BPService";

    Activity activity;
    ServiceConnection mConnection;
    IBook iBook;
    IBinderPoolManager binderPoolManager;
    IWeather iWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_binder_pool);
        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                binderPoolManager = IBinderPoolManager.Stub.asInterface(service);
                try {
                    iBook = IBook.Stub.asInterface(
                            binderPoolManager.queryCode(BinderPoolService.CODE_BOOK));
                    iWeather = IWeather.Stub.asInterface(
                            binderPoolManager.queryCode(BinderPoolService.CODE_WEATHER));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                log("onServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iBook = null;
                log("onServiceDisconnected");
            }
        };
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, BinderPoolService.class);
        switch (v.getId()) {
            case R.id.btn1:
                bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn2:
                unbindService(mConnection);
                break;
            case R.id.btn3:
                try {
                    iBook.setName("Helloya");

                    log("onClick - " + iBook.getNames());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn4:
                try {
                    iWeather.setName("Helloya");
                    log("onClick - " + iWeather.getNames());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void log(String msg) {
        Log.e(TAG, msg);
    }
}
