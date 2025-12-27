package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSBooleanObject;

public final class BooleanPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<BooleanPrototypeBuiltins.BooleanPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new BooleanPrototypeBuiltins();

   protected BooleanPrototypeBuiltins() {
      super(JSBoolean.PROTOTYPE_NAME, BooleanPrototypeBuiltins.BooleanPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, BooleanPrototypeBuiltins.BooleanPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case toString:
            return BooleanPrototypeBuiltinsFactory.JSBooleanToStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case valueOf:
            return BooleanPrototypeBuiltinsFactory.JSBooleanValueOfNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum BooleanPrototype implements BuiltinEnum<BooleanPrototypeBuiltins.BooleanPrototype> {
      toString(0),
      valueOf(0);

      private final int length;

      private BooleanPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSBooleanToStringNode extends JSBuiltinNode {
      public JSBooleanToStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object toString(JSBooleanObject thisObj) {
         return Strings.fromBoolean(JSBoolean.valueOf(thisObj));
      }

      @Specialization
      protected Object toStringPrimitive(boolean thisObj) {
         return JSRuntime.booleanToString(thisObj);
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected Object toStringForeignObject(Object thisObj, @CachedLibrary("thisObj") InteropLibrary interop) {
         if (interop.isBoolean(thisObj)) {
            try {
               return Strings.fromBoolean(interop.asBoolean(thisObj));
            } catch (UnsupportedMessageException var4) {
               throw Errors.createTypeErrorUnboxException(thisObj, var4, this);
            }
         } else {
            return this.toStringOther(thisObj);
         }
      }

      @Fallback
      protected String toStringOther(Object thisObj) {
         throw JSBoolean.noBooleanError();
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class JSBooleanValueOfNode extends JSBuiltinNode {
      public JSBooleanValueOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean valueOf(JSBooleanObject thisObj) {
         return JSBoolean.valueOf(thisObj);
      }

      @Specialization
      protected boolean valueOfPrimitive(boolean thisObj) {
         return thisObj;
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "InteropLibraryLimit")
      protected boolean valueOfForeignObject(Object thisObj, @CachedLibrary("thisObj") InteropLibrary interop) {
         if (interop.isBoolean(thisObj)) {
            try {
               return interop.asBoolean(thisObj);
            } catch (UnsupportedMessageException var4) {
               throw Errors.createTypeErrorUnboxException(thisObj, var4, this);
            }
         } else {
            return this.valueOfOther(thisObj);
         }
      }

      @Fallback
      protected boolean valueOfOther(Object thisObj) {
         throw JSBoolean.noBooleanError();
      }
   }
}
