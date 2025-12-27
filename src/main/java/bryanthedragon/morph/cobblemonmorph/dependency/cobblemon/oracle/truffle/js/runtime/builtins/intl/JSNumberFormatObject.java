package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSNumberFormatObject extends JSNonProxyObject {
   private final JSNumberFormat.InternalState internalState;

   protected JSNumberFormatObject(Shape shape, JSNumberFormat.InternalState internalState) {
      super(shape);
      this.internalState = Objects.requireNonNull(internalState);
   }

   public JSNumberFormat.InternalState getInternalState() {
      return this.internalState;
   }
}
