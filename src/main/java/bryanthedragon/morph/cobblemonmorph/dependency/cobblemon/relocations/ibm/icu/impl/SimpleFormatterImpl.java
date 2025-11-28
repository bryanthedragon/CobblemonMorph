package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.text.Format.Field;

public final class SimpleFormatterImpl {
   private static final int ARG_NUM_LIMIT = 256;
   private static final char LEN1_CHAR = 'ā';
   private static final char LEN2_CHAR = 'Ă';
   private static final char LEN3_CHAR = 'ă';
   private static final char SEGMENT_LENGTH_ARGUMENT_CHAR = '\uffff';
   private static final int MAX_SEGMENT_LENGTH = 65279;
   private static final String[][] COMMON_PATTERNS = new String[][]{
      {"{0} {1}", "\u0002\u0000ā \u0001"},
      {"{0} ({1})", "\u0002\u0000Ă (\u0001ā)"},
      {"{0}, {1}", "\u0002\u0000Ă, \u0001"},
      {"{0} – {1}", "\u0002\u0000ă – \u0001"}
   };

   private SimpleFormatterImpl() {
   }

   public static String compileToStringMinMaxArguments(CharSequence pattern, StringBuilder sb, int min, int max) {
      if (min <= 2 && 2 <= max) {
         for (String[] pair : COMMON_PATTERNS) {
            if (pair[0].contentEquals(pattern)) {
               assert pair[1].charAt(0) == 2;

               return pair[1];
            }
         }
      }

      int patternLength = pattern.length();
      sb.ensureCapacity(patternLength);
      sb.setLength(1);
      int textLength = 0;
      int maxArg = -1;
      boolean inQuote = false;
      int i = 0;

      while (i < patternLength) {
         char c = pattern.charAt(i++);
         if (c == '\'') {
            if (i < patternLength && (c = pattern.charAt(i)) == '\'') {
               i++;
            } else {
               if (inQuote) {
                  inQuote = false;
                  continue;
               }

               if (c != '{' && c != '}') {
                  c = '\'';
               } else {
                  i++;
                  inQuote = true;
               }
            }
         } else if (!inQuote && c == '{') {
            if (textLength > 0) {
               sb.setCharAt(sb.length() - textLength - 1, (char)(256 + textLength));
               textLength = 0;
            }

            int argNumber;
            if (i + 1 < patternLength && 0 <= (argNumber = pattern.charAt(i) - '0') && argNumber <= 9 && pattern.charAt(i + 1) == '}') {
               i += 2;
            } else {
               int argStart = i - 1;
               argNumber = -1;
               if (i < patternLength && '1' <= (c = pattern.charAt(i++)) && c <= '9') {
                  argNumber = c - '0';

                  while (i < patternLength && '0' <= (c = pattern.charAt(i++)) && c <= '9') {
                     argNumber = argNumber * 10 + (c - '0');
                     if (argNumber >= 256) {
                        break;
                     }
                  }
               }

               if (argNumber < 0 || c != '}') {
                  throw new IllegalArgumentException(
                     "Argument syntax error in pattern \"" + pattern + "\" at index " + argStart + ": " + pattern.subSequence(argStart, i)
                  );
               }
            }

            if (argNumber > maxArg) {
               maxArg = argNumber;
            }

            sb.append((char)argNumber);
            continue;
         }

         if (textLength == 0) {
            sb.append('\uffff');
         }

         sb.append(c);
         if (++textLength == 65279) {
            textLength = 0;
         }
      }

      if (textLength > 0) {
         sb.setCharAt(sb.length() - textLength - 1, (char)(256 + textLength));
      }

      i = maxArg + 1;
      if (i < min) {
         throw new IllegalArgumentException("Fewer than minimum " + min + " arguments in pattern \"" + pattern + "\"");
      } else if (i > max) {
         throw new IllegalArgumentException("More than maximum " + max + " arguments in pattern \"" + pattern + "\"");
      } else {
         sb.setCharAt(0, (char)i);
         return sb.toString();
      }
   }

   public static int getArgumentLimit(String compiledPattern) {
      return compiledPattern.charAt(0);
   }

   public static String formatCompiledPattern(String compiledPattern, CharSequence... values) {
      return formatAndAppend(compiledPattern, new StringBuilder(), null, values).toString();
   }

   public static String formatRawPattern(String pattern, int min, int max, CharSequence... values) {
      StringBuilder sb = new StringBuilder();
      String compiledPattern = compileToStringMinMaxArguments(pattern, sb, min, max);
      sb.setLength(0);
      return formatAndAppend(compiledPattern, sb, null, values).toString();
   }

   public static StringBuilder formatAndAppend(String compiledPattern, StringBuilder appendTo, int[] offsets, CharSequence... values) {
      int valuesLength = values != null ? values.length : 0;
      if (valuesLength < getArgumentLimit(compiledPattern)) {
         throw new IllegalArgumentException("Too few values.");
      } else {
         return format(compiledPattern, values, appendTo, null, true, offsets);
      }
   }

