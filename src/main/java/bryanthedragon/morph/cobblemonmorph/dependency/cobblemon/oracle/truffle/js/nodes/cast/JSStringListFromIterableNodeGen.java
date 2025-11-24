/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.cast.JSStringListFromIterableNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSStringListFromIterableNode.class)
public final class JSStringListFromIterableNodeGen
extends JSStringListFromIterableNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private ToArrayData toArray_cache;

    private JSStringListFromIterableNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public List<String> executeIterable(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return JSStringListFromIterableNode.stringToList(arg0Value_);
        }
        if ((state_0 & 6) != 0) {
            ToArrayData s1_;
            if ((state_0 & 2) != 0 && (s1_ = this.toArray_cache) != null && !JSGuards.isUndefined(arg0Value) && !JSGuards.isString(arg0Value)) {
                return JSStringListFromIterableNode.toArray(arg0Value, s1_.getIteratorNode_, s1_.iteratorStepNode_, s1_.iteratorValueNode_, s1_.iteratorCloseNode_);
            }
            if ((state_0 & 4) != 0 && JSGuards.isUndefined(arg0Value)) {
                return this.doUndefined(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private List<String> executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                List<String> list = JSStringListFromIterableNode.stringToList(arg0Value_);
                return list;
            }
            if (!JSGuards.isUndefined(arg0Value) && !JSGuards.isString(arg0Value)) {
                ToArrayData s1_ = super.insert(new ToArrayData());
                s1_.getIteratorNode_ = s1_.insertAccessor(GetIteratorBaseNode.create());
                s1_.iteratorStepNode_ = s1_.insertAccessor(IteratorStepNode.create());
                s1_.iteratorValueNode_ = s1_.insertAccessor(IteratorValueNode.create());
                s1_.iteratorCloseNode_ = s1_.insertAccessor(IteratorCloseNode.create(this.context));
                VarHandle.storeStoreFence();
                this.toArray_cache = s1_;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                List<String> list = JSStringListFromIterableNode.toArray(arg0Value, s1_.getIteratorNode_, s1_.iteratorStepNode_, s1_.iteratorValueNode_, s1_.iteratorCloseNode_);
                return list;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                List<String> list = this.doUndefined(arg0Value);
                return list;
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
        Object[] s = new Object[3];
        s[0] = "stringToList";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "toArray";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            ToArrayData s1_ = this.toArray_cache;
            if (s1_ != null) {
                cached.add(Arrays.asList(s1_.getIteratorNode_, s1_.iteratorStepNode_, s1_.iteratorValueNode_, s1_.iteratorCloseNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSStringListFromIterableNode create(JSContext context) {
        return new JSStringListFromIterableNodeGen(context);
    }

    @GeneratedBy(value=JSStringListFromIterableNode.class)
    private static final class ToArrayData
    extends Node {
        @Node.Child
        GetIteratorBaseNode getIteratorNode_;
        @Node.Child
        IteratorStepNode iteratorStepNode_;
        @Node.Child
        IteratorValueNode iteratorValueNode_;
        @Node.Child
        IteratorCloseNode iteratorCloseNode_;

        ToArrayData() {
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

