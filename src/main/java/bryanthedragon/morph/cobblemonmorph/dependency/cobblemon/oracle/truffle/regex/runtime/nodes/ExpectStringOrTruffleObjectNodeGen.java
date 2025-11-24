/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ExpectStringOrTruffleObjectNode.class)
public final class ExpectStringOrTruffleObjectNodeGen
extends ExpectStringOrTruffleObjectNode {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private BoxedString0Data boxedString0_cache;
    @Node.Child
    private BoxedCharArray0Data boxedCharArray0_cache;

    private ExpectStringOrTruffleObjectNodeGen() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public Object execute(Object arg0Value) throws UnsupportedTypeException {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return ExpectStringOrTruffleObjectNode.doString(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return ExpectStringOrTruffleObjectNode.doTString(arg0Value_);
        }
        if ((state_0 & 0x3C) != 0) {
            Node prev_;
            EncapsulatingNodeReference encapsulating_;
            if ((state_0 & 4) != 0) {
                BoxedString0Data s2_ = this.boxedString0_cache;
                while (s2_ != null) {
                    if (s2_.inputs_.accepts(arg0Value) && s2_.inputs_.isString(arg0Value)) {
                        return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, s2_.inputs_);
                    }
                    s2_ = s2_.next_;
                }
            }
            if ((state_0 & 8) != 0) {
                encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
                    if (boxedString1_inputs__.isString(arg0Value)) {
                        Object object = this.boxedString1Boundary(state_0, arg0Value);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
            if ((state_0 & 0x10) != 0) {
                BoxedCharArray0Data s4_ = this.boxedCharArray0_cache;
                while (s4_ != null) {
                    if (s4_.inputs_.accepts(arg0Value) && s4_.inputs_.hasArrayElements(arg0Value)) {
                        return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, s4_.inputs_);
                    }
                    s4_ = s4_.next_;
                }
            }
            if ((state_0 & 0x20) != 0) {
                encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
                    if (boxedCharArray1_inputs__.hasArrayElements(arg0Value)) {
                        Object object = this.boxedCharArray1Boundary(state_0, arg0Value);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    @CompilerDirectives.TruffleBoundary
    private Object boxedString1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
        InteropLibrary boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
        return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, boxedString1_inputs__);
    }

    @CompilerDirectives.TruffleBoundary
    private Object boxedCharArray1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
        InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
        return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, boxedCharArray1_inputs__);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object arg0Value) throws UnsupportedTypeException {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                String string = ExpectStringOrTruffleObjectNode.doString(arg0Value_);
                return string;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = ExpectStringOrTruffleObjectNode.doTString(arg0Value_);
                return truffleString;
            }
            if ((exclude & 1) == 0) {
                Object inputs__;
                int count2_ = 0;
                BoxedString0Data s2_ = this.boxedString0_cache;
                if ((state_0 & 4) != 0) {
                    while (!(s2_ == null || s2_.inputs_.accepts(arg0Value) && s2_.inputs_.isString(arg0Value))) {
                        s2_ = s2_.next_;
                        ++count2_;
                    }
                }
                if (s2_ == null && ((InteropLibrary)(inputs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).isString(arg0Value) && count2_ < 2) {
                    s2_ = super.insert(new BoxedString0Data(this.boxedString0_cache));
                    s2_.inputs_ = (InteropLibrary)s2_.insertAccessor(inputs__);
                    VarHandle.storeStoreFence();
                    this.boxedString0_cache = s2_;
                    this.state_0_ = state_0 |= 4;
                }
                if (s2_ != null) {
                    lock.unlock();
                    hasLock = false;
                    inputs__ = ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, s2_.inputs_);
                    return inputs__;
                }
            }
            InteropLibrary boxedString1_inputs__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
                if (boxedString1_inputs__.isString(arg0Value)) {
                    this.exclude_ = exclude |= 1;
                    this.boxedString0_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    String string = ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, boxedString1_inputs__);
                    return string;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            if ((exclude & 2) == 0) {
                Object inputs__1;
                int count4_ = 0;
                BoxedCharArray0Data s4_ = this.boxedCharArray0_cache;
                if ((state_0 & 0x10) != 0) {
                    while (!(s4_ == null || s4_.inputs_.accepts(arg0Value) && s4_.inputs_.hasArrayElements(arg0Value))) {
                        s4_ = s4_.next_;
                        ++count4_;
                    }
                }
                if (s4_ == null && ((InteropLibrary)(inputs__1 = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).hasArrayElements(arg0Value) && count4_ < 2) {
                    s4_ = super.insert(new BoxedCharArray0Data(this.boxedCharArray0_cache));
                    s4_.inputs_ = (InteropLibrary)s4_.insertAccessor(inputs__1);
                    VarHandle.storeStoreFence();
                    this.boxedCharArray0_cache = s4_;
                    this.state_0_ = state_0 |= 0x10;
                }
                if (s4_ != null) {
                    lock.unlock();
                    hasLock = false;
                    inputs__1 = ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, s4_.inputs_);
                    return inputs__1;
                }
            }
            InteropLibrary boxedCharArray1_inputs__ = null;
            encapsulating_ = EncapsulatingNodeReference.getCurrent();
            prev_ = encapsulating_.set(this);
            try {
                boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
                if (boxedCharArray1_inputs__.hasArrayElements(arg0Value)) {
                    this.exclude_ = exclude |= 2;
                    this.boxedCharArray0_cache = null;
                    state_0 &= 0xFFFFFFEF;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Object object = ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, boxedCharArray1_inputs__);
                    return object;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            BoxedString0Data s2_ = this.boxedString0_cache;
            BoxedCharArray0Data s4_ = this.boxedCharArray0_cache;
            if (!(s2_ != null && s2_.next_ != null || s4_ != null && s4_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    public static ExpectStringOrTruffleObjectNode create() {
        return new ExpectStringOrTruffleObjectNodeGen();
    }

    public static ExpectStringOrTruffleObjectNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ExpectStringOrTruffleObjectNode.class)
    @DenyReplace
    private static final class Uncached
    extends ExpectStringOrTruffleObjectNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(Object arg0Value) throws UnsupportedTypeException {
            if (arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                return ExpectStringOrTruffleObjectNode.doString(arg0Value_);
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return ExpectStringOrTruffleObjectNode.doTString(arg0Value_);
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).isString(arg0Value)) {
                return ExpectStringOrTruffleObjectNode.doBoxedString(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).hasArrayElements(arg0Value)) {
                return ExpectStringOrTruffleObjectNode.doBoxedCharArray(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @GeneratedBy(value=ExpectStringOrTruffleObjectNode.class)
    private static final class BoxedCharArray0Data
    extends Node {
        @Node.Child
        BoxedCharArray0Data next_;
        @Node.Child
        InteropLibrary inputs_;

        BoxedCharArray0Data(BoxedCharArray0Data next_) {
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

    @GeneratedBy(value=ExpectStringOrTruffleObjectNode.class)
    private static final class BoxedString0Data
    extends Node {
        @Node.Child
        BoxedString0Data next_;
        @Node.Child
        InteropLibrary inputs_;

        BoxedString0Data(BoxedString0Data next_) {
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

