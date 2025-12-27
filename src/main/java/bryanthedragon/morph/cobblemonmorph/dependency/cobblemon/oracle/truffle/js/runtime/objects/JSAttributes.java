package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;

public final class JSAttributes {
   public static final TruffleString VALUE = Strings.constant("value");
   public static final TruffleString GET = Strings.constant("get");
   public static final TruffleString SET = Strings.constant("set");
   public static final TruffleString WRITABLE = Strings.constant("writable");
   public static final TruffleString ENUMERABLE = Strings.constant("enumerable");
   public static final TruffleString CONFIGURABLE = Strings.constant("configurable");
   public static final int NOT_ENUMERABLE = 1;
   public static final int NOT_CONFIGURABLE = 2;
   public static final int NOT_WRITABLE = 4;
   public static final int ATTRIBUTES_MASK = 7;
   private static final int NOT_CONFIGURABLE_ENUMERABLE_WRITABLE = 2;
   private static final int NOT_CONFIGURABLE_ENUMERABLE_NOT_WRITABLE = 6;
   private static final int NOT_CONFIGURABLE_NOT_ENUMERABLE_WRITABLE = 3;
   private static final int NOT_CONFIGURABLE_NOT_ENUMERABLE_NOT_WRITABLE = 7;
   private static final int CONFIGURABLE_NOT_ENUMERABLE_WRITABLE = 1;
   private static final int CONFIGURABLE_NOT_ENUMERABLE_NOT_WRITABLE = 5;
   private static final int CONFIGURABLE_ENUMERABLE_NOT_WRITABLE = 4;
   private static final int CONFIGURABLE_ENUMERABLE_WRITABLE = 0;

   private JSAttributes() {
   }

   public static int getDefault() {
      return configurableEnumerableWritable();
   }

   public static int getDefaultNotEnumerable() {
      return configurableNotEnumerableWritable();
   }

   public static int configurableEnumerableWritable() {
      return 0;
   }

   public static int configurableNotEnumerableWritable() {
      return 1;
   }

   public static int configurableEnumerableNotWritable() {
      return 4;
   }

   public static int configurableNotEnumerableNotWritable() {
      return 5;
   }

   public static int notConfigurableNotEnumerableNotWritable() {
      return 7;
   }

   public static int notConfigurableNotEnumerableWritable() {
      return 3;
   }

   public static int notConfigurableEnumerableWritable() {
      return 2;
   }

   public static int notConfigurableEnumerableNotWritable() {
      return 6;
   }

   public static int getAccessorDefault() {
      return configurableEnumerableWritable();
   }

   public static int notConfigurableNotEnumerable() {
      return notConfigurableNotEnumerableWritable();
   }

   public static int configurableNotEnumerable() {
      return configurableNotEnumerableWritable();
   }

   public static int fromConfigurableEnumerableWritable(boolean configurable, boolean enumerable, boolean writable) {
      return (!configurable ? 2 : 0) | (!enumerable ? 1 : 0) | (!writable ? 4 : 0);
   }

   public static int fromConfigurableEnumerable(boolean configurable, boolean enumerable) {
      return (!configurable ? 2 : 0) | (!enumerable ? 1 : 0);
   }

   public static boolean isConfigurable(int flags) {
      return (flags & 2) == 0;
   }

   public static boolean isEnumerable(int flags) {
      return (flags & 1) == 0;
   }

   public static boolean isWritable(int flags) {
      return (flags & 4) == 0;
   }
}
