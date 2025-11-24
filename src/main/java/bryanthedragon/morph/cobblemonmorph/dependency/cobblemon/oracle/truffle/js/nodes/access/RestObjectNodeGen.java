
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.RestObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RestObjectNode.class)
public final class RestObjectNodeGen
extends RestObjectNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToObjectNode other_toObjectNode_;

    private RestObjectNodeGen(JSContext context, JavaScriptNode targetNode, JavaScriptNode sourceNode) {
        super(context, targetNode, sourceNode);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetNodeValue_ = this.targetNode.execute(frameValue);
        Object sourceNodeValue_ = this.sourceNode.execute(frameValue);
        if (state_0 != 0 && targetNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject sourceNodeValue__;
            JSDynamicObject targetNodeValue__ = (JSDynamicObject)targetNodeValue_;
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(sourceNodeValue_)) {
                return RestObjectNode.doNullOrUndefined(targetNodeValue__, sourceNodeValue_);
            }
            if ((state_0 & 2) != 0 && sourceNodeValue_ instanceof JSDynamicObject && JSGuards.isJSObject(sourceNodeValue__ = (JSDynamicObject)sourceNodeValue_)) {
                return this.copyDataProperties(targetNodeValue__, sourceNodeValue__);
            }
            if ((state_0 & 4) != 0 && !JSGuards.isJSDynamicObject(sourceNodeValue_)) {
                return this.doOther(targetNodeValue__, sourceNodeValue_, this.other_toObjectNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue_, sourceNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private Object executeAndSpecialize(Object targetNodeValue, Object sourceNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (targetNodeValue instanceof JSDynamicObject) {
                JSDynamicObject sourceNodeValue_;
                JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
                if (JSGuards.isNullOrUndefined(sourceNodeValue)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = RestObjectNode.doNullOrUndefined(targetNodeValue_, sourceNodeValue);
                    return jSDynamicObject;
                }
                if (sourceNodeValue instanceof JSDynamicObject && JSGuards.isJSObject(sourceNodeValue_ = (JSDynamicObject)sourceNodeValue)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.copyDataProperties(targetNodeValue_, sourceNodeValue_);
                    return jSDynamicObject;
                }
                if (!JSGuards.isJSDynamicObject(sourceNodeValue)) {
                    this.other_toObjectNode_ = super.insert(JSToObjectNode.createToObjectNoCheck(this.context));
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doOther(targetNodeValue_, sourceNodeValue, this.other_toObjectNode_);
                    return object;
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.targetNode, this.sourceNode}, targetNodeValue, sourceNodeValue);
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
        s[0] = "doNullOrUndefined";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "copyDataProperties";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doOther";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSToObjectNode>> cached = new ArrayList<List<JSToObjectNode>>();
            cached.add(Arrays.asList(this.other_toObjectNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static RestObjectNode create(JSContext context, JavaScriptNode targetNode, JavaScriptNode sourceNode) {
        return new RestObjectNodeGen(context, targetNode, sourceNode);
    }
}

