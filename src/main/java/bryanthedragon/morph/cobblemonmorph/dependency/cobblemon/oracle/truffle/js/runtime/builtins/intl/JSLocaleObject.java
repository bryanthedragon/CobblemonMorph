package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSLocaleObject extends JSNonProxyObject {
   private final JSLocale.InternalState internalState;

   protected JSLocaleObject(Shape shape, JSLocale.InternalState internalState) {
      super(shape);
      this.internalState = Objects.requireNonNull(internalState);
   }

   public JSLocale.InternalState getInternalState() {
      return this.internalState;
   }
}
