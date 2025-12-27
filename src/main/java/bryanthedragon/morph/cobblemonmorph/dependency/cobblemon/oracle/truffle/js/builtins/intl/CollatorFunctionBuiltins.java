package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;

public final class CollatorFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<CollatorFunctionBuiltins.CollatorFunction> {
   public static final JSBuiltinsContainer BUILTINS = new CollatorFunctionBuiltins();

   protected CollatorFunctionBuiltins() {
      super(JSCollator.CLASS_NAME, CollatorFunctionBuiltins.CollatorFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, CollatorFunctionBuiltins.CollatorFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case supportedLocalesOf:
            return SupportedLocalesOfNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum CollatorFunction implements BuiltinEnum<CollatorFunctionBuiltins.CollatorFunction> {
      supportedLocalesOf(1);

      private final int length;

      private CollatorFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
