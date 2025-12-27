package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.source.Source;

public final class LoadSourceEvent {
   private final Source source;

   LoadSourceEvent(Source source) {
      this.source = source;
   }

   public Source getSource() {
      return this.source;
   }
}
