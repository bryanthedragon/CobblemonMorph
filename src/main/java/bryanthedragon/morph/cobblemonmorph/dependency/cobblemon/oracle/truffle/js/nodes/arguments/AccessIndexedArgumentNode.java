package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public class AccessIndexedArgumentNode extends JavaScriptNode implements RepeatableNode {
   protected final int index;
   @CompilerDirectives.CompilationFinal
   private boolean wasTrue;
   @CompilerDirectives.CompilationFinal
   private boolean wasFalse;

   AccessIndexedArgumentNode(int index) {
      this.index = index;
   }

   public static JavaScriptNode create(int paramIndex) {
      return new AccessIndexedArgumentNode(paramIndex);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object[] jsArguments = frame.getArguments();
      if (this.profile(this.index < JSArguments.getUserArgumentCount(jsArguments))) {
         Object userArg = JSArguments.getUserArgument(jsArguments, this.index);

         assert userArg != null;

         return userArg;
      } else {
         return Undefined.instance;
      }
   }

   protected final boolean profile(boolean value) {
      if (value) {
         if (!this.wasTrue) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.wasTrue = true;
         }

         return true;
      } else {
         if (!this.wasFalse) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.wasFalse = true;
         }

         return false;
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(this.index);
   }

   public int getIndex() {
      return this.index;
   }
}
