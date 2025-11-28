package com.cobblemon.mod.relocations.ibm.icu.impl.duration.impl;

import java.util.Locale;

public class Utils {
   public static final Locale localeFromString(String s) {
      String language = s;
      String region = "";
      String variant = "";
      int x = s.indexOf("_");
      if (x != -1) {
         region = s.substring(x + 1);
         language = s.substring(0, x);
      }

      x = region.indexOf("_");
      if (x != -1) {
         variant = region.substring(x + 1);
         region = region.substring(0, x);
      }

      return new Locale(language, region, variant);
   }

   public static String chineseNumber(long n, Utils.ChineseDigits zh) {
      if (n < 0L) {
         n = -n;
      }

      if (n <= 10L) {
         return n == 2L ? String.valueOf(zh.liang) : String.valueOf(zh.digits[(int)n]);
      } else {
         char[] buf = new char[40];
         char[] digits = String.valueOf(n).toCharArray();
         boolean inZero = true;
         boolean forcedZero = false;
         int x = buf.length;
         int i = digits.length;
         int u = -1;
         int l = -1;

         while (--i >= 0) {
            if (u == -1) {
               if (l != -1) {
                  buf[--x] = zh.levels[l];
                  inZero = true;
                  forcedZero = false;
               }

               u++;
            } else {
               buf[--x] = zh.units[u++];
               if (u == 3) {
                  u = -1;
                  l++;
               }
            }

            int d = digits[i] - '0';
            if (d == 0) {
               if (x < buf.length - 1 && u != 0) {
                  buf[x] = '*';
               }

               if (!inZero && !forcedZero) {
                  buf[--x] = zh.digits[0];
                  inZero = true;
                  forcedZero = u == 1;
               } else {
                  buf[--x] = '*';
               }
            } else {
               inZero = false;
               buf[--x] = zh.digits[d];
            }
         }

         if (n > 1000000L) {
            boolean last = true;
            u = buf.length - 3;

            while (buf[u] != '0') {
               u -= 8;
               last = !last;
               if (u <= x) {
                  break;
               }
            }

            u = buf.length - 7;

            do {
               if (buf[u] == zh.digits[0] && !last) {
                  buf[u] = '*';
               }

               u -= 8;
               last = !last;
            } while (u > x);

            if (n >= 100000000L) {
               u = buf.length - 8;

               do {
                  boolean empty = true;
                  int j = u - 1;

                  for (int e = Math.max(x - 1, u - 8); j > e; j--) {
                     if (buf[j] != '*') {
                        empty = false;
                        break;
                     }
                  }

                  if (empty) {
                     if (buf[u + 1] != '*' && buf[u + 1] != zh.digits[0]) {
                        buf[u] = zh.digits[0];
                     } else {
                        buf[u] = '*';
                     }
                  }

                  u -= 8;
               } while (u > x);
            }
         }

         for (int ix = x; ix < buf.length; ix++) {
            if (buf[ix] == zh.digits[2]
               && (ix >= buf.length - 1 || buf[ix + 1] != zh.units[0])
               && (ix <= x || buf[ix - 1] != zh.units[0] && buf[ix - 1] != zh.digits[0] && buf[ix - 1] != '*')) {
               buf[ix] = zh.liang;
            }
         }

         if (buf[x] == zh.digits[1] && (zh.ko || buf[x + 1] == zh.units[0])) {
            x++;
         }

         i = x;

         for (int r = x; r < buf.length; r++) {
            if (buf[r] != '*') {
               buf[i++] = buf[r];
            }
         }

         return new String(buf, x, i - x);
      }
   }

   public static class ChineseDigits {
      final char[] digits;
      final char[] units;
      final char[] levels;
      final char liang;
      final boolean ko;
      public static final Utils.ChineseDigits DEBUG = new Utils.ChineseDigits("0123456789s", "sbq", "WYZ", 'L', false);
      public static final Utils.ChineseDigits TRADITIONAL = new Utils.ChineseDigits("零一二三四五六七八九十", "十百千", "萬億兆", '兩', false);
      public static final Utils.ChineseDigits SIMPLIFIED = new Utils.ChineseDigits("零一二三四五六七八九十", "十百千", "万亿兆", '两', false);
      public static final Utils.ChineseDigits KOREAN = new Utils.ChineseDigits("영일이삼사오육칠팔구십", "십백천", "만억?", '이', true);

      ChineseDigits(String digits, String units, String levels, char liang, boolean ko) {
         this.digits = digits.toCharArray();
         this.units = units.toCharArray();
         this.levels = levels.toCharArray();
         this.liang = liang;
         this.ko = ko;
      }
   }
}
