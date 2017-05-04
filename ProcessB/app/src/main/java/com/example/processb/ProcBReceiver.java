package com.example.processb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *
 */

public class ProcBReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Tools.l("receive from A:" + intent.getStringExtra("msg"));
    Toast.makeText(context, "receive from A:" + intent.getStringExtra("msg"), Toast
        .LENGTH_SHORT).show();
  }
}
