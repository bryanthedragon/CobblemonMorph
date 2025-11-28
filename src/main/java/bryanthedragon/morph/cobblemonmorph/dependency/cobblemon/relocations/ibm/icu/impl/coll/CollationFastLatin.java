package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

public final class CollationFastLatin {
   public static final int VERSION = 2;
   public static final int LATIN_MAX = 383;
   public static final int LATIN_LIMIT = 384;
   static final int LATIN_MAX_UTF8_LEAD = 197;
   static final int PUNCT_START = 8192;
   static final int PUNCT_LIMIT = 8256;
   static final int NUM_FAST_CHARS = 448;
   static final int SHORT_PRIMARY_MASK = 64512;
   static final int INDEX_MASK = 1023;
   static final int SECONDARY_MASK = 992;
   static final int CASE_MASK = 24;
   static final int LONG_PRIMARY_MASK = 65528;
   static final int TERTIARY_MASK = 7;
   static final int CASE_AND_TERTIARY_MASK = 31;
   static final int TWO_SHORT_PRIMARIES_MASK = -67044352;
   static final int TWO_LONG_PRIMARIES_MASK = -458760;
   static final int TWO_SECONDARIES_MASK = 65012704;
   static final int TWO_CASES_MASK = 1572888;
   static final int TWO_TERTIARIES_MASK = 458759;
   static final int CONTRACTION = 1024;
   static final int EXPANSION = 2048;
   static final int MIN_LONG = 3072;
   static final int LONG_INC = 8;
   static final int MAX_LONG = 4088;
   static final int MIN_SHORT = 4096;
   static final int SHORT_INC = 1024;
   static final int MAX_SHORT = 64512;
   static final int MIN_SEC_BEFORE = 0;
   static final int SEC_INC = 32;
   static final int MAX_SEC_BEFORE = 128;
   static final int COMMON_SEC = 160;
   static final int MIN_SEC_AFTER = 192;
   static final int MAX_SEC_AFTER = 352;
   static final int MIN_SEC_HIGH = 384;
   static final int MAX_SEC_HIGH = 992;
   static final int SEC_OFFSET = 32;
   static final int COMMON_SEC_PLUS_OFFSET = 192;
   static final int TWO_SEC_OFFSETS = 2097184;
   static final int TWO_COMMON_SEC_PLUS_OFFSET = 12583104;
   static final int LOWER_CASE = 8;
   static final int TWO_LOWER_CASES = 524296;
   static final int COMMON_TER = 0;
   static final int MAX_TER_AFTER = 7;
   static final int TER_OFFSET = 32;
   static final int COMMON_TER_PLUS_OFFSET = 32;
   static final int TWO_TER_OFFSETS = 2097184;
   static final int TWO_COMMON_TER_PLUS_OFFSET = 2097184;
   static final int MERGE_WEIGHT = 3;
   static final int EOS = 2;
   static final int BAIL_OUT = 1;
   static final int CONTR_CHAR_MASK = 511;
   static final int CONTR_LENGTH_SHIFT = 9;
   public static final int BAIL_OUT_RESULT = -2;

   static int getCharIndex(char c) {
      if (c <= 383) {
         return c;
      } else {
         return 8192 <= c && c < 8256 ? c - 7808 : -1;
      }
   }

