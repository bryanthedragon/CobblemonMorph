package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.runtime.JSContext;

public class ScriptOrModule {
   protected final JSContext context;
   protected final Source source;

   public ScriptOrModule(JSContext context, Source source) {
      this.context = context;
      this.source = source;
   }

   public final JSContext getContext() {
      return this.context;
   }

   public final Source getSource() {
      return this.source;
   }
}
