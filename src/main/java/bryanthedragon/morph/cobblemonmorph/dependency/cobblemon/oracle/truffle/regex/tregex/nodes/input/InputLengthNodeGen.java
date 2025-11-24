/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InputLengthNode.class)
public final class InputLengthNodeGen
extends InputLengthNode {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private TruffleObj0Data truffleObj0_cache;

    private InputLengthNodeGen() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public int execute(Object arg0Value, Encodings.Encoding arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
                byte[] arg0Value_ = (byte[])arg0Value;
                return InputLengthNode.doBytes(arg0Value_, arg1Value);
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                return InputLengthNode.doString(arg0Value_, arg1Value);
            }
            if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return InputLengthNode.doTString(arg0Value_, arg1Value);
            }
            if ((state_0 & 0x18) != 0) {
                if ((state_0 & 8) != 0) {
                    TruffleObj0Data s3_ = this.truffleObj0_cache;
                    while (s3_ != null) {
                        if (s3_.inputs_.accepts(arg0Value) && s3_.inputs_.hasArrayElements(arg0Value)) {
                            return InputLengthNode.doTruffleObj(arg0Value, arg1Value, s3_.inputs_);
                        }
                        s3_ = s3_.next_;
                    }
                }
                if ((state_0 & 0x10) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary truffleObj1_inputs__ = INTEROP_LIBRARY_.getUncached();
                        if (truffleObj1_inputs__.hasArrayElements(arg0Value)) {
                            int n = this.truffleObj1Boundary(state_0, arg0Value, arg1Value);
                            return n;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    @CompilerDirectives.TruffleBoundary
    private int truffleObj1Boundary(int state_0, Object arg0Value, Encodings.Encoding arg1Value) {
        InteropLibrary truffleObj1_inputs__ = INTEROP_LIBRARY_.getUncached();
        return InputLengthNode.doTruffleObj(arg0Value, arg1Value, truffleObj1_inputs__);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int executeAndSpecialize(Object arg0Value, Encodings.Encoding arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof byte[]) {
                byte[] arg0Value_ = (byte[])arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = InputLengthNode.doBytes(arg0Value_, arg1Value);
                return n;
            }
            if (arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                int n = InputLengthNode.doString(arg0Value_, arg1Value);
                return n;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                int n = InputLengthNode.doTString(arg0Value_, arg1Value);
                return n;
            }
            if (exclude == 0) {
                InteropLibrary inputs__2;
                int count3_ = 0;
                TruffleObj0Data s3_ = this.truffleObj0_cache;
                if ((state_0 & 8) != 0) {
                    while (!(s3_ == null || s3_.inputs_.accepts(arg0Value) && s3_.inputs_.hasArrayElements(arg0Value))) {
                        s3_ = s3_.next_;
                        ++count3_;
                    }
                }
                if (s3_ == null && (inputs__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).hasArrayElements(arg0Value) && count3_ < 2) {
                    s3_ = super.insert(new TruffleObj0Data(this.truffleObj0_cache));
                    s3_.inputs_ = s3_.insertAccessor(inputs__2);
                    VarHandle.storeStoreFence();
                    this.truffleObj0_cache = s3_;
                    this.state_0_ = state_0 |= 8;
                }
                if (s3_ != null) {
                    lock.unlock();
                    hasLock = false;
                    int inputs__2 = InputLengthNode.doTruffleObj(arg0Value, arg1Value, s3_.inputs_);
                    return inputs__2;
                }
            }
            InteropLibrary truffleObj1_inputs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                truffleObj1_inputs__ = INTEROP_LIBRARY_.getUncached();
                if (truffleObj1_inputs__.hasArrayElements(arg0Value)) {
                    this.exclude_ = exclude |= 1;
                    this.truffleObj0_cache = null;
                    state_0 &= 0xFFFFFFF7;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = InputLengthNode.doTruffleObj(arg0Value, arg1Value, truffleObj1_inputs__);
                    return n;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        TruffleObj0Data s3_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s3_ = this.truffleObj0_cache) == null || s3_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static InputLengthNode create() {
        return new InputLengthNodeGen();
    }

    public static InputLengthNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=InputLengthNode.class)
    @DenyReplace
    private static final class Uncached
    extends InputLengthNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int execute(Object arg0Value, Encodings.Encoding arg1Value) {
            if (arg0Value instanceof byte[]) {
                byte[] arg0Value_ = (byte[])arg0Value;
                return InputLengthNode.doBytes(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                return InputLengthNode.doString(arg0Value_, arg1Value);
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return InputLengthNode.doTString(arg0Value_, arg1Value);
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).hasArrayElements(arg0Value)) {
                return InputLengthNode.doTruffleObj(arg0Value, arg1Value, INTEROP_LIBRARY_.getUncached(arg0Value));
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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

    @GeneratedBy(value=InputLengthNode.class)
    private static final class TruffleObj0Data
    extends Node {
        @Node.Child
        TruffleObj0Data next_;
        @Node.Child
        InteropLibrary inputs_;

        TruffleObj0Data(TruffleObj0Data next_) {
            this.next_ = next_;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }
}

