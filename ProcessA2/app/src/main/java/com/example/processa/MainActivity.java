package com.example.processa;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.aidl.model.AidlProcAModel;
import com.example.aidl.model.AidlProcBModel;
import com.example.aidl.model.SingletonTest;
import com.example.processb.IAidlInterface;


/**
 *
 */

public class MainActivity extends Activity implements View.OnClickListener {

  private Button btnBroadcast;
  private Button btnActivity;
  private Button btnService;
  private Button btnProvider;
  private Button btnAidlConnect;
  private Button btnAidlTransfer;
  private Button btnMessengerConnect;
  private Button btnMessengerTransfer;
  private CheckBox cb;


  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SingletonTest.getInstance().setFieldInt(1);
    SingletonTest.getInstance().setFieldString("AAA");

    Tools.l("Singleton int:" + SingletonTest.getInstance().getFieldInt()
        + " string:" + SingletonTest.getInstance().getFieldString());

    btnBroadcast = (Button) findViewById(R.id.boradcast);
    btnActivity = (Button) findViewById(R.id.activity);
    btnService = (Button) findViewById(R.id.service);
    btnProvider = (Button) findViewById(R.id.provider);
    btnAidlConnect = (Button) findViewById(R.id.aidl_connect);
    btnAidlTransfer = (Button) findViewById(R.id.aidl);
    btnMessengerConnect = (Button) findViewById(R.id.messenger_connect);
    btnMessengerTransfer = (Button) findViewById(R.id.messenger);
    cb = (CheckBox) findViewById(R.id.cb);

    btnBroadcast.setOnClickListener(this);
    btnActivity.setOnClickListener(this);
    btnService.setOnClickListener(this);
    btnProvider.setOnClickListener(this);
    btnAidlConnect.setOnClickListener(this);
    btnAidlTransfer.setOnClickListener(this);
    btnMessengerConnect.setOnClickListener(this);
    btnMessengerTransfer.setOnClickListener(this);

    cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          handler.postDelayed(timeRunnable, 1000);
        } else {
          handler.removeCallbacks(timeRunnable);
        }
      }
    });

  }

  private Handler handler = new Handler(Looper.getMainLooper());
  private Runnable timeRunnable = new Runnable() {
    @Override
    public void run() {
      time++;
      Tools.l("proA tick:" + time);
      handler.postDelayed(timeRunnable, 1000);
    }
  };
  int time = 0;


  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.boradcast:
        broadcast();
        break;
      case R.id.activity:
        activity();
        break;
      case R.id.service:
        service();
        break;
      case R.id.provider:
        provider();
        break;
      case R.id.aidl_connect:
        aidlConnect();
        break;
      case R.id.aidl:
        aidlSend();
        break;
      case R.id.messenger_connect:
        messengerConnect();
        break;
      case R.id.messenger:
        messengerSend();
        break;
    }
  }

  private void broadcast() {
    Bundle broadcastBundle = new Bundle();
    broadcastBundle.putString("msg", "foo boradcast");
    Intent broadcastIntent = new Intent();
    broadcastIntent.setAction("com.example.multiprocess.broadcast");
    broadcastIntent.putExtras(broadcastBundle);
    sendBroadcast(broadcastIntent);
  }

  private void activity() {
    Bundle bundle = new Bundle();
    bundle.putString("msg", "foo activity");
    Intent intent = new Intent();
    intent.putExtras(bundle);
    intent.setClassName("com.example.processb", "com.example.processb.ProcBActivity");
    startActivity(intent);
  }


  private void service() {
    Bundle bundle = new Bundle();
    bundle.putString("msg", "foo service");
    Intent intent = new Intent();
    intent.putExtras(bundle);
    intent.setClassName("com.example.processb", "com.example.processb.ProcBService");
    startService(intent);
  }

  private void provider() {
    try {
      Tools.l("req provider");
      Cursor info = this.getContentResolver().query(
          Uri.parse("content://com.example.multiprocess.provider"), null, null, null, null);
      Tools.l("result from provider");
      if (info != null && info.moveToLast()) {
        Tools.l("on provider: string:" + info.getString(0));
        info.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private IAidlInterface aidl;// 服务

  private ServiceConnection aidlConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      aidl = IAidlInterface.Stub.asInterface(service);// 获取服务对象
      Tools.l("proca aidl connect ok!");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      Tools.l("proca aidl disconnect");
    }
  };

  private void aidlConnect() {
    Bundle bundle = new Bundle();
    bundle.putString("msg", "foo aidl");
    Intent intent = new Intent();
    intent.putExtras(bundle);
    intent.setClassName("com.example.processb", "com.example.processb.ProcBAidl");
    bindService(intent, aidlConnection, BIND_AUTO_CREATE);
  }

  private void aidlSend() {
    if (aidl != null) {

      AidlProcAModel objA = new AidlProcAModel();
      objA.setA(1);
      objA.setB("a");

      try {
        Tools.l("req aidl");
        AidlProcBModel objB = aidl.transfer(objA);
        Tools.l("proca aidl transfer receive from B:" + objB.getX() + " " + objB.getY() + " " + objB.getZ());
      } catch (RemoteException e) {
        e.printStackTrace();
      }

    }
  }

  private Messenger remoteMessenger;

  ServiceConnection messengerConnection = new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      remoteMessenger = new Messenger(service);
      Tools.l("proca messenger connect ok!");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
      Tools.l("proca messenger disconnect");
    }
  };

  private void messengerConnect() {
    Bundle bundle = new Bundle();
    bundle.putString("msg", "foo messenger");
    Intent intent = new Intent();
    intent.putExtras(bundle);
    intent.setClassName("com.example.processb", "com.example.processb.ProcBMessenger");
    bindService(intent, messengerConnection, BIND_AUTO_CREATE);
  }

  private void messengerSend() {

    if (remoteMessenger != null) {

      AidlProcAModel objA = new AidlProcAModel();
      objA.setA(11);
      objA.setB("aa");

      Message msg = new Message();
      msg.what = 1000;

      Bundle bd = new Bundle();
      bd.putParcelable("a", objA);
      msg.setData(bd);
      msg.replyTo = localMessenger;
      try {
        Tools.l("req messenger");
        remoteMessenger.send(msg);
      } catch (RemoteException e) {
        e.printStackTrace();
      }
    }
  }


  Messenger localMessenger = new Messenger(new Handler() {
    @Override
    public void handleMessage(Message msg) {
      super.handleMessage(msg);
      if (msg.what == 2000) {
        Bundle bd = msg.getData();
        bd.setClassLoader(AidlProcBModel.class.getClassLoader());
        AidlProcBModel obj = bd.getParcelable("b");
        if (obj != null) {
          Tools.l("proca messenger transfer receive obj from procB:" + obj.getX()
              + " " + obj.getY() + " " + obj.getZ());
        }

      }

    }
  }
  );


  @Override
  protected void onDestroy() {
    if (aidl != null) {
      aidl = null;
      unbindService(aidlConnection);
    }

    if (remoteMessenger != null) {
      remoteMessenger = null;
      unbindService(messengerConnection);
    }
    super.onDestroy();
  }
}
