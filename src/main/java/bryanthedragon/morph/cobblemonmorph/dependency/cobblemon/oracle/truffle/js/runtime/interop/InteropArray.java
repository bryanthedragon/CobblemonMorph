package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import java.util.List;

@ExportLibrary(InteropLibrary.class)
public final class InteropArray implements TruffleObject {
   final Object[] array;

   private InteropArray(Object[] array) {
      this.array = array;
   }

   public static InteropArray create(Object[] array) {
      return new InteropArray(array);
   }

   @CompilerDirectives.TruffleBoundary
   public static InteropArray create(List<? extends Object> list) {
      return new InteropArray(list.toArray(ScriptArray.EMPTY_OBJECT_ARRAY));
   }

   @ExportMessage
   boolean hasArrayElements() {
      return true;
   }

   @ExportMessage
   Object readArrayElement(long index) throws InvalidArrayIndexException {
      if (!this.isArrayElementReadable(index)) {
         throw InvalidArrayIndexException.create(index);
      } else {
         return this.array[(int)index];
      }
   }

   @ExportMessage
   long getArraySize() {
      return this.array.length;
   }

   @ExportMessage
   boolean isArrayElementReadable(long index) {
      return index >= 0L && index < this.array.length;
   }
}
