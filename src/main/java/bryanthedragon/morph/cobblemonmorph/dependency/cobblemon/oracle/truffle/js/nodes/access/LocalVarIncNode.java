
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.access.FrameSlotNode;
import com.oracle.truffle.js.nodes.access.LocalVarPostfixIncNodeGen;
import com.oracle.truffle.js.nodes.access.LocalVarPrefixIncNodeGen;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;

public abstract class LocalVarIncNode
extends FrameSlotNode.WithDescriptor {
    protected final LocalVarOp op;
    protected final boolean hasTemporalDeadZone;
    @Node.Child
    @Executed
    protected ScopeFrameNode scopeFrameNode;

    protected LocalVarIncNode(LocalVarOp op, int slot, Object identifier, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
        super(slot, identifier);
        this.op = op;
        this.hasTemporalDeadZone = hasTemporalDeadZone;
        this.scopeFrameNode = scopeFrameNode;
    }

    public static LocalVarIncNode createPrefix(Op op, JSFrameSlot frameSlot, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
        return LocalVarPrefixIncNodeGen.create(op.op, frameSlot.getIndex(), frameSlot.getIdentifier(), hasTemporalDeadZone, scopeFrameNode);
    }

    public static LocalVarIncNode createPostfix(Op op, JSFrameSlot frameSlot, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
        return LocalVarPostfixIncNodeGen.create(op.op, frameSlot.getIndex(), frameSlot.getIdentifier(), hasTemporalDeadZone, scopeFrameNode);
    }

    @Override
    public final boolean hasTemporalDeadZone() {
        return this.hasTemporalDeadZone;
    }

    @Override
    public final ScopeFrameNode getLevelFrameNode() {
        return this.scopeFrameNode;
    }

    protected static class DecOp
    extends LocalVarOp {
        protected DecOp() {
        }

        @Override
        public int doInt(int value2) {
            return Math.subtractExact(value2, 1);
        }

        @Override
        public double doDouble(double value2) {
            return value2 - 1.0;
        }

        @Override
        public Number doNumber(Number numValue, ConditionProfile isIntegerProfile, ConditionProfile isBoundaryValue) {
            if (isIntegerProfile.profile(numValue instanceof Integer)) {
                int intValue = (Integer)numValue;
                if (isBoundaryValue.profile(intValue != Integer.MIN_VALUE)) {
                    return intValue - 1;
                }
                return (double)intValue - 1.0;
            }
            double doubleValue = JSRuntime.doubleValue(numValue);
            return doubleValue - 1.0;
        }

        @Override
        public BigInt doBigInt(BigInt value2) {
            return value2.subtract(BigInt.ONE);
        }

        @Override
        public SafeInteger doSafeInteger(SafeInteger value2) {
            return value2.decrementExact();
        }

        @Override
        public TruffleString getOverloadedOperatorName() {
            return Strings.SYMBOL_MINUS_MINUS;
        }
    }

    protected static class IncOp
    extends LocalVarOp {
        protected IncOp() {
        }

        @Override
        public int doInt(int value2) {
            return Math.addExact(value2, 1);
        }

        @Override
        public double doDouble(double value2) {
            return value2 + 1.0;
        }

        @Override
        public Number doNumber(Number numValue, ConditionProfile isIntegerProfile, ConditionProfile isBoundaryValue) {
            if (isIntegerProfile.profile(numValue instanceof Integer)) {
                int intValue = (Integer)numValue;
                if (isBoundaryValue.profile(intValue != Integer.MAX_VALUE)) {
                    return intValue + 1;
                }
                return (double)intValue + 1.0;
            }
            double doubleValue = JSRuntime.doubleValue(numValue);
            return doubleValue + 1.0;
        }

        @Override
        public BigInt doBigInt(BigInt value2) {
            return value2.add(BigInt.ONE);
        }

        @Override
        public SafeInteger doSafeInteger(SafeInteger value2) {
            return value2.incrementExact();
        }

        @Override
        public TruffleString getOverloadedOperatorName() {
            return Strings.SYMBOL_PLUS_PLUS;
        }
    }

    static abstract class LocalVarOp {
        LocalVarOp() {
        }

        public abstract int doInt(int var1);

        public abstract double doDouble(double var1);

        public abstract Number doNumber(Number var1, ConditionProfile var2, ConditionProfile var3);

        public abstract BigInt doBigInt(BigInt var1);

        public abstract SafeInteger doSafeInteger(SafeInteger var1);

        public abstract TruffleString getOverloadedOperatorName();
    }

    public static enum Op {
        Inc(new IncOp()),
        Dec(new DecOp());

        public final LocalVarOp op;

        private Op(LocalVarOp op) {
            this.op = op;
        }
    }
}

