package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.text.BreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.text.Edits;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUUncheckedIOException;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.IOException;
import java.text.CharacterIterator;
import java.util.Locale;

public final class CaseMapImpl {
   public static final int TITLECASE_WHOLE_STRING = 32;
   public static final int TITLECASE_SENTENCES = 64;
   private static final int TITLECASE_ITERATOR_MASK = 224;
   public static final int TITLECASE_ADJUST_TO_CASED = 1024;
   private static final int TITLECASE_ADJUSTMENT_MASK = 1536;
   private static final char ACUTE = '́';
   private static final int U_GC_M_MASK = 448;
   private static final int LNS = 251792942;
   public static final int OMIT_UNCHANGED_TEXT = 16384;
   private static final Trie2_16 CASE_TRIE = UCaseProps.getTrie();

   public static int addTitleAdjustmentOption(int options, int newOption) {
      int adjOptions = options & 1536;
      if (adjOptions != 0 && adjOptions != newOption) {
         throw new IllegalArgumentException("multiple titlecasing index adjustment options");
      } else {
         return options | newOption;
      }
   }

   private static boolean isLNS(int c) {
      int gc = UCharacterProperty.INSTANCE.getType(c);
      return (1 << gc & 251792942) != 0 || gc == 4 && UCaseProps.INSTANCE.getType(c) != 0;
   }

   public static int addTitleIteratorOption(int options, int newOption) {
      int iterOptions = options & 224;
      if (iterOptions != 0 && iterOptions != newOption) {
         throw new IllegalArgumentException("multiple titlecasing iterator options");
      } else {
         return options | newOption;
      }
   }

   public static BreakIterator getTitleBreakIterator(Locale locale, int options, BreakIterator iter) {
      options &= 224;
      if (options != 0 && iter != null) {
         throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
      } else {
         if (iter == null) {
            switch (options) {
               case 0:
                  iter = BreakIterator.getWordInstance(locale);
                  break;
               case 32:
                  iter = new CaseMapImpl.WholeStringBreakIterator();
                  break;
               case 64:
                  iter = BreakIterator.getSentenceInstance(locale);
                  break;
               default:
                  throw new IllegalArgumentException("unknown titlecasing iterator option");
            }
         }

         return iter;
      }
   }

   public static BreakIterator getTitleBreakIterator(ULocale locale, int options, BreakIterator iter) {
      options &= 224;
      if (options != 0 && iter != null) {
         throw new IllegalArgumentException("titlecasing iterator option together with an explicit iterator");
      } else {
         if (iter == null) {
            switch (options) {
               case 0:
                  iter = BreakIterator.getWordInstance(locale);
                  break;
               case 32:
                  iter = new CaseMapImpl.WholeStringBreakIterator();
                  break;
               case 64:
                  iter = BreakIterator.getSentenceInstance(locale);
                  break;
               default:
                  throw new IllegalArgumentException("unknown titlecasing iterator option");
            }
         }

         return iter;
      }
   }

   private static int appendCodePoint(Appendable a, int c) throws IOException {
      if (c <= 65535) {
         a.append((char)c);
         return 1;
      } else {
         a.append((char)(55232 + (c >> 10)));
         a.append((char)(56320 + (c & 1023)));
         return 2;
      }
   }

   private static void appendResult(int result, Appendable dest, int cpLength, int options, Edits edits) throws IOException {
      if (result < 0) {
         if (edits != null) {
            edits.addUnchanged(cpLength);
         }

         if ((options & 16384) != 0) {
            return;
         }

         appendCodePoint(dest, ~result);
      } else if (result <= 31) {
         if (edits != null) {
            edits.addReplace(cpLength, result);
         }
      } else {
         int length = appendCodePoint(dest, result);
         if (edits != null) {
            edits.addReplace(cpLength, length);
         }
      }
   }

   private static final void appendUnchanged(CharSequence src, int start, int length, Appendable dest, int options, Edits edits) throws IOException {
      if (length > 0) {
         if (edits != null) {
            edits.addUnchanged(length);
         }

         if ((options & 16384) != 0) {
            return;
         }

         dest.append(src, start, start + length);
      }
   }

