package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

public final class CharacterPropertiesImpl {
   private static final int NUM_INCLUSIONS = 41;
   private static final UnicodeSet[] inclusions = new UnicodeSet[41];

   public static synchronized void clear() {
      for (int i = 0; i < inclusions.length; i++) {
         inclusions[i] = null;
      }
   }

   private static UnicodeSet getInclusionsForSource(int src) {
      if (inclusions[src] == null) {
         UnicodeSet incl = new UnicodeSet();
         switch (src) {
            case 1:
               UCharacterProperty.INSTANCE.addPropertyStarts(incl);
               break;
            case 2:
               UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts(incl);
               break;
            case 3:
            default:
               throw new IllegalStateException("getInclusions(unknown src " + src + ")");
            case 4:
               UCaseProps.INSTANCE.addPropertyStarts(incl);
               break;
            case 5:
               UBiDiProps.INSTANCE.addPropertyStarts(incl);
               break;
            case 6:
               UCharacterProperty.INSTANCE.addPropertyStarts(incl);
               UCharacterProperty.INSTANCE.upropsvec_addPropertyStarts(incl);
               break;
            case 7:
               Norm2AllModes.getNFCInstance().impl.addPropertyStarts(incl);
               UCaseProps.INSTANCE.addPropertyStarts(incl);
               break;
            case 8:
               Norm2AllModes.getNFCInstance().impl.addPropertyStarts(incl);
               break;
            case 9:
               Norm2AllModes.getNFKCInstance().impl.addPropertyStarts(incl);
               break;
            case 10:
               Norm2AllModes.getNFKC_CFInstance().impl.addPropertyStarts(incl);
               break;
            case 11:
               Norm2AllModes.getNFCInstance().impl.addCanonIterPropertyStarts(incl);
               break;
            case 12:
            case 13:
            case 14:
               UCharacterProperty.ulayout_addPropertyStarts(src, incl);
               break;
            case 15:
               EmojiProps.INSTANCE.addPropertyStarts(incl);
         }

         inclusions[src] = incl.compact();
      }

      return inclusions[src];
   }

   private static UnicodeSet getIntPropInclusions(int prop) {
      assert 4096 <= prop && prop < 4121;

      int inclIndex = 16 + prop - 4096;
      if (inclusions[inclIndex] != null) {
         return inclusions[inclIndex];
      } else {
         int src = UCharacterProperty.INSTANCE.getSource(prop);
         UnicodeSet incl = getInclusionsForSource(src);
         UnicodeSet intPropIncl = new UnicodeSet(0, 0);
         int numRanges = incl.getRangeCount();
         int prevValue = 0;

         for (int i = 0; i < numRanges; i++) {
            int rangeEnd = incl.getRangeEnd(i);

            for (int c = incl.getRangeStart(i); c <= rangeEnd; c++) {
               int value = UCharacter.getIntPropertyValue(c, prop);
               if (value != prevValue) {
                  intPropIncl.add(c);
                  prevValue = value;
               }
            }
         }

         return inclusions[inclIndex] = intPropIncl.compact();
      }
   }

   public static synchronized UnicodeSet getInclusionsForProperty(int prop) {
      if (4096 <= prop && prop < 4121) {
         return getIntPropInclusions(prop);
      } else {
         int src = UCharacterProperty.INSTANCE.getSource(prop);
         return getInclusionsForSource(src);
      }
   }
}