   public static int getOptions(CollationData data, CollationSettings settings, char[] primaries) {
      char[] header = data.fastLatinTableHeader;
      if (header == null) {
         return -1;
      } else {
         assert header[0] >> '\b' == 2;

         if (primaries.length != 384) {
            assert false;

            return -1;
         } else {
            int miniVarTop;
            if ((settings.options & 12) == 0) {
               miniVarTop = 3071;
            } else {
               int headerLength = header[0] & 255;
               int i = 1 + settings.getMaxVariable();
               if (i >= headerLength) {
                  return -1;
               }

               miniVarTop = header[i];
            }

            boolean digitsAreReordered = false;
            if (settings.hasReordering()) {
               long prevStart = 0L;
               long beforeDigitStart = 0L;
               long digitStart = 0L;
               long afterDigitStart = 0L;

               for (int group = 4096; group < 4104; group++) {
                  long start = data.getFirstPrimaryForGroup(group);
                  start = settings.reorder(start);
                  if (group == 4100) {
                     beforeDigitStart = prevStart;
                     digitStart = start;
                  } else if (start != 0L) {
                     if (start < prevStart) {
                        return -1;
                     }

                     if (digitStart != 0L && afterDigitStart == 0L && prevStart == beforeDigitStart) {
                        afterDigitStart = start;
                     }

                     prevStart = start;
                  }
               }

               long latinStart = data.getFirstPrimaryForGroup(25);
               latinStart = settings.reorder(latinStart);
               if (latinStart < prevStart) {
                  return -1;
               }

               if (afterDigitStart == 0L) {
                  afterDigitStart = latinStart;
               }

               if (beforeDigitStart >= digitStart || digitStart >= afterDigitStart) {
                  digitsAreReordered = true;
               }
            }

            char[] table = data.fastLatinTable;

            for (int c = 0; c < 384; c++) {
               int p = table[c];
               if (p >= 4096) {
                  p &= 64512;
               } else if (p > miniVarTop) {
                  p &= 65528;
               } else {
                  p = 0;
               }

               primaries[c] = (char)p;
            }

            if (digitsAreReordered || (settings.options & 2) != 0) {
               for (int c = 48; c <= 57; c++) {
                  primaries[c] = 0;
               }
            }

            return miniVarTop << 16 | settings.options;
         }
      }
   }

