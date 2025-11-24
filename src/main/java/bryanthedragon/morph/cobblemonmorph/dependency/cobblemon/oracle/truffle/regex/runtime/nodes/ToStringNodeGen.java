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
import com.oracle.truffle.regex.runtime.nodes.ToCharNode;
import com.oracle.truffle.regex.runtime.nodes.ToCharNodeGen;
import com.oracle.truffle.regex.runtime.nodes.ToStringNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToStringNode.class)
public final class ToStringNodeGen
extends ToStringNode {
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
    @Node.Child
    private ToCharNode boxedCharArray1_toCharNode_;

    private ToStringNodeGen() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public String execute(Object arg0Value) throws UnsupportedTypeException {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return ToStringNode.doString(arg0Value_);
        }
        if ((state_0 & 0x1E) != 0) {
            Node prev_;
            EncapsulatingNodeReference encapsulating_;
            if ((state_0 & 2) != 0) {
                BoxedString0Data s1_ = this.boxedString0_cache;
                while (s1_ != null) {
                    if (s1_.inputs_.accepts(arg0Value) && s1_.inputs_.isString(arg0Value)) {
                        return ToStringNode.doBoxedString(arg0Value, s1_.inputs_);
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 4) != 0) {
                encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
                    if (boxedString1_inputs__.isString(arg0Value)) {
                        String string = this.boxedString1Boundary(state_0, arg0Value);
                        return string;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
            if ((state_0 & 8) != 0) {
                BoxedCharArray0Data s3_ = this.boxedCharArray0_cache;
                while (s3_ != null) {
                    if (s3_.inputs_.accepts(arg0Value) && s3_.inputs_.hasArrayElements(arg0Value)) {
                        return ToStringNode.doBoxedCharArray(arg0Value, s3_.inputs_, s3_.toCharNode_);
                    }
                    s3_ = s3_.next_;
                }
            }
            if ((state_0 & 0x10) != 0) {
                encapsulating_ = EncapsulatingNodeReference.getCurrent();
                prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
                    if (boxedCharArray1_inputs__.hasArrayElements(arg0Value)) {
                        String string = this.boxedCharArray1Boundary(state_0, arg0Value);
                        return string;
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
    private String boxedString1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
        InteropLibrary boxedString1_inputs__ = INTEROP_LIBRARY_.getUncached();
        return ToStringNode.doBoxedString(arg0Value, boxedString1_inputs__);
    }

    @CompilerDirectives.TruffleBoundary
    private String boxedCharArray1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
        InteropLibrary boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
        return ToStringNode.doBoxedCharArray(arg0Value, boxedCharArray1_inputs__, this.boxedCharArray1_toCharNode_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String executeAndSpecialize(Object arg0Value) throws UnsupportedTypeException {
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
                String string = ToStringNode.doString(arg0Value_);
                return string;
            }
            if ((exclude & 1) == 0) {
                Object inputs__;
                int count1_ = 0;
                BoxedString0Data s1_ = this.boxedString0_cache;
                if ((state_0 & 2) != 0) {
                    while (!(s1_ == null || s1_.inputs_.accepts(arg0Value) && s1_.inputs_.isString(arg0Value))) {
                        s1_ = s1_.next_;
                        ++count1_;
                    }
                }
                if (s1_ == null && ((InteropLibrary)(inputs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).isString(arg0Value) && count1_ < 2) {
                    s1_ = super.insert(new BoxedString0Data(this.boxedString0_cache));
                    s1_.inputs_ = (InteropLibrary)s1_.insertAccessor(inputs__);
                    VarHandle.storeStoreFence();
                    this.boxedString0_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                }
                if (s1_ != null) {
                    lock.unlock();
                    hasLock = false;
                    inputs__ = ToStringNode.doBoxedString(arg0Value, s1_.inputs_);
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
                    state_0 &= 0xFFFFFFFD;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    String string = ToStringNode.doBoxedString(arg0Value, boxedString1_inputs__);
                    return string;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            if ((exclude & 2) == 0) {
                Object inputs__1;
                int count3_ = 0;
                BoxedCharArray0Data s3_ = this.boxedCharArray0_cache;
                if ((state_0 & 8) != 0) {
                    while (!(s3_ == null || s3_.inputs_.accepts(arg0Value) && s3_.inputs_.hasArrayElements(arg0Value))) {
                        s3_ = s3_.next_;
                        ++count3_;
                    }
                }
                if (s3_ == null && ((InteropLibrary)(inputs__1 = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).hasArrayElements(arg0Value) && count3_ < 2) {
                    s3_ = super.insert(new BoxedCharArray0Data(this.boxedCharArray0_cache));
                    s3_.inputs_ = (InteropLibrary)s3_.insertAccessor(inputs__1);
                    s3_.toCharNode_ = s3_.insertAccessor(ToCharNode.create());
                    VarHandle.storeStoreFence();
                    this.boxedCharArray0_cache = s3_;
                    this.state_0_ = state_0 |= 8;
                }
                if (s3_ != null) {
                    lock.unlock();
                    hasLock = false;
                    inputs__1 = ToStringNode.doBoxedCharArray(arg0Value, s3_.inputs_, s3_.toCharNode_);
                    return inputs__1;
                }
            }
            InteropLibrary boxedCharArray1_inputs__ = null;
            encapsulating_ = EncapsulatingNodeReference.getCurrent();
            prev_ = encapsulating_.set(this);
            try {
                boxedCharArray1_inputs__ = INTEROP_LIBRARY_.getUncached();
                if (boxedCharArray1_inputs__.hasArrayElements(arg0Value)) {
                    this.boxedCharArray1_toCharNode_ = super.insert(ToCharNode.create());
                    this.exclude_ = exclude |= 2;
                    this.boxedCharArray0_cache = null;
                    state_0 &= 0xFFFFFFF7;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    String string = ToStringNode.doBoxedCharArray(arg0Value, boxedCharArray1_inputs__, this.boxedCharArray1_toCharNode_);
                    return string;
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
            BoxedString0Data s1_ = this.boxedString0_cache;
            BoxedCharArray0Data s3_ = this.boxedCharArray0_cache;
            if (!(s1_ != null && s1_.next_ != null || s3_ != null && s3_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
        }
        return NodeCost.POLYMORPHIC;
    }

    public static ToStringNode create() {
        return new ToStringNodeGen();
    }

    public static ToStringNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ToStringNode.class)
    @DenyReplace
    private static final class Uncached
    extends ToStringNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public String execute(Object arg0Value) throws UnsupportedTypeException {
            if (arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                return ToStringNode.doString(arg0Value_);
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).isString(arg0Value)) {
                return ToStringNode.doBoxedString(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).hasArrayElements(arg0Value)) {
                return ToStringNode.doBoxedCharArray(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value), ToCharNodeGen.getUncached());
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

    @GeneratedBy(value=ToStringNode.class)
    private static final class BoxedCharArray0Data
    extends Node {
        @Node.Child
        BoxedCharArray0Data next_;
        @Node.Child
        InteropLibrary inputs_;
        @Node.Child
        ToCharNode toCharNode_;

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

    @GeneratedBy(value=ToStringNode.class)
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

