package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.unicode.UnicodeEncoding;

abstract class BaseUTF8Encoding extends UnicodeEncoding {
   static final boolean USE_INVALID_CODE_SCHEME = true;
   private static final int INVALID_CODE_FE = -2;
   private static final int INVALID_CODE_FF = -1;
   private static final int VALID_CODE_LIMIT = 1114111;

   protected BaseUTF8Encoding(int[] EncLen, int[][] Trans) {
      super("UTF-8", 1, 4, EncLen, Trans);
      this.isUTF8 = true;
   }

   @Override
   public String getCharsetName() {
      return "UTF-8";
   }

   @Override
   public boolean isNewLine(byte[] bytes, int p, int end) {
      return p < end && bytes[p] == 10;
   }

   @Override
   public int codeToMbcLength(int code) {
      if ((code & -128) == 0) {
         return 1;
      } else if ((code & -2048) == 0) {
         return 2;
      } else if ((code & -65536) == 0) {
         return 3;
      } else if ((code & 4294967295L) <= 1114111L) {
         return 4;
      } else if (code == -2) {
         return 1;
      } else {
         return code == -1 ? 1 : -401;
      }
   }

   @Override
   public int mbcToCode(byte[] bytes, int p, int end) {
      int len = this.length(bytes, p, end);
      int c = bytes[p++] & 255;
      if (len <= 1) {
         if (c > 253) {
            return c == 254 ? -2 : -1;
         } else {
            return c;
         }
      } else {
         len--;
         int n = c & (1 << 6 - len) - 1;

         while (len-- != 0) {
            c = bytes[p++] & 255;
            n = n << 6 | c & 63;
         }

         return n;
      }
   }

   static byte trailS(int code, int shift) {
      return (byte)(code >>> shift & 63 | 128);
   }

   static byte trail0(int code) {
      return (byte)(code & 63 | 128);
   }

   @Override
   public int codeToMbc(int code, byte[] bytes, int p) {
      if ((code & -128) == 0) {
         bytes[p] = (byte)code;
         return 1;
      } else {
         int var6;
         if ((code & -2048) == 0) {
            var6 = p + 1;
            bytes[p] = (byte)(code >>> 6 & 31 | 192);
         } else if ((code & -65536) == 0) {
            var6 = p + 1;
            bytes[p] = (byte)(code >>> 12 & 15 | 224);
            bytes[var6++] = trailS(code, 6);
         } else {
            if ((code & 4294967295L) > 1114111L) {
               if (code == -2) {
                  bytes[p] = -2;
                  return 1;
               }

               if (code == -1) {
                  bytes[p] = -1;
                  return 1;
               }

               return -401;
            }

            var6 = p + 1;
            bytes[p] = (byte)(code >>> 18 & 7 | 240);
            bytes[var6++] = trailS(code, 12);
            bytes[var6++] = trailS(code, 6);
         }

         bytes[var6++] = trail0(code);
         return var6 - p;
      }
   }

   @Override
   public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end, byte[] fold) {
      int p = pp.value;
      int foldP = 0;
      if (isMbcAscii(bytes[p])) {
         fold[foldP] = AsciiTables.ToLowerCaseTable[bytes[p] & 255];
         pp.value++;
         return 1;
      } else {
         return super.mbcCaseFold(flag, bytes, pp, end, fold);
      }
   }

   @Override
   public int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
      sbOut.value = 128;
      return super.ctypeCodeRange(ctype);
   }

   private static boolean utf8IsLead(int c) {
      return (c & 192 & 0xFF) != 128;
   }

   @Override
   public int leftAdjustCharHead(byte[] bytes, int p, int s, int end) {
      if (s <= p) {
         return s;
      } else {
         int p_ = s;

         while (!utf8IsLead(bytes[p_] & 255) && p_ > p) {
            p_--;
         }

         return p_;
      }
   }

   @Override
   public boolean isReverseMatchAllowed(byte[] bytes, int p, int end) {
      return true;
   }
}
