package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;

public final class LoadSourceSectionEvent {
   private final SourceSection sourceSection;
   private final Node node;

   LoadSourceSectionEvent(SourceSection sourceSection, Node node) {
      this.sourceSection = sourceSection;
      this.node = node;
   }

   public SourceSection getSourceSection() {
      return this.sourceSection;
   }

   public Node getNode() {
      return this.node;
   }
}
