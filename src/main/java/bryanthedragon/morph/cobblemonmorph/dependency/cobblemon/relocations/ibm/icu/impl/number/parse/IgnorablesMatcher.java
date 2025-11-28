package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StaticUnicodeSets;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

public class IgnorablesMatcher extends SymbolMatcher implements NumberParseMatcher.Flexible {
   private static final IgnorablesMatcher DEFAULT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.DEFAULT_IGNORABLES));
   private static final IgnorablesMatcher STRICT = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.STRICT_IGNORABLES));
   private static final IgnorablesMatcher JAVA_COMPATIBILITY = new IgnorablesMatcher(StaticUnicodeSets.get(StaticUnicodeSets.Key.EMPTY));

   public static IgnorablesMatcher getInstance(int parseFlags) {
      if (0 != (parseFlags & 65536)) {
         return JAVA_COMPATIBILITY;
      } else {
         return 0 != (parseFlags & 32768) ? STRICT : DEFAULT;
      }
   }

   private IgnorablesMatcher(UnicodeSet ignorables) {
      super("", ignorables);
   }

   @Override
   protected boolean isDisabled(ParsedNumber result) {
      return false;
   }

   @Override
   protected void accept(StringSegment segment, ParsedNumber result) {
   }

   @Override
   public String toString() {
      return "<IgnorablesMatcher>";
   }
}
