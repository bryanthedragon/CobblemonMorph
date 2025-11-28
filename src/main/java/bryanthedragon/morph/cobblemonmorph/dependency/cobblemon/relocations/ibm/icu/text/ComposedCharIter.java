package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.Norm2AllModes;
import com.cobblemon.mod.relocations.ibm.icu.impl.Normalizer2Impl;

@Deprecated
public final class ComposedCharIter {
   @Deprecated
   public static final char DONE = '\uffff';
   private final Normalizer2Impl n2impl;
   private String decompBuf;
   private int curChar = 0;
   private int nextChar = -1;

   @Deprecated
   public ComposedCharIter() {
      this(false, 0);
   }

   @Deprecated
   public ComposedCharIter(boolean compat, int options) {
      if (compat) {
         this.n2impl = Norm2AllModes.getNFKCInstance().impl;
      } else {
         this.n2impl = Norm2AllModes.getNFCInstance().impl;
      }
   }

   @Deprecated
   public boolean hasNext() {
      if (this.nextChar == -1) {
         this.findNextChar();
      }

      return this.nextChar != -1;
   }

   @Deprecated
   public char next() {
      if (this.nextChar == -1) {
         this.findNextChar();
      }

      this.curChar = this.nextChar;
      this.nextChar = -1;
      return (char)this.curChar;
   }

   @Deprecated
   public String decomposition() {
      return this.decompBuf != null ? this.decompBuf : "";
   }

   private void findNextChar() {
      int c = this.curChar + 1;
      this.decompBuf = null;

      while (true) {
         if (c >= 65535) {
            c = -1;
            break;
         }

         this.decompBuf = this.n2impl.getDecomposition(c);
         if (this.decompBuf != null) {
            break;
         }

         c++;
      }

      this.nextChar = c;
   }
}
