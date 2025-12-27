package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.regex.tregex.util.TruffleReadOnlyIntArray;
import com.oracle.truffle.regex.util.TruffleNull;
import com.oracle.truffle.regex.util.TruffleReadOnlyMap;
import com.oracle.truffle.regex.util.TruffleSmallReadOnlyStringToIntMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@ExportLibrary(InteropLibrary.class)
public abstract class AbstractRegexObject implements TruffleObject {
   @ExportMessage
   public final boolean hasLanguage() {
      return true;
   }

   @ExportMessage
   public final Class<? extends TruffleLanguage<?>> getLanguage() {
      return RegexLanguage.class;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   public Object toDisplayString(boolean allowSideEffects) {
      return this.toString();
   }

   @CompilerDirectives.TruffleBoundary
   public static AbstractRegexObject createNamedCaptureGroupMapInt(Map<String, Integer> namedCaptureGroups) {
      if (namedCaptureGroups == null) {
         return TruffleNull.INSTANCE;
      } else {
         return (AbstractRegexObject)(TruffleSmallReadOnlyStringToIntMap.canCreate(namedCaptureGroups)
            ? TruffleSmallReadOnlyStringToIntMap.create(namedCaptureGroups)
            : new TruffleReadOnlyMap(namedCaptureGroups));
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static AbstractRegexObject createNamedCaptureGroupMapListInt(Map<String, List<Integer>> namedCaptureGroups) {
      if (namedCaptureGroups == null) {
         return TruffleNull.INSTANCE;
      } else {
         Map<String, TruffleReadOnlyIntArray> map = new HashMap<>(namedCaptureGroups.size());

         for (Entry<String, List<Integer>> entry : namedCaptureGroups.entrySet()) {
            int[] array = new int[entry.getValue().size()];

            for (int i = 0; i < array.length; i++) {
               array[i] = entry.getValue().get(i);
            }

            map.put(entry.getKey(), new TruffleReadOnlyIntArray(array));
         }

         return new TruffleReadOnlyMap(namedCaptureGroups);
      }
   }
}
