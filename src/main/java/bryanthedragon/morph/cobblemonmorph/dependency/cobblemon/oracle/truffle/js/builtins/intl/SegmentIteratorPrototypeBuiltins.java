package com.oracle.truffle.js.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.BreakIterator;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.intl.CreateSegmentDataObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmentIteratorObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class SegmentIteratorPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<SegmentIteratorPrototypeBuiltins.SegmentIteratorPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new SegmentIteratorPrototypeBuiltins();

   protected SegmentIteratorPrototypeBuiltins() {
      super(JSSegmenter.ITERATOR_PROTOTYPE_NAME, SegmentIteratorPrototypeBuiltins.SegmentIteratorPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, SegmentIteratorPrototypeBuiltins.SegmentIteratorPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case next:
            return SegmentIteratorPrototypeBuiltinsFactory.SegmentIteratorNextNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         default:
            return null;
      }
   }

   public abstract static class SegmentIteratorNextNode extends JSBuiltinNode {
      @Node.Child
      protected CreateIterResultObjectNode createIterResultObjectNode;

      public SegmentIteratorNextNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
      }

      @Specialization
      protected JSDynamicObject doSegmentIterator(
         VirtualFrame frame, JSSegmentIteratorObject iteratorObj, @Cached("create(getContext())") CreateSegmentDataObjectNode createNextValueNode
      ) {
         JSSegmenter.IteratorState iterator = iteratorObj.getIteratorState();
         TruffleString iteratedString = iterator.getIteratedString();
         BreakIterator icuIterator = iterator.getBreakIterator();
         JSSegmenter.Granularity segmenterGranularity = iterator.getSegmenterGranularity();
         int startIndex = findBoundaryCurrent(icuIterator);
         int endIndex = findBoundaryNext(icuIterator);
         boolean done = endIndex == -1;
         Object nextValue;
         if (done) {
            nextValue = Undefined.instance;
         } else {
            nextValue = createNextValueNode.execute(icuIterator, segmenterGranularity, iteratedString, startIndex, endIndex);
         }

         return this.createIterResultObjectNode.execute(frame, nextValue, done);
      }

      @Specialization(guards = "!isJSSegmentIterator(iterator)")
      protected JSDynamicObject doIncompatibleReceiver(Object iterator) {
         throw Errors.createTypeErrorTypeXExpected(JSSegmenter.ITERATOR_CLASS_NAME);
      }

      @CompilerDirectives.TruffleBoundary
      private static int findBoundaryCurrent(BreakIterator breakIterator) {
         return breakIterator.current();
      }

      @CompilerDirectives.TruffleBoundary
      private static int findBoundaryNext(BreakIterator breakIterator) {
         return breakIterator.next();
      }
   }

   public static enum SegmentIteratorPrototype implements BuiltinEnum<SegmentIteratorPrototypeBuiltins.SegmentIteratorPrototype> {
      next(0);

      private final int length;

      private SegmentIteratorPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }
}
