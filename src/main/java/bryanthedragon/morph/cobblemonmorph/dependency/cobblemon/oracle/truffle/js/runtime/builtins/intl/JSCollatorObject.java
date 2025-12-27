package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSCollatorObject extends JSNonProxyObject {
   private final JSCollator.InternalState internalState;

   protected JSCollatorObject(Shape shape, JSCollator.InternalState internalState) {
      super(shape);
      this.internalState = Objects.requireNonNull(internalState);
   }

   public JSCollator.InternalState getInternalState() {
      return this.internalState;
   }
}
