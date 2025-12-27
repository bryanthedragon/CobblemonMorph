package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
final class HashIterator implements TruffleObject {
   final Object entriesIterator;
   private final long index;

   private HashIterator(Object entriesIterator, long index) {
      assert InteropLibrary.getUncached().isIterator(entriesIterator) : "EntriesIterator must be an iterator.";

      assert index >= 0L && index < 2L;

      this.entriesIterator = entriesIterator;
      this.index = index;
   }

   @ExportMessage
   boolean isIterator() {
      return true;
   }

   @ExportMessage
   boolean hasIteratorNextElement(@CachedLibrary("this.entriesIterator") InteropLibrary iterators) {
      try {
         return iterators.hasIteratorNextElement(this.entriesIterator);
      } catch (UnsupportedMessageException var3) {
         throw CompilerDirectives.shouldNotReachHere(var3);
      }
   }

   @ExportMessage
   Object getIteratorNextElement(@CachedLibrary("this.entriesIterator") InteropLibrary iterators, @CachedLibrary(limit = "1") InteropLibrary arrays) throws UnsupportedMessageException, StopIterationException {
      try {
         Object entry = iterators.getIteratorNextElement(this.entriesIterator);
         return arrays.readArrayElement(entry, this.index);
      } catch (InvalidArrayIndexException var4) {
         throw CompilerDirectives.shouldNotReachHere("Hash entry must have two array elements.", var4);
      }
   }

   static HashIterator keys(Object entriesIterator) {
      return new HashIterator(entriesIterator, 0L);
   }

   static HashIterator values(Object entriesIterator) {
      return new HashIterator(entriesIterator, 1L);
   }
}
