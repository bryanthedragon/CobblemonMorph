package com.oracle.truffle.js.parser.env;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;

public class DebugEnvironment extends Environment {
   private final Object scope;

   public DebugEnvironment(Environment parent, NodeFactory factory, JSContext context, Object scope) {
      super(parent, factory, context);
      this.scope = scope;

      assert InteropLibrary.getUncached().isScope(scope) : scope;
   }

   @Override
   public boolean isStrictMode() {
      return true;
   }

   public boolean hasMember(TruffleString name) {
      return InteropLibrary.getUncached().isMemberReadable(this.scope, Strings.toJavaString(name));
   }
}
