package org.graalvm.shadowed.org.jcodings.transcode;

public class TranscodeTableSupport implements TranscodingInstruction {
   public static final int WORDINDEX_SHIFT_BITS = 2;

   public static int WORDINDEX2INFO(int widx) {
      return widx << 2;
   }

   public static int INFO2WORDINDEX(int info) {
      return info >>> 2;
   }

   public static int makeSTR1(int bi) {
      return bi << 6 | 17;
   }

   public static byte makeSTR1LEN(int len) {
      return (byte)(len - 4);
   }

   public static int o1(int b1) {
      return b1 << 8 | 2;
   }

   public static int o2(int b1, int b2) {
      return b1 << 8 | b2 << 16 | 3;
   }

   public static int o3(int b1, int b2, int b3) {
      return (b1 << 8 | b2 << 16 | b3 << 24 | 5) & -1;
   }

   public static int o4(int b0, int b1, int b2, int b3) {
      return (b1 << 8 | b2 << 16 | b3 << 24 | (b0 & 7) << 5 | 6) & -1;
   }

   public static int g4(int b0, int b1, int b2, int b3) {
      return (b0 << 8 | b2 << 16 | (b1 & 15) << 24 | (b3 & 15) << 28 | 18) & -1;
   }

   public static int funsio(int diff) {
      return diff << 8 & 19;
   }

   public static int getBT1(int a) {
      return a >>> 8;
   }

   public static int getBT2(int a) {
      return a >>> 16;
   }

   public static int getBT3(int a) {
      return a >>> 24;
   }

   public static int getBT0(int a) {
      return a >>> 5 & 7 | 240;
   }

   public static int o2FUNii(int b1, int b2) {
      return b1 << 8 | b2 << 16 | 11;
   }
}
