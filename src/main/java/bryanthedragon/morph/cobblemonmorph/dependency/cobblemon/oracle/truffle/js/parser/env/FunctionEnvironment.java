package com.oracle.truffle.js.parser.env;

import com.oracle.js.parser.ir.Scope;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.control.BreakTarget;
import com.oracle.truffle.js.nodes.control.ContinueTarget;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.Strings;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;

public final class FunctionEnvironment extends Environment {
   private static final TruffleString RETURN_SLOT_IDENTIFIER = Strings.constant("<return>");
   static final TruffleString ARGUMENTS_SLOT_IDENTIFIER = Strings.constant("<arguments>");
   static final TruffleString THIS_SLOT_IDENTIFIER = Strings.constant("<this>");
   static final TruffleString SUPER_SLOT_IDENTIFIER = Strings.constant("<super>");
   static final TruffleString NEW_TARGET_SLOT_IDENTIFIER = Strings.constant("<new.target>");
   static final TruffleString YIELD_VALUE_SLOT_IDENTIFIER = Strings.constant("<yieldvalue>");
   static final TruffleString ASYNC_CONTEXT_SLOT_IDENTIFIER = Strings.constant("<asynccontext>");
   static final TruffleString ASYNC_RESULT_SLOT_IDENTIFIER = Strings.constant("<asyncresult>");
   private static final TruffleString YIELD_RESULT_SLOT_IDENTIFIER = Strings.constant("<yieldresult>");
   public static final TruffleString DYNAMIC_SCOPE_IDENTIFIER = ScopeFrameNode.EVAL_SCOPE_IDENTIFIER;
   private final FunctionEnvironment parentFunction;
   private final JSFrameDescriptor frameDescriptor;
   private final boolean isStrictMode;
   private final Scope scope;
   private JSFrameSlot returnSlot;
   private JSFrameSlot blockScopeSlot;
   private TruffleString functionName = Strings.EMPTY_STRING;
   private TruffleString internalFunctionName = Strings.EMPTY_STRING;
   private boolean isNamedExpression;
   private boolean needsParentFrame;
   private boolean frozen;
   private int breakNodeCount;
   private int continueNodeCount;
   private boolean hasReturn;
   private boolean hasYield;
   private boolean hasAwait;
   private boolean hasMappedParameters;
   private List<BreakTarget> jumpTargetStack;
   private boolean directArgumentsAccess;
   private final boolean isGlobal;
   private final boolean isEval;
   private final boolean isDirectEval;
   private final boolean isArrowFunction;
   private final boolean isGeneratorFunction;
   private final boolean isDerivedConstructor;
   private final boolean isAsyncFunction;
   private final boolean hasSyntheticArguments;
   private boolean hasRestParameter;
   private boolean simpleParameterList = true;
   private boolean isDynamicallyScoped;
   private boolean needsNewTarget;
   private final boolean inDirectEval;

   public FunctionEnvironment(
      Environment parent,
      NodeFactory factory,
      JSContext context,
      Scope scope,
      boolean isStrictMode,
      boolean isEval,
      boolean isDirectEval,
      boolean isArrowFunction,
      boolean isGeneratorFunction,
      boolean isDerivedConstructor,
      boolean isAsyncFunction,
      boolean isGlobal,
      boolean hasSyntheticArguments
   ) {
      super(parent, factory, context);
      this.isDirectEval = isDirectEval;
      this.isAsyncFunction = isAsyncFunction;
      this.isStrictMode = isStrictMode;
      this.isEval = isEval;
      this.isArrowFunction = isArrowFunction;
      this.isGeneratorFunction = isGeneratorFunction;
      this.isDerivedConstructor = isDerivedConstructor;
      this.isGlobal = isGlobal;
      this.hasSyntheticArguments = hasSyntheticArguments;
      this.parentFunction = parent == null ? null : parent.function();
      this.frameDescriptor = factory.createFunctionFrameDescriptor();
      this.scope = scope;
      this.inDirectEval = isDirectEval || parent != null && parent.function() != null && parent.function().inDirectEval();
   }

   @Override
   public JSFrameSlot declareLocalVar(Object name) {
      assert !this.isFrozen() : name;

      return this.getFunctionFrameDescriptor().findOrAddFrameSlot(name, FrameSlotKind.Illegal);
   }

