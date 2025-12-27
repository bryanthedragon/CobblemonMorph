package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSGlobalObject extends JSNonProxyObject {
   protected JSGlobalObject(Shape shape) {
      super(shape);
   }

   @Override
   public TruffleString getClassName() {
      return JSGlobal.CLASS_NAME;
   }
}
