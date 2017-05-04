package com.example.processb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;


/**
 *
 */

public class ProcBActivity extends Activity {
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.procb_activity);
    Intent intent = getIntent();
    if (intent != null) {
      Tools.l("receive from A:" + intent.getStringExtra("msg"));
      Toast.makeText(this, "receive from A:" + intent.getStringExtra("msg"), Toast
          .LENGTH_SHORT).show();
    }
  }
}