   public static int compareUTF16(char[] table, char[] primaries, int options, CharSequence left, CharSequence right, int startIndex) {
      int variableTop = options >> 16;
      options &= 65535;
      int leftIndex = startIndex;
      int rightIndex = startIndex;
      int leftPair = 0;
      int rightPair = 0;

      while (true) {
         while (leftPair == 0) {
            if (leftIndex == left.length()) {
               leftPair = 2;
               break;
            }

            int c = left.charAt(leftIndex++);
            if (c <= 383) {
               leftPair = primaries[c];
               if (leftPair != 0) {
                  break;
               }

               if (c <= 57 && c >= 48 && (options & 2) != 0) {
                  return -2;
               }

               leftPair = table[c];
            } else if (8192 <= c && c < 8256) {
               leftPair = table[c - 8192 + 384];
            } else {
               leftPair = lookup(table, c);
            }

            if (leftPair >= 4096) {
               leftPair &= 64512;
               break;
            }

            if (leftPair > variableTop) {
               leftPair &= 65528;
               break;
            }

            long pairAndInc = nextPair(table, c, leftPair, left, leftIndex);
            if (pairAndInc < 0L) {
               leftIndex++;
               pairAndInc = ~pairAndInc;
            }

            leftPair = (int)pairAndInc;
            if (leftPair == 1) {
               return -2;
            }

            leftPair = getPrimaries(variableTop, leftPair);
         }

         while (rightPair == 0) {
            if (rightIndex == right.length()) {
               rightPair = 2;
               break;
            }

            int cx = right.charAt(rightIndex++);
            if (cx <= 383) {
               rightPair = primaries[cx];
               if (rightPair != 0) {
                  break;
               }

               if (cx <= 57 && cx >= 48 && (options & 2) != 0) {
                  return -2;
               }

               rightPair = table[cx];
            } else if (8192 <= cx && cx < 8256) {
               rightPair = table[cx - 8192 + 384];
            } else {
               rightPair = lookup(table, cx);
            }

            if (rightPair >= 4096) {
               rightPair &= 64512;
               break;
            }

            if (rightPair > variableTop) {
               rightPair &= 65528;
               break;
            }

            long pairAndIncx = nextPair(table, cx, rightPair, right, rightIndex);
            if (pairAndIncx < 0L) {
               rightIndex++;
               pairAndIncx = ~pairAndIncx;
            }

            rightPair = (int)pairAndIncx;
            if (rightPair == 1) {
               return -2;
            }

            rightPair = getPrimaries(variableTop, rightPair);
         }

         if (leftPair == rightPair) {
            if (leftPair == 2) {
               break;
            }

            rightPair = 0;
            leftPair = 0;
         } else {
            int leftPrimary = leftPair & 65535;
            int rightPrimary = rightPair & 65535;
            if (leftPrimary != rightPrimary) {
               return leftPrimary < rightPrimary ? -1 : 1;
            }

            if (leftPair == 2) {
               break;
            }

            leftPair >>>= 16;
            rightPair >>>= 16;
         }
      }

      if (CollationSettings.getStrength(options) >= 1) {
         rightIndex = startIndex;
         leftIndex = startIndex;
         rightPair = 0;
         leftPair = 0;

         label437:
         while (true) {
            if (leftPair == 0) {
               if (leftIndex == left.length()) {
                  leftPair = 2;
               } else {
                  int cxx = left.charAt(leftIndex++);
                  if (cxx <= 383) {
                     leftPair = table[cxx];
                  } else if (8192 <= cxx && cxx < 8256) {
                     leftPair = table[cxx - 8192 + 384];
                  } else {
                     leftPair = lookup(table, cxx);
                  }

                  if (leftPair >= 4096) {
                     leftPair = getSecondariesFromOneShortCE(leftPair);
                  } else {
                     if (leftPair <= variableTop) {
                        long pairAndIncxx = nextPair(table, cxx, leftPair, left, leftIndex);
                        if (pairAndIncxx < 0L) {
                           leftIndex++;
                           pairAndIncxx = ~pairAndIncxx;
                        }

                        leftPair = getSecondaries(variableTop, (int)pairAndIncxx);
                        continue;
                     }

                     leftPair = 192;
                  }
               }
            }

            while (true) {
               if (rightPair == 0) {
                  if (rightIndex == right.length()) {
                     rightPair = 2;
                  } else {
                     int cxxx = right.charAt(rightIndex++);
                     if (cxxx <= 383) {
                        rightPair = table[cxxx];
                     } else if (8192 <= cxxx && cxxx < 8256) {
                        rightPair = table[cxxx - 8192 + 384];
                     } else {
                        rightPair = lookup(table, cxxx);
                     }

                     if (rightPair >= 4096) {
                        rightPair = getSecondariesFromOneShortCE(rightPair);
                     } else {
                        if (rightPair <= variableTop) {
                           long pairAndIncxx = nextPair(table, cxxx, rightPair, right, rightIndex);
                           if (pairAndIncxx < 0L) {
                              rightIndex++;
                              pairAndIncxx = ~pairAndIncxx;
                           }

                           rightPair = getSecondaries(variableTop, (int)pairAndIncxx);
                           continue;
                        }

                        rightPair = 192;
                     }
                  }
               }

               if (leftPair == rightPair) {
                  if (leftPair == 2) {
                     break label437;
                  }

                  rightPair = 0;
                  leftPair = 0;
               } else {
                  int leftSecondary = leftPair & 65535;
                  int rightSecondary = rightPair & 65535;
                  if (leftSecondary != rightSecondary) {
                     if ((options & 2048) != 0) {
                        return -2;
                     }

                     return leftSecondary < rightSecondary ? -1 : 1;
                  }

                  if (leftPair == 2) {
                     break label437;
                  }

                  leftPair >>>= 16;
                  rightPair >>>= 16;
               }
               break;
            }
         }
      }

      if ((options & 1024) != 0) {
         boolean strengthIsPrimary = CollationSettings.getStrength(options) == 0;
         rightIndex = startIndex;
         leftIndex = startIndex;
         rightPair = 0;
         leftPair = 0;

         label395:
         while (true) {
            if (leftPair == 0) {
               if (leftIndex != left.length()) {
                  int cxxxx = left.charAt(leftIndex++);
                  leftPair = cxxxx <= 383 ? table[cxxxx] : lookup(table, cxxxx);
                  if (leftPair < 3072) {
                     long pairAndIncxx = nextPair(table, cxxxx, leftPair, left, leftIndex);
                     if (pairAndIncxx < 0L) {
                        leftIndex++;
                        pairAndIncxx = ~pairAndIncxx;
                     }

                     leftPair = (int)pairAndIncxx;
                  }

                  leftPair = getCases(variableTop, strengthIsPrimary, leftPair);
                  continue;
               }

               leftPair = 2;
            }

            while (true) {
               if (rightPair == 0) {
                  if (rightIndex != right.length()) {
                     int cxxxx = right.charAt(rightIndex++);
                     rightPair = cxxxx <= 383 ? table[cxxxx] : lookup(table, cxxxx);
                     if (rightPair < 3072) {
                        long pairAndIncxx = nextPair(table, cxxxx, rightPair, right, rightIndex);
                        if (pairAndIncxx < 0L) {
                           rightIndex++;
                           pairAndIncxx = ~pairAndIncxx;
                        }

                        rightPair = (int)pairAndIncxx;
                     }

                     rightPair = getCases(variableTop, strengthIsPrimary, rightPair);
                     continue;
                  }

                  rightPair = 2;
               }

               if (leftPair == rightPair) {
                  if (leftPair == 2) {
                     break label395;
                  }

                  rightPair = 0;
                  leftPair = 0;
               } else {
                  int leftCase = leftPair & 65535;
                  int rightCase = rightPair & 65535;
                  if (leftCase != rightCase) {
                     if ((options & 256) == 0) {
                        return leftCase < rightCase ? -1 : 1;
                     }

                     return leftCase < rightCase ? 1 : -1;
                  }

                  if (leftPair == 2) {
                     break label395;
                  }

                  leftPair >>>= 16;
                  rightPair >>>= 16;
               }
               break;
            }
         }
      }

      if (CollationSettings.getStrength(options) <= 1) {
         return 0;
      } else {
         boolean withCaseBits = CollationSettings.isTertiaryWithCaseBits(options);
         rightIndex = startIndex;
         leftIndex = startIndex;
         rightPair = 0;
         leftPair = 0;

         while (true) {
            while (leftPair == 0) {
               if (leftIndex == left.length()) {
                  leftPair = 2;
                  break;
               }

               int cxxxx = left.charAt(leftIndex++);
               leftPair = cxxxx <= 383 ? table[cxxxx] : lookup(table, cxxxx);
               if (leftPair < 3072) {
                  long pairAndIncxx = nextPair(table, cxxxx, leftPair, left, leftIndex);
                  if (pairAndIncxx < 0L) {
                     leftIndex++;
                     pairAndIncxx = ~pairAndIncxx;
                  }

                  leftPair = (int)pairAndIncxx;
               }

               leftPair = getTertiaries(variableTop, withCaseBits, leftPair);
            }

            while (rightPair == 0) {
               if (rightIndex == right.length()) {
                  rightPair = 2;
                  break;
               }

               int cxxxx = right.charAt(rightIndex++);
               rightPair = cxxxx <= 383 ? table[cxxxx] : lookup(table, cxxxx);
               if (rightPair < 3072) {
                  long pairAndIncxx = nextPair(table, cxxxx, rightPair, right, rightIndex);
                  if (pairAndIncxx < 0L) {
                     rightIndex++;
                     pairAndIncxx = ~pairAndIncxx;
                  }

                  rightPair = (int)pairAndIncxx;
               }

               rightPair = getTertiaries(variableTop, withCaseBits, rightPair);
            }

            if (leftPair == rightPair) {
               if (leftPair == 2) {
                  break;
               }

               rightPair = 0;
               leftPair = 0;
            } else {
               int leftTertiary = leftPair & 65535;
               int rightTertiary = rightPair & 65535;
               if (leftTertiary != rightTertiary) {
                  if (CollationSettings.sortsTertiaryUpperCaseFirst(options)) {
                     if (leftTertiary > 3) {
                        leftTertiary ^= 24;
                     }

                     if (rightTertiary > 3) {
                        rightTertiary ^= 24;
                     }
                  }

                  return leftTertiary < rightTertiary ? -1 : 1;
               }

               if (leftPair == 2) {
                  break;
               }

               leftPair >>>= 16;
               rightPair >>>= 16;
            }
         }

         if (CollationSettings.getStrength(options) <= 2) {
            return 0;
         } else {
            rightIndex = startIndex;
            leftIndex = startIndex;
            rightPair = 0;
            leftPair = 0;

            while (true) {
               while (leftPair == 0) {
                  if (leftIndex == left.length()) {
                     leftPair = 2;
                     break;
                  }

                  int cxxxx = left.charAt(leftIndex++);
                  leftPair = cxxxx <= 383 ? table[cxxxx] : lookup(table, cxxxx);
                  if (leftPair < 3072) {
                     long pairAndIncxx = nextPair(table, cxxxx, leftPair, left, leftIndex);
                     if (pairAndIncxx < 0L) {
                        leftIndex++;
                        pairAndIncxx = ~pairAndIncxx;
                     }

                     leftPair = (int)pairAndIncxx;
                  }

                  leftPair = getQuaternaries(variableTop, leftPair);
               }

               while (rightPair == 0) {
                  if (rightIndex == right.length()) {
                     rightPair = 2;
                     break;
                  }

                  int cxxxx = right.charAt(rightIndex++);
                  rightPair = cxxxx <= 383 ? table[cxxxx] : lookup(table, cxxxx);
                  if (rightPair < 3072) {
                     long pairAndIncxx = nextPair(table, cxxxx, rightPair, right, rightIndex);
                     if (pairAndIncxx < 0L) {
                        rightIndex++;
                        pairAndIncxx = ~pairAndIncxx;
                     }

                     rightPair = (int)pairAndIncxx;
                  }

                  rightPair = getQuaternaries(variableTop, rightPair);
               }

               if (leftPair == rightPair) {
                  if (leftPair == 2) {
                     break;
                  }

                  rightPair = 0;
                  leftPair = 0;
               } else {
                  int leftQuaternary = leftPair & 65535;
                  int rightQuaternary = rightPair & 65535;
                  if (leftQuaternary != rightQuaternary) {
                     return leftQuaternary < rightQuaternary ? -1 : 1;
                  }

                  if (leftPair == 2) {
                     break;
                  }

                  leftPair >>>= 16;
                  rightPair >>>= 16;
               }
            }

            return 0;
         }
      }
   }

