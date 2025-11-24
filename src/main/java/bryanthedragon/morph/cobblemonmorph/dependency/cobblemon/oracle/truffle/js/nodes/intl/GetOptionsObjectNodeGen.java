
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.intl.GetOptionsObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GetOptionsObjectNode.class)
public final class GetOptionsObjectNodeGen
extends GetOptionsObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private IsObjectNode fromOther_isObjectNode_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile fromOther_errorBranch_;

    private GetOptionsObjectNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && JSGuards.isUndefined(arg0Value)) {
            return this.fromUndefined(arg0Value);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.fromJSObject(arg0Value_);
        }
        if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arg0Value)) {
            return this.fromOther(arg0Value, this.fromOther_isObjectNode_, this.fromOther_errorBranch_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.fromUndefined(arg0Value);
                return object;
            }
            if (exclude == 0 && arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.fromJSObject(arg0Value_);
                return object;
            }
            if (!JSGuards.isUndefined(arg0Value)) {
                this.fromOther_isObjectNode_ = super.insert(IsObjectNode.create());
                this.fromOther_errorBranch_ = BranchProfile.create();
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFD;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.fromOther(arg0Value, this.fromOther_isObjectNode_, this.fromOther_errorBranch_);
                return object;
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
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "fromUndefined";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "fromJSObject";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "fromOther";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.fromOther_isObjectNode_, this.fromOther_errorBranch_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static GetOptionsObjectNode create(JSContext context) {
        return new GetOptionsObjectNodeGen(context);
    }
}

