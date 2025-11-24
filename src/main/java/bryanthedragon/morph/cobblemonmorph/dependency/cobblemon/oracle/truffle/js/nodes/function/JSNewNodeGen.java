/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.function.JSNewNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSNewNode.class)
public final class JSNewNodeGen
extends JSNewNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSFunctionCallNode callNew;
    @Node.Child
    private NewForeignObjectData newForeignObject_cache;

    private JSNewNodeGen(JSContext context, JavaScriptNode targetNode, AbstractFunctionArgumentsNode arguments) {
        super(context, targetNode, arguments);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetNodeValue_ = this.targetNode.execute(frameValue);
        if ((state_0 & 0xF) != 0 && targetNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue__ = (JSDynamicObject)targetNodeValue_;
            if ((state_0 & 1) != 0 && JSGuards.isJSFunction(targetNodeValue__)) {
                return this.doNewReturnThis(frameValue, targetNodeValue__, this.callNew);
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSProxy(targetNodeValue__)) {
                return this.doNewJSProxy(frameValue, targetNodeValue__, this.callNew);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSAdapter(targetNodeValue__)) {
                return this.doJSAdapter(frameValue, targetNodeValue__);
            }
            if ((state_0 & 8) != 0 && JSGuards.isJavaPackage(targetNodeValue__)) {
                return this.createClassNotFoundError(frameValue, targetNodeValue__);
            }
        }
        if ((state_0 & 0x30) != 0) {
            NewForeignObjectData s4_;
            if ((state_0 & 0x10) != 0 && (s4_ = this.newForeignObject_cache) != null && JSGuards.isForeignObject(targetNodeValue_)) {
                return this.doNewForeignObject(frameValue, targetNodeValue_, s4_.interop_, s4_.convert_, s4_.toJSType_, s4_.isHostClassProf_, s4_.isAbstractProf_);
            }
            if (!((state_0 & 0x20) == 0 || JSGuards.isJSFunction(targetNodeValue_) || JSGuards.isJSAdapter(targetNodeValue_) || JSGuards.isJSProxy(targetNodeValue_) || JSGuards.isJavaPackage(targetNodeValue_) || JSGuards.isForeignObject(targetNodeValue_))) {
                return this.createFunctionTypeError(frameValue, targetNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue, targetNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object targetNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (targetNodeValue instanceof JSDynamicObject) {
                JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
                if (JSGuards.isJSFunction(targetNodeValue_)) {
                    this.callNew = super.insert(this.callNew == null ? JSFunctionCallNode.createNew() : this.callNew);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doNewReturnThis(frameValue, targetNodeValue_, this.callNew);
                    return object;
                }
                if (JSGuards.isJSProxy(targetNodeValue_)) {
                    this.callNew = super.insert(this.callNew == null ? JSFunctionCallNode.createNew() : this.callNew);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doNewJSProxy(frameValue, targetNodeValue_, this.callNew);
                    return object;
                }
                if (JSGuards.isJSAdapter(targetNodeValue_)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doJSAdapter(frameValue, targetNodeValue_);
                    return object;
                }
                if (JSGuards.isJavaPackage(targetNodeValue_)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.createClassNotFoundError(frameValue, targetNodeValue_);
                    return object;
                }
            }
            if (JSGuards.isForeignObject(targetNodeValue)) {
                NewForeignObjectData s4_ = super.insert(new NewForeignObjectData());
                s4_.interop_ = s4_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                s4_.convert_ = s4_.insertAccessor(ExportValueNode.create());
                s4_.toJSType_ = s4_.insertAccessor(ImportValueNode.create());
                s4_.isHostClassProf_ = ConditionProfile.createBinaryProfile();
                s4_.isAbstractProf_ = ConditionProfile.createBinaryProfile();
                VarHandle.storeStoreFence();
                this.newForeignObject_cache = s4_;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = this.doNewForeignObject(frameValue, targetNodeValue, s4_.interop_, s4_.convert_, s4_.toJSType_, s4_.isHostClassProf_, s4_.isAbstractProf_);
                return object;
            }
            if (!(JSGuards.isJSFunction(targetNodeValue) || JSGuards.isJSAdapter(targetNodeValue) || JSGuards.isJSProxy(targetNodeValue) || JSGuards.isJavaPackage(targetNodeValue) || JSGuards.isForeignObject(targetNodeValue))) {
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Object object = this.createFunctionTypeError(frameValue, targetNodeValue);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.targetNode}, targetNodeValue);
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
        Object[] data = new Object[7];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doNewReturnThis";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.callNew));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doNewJSProxy";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.callNew));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doJSAdapter";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "createClassNotFoundError";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doNewForeignObject";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            NewForeignObjectData s4_ = this.newForeignObject_cache;
            if (s4_ != null) {
                cached.add(Arrays.asList(s4_.interop_, s4_.convert_, s4_.toJSType_, s4_.isHostClassProf_, s4_.isAbstractProf_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "createFunctionTypeError";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        return Introspection.Provider.create(data);
    }

    public static JSNewNode create(JSContext context, JavaScriptNode targetNode, AbstractFunctionArgumentsNode arguments) {
        return new JSNewNodeGen(context, targetNode, arguments);
    }

    @GeneratedBy(value=JSNewNode.class)
    private static final class NewForeignObjectData
    extends Node {
        @Node.Child
        InteropLibrary interop_;
        @Node.Child
        ExportValueNode convert_;
        @Node.Child
        ImportValueNode toJSType_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isHostClassProf_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isAbstractProf_;

        NewForeignObjectData() {
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