   private static int lookup(char[] table, int c) {
      assert c > 383;

      if (8192 <= c && c < 8256) {
         return table[c - 8192 + 384];
      } else if (c == 65534) {
         return 3;
      } else {
         return c == 65535 ? 64680 : 1;
      }
   }

   private static long nextPair(char[] table, int c, int ce, CharSequence s16, int sIndex) {
      if (ce >= 3072 || ce < 1024) {
         return ce;
      } else if (ce >= 2048) {
         int index = 448 + (ce & 1023);
         return (long)table[index + 1] << 16 | table[index];
      } else {
         int index = 448 + (ce & 1023);
         boolean inc = false;
         if (sIndex != s16.length()) {
            int result = sIndex + 1;
            int c2 = s16.charAt(sIndex);
            if (c2 > 383) {
               if (8192 <= c2 && c2 < 8256) {
                  c2 = c2 - 8192 + 384;
               } else {
                  if (c2 != 65534 && c2 != 65535) {
                     return 1L;
                  }

                  c2 = -1;
               }
            }

            int i = index;
            int head = table[index];

            int x;
            do {
               i += head >> 9;
               head = table[i];
               x = head & 511;
            } while (x < c2);

            if (x == c2) {
               index = i;
               inc = true;
            }
         }

         int length = table[index] >> '\t';
         if (length == 1) {
            return 1L;
         } else {
            int var12 = table[index + 1];
            long resultx;
            if (length == 2) {
               resultx = var12;
            } else {
               resultx = (long)table[index + 2] << 16 | var12;
            }

            return inc ? ~resultx : resultx;
         }
      }
   }

   private static int getPrimaries(int variableTop, int pair) {
      int ce = pair & 65535;
      if (ce >= 4096) {
         return pair & -67044352;
      } else if (ce > variableTop) {
         return pair & -458760;
      } else {
         return ce >= 3072 ? 0 : pair;
      }
   }

   private static int getSecondariesFromOneShortCE(int ce) {
      ce &= 992;
      return ce < 384 ? ce + 32 : ce + 32 << 16 | 192;
   }

   private static int getSecondaries(int variableTop, int pair) {
      if (pair <= 65535) {
         if (pair >= 4096) {
            pair = getSecondariesFromOneShortCE(pair);
         } else if (pair > variableTop) {
            pair = 192;
         } else if (pair >= 3072) {
            pair = 0;
         }
      } else {
         int ce = pair & 65535;
         if (ce >= 4096) {
            pair = (pair & 65012704) + 2097184;
         } else if (ce > variableTop) {
            pair = 12583104;
         } else {
            assert ce >= 3072;

            pair = 0;
         }
      }

      return pair;
   }

   private static int getCases(int variableTop, boolean strengthIsPrimary, int pair) {
      if (pair <= 65535) {
         if (pair >= 4096) {
            int ce = pair;
            pair &= 24;
            if (!strengthIsPrimary && (ce & 992) >= 384) {
               pair |= 524288;
            }
         } else if (pair > variableTop) {
            pair = 8;
         } else if (pair >= 3072) {
            pair = 0;
         }
      } else {
         int ce = pair & 65535;
         if (ce >= 4096) {
            if (strengthIsPrimary && (pair & -67108864) == 0) {
               pair &= 24;
            } else {
               pair &= 1572888;
            }
         } else if (ce > variableTop) {
            pair = 524296;
         } else {
            assert ce >= 3072;

            pair = 0;
         }
      }

      return pair;
   }

