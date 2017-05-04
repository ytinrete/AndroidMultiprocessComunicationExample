// IAidlInterface.aidl
package com.example.processb;

import com.example.aidl.model.AidlProcAModel;
import com.example.aidl.model.AidlProcBModel;

// Declare any non-default types here with import statements

interface IAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);


    AidlProcBModel transfer(in AidlProcAModel obj);

}
