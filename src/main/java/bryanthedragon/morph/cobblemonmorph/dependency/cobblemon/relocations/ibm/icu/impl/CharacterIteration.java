package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.UTF16;
import java.text.CharacterIterator;

public final class CharacterIteration {
   public static final int DONE32 = Integer.MAX_VALUE;

   private CharacterIteration() {
   }

   public static int next32(CharacterIterator ci) {
      int c = ci.current();
      if (c >= 55296 && c <= 56319) {
         int var2 = ci.next();
         if (var2 < '\udc00' || var2 > '\udfff') {
            ci.previous();
         }
      }

      c = ci.next();
      if (c >= 55296) {
         c = nextTrail32(ci, c);
      }

      if (c >= 65536 && c != Integer.MAX_VALUE) {
         ci.previous();
      }

      return c;
   }

   public static int nextTrail32(CharacterIterator ci, int lead) {
      if (lead == 65535 && ci.getIndex() >= ci.getEndIndex()) {
         return Integer.MAX_VALUE;
      } else {
         int retVal = lead;
         if (lead <= 56319) {
            char cTrail = ci.next();
            if (UTF16.isTrailSurrogate(cTrail)) {
               retVal = (lead - 55296 << 10) + (cTrail - '\udc00') + 65536;
            } else {
               ci.previous();
            }
         }

         return retVal;
      }
   }

   public static int previous32(CharacterIterator ci) {
      if (ci.getIndex() <= ci.getBeginIndex()) {
         return Integer.MAX_VALUE;
      } else {
         char trail = ci.previous();
         int retVal = trail;
         if (UTF16.isTrailSurrogate(trail) && ci.getIndex() > ci.getBeginIndex()) {
            char lead = ci.previous();
            if (UTF16.isLeadSurrogate(lead)) {
               retVal = (lead - '\ud800' << 10) + (trail - '\udc00') + 65536;
            } else {
               ci.next();
            }
         }

         return retVal;
      }
   }

   public static int current32(CharacterIterator ci) {
      char lead = ci.current();
      int retVal = lead;
      if (lead < '\ud800') {
         return lead;
      } else {
         if (UTF16.isLeadSurrogate(lead)) {
            int trail = ci.next();
            ci.previous();
            if (UTF16.isTrailSurrogate(trail)) {
               retVal = (lead - '\ud800' << 10) + (trail - 56320) + 65536;
            }
         } else if (lead == '\uffff' && ci.getIndex() >= ci.getEndIndex()) {
            retVal = Integer.MAX_VALUE;
         }

         return retVal;
      }
   }
}
