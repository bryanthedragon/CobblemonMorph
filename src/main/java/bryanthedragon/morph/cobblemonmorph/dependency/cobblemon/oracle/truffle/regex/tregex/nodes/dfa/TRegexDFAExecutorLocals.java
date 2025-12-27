package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;

public final class TRegexDFAExecutorLocals extends TRegexExecutorLocals {
   private int curMinIndex;
   private int result = -2;
   private short lastTransition;
   private int lastIndex;
   private final DFACaptureGroupTrackingData cgData;

   public TRegexDFAExecutorLocals(Object input, int fromIndex, int index, int maxIndex, DFACaptureGroupTrackingData cgData) {
      super(input, fromIndex, maxIndex, index);
      this.cgData = cgData;
   }

   public int getCurMinIndex() {
      return this.curMinIndex;
   }

   public void setCurMinIndex(int curMinIndex) {
      this.curMinIndex = curMinIndex;
   }

   public short getLastTransition() {
      return this.lastTransition;
   }

   public void setLastTransition(short lastTransition) {
      this.lastTransition = lastTransition;
   }

   public void setLastIndex() {
      this.lastIndex = this.getIndex();
   }

   public int getLastIndex() {
      return this.lastIndex;
   }

   public int getResultInt() {
      return this.result;
   }

   public void setResultInt(int result) {
      this.result = result;
   }

   public DFACaptureGroupTrackingData getCGData() {
      return this.cgData;
   }

   public TRegexDFAExecutorLocals toInnerLiteralBackwardLocals() {
      return new TRegexDFAExecutorLocals(this.getInput(), this.getFromIndex(), this.getIndex(), this.getMaxIndex(), this.cgData);
   }
}
