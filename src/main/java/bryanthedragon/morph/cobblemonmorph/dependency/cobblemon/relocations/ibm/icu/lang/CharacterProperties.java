package com.cobblemon.mod.relocations.ibm.icu.lang;

import com.cobblemon.mod.relocations.ibm.icu.impl.CharacterPropertiesImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.EmojiProps;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointMap;
import com.cobblemon.mod.relocations.ibm.icu.util.CodePointTrie;
import com.cobblemon.mod.relocations.ibm.icu.util.MutableCodePointTrie;

public final class CharacterProperties {
   private static final UnicodeSet[] sets = new UnicodeSet[72];
   private static final CodePointMap[] maps = new CodePointMap[25];

   private CharacterProperties() {
   }

   private static UnicodeSet makeSet(int property) {
      UnicodeSet set = new UnicodeSet();
      if (65 <= property && property <= 71) {
         EmojiProps.INSTANCE.addStrings(property, set);
         if (property != 65 && property != 71) {
            return set.freeze();
         }
      }

      UnicodeSet inclusions = CharacterPropertiesImpl.getInclusionsForProperty(property);
      int numRanges = inclusions.getRangeCount();
      int startHasProperty = -1;

      for (int i = 0; i < numRanges; i++) {
         int rangeEnd = inclusions.getRangeEnd(i);

         for (int c = inclusions.getRangeStart(i); c <= rangeEnd; c++) {
            if (UCharacter.hasBinaryProperty(c, property)) {
               if (startHasProperty < 0) {
                  startHasProperty = c;
               }
            } else if (startHasProperty >= 0) {
               set.add(startHasProperty, c - 1);
               startHasProperty = -1;
            }
         }
      }

      if (startHasProperty >= 0) {
         set.add(startHasProperty, 1114111);
      }

      return set.freeze();
   }

   private static CodePointMap makeMap(int property) {
      int nullValue = property == 4106 ? 103 : 0;
      MutableCodePointTrie mutableTrie = new MutableCodePointTrie(nullValue, nullValue);
      UnicodeSet inclusions = CharacterPropertiesImpl.getInclusionsForProperty(property);
      int numRanges = inclusions.getRangeCount();
      int start = 0;
      int value = nullValue;

      for (int i = 0; i < numRanges; i++) {
         int rangeEnd = inclusions.getRangeEnd(i);

         for (int c = inclusions.getRangeStart(i); c <= rangeEnd; c++) {
            int nextValue = UCharacter.getIntPropertyValue(c, property);
            if (value != nextValue) {
               if (value != nullValue) {
                  mutableTrie.setRange(start, c - 1, value);
               }

               start = c;
               value = nextValue;
            }
         }
      }

      if (value != 0) {
         mutableTrie.setRange(start, 1114111, value);
      }

      CodePointTrie.Type type;
      if (property != 4096 && property != 4101) {
         type = CodePointTrie.Type.SMALL;
      } else {
         type = CodePointTrie.Type.FAST;
      }

      int max = UCharacter.getIntPropertyMaxValue(property);
      CodePointTrie.ValueWidth valueWidth;
      if (max <= 255) {
         valueWidth = CodePointTrie.ValueWidth.BITS_8;
      } else if (max <= 65535) {
         valueWidth = CodePointTrie.ValueWidth.BITS_16;
      } else {
         valueWidth = CodePointTrie.ValueWidth.BITS_32;
      }

      return mutableTrie.buildImmutable(type, valueWidth);
   }

   public static final UnicodeSet getBinaryPropertySet(int property) {
      if (property >= 0 && 72 > property) {
         synchronized (sets) {
            UnicodeSet set = sets[property];
            if (set == null) {
               sets[property] = set = makeSet(property);
            }

            return set;
         }
      } else {
         throw new IllegalArgumentException("" + property + " is not a constant for a UProperty binary property");
      }
   }

   public static final CodePointMap getIntPropertyMap(int property) {
      if (property >= 4096 && 4121 > property) {
         synchronized (maps) {
            CodePointMap map = maps[property - 4096];
            if (map == null) {
               maps[property - 4096] = map = makeMap(property);
            }

            return map;
         }
      } else {
         throw new IllegalArgumentException("" + property + " is not a constant for a UProperty int property");
      }
   }
}
