package com.update.ipc.binder_pool;

import android.os.IBinder;

interface IBinderPoolManager {
    IBinder queryCode(int code);
}