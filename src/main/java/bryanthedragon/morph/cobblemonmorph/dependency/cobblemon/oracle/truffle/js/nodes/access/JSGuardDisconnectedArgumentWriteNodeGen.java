
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSGuardDisconnectedArgumentWrite;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSGuardDisconnectedArgumentWrite.class)
public final class JSGuardDisconnectedArgumentWriteNodeGen
extends JSGuardDisconnectedArgumentWrite
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile unconnected;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile objectDisconnected_wasDisconnected_;

    private JSGuardDisconnectedArgumentWriteNodeGen(int index, WriteElementNode argumentsArrayAccess, JavaScriptNode argumentsArray, JavaScriptNode rhs, TruffleString name) {
        super(index, argumentsArrayAccess, argumentsArray, rhs, name);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object argumentsArrayNodeValue_ = this.argumentsArrayNode.execute(frameValue);
        Object rhsNodeValue_ = this.rhsNode.execute(frameValue);
        if (state_0 != 0 && argumentsArrayNodeValue_ instanceof JSArgumentsObject) {
            JSArgumentsObject argumentsArrayNodeValue__ = (JSArgumentsObject)argumentsArrayNodeValue_;
            if ((state_0 & 1) != 0 && !JSGuards.isArgumentsDisconnected(argumentsArrayNodeValue__)) {
                return this.doObject(argumentsArrayNodeValue__, rhsNodeValue_, this.unconnected);
            }
            if ((state_0 & 2) != 0 && JSGuards.isArgumentsDisconnected(argumentsArrayNodeValue__)) {
                return this.doObjectDisconnected(argumentsArrayNodeValue__, rhsNodeValue_, this.objectDisconnected_wasDisconnected_, this.unconnected);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(argumentsArrayNodeValue_, rhsNodeValue_);
    }

    @Override
    protected void executeWrite(VirtualFrame frameValue, Object argumentsArrayNodeValue, Object rhsNodeValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0 && argumentsArrayNodeValue instanceof JSArgumentsObject) {
            JSArgumentsObject argumentsArrayNodeValue_ = (JSArgumentsObject)argumentsArrayNodeValue;
            if ((state_0 & 1) != 0 && !JSGuards.isArgumentsDisconnected(argumentsArrayNodeValue_)) {
                this.doObject(argumentsArrayNodeValue_, rhsNodeValue, this.unconnected);
                return;
            }
            if ((state_0 & 2) != 0 && JSGuards.isArgumentsDisconnected(argumentsArrayNodeValue_)) {
                this.doObjectDisconnected(argumentsArrayNodeValue_, rhsNodeValue, this.objectDisconnected_wasDisconnected_, this.unconnected);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(argumentsArrayNodeValue, rhsNodeValue);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private Object executeAndSpecialize(Object argumentsArrayNodeValue, Object rhsNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (argumentsArrayNodeValue instanceof JSArgumentsObject) {
                JSArgumentsObject argumentsArrayNodeValue_ = (JSArgumentsObject)argumentsArrayNodeValue;
                if (!JSGuards.isArgumentsDisconnected(argumentsArrayNodeValue_)) {
                    this.unconnected = this.unconnected == null ? ConditionProfile.createBinaryProfile() : this.unconnected;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doObject(argumentsArrayNodeValue_, rhsNodeValue, this.unconnected);
                    return object;
                }
                if (JSGuards.isArgumentsDisconnected(argumentsArrayNodeValue_)) {
                    this.objectDisconnected_wasDisconnected_ = ConditionProfile.createBinaryProfile();
                    this.unconnected = this.unconnected == null ? ConditionProfile.createBinaryProfile() : this.unconnected;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doObjectDisconnected(argumentsArrayNodeValue_, rhsNodeValue, this.objectDisconnected_wasDisconnected_, this.unconnected);
                    return object;
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.argumentsArrayNode, this.rhsNode}, argumentsArrayNodeValue, rhsNodeValue);
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
        ArrayList<List<ConditionProfile>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doObject";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<ConditionProfile>>();
            cached.add(Arrays.asList(this.unconnected));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doObjectDisconnected";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.objectDisconnected_wasDisconnected_, this.unconnected));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static JSGuardDisconnectedArgumentWrite create(int index, WriteElementNode argumentsArrayAccess, JavaScriptNode argumentsArray, JavaScriptNode rhs, TruffleString name) {
        return new JSGuardDisconnectedArgumentWriteNodeGen(index, argumentsArrayAccess, argumentsArray, rhs, name);
    }
}

