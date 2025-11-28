package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;

class StringReplacer implements UnicodeReplacer {
   private String output;
   private int cursorPos;
   private boolean hasCursor;
   private boolean isComplex;
   private final RuleBasedTransliterator.Data data;

   public StringReplacer(String theOutput, int theCursorPos, RuleBasedTransliterator.Data theData) {
      this.output = theOutput;
      this.cursorPos = theCursorPos;
      this.hasCursor = true;
      this.data = theData;
      this.isComplex = true;
   }

   public StringReplacer(String theOutput, RuleBasedTransliterator.Data theData) {
      this.output = theOutput;
      this.cursorPos = 0;
      this.hasCursor = false;
      this.data = theData;
      this.isComplex = true;
   }

   @Override
   public int replace(Replaceable text, int start, int limit, int[] cursor) {
      int newStart = 0;
      int outLen;
      if (!this.isComplex) {
         text.replace(start, limit, this.output);
         outLen = this.output.length();
         newStart = this.cursorPos;
      } else {
         StringBuffer buf = new StringBuffer();
         this.isComplex = false;
         int tempStart = text.length();
         int destStart;
         if (start > 0) {
            int len = UTF16.getCharCount(text.char32At(start - 1));
            text.copy(start - len, start, tempStart);
            destStart = tempStart + len;
         } else {
            text.replace(tempStart, tempStart, "\uffff");
            destStart = tempStart + 1;
         }

         int destLimit = destStart;
         int tempExtra = 0;
         int oOutput = 0;

         while (oOutput < this.output.length()) {
            if (oOutput == this.cursorPos) {
               newStart = buf.length() + destLimit - destStart;
            }

            int c = UTF16.charAt(this.output, oOutput);
            int nextIndex = oOutput + UTF16.getCharCount(c);
            if (nextIndex == this.output.length()) {
               tempExtra = UTF16.getCharCount(text.char32At(limit));
               text.copy(limit, limit + tempExtra, destLimit);
            }

            UnicodeReplacer r = this.data.lookupReplacer(c);
            if (r == null) {
               UTF16.append(buf, c);
            } else {
               this.isComplex = true;
               if (buf.length() > 0) {
                  text.replace(destLimit, destLimit, buf.toString());
                  destLimit += buf.length();
                  buf.setLength(0);
               }

               int len = r.replace(text, destLimit, destLimit, cursor);
               destLimit += len;
            }

            oOutput = nextIndex;
         }

         if (buf.length() > 0) {
            text.replace(destLimit, destLimit, buf.toString());
            destLimit += buf.length();
         }

         if (oOutput == this.cursorPos) {
            newStart = destLimit - destStart;
         }

         outLen = destLimit - destStart;
         text.copy(destStart, destLimit, start);
         text.replace(tempStart + outLen, destLimit + tempExtra + outLen, "");
         text.replace(start + outLen, limit + outLen, "");
      }

      if (this.hasCursor) {
         if (this.cursorPos < 0) {
            newStart = start;

            int n;
            for (n = this.cursorPos; n < 0 && newStart > 0; n++) {
               newStart -= UTF16.getCharCount(text.char32At(newStart - 1));
            }

            newStart += n;
         } else if (this.cursorPos <= this.output.length()) {
            newStart += start;
         } else {
            newStart = start + outLen;

            int n;
            for (n = this.cursorPos - this.output.length(); n > 0 && newStart < text.length(); n--) {
               newStart += UTF16.getCharCount(text.char32At(newStart));
            }

            newStart += n;
         }

         cursor[0] = newStart;
      }

      return outLen;
   }

   @Override
   public String toReplacerPattern(boolean escapeUnprintable) {
      StringBuffer rule = new StringBuffer();
      StringBuffer quoteBuf = new StringBuffer();
      int cursor = this.cursorPos;
      if (this.hasCursor && cursor < 0) {
         while (cursor++ < 0) {
            Utility.appendToRule(rule, 64, true, escapeUnprintable, quoteBuf);
         }
      }

      for (int i = 0; i < this.output.length(); i++) {
         if (this.hasCursor && i == cursor) {
            Utility.appendToRule(rule, 124, true, escapeUnprintable, quoteBuf);
         }

         char c = this.output.charAt(i);
         UnicodeReplacer r = this.data.lookupReplacer(c);
         if (r == null) {
            Utility.appendToRule(rule, c, false, escapeUnprintable, quoteBuf);
         } else {
            StringBuffer buf = new StringBuffer(" ");
            buf.append(r.toReplacerPattern(escapeUnprintable));
            buf.append(' ');
            Utility.appendToRule(rule, buf.toString(), true, escapeUnprintable, quoteBuf);
         }
      }

      if (this.hasCursor && cursor > this.output.length()) {
         cursor -= this.output.length();

         while (cursor-- > 0) {
            Utility.appendToRule(rule, 64, true, escapeUnprintable, quoteBuf);
         }

         Utility.appendToRule(rule, 124, true, escapeUnprintable, quoteBuf);
      }

      Utility.appendToRule(rule, -1, true, escapeUnprintable, quoteBuf);
      return rule.toString();
   }

   @Override
   public void addReplacementSetTo(UnicodeSet toUnionTo) {
      int i = 0;

      while (i < this.output.length()) {
         int ch = UTF16.charAt(this.output, i);
         UnicodeReplacer r = this.data.lookupReplacer(ch);
         if (r == null) {
            toUnionTo.add(ch);
         } else {
            r.addReplacementSetTo(toUnionTo);
         }

         i += UTF16.getCharCount(ch);
      }
   }
}
