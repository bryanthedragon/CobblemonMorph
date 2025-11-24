
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesIntArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Arrays;

final class ArrayCopy {
    private ArrayCopy() {
    }

    static byte[] byteToByte(byte[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static int[] byteToInt(byte[] array) {
        int[] copyArray = new int[array.length];
        for (int i = 0; i < array.length; ++i) {
            copyArray[i] = array[i];
        }
        return copyArray;
    }

    static double[] byteToDouble(byte[] array) {
        double[] copyArray = new double[array.length];
        for (int i = 0; i < array.length; ++i) {
            copyArray[i] = array[i];
        }
        return copyArray;
    }

    static Object[] byteToObject(byte[] array) {
        Object[] copyArray = new Object[array.length];
        for (int i = 0; i < array.length; ++i) {
            copyArray[i] = (int)array[i];
        }
        return copyArray;
    }

    static int[] intToInt(int[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static double[] intToDouble(int[] array) {
        return ArrayCopy.intToDouble(array, 0, array.length);
    }

    static double[] intToDouble(int[] array, int arrayOffset, int usedLength) {
        double[] copyArray = new double[array.length];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            copyArray[i] = array[i];
        }
        return copyArray;
    }

    static double[] intToDoubleHoles(int[] array, int arrayOffset, int usedLength) {
        double[] copyArray = new double[array.length];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            int intValue = array[i];
            copyArray[i] = HolesIntArray.isHoleValue(intValue) ? HolesDoubleArray.HOLE_VALUE_DOUBLE : (double)intValue;
        }
        return copyArray;
    }

    static Object[] intToObject(int[] array) {
        return ArrayCopy.intToObject(array, 0, array.length);
    }

    static Object[] intToObject(int[] array, int arrayOffset, int usedLength) {
        Object[] copyArray = new Object[array.length];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            copyArray[i] = array[i];
        }
        return copyArray;
    }

    static Object[] intToObject(int[] array, int arrayOffset, int usedLength, int newLength) {
        Object[] copyArray = new Object[newLength];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            copyArray[i] = array[i];
        }
        return copyArray;
    }

    static Object[] intToObjectHoles(int[] array, int arrayOffset, int usedLength) {
        Object[] copyArray = new Object[array.length];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            int intValue = array[i];
            copyArray[i] = intValue == Integer.MIN_VALUE ? null : Integer.valueOf(intValue);
        }
        return copyArray;
    }

    static double[] doubleToDouble(double[] array) {
        return Arrays.copyOf(array, array.length);
    }

    static Object[] doubleToObject(double[] array) {
        return ArrayCopy.doubleToObject(array, 0, array.length);
    }

    static Object[] doubleToObject(double[] array, int arrayOffset, int usedLength) {
        Object[] copyArray = new Object[array.length];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            copyArray[i] = array[i];
        }
        return copyArray;
    }

    static Object[] doubleToObjectHoles(double[] array, int arrayOffset, int usedLength) {
        Object[] copyArray = new Object[array.length];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            double value2 = array[i];
            copyArray[i] = HolesDoubleArray.isHoleValue(value2) ? null : Double.valueOf(value2);
        }
        return copyArray;
    }

    static Object[] objectToObject(Object[] array) {
        return ArrayCopy.objectToObject(array, array.length);
    }

    static Object[] objectToObject(Object[] array, int usedLength) {
        Object[] newArray = new Object[usedLength];
        System.arraycopy(array, 0, newArray, 0, usedLength);
        return newArray;
    }

    static JSDynamicObject[] jsobjectToJSObject(JSDynamicObject[] array) {
        return ArrayCopy.jsobjectToJSObject(array, array.length);
    }

    static JSDynamicObject[] jsobjectToJSObject(JSDynamicObject[] array, int usedLength) {
        JSDynamicObject[] newArray = new JSDynamicObject[usedLength];
        System.arraycopy(array, 0, newArray, 0, usedLength);
        return newArray;
    }

    static Object[] jsobjectToObjectHoles(JSDynamicObject[] array, int arrayOffset, int usedLength) {
        return ArrayCopy.jsobjectToObject(array, arrayOffset, usedLength);
    }

    static Object[] jsobjectToObject(JSDynamicObject[] array, int arrayOffset, int usedLength) {
        Object[] newArray = new Object[array.length];
        System.arraycopy(array, arrayOffset, newArray, arrayOffset, usedLength);
        return newArray;
    }
}

