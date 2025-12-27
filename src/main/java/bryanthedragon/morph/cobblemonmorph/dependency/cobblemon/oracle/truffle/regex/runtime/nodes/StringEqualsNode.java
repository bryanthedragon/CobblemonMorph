package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;

@GenerateUncached
public abstract class StringEqualsNode extends Node {
   public abstract boolean execute(String a, String b);

   @Specialization(guards = {"a == cachedA", "cachedA.equals(b)"}, limit = "4")
   static boolean cacheIdentity(String a, String b, @Cached("a") String cachedA) {
      CompilerAsserts.compilationConstant(b);
      return true;
   }

   @Specialization(replaces = "cacheIdentity")
   static boolean doEquals(String a, String b) {
      CompilerAsserts.compilationConstant(b);
      return b.equals(a);
   }
}
