package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.RegexBodyNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorEntryNode;

public class TRegexTraceFinderRootNode extends RegexBodyNode {
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final PreCalculatedResultFactory[] preCalculatedResults;
   @Node.Child
   private TRegexExecutorEntryNode entryNode;

   public TRegexTraceFinderRootNode(
      RegexLanguage language, RegexSource source, PreCalculatedResultFactory[] preCalculatedResults, TRegexExecutorEntryNode entryNode
   ) {
      super(language, source);
      this.preCalculatedResults = preCalculatedResults;
      this.entryNode = this.insert(entryNode);
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      Object[] args = frame.getArguments();

      assert args.length == 1;

      RegexResult receiver = (RegexResult)args[0];
      int traceFinderResult = (Integer)this.entryNode.execute(frame, receiver.getInput(), receiver.getFromIndex(), receiver.getEnd(), receiver.getEnd());
      int[] result = this.preCalculatedResults[traceFinderResult].createArrayFromEnd(receiver.getEnd());
      receiver.setResult(result);
      return result[0];
   }

   @Override
   public String getEngineLabel() {
      return "DFA traceFinder";
   }
}
