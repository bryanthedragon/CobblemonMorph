package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;

@GenerateUncached
public abstract class ToLongNode extends Node {
   public abstract long execute(Object arg) throws UnsupportedTypeException;

   @Specialization
   static long doPrimitiveInt(int arg) {
      return arg;
   }

   @Specialization
   static long doPrimitiveLong(long arg) {
      return arg;
   }

   @Specialization(guards = "args.fitsInLong(arg)", limit = "2")
   static long doBoxed(Object arg, @CachedLibrary("arg") InteropLibrary args) throws UnsupportedTypeException {
      try {
         return args.asLong(arg);
      } catch (UnsupportedMessageException var3) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw UnsupportedTypeException.create(new Object[]{arg});
      }
   }

   public static ToLongNode create() {
      return ToLongNodeGen.create();
   }
}
