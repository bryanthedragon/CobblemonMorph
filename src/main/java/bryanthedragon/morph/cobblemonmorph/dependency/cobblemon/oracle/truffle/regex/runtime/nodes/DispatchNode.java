package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.result.RegexResult;

@GenerateUncached
public abstract class DispatchNode extends Node {
   public abstract Object execute(CallTarget receiver, RegexResult result);

   @Specialization(guards = "target == cachedTarget")
   static Object executeDirect(
      CallTarget target, RegexResult result, @Cached("target") CallTarget cachedTarget, @Cached("create(cachedTarget)") DirectCallNode callNode
   ) {
      return callNode.call(result);
   }

   @Specialization(replaces = "executeDirect")
   static Object executeIndirect(CallTarget target, RegexResult result, @Cached IndirectCallNode callNode) {
      return callNode.call(target, result);
   }
}
