package org.graalvm.shadowed.org.jcodings.transcode;

import java.util.Arrays;
import org.graalvm.shadowed.org.jcodings.transcode.specific.From_UTF8_MAC_Transcoder;

public class TranscodeFunctions {
   public static final int BE = 1;
   public static final int LE = 2;
   public static final int from_UTF_16BE_D8toDB_00toFF = Transcoding.WORDINDEX2INFO(39);
   public static final int from_UTF_16LE_00toFF_D8toDB = Transcoding.WORDINDEX2INFO(5);
   public static final byte G0_ASCII = 0;
   public static final byte G0_JISX0208_1978 = 1;
   public static final byte G0_JISX0208_1983 = 2;
   public static final byte G0_JISX0201_KATAKANA = 3;
   public static final int EMACS_MULE_LEADING_CODE_JISX0208_1978 = 144;
   public static final int EMACS_MULE_LEADING_CODE_JISX0208_1983 = 146;
   public static final byte[] tbl0208 = new byte[]{
      33,
      35,
      33,
      86,
      33,
      87,
      33,
      34,
      33,
      38,
      37,
      114,
      37,
      33,
      37,
      35,
      37,
      37,
      37,
      39,
      37,
      41,
      37,
      99,
      37,
      101,
      37,
      103,
      37,
      67,
      33,
      60,
      37,
      34,
      37,
      36,
      37,
      38,
      37,
      40,
      37,
      42,
      37,
      43,
      37,
      45,
      37,
      47,
      37,
      49,
      37,
      51,
      37,
      53,
      37,
      55,
      37,
      57,
      37,
      59,
      37,
      61,
      37,
      63,
      37,
      65,
      37,
      68,
      37,
      70,
      37,
      72,
      37,
      74,
      37,
      75,
      37,
      76,
      37,
      77,
      37,
      78,
      37,
      79,
      37,
      82,
      37,
      85,
      37,
      88,
      37,
      91,
      37,
      94,
      37,
      95,
      37,
      96,
      37,
      97,
      37,
      98,
      37,
      100,
      37,
      102,
      37,
      104,
      37,
      105,
      37,
      106,
      37,
      107,
      37,
      108,
      37,
      109,
      37,
      111,
      37,
      115,
      33,
      43,
      33,
      44
   };
   public static final int iso2022jp_decoder_jisx0208_rest = Transcoding.WORDINDEX2INFO(16);
   public static final int iso2022jp_kddi_decoder_jisx0208_rest = Transcoding.WORDINDEX2INFO(16);
   private static final int STATUS_BUF_SIZE = 16;
   private static final int TOTAL_BUF_SIZE = 24;
   private static final int from_utf8_mac_nfc2 = Transcoding.WORDINDEX2INFO(35578);
   private static final int ESCAPE_END = 0;
   private static final int ESCAPE_NORMAL = 1;
   private static final int NEWLINE_NORMAL = 0;
   private static final int NEWLINE_JUST_AFTER_CR = 1;
   private static final int MET_LF = 1;
   private static final int MET_CRLF = 2;
   private static final int MET_CR = 4;

   public static int funSoToUTF16(byte[] statep, byte[] sBytes, int sStart, int l, byte[] o, int oStart, int osize) {
      int sp = 0;
      if (statep[sp] == 0) {
         o[oStart++] = -2;
         o[oStart++] = -1;
         statep[sp] = 1;
         return 2 + funSoToUTF16BE(statep, sBytes, sStart, l, o, oStart, osize);
      } else {
         return funSoToUTF16BE(statep, sBytes, sStart, l, o, oStart, osize);
      }
   }

