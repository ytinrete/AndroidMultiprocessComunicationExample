package com.example.aidl.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */

public class AidlProcAModel implements Parcelable {

  private int a;
  private String b;

  public AidlProcAModel() {

  }

  public int getA() {
    return a;
  }

  public void setA(int a) {
    this.a = a;
  }

  public String getB() {
    return b;
  }

  public void setB(String b) {
    this.b = b;
  }

  protected AidlProcAModel(Parcel in) {
    // 1.必须按成员变量声明的顺序读取数据
    a = in.readInt();
    b = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    // 1.必须按成员变量声明的顺序封装数据
    dest.writeInt(this.a);
    dest.writeString(this.b);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<AidlProcAModel> CREATOR = new Creator<AidlProcAModel>() {
    @Override
    public AidlProcAModel createFromParcel(Parcel in) {
      return new AidlProcAModel(in);
    }

    @Override
    public AidlProcAModel[] newArray(int size) {
      return new AidlProcAModel[size];
    }
  };
}
