package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public final class InteropArrayIndexIterator implements TruffleObject {
   final Object array;
   private long cursor;

   private InteropArrayIndexIterator(Object array) {
      this.array = array;
   }

   public static InteropArrayIndexIterator create(Object array) {
      return new InteropArrayIndexIterator(array);
   }

   @ExportMessage
   boolean isIterator() {
      return true;
   }

   @ExportMessage
   boolean hasIteratorNextElement(@CachedLibrary("this.array") InteropLibrary interop) {
      try {
         long currentSize = interop.getArraySize(this.array);
         return this.cursor < currentSize;
      } catch (UnsupportedMessageException var4) {
         return false;
      }
   }

   @ExportMessage
   Object getIteratorNextElement(@CachedLibrary("this.array") InteropLibrary interop) throws StopIterationException {
      if (this.hasIteratorNextElement(interop)) {
         return this.cursor++;
      } else {
         throw StopIterationException.create();
      }
   }
}
