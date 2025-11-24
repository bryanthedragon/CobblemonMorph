
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.runtime.nodes.ExpectByteArrayHostObjectNode;

@GeneratedBy(value=ExpectByteArrayHostObjectNode.class)
public final class ExpectByteArrayHostObjectNodeGen
extends ExpectByteArrayHostObjectNode {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private ExpectByteArrayHostObjectNodeGen() {
    }

    @Override
    public byte[] execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            return ExpectByteArrayHostObjectNode.doByteArray(arg0Value_);
        }
        if ((state_0 & 2) != 0) {
            return this.doBoxed(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private byte[] executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            this.state_0_ = state_0 |= 1;
            return ExpectByteArrayHostObjectNode.doByteArray(arg0Value_);
        }
        this.state_0_ = state_0 |= 2;
        return this.doBoxed(arg0Value);
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static ExpectByteArrayHostObjectNode create() {
        return new ExpectByteArrayHostObjectNodeGen();
    }

    public static ExpectByteArrayHostObjectNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ExpectByteArrayHostObjectNode.class)
    @DenyReplace
    private static final class Uncached
    extends ExpectByteArrayHostObjectNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public byte[] execute(Object arg0Value) {
            if (arg0Value instanceof byte[]) {
                byte[] arg0Value_ = (byte[])arg0Value;
                return ExpectByteArrayHostObjectNode.doByteArray(arg0Value_);
            }
            return this.doBoxed(arg0Value);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }
}

