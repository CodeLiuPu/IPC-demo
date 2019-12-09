package com.update.ipc.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.List;

/**
 * @author : liupu
 * date   : 2019/12/9
 * desc   :
 * github : https://github.com/CodeLiuPu/
 */
public interface IBook extends IInterface {

    public static abstract class Stub extends Binder implements IBook {
        private static final String DESCRIPTOR = "com.update.ipc.binder.IBook";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        public static IBook asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }

            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if ((iin != null) && (iin instanceof IBook)) {
                return (IBook) iin;
            }
            return new IBook.Stub.Proxy(obj);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descripor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION:{
                    reply.writeString(descripor);
                    return true;
                }
                case TRANSATION_getNames:{
                    data.enforceInterface(descripor);
                    List<String> _result = this.getNames();
                    reply.writeNoException();
                    reply.writeStringList(_result);
                    return true;
                }
                case TRANSATION_addName:{
                    data.enforceInterface(descripor);
                    String _arg0;
                    _arg0 = data.readString();
                    this.addName(_arg0);
                    reply.writeNoException();
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements IBook {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                mRemote = remote;
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }

            @Override
            public List<String> getNames() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                List<String> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSATION_getNames, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createStringArrayList();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
                return _result;
            }

            @Override
            public void addName(String name) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(name);
                    mRemote.transact(Stub.TRANSATION_addName, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }
        }

        static final int TRANSATION_getNames = IBinder.FIRST_CALL_TRANSACTION + 0;
        static final int TRANSATION_addName = IBinder.FIRST_CALL_TRANSACTION + 1;
    }

    List<String> getNames() throws RemoteException;

    void addName(String name) throws RemoteException;

}
