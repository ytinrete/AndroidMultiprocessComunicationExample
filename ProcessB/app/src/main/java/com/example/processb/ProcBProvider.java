package com.example.processb;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 *
 */

public class ProcBProvider extends ContentProvider {
  @Override
  public boolean onCreate() {
    Tools.l("prob provider onCreate");
    return false;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    Tools.l("prob provider query");


//    Tools.l("prob provider begin to sleep");
//    try {
//      Thread.sleep(3000);
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    Tools.l("prob provider wake up");
//
//    getContext().getContentResolver().notifyChange(uri, null);


    return new TestCursor();
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }

  class TestCursor extends AbstractCursor {

    @Override
    public int getCount() {
      return 1;
    }

    @Override
    public String[] getColumnNames() {
      return new String[]{"0"};
    }

    @Override
    public String getString(int column) {
      return "bbb";
    }

    @Override
    public short getShort(int column) {
      return 0;
    }

    @Override
    public int getInt(int column) {
      return 0;
    }

    @Override
    public long getLong(int column) {
      return 0;
    }

    @Override
    public float getFloat(int column) {
      return 0;
    }

    @Override
    public double getDouble(int column) {
      return 0;
    }

    @Override
    public boolean isNull(int column) {
      return false;
    }
  }

}
