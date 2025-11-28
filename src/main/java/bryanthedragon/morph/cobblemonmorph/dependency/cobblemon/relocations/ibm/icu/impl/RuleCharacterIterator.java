package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.text.SymbolTable;
import com.cobblemon.mod.relocations.ibm.icu.text.UTF16;
import java.text.ParsePosition;

public class RuleCharacterIterator {
   private String text;
   private ParsePosition pos;
   private SymbolTable sym;
   private String buf;
   private int bufPos;
   private boolean isEscaped;
   public static final int DONE = -1;
   public static final int PARSE_VARIABLES = 1;
   public static final int PARSE_ESCAPES = 2;
   public static final int SKIP_WHITESPACE = 4;

   public RuleCharacterIterator(String text, SymbolTable sym, ParsePosition pos) {
      if (text != null && pos.getIndex() <= text.length()) {
         this.text = text;
         this.sym = sym;
         this.pos = pos;
         this.buf = null;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public boolean atEnd() {
      return this.buf == null && this.pos.getIndex() == this.text.length();
   }

   public int next(int options) {
      int c = -1;
      this.isEscaped = false;

      while (true) {
         c = this._current();
         this._advance(UTF16.getCharCount(c));
         if (c == 36 && this.buf == null && (options & 1) != 0 && this.sym != null) {
            String name = this.sym.parseReference(this.text, this.pos, this.text.length());
            if (name == null) {
               break;
            }

            this.bufPos = 0;
            char[] chars = this.sym.lookup(name);
            if (chars == null) {
               this.buf = null;
               throw new IllegalArgumentException("Undefined variable: " + name);
            }

            if (chars.length == 0) {
               this.buf = null;
            }

            this.buf = new String(chars);
         } else if ((options & 4) == 0 || !PatternProps.isWhiteSpace(c)) {
            if (c == 92 && (options & 2) != 0) {
               int cpAndLength = Utility.unescapeAndLengthAt(this.getCurrentBuffer(), this.getCurrentBufferPos());
               if (cpAndLength < 0) {
                  throw new IllegalArgumentException("Invalid escape");
               }

               c = Utility.cpFromCodePointAndLength(cpAndLength);
               this.jumpahead(Utility.lengthFromCodePointAndLength(cpAndLength));
               this.isEscaped = true;
            }
            break;
         }
      }

      return c;
   }

   public boolean isEscaped() {
      return this.isEscaped;
   }

   public boolean inVariable() {
      return this.buf != null;
   }

   public RuleCharacterIterator.Position getPos(RuleCharacterIterator.Position p) {
      if (p == null) {
         p = new RuleCharacterIterator.Position();
      }

      p.buf = this.buf;
      p.bufPos = this.bufPos;
      p.posIndex = this.pos.getIndex();
      return p;
   }

   public void setPos(RuleCharacterIterator.Position p) {
      this.buf = p.buf;
      this.pos.setIndex(p.posIndex);
      this.bufPos = p.bufPos;
   }

   public void skipIgnored(int options) {
      if ((options & 4) != 0) {
         while (true) {
            int a = this._current();
            if (!PatternProps.isWhiteSpace(a)) {
               break;
            }

            this._advance(UTF16.getCharCount(a));
         }
      }
   }

   public String getCurrentBuffer() {
      return this.buf != null ? this.buf : this.text;
   }

   public int getCurrentBufferPos() {
      return this.buf != null ? this.bufPos : this.pos.getIndex();
   }

   public void jumpahead(int count) {
      if (count < 0) {
         throw new IllegalArgumentException();
      } else {
         if (this.buf != null) {
            this.bufPos += count;
            if (this.bufPos > this.buf.length()) {
               throw new IllegalArgumentException();
            }

            if (this.bufPos == this.buf.length()) {
               this.buf = null;
            }
         } else {
            int i = this.pos.getIndex() + count;
            this.pos.setIndex(i);
            if (i > this.text.length()) {
               throw new IllegalArgumentException();
            }
         }
      }
   }

   @Override
   public String toString() {
      int b = this.pos.getIndex();
      return this.text.substring(0, b) + '|' + this.text.substring(b);
   }

   private int _current() {
      if (this.buf != null) {
         return UTF16.charAt(this.buf, this.bufPos);
      } else {
         int i = this.pos.getIndex();
         return i < this.text.length() ? UTF16.charAt(this.text, i) : -1;
      }
   }

   private void _advance(int count) {
      if (this.buf != null) {
         this.bufPos += count;
         if (this.bufPos == this.buf.length()) {
            this.buf = null;
         }
      } else {
         this.pos.setIndex(this.pos.getIndex() + count);
         if (this.pos.getIndex() > this.text.length()) {
            this.pos.setIndex(this.text.length());
         }
      }
   }

   public static final class Position {
      private String buf;
      private int bufPos;
      private int posIndex;
   }
}
