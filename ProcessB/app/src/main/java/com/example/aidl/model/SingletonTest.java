package com.example.aidl.model;

/**
 *
 */

public class SingletonTest {
  private static SingletonTest instance;

  private int fieldInt = -1;
  private String fieldString = "Base";

  private SingletonTest() {

  }

  public static SingletonTest getInstance() {
    if (instance == null) {
      instance = new SingletonTest();
    }
    return instance;
  }

  public int getFieldInt() {
    return fieldInt;
  }

  public void setFieldInt(int fieldInt) {
    this.fieldInt = fieldInt;
  }

  public String getFieldString() {
    return fieldString;
  }

  public void setFieldString(String fieldString) {
    this.fieldString = fieldString;
  }
}
