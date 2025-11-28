package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;

final class BidiWriter {
   static final char LRM_CHAR = '\u200e';
   static final char RLM_CHAR = '\u200f';
   static final int MASK_R_AL = 8194;

   private static boolean IsCombining(int type) {
      return (1 << type & 448) != 0;
   }

   private static String doWriteForward(String src, int options) {
      switch (options & 10) {
         case 0:
            return src;
         case 2:
            StringBuffer dest = new StringBuffer(src.length());
            int i = 0;

            do {
               int c = UTF16.charAt(src, i);
               i += UTF16.getCharCount(c);
               UTF16.append(dest, UCharacter.getMirror(c));
            } while (i < src.length());

            return dest.toString();
         case 8:
            StringBuilder dest = new StringBuilder(src.length());
            int i = 0;

            do {
               char c = src.charAt(i++);
               if (!Bidi.IsBidiControlChar(c)) {
                  dest.append(c);
               }
            } while (i < src.length());

            return dest.toString();
         default:
            StringBuffer dest = new StringBuffer(src.length());
            int i = 0;

            do {
               int c = UTF16.charAt(src, i);
               i += UTF16.getCharCount(c);
               if (!Bidi.IsBidiControlChar(c)) {
                  UTF16.append(dest, UCharacter.getMirror(c));
               }
            } while (i < src.length());

            return dest.toString();
      }
   }

   private static String doWriteForward(char[] text, int start, int limit, int options) {
      return doWriteForward(new String(text, start, limit - start), options);
   }

   static String writeReverse(String src, int options) {
      StringBuffer dest = new StringBuffer(src.length());
      switch (options & 11) {
         case 0:
            int srcLength = src.length();

            do {
               int ix = srcLength;
               srcLength -= UTF16.getCharCount(UTF16.charAt(src, srcLength - 1));
               dest.append(src.substring(srcLength, ix));
            } while (srcLength > 0);

            return dest.toString();
         case 1:
            int srcLength = src.length();

            do {
               int ix = srcLength;

               int cx;
               do {
                  cx = UTF16.charAt(src, srcLength - 1);
                  srcLength -= UTF16.getCharCount(cx);
               } while (srcLength > 0 && IsCombining(UCharacter.getType(cx)));

               dest.append(src.substring(srcLength, ix));
            } while (srcLength > 0);

            return dest.toString();
         default:
            int srcLength = src.length();

            do {
               int i = srcLength;
               int c = UTF16.charAt(src, srcLength - 1);
               srcLength -= UTF16.getCharCount(c);
               if ((options & 1) != 0) {
                  while (srcLength > 0 && IsCombining(UCharacter.getType(c))) {
                     c = UTF16.charAt(src, srcLength - 1);
                     srcLength -= UTF16.getCharCount(c);
                  }
               }

               if ((options & 8) == 0 || !Bidi.IsBidiControlChar(c)) {
                  int j = srcLength;
                  if ((options & 2) != 0) {
                     c = UCharacter.getMirror(c);
                     UTF16.append(dest, c);
                     j = srcLength + UTF16.getCharCount(c);
                  }

                  dest.append(src.substring(j, i));
               }
            } while (srcLength > 0);

            return dest.toString();
      }
   }

   static String doWriteReverse(char[] text, int start, int limit, int options) {
      return writeReverse(new String(text, start, limit - start), options);
   }

