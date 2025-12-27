package com.oracle.truffle.regex.dead;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.regex.RegexExecNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.result.RegexResult;

public final class DeadRegexExecNode extends RegexExecNode {
   public DeadRegexExecNode(RegexLanguage language, RegexSource source) {
      super(language, source, false);
   }

   @Override
   protected RegexResult execute(VirtualFrame frame, Object input, int fromIndex) {
      return RegexResult.getNoMatchInstance();
   }

   @Override
   protected String getEngineLabel() {
      return "dead";
   }
}
