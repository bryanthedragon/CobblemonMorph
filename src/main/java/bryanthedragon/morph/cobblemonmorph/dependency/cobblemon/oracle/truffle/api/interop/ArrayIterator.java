package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
final class ArrayIterator implements TruffleObject {
   final Object array;
   private long currentItemIndex;

   ArrayIterator(Object array) {
      this.array = array;

      assert InteropLibrary.getUncached().hasArrayElements(array) : "Array must have array elements.";
   }

   @ExportMessage
   boolean isIterator() {
      return true;
   }

   @ExportMessage
   boolean hasIteratorNextElement(@CachedLibrary("this.array") InteropLibrary arrays) {
      try {
         return this.currentItemIndex < arrays.getArraySize(this.array);
      } catch (UnsupportedMessageException var3) {
         throw CompilerDirectives.shouldNotReachHere(var3);
      }
   }

   @ExportMessage
   Object getIteratorNextElement(@CachedLibrary("this.array") InteropLibrary arrays) throws UnsupportedMessageException, StopIterationException {
      try {
         long size = arrays.getArraySize(this.array);
         if (this.currentItemIndex >= size) {
            throw StopIterationException.create();
         } else {
            Object res = arrays.readArrayElement(this.array, this.currentItemIndex);
            this.currentItemIndex++;
            return res;
         }
      } catch (UnsupportedMessageException var5) {
         throw CompilerDirectives.shouldNotReachHere(var5);
      } catch (InvalidArrayIndexException var6) {
         throw UnsupportedMessageException.create();
      }
   }
}
