package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public class TemporalPlainDateTimeFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<TemporalPlainDateTimeFunctionBuiltins.TemporalPlainDateTimeFunction> {
   public static final JSBuiltinsContainer BUILTINS = new TemporalPlainDateTimeFunctionBuiltins();

   protected TemporalPlainDateTimeFunctionBuiltins() {
      super(JSTemporalPlainDateTime.CLASS_NAME, TemporalPlainDateTimeFunctionBuiltins.TemporalPlainDateTimeFunction.class);
   }

   protected Object createNode(
      JSContext context,
      JSBuiltin builtin,
      boolean construct,
      boolean newTarget,
      TemporalPlainDateTimeFunctionBuiltins.TemporalPlainDateTimeFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case from:
            return TemporalPlainDateTimeFunctionBuiltinsFactory.JSTemporalPlainDateTimeFromNodeGen.create(
               context, builtin, args().fixedArgs(2).createArgumentNodes(context)
            );
         case compare:
            return TemporalPlainDateTimeFunctionBuiltinsFactory.JSTemporalPlainDateTimeCompareNodeGen.create(
               context, builtin, args().fixedArgs(2).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSTemporalPlainDateTimeCompareNode extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      public JSTemporalPlainDateTimeCompareNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected int compare(Object obj1, Object obj2, @Cached("create(getContext())") ToTemporalDateTimeNode toTemporalDateTime) {
         JSTemporalPlainDateTimeObject one = (JSTemporalPlainDateTimeObject)toTemporalDateTime.executeDynamicObject(obj1, Undefined.instance);
         JSTemporalPlainDateTimeObject two = (JSTemporalPlainDateTimeObject)toTemporalDateTime.executeDynamicObject(obj2, Undefined.instance);
         return TemporalUtil.compareISODateTime(
            one.getYear(),
            one.getMonth(),
            one.getDay(),
            one.getHour(),
            one.getMinute(),
            one.getSecond(),
            one.getMillisecond(),
            one.getMicrosecond(),
            one.getNanosecond(),
            two.getYear(),
            two.getMonth(),
            two.getDay(),
            two.getHour(),
            two.getMinute(),
            two.getSecond(),
            two.getMillisecond(),
            two.getMicrosecond(),
            two.getNanosecond()
         );
      }
   }

   public abstract static class JSTemporalPlainDateTimeFromNode extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      public JSTemporalPlainDateTimeFromNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object from(Object item, Object optParam, @Cached("create(getContext())") ToTemporalDateTimeNode toTemporalDateTime) {
         JSDynamicObject options = this.getOptionsObject(optParam);
         if (this.isObject(item) && JSTemporalPlainDateTime.isJSTemporalPlainDateTime(item)) {
            JSTemporalPlainDateTimeObject dtItem = (JSTemporalPlainDateTimeObject)item;
            TemporalUtil.toTemporalOverflow(options, this.getOptionNode());
            return JSTemporalPlainDateTime.create(
               this.getContext(),
               dtItem.getYear(),
               dtItem.getMonth(),
               dtItem.getDay(),
               dtItem.getHour(),
               dtItem.getMinute(),
               dtItem.getSecond(),
               dtItem.getMillisecond(),
               dtItem.getMicrosecond(),
               dtItem.getNanosecond(),
               dtItem.getCalendar(),
               this.errorBranch
            );
         } else {
            return toTemporalDateTime.executeDynamicObject(item, options);
         }
      }
   }

   public static enum TemporalPlainDateTimeFunction implements BuiltinEnum<TemporalPlainDateTimeFunctionBuiltins.TemporalPlainDateTimeFunction> {
      from(1),
      compare(2);

      private final int length;

      private TemporalPlainDateTimeFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
