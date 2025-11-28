package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.text.StringPrepParseException;
import com.cobblemon.mod.relocations.ibm.icu.text.UTF16;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUInputTooLongException;

public final class Punycode {
   private static final int BASE = 36;
   private static final int TMIN = 1;
   private static final int TMAX = 26;
   private static final int SKEW = 38;
   private static final int DAMP = 700;
   private static final int INITIAL_BIAS = 72;
   private static final int INITIAL_N = 128;
   private static final char HYPHEN = '-';
   private static final char DELIMITER = '-';
   private static final int ZERO = 48;
   private static final int SMALL_A = 97;
   private static final int SMALL_Z = 122;
   private static final int CAPITAL_A = 65;
   private static final int CAPITAL_Z = 90;
   private static final int ENCODE_MAX_CODE_UNITS = 1000;
   private static final int DECODE_MAX_CHARS = 2000;

   private static int adaptBias(int delta, int length, boolean firstTime) {
      if (firstTime) {
         delta /= 700;
      } else {
         delta /= 2;
      }

      delta += delta / length;

      int count;
      for (count = 0; delta > 455; count += 36) {
         delta /= 35;
      }

      return count + 36 * delta / (delta + 38);
   }

   private static final int decodeDigit(int cp) {
      if (cp <= 90) {
         if (cp <= 57) {
            return cp < 48 ? -1 : cp - 48 + 26;
         } else {
            return cp - 65;
         }
      } else {
         return cp <= 122 ? cp - 97 : -1;
      }
   }

   private static char asciiCaseMap(char b, boolean uppercase) {
      if (uppercase) {
         if ('a' <= b && b <= 'z') {
            b = (char)(b - ' ');
         }
      } else if ('A' <= b && b <= 'Z') {
         b = (char)(b + ' ');
      }

      return b;
   }

   private static char digitToBasic(int digit, boolean uppercase) {
      if (digit < 26) {
         return uppercase ? (char)(65 + digit) : (char)(97 + digit);
      } else {
         return (char)(22 + digit);
      }
   }

   public static StringBuilder encode(CharSequence src, boolean[] caseFlags) throws StringPrepParseException {
      int srcLength = src.length();
      if (srcLength > 1000) {
         throw new ICUInputTooLongException("input too long: " + srcLength + " UTF-16 code units");
      } else {
         int[] cpBuffer = new int[srcLength];
         StringBuilder dest = new StringBuilder(srcLength);
         int srcCPCount = 0;

         for (int j = 0; j < srcLength; j++) {
            char c = src.charAt(j);
            if (isBasic(c)) {
               cpBuffer[srcCPCount++] = 0;
               dest.append(caseFlags != null ? asciiCaseMap(c, caseFlags[j]) : c);
            } else {
               int n = (caseFlags != null && caseFlags[j] ? 1 : 0) << 31;
               if (!UTF16.isSurrogate(c)) {
                  n |= c;
               } else {
                  char c2;
                  if (!UTF16.isLeadSurrogate(c) || j + 1 >= srcLength || !UTF16.isTrailSurrogate(c2 = src.charAt(j + 1))) {
                     throw new StringPrepParseException("Illegal char found", 1);
                  }

                  j++;
                  n |= UCharacter.getCodePoint(c, c2);
               }

               cpBuffer[srcCPCount++] = n;
            }
         }

         int basicLength = dest.length();
         if (basicLength > 0) {
            dest.append('-');
         }

         int n = 128;
         int delta = 0;
         int bias = 72;

         for (int handledCPCount = basicLength; handledCPCount < srcCPCount; n++) {
            int m = Integer.MAX_VALUE;

            for (int var22 = 0; var22 < srcCPCount; var22++) {
               int q = cpBuffer[var22] & 2147483647;
               if (n <= q && q < m) {
                  m = q;
               }
            }

            if (m - n > (Integer.MAX_VALUE - handledCPCount - delta) / (handledCPCount + 1)) {
               throw new IllegalStateException("Internal program error");
            }

            delta += (m - n) * (handledCPCount + 1);
            n = m;

            for (int var23 = 0; var23 < srcCPCount; var23++) {
               int q = cpBuffer[var23] & 2147483647;
               if (q < n) {
                  delta++;
               } else if (q == n) {
                  q = delta;
                  int k = 36;

                  while (true) {
                     int t = k - bias;
                     if (t < 1) {
                        t = 1;
                     } else if (k >= bias + 26) {
                        t = 26;
                     }

                     if (q < t) {
                        dest.append(digitToBasic(q, cpBuffer[var23] < 0));
                        bias = adaptBias(delta, handledCPCount + 1, handledCPCount == basicLength);
                        delta = 0;
                        handledCPCount++;
                        break;
                     }

                     dest.append(digitToBasic(t + (q - t) % (36 - t), false));
                     q = (q - t) / (36 - t);
                     k += 36;
                  }
               }
            }

            delta++;
         }

         return dest;
      }
   }

