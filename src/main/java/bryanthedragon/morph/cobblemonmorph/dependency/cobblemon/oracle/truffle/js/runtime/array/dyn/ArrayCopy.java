package com.oracle.truffle.js.runtime.array.dyn;

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

      for (int i = 0; i < array.length; i++) {
         copyArray[i] = array[i];
      }

      return copyArray;
   }

   static double[] byteToDouble(byte[] array) {
      double[] copyArray = new double[array.length];

      for (int i = 0; i < array.length; i++) {
         copyArray[i] = array[i];
      }

      return copyArray;
   }

   static Object[] byteToObject(byte[] array) {
      Object[] copyArray = new Object[array.length];

      for (int i = 0; i < array.length; i++) {
         copyArray[i] = Integer.valueOf(array[i]);
      }

      return copyArray;
   }

   static int[] intToInt(int[] array) {
      return Arrays.copyOf(array, array.length);
   }

   static double[] intToDouble(int[] array) {
      return intToDouble(array, 0, array.length);
   }

   static double[] intToDouble(int[] array, int arrayOffset, int usedLength) {
      double[] copyArray = new double[array.length];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         copyArray[i] = array[i];
      }

      return copyArray;
   }

   static double[] intToDoubleHoles(int[] array, int arrayOffset, int usedLength) {
      double[] copyArray = new double[array.length];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         int intValue = array[i];
         if (HolesIntArray.isHoleValue(intValue)) {
            copyArray[i] = HolesDoubleArray.HOLE_VALUE_DOUBLE;
         } else {
            copyArray[i] = intValue;
         }
      }

      return copyArray;
   }

   static Object[] intToObject(int[] array) {
      return intToObject(array, 0, array.length);
   }

   static Object[] intToObject(int[] array, int arrayOffset, int usedLength) {
      Object[] copyArray = new Object[array.length];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         copyArray[i] = array[i];
      }

      return copyArray;
   }

   static Object[] intToObject(int[] array, int arrayOffset, int usedLength, int newLength) {
      Object[] copyArray = new Object[newLength];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         copyArray[i] = array[i];
      }

      return copyArray;
   }

   static Object[] intToObjectHoles(int[] array, int arrayOffset, int usedLength) {
      Object[] copyArray = new Object[array.length];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         int intValue = array[i];
         if (intValue == Integer.MIN_VALUE) {
            copyArray[i] = null;
         } else {
            copyArray[i] = intValue;
         }
      }

      return copyArray;
   }

   static double[] doubleToDouble(double[] array) {
      return Arrays.copyOf(array, array.length);
   }

   static Object[] doubleToObject(double[] array) {
      return doubleToObject(array, 0, array.length);
   }

   static Object[] doubleToObject(double[] array, int arrayOffset, int usedLength) {
      Object[] copyArray = new Object[array.length];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         copyArray[i] = array[i];
      }

      return copyArray;
   }

   static Object[] doubleToObjectHoles(double[] array, int arrayOffset, int usedLength) {
      Object[] copyArray = new Object[array.length];

      for (int i = arrayOffset; i < arrayOffset + usedLength; i++) {
         double value = array[i];
         if (HolesDoubleArray.isHoleValue(value)) {
            copyArray[i] = null;
         } else {
            copyArray[i] = value;
         }
      }

      return copyArray;
   }

   static Object[] objectToObject(Object[] array) {
      return objectToObject(array, array.length);
   }

   static Object[] objectToObject(Object[] array, int usedLength) {
      Object[] newArray = new Object[usedLength];
      System.arraycopy(array, 0, newArray, 0, usedLength);
      return newArray;
   }

   static JSDynamicObject[] jsobjectToJSObject(JSDynamicObject[] array) {
      return jsobjectToJSObject(array, array.length);
   }

   static JSDynamicObject[] jsobjectToJSObject(JSDynamicObject[] array, int usedLength) {
      JSDynamicObject[] newArray = new JSDynamicObject[usedLength];
      System.arraycopy(array, 0, newArray, 0, usedLength);
      return newArray;
   }

   static Object[] jsobjectToObjectHoles(JSDynamicObject[] array, int arrayOffset, int usedLength) {
      return jsobjectToObject(array, arrayOffset, usedLength);
   }

   static Object[] jsobjectToObject(JSDynamicObject[] array, int arrayOffset, int usedLength) {
      Object[] newArray = new Object[array.length];
      System.arraycopy(array, arrayOffset, newArray, arrayOffset, usedLength);
      return newArray;
   }
}
