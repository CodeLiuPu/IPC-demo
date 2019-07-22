package com.update.ipc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;


public class AidlService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }


    private IBinder iBinder = new IBook.Stub() {
        List<String> names = new ArrayList<>();
        @Override
        public List<String> getNames() throws RemoteException {
            return names;
        }

        @Override
        public void addName(String name) throws RemoteException {
            names.add(name);
        }
    };
}
