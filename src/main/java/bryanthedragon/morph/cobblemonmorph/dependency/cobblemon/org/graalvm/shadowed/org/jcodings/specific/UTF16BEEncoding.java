package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.unicode.UnicodeEncoding;

public final class UTF16BEEncoding extends UnicodeEncoding {
   static final int[] UTF16EncLen = new int[]{
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      4,
      4,
      4,
      4,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2
   };
   public static final UTF16BEEncoding INSTANCE = new UTF16BEEncoding();

   protected UTF16BEEncoding() {
      super("UTF-16BE", 2, 4, UTF16EncLen);
   }

   @Override
   public int length(byte[] bytes, int p, int end) {
      int b = bytes[p] & 255;
      if (!isSurrogate(b)) {
         return end - p >= 2 ? 2 : this.missing(1);
      } else {
         if (isSurrogateFirst(b)) {
            switch (end - p) {
               case 1:
                  return this.missing(3);
               case 2:
                  return this.missing(2);
               case 3:
                  if (isSurrogateSecond(bytes[p + 2] & 255)) {
                     return this.missing(1);
                  }
               default:
                  if (isSurrogateSecond(bytes[p + 2] & 255)) {
                     return 4;
                  }
            }
         }

         return -1;
      }
   }

   @Override
   public boolean isNewLine(byte[] bytes, int p, int end) {
      return p + 1 < end && bytes[p + 1] == 10 && bytes[p] == 0;
   }

   @Override
   public int mbcToCode(byte[] bytes, int p, int end) {
      int code;
      if (isSurrogateFirst(bytes[p] & 255)) {
         code = ((((bytes[p + 0] & 255) << 8) + (bytes[p + 1] & 255) & 1023) << 10) + (((bytes[p + 2] & 255) << 8) + (bytes[p + 3] & 255) & 1023) + 65536;
      } else {
         code = (bytes[p + 0] & 255) * 256 + (bytes[p + 1] & 255);
      }

      return code;
   }

   @Override
   public int codeToMbcLength(int code) {
      return code > 65535 ? 4 : 2;
   }

   @Override
   public int codeToMbc(int code, byte[] bytes, int p) {
      if (code > 65535) {
         int high = (code >>> 10) + 55232;
         int low = (code & 1023) + 56320;
         int var8 = p + 1;
         bytes[p] = (byte)(high >>> 8 & 0xFF);
         bytes[var8++] = (byte)(high & 0xFF);
         bytes[var8++] = (byte)(low >>> 8 & 0xFF);
         bytes[var8] = (byte)(low & 0xFF);
         return 4;
      } else {
         int p_ = p + 1;
         bytes[p] = (byte)((code & 0xFF00) >>> 8);
         bytes[p_++] = (byte)(code & 0xFF);
         return 2;
      }
   }

   @Override
   public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end, byte[] fold) {
      int p = pp.value;
      int foldP = 0;
      if (isAscii(bytes[p + 1] & 255) && bytes[p] == 0) {
         p++;
         fold[foldP++] = 0;
         fold[foldP] = AsciiTables.ToLowerCaseTable[bytes[p] & 255];
         pp.value += 2;
         return 2;
      } else {
         return super.mbcCaseFold(flag, bytes, pp, end, fold);
      }
   }

   @Override
   public int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
      sbOut.value = 0;
      return super.ctypeCodeRange(ctype);
   }

   @Override
   public int leftAdjustCharHead(byte[] bytes, int p, int s, int end) {
      if (s <= p) {
         return s;
      } else {
         if ((s - p) % 2 == 1) {
            s--;
         }

         if (isSurrogateSecond(bytes[s] & 255) && s > p + 1) {
            s -= 2;
         }

         return s;
      }
   }

   @Override
   public boolean isReverseMatchAllowed(byte[] bytes, int p, int end) {
      return false;
   }

   private static boolean isSurrogateFirst(int c) {
      return (c & 252) == 216;
   }

   private static boolean isSurrogateSecond(int c) {
      return (c & 252) == 220;
   }

   private static boolean isSurrogate(int c) {
      return (c & 248) == 216;
   }
}