   private static String applyEdits(CharSequence src, StringBuilder replacementChars, Edits edits) {
      if (!edits.hasChanges()) {
         return src.toString();
      } else {
         StringBuilder result = new StringBuilder(src.length() + edits.lengthDelta());
         Edits.Iterator ei = edits.getCoarseIterator();

         while (ei.next()) {
            if (ei.hasChange()) {
               int i = ei.replacementIndex();
               result.append(replacementChars, i, i + ei.newLength());
            } else {
               int i = ei.sourceIndex();
               result.append(src, i, i + ei.oldLength());
            }
         }

         return result.toString();
      }
   }

   private static void internalToLower(
      int caseLocale, int options, CharSequence src, int srcStart, int srcLimit, CaseMapImpl.StringContextIterator iter, Appendable dest, Edits edits
   ) throws IOException {
      byte[] latinToLower;
      if (caseLocale != 1 && (caseLocale >= 0 ? caseLocale == 2 || caseLocale == 3 : (options & 7) != 0)) {
         latinToLower = UCaseProps.LatinCase.TO_LOWER_TR_LT;
      } else {
         latinToLower = UCaseProps.LatinCase.TO_LOWER_NORMAL;
      }

      int prev = srcStart;
      int srcIndex = srcStart;

      while (srcIndex < srcLimit) {
         char lead;
         int delta;
         label94: {
            lead = src.charAt(srcIndex);
            if (lead < 383) {
               byte d = latinToLower[lead];
               if (d != -128) {
                  srcIndex++;
                  if (d == 0) {
                     continue;
                  }

                  delta = d;
                  break label94;
               }
            } else if (lead < '\ud800') {
               int props = CASE_TRIE.getFromU16SingleLead(lead);
               if (!UCaseProps.propsHasException(props)) {
                  srcIndex++;
                  if (!UCaseProps.isUpperOrTitleFromProps(props) || (delta = UCaseProps.getDelta(props)) == 0) {
                     continue;
                  }
                  break label94;
               }
            }

            delta = srcIndex++;
            int c;
            char trail;
            if (Character.isHighSurrogate(lead) && srcIndex < srcLimit && Character.isLowSurrogate(trail = src.charAt(srcIndex))) {
               c = Character.toCodePoint(lead, trail);
               srcIndex++;
            } else {
               c = lead;
            }

            appendUnchanged(src, prev, delta - prev, dest, options, edits);
            prev = delta;
            if (caseLocale >= 0) {
               if (iter == null) {
                  iter = new CaseMapImpl.StringContextIterator(src, delta, srcIndex);
               } else {
                  iter.setCPStartAndLimit(delta, srcIndex);
               }

               c = UCaseProps.INSTANCE.toFullLower(c, iter, dest, caseLocale);
            } else {
               c = UCaseProps.INSTANCE.toFullFolding(c, dest, options);
            }

            if (c >= 0) {
               appendResult(c, dest, srcIndex - delta, options, edits);
               prev = srcIndex;
            }
            continue;
         }

         lead = (char)(lead + delta);
         appendUnchanged(src, prev, srcIndex - 1 - prev, dest, options, edits);
         dest.append(lead);
         if (edits != null) {
            edits.addReplace(1, 1);
         }

         prev = srcIndex;
      }

      appendUnchanged(src, prev, srcIndex - prev, dest, options, edits);
   }

