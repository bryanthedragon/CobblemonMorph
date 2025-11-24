
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
import com.oracle.truffle.js.builtins.math.MinNode;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=MinNode.class)
public final class MinNodeGen
extends MinNode
implements Introspection.Provider {
    @Node.Child
    private JavaScriptNode arguments0_;
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile minProfile;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile min2Param_isIntBranch_;
    @Node.Child
    private JSToNumberNode min2Param_toNumber1Node_;
    @Node.Child
    private JSToNumberNode min2Param_toNumber2Node_;

    private MinNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
                return MinNode.min0Param(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value__.length == 1) {
                return this.min1Param(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && arguments0Value__.length == 2 && MinNode.caseIntInt(arguments0Value__)) {
                return MinNode.min2ParamInt(arguments0Value__, this.minProfile);
            }
            if ((state_0 & 8) != 0 && arguments0Value__.length == 2 && !MinNode.caseIntInt(arguments0Value__)) {
                return this.min2Param(arguments0Value__, this.min2Param_isIntBranch_, this.minProfile, this.min2Param_toNumber1Node_, this.min2Param_toNumber2Node_);
            }
            if ((state_0 & 0x10) != 0 && arguments0Value__.length == 3 && MinNode.caseIntIntInt(arguments0Value__)) {
                return this.min3ParamInt(arguments0Value__);
            }
            if ((state_0 & 0x20) != 0 && arguments0Value__.length == 3 && !MinNode.caseIntIntInt(arguments0Value__)) {
                return this.min3ParamOther(arguments0Value__);
            }
            if ((state_0 & 0x40) != 0 && arguments0Value__.length >= 4) {
                return this.minGeneric(arguments0Value__);
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
        if ((state_0 & 0x63) != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
                return MinNode.min0Param(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && arguments0Value__.length == 1) {
                return this.min1Param(arguments0Value__);
            }
            if ((state_0 & 0x20) != 0 && arguments0Value__.length == 3 && !MinNode.caseIntIntInt(arguments0Value__)) {
                return this.min3ParamOther(arguments0Value__);
            }
            if ((state_0 & 0x40) != 0 && arguments0Value__.length >= 4) {
                return this.minGeneric(arguments0Value__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
    }

    @Override
    public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 8) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
        }
        Object arguments0Value_ = this.arguments0_.execute(frameValue);
        if ((state_0 & 0x14) != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 4) != 0 && arguments0Value__.length == 2 && MinNode.caseIntInt(arguments0Value__)) {
                return MinNode.min2ParamInt(arguments0Value__, this.minProfile);
            }
            if ((state_0 & 0x10) != 0 && arguments0Value__.length == 3 && MinNode.caseIntIntInt(arguments0Value__)) {
                return this.min3ParamInt(arguments0Value__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x6B) == 0 && state_0 != 0) {
                this.executeInt(frameValue);
                return;
            }
            if ((state_0 & 0x1C) == 0 && state_0 != 0) {
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
                    Double d = MinNode.min0Param(arguments0Value_);
                    return d;
                }
                if (arguments0Value_.length == 1) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.min1Param(arguments0Value_);
                    return d;
                }
                if (arguments0Value_.length == 2 && MinNode.caseIntInt(arguments0Value_)) {
                    this.minProfile = this.minProfile == null ? ConditionProfile.createBinaryProfile() : this.minProfile;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Integer n = MinNode.min2ParamInt(arguments0Value_, this.minProfile);
                    return n;
                }
                if (arguments0Value_.length == 2 && !MinNode.caseIntInt(arguments0Value_)) {
                    this.min2Param_isIntBranch_ = ConditionProfile.createBinaryProfile();
                    this.minProfile = this.minProfile == null ? ConditionProfile.createBinaryProfile() : this.minProfile;
                    this.min2Param_toNumber1Node_ = super.insert(JSToNumberNode.create());
                    this.min2Param_toNumber2Node_ = super.insert(JSToNumberNode.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.min2Param(arguments0Value_, this.min2Param_isIntBranch_, this.minProfile, this.min2Param_toNumber1Node_, this.min2Param_toNumber2Node_);
                    return object;
                }
                if (arguments0Value_.length == 3 && MinNode.caseIntIntInt(arguments0Value_)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Integer n = this.min3ParamInt(arguments0Value_);
                    return n;
                }
                if (arguments0Value_.length == 3 && !MinNode.caseIntIntInt(arguments0Value_)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.min3ParamOther(arguments0Value_);
                    return d;
                }
                if (arguments0Value_.length >= 4) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.minGeneric(arguments0Value_);
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
        Object[] data = new Object[8];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "min0Param";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "min1Param";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "min2ParamInt";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.minProfile));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "min2Param";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.min2Param_isIntBranch_, this.minProfile, this.min2Param_toNumber1Node_, this.min2Param_toNumber2Node_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "min3ParamInt";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "min3ParamOther";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "minGeneric";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        return Introspection.Provider.create(data);
    }

    public static MinNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return new MinNodeGen(context, builtin, arguments);
    }
}

