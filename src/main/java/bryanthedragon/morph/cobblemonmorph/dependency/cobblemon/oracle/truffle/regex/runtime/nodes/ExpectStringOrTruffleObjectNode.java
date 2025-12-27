package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;

@GenerateUncached
public abstract class ExpectStringOrTruffleObjectNode extends Node {
   public abstract Object execute(Object arg) throws UnsupportedTypeException;

   @Specialization
   static String doString(String input) {
      return input;
   }

   @Specialization
   static TruffleString doTString(TruffleString input) {
      return input;
   }

   @Specialization(guards = "inputs.isString(input)", limit = "2")
   static String doBoxedString(Object input, @CachedLibrary("input") InteropLibrary inputs) throws UnsupportedTypeException {
      try {
         return inputs.asString(input);
      } catch (UnsupportedMessageException var3) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw UnsupportedTypeException.create(new Object[]{input});
      }
   }

   @Specialization(guards = "inputs.hasArrayElements(input)", limit = "2")
   static Object doBoxedCharArray(Object input, @CachedLibrary("input") InteropLibrary inputs) throws UnsupportedTypeException {
      try {
         long inputLength = inputs.getArraySize(input);
         if (inputLength > 2147483647L) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw UnsupportedTypeException.create(new Object[]{input});
         } else {
            return input;
         }
      } catch (UnsupportedMessageException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw UnsupportedTypeException.create(new Object[]{input});
      }
   }

   public static ExpectStringOrTruffleObjectNode create() {
      return ExpectStringOrTruffleObjectNodeGen.create();
   }
}
