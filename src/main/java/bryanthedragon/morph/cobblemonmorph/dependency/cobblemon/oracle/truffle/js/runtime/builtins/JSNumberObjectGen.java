/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.FinalBitSet;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNodeGen;
import com.oracle.truffle.js.nodes.interop.JSInteropInvokeNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInvokeNodeGen;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNodeGen;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSNumberObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSNumberObject.class)
final class JSNumberObjectGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private JSNumberObjectGen() {
    }

    static {
        LibraryExport.register(JSNumberObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSNumberObject.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        static final FinalBitSet ENABLED_MESSAGES = InteropLibraryExports.createMessageBitSet(INTEROP_LIBRARY_, "isIdenticalOrUndefined", "identityHashCode", "getMembers", "hasMembers", "readMember", "isMemberReadable", "writeMember", "isMemberModifiable", "isMemberInsertable", "removeMember", "isMemberRemovable", "invokeMember", "isMemberInvocable", "hasMemberReadSideEffects", "hasMemberWriteSideEffects", "hasIterator", "getIterator", "hasLanguage", "getLanguage", "toDisplayString", "hasMetaObject", "getMetaObject");

        private InteropLibraryExports() {
            super(InteropLibrary.class, JSNumberObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSNumberObject);
            InteropLibrary uncached = InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, new Uncached());
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSNumberObject);
            return InteropLibraryExports.createDelegate(INTEROP_LIBRARY_, new Cached(receiver));
        }

        @GeneratedBy(value=JSNumberObject.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            protected Uncached() {
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((JSNumberObject)receiver_).number;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate_) {
                return INTEROP_LIBRARY_.getUncached(delegate_);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JSNumberObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof JSNumberObject;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSDynamicObject arg0Value = (JSDynamicObject)arg0Value_;
                if (arg1Value instanceof JSDynamicObject) {
                    JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                    return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                }
                return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSDynamicObject)receiver).identityHashCode();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object arg0Value_, boolean arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return JSObject.GetMembers.nonArrayUncached(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSObject)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.readMember(arg1Value, (InteropLibrary)this.getParent(), JSObject.getUncachedRead(), JSObject.language((InteropLibrary)this.getParent()).bindMemberFunctions(), ExportValueNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.isMemberReadable(arg1Value, KeyInfoNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnknownIdentifierException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                arg0Value.writeMember(arg1Value, arg2Value, KeyInfoNodeGen.getUncached(), ImportValueNode.getUncached(), JSObject.getUncachedWrite());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.isMemberModifiable(arg1Value, KeyInfoNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.isMemberInsertable(arg1Value, KeyInfoNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((JSObject)receiver).removeMember(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.isMemberRemovable(arg1Value, KeyInfoNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, (InteropLibrary)this.getParent(), JSInteropInvokeNodeGen.getUncached(), ExportValueNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.isMemberInvocable(arg1Value, KeyInfoNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.hasMemberReadSideEffects(arg1Value, KeyInfoNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.hasMemberWriteSideEffects(arg1Value, KeyInfoNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIterator(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.hasIterator((InteropLibrary)this.getParent(), JSInteropGetIteratorNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.getIterator((InteropLibrary)this.getParent(), JSInteropGetIteratorNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSObject)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSObject)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSObject)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSNonProxyObject)receiver).hasMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSNonProxyObject)receiver).getMetaObject();
            }
        }

        @GeneratedBy(value=JSNumberObject.class)
        private static final class Cached
        extends InteropLibrary
        implements LibraryExport.DelegateExport {
            @Node.Child
            private InteropLibrary receiverNumberInteropLibrary_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private KeyInfoNode keyInfo;
            @Node.Child
            private JSInteropGetIteratorNode getIterator;
            @CompilerDirectives.CompilationFinal
            private GetMembersNonArrayCachedData getMembers_nonArrayCached_cache;
            @Node.Child
            private ReadElementNode readMemberNode__readMember_readNode_;
            @CompilerDirectives.CompilationFinal
            private boolean readMemberNode__readMember_bindMemberFunctions_;
            @Node.Child
            private ExportValueNode readMemberNode__readMember_exportNode_;
            @Node.Child
            private ImportValueNode writeMemberNode__writeMember_castValueNode_;
            @Node.Child
            private WriteElementNode writeMemberNode__writeMember_writeNode_;
            @Node.Child
            private JSInteropInvokeNode invokeMemberNode__invokeMember_callNode_;
            @Node.Child
            private ExportValueNode invokeMemberNode__invokeMember_exportNode_;

            protected Cached(Object receiver) {
                JSNumberObject castReceiver = (JSNumberObject)receiver;
                this.receiverNumberInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.number));
            }

            @Override
            public FinalBitSet getDelegateExportMessages() {
                return ENABLED_MESSAGES;
            }

            @Override
            public Object readDelegateExport(Object receiver_) {
                return ((JSNumberObject)receiver_).number;
            }

            @Override
            public Library getDelegateExportLibrary(Object delegate) {
                return this.receiverNumberInteropLibrary_;
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof JSNumberObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof JSNumberObject)) {
                    return false;
                }
                return this.receiverNumberInteropLibrary_.accepts(((JSNumberObject)receiver).number);
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 3) != 0) {
                    if ((state_0 & 1) != 0 && arg1Value instanceof JSDynamicObject) {
                        JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                        return JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 2) != 0 && Cached.isIdenticalOrUndefinedFallbackGuard_(state_0, arg0Value, arg1Value)) {
                        return JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private TriState isIdenticalOrUndefinedAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    if (arg1Value instanceof JSDynamicObject) {
                        JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        TriState triState = JSDynamicObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                        return triState;
                    }
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    TriState triState = JSDynamicObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
                    return triState;
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
                if ((state_0 & 3) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & 3 & (state_0 & 3) - 1) == 0) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNumberObject)receiver).identityHashCode();
            }

            @Override
            @ExplodeLoop
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xC) != 0) {
                    if ((state_0 & 4) != 0) {
                        GetMembersNonArrayCachedData s0_ = this.getMembers_nonArrayCached_cache;
                        while (s0_ != null) {
                            assert (s0_.cachedJSClass_ != null);
                            if (JSObject.getJSClass(arg0Value) == s0_.cachedJSClass_) {
                                return JSObject.GetMembers.nonArrayCached(arg0Value, arg1Value, s0_.cachedJSClass_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 8) != 0) {
                        return JSObject.GetMembers.nonArrayUncached(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getMembersAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getMembersAndSpecialize(JSObject arg0Value, boolean arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if (exclude == 0) {
                        JSClass cachedJSClass__;
                        int count0_ = 0;
                        GetMembersNonArrayCachedData s0_ = this.getMembers_nonArrayCached_cache;
                        if ((state_0 & 4) != 0) {
                            while (s0_ != null) {
                                assert (s0_.cachedJSClass_ != null);
                                if (JSObject.getJSClass(arg0Value) == s0_.cachedJSClass_) break;
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null && (cachedJSClass__ = JSObject.getJSClass(arg0Value)) != null && JSObject.getJSClass(arg0Value) == cachedJSClass__ && count0_ < 3) {
                            s0_ = new GetMembersNonArrayCachedData(this.getMembers_nonArrayCached_cache);
                            s0_.cachedJSClass_ = cachedJSClass__;
                            VarHandle.storeStoreFence();
                            this.getMembers_nonArrayCached_cache = s0_;
                            this.state_0_ = state_0 |= 4;
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object = JSObject.GetMembers.nonArrayCached(arg0Value, arg1Value, s0_.cachedJSClass_);
                            return object;
                        }
                    }
                    this.exclude_ = exclude |= 1;
                    this.getMembers_nonArrayCached_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = JSObject.GetMembers.nonArrayUncached(arg0Value, arg1Value);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNumberObject)receiver).hasMembers();
            }

            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10) != 0) {
                    InteropLibrary readMemberNode__readMember_self__ = (InteropLibrary)this.getParent();
                    return arg0Value.readMember(arg1Value, readMemberNode__readMember_self__, this.readMemberNode__readMember_readNode_, this.readMemberNode__readMember_bindMemberFunctions_, this.readMemberNode__readMember_exportNode_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readMemberNode_AndSpecialize(JSObject arg0Value, String arg1Value) throws UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary readMemberNode__readMember_self__ = null;
                    readMemberNode__readMember_self__ = (InteropLibrary)this.getParent();
                    this.readMemberNode__readMember_readNode_ = super.insert(ReadElementNode.create(JSObject.language(readMemberNode__readMember_self__).getJSContext()));
                    this.readMemberNode__readMember_bindMemberFunctions_ = JSObject.language(readMemberNode__readMember_self__).bindMemberFunctions();
                    this.readMemberNode__readMember_exportNode_ = super.insert(ExportValueNode.create());
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readMember(arg1Value, readMemberNode__readMember_self__, this.readMemberNode__readMember_readNode_, this.readMemberNode__readMember_bindMemberFunctions_, this.readMemberNode__readMember_exportNode_);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20) != 0) {
                    return arg0Value.isMemberReadable(arg1Value, this.keyInfo);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberReadableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberReadableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberReadable(arg1Value, this.keyInfo);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40) != 0) {
                    arg0Value.writeMember(arg1Value, arg2Value, this.keyInfo, this.writeMemberNode__writeMember_castValueNode_, this.writeMemberNode__writeMember_writeNode_);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeMemberNode_AndSpecialize(JSObject arg0Value, String arg1Value, Object arg2Value) throws UnknownIdentifierException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.writeMemberNode__writeMember_castValueNode_ = super.insert(ImportValueNode.create());
                    this.writeMemberNode__writeMember_writeNode_ = super.insert(WriteElementNode.createCachedInterop());
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeMember(arg1Value, arg2Value, this.keyInfo, this.writeMemberNode__writeMember_castValueNode_, this.writeMemberNode__writeMember_writeNode_);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x80) != 0) {
                    return arg0Value.isMemberModifiable(arg1Value, this.keyInfo);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberModifiableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberModifiable(arg1Value, this.keyInfo);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x100) != 0) {
                    return arg0Value.isMemberInsertable(arg1Value, this.keyInfo);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInsertableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberInsertable(arg1Value, this.keyInfo);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSNumberObject)receiver).removeMember(member);
            }

            @Override
            public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200) != 0) {
                    return arg0Value.isMemberRemovable(arg1Value, this.keyInfo);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberRemovableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberRemovableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberRemovable(arg1Value, this.keyInfo);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x400) != 0) {
                    InteropLibrary invokeMemberNode__invokeMember_self__ = (InteropLibrary)this.getParent();
                    return arg0Value.invokeMember(arg1Value, arg2Value, invokeMemberNode__invokeMember_self__, this.invokeMemberNode__invokeMember_callNode_, this.invokeMemberNode__invokeMember_exportNode_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object invokeMemberNode_AndSpecialize(JSObject arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedMessageException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary invokeMemberNode__invokeMember_self__ = null;
                    invokeMemberNode__invokeMember_self__ = (InteropLibrary)this.getParent();
                    this.invokeMemberNode__invokeMember_callNode_ = super.insert(JSInteropInvokeNode.create());
                    this.invokeMemberNode__invokeMember_exportNode_ = super.insert(ExportValueNode.create());
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.invokeMember(arg1Value, arg2Value, invokeMemberNode__invokeMember_self__, this.invokeMemberNode__invokeMember_callNode_, this.invokeMemberNode__invokeMember_exportNode_);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x800) != 0) {
                    return arg0Value.isMemberInvocable(arg1Value, this.keyInfo);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInvocableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInvocableNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberInvocable(arg1Value, this.keyInfo);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x1000) != 0) {
                    return arg0Value.hasMemberReadSideEffects(arg1Value, this.keyInfo);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasMemberReadSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasMemberReadSideEffectsNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasMemberReadSideEffects(arg1Value, this.keyInfo);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x2000) != 0) {
                    return arg0Value.hasMemberWriteSideEffects(arg1Value, this.keyInfo);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasMemberWriteSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasMemberWriteSideEffectsNode_AndSpecialize(JSObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyInfo = super.insert(this.keyInfo == null ? KeyInfoNodeGen.create() : this.keyInfo);
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasMemberWriteSideEffects(arg1Value, this.keyInfo);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasIterator(Object arg0Value_) {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x4000) != 0) {
                    InteropLibrary hasIteratorNode__hasIterator_self__ = (InteropLibrary)this.getParent();
                    return arg0Value.hasIterator(hasIteratorNode__hasIterator_self__, this.getIterator);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasIteratorNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasIteratorNode_AndSpecialize(JSObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary hasIteratorNode__hasIterator_self__ = null;
                    hasIteratorNode__hasIterator_self__ = (InteropLibrary)this.getParent();
                    this.getIterator = super.insert(this.getIterator == null ? JSInteropGetIteratorNode.create() : this.getIterator);
                    this.state_0_ = state_0 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasIterator(hasIteratorNode__hasIterator_self__, this.getIterator);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (arg0Value_ instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSNumberObject arg0Value = (JSNumberObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x8000) != 0) {
                    InteropLibrary getIteratorNode__getIterator_self__ = (InteropLibrary)this.getParent();
                    return arg0Value.getIterator(getIteratorNode__getIterator_self__, this.getIterator);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getIteratorNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getIteratorNode_AndSpecialize(JSObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary getIteratorNode__getIterator_self__ = null;
                    getIteratorNode__getIterator_self__ = (InteropLibrary)this.getParent();
                    this.getIterator = super.insert(this.getIterator == null ? JSInteropGetIteratorNode.create() : this.getIterator);
                    this.state_0_ = state_0 |= 0x8000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getIterator(getIteratorNode__getIterator_self__, this.getIterator);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasLanguage(Object receiver) {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNumberObject)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNumberObject)receiver).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNumberObject)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNumberObject)receiver).hasMetaObject();
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (receiver instanceof JSNumberObject) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSNumberObject)receiver).getMetaObject();
            }

            private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_0, JSDynamicObject arg0Value, Object arg1Value) {
                return (state_0 & 1) != 0 || !(arg1Value instanceof JSDynamicObject);
            }

            @GeneratedBy(value=JSObject.class)
            private static final class GetMembersNonArrayCachedData {
                @CompilerDirectives.CompilationFinal
                GetMembersNonArrayCachedData next_;
                @CompilerDirectives.CompilationFinal
                JSClass cachedJSClass_;

                GetMembersNonArrayCachedData(GetMembersNonArrayCachedData next_) {
                    this.next_ = next_;
                }
            }
        }
    }
}