   @Override
   public JSFrameSlot declareInternalSlot(Object name) {
      assert JSFrameSlot.isAllowedIdentifierType(name) : name;

      return this.getFunctionFrameDescriptor().findOrAddFrameSlot(name);
   }

   public JSFrameSlot getReturnSlot() {
      if (this.returnSlot == null) {
         this.returnSlot = this.declareLocalVar(RETURN_SLOT_IDENTIFIER);
      }

      return this.returnSlot;
   }

   public boolean hasReturnSlot() {
      return this.returnSlot != null;
   }

   public JSFrameSlot getAsyncResultSlot() {
      return this.declareLocalVar(ASYNC_RESULT_SLOT_IDENTIFIER);
   }

   public JSFrameSlot getAsyncContextSlot() {
      return this.declareLocalVar(ASYNC_CONTEXT_SLOT_IDENTIFIER);
   }

   public JSFrameSlot getYieldResultSlot() {
      return this.declareLocalVar(YIELD_RESULT_SLOT_IDENTIFIER);
   }

   JSFrameSlot getOrCreateBlockScopeSlot() {
      if (this.blockScopeSlot == null) {
         this.blockScopeSlot = this.declareLocalVar(ScopeFrameNode.BLOCK_SCOPE_IDENTIFIER);
      }

      return this.blockScopeSlot;
   }

   JSFrameSlot getBlockScopeSlot() {
      return this.blockScopeSlot;
   }

   @Override
   public JSFrameSlot getCurrentBlockScopeSlot() {
      return null;
   }

   public boolean isEval() {
      return this.isEval;
   }

   public boolean isArrowFunction() {
      return this.isArrowFunction;
   }

   public boolean isGeneratorFunction() {
      return this.isGeneratorFunction;
   }

   @Override
   public JSFrameDescriptor getBlockFrameDescriptor() {
      return this.getFunctionFrameDescriptor();
   }

   @Override
   public JSFrameSlot findBlockFrameSlot(Object name) {
      return null;
   }

   @Override
   public JSFrameSlot findFunctionFrameSlot(Object name) {
      return this.getFunctionFrameDescriptor().findFrameSlot(name);
   }

   private <T extends BreakTarget> T pushJumpTarget(T target) {
      if (this.jumpTargetStack == null) {
         this.jumpTargetStack = new ArrayList<>(4);
      }

      this.jumpTargetStack.add(target);
      return target;
   }

   private void popJumpTarget(BreakTarget target) {
      assert this.jumpTargetStack != null && this.jumpTargetStack.get(this.jumpTargetStack.size() - 1) == target;

      this.jumpTargetStack.remove(this.jumpTargetStack.size() - 1);
   }

   public FunctionEnvironment.JumpTargetCloseable<ContinueTarget> pushContinueTarget(String label) {
      ContinueTarget target = ContinueTarget.forLoop(label, -1);
      this.pushJumpTarget(target);
      return new FunctionEnvironment.JumpTargetCloseable<>(target);
   }

   public FunctionEnvironment.JumpTargetCloseable<BreakTarget> pushBreakTarget(String label) {
      BreakTarget target = label == null ? BreakTarget.forSwitch() : BreakTarget.forLabel(label, -1);
      this.pushJumpTarget(target);
      return new FunctionEnvironment.JumpTargetCloseable<>(target);
   }

   public BreakTarget findBreakTarget(Object label) {
      this.breakNodeCount++;
      return this.findJumpTarget(label, BreakTarget.class, true);
   }

   public ContinueTarget findContinueTarget(Object label) {
      this.continueNodeCount++;
      return this.findJumpTarget(label, ContinueTarget.class, false);
   }

