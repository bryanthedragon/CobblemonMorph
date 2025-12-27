package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSDate;

public final class DateFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<DateFunctionBuiltins.DateFunction> {
   public static final JSBuiltinsContainer BUILTINS = new DateFunctionBuiltins();

   protected DateFunctionBuiltins() {
      super(JSDate.CLASS_NAME, DateFunctionBuiltins.DateFunction.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DateFunctionBuiltins.DateFunction builtinEnum) {
      switch (builtinEnum) {
         case parse:
            return DateFunctionBuiltinsFactory.DateParseNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case now:
            return DateFunctionBuiltinsFactory.DateNowNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case UTC:
            return DateFunctionBuiltinsFactory.DateUTCNodeGen.create(context, builtin, args().varArgs().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum DateFunction implements BuiltinEnum<DateFunctionBuiltins.DateFunction> {
      parse(1),
      now(0),
      UTC(7);

      private final int length;

      private DateFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class DateNowNode extends JSBuiltinNode {
      public DateNowNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      @CompilerDirectives.TruffleBoundary
      protected double now() {
         return this.getRealm().currentTimeMillis();
      }
   }

   public abstract static class DateParseNode extends JSBuiltinNode {
      private final ConditionProfile gotFieldsProfile = ConditionProfile.createBinaryProfile();

      public DateParseNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected double parse(Object parseDate, @Cached JSToStringNode toStringNode) {
         TruffleString dateString = toStringNode.executeString(parseDate);
         Integer[] fields = this.getContext().getEvaluator().parseDate(this.getRealm(), Strings.toJavaString(Strings.lazyTrim(dateString)), false);
         return this.gotFieldsProfile.profile(fields != null)
            ? JSDate.makeDate(
               fields[0].intValue(),
               fields[1].intValue(),
               fields[2].intValue(),
               fields[3].intValue(),
               fields[4].intValue(),
               fields[5].intValue(),
               fields[6].intValue(),
               fields[7]
            )
            : Double.NaN;
      }
   }

   public abstract static class DateUTCNode extends JSBuiltinNode {
      public DateUTCNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected double utc(Object[] args, @Cached("create()") JSToNumberNode toNumberNode) {
         double[] argsEvaluated = new double[args.length];
         boolean isNaN = false;

         for (int i = 0; i < args.length; i++) {
            double d = JSRuntime.doubleValue(toNumberNode.executeNumber(args[i]));
            if (Double.isNaN(d)) {
               isNaN = true;
            }

            argsEvaluated[i] = d;
         }

         return isNaN ? Double.NaN : JSDate.executeConstructor(argsEvaluated, true);
      }
   }
}
