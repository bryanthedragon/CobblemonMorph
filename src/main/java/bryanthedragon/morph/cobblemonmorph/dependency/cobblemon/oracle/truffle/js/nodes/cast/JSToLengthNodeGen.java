
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToLengthNode.class)
public final class JSToLengthNodeGen
extends JSToLengthNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile negativeBranch;
    @CompilerDirectives.CompilationFinal
    private BranchProfile tooLargeBranch;
    @Node.Child
    private JSToNumberNode object_toNumberNode_;

    private JSToLengthNodeGen() {
    }

    @Override
    public long executeLong(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return JSToLengthNode.doInt(arg0Value_, this.negativeBranch);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            return JSToLengthNode.doSafeInteger(arg0Value_, this.negativeBranch);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E0) >>> 5, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x1E0) >>> 5, arg0Value);
            return JSToLengthNode.doDouble(arg0Value_, this.negativeBranch, this.tooLargeBranch);
        }
        if ((state_0 & 0x18) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isUndefined(arg0Value)) {
                return JSToLengthNode.doUndefined(arg0Value);
            }
            if ((state_0 & 0x10) != 0) {
                return JSToLengthNode.doObject(arg0Value, this.object_toNumberNode_, this.negativeBranch, this.tooLargeBranch);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                long l = JSToLengthNode.doInt(arg0Value_, this.negativeBranch);
                return l;
            }
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_ = (SafeInteger)arg0Value;
                this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                long l = JSToLengthNode.doSafeInteger(arg0Value_, this.negativeBranch);
                return l;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
                this.tooLargeBranch = this.tooLargeBranch == null ? BranchProfile.create() : this.tooLargeBranch;
                state_0 |= doubleCast0 << 5;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                long l = JSToLengthNode.doDouble(arg0Value_, this.negativeBranch, this.tooLargeBranch);
                return l;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                long l = JSToLengthNode.doUndefined(arg0Value);
                return l;
            }
            this.object_toNumberNode_ = super.insert(JSToNumberNode.create());
            this.negativeBranch = this.negativeBranch == null ? BranchProfile.create() : this.negativeBranch;
            this.tooLargeBranch = this.tooLargeBranch == null ? BranchProfile.create() : this.tooLargeBranch;
            this.state_0_ = state_0 |= 0x10;
            lock.unlock();
            hasLock = false;
            long l = JSToLengthNode.doObject(arg0Value, this.object_toNumberNode_, this.negativeBranch, this.tooLargeBranch);
            return l;
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
        if ((state_0 & 0x1F) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x1F & (state_0 & 0x1F) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.negativeBranch));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.negativeBranch));
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
            cached.add(Arrays.asList(this.negativeBranch, this.tooLargeBranch));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doObject";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.object_toNumberNode_, this.negativeBranch, this.tooLargeBranch));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToLengthNode create() {
        return new JSToLengthNodeGen();
    }
}

