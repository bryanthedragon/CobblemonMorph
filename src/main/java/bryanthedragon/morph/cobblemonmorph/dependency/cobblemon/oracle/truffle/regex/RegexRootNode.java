package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;

public final class RegexRootNode extends RootNode {
   public static final FrameDescriptor SHARED_EMPTY_FRAMEDESCRIPTOR = new FrameDescriptor();
   @Node.Child
   private RegexBodyNode body;

   public RegexRootNode(RegexLanguage language, RegexBodyNode body) {
      super(language, SHARED_EMPTY_FRAMEDESCRIPTOR);
      this.body = body;
   }

   public RegexSource getSource() {
      return this.body.getSource();
   }

   @Override
   public SourceSection getSourceSection() {
      return this.body.getSourceSection();
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return this.body.execute(frame);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.body instanceof InstrumentableNode.WrapperNode
         ? ((InstrumentableNode.WrapperNode)this.body).getDelegateNode().toString()
         : this.body.toString();
   }

   public static void checkThreadInterrupted() {
      CompilerAsserts.neverPartOfCompilation("do not check thread interruption from compiled code");
      if (Thread.interrupted()) {
         throw new RegexInterruptedException();
      }
   }
}
