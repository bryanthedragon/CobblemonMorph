
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.JSProxyHasPropertyNode;
import com.oracle.truffle.js.nodes.binary.InNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InNode.class)
public final class InNodeGen
extends InNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSProxyHasPropertyNode proxy_proxyHasPropertyNode_;
    @Node.Child
    private IsObjectNode foreign_isObjectNode_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile foreign_errorBranch_;

    private InNodeGen(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        super(context, left, right);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if (state_0 != 0) {
            Object rightNodeValue__;
            if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
                rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
                if ((state_0 & 1) != 0 && JSGuards.isJSObject(rightNodeValue__) && !JSGuards.isJSProxy(rightNodeValue__)) {
                    return this.doObject(leftNodeValue_, (JSDynamicObject)rightNodeValue__);
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSProxy(rightNodeValue__)) {
                    return this.doProxy(leftNodeValue_, (JSDynamicObject)rightNodeValue__, this.proxy_proxyHasPropertyNode_);
                }
            }
            if ((state_0 & 0xC) != 0) {
                if ((state_0 & 4) != 0 && JSGuards.isForeignObject(rightNodeValue_)) {
                    return this.doForeign(leftNodeValue_, rightNodeValue_, this.foreign_isObjectNode_, this.foreign_errorBranch_);
                }
                if ((state_0 & 8) != 0 && JSGuards.isNullOrUndefined(rightNodeValue_)) {
                    return InNode.doNullOrUndefined(leftNodeValue_, rightNodeValue_);
                }
            }
            if ((state_0 & 0x10) != 0 && rightNodeValue_ instanceof Symbol) {
                rightNodeValue__ = (Symbol)rightNodeValue_;
                return InNode.doSymbol(leftNodeValue_, (Symbol)rightNodeValue__);
            }
            if ((state_0 & 0x20) != 0 && rightNodeValue_ instanceof TruffleString) {
                rightNodeValue__ = (TruffleString)rightNodeValue_;
                return InNode.doTString(leftNodeValue_, (TruffleString)rightNodeValue__);
            }
            if ((state_0 & 0x40) != 0 && rightNodeValue_ instanceof SafeInteger) {
                rightNodeValue__ = (SafeInteger)rightNodeValue_;
                return InNode.doSafeInteger(leftNodeValue_, (SafeInteger)rightNodeValue__);
            }
            if ((state_0 & 0x80) != 0 && rightNodeValue_ instanceof BigInt) {
                rightNodeValue__ = (BigInt)rightNodeValue_;
                return InNode.doBigInt(leftNodeValue_, (BigInt)rightNodeValue__);
            }
            if ((state_0 & 0x100) != 0 && !JSGuards.isTruffleObject(rightNodeValue_)) {
                return InNode.doNotTruffleObject(leftNodeValue_, rightNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
        int state_0 = this.state_0_;
        if ((state_0 & 0x1F8) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
        }
        Object leftNodeValue_ = this.leftNode.execute(frameValue);
        Object rightNodeValue_ = this.rightNode.execute(frameValue);
        if ((state_0 & 7) != 0) {
            if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
                JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
                if ((state_0 & 1) != 0 && JSGuards.isJSObject(rightNodeValue__) && !JSGuards.isJSProxy(rightNodeValue__)) {
                    return this.doObject(leftNodeValue_, rightNodeValue__);
                }
                if ((state_0 & 2) != 0 && JSGuards.isJSProxy(rightNodeValue__)) {
                    return this.doProxy(leftNodeValue_, rightNodeValue__, this.proxy_proxyHasPropertyNode_);
                }
            }
            if ((state_0 & 4) != 0 && JSGuards.isForeignObject(rightNodeValue_)) {
                return this.doForeign(leftNodeValue_, rightNodeValue_, this.foreign_isObjectNode_, this.foreign_errorBranch_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return JSTypesGen.expectBoolean(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        try {
            if ((state_0 & 0x1F8) == 0 && state_0 != 0) {
                this.executeBoolean(frameValue);
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

    private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object rightNodeValue_;
            int state_0 = this.state_0_;
            if (rightNodeValue instanceof JSDynamicObject) {
                rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                if (JSGuards.isJSObject(rightNodeValue_) && !JSGuards.isJSProxy(rightNodeValue_)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Boolean bl = this.doObject(leftNodeValue, (JSDynamicObject)rightNodeValue_);
                    return bl;
                }
                if (JSGuards.isJSProxy(rightNodeValue_)) {
                    this.proxy_proxyHasPropertyNode_ = super.insert(JSProxyHasPropertyNode.create(this.context));
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Boolean bl = this.doProxy(leftNodeValue, (JSDynamicObject)rightNodeValue_, this.proxy_proxyHasPropertyNode_);
                    return bl;
                }
            }
            if (JSGuards.isForeignObject(rightNodeValue)) {
                this.foreign_isObjectNode_ = super.insert(IsObjectNode.create());
                this.foreign_errorBranch_ = BranchProfile.create();
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                rightNodeValue_ = this.doForeign(leftNodeValue, rightNodeValue, this.foreign_isObjectNode_, this.foreign_errorBranch_);
                return rightNodeValue_;
            }
            if (JSGuards.isNullOrUndefined(rightNodeValue)) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                rightNodeValue_ = InNode.doNullOrUndefined(leftNodeValue, rightNodeValue);
                return rightNodeValue_;
            }
            if (rightNodeValue instanceof Symbol) {
                rightNodeValue_ = (Symbol)rightNodeValue;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = InNode.doSymbol(leftNodeValue, (Symbol)rightNodeValue_);
                return object;
            }
            if (rightNodeValue instanceof TruffleString) {
                rightNodeValue_ = (TruffleString)rightNodeValue;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Object object = InNode.doTString(leftNodeValue, (TruffleString)rightNodeValue_);
                return object;
            }
            if (rightNodeValue instanceof SafeInteger) {
                rightNodeValue_ = (SafeInteger)rightNodeValue;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Object object = InNode.doSafeInteger(leftNodeValue, (SafeInteger)rightNodeValue_);
                return object;
            }
            if (rightNodeValue instanceof BigInt) {
                rightNodeValue_ = (BigInt)rightNodeValue;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Object object = InNode.doBigInt(leftNodeValue, (BigInt)rightNodeValue_);
                return object;
            }
            if (!JSGuards.isTruffleObject(rightNodeValue)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Object object = InNode.doNotTruffleObject(leftNodeValue, rightNodeValue);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.leftNode, this.rightNode}, leftNodeValue, rightNodeValue);
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
        Object[] data = new Object[10];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doObject";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doProxy";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.proxy_proxyHasPropertyNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doForeign";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.foreign_isObjectNode_, this.foreign_errorBranch_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "doNullOrUndefined";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doTString";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doNotTruffleObject";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        return Introspection.Provider.create(data);
    }

    public static InNode create(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        return new InNodeGen(context, left, right);
    }
}

