package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import java.util.Set;

public final class RuntimeErrorNode extends StatementNode {
   private final JSErrorType errorType;
   private final String message;

   RuntimeErrorNode(JSErrorType errorType, String message) {
      this.errorType = errorType;
      this.message = message;
   }

   public static RuntimeErrorNode create(JSErrorType errorType, String message) {
      return new RuntimeErrorNode(errorType, message);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      throw this.createException();
   }

   @CompilerDirectives.TruffleBoundary
   private JSException createException() {
      return JSException.create(this.errorType, this.message, this);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(this.errorType, this.message);
   }
}