   private static void internalToUpper(int caseLocale, int options, CharSequence src, Appendable dest, Edits edits) throws IOException {
      CaseMapImpl.StringContextIterator iter = null;
      byte[] latinToUpper;
      if (caseLocale == 2) {
         latinToUpper = UCaseProps.LatinCase.TO_UPPER_TR;
      } else {
         latinToUpper = UCaseProps.LatinCase.TO_UPPER_NORMAL;
      }

      int prev = 0;
      int srcIndex = 0;
      int srcLength = src.length();

      while (srcIndex < srcLength) {
         char lead;
         int delta;
         label71: {
            lead = src.charAt(srcIndex);
            if (lead < 383) {
               byte d = latinToUpper[lead];
               if (d != -128) {
                  srcIndex++;
                  if (d == 0) {
                     continue;
                  }

                  delta = d;
                  break label71;
               }
            } else if (lead < '\ud800') {
               int props = CASE_TRIE.getFromU16SingleLead(lead);
               if (!UCaseProps.propsHasException(props)) {
                  srcIndex++;
                  if (UCaseProps.getTypeFromProps(props) != 1 || (delta = UCaseProps.getDelta(props)) == 0) {
                     continue;
                  }
                  break label71;
               }
            }

            delta = srcIndex++;
            int c;
            char trail;
            if (Character.isHighSurrogate(lead) && srcIndex < srcLength && Character.isLowSurrogate(trail = src.charAt(srcIndex))) {
               c = Character.toCodePoint(lead, trail);
               srcIndex++;
            } else {
               c = lead;
            }

            if (iter == null) {
               iter = new CaseMapImpl.StringContextIterator(src, delta, srcIndex);
            } else {
               iter.setCPStartAndLimit(delta, srcIndex);
            }

            appendUnchanged(src, prev, delta - prev, dest, options, edits);
            prev = delta;
            c = UCaseProps.INSTANCE.toFullUpper(c, iter, dest, caseLocale);
            if (c >= 0) {
               appendResult(c, dest, srcIndex - delta, options, edits);
               prev = srcIndex;
            }
            continue;
         }

         lead = (char)(lead + delta);
         appendUnchanged(src, prev, srcIndex - 1 - prev, dest, options, edits);
         dest.append(lead);
         if (edits != null) {
            edits.addReplace(1, 1);
         }

         prev = srcIndex;
      }

      appendUnchanged(src, prev, srcIndex - prev, dest, options, edits);
   }

   public static String toLower(int caseLocale, int options, CharSequence src) {
      if (src.length() > 100 || (options & 16384) != 0) {
         return toLower(caseLocale, options, src, new StringBuilder(src.length()), null).toString();
      } else if (src.length() == 0) {
         return src.toString();
      } else {
         Edits edits = new Edits();
         StringBuilder replacementChars = toLower(caseLocale, options | 16384, src, new StringBuilder(), edits);
         return applyEdits(src, replacementChars, edits);
      }
   }

