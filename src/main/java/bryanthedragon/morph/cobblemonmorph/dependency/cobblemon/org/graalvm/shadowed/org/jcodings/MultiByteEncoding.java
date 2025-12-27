package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;

public abstract class MultiByteEncoding extends AbstractEncoding {
   protected final int[] EncLen;
   protected static final int A = -1;
   protected static final int F = -2;
   protected final int[][] Trans;
   protected final int[] TransZero;

   protected MultiByteEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans, short[] CTypeTable) {
      super(name, minLength, maxLength, CTypeTable);
      this.EncLen = EncLen;
      this.Trans = Trans;
      this.TransZero = Trans != null ? Trans[0] : null;
   }

   @Override
   public int length(byte c) {
      return this.EncLen[c & 0xFF];
   }

   protected final int missing(int n) {
      return -1 - n;
   }

   protected final int missing(int b, int delta) {
      return this.missing(this.EncLen[b] - delta);
   }

   protected final int safeLengthForUptoFour(byte[] bytes, int p, int end) {
      int b = bytes[p] & 255;
      int s = this.TransZero[b];
      if (s < 0) {
         return s == -1 ? 1 : -1;
      } else {
         return this.lengthForTwoUptoFour(bytes, p, end, b, s);
      }
   }

   protected final int lengthForTwoUptoFour(byte[] bytes, int p, int end, int b, int s) {
      if (++p == end) {
         return this.missing(b, 1);
      } else {
         s = this.Trans[s][bytes[p] & 255];
         if (s < 0) {
            return s == -1 ? 2 : -1;
         } else {
            return this.lengthForThreeUptoFour(bytes, p, end, b, s);
         }
      }
   }

   private int lengthForThreeUptoFour(byte[] bytes, int p, int end, int b, int s) {
      if (++p == end) {
         return this.missing(b, 2);
      } else {
         s = this.Trans[s][bytes[p] & 255];
         if (s < 0) {
            return s == -1 ? 3 : -1;
         } else if (++p == end) {
            return this.missing(b, 3);
         } else {
            s = this.Trans[s][bytes[p] & 255];
            return s == -1 ? 4 : -1;
         }
      }
   }

   protected final int safeLengthForUptoThree(byte[] bytes, int p, int end) {
      int b = bytes[p] & 255;
      int s = this.TransZero[b];
      if (s < 0) {
         return s == -1 ? 1 : -1;
      } else {
         return this.lengthForTwoUptoThree(bytes, p, end, b, s);
      }
   }

   private int lengthForTwoUptoThree(byte[] bytes, int p, int end, int b, int s) {
      if (++p == end) {
         return this.missing(b, 1);
      } else {
         s = this.Trans[s][bytes[p] & 255];
         if (s < 0) {
            return s == -1 ? 2 : -1;
         } else {
            return this.lengthForThree(bytes, p, end, b, s);
         }
      }
   }

   private int lengthForThree(byte[] bytes, int p, int end, int b, int s) {
      if (++p == end) {
         return this.missing(b, 2);
      } else {
         s = this.Trans[s][bytes[p] & 255];
         return s == -1 ? 3 : -1;
      }
   }

   protected final int safeLengthForUptoTwo(byte[] bytes, int p, int end) {
      int b = bytes[p] & 255;
      int s = this.TransZero[b];
      if (s < 0) {
         return s == -1 ? 1 : -1;
      } else {
         return this.lengthForTwo(bytes, p, end, b, s);
      }
   }

   private int lengthForTwo(byte[] bytes, int p, int end, int b, int s) {
      if (++p == end) {
         return this.missing(b, 1);
      } else {
         s = this.Trans[s][bytes[p] & 255];
         return s == -1 ? 2 : -1;
      }
   }

   protected final int mbnMbcToCode(byte[] bytes, int p, int end) {
      int len = this.length(bytes, p, end);
      int n = bytes[p++] & 255;
      if (len == 1) {
         return n;
      } else {
         for (int i = 1; i < len && p < end; i++) {
            int c = bytes[p++] & 255;
            n <<= 8;
            n += c;
         }

         return n;
      }
   }

   @Override
   public int caseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end, byte[] to, int toP, int toEnd) {
      return this.asciiOnlyCaseMap(flagP, bytes, pp, end, to, toP, toEnd);
   }

   protected final int mbnMbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end, byte[] lower) {
      int p = pp.value;
      int lowerP = 0;
      if (isAscii(bytes[p] & 255)) {
         lower[lowerP] = AsciiTables.ToLowerCaseTable[bytes[p] & 255];
         pp.value++;
         return 1;
      } else {
         int len = this.length(bytes, p, end);

         for (int i = 0; i < len; i++) {
            lower[lowerP++] = bytes[p++];
         }

         pp.value += len;
         return len;
      }
   }

   protected final int mb2CodeToMbcLength(int code) {
      return (code & 0xFF00) != 0 ? 2 : 1;
   }

   protected final int mb4CodeToMbcLength(int code) {
      if ((code & 0xFF000000) != 0) {
         return 4;
      } else if ((code & 0xFF0000) != 0) {
         return 3;
      } else {
         return (code & 0xFF00) != 0 ? 2 : 1;
      }
   }

   protected final int mb2CodeToMbc(int code, byte[] bytes, int p) {
      int p_ = p;
      if ((code & 0xFF00) != 0) {
         p_ = p + 1;
         bytes[p] = (byte)(code >>> 8 & 0xFF);
      }

      bytes[p_++] = (byte)(code & 0xFF);
      return this.length(bytes, p, p_) != p_ - p ? -400 : p_ - p;
   }

   protected final int mb4CodeToMbc(int code, byte[] bytes, int p) {
      int p_ = p;
      if ((code & 0xFF000000) != 0) {
         p_ = p + 1;
         bytes[p] = (byte)(code >>> 24 & 0xFF);
      }

      if ((code & 0xFF0000) != 0 || p_ != p) {
         bytes[p_++] = (byte)(code >>> 16 & 0xFF);
      }

      if ((code & 0xFF00) != 0 || p_ != p) {
         bytes[p_++] = (byte)(code >>> 8 & 0xFF);
      }

      bytes[p_++] = (byte)(code & 0xFF);
      return this.length(bytes, p, p_) != p_ - p ? -400 : p_ - p;
   }

   protected final boolean mb2IsCodeCType(int code, int ctype) {
      if ((code & 4294967295L) < 128L) {
         return this.isCodeCTypeInternal(code, ctype);
      } else {
         return isWordGraphPrint(ctype) ? this.codeToMbcLength(code) > 1 : false;
      }
   }

   protected final boolean mb4IsCodeCType(int code, int ctype) {
      return this.mb2IsCodeCType(code, ctype);
   }

   @Override
   public int strLength(byte[] bytes, int p, int end) {
      int n = 0;

      for (int q = p; q < end; n++) {
         q += this.length(bytes, q, end);
      }

      return n;
   }

   @Override
   public int strCodeAt(byte[] bytes, int p, int end, int index) {
      int n = 0;

      for (int q = p; q < end; n++) {
         if (n == index) {
            return this.mbcToCode(bytes, q, end);
         }

         q += this.length(bytes, q, end);
      }

      return -1;
   }

   public static boolean isInRange(int code, int from, int to) {
      return code - from >= 0 && to - code >= 0;
   }
}
