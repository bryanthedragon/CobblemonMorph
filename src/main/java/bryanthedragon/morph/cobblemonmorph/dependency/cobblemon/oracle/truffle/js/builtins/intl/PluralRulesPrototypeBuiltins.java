package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRules;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRulesObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class PluralRulesPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<PluralRulesPrototypeBuiltins.PluralRulesPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new PluralRulesPrototypeBuiltins();

   protected PluralRulesPrototypeBuiltins() {
      super(JSPluralRules.PROTOTYPE_NAME, PluralRulesPrototypeBuiltins.PluralRulesPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, PluralRulesPrototypeBuiltins.PluralRulesPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case resolvedOptions:
            return PluralRulesPrototypeBuiltinsFactory.JSPluralRulesResolvedOptionsNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case select:
            return PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case selectRange:
            return PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSPluralRulesResolvedOptionsNode extends JSBuiltinNode {
      public JSPluralRulesResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doResolvedOptions(JSPluralRulesObject pluralRules) {
         return JSPluralRules.resolvedOptions(this.getContext(), this.getRealm(), pluralRules);
      }

      @Fallback
      public Object throwTypeError(Object bummer) {
         throw Errors.createTypeErrorTypeXExpected(JSPluralRules.CLASS_NAME);
      }
   }

   public abstract static class JSPluralRulesSelectNode extends JSBuiltinNode {
      public JSPluralRulesSelectNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doSelect(JSPluralRulesObject pluralRules, Object value) {
         return JSPluralRules.select(pluralRules, value);
      }

      @Fallback
      public Object throwTypeError(Object bummer, Object value) {
         throw Errors.createTypeErrorTypeXExpected(JSPluralRules.CLASS_NAME);
      }
   }

   public abstract static class JSPluralRulesSelectRangeNode extends JSBuiltinNode {
      public JSPluralRulesSelectRangeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doSelectRange(
         JSPluralRulesObject pluralRules,
         Object start,
         Object end,
         @Cached JSToNumberNode startToNumber,
         @Cached JSToNumberNode endToNumber,
         @Cached BranchProfile errorBranch
      ) {
         if (start != Undefined.instance && end != Undefined.instance) {
            double x = JSRuntime.doubleValue(startToNumber.executeNumber(start));
            double y = JSRuntime.doubleValue(endToNumber.executeNumber(end));
            if (!Double.isNaN(x) && !Double.isNaN(y)) {
               return JSPluralRules.selectRange(pluralRules, x, y);
            } else {
               errorBranch.enter();
               throw Errors.createRangeError("invalid range");
            }
         } else {
            errorBranch.enter();
            throw Errors.createTypeError("invalid range");
         }
      }

      @Fallback
      public Object throwTypeError(Object bummer, Object start, Object end) {
         throw Errors.createTypeErrorTypeXExpected(JSPluralRules.CLASS_NAME);
      }
   }

   public static enum PluralRulesPrototype implements BuiltinEnum<PluralRulesPrototypeBuiltins.PluralRulesPrototype> {
      resolvedOptions(0),
      select(1),
      selectRange(2);

      private final int length;

      private PluralRulesPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         return selectRange == this ? 14 : BuiltinEnum.super.getECMAScriptVersion();
      }
   }
}
