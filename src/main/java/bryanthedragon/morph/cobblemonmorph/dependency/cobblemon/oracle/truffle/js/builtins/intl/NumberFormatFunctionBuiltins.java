package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;

public final class NumberFormatFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<NumberFormatFunctionBuiltins.NumberFormatFunction> {
   public static final JSBuiltinsContainer BUILTINS = new NumberFormatFunctionBuiltins();

   protected NumberFormatFunctionBuiltins() {
      super(JSNumberFormat.CLASS_NAME, NumberFormatFunctionBuiltins.NumberFormatFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, NumberFormatFunctionBuiltins.NumberFormatFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case supportedLocalesOf:
            return SupportedLocalesOfNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum NumberFormatFunction implements BuiltinEnum<NumberFormatFunctionBuiltins.NumberFormatFunction> {
      supportedLocalesOf(1);

      private final int length;

      private NumberFormatFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
