package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.parser.ast.InnerLiteral;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public final class DFAFindInnerLiteralStateNode extends DFAAbstractStateNode {
   private final InnerLiteral innerLiteral;

   public DFAFindInnerLiteralStateNode(short id, short[] successors, InnerLiteral innerLiteral) {
      super(id, successors);

      assert successors.length == 1;

      this.innerLiteral = innerLiteral;
   }

   public InnerLiteral getInnerLiteral() {
      return this.innerLiteral;
   }

   @Override
   public DFAAbstractStateNode createNodeSplitCopy(short copyID) {
      return new DFAFindInnerLiteralStateNode(copyID, Arrays.copyOf(this.getSuccessors(), this.getSuccessors().length), this.innerLiteral);
   }

   int executeInnerLiteralSearch(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean tString) {
      return executor.getIndexOfStringNode()
         .execute(
            locals.getInput(),
            locals.getIndex(),
            executor.getMaxIndex(locals),
            this.innerLiteral.getLiteralContent(tString),
            this.innerLiteral.getMaskContent(tString),
            executor.getEncoding()
         );
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("id", this.getId()),
         Json.prop("anchoredFinalState", false),
         Json.prop("finalState", false),
         Json.prop("loopToSelf", false),
         Json.prop(
            "transitions",
            Json.array(Json.obj(Json.prop("matcher", "innerLiteral(" + this.innerLiteral.getLiteral() + ")"), Json.prop("target", this.successors[0])))
         )
      );
   }
}
