
package com.oracle.truffle.js.parser.env;

import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.util.DebugCounter;
import java.util.Map;
import java.util.Objects;

public final class BlockEnvironment
extends Environment {
    private final JSFrameDescriptor blockFrameDescriptor;
    private final JSFrameSlot parentSlot;
    private final int scopeLevel;
    private final boolean isFunctionBlock;
    private final Scope scope;
    private final JSFrameSlot blockScopeSlot;
    private int frameStart = -1;
    private int frameEnd = -1;
    private static final DebugCounter reifiedScopes = DebugCounter.create("Reified scopes");
    private static final DebugCounter virtualScopes = DebugCounter.create("Virtual scopes");

    public BlockEnvironment(Environment parent, NodeFactory factory, JSContext context, Scope blockScope) {
        super(parent, factory, context);
        this.isFunctionBlock = blockScope != null && (blockScope.isFunctionTopScope() || blockScope.isEvalScope());
        this.scope = blockScope;
        if (BlockEnvironment.isScopeCaptured(blockScope) || !context.getContextOptions().isScopeOptimization()) {
            this.blockFrameDescriptor = factory.createBlockFrameDescriptor();
            this.parentSlot = Objects.requireNonNull(this.blockFrameDescriptor.findFrameSlot(ScopeFrameNode.PARENT_SCOPE_IDENTIFIER));
            this.scopeLevel = parent.getScopeLevel() + 1;
            this.blockScopeSlot = parent.function().getOrCreateBlockScopeSlot();
            reifiedScopes.inc();
        } else {
            this.blockFrameDescriptor = null;
            this.parentSlot = null;
            this.scopeLevel = parent.getScopeLevel();
            this.blockScopeSlot = parent.getCurrentBlockScopeSlot();
            virtualScopes.inc();
        }
    }

    public static boolean isScopeCaptured(Scope blockScope) {
        return blockScope == null || blockScope.hasClosures() || blockScope.hasNestedEval() || blockScope.isClassHeadScope();
    }

    public BlockEnvironment(Environment parent, NodeFactory factory, JSContext context) {
        this(parent, factory, context, null);
    }

    @Override
    public JSFrameSlot findBlockFrameSlot(Object name) {
        if (!this.hasScopeFrame()) {
            return null;
        }
        JSFrameSlot slot = this.getBlockFrameDescriptor().findFrameSlot(this.slotId(name));
        if (slot != null && JSFrameUtil.isPrivateName(slot)) {
            return null;
        }
        return slot;
    }

    @Override
    public JSFrameSlot findFunctionFrameSlot(Object name) {
        return this.getFunctionFrameDescriptor().findFrameSlot(this.slotId(name));
    }

    @Override
    public JSFrameDescriptor getBlockFrameDescriptor() {
        if (this.blockFrameDescriptor != null) {
            return this.blockFrameDescriptor;
        }
        return this.getFunctionFrameDescriptor();
    }

    public JSFrameSlot getParentSlot() {
        return this.parentSlot;
    }

    @Override
    public int getScopeLevel() {
        return this.scopeLevel;
    }

    @Override
    public boolean hasScopeFrame() {
        return this.blockFrameDescriptor != null;
    }

    @Override
    public JSFrameSlot getCurrentBlockScopeSlot() {
        return this.blockScopeSlot;
    }

    public boolean isFunctionBlock() {
        return this.isFunctionBlock;
    }

    private Object slotId(Object name) {
        assert (JSFrameSlot.isAllowedIdentifierType(name)) : name;
        if (this.isFunctionBlock) {
            return name;
        }
        return JSFrameDescriptor.scopedIdentifier(name, this.scope);
    }

    @Override
    public JSFrameSlot declareInternalSlot(Object name) {
        return this.getBlockFrameDescriptor().findOrAddFrameSlot(this.slotId(name), 0, FrameSlotKind.Illegal);
    }

    @Override
    public void addFrameSlotFromSymbol(Symbol symbol) {
        Object id = this.slotId(symbol.getNameTS());
        assert (!(this.hasScopeFrame() && this.getBlockFrameDescriptor().contains(id) || this.getFunctionFrameDescriptor().contains(id))) : symbol;
        if (symbol.isClosedOver() || this.scope != null && this.scope.hasNestedEval() || !this.context.getContextOptions().isScopeOptimization()) {
            this.getBlockFrameDescriptor().findOrAddFrameSlot(id, symbol.getFlags(), FrameSlotKind.Illegal);
        } else {
            JSFrameSlot slot = this.getFunctionFrameDescriptor().findOrAddFrameSlot(id, symbol.getFlags() | (!this.isFunctionBlock ? Integer.MIN_VALUE : 0), FrameSlotKind.Illegal);
            if (!this.isFunctionBlock) {
                this.updateSlotRange(slot);
            }
        }
    }

    private void updateSlotRange(JSFrameSlot slot) {
        if (slot.getIndex() < this.frameStart || this.frameStart == -1) {
            this.frameStart = slot.getIndex();
        }
        if (slot.getIndex() >= this.frameEnd || this.frameEnd == -1) {
            this.frameEnd = slot.getIndex() + 1;
        }
    }

    public int getStart() {
        return this.frameStart;
    }

    public int getEnd() {
        return this.frameEnd;
    }

    @Override
    public Scope getScope() {
        return this.scope;
    }

    public boolean isGeneratorFunctionBlock() {
        return this.isFunctionBlock && this.function().isGeneratorFunction();
    }

    public boolean capturesFunctionFrame() {
        return this.scopeLevel == 1 && this.function().isModule();
    }

    @Override
    protected String toStringImpl(Map<String, Integer> state) {
        int currentFrameLevel = state.getOrDefault("frameLevel", 0);
        int currentScopeLevel = state.getOrDefault("scopeLevel", 0);
        state.put("scopeLevel", currentScopeLevel + (this.hasScopeFrame() ? 1 : 0));
        return "Block(" + currentFrameLevel + ", " + currentScopeLevel + ") size=" + this.getBlockFrameDescriptor().getSize() + " " + BlockEnvironment.joinElements(this.getBlockFrameDescriptor().getIdentifiers()) + " " + this.scope;
    }
}

