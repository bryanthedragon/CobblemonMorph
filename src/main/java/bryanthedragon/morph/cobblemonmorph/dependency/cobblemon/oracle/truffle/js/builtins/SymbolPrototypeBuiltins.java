package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.builtins.JSSymbolObject;

public final class SymbolPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<SymbolPrototypeBuiltins.SymbolPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new SymbolPrototypeBuiltins();

   protected SymbolPrototypeBuiltins() {
      super(JSSymbol.PROTOTYPE_NAME, SymbolPrototypeBuiltins.SymbolPrototype.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, SymbolPrototypeBuiltins.SymbolPrototype builtinEnum) {
      switch (builtinEnum) {
         case toString:
            return SymbolPrototypeBuiltinsFactory.SymbolToStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case valueOf:
            return SymbolPrototypeBuiltinsFactory.SymbolValueOfNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case _toPrimitive:
            return SymbolPrototypeBuiltinsFactory.SymbolToPrimitiveNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum SymbolPrototype implements BuiltinEnum<SymbolPrototypeBuiltins.SymbolPrototype> {
      toString(0),
      valueOf(0),
      _toPrimitive(1) {
         @Override
         public Object getKey() {
            return Symbol.SYMBOL_TO_PRIMITIVE;
         }

         @Override
         public boolean isWritable() {
            return false;
         }
      };

      private final int length;

      private SymbolPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class SymbolToPrimitiveNode extends JSBuiltinNode {
      private final ConditionProfile isSymbolProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isSymbolObjectProfile = ConditionProfile.createBinaryProfile();

      public SymbolToPrimitiveNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Symbol toPrimitive(Object thisObj) {
         if (this.isSymbolProfile.profile(thisObj instanceof Symbol)) {
            return (Symbol)thisObj;
         } else if (this.isSymbolObjectProfile.profile(JSSymbol.isJSSymbol(thisObj))) {
            return JSSymbol.getSymbolData((JSSymbolObject)thisObj);
         } else {
            throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
         }
      }
   }

   public abstract static class SymbolToStringNode extends JSBuiltinNode {
      private final ConditionProfile isSymbolObjectProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isPrimitiveSymbolProfile = ConditionProfile.createBinaryProfile();

      public SymbolToStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString toString(Object thisObj) {
         if (this.isSymbolObjectProfile.profile(JSSymbol.isJSSymbol(thisObj))) {
            return JSSymbol.getSymbolData((JSSymbolObject)thisObj).toTString();
         } else if (this.isPrimitiveSymbolProfile.profile(thisObj instanceof Symbol)) {
            return ((Symbol)thisObj).toTString();
         } else {
            throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
         }
      }
   }

   public abstract static class SymbolValueOfNode extends JSBuiltinNode {
      private final ConditionProfile isSymbolObjectProfile = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isPrimitiveSymbolProfile = ConditionProfile.createBinaryProfile();

      public SymbolValueOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Symbol valueOf(Object thisObj) {
         if (this.isSymbolObjectProfile.profile(JSSymbol.isJSSymbol(thisObj))) {
            return JSSymbol.getSymbolData((JSSymbolObject)thisObj);
         } else if (this.isPrimitiveSymbolProfile.profile(thisObj instanceof Symbol)) {
            return (Symbol)thisObj;
         } else {
            throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
         }
      }
   }
}