   static String writeReordered(Bidi bidi, int options) {
      char[] text = bidi.text;
      int runCount = bidi.countRuns();
      if ((bidi.reorderingOptions & 1) != 0) {
         options |= 4;
         options &= -9;
      }

      if ((bidi.reorderingOptions & 2) != 0) {
         options |= 8;
         options &= -5;
      }

      if (bidi.reorderingMode != 4 && bidi.reorderingMode != 5 && bidi.reorderingMode != 6 && bidi.reorderingMode != 3) {
         options &= -5;
      }

      StringBuilder dest = new StringBuilder((options & 4) != 0 ? bidi.length * 2 : bidi.length);
      if ((options & 16) == 0) {
         if ((options & 4) == 0) {
            for (int run = 0; run < runCount; run++) {
               BidiRun bidiRun = bidi.getVisualRun(run);
               if (bidiRun.isEvenRun()) {
                  dest.append(doWriteForward(text, bidiRun.start, bidiRun.limit, options & -3));
               } else {
                  dest.append(doWriteReverse(text, bidiRun.start, bidiRun.limit, options));
               }
            }
         } else {
            byte[] dirProps = bidi.dirProps;

            for (int runx = 0; runx < runCount; runx++) {
               BidiRun bidiRun = bidi.getVisualRun(runx);
               int markFlag = 0;
               markFlag = bidi.runs[runx].insertRemove;
               if (markFlag < 0) {
                  markFlag = 0;
               }

               if (bidiRun.isEvenRun()) {
                  if (bidi.isInverse() && dirProps[bidiRun.start] != 0) {
                     markFlag |= 1;
                  }

                  char uc;
                  if ((markFlag & 1) != 0) {
                     uc = '\u200e';
                  } else if ((markFlag & 4) != 0) {
                     uc = '\u200f';
                  } else {
                     uc = '\u0000';
                  }

                  if (uc != 0) {
                     dest.append(uc);
                  }

                  dest.append(doWriteForward(text, bidiRun.start, bidiRun.limit, options & -3));
                  if (bidi.isInverse() && dirProps[bidiRun.limit - 1] != 0) {
                     markFlag |= 2;
                  }

                  if ((markFlag & 2) != 0) {
                     uc = '\u200e';
                  } else if ((markFlag & 8) != 0) {
                     uc = '\u200f';
                  } else {
                     uc = '\u0000';
                  }

                  if (uc != 0) {
                     dest.append(uc);
                  }
               } else {
                  if (bidi.isInverse() && !bidi.testDirPropFlagAt(8194, bidiRun.limit - 1)) {
                     markFlag |= 4;
                  }

                  char ucx;
                  if ((markFlag & 1) != 0) {
                     ucx = '\u200e';
                  } else if ((markFlag & 4) != 0) {
                     ucx = '\u200f';
                  } else {
                     ucx = '\u0000';
                  }

                  if (ucx != 0) {
                     dest.append(ucx);
                  }

                  dest.append(doWriteReverse(text, bidiRun.start, bidiRun.limit, options));
                  if (bidi.isInverse() && (8194 & Bidi.DirPropFlag(dirProps[bidiRun.start])) == 0) {
                     markFlag |= 8;
                  }

                  if ((markFlag & 2) != 0) {
                     ucx = '\u200e';
                  } else if ((markFlag & 8) != 0) {
                     ucx = '\u200f';
                  } else {
                     ucx = '\u0000';
                  }

                  if (ucx != 0) {
                     dest.append(ucx);
                  }
               }
            }
         }
      } else if ((options & 4) == 0) {
         int runx = runCount;

         while (--runx >= 0) {
            BidiRun bidiRunx = bidi.getVisualRun(runx);
            if (bidiRunx.isEvenRun()) {
               dest.append(doWriteReverse(text, bidiRunx.start, bidiRunx.limit, options & -3));
            } else {
               dest.append(doWriteForward(text, bidiRunx.start, bidiRunx.limit, options));
            }
         }
      } else {
         byte[] dirProps = bidi.dirProps;
         int runx = runCount;

         while (--runx >= 0) {
            BidiRun bidiRunx = bidi.getVisualRun(runx);
            if (bidiRunx.isEvenRun()) {
               if (dirProps[bidiRunx.limit - 1] != 0) {
                  dest.append('\u200e');
               }

               dest.append(doWriteReverse(text, bidiRunx.start, bidiRunx.limit, options & -3));
               if (dirProps[bidiRunx.start] != 0) {
                  dest.append('\u200e');
               }
            } else {
               if ((8194 & Bidi.DirPropFlag(dirProps[bidiRunx.start])) == 0) {
                  dest.append('\u200f');
               }

               dest.append(doWriteForward(text, bidiRunx.start, bidiRunx.limit, options));
               if ((8194 & Bidi.DirPropFlag(dirProps[bidiRunx.limit - 1])) == 0) {
                  dest.append('\u200f');
               }
            }
         }
      }

      return dest.toString();
   }
}
