
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt16Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToUInt16Node.class)
public final class JSToUInt16NodeGen
extends JSToUInt16Node
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile double_needPositiveInfinityBranch_;
    @Node.Child
    private JSToNumberNode generic_toNumberNode_;

    private JSToUInt16NodeGen() {
    }

    @Override
    public int executeInt(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInt(arg0Value_);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x78) >>> 3, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x78) >>> 3, arg0Value);
            return this.doDouble(arg0Value_, this.double_needPositiveInfinityBranch_);
        }
        if ((state_0 & 4) != 0) {
            return this.doGeneric(arg0Value, this.generic_toNumberNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
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
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.doInt(arg0Value_);
                return n;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                this.double_needPositiveInfinityBranch_ = BranchProfile.create();
                state_0 |= doubleCast0 << 3;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                int n = this.doDouble(arg0Value_, this.double_needPositiveInfinityBranch_);
                return n;
            }
            this.generic_toNumberNode_ = super.insert(JSToNumberNode.create());
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            int n = this.doGeneric(arg0Value, this.generic_toNumberNode_);
            return n;
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
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.double_needPositiveInfinityBranch_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.generic_toNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToUInt16Node create() {
        return new JSToUInt16NodeGen();
    }
}

