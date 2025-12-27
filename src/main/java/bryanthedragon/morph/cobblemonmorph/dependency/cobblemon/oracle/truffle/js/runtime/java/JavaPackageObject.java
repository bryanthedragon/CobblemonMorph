package com.oracle.truffle.js.runtime.java;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JavaPackageObject extends JSNonProxyObject {
   private final TruffleString packageName;

   protected JavaPackageObject(Shape shape, TruffleString packageName) {
      super(shape);
      this.packageName = packageName;
   }

   public TruffleString getPackageName() {
      return this.packageName;
   }
}
