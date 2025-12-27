package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import java.lang.ref.WeakReference;

public final class EncapsulatingNodeReference {
   private static final ThreadLocal<EncapsulatingNodeReference> CURRENT = new ThreadLocal<EncapsulatingNodeReference>() {
      protected EncapsulatingNodeReference initialValue() {
         return new EncapsulatingNodeReference(Thread.currentThread());
      }
   };
   @CompilerDirectives.CompilationFinal
   private static volatile boolean seenNullContext;
   private final WeakReference<Thread> thread;
   private Node reference;

   EncapsulatingNodeReference(Thread t) {
      this.thread = new WeakReference<>(t);
   }

   public Node set(Node node) {
      assert node == null || node.isAdoptable() : "Node must be adoptable to be pushed as encapsulating node.";

      assert node == null || node.getRootNode() != null : "Node must be adopted by a RootNode to be pushed as encapsulating node.";

      assert Thread.currentThread() == this.thread.get();

      Node old = this.reference;
      this.reference = node;
      return old;
   }

   public Node get() {
      assert Thread.currentThread() == this.thread.get();

      return this.reference;
   }

   public static EncapsulatingNodeReference getCurrent() {
      boolean invalidateOnNull = CompilerDirectives.inCompiledCode() ? !seenNullContext : false;
      EncapsulatingNodeReference ref = NodeAccessor.ENGINE.getEncapsulatingNodeReference(invalidateOnNull);
      if (ref != null) {
         return ref;
      } else {
         if (!seenNullContext) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            seenNullContext = true;
         }

         return getThreadLocal();
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static EncapsulatingNodeReference getThreadLocal() {
      return CURRENT.get();
   }
}
