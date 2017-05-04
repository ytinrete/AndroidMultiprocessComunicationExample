package com.example.processb;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.aidl.model.AidlProcAModel;
import com.example.aidl.model.AidlProcBModel;

/**
 *
 */

public class ProcBMessenger extends Service {

  private Handler mainHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      localHandleMessage(msg);
    }
  };

  private Handler threadHandler;

  @Override
  public void onCreate() {
    super.onCreate();
    Tools.l("procb messenger service onCreate");
    thread.start();
    threadHandler = new Handler(thread.getLooper()) {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
        localHandleMessage(msg);
      }
    };
    messenger = new Messenger(threadHandler);
//    messenger = new Messenger(mainHandler);
  }

  private HandlerThread thread = new HandlerThread("thread");


  private Messenger messenger;

  private void localHandleMessage(Message msg){
    if (msg.what == 1000) {
      Bundle bd = msg.getData();
      bd.setClassLoader(AidlProcAModel.class.getClassLoader());
      AidlProcAModel obj = bd.getParcelable("a");
      if (obj != null) {
        Tools.l("procb messenger transfer receive obj from procA:" + obj.getA() + " " + obj.getB());
      }

//      Tools.l("prob messenger begin to sleep");
//      try {
//        Thread.sleep(3000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//      Tools.l("prob messenger wake up");

      AidlProcBModel objB = new AidlProcBModel();
      objB.setX(22);
      objB.setY("bb");
      objB.setZ(22.0);

      Message back = new Message();
      back.what = 2000;
      Bundle bdBack = new Bundle();
      bdBack.putParcelable("b", objB);
      back.setData(bdBack);

      try {
        msg.replyTo.send(back);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }




  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Tools.l("procb messenger service onStartCommand");
    Tools.l("receive from A:" + intent.getStringExtra("msg"));

    return super.onStartCommand(intent, flags, startId);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    Tools.l("procb messenger onBind");
    return messenger.getBinder();
  }
}
