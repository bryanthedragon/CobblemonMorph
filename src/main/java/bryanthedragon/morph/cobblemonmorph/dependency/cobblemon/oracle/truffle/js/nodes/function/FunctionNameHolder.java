package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;

public interface FunctionNameHolder {
   TruffleString getFunctionName();

   void setFunctionName(TruffleString name);

   default boolean isAnonymous() {
      return Strings.isEmpty(this.getFunctionName());
   }

   public interface Delegate extends FunctionNameHolder {
      FunctionNameHolder getFunctionNameHolder();

      @Override
      default TruffleString getFunctionName() {
         return this.getFunctionNameHolder().getFunctionName();
      }

      @Override
      default void setFunctionName(TruffleString name) {
         this.getFunctionNameHolder().setFunctionName(name);
      }

      @Override
      default boolean isAnonymous() {
         return this.getFunctionNameHolder().isAnonymous();
      }
   }
}
