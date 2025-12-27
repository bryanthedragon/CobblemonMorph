package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.List;

@ExportLibrary(InteropLibrary.class)
public final class InteropList implements TruffleObject {
   final List<? extends Object> list;

   private InteropList(List<? extends Object> list) {
      this.list = list;
   }

   public static TruffleObject create(List<? extends Object> list) {
      return new InteropList(list);
   }

   @ExportMessage
   boolean hasArrayElements() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object readArrayElement(long index) throws InvalidArrayIndexException {
      if (!this.isArrayElementReadable(index)) {
         throw InvalidArrayIndexException.create(index);
      } else {
         return this.list.get((int)index);
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   long getArraySize() {
      return this.list.size();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean isArrayElementReadable(long index) {
      return index >= 0L && index < this.list.size();
   }
}