   private <T extends BreakTarget> T findJumpTarget(Object label, Class<T> targetClass, boolean direct) {
      T applicableTarget = null;
      ListIterator<BreakTarget> iterator = this.jumpTargetStack.listIterator(this.jumpTargetStack.size());

      while (iterator.hasPrevious()) {
         BreakTarget target = iterator.previous();
         if (direct && label == null) {
            if (BreakTarget.forSwitch() == target || target instanceof ContinueTarget) {
               return targetClass.cast(target);
            }
         } else if (!direct && label != null) {
            assert !direct;

            if (targetClass.isInstance(target)) {
               applicableTarget = (T)targetClass.cast(target);
            }

            if (label.equals(target.getLabel())) {
               assert applicableTarget != null : "Illegal or duplicate label";

               return applicableTarget;
            }
         } else if ((label == null || label.equals(target.getLabel())) && targetClass.isInstance(target)) {
            return targetClass.cast(target);
         }
      }

      throw new NoSuchElementException("jump target not found");
   }

   public boolean hasReturn() {
      return this.hasReturn;
   }

   public void addReturn() {
      this.hasReturn = true;
   }

   public boolean hasAwait() {
      return this.hasAwait;
   }

   public void addAwait() {
      this.hasAwait = true;
   }

   public boolean hasYield() {
      return this.hasYield;
   }

   public void addYield() {
      this.hasYield = true;
   }

   public void setDirectArgumentsAccess(boolean directArgumentsAccess) {
      this.directArgumentsAccess = directArgumentsAccess;
   }

   public boolean isDirectArgumentsAccess() {
      return this.directArgumentsAccess;
   }

   public void addMappedParameter(JSFrameSlot slot, int index) {
      assert slot != null && JSFrameUtil.isParam(slot) : slot;

      assert slot.getMappedParameterIndex() == -1;

      this.hasMappedParameters = true;
      slot.setMappedParameterIndex(index);
   }

   public TruffleString getFunctionName() {
      return this.functionName;
   }

   public void setFunctionName(TruffleString functionName) {
      this.functionName = functionName;
   }

   public TruffleString getInternalFunctionName() {
      return this.internalFunctionName;
   }

   public void setInternalFunctionName(TruffleString internalFunctionName) {
      this.internalFunctionName = internalFunctionName;
   }

   public void setNamedFunctionExpression(boolean isNamedExpression) {
      this.isNamedExpression = isNamedExpression;
   }

   protected boolean isNamedFunctionExpression() {
      return this.isNamedExpression;
   }

   public boolean needsParentFrame() {
      return this.needsParentFrame;
   }

   public void setNeedsParentFrame(boolean needsParentFrame) {
      if (this.frozen && needsParentFrame != this.needsParentFrame) {
         throw errorFrozenEnv();
      } else {
         this.needsParentFrame = needsParentFrame;
      }
   }

   private static RuntimeException errorFrozenEnv() {
      return new IllegalStateException("frozen function environment cannot be mutated");
   }

