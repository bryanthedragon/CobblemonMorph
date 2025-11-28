package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;

class UnescapeTransliterator extends Transliterator {
   private char[] spec;
   private static final char END = '\uffff';

   static void register() {
      Transliterator.registerFactory("Hex-Any/Unicode", new Transliterator.Factory() {
         @Override
         public Transliterator getInstance(String ID) {
            return new UnescapeTransliterator("Hex-Any/Unicode", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0006', 'U', '+', '\uffff'});
         }
      });
      Transliterator.registerFactory("Hex-Any/Java", new Transliterator.Factory() {
         @Override
         public Transliterator getInstance(String ID) {
            return new UnescapeTransliterator("Hex-Any/Java", new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\uffff'});
         }
      });
      Transliterator.registerFactory(
         "Hex-Any/C",
         new Transliterator.Factory() {
            @Override
            public Transliterator getInstance(String ID) {
               return new UnescapeTransliterator(
                  "Hex-Any/C",
                  new char[]{'\u0002', '\u0000', '\u0010', '\u0004', '\u0004', '\\', 'u', '\u0002', '\u0000', '\u0010', '\b', '\b', '\\', 'U', '\uffff'}
               );
            }
         }
      );
      Transliterator.registerFactory("Hex-Any/XML", new Transliterator.Factory() {
         @Override
         public Transliterator getInstance(String ID) {
            return new UnescapeTransliterator("Hex-Any/XML", new char[]{'\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '&', '#', 'x', ';', '\uffff'});
         }
      });
      Transliterator.registerFactory("Hex-Any/XML10", new Transliterator.Factory() {
         @Override
         public Transliterator getInstance(String ID) {
            return new UnescapeTransliterator("Hex-Any/XML10", new char[]{'\u0002', '\u0001', '\n', '\u0001', '\u0007', '&', '#', ';', '\uffff'});
         }
      });
      Transliterator.registerFactory("Hex-Any/Perl", new Transliterator.Factory() {
         @Override
         public Transliterator getInstance(String ID) {
            return new UnescapeTransliterator("Hex-Any/Perl", new char[]{'\u0003', '\u0001', '\u0010', '\u0001', '\u0006', '\\', 'x', '{', '}', '\uffff'});
         }
      });
      Transliterator.registerFactory(
         "Hex-Any",
         new Transliterator.Factory() {
            @Override
            public Transliterator getInstance(String ID) {
               return new UnescapeTransliterator(
                  "Hex-Any",
                  new char[]{
                     '\u0002',
                     '\u0000',
                     '\u0010',
                     '\u0004',
                     '\u0006',
                     'U',
                     '+',
                     '\u0002',
                     '\u0000',
                     '\u0010',
                     '\u0004',
                     '\u0004',
                     '\\',
                     'u',
                     '\u0002',
                     '\u0000',
                     '\u0010',
                     '\b',
                     '\b',
                     '\\',
                     'U',
                     '\u0003',
                     '\u0001',
                     '\u0010',
                     '\u0001',
                     '\u0006',
                     '&',
                     '#',
                     'x',
                     ';',
                     '\u0002',
                     '\u0001',
                     '\n',
                     '\u0001',
                     '\u0007',
                     '&',
                     '#',
                     ';',
                     '\u0003',
                     '\u0001',
                     '\u0010',
                     '\u0001',
                     '\u0006',
                     '\\',
                     'x',
                     '{',
                     '}',
                     '\uffff'
                  }
               );
            }
         }
      );
   }

   UnescapeTransliterator(String ID, char[] spec) {
      super(ID, null);
      this.spec = spec;
   }

   @Override
   protected void handleTransliterate(Replaceable text, Transliterator.Position pos, boolean isIncremental) {
      int start = pos.start;
      int limit = pos.limit;

      label104:
      while (start < limit) {
         int ipat = 0;

         while (this.spec[ipat] != '\uffff') {
            int prefixLen = this.spec[ipat++];
            int suffixLen = this.spec[ipat++];
            int radix = this.spec[ipat++];
            int minDigits = this.spec[ipat++];
            int maxDigits = this.spec[ipat++];
            int s = start;
            boolean match = true;

            for (int i = 0; i < prefixLen; i++) {
               if (s >= limit && i > 0) {
                  if (isIncremental) {
                     break label104;
                  }

                  match = false;
                  break;
               }

               char c = text.charAt(s++);
               if (c != this.spec[ipat + i]) {
                  match = false;
                  break;
               }
            }

            if (match) {
               int u = 0;
               int digitCount = 0;

               do {
                  if (s >= limit) {
                     if (s > start && isIncremental) {
                        break label104;
                     }
                     break;
                  }

                  int ch = text.char32At(s);
                  int digit = UCharacter.digit(ch, radix);
                  if (digit < 0) {
                     break;
                  }

                  s += UTF16.getCharCount(ch);
                  u = u * radix + digit;
               } while (++digitCount != maxDigits);

               match = digitCount >= minDigits;
               if (match) {
                  for (int var19 = 0; var19 < suffixLen; var19++) {
                     if (s >= limit) {
                        if (s > start && isIncremental) {
                           break label104;
                        }

                        match = false;
                        break;
                     }

                     char c = text.charAt(s++);
                     if (c != this.spec[ipat + prefixLen + var19]) {
                        match = false;
                        break;
                     }
                  }

                  if (match) {
                     String str = UTF16.valueOf(u);
                     text.replace(start, s, str);
                     limit -= s - start - str.length();
                     if (start < limit) {
                        start += UTF16.getCharCount(text.char32At(start));
                     }
                     continue label104;
                  }
               }
            }

            ipat += prefixLen + suffixLen;
         }
         break;
      }

      pos.contextLimit = pos.contextLimit + (limit - pos.limit);
      pos.limit = limit;
      pos.start = start;
   }

   @Override
   public void addSourceTargetSet(UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      UnicodeSet myFilter = this.getFilterAsUnicodeSet(inputFilter);
      UnicodeSet items = new UnicodeSet();
      StringBuilder buffer = new StringBuilder();
      int i = 0;

      while (this.spec[i] != '\uffff') {
         int end = i + this.spec[i] + this.spec[i + 1] + 5;
         int radix = this.spec[i + 2];

         for (int j = 0; j < radix; j++) {
            Utility.appendNumber(buffer, j, radix, 0);
         }

         for (int j = i + 5; j < end; j++) {
            items.add(this.spec[j]);
         }

         i = end;
      }

      items.addAll(buffer.toString());
      items.retainAll(myFilter);
      if (items.size() > 0) {
         sourceSet.addAll(items);
         targetSet.addAll(0, 1114111);
      }
   }
}
