
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.ArrayLiteralNode;
import com.oracle.truffle.js.nodes.access.GetTemplateObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GetTemplateObjectNode.class)
public final class GetTemplateObjectNodeGen
extends GetTemplateObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private JSDynamicObject cached_cachedTemplate_;
    @CompilerDirectives.CompilationFinal
    private Assumption cached_assumption0_;

    private GetTemplateObjectNodeGen(JSContext context, ArrayLiteralNode rawStrings, ArrayLiteralNode cookedStrings) {
        super(context, rawStrings, cookedStrings);
    }

    private GetTemplateObjectNodeGen(JSContext context, ArrayLiteralNode rawStrings, ArrayLiteralNode cookedStrings, Object identity) {
        super(context, rawStrings, cookedStrings, identity);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0) {
            if (!Assumption.isValidAssumption(this.cached_assumption0_)) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeCached_();
                return this.executeAndSpecialize(frameValue);
            }
            assert (!this.context.isMultiContext());
            return this.doCached(frameValue, this.cached_cachedTemplate_);
        }
        if ((state_0 & 2) != 0) {
            return this.doUncached(frameValue);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(VirtualFrame frameValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Assumption cached_assumption0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && !this.context.isMultiContext() && Assumption.isValidAssumption(cached_assumption0 = this.context.getSingleRealmAssumption())) {
                this.cached_cachedTemplate_ = this.doUncached(frameValue);
                this.cached_assumption0_ = cached_assumption0;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.doCached(frameValue, this.cached_cachedTemplate_);
                return jSDynamicObject;
            }
            this.exclude_ = exclude |= 1;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            JSDynamicObject jSDynamicObject = this.doUncached(frameValue);
            return jSDynamicObject;
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

    void removeCached_() {
        Lock lock = this.getLock();
        lock.lock();
        try {
            this.state_0_ &= 0xFFFFFFFE;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSDynamicObject>> cached = new ArrayList<List<JSDynamicObject>>();
            cached.add(Arrays.asList(this.cached_cachedTemplate_));
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doUncached";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static GetTemplateObjectNode create(JSContext context, ArrayLiteralNode rawStrings, ArrayLiteralNode cookedStrings) {
        return new GetTemplateObjectNodeGen(context, rawStrings, cookedStrings);
    }

    public static GetTemplateObjectNode create(JSContext context, ArrayLiteralNode rawStrings, ArrayLiteralNode cookedStrings, Object identity) {
        return new GetTemplateObjectNodeGen(context, rawStrings, cookedStrings, identity);
    }
}

