package com.update.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class MessengerService extends Service {
    private static final String TAG = "Update MessengerService";

    public static final String KEY_MESSAGE = "message";
    public static final int KEY_CONN = 1;
    public static final int KEY_DISCONN = 2;

    Messenger messenger = null;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case KEY_CONN:
                    log("i received " + msg.getData().getString(KEY_MESSAGE));
                    Messenger client = msg.replyTo;

                    if (client != null) {
                        String data = "i have received your message";
                        Message reply = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putString(KEY_MESSAGE, data);

                        reply.setData(bundle);

                        try {
                            client.send(reply);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case KEY_DISCONN:
                    log("i received " + msg.getData().getString(KEY_MESSAGE));
                    log("client has disconn this conn");
                    break;
                default:
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        messenger = new Messenger(new MessengerHandler());
    }

    private static void log(String msg) {
        Log.e(TAG, msg);
    }

}