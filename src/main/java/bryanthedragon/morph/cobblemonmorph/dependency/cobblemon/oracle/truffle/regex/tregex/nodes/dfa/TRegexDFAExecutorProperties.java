package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;

public final class TRegexDFAExecutorProperties {
   private final boolean forward;
   private final boolean searching;
   private final boolean genericCG;
   private final boolean allowSimpleCG;
   @CompilerDirectives.CompilationFinal
   private boolean simpleCG;
   @CompilerDirectives.CompilationFinal
   private boolean simpleCGMustCopy;
   private final boolean regressionTestMode;
   private final boolean trackLastGroup;
   private final int minResultLength;

   public TRegexDFAExecutorProperties(
      boolean forward, boolean searching, boolean genericCG, boolean allowSimpleCG, boolean regressionTestMode, boolean trackLastGroup, int minResultLength
   ) {
      this.forward = forward;
      this.searching = searching;
      this.genericCG = genericCG;
      this.allowSimpleCG = allowSimpleCG;
      this.regressionTestMode = regressionTestMode;
      this.trackLastGroup = trackLastGroup;
      this.minResultLength = minResultLength;
   }

   public boolean isForward() {
      return this.forward;
   }

   public boolean isBackward() {
      return !this.forward;
   }

   public boolean isSearching() {
      return this.searching;
   }

   public boolean isGenericCG() {
      return this.genericCG;
   }

   public boolean isAllowSimpleCG() {
      return this.allowSimpleCG;
   }

   public boolean isSimpleCG() {
      return this.simpleCG;
   }

   public void setSimpleCG(boolean simpleCG) {
      this.simpleCG = simpleCG;
   }

   public boolean isSimpleCGMustCopy() {
      return this.simpleCGMustCopy;
   }

   public void setSimpleCGMustCopy(boolean simpleCGMustCopy) {
      this.simpleCGMustCopy = simpleCGMustCopy;
   }

   public boolean isRegressionTestMode() {
      return this.regressionTestMode;
   }

   public boolean tracksLastGroup() {
      return this.trackLastGroup;
   }

   public int getMinResultLength() {
      return this.minResultLength;
   }
}
