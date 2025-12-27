package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;

public abstract class DFAAbstractStateNode implements JsonConvertible {
   private final short id;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   protected final short[] successors;

   DFAAbstractStateNode(short id, short[] successors) {
      this.id = id;
      this.successors = successors;
   }

   public abstract DFAAbstractStateNode createNodeSplitCopy(short copyID);

   public final short getId() {
      return this.id;
   }

   public final short[] getSuccessors() {
      return this.successors;
   }
}
