package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;

public abstract class IteratorCompleteNode extends JavaScriptBaseNode {
   protected IteratorCompleteNode() {
   }

   public abstract boolean execute(Object iterResult);

   public static IteratorCompleteNode create(JSContext context) {
      return new IteratorCompleteNode.Cached(context);
   }

   public static IteratorCompleteNode getUncached() {
      return IteratorCompleteNode.Uncached.INSTANCE;
   }

   static final class Cached extends IteratorCompleteNode {
      @Node.Child
      private PropertyGetNode getDoneNode;
      @Node.Child
      private JSToBooleanNode toBooleanNode;

      Cached(JSContext context) {
         this.getDoneNode = PropertyGetNode.create(Strings.DONE, false, context);
         this.toBooleanNode = JSToBooleanNode.create();
      }

      @Override
      public boolean execute(Object iterResult) {
         return this.toBooleanNode.executeBoolean(this.getDoneNode.getValue(iterResult));
      }
   }

   @DenyReplace
   static final class Uncached extends IteratorCompleteNode {
      static final IteratorCompleteNode.Uncached INSTANCE = new IteratorCompleteNode.Uncached();

      private Uncached() {
      }

      @Override
      public boolean execute(Object iterResult) {
         return JSRuntime.toBoolean(JSInteropUtil.get(iterResult, Strings.DONE));
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