   private static boolean isBasic(int ch) {
      return ch < 128;
   }

   private static boolean isBasicUpperCase(int ch) {
      return 65 <= ch && ch >= 90;
   }

   private static boolean isSurrogate(int ch) {
      return (ch & -2048) == 55296;
   }

   public static StringBuilder decode(CharSequence src, boolean[] caseFlags) throws StringPrepParseException {
      int srcLength = src.length();
      if (srcLength > 2000) {
         throw new ICUInputTooLongException("input too long: " + srcLength + " characters");
      } else {
         StringBuilder dest = new StringBuilder(src.length());
         int j = srcLength;

         while (j > 0) {
            if (src.charAt(--j) == '-') {
               break;
            }
         }

         int destCPCount = j;
         int basicLength = j;

         for (int var21 = 0; var21 < basicLength; var21++) {
            char b = src.charAt(var21);
            if (!isBasic(b)) {
               throw new StringPrepParseException("Illegal char found", 0);
            }

            dest.append(b);
            if (caseFlags != null && var21 < caseFlags.length) {
               caseFlags[var21] = isBasicUpperCase(b);
            }
         }

         int n = 128;
         int i = 0;
         int bias = 72;
         int firstSupplementaryIndex = 1000000000;
         int in = basicLength > 0 ? basicLength + 1 : 0;

         label110:
         while (in < srcLength) {
            int oldi = i;
            int w = 1;

            for (int k = 36; in < srcLength; k += 36) {
               int digit = decodeDigit(src.charAt(in++));
               if (digit < 0) {
                  throw new StringPrepParseException("Invalid char found", 0);
               }

               if (digit > (Integer.MAX_VALUE - i) / w) {
                  throw new StringPrepParseException("Illegal char found", 1);
               }

               i += digit * w;
               int t = k - bias;
               if (t < 1) {
                  t = 1;
               } else if (k >= bias + 26) {
                  t = 26;
               }

               if (digit < t) {
                  destCPCount++;
                  bias = adaptBias(i - oldi, destCPCount, oldi == 0);
                  if (i / destCPCount > Integer.MAX_VALUE - n) {
                     throw new StringPrepParseException("Illegal char found", 1);
                  }

                  n += i / destCPCount;
                  i %= destCPCount;
                  if (n <= 1114111 && !isSurrogate(n)) {
                     int cpLength = Character.charCount(n);
                     int codeUnitIndex;
                     if (i <= firstSupplementaryIndex) {
                        codeUnitIndex = i;
                        if (cpLength > 1) {
                           firstSupplementaryIndex = i;
                        } else {
                           firstSupplementaryIndex++;
                        }
                     } else {
                        codeUnitIndex = dest.offsetByCodePoints(firstSupplementaryIndex, i - firstSupplementaryIndex);
                     }

                     if (caseFlags != null && dest.length() + cpLength <= caseFlags.length) {
                        if (codeUnitIndex < dest.length()) {
                           System.arraycopy(caseFlags, codeUnitIndex, caseFlags, codeUnitIndex + cpLength, dest.length() - codeUnitIndex);
                        }

                        caseFlags[codeUnitIndex] = isBasicUpperCase(src.charAt(in - 1));
                        if (cpLength == 2) {
                           caseFlags[codeUnitIndex + 1] = false;
                        }
                     }

                     if (cpLength == 1) {
                        dest.insert(codeUnitIndex, (char)n);
                     } else {
                        dest.insert(codeUnitIndex, UTF16.getLeadSurrogate(n));
                        dest.insert(codeUnitIndex + 1, UTF16.getTrailSurrogate(n));
                     }

                     i++;
                     continue label110;
                  }

                  throw new StringPrepParseException("Illegal char found", 1);
               }

               if (w > Integer.MAX_VALUE / (36 - t)) {
                  throw new StringPrepParseException("Illegal char found", 1);
               }

               w *= 36 - t;
            }

            throw new StringPrepParseException("Illegal char found", 1);
         }

         return dest;
      }
   }
}
