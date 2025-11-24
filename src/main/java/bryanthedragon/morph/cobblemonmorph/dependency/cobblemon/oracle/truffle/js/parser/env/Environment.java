
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
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.parser.env.BlockEnvironment;
import com.oracle.truffle.js.parser.env.DebugEnvironment;
import com.oracle.truffle.js.parser.env.FunctionEnvironment;
import com.oracle.truffle.js.parser.env.GlobalEnvironment;
import com.oracle.truffle.js.parser.env.WithEnvironment;
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

    public VarRef findThisVar() {
        return this.findInternalSlot(FunctionEnvironment.THIS_SLOT_IDENTIFIER, true);
    }

    public void reserveThisSlot() {
        this.declareInternalSlot(FunctionEnvironment.THIS_SLOT_IDENTIFIER);
    }

    public VarRef findSuperVar() {
        assert (!this.function().isGlobal());
        return this.findInternalSlot(FunctionEnvironment.SUPER_SLOT_IDENTIFIER);
    }

    public void reserveSuperSlot() {
        this.declareInternalSlot(FunctionEnvironment.SUPER_SLOT_IDENTIFIER);
    }

    public VarRef findArgumentsVar() {
        assert (!this.function().isGlobal());
        return this.findInternalSlot(FunctionEnvironment.ARGUMENTS_SLOT_IDENTIFIER);
    }

    public void reserveArgumentsSlot() {
        this.declareInternalSlot(FunctionEnvironment.ARGUMENTS_SLOT_IDENTIFIER);
    }

    public VarRef findNewTargetVar() {
        assert (!this.function().isGlobal());
        return this.findInternalSlot(FunctionEnvironment.NEW_TARGET_SLOT_IDENTIFIER);
    }

    public void reserveNewTargetSlot() {
        this.declareInternalSlot(FunctionEnvironment.NEW_TARGET_SLOT_IDENTIFIER);
    }

    public VarRef findAsyncContextVar() {
        assert (!this.function().isGlobal() || this.function().isAsyncGeneratorFunction());
        this.declareLocalVar(FunctionEnvironment.ASYNC_CONTEXT_SLOT_IDENTIFIER);
        return this.findInternalSlot(FunctionEnvironment.ASYNC_CONTEXT_SLOT_IDENTIFIER);
    }

    public VarRef findAsyncResultVar() {
        assert (!this.function().isGlobal() || this.function().isAsyncGeneratorFunction());
        this.declareLocalVar(FunctionEnvironment.ASYNC_RESULT_SLOT_IDENTIFIER);
        return this.findInternalSlot(FunctionEnvironment.ASYNC_RESULT_SLOT_IDENTIFIER);
    }

    public VarRef findYieldValueVar() {
        assert (!this.function().isGlobal());
        this.declareLocalVar(FunctionEnvironment.YIELD_VALUE_SLOT_IDENTIFIER);
        return this.findInternalSlot(FunctionEnvironment.YIELD_VALUE_SLOT_IDENTIFIER);
    }

    public VarRef findDynamicScopeVar() {
        assert (!this.function().isGlobal());
        return this.findInternalSlot(FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER);
    }

    public void reserveDynamicScopeSlot() {
        assert (!this.function().isGlobal());
        this.declareInternalSlot(FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER);
    }

    public JSFrameSlot declareInternalSlot(Object name) {
        throw this.unsupported();
    }

    public final JavaScriptNode createLocal(JSFrameSlot frameSlot, int frameLevel, int scopeLevel) {
        return this.factory.createReadFrameSlot(frameSlot, this.factory.createScopeFrame(frameLevel, scopeLevel, this.getBlockScopeSlot(frameLevel, scopeLevel)), false);
    }

    public final VarRef findInternalSlot(Object name) {
        return this.findInternalSlot(name, false, 0);
    }

    public final VarRef findInternalSlot(Object name, boolean allowDebug) {
        return this.findInternalSlot(name, allowDebug, 0);
    }

    protected final VarRef findInternalSlot(Object name, boolean allowDebug, int skippedFrames) {
        Environment current = this;
        int frameLevel = 0;
        int scopeLevel = 0;
        do {
            if (frameLevel >= skippedFrames) {
                int effectiveScopeLevel = scopeLevel;
                JSFrameSlot slot = current.findBlockFrameSlot(name);
                if (slot == null) {
                    slot = current.findFunctionFrameSlot(name);
                    effectiveScopeLevel += current.getScopeLevel();
                }
                if (slot != null) {
                    return new FrameSlotVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
                }
            }
            if (current instanceof FunctionEnvironment) {
                ++frameLevel;
                scopeLevel = 0;
                continue;
            }
            if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
                ++scopeLevel;
                continue;
            }
            if (!(current instanceof DebugEnvironment) || !(name instanceof TruffleString) || !allowDebug || !((DebugEnvironment)current).hasMember((TruffleString)name)) continue;
            return new DebugVarRef((TruffleString)name, frameLevel);
        } while ((current = current.getParent()) != null);
        return null;
    }

    public final VarRef findLocalVar(TruffleString name) {
        return this.findVar(name, true, true, false, true, true, false);
    }

    public final VarRef findVar(TruffleString name, boolean skipWith) {
        return this.findVar(name, skipWith, skipWith, false, false, false, false);
    }

    public final VarRef findBlockScopedVar(TruffleString name) {
        return this.findVar(name, true, true, false, true, true, true);
    }

    public final VarRef findVar(TruffleString name, boolean skipWith, boolean skipEval, boolean skipBlockScoped, boolean skipGlobal, boolean skipMapped) {
        return this.findVar(name, skipWith, skipEval, skipBlockScoped, skipGlobal, skipMapped, false);
    }

    public final VarRef findVar(TruffleString name, boolean skipWith, boolean skipEval, boolean skipBlockScoped, boolean skipGlobal, boolean skipMapped, boolean skipVar) {
        Environment current = this;
        int frameLevel = 0;
        int scopeLevel = 0;
        WrapClosure wrapClosure = null;
        int wrapFrameLevel = 0;
        do {
            if (current instanceof WithEnvironment) {
                if (skipWith) continue;
                wrapClosure = this.makeWithWrapClosure(wrapClosure, name, ((WithEnvironment)current).getWithVarIdentifier());
                wrapFrameLevel = frameLevel;
                continue;
            }
            if (current instanceof GlobalEnvironment) {
                GlobalEnvironment globalEnv = (GlobalEnvironment)current;
                if (globalEnv.hasLexicalDeclaration(name)) {
                    return this.wrapIn(wrapClosure, wrapFrameLevel, new GlobalLexVarRef(name, globalEnv.hasConstDeclaration(name), globalEnv));
                }
                if (globalEnv.hasVarDeclaration(name)) continue;
                wrapClosure = this.makeGlobalWrapClosure(wrapClosure, name);
                continue;
            }
            if (current instanceof DebugEnvironment) {
                if (!((DebugEnvironment)current).hasMember(name)) continue;
                wrapClosure = this.makeDebugWrapClosure(wrapClosure, name, frameLevel);
                wrapFrameLevel = frameLevel;
                continue;
            }
            int effectiveScopeLevel = scopeLevel;
            JSFrameSlot slot = current.findBlockFrameSlot(name);
            if (slot == null) {
                slot = current.findFunctionFrameSlot(name);
                effectiveScopeLevel += current.getScopeLevel();
            }
            if (!(slot == null || skipBlockScoped && (JSFrameUtil.isConst(slot) || JSFrameUtil.isLet(slot)) || skipVar && !JSFrameUtil.isConst(slot) && !JSFrameUtil.isLet(slot))) {
                AbstractFrameVarRef varRef;
                if (!skipMapped && Environment.isMappedArgumentsParameter(slot, current)) {
                    varRef = new MappedArgumentVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
                } else if (JSFrameUtil.isArguments(slot)) {
                    assert (!(current.function().isArrowFunction() || current.function().isGlobal() || current.function().isEval()));
                    varRef = new ArgumentsVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
                } else {
                    assert (frameLevel == 0 || JSFrameUtil.isClosedOver(slot) || current.getScope() != null && current.getScope().hasNestedEval()) : slot;
                    varRef = new FrameSlotVarRef(slot, effectiveScopeLevel, frameLevel, name, current);
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
                    return this.wrapIn(wrapClosure, wrapFrameLevel, new FunctionCalleeVarRef(frameLevel, name, current));
                }
                ++frameLevel;
                scopeLevel = 0;
                continue;
            }
            if (!(current instanceof BlockEnvironment) || !current.hasScopeFrame()) continue;
            ++scopeLevel;
        } while ((current = current.getParent()) != null);
        if (skipGlobal) {
            return null;
        }
        return this.wrapIn(wrapClosure, wrapFrameLevel, new GlobalVarRef(name));
    }

    void ensureFrameLevelAvailable(int frameLevel) {
        int level = 0;
        for (FunctionEnvironment currentFunction = this.function(); currentFunction != null && level < frameLevel; currentFunction = currentFunction.getParentFunction(), ++level) {
            currentFunction.setNeedsParentFrame(true);
        }
    }

    private WrapClosure makeEvalWrapClosure(WrapClosure wrapClosure, final TruffleString name, final int frameLevel, final int scopeLevel, final Environment current) {
        final JSFrameSlot dynamicScopeSlot = current.findBlockFrameSlot(FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER);
        assert (dynamicScopeSlot != null);
        return WrapClosure.compose(wrapClosure, new WrapClosure(){

            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, WrapAccess access) {
                JSTargetableNode scopeAccessNode;
                JavaScriptNode dynamicScopeNode = new FrameSlotVarRef(dynamicScopeSlot, scopeLevel, frameLevel, FunctionEnvironment.DYNAMIC_SCOPE_IDENTIFIER, current).createReadNode();
                if (access == WrapAccess.Delete) {
                    scopeAccessNode = Environment.this.factory.createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
                } else if (access == WrapAccess.Write) {
                    assert (delegateNode instanceof WriteNode) : delegateNode;
                    scopeAccessNode = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, Environment.this.isStrictMode());
                } else if (access == WrapAccess.Read) {
                    assert (delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode) : delegateNode;
                    scopeAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
                } else {
                    throw new IllegalArgumentException();
                }
                return new EvalVariableNode(Environment.this.context, name, delegateNode, dynamicScopeNode, scopeAccessNode);
            }
        });
    }

    private WrapClosure makeWithWrapClosure(WrapClosure wrapClosure, final TruffleString name, final Object withVarName) {
        return WrapClosure.compose(wrapClosure, new WrapClosure(){

            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, WrapAccess access) {
                JSTargetableNode withAccessNode;
                VarRef withVarNameRef = Objects.requireNonNull(Environment.this.findInternalSlot(withVarName));
                if (access == WrapAccess.Delete) {
                    withAccessNode = Environment.this.factory.createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
                } else if (access == WrapAccess.Write) {
                    assert (delegateNode instanceof WriteNode) : delegateNode;
                    withAccessNode = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, Environment.this.isStrictMode(), false, true);
                } else if (access == WrapAccess.Read) {
                    assert (delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode) : delegateNode;
                    withAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
                } else {
                    throw new IllegalArgumentException();
                }
                JavaScriptNode withTarget = Environment.this.factory.createWithTarget(Environment.this.context, name, withVarNameRef.createReadNode());
                return Environment.this.factory.createWithVarWrapper(name, withTarget, withAccessNode, delegateNode);
            }

            @Override
            public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> applyCompound(Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> suppliers) {
                VarRef withTargetTempVar = Environment.this.createTempVar();
                VarRef withObjVar = Objects.requireNonNull(Environment.this.findInternalSlot(withVarName));
                Supplier<JavaScriptNode> innerReadSupplier = suppliers.getFirst();
                UnaryOperator<JavaScriptNode> innerWriteSupplier = suppliers.getSecond();
                Supplier<JavaScriptNode> readSupplier = () -> {
                    JSTargetableNode readWithProperty = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
                    return Environment.this.factory.createWithVarWrapper(name, withTargetTempVar.createReadNode(), readWithProperty, (JavaScriptNode)innerReadSupplier.get());
                };
                UnaryOperator writeSupplier = rhs -> {
                    JavaScriptNode withTarget = Environment.this.factory.createWithTarget(Environment.this.context, name, withObjVar.createReadNode());
                    WritePropertyNode writeWithProperty = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, Environment.this.isStrictMode(), false, true);
                    return Environment.this.factory.createWithVarWrapper(name, withTargetTempVar.createWriteNode(withTarget), writeWithProperty, (JavaScriptNode)innerWriteSupplier.apply((JavaScriptNode)rhs));
                };
                return new Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>>(readSupplier, writeSupplier);
            }
        });
    }

    private WrapClosure makeGlobalWrapClosure(WrapClosure wrapClosure, final TruffleString name) {
        return WrapClosure.compose(wrapClosure, new WrapClosure(){

            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, WrapAccess access) {
                JSTargetableNode scopeAccessNode;
                if (access == WrapAccess.Delete) {
                    scopeAccessNode = Environment.this.factory.createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
                } else if (access == WrapAccess.Write) {
                    assert (delegateNode instanceof WriteNode) : delegateNode;
                    scopeAccessNode = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, true);
                } else if (access == WrapAccess.Read) {
                    assert (delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode) : delegateNode;
                    scopeAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
                } else {
                    throw new IllegalArgumentException();
                }
                JavaScriptNode globalScope = Environment.this.factory.createGlobalScope(Environment.this.context);
                return Environment.this.factory.createGlobalVarWrapper(name, delegateNode, globalScope, scopeAccessNode);
            }
        });
    }

    private WrapClosure makeDebugWrapClosure(WrapClosure wrapClosure, final TruffleString name, final int frameLevel) {
        this.ensureFrameLevelAvailable(frameLevel);
        return WrapClosure.compose(wrapClosure, new WrapClosure(){

            @Override
            public JavaScriptNode apply(JavaScriptNode delegateNode, WrapAccess access) {
                JSTargetableNode scopeAccessNode;
                if (access == WrapAccess.Delete) {
                    scopeAccessNode = Environment.this.factory.createDeleteProperty(null, Environment.this.factory.createConstantString(name), Environment.this.isStrictMode(), Environment.this.context);
                } else if (access == WrapAccess.Write) {
                    assert (delegateNode instanceof WriteNode) : delegateNode;
                    scopeAccessNode = Environment.this.factory.createWriteProperty(null, name, null, Environment.this.context, true);
                } else if (access == WrapAccess.Read) {
                    assert (delegateNode instanceof ReadNode || delegateNode instanceof RepeatableNode) : delegateNode;
                    scopeAccessNode = Environment.this.factory.createReadProperty(Environment.this.context, null, name);
                } else {
                    throw new IllegalArgumentException();
                }
                JavaScriptNode debugScope = Environment.this.factory.createDebugScope(Environment.this.context, Environment.this.factory.createAccessCallee(frameLevel - 1));
                return Environment.this.factory.createDebugVarWrapper(name, delegateNode, debugScope, scopeAccessNode);
            }
        });
    }

    private VarRef wrapIn(WrapClosure wrapClosure, int wrapFrameLevel, VarRef wrappee) {
        if (wrapClosure != null) {
            this.ensureFrameLevelAvailable(wrapFrameLevel);
            return new WrappedVarRef(wrappee.getName(), wrappee, wrapClosure);
        }
        return wrappee;
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
        do {
            if (currentFrameLevel == frameLevel && currentScopeLevel == scopeLevel) {
                return current;
            }
            if (current instanceof FunctionEnvironment) {
                ++currentFrameLevel;
                currentScopeLevel = 0;
                continue;
            }
            if (!(current instanceof BlockEnvironment) || !current.hasScopeFrame()) continue;
            ++currentScopeLevel;
        } while ((current = current.getParent()) != null);
        return null;
    }

    public VarRef createTempVar() {
        JSFrameSlot var = this.declareTempVar(Strings.constant("tmp"));
        return this.findTempVar(var);
    }

    public VarRef findTempVar(final JSFrameSlot var) {
        return new VarRef(var.getIdentifier()){

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
        for (int currentFrameLevel = frameLevel; currentFrameLevel > 0; --currentFrameLevel) {
            current = current.function().getParent();
        }
        int currentScopeLevel = scopeLevel;
        while (current != null) {
            if (current instanceof FunctionEnvironment) {
                assert (currentScopeLevel == 0);
                return null;
            }
            if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
                if (currentScopeLevel == 0) {
                    return this.function().getBlockScopeSlot();
                }
                --currentScopeLevel;
            }
            current = current.getParent();
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
            if (!symbol.isBlockScoped() && (onlyBlockScoped || !symbol.isVar() || symbol.isGlobal() || symbol.isThis() || symbol.isSuper() || symbol.isNewTarget()) || symbol.isFunctionSelf() || filter != null && !filter.test(symbol)) continue;
            this.addFrameSlotFromSymbol(symbol);
        }
    }

    public void addFrameSlotFromSymbol(Symbol symbol) {
        assert (!this.getBlockFrameDescriptor().contains(symbol.getNameTS()) || this instanceof FunctionEnvironment) : symbol;
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

    public VarRef findActiveModule() {
        Environment current = this;
        int frameLevel = 0;
        int scopeLevel = 0;
        while (current.getParent() != null) {
            if (current instanceof FunctionEnvironment) {
                assert (!((FunctionEnvironment)current).isModule());
                ((FunctionEnvironment)current).setNeedsParentFrame(true);
                ++frameLevel;
                scopeLevel = 0;
            } else if (current instanceof BlockEnvironment && current.hasScopeFrame()) {
                ++scopeLevel;
            }
            current = current.getParent();
        }
        assert (current instanceof FunctionEnvironment && ((FunctionEnvironment)current).isModule());
        return new ActiveModuleRef(scopeLevel, frameLevel, current);
    }

    protected String toStringImpl(Map<String, Integer> state) {
        return this.getClass().getSimpleName();
    }

    protected static String joinElements(Iterable<? extends Object> keySet) {
        StringJoiner sj = new StringJoiner(", ", "{", "}");
        for (Object object : keySet) {
            sj.add(String.valueOf(object));
        }
        return sj.toString();
    }

    public String toString() {
        StringJoiner output = new StringJoiner("\n");
        HashMap<String, Integer> state = new HashMap<String, Integer>();
        Environment current = this;
        do {
            output.add(current.toStringImpl(state));
        } while ((current = current.getParent()) != null);
        return output.toString();
    }

    final class ActiveModuleRef
    extends AbstractArgumentsVarRef {
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

    class DebugVarRef
    extends VarRef {
        private final int frameLevel;

        DebugVarRef(TruffleString name, int frameLevel) {
            super(name);
            this.frameLevel = frameLevel;
            Environment.this.ensureFrameLevelAvailable(frameLevel);
        }

        @Override
        public JavaScriptNode createReadNode() {
            JavaScriptNode debugScope = Environment.this.factory.createDebugScope(Environment.this.context, Environment.this.factory.createAccessCallee(this.frameLevel - 1));
            return Environment.this.factory.createDebugVarWrapper(this.getName(), Environment.this.factory.createConstantUndefined(), debugScope, Environment.this.factory.createReadProperty(Environment.this.context, null, this.getName()));
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

    public class WrappedVarRef
    extends VarRef {
        private final VarRef wrappee;
        private final WrapClosure wrapClosure;

        public WrappedVarRef(Object name, VarRef wrappee, WrapClosure wrapClosure) {
            super(name);
            this.wrappee = wrappee;
            this.wrapClosure = wrapClosure;
            assert (!(wrappee instanceof WrappedVarRef));
        }

        @Override
        public JavaScriptNode createReadNode() {
            return this.wrapClosure.apply(this.wrappee.createReadNode(), WrapAccess.Read);
        }

        @Override
        public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
            JavaScriptNode writeNode = this.wrappee.isConst() ? Environment.this.factory.createWriteConstantVariable(rhs, true) : this.wrappee.createWriteNode(rhs);
            return this.wrapClosure.apply(writeNode, WrapAccess.Write);
        }

        @Override
        public JavaScriptNode createDeleteNode() {
            return this.wrapClosure.apply(this.wrappee.createDeleteNode(), WrapAccess.Delete);
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

        public VarRef getWrappee() {
            return this.wrappee;
        }

        @Override
        public VarRef withTDZCheck() {
            return new WrappedVarRef(this.name, this.wrappee.withTDZCheck(), this.wrapClosure);
        }

        @Override
        public VarRef withRequired(boolean required) {
            return new WrappedVarRef(this.name, this.wrappee.withRequired(required), this.wrapClosure);
        }

        @Override
        public boolean hasTDZCheck() {
            return this.wrappee.hasTDZCheck();
        }
    }

    public class GlobalLexVarRef
    extends VarRef {
        private final boolean isConst;
        private final boolean required;
        private final boolean checkTDZ;
        private final GlobalEnvironment globalEnv;

        public GlobalLexVarRef(TruffleString name, boolean isConst, GlobalEnvironment globalEnv) {
            this(name, isConst, globalEnv, true, false);
        }

        private GlobalLexVarRef(Object name, boolean isConst, GlobalEnvironment globalEnv, boolean required, boolean checkTDZ) {
            super(name);
            assert (name instanceof TruffleString && !name.equals(Null.NAME) && !GlobalEnvironment.isGlobalObjectConstant((TruffleString)name)) : name;
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
            }
            return Environment.this.factory.createReadLexicalGlobal(this.getName(), this.checkTDZ, Environment.this.context);
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
        public VarRef withRequired(boolean required) {
            if (this.required != required) {
                return new GlobalLexVarRef(this.name, this.isConst, this.globalEnv, required, this.checkTDZ);
            }
            return this;
        }

        @Override
        public VarRef withTDZCheck() {
            if (!this.checkTDZ) {
                return new GlobalLexVarRef(this.name, this.isConst, this.globalEnv, this.required, true);
            }
            return this;
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

    public class GlobalVarRef
    extends VarRef {
        private final boolean required;

        public GlobalVarRef(TruffleString name) {
            this(name, true);
        }

        private GlobalVarRef(TruffleString name, boolean required) {
            super(name);
            assert (!Null.NAME.equals(name));
            this.required = required;
        }

        @Override
        public JavaScriptNode createReadNode() {
            if (this.name.equals(Undefined.NAME)) {
                return Environment.this.factory.createConstantUndefined();
            }
            if (!this.required) {
                return Environment.this.factory.createReadProperty(Environment.this.context, Environment.this.factory.createGlobalObject(), this.getName());
            }
            return Environment.this.factory.createReadGlobalProperty(Environment.this.context, this.getName());
        }

        @Override
        public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
            return Environment.this.factory.createWriteProperty(Environment.this.factory.createGlobalObject(), this.getName(), rhs, Environment.this.context, Environment.this.isStrictMode(), this.isGlobal(), this.required);
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
        public VarRef withRequired(boolean required) {
            if (this.required != required) {
                return new GlobalVarRef(this.getName(), required);
            }
            return this;
        }
    }

    public class MappedArgumentVarRef
    extends AbstractArgumentsVarRef {
        private final JSFrameSlot frameSlot;
        private final int parameterIndex;

        public MappedArgumentVarRef(JSFrameSlot frameSlot, int scopeLevel, int frameLevel, TruffleString name, Environment current) {
            super(scopeLevel, frameLevel, name, current);
            assert (!JSFrameUtil.hasTemporalDeadZone(frameSlot));
            assert (current.function().hasSimpleParameterList());
            assert (!current.function().isDirectArgumentsAccess());
            assert (frameSlot.getMappedParameterIndex() != -1);
            this.frameSlot = frameSlot;
            this.parameterIndex = frameSlot.getMappedParameterIndex();
        }

        private VarRef findArgumentsObject() {
            return Environment.this.findInternalSlot(FunctionEnvironment.ARGUMENTS_SLOT_IDENTIFIER, false, this.getFrameLevel());
        }

        @Override
        public JavaScriptNode createReadNode() {
            VarRef argumentsObject = this.findArgumentsObject();
            ReadElementNode readArgumentsObjectElement = Environment.this.factory.createReadElementNode(Environment.this.context, argumentsObject.createReadNode(), Environment.this.factory.createConstantInteger(this.parameterIndex));
            return Environment.this.factory.createGuardDisconnectedArgumentRead(this.parameterIndex, readArgumentsObjectElement, argumentsObject.createReadNode(), this.frameSlot);
        }

        @Override
        public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
            VarRef argumentsObject = this.findArgumentsObject();
            WriteElementNode writeArgumentsObjectElement = Environment.this.factory.createWriteElementNode(argumentsObject.createReadNode(), Environment.this.factory.createConstantInteger(this.parameterIndex), null, Environment.this.context, false);
            return Environment.this.factory.createGuardDisconnectedArgumentWrite(this.parameterIndex, writeArgumentsObjectElement, argumentsObject.createReadNode(), rhs, this.frameSlot);
        }
    }

    private final class ArgumentsVarRef
    extends AbstractArgumentsVarRef {
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
                JavaScriptNode createArgumentsObjectNode = Environment.this.factory.createArgumentsObjectNode(Environment.this.context, Environment.this.isStrictMode(), currentFunction.getLeadingArgumentCount());
                JSWriteFrameSlotNode writeNode = Environment.this.factory.createWriteFrameSlot(this.frameSlot, this.createScopeFrameNode(), createArgumentsObjectNode);
                return Environment.this.factory.createAccessArgumentsArrayDirectly(writeNode, argumentsVarNode, currentFunction.getLeadingArgumentCount());
            }
            return argumentsVarNode;
        }

        @Override
        public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
            assert (!this.resolvedEnv.function().isDirectArgumentsAccess());
            return Environment.this.factory.createWriteFrameSlot(this.frameSlot, this.createScopeFrameNode(), rhs);
        }

        @Override
        public JSFrameSlot getFrameSlot() {
            return this.frameSlot;
        }
    }

    private final class FunctionCalleeVarRef
    extends AbstractArgumentsVarRef {
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

    private abstract class AbstractArgumentsVarRef
    extends AbstractFrameVarRef {
        AbstractArgumentsVarRef(int scopeLevel, int frameLevel, TruffleString name, Environment current) {
            super(scopeLevel, frameLevel, name, current);
        }

        @Override
        public JSFrameSlot getFrameSlot() {
            return null;
        }
    }

    public class FrameSlotVarRef
    extends AbstractFrameVarRef {
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
            if (JSFrameUtil.isImportBinding(this.frameSlot)) {
                return Environment.this.factory.createReadImportBinding(readNode);
            }
            return readNode;
        }

        @Override
        public JavaScriptNode createWriteNode(JavaScriptNode rhs) {
            return Environment.this.factory.createWriteFrameSlot(this.frameSlot, this.createScopeFrameNode(), rhs, this.checkTDZ);
        }

        @Override
        public VarRef withTDZCheck() {
            if (this.checkTDZ || !JSFrameUtil.hasTemporalDeadZone(this.frameSlot)) {
                return this;
            }
            return new FrameSlotVarRef(this.frameSlot, this.scopeLevel, this.frameLevel, this.name, this.resolvedEnv, true);
        }

        @Override
        public boolean hasTDZCheck() {
            return this.checkTDZ;
        }
    }

    public abstract class AbstractFrameVarRef
    extends VarRef {
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
            }
            if (this.isInCurrentFunctionFrame()) {
                return null;
            }
            return this.resolvedEnv.getCurrentBlockScopeSlot();
        }

        private int getEffectiveScopeLevel() {
            if (this.isInCurrentFunctionFrame()) {
                return 0;
            }
            return this.scopeLevel;
        }

        protected boolean isInCurrentFunctionFrame() {
            return this.frameLevel == 0 && this.scopeLevel == Environment.this.getScopeLevel();
        }
    }

    public static abstract class VarRef {
        protected final Object name;

        protected VarRef(Object name) {
            assert (name == null || JSFrameSlot.isAllowedIdentifierType(name)) : name;
            this.name = name;
        }

        public abstract JavaScriptNode createReadNode();

        public abstract JavaScriptNode createWriteNode(JavaScriptNode var1);

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
            assert (this.name instanceof TruffleString) : this.name;
            return (TruffleString)this.name;
        }

        public abstract JavaScriptNode createDeleteNode();

        public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> createCompoundAssignNode() {
            return new Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>>(this::createReadNode, this::createWriteNode);
        }

        public VarRef withTDZCheck() {
            return this;
        }

        public VarRef withRequired(boolean required) {
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

        public String toString() {
            return this.getClass().getSimpleName() + "(" + this.getName() + ")";
        }
    }

    @FunctionalInterface
    static interface WrapClosure {
        public JavaScriptNode apply(JavaScriptNode var1, WrapAccess var2);

        default public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> applyCompound(Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> suppliers) {
            Supplier<JavaScriptNode> readSupplier = suppliers.getFirst();
            UnaryOperator<JavaScriptNode> writeSupplier = suppliers.getSecond();
            return new Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>>(() -> this.apply((JavaScriptNode)readSupplier.get(), WrapAccess.Read), rhs -> this.apply((JavaScriptNode)writeSupplier.apply((JavaScriptNode)rhs), WrapAccess.Write));
        }

        public static WrapClosure compose(final WrapClosure inner, final WrapClosure before) {
            Objects.requireNonNull(before);
            if (inner == null) {
                return before;
            }
            return new WrapClosure(){

                @Override
                public JavaScriptNode apply(JavaScriptNode v, WrapAccess w) {
                    return inner.apply(before.apply(v, w), w);
                }

                @Override
                public Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> applyCompound(Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> suppliers) {
                    return inner.applyCompound(before.applyCompound(suppliers));
                }
            };
        }
    }

    static enum WrapAccess {
        Read,
        Write,
        Delete;

    }
}

