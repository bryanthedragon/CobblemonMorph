
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToLimitedTemporalDurationNode.class)
public final class ToLimitedTemporalDurationNodeGen
extends ToLimitedTemporalDurationNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private IsObjectNode isObjectNode_;
    @Node.Child
    private JSToStringNode toStringNode_;

    private ToLimitedTemporalDurationNodeGen() {
    }

    @Override
    public JSTemporalDurationRecord executeDynamicObject(Object arg0Value, List<TruffleString> arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            return this.toLimitedTemporalDuration(arg0Value, arg1Value, this.isObjectNode_, this.toStringNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSTemporalDurationRecord executeAndSpecialize(Object arg0Value, List<TruffleString> arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            this.isObjectNode_ = super.insert(IsObjectNode.create());
            this.toStringNode_ = super.insert(JSToStringNode.create());
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            JSTemporalDurationRecord jSTemporalDurationRecord = this.toLimitedTemporalDuration(arg0Value, arg1Value, this.isObjectNode_, this.toStringNode_);
            return jSTemporalDurationRecord;
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
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "toLimitedTemporalDuration";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.isObjectNode_, this.toStringNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static ToLimitedTemporalDurationNode create() {
        return new ToLimitedTemporalDurationNodeGen();
    }
}

