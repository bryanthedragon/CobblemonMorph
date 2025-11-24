/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.MaxNode;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=MaxNode.class)
public final class MaxNodeGen
extends MaxNode
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile max2ParamInt_maxProfile_;
    @Node.Child
    private Max2ParamData max2Param_cache;

    private MaxNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        super(context, builtin);
        this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
    }

    @Override
    public JavaScriptNode[] getArguments() {
        return new JavaScriptNode[]{this.arguments0_};
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
            Max2ParamData s3_;
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
                return MaxNode.max0Param(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value__.length == 1) {
                return this.max1Param(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && arguments0Value__.length == 2 && MaxNode.caseIntInt(arguments0Value__)) {
                return MaxNode.max2ParamInt(arguments0Value__, this.max2ParamInt_maxProfile_);
            }
            if ((state_0 & 8) != 0 && (s3_ = this.max2Param_cache) != null && arguments0Value__.length == 2 && !MaxNode.caseIntInt(arguments0Value__)) {
                return this.max2Param(arguments0Value__, s3_.isIntBranch_, s3_.maxProfile_, s3_.toNumber1Node_, s3_.toNumber2Node_);
            }
            if ((state_0 & 0x10) != 0 && arguments0Value__.length >= 3) {
                return this.max(arguments0Value__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arguments0Value_);
    }

    @Override
    public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 8) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
        }
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 0x13) != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
                return MaxNode.max0Param(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value__.length == 1) {
                return this.max1Param(arguments0Value__);
            }
            if ((state_0 & 0x10) != 0 && arguments0Value__.length >= 3) {
                return this.max(arguments0Value__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        Object[] arguments0Value__;
        int state_0 = this.state_0_;
        if ((state_0 & 8) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 4) != 0 && arguments0Value_ instanceof Object[] && (arguments0Value__ = (Object[])arguments0Value_).length == 2 && MaxNode.caseIntInt(arguments0Value__)) {
            return MaxNode.max2ParamInt(arguments0Value__, this.max2ParamInt_maxProfile_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x1B) == 0 && state_0 != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0xC) == 0 && state_0 != 0) {
                this.executeDouble(frameValue);
                return;
            }
            this.execute(frameValue);
            return;
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return;
        }
    }

    private Object executeAndSpecialize(Object arguments0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Object[]) {
                Object[] arguments0Value_ = (Object[])arguments0Value;
                if (arguments0Value_.length == 0) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Double d = MaxNode.max0Param(arguments0Value_);
                    return d;
                }
                if (arguments0Value_.length == 1) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.max1Param(arguments0Value_);
                    return d;
                }
                if (arguments0Value_.length == 2 && MaxNode.caseIntInt(arguments0Value_)) {
                    this.max2ParamInt_maxProfile_ = ConditionProfile.createBinaryProfile();
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Integer n = MaxNode.max2ParamInt(arguments0Value_, this.max2ParamInt_maxProfile_);
                    return n;
                }
                if (arguments0Value_.length == 2 && !MaxNode.caseIntInt(arguments0Value_)) {
                    Max2ParamData s3_ = super.insert(new Max2ParamData());
                    s3_.isIntBranch_ = ConditionProfile.createBinaryProfile();
                    s3_.maxProfile_ = ConditionProfile.createBinaryProfile();
                    s3_.toNumber1Node_ = s3_.insertAccessor(JSToNumberNode.create());
                    s3_.toNumber2Node_ = s3_.insertAccessor(JSToNumberNode.create());
                    VarHandle.storeStoreFence();
                    this.max2Param_cache = s3_;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.max2Param(arguments0Value_, s3_.isIntBranch_, s3_.maxProfile_, s3_.toNumber1Node_, s3_.toNumber2Node_);
                    return object;
                }
                if (arguments0Value_.length >= 3) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.max(arguments0Value_);
                    return d;
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "max0Param";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "max1Param";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "max2ParamInt";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.max2ParamInt_maxProfile_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "max2Param";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            Max2ParamData s3_ = this.max2Param_cache;
            if (s3_ != null) {
                cached.add(Arrays.asList(s3_.isIntBranch_, s3_.maxProfile_, s3_.toNumber1Node_, s3_.toNumber2Node_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "max";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static MaxNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new MaxNodeGen(context, builtin, arguments);
    }

    @GeneratedBy(value=MaxNode.class)
    private static final class Max2ParamData
    extends Node {
        @CompilerDirectives.CompilationFinal
        ConditionProfile isIntBranch_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile maxProfile_;
        @Node.Child
        JSToNumberNode toNumber1Node_;
        @Node.Child
        JSToNumberNode toNumber2Node_;

        Max2ParamData() {
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

