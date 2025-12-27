package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;

public abstract class JSConcatStringsNode extends JavaScriptBaseNode {
   protected final int stringLengthLimit;

   protected JSConcatStringsNode(int stringLengthLimit) {
      this.stringLengthLimit = stringLengthLimit;
   }

   public static JSConcatStringsNode create(int stringLengthLimit) {
      return JSConcatStringsNodeGen.create(stringLengthLimit);
   }

   public static JSConcatStringsNode create() {
      return create(JavaScriptLanguage.getCurrentLanguage().getJSContext().getStringLengthLimit());
   }

   public abstract TruffleString executeTString(TruffleString a, TruffleString b);

   @Specialization
   protected final TruffleString doConcat(
      TruffleString left, TruffleString right, @Cached BranchProfile errorBranch, @Cached TruffleString.ConcatNode concatNode
   ) {
      this.validateStringLength(Strings.length(left) + Strings.length(right), errorBranch);
      return Strings.concat(concatNode, left, right);
   }

   private void validateStringLength(int resultLength, BranchProfile errorBranch) {
      if (CompilerDirectives.injectBranchProbability(1.0E-4, resultLength < 0 || resultLength > this.stringLengthLimit)) {
         errorBranch.enter();
         throw Errors.createRangeErrorInvalidStringLength(this);
      }
   }
}
