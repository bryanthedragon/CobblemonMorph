package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.FrameDescriptorProvider;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JavaScriptRealmBoundaryRootNode;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import java.util.Map;

@NodeInfo(cost = NodeCost.NONE, language = "JavaScript", description = "The root node of all functions in JavaScript.")
public final class FunctionRootNode extends JavaScriptRealmBoundaryRootNode implements FrameDescriptorProvider, JSFunctionData.CallTargetInitializer {
   @Node.Child
   private JavaScriptNode body;
   private final JSFunctionData functionData;
   private TruffleString internalFunctionName;
   private static final ThreadLocal<JSFunctionData> OMIT_FROM_STACK_TRACE = new ThreadLocal<>();

   protected FunctionRootNode(
      AbstractBodyNode body, FrameDescriptor frameDescriptor, JSFunctionData functionData, SourceSection sourceSection, TruffleString internalFunctionName
   ) {
      super(functionData.getContext().getLanguage(), sourceSection, frameDescriptor);
      this.body = body;
      if (!this.body.hasSourceSection()) {
         this.body.setSourceSection(sourceSection);
      }

      this.functionData = functionData;
      this.internalFunctionName = internalFunctionName;
   }

   public static FunctionRootNode create(
      AbstractBodyNode body, FrameDescriptor frameDescriptor, JSFunctionData functionData, SourceSection sourceSection, TruffleString internalFunctionName
   ) {
      FunctionRootNode rootNode = new FunctionRootNode(body, frameDescriptor, functionData, sourceSection, internalFunctionName);
      if (functionData.getContext().getContextOptions().isTestCloneUninitialized()) {
         assert JSNodeUtil.hasExactlyOneRootBodyTag(body) : "Function does not have exactly one RootBodyTag";

         return (FunctionRootNode)rootNode.cloneUninitialized();
      } else {
         return rootNode;
      }
   }

   public JSFunctionData getFunctionData() {
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

   protected JavaScriptRootNode cloneUninitialized() {
      return new FunctionRootNode(
         JavaScriptNode.cloneUninitialized(this.body, null), this.getFrameDescriptor(), this.functionData, this.getSourceSection(), this.internalFunctionName
      );
   }

   public boolean isInlineImmediately() {
      return this.functionData.isBuiltin();
   }

   public boolean isSplitImmediately() {
      return false;
   }

   @Override
   public String getName() {
      return Strings.toJavaString(this.getNameTString());
   }

   public TruffleString getNameTString() {
      if (this.functionData.isBuiltin() || Strings.isEmpty(this.functionData.getName()) && this.internalFunctionName != null) {
         assert this.internalFunctionName != null;

         return this.internalFunctionName;
      } else {
         return this.functionData.getName();
      }
   }

   @Override
   public String toString() {
      return this.getName();
   }

   public JavaScriptNode getBody() {
      return this.body;
   }

   @Override
   protected Object executeInRealm(VirtualFrame frame) {
      return this.body.execute(frame);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Map<String, Object> getDebugProperties() {
      Map<String, Object> map = super.getDebugProperties();
      map.put("name", "function " + this.getName() + "(" + this.getParamCount() + "/" + this.getFrameDescriptor().getNumberOfSlots() + ")");
      return map;
   }

   public int getParamCount() {
      return this.functionData.getLength();
   }

   @Override
   public boolean isFunction() {
      return true;
   }

   @Override
   protected boolean countsTowardsStackTraceLimit() {
      if (CompilerDirectives.inInterpreter()) {
         JSFunctionData omitUntil = OMIT_FROM_STACK_TRACE.get();
         if (omitUntil != null) {
            if (omitUntil == this.functionData) {
               OMIT_FROM_STACK_TRACE.set(null);
            }

            return false;
         }
      } else {
         assert OMIT_FROM_STACK_TRACE.get() == null;
      }

      return this.body instanceof JSBuiltinNode ? ((JSBuiltinNode)this.body).countsTowardsStackTraceLimit() : !this.isInternal();
   }

   public static void setOmitFromStackTrace(JSFunctionData until) {
      OMIT_FROM_STACK_TRACE.set(until);
   }

   @Override
   public void initializeCallTarget(JSFunctionData fd, JSFunctionData.Target target, CallTarget rootTarget) {
      initializeFunctionDataCallTarget(fd, target, rootTarget, this);
   }

   private static void initializeFunctionDataCallTarget(
      JSFunctionData functionData, JSFunctionData.Target target, CallTarget rootTarget, FunctionRootNode functionRoot
   ) {
      NodeFactory factory = NodeFactory.getDefaultInstance();
      if (target == JSFunctionData.Target.Call) {
         CallTarget functionCallTarget;
         if (functionData.requiresNew()) {
            functionCallTarget = factory.createConstructorRequiresNewRoot(functionData, functionRoot.getSourceSection()).getCallTarget();
         } else if (functionData.needsNewTarget()) {
            functionCallTarget = factory.createNewTargetCall(functionData.getContext(), rootTarget).getCallTarget();
         } else {
            functionCallTarget = rootTarget;
         }

         functionData.setCallTarget(functionCallTarget);
      } else if (target == JSFunctionData.Target.Construct) {
         CallTarget constructCallTarget;
         if (functionData.isGenerator()) {
            constructCallTarget = functionData.getContext().getGeneratorNotConstructibleCallTarget();
         } else if (functionData.isAsync()) {
            constructCallTarget = functionData.getContext().getNotConstructibleCallTarget();
         } else {
            constructCallTarget = factory.createConstructorRootNode(functionData, rootTarget, false).getCallTarget();
            if (functionData.needsNewTarget()) {
               constructCallTarget = factory.createNewTargetConstruct(functionData.getContext(), constructCallTarget).getCallTarget();
            }
         }

         functionData.setConstructTarget(constructCallTarget);
      } else if (target == JSFunctionData.Target.ConstructNewTarget) {
         CallTarget newTargetCallTarget;
         if (functionData.needsNewTarget()) {
            newTargetCallTarget = rootTarget;
         } else {
            newTargetCallTarget = factory.createDropNewTarget(functionData.getContext(), rootTarget).getCallTarget();
         }

         CallTarget constructNewTargetCallTarget = factory.createConstructorRootNode(functionData, newTargetCallTarget, true).getCallTarget();
         functionData.setConstructNewTarget(constructNewTargetCallTarget);
      }
   }
}
