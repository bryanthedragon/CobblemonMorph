package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.AbstractRegexObject;
import java.util.Map;
import java.util.Map.Entry;

@ExportLibrary(InteropLibrary.class)
public final class TruffleSmallReadOnlyStringToIntMap extends AbstractRegexObject {
   public static final int MAX_SIZE = 8;
   private final TruffleReadOnlyKeysArray keys;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final String[] map;

   private TruffleSmallReadOnlyStringToIntMap(String[] keys, String[] map) {
      this.keys = new TruffleReadOnlyKeysArray(keys);
      this.map = map;
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean canCreate(Map<String, Integer> map) {
      return maxValue(map) < 8;
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleSmallReadOnlyStringToIntMap create(Map<String, Integer> argMap) {
      String[] keys = new String[argMap.size()];
      String[] map = new String[maxValue(argMap) + 1];

      assert map.length <= 8;

      int i = 0;

      for (Entry<String, Integer> entry : argMap.entrySet()) {
         keys[i++] = entry.getKey();

         assert map[entry.getValue()] == null;

         map[entry.getValue()] = entry.getKey();
      }

      return new TruffleSmallReadOnlyStringToIntMap(keys, map);
   }

   @CompilerDirectives.TruffleBoundary
   private static int maxValue(Map<String, Integer> map) {
      int max = 0;

      for (int i : map.values()) {
         max = Math.max(max, i);
      }

      return max;
   }

   @ExportMessage
   boolean hasMembers() {
      return true;
   }

   @ExportMessage
   Object getMembers(boolean includeInternal) {
      return this.keys;
   }

   @ExportMessage
   boolean isMemberReadable(String symbol) {
      return this.keys.contains(symbol);
   }

   @ExportMessage
   int readMember(String symbol) {
      for (int i = 0; i < this.map.length; i++) {
         if (this.map[i] != null && this.map[i].equals(symbol)) {
            return i;
         }
      }

      throw CompilerDirectives.shouldNotReachHere();
   }

   @Override
   public String toString() {
      return "TRegexReadOnlyMap";
   }
}
