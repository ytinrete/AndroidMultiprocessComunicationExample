package com.example.aidl.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */

public class AidlProcBModel implements Parcelable {

  private int x;
  private String y;
  private double z;

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public String getY() {
    return y;
  }

  public void setY(String y) {
    this.y = y;
  }

  public double getZ() {
    return z;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public AidlProcBModel() {

  }

  protected AidlProcBModel(Parcel in) {
    // 1.必须按成员变量声明的顺序读取数据
    x = in.readInt();
    y = in.readString();
    z = in.readDouble();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.x);
    dest.writeString(this.y);
    dest.writeDouble(this.z);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<AidlProcBModel> CREATOR = new Creator<AidlProcBModel>() {
    @Override
    public AidlProcBModel createFromParcel(Parcel in) {
      return new AidlProcBModel(in);
    }

    @Override
    public AidlProcBModel[] newArray(int size) {
      return new AidlProcBModel[size];
    }
  };
}
