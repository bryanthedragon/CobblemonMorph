package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSWeakRef;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class WeakRefPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<WeakRefPrototypeBuiltins.WeakRefPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new WeakRefPrototypeBuiltins();

   protected WeakRefPrototypeBuiltins() {
      super(JSWeakRef.PROTOTYPE_NAME, WeakRefPrototypeBuiltins.WeakRefPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WeakRefPrototypeBuiltins.WeakRefPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case deref:
            return WeakRefPrototypeBuiltinsFactory.JSWeakRefDerefNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class JSWeakRefDerefNode extends WeakRefPrototypeBuiltins.JSWeakRefOperation {
      public JSWeakRefDerefNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSWeakRef(thisObj)")
      protected JSDynamicObject deref(JSDynamicObject thisObj) {
         Object referent = JSWeakRef.getInternalWeakRef(thisObj).get();
         if (referent != null) {
            this.getContext().addWeakRefTargetToSet(referent);
            return (JSDynamicObject)referent;
         } else {
            return Undefined.instance;
         }
      }

      @Specialization(guards = "!isJSWeakRef(thisObj)")
      protected static JSDynamicObject notWeakRef(Object thisObj) {
         throw Errors.createTypeError("WeakRef expected");
      }
   }

   public abstract static class JSWeakRefOperation extends JSBuiltinNode {
      public JSWeakRefOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }
   }

   public static enum WeakRefPrototype implements BuiltinEnum<WeakRefPrototypeBuiltins.WeakRefPrototype> {
      deref(0);

      private final int length;

      private WeakRefPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
