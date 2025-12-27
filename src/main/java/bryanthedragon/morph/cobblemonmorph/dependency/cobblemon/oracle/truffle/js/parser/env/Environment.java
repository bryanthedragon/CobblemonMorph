package com.oracle.truffle.js.parser.env;

import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.access.EvalVariableNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class Environment {
   private final Environment parent;
   private final FunctionEnvironment functionEnvironment;
   protected final NodeFactory factory;
   protected final JSContext context;

   public Environment(Environment parent, NodeFactory factory, JSContext context) {
      this.parent = parent;
      this.factory = factory;
      this.context = context;
      this.functionEnvironment = this instanceof FunctionEnvironment ? (FunctionEnvironment)this : (parent == null ? null : parent.functionEnvironment);
   }

   public JSFrameSlot declareLocalVar(Object name) {
      return this.function().declareLocalVar(name);
   }

   public boolean hasLocalVar(Object name) {
      return this.getFunctionFrameDescriptor().findFrameSlot(name) != null;
   }

   public Environment.VarRef findThisVar() {
      return this.findInternalSlot(FunctionEnvironment.THIS_SLOT_IDENTIFIER, true);
   }

   public void reserveThisSlot() {
      this.declareInternalSlot(FunctionEnvironment.THIS_SLOT_IDENTIFIER);
   }

   public Environment.VarRef findSuperVar() {
      assert !this.function().isGlobal();

      return this.findInternalSlot(FunctionEnvironment.SUPER_SLOT_IDENTIFIER);
   }

   public void reserveSuperSlot() {
      this.declareInternalSlot(FunctionEnvironment.SUPER_SLOT_IDENTIFIER);
   }

   public Environment.VarRef findArgumentsVar() {
      assert !this.function().isGlobal();

      return this.findInternalSlot(FunctionEnvironment.ARGUMENTS_SLOT_IDENTIFIER);
   }

   public void reserveArgumentsSlot() {
      this.declareInternalSlot(FunctionEnvironment.ARGUMENTS_SLOT_IDENTIFIER);
   }

   public Environment.VarRef findNewTargetVar() {
      assert !this.function().isGlobal();

      return this.findInternalSlot(FunctionEnvironment.NEW_TARGET_SLOT_IDENTIFIER);
   }

   public void reserveNewTargetSlot() {
      this.declareInternalSlot(FunctionEnvironment.NEW_TARGET_SLOT_IDENTIFIER);
   }

   public Environment.VarRef findAsyncContextVar() {
      assert !this.function().isGlobal() || this.function().isAsyncGeneratorFunction();

      this.declareLocalVar(FunctionEnvironment.ASYNC_CONTEXT_SLOT_IDENTIFIER);
      return this.findInternalSlot(FunctionEnvironment.ASYNC_CONTEXT_SLOT_IDENTIFIER);
   }

   public Environment.VarRef findAsyncResultVar() {
      assert !this.function().isGlobal() || this.function().isAsyncGeneratorFunction();

      this.declareLocalVar(FunctionEnvironment.ASYNC_RESULT_SLOT_IDENTIFIER);
      return this.findInternalSlot(FunctionEnvironment.ASYNC_RESULT_SLOT_IDENTIFIER);
   }

   public Environment.VarRef findYieldValueVar() {
      assert !this.function().isGlobal();

      this.declareLocalVar(FunctionEnvironment.YIELD_VALUE_SLOT_IDENTIFIER);
      return this.findInternalSlot(FunctionEnvironment.YIELD_VALUE_SLOT_IDENTIFIER);
   }

   public Environment.VarRef findDynamicScopeVar() {
      assert !this.function().isGlobal();

      return this.findInternalSlot(FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER);
   }

   public void reserveDynamicScopeSlot() {
      assert !this.function().isGlobal();

      this.declareInternalSlot(FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER);
   }

   public JSFrameSlot declareInternalSlot(Object name) {
      throw this.unsupported();
   }

   public final JavaScriptNode createLocal(JSFrameSlot frameSlot, int frameLevel, int scopeLevel) {
      return this.factory
         .createReadFrameSlot(frameSlot, this.factory.createScopeFrame(frameLevel, scopeLevel, this.getBlockScopeSlot(frameLevel, scopeLevel)), false);
   }

   public final Environment.VarRef findInternalSlot(Object name) {
      return this.findInternalSlot(name, false, 0);
   }

   public final Environment.VarRef findInternalSlot(Object name, boolean allowDebug) {
      return this.findInternalSlot(name, allowDebug, 0);
   }

   protected final Environment.VarRef findInternalSlot(Object name, boolean allowDebug, int skippedFrames) {
      Environment current = this;
      int frameLevel = 0;
      int scopeLevel = 0;

      do {
         if (frameLevel >= skippedFrames) {
            int effectiveScopeLevel = scopeLevel;
            JSFrameSlot slot = current.findBlockFrameSlot(name);
            if (slot == null) {
               slot = current.findFunctionFrameSlot(name);
               effectiveScopeLevel = scopeLevel + current.getScopeLevel();
            }

            if (slot != null) {
               return new Environment.FrameSlotVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
            }
         }

         if (current instanceof FunctionEnvironment) {
            frameLevel++;
            scopeLevel = 0;
         } else if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
            scopeLevel++;
         } else if (current instanceof DebugEnvironment
            && name instanceof TruffleString
            && allowDebug
            && ((DebugEnvironment)current).hasMember((TruffleString)name)) {
            return new Environment.DebugVarRef((TruffleString)name, frameLevel);
         }

         current = current.getParent();
      } while (current != null);

      return null;
   }

   public final Environment.VarRef findLocalVar(TruffleString name) {
      return this.findVar(name, true, true, false, true, true, false);
   }

   public final Environment.VarRef findVar(TruffleString name, boolean skipWith) {
      return this.findVar(name, skipWith, skipWith, false, false, false, false);
   }

   public final Environment.VarRef findBlockScopedVar(TruffleString name) {
      return this.findVar(name, true, true, false, true, true, true);
   }

   public final Environment.VarRef findVar(
      TruffleString name, boolean skipWith, boolean skipEval, boolean skipBlockScoped, boolean skipGlobal, boolean skipMapped
   ) {
      return this.findVar(name, skipWith, skipEval, skipBlockScoped, skipGlobal, skipMapped, false);
   }

   public final Environment.VarRef findVar(
      TruffleString name, boolean skipWith, boolean skipEval, boolean skipBlockScoped, boolean skipGlobal, boolean skipMapped, boolean skipVar
   ) {
      Environment current = this;
      int frameLevel = 0;
      int scopeLevel = 0;
      Environment.WrapClosure wrapClosure = null;
      int wrapFrameLevel = 0;

      do {
         if (current instanceof WithEnvironment) {
            if (!skipWith) {
               wrapClosure = this.makeWithWrapClosure(wrapClosure, name, ((WithEnvironment)current).getWithVarIdentifier());
               wrapFrameLevel = frameLevel;
            }
         } else if (current instanceof GlobalEnvironment) {
            GlobalEnvironment globalEnv = (GlobalEnvironment)current;
            if (globalEnv.hasLexicalDeclaration(name)) {
               return this.wrapIn(wrapClosure, wrapFrameLevel, new Environment.GlobalLexVarRef(name, globalEnv.hasConstDeclaration(name), globalEnv));
            }

            if (!globalEnv.hasVarDeclaration(name)) {
               wrapClosure = this.makeGlobalWrapClosure(wrapClosure, name);
            }
         } else if (current instanceof DebugEnvironment) {
            if (((DebugEnvironment)current).hasMember(name)) {
               wrapClosure = this.makeDebugWrapClosure(wrapClosure, name, frameLevel);
               wrapFrameLevel = frameLevel;
            }
         } else {
            int effectiveScopeLevel = scopeLevel;
            JSFrameSlot slot = current.findBlockFrameSlot(name);
            if (slot == null) {
               slot = current.findFunctionFrameSlot(name);
               effectiveScopeLevel = scopeLevel + current.getScopeLevel();
            }

            if (slot != null
               && (!skipBlockScoped || !JSFrameUtil.isConst(slot) && !JSFrameUtil.isLet(slot))
               && (!skipVar || JSFrameUtil.isConst(slot) || JSFrameUtil.isLet(slot))) {
               Environment.VarRef varRef;
               if (!skipMapped && isMappedArgumentsParameter(slot, current)) {
                  varRef = new Environment.MappedArgumentVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
               } else if (JSFrameUtil.isArguments(slot)) {
                  assert !current.function().isArrowFunction() && !current.function().isGlobal() && !current.function().isEval();

                  varRef = new Environment.ArgumentsVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
               } else {
                  assert frameLevel == 0 || JSFrameUtil.isClosedOver(slot) || current.getScope() != null && current.getScope().hasNestedEval() : slot;

                  varRef = new Environment.FrameSlotVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
               }

               return this.wrapIn(wrapClosure, wrapFrameLevel, varRef);
            }

            if (!skipEval && current.function().isDynamicallyScoped() && current.findBlockFrameSlot(FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER) != null) {
               wrapClosure = this.makeEvalWrapClosure(wrapClosure, name, frameLevel, scopeLevel, current);
               wrapFrameLevel = frameLevel;
            }

            if (current instanceof FunctionEnvironment) {
               FunctionEnvironment fnEnv = current.function();
               if (fnEnv.isNamedFunctionExpression() && fnEnv.getFunctionName().equals(name)) {
                  return this.wrapIn(wrapClosure, wrapFrameLevel, new Environment.FunctionCalleeVarRef(frameLevel, name, current));
               }

               frameLevel++;
               scopeLevel = 0;
            } else if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
               scopeLevel++;
            }
         }

         current = current.getParent();
      } while (current != null);

      return skipGlobal ? null : this.wrapIn(wrapClosure, wrapFrameLevel, new Environment.GlobalVarRef(name));
   }

   void ensureFrameLevelAvailable(int frameLevel) {
      int level = 0;

      for (FunctionEnvironment currentFunction = this.function(); currentFunction != null && level < frameLevel; level++) {
         currentFunction.setNeedsParentFrame(true);
         currentFunction = currentFunction.getParentFunction();
      }
   }

   private Environment.WrapClosure makeEvalWrapClosure(
      Environment.WrapClosure wrapClosure, TruffleString name, int frameLevel, int scopeLevel, Environment current
   ) {
      final JSFrameSlot dynamicScopeSlot = current.findBlockFrameSlot(FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER);

      assert dynamicScopeSlot != null;

      return Environment.WrapClosure.compose(
         wrapClosure,
         new Environment.WrapClosure() {
            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, Environment.WrapAccess access) {
               JavaScriptNode dynamicScopeNode = Environment.this.new FrameSlotVarRef(
                     dynamicScopeSlot, scopeLevel, frameLevel, FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER, current
                  )
                  .createReadNode();
               JSTargetableNode scopeAccessNode;
               if (access == Environment.WrapAccess.Delete) {
                  scopeAccessNode = Environment.this.factory
                     .createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
               } else if (access == Environment.WrapAccess.Write) {
                  assert delegateNode instanceof WriteNode : delegateNode;

                  scopeAccessNode = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, Environment.this.isStrictMode());
               } else {
                  if (access != Environment.WrapAccess.Read) {
                     throw new IllegalArgumentException();
                  }

                  assert delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode : delegateNode;

                  scopeAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
               }

               return new EvalVariableNode(Environment.this.context, name, delegateNode, dynamicScopeNode, scopeAccessNode);
            }
         }
      );
   }

   private Environment.WrapClosure makeWithWrapClosure(Environment.WrapClosure wrapClosure, TruffleString name, Object withVarName) {
      return Environment.WrapClosure.compose(
         wrapClosure,
         new Environment.WrapClosure() {
            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, Environment.WrapAccess access) {
               Environment.VarRef withVarNameRef = Objects.requireNonNull(Environment.this.findInternalSlot(withVarName));
               JSTargetableNode withAccessNode;
               if (access == Environment.WrapAccess.Delete) {
                  withAccessNode = Environment.this.factory
                     .createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
               } else if (access == Environment.WrapAccess.Write) {
                  assert delegateNode instanceof WriteNode : delegateNode;

                  withAccessNode = Environment.this.factory
                     .createWriteProperty(null, name, null, Environment.this.context, Environment.this.isStrictMode(), false, true);
               } else {
                  if (access != Environment.WrapAccess.Read) {
                     throw new IllegalArgumentException();
                  }

                  assert delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode : delegateNode;

                  withAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
               }

               JavaScriptNode withTarget = Environment.this.factory.createWithTarget(Environment.this.context, name, withVarNameRef.createReadNode());
               return Environment.this.factory.createWithVarWrapper(name, withTarget, withAccessNode, delegateNode);
            }

            @Override
            public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> applyCompound(
               Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> suppliers
            ) {
               Environment.VarRef withTargetTempVar = Environment.this.createTempVar();
               Environment.VarRef withObjVar = Objects.requireNonNull(Environment.this.findInternalSlot(withVarName));
               Supplier<JavaScriptNode> innerReadSupplier = suppliers.getFirst();
               UnaryOperator<JavaScriptNode> innerWriteSupplier = suppliers.getSecond();
               Supplier<JavaScriptNode> readSupplier = () -> {
                  JSTargetableNode readWithProperty = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
                  return Environment.this.factory.createWithVarWrapper(name, withTargetTempVar.createReadNode(), readWithProperty, innerReadSupplier.get());
               };
               UnaryOperator<JavaScriptNode> writeSupplier = rhs -> {
                  JavaScriptNode withTarget = Environment.this.factory.createWithTarget(Environment.this.context, name, withObjVar.createReadNode());
                  WritePropertyNode writeWithProperty = Environment.this.factory
                     .createWriteProperty(null, name, null, Environment.this.context, Environment.this.isStrictMode(), false, true);
                  return Environment.this.factory
                     .createWithVarWrapper(name, withTargetTempVar.createWriteNode(withTarget), writeWithProperty, innerWriteSupplier.apply(rhs));
               };
               return new Pair<>(readSupplier, writeSupplier);
            }
         }
      );
   }

   private Environment.WrapClosure makeGlobalWrapClosure(Environment.WrapClosure wrapClosure, TruffleString name) {
      return Environment.WrapClosure.compose(
         wrapClosure,
         new Environment.WrapClosure() {
            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, Environment.WrapAccess access) {
               JSTargetableNode scopeAccessNode;
               if (access == Environment.WrapAccess.Delete) {
                  scopeAccessNode = Environment.this.factory
                     .createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
               } else if (access == Environment.WrapAccess.Write) {
                  assert delegateNode instanceof WriteNode : delegateNode;

                  scopeAccessNode = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, true);
               } else {
                  if (access != Environment.WrapAccess.Read) {
                     throw new IllegalArgumentException();
                  }

                  assert delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode : delegateNode;

                  scopeAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
               }

               JavaScriptNode globalScope = Environment.this.factory.createGlobalScope(Environment.this.context);
               return Environment.this.factory.createGlobalVarWrapper(name, delegateNode, globalScope, scopeAccessNode);
            }
         }
      );
   }

   private Environment.WrapClosure makeDebugWrapClosure(Environment.WrapClosure wrapClosure, TruffleString name, int frameLevel) {
      this.ensureFrameLevelAvailable(frameLevel);
      return Environment.WrapClosure.compose(
         wrapClosure,
         new Environment.WrapClosure() {
            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, Environment.WrapAccess access) {
               JSTargetableNode scopeAccessNode;
               if (access == Environment.WrapAccess.Delete) {
                  scopeAccessNode = Environment.this.factory
                     .createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
               } else if (access == Environment.WrapAccess.Write) {
                  assert delegateNode instanceof WriteNode : delegateNode;

                  scopeAccessNode = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, true);
               } else {
                  if (access != Environment.WrapAccess.Read) {
                     throw new IllegalArgumentException();
                  }

                  assert delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode : delegateNode;

                  scopeAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
               }

               JavaScriptNode debugScope = Environment.this.factory
                  .createDebugScope(Environment.this.context, Environment.this.factory.createAccessCallee(frameLevel - 1));
               return Environment.this.factory.createDebugVarWrapper(name, delegateNode, debugScope, scopeAccessNode);
            }
         }
      );
   }

   private Environment.VarRef wrapIn(Environment.WrapClosure wrapClosure, int wrapFrameLevel, Environment.VarRef wrappee) {
      if (wrapClosure != null) {
         this.ensureFrameLevelAvailable(wrapFrameLevel);
         return new Environment.WrappedVarRef(wrappee.getName(), wrappee, wrapClosure);
      } else {
         return wrappee;
      }
   }

   public JSFrameSlot findBlockFrameSlot(Object name) {
      return null;
   }

   public JSFrameSlot findFunctionFrameSlot(Object name) {
      return null;
   }

   public JSFrameDescriptor getBlockFrameDescriptor() {
      throw this.unsupported();
   }

   private static boolean isMappedArgumentsParameter(JSFrameSlot slot, Environment current) {
      FunctionEnvironment function = current.function();
      return function.hasMappedParameters() && !function.isStrictMode() && function.hasSimpleParameterList() && JSFrameUtil.isParam(slot);
   }

   public final Environment getParent() {
      return this.parent;
   }

   public final FunctionEnvironment function() {
      return this.functionEnvironment;
   }

   public final Environment getParentAt(int frameLevel, int scopeLevel) {
      Environment current = this;
      int currentFrameLevel = 0;
      int currentScopeLevel = 0;

      while (currentFrameLevel != frameLevel || currentScopeLevel != scopeLevel) {
         if (current instanceof FunctionEnvironment) {
            currentFrameLevel++;
            currentScopeLevel = 0;
         } else if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
            currentScopeLevel++;
         }

         current = current.getParent();
         if (current == null) {
            return null;
         }
      }

      return current;
   }

   public Environment.VarRef createTempVar() {
      JSFrameSlot var = this.declareTempVar(Strings.constant("tmp"));
      return this.findTempVar(var);
   }

   public Environment.VarRef findTempVar(JSFrameSlot var) {
      return new Environment.VarRef(var.getIdentifier()) {
         @Override
         public boolean isGlobal() {
            return false;
         }

         @Override
         public boolean isFunctionLocal() {
            return false;
         }

         @Override
         public JSFrameSlot getFrameSlot() {
            return var;
         }

         @Override
         public JavaScriptNode createReadNode() {
            return Environment.this.factory.createReadCurrentFrameSlot(var);
         }

         @Override
         public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
            return Environment.this.factory.createWriteCurrentFrameSlot(var, rhs);
         }

         @Override
         public JavaScriptNode createDeleteNode() {
            throw Errors.shouldNotReachHere();
         }
      };
   }

   private JSFrameSlot declareTempVar(TruffleString prefix) {
      return this.declareLocalVar(this.factory.createInternalSlotId(prefix, this.getFunctionFrameDescriptor().getSize()));
   }

   public JSFrameDescriptor getFunctionFrameDescriptor() {
      return this.function().getFunctionFrameDescriptor();
   }

   public boolean isStrictMode() {
      return this.function().isStrictMode();
   }

   public int getScopeLevel() {
      throw this.unsupported();
   }

   public boolean hasScopeFrame() {
      return false;
   }

   public Scope getScope() {
      return null;
   }

   private UnsupportedOperationException unsupported() {
      return new UnsupportedOperationException(this.getClass().getName());
   }

   public final JSFrameSlot getBlockScopeSlot(int frameLevel, int scopeLevel) {
      Environment current = this;

      for (int currentFrameLevel = frameLevel; currentFrameLevel > 0; currentFrameLevel--) {
         current = current.function().getParent();
      }

      for (int currentScopeLevel = scopeLevel; current != null; current = current.getParent()) {
         if (current instanceof FunctionEnvironment) {
            assert currentScopeLevel == 0;

            return null;
         }

         if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
            if (currentScopeLevel == 0) {
               return this.function().getBlockScopeSlot();
            }

            currentScopeLevel--;
         }
      }

      return null;
   }

   public JSFrameSlot getCurrentBlockScopeSlot() {
      return null;
   }

   public void addFrameSlotsFromSymbols(Iterable<Symbol> symbols) {
      this.addFrameSlotsFromSymbols(symbols, false, null);
   }

   public void addFrameSlotsFromSymbols(Iterable<Symbol> symbols, boolean onlyBlockScoped, Predicate<Symbol> filter) {
      for (Symbol symbol : symbols) {
         if ((
               symbol.isBlockScoped()
                  || !onlyBlockScoped && symbol.isVar() && !symbol.isGlobal() && !symbol.isThis() && !symbol.isSuper() && !symbol.isNewTarget()
            )
            && !symbol.isFunctionSelf()
            && (filter == null || filter.test(symbol))) {
            this.addFrameSlotFromSymbol(symbol);
         }
      }
   }

   public void addFrameSlotFromSymbol(Symbol symbol) {
      assert !this.getBlockFrameDescriptor().contains(symbol.getNameTS()) || this instanceof FunctionEnvironment : symbol;

      this.getBlockFrameDescriptor().findOrAddFrameSlot(symbol.getNameTS(), symbol.getFlags(), FrameSlotKind.Illegal);
   }

   public boolean isDynamicallyScoped() {
      return false;
   }

   public boolean isDynamicScopeContext() {
      return this.getParent() == null ? false : this.getParent().isDynamicScopeContext();
   }

   public Environment getVariableEnvironment() {
      return this.function().getVariableEnvironment();
   }

   public Environment.VarRef findActiveModule() {
      Environment current = this;
      int frameLevel = 0;

      int scopeLevel;
      for (scopeLevel = 0; current.getParent() != null; current = current.getParent()) {
         if (current instanceof FunctionEnvironment) {
            assert !((FunctionEnvironment)current).isModule();

            ((FunctionEnvironment)current).setNeedsParentFrame(true);
            frameLevel++;
            scopeLevel = 0;
         } else if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
            scopeLevel++;
         }
      }

      assert current instanceof FunctionEnvironment && ((FunctionEnvironment)current).isModule();

      return new Environment.ActiveModuleRef(scopeLevel, frameLevel, current);
   }

   protected String toStringImpl(Map<String, Integer> state) {
      return this.getClass().getSimpleName();
   }

   protected static String joinElements(Iterable<? extends Object> keySet) {
      StringJoiner sj = new StringJoiner(", ", "{", "}");

      for (Object key : keySet) {
         sj.add(String.valueOf(key));
      }

      return sj.toString();
   }

   @Override
   public String toString() {
      StringJoiner output = new StringJoiner("\n");
      Map<String, Integer> state = new HashMap<>();
      Environment current = this;

      do {
         output.add(current.toStringImpl(state));
         current = current.getParent();
      } while (current != null);

      return output.toString();
   }

   private abstract class AbstractArgumentsVarRef extends Environment.AbstractFrameVarRef {
      AbstractArgumentsVarRef(int scopeLevel, int frameLevel, TruffleString name, Environment current) {
         super(scopeLevel, frameLevel, name, current);
      }

      @Override
      public JSFrameSlot getFrameSlot() {
         return null;
      }
   }

   public abstract class AbstractFrameVarRef extends Environment.VarRef {
      protected final int scopeLevel;
      protected final int frameLevel;
      protected final Environment resolvedEnv;

      public AbstractFrameVarRef(int scopeLevel, int frameLevel, Object name, Environment resolvedEnv) {
         super(name);
         this.scopeLevel = scopeLevel;
         this.frameLevel = frameLevel;
         this.resolvedEnv = resolvedEnv;
         Environment.this.ensureFrameLevelAvailable(frameLevel);
      }

      public int getScopeLevel() {
         return this.scopeLevel;
      }

      public int getFrameLevel() {
         return this.frameLevel;
      }

      @Override
      public boolean isFunctionLocal() {
         return this.frameLevel == 0;
      }

      @Override
      public boolean isGlobal() {
         return false;
      }

      @Override
      public JavaScriptNode createDeleteNode() {
         return Environment.this.factory.createConstantBoolean(false);
      }

      public ScopeFrameNode createScopeFrameNode() {
         return Environment.this.factory.createScopeFrame(this.frameLevel, this.getEffectiveScopeLevel(), this.getBlockScopeSlot());
      }

      private JSFrameSlot getBlockScopeSlot() {
         if (this.frameLevel != 0) {
            return null;
         } else {
            return this.isInCurrentFunctionFrame() ? null : this.resolvedEnv.getCurrentBlockScopeSlot();
         }
      }

      private int getEffectiveScopeLevel() {
         return this.isInCurrentFunctionFrame() ? 0 : this.scopeLevel;
      }

      protected boolean isInCurrentFunctionFrame() {
         return this.frameLevel == 0 && this.scopeLevel == Environment.this.getScopeLevel();
      }
   }

   final class ActiveModuleRef extends Environment.AbstractArgumentsVarRef {
      ActiveModuleRef(int scopeLevel, int frameLevel, Environment current) {
         super(scopeLevel, frameLevel, null, current);
      }

      @Override
      public JavaScriptNode createReadNode() {
         return Environment.this.factory.createAccessFrameArgument(this.createScopeFrameNode(), 0);
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         throw Errors.shouldNotReachHere();
      }
   }

   private final class ArgumentsVarRef extends Environment.AbstractArgumentsVarRef {
      private final JSFrameSlot frameSlot;

      ArgumentsVarRef(JSFrameSlot frameSlot, int scopeLevel, int frameLevel, TruffleString name, Environment current) {
         super(scopeLevel, frameLevel, name, current);
         this.frameSlot = frameSlot;
      }

      @Override
      public JavaScriptNode createReadNode() {
         JavaScriptNode argumentsVarNode = Environment.this.factory.createReadFrameSlot(this.frameSlot, this.createScopeFrameNode());
         if (Environment.this.function().isDirectArgumentsAccess()) {
            FunctionEnvironment currentFunction = this.resolvedEnv.function();
            JavaScriptNode createArgumentsObjectNode = Environment.this.factory
               .createArgumentsObjectNode(Environment.this.context, Environment.this.isStrictMode(), currentFunction.getLeadingArgumentCount());
            JavaScriptNode writeNode = Environment.this.factory.createWriteFrameSlot(this.frameSlot, this.createScopeFrameNode(), createArgumentsObjectNode);
            return Environment.this.factory.createAccessArgumentsArrayDirectly(writeNode, argumentsVarNode, currentFunction.getLeadingArgumentCount());
         } else {
            return argumentsVarNode;
         }
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         assert !this.resolvedEnv.function().isDirectArgumentsAccess();

         return Environment.this.factory.createWriteFrameSlot(this.frameSlot, this.createScopeFrameNode(), rhs);
      }

      @Override
      public JSFrameSlot getFrameSlot() {
         return this.frameSlot;
      }
   }

   class DebugVarRef extends Environment.VarRef {
      private final int frameLevel;

      DebugVarRef(TruffleString name, int frameLevel) {
         super(name);
         this.frameLevel = frameLevel;
         Environment.this.ensureFrameLevelAvailable(frameLevel);
      }

      @Override
      public JavaScriptNode createReadNode() {
         JavaScriptNode debugScope = Environment.this.factory
            .createDebugScope(Environment.this.context, Environment.this.factory.createAccessCallee(this.frameLevel - 1));
         return Environment.this.factory
            .createDebugVarWrapper(
               this.getName(),
               Environment.this.factory.createConstantUndefined(),
               debugScope,
               Environment.this.factory.createReadProperty(Environment.this.context, null, this.getName())
            );
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         return Environment.this.factory.createWriteConstantVariable(rhs, Environment.this.isStrictMode());
      }

      @Override
      public JavaScriptNode createDeleteNode() {
         return Environment.this.factory.createConstantBoolean(false);
      }

      @Override
      public boolean isFunctionLocal() {
         return false;
      }

      @Override
      public boolean isGlobal() {
         return false;
      }
   }

   public class FrameSlotVarRef extends Environment.AbstractFrameVarRef {
      protected final JSFrameSlot frameSlot;
      private final boolean checkTDZ;

      public FrameSlotVarRef(JSFrameSlot frameSlot, int scopeLevel, int frameLevel, Object name, Environment current) {
         this(frameSlot, scopeLevel, frameLevel, name, current, JSFrameUtil.needsTemporalDeadZoneCheck(frameSlot, frameLevel));
      }

      public FrameSlotVarRef(JSFrameSlot frameSlot, int scopeLevel, int frameLevel, Object name, Environment current, boolean checkTDZ) {
         super(scopeLevel, frameLevel, name, current);
         this.frameSlot = frameSlot;
         this.checkTDZ = checkTDZ;
      }

      @Override
      public JSFrameSlot getFrameSlot() {
         return this.frameSlot;
      }

      @Override
      public boolean isConst() {
         return JSFrameUtil.isConst(this.frameSlot);
      }

      @Override
      public JavaScriptNode createReadNode() {
         JavaScriptNode readNode = Environment.this.factory.createReadFrameSlot(this.frameSlot, this.createScopeFrameNode(), this.checkTDZ);
         return JSFrameUtil.isImportBinding(this.frameSlot) ? Environment.this.factory.createReadImportBinding(readNode) : readNode;
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         return Environment.this.factory.createWriteFrameSlot(this.frameSlot, this.createScopeFrameNode(), rhs, this.checkTDZ);
      }

      @Override
      public Environment.VarRef withTDZCheck() {
         return !this.checkTDZ && JSFrameUtil.hasTemporalDeadZone(this.frameSlot)
            ? Environment.this.new FrameSlotVarRef(this.frameSlot, this.scopeLevel, this.frameLevel, this.name, this.resolvedEnv, true)
            : this;
      }

      @Override
      public boolean hasTDZCheck() {
         return this.checkTDZ;
      }
   }

   private final class FunctionCalleeVarRef extends Environment.AbstractArgumentsVarRef {
      FunctionCalleeVarRef(int frameLevel, TruffleString name, Environment current) {
         super(0, frameLevel, name, current);
      }

      @Override
      public JavaScriptNode createReadNode() {
         return Environment.this.factory.createAccessCallee(this.frameLevel);
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         return Environment.this.factory.createWriteConstantVariable(rhs, Environment.this.isStrictMode());
      }
   }

   public class GlobalLexVarRef extends Environment.VarRef {
      private final boolean isConst;
      private final boolean required;
      private final boolean checkTDZ;
      private final GlobalEnvironment globalEnv;

      public GlobalLexVarRef(TruffleString name, boolean isConst, GlobalEnvironment globalEnv) {
         this(name, isConst, globalEnv, true, false);
      }

      private GlobalLexVarRef(Object name, boolean isConst, GlobalEnvironment globalEnv, boolean required, boolean checkTDZ) {
         super(name);

         assert name instanceof TruffleString && !name.equals(Null.NAME) && !GlobalEnvironment.isGlobalObjectConstant((TruffleString)name) : name;

         this.isConst = isConst;
         this.required = required;
         this.checkTDZ = checkTDZ;
         this.globalEnv = globalEnv;
      }

      @Override
      public JavaScriptNode createReadNode() {
         if (!this.required) {
            JavaScriptNode globalScope = Environment.this.factory.createGlobalScopeTDZCheck(Environment.this.context, this.getName(), this.checkTDZ);
            return Environment.this.factory.createReadProperty(Environment.this.context, globalScope, this.getName());
         } else {
            return Environment.this.factory.createReadLexicalGlobal(this.getName(), this.checkTDZ, Environment.this.context);
         }
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         JavaScriptNode globalScope = Environment.this.factory.createGlobalScopeTDZCheck(Environment.this.context, this.getName(), this.checkTDZ);
         return Environment.this.factory.createWriteProperty(globalScope, this.getName(), rhs, Environment.this.context, true, this.required, false);
      }

      @Override
      public boolean isFunctionLocal() {
         return Environment.this.function().isGlobal();
      }

      @Override
      public JSFrameSlot getFrameSlot() {
         return null;
      }

      @Override
      public boolean isGlobal() {
         return true;
      }

      @Override
      public boolean isConst() {
         return this.isConst;
      }

      @Override
      public JavaScriptNode createDeleteNode() {
         JavaScriptNode element = Environment.this.factory.createConstantString(this.getName());
         JavaScriptNode object = Environment.this.factory.createGlobalScope(Environment.this.context);
         return Environment.this.factory.createDeleteProperty(object, element, Environment.this.isStrictMode(), Environment.this.context);
      }

      @Override
      public Environment.VarRef withRequired(boolean required) {
         return this.required != required ? Environment.this.new GlobalLexVarRef(this.name, this.isConst, this.globalEnv, required, this.checkTDZ) : this;
      }

      @Override
      public Environment.VarRef withTDZCheck() {
         return !this.checkTDZ ? Environment.this.new GlobalLexVarRef(this.name, this.isConst, this.globalEnv, this.required, true) : this;
      }

      @Override
      public boolean hasTDZCheck() {
         return this.checkTDZ;
      }

      @Override
      public boolean hasBeenDeclared() {
         return this.globalEnv.hasBeenDeclared((TruffleString)this.name);
      }

      @Override
      public void setHasBeenDeclared(boolean declared) {
         this.globalEnv.setHasBeenDeclared((TruffleString)this.name, declared);
      }
   }

   public class GlobalVarRef extends Environment.VarRef {
      private final boolean required;

      public GlobalVarRef(TruffleString name) {
         this(name, true);
      }

      private GlobalVarRef(TruffleString name, boolean required) {
         super(name);

         assert !Null.NAME.equals(name);

         this.required = required;
      }

      @Override
      public JavaScriptNode createReadNode() {
         if (this.name.equals(Undefined.NAME)) {
            return Environment.this.factory.createConstantUndefined();
         } else {
            return !this.required
               ? Environment.this.factory.createReadProperty(Environment.this.context, Environment.this.factory.createGlobalObject(), this.getName())
               : Environment.this.factory.createReadGlobalProperty(Environment.this.context, this.getName());
         }
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         return Environment.this.factory
            .createWriteProperty(
               Environment.this.factory.createGlobalObject(),
               this.getName(),
               rhs,
               Environment.this.context,
               Environment.this.isStrictMode(),
               this.isGlobal(),
               this.required
            );
      }

      @Override
      public boolean isFunctionLocal() {
         return false;
      }

      @Override
      public JSFrameSlot getFrameSlot() {
         return null;
      }

      @Override
      public boolean isGlobal() {
         return true;
      }

      @Override
      public JavaScriptNode createDeleteNode() {
         JavaScriptNode element = Environment.this.factory.createConstantString(this.getName());
         JavaScriptNode object = Environment.this.factory.createGlobalObject();
         return Environment.this.factory.createDeleteProperty(object, element, Environment.this.isStrictMode(), Environment.this.context);
      }

      @Override
      public Environment.VarRef withRequired(boolean required) {
         return this.required != required ? Environment.this.new GlobalVarRef(this.getName(), required) : this;
      }
   }

   public class MappedArgumentVarRef extends Environment.AbstractArgumentsVarRef {
      private final JSFrameSlot frameSlot;
      private final int parameterIndex;

      public MappedArgumentVarRef(JSFrameSlot frameSlot, int scopeLevel, int frameLevel, TruffleString name, Environment current) {
         super(scopeLevel, frameLevel, name, current);

         assert !JSFrameUtil.hasTemporalDeadZone(frameSlot);

         assert current.function().hasSimpleParameterList();

         assert !current.function().isDirectArgumentsAccess();

         assert frameSlot.getMappedParameterIndex() != -1;

         this.frameSlot = frameSlot;
         this.parameterIndex = frameSlot.getMappedParameterIndex();
      }

      private Environment.VarRef findArgumentsObject() {
         return Environment.this.findInternalSlot(FunctionEnvironment.ARGUMENTS_SLOT_IDENTIFIER, false, this.getFrameLevel());
      }

      @Override
      public JavaScriptNode createReadNode() {
         Environment.VarRef argumentsObject = this.findArgumentsObject();
         ReadElementNode readArgumentsObjectElement = Environment.this.factory
            .createReadElementNode(
               Environment.this.context, argumentsObject.createReadNode(), Environment.this.factory.createConstantInteger(this.parameterIndex)
            );
         return Environment.this.factory
            .createGuardDisconnectedArgumentRead(this.parameterIndex, readArgumentsObjectElement, argumentsObject.createReadNode(), this.frameSlot);
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         Environment.VarRef argumentsObject = this.findArgumentsObject();
         WriteElementNode writeArgumentsObjectElement = Environment.this.factory
            .createWriteElementNode(
               argumentsObject.createReadNode(), Environment.this.factory.createConstantInteger(this.parameterIndex), null, Environment.this.context, false
            );
         return Environment.this.factory
            .createGuardDisconnectedArgumentWrite(this.parameterIndex, writeArgumentsObjectElement, argumentsObject.createReadNode(), rhs, this.frameSlot);
      }
   }

   public abstract static class VarRef {
      protected final Object name;

      protected VarRef(Object name) {
         assert name == null || JSFrameSlot.isAllowedIdentifierType(name) : name;

         this.name = name;
      }

      public abstract JavaScriptNode createReadNode();

      public abstract JavaScriptNode createWriteNode(JavaScriptNode rhs);

      public abstract boolean isFunctionLocal();

      public boolean isFrameVar() {
         return this.getFrameSlot() != null;
      }

      public abstract boolean isGlobal();

      public boolean isConst() {
         return false;
      }

      public JSFrameSlot getFrameSlot() {
         return null;
      }

      public TruffleString getName() {
         assert this.name instanceof TruffleString : this.name;

         return (TruffleString)this.name;
      }

      public abstract JavaScriptNode createDeleteNode();

      public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> createCompoundAssignNode() {
         return new Pair<>(this::createReadNode, this::createWriteNode);
      }

      public Environment.VarRef withTDZCheck() {
         return this;
      }

      public Environment.VarRef withRequired(boolean required) {
         return this;
      }

      public boolean hasTDZCheck() {
         return false;
      }

      public boolean hasBeenDeclared() {
         JSFrameSlot frameSlot = this.getFrameSlot();
         return frameSlot != null && (frameSlot.hasBeenDeclared() || !JSFrameUtil.hasTemporalDeadZone(frameSlot));
      }

      public void setHasBeenDeclared(boolean declared) {
         JSFrameSlot frameSlot = this.getFrameSlot();
         if (frameSlot != null && frameSlot.hasBeenDeclared() != declared) {
            frameSlot.setHasBeenDeclared(declared);
         }
      }

      @Override
      public String toString() {
         return this.getClass().getSimpleName() + "(" + this.getName() + ")";
      }
   }

   static enum WrapAccess {
      Read,
      Write,
      Delete;
   }

   @FunctionalInterface
   interface WrapClosure {
      JavaScriptNode apply(JavaScriptNode node, Environment.WrapAccess access);

      default Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> applyCompound(
         Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> suppliers
      ) {
         Supplier<JavaScriptNode> readSupplier = suppliers.getFirst();
         UnaryOperator<JavaScriptNode> writeSupplier = suppliers.getSecond();
         return new Pair<>(
            () -> this.apply(readSupplier.get(), Environment.WrapAccess.Read), rhs -> this.apply(writeSupplier.apply(rhs), Environment.WrapAccess.Write)
         );
      }

      static Environment.WrapClosure compose(Environment.WrapClosure inner, Environment.WrapClosure before) {
         Objects.requireNonNull(before);
         return inner == null
            ? before
            : new Environment.WrapClosure() {
               @Override
               public JavaScriptNode apply(JavaScriptNode v, Environment.WrapAccess w) {
                  return inner.apply(before.apply(v, w), w);
               }

               @Override
               public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> applyCompound(
                  Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> suppliers
               ) {
                  return inner.applyCompound(before.applyCompound(suppliers));
               }
            };
      }
   }

   public class WrappedVarRef extends Environment.VarRef {
      private final Environment.VarRef wrappee;
      private final Environment.WrapClosure wrapClosure;

      public WrappedVarRef(Object name, Environment.VarRef wrappee, Environment.WrapClosure wrapClosure) {
         super(name);
         this.wrappee = wrappee;
         this.wrapClosure = wrapClosure;

         assert !(wrappee instanceof Environment.WrappedVarRef);
      }

      @Override
      public JavaScriptNode createReadNode() {
         return this.wrapClosure.apply(this.wrappee.createReadNode(), Environment.WrapAccess.Read);
      }

      @Override
      public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
         JavaScriptNode writeNode = (JavaScriptNode)(this.wrappee.isConst()
            ? Environment.this.factory.createWriteConstantVariable(rhs, true)
            : this.wrappee.createWriteNode(rhs));
         return this.wrapClosure.apply(writeNode, Environment.WrapAccess.Write);
      }

      @Override
      public JavaScriptNode createDeleteNode() {
         return this.wrapClosure.apply(this.wrappee.createDeleteNode(), Environment.WrapAccess.Delete);
      }

      @Override
      public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> createCompoundAssignNode() {
         return this.wrapClosure.applyCompound(this.wrappee.createCompoundAssignNode());
      }

      @Override
      public boolean isFunctionLocal() {
         return this.wrappee.isFunctionLocal();
      }

      @Override
      public JSFrameSlot getFrameSlot() {
         return null;
      }

      @Override
      public boolean isGlobal() {
         return this.wrappee.isGlobal();
      }

      public Environment.VarRef getWrappee() {
         return this.wrappee;
      }

      @Override
      public Environment.VarRef withTDZCheck() {
         return Environment.this.new WrappedVarRef(this.name, this.wrappee.withTDZCheck(), this.wrapClosure);
      }

      @Override
      public Environment.VarRef withRequired(boolean required) {
         return Environment.this.new WrappedVarRef(this.name, this.wrappee.withRequired(required), this.wrapClosure);
      }

      @Override
      public boolean hasTDZCheck() {
         return this.wrappee.hasTDZCheck();
      }
   }
}