   public static int funSoToUTF16BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      if ((s0 & 128) == 0) {
         o[oStart] = 0;
         o[oStart + 1] = (byte)s0;
         return 2;
      } else if ((s0 & 224) == 192) {
         int s1 = s[sStart + 1] & 255;
         o[oStart] = (byte)(s0 >> 2 & 7);
         o[oStart + 1] = (byte)((s0 & 3) << 6 | s1 & 63);
         return 2;
      } else if ((s0 & 240) == 224) {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         o[oStart] = (byte)(s0 << 4 | s1 >> 2 ^ 32);
         o[oStart + 1] = (byte)(s1 << 6 | s2 ^ 128);
         return 2;
      } else {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         int s3 = s[sStart + 3] & 255;
         int w = ((s0 & 7) << 2 | s1 >> 4 & 3) - 1;
         o[oStart] = (byte)(216 | w >> 2);
         o[oStart + 1] = (byte)(w << 6 | (s1 & 15) << 2 | (s2 >> 4) - 8);
         o[oStart + 2] = (byte)(220 | s2 >> 2 & 3);
         o[oStart + 3] = (byte)(s2 << 6 | s3 & -129);
         return 4;
      }
   }

   public static int funSoToUTF16LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      if ((s0 & 128) == 0) {
         o[oStart + 1] = 0;
         o[oStart] = (byte)s0;
         return 2;
      } else if ((s0 & 224) == 192) {
         int s1 = s[sStart + 1] & 255;
         o[oStart + 1] = (byte)(s0 >> 2 & 7);
         o[oStart] = (byte)((s0 & 3) << 6 | s1 & 63);
         return 2;
      } else if ((s0 & 240) == 224) {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         o[oStart + 1] = (byte)(s0 << 4 | s1 >> 2 ^ 32);
         o[oStart] = (byte)(s1 << 6 | s2 ^ 128);
         return 2;
      } else {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         int s3 = s[sStart + 3] & 255;
         int w = ((s0 & 7) << 2 | s1 >> 4 & 3) - 1;
         o[oStart + 1] = (byte)(216 | w >> 2);
         o[oStart] = (byte)(w << 6 | (s1 & 15) << 2 | (s2 >> 4) - 8);
         o[oStart + 3] = (byte)(220 | s2 >> 2 & 3);
         o[oStart + 2] = (byte)(s2 << 6 | s3 & -129);
         return 4;
      }
   }

   public static int funSoToUTF32(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int sp = 0;
      if (statep[sp] == 0) {
         o[oStart++] = 0;
         o[oStart++] = 0;
         o[oStart++] = -2;
         o[oStart++] = -1;
         statep[sp] = 1;
         return 4 + funSoToUTF32BE(statep, s, sStart, l, o, oStart, osize);
      } else {
         return funSoToUTF32BE(statep, s, sStart, l, o, oStart, osize);
      }
   }

   public static int funSoToUTF32BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      o[oStart] = 0;
      if ((s0 & 128) == 0) {
         o[oStart + 1] = o[oStart + 2] = 0;
         o[oStart + 3] = (byte)s0;
      } else if ((s0 & 224) == 192) {
         int s1 = s[sStart + 1] & 255;
         o[oStart + 1] = 0;
         o[oStart + 2] = (byte)(s0 >> 2 & 7);
         o[oStart + 3] = (byte)((s0 & 3) << 6 | s1 & 63);
      } else if ((s0 & 240) == 224) {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         o[oStart + 1] = 0;
         o[oStart + 2] = (byte)(s0 << 4 | s1 >> 2 ^ 32);
         o[oStart + 3] = (byte)(s1 << 6 | s2 ^ 128);
      } else {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         int s3 = s[sStart + 3] & 255;
         o[oStart + 1] = (byte)((s0 & 7) << 2 | s1 >> 4 & 3);
         o[oStart + 2] = (byte)((s1 & 15) << 4 | s2 >> 2 & 15);
         o[oStart + 3] = (byte)((s2 & 3) << 6 | s3 & 63);
      }

      return 4;
   }

   public static int funSoToUTF32LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      o[oStart + 3] = 0;
      int s0 = s[sStart] & 255;
      if ((s0 & 128) == 0) {
         o[oStart + 2] = o[oStart + 1] = 0;
         o[oStart] = (byte)s0;
      } else if ((s[sStart] & 224) == 192) {
         int s1 = s[sStart + 1] & 255;
         o[oStart + 2] = 0;
         o[oStart + 1] = (byte)(s0 >> 2 & 7);
         o[oStart] = (byte)((s0 & 3) << 6 | s1 & 63);
      } else if ((s[sStart] & 240) == 224) {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         o[oStart + 2] = 0;
         o[oStart + 1] = (byte)(s0 << 4 | s1 >> 2 ^ 32);
         o[oStart] = (byte)(s1 << 6 | s2 ^ 128);
      } else {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         int s3 = s[sStart + 3] & 255;
         o[oStart + 2] = (byte)((s0 & 7) << 2 | s1 >> 4 & 3);
         o[oStart + 1] = (byte)((s1 & 15) << 4 | s2 >> 2 & 15);
         o[oStart] = (byte)((s2 & 3) << 6 | s3 & 63);
      }

      return 4;
   }

   public static int funSiFromUTF32(byte[] statep, byte[] s, int sStart, int l) {
      int s0 = s[sStart] & 255;
      int s1 = s[sStart + 1] & 255;
      int s2 = s[sStart + 2] & 255;
      switch (statep[0]) {
         case 0:
            int s3 = s[sStart + 3] & 255;
            if (s0 == 0 && s1 == 0 && s2 == 254 && s3 == 255) {
               statep[0] = 1;
               return 10;
            }

            if (s0 == 255 && s1 == 254 && s2 == 0 && s3 == 0) {
               statep[0] = 2;
               return 10;
            }
            break;
         case 1:
            if (s0 == 0 && 0 < s1 && s1 <= 16 || s1 == 0 && (s2 < 216 || 223 < s2)) {
               return 15;
            }
            break;
         case 2:
            int s3x = s[sStart + 3] & 255;
            if (s3x == 0 && (0 < s2 && s2 <= 16 || s2 == 0 && (s1 < 216 || 223 < s1))) {
               return 15;
            }
      }

      return 7;
   }

   public static int funSoFromUTF32(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      switch (statep[0]) {
         case 1:
            return funSoFromUTF32BE(statep, s, sStart, l, o, oStart, osize);
         case 2:
            return funSoFromUTF32LE(statep, s, sStart, l, o, oStart, osize);
         default:
            return 0;
      }
   }

   public static int funSoFromUTF32BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int s1 = s[sStart + 1] & 255;
      int s2 = s[sStart + 2] & 255;
      int s3 = s[sStart + 3] & 255;
      if (s1 == 0) {
         if (s2 == 0 && s3 < 128) {
            o[oStart] = (byte)s3;
            return 1;
         } else if (s2 < 8) {
            o[oStart] = (byte)(192 | s2 << 2 | s3 >> 6);
            o[oStart + 1] = (byte)(128 | s3 & 63);
            return 2;
         } else {
            o[oStart] = (byte)(224 | s2 >> 4);
            o[oStart + 1] = (byte)(128 | (s2 & 15) << 2 | s3 >> 6);
            o[oStart + 2] = (byte)(128 | s3 & 63);
            return 3;
         }
      } else {
         o[oStart] = (byte)(240 | s1 >> 2);
         o[oStart + 1] = (byte)(128 | (s1 & 3) << 4 | s2 >> 4);
         o[oStart + 2] = (byte)(128 | (s2 & 15) << 2 | s3 >> 6);
         o[oStart + 3] = (byte)(128 | s3 & 63);
         return 4;
      }
   }

   public static int funSoFromUTF32LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      int s1 = s[sStart + 1] & 255;
      int s2 = s[sStart + 2] & 255;
      if (s2 == 0) {
         if (s1 == 0 && s0 < 128) {
            o[oStart] = (byte)s0;
            return 1;
         } else if (s1 < 8) {
            o[oStart] = (byte)(192 | s1 << 2 | s0 >> 6);
            o[oStart + 1] = (byte)(128 | s0 & 63);
            return 2;
         } else {
            o[oStart] = (byte)(224 | s1 >> 4);
            o[oStart + 1] = (byte)(128 | (s1 & 15) << 2 | s0 >> 6);
            o[oStart + 2] = (byte)(128 | s0 & 63);
            return 3;
         }
      } else {
         o[oStart] = (byte)(240 | s2 >> 2);
         o[oStart + 1] = (byte)(128 | (s2 & 3) << 4 | s1 >> 4);
         o[oStart + 2] = (byte)(128 | (s1 & 15) << 2 | s0 >> 6);
         o[oStart + 3] = (byte)(128 | s0 & 63);
         return 4;
      }
   }

   public static int funSiFromUTF16(byte[] statep, byte[] s, int sStart, int l) {
      int s0 = s[sStart] & 255;
      switch (statep[0]) {
         case 0:
            int s1 = s[sStart + 1] & 255;
            if (s0 == 254 && s1 == 255) {
               statep[0] = 1;
               return 10;
            }

            if (s0 == 255 && s1 == 254) {
               statep[0] = 2;
               return 10;
            }
            break;
         case 1:
            if (s0 < 216 || 223 < s0) {
               return 15;
            }

            if (s0 <= 219) {
               return from_UTF_16BE_D8toDB_00toFF;
            }
            break;
         case 2:
            int s1x = s[sStart + 1] & 255;
            if (s1x < 216 || 223 < s1x) {
               return 15;
            }

            if (s1x <= 219) {
               return from_UTF_16LE_00toFF_D8toDB;
            }
      }

      return 7;
   }

   public static int funSoFromUTF16(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      switch (statep[0]) {
         case 1:
            return funSoFromUTF16BE(statep, s, sStart, l, o, oStart, osize);
         case 2:
            return funSoFromUTF16LE(statep, s, sStart, l, o, oStart, osize);
         default:
            return 0;
      }
   }

   public static int funSoFromUTF16BE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      int s1 = s[sStart + 1] & 255;
      if (s0 == 0 && s1 < 128) {
         o[oStart] = (byte)s1;
         return 1;
      } else if (s0 < 8) {
         o[oStart] = (byte)(192 | s0 << 2 | s1 >> 6);
         o[oStart + 1] = (byte)(128 | s1 & 63);
         return 2;
      } else if ((s0 & 248) != 216) {
         o[oStart] = (byte)(224 | s0 >> 4);
         o[oStart + 1] = (byte)(128 | (s0 & 15) << 2 | s1 >> 6);
         o[oStart + 2] = (byte)(128 | s1 & 63);
         return 3;
      } else {
         int s2 = s[sStart + 2] & 255;
         int s3 = s[sStart + 3] & 255;
         long u = ((s0 & 3) << 2 | s1 >> 6) + 1;
         o[oStart] = (byte)(240L | u >> 2);
         o[oStart + 1] = (byte)(128L | (u & 3L) << 4 | s1 >> 2 & 15);
         o[oStart + 2] = (byte)(128 | (s1 & 3) << 4 | (s2 & 3) << 2 | s3 >> 6);
         o[oStart + 3] = (byte)(128 | s3 & 63);
         return 4;
      }
   }

   public static int funSoFromUTF16LE(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      int s1 = s[sStart + 1] & 255;
      if (s1 == 0 && s0 < 128) {
         o[oStart] = (byte)s0;
         return 1;
      } else if (s1 < 8) {
         o[oStart] = (byte)(192 | s1 << 2 | s0 >> 6);
         o[oStart + 1] = (byte)(128 | s0 & 63);
         return 2;
      } else if ((s1 & 248) != 216) {
         o[oStart] = (byte)(224 | s1 >> 4);
         o[oStart + 1] = (byte)(128 | (s1 & 15) << 2 | s0 >> 6);
         o[oStart + 2] = (byte)(128 | s0 & 63);
         return 3;
      } else {
         int s2 = s[sStart + 2] & 255;
         int s3 = s[sStart + 3] & 255;
         long u = ((s1 & 3) << 2 | s0 >> 6) + 1;
         o[oStart] = (byte)(240L | u >> 2);
         o[oStart + 1] = (byte)(128L | (u & 3L) << 4 | s0 >> 2 & 15);
         o[oStart + 2] = (byte)(128 | (s0 & 3) << 4 | (s3 & 3) << 2 | s2 >> 6);
         o[oStart + 3] = (byte)(128 | s2 & 63);
         return 4;
      }
   }

   public static int funSoEucjp2Sjis(byte[] statep, byte[] s, int sStart, int _l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      int s1 = s[sStart + 1] & 255;
      if (s0 == 142) {
         o[oStart] = (byte)s1;
         return 1;
      } else {
         int m = s0 & 1;
         int h = s0 + m >> 1;
         h += s0 < 223 ? 48 : 112;
         int l = s1 - m * 94 - 3;
         if (127 <= l) {
            l++;
         }

         o[oStart] = (byte)h;
         o[oStart + 1] = (byte)l;
         return 2;
      }
   }

   public static int funSoSjis2Eucjp(byte[] statep, byte[] s, int sStart, int _l, byte[] o, int oStart, int osize) {
      int s0 = s[sStart] & 255;
      if (_l == 1) {
         o[oStart] = -114;
         o[oStart + 1] = (byte)s0;
         return 2;
      } else {
         int h = s0;
         int l = s[sStart + 1] & 255;
         if (224 <= s0) {
            h = s0 - 64;
         }

         l += l < 128 ? 97 : 96;
         h = h * 2 - 97;
         if (254 < l) {
            l -= 94;
            h++;
         }

         o[oStart] = (byte)h;
         o[oStart + 1] = (byte)l;
         return 2;
      }
   }

   public static int funSoFromGB18030(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      long s0 = s[sStart] & 255;
      long s1 = s[sStart + 1] & 255;
      long s2 = s[sStart + 2] & 255;
      long s3 = s[sStart + 3] & 255;
      long u = (s0 - 144L) * 10L * 126L * 10L + (s1 - 48L) * 126L * 10L + (s2 - 129L) * 10L + (s3 - 48L) + 65536L & 4294967295L;
      o[oStart] = (byte)(240L | u >>> 18);
      o[oStart + 1] = (byte)(128L | u >>> 12 & 63L);
      o[oStart + 2] = (byte)(128L | u >>> 6 & 63L);
      o[oStart + 3] = (byte)(128L | u & 63L);
      return 4;
   }

   public static int funSioFromGB18030(byte[] statep, byte[] s, int sStart, int l, int info, byte[] o, int oStart, int osize) {
      long s0 = s[sStart] & 255;
      long s1 = s[sStart + 1] & 255;
      long diff = info >> 8;
      long u;
      if ((diff & 131072L) != 0L) {
         long s2 = s[sStart + 2] & 255;
         long s3 = s[sStart + 3] & 255;
         u = ((s0 * 10L + s1) * 126L + s2) * 10L + s3 - diff - 1507328L & 4294967295L;
      } else {
         u = s0 * 256L + s1 + 24055L - diff & 4294967295L;
      }

      o[oStart] = (byte)(224L | u >>> 12);
      o[oStart + 1] = (byte)(128L | u >>> 6 & 63L);
      o[oStart + 2] = (byte)(128L | u & 63L);
      return 3;
   }

   public static int funSoToGB18030(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int osize) {
      long s0 = s[sStart] & 255;
      long s1 = s[sStart + 1] & 255;
      long s2 = s[sStart + 2] & 255;
      long s3 = s[sStart + 3] & 255;
      long u = (s0 & 7L) << 18 | (s1 & 63L) << 12 | (s2 & 63L) << 6 | s3 & 63L;
      u -= 65536L;
      o[oStart + 3] = (byte)(48L + u % 10L);
      u /= 10L;
      o[oStart + 2] = (byte)(129L + u % 126L);
      u /= 126L;
      o[oStart + 1] = (byte)(48L + u % 10L);
      o[oStart] = (byte)(144L + u / 10L);
      return 4;
   }

   public static int funSioToGB18030(byte[] statep, byte[] s, int sStart, int l, int info, byte[] o, int oStart, int osize) {
      long s0 = s[sStart] & 255;
      long s1 = s[sStart + 1] & 255;
      long s2 = s[sStart + 2] & 255;
      long diff = info >>> 8;
      long u = (s0 & 15L) << 12 | (s1 & 63L) << 6 | s2 & 63L;
      if ((diff & 131072L) != 0L) {
         u += diff + 1507328L;
         u -= 1688980L;
         u += 2L;
         o[oStart + 3] = (byte)(48L + u % 10L);
         u /= 10L;
         u += 50L;
         o[oStart + 2] = (byte)(129L + u % 126L);
         u /= 126L;
         o[oStart + 1] = (byte)(48L + ++u % 10L);
         u /= 10L;
         o[oStart] = (byte)(129L + u);
         return 4;
      } else {
         u += diff - 24055L;
         o[oStart + 1] = (byte)(u % 256L);
         o[oStart] = (byte)(u / 256L);
         return 2;
      }
   }

   public static int iso2022jpInit(byte[] state) {
      state[0] = 0;
      return 0;
   }

   public static int funSoCp50220Encoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int output0 = oStart;
      if (statep[0] == 3) {
         int c = statep[2] & 127;
         int p = (c - 33) * 2;
         byte[] pBytes = tbl0208;
         if (statep[1] != 2) {
            o[oStart++] = 27;
            o[oStart++] = 36;
            o[oStart++] = 66;
         }

         statep[0] = 2;
         o[oStart++] = pBytes[p++];
         int s0 = s[sStart] & 255;
         int s1 = s[sStart + 1] & 255;
         if (l == 2 && s0 == 142) {
            if (s1 == 222) {
               o[oStart++] = (byte)(pBytes[p] + 1);
               return oStart - output0;
            }

            if (s1 == 223 && 74 <= c && c <= 78) {
               o[oStart++] = (byte)(pBytes[p] + 2);
               return oStart - output0;
            }
         }

         o[oStart++] = pBytes[p];
      }

      int s0 = s[sStart] & 255;
      if (l == 2 && s0 == 142) {
         int s1 = s[sStart + 1] & 255;
         int px = (s1 - 161) * 2;
         byte[] pBytesx = tbl0208;
         if (161 <= s1 && s1 <= 181 || 197 <= s1 && s1 <= 201 || 207 <= s1 && s1 <= 223) {
            if (statep[0] != 2) {
               o[oStart++] = 27;
               o[oStart++] = 36;
               o[oStart++] = 66;
               statep[0] = 2;
            }

            o[oStart++] = pBytesx[px++];
            o[oStart++] = pBytesx[px];
            return oStart - output0;
         } else {
            statep[2] = (byte)s1;
            statep[1] = statep[0];
            statep[0] = 3;
            return oStart - output0;
         }
      } else {
         oStart += funSoCp5022xEncoder(statep, s, sStart, l, o, oStart, oSize);
         return oStart - output0;
      }
   }

   public static int funSoCp5022xEncoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int output0 = oStart;
      int newstate;
      if (l == 1) {
         newstate = 0;
      } else if ((s[sStart] & 255) == 142) {
         sStart++;
         l = 1;
         newstate = 3;
      } else {
         newstate = 2;
      }

      if (statep[0] != newstate) {
         if (newstate == 0) {
            o[oStart++] = 27;
            o[oStart++] = 40;
            o[oStart++] = 66;
         } else if (newstate == 3) {
            o[oStart++] = 27;
            o[oStart++] = 40;
            o[oStart++] = 73;
         } else {
            o[oStart++] = 27;
            o[oStart++] = 36;
            o[oStart++] = 66;
         }

         statep[0] = (byte)newstate;
      }

      int s0 = s[sStart] & 255;
      if (l == 1) {
         o[oStart++] = (byte)(s0 & 127);
      } else {
         int s1 = s[sStart + 1] & 255;
         o[oStart++] = (byte)(s0 & 127);
         o[oStart++] = (byte)(s1 & 127);
      }

      return oStart - output0;
   }

   public static int finishCp50220Encoder(byte[] statep, byte[] o, int oStart, int size) {
      int output0 = oStart;
      if (statep[0] == 0) {
         return 0;
      } else {
         if (statep[0] == 3) {
            int c = statep[2] & 127;
            int p = (c - 33) * 2;
            byte[] pBytes = tbl0208;
            if (statep[1] != 2) {
               o[oStart++] = 27;
               o[oStart++] = 36;
               o[oStart++] = 66;
            }

            statep[0] = 2;
            o[oStart++] = pBytes[p++];
            o[oStart++] = pBytes[p];
         }

         o[oStart++] = 27;
         o[oStart++] = 40;
         o[oStart++] = 66;
         statep[0] = 0;
         return oStart - output0;
      }
   }

   public static int iso2022jpEncoderResetSequenceSize(byte[] statep) {
      return statep[0] != 0 ? 3 : 0;
   }

   public static int funSiIso50220jpDecoder(byte[] statep, byte[] s, int sStart, int l) {
      int s0 = s[sStart] & 255;
      if (statep[0] == 0) {
         return 1;
      } else {
         return 33 <= s0 && s0 <= 126 ? iso2022jp_decoder_jisx0208_rest : 7;
      }
   }

   public static int funSoIso2022jpDecoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int s0 = s[sStart] & 255;
      int s1 = s[sStart + 1] & 255;
      if (s0 == 27) {
         if (s1 == 40) {
            switch (s[sStart + l - 1] & 0xFF) {
               case 66:
               case 74:
                  statep[0] = 0;
            }
         } else {
            switch (s[sStart + l - 1]) {
               case 64:
                  statep[0] = 1;
                  break;
               case 66:
                  statep[0] = 2;
            }
         }

         return 0;
      } else {
         if (statep[0] == 1) {
            o[oStart] = -112;
         } else {
            o[oStart] = -110;
         }

         o[oStart + 1] = (byte)(s0 | 128);
         o[oStart + 2] = (byte)(s1 | 128);
         return 3;
      }
   }

   public static int funSoStatelessIso2022jpToEucjp(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      o[oStart] = s[sStart + 1];
      o[oStart + 1] = s[sStart + 2];
      return 2;
   }

   public static int funSoEucjpToStatelessIso2022jp(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      o[oStart] = -110;
      o[oStart + 1] = s[sStart];
      o[oStart + 2] = s[sStart + 1];
      return 3;
   }

   public static int funSoIso2022jpEncoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int output0 = oStart;
      int newstate;
      if (l == 1) {
         newstate = 0;
      } else if ((s[sStart] & 255) == 144) {
         newstate = 1;
      } else {
         newstate = 2;
      }

      if (statep[0] != newstate) {
         if (newstate == 0) {
            o[oStart++] = 27;
            o[oStart++] = 40;
            o[oStart++] = 66;
         } else if (newstate == 1) {
            o[oStart++] = 27;
            o[oStart++] = 36;
            o[oStart++] = 64;
         } else {
            o[oStart++] = 27;
            o[oStart++] = 36;
            o[oStart++] = 66;
         }

         statep[0] = (byte)newstate;
      }

      if (l == 1) {
         o[oStart++] = (byte)(s[sStart] & 127);
      } else {
         o[oStart++] = (byte)(s[sStart + 1] & 127);
         o[oStart++] = (byte)(s[sStart + 2] & 127);
      }

      return oStart - output0;
   }

   public static int finishIso2022jpEncoder(byte[] statep, byte[] o, int oStart, int oSize) {
      int output0 = oStart;
      if (statep[0] == 0) {
         return 0;
      } else {
         o[oStart++] = 27;
         o[oStart++] = 40;
         o[oStart++] = 66;
         statep[0] = 0;
         return oStart - output0;
      }
   }

   public static int funSiCp50221Decoder(byte[] statep, byte[] s, int sStart, int l) {
      int s0 = s[sStart] & 255;
      switch (statep[0]) {
         case 0:
            if (161 <= s0 && s0 <= 223) {
               return 15;
            }

            return 1;
         case 1:
            if (33 <= s0 && s0 <= 40 || 48 <= s0 && s0 <= 116) {
               return iso2022jp_decoder_jisx0208_rest;
            }
            break;
         case 2:
            if (33 <= s0 && s0 <= 40 || s0 == 45 || 48 <= s0 && s0 <= 116 || 121 <= s0 && s0 <= 124) {
               return iso2022jp_decoder_jisx0208_rest;
            }
            break;
         case 3:
            int c = s0 & 127;
            if (33 <= c && c <= 95) {
               return 15;
            }
      }

      return 7;
   }

   public static int funSoCp50221Decoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int s0 = s[sStart] & 255;
      switch (s0) {
         case 14:
            statep[0] = 3;
            return 0;
         case 15:
            statep[0] = 0;
            return 0;
         case 27:
            int s1 = s[sStart + 1] & 255;
            if (s1 == 40) {
               switch (s[sStart + l - 1] & 0xFF) {
                  case 66:
                  case 74:
                     statep[0] = 0;
                     break;
                  case 73:
                     statep[0] = 3;
               }
            } else {
               switch (s[sStart + l - 1] & 0xFF) {
                  case 64:
                     statep[0] = 1;
                     break;
                  case 66:
                     statep[0] = 2;
               }
            }

            return 0;
         default:
            if (statep[0] != 3 && (161 > s0 || s0 > 223 || statep[0] != 0)) {
               int s1 = s[sStart + 1] & 255;
               o[oStart] = (byte)(s0 | 128);
               o[oStart + 1] = (byte)(s1 | 128);
            } else {
               o[oStart] = -114;
               o[oStart + 1] = (byte)(s0 | 128);
            }

            return 2;
      }
   }

   public static int iso2022jpKddiInit(byte[] statep) {
      statep[0] = 0;
      return 0;
   }

   public static int funSiIso2022jpKddiDecoder(byte[] statep, byte[] s, int sStart, int l) {
      int s0 = s[sStart] & 255;
      if (statep[0] == 0) {
         return 1;
      } else {
         return 33 <= s0 && s0 <= 126 ? iso2022jp_kddi_decoder_jisx0208_rest : 7;
      }
   }

   public static int funSoIso2022jpKddiDecoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int s0 = s[sStart] & 255;
      int s1 = s[sStart + 1] & 255;
      if (s0 == 27) {
         if (s1 == 40) {
            switch (s[sStart + l - 1] & 0xFF) {
               case 66:
               case 74:
                  statep[0] = 0;
            }
         } else {
            switch (s[sStart + l - 1] & 0xFF) {
               case 64:
                  statep[0] = 1;
                  break;
               case 66:
                  statep[0] = 2;
            }
         }

         return 0;
      } else {
         if (statep[0] == 1) {
            o[oStart] = -112;
         } else {
            o[oStart] = -110;
         }

         o[oStart + 1] = (byte)(s0 | 128);
         o[oStart + 2] = (byte)(s1 | 128);
         return 3;
      }
   }

   public static int funSoIso2022jpKddiEncoder(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int s0 = s[sStart] & 255;
      int output0 = oStart;
      int newstate;
      if (l == 1) {
         newstate = 0;
      } else if (s0 == 144) {
         newstate = 1;
      } else {
         newstate = 2;
      }

      if (statep[0] != newstate) {
         o[oStart++] = 27;
         switch (newstate) {
            case 0:
               o[oStart++] = 40;
               o[oStart++] = 66;
               break;
            case 1:
               o[oStart++] = 36;
               o[oStart++] = 64;
               break;
            default:
               o[oStart++] = 36;
               o[oStart++] = 66;
         }

         statep[0] = (byte)newstate;
      }

      if (l == 1) {
         o[oStart++] = (byte)(s0 & 127);
      } else {
         int s1 = s[sStart + 1] & 255;
         int s2 = s[sStart + 2] & 255;
         o[oStart++] = (byte)(s1 & 127);
         o[oStart++] = (byte)(s2 & 127);
      }

      return oStart - output0;
   }

   public static int finishIso2022jpKddiEncoder(byte[] statep, byte[] o, int oStart, int oSize) {
      int output0 = oStart;
      if (statep[0] == 0) {
         return 0;
      } else {
         o[oStart++] = 27;
         o[oStart++] = 40;
         o[oStart++] = 66;
         statep[0] = 0;
         return oStart - output0;
      }
   }

   public static int iso2022jpKddiEncoderResetSequence_size(byte[] statep) {
      return statep[0] != 0 ? 3 : 0;
   }

   public static int fromUtf8MacInit(byte[] state) {
      bufClear(state);
      return 0;
   }

   private static final int bufBytesize(byte[] p) {
      return (bufEnd(p) - bufBeg(p) + 16) % 16;
   }

   private static final byte bufAt(byte[] sp, int pos) {
      pos += bufBeg(sp);
      pos %= 16;
      return sp[pos];
   }

   private static void bufClear(byte[] state) {
      assert state.length >= 24 : "UTF8-MAC state not large enough";

      Arrays.fill(state, (byte)0);
   }

   public static int funSoFromUtf8Mac(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int n = 0;
      switch (l) {
         case 1:
            n = fromUtf8MacFinish(statep, o, oStart, oSize);
         default:
            bufPush(statep, s, sStart, l);
            return n + bufApply(statep, o, oStart);
         case 4:
            n = fromUtf8MacFinish(statep, o, oStart, oSize);
            o[oStart + n++] = s[sStart++];
            o[oStart + n++] = s[sStart++];
            o[oStart + n++] = s[sStart++];
            o[oStart + n++] = s[sStart++];
            return n;
      }
   }

   private static void bufPush(byte[] sp, byte[] p, int pStart, int l) {
      int pend = pStart + l;

      while (pStart < pend) {
         sp[bufEndPostInc(sp)] = p[pStart++];
         bufEnd(sp, bufEnd(sp) % 16);
      }
   }

   private static int bufApply(byte[] sp, byte[] o, int oStart) {
      int n = 0;
      byte[] buf = new byte[]{0, 0, 0};
      if (bufBytesize(sp) >= 3 && (bufBytesize(sp) != 3 || bufAt(sp, 0) < 224)) {
         int next_info = getInfo(from_utf8_mac_nfc2, sp);
         switch (next_info & 31) {
            case 3:
            case 5:
               buf[n++] = Transcoding.getBT1(next_info);
               buf[n++] = Transcoding.getBT2(next_info);
               if (5 == (next_info & 31)) {
                  buf[n++] = Transcoding.getBT3(next_info);
               }

               bufClear(sp);
               bufPush(sp, buf, 0, n);
               return 0;
            default:
               return bufOutputChar(sp, o, oStart);
         }
      } else {
         return 0;
      }
   }

   private static boolean bufEmpty(byte[] sp) {
      return bufBeg(sp) == bufEnd(sp);
   }

   private static byte bufShift(byte[] sp) {
      int c = sp[bufBegPostInc(sp)];
      bufBeg(sp, bufBeg(sp) % 16);
      return (byte)c;
   }

   private static boolean utf8Trailbyte(byte c) {
      return (c & 192) == 128;
   }

   private static int bufOutputChar(byte[] sp, byte[] o, int oStart) {
      int n = 0;

      while (!bufEmpty(sp)) {
         o[oStart + n++] = bufShift(sp);
         if (!utf8Trailbyte(sp[bufBeg(sp)])) {
            break;
         }
      }

      return n;
   }

   private static int getInfo(int nextInfo, byte[] sp) {
      int pos = 0;

      while (pos < bufBytesize(sp)) {
         int next_byte = bufAt(sp, pos++) & 255;
         if (next_byte >= UTF8MAC_BL_MIN_BYTE(nextInfo) && UTF8MAC_BL_MAX_BYTE(nextInfo) >= next_byte) {
            nextInfo = UTF8MAC_BL_ACTION(nextInfo, (byte)next_byte);
         } else {
            nextInfo = 7;
         }

         if ((nextInfo & 3) != 0) {
            break;
         }
      }

      return nextInfo;
   }

   public static int UTF8MAC_BL_MIN_BYTE(int nextInfo) {
      return From_UTF8_MAC_Transcoder.INSTANCE.byteArray[BL_BASE(nextInfo)] & 0xFF;
   }

   public static int UTF8MAC_BL_MAX_BYTE(int nextInfo) {
      return From_UTF8_MAC_Transcoder.INSTANCE.byteArray[BL_BASE(nextInfo) + 1] & 0xFF;
   }

   public static int UTF8MAC_BL_OFFSET(int nextInfo, int b) {
      return From_UTF8_MAC_Transcoder.INSTANCE.byteArray[BL_BASE(nextInfo) + 2 + b - UTF8MAC_BL_MIN_BYTE(nextInfo)] & 0xFF;
   }

   public static int UTF8MAC_BL_ACTION(int nextInfo, byte b) {
      return From_UTF8_MAC_Transcoder.INSTANCE.intArray[BL_INFO(nextInfo) + UTF8MAC_BL_OFFSET(nextInfo, b & 0xFF)];
   }

   private static int BL_BASE(int nextInfo) {
      return BYTE_ADDR(BYTE_LOOKUP_BASE(WORD_ADDR(nextInfo)));
   }

   private static int BL_INFO(int nextInfo) {
      return WORD_ADDR(BYTE_LOOKUP_INFO(WORD_ADDR(nextInfo)));
   }

   private static int BYTE_ADDR(int index) {
      return index;
   }

   private static int WORD_ADDR(int index) {
      return TranscodeTableSupport.INFO2WORDINDEX(index);
   }

   private static int BYTE_LOOKUP_BASE(int bl) {
      return From_UTF8_MAC_Transcoder.INSTANCE.intArray[bl];
   }

   private static int BYTE_LOOKUP_INFO(int bl) {
      return From_UTF8_MAC_Transcoder.INSTANCE.intArray[bl + 1];
   }

   private static int bufInt(byte[] statep, int base) {
      return statep[base] << 24 | statep[base + 1] << 16 | statep[base + 2] << 8 | statep[base + 3];
   }

   private static void bufInt(byte[] statep, int base, int val) {
      statep[base] = (byte)(val >>> 24 & 0xFF);
      statep[base + 1] = (byte)(val >>> 16 & 0xFF);
      statep[base + 2] = (byte)(val >>> 8 & 0xFF);
      statep[base + 3] = (byte)(val & 0xFF);
   }

   private static int bufBeg(byte[] statep) {
      return bufInt(statep, 16);
   }

   private static int bufEnd(byte[] statep) {
      return bufInt(statep, 20);
   }

   private static void bufBeg(byte[] statep, int end) {
      bufInt(statep, 16, end);
   }

   private static void bufEnd(byte[] statep, int end) {
      bufInt(statep, 20, end);
   }

   private static int bufEndPostInc(byte[] statep) {
      int end = bufInt(statep, 20);
      bufInt(statep, 20, end + 1);
      return end;
   }

   private static int bufBegPostInc(byte[] statep) {
      int beg = bufInt(statep, 16);
      bufInt(statep, 16, beg + 1);
      return beg;
   }

   public static int fromUtf8MacFinish(byte[] statep, byte[] o, int oStart, int oSize) {
      return bufOutputAll(statep, o, oStart);
   }

   private static int bufOutputAll(byte[] sp, byte[] o, int oStart) {
      int n = 0;

      while (!bufEmpty(sp)) {
         o[oStart + n++] = bufShift(sp);
      }

      return n;
   }

   public static int escapeXmlAttrQuoteInit(byte[] statep) {
      statep[0] = 0;
      return 0;
   }

   public static int funSoEscapeXmlAttrQuote(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int n = 0;
      if (statep[0] == 0) {
         statep[0] = 1;
         o[oStart + n++] = 34;
      }

      o[oStart + n++] = s[sStart];
      return n;
   }

   public static int escapeXmlAttrQuoteFinish(byte[] statep, byte[] o, int oStart, int oSize) {
      int n = 0;
      if (statep[0] == 0) {
         o[oStart + n++] = 34;
      }

      o[oStart + n++] = 34;
      statep[0] = 0;
      return n;
   }

   private static byte NEWLINE_STATE(byte[] sp) {
      return sp[0];
   }

   private static void NEWLINE_STATE(byte[] sp, int b) {
      sp[0] = (byte)b;
   }

   private static void NEWLINE_NEWLINES_MET(byte[] sp, int b) {
      sp[1] = (byte)b;
   }

   private static void NEWLINE_NEWLINES_MET_or_mask(byte[] sp, int b) {
      sp[1] |= (byte)b;
   }

   public static int universalNewlineInit(byte[] statep) {
      NEWLINE_STATE(statep, 0);
      NEWLINE_NEWLINES_MET(statep, 0);
      return 0;
   }

   public static int funSoUniversalNewline(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      int s0 = s[sStart] & 255;
      int len;
      if (s0 == 10) {
         if (NEWLINE_STATE(statep) == 0) {
            NEWLINE_NEWLINES_MET_or_mask(statep, 1);
         } else {
            NEWLINE_NEWLINES_MET_or_mask(statep, 2);
         }

         o[oStart] = 10;
         len = 1;
         NEWLINE_STATE(statep, 0);
      } else {
         len = 0;
         if (NEWLINE_STATE(statep) == 1) {
            o[oStart] = 10;
            len = 1;
            NEWLINE_NEWLINES_MET_or_mask(statep, 4);
         }

         if (s0 == 13) {
            NEWLINE_STATE(statep, 1);
         } else {
            o[oStart + len++] = (byte)s0;
            NEWLINE_STATE(statep, 0);
         }
      }

      return len;
   }

   public static int universalNewlineFinish(byte[] statep, byte[] o, int oStart, int oSize) {
      int len = 0;
      if (NEWLINE_STATE(statep) == 1) {
         o[oStart] = 10;
         len = 1;
         NEWLINE_NEWLINES_MET_or_mask(statep, 4);
      }

      statep[0] = 0;
      return len;
   }
}
