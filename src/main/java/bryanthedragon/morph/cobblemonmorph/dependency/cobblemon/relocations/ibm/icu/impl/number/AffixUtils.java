package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;

public class AffixUtils {
   private static final int STATE_BASE = 0;
   private static final int STATE_FIRST_QUOTE = 1;
   private static final int STATE_INSIDE_QUOTE = 2;
   private static final int STATE_AFTER_QUOTE = 3;
   private static final int STATE_FIRST_CURR = 4;
   private static final int STATE_SECOND_CURR = 5;
   private static final int STATE_THIRD_CURR = 6;
   private static final int STATE_FOURTH_CURR = 7;
   private static final int STATE_FIFTH_CURR = 8;
   private static final int STATE_OVERFLOW_CURR = 9;
   private static final int TYPE_CODEPOINT = 0;
   public static final int TYPE_MINUS_SIGN = -1;
   public static final int TYPE_PLUS_SIGN = -2;
   public static final int TYPE_APPROXIMATELY_SIGN = -3;
   public static final int TYPE_PERCENT = -4;
   public static final int TYPE_PERMILLE = -5;
   public static final int TYPE_CURRENCY_SINGLE = -6;
   public static final int TYPE_CURRENCY_DOUBLE = -7;
   public static final int TYPE_CURRENCY_TRIPLE = -8;
   public static final int TYPE_CURRENCY_QUAD = -9;
   public static final int TYPE_CURRENCY_QUINT = -10;
   public static final int TYPE_CURRENCY_OVERFLOW = -15;

   public static int estimateLength(CharSequence patternString) {
      if (patternString == null) {
         return 0;
      } else {
         int state = 0;
         int offset = 0;
         int length = 0;

         while (offset < patternString.length()) {
            int cp = Character.codePointAt(patternString, offset);
            switch (state) {
               case 0:
                  if (cp == 39) {
                     state = 1;
                  } else {
                     length++;
                  }
                  break;
               case 1:
                  if (cp == 39) {
                     length++;
                     state = 0;
                  } else {
                     length++;
                     state = 2;
                  }
                  break;
               case 2:
                  if (cp == 39) {
                     state = 3;
                  } else {
                     length++;
                  }
                  break;
               case 3:
                  if (cp == 39) {
                     length++;
                     state = 2;
                  } else {
                     length++;
                  }
                  break;
               default:
                  throw new AssertionError();
            }

            offset += Character.charCount(cp);
         }

         switch (state) {
            case 1:
            case 2:
               throw new IllegalArgumentException("Unterminated quote: \"" + patternString + "\"");
            default:
               return length;
         }
      }
   }

   public static int escape(CharSequence input, StringBuilder output) {
      if (input == null) {
         return 0;
      } else {
         int state = 0;
         int offset = 0;
         int startLength = output.length();

         while (offset < input.length()) {
            int cp = Character.codePointAt(input, offset);
            switch (cp) {
               case 37:
               case 43:
               case 45:
               case 164:
               case 8240:
                  if (state == 0) {
                     output.append('\'');
                     output.appendCodePoint(cp);
                     state = 2;
                  } else {
                     output.appendCodePoint(cp);
                  }
                  break;
               case 39:
                  output.append("''");
                  break;
               default:
                  if (state == 2) {
                     output.append('\'');
                     output.appendCodePoint(cp);
                     state = 0;
                  } else {
                     output.appendCodePoint(cp);
                  }
            }

            offset += Character.charCount(cp);
         }

         if (state == 2) {
            output.append('\'');
         }

         return output.length() - startLength;
      }
   }

   public static String escape(CharSequence input) {
      if (input == null) {
         return null;
      } else {
         StringBuilder sb = new StringBuilder();
         escape(input, sb);
         return sb.toString();
      }
   }

