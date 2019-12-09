package com.update.ipc.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : liupu
 * date   : 2019/12/9
 * desc   :
 * github : https://github.com/CodeLiuPu/
 */
public class BinderService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("update", "update service binder " + iBinder.toString());
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