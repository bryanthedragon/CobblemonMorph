package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

import com.cobblemon.mod.relocations.ibm.icu.impl.Normalizer2Impl;
import com.cobblemon.mod.relocations.ibm.icu.impl.Trie2_32;
import com.cobblemon.mod.relocations.ibm.icu.text.UnicodeSet;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;

public final class CollationData {
   static final int REORDER_RESERVED_BEFORE_LATIN = 4110;
   static final int REORDER_RESERVED_AFTER_LATIN = 4111;
   static final int MAX_NUM_SPECIAL_REORDER_CODES = 8;
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   static final int JAMO_CE32S_LENGTH = 67;
   Trie2_32 trie;
   int[] ce32s;
   long[] ces;
   String contexts;
   public CollationData base;
   int[] jamoCE32s = new int[67];
   public Normalizer2Impl nfcImpl;
   long numericPrimary = 301989888L;
   public boolean[] compressibleBytes;
   UnicodeSet unsafeBackwardSet;
   public char[] fastLatinTable;
   char[] fastLatinTableHeader;
   int numScripts;
   char[] scriptsIndex;
   char[] scriptStarts;
   public long[] rootElements;

   CollationData(Normalizer2Impl nfc) {
      this.nfcImpl = nfc;
   }

   public int getCE32(int c) {
      return this.trie.get(c);
   }

   int getCE32FromSupplementary(int c) {
      return this.trie.get(c);
   }

   boolean isDigit(int c) {
      return c < 1632 ? c <= 57 && 48 <= c : Collation.hasCE32Tag(this.getCE32(c), 10);
   }

   public boolean isUnsafeBackward(int c, boolean numeric) {
      return this.unsafeBackwardSet.contains(c) || numeric && this.isDigit(c);
   }

   public boolean isCompressibleLeadByte(int b) {
      return this.compressibleBytes[b];
   }

   public boolean isCompressiblePrimary(long p) {
      return this.isCompressibleLeadByte((int)p >>> 24);
   }

   int getCE32FromContexts(int index) {
      return this.contexts.charAt(index) << 16 | this.contexts.charAt(index + 1);
   }

   int getIndirectCE32(int ce32) {
      assert Collation.isSpecialCE32(ce32);

      int tag = Collation.tagFromCE32(ce32);
      if (tag == 10) {
         ce32 = this.ce32s[Collation.indexFromCE32(ce32)];
      } else if (tag == 13) {
         ce32 = -1;
      } else if (tag == 11) {
         ce32 = this.ce32s[0];
      }

      return ce32;
   }

   int getFinalCE32(int ce32) {
      if (Collation.isSpecialCE32(ce32)) {
         ce32 = this.getIndirectCE32(ce32);
      }

      return ce32;
   }

   long getCEFromOffsetCE32(int c, int ce32) {
      long dataCE = this.ces[Collation.indexFromCE32(ce32)];
      return Collation.makeCE(Collation.getThreeBytePrimaryForOffsetData(c, dataCE));
   }

   long getSingleCE(int c) {
      int ce32 = this.getCE32(c);
      CollationData d;
      if (ce32 == 192) {
         d = this.base;
         ce32 = this.base.getCE32(c);
      } else {
         d = this;
      }

      while (Collation.isSpecialCE32(ce32)) {
         switch (Collation.tagFromCE32(ce32)) {
            case 0:
            case 3:
               throw new AssertionError(String.format("unexpected CE32 tag for U+%04X (CE32 0x%08x)", c, ce32));
            case 1:
               return Collation.ceFromLongPrimaryCE32(ce32);
            case 2:
               return Collation.ceFromLongSecondaryCE32(ce32);
            case 4:
            case 7:
            case 8:
            case 9:
            case 12:
            case 13:
               throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", c, ce32));
            case 5:
               if (Collation.lengthFromCE32(ce32) != 1) {
                  throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", c, ce32));
               }

               ce32 = d.ce32s[Collation.indexFromCE32(ce32)];
               break;
            case 6:
               if (Collation.lengthFromCE32(ce32) == 1) {
                  return d.ces[Collation.indexFromCE32(ce32)];
               }

               throw new UnsupportedOperationException(String.format("there is not exactly one collation element for U+%04X (CE32 0x%08x)", c, ce32));
            case 10:
               ce32 = d.ce32s[Collation.indexFromCE32(ce32)];
               break;
            case 11:
               assert c == 0;

               ce32 = d.ce32s[0];
               break;
            case 14:
               return d.getCEFromOffsetCE32(c, ce32);
            case 15:
               return Collation.unassignedCEFromCodePoint(c);
         }
      }

      return Collation.ceFromSimpleCE32(ce32);
   }

   int getFCD16(int c) {
      return this.nfcImpl.getFCD16(c);
   }

   long getFirstPrimaryForGroup(int script) {
      int index = this.getScriptIndex(script);
      return index == 0 ? 0L : (long)this.scriptStarts[index] << 16;
   }

   public long getLastPrimaryForGroup(int script) {
      int index = this.getScriptIndex(script);
      if (index == 0) {
         return 0L;
      } else {
         long limit = this.scriptStarts[index + 1];
         return (limit << 16) - 1L;
      }
   }

   public int getGroupForPrimary(long p) {
      p >>= 16;
      if (p >= this.scriptStarts[1] && this.scriptStarts[this.scriptStarts.length - 1] > p) {
         int index = 1;

         while (p >= this.scriptStarts[index + 1]) {
            index++;
         }

         for (int i = 0; i < this.numScripts; i++) {
            if (this.scriptsIndex[i] == index) {
               return i;
            }
         }

         for (int ix = 0; ix < 8; ix++) {
            if (this.scriptsIndex[this.numScripts + ix] == index) {
               return 4096 + ix;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private int getScriptIndex(int script) {
      if (script < 0) {
         return 0;
      } else if (script < this.numScripts) {
         return this.scriptsIndex[script];
      } else if (script < 4096) {
         return 0;
      } else {
         script -= 4096;
         return script < 8 ? this.scriptsIndex[this.numScripts + script] : 0;
      }
   }

   public int[] getEquivalentScripts(int script) {
      int index = this.getScriptIndex(script);
      if (index == 0) {
         return EMPTY_INT_ARRAY;
      } else if (script >= 4096) {
         return new int[]{script};
      } else {
         int length = 0;

         for (int i = 0; i < this.numScripts; i++) {
            if (this.scriptsIndex[i] == index) {
               length++;
            }
         }

         int[] dest = new int[length];
         if (length == 1) {
            dest[0] = script;
            return dest;
         } else {
            length = 0;

            for (int ix = 0; ix < this.numScripts; ix++) {
               if (this.scriptsIndex[ix] == index) {
                  dest[length++] = ix;
               }
            }

            return dest;
         }
      }
   }

   void makeReorderRanges(int[] reorder, UVector32 ranges) {
      this.makeReorderRanges(reorder, false, ranges);
   }

   private void makeReorderRanges(int[] reorder, boolean latinMustMove, UVector32 ranges) {
      ranges.removeAllElements();
      int length = reorder.length;
      if (length != 0 && (length != 1 || reorder[0] != 103)) {
         short[] table = new short[this.scriptStarts.length - 1];
         int index = this.scriptsIndex[this.numScripts + 4110 - 4096];
         if (index != 0) {
            table[index] = 255;
         }

         int lowStart = this.scriptsIndex[this.numScripts + 4111 - 4096];
         if (lowStart != 0) {
            table[lowStart] = 255;
         }

         assert this.scriptStarts.length >= 2;

         assert this.scriptStarts[0] == 0;

         lowStart = this.scriptStarts[1];

         assert lowStart == 768;

         int highLimit = this.scriptStarts[this.scriptStarts.length - 1];

         assert highLimit == 65280;

         int specials = 0;

         for (int i = 0; i < length; i++) {
            int reorderCode = reorder[i] - 4096;
            if (0 <= reorderCode && reorderCode < 8) {
               specials |= 1 << reorderCode;
            }
         }

         for (int ix = 0; ix < 8; ix++) {
            int indexx = this.scriptsIndex[this.numScripts + ix];
            if (indexx != 0 && (specials & 1 << ix) == 0) {
               lowStart = this.addLowScriptRange(table, indexx, lowStart);
            }
         }

         int skippedReserved = 0;
         if (specials == 0 && reorder[0] == 25 && !latinMustMove) {
            int indexx = this.scriptsIndex[25];

            assert indexx != 0;

            int start = this.scriptStarts[indexx];

            assert lowStart <= start;

            skippedReserved = start - lowStart;
            lowStart = start;
         }

         boolean hasReorderToEnd = false;
         int ixx = 0;

         while (ixx < length) {
            int script = reorder[ixx++];
            if (script == 103) {
               hasReorderToEnd = true;

               while (ixx < length) {
                  script = reorder[--length];
                  if (script == 103) {
                     throw new IllegalArgumentException("setReorderCodes(): duplicate UScript.UNKNOWN");
                  }

                  if (script == -1) {
                     throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
                  }

                  int indexx = this.getScriptIndex(script);
                  if (indexx != 0) {
                     if (table[indexx] != 0) {
                        throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + scriptCodeString(script));
                     }

                     highLimit = this.addHighScriptRange(table, indexx, highLimit);
                  }
               }
               break;
            }

            if (script == -1) {
               throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
            }

            int indexx = this.getScriptIndex(script);
            if (indexx != 0) {
               if (table[indexx] != 0) {
                  throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + scriptCodeString(script));
               }

               lowStart = this.addLowScriptRange(table, indexx, lowStart);
            }
         }

         for (int ixxx = 1; ixxx < this.scriptStarts.length - 1; ixxx++) {
            int leadByte = table[ixxx];
            if (leadByte == 0) {
               int start = this.scriptStarts[ixxx];
               if (!hasReorderToEnd && start > lowStart) {
                  lowStart = start;
               }

               lowStart = this.addLowScriptRange(table, ixxx, lowStart);
            }
         }

         if (lowStart > highLimit) {
            if (lowStart - (skippedReserved & 0xFF00) <= highLimit) {
               this.makeReorderRanges(reorder, true, ranges);
            } else {
               throw new ICUException("setReorderCodes(): reordering too many partial-primary-lead-byte scripts");
            }
         } else {
            ixx = 0;
            int ixxxx = 1;

            while (true) {
               int nextOffset;
               for (nextOffset = ixx; ixxxx < this.scriptStarts.length - 1; ixxxx++) {
                  int newLeadByte = table[ixxxx];
                  if (newLeadByte != 255) {
                     nextOffset = newLeadByte - (this.scriptStarts[ixxxx] >> '\b');
                     if (nextOffset != ixx) {
                        break;
                     }
                  }
               }

               if (ixx != 0 || ixxxx < this.scriptStarts.length - 1) {
                  ranges.addElement(this.scriptStarts[ixxxx] << 16 | ixx & 65535);
               }

               if (ixxxx == this.scriptStarts.length - 1) {
                  return;
               }

               ixx = nextOffset;
               ixxxx++;
            }
         }
      }
   }

   private int addLowScriptRange(short[] table, int index, int lowStart) {
      int start = this.scriptStarts[index];
      if ((start & 0xFF) < (lowStart & 0xFF)) {
         lowStart += 256;
      }

      table[index] = (short)(lowStart >> 8);
      int limit = this.scriptStarts[index + 1];
      return (lowStart & 0xFF00) + ((limit & 0xFF00) - (start & 0xFF00)) | limit & 0xFF;
   }

   private int addHighScriptRange(short[] table, int index, int highLimit) {
      int limit = this.scriptStarts[index + 1];
      if ((limit & 0xFF) > (highLimit & 0xFF)) {
         highLimit -= 256;
      }

      int start = this.scriptStarts[index];
      highLimit = (highLimit & 0xFF00) - ((limit & 0xFF00) - (start & 0xFF00)) | start & 0xFF;
      table[index] = (short)(highLimit >> 8);
      return highLimit;
   }

   private static String scriptCodeString(int script) {
      return script < 4096 ? Integer.toString(script) : "0x" + Integer.toHexString(script);
   }
}