   public static final NumberFormat.Field getFieldForType(int type) {
      switch (type) {
         case -15:
            return NumberFormat.Field.CURRENCY;
         case -14:
         case -13:
         case -12:
         case -11:
         default:
            throw new AssertionError();
         case -10:
            return NumberFormat.Field.CURRENCY;
         case -9:
            return NumberFormat.Field.CURRENCY;
         case -8:
            return NumberFormat.Field.CURRENCY;
         case -7:
            return NumberFormat.Field.CURRENCY;
         case -6:
            return NumberFormat.Field.CURRENCY;
         case -5:
            return NumberFormat.Field.PERMILLE;
         case -4:
            return NumberFormat.Field.PERCENT;
         case -3:
            return NumberFormat.Field.APPROXIMATELY_SIGN;
         case -2:
            return NumberFormat.Field.SIGN;
         case -1:
            return NumberFormat.Field.SIGN;
      }
   }

   public static int unescape(
      CharSequence affixPattern, FormattedStringBuilder output, int position, AffixUtils.SymbolProvider provider, NumberFormat.Field field
   ) {
      assert affixPattern != null;

      int length = 0;
      long tag = 0L;

      while (hasNext(tag, affixPattern)) {
         tag = nextToken(tag, affixPattern);
         int typeOrCp = getTypeOrCp(tag);
         if (typeOrCp == -15) {
            length += output.insertCodePoint(position + length, 65533, NumberFormat.Field.CURRENCY);
         } else if (typeOrCp < 0) {
            length += output.insert(position + length, provider.getSymbol(typeOrCp), getFieldForType(typeOrCp));
         } else {
            length += output.insertCodePoint(position + length, typeOrCp, field);
         }
      }

      return length;
   }

   public static int unescapedCount(CharSequence affixPattern, boolean lengthOrCount, AffixUtils.SymbolProvider provider) {
      int length = 0;
      long tag = 0L;

      while (hasNext(tag, affixPattern)) {
         tag = nextToken(tag, affixPattern);
         int typeOrCp = getTypeOrCp(tag);
         if (typeOrCp == -15) {
            length++;
         } else if (typeOrCp < 0) {
            CharSequence symbol = provider.getSymbol(typeOrCp);
            length += lengthOrCount ? symbol.length() : Character.codePointCount(symbol, 0, symbol.length());
         } else {
            length += lengthOrCount ? Character.charCount(typeOrCp) : 1;
         }
      }

      return length;
   }

