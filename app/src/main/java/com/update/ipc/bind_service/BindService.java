package com.update.ipc.bind_service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.blankj.rxbus.RxBus;

public class BindService extends Service {
    private static final String TAG = "Update BindService";

    private final IBinder mBinder = new TestBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        RxBus.getDefault().subscribe(this, new RxBus.Callback<String>() {
            @Override
            public void onEvent(String s) {
                log("onEvent");
                stopSelf();
            }
        });
        log("onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        log("onStart");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        log("onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        log("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }

    private void log(String msg) {
        Log.e(TAG, msg);
    }

    public String getName(){
        return "Helloya";
    }

    class TestBinder extends Binder {
        BindService getService(){
            return BindService.this;
        }
    }
}
