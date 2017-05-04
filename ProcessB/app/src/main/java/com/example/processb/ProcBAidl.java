package com.example.processb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.aidl.model.AidlProcAModel;
import com.example.aidl.model.AidlProcBModel;

/**
 *
 */

public class ProcBAidl extends Service {

  private IAidlInterface.Stub binder = new IAidlInterface.Stub() {
    @Override
    public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

    }

    @Override
    public AidlProcBModel transfer(AidlProcAModel obj) throws RemoteException {

      Tools.l("procb aidl transfer receive obj from procA:"+obj.getA()+" "+obj.getB());


//    Tools.l("prob aidl begin to sleep");
//    try {
//      Thread.sleep(3000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    Tools.l("prob aidl wake up");

      AidlProcBModel objB = new AidlProcBModel();
      objB.setX(2);
      objB.setY("b");
      objB.setZ(2.0);

      return objB;
    }

  };

  @Override
  public void onCreate() {
    super.onCreate();
    Tools.l("procb aidl service onCreate");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Tools.l("procb aidl service onStartCommand");
    Tools.l("receive from A:" + intent.getStringExtra("msg"));

    return super.onStartCommand(intent, flags, startId);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    Tools.l("procb aidl onBind");
    return binder;
  }
}
