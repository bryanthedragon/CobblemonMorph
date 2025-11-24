/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
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
import com.oracle.truffle.js.runtime.objects.JSDynamicObjectGen;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSObject.class)
public final class JSObjectGen {
    private JSObjectGen() {
    }

    static {
        LibraryExport.register(JSObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSObject.class)
        public static class Uncached
        extends JSDynamicObjectGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
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
            public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.readMember(arg1Value, this, JSObject.getUncachedRead(), JSObject.language(this).bindMemberFunctions(), ExportValueNode.getUncached());
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
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnknownIdentifierException, UnsupportedMessageException, UnsupportedTypeException {
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
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, ArityException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, this, JSInteropInvokeNodeGen.getUncached(), ExportValueNode.getUncached());
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
                return arg0Value.hasIterator(this, JSInteropGetIteratorNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSObject arg0Value = (JSObject)arg0Value_;
                return arg0Value.getIterator(this, JSInteropGetIteratorNodeGen.getUncached());
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
        }

        @GeneratedBy(value=JSObject.class)
        public static class Cached
        extends JSDynamicObjectGen.InteropLibraryExports.Cached {
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
                super(receiver);
            }

            @Override
            @ExplodeLoop
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 3) != 0) {
                    if ((state_0 & 1) != 0) {
                        GetMembersNonArrayCachedData s0_ = this.getMembers_nonArrayCached_cache;
                        while (s0_ != null) {
                            assert (s0_.cachedJSClass_ != null);
                            if (JSObject.getJSClass(arg0Value) == s0_.cachedJSClass_) {
                                return JSObject.GetMembers.nonArrayCached(arg0Value, arg1Value, s0_.cachedJSClass_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
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
                        if ((state_0 & 1) != 0) {
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
                            this.state_0_ = state_0 |= 1;
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
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
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
            public NodeCost getCost() {
                GetMembersNonArrayCachedData s0_;
                int state_0 = this.state_0_;
                if ((state_0 & 3) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & 3 & (state_0 & 3) - 1) == 0 && ((s0_ = this.getMembers_nonArrayCached_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSObject)receiver).hasMembers();
            }

            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 4) != 0) {
                    Cached readMemberNode__readMember_self__ = this;
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
                    Cached readMemberNode__readMember_self__ = null;
                    readMemberNode__readMember_self__ = this;
                    this.readMemberNode__readMember_readNode_ = super.insert(ReadElementNode.create(JSObject.language(readMemberNode__readMember_self__).getJSContext()));
                    this.readMemberNode__readMember_bindMemberFunctions_ = JSObject.language(readMemberNode__readMember_self__).bindMemberFunctions();
                    this.readMemberNode__readMember_exportNode_ = super.insert(ExportValueNode.create());
                    this.state_0_ = state_0 |= 4;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
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
                    this.state_0_ = state_0 |= 8;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10) != 0) {
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
                    this.state_0_ = state_0 |= 0x10;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20) != 0) {
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
                    this.state_0_ = state_0 |= 0x20;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40) != 0) {
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
                    this.state_0_ = state_0 |= 0x40;
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
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                ((JSObject)receiver).removeMember(member);
            }

            @Override
            public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x80) != 0) {
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
                    this.state_0_ = state_0 |= 0x80;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x100) != 0) {
                    Cached invokeMemberNode__invokeMember_self__ = this;
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
                    Cached invokeMemberNode__invokeMember_self__ = null;
                    invokeMemberNode__invokeMember_self__ = this;
                    this.invokeMemberNode__invokeMember_callNode_ = super.insert(JSInteropInvokeNode.create());
                    this.invokeMemberNode__invokeMember_exportNode_ = super.insert(ExportValueNode.create());
                    this.state_0_ = state_0 |= 0x100;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200) != 0) {
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
                    this.state_0_ = state_0 |= 0x200;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x400) != 0) {
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
                    this.state_0_ = state_0 |= 0x400;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x800) != 0) {
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
                    this.state_0_ = state_0 |= 0x800;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x1000) != 0) {
                    Cached hasIteratorNode__hasIterator_self__ = this;
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
                    Cached hasIteratorNode__hasIterator_self__ = null;
                    hasIteratorNode__hasIterator_self__ = this;
                    this.getIterator = super.insert(this.getIterator == null ? JSInteropGetIteratorNode.create() : this.getIterator);
                    this.state_0_ = state_0 |= 0x1000;
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSObject arg0Value = (JSObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x2000) != 0) {
                    Cached getIteratorNode__getIterator_self__ = this;
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
                    Cached getIteratorNode__getIterator_self__ = null;
                    getIteratorNode__getIterator_self__ = this;
                    this.getIterator = super.insert(this.getIterator == null ? JSInteropGetIteratorNode.create() : this.getIterator);
                    this.state_0_ = state_0 |= 0x2000;
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
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSObject)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSObject)receiver).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSObject)receiver).toDisplayString(allowSideEffects);
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