   public void freeze() {
      if (!this.frozen) {
         this.frozen = true;
      }
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   public boolean isDeepFrozen() {
      return this.isFrozen() && (this.getParentFunction() == null || this.getParentFunction().isDeepFrozen());
   }

   public boolean hasMappedParameters() {
      return this.hasMappedParameters;
   }

   @Override
   public JSFrameDescriptor getFunctionFrameDescriptor() {
      return this.frameDescriptor;
   }

   @Override
   public boolean isStrictMode() {
      return this.isStrictMode;
   }

   public FunctionEnvironment getParentFunction() {
      return this.parentFunction;
   }

   public FunctionEnvironment getParentFunction(int level) {
      assert level >= 0;

      return level == 0 ? this : this.parentFunction.getParentFunction(level - 1);
   }

   public FunctionEnvironment getNonArrowParentFunction() {
      return !this.isArrowFunction() && !this.isDirectEval() ? this : this.getParentFunction().getNonArrowParentFunction();
   }

   @Override
   public int getScopeLevel() {
      return 0;
   }

   public boolean isGlobal() {
      return this.isGlobal;
   }

   public boolean hasSyntheticArguments() {
      return this.hasSyntheticArguments;
   }

   public boolean returnsLastStatementResult() {
      return this.isGlobal() || this.isDirectEval() || this.hasSyntheticArguments();
   }

   public void setIsDynamicallyScoped(boolean isDynamicallyScoped) {
      this.isDynamicallyScoped = isDynamicallyScoped;
   }

   @Override
   public boolean isDynamicallyScoped() {
      return this.isDynamicallyScoped;
   }

   @Override
   public boolean isDynamicScopeContext() {
      return this.isDynamicallyScoped() || this.isCallerContextEval() || super.isDynamicScopeContext();
   }

   @Override
   public Environment getVariableEnvironment() {
      return (Environment)(this.isCallerContextEval() ? this.getParentFunction().getVariableEnvironment() : this);
   }

   public boolean isDirectEval() {
      return this.isDirectEval;
   }

   public boolean isIndirectEval() {
      return this.isEval() && !this.isDirectEval();
   }

   public boolean isCallerContextEval() {
      return this.isDirectEval() && !this.isStrictMode() && !this.isGlobal();
   }

   public boolean inDirectEval() {
      return this.inDirectEval;
   }

   public void setNeedsNewTarget(boolean needsNewTarget) {
      this.needsNewTarget = needsNewTarget;
   }

   public void setRestParameter(boolean restParameter) {
      this.hasRestParameter = restParameter;
   }

   public boolean hasRestParameter() {
      return this.hasRestParameter;
   }

   public void setSimpleParameterList(boolean simpleParameterList) {
      this.simpleParameterList = simpleParameterList;
   }

   public boolean hasSimpleParameterList() {
      return this.simpleParameterList;
   }

   public int getLeadingArgumentCount() {
      return this.needsNewTarget ? 1 : 0;
   }

   public boolean isDerivedConstructor() {
      return this.isDerivedConstructor;
   }

   public int getThisFunctionLevel() {
      int level = 0;

      for (FunctionEnvironment currentFunction = this; currentFunction.isArrowFunction() || currentFunction.isDirectEval(); level++) {
         currentFunction.setNeedsParentFrame(true);
         currentFunction = currentFunction.getParentFunction();
      }

      return level;
   }

   public boolean isAsyncFunction() {
      return this.isAsyncFunction;
   }

   public boolean isAsyncGeneratorFunction() {
      return this.isAsyncFunction && this.isGeneratorFunction;
   }

   @Override
   public Scope getScope() {
      return this.scope;
   }

   public boolean isModule() {
      return this.getScope().isModuleScope();
   }

   @Override
   protected String toStringImpl(Map<String, Integer> state) {
      int currentFrameLevel = state.getOrDefault("frameLevel", 0);
      state.put("frameLevel", currentFrameLevel + 1);
      state.put("scopeLevel", 0);
      return "Function("
         + currentFrameLevel
         + ") size="
         + this.getFunctionFrameDescriptor().getSize()
         + " "
         + joinElements(this.getFunctionFrameDescriptor().getIdentifiers());
   }

   public class JumpTargetCloseable<T extends BreakTarget> implements AutoCloseable {
      private final T target;
      private final int prevBreakCount = FunctionEnvironment.this.breakNodeCount;
      private final int prevContinueCount = FunctionEnvironment.this.continueNodeCount;

      protected JumpTargetCloseable(T target) {
         this.target = target;
      }

      public T getTarget() {
         return this.target;
      }

      @Override
      public void close() {
         FunctionEnvironment.this.popJumpTarget(this.target);
      }

      private boolean hasBreak() {
         return FunctionEnvironment.this.breakNodeCount != this.prevBreakCount;
      }

      private boolean hasContinue() {
         return FunctionEnvironment.this.continueNodeCount != this.prevContinueCount;
      }

      public JavaScriptNode wrapContinueTargetNode(JavaScriptNode child) {
         boolean hasContinue = this.hasContinue();
         return (JavaScriptNode)(hasContinue ? FunctionEnvironment.this.factory.createContinueTarget(child, (ContinueTarget)this.target) : child);
      }

      public JavaScriptNode wrapBreakTargetNode(JavaScriptNode child) {
         assert this.target.getLabel() == null;

         boolean hasBreak = this.hasBreak();
         return (JavaScriptNode)(hasBreak ? FunctionEnvironment.this.factory.createDirectBreakTarget(child) : child);
      }

      public JavaScriptNode wrapLabelBreakTargetNode(JavaScriptNode child) {
         assert this.target.getLabel() != null;

         boolean hasBreak = this.hasBreak();
         return (JavaScriptNode)(hasBreak ? FunctionEnvironment.this.factory.createLabel(child, this.target) : child);
      }
   }
}
