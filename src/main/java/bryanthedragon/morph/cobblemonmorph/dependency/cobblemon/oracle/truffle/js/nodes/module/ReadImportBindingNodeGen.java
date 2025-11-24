/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.module;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.module.ReadImportBindingNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespaceObject;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ReadImportBindingNode.class)
public final class ReadImportBindingNodeGen
extends ReadImportBindingNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private CachedData cached_cache;

    private ReadImportBindingNodeGen(JavaScriptNode resolutionNode) {
        super(resolutionNode);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        Object resolutionNodeValue__;
        int state_0 = this.state_0_;
        Object resolutionNodeValue_ = this.resolutionNode.execute(frameValue);
        if ((state_0 & 3) != 0 && resolutionNodeValue_ instanceof ExportResolution.Resolved) {
            CachedData s0_;
            resolutionNodeValue__ = (ExportResolution.Resolved)resolutionNodeValue_;
            if ((state_0 & 1) != 0 && (s0_ = this.cached_cache) != null && s0_.frameDescriptor_ == ((ExportResolution.Resolved)resolutionNodeValue__).getModule().getFrameDescriptor() && Strings.equals(s0_.equalNode_, s0_.bindingName_, ((ExportResolution.Resolved)resolutionNodeValue__).getBindingName())) {
                return ReadImportBindingNode.doCached((ExportResolution.Resolved)resolutionNodeValue__, s0_.frameDescriptor_, s0_.bindingName_, s0_.readFrameSlot_, s0_.equalNode_);
            }
            if ((state_0 & 2) != 0) {
                return this.doUncached((ExportResolution.Resolved)resolutionNodeValue__);
            }
        }
        if ((state_0 & 4) != 0 && resolutionNodeValue_ instanceof JSModuleNamespaceObject) {
            resolutionNodeValue__ = (JSModuleNamespaceObject)resolutionNodeValue_;
            return ReadImportBindingNode.doNamespace((JSModuleNamespaceObject)resolutionNodeValue__);
        }
        if ((state_0 & 8) != 0 && ReadImportBindingNodeGen.fallbackGuard_(state_0, resolutionNodeValue_)) {
            return ReadImportBindingNode.doUnresolved(resolutionNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(resolutionNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object resolutionNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (resolutionNodeValue instanceof ExportResolution.Resolved) {
                ExportResolution.Resolved resolutionNodeValue_ = (ExportResolution.Resolved)resolutionNodeValue;
                if (exclude == 0) {
                    FrameDescriptor frameDescriptor__;
                    CachedData s0_ = this.cached_cache;
                    boolean Cached_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && s0_.frameDescriptor_ == resolutionNodeValue_.getModule().getFrameDescriptor() && Strings.equals(s0_.equalNode_, s0_.bindingName_, resolutionNodeValue_.getBindingName())) {
                        Cached_duplicateFound_ = true;
                    }
                    if (!Cached_duplicateFound_ && (frameDescriptor__ = resolutionNodeValue_.getModule().getFrameDescriptor()) == resolutionNodeValue_.getModule().getFrameDescriptor()) {
                        TruffleString bindingName__ = resolutionNodeValue_.getBindingName();
                        TruffleString.EqualNode equalNode__ = super.insert(TruffleString.EqualNode.create());
                        if (Strings.equals(equalNode__, bindingName__, resolutionNodeValue_.getBindingName()) && (state_0 & 1) == 0) {
                            s0_ = super.insert(new CachedData());
                            s0_.frameDescriptor_ = frameDescriptor__;
                            s0_.bindingName_ = bindingName__;
                            s0_.readFrameSlot_ = s0_.insertAccessor(JSReadFrameSlotNode.create(frameDescriptor__, ReadImportBindingNode.findImportedSlotIndex(bindingName__, resolutionNodeValue_.getModule())));
                            s0_.equalNode_ = s0_.insertAccessor(equalNode__);
                            VarHandle.storeStoreFence();
                            this.cached_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                            Cached_duplicateFound_ = true;
                        }
                    }
                    if (Cached_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = ReadImportBindingNode.doCached(resolutionNodeValue_, s0_.frameDescriptor_, s0_.bindingName_, s0_.readFrameSlot_, s0_.equalNode_);
                        return object;
                    }
                }
                this.exclude_ = exclude |= 1;
                this.cached_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.doUncached(resolutionNodeValue_);
                return object;
            }
            if (resolutionNodeValue instanceof JSModuleNamespaceObject) {
                JSModuleNamespaceObject resolutionNodeValue_ = (JSModuleNamespaceObject)resolutionNodeValue;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = ReadImportBindingNode.doNamespace(resolutionNodeValue_);
                return object;
            }
            this.state_0_ = state_0 |= 8;
            lock.unlock();
            hasLock = false;
            Object object = ReadImportBindingNode.doUnresolved(resolutionNodeValue);
            return object;
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
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Object>> cached = new ArrayList<List<Object>>();
            CachedData s0_ = this.cached_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.frameDescriptor_, s0_.bindingName_, s0_.readFrameSlot_, s0_.equalNode_));
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doUncached";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doNamespace";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doUnresolved";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object resolutionNodeValue) {
        if ((state_0 & 2) == 0 && resolutionNodeValue instanceof ExportResolution.Resolved) {
            return false;
        }
        return (state_0 & 4) != 0 || !(resolutionNodeValue instanceof JSModuleNamespaceObject);
    }

    public static ReadImportBindingNode create(JavaScriptNode resolutionNode) {
        return new ReadImportBindingNodeGen(resolutionNode);
    }

    @GeneratedBy(value=ReadImportBindingNode.class)
    private static final class CachedData
    extends Node {
        @CompilerDirectives.CompilationFinal
        FrameDescriptor frameDescriptor_;
        @CompilerDirectives.CompilationFinal
        TruffleString bindingName_;
        @Node.Child
        JSReadFrameSlotNode readFrameSlot_;
        @Node.Child
        TruffleString.EqualNode equalNode_;

        CachedData() {
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

