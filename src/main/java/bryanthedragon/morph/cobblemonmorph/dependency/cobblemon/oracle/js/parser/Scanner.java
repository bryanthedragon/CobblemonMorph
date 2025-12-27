package com.oracle.js.parser;

public class Scanner {
   protected final String content;
   protected int position;
   protected final int limit;
   protected int line;
   protected char ch0;
   protected char ch1;
   protected char ch2;
   protected char ch3;

   protected Scanner(final String content, final int line, final int start, final int length) {
      this.content = content;
      this.position = start;
      this.limit = start + length;
      this.line = line;
      this.reset(this.position);
   }

   Scanner(final Scanner scanner, final Scanner.State state) {
      this.content = scanner.content;
      this.position = state.position;
      this.limit = state.limit;
      this.line = state.line;
      this.reset(this.position);
   }

   Scanner.State saveState() {
      return new Scanner.State(this.position, this.limit, this.line);
   }

   void restoreState(final Scanner.State state) {
      this.position = state.position;
      this.line = state.line;
      this.reset(this.position);
   }

   protected final boolean atEOF() {
      return this.position == this.limit;
   }

   protected final char charAt(final int i) {
      return i < this.limit ? this.content.charAt(i) : '\u0000';
   }

   protected final void reset(final int i) {
      this.ch0 = this.charAt(i);
      this.ch1 = this.charAt(i + 1);
      this.ch2 = this.charAt(i + 2);
      this.ch3 = this.charAt(i + 3);
      this.position = Math.min(i, this.limit);
   }

   protected final void skip(final int n) {
      if (n == 1 && !this.atEOF()) {
         this.ch0 = this.ch1;
         this.ch1 = this.ch2;
         this.ch2 = this.ch3;
         this.ch3 = this.charAt(this.position + 4);
         this.position++;
      } else if (n != 0) {
         this.reset(this.position + n);
      }
   }

   static class State {
      public final int position;
      private int limit;
      public final int line;

      State(final int position, final int limit, final int line) {
         this.position = position;
         this.limit = limit;
         this.line = line;
      }

      void setLimit(final int limit) {
         this.limit = limit;
      }

      int getLimit() {
         return this.limit;
      }

      boolean isEmpty() {
         return this.position == this.limit;
      }
   }
}
