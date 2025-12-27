package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormatObject;

public final class RelativeTimeFormatPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<RelativeTimeFormatPrototypeBuiltins.RelativeTimeFormatPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new RelativeTimeFormatPrototypeBuiltins();

   protected RelativeTimeFormatPrototypeBuiltins() {
      super(JSRelativeTimeFormat.PROTOTYPE_NAME, RelativeTimeFormatPrototypeBuiltins.RelativeTimeFormatPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RelativeTimeFormatPrototypeBuiltins.RelativeTimeFormatPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case resolvedOptions:
            return RelativeTimeFormatPrototypeBuiltinsFactory.JSRelativeTimeFormatResolvedOptionsNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case format:
            return RelativeTimeFormatPrototypeBuiltinsFactory.JSRelativeTimeFormatFormatNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case formatToParts:
            return RelativeTimeFormatPrototypeBuiltinsFactory.JSRelativeTimeFormatFormatToPartsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSRelativeTimeFormatFormatNode extends JSBuiltinNode {
      public JSRelativeTimeFormatFormatNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public TruffleString doFormat(
         JSRelativeTimeFormatObject relativeTimeFormat,
         Object value,
         Object unit,
         @Cached("create()") JSToStringNode toStringNode,
         @Cached("create()") JSToNumberNode toNumberNode
      ) {
         return JSRelativeTimeFormat.format(
            relativeTimeFormat, JSRuntime.doubleValue(toNumberNode.executeNumber(value)), Strings.toJavaString(toStringNode.executeString(unit))
         );
      }

      @Specialization(guards = "!isJSRelativeTimeFormat(bummer)")
      public Object throwTypeError(Object bummer, Object value, Object unit) {
         throw Errors.createTypeErrorTypeXExpected(JSRelativeTimeFormat.CLASS_NAME);
      }
   }

   public abstract static class JSRelativeTimeFormatFormatToPartsNode extends JSBuiltinNode {
      public JSRelativeTimeFormatFormatToPartsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doFormatToParts(
         JSRelativeTimeFormatObject relativeTimeFormat,
         Object value,
         Object unit,
         @Cached("create()") JSToStringNode toStringNode,
         @Cached("create()") JSToNumberNode toNumberNode
      ) {
         double amount = JSRuntime.doubleValue(toNumberNode.executeNumber(value));
         TruffleString unitString = toStringNode.executeString(unit);
         return JSRelativeTimeFormat.formatToParts(this.getContext(), this.getRealm(), relativeTimeFormat, amount, Strings.toJavaString(unitString));
      }

      @Specialization(guards = "!isJSRelativeTimeFormat(bummer)")
      public Object throwTypeError(Object bummer, Object value, Object unit) {
         throw Errors.createTypeErrorTypeXExpected(JSRelativeTimeFormat.CLASS_NAME);
      }
   }

   public abstract static class JSRelativeTimeFormatResolvedOptionsNode extends JSBuiltinNode {
      public JSRelativeTimeFormatResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doResolvedOptions(JSRelativeTimeFormatObject relativeTimeFormat) {
         return JSRelativeTimeFormat.resolvedOptions(this.getContext(), this.getRealm(), relativeTimeFormat);
      }

      @Specialization(guards = "!isJSRelativeTimeFormat(bummer)")
      public Object doResolvedOptions(Object bummer) {
         throw Errors.createTypeErrorTypeXExpected(JSRelativeTimeFormat.CLASS_NAME);
      }
   }

   public static enum RelativeTimeFormatPrototype implements BuiltinEnum<RelativeTimeFormatPrototypeBuiltins.RelativeTimeFormatPrototype> {
      resolvedOptions(0),
      format(2),
      formatToParts(2);

      private final int length;

      private RelativeTimeFormatPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