   private static int getTertiaries(int variableTop, boolean withCaseBits, int pair) {
      if (pair <= 65535) {
         if (pair >= 4096) {
            int ce = pair;
            if (withCaseBits) {
               pair = (pair & 31) + 32;
               if ((ce & 992) >= 384) {
                  pair |= 2621440;
               }
            } else {
               pair = (pair & 7) + 32;
               if ((ce & 992) >= 384) {
                  pair |= 2097152;
               }
            }
         } else if (pair > variableTop) {
            pair = (pair & 7) + 32;
            if (withCaseBits) {
               pair |= 8;
            }
         } else if (pair >= 3072) {
            pair = 0;
         }
      } else {
         int ce = pair & 65535;
         if (ce >= 4096) {
            if (withCaseBits) {
               pair &= 2031647;
            } else {
               pair &= 458759;
            }

            pair += 2097184;
         } else if (ce > variableTop) {
            pair = (pair & 458759) + 2097184;
            if (withCaseBits) {
               pair |= 524296;
            }
         } else {
            assert ce >= 3072;

            pair = 0;
         }
      }

      return pair;
   }

   private static int getQuaternaries(int variableTop, int pair) {
      if (pair <= 65535) {
         if (pair >= 4096) {
            if ((pair & 992) >= 384) {
               pair = -67044352;
            } else {
               pair = 64512;
            }
         } else if (pair > variableTop) {
            pair = 64512;
         } else if (pair >= 3072) {
            pair &= 65528;
         }
      } else {
         int ce = pair & 65535;
         if (ce > variableTop) {
            pair = -67044352;
         } else {
            assert ce >= 3072;

            pair &= -458760;
         }
      }

      return pair;
   }

   private CollationFastLatin() {
   }
}
