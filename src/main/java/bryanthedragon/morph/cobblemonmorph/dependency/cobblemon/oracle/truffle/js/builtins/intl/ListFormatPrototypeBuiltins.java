package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSStringListFromIterableNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormatObject;
import java.util.List;

public final class ListFormatPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<ListFormatPrototypeBuiltins.ListFormatPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new ListFormatPrototypeBuiltins();

   protected ListFormatPrototypeBuiltins() {
      super(JSListFormat.PROTOTYPE_NAME, ListFormatPrototypeBuiltins.ListFormatPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ListFormatPrototypeBuiltins.ListFormatPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case resolvedOptions:
            return ListFormatPrototypeBuiltinsFactory.JSListFormatResolvedOptionsNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case format:
            return ListFormatPrototypeBuiltinsFactory.JSListFormatFormatNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case formatToParts:
            return ListFormatPrototypeBuiltinsFactory.JSListFormatFormatToPartsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSListFormatFormatNode extends JSBuiltinNode {
      public JSListFormatFormatNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public TruffleString doFormat(
         JSListFormatObject listFormat, Object value, @Cached("create(getContext())") JSStringListFromIterableNode strListFromIterableNode
      ) {
         List<String> list = strListFromIterableNode.executeIterable(value);
         return JSListFormat.format(listFormat, list);
      }

      @Fallback
      public Object throwTypeError(Object bummer, Object value) {
         throw Errors.createTypeErrorTypeXExpected(JSListFormat.CLASS_NAME);
      }
   }

   public abstract static class JSListFormatFormatToPartsNode extends JSBuiltinNode {
      public JSListFormatFormatToPartsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doFormatToParts(
         JSListFormatObject listFormat, Object value, @Cached("create(getContext())") JSStringListFromIterableNode strListFromIterableNode
      ) {
         List<String> list = strListFromIterableNode.executeIterable(value);
         return JSListFormat.formatToParts(this.getContext(), this.getRealm(), listFormat, list);
      }

      @Fallback
      public Object throwTypeError(Object bummer, Object value) {
         throw Errors.createTypeErrorTypeXExpected(JSListFormat.CLASS_NAME);
      }
   }

   public abstract static class JSListFormatResolvedOptionsNode extends JSBuiltinNode {
      public JSListFormatResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doResolvedOptions(JSListFormatObject listFormat) {
         return JSListFormat.resolvedOptions(this.getContext(), this.getRealm(), listFormat);
      }

      @Fallback
      public Object throwTypeError(Object bummer) {
         throw Errors.createTypeErrorTypeXExpected(JSListFormat.CLASS_NAME);
      }
   }

   public static enum ListFormatPrototype implements BuiltinEnum<ListFormatPrototypeBuiltins.ListFormatPrototype> {
      resolvedOptions(0),
      format(1),
      formatToParts(1);

      private final int length;

      private ListFormatPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
