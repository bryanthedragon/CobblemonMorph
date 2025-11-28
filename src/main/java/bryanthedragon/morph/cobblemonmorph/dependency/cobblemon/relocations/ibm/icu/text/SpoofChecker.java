package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUBinary;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.lang.UScript;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpoofChecker {
   public static final UnicodeSet INCLUSION = new UnicodeSet(
         "['\\-.\\:\\u00B7\\u0375\\u058A\\u05F3\\u05F4\\u06FD\\u06FE\\u0F0B\\u200C\\u200D\\u2010\\u2019\\u2027\\u30A0\\u30FB]"
      )
      .freeze();
   public static final UnicodeSet RECOMMENDED = new UnicodeSet(
         "[0-9A-Z_a-z\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u0131\\u0134-\\u013E\\u0141-\\u0148\\u014A-\\u017E\\u018F\\u01A0\\u01A1\\u01AF\\u01B0\\u01CD-\\u01DC\\u01DE-\\u01E3\\u01E6-\\u01F0\\u01F4\\u01F5\\u01F8-\\u021B\\u021E\\u021F\\u0226-\\u0233\\u0259\\u02BB\\u02BC\\u02EC\\u0300-\\u0304\\u0306-\\u030C\\u030F-\\u0311\\u0313\\u0314\\u031B\\u0323-\\u0328\\u032D\\u032E\\u0330\\u0331\\u0335\\u0338\\u0339\\u0342\\u0345\\u037B-\\u037D\\u0386\\u0388-\\u038A\\u038C\\u038E-\\u03A1\\u03A3-\\u03CE\\u03FC-\\u045F\\u048A-\\u04FF\\u0510-\\u0529\\u052E\\u052F\\u0531-\\u0556\\u0559\\u0561-\\u0586\\u05B4\\u05D0-\\u05EA\\u05EF-\\u05F2\\u0620-\\u063F\\u0641-\\u0655\\u0660-\\u0669\\u0670-\\u0672\\u0674\\u0679-\\u068D\\u068F-\\u06A0\\u06A2-\\u06D3\\u06D5\\u06E5\\u06E6\\u06EE-\\u06FC\\u06FF\\u0750-\\u07B1\\u0870-\\u0887\\u0889-\\u088E\\u08A0-\\u08AC\\u08B2\\u08B5-\\u08C9\\u0901-\\u094D\\u094F\\u0950\\u0956\\u0957\\u0960-\\u0963\\u0966-\\u096F\\u0971-\\u0977\\u0979-\\u097F\\u0981-\\u0983\\u0985-\\u098C\\u098F\\u0990\\u0993-\\u09A8\\u09AA-\\u09B0\\u09B2\\u09B6-\\u09B9\\u09BC-\\u09C4\\u09C7\\u09C8\\u09CB-\\u09CE\\u09D7\\u09E0-\\u09E3\\u09E6-\\u09F1\\u09FE\\u0A01-\\u0A03\\u0A05-\\u0A0A\\u0A0F\\u0A10\\u0A13-\\u0A28\\u0A2A-\\u0A30\\u0A32\\u0A35\\u0A38\\u0A39\\u0A3C\\u0A3E-\\u0A42\\u0A47\\u0A48\\u0A4B-\\u0A4D\\u0A5C\\u0A66-\\u0A74\\u0A81-\\u0A83\\u0A85-\\u0A8D\\u0A8F-\\u0A91\\u0A93-\\u0AA8\\u0AAA-\\u0AB0\\u0AB2\\u0AB3\\u0AB5-\\u0AB9\\u0ABC-\\u0AC5\\u0AC7-\\u0AC9\\u0ACB-\\u0ACD\\u0AD0\\u0AE0-\\u0AE3\\u0AE6-\\u0AEF\\u0AFA-\\u0AFF\\u0B01-\\u0B03\\u0B05-\\u0B0C\\u0B0F\\u0B10\\u0B13-\\u0B28\\u0B2A-\\u0B30\\u0B32\\u0B33\\u0B35-\\u0B39\\u0B3C-\\u0B43\\u0B47\\u0B48\\u0B4B-\\u0B4D\\u0B55-\\u0B57\\u0B5F-\\u0B61\\u0B66-\\u0B6F\\u0B71\\u0B82\\u0B83\\u0B85-\\u0B8A\\u0B8E-\\u0B90\\u0B92-\\u0B95\\u0B99\\u0B9A\\u0B9C\\u0B9E\\u0B9F\\u0BA3\\u0BA4\\u0BA8-\\u0BAA\\u0BAE-\\u0BB9\\u0BBE-\\u0BC2\\u0BC6-\\u0BC8\\u0BCA-\\u0BCD\\u0BD0\\u0BD7\\u0BE6-\\u0BEF\\u0C01-\\u0C0C\\u0C0E-\\u0C10\\u0C12-\\u0C28\\u0C2A-\\u0C33\\u0C35-\\u0C39\\u0C3C-\\u0C44\\u0C46-\\u0C48\\u0C4A-\\u0C4D\\u0C55\\u0C56\\u0C5D\\u0C60\\u0C61\\u0C66-\\u0C6F\\u0C80\\u0C82\\u0C83\\u0C85-\\u0C8C\\u0C8E-\\u0C90\\u0C92-\\u0CA8\\u0CAA-\\u0CB3\\u0CB5-\\u0CB9\\u0CBC-\\u0CC4\\u0CC6-\\u0CC8\\u0CCA-\\u0CCD\\u0CD5\\u0CD6\\u0CDD\\u0CE0-\\u0CE3\\u0CE6-\\u0CEF\\u0CF1\\u0CF2\\u0D00\\u0D02\\u0D03\\u0D05-\\u0D0C\\u0D0E-\\u0D10\\u0D12-\\u0D3A\\u0D3D-\\u0D43\\u0D46-\\u0D48\\u0D4A-\\u0D4E\\u0D54-\\u0D57\\u0D60\\u0D61\\u0D66-\\u0D6F\\u0D7A-\\u0D7F\\u0D82\\u0D83\\u0D85-\\u0D8E\\u0D91-\\u0D96\\u0D9A-\\u0DA5\\u0DA7-\\u0DB1\\u0DB3-\\u0DBB\\u0DBD\\u0DC0-\\u0DC6\\u0DCA\\u0DCF-\\u0DD4\\u0DD6\\u0DD8-\\u0DDE\\u0DF2\\u0E01-\\u0E32\\u0E34-\\u0E3A\\u0E40-\\u0E4E\\u0E50-\\u0E59\\u0E81\\u0E82\\u0E84\\u0E86-\\u0E8A\\u0E8C-\\u0EA3\\u0EA5\\u0EA7-\\u0EB2\\u0EB4-\\u0EBD\\u0EC0-\\u0EC4\\u0EC6\\u0EC8-\\u0ECD\\u0ED0-\\u0ED9\\u0EDE\\u0EDF\\u0F00\\u0F20-\\u0F29\\u0F35\\u0F37\\u0F3E-\\u0F42\\u0F44-\\u0F47\\u0F49-\\u0F4C\\u0F4E-\\u0F51\\u0F53-\\u0F56\\u0F58-\\u0F5B\\u0F5D-\\u0F68\\u0F6A-\\u0F6C\\u0F71\\u0F72\\u0F74\\u0F7A-\\u0F80\\u0F82-\\u0F84\\u0F86-\\u0F92\\u0F94-\\u0F97\\u0F99-\\u0F9C\\u0F9E-\\u0FA1\\u0FA3-\\u0FA6\\u0FA8-\\u0FAB\\u0FAD-\\u0FB8\\u0FBA-\\u0FBC\\u0FC6\\u1000-\\u1049\\u1050-\\u109D\\u10C7\\u10CD\\u10D0-\\u10F0\\u10F7-\\u10FA\\u10FD-\\u10FF\\u1200-\\u1248\\u124A-\\u124D\\u1250-\\u1256\\u1258\\u125A-\\u125D\\u1260-\\u1288\\u128A-\\u128D\\u1290-\\u12B0\\u12B2-\\u12B5\\u12B8-\\u12BE\\u12C0\\u12C2-\\u12C5\\u12C8-\\u12D6\\u12D8-\\u1310\\u1312-\\u1315\\u1318-\\u135A\\u135D-\\u135F\\u1380-\\u138F\\u1780-\\u17A2\\u17A5-\\u17A7\\u17A9-\\u17B3\\u17B6-\\u17CD\\u17D0\\u17D2\\u17D7\\u17DC\\u17E0-\\u17E9\\u1C90-\\u1CBA\\u1CBD-\\u1CBF\\u1E00-\\u1E99\\u1E9E\\u1EA0-\\u1EF9\\u1F00-\\u1F15\\u1F18-\\u1F1D\\u1F20-\\u1F45\\u1F48-\\u1F4D\\u1F50-\\u1F57\\u1F59\\u1F5B\\u1F5D\\u1F5F-\\u1F70\\u1F72\\u1F74\\u1F76\\u1F78\\u1F7A\\u1F7C\\u1F80-\\u1FB4\\u1FB6-\\u1FBA\\u1FBC\\u1FC2-\\u1FC4\\u1FC6-\\u1FC8\\u1FCA\\u1FCC\\u1FD0-\\u1FD2\\u1FD6-\\u1FDA\\u1FE0-\\u1FE2\\u1FE4-\\u1FEA\\u1FEC\\u1FF2-\\u1FF4\\u1FF6-\\u1FF8\\u1FFA\\u1FFC\\u2D27\\u2D2D\\u2D80-\\u2D96\\u2DA0-\\u2DA6\\u2DA8-\\u2DAE\\u2DB0-\\u2DB6\\u2DB8-\\u2DBE\\u2DC0-\\u2DC6\\u2DC8-\\u2DCE\\u2DD0-\\u2DD6\\u2DD8-\\u2DDE\\u3005-\\u3007\\u3041-\\u3096\\u3099\\u309A\\u309D\\u309E\\u30A1-\\u30FA\\u30FC-\\u30FE\\u3105-\\u312D\\u312F\\u31A0-\\u31BF\\u3400-\\u4DBF\\u4E00-\\u9FFF\\uA67F\\uA717-\\uA71F\\uA788\\uA78D\\uA792\\uA793\\uA7AA\\uA7AE\\uA7B8\\uA7B9\\uA7C0-\\uA7CA\\uA7D0\\uA7D1\\uA7D3\\uA7D5-\\uA7D9\\uA9E7-\\uA9FE\\uAA60-\\uAA76\\uAA7A-\\uAA7F\\uAB01-\\uAB06\\uAB09-\\uAB0E\\uAB11-\\uAB16\\uAB20-\\uAB26\\uAB28-\\uAB2E\\uAB66\\uAB67\\uAC00-\\uD7A3\\uFA0E\\uFA0F\\uFA11\\uFA13\\uFA14\\uFA1F\\uFA21\\uFA23\\uFA24\\uFA27-\\uFA29\\U00011301\\U00011303\\U0001133B\\U0001133C\\U00016FF0\\U00016FF1\\U0001B11F-\\U0001B122\\U0001B150-\\U0001B152\\U0001B164-\\U0001B167\\U0001DF00-\\U0001DF1E\\U0001E7E0-\\U0001E7E6\\U0001E7E8-\\U0001E7EB\\U0001E7ED\\U0001E7EE\\U0001E7F0-\\U0001E7FE\\U00020000-\\U0002A6DF\\U0002A700-\\U0002B738\\U0002B740-\\U0002B81D\\U0002B820-\\U0002CEA1\\U0002CEB0-\\U0002EBE0\\U00030000-\\U0003134A]"
      )
      .freeze();
   public static final int SINGLE_SCRIPT_CONFUSABLE = 1;
   public static final int MIXED_SCRIPT_CONFUSABLE = 2;
   public static final int WHOLE_SCRIPT_CONFUSABLE = 4;
   public static final int CONFUSABLE = 7;
   @Deprecated
   public static final int ANY_CASE = 8;
   public static final int RESTRICTION_LEVEL = 16;
   @Deprecated
   public static final int SINGLE_SCRIPT = 16;
   public static final int INVISIBLE = 32;
   public static final int CHAR_LIMIT = 64;
   public static final int MIXED_NUMBERS = 128;
   public static final int HIDDEN_OVERLAY = 256;
   public static final int ALL_CHECKS = -1;
   static final UnicodeSet ASCII = new UnicodeSet(0, 127).freeze();
   private int fChecks;
   private SpoofChecker.SpoofData fSpoofData;
   private Set<ULocale> fAllowedLocales;
   private UnicodeSet fAllowedCharsSet;
   private SpoofChecker.RestrictionLevel fRestrictionLevel;
   private static Normalizer2 nfdNormalizer = Normalizer2.getNFDInstance();

   private SpoofChecker() {
   }

   @Deprecated
   public SpoofChecker.RestrictionLevel getRestrictionLevel() {
      return this.fRestrictionLevel;
   }

   public int getChecks() {
      return this.fChecks;
   }

   public Set<ULocale> getAllowedLocales() {
      return Collections.unmodifiableSet(this.fAllowedLocales);
   }

   public Set<Locale> getAllowedJavaLocales() {
      HashSet<Locale> locales = new HashSet<>(this.fAllowedLocales.size());

      for (ULocale uloc : this.fAllowedLocales) {
         locales.add(uloc.toLocale());
      }

      return locales;
   }

   public UnicodeSet getAllowedChars() {
      return this.fAllowedCharsSet;
   }

   public boolean failsChecks(String text, SpoofChecker.CheckResult checkResult) {
      int length = text.length();
      int result = 0;
      if (checkResult != null) {
         checkResult.position = 0;
         checkResult.numerics = null;
         checkResult.restrictionLevel = null;
      }

      if (0 != (this.fChecks & 16)) {
         SpoofChecker.RestrictionLevel textRestrictionLevel = this.getRestrictionLevel(text);
         if (textRestrictionLevel.compareTo(this.fRestrictionLevel) > 0) {
            result |= 16;
         }

         if (checkResult != null) {
            checkResult.restrictionLevel = textRestrictionLevel;
         }
      }

      if (0 != (this.fChecks & 128)) {
         UnicodeSet numerics = new UnicodeSet();
         this.getNumerics(text, numerics);
         if (numerics.size() > 1) {
            result |= 128;
         }

         if (checkResult != null) {
            checkResult.numerics = numerics;
         }
      }

      if (0 != (this.fChecks & 256)) {
         int index = this.findHiddenOverlay(text);
         if (index != -1) {
            result |= 256;
         }
      }

      if (0 != (this.fChecks & 64)) {
         int i = 0;

         while (i < length) {
            int c = Character.codePointAt(text, i);
            i = Character.offsetByCodePoints(text, i, 1);
            if (!this.fAllowedCharsSet.contains(c)) {
               result |= 64;
               break;
            }
         }
      }

      if (0 != (this.fChecks & 32)) {
         String nfdText = nfdNormalizer.normalize(text);
         int firstNonspacingMark = 0;
         boolean haveMultipleMarks = false;
         UnicodeSet marksSeenSoFar = new UnicodeSet();
         int i = 0;

         while (i < length) {
            int c = Character.codePointAt(nfdText, i);
            i = Character.offsetByCodePoints(nfdText, i, 1);
            if (Character.getType(c) != 6) {
               firstNonspacingMark = 0;
               if (haveMultipleMarks) {
                  marksSeenSoFar.clear();
                  haveMultipleMarks = false;
               }
            } else if (firstNonspacingMark == 0) {
               firstNonspacingMark = c;
            } else {
               if (!haveMultipleMarks) {
                  marksSeenSoFar.add(firstNonspacingMark);
                  haveMultipleMarks = true;
               }

               if (marksSeenSoFar.contains(c)) {
                  result |= 32;
                  break;
               }

               marksSeenSoFar.add(c);
            }
         }
      }

      if (checkResult != null) {
         checkResult.checks = result;
      }

      return 0 != result;
   }

   public boolean failsChecks(String text) {
      return this.failsChecks(text, null);
   }

   public int areConfusable(String s1, String s2) {
      if ((this.fChecks & 7) == 0) {
         throw new IllegalArgumentException("No confusable checks are enabled.");
      } else {
         String s1Skeleton = this.getSkeleton(s1);
         String s2Skeleton = this.getSkeleton(s2);
         if (!s1Skeleton.equals(s2Skeleton)) {
            return 0;
         } else {
            SpoofChecker.ScriptSet s1RSS = new SpoofChecker.ScriptSet();
            this.getResolvedScriptSet(s1, s1RSS);
            SpoofChecker.ScriptSet s2RSS = new SpoofChecker.ScriptSet();
            this.getResolvedScriptSet(s2, s2RSS);
            int result = 0;
            if (s1RSS.intersects(s2RSS)) {
               result |= 1;
            } else {
               result |= 2;
               if (!s1RSS.isEmpty() && !s2RSS.isEmpty()) {
                  result |= 4;
               }
            }

            return result & this.fChecks;
         }
      }
   }

   public String getSkeleton(CharSequence str) {
      String nfdId = nfdNormalizer.normalize(str);
      int normalizedLen = nfdId.length();
      StringBuilder skelSB = new StringBuilder();
      int inputIndex = 0;

      while (inputIndex < normalizedLen) {
         int c = Character.codePointAt(nfdId, inputIndex);
         inputIndex += Character.charCount(c);
         this.fSpoofData.confusableLookup(c, skelSB);
      }

      String skelStr = skelSB.toString();
      return nfdNormalizer.normalize(skelStr);
   }

   @Deprecated
   public String getSkeleton(int type, String id) {
      return this.getSkeleton(id);
   }

   @Override
   public boolean equals(Object other) {
      if (!(other instanceof SpoofChecker)) {
         return false;
      } else {
         SpoofChecker otherSC = (SpoofChecker)other;
         if (this.fSpoofData != otherSC.fSpoofData && this.fSpoofData != null && !this.fSpoofData.equals(otherSC.fSpoofData)) {
            return false;
         } else if (this.fChecks != otherSC.fChecks) {
            return false;
         } else if (this.fAllowedLocales != otherSC.fAllowedLocales && this.fAllowedLocales != null && !this.fAllowedLocales.equals(otherSC.fAllowedLocales)) {
            return false;
         } else {
            return this.fAllowedCharsSet != otherSC.fAllowedCharsSet
                  && this.fAllowedCharsSet != null
                  && !this.fAllowedCharsSet.equals(otherSC.fAllowedCharsSet)
               ? false
               : this.fRestrictionLevel == otherSC.fRestrictionLevel;
         }
      }
   }

   @Override
   public int hashCode() {
      return this.fChecks ^ this.fSpoofData.hashCode() ^ this.fAllowedLocales.hashCode() ^ this.fAllowedCharsSet.hashCode() ^ this.fRestrictionLevel.ordinal();
   }

   private static void getAugmentedScriptSet(int codePoint, SpoofChecker.ScriptSet result) {
      result.clear();
      UScript.getScriptExtensions(codePoint, result);
      if (result.get(17)) {
         result.set(172);
         result.set(105);
         result.set(119);
      }

      if (result.get(20)) {
         result.set(105);
      }

      if (result.get(22)) {
         result.set(105);
      }

      if (result.get(18)) {
         result.set(119);
      }

      if (result.get(5)) {
         result.set(172);
      }

      if (result.get(0) || result.get(1)) {
         result.setAll();
      }
   }

   private void getResolvedScriptSet(CharSequence input, SpoofChecker.ScriptSet result) {
      this.getResolvedScriptSetWithout(input, 198, result);
   }

   private void getResolvedScriptSetWithout(CharSequence input, int script, SpoofChecker.ScriptSet result) {
      result.setAll();
      SpoofChecker.ScriptSet temp = new SpoofChecker.ScriptSet();
      int utf16Offset = 0;

      while (utf16Offset < input.length()) {
         int codePoint = Character.codePointAt(input, utf16Offset);
         utf16Offset += Character.charCount(codePoint);
         getAugmentedScriptSet(codePoint, temp);
         if (script == 198 || !temp.get(script)) {
            result.and(temp);
         }
      }
   }

   private void getNumerics(String input, UnicodeSet result) {
      result.clear();
      int utf16Offset = 0;

      while (utf16Offset < input.length()) {
         int codePoint = Character.codePointAt(input, utf16Offset);
         utf16Offset += Character.charCount(codePoint);
         if (UCharacter.getType(codePoint) == 9) {
            result.add(codePoint - UCharacter.getNumericValue(codePoint));
         }
      }
   }

   private SpoofChecker.RestrictionLevel getRestrictionLevel(String input) {
      if (!this.fAllowedCharsSet.containsAll(input)) {
         return SpoofChecker.RestrictionLevel.UNRESTRICTIVE;
      } else if (ASCII.containsAll(input)) {
         return SpoofChecker.RestrictionLevel.ASCII;
      } else {
         SpoofChecker.ScriptSet resolvedScriptSet = new SpoofChecker.ScriptSet();
         this.getResolvedScriptSet(input, resolvedScriptSet);
         if (!resolvedScriptSet.isEmpty()) {
            return SpoofChecker.RestrictionLevel.SINGLE_SCRIPT_RESTRICTIVE;
         } else {
            SpoofChecker.ScriptSet resolvedNoLatn = new SpoofChecker.ScriptSet();
            this.getResolvedScriptSetWithout(input, 25, resolvedNoLatn);
            if (resolvedNoLatn.get(172) || resolvedNoLatn.get(105) || resolvedNoLatn.get(119)) {
               return SpoofChecker.RestrictionLevel.HIGHLY_RESTRICTIVE;
            } else {
               return !resolvedNoLatn.isEmpty() && !resolvedNoLatn.get(8) && !resolvedNoLatn.get(14) && !resolvedNoLatn.get(6)
                  ? SpoofChecker.RestrictionLevel.MODERATELY_RESTRICTIVE
                  : SpoofChecker.RestrictionLevel.MINIMALLY_RESTRICTIVE;
            }
         }
      }
   }

   int findHiddenOverlay(String input) {
      boolean sawLeadCharacter = false;
      StringBuilder sb = new StringBuilder();
      int i = 0;

      while (i < input.length()) {
         int cp = input.codePointAt(i);
         if (sawLeadCharacter && cp == 775) {
            return i;
         }

         int combiningClass = UCharacter.getCombiningClass(cp);

         assert UCharacter.getCombiningClass(775) == 230;

         if (combiningClass == 0 || combiningClass == 230) {
            sawLeadCharacter = this.isIllegalCombiningDotLeadCharacter(cp, sb);
         }

         i += UCharacter.charCount(cp);
      }

      return -1;
   }

   boolean isIllegalCombiningDotLeadCharacterNoLookup(int cp) {
      return cp == 105 || cp == 106 || cp == 305 || cp == 567 || cp == 108 || UCharacter.hasBinaryProperty(cp, 27);
   }

   boolean isIllegalCombiningDotLeadCharacter(int cp, StringBuilder sb) {
      if (this.isIllegalCombiningDotLeadCharacterNoLookup(cp)) {
         return true;
      } else {
         sb.setLength(0);
         this.fSpoofData.confusableLookup(cp, sb);
         int finalCp = UCharacter.codePointBefore(sb, sb.length());
         return finalCp != cp && this.isIllegalCombiningDotLeadCharacterNoLookup(finalCp);
      }
   }

   public static class Builder {
      int fChecks;
      SpoofChecker.SpoofData fSpoofData;
      final UnicodeSet fAllowedCharsSet = new UnicodeSet(0, 1114111);
      final Set<ULocale> fAllowedLocales = new LinkedHashSet<>();
      private SpoofChecker.RestrictionLevel fRestrictionLevel;

      public Builder() {
         this.fChecks = -1;
         this.fSpoofData = null;
         this.fRestrictionLevel = SpoofChecker.RestrictionLevel.HIGHLY_RESTRICTIVE;
      }

      public Builder(SpoofChecker src) {
         this.fChecks = src.fChecks;
         this.fSpoofData = src.fSpoofData;
         this.fAllowedCharsSet.set(src.fAllowedCharsSet);
         this.fAllowedLocales.addAll(src.fAllowedLocales);
         this.fRestrictionLevel = src.fRestrictionLevel;
      }

      public SpoofChecker build() {
         if (this.fSpoofData == null) {
            this.fSpoofData = SpoofChecker.SpoofData.getDefault();
         }

         SpoofChecker result = new SpoofChecker();
         result.fChecks = this.fChecks;
         result.fSpoofData = this.fSpoofData;
         result.fAllowedCharsSet = (UnicodeSet)this.fAllowedCharsSet.clone();
         result.fAllowedCharsSet.freeze();
         result.fAllowedLocales = new HashSet<>(this.fAllowedLocales);
         result.fRestrictionLevel = this.fRestrictionLevel;
         return result;
      }

      public SpoofChecker.Builder setData(Reader confusables) throws ParseException, IOException {
         this.fSpoofData = new SpoofChecker.SpoofData();
         SpoofChecker.Builder.ConfusabledataBuilder.buildConfusableData(confusables, this.fSpoofData);
         return this;
      }

      @Deprecated
      public SpoofChecker.Builder setData(Reader confusables, Reader confusablesWholeScript) throws ParseException, IOException {
         this.setData(confusables);
         return this;
      }

      public SpoofChecker.Builder setChecks(int checks) {
         if (0 != (checks & 0)) {
            throw new IllegalArgumentException("Bad Spoof Checks value.");
         } else {
            this.fChecks = checks & -1;
            return this;
         }
      }

      public SpoofChecker.Builder setAllowedLocales(Set<ULocale> locales) {
         this.fAllowedCharsSet.clear();

         for (ULocale locale : locales) {
            this.addScriptChars(locale, this.fAllowedCharsSet);
         }

         this.fAllowedLocales.clear();
         if (locales.size() == 0) {
            this.fAllowedCharsSet.add(0, 1114111);
            this.fChecks &= -65;
            return this;
         } else {
            UnicodeSet tempSet = new UnicodeSet();
            tempSet.applyIntPropertyValue(4106, 0);
            this.fAllowedCharsSet.addAll(tempSet);
            tempSet.applyIntPropertyValue(4106, 1);
            this.fAllowedCharsSet.addAll(tempSet);
            this.fAllowedLocales.clear();
            this.fAllowedLocales.addAll(locales);
            this.fChecks |= 64;
            return this;
         }
      }

      public SpoofChecker.Builder setAllowedJavaLocales(Set<Locale> locales) {
         HashSet<ULocale> ulocales = new HashSet<>(locales.size());

         for (Locale locale : locales) {
            ulocales.add(ULocale.forLocale(locale));
         }

         return this.setAllowedLocales(ulocales);
      }

      private void addScriptChars(ULocale locale, UnicodeSet allowedChars) {
         int[] scripts = UScript.getCode(locale);
         if (scripts != null) {
            UnicodeSet tmpSet = new UnicodeSet();

            for (int i = 0; i < scripts.length; i++) {
               tmpSet.applyIntPropertyValue(4106, scripts[i]);
               allowedChars.addAll(tmpSet);
            }
         }
      }

      public SpoofChecker.Builder setAllowedChars(UnicodeSet chars) {
         this.fAllowedCharsSet.set(chars);
         this.fAllowedLocales.clear();
         this.fChecks |= 64;
         return this;
      }

      public SpoofChecker.Builder setRestrictionLevel(SpoofChecker.RestrictionLevel restrictionLevel) {
         this.fRestrictionLevel = restrictionLevel;
         this.fChecks |= 144;
         return this;
      }

      private static class ConfusabledataBuilder {
         private Hashtable<Integer, SpoofChecker.Builder.ConfusabledataBuilder.SPUString> fTable = new Hashtable<>();
         private UnicodeSet fKeySet = new UnicodeSet();
         private StringBuffer fStringTable;
         private ArrayList<Integer> fKeyVec = new ArrayList<>();
         private ArrayList<Integer> fValueVec = new ArrayList<>();
         private SpoofChecker.Builder.ConfusabledataBuilder.SPUStringPool stringPool = new SpoofChecker.Builder.ConfusabledataBuilder.SPUStringPool();
         private Pattern fParseLine;
         private Pattern fParseHexNum;
         private int fLineNum;

         ConfusabledataBuilder() {
         }

         void build(Reader confusables, SpoofChecker.SpoofData dest) throws ParseException, IOException {
            StringBuffer fInput = new StringBuffer();
            LineNumberReader lnr = new LineNumberReader(confusables);

            while (true) {
               String line = lnr.readLine();
               if (line == null) {
                  this.fParseLine = Pattern.compile(
                     "(?m)^[ \\t]*([0-9A-Fa-f]+)[ \\t]+;[ \\t]*([0-9A-Fa-f]+(?:[ \\t]+[0-9A-Fa-f]+)*)[ \\t]*;\\s*(?:(SL)|(SA)|(ML)|(MA))[ \\t]*(?:#.*?)?$|^([ \\t]*(?:#.*?)?)$|^(.*?)$"
                  );
                  this.fParseHexNum = Pattern.compile("\\s*([0-9A-F]+)");
                  if (fInput.charAt(0) == '\ufeff') {
                     fInput.setCharAt(0, ' ');
                  }

                  Matcher matcher = this.fParseLine.matcher(fInput);

                  while (matcher.find()) {
                     this.fLineNum++;
                     if (matcher.start(7) < 0) {
                        if (matcher.start(8) >= 0) {
                           throw new ParseException("Confusables, line " + this.fLineNum + ": Unrecognized Line: " + matcher.group(8), matcher.start(8));
                        }

                        int keyChar = Integer.parseInt(matcher.group(1), 16);
                        if (keyChar > 1114111) {
                           throw new ParseException("Confusables, line " + this.fLineNum + ": Bad code point: " + matcher.group(1), matcher.start(1));
                        }

                        Matcher m = this.fParseHexNum.matcher(matcher.group(2));
                        StringBuilder mapString = new StringBuilder();

                        while (m.find()) {
                           int c = Integer.parseInt(m.group(1), 16);
                           if (c > 1114111) {
                              throw new ParseException("Confusables, line " + this.fLineNum + ": Bad code point: " + Integer.toString(c, 16), matcher.start(2));
                           }

                           mapString.appendCodePoint(c);
                        }

                        assert mapString.length() >= 1;

                        SpoofChecker.Builder.ConfusabledataBuilder.SPUString smapString = this.stringPool.addString(mapString.toString());
                        this.fTable.put(keyChar, smapString);
                        this.fKeySet.add(keyChar);
                     }
                  }

                  this.stringPool.sort();
                  this.fStringTable = new StringBuffer();
                  int poolSize = this.stringPool.size();

                  for (int i = 0; i < poolSize; i++) {
                     SpoofChecker.Builder.ConfusabledataBuilder.SPUString s = this.stringPool.getByIndex(i);
                     int strLen = s.fStr.length();
                     int strIndex = this.fStringTable.length();
                     if (strLen == 1) {
                        s.fCharOrStrTableIndex = s.fStr.charAt(0);
                     } else {
                        s.fCharOrStrTableIndex = strIndex;
                        this.fStringTable.append(s.fStr);
                     }
                  }

                  for (String keyCharStr : this.fKeySet) {
                     int keyCharx = keyCharStr.codePointAt(0);
                     SpoofChecker.Builder.ConfusabledataBuilder.SPUString targetMapping = this.fTable.get(keyCharx);

                     assert targetMapping != null;

                     if (targetMapping.fStr.length() > 256) {
                        throw new IllegalArgumentException("Confusable prototypes cannot be longer than 256 entries.");
                     }

                     int key = SpoofChecker.ConfusableDataUtils.codePointAndLengthToKey(keyCharx, targetMapping.fStr.length());
                     int value = targetMapping.fCharOrStrTableIndex;
                     this.fKeyVec.add(key);
                     this.fValueVec.add(value);
                  }

                  int numKeys = this.fKeyVec.size();
                  dest.fCFUKeys = new int[numKeys];
                  int previousCodePoint = 0;

                  for (int var17 = 0; var17 < numKeys; var17++) {
                     int key = this.fKeyVec.get(var17);
                     int codePoint = SpoofChecker.ConfusableDataUtils.keyToCodePoint(key);

                     assert codePoint > previousCodePoint;

                     dest.fCFUKeys[var17] = key;
                     previousCodePoint = codePoint;
                  }

                  int numValues = this.fValueVec.size();

                  assert numKeys == numValues;

                  dest.fCFUValues = new short[numValues];
                  int var18 = 0;

                  for (int value : this.fValueVec) {
                     assert value < 65535;

                     dest.fCFUValues[var18++] = (short)value;
                  }

                  dest.fCFUStrings = this.fStringTable.toString();
                  return;
               }

               fInput.append(line);
               fInput.append('\n');
            }
         }

         public static void buildConfusableData(Reader confusables, SpoofChecker.SpoofData dest) throws IOException, ParseException {
            SpoofChecker.Builder.ConfusabledataBuilder builder = new SpoofChecker.Builder.ConfusabledataBuilder();
            builder.build(confusables, dest);
         }

         private static class SPUString {
            String fStr;
            int fCharOrStrTableIndex;

            SPUString(String s) {
               this.fStr = s;
               this.fCharOrStrTableIndex = 0;
            }
         }

         private static class SPUStringComparator implements Comparator<SpoofChecker.Builder.ConfusabledataBuilder.SPUString> {
            static final SpoofChecker.Builder.ConfusabledataBuilder.SPUStringComparator INSTANCE = new SpoofChecker.Builder.ConfusabledataBuilder.SPUStringComparator();

            public int compare(SpoofChecker.Builder.ConfusabledataBuilder.SPUString sL, SpoofChecker.Builder.ConfusabledataBuilder.SPUString sR) {
               int lenL = sL.fStr.length();
               int lenR = sR.fStr.length();
               if (lenL < lenR) {
                  return -1;
               } else {
                  return lenL > lenR ? 1 : sL.fStr.compareTo(sR.fStr);
               }
            }
         }

         private static class SPUStringPool {
            private Vector<SpoofChecker.Builder.ConfusabledataBuilder.SPUString> fVec = new Vector<>();
            private Hashtable<String, SpoofChecker.Builder.ConfusabledataBuilder.SPUString> fHash = new Hashtable<>();

            public SPUStringPool() {
            }

            public int size() {
               return this.fVec.size();
            }

            public SpoofChecker.Builder.ConfusabledataBuilder.SPUString getByIndex(int index) {
               return this.fVec.elementAt(index);
            }

            public SpoofChecker.Builder.ConfusabledataBuilder.SPUString addString(String src) {
               SpoofChecker.Builder.ConfusabledataBuilder.SPUString hashedString = this.fHash.get(src);
               if (hashedString == null) {
                  hashedString = new SpoofChecker.Builder.ConfusabledataBuilder.SPUString(src);
                  this.fHash.put(src, hashedString);
                  this.fVec.addElement(hashedString);
               }

               return hashedString;
            }

            public void sort() {
               Collections.sort(this.fVec, SpoofChecker.Builder.ConfusabledataBuilder.SPUStringComparator.INSTANCE);
            }
         }
      }
   }

   public static class CheckResult {
      public int checks = 0;
      @Deprecated
      public int position = 0;
      public UnicodeSet numerics;
      public SpoofChecker.RestrictionLevel restrictionLevel;

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("checks:");
         if (this.checks == 0) {
            sb.append(" none");
         } else if (this.checks == -1) {
            sb.append(" all");
         } else {
            if ((this.checks & 1) != 0) {
               sb.append(" SINGLE_SCRIPT_CONFUSABLE");
            }

            if ((this.checks & 2) != 0) {
               sb.append(" MIXED_SCRIPT_CONFUSABLE");
            }

            if ((this.checks & 4) != 0) {
               sb.append(" WHOLE_SCRIPT_CONFUSABLE");
            }

            if ((this.checks & 8) != 0) {
               sb.append(" ANY_CASE");
            }

            if ((this.checks & 16) != 0) {
               sb.append(" RESTRICTION_LEVEL");
            }

            if ((this.checks & 32) != 0) {
               sb.append(" INVISIBLE");
            }

            if ((this.checks & 64) != 0) {
               sb.append(" CHAR_LIMIT");
            }

            if ((this.checks & 128) != 0) {
               sb.append(" MIXED_NUMBERS");
            }
         }

         sb.append(", numerics: ").append(this.numerics.toPattern(false));
         sb.append(", position: ").append(this.position);
         sb.append(", restrictionLevel: ").append(this.restrictionLevel);
         return sb.toString();
      }
   }

   private static final class ConfusableDataUtils {
      public static final int FORMAT_VERSION = 2;

      public static final int keyToCodePoint(int key) {
         return key & 16777215;
      }

      public static final int keyToLength(int key) {
         return ((key & 0xFF000000) >> 24) + 1;
      }

      public static final int codePointAndLengthToKey(int codePoint, int length) {
         assert (codePoint & 16777215) == codePoint;

         assert length <= 256;

         return codePoint | length - 1 << 24;
      }
   }

   public static enum RestrictionLevel {
      ASCII,
      SINGLE_SCRIPT_RESTRICTIVE,
      HIGHLY_RESTRICTIVE,
      MODERATELY_RESTRICTIVE,
      MINIMALLY_RESTRICTIVE,
      UNRESTRICTIVE;
   }

   static class ScriptSet extends BitSet {
      private static final long serialVersionUID = 1L;

      public void and(int script) {
         this.clear(0, script);
         this.clear(script + 1, 198);
      }

      public void setAll() {
         this.set(0, 198);
      }

      public boolean isFull() {
         return this.cardinality() == 198;
      }

      public void appendStringTo(StringBuilder sb) {
         sb.append("{ ");
         if (this.isEmpty()) {
            sb.append("- ");
         } else if (this.isFull()) {
            sb.append("* ");
         } else {
            for (int script = 0; script < 198; script++) {
               if (this.get(script)) {
                  sb.append(UScript.getShortName(script));
                  sb.append(" ");
               }
            }
         }

         sb.append("}");
      }

      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append("<ScriptSet ");
         this.appendStringTo(sb);
         sb.append(">");
         return sb.toString();
      }
   }

   private static class SpoofData {
      int[] fCFUKeys;
      short[] fCFUValues;
      String fCFUStrings;
      private static final int DATA_FORMAT = 1130788128;
      private static final SpoofChecker.SpoofData.IsAcceptable IS_ACCEPTABLE = new SpoofChecker.SpoofData.IsAcceptable();

      public static SpoofChecker.SpoofData getDefault() {
         if (SpoofChecker.SpoofData.DefaultData.EXCEPTION != null) {
            throw new MissingResourceException(
               "Could not load default confusables data: " + SpoofChecker.SpoofData.DefaultData.EXCEPTION.getMessage(), "SpoofChecker", ""
            );
         } else {
            return SpoofChecker.SpoofData.DefaultData.INSTANCE;
         }
      }

      private SpoofData() {
      }

      private SpoofData(ByteBuffer bytes) throws IOException {
         ICUBinary.readHeader(bytes, 1130788128, IS_ACCEPTABLE);
         ((Buffer)bytes).mark();
         this.readData(bytes);
      }

      @Override
      public boolean equals(Object other) {
         if (!(other instanceof SpoofChecker.SpoofData)) {
            return false;
         } else {
            SpoofChecker.SpoofData otherData = (SpoofChecker.SpoofData)other;
            if (!Arrays.equals(this.fCFUKeys, otherData.fCFUKeys)) {
               return false;
            } else {
               return !Arrays.equals(this.fCFUValues, otherData.fCFUValues)
                  ? false
                  : Utility.sameObjects(this.fCFUStrings, otherData.fCFUStrings) || this.fCFUStrings == null || this.fCFUStrings.equals(otherData.fCFUStrings);
            }
         }
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode(this.fCFUKeys) ^ Arrays.hashCode(this.fCFUValues) ^ this.fCFUStrings.hashCode();
      }

      private void readData(ByteBuffer bytes) throws IOException {
         int magic = bytes.getInt();
         if (magic != 944111087) {
            throw new IllegalArgumentException("Bad Spoof Check Data.");
         } else {
            int dataFormatVersion = bytes.getInt();
            int dataLength = bytes.getInt();
            int CFUKeysOffset = bytes.getInt();
            int CFUKeysSize = bytes.getInt();
            int CFUValuesOffset = bytes.getInt();
            int CFUValuesSize = bytes.getInt();
            int CFUStringTableOffset = bytes.getInt();
            int CFUStringTableSize = bytes.getInt();
            ((Buffer)bytes).reset();
            ICUBinary.skipBytes(bytes, CFUKeysOffset);
            this.fCFUKeys = ICUBinary.getInts(bytes, CFUKeysSize, 0);
            ((Buffer)bytes).reset();
            ICUBinary.skipBytes(bytes, CFUValuesOffset);
            this.fCFUValues = ICUBinary.getShorts(bytes, CFUValuesSize, 0);
            ((Buffer)bytes).reset();
            ICUBinary.skipBytes(bytes, CFUStringTableOffset);
            this.fCFUStrings = ICUBinary.getString(bytes, CFUStringTableSize, 0);
         }
      }

      public void confusableLookup(int inChar, StringBuilder dest) {
         int lo = 0;
         int hi = this.length();

         do {
            int mid = (lo + hi) / 2;
            if (this.codePointAt(mid) > inChar) {
               hi = mid;
            } else {
               if (this.codePointAt(mid) >= inChar) {
                  lo = mid;
                  break;
               }

               lo = mid;
            }
         } while (hi - lo > 1);

         if (this.codePointAt(lo) != inChar) {
            dest.appendCodePoint(inChar);
         } else {
            this.appendValueTo(lo, dest);
         }
      }

      public int length() {
         return this.fCFUKeys.length;
      }

      public int codePointAt(int index) {
         return SpoofChecker.ConfusableDataUtils.keyToCodePoint(this.fCFUKeys[index]);
      }

      public void appendValueTo(int index, StringBuilder dest) {
         int stringLength = SpoofChecker.ConfusableDataUtils.keyToLength(this.fCFUKeys[index]);
         short value = this.fCFUValues[index];
         if (stringLength == 1) {
            dest.append((char)value);
         } else {
            dest.append(this.fCFUStrings, value, value + stringLength);
         }
      }

      private static final class DefaultData {
         private static SpoofChecker.SpoofData INSTANCE = null;
         private static IOException EXCEPTION = null;

         static {
            try {
               INSTANCE = new SpoofChecker.SpoofData(ICUBinary.getRequiredData("confusables.cfu"));
            } catch (IOException var1) {
               EXCEPTION = var1;
            }
         }
      }

      private static final class IsAcceptable implements ICUBinary.Authenticate {
         private IsAcceptable() {
         }

         @Override
         public boolean isDataVersionAcceptable(byte[] version) {
            return version[0] == 2 || version[1] != 0 || version[2] != 0 || version[3] != 0;
         }
      }
   }
}
