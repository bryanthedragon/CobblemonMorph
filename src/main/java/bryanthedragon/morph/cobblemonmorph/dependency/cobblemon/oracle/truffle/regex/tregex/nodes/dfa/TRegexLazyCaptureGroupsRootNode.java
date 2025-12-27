package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.RegexBodyNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexProfile;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorEntryNode;

public class TRegexLazyCaptureGroupsRootNode extends RegexBodyNode {
   @Node.Child
   private TRegexExecutorEntryNode entryNode;
   @Node.Child
   private DirectCallNode findStartCallNode;
   private final RegexProfile.TracksRegexProfile profiler;
   private final CallTarget findStartCallTarget;

   public TRegexLazyCaptureGroupsRootNode(
      RegexLanguage language,
      RegexSource source,
      TRegexExecutorEntryNode captureGroupNode,
      RegexProfile.TracksRegexProfile profiler,
      CallTarget findStartCallTarget
   ) {
      super(language, source);
      this.entryNode = this.insert(captureGroupNode);
      this.profiler = profiler;
      this.findStartCallTarget = findStartCallTarget;
      if (findStartCallTarget != null) {
         this.findStartCallNode = this.insert(DirectCallNode.create(findStartCallTarget));
      }
   }

   public final Void execute(VirtualFrame frame) {
      Object[] args = frame.getArguments();

      assert args.length == 1;

      RegexResult receiver = (RegexResult)args[0];
      int start;
      if (this.findStartCallTarget != null) {
         start = (Integer)this.findStartCallNode.call(receiver);
      } else {
         start = receiver.getStart();
      }

      int[] result = (int[])this.entryNode.execute(frame, receiver.getInput(), receiver.getFromIndex(), start, receiver.getEnd());
      if (CompilerDirectives.inInterpreter()) {
         RegexProfile profile = this.profiler.getRegexProfile();
         profile.profileCaptureGroupAccess(result[1] - result[0], result[1] - (receiver.getFromIndex() + 1));
      }

      receiver.setResult(result);
      return null;
   }

   @Override
   protected String getEngineLabel() {
      return "TRegex cg";
   }
}
