package com.oracle.truffle.js.runtime.builtins.intl;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.BreakIterator;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSSegmenterObject extends JSNonProxyObject {
   private final JSSegmenter.InternalState internalState;
   private BreakIterator breakIterator;

   protected JSSegmenterObject(Shape shape, JSSegmenter.InternalState internalState) {
      super(shape);
      this.internalState = Objects.requireNonNull(internalState);
   }

   public JSSegmenter.InternalState getInternalState() {
      return this.internalState;
   }

   public BreakIterator getBreakIterator() {
      if (this.breakIterator == null) {
         this.breakIterator = JSSegmenter.createBreakIterator(this);
      }

      return this.breakIterator;
   }
}