   public static <A extends Appendable> A toLower(int caseLocale, int options, CharSequence src, A dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         internalToLower(caseLocale, options, src, 0, src.length(), null, dest, edits);
         return dest;
      } catch (IOException var6) {
         throw new ICUUncheckedIOException(var6);
      }
   }

   public static String toUpper(int caseLocale, int options, CharSequence src) {
      if (src.length() > 100 || (options & 16384) != 0) {
         return toUpper(caseLocale, options, src, new StringBuilder(src.length()), null).toString();
      } else if (src.length() == 0) {
         return src.toString();
      } else {
         Edits edits = new Edits();
         StringBuilder replacementChars = toUpper(caseLocale, options | 16384, src, new StringBuilder(), edits);
         return applyEdits(src, replacementChars, edits);
      }
   }

   public static <A extends Appendable> A toUpper(int caseLocale, int options, CharSequence src, A dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         if (caseLocale == 4) {
            return CaseMapImpl.GreekUpper.toUpper(options, src, dest, edits);
         } else {
            internalToUpper(caseLocale, options, src, dest, edits);
            return dest;
         }
      } catch (IOException var6) {
         throw new ICUUncheckedIOException(var6);
      }
   }

   public static String toTitle(int caseLocale, int options, BreakIterator iter, CharSequence src) {
      if (src.length() > 100 || (options & 16384) != 0) {
         return toTitle(caseLocale, options, iter, src, new StringBuilder(src.length()), null).toString();
      } else if (src.length() == 0) {
         return src.toString();
      } else {
         Edits edits = new Edits();
         StringBuilder replacementChars = toTitle(caseLocale, options | 16384, iter, src, new StringBuilder(), edits);
         return applyEdits(src, replacementChars, edits);
      }
   }

   public static <A extends Appendable> A toTitle(int caseLocale, int options, BreakIterator titleIter, CharSequence src, A dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         CaseMapImpl.StringContextIterator iter = new CaseMapImpl.StringContextIterator(src);
         int srcLength = src.length();
         int prev = 0;
         boolean isFirstIndex = true;

         while (prev < srcLength) {
            int index;
            if (isFirstIndex) {
               isFirstIndex = false;
               index = titleIter.first();
            } else {
               index = titleIter.next();
            }

            if (index == -1 || index > srcLength) {
               index = srcLength;
            }

            if (prev < index) {
               int titleStart = prev;
               iter.setLimit(index);
               int c = iter.nextCaseMapCP();
               if ((options & 512) == 0) {
                  boolean toCased = (options & 1024) != 0;

                  while ((toCased ? 0 == UCaseProps.INSTANCE.getType(c) : !isLNS(c)) && (c = iter.nextCaseMapCP()) >= 0) {
                  }

                  titleStart = iter.getCPStart();
                  if (prev < titleStart) {
                     appendUnchanged(src, prev, titleStart - prev, dest, options, edits);
                  }
               }

               if (titleStart < index) {
                  c = UCaseProps.INSTANCE.toFullTitle(c, iter, dest, caseLocale);
                  appendResult(c, dest, iter.getCPLength(), options, edits);
                  int titleLimit;
                  if (titleStart + 1 < index && caseLocale == 5) {
                     if (c < 0) {
                        c = ~c;
                     }

                     if (c != 73 && c != 205) {
                        titleLimit = iter.getCPLimit();
                     } else {
                        titleLimit = maybeTitleDutchIJ(src, c, titleStart + 1, index, dest, options, edits);
                        iter.moveTo(titleLimit);
                     }
                  } else {
                     titleLimit = iter.getCPLimit();
                  }

                  if (titleLimit < index) {
                     if ((options & 256) == 0) {
                        internalToLower(caseLocale, options, src, titleLimit, index, iter, dest, edits);
                     } else {
                        appendUnchanged(src, titleLimit, index - titleLimit, dest, options, edits);
                     }

                     iter.moveToLimit();
                  }
               }
            }

            prev = index;
         }

         return dest;
      } catch (IOException var14) {
         throw new ICUUncheckedIOException(var14);
      }
   }

   private static <A extends Appendable> int maybeTitleDutchIJ(CharSequence src, int c, int start, int segmentLimit, A dest, int options, Edits edits) throws IOException {
      assert start < segmentLimit;

      boolean withAcute = false;
      int unchanged1 = 0;
      boolean doTitleJ = false;
      int unchanged2 = 0;
      int index = start + 1;
      char c2 = src.charAt(start);
      if (c == 73) {
         if (c2 == 769) {
            withAcute = true;
            unchanged1 = 1;
            if (index == segmentLimit) {
               return start;
            }

            c2 = src.charAt(index++);
         }
      } else {
         withAcute = true;
      }

      if (c2 == 'j') {
         doTitleJ = true;
      } else {
         if (c2 != 'J') {
            return start;
         }

         unchanged1++;
      }

      if (withAcute) {
         if (index == segmentLimit || src.charAt(index++) != 769) {
            return start;
         }

         if (doTitleJ) {
            unchanged2 = 1;
         } else {
            unchanged1++;
         }
      }

      if (index < segmentLimit) {
         int cp = Character.codePointAt(src, index);
         int bit = 1 << UCharacter.getType(cp);
         if ((bit & 448) != 0) {
            return start;
         }
      }

      appendUnchanged(src, start, unchanged1, dest, options, edits);
      start += unchanged1;
      if (doTitleJ) {
         dest.append('J');
         if (edits != null) {
            edits.addReplace(1, 1);
         }

         start++;
      }

      appendUnchanged(src, start, unchanged2, dest, options, edits);

      assert start + unchanged2 == index;

      return index;
   }

   public static String fold(int options, CharSequence src) {
      if (src.length() > 100 || (options & 16384) != 0) {
         return fold(options, src, new StringBuilder(src.length()), null).toString();
      } else if (src.length() == 0) {
         return src.toString();
      } else {
         Edits edits = new Edits();
         StringBuilder replacementChars = fold(options | 16384, src, new StringBuilder(), edits);
         return applyEdits(src, replacementChars, edits);
      }
   }

   public static <A extends Appendable> A fold(int options, CharSequence src, A dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         internalToLower(-1, options, src, 0, src.length(), null, dest, edits);
         return dest;
      } catch (IOException var5) {
         throw new ICUUncheckedIOException(var5);
      }
   }

   private static final class GreekUpper {
      private static final int UPPER_MASK = 1023;
      private static final int HAS_VOWEL = 4096;
      private static final int HAS_YPOGEGRAMMENI = 8192;
      private static final int HAS_ACCENT = 16384;
      private static final int HAS_DIALYTIKA = 32768;
      private static final int HAS_COMBINING_DIALYTIKA = 65536;
      private static final int HAS_OTHER_GREEK_DIACRITIC = 131072;
      private static final int HAS_VOWEL_AND_ACCENT = 20480;
      private static final int HAS_VOWEL_AND_ACCENT_AND_DIALYTIKA = 53248;
      private static final int HAS_EITHER_DIALYTIKA = 98304;
      private static final int AFTER_CASED = 1;
      private static final int AFTER_VOWEL_WITH_ACCENT = 2;
      private static final char[] data0370 = new char[]{
         'Ͱ',
         'Ͱ',
         'Ͳ',
         'Ͳ',
         '\u0000',
         '\u0000',
         'Ͷ',
         'Ͷ',
         '\u0000',
         '\u0000',
         'ͺ',
         'Ͻ',
         'Ͼ',
         'Ͽ',
         '\u0000',
         'Ϳ',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '厑',
         '\u0000',
         '厕',
         '厗',
         '厙',
         '\u0000',
         '原',
         '\u0000',
         '厥',
         '厩',
         '펙',
         '᎑',
         'Β',
         'Γ',
         'Δ',
         '᎕',
         'Ζ',
         '᎗',
         'Θ',
         '᎙',
         'Κ',
         'Λ',
         'Μ',
         'Ν',
         'Ξ',
         '\u139f',
         'Π',
         'Ρ',
         '\u0000',
         'Σ',
         'Τ',
         'Ꭵ',
         'Φ',
         'Χ',
         'Ψ',
         'Ꭹ',
         '鎙',
         '鎥',
         '厑',
         '厕',
         '厗',
         '厙',
         '펥',
         '᎑',
         'Β',
         'Γ',
         'Δ',
         '᎕',
         'Ζ',
         '᎗',
         'Θ',
         '᎙',
         'Κ',
         'Λ',
         'Μ',
         'Ν',
         'Ξ',
         '\u139f',
         'Π',
         'Ρ',
         'Σ',
         'Σ',
         'Τ',
         'Ꭵ',
         'Φ',
         'Χ',
         'Ψ',
         'Ꭹ',
         '鎙',
         '鎥',
         '原',
         '厥',
         '厩',
         'Ϗ',
         'Β',
         'Θ',
         'ϒ',
         '䏒',
         '菒',
         'Φ',
         'Π',
         'Ϗ',
         'Ϙ',
         'Ϙ',
         'Ϛ',
         'Ϛ',
         'Ϝ',
         'Ϝ',
         'Ϟ',
         'Ϟ',
         'Ϡ',
         'Ϡ',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         'Κ',
         'Ρ',
         'Ϲ',
         'Ϳ',
         'ϴ',
         '᎕',
         '\u0000',
         'Ϸ',
         'Ϸ',
         'Ϲ',
         'Ϻ',
         'Ϻ',
         'ϼ',
         'Ͻ',
         'Ͼ',
         'Ͽ'
      };
      private static final char[] data1F00 = new char[]{
         '᎑',
         '᎑',
         '厑',
         '厑',
         '厑',
         '厑',
         '厑',
         '厑',
         '᎑',
         '᎑',
         '厑',
         '厑',
         '厑',
         '厑',
         '厑',
         '厑',
         '᎕',
         '᎕',
         '厕',
         '厕',
         '厕',
         '厕',
         '\u0000',
         '\u0000',
         '᎕',
         '᎕',
         '厕',
         '厕',
         '厕',
         '厕',
         '\u0000',
         '\u0000',
         '᎗',
         '᎗',
         '厗',
         '厗',
         '厗',
         '厗',
         '厗',
         '厗',
         '᎗',
         '᎗',
         '厗',
         '厗',
         '厗',
         '厗',
         '厗',
         '厗',
         '᎙',
         '᎙',
         '厙',
         '厙',
         '厙',
         '厙',
         '厙',
         '厙',
         '᎙',
         '᎙',
         '厙',
         '厙',
         '厙',
         '厙',
         '厙',
         '厙',
         '\u139f',
         '\u139f',
         '原',
         '原',
         '原',
         '原',
         '\u0000',
         '\u0000',
         '\u139f',
         '\u139f',
         '原',
         '原',
         '原',
         '原',
         '\u0000',
         '\u0000',
         'Ꭵ',
         'Ꭵ',
         '厥',
         '厥',
         '厥',
         '厥',
         '厥',
         '厥',
         '\u0000',
         'Ꭵ',
         '\u0000',
         '厥',
         '\u0000',
         '厥',
         '\u0000',
         '厥',
         'Ꭹ',
         'Ꭹ',
         '厩',
         '厩',
         '厩',
         '厩',
         '厩',
         '厩',
         'Ꭹ',
         'Ꭹ',
         '厩',
         '厩',
         '厩',
         '厩',
         '厩',
         '厩',
         '厑',
         '厑',
         '厕',
         '厕',
         '厗',
         '厗',
         '厙',
         '厙',
         '原',
         '原',
         '厥',
         '厥',
         '厩',
         '厩',
         '\u0000',
         '\u0000',
         '㎑',
         '㎑',
         '玑',
         '玑',
         '玑',
         '玑',
         '玑',
         '玑',
         '㎑',
         '㎑',
         '玑',
         '玑',
         '玑',
         '玑',
         '玑',
         '玑',
         '㎗',
         '㎗',
         '玗',
         '玗',
         '玗',
         '玗',
         '玗',
         '玗',
         '㎗',
         '㎗',
         '玗',
         '玗',
         '玗',
         '玗',
         '玗',
         '玗',
         '㎩',
         '㎩',
         '玩',
         '玩',
         '玩',
         '玩',
         '玩',
         '玩',
         '㎩',
         '㎩',
         '玩',
         '玩',
         '玩',
         '玩',
         '玩',
         '玩',
         '᎑',
         '᎑',
         '玑',
         '㎑',
         '玑',
         '\u0000',
         '厑',
         '玑',
         '᎑',
         '᎑',
         '厑',
         '厑',
         '㎑',
         '\u0000',
         '᎙',
         '\u0000',
         '\u0000',
         '\u0000',
         '玗',
         '㎗',
         '玗',
         '\u0000',
         '厗',
         '玗',
         '厕',
         '厕',
         '厗',
         '厗',
         '㎗',
         '\u0000',
         '\u0000',
         '\u0000',
         '᎙',
         '᎙',
         '펙',
         '펙',
         '\u0000',
         '\u0000',
         '厙',
         '펙',
         '᎙',
         '᎙',
         '厙',
         '厙',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         'Ꭵ',
         'Ꭵ',
         '펥',
         '펥',
         'Ρ',
         'Ρ',
         '厥',
         '펥',
         'Ꭵ',
         'Ꭵ',
         '厥',
         '厥',
         'Ρ',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '\u0000',
         '玩',
         '㎩',
         '玩',
         '\u0000',
         '厩',
         '玩',
         '原',
         '原',
         '厩',
         '厩',
         '㎩',
         '\u0000',
         '\u0000',
         '\u0000'
      };
      private static final char data2126 = 'Ꭹ';

      private static final int getLetterData(int c) {
         if (c >= 880 && 8486 >= c && (1023 >= c || c >= 7936)) {
            if (c <= 1023) {
               return data0370[c - 880];
            } else if (c <= 8191) {
               return data1F00[c - 7936];
            } else {
               return c == 8486 ? 5033 : 0;
            }
         } else {
            return 0;
         }
      }

      private static final int getDiacriticData(int c) {
         switch (c) {
            case 768:
            case 769:
            case 770:
            case 771:
            case 785:
            case 834:
               return 16384;
            case 772:
            case 774:
            case 787:
            case 788:
            case 835:
               return 131072;
            case 776:
               return 65536;
            case 836:
               return 81920;
            case 837:
               return 8192;
            default:
               return 0;
         }
      }

      private static boolean isFollowedByCasedLetter(CharSequence s, int i) {
         while (i < s.length()) {
            int c = Character.codePointAt(s, i);
            int type = UCaseProps.INSTANCE.getTypeOrIgnorable(c);
            if ((type & 4) == 0) {
               if (type != 0) {
                  return true;
               }

               return false;
            }

            i += Character.charCount(c);
         }

         return false;
      }

      private static <A extends Appendable> A toUpper(int options, CharSequence src, A dest, Edits edits) throws IOException {
         int state = 0;
         int i = 0;

         while (i < src.length()) {
            int c = Character.codePointAt(src, i);
            int nextIndex = i + Character.charCount(c);
            int nextState = 0;
            int type = UCaseProps.INSTANCE.getTypeOrIgnorable(c);
            if ((type & 4) != 0) {
               nextState |= state & 1;
            } else if (type != 0) {
               nextState |= 1;
            }

            int data = getLetterData(c);
            if (data <= 0) {
               c = UCaseProps.INSTANCE.toFullUpper(c, null, dest, 4);
               CaseMapImpl.appendResult(c, dest, nextIndex - i, options, edits);
            } else {
               int upper = data & 1023;
               if ((data & 4096) != 0 && (state & 2) != 0 && (upper == 921 || upper == 933)) {
                  data |= 32768;
               }

               int numYpogegrammeni = 0;
               if ((data & 8192) != 0) {
                  numYpogegrammeni = 1;
               }

               for (; nextIndex < src.length(); nextIndex++) {
                  int diacriticData = getDiacriticData(src.charAt(nextIndex));
                  if (diacriticData == 0) {
                     break;
                  }

                  data |= diacriticData;
                  if ((diacriticData & 8192) != 0) {
                     numYpogegrammeni++;
                  }
               }

               if ((data & 53248) == 20480) {
                  nextState |= 2;
               }

               boolean addTonos = false;
               if (upper == 919 && (data & 16384) != 0 && numYpogegrammeni == 0 && (state & 1) == 0 && !isFollowedByCasedLetter(src, nextIndex)) {
                  if (i == nextIndex) {
                     upper = 905;
                  } else {
                     addTonos = true;
                  }
               } else if ((data & 32768) != 0) {
                  if (upper == 921) {
                     upper = 938;
                     data &= -98305;
                  } else if (upper == 933) {
                     upper = 939;
                     data &= -98305;
                  }
               }

               boolean change;
               if (edits == null && (options & 16384) == 0) {
                  change = true;
               } else {
                  change = src.charAt(i) != upper || numYpogegrammeni > 0;
                  int i2 = i + 1;
                  if ((data & 98304) != 0) {
                     change |= i2 >= nextIndex || src.charAt(i2) != 776;
                     i2++;
                  }

                  if (addTonos) {
                     change |= i2 >= nextIndex || src.charAt(i2) != 769;
                     i2++;
                  }

                  int oldLength = nextIndex - i;
                  int newLength = i2 - i + numYpogegrammeni;
                  change |= oldLength != newLength;
                  if (change) {
                     if (edits != null) {
                        edits.addReplace(oldLength, newLength);
                     }
                  } else {
                     if (edits != null) {
                        edits.addUnchanged(oldLength);
                     }

                     change = (options & 16384) == 0;
                  }
               }

               if (change) {
                  dest.append((char)upper);
                  if ((data & 98304) != 0) {
                     dest.append('̈');
                  }

                  if (addTonos) {
                     dest.append('́');
                  }

                  while (numYpogegrammeni > 0) {
                     dest.append('Ι');
                     numYpogegrammeni--;
                  }
               }
            }

            i = nextIndex;
            state = nextState;
         }

         return dest;
      }
   }

   public static final class StringContextIterator implements UCaseProps.ContextIterator {
      protected CharSequence s;
      protected int index;
      protected int limit;
      protected int cpStart;
      protected int cpLimit;
      protected int dir;

      public StringContextIterator(CharSequence src) {
         this.s = src;
         this.limit = src.length();
         this.cpStart = this.cpLimit = this.index = 0;
         this.dir = 0;
      }

      public StringContextIterator(CharSequence src, int cpStart, int cpLimit) {
         this.s = src;
         this.index = 0;
         this.limit = src.length();
         this.cpStart = cpStart;
         this.cpLimit = cpLimit;
         this.dir = 0;
      }

      public void setLimit(int lim) {
         if (0 <= lim && lim <= this.s.length()) {
            this.limit = lim;
         } else {
            this.limit = this.s.length();
         }
      }

      public void moveToLimit() {
         this.cpStart = this.cpLimit = this.limit;
      }

      public void moveTo(int i) {
         this.cpStart = this.cpLimit = i;
      }

      public int nextCaseMapCP() {
         this.cpStart = this.cpLimit;
         if (this.cpLimit < this.limit) {
            int c = Character.codePointAt(this.s, this.cpLimit);
            this.cpLimit = this.cpLimit + Character.charCount(c);
            return c;
         } else {
            return -1;
         }
      }

      public void setCPStartAndLimit(int s, int l) {
         this.cpStart = s;
         this.cpLimit = l;
         this.dir = 0;
      }

      public int getCPStart() {
         return this.cpStart;
      }

      public int getCPLimit() {
         return this.cpLimit;
      }

      public int getCPLength() {
         return this.cpLimit - this.cpStart;
      }

      @Override
      public void reset(int direction) {
         if (direction > 0) {
            this.dir = 1;
            this.index = this.cpLimit;
         } else if (direction < 0) {
            this.dir = -1;
            this.index = this.cpStart;
         } else {
            this.dir = 0;
            this.index = 0;
         }
      }

      @Override
      public int next() {
         if (this.dir > 0 && this.index < this.s.length()) {
            int c = Character.codePointAt(this.s, this.index);
            this.index = this.index + Character.charCount(c);
            return c;
         } else if (this.dir < 0 && this.index > 0) {
            int c = Character.codePointBefore(this.s, this.index);
            this.index = this.index - Character.charCount(c);
            return c;
         } else {
            return -1;
         }
      }
   }

   private static final class WholeStringBreakIterator extends BreakIterator {
      private int length;

      private WholeStringBreakIterator() {
      }

      private static void notImplemented() {
         throw new UnsupportedOperationException("should not occur");
      }

      @Override
      public int first() {
         return 0;
      }

      @Override
      public int last() {
         notImplemented();
         return 0;
      }

      @Override
      public int next(int n) {
         notImplemented();
         return 0;
      }

      @Override
      public int next() {
         return this.length;
      }

      @Override
      public int previous() {
         notImplemented();
         return 0;
      }

      @Override
      public int following(int offset) {
         notImplemented();
         return 0;
      }

      @Override
      public int current() {
         notImplemented();
         return 0;
      }

      @Override
      public CharacterIterator getText() {
         notImplemented();
         return null;
      }

      @Override
      public void setText(CharacterIterator newText) {
         this.length = newText.getEndIndex();
      }

      @Override
      public void setText(CharSequence newText) {
         this.length = newText.length();
      }

      @Override
      public void setText(String newText) {
         this.length = newText.length();
      }
   }
}
