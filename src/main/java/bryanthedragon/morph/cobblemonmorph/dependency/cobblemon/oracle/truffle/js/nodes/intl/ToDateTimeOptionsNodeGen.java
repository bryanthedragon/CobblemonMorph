
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.intl.ToDateTimeOptionsNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToDateTimeOptionsNode.class)
public final class ToDateTimeOptionsNodeGen
extends ToDateTimeOptionsNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private CreateObjectNode.CreateObjectWithPrototypeNode fromOtherThenUndefined_createObjectNode_;

    private ToDateTimeOptionsNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public JSDynamicObject execute(Object arg0Value, String arg1Value, String arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isUndefined(arg0Value)) {
                return this.fromUndefined(arg0Value, arg1Value, arg2Value);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arg0Value)) {
                return this.fromOtherThenUndefined(arg0Value, arg1Value, arg2Value, this.fromOtherThenUndefined_createObjectNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    private JSDynamicObject executeAndSpecialize(Object arg0Value, String arg1Value, String arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.fromUndefined(arg0Value, arg1Value, arg2Value);
                return jSDynamicObject;
            }
            if (!JSGuards.isUndefined(arg0Value)) {
                this.fromOtherThenUndefined_createObjectNode_ = super.insert(CreateObjectNode.createOrdinaryWithPrototype(this.context));
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.fromOtherThenUndefined(arg0Value, arg1Value, arg2Value, this.fromOtherThenUndefined_createObjectNode_);
                return jSDynamicObject;
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
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "fromUndefined";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "fromOtherThenUndefined";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<CreateObjectNode.CreateObjectWithPrototypeNode>> cached = new ArrayList<List<CreateObjectNode.CreateObjectWithPrototypeNode>>();
            cached.add(Arrays.asList(this.fromOtherThenUndefined_createObjectNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static ToDateTimeOptionsNode create(JSContext context) {
        return new ToDateTimeOptionsNodeGen(context);
    }
}

