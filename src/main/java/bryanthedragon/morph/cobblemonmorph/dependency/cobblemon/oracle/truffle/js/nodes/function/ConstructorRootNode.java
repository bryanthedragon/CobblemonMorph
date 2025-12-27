package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class ConstructorRootNode extends JavaScriptRootNode {
   private final JSFunctionData functionData;
   private final CallTarget callTarget;
   @Node.Child
   private DirectCallNode callNode;
   @Node.Child
   private SpecializedNewObjectNode newObjectNode;
   @Node.Child
   private IsObjectNode isObjectNode;
   private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();
   private final boolean newTarget;
   private final JSOrdinary instanceLayout;

   protected ConstructorRootNode(JSFunctionData functionData, CallTarget callTarget, boolean newTarget, JSOrdinary instanceLayout) {
      super(functionData.getContext().getLanguage(), ((RootCallTarget)callTarget).getRootNode().getSourceSection(), null);
      this.functionData = functionData;
      this.callTarget = callTarget;
      this.newTarget = newTarget;
      this.instanceLayout = instanceLayout;
   }

   public static ConstructorRootNode create(JSFunctionData functionData, CallTarget callTarget, boolean newTarget, JSOrdinary instanceLayout) {
      return new ConstructorRootNode(functionData, callTarget, newTarget, instanceLayout);
   }

   public static ConstructorRootNode create(JSFunctionData functionData, CallTarget callTarget, boolean newTarget) {
      return create(functionData, callTarget, newTarget, JSOrdinary.INSTANCE);
   }

   private Object allocateThisObject(VirtualFrame frame, Object[] arguments) {
      Object thisObject;
      if (!this.getFunctionData().isDerived()) {
         Object functionObject = this.newTarget ? arguments[2] : arguments[1];
         thisObject = this.newObjectNode.execute(frame, (JSDynamicObject)functionObject);
      } else {
         thisObject = JSFunction.CONSTRUCT;
      }

      arguments[0] = thisObject;
      return thisObject;
   }

   private Object filterConstructorResult(Object thisObject, Object result) {
      if (this.isObject.profile(this.isObjectNode.executeBoolean(result))) {
         return result;
      } else if (this.getFunctionData().isDerived()) {
         if (result != Undefined.instance) {
            throw Errors.createTypeErrorDerivedConstructorReturnedIllegalType(this);
         } else {
            throw Errors.createReferenceErrorDerivedConstructorThisNotInitialized(this);
         }
      } else {
         assert thisObject != JSFunction.CONSTRUCT;

         return thisObject;
      }
   }

   @Override
   public Object execute(VirtualFrame frame) {
      if (this.callNode == null || this.newObjectNode == null || this.isObjectNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.initialize();
      }

      Object[] arguments = frame.getArguments();
      Object thisObject = this.allocateThisObject(frame, arguments);
      Object result = this.callNode.call(arguments);
      return this.filterConstructorResult(thisObject, result);
   }

   private void initialize() {
      this.callNode = this.insert(Truffle.getRuntime().createDirectCallNode(this.callTarget));
      this.newObjectNode = this.insert(SpecializedNewObjectNode.create(this.functionData, this.instanceLayout));
      this.isObjectNode = this.insert(IsObjectNode.create());
   }

   private JSFunctionData getFunctionData() {
      return this.functionData;
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

   protected JavaScriptRootNode cloneUninitialized() {
      return new ConstructorRootNode(this.functionData, this.callTarget, this.newTarget, this.instanceLayout);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return ((RootCallTarget)this.callTarget).getRootNode().toString();
   }
}
