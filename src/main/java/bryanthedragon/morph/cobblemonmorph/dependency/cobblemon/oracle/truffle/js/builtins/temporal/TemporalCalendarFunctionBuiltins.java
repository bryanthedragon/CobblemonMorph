package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public class TemporalCalendarFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<TemporalCalendarFunctionBuiltins.TemporalCalendarFunction> {
   public static final JSBuiltinsContainer BUILTINS = new TemporalCalendarFunctionBuiltins();

   protected TemporalCalendarFunctionBuiltins() {
      super(JSTemporalCalendar.CLASS_NAME, TemporalCalendarFunctionBuiltins.TemporalCalendarFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalCalendarFunctionBuiltins.TemporalCalendarFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case from:
            return TemporalCalendarFunctionBuiltinsFactory.JSTemporalCalendarFromNodeGen.create(
               context, builtin, args().fixedArgs(1).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSTemporalCalendarFromNode extends JSBuiltinNode {
      public JSTemporalCalendarFromNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject from(Object item, @Cached("create(getContext())") ToTemporalCalendarNode toTemporalCalendar) {
         return toTemporalCalendar.executeDynamicObject(item);
      }
   }

   public static enum TemporalCalendarFunction implements BuiltinEnum<TemporalCalendarFunctionBuiltins.TemporalCalendarFunction> {
      from(1);

      private final int length;

      private TemporalCalendarFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
