package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSPluralRulesObject extends JSNonProxyObject {
   private final JSPluralRules.InternalState internalState;

   protected JSPluralRulesObject(Shape shape, JSPluralRules.InternalState internalState) {
      super(shape);
      this.internalState = Objects.requireNonNull(internalState);
   }

   public JSPluralRules.InternalState getInternalState() {
      return this.internalState;
   }
}
