package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;

public class Padder {
   public static final String FALLBACK_PADDING_STRING = " ";
   public static final Padder NONE = new Padder(null, -1, null);
   String paddingString;
   int targetWidth;
   Padder.PadPosition position;

   public Padder(String paddingString, int targetWidth, Padder.PadPosition position) {
      this.paddingString = paddingString == null ? " " : paddingString;
      this.targetWidth = targetWidth;
      this.position = position == null ? Padder.PadPosition.BEFORE_PREFIX : position;
   }

   public static Padder none() {
      return NONE;
   }

   public static Padder codePoints(int cp, int targetWidth, Padder.PadPosition position) {
      if (targetWidth >= 0) {
         String paddingString = String.valueOf(Character.toChars(cp));
         return new Padder(paddingString, targetWidth, position);
      } else {
         throw new IllegalArgumentException("Padding width must not be negative");
      }
   }

   public static Padder forProperties(DecimalFormatProperties properties) {
      return new Padder(properties.getPadString(), properties.getFormatWidth(), properties.getPadPosition());
   }

   public boolean isValid() {
      return this.targetWidth > 0;
   }

   public int padAndApply(Modifier mod1, Modifier mod2, FormattedStringBuilder string, int leftIndex, int rightIndex) {
      int modLength = mod1.getCodePointCount() + mod2.getCodePointCount();
      int requiredPadding = this.targetWidth - modLength - string.codePointCount();

      assert leftIndex == 0 && rightIndex == string.length();

      int length = 0;
      if (requiredPadding <= 0) {
         length += mod1.apply(string, leftIndex, rightIndex);
         return length + mod2.apply(string, leftIndex, rightIndex + length);
      } else {
         if (this.position == Padder.PadPosition.AFTER_PREFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, leftIndex);
         } else if (this.position == Padder.PadPosition.BEFORE_SUFFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, rightIndex + length);
         }

         length += mod1.apply(string, leftIndex, rightIndex + length);
         length += mod2.apply(string, leftIndex, rightIndex + length);
         if (this.position == Padder.PadPosition.BEFORE_PREFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, leftIndex);
         } else if (this.position == Padder.PadPosition.AFTER_SUFFIX) {
            length += addPaddingHelper(this.paddingString, requiredPadding, string, rightIndex + length);
         }

         return length;
      }
   }

   private static int addPaddingHelper(String paddingString, int requiredPadding, FormattedStringBuilder string, int index) {
      for (int i = 0; i < requiredPadding; i++) {
         string.insert(index, paddingString, null);
      }

      return paddingString.length() * requiredPadding;
   }

   public static enum PadPosition {
      BEFORE_PREFIX,
      AFTER_PREFIX,
      BEFORE_SUFFIX,
      AFTER_SUFFIX;

      public static Padder.PadPosition fromOld(int old) {
         switch (old) {
            case 0:
               return BEFORE_PREFIX;
            case 1:
               return AFTER_PREFIX;
            case 2:
               return BEFORE_SUFFIX;
            case 3:
               return AFTER_SUFFIX;
            default:
               throw new IllegalArgumentException("Don't know how to map " + old);
         }
      }

      public int toOld() {
         switch (this) {
            case BEFORE_PREFIX:
               return 0;
            case AFTER_PREFIX:
               return 1;
            case BEFORE_SUFFIX:
               return 2;
            case AFTER_SUFFIX:
               return 3;
            default:
               return -1;
         }
      }
   }
}
