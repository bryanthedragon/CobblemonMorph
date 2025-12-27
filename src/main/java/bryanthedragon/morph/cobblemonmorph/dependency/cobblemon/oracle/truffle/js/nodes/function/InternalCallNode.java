package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;

public abstract class InternalCallNode extends JavaScriptBaseNode {
   static final int LIMIT = 3;

   protected InternalCallNode() {
   }

   public static InternalCallNode create() {
      return InternalCallNodeGen.create();
   }

   public abstract Object execute(CallTarget callTarget, Object[] arguments);

   @Specialization(guards = "callTarget == cachedCallTarget", limit = "LIMIT")
   protected static Object directCall(
      CallTarget callTarget,
      Object[] arguments,
      @Cached("callTarget") CallTarget cachedCallTarget,
      @Cached("create(cachedCallTarget)") DirectCallNode directCallNode
   ) {
      return directCallNode.call(arguments);
   }

   @Specialization
   protected static Object indirectCall(CallTarget callTarget, Object[] arguments, @Cached("create()") IndirectCallNode indirectCallNode) {
      return indirectCallNode.call(callTarget, arguments);
   }
}