   public static StringBuilder formatAndReplace(String compiledPattern, StringBuilder result, int[] offsets, CharSequence... values) {
      int valuesLength = values != null ? values.length : 0;
      if (valuesLength < getArgumentLimit(compiledPattern)) {
         throw new IllegalArgumentException("Too few values.");
      } else {
         int firstArg = -1;
         String resultCopy = null;
         if (getArgumentLimit(compiledPattern) > 0) {
            int i = 1;

            while (i < compiledPattern.length()) {
               int n = compiledPattern.charAt(i++);
               if (n < 256) {
                  if (values[n] == result) {
                     if (i == 2) {
                        firstArg = n;
                     } else if (resultCopy == null) {
                        resultCopy = result.toString();
                     }
                  }
               } else {
                  i += n - 256;
               }
            }
         }

         if (firstArg < 0) {
            result.setLength(0);
         }

         return format(compiledPattern, values, result, resultCopy, false, offsets);
      }
   }

   public static String getTextWithNoArguments(String compiledPattern) {
      int capacity = compiledPattern.length() - 1 - getArgumentLimit(compiledPattern);
      StringBuilder sb = new StringBuilder(capacity);
      int i = 1;

      while (i < compiledPattern.length()) {
         int segmentLength = compiledPattern.charAt(i++) - 256;
         if (segmentLength > 0) {
            int limit = i + segmentLength;
            sb.append(compiledPattern, i, limit);
            i = limit;
         }
      }

      return sb.toString();
   }

   public static int getLength(String compiledPattern, boolean codePoints) {
      int result = 0;
      int i = 1;

      while (i < compiledPattern.length()) {
         int segmentLength = compiledPattern.charAt(i++) - 256;
         if (segmentLength > 0) {
            int limit = i + segmentLength;
            if (codePoints) {
               result += Character.codePointCount(compiledPattern, i, limit);
            } else {
               result += limit - i;
            }

            i = limit;
         }
      }

      return result;
   }

   public static int getPrefixLength(String compiledPattern) {
      if (compiledPattern.length() == 1) {
         return 0;
      } else if (compiledPattern.charAt(0) == 0) {
         return compiledPattern.length() - 2;
      } else {
         return compiledPattern.charAt(1) <= 256 ? 0 : compiledPattern.charAt(1) - 256;
      }
   }

   public static int formatPrefixSuffix(String compiledPattern, Field field, int start, int end, FormattedStringBuilder output) {
      int argLimit = getArgumentLimit(compiledPattern);
      if (argLimit == 0) {
         return output.splice(start, end, compiledPattern, 2, compiledPattern.length(), field);
      } else {
         assert argLimit == 1;

         int length = 0;
         int suffixOffset;
         if (compiledPattern.charAt(1) != 0) {
            int prefixLength = compiledPattern.charAt(1) - 256;
            length = output.insert(start, compiledPattern, 2, 2 + prefixLength, field);
            suffixOffset = 3 + prefixLength;
         } else {
            suffixOffset = 2;
         }

         if (suffixOffset < compiledPattern.length()) {
            int suffixLength = compiledPattern.charAt(suffixOffset) - 256;
            length += output.insert(end + length, compiledPattern, 1 + suffixOffset, 1 + suffixOffset + suffixLength, field);
         }

         return length;
      }
   }

   private static StringBuilder format(
      String compiledPattern, CharSequence[] values, StringBuilder result, String resultCopy, boolean forbidResultAsValue, int[] offsets
   ) {
      int offsetsLength;
      if (offsets == null) {
         offsetsLength = 0;
      } else {
         offsetsLength = offsets.length;

         for (int i = 0; i < offsetsLength; i++) {
            offsets[i] = -1;
         }
      }

      int i = 1;

      while (i < compiledPattern.length()) {
         int n = compiledPattern.charAt(i++);
         if (n < 256) {
            CharSequence value = values[n];
            if (value == result) {
               if (forbidResultAsValue) {
                  throw new IllegalArgumentException("Value must not be same object as result");
               }

               if (i == 2) {
                  if (n < offsetsLength) {
                     offsets[n] = 0;
                  }
               } else {
                  if (n < offsetsLength) {
                     offsets[n] = result.length();
                  }

                  result.append(resultCopy);
               }
            } else {
               if (n < offsetsLength) {
                  offsets[n] = result.length();
               }

               result.append(value);
            }
         } else {
            int limit = i + (n - 256);
            result.append(compiledPattern, i, limit);
            i = limit;
         }
      }

      return result;
   }

   public static class IterInternal {
      public static final long DONE = -1L;

      public static long step(long state, CharSequence compiledPattern, Appendable output) {
         int i = (int)(state >>> 32);

         assert i < compiledPattern.length();

         i++;

         while (i < compiledPattern.length() && compiledPattern.charAt(i) > 256) {
            int limit = i + compiledPattern.charAt(i) + 1 - 256;

            try {
               output.append(compiledPattern, i + 1, limit);
            } catch (IOException var7) {
               throw new ICUUncheckedIOException(var7);
            }

            i = limit;
         }

         return i == compiledPattern.length() ? -1L : (long)i << 32 | compiledPattern.charAt(i);
      }

      public static int getArgIndex(long state) {
         return (int)state;
      }
   }
}
