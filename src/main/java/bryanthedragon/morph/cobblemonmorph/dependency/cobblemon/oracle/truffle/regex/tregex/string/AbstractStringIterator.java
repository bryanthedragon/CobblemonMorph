package com.oracle.truffle.regex.tregex.string;

import java.util.PrimitiveIterator.OfInt;

public abstract class AbstractStringIterator implements OfInt {
   protected int i;

   public int getIndex() {
      return this.i;
   }
}
