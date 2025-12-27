package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.AbstractRegexObject;
import java.util.Map;

@ExportLibrary(InteropLibrary.class)
public final class TruffleReadOnlyMap extends AbstractRegexObject {
   private final Map<String, ?> map;

   public TruffleReadOnlyMap(Map<String, ?> map) {
      this.map = map;
   }

   @ExportMessage
   boolean hasMembers() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMembers(boolean includeInternal) {
      return new TruffleReadOnlyKeysArray(Boundaries.setToArray(Boundaries.mapKeySet(this.map), new String[this.map.size()]));
   }

   @ExportMessage
   boolean isMemberReadable(String symbol) {
      return Boundaries.mapContainsKey(this.map, symbol);
   }

   @ExportMessage
   Object readMember(String symbol) {
      Object value = Boundaries.mapGet(this.map, symbol);
      if (value == null) {
         throw CompilerDirectives.shouldNotReachHere();
      } else {
         return value;
      }
   }

   @Override
   public String toString() {
      return "TRegexReadOnlyMap";
   }
}
