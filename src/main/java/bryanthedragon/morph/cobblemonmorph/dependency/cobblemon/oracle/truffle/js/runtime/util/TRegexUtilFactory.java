/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.util;

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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TRegexUtil.class)
public final class TRegexUtilFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=TRegexUtil.InvokeGetGroupBoundariesMethodNode.class)
    public static final class InvokeGetGroupBoundariesMethodNodeGen
    extends TRegexUtil.InvokeGetGroupBoundariesMethodNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Exec0Data exec0_cache;
        @Node.Child
        private TRegexUtil.InteropToIntNode exec1_toIntNode_;

        private InvokeGetGroupBoundariesMethodNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public int execute(Object arg0Value, Object arg1Value, int arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0 && arg1Value instanceof String) {
                String arg1Value_ = (String)arg1Value;
                if ((state_0 & 1) != 0) {
                    Exec0Data s0_ = this.exec0_cache;
                    while (s0_ != null) {
                        if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberInvocable(arg0Value, arg1Value_)) {
                            return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, s0_.objs_, s0_.toIntNode_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary exec1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (exec1_objs__.isMemberInvocable(arg0Value, arg1Value_)) {
                            int n = this.exec1Boundary(state_0, arg0Value, arg1Value_, arg2Value);
                            return n;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        @CompilerDirectives.TruffleBoundary
        private int exec1Boundary(int state_0, Object arg0Value, String arg1Value_, int arg2Value) {
            InteropLibrary exec1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, exec1_objs__, this.exec1_toIntNode_);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(Object arg0Value, Object arg1Value, int arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg1Value instanceof String) {
                    String arg1Value_ = (String)arg1Value;
                    if (exclude == 0) {
                        InteropLibrary objs__2;
                        int count0_ = 0;
                        Exec0Data s0_ = this.exec0_cache;
                        if ((state_0 & 1) != 0) {
                            while (!(s0_ == null || s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberInvocable(arg0Value, arg1Value_))) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && (objs__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).isMemberInvocable(arg0Value, arg1Value_) && count0_ < 9) {
                            s0_ = super.insert(new Exec0Data(this.exec0_cache));
                            s0_.objs_ = s0_.insertAccessor(objs__2);
                            s0_.toIntNode_ = s0_.insertAccessor(InteropToIntNodeGen.create());
                            VarHandle.storeStoreFence();
                            this.exec0_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            int objs__2 = TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, s0_.objs_, s0_.toIntNode_);
                            return objs__2;
                        }
                    }
                    InteropLibrary exec1_objs__ = null;
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        exec1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (exec1_objs__.isMemberInvocable(arg0Value, arg1Value_)) {
                            this.exec1_toIntNode_ = super.insert(InteropToIntNodeGen.create());
                            this.exclude_ = exclude |= 1;
                            this.exec0_cache = null;
                            state_0 &= 0xFFFFFFFE;
                            this.state_0_ = state_0 |= 2;
                            lock.unlock();
                            hasLock = false;
                            int n = TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, exec1_objs__, this.exec1_toIntNode_);
                            return n;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            Exec0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.exec0_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InvokeGetGroupBoundariesMethodNode create() {
            return new InvokeGetGroupBoundariesMethodNodeGen();
        }

        public static TRegexUtil.InvokeGetGroupBoundariesMethodNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InvokeGetGroupBoundariesMethodNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InvokeGetGroupBoundariesMethodNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(Object arg0Value, Object arg1Value, int arg2Value) {
                if (arg1Value instanceof String) {
                    String arg1Value_ = (String)arg1Value;
                    if (INTEROP_LIBRARY_.getUncached(arg0Value).isMemberInvocable(arg0Value, arg1Value_)) {
                        return TRegexUtil.InvokeGetGroupBoundariesMethodNode.exec(arg0Value, arg1Value_, arg2Value, INTEROP_LIBRARY_.getUncached(arg0Value), InteropToIntNodeGen.getUncached());
                    }
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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

        @GeneratedBy(value=TRegexUtil.InvokeGetGroupBoundariesMethodNode.class)
        private static final class Exec0Data
        extends Node {
            @Node.Child
            Exec0Data next_;
            @Node.Child
            InteropLibrary objs_;
            @Node.Child
            TRegexUtil.InteropToIntNode toIntNode_;

            Exec0Data(Exec0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InvokeExecMethodNode.class)
    public static final class InvokeExecMethodNodeGen
    extends TRegexUtil.InvokeExecMethodNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Exec0Data exec0_cache;

        private InvokeExecMethodNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public Object execute(Object arg0Value, Object arg1Value, long arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    Exec0Data s0_ = this.exec0_cache;
                    while (s0_ != null) {
                        if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberInvocable(arg0Value, "exec")) {
                            return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, s0_.objs_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary exec1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (exec1_objs__.isMemberInvocable(arg0Value, "exec")) {
                            Object object = this.exec1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                            return object;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
        }

        @CompilerDirectives.TruffleBoundary
        private Object exec1Boundary(int state_0, Object arg0Value, Object arg1Value, long arg2Value) {
            InteropLibrary exec1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, exec1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arg0Value, Object arg1Value, long arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    Object objs__;
                    int count0_ = 0;
                    Exec0Data s0_ = this.exec0_cache;
                    if ((state_0 & 1) != 0) {
                        while (!(s0_ == null || s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberInvocable(arg0Value, "exec"))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && ((InteropLibrary)(objs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).isMemberInvocable(arg0Value, "exec") && count0_ < 3) {
                        s0_ = super.insert(new Exec0Data(this.exec0_cache));
                        s0_.objs_ = (InteropLibrary)s0_.insertAccessor(objs__);
                        VarHandle.storeStoreFence();
                        this.exec0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        objs__ = TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, s0_.objs_);
                        return objs__;
                    }
                }
                InteropLibrary exec1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    exec1_objs__ = INTEROP_LIBRARY_.getUncached();
                    if (exec1_objs__.isMemberInvocable(arg0Value, "exec")) {
                        this.exclude_ = exclude |= 1;
                        this.exec0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, exec1_objs__);
                        return object;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            Exec0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.exec0_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InvokeExecMethodNode create() {
            return new InvokeExecMethodNodeGen();
        }

        public static TRegexUtil.InvokeExecMethodNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InvokeExecMethodNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InvokeExecMethodNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value, Object arg1Value, long arg2Value) {
                if (INTEROP_LIBRARY_.getUncached(arg0Value).isMemberInvocable(arg0Value, "exec")) {
                    return TRegexUtil.InvokeExecMethodNode.exec(arg0Value, arg1Value, arg2Value, INTEROP_LIBRARY_.getUncached(arg0Value));
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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

        @GeneratedBy(value=TRegexUtil.InvokeExecMethodNode.class)
        private static final class Exec0Data
        extends Node {
            @Node.Child
            Exec0Data next_;
            @Node.Child
            InteropLibrary objs_;

            Exec0Data(Exec0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropToStringNode.class)
    public static final class InteropToStringNodeGen
    extends TRegexUtil.InteropToStringNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Coerce0Data coerce0_cache;

        private InteropToStringNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public TruffleString execute(Object arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                return TRegexUtil.InteropToStringNode.coerceJavaString(arg0Value_);
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return TRegexUtil.InteropToStringNode.coerceDirect(arg0Value_);
            }
            if ((state_0 & 0xC) != 0) {
                if ((state_0 & 4) != 0) {
                    Coerce0Data s2_ = this.coerce0_cache;
                    while (s2_ != null) {
                        if (s2_.objs_.accepts(arg0Value) && !JSGuards.isTruffleString(arg0Value) && s2_.objs_.isString(arg0Value)) {
                            return TRegexUtil.InteropToStringNode.coerce(arg0Value, s2_.objs_);
                        }
                        s2_ = s2_.next_;
                    }
                }
                if ((state_0 & 8) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary coerce1_objs__;
                        if (!JSGuards.isTruffleString(arg0Value) && (coerce1_objs__ = INTEROP_LIBRARY_.getUncached()).isString(arg0Value)) {
                            TruffleString truffleString = this.coerce1Boundary(state_0, arg0Value);
                            return truffleString;
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
        private TruffleString coerce1Boundary(int state_0, Object arg0Value) {
            InteropLibrary coerce1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InteropToStringNode.coerce(arg0Value, coerce1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(Object arg0Value) {
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
                    TruffleString truffleString = TRegexUtil.InteropToStringNode.coerceJavaString(arg0Value_);
                    return truffleString;
                }
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = TRegexUtil.InteropToStringNode.coerceDirect(arg0Value_);
                    return truffleString;
                }
                if (exclude == 0) {
                    Object objs__;
                    int count2_ = 0;
                    Coerce0Data s2_ = this.coerce0_cache;
                    if ((state_0 & 4) != 0) {
                        while (!(s2_ == null || s2_.objs_.accepts(arg0Value) && !JSGuards.isTruffleString(arg0Value) && s2_.objs_.isString(arg0Value))) {
                            s2_ = s2_.next_;
                            ++count2_;
                        }
                    }
                    if (s2_ == null && !JSGuards.isTruffleString(arg0Value) && ((InteropLibrary)(objs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).isString(arg0Value) && count2_ < 3) {
                        s2_ = super.insert(new Coerce0Data(this.coerce0_cache));
                        s2_.objs_ = (InteropLibrary)s2_.insertAccessor(objs__);
                        VarHandle.storeStoreFence();
                        this.coerce0_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                    }
                    if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        objs__ = TRegexUtil.InteropToStringNode.coerce(arg0Value, s2_.objs_);
                        return objs__;
                    }
                }
                InteropLibrary coerce1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (!JSGuards.isTruffleString(arg0Value) && (coerce1_objs__ = INTEROP_LIBRARY_.getUncached()).isString(arg0Value)) {
                        this.exclude_ = exclude |= 1;
                        this.coerce0_cache = null;
                        state_0 &= 0xFFFFFFFB;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = TRegexUtil.InteropToStringNode.coerce(arg0Value, coerce1_objs__);
                        return truffleString;
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
            Coerce0Data s2_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.coerce0_cache) == null || s2_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropToStringNode create() {
            return new InteropToStringNodeGen();
        }

        public static TRegexUtil.InteropToStringNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropToStringNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropToStringNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(Object arg0Value) {
                if (arg0Value instanceof String) {
                    String arg0Value_ = (String)arg0Value;
                    return TRegexUtil.InteropToStringNode.coerceJavaString(arg0Value_);
                }
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    return TRegexUtil.InteropToStringNode.coerceDirect(arg0Value_);
                }
                if (!JSGuards.isTruffleString(arg0Value) && INTEROP_LIBRARY_.getUncached(arg0Value).isString(arg0Value)) {
                    return TRegexUtil.InteropToStringNode.coerce(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropToStringNode.class)
        private static final class Coerce0Data
        extends Node {
            @Node.Child
            Coerce0Data next_;
            @Node.Child
            InteropLibrary objs_;

            Coerce0Data(Coerce0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropToIntNode.class)
    public static final class InteropToIntNodeGen
    extends TRegexUtil.InteropToIntNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Coerce0Data coerce0_cache;

        private InteropToIntNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public int execute(Object arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                return TRegexUtil.InteropToIntNode.coerceDirect(arg0Value_);
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0) {
                    Coerce0Data s1_ = this.coerce0_cache;
                    while (s1_ != null) {
                        if (s1_.objs_.accepts(arg0Value) && s1_.objs_.fitsInInt(arg0Value)) {
                            return TRegexUtil.InteropToIntNode.coerce(arg0Value, s1_.objs_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary coerce1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (coerce1_objs__.fitsInInt(arg0Value)) {
                            int n = this.coerce1Boundary(state_0, arg0Value);
                            return n;
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
        private int coerce1Boundary(int state_0, Object arg0Value) {
            InteropLibrary coerce1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InteropToIntNode.coerce(arg0Value, coerce1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(Object arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg0Value instanceof Integer) {
                    int arg0Value_ = (Integer)arg0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = TRegexUtil.InteropToIntNode.coerceDirect(arg0Value_);
                    return n;
                }
                if (exclude == 0) {
                    InteropLibrary objs__2;
                    int count1_ = 0;
                    Coerce0Data s1_ = this.coerce0_cache;
                    if ((state_0 & 2) != 0) {
                        while (!(s1_ == null || s1_.objs_.accepts(arg0Value) && s1_.objs_.fitsInInt(arg0Value))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && (objs__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).fitsInInt(arg0Value) && count1_ < 3) {
                        s1_ = super.insert(new Coerce0Data(this.coerce0_cache));
                        s1_.objs_ = s1_.insertAccessor(objs__2);
                        VarHandle.storeStoreFence();
                        this.coerce0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        int objs__2 = TRegexUtil.InteropToIntNode.coerce(arg0Value, s1_.objs_);
                        return objs__2;
                    }
                }
                InteropLibrary coerce1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    coerce1_objs__ = INTEROP_LIBRARY_.getUncached();
                    if (coerce1_objs__.fitsInInt(arg0Value)) {
                        this.exclude_ = exclude |= 1;
                        this.coerce0_cache = null;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        int n = TRegexUtil.InteropToIntNode.coerce(arg0Value, coerce1_objs__);
                        return n;
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
            Coerce0Data s1_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.coerce0_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropToIntNode create() {
            return new InteropToIntNodeGen();
        }

        public static TRegexUtil.InteropToIntNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropToIntNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropToIntNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(Object arg0Value) {
                if (arg0Value instanceof Integer) {
                    int arg0Value_ = (Integer)arg0Value;
                    return TRegexUtil.InteropToIntNode.coerceDirect(arg0Value_);
                }
                if (INTEROP_LIBRARY_.getUncached(arg0Value).fitsInInt(arg0Value)) {
                    return TRegexUtil.InteropToIntNode.coerce(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropToIntNode.class)
        private static final class Coerce0Data
        extends Node {
            @Node.Child
            Coerce0Data next_;
            @Node.Child
            InteropLibrary objs_;

            Coerce0Data(Coerce0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropToBooleanNode.class)
    public static final class InteropToBooleanNodeGen
    extends TRegexUtil.InteropToBooleanNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Coerce0Data coerce0_cache;

        private InteropToBooleanNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public boolean execute(Object arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                return TRegexUtil.InteropToBooleanNode.coerceDirect(arg0Value_);
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0) {
                    Coerce0Data s1_ = this.coerce0_cache;
                    while (s1_ != null) {
                        if (s1_.objs_.accepts(arg0Value) && s1_.objs_.isBoolean(arg0Value)) {
                            return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, s1_.objs_);
                        }
                        s1_ = s1_.next_;
                    }
                }
                if ((state_0 & 4) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary coerce1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (coerce1_objs__.isBoolean(arg0Value)) {
                            boolean bl = this.coerce1Boundary(state_0, arg0Value);
                            return bl;
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
        private boolean coerce1Boundary(int state_0, Object arg0Value) {
            InteropLibrary coerce1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, coerce1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(Object arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arg0Value instanceof Boolean) {
                    boolean arg0Value_ = (Boolean)arg0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = TRegexUtil.InteropToBooleanNode.coerceDirect(arg0Value_);
                    return bl;
                }
                if (exclude == 0) {
                    InteropLibrary objs__2;
                    int count1_ = 0;
                    Coerce0Data s1_ = this.coerce0_cache;
                    if ((state_0 & 2) != 0) {
                        while (!(s1_ == null || s1_.objs_.accepts(arg0Value) && s1_.objs_.isBoolean(arg0Value))) {
                            s1_ = s1_.next_;
                            ++count1_;
                        }
                    }
                    if (s1_ == null && (objs__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).isBoolean(arg0Value) && count1_ < 3) {
                        s1_ = super.insert(new Coerce0Data(this.coerce0_cache));
                        s1_.objs_ = s1_.insertAccessor(objs__2);
                        VarHandle.storeStoreFence();
                        this.coerce0_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                    }
                    if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean objs__2 = TRegexUtil.InteropToBooleanNode.coerce(arg0Value, s1_.objs_);
                        return objs__2;
                    }
                }
                InteropLibrary coerce1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    coerce1_objs__ = INTEROP_LIBRARY_.getUncached();
                    if (coerce1_objs__.isBoolean(arg0Value)) {
                        this.exclude_ = exclude |= 1;
                        this.coerce0_cache = null;
                        state_0 &= 0xFFFFFFFD;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = TRegexUtil.InteropToBooleanNode.coerce(arg0Value, coerce1_objs__);
                        return bl;
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
            Coerce0Data s1_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.coerce0_cache) == null || s1_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropToBooleanNode create() {
            return new InteropToBooleanNodeGen();
        }

        public static TRegexUtil.InteropToBooleanNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropToBooleanNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropToBooleanNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(Object arg0Value) {
                if (arg0Value instanceof Boolean) {
                    boolean arg0Value_ = (Boolean)arg0Value;
                    return TRegexUtil.InteropToBooleanNode.coerceDirect(arg0Value_);
                }
                if (INTEROP_LIBRARY_.getUncached(arg0Value).isBoolean(arg0Value)) {
                    return TRegexUtil.InteropToBooleanNode.coerce(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropToBooleanNode.class)
        private static final class Coerce0Data
        extends Node {
            @Node.Child
            Coerce0Data next_;
            @Node.Child
            InteropLibrary objs_;

            Coerce0Data(Coerce0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropReadStringMemberNode.class)
    public static final class InteropReadStringMemberNodeGen
    extends TRegexUtil.InteropReadStringMemberNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Read0Data read0_cache;
        @Node.Child
        private TRegexUtil.InteropToStringNode read1_coerceNode_;

        private InteropReadStringMemberNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public TruffleString execute(Object arg0Value, String arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    Read0Data s0_ = this.read0_cache;
                    while (s0_ != null) {
                        if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                            return TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                            TruffleString truffleString = this.read1Boundary(state_0, arg0Value, arg1Value);
                            return truffleString;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        @CompilerDirectives.TruffleBoundary
        private TruffleString read1Boundary(int state_0, Object arg0Value, String arg1Value) {
            InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(Object arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    Object objs__;
                    int count0_ = 0;
                    Read0Data s0_ = this.read0_cache;
                    if ((state_0 & 1) != 0) {
                        while (!(s0_ == null || s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && ((InteropLibrary)(objs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).isMemberReadable(arg0Value, arg1Value) && count0_ < 3) {
                        s0_ = super.insert(new Read0Data(this.read0_cache));
                        s0_.coerceNode_ = s0_.insertAccessor(InteropToStringNodeGen.create());
                        s0_.objs_ = (InteropLibrary)s0_.insertAccessor(objs__);
                        VarHandle.storeStoreFence();
                        this.read0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        objs__ = TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                        return objs__;
                    }
                }
                InteropLibrary read1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    read1_objs__ = INTEROP_LIBRARY_.getUncached();
                    if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                        this.read1_coerceNode_ = super.insert(InteropToStringNodeGen.create());
                        this.exclude_ = exclude |= 1;
                        this.read0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        TruffleString truffleString = TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
                        return truffleString;
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
            Read0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.read0_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropReadStringMemberNode create() {
            return new InteropReadStringMemberNodeGen();
        }

        public static TRegexUtil.InteropReadStringMemberNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropReadStringMemberNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropReadStringMemberNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TruffleString execute(Object arg0Value, String arg1Value) {
                if (INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
                    return TRegexUtil.InteropReadStringMemberNode.read(arg0Value, arg1Value, InteropToStringNodeGen.getUncached(), INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropReadStringMemberNode.class)
        private static final class Read0Data
        extends Node {
            @Node.Child
            Read0Data next_;
            @Node.Child
            TRegexUtil.InteropToStringNode coerceNode_;
            @Node.Child
            InteropLibrary objs_;

            Read0Data(Read0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropReadBooleanMemberNode.class)
    public static final class InteropReadBooleanMemberNodeGen
    extends TRegexUtil.InteropReadBooleanMemberNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Read0Data read0_cache;
        @Node.Child
        private TRegexUtil.InteropToBooleanNode read1_coerceNode_;

        private InteropReadBooleanMemberNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public boolean execute(Object arg0Value, String arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    Read0Data s0_ = this.read0_cache;
                    while (s0_ != null) {
                        if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                            return TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                            boolean bl = this.read1Boundary(state_0, arg0Value, arg1Value);
                            return bl;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        @CompilerDirectives.TruffleBoundary
        private boolean read1Boundary(int state_0, Object arg0Value, String arg1Value) {
            InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(Object arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    InteropLibrary objs__2;
                    int count0_ = 0;
                    Read0Data s0_ = this.read0_cache;
                    if ((state_0 & 1) != 0) {
                        while (!(s0_ == null || s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (objs__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).isMemberReadable(arg0Value, arg1Value) && count0_ < 9) {
                        s0_ = super.insert(new Read0Data(this.read0_cache));
                        s0_.coerceNode_ = s0_.insertAccessor(InteropToBooleanNodeGen.create());
                        s0_.objs_ = s0_.insertAccessor(objs__2);
                        VarHandle.storeStoreFence();
                        this.read0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean objs__2 = TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                        return objs__2;
                    }
                }
                InteropLibrary read1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    read1_objs__ = INTEROP_LIBRARY_.getUncached();
                    if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                        this.read1_coerceNode_ = super.insert(InteropToBooleanNodeGen.create());
                        this.exclude_ = exclude |= 1;
                        this.read0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
                        return bl;
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
            Read0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.read0_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropReadBooleanMemberNode create() {
            return new InteropReadBooleanMemberNodeGen();
        }

        public static TRegexUtil.InteropReadBooleanMemberNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropReadBooleanMemberNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropReadBooleanMemberNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(Object arg0Value, String arg1Value) {
                if (INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
                    return TRegexUtil.InteropReadBooleanMemberNode.read(arg0Value, arg1Value, InteropToBooleanNodeGen.getUncached(), INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropReadBooleanMemberNode.class)
        private static final class Read0Data
        extends Node {
            @Node.Child
            Read0Data next_;
            @Node.Child
            TRegexUtil.InteropToBooleanNode coerceNode_;
            @Node.Child
            InteropLibrary objs_;

            Read0Data(Read0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropReadIntMemberNode.class)
    public static final class InteropReadIntMemberNodeGen
    extends TRegexUtil.InteropReadIntMemberNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Read0Data read0_cache;
        @Node.Child
        private TRegexUtil.InteropToIntNode read1_coerceNode_;

        private InteropReadIntMemberNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public int execute(Object arg0Value, String arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    Read0Data s0_ = this.read0_cache;
                    while (s0_ != null) {
                        if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                            return TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                            int n = this.read1Boundary(state_0, arg0Value, arg1Value);
                            return n;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        @CompilerDirectives.TruffleBoundary
        private int read1Boundary(int state_0, Object arg0Value, String arg1Value) {
            InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(Object arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    InteropLibrary objs__2;
                    int count0_ = 0;
                    Read0Data s0_ = this.read0_cache;
                    if ((state_0 & 1) != 0) {
                        while (!(s0_ == null || s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (objs__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).isMemberReadable(arg0Value, arg1Value) && count0_ < 9) {
                        s0_ = super.insert(new Read0Data(this.read0_cache));
                        s0_.coerceNode_ = s0_.insertAccessor(InteropToIntNodeGen.create());
                        s0_.objs_ = s0_.insertAccessor(objs__2);
                        VarHandle.storeStoreFence();
                        this.read0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        int objs__2 = TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, s0_.coerceNode_, s0_.objs_);
                        return objs__2;
                    }
                }
                InteropLibrary read1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    read1_objs__ = INTEROP_LIBRARY_.getUncached();
                    if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                        this.read1_coerceNode_ = super.insert(InteropToIntNodeGen.create());
                        this.exclude_ = exclude |= 1;
                        this.read0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        int n = TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, this.read1_coerceNode_, read1_objs__);
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
            Read0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.read0_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropReadIntMemberNode create() {
            return new InteropReadIntMemberNodeGen();
        }

        public static TRegexUtil.InteropReadIntMemberNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropReadIntMemberNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropReadIntMemberNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int execute(Object arg0Value, String arg1Value) {
                if (INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
                    return TRegexUtil.InteropReadIntMemberNode.read(arg0Value, arg1Value, InteropToIntNodeGen.getUncached(), INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropReadIntMemberNode.class)
        private static final class Read0Data
        extends Node {
            @Node.Child
            Read0Data next_;
            @Node.Child
            TRegexUtil.InteropToIntNode coerceNode_;
            @Node.Child
            InteropLibrary objs_;

            Read0Data(Read0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropReadMemberNode.class)
    public static final class InteropReadMemberNodeGen
    extends TRegexUtil.InteropReadMemberNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Read0Data read0_cache;

        private InteropReadMemberNodeGen() {
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public Object execute(Object arg0Value, String arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    Read0Data s0_ = this.read0_cache;
                    while (s0_ != null) {
                        if (s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value)) {
                            return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, s0_.objs_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
                        if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                            Object object = this.read1Boundary(state_0, arg0Value, arg1Value);
                            return object;
                        }
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        @CompilerDirectives.TruffleBoundary
        private Object read1Boundary(int state_0, Object arg0Value, String arg1Value) {
            InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached();
            return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, read1_objs__);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    Object objs__;
                    int count0_ = 0;
                    Read0Data s0_ = this.read0_cache;
                    if ((state_0 & 1) != 0) {
                        while (!(s0_ == null || s0_.objs_.accepts(arg0Value) && s0_.objs_.isMemberReadable(arg0Value, arg1Value))) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && ((InteropLibrary)(objs__ = super.insert(INTEROP_LIBRARY_.create(arg0Value)))).isMemberReadable(arg0Value, arg1Value) && count0_ < 9) {
                        s0_ = super.insert(new Read0Data(this.read0_cache));
                        s0_.objs_ = (InteropLibrary)s0_.insertAccessor(objs__);
                        VarHandle.storeStoreFence();
                        this.read0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        objs__ = TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, s0_.objs_);
                        return objs__;
                    }
                }
                InteropLibrary read1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    read1_objs__ = INTEROP_LIBRARY_.getUncached();
                    if (read1_objs__.isMemberReadable(arg0Value, arg1Value)) {
                        this.exclude_ = exclude |= 1;
                        this.read0_cache = null;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        Object object = TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, read1_objs__);
                        return object;
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
            Read0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.read0_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropReadMemberNode create() {
            return new InteropReadMemberNodeGen();
        }

        public static TRegexUtil.InteropReadMemberNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropReadMemberNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropReadMemberNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value, String arg1Value) {
                if (INTEROP_LIBRARY_.getUncached(arg0Value).isMemberReadable(arg0Value, arg1Value)) {
                    return TRegexUtil.InteropReadMemberNode.read(arg0Value, arg1Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropReadMemberNode.class)
        private static final class Read0Data
        extends Node {
            @Node.Child
            Read0Data next_;
            @Node.Child
            InteropLibrary objs_;

            Read0Data(Read0Data next_) {
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

    @GeneratedBy(value=TRegexUtil.InteropIsMemberReadableNode.class)
    public static final class InteropIsMemberReadableNodeGen
    extends TRegexUtil.InteropIsMemberReadableNode {
        private static final Uncached UNCACHED = new Uncached();
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private Read0Data read0_cache;

        private InteropIsMemberReadableNodeGen() {
        }

        @Override
        @ExplodeLoop
        public boolean execute(Object arg0Value, String arg1Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    Read0Data s0_ = this.read0_cache;
                    while (s0_ != null) {
                        if (s0_.objs_.accepts(arg0Value)) {
                            return TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, s0_.objs_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    return this.read1Boundary(state_0, arg0Value, arg1Value);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private boolean read1Boundary(int state_0, Object arg0Value, String arg1Value) {
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                InteropLibrary read1_objs__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                boolean bl = TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, read1_objs__);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(Object arg0Value, String arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                boolean bl;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int count0_ = 0;
                    Read0Data s0_ = this.read0_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && !s0_.objs_.accepts(arg0Value)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && count0_ < 9) {
                        s0_ = super.insert(new Read0Data(this.read0_cache));
                        s0_.objs_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                        VarHandle.storeStoreFence();
                        this.read0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl2 = TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, s0_.objs_);
                        return bl2;
                    }
                }
                InteropLibrary read1_objs__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    read1_objs__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.exclude_ = exclude |= 1;
                    this.read0_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    bl = TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, read1_objs__);
                    encapsulating_.set(prev_);
                }
                catch (Throwable throwable) {
                    encapsulating_.set(prev_);
                    throw throwable;
                }
                return bl;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            Read0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.read0_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        public static TRegexUtil.InteropIsMemberReadableNode create() {
            return new InteropIsMemberReadableNodeGen();
        }

        public static TRegexUtil.InteropIsMemberReadableNode getUncached() {
            return UNCACHED;
        }

        @GeneratedBy(value=TRegexUtil.InteropIsMemberReadableNode.class)
        @DenyReplace
        private static final class Uncached
        extends TRegexUtil.InteropIsMemberReadableNode {
            private Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean execute(Object arg0Value, String arg1Value) {
                return TRegexUtil.InteropIsMemberReadableNode.read(arg0Value, arg1Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

        @GeneratedBy(value=TRegexUtil.InteropIsMemberReadableNode.class)
        private static final class Read0Data
        extends Node {
            @Node.Child
            Read0Data next_;
            @Node.Child
            InteropLibrary objs_;

            Read0Data(Read0Data next_) {
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
}

