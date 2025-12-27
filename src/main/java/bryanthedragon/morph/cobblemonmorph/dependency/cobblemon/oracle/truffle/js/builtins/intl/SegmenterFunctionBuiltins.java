package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.SupportedLocalesOfNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;

public final class SegmenterFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<SegmenterFunctionBuiltins.SegmenterFunction> {
   public static final JSBuiltinsContainer BUILTINS = new SegmenterFunctionBuiltins();

   protected SegmenterFunctionBuiltins() {
      super(JSSegmenter.CLASS_NAME, SegmenterFunctionBuiltins.SegmenterFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, SegmenterFunctionBuiltins.SegmenterFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case supportedLocalesOf:
            return SupportedLocalesOfNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum SegmenterFunction implements BuiltinEnum<SegmenterFunctionBuiltins.SegmenterFunction> {
      supportedLocalesOf(1);

      private final int length;

      private SegmenterFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