   public static boolean containsType(CharSequence affixPattern, int type) {
      if (affixPattern != null && affixPattern.length() != 0) {
         long tag = 0L;

         while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            if (getTypeOrCp(tag) == type) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static boolean hasCurrencySymbols(CharSequence affixPattern) {
      if (affixPattern != null && affixPattern.length() != 0) {
         long tag = 0L;

         while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            int typeOrCp = getTypeOrCp(tag);
            if (typeOrCp < 0 && getFieldForType(typeOrCp) == NumberFormat.Field.CURRENCY) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static String replaceType(CharSequence affixPattern, int type, char replacementChar) {
      if (affixPattern != null && affixPattern.length() != 0) {
         char[] chars = affixPattern.toString().toCharArray();
         long tag = 0L;

         while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            if (getTypeOrCp(tag) == type) {
               int offset = getOffset(tag);
               chars[offset - 1] = replacementChar;
            }
         }

         return new String(chars);
      } else {
         return "";
      }
   }

   public static boolean containsOnlySymbolsAndIgnorables(CharSequence affixPattern, UnicodeSet ignorables) {
      if (affixPattern == null) {
         return true;
      } else {
         long tag = 0L;

         while (hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            int typeOrCp = getTypeOrCp(tag);
            if (typeOrCp >= 0 && !ignorables.contains(typeOrCp)) {
               return false;
            }
         }

         return true;
      }
   }

   public static void iterateWithConsumer(CharSequence affixPattern, AffixUtils.TokenConsumer consumer) {
      assert affixPattern != null;

      long tag = 0L;

      while (hasNext(tag, affixPattern)) {
         tag = nextToken(tag, affixPattern);
         int typeOrCp = getTypeOrCp(tag);
         consumer.consumeToken(typeOrCp);
      }
   }

   private static long nextToken(long tag, CharSequence patternString) {
      int offset = getOffset(tag);
      int state = getState(tag);

      while (offset < patternString.length()) {
         int cp = Character.codePointAt(patternString, offset);
         int count = Character.charCount(cp);
         switch (state) {
            case 0:
               switch (cp) {
                  case 37:
                     return makeTag(offset + count, -4, 0, 0);
                  case 39:
                     state = 1;
                     offset += count;
                     continue;
                  case 43:
                     return makeTag(offset + count, -2, 0, 0);
                  case 45:
                     return makeTag(offset + count, -1, 0, 0);
                  case 126:
                     return makeTag(offset + count, -3, 0, 0);
                  case 164:
                     state = 4;
                     offset += count;
                     continue;
                  case 8240:
                     return makeTag(offset + count, -5, 0, 0);
                  default:
                     return makeTag(offset + count, 0, 0, cp);
               }
            case 1:
               if (cp == 39) {
                  return makeTag(offset + count, 0, 0, cp);
               }

               return makeTag(offset + count, 0, 2, cp);
            case 2:
               if (cp != 39) {
                  return makeTag(offset + count, 0, 2, cp);
               }

               state = 3;
               offset += count;
               break;
            case 3:
               if (cp == 39) {
                  return makeTag(offset + count, 0, 2, cp);
               }

               state = 0;
               break;
            case 4:
               if (cp != 164) {
                  return makeTag(offset, -6, 0, 0);
               }

               state = 5;
               offset += count;
               break;
            case 5:
               if (cp != 164) {
                  return makeTag(offset, -7, 0, 0);
               }

               state = 6;
               offset += count;
               break;
            case 6:
               if (cp != 164) {
                  return makeTag(offset, -8, 0, 0);
               }

               state = 7;
               offset += count;
               break;
            case 7:
               if (cp != 164) {
                  return makeTag(offset, -9, 0, 0);
               }

               state = 8;
               offset += count;
               break;
            case 8:
               if (cp != 164) {
                  return makeTag(offset, -10, 0, 0);
               }

               state = 9;
               offset += count;
               break;
            case 9:
               if (cp != 164) {
                  return makeTag(offset, -15, 0, 0);
               }

               offset += count;
               break;
            default:
               throw new AssertionError();
         }
      }

      switch (state) {
         case 0:
            return -1L;
         case 1:
         case 2:
            throw new IllegalArgumentException("Unterminated quote in pattern affix: \"" + patternString + "\"");
         case 3:
            return -1L;
         case 4:
            return makeTag(offset, -6, 0, 0);
         case 5:
            return makeTag(offset, -7, 0, 0);
         case 6:
            return makeTag(offset, -8, 0, 0);
         case 7:
            return makeTag(offset, -9, 0, 0);
         case 8:
            return makeTag(offset, -10, 0, 0);
         case 9:
            return makeTag(offset, -15, 0, 0);
         default:
            throw new AssertionError();
      }
   }

   private static boolean hasNext(long tag, CharSequence string) {
      assert tag >= 0L;

      int state = getState(tag);
      int offset = getOffset(tag);
      if (state == 2 && offset == string.length() - 1 && string.charAt(offset) == '\'') {
         return false;
      } else {
         return state != 0 ? true : offset < string.length();
      }
   }

   private static int getTypeOrCp(long tag) {
      assert tag >= 0L;

      int type = getType(tag);
      return type == 0 ? getCodePoint(tag) : -type;
   }

   private static long makeTag(int offset, int type, int state, int cp) {
      long tag = 0L;
      tag |= offset;
      tag |= -type << 32;
      tag |= (long)state << 36;
      tag |= (long)cp << 40;

      assert tag >= 0L;

      return tag;
   }

   private static int getOffset(long tag) {
      return (int)(tag & -1L);
   }

   private static int getType(long tag) {
      return (int)(tag >>> 32 & 15L);
   }

   private static int getState(long tag) {
      return (int)(tag >>> 36 & 15L);
   }

   private static int getCodePoint(long tag) {
      return (int)(tag >>> 40);
   }

   public interface SymbolProvider {
      CharSequence getSymbol(int var1);
   }

   public interface TokenConsumer {
      void consumeToken(int var1);
   }
}
