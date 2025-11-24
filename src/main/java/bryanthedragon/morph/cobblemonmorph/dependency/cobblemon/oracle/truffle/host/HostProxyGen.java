
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.host.GuestToHostCodeCache;
import com.oracle.truffle.host.HostProxy;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HostProxy.class)
final class HostProxyGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private HostProxyGen() {
    }

    static {
        LibraryExport.register(HostProxy.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=HostProxy.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, HostProxy.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof HostProxy);
            Uncached uncached = new Uncached();
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof HostProxy);
            return new Cached();
        }

        @GeneratedBy(value=HostProxy.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostProxy) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HostProxy;
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
                HostProxy arg0Value = (HostProxy)arg0Value_;
                if (arg1Value instanceof HostProxy) {
                    HostProxy arg1Value_ = (HostProxy)arg1Value;
                    return HostProxy.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                }
                return HostProxy.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isInstantiable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object instantiate(Object arg0Value_, Object ... arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.instantiate(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isExecutable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.execute(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isPointer(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isPointer();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asPointer(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.asPointer(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasArrayElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).hasArrayElements();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.readArrayElement(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                arg0Value.writeArrayElement(arg1Value, arg2Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                arg0Value.removeArrayElement(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getArraySize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.getArraySize(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isArrayElementExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isArrayElementExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isArrayElementExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isArrayElementInsertable(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.getMembers(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.readMember(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                arg0Value.writeMember(arg1Value, arg2Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, this, INTEROP_LIBRARY_.getUncached(), arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isMemberInvocable(arg1Value, this, INTEROP_LIBRARY_.getUncached(), arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                arg0Value.removeMember(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isMemberExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isMemberExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isMemberExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isMemberInsertable(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isTime();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isTimeZone();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ZoneId asTimeZone(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.asTimeZone(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalDate asDate(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.asDate(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalTime asTime(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.asTime(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Instant asInstant(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.asInstant(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isDuration();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Duration asDuration(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.asDuration(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).hasMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).getMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).hasIterator();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.getIterator(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).isIterator();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.hasIteratorNextElement(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.getIteratorNextElement(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasHashEntries(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostProxy)receiver).hasHashEntries();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.getHashSize(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isHashValueExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isHashValueExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isHashValueExisting(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.readHashValue(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.isHashEntryInsertable(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                arg0Value.writeHashEntry(arg1Value, arg2Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                arg0Value.removeHashEntry(arg1Value, this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getHashEntriesIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostProxy arg0Value = (HostProxy)arg0Value_;
                return arg0Value.getHashEntriesIterator(this, arg0Value.context.getGuestToHostCache());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return HostProxy.identityHashCode((HostProxy)receiver);
            }
        }

        @GeneratedBy(value=HostProxy.class)
        private static final class Cached
        extends InteropLibrary {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_1_;
            @CompilerDirectives.CompilationFinal
            private GuestToHostCodeCache cache;
            @Node.Child
            private InteropLibrary executables;

            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostProxy) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HostProxy;
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 3) != 0) {
                    if ((state_0 & 1) != 0 && arg1Value instanceof HostProxy) {
                        HostProxy arg1Value_ = (HostProxy)arg1Value;
                        return HostProxy.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                    }
                    if ((state_0 & 2) != 0 && Cached.isIdenticalOrUndefinedFallbackGuard_(state_0, arg0Value, arg1Value)) {
                        return HostProxy.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
            }

            private TriState isIdenticalOrUndefinedAndSpecialize(HostProxy arg0Value, Object arg1Value) {
                int state_0 = this.state_0_;
                if (arg1Value instanceof HostProxy) {
                    HostProxy arg1Value_ = (HostProxy)arg1Value;
                    this.state_0_ = state_0 |= 1;
                    return HostProxy.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                }
                this.state_0_ = state_0 |= 2;
                return HostProxy.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
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
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isInstantiable();
            }

            @Override
            public Object instantiate(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 4) != 0) {
                    Cached instantiateNode__instantiate_library__ = this;
                    return arg0Value.instantiate(arg1Value, instantiateNode__instantiate_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.instantiateNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object instantiateNode_AndSpecialize(HostProxy arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached instantiateNode__instantiate_library__ = null;
                    instantiateNode__instantiate_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.instantiate(arg1Value, instantiateNode__instantiate_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isExecutable();
            }

            @Override
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    Cached executeNode__execute_library__ = this;
                    return arg0Value.execute(arg1Value, executeNode__execute_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeNode_AndSpecialize(HostProxy arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached executeNode__execute_library__ = null;
                    executeNode__execute_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.execute(arg1Value, executeNode__execute_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isPointer(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isPointer();
            }

            @Override
            public long asPointer(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10) != 0) {
                    Cached asPointerNode__asPointer_library__ = this;
                    return arg0Value.asPointer(asPointerNode__asPointer_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asPointerNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long asPointerNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached asPointerNode__asPointer_library__ = null;
                    asPointerNode__asPointer_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.asPointer(asPointerNode__asPointer_library__, this.cache);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasArrayElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).hasArrayElements();
            }

            @Override
            public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20) != 0) {
                    Cached readArrayElementNode__readArrayElement_library__ = this;
                    return arg0Value.readArrayElement(arg1Value, readArrayElementNode__readArrayElement_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readArrayElementNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readArrayElementNode_AndSpecialize(HostProxy arg0Value, long arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached readArrayElementNode__readArrayElement_library__ = null;
                    readArrayElementNode__readArrayElement_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readArrayElement(arg1Value, readArrayElementNode__readArrayElement_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, InvalidArrayIndexException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40) != 0) {
                    Cached writeArrayElementNode__writeArrayElement_library__ = this;
                    arg0Value.writeArrayElement(arg1Value, arg2Value, writeArrayElementNode__writeArrayElement_library__, this.cache);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeArrayElementNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeArrayElementNode_AndSpecialize(HostProxy arg0Value, long arg1Value, Object arg2Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached writeArrayElementNode__writeArrayElement_library__ = null;
                    writeArrayElementNode__writeArrayElement_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeArrayElement(arg1Value, arg2Value, writeArrayElementNode__writeArrayElement_library__, this.cache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void removeArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x80) != 0) {
                    Cached removeArrayElementNode__removeArrayElement_library__ = this;
                    arg0Value.removeArrayElement(arg1Value, removeArrayElementNode__removeArrayElement_library__, this.cache);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeArrayElementNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void removeArrayElementNode_AndSpecialize(HostProxy arg0Value, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached removeArrayElementNode__removeArrayElement_library__ = null;
                    removeArrayElementNode__removeArrayElement_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.removeArrayElement(arg1Value, removeArrayElementNode__removeArrayElement_library__, this.cache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public long getArraySize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x100) != 0) {
                    Cached getArraySizeNode__getArraySize_library__ = this;
                    return arg0Value.getArraySize(getArraySizeNode__getArraySize_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getArraySizeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long getArraySizeNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached getArraySizeNode__getArraySize_library__ = null;
                    getArraySizeNode__getArraySize_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.getArraySize(getArraySizeNode__getArraySize_library__, this.cache);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200) != 0) {
                    Cached isArrayElementExistingNode__isArrayElementExisting_library__ = this;
                    return arg0Value.isArrayElementExisting(arg1Value, isArrayElementExistingNode__isArrayElementExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isArrayElementExistingNode_AndSpecialize(HostProxy arg0Value, long arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached isArrayElementExistingNode__isArrayElementExisting_library__ = null;
                    isArrayElementExistingNode__isArrayElementExisting_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isArrayElementExisting(arg1Value, isArrayElementExistingNode__isArrayElementExisting_library__, this.cache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200) != 0) {
                    Cached isArrayElementExistingNode__isArrayElementExisting_library__ = this;
                    return arg0Value.isArrayElementExisting(arg1Value, isArrayElementExistingNode__isArrayElementExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200) != 0) {
                    Cached isArrayElementExistingNode__isArrayElementExisting_library__ = this;
                    return arg0Value.isArrayElementExisting(arg1Value, isArrayElementExistingNode__isArrayElementExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x400) != 0) {
                    Cached isArrayElementInsertableNode__isArrayElementInsertable_library__ = this;
                    return arg0Value.isArrayElementInsertable(arg1Value, isArrayElementInsertableNode__isArrayElementInsertable_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isArrayElementInsertableNode_AndSpecialize(HostProxy arg0Value, long arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached isArrayElementInsertableNode__isArrayElementInsertable_library__ = null;
                    isArrayElementInsertableNode__isArrayElementInsertable_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isArrayElementInsertable(arg1Value, isArrayElementInsertableNode__isArrayElementInsertable_library__, this.cache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).hasMembers();
            }

            @Override
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x800) != 0) {
                    Cached getMembersNode__getMembers_library__ = this;
                    return arg0Value.getMembers(arg1Value, getMembersNode__getMembers_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getMembersNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getMembersNode_AndSpecialize(HostProxy arg0Value, boolean arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached getMembersNode__getMembers_library__ = null;
                    getMembersNode__getMembers_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getMembers(arg1Value, getMembersNode__getMembers_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x1000) != 0) {
                    Cached readMemberNode__readMember_library__ = this;
                    return arg0Value.readMember(arg1Value, readMemberNode__readMember_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached readMemberNode__readMember_library__ = null;
                    readMemberNode__readMember_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readMember(arg1Value, readMemberNode__readMember_library__, this.cache);
                    return object;
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
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x2000) != 0) {
                    Cached writeMemberNode__writeMember_library__ = this;
                    arg0Value.writeMember(arg1Value, arg2Value, writeMemberNode__writeMember_library__, this.cache);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value, Object arg2Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached writeMemberNode__writeMember_library__ = null;
                    writeMemberNode__writeMember_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeMember(arg1Value, arg2Value, writeMemberNode__writeMember_library__, this.cache);
                    return;
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
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x4000) != 0) {
                    Cached invokeMemberNode__invokeMember_library__ = this;
                    return arg0Value.invokeMember(arg1Value, arg2Value, invokeMemberNode__invokeMember_library__, this.executables, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object invokeMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached invokeMemberNode__invokeMember_library__ = null;
                    invokeMemberNode__invokeMember_library__ = this;
                    this.executables = super.insert(this.executables == null ? INTEROP_LIBRARY_.createDispatched(5) : this.executables);
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.invokeMember(arg1Value, arg2Value, invokeMemberNode__invokeMember_library__, this.executables, this.cache);
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
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x8000) != 0) {
                    Cached isMemberInvocableNode__isMemberInvocable_library__ = this;
                    return arg0Value.isMemberInvocable(arg1Value, isMemberInvocableNode__isMemberInvocable_library__, this.executables, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInvocableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInvocableNode_AndSpecialize(HostProxy arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached isMemberInvocableNode__isMemberInvocable_library__ = null;
                    isMemberInvocableNode__isMemberInvocable_library__ = this;
                    this.executables = super.insert(this.executables == null ? INTEROP_LIBRARY_.createDispatched(5) : this.executables);
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x8000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberInvocable(arg1Value, isMemberInvocableNode__isMemberInvocable_library__, this.executables, this.cache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10000) != 0) {
                    Cached removeMemberNode__removeMember_library__ = this;
                    arg0Value.removeMember(arg1Value, removeMemberNode__removeMember_library__, this.cache);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeMemberNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void removeMemberNode_AndSpecialize(HostProxy arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached removeMemberNode__removeMember_library__ = null;
                    removeMemberNode__removeMember_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x10000;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.removeMember(arg1Value, removeMemberNode__removeMember_library__, this.cache);
                    return;
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
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20000) != 0) {
                    Cached isMemberExistingNode__isMemberExisting_library__ = this;
                    return arg0Value.isMemberExisting(arg1Value, isMemberExistingNode__isMemberExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberExistingNode_AndSpecialize(HostProxy arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached isMemberExistingNode__isMemberExisting_library__ = null;
                    isMemberExistingNode__isMemberExisting_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x20000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberExisting(arg1Value, isMemberExistingNode__isMemberExisting_library__, this.cache);
                    return bl;
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
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20000) != 0) {
                    Cached isMemberExistingNode__isMemberExisting_library__ = this;
                    return arg0Value.isMemberExisting(arg1Value, isMemberExistingNode__isMemberExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20000) != 0) {
                    Cached isMemberExistingNode__isMemberExisting_library__ = this;
                    return arg0Value.isMemberExisting(arg1Value, isMemberExistingNode__isMemberExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40000) != 0) {
                    Cached isMemberInsertableNode__isMemberInsertable_library__ = this;
                    return arg0Value.isMemberInsertable(arg1Value, isMemberInsertableNode__isMemberInsertable_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInsertableNode_AndSpecialize(HostProxy arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached isMemberInsertableNode__isMemberInsertable_library__ = null;
                    isMemberInsertableNode__isMemberInsertable_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x40000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberInsertable(arg1Value, isMemberInsertableNode__isMemberInsertable_library__, this.cache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isDate();
            }

            @Override
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isTime();
            }

            @Override
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isTimeZone();
            }

            @Override
            public ZoneId asTimeZone(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x80000) != 0) {
                    Cached asTimeZoneNode__asTimeZone_library__ = this;
                    return arg0Value.asTimeZone(asTimeZoneNode__asTimeZone_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asTimeZoneNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private ZoneId asTimeZoneNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached asTimeZoneNode__asTimeZone_library__ = null;
                    asTimeZoneNode__asTimeZone_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x80000;
                    lock.unlock();
                    hasLock = false;
                    ZoneId zoneId = arg0Value.asTimeZone(asTimeZoneNode__asTimeZone_library__, this.cache);
                    return zoneId;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public LocalDate asDate(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x100000) != 0) {
                    Cached asDateNode__asDate_library__ = this;
                    return arg0Value.asDate(asDateNode__asDate_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asDateNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private LocalDate asDateNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached asDateNode__asDate_library__ = null;
                    asDateNode__asDate_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x100000;
                    lock.unlock();
                    hasLock = false;
                    LocalDate localDate = arg0Value.asDate(asDateNode__asDate_library__, this.cache);
                    return localDate;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public LocalTime asTime(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200000) != 0) {
                    Cached asTimeNode__asTime_library__ = this;
                    return arg0Value.asTime(asTimeNode__asTime_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asTimeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private LocalTime asTimeNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached asTimeNode__asTime_library__ = null;
                    asTimeNode__asTime_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x200000;
                    lock.unlock();
                    hasLock = false;
                    LocalTime localTime = arg0Value.asTime(asTimeNode__asTime_library__, this.cache);
                    return localTime;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Instant asInstant(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x400000) != 0) {
                    Cached asInstantNode__asInstant_library__ = this;
                    return arg0Value.asInstant(asInstantNode__asInstant_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asInstantNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Instant asInstantNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached asInstantNode__asInstant_library__ = null;
                    asInstantNode__asInstant_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x400000;
                    lock.unlock();
                    hasLock = false;
                    Instant instant = arg0Value.asInstant(asInstantNode__asInstant_library__, this.cache);
                    return instant;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isDuration();
            }

            @Override
            public Duration asDuration(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x800000) != 0) {
                    Cached asDurationNode__asDuration_library__ = this;
                    return arg0Value.asDuration(asDurationNode__asDuration_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asDurationNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Duration asDurationNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached asDurationNode__asDuration_library__ = null;
                    asDurationNode__asDuration_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x800000;
                    lock.unlock();
                    hasLock = false;
                    Duration duration = arg0Value.asDuration(asDurationNode__asDuration_library__, this.cache);
                    return duration;
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
                return ((HostProxy)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).hasMetaObject();
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).getMetaObject();
            }

            @Override
            public boolean hasIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).hasIterator();
            }

            @Override
            public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x1000000) != 0) {
                    Cached getIteratorNode__getIterator_library__ = this;
                    return arg0Value.getIterator(getIteratorNode__getIterator_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getIteratorNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getIteratorNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached getIteratorNode__getIterator_library__ = null;
                    getIteratorNode__getIterator_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x1000000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getIterator(getIteratorNode__getIterator_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isIterator(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).isIterator();
            }

            @Override
            public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x2000000) != 0) {
                    Cached hasIteratorNextElementNode__hasIteratorNextElement_library__ = this;
                    return arg0Value.hasIteratorNextElement(hasIteratorNextElementNode__hasIteratorNextElement_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasIteratorNextElementNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasIteratorNextElementNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached hasIteratorNextElementNode__hasIteratorNextElement_library__ = null;
                    hasIteratorNextElementNode__hasIteratorNextElement_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x2000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasIteratorNextElement(hasIteratorNextElementNode__hasIteratorNextElement_library__, this.cache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x4000000) != 0) {
                    Cached getIteratorNextElementNode__getIteratorNextElement_library__ = this;
                    return arg0Value.getIteratorNextElement(getIteratorNextElementNode__getIteratorNextElement_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getIteratorNextElementNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getIteratorNextElementNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached getIteratorNextElementNode__getIteratorNextElement_library__ = null;
                    getIteratorNextElementNode__getIteratorNextElement_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x4000000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getIteratorNextElement(getIteratorNextElementNode__getIteratorNextElement_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasHashEntries(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostProxy)receiver).hasHashEntries();
            }

            @Override
            public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x8000000) != 0) {
                    Cached getHashSizeNode__getHashSize_library__ = this;
                    return arg0Value.getHashSize(getHashSizeNode__getHashSize_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getHashSizeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long getHashSizeNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached getHashSizeNode__getHashSize_library__ = null;
                    getHashSizeNode__getHashSize_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x8000000;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.getHashSize(getHashSizeNode__getHashSize_library__, this.cache);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10000000) != 0) {
                    Cached isHashValueExistingNode__isHashValueExisting_library__ = this;
                    return arg0Value.isHashValueExisting(arg1Value, isHashValueExistingNode__isHashValueExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashValueExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isHashValueExistingNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached isHashValueExistingNode__isHashValueExisting_library__ = null;
                    isHashValueExistingNode__isHashValueExisting_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x10000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isHashValueExisting(arg1Value, isHashValueExistingNode__isHashValueExisting_library__, this.cache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10000000) != 0) {
                    Cached isHashValueExistingNode__isHashValueExisting_library__ = this;
                    return arg0Value.isHashValueExisting(arg1Value, isHashValueExistingNode__isHashValueExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashValueExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10000000) != 0) {
                    Cached isHashValueExistingNode__isHashValueExisting_library__ = this;
                    return arg0Value.isHashValueExisting(arg1Value, isHashValueExistingNode__isHashValueExisting_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashValueExistingNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20000000) != 0) {
                    Cached readHashValueNode__readHashValue_library__ = this;
                    return arg0Value.readHashValue(arg1Value, readHashValueNode__readHashValue_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readHashValueNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readHashValueNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached readHashValueNode__readHashValue_library__ = null;
                    readHashValueNode__readHashValue_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x20000000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readHashValue(arg1Value, readHashValueNode__readHashValue_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40000000) != 0) {
                    Cached isHashEntryInsertableNode__isHashEntryInsertable_library__ = this;
                    return arg0Value.isHashEntryInsertable(arg1Value, isHashEntryInsertableNode__isHashEntryInsertable_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isHashEntryInsertableNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached isHashEntryInsertableNode__isHashEntryInsertable_library__ = null;
                    isHashEntryInsertableNode__isHashEntryInsertable_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= 0x40000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isHashEntryInsertable(arg1Value, isHashEntryInsertableNode__isHashEntryInsertable_library__, this.cache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & Integer.MIN_VALUE) != 0) {
                    Cached writeHashEntryNode__writeHashEntry_library__ = this;
                    arg0Value.writeHashEntry(arg1Value, arg2Value, writeHashEntryNode__writeHashEntry_library__, this.cache);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeHashEntryNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeHashEntryNode_AndSpecialize(HostProxy arg0Value, Object arg1Value, Object arg2Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached writeHashEntryNode__writeHashEntry_library__ = null;
                    writeHashEntryNode__writeHashEntry_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_0_ = state_0 |= Integer.MIN_VALUE;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeHashEntry(arg1Value, arg2Value, writeHashEntryNode__writeHashEntry_library__, this.cache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 1) != 0) {
                    Cached removeHashEntryNode__removeHashEntry_library__ = this;
                    arg0Value.removeHashEntry(arg1Value, removeHashEntryNode__removeHashEntry_library__, this.cache);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeHashEntryNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void removeHashEntryNode_AndSpecialize(HostProxy arg0Value, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    Cached removeHashEntryNode__removeHashEntry_library__ = null;
                    removeHashEntryNode__removeHashEntry_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_1_ = state_1 |= 1;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.removeHashEntry(arg1Value, removeHashEntryNode__removeHashEntry_library__, this.cache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getHashEntriesIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostProxy arg0Value = (HostProxy)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 2) != 0) {
                    Cached getHashEntriesIteratorNode__getHashEntriesIterator_library__ = this;
                    return arg0Value.getHashEntriesIterator(getHashEntriesIteratorNode__getHashEntriesIterator_library__, this.cache);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getHashEntriesIteratorNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getHashEntriesIteratorNode_AndSpecialize(HostProxy arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    Cached getHashEntriesIteratorNode__getHashEntriesIterator_library__ = null;
                    getHashEntriesIteratorNode__getHashEntriesIterator_library__ = this;
                    this.cache = this.cache == null ? arg0Value.context.getGuestToHostCache() : this.cache;
                    this.state_1_ = state_1 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getHashEntriesIterator(getHashEntriesIteratorNode__getHashEntriesIterator_library__, this.cache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return HostProxy.identityHashCode((HostProxy)receiver);
            }

            private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_0, HostProxy arg0Value, Object arg1Value) {
                return (state_0 & 1) != 0 || !(arg1Value instanceof HostProxy);
            }
        }
    }
}

