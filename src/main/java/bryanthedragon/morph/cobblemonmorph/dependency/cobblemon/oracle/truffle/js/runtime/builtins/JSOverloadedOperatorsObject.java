package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSOrdinaryObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;

public final class JSOverloadedOperatorsObject extends JSOrdinaryObject {
   @DynamicObject.DynamicField
   Object o0;
   @DynamicObject.DynamicField
   Object o1;
   @DynamicObject.DynamicField
   Object o2;
   @DynamicObject.DynamicField
   Object o3;
   @DynamicObject.DynamicField
   long p0;
   @DynamicObject.DynamicField
   long p1;
   @DynamicObject.DynamicField
   long p2;
   private final OperatorSet operatorSet;

   private JSOverloadedOperatorsObject(Shape shape, OperatorSet operatorSet) {
      super(shape);
      this.operatorSet = operatorSet;
   }

   public OperatorSet getOperatorSet() {
      return this.operatorSet;
   }

   public int getOperatorCounter() {
      return this.getOperatorSet().getOperatorCounter();
   }

   public boolean matchesOperatorCounter(int operatorCounter) {
      return this.getOperatorSet().getOperatorCounter() == operatorCounter;
   }

   public static boolean hasOverloadedOperators(Object value) {
      return value instanceof JSOverloadedOperatorsObject;
   }

   public static JSOverloadedOperatorsObject create(JSContext context, Shape shape, OperatorSet operatorSet) {
      JSOverloadedOperatorsObject object = new JSOverloadedOperatorsObject(shape, operatorSet);
      return context.trackAllocation(object);
   }

   @Override
   protected JSObject copyWithoutProperties(Shape shape) {
      return new JSOverloadedOperatorsObject(shape, this.operatorSet);
   }
}
