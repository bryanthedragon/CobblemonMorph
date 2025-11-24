
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSDoubleToStringNode.class)
public final class JSDoubleToStringNodeGen
extends JSDoubleToStringNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private TruffleString.FromLongNode fromLongNode;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile double_isInt_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile double_isNaN_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile double_isPositiveInfinity_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile double_isNegativeInfinity_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile double_isZero_;
    @Node.Child
    private TruffleString.FromJavaStringNode double_fromJavaStringNode_;

    private JSDoubleToStringNodeGen() {
    }

    @Override
    public TruffleString executeString(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return JSDoubleToStringNode.doInt(arg0Value_, this.fromLongNode);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return JSDoubleToStringNode.doLong(arg0Value_, this.fromLongNode);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arg0Value);
            return JSDoubleToStringNode.doDouble(arg0Value_, this.fromLongNode, this.double_isInt_, this.double_isNaN_, this.double_isPositiveInfinity_, this.double_isNegativeInfinity_, this.double_isZero_, this.double_fromJavaStringNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private TruffleString executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.fromLongNode = super.insert(this.fromLongNode == null ? TruffleString.FromLongNode.create() : this.fromLongNode);
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = JSDoubleToStringNode.doInt(arg0Value_, this.fromLongNode);
                return truffleString;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                this.fromLongNode = super.insert(this.fromLongNode == null ? TruffleString.FromLongNode.create() : this.fromLongNode);
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = JSDoubleToStringNode.doLong(arg0Value_, this.fromLongNode);
                return truffleString;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                this.fromLongNode = super.insert(this.fromLongNode == null ? TruffleString.FromLongNode.create() : this.fromLongNode);
                this.double_isInt_ = ConditionProfile.create();
                this.double_isNaN_ = ConditionProfile.create();
                this.double_isPositiveInfinity_ = ConditionProfile.create();
                this.double_isNegativeInfinity_ = ConditionProfile.create();
                this.double_isZero_ = ConditionProfile.create();
                this.double_fromJavaStringNode_ = super.insert(TruffleString.FromJavaStringNode.create());
                state_0 |= doubleCast0 << 3;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = JSDoubleToStringNode.doDouble(arg0Value_, this.fromLongNode, this.double_isInt_, this.double_isNaN_, this.double_isPositiveInfinity_, this.double_isNegativeInfinity_, this.double_isZero_, this.double_fromJavaStringNode_);
                return truffleString;
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
        if ((state_0 & 7) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 7 & (state_0 & 7) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.fromLongNode));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doLong";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.fromLongNode));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.fromLongNode, this.double_isInt_, this.double_isNaN_, this.double_isPositiveInfinity_, this.double_isNegativeInfinity_, this.double_isZero_, this.double_fromJavaStringNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSDoubleToStringNode create() {
        return new JSDoubleToStringNodeGen();
    }

    public static JSDoubleToStringNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=JSDoubleToStringNode.class)
    @DenyReplace
    private static final class Uncached
    extends JSDoubleToStringNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public TruffleString executeString(Object arg0Value) {
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                return JSDoubleToStringNode.doInt(arg0Value_, TruffleString.FromLongNode.getUncached());
            }
            if (arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                return JSDoubleToStringNode.doLong(arg0Value_, TruffleString.FromLongNode.getUncached());
            }
            if (JSTypesGen.isImplicitDouble(arg0Value)) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(arg0Value);
                return JSDoubleToStringNode.doDouble(arg0Value_, TruffleString.FromLongNode.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), TruffleString.FromJavaStringNode.getUncached());
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
}

