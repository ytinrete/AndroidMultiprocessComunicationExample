package com.example.processb;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.aidl.model.SingletonTest;


/**
 *
 */

public class MainActivity extends Activity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);

    Tools.l("Singleton int:" + SingletonTest.getInstance().getFieldInt()
        + " string:" + SingletonTest.getInstance().getFieldString());


    getContentResolver().registerContentObserver(Uri.parse("content://com.example.multiprocess.provider"),
        false, new ContentObserver(new Handler()) {
          @Override
          public boolean deliverSelfNotifications() {
            Tools.l("procB deliverSelfNotifications");
            return super.deliverSelfNotifications();
          }

          @Override
          public void onChange(boolean selfChange) {
            Tools.l("procB onChange1");
            super.onChange(selfChange);
          }

          @Override
          public void onChange(boolean selfChange, Uri uri) {
            Tools.l("procB onChange2 :" + uri.toString());
            super.onChange(selfChange, uri);
          }
        });
    ((CheckBox) findViewById(R.id.cb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

  int time = 0;

  private Handler handler = new Handler(Looper.getMainLooper());

  private Runnable timeRunnable = new Runnable() {
    @Override
    public void run() {
      time++;
      Tools.l("proB tick:" + time);
      handler.postDelayed(this, 1000);
    }
  };


  @Override
  protected void onDestroy() {
    super.onDestroy();
  }
}
