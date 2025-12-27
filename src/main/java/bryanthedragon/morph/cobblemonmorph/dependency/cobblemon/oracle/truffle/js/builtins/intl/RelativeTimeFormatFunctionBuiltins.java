package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormat;

public final class RelativeTimeFormatFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<RelativeTimeFormatFunctionBuiltins.RelativeTimeFormatFunction> {
   public static final JSBuiltinsContainer BUILTINS = new RelativeTimeFormatFunctionBuiltins();

   protected RelativeTimeFormatFunctionBuiltins() {
      super(JSRelativeTimeFormat.CLASS_NAME, RelativeTimeFormatFunctionBuiltins.RelativeTimeFormatFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RelativeTimeFormatFunctionBuiltins.RelativeTimeFormatFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case supportedLocalesOf:
            return SupportedLocalesOfNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum RelativeTimeFormatFunction implements BuiltinEnum<RelativeTimeFormatFunctionBuiltins.RelativeTimeFormatFunction> {
      supportedLocalesOf(1);

      private final int length;

      private RelativeTimeFormatFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
