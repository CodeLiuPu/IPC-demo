package com.update.ipc.binder_pool;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


public class BinderPoolService extends Service {
    public static final int CODE_WEATHER = 1;
    public static final int CODE_BOOK = 2;

    private IBinderPoolManager iBinderPoolManager;

    @Override
    public void onCreate() {
        super.onCreate();
        iBinderPoolManager = new IBinderPoolManager.Stub() {

            @Override
            public IBinder queryCode(int code) throws RemoteException {
                switch (code) {
                    case CODE_BOOK:
                        return new IBook.Stub() {
                            String name = "Default";

                            @Override
                            public String getNames() throws RemoteException {
                                return "book = " + name;
                            }

                            @Override
                            public void setName(String name) throws RemoteException {
                                this.name = name;
                            }
                        };
                    case CODE_WEATHER:
                        return new IWeather.Stub() {
                            String name = "Default";

                            @Override
                            public String getNames() throws RemoteException {
                                return "weather = " + name;
                            }

                            @Override
                            public void setName(String name) throws RemoteException {
                                this.name = name;
                            }
                        };
                    default:
                        return null;
                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinderPoolManager.asBinder();
    }


}
