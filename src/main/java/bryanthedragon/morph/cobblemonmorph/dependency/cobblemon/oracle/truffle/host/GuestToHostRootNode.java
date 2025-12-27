package com.oracle.truffle.host;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

abstract class GuestToHostRootNode extends RootNode {
   protected static final int ARGUMENT_OFFSET = 2;
   private final String boundaryName;

   protected GuestToHostRootNode(Class<?> targetType, String methodName) {
      super(null);
      this.boundaryName = targetType.getName() + "." + methodName;
   }

   @Override
   protected boolean isInstrumentable() {
      return false;
   }

   @Override
   public boolean isCloningAllowed() {
      return false;
   }

   @Override
   public final String getName() {
      return this.boundaryName;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object[] arguments = frame.getArguments();

      try {
         return this.executeImpl(arguments[1], arguments);
      } catch (InteropException var4) {
         throw silenceException(RuntimeException.class, var4);
      } catch (Throwable var5) {
         throw ((HostContext)arguments[0]).hostToGuestException(var5);
      }
   }

   static <E extends Throwable> RuntimeException silenceException(Class<E> type, Throwable ex) throws E {
      throw ex;
   }

   protected abstract Object executeImpl(Object receiver, Object[] arguments) throws InteropException;

   static Object guestToHostCall(Node node, CallTarget target, Object... arguments) {
      Node encapsulatingNode;
      if (node.isAdoptable()) {
         encapsulatingNode = node;
      } else {
         encapsulatingNode = EncapsulatingNodeReference.getCurrent().get();
      }

      return HostAccessor.RUNTIME.callInlined(encapsulatingNode, target, arguments);
   }
}
