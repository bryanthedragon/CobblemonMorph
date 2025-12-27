package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenterObject;

public final class SegmenterPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<SegmenterPrototypeBuiltins.SegmenterPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new SegmenterPrototypeBuiltins();

   protected SegmenterPrototypeBuiltins() {
      super(JSSegmenter.PROTOTYPE_NAME, SegmenterPrototypeBuiltins.SegmenterPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, SegmenterPrototypeBuiltins.SegmenterPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case resolvedOptions:
            return SegmenterPrototypeBuiltinsFactory.JSSegmenterResolvedOptionsNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case segment:
            return SegmenterPrototypeBuiltinsFactory.JSSegmenterSegmentNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSSegmenterResolvedOptionsNode extends JSBuiltinNode {
      public JSSegmenterResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doResolvedOptions(JSSegmenterObject segmenter) {
         return JSSegmenter.resolvedOptions(this.getContext(), this.getRealm(), segmenter);
      }

      @Specialization(guards = "!isJSSegmenter(bummer)")
      public Object doResolvedOptions(Object bummer) {
         throw Errors.createTypeErrorSegmenterExpected();
      }
   }

   public abstract static class JSSegmenterSegmentNode extends JSBuiltinNode {
      public JSSegmenterSegmentNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object doSegmenter(JSSegmenterObject segmenter, Object value, @Cached("create()") JSToStringNode toStringNode) {
         TruffleString string = toStringNode.executeString(value);
         return JSSegmenter.createSegments(this.getContext(), this.getRealm(), segmenter, string);
      }

      @Specialization(guards = "!isJSSegmenter(bummer)")
      public Object doOther(Object bummer, Object value) {
         throw Errors.createTypeErrorSegmenterExpected();
      }
   }

   public static enum SegmenterPrototype implements BuiltinEnum<SegmenterPrototypeBuiltins.SegmenterPrototype> {
      resolvedOptions(0),
      segment(1);

      private final int length;

      private SegmenterPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
