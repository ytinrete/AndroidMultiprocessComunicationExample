package com.example.processa;

import android.util.Log;

/**
 *
 */

public class Tools {

  public static void l(String s) {
    Log.i("xxxxx", "Proccess A PID:" + android.os.Process.myPid() + " thread:" + Thread
        .currentThread().getId() + " log:" + s);
  }

}
