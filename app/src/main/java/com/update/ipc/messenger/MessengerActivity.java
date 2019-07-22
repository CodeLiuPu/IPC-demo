package com.update.ipc.messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.update.ipc.R;

import java.text.SimpleDateFormat;

public class MessengerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Update TestService";

    private ServiceConnection serviceConnection;
    private Messenger serverMessenger;
    private Messenger messenger;

    private boolean hasBindService = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        initView();
        initData();

    }

    private void initView() {
        findViewById(R.id.btn).setOnClickListener(this);

    }

    private void initData() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                serverMessenger = new Messenger(service);
                communicate();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                serverMessenger = null;
            }
        };
        messenger = new Messenger(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                log("i have received " + msg.getData().getString(ServerService.KEY_MESSAGE));
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString(ServerService.KEY_MESSAGE, "Ok bye");
                message.setData(bundle);
                log("i have send " + message.getData().getString(ServerService.KEY_MESSAGE));

                message.what = ServerService.KEY_DISCONN;

                if (null != serverMessenger) {
                    try {
                        serverMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void communicate() {
        String format = "yyyy-MM-dd-HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String currTime = simpleDateFormat.format(System.currentTimeMillis());
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putString(ServerService.KEY_MESSAGE, "i have send handler a message at " + currTime);
        message.setData(bundle);
        log("i have send " + message.getData().getString(ServerService.KEY_MESSAGE));
        message.what = ServerService.KEY_CONN;
        message.replyTo = messenger;
        if (null != serverMessenger) {
            try {
                serverMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                if (!hasBindService) {
                    Intent intent = new Intent();
                    String packageName = "com.update.messengerdemo";
                    String className = packageName + ".ServerService";
                    intent.setClassName(packageName, className);
                    bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                    hasBindService = true;
                } else {
                    if (null != serverMessenger) {
                        return;
                    }
                    communicate();
                }
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != serverMessenger) {
            unbindService(serviceConnection);
        }
    }

    private static void log(String msg) {
        Log.e(TAG, msg);
    }

}
