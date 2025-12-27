package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class NewTargetRootNode extends JavaScriptRootNode {
   protected final CallTarget callTarget;
   @Node.Child
   protected DirectCallNode callNode;

   protected NewTargetRootNode(JavaScriptLanguage lang, CallTarget callTarget) {
      super(lang, ((RootCallTarget)callTarget).getRootNode().getSourceSection(), null);
      this.callTarget = callTarget;
   }

   public static JavaScriptRootNode createNewTargetConstruct(JavaScriptLanguage lang, CallTarget callTarget) {
      return createNewTarget(lang, callTarget, true);
   }

   public static JavaScriptRootNode createNewTargetCall(JavaScriptLanguage lang, CallTarget callTarget) {
      return createNewTarget(lang, callTarget, false);
   }

   private static JavaScriptRootNode createNewTarget(JavaScriptLanguage lang, CallTarget callTarget, boolean construct) {
      return new NewTargetRootNode.InsertNewTargetRootNode(lang, callTarget, construct);
   }

   public static JavaScriptRootNode createDropNewTarget(JavaScriptLanguage lang, CallTarget callTarget) {
      return new NewTargetRootNode.DropNewTargetRootNode(lang, callTarget);
   }

   @Override
   public boolean isCloningAllowed() {
      return true;
   }

   @Override
   protected boolean isCloneUninitializedSupported() {
      return true;
   }

   @Override
   public boolean isInternal() {
      return true;
   }

   protected abstract JavaScriptRootNode cloneUninitialized();

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return ((RootCallTarget)this.callTarget).getRootNode().toString();
   }

   @Override
   public Object execute(VirtualFrame frame) {
      if (this.callNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.callNode = this.insert(Truffle.getRuntime().createDirectCallNode(this.callTarget));
      }

      return this.doCall(frame);
   }

   @Override
   public String getName() {
      return ((RootCallTarget)this.callTarget).getRootNode().getName();
   }

   protected abstract Object doCall(VirtualFrame frame);

   public static class DropNewTargetRootNode extends NewTargetRootNode {
      protected DropNewTargetRootNode(JavaScriptLanguage lang, CallTarget callTarget) {
         super(lang, callTarget);
      }

      private static Object[] copyAndDropArgument(Object[] arguments, int dropPosition, int dropLength) {
         Object[] newArguments = new Object[arguments.length - dropLength];
         System.arraycopy(arguments, 0, newArguments, 0, dropPosition);
         System.arraycopy(arguments, dropPosition + dropLength, newArguments, dropPosition, arguments.length - dropPosition - dropLength);
         return newArguments;
      }

      @Override
      protected Object doCall(VirtualFrame frame) {
         Object[] arguments = frame.getArguments();
         Object[] newArguments = copyAndDropArgument(arguments, 2, 1);
         return this.callNode.call(newArguments);
      }

      @Override
      protected JavaScriptRootNode cloneUninitialized() {
         return new NewTargetRootNode.DropNewTargetRootNode(this.getLanguage(), this.callTarget);
      }
   }

   public static class InsertNewTargetRootNode extends NewTargetRootNode {
      private final boolean construct;

      protected InsertNewTargetRootNode(JavaScriptLanguage lang, CallTarget callTarget, boolean construct) {
         super(lang, callTarget);
         this.construct = construct;
      }

      private static Object[] copyAndInsertArgument(Object[] arguments, int insertPosition, Object newTarget) {
         int insertLength = 1;
         Object[] newArguments = new Object[arguments.length + 1];
         System.arraycopy(arguments, 0, newArguments, 0, insertPosition);
         newArguments[insertPosition] = newTarget;
         System.arraycopy(arguments, insertPosition, newArguments, insertPosition + 1, arguments.length - insertPosition);
         return newArguments;
      }

      @Override
      protected Object doCall(VirtualFrame frame) {
         Object[] arguments = frame.getArguments();
         Object newTarget = this.construct ? JSArguments.getFunctionObject(frame.getArguments()) : Undefined.instance;
         Object[] newArguments = copyAndInsertArgument(arguments, 2, newTarget);
         return this.callNode.call(newArguments);
      }

      @Override
      protected JavaScriptRootNode cloneUninitialized() {
         return new NewTargetRootNode.InsertNewTargetRootNode(this.getLanguage(), this.callTarget, this.construct);
      }
   }
}
