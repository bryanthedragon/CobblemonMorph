package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZone;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public class TemporalTimeZoneFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<TemporalTimeZoneFunctionBuiltins.TemporalTimeZoneFunction> {
   public static final JSBuiltinsContainer BUILTINS = new TemporalTimeZoneFunctionBuiltins();

   protected TemporalTimeZoneFunctionBuiltins() {
      super(JSTemporalTimeZone.CLASS_NAME, TemporalTimeZoneFunctionBuiltins.TemporalTimeZoneFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalTimeZoneFunctionBuiltins.TemporalTimeZoneFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case from:
            return TemporalTimeZoneFunctionBuiltinsFactory.JSTemporalTimeZoneFromNodeGen.create(
               context, builtin, args().fixedArgs(1).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSTemporalTimeZoneFromNode extends JSBuiltinNode {
      public JSTemporalTimeZoneFromNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject from(Object item, @Cached("create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone) {
         return toTemporalTimeZone.executeDynamicObject(item);
      }
   }

   public static enum TemporalTimeZoneFunction implements BuiltinEnum<TemporalTimeZoneFunctionBuiltins.TemporalTimeZoneFunction> {
      from(1);

      private final int length;

      private TemporalTimeZoneFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
