package com.example.processb;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;


/**
 *
 */

public class ProcBService extends Service {
  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Tools.l("procB service onCreate");


//    try {
//      Tools.l("procB service begin sleep");
//      Thread.sleep(3000);
//      Tools.l("procB service wake up");
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }

  }


  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Tools.l("receive from A:" + intent.getStringExtra("msg"));
    Toast.makeText(getApplication().getApplicationContext(), "receive from A:" +
        intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();


    return super.onStartCommand(intent, flags, startId);
  }
}
