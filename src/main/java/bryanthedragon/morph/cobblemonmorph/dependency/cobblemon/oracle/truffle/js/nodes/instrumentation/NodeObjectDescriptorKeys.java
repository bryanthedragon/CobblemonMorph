package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Map;

@ExportLibrary(InteropLibrary.class)
public final class NodeObjectDescriptorKeys implements TruffleObject {
   private final Object[] keys;

   NodeObjectDescriptorKeys(Map<String, Object> from) {
      this.keys = from.keySet().toArray();
   }

   @ExportMessage
   boolean hasArrayElements() {
      return true;
   }

   @ExportMessage
   long getArraySize() {
      return this.keys.length;
   }

   @ExportMessage
   boolean isArrayElementReadable(long index) {
      return index >= 0L && index < this.getArraySize();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object readArrayElement(long index) throws InvalidArrayIndexException {
      if (!this.isArrayElementReadable(index)) {
         throw InvalidArrayIndexException.create(index);
      } else {
         return this.keys[(int)index];
      }
   }
}
