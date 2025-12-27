package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollatorObject;

public final class CollatorPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<CollatorPrototypeBuiltins.CollatorPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new CollatorPrototypeBuiltins();

   protected CollatorPrototypeBuiltins() {
      super(JSCollator.PROTOTYPE_NAME, CollatorPrototypeBuiltins.CollatorPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, CollatorPrototypeBuiltins.CollatorPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case resolvedOptions:
            return CollatorPrototypeBuiltinsFactory.JSCollatorResolvedOptionsNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum CollatorPrototype implements BuiltinEnum<CollatorPrototypeBuiltins.CollatorPrototype> {
      resolvedOptions(0);

      private final int length;

      private CollatorPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class JSCollatorResolvedOptionsNode extends JSBuiltinNode {
      public JSCollatorResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doResolvedOptions(JSCollatorObject collator) {
         return JSCollator.resolvedOptions(this.getContext(), this.getRealm(), collator);
      }

      @Fallback
      public Object doResolvedOptions(Object bummer) {
         throw Errors.createTypeError("Collator object expected.");
      }
   }
}
