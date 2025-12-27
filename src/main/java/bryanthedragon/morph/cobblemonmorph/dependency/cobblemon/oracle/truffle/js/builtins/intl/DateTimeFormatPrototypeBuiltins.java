package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormatObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class DateTimeFormatPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<DateTimeFormatPrototypeBuiltins.DateTimeFormatPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new DateTimeFormatPrototypeBuiltins();

   protected DateTimeFormatPrototypeBuiltins() {
      super(JSDateTimeFormat.PROTOTYPE_NAME, DateTimeFormatPrototypeBuiltins.DateTimeFormatPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DateTimeFormatPrototypeBuiltins.DateTimeFormatPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case formatToParts:
            return DateTimeFormatPrototypeBuiltinsFactory.JSDateTimeFormatFormatToPartsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case formatRange:
            return DateTimeFormatPrototypeBuiltinsFactory.JSDateTimeFormatFormatRangeNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case formatRangeToParts:
            return DateTimeFormatPrototypeBuiltinsFactory.JSDateTimeFormatFormatRangeToPartsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case resolvedOptions:
            return DateTimeFormatPrototypeBuiltinsFactory.JSDateTimeFormatResolvedOptionsNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public static enum DateTimeFormatPrototype implements BuiltinEnum<DateTimeFormatPrototypeBuiltins.DateTimeFormatPrototype> {
      resolvedOptions(0),
      formatToParts(1),
      formatRange(2),
      formatRangeToParts(2);

      private final int length;

      private DateTimeFormatPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public int getECMAScriptVersion() {
         switch (this) {
            case formatToParts:
               return 8;
            case formatRange:
            case formatRangeToParts:
               return 12;
            default:
               return BuiltinEnum.super.getECMAScriptVersion();
         }
      }
   }

   public abstract static class JSDateTimeFormatFormatRangeNode extends JSBuiltinNode {
      public JSDateTimeFormatFormatRangeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public TruffleString doFormatRange(
         JSDateTimeFormatObject dateTimeFormat,
         Object startDate,
         Object endDate,
         @Cached JSToNumberNode startDateToNumberNode,
         @Cached JSToNumberNode endDateToNumberNode,
         @Cached BranchProfile errorBranch
      ) {
         if (startDate != Undefined.instance && endDate != Undefined.instance) {
            Number xNumber = startDateToNumberNode.executeNumber(startDate);
            Number yNumber = endDateToNumberNode.executeNumber(endDate);
            double x = JSDate.timeClip(JSRuntime.toDouble(xNumber));
            double y = JSDate.timeClip(JSRuntime.toDouble(yNumber));
            if (!Double.isNaN(x) && !Double.isNaN(y)) {
               return JSDateTimeFormat.formatRange(dateTimeFormat, x, y);
            } else {
               errorBranch.enter();
               throw Errors.createRangeErrorInvalidTimeValue();
            }
         } else {
            errorBranch.enter();
            throw Errors.createTypeErrorInvalidTimeValue();
         }
      }

      @Fallback
      public Object throwTypeError(Object bummer, Object startDate, Object endDate) {
         throw Errors.createTypeErrorTypeXExpected(JSDateTimeFormat.CLASS_NAME);
      }
   }

   public abstract static class JSDateTimeFormatFormatRangeToPartsNode extends JSBuiltinNode {
      public JSDateTimeFormatFormatRangeToPartsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doFormatRangeToParts(
         JSDateTimeFormatObject dateTimeFormat,
         Object startDate,
         Object endDate,
         @Cached JSToNumberNode startDateToNumberNode,
         @Cached JSToNumberNode endDateToNumberNode,
         @Cached BranchProfile errorBranch
      ) {
         if (startDate != Undefined.instance && endDate != Undefined.instance) {
            Number xNumber = startDateToNumberNode.executeNumber(startDate);
            Number yNumber = endDateToNumberNode.executeNumber(endDate);
            double x = JSDate.timeClip(JSRuntime.toDouble(xNumber));
            double y = JSDate.timeClip(JSRuntime.toDouble(yNumber));
            if (!Double.isNaN(x) && !Double.isNaN(y)) {
               return JSDateTimeFormat.formatRangeToParts(this.getContext(), this.getRealm(), dateTimeFormat, x, y);
            } else {
               errorBranch.enter();
               throw Errors.createRangeErrorInvalidTimeValue();
            }
         } else {
            errorBranch.enter();
            throw Errors.createTypeErrorInvalidTimeValue();
         }
      }

      @Fallback
      public Object throwTypeError(Object bummer, Object startDate, Object endDate) {
         throw Errors.createTypeErrorTypeXExpected(JSDateTimeFormat.CLASS_NAME);
      }
   }

   public abstract static class JSDateTimeFormatFormatToPartsNode extends JSBuiltinNode {
      public JSDateTimeFormatFormatToPartsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doFormatToParts(JSDateTimeFormatObject dateTimeFormat, Object value) {
         return JSDateTimeFormat.formatToParts(this.getContext(), this.getRealm(), dateTimeFormat, value, null);
      }

      @Fallback
      public Object throwTypeError(Object bummer, Object value) {
         throw Errors.createTypeErrorTypeXExpected(JSDateTimeFormat.CLASS_NAME);
      }
   }

   public abstract static class JSDateTimeFormatResolvedOptionsNode extends JSBuiltinNode {
      public JSDateTimeFormatResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doResolvedOptions(JSDateTimeFormatObject dateTimeFormat) {
         return JSDateTimeFormat.resolvedOptions(this.getContext(), this.getRealm(), dateTimeFormat);
      }

      @Fallback
      public Object throwTypeError(Object bummer) {
         throw Errors.createTypeErrorTypeXExpected(JSDateTimeFormat.CLASS_NAME);
      }
   }
}
