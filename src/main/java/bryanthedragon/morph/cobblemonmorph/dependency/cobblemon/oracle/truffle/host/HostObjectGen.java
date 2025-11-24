/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostContextFactory;
import com.oracle.truffle.host.HostExecuteNode;
import com.oracle.truffle.host.HostExecuteNodeGen;
import com.oracle.truffle.host.HostObject;
import com.oracle.truffle.host.HostObjectFactory;
import com.oracle.truffle.host.HostToTypeNode;
import com.oracle.truffle.host.HostToTypeNodeGen;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HostObject.class)
final class HostObjectGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private HostObjectGen() {
    }

    static {
        LibraryExport.register(HostObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=HostObject.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, HostObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof HostObject);
            Uncached uncached = new Uncached();
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof HostObject);
            return new Cached();
        }

        @GeneratedBy(value=HostObject.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HostObject;
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
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInternal(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementReadable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementReadable.doArray(arg0Value, arg1Value, HostObjectFactory.IsArrayNodeGen.getUncached());
                }
                if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementReadable.doList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementReadable.doMapEntry(arg0Value, arg1Value, HostObjectFactory.IsMapEntryNodeGen.getUncached());
                }
                if (!(HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value))) {
                    return HostObject.IsArrayElementReadable.doNotArrayOrList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsMapEntryNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementModifiable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementModifiable.doArray(arg0Value, arg1Value, HostObjectFactory.IsArrayNodeGen.getUncached());
                }
                if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementModifiable.doList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementModifiable.doMapEntry(arg0Value, arg1Value, HostObjectFactory.IsMapEntryNodeGen.getUncached());
                }
                if (!(HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value))) {
                    return HostObject.IsArrayElementModifiable.doNotArrayOrList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsMapEntryNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeArrayElement(Object arg0Value_, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedTypeException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.WriteArrayElement.doArray(arg0Value, arg1Value, arg2Value, HostToTypeNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.ArraySetNodeGen.getUncached(), BranchProfile.getUncached());
                    return;
                }
                if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.WriteArrayElement.doList(arg0Value, arg1Value, arg2Value, HostObjectFactory.IsListNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached());
                    return;
                }
                if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.WriteArrayElement.doMapEntry(arg0Value, arg1Value, arg2Value, HostObjectFactory.IsMapEntryNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached());
                    return;
                }
                if (!(HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value))) {
                    HostObject.WriteArrayElement.doNotArrayOrList(arg0Value, arg1Value, arg2Value, HostObjectFactory.IsListNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsMapEntryNodeGen.getUncached());
                    return;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementRemovable.doList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.IsArrayElementRemovable.doOther(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeArrayElement(Object arg0Value_, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.RemoveArrayElement.doList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
                    return;
                }
                if (!HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.RemoveArrayElement.doOther(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached());
                    return;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readArrayElement(Object arg0Value_, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.ReadArrayElement.doArray(arg0Value, arg1Value, HostObjectFactory.ArrayGetNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.ReadArrayElement.doList(arg0Value, arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.ReadArrayElement.doMapEntry(arg0Value, arg1Value, HostObjectFactory.IsMapEntryNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!(HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value))) {
                    return HostObject.ReadArrayElement.doNotArrayOrList(arg0Value, arg1Value, HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsListNodeGen.getUncached(), HostObjectFactory.IsMapEntryNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getArraySize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetArraySize.doArray(arg0Value, HostObjectFactory.IsArrayNodeGen.getUncached());
                }
                if (HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetArraySize.doList(arg0Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetArraySize.doMapEntry(arg0Value, HostObjectFactory.IsMapEntryNodeGen.getUncached());
                }
                if (!(HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsListNodeGen.getUncached().execute(arg0Value) || HostObjectFactory.IsMapEntryNodeGen.getUncached().execute(arg0Value))) {
                    return HostObject.GetArraySize.doNotArrayOrList(arg0Value, HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsListNodeGen.getUncached(), HostObjectFactory.IsMapEntryNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isInstantiable(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (!arg0Value.isClass()) {
                    return HostObject.IsInstantiable.doUnsupported(arg0Value);
                }
                if (arg0Value.isArrayClass()) {
                    return HostObject.IsInstantiable.doArrayCached(arg0Value);
                }
                if (arg0Value.isDefaultClass()) {
                    return HostObject.IsInstantiable.doObjectCached(arg0Value, HostObjectFactory.LookupConstructorNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object instantiate(Object arg0Value_, Object ... arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (!arg0Value.isClass()) {
                    return HostObject.Instantiate.doUnsupported(arg0Value, arg1Value);
                }
                if (arg0Value.isArrayClass()) {
                    return HostObject.Instantiate.doArrayCached(arg0Value, arg1Value, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
                }
                if (arg0Value.isDefaultClass()) {
                    return HostObject.Instantiate.doObjectCached(arg0Value, arg1Value, HostObjectFactory.LookupConstructorNodeGen.getUncached(), HostExecuteNodeGen.getUncached(), BranchProfile.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetIterator.doArray(arg0Value, HostObjectFactory.IsArrayNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached());
                }
                if (HostObjectFactory.IsIterableNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetIterator.doIterable(arg0Value, HostObjectFactory.IsIterableNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!HostObjectFactory.IsArrayNodeGen.getUncached().execute(arg0Value) && !HostObjectFactory.IsIterableNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetIterator.doNotArrayOrIterable(arg0Value, HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsIterableNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.HasIteratorNextElement.doIterator(arg0Value, HostObjectFactory.IsIteratorNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.HasIteratorNextElement.doNotIterator(arg0Value, HostObjectFactory.IsIteratorNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getIteratorNextElement(Object arg0Value_) throws StopIterationException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetIteratorNextElement.doIterator(arg0Value, HostObjectFactory.IsIteratorNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached(), BranchProfile.getUncached());
                }
                if (!HostObjectFactory.IsIteratorNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetIteratorNextElement.doNotIterator(arg0Value, HostObjectFactory.IsIteratorNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetHashSize.doMap(arg0Value, HostObjectFactory.IsMapNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetHashSize.doNotMap(arg0Value, HostObjectFactory.IsMapNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.ReadHashValue.doMap(arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.ReadHashValue.doNotMap(arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedTypeException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.WriteHashEntry.doMap(arg0Value, arg1Value, arg2Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached());
                    return;
                }
                if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.WriteHashEntry.doNotMap(arg0Value, arg1Value, arg2Value, HostObjectFactory.IsMapNodeGen.getUncached());
                    return;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.RemoveHashEntry.doMap(arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached());
                    return;
                }
                if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    HostObject.RemoveHashEntry.doNotMap(arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached());
                    return;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getHashEntriesIterator(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetHashEntriesIterator.doMap(arg0Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostContextFactory.ToGuestValueNodeGen.getUncached(), BranchProfile.getUncached());
                }
                if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
                    return HostObject.GetHashEntriesIterator.doNotMap(arg0Value, HostObjectFactory.IsMapNodeGen.getUncached());
                }
                throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                if (arg1Value instanceof HostObject) {
                    HostObject arg1Value_ = (HostObject)arg1Value;
                    return HostObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                }
                return HostObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getMembers(includeInternal);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.readMember(arg1Value, HostObjectFactory.LookupFieldNodeGen.getUncached(), HostObjectFactory.ReadFieldNodeGen.getUncached(), HostObjectFactory.LookupMethodNodeGen.getUncached(), HostObjectFactory.LookupInnerClassNodeGen.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isMemberInsertable(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                arg0Value.writeMember(arg1Value, arg2Value, HostObjectFactory.LookupFieldNodeGen.getUncached(), HostObjectFactory.WriteFieldNodeGen.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, HostObjectFactory.LookupMethodNodeGen.getUncached(), HostExecuteNodeGen.getUncached(), HostObjectFactory.LookupFieldNodeGen.getUncached(), HostObjectFactory.ReadFieldNodeGen.getUncached(), INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isArrayElementInsertable(arg1Value, HostObjectFactory.IsListNodeGen.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasArrayElements(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.hasArrayElements(HostObjectFactory.IsListNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached(), HostObjectFactory.IsMapEntryNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasBufferElements(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.hasBufferElements(HostObjectFactory.IsBufferNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isBufferWritable(HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getBufferSize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.getBufferSize(HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.readBufferByte(arg1Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                arg0Value.writeBufferByte(arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.readBufferShort(arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.readBufferInt(arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.readBufferLong(arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.readBufferFloat(arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.readBufferDouble(arg1Value, arg2Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, HostObjectFactory.IsBufferNodeGen.getUncached(), BranchProfile.getUncached(), ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isNull(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isNull();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExecutable(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isExecutable(HostObjectFactory.LookupFunctionalMethodNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.execute(arg1Value, HostExecuteNodeGen.getUncached(), HostObjectFactory.LookupFunctionalMethodNodeGen.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isNumber(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isNumber(ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInByte(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.fitsInByte(this, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInShort(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.fitsInShort(this, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInInt(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.fitsInInt(this, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInLong(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.fitsInLong(this, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInFloat(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.fitsInFloat(this, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean fitsInDouble(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.fitsInDouble(this, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte asByte(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asByte(this, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short asShort(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asShort(this, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int asInt(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asInt(this, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long asLong(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asLong(this, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float asFloat(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asFloat(this, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double asDouble(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asDouble(this, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isString(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isString(ValueProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public String asString(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asString(this, INTEROP_LIBRARY_.getUncached(), BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isBoolean();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean asBoolean(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.asBoolean(BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDate(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).asDate();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isTime();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).asTime();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isTimeZone();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).asTimeZone();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Instant asInstant(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).asInstant();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isDuration();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Duration asDuration(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).asDuration();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isException(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isException();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.getExceptionType(BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isExceptionIncompleteSource(BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int getExceptionExitStatus(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.getExceptionExitStatus(BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExceptionMessage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).hasExceptionMessage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.getExceptionMessage(BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExceptionCause(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).hasExceptionCause();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getExceptionCause();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasExceptionStackTrace(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).hasExceptionStackTrace();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getExceptionStackTrace();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public RuntimeException throwException(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.throwException(BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasLanguage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).hasLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getLanguage();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasIterator(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.hasIterator(HostObjectFactory.IsIterableNodeGen.getUncached(), HostObjectFactory.IsArrayNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isIterator(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isIterator(HostObjectFactory.IsIteratorNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasHashEntries(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.hasHashEntries(HostObjectFactory.IsMapNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isHashEntryReadable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isHashEntryReadable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isHashEntryReadable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isHashEntryInsertable(arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostObjectFactory.ContainsKeyNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).hasMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).isMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getMetaQualifiedName();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getMetaSimpleName();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMetaInstance(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostObject arg0Value = (HostObject)arg0Value_;
                return arg0Value.isMetaInstance(arg1Value, this, BranchProfile.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaParents(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).hasMetaParents();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostObject)receiver).getMetaParents();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return HostObject.identityHashCode((HostObject)receiver);
            }
        }

        @GeneratedBy(value=HostObject.class)
        private static final class Cached
        extends InteropLibrary {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_1_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_2_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_3_;
            @CompilerDirectives.CompilationFinal
            private volatile int exclude_;
            @Node.Child
            private HostObject.IsArrayNode isArray;
            @Node.Child
            private HostObject.IsListNode isList;
            @CompilerDirectives.CompilationFinal
            private BranchProfile error;
            @Node.Child
            private HostObject.IsMapEntryNode isMapEntry;
            @Node.Child
            private HostToTypeNode toHost;
            @Node.Child
            private HostContext.ToGuestValueNode toGuest;
            @Node.Child
            private HostObject.LookupConstructorNode lookupConstructor;
            @Node.Child
            private HostExecuteNode hostExecute;
            @Node.Child
            private HostObject.IsIterableNode isIterable;
            @Node.Child
            private HostObject.IsIteratorNode isIterator;
            @Node.Child
            private HostObject.IsMapNode isMap;
            @Node.Child
            private HostObject.LookupFieldNode lookupField;
            @Node.Child
            private HostObject.ReadFieldNode readField;
            @Node.Child
            private HostObject.LookupMethodNode lookupMethod;
            @Node.Child
            private HostObject.IsBufferNode isBuffer;
            @CompilerDirectives.CompilationFinal
            private ValueProfile classProfile;
            @Node.Child
            private HostObject.LookupFunctionalMethodNode lookupFunctionalMethod;
            @Node.Child
            private InteropLibrary numbers;
            @Node.Child
            private HostObject.ContainsKeyNode containsKey;
            @CompilerDirectives.CompilationFinal
            private IsMemberReadableCachedData isMemberReadable_cached_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberModifiableCachedData isMemberModifiable_cached_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberInternalCachedData isMemberInternal_cached_cache;
            @CompilerDirectives.CompilationFinal
            private IsMemberInvocableCachedData isMemberInvocable_cached_cache;
            @Node.Child
            private HostObject.ArraySet writeArrayElement_array_arraySet_;
            @Node.Child
            private HostObject.ArrayGet readArrayElement_array_arrayGet_;
            @Node.Child
            private InteropLibrary instantiate_arrayCached_indexes_;
            @CompilerDirectives.CompilationFinal
            private BranchProfile getIteratorNextElement_iterator_stopIteration_;
            @Node.Child
            private HostObject.LookupInnerClassNode readMemberNode__readMember_lookupInnerClass_;
            @Node.Child
            private HostObject.WriteFieldNode writeMemberNode__writeMember_writeField_;
            @Node.Child
            private InteropLibrary invokeMemberNode__invokeMember_fieldValues_;

            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostObject) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HostObject;
            }

            @Override
            @ExplodeLoop
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 3) != 0) {
                    if ((state_0 & 1) != 0 && arg0Value.isStaticClass()) {
                        IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
                        while (s0_ != null) {
                            if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                                return HostObject.IsMemberReadable.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedReadable_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberReadableAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberReadableAndSpecialize(HostObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 1) == 0 && arg0Value.isStaticClass()) {
                        int count0_ = 0;
                        IsMemberReadableCachedData s0_ = this.isMemberReadable_cached_cache;
                        if ((state_0 & 1) != 0) {
                            while (!(s0_ == null || arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value))) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null) {
                            boolean cachedStatic__ = arg0Value.isStaticClass();
                            if (arg0Value.isStaticClass() == cachedStatic__) {
                                Class<?> cachedClazz__ = arg0Value.getLookupClass();
                                if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                                    s0_ = new IsMemberReadableCachedData(this.isMemberReadable_cached_cache);
                                    s0_.cachedStatic_ = cachedStatic__;
                                    s0_.cachedClazz_ = cachedClazz__;
                                    s0_.cachedName_ = arg1Value;
                                    s0_.cachedReadable_ = HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
                                    VarHandle.storeStoreFence();
                                    this.isMemberReadable_cached_cache = s0_;
                                    this.state_0_ = state_0 |= 1;
                                }
                            }
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = HostObject.IsMemberReadable.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedReadable_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 1;
                    this.isMemberReadable_cached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = HostObject.IsMemberReadable.doUncached(arg0Value, arg1Value);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                IsMemberReadableCachedData s0_;
                int state_0 = this.state_0_;
                if ((state_0 & 3) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                if ((state_0 & 3 & (state_0 & 3) - 1) == 0 && ((s0_ = this.isMemberReadable_cached_cache) == null || s0_.next_ == null)) {
                    return NodeCost.MONOMORPHIC;
                }
                return NodeCost.POLYMORPHIC;
            }

            @Override
            @ExplodeLoop
            public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xC) != 0) {
                    if ((state_0 & 4) != 0 && arg0Value.isStaticClass()) {
                        IsMemberModifiableCachedData s0_ = this.isMemberModifiable_cached_cache;
                        while (s0_ != null) {
                            if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                                return HostObject.IsMemberModifiable.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedModifiable_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 8) != 0) {
                        return HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberModifiableAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberModifiableAndSpecialize(HostObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 2) == 0 && arg0Value.isStaticClass()) {
                        int count0_ = 0;
                        IsMemberModifiableCachedData s0_ = this.isMemberModifiable_cached_cache;
                        if ((state_0 & 4) != 0) {
                            while (!(s0_ == null || arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value))) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null) {
                            boolean cachedStatic__ = arg0Value.isStaticClass();
                            if (arg0Value.isStaticClass() == cachedStatic__) {
                                Class<?> cachedClazz__ = arg0Value.getLookupClass();
                                if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                                    s0_ = new IsMemberModifiableCachedData(this.isMemberModifiable_cached_cache);
                                    s0_.cachedStatic_ = cachedStatic__;
                                    s0_.cachedClazz_ = cachedClazz__;
                                    s0_.cachedName_ = arg1Value;
                                    s0_.cachedModifiable_ = HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
                                    VarHandle.storeStoreFence();
                                    this.isMemberModifiable_cached_cache = s0_;
                                    this.state_0_ = state_0 |= 4;
                                }
                            }
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = HostObject.IsMemberModifiable.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedModifiable_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 2;
                    this.isMemberModifiable_cached_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = HostObject.IsMemberModifiable.doUncached(arg0Value, arg1Value);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            @ExplodeLoop
            public boolean isMemberInternal(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x30) != 0) {
                    if ((state_0 & 0x10) != 0 && arg0Value.isStaticClass()) {
                        IsMemberInternalCachedData s0_ = this.isMemberInternal_cached_cache;
                        while (s0_ != null) {
                            if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                                return HostObject.IsMemberInternal.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInternal_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 0x20) != 0) {
                        return HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInternalAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInternalAndSpecialize(HostObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 4) == 0 && arg0Value.isStaticClass()) {
                        int count0_ = 0;
                        IsMemberInternalCachedData s0_ = this.isMemberInternal_cached_cache;
                        if ((state_0 & 0x10) != 0) {
                            while (!(s0_ == null || arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value))) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null) {
                            boolean cachedStatic__ = arg0Value.isStaticClass();
                            if (arg0Value.isStaticClass() == cachedStatic__) {
                                Class<?> cachedClazz__ = arg0Value.getLookupClass();
                                if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                                    s0_ = new IsMemberInternalCachedData(this.isMemberInternal_cached_cache);
                                    s0_.cachedStatic_ = cachedStatic__;
                                    s0_.cachedClazz_ = cachedClazz__;
                                    s0_.cachedName_ = arg1Value;
                                    s0_.cachedInternal_ = HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
                                    VarHandle.storeStoreFence();
                                    this.isMemberInternal_cached_cache = s0_;
                                    this.state_0_ = state_0 |= 0x10;
                                }
                            }
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = HostObject.IsMemberInternal.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInternal_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 4;
                    this.isMemberInternal_cached_cache = null;
                    state_0 &= 0xFFFFFFEF;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = HostObject.IsMemberInternal.doUncached(arg0Value, arg1Value);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            @ExplodeLoop
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xC0) != 0) {
                    if ((state_0 & 0x40) != 0 && arg0Value.isStaticClass()) {
                        IsMemberInvocableCachedData s0_ = this.isMemberInvocable_cached_cache;
                        while (s0_ != null) {
                            if (arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                                return HostObject.IsMemberInvocable.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInvokable_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 0x80) != 0) {
                        return HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInvocableAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInvocableAndSpecialize(HostObject arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    int exclude = this.exclude_;
                    if ((exclude & 8) == 0 && arg0Value.isStaticClass()) {
                        int count0_ = 0;
                        IsMemberInvocableCachedData s0_ = this.isMemberInvocable_cached_cache;
                        if ((state_0 & 0x40) != 0) {
                            while (!(s0_ == null || arg0Value.isStaticClass() == s0_.cachedStatic_ && arg0Value.getLookupClass() == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value))) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null) {
                            boolean cachedStatic__ = arg0Value.isStaticClass();
                            if (arg0Value.isStaticClass() == cachedStatic__) {
                                Class<?> cachedClazz__ = arg0Value.getLookupClass();
                                if (arg0Value.getLookupClass() == cachedClazz__ && count0_ < 5) {
                                    s0_ = new IsMemberInvocableCachedData(this.isMemberInvocable_cached_cache);
                                    s0_.cachedStatic_ = cachedStatic__;
                                    s0_.cachedClazz_ = cachedClazz__;
                                    s0_.cachedName_ = arg1Value;
                                    s0_.cachedInvokable_ = HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
                                    VarHandle.storeStoreFence();
                                    this.isMemberInvocable_cached_cache = s0_;
                                    this.state_0_ = state_0 |= 0x40;
                                }
                            }
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            boolean bl = HostObject.IsMemberInvocable.doCached(arg0Value, arg1Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInvokable_);
                            return bl;
                        }
                    }
                    this.exclude_ = exclude |= 8;
                    this.isMemberInvocable_cached_cache = null;
                    state_0 &= 0xFFFFFFBF;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = HostObject.IsMemberInvocable.doUncached(arg0Value, arg1Value);
                    return bl;
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xF00) != 0) {
                    if ((state_0 & 0x100) != 0 && this.isArray.execute(arg0Value)) {
                        return HostObject.IsArrayElementReadable.doArray(arg0Value, arg1Value, this.isArray);
                    }
                    if ((state_0 & 0x200) != 0 && this.isList.execute(arg0Value)) {
                        return HostObject.IsArrayElementReadable.doList(arg0Value, arg1Value, this.isList, this.error);
                    }
                    if ((state_0 & 0x400) != 0 && this.isMapEntry.execute(arg0Value)) {
                        return HostObject.IsArrayElementReadable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
                    }
                    if (!((state_0 & 0x800) == 0 || this.isList.execute(arg0Value) || this.isArray.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        return HostObject.IsArrayElementReadable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementReadableAndSpecialize(arg0Value, arg1Value);
            }

            private boolean isArrayElementReadableAndSpecialize(HostObject arg0Value, long arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapEntryNode isArrayElementReadable_notArrayOrList_isMapEntry__;
                    HostObject.IsArrayNode isArrayElementReadable_notArrayOrList_isArray__;
                    HostObject.IsListNode isArrayElementReadable_notArrayOrList_isList__;
                    HostObject.IsMapEntryNode isArrayElementReadable_mapEntry_isMapEntry__2;
                    HostObject.IsListNode isArrayElementReadable_list_isList__2;
                    HostObject.IsArrayNode isArrayElementReadable_array_isArray__2;
                    int state_0 = this.state_0_;
                    boolean Array_duplicateFound_ = false;
                    if ((state_0 & 0x100) != 0 && this.isArray.execute(arg0Value)) {
                        Array_duplicateFound_ = true;
                    }
                    if (!Array_duplicateFound_ && (isArrayElementReadable_array_isArray__2 = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) && (state_0 & 0x100) == 0) {
                        if (this.isArray == null) {
                            HostObject.IsArrayNode isArrayElementReadable_array_isArray___check = super.insert(isArrayElementReadable_array_isArray__2);
                            if (isArrayElementReadable_array_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doArray(HostObject, long, IsArrayNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = isArrayElementReadable_array_isArray___check;
                        }
                        this.state_0_ = state_0 |= 0x100;
                        Array_duplicateFound_ = true;
                    }
                    if (Array_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean isArrayElementReadable_array_isArray__2 = HostObject.IsArrayElementReadable.doArray(arg0Value, arg1Value, this.isArray);
                        return isArrayElementReadable_array_isArray__2;
                    }
                    boolean List_duplicateFound_ = false;
                    if ((state_0 & 0x200) != 0 && this.isList.execute(arg0Value)) {
                        List_duplicateFound_ = true;
                    }
                    if (!List_duplicateFound_ && (isArrayElementReadable_list_isList__2 = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x200) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode isArrayElementReadable_list_isList___check = super.insert(isArrayElementReadable_list_isList__2);
                            if (isArrayElementReadable_list_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = isArrayElementReadable_list_isList___check;
                        }
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x200;
                        List_duplicateFound_ = true;
                    }
                    if (List_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean isArrayElementReadable_list_isList__2 = HostObject.IsArrayElementReadable.doList(arg0Value, arg1Value, this.isList, this.error);
                        return isArrayElementReadable_list_isList__2;
                    }
                    boolean MapEntry_duplicateFound_ = false;
                    if ((state_0 & 0x400) != 0 && this.isMapEntry.execute(arg0Value)) {
                        MapEntry_duplicateFound_ = true;
                    }
                    if (!MapEntry_duplicateFound_ && (isArrayElementReadable_mapEntry_isMapEntry__2 = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) && (state_0 & 0x400) == 0) {
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode isArrayElementReadable_mapEntry_isMapEntry___check = super.insert(isArrayElementReadable_mapEntry_isMapEntry__2);
                            if (isArrayElementReadable_mapEntry_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMapEntry(HostObject, long, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = isArrayElementReadable_mapEntry_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= 0x400;
                        MapEntry_duplicateFound_ = true;
                    }
                    if (MapEntry_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean isArrayElementReadable_mapEntry_isMapEntry__2 = HostObject.IsArrayElementReadable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
                        return isArrayElementReadable_mapEntry_isMapEntry__2;
                    }
                    boolean NotArrayOrList_duplicateFound_ = false;
                    if (!((state_0 & 0x800) == 0 || this.isList.execute(arg0Value) || this.isArray.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (!(NotArrayOrList_duplicateFound_ || (isArrayElementReadable_notArrayOrList_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) || (isArrayElementReadable_notArrayOrList_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) || (isArrayElementReadable_notArrayOrList_isMapEntry__ = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) || (state_0 & 0x800) != 0)) {
                        if (this.isList == null) {
                            HostObject.IsListNode isArrayElementReadable_notArrayOrList_isList___check = super.insert(isArrayElementReadable_notArrayOrList_isList__);
                            if (isArrayElementReadable_notArrayOrList_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = isArrayElementReadable_notArrayOrList_isList___check;
                        }
                        if (this.isArray == null) {
                            HostObject.IsArrayNode isArrayElementReadable_notArrayOrList_isArray___check = super.insert(isArrayElementReadable_notArrayOrList_isArray__);
                            if (isArrayElementReadable_notArrayOrList_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = isArrayElementReadable_notArrayOrList_isArray___check;
                        }
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode isArrayElementReadable_notArrayOrList_isMapEntry___check = super.insert(isArrayElementReadable_notArrayOrList_isMapEntry__);
                            if (isArrayElementReadable_notArrayOrList_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = isArrayElementReadable_notArrayOrList_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= 0x800;
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (NotArrayOrList_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HostObject.IsArrayElementReadable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
                        return bl;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xF000) != 0) {
                    if ((state_0 & 0x1000) != 0 && this.isArray.execute(arg0Value)) {
                        return HostObject.IsArrayElementModifiable.doArray(arg0Value, arg1Value, this.isArray);
                    }
                    if ((state_0 & 0x2000) != 0 && this.isList.execute(arg0Value)) {
                        return HostObject.IsArrayElementModifiable.doList(arg0Value, arg1Value, this.isList, this.error);
                    }
                    if ((state_0 & 0x4000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        return HostObject.IsArrayElementModifiable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
                    }
                    if (!((state_0 & 0x8000) == 0 || this.isList.execute(arg0Value) || this.isArray.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        return HostObject.IsArrayElementModifiable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementModifiableAndSpecialize(arg0Value, arg1Value);
            }

            private boolean isArrayElementModifiableAndSpecialize(HostObject arg0Value, long arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapEntryNode isArrayElementModifiable_notArrayOrList_isMapEntry__;
                    HostObject.IsArrayNode isArrayElementModifiable_notArrayOrList_isArray__;
                    HostObject.IsListNode isArrayElementModifiable_notArrayOrList_isList__;
                    HostObject.IsMapEntryNode isArrayElementModifiable_mapEntry_isMapEntry__2;
                    HostObject.IsListNode isArrayElementModifiable_list_isList__2;
                    HostObject.IsArrayNode isArrayElementModifiable_array_isArray__2;
                    int state_0 = this.state_0_;
                    boolean Array_duplicateFound_ = false;
                    if ((state_0 & 0x1000) != 0 && this.isArray.execute(arg0Value)) {
                        Array_duplicateFound_ = true;
                    }
                    if (!Array_duplicateFound_ && (isArrayElementModifiable_array_isArray__2 = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) && (state_0 & 0x1000) == 0) {
                        if (this.isArray == null) {
                            HostObject.IsArrayNode isArrayElementModifiable_array_isArray___check = super.insert(isArrayElementModifiable_array_isArray__2);
                            if (isArrayElementModifiable_array_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doArray(HostObject, long, IsArrayNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = isArrayElementModifiable_array_isArray___check;
                        }
                        this.state_0_ = state_0 |= 0x1000;
                        Array_duplicateFound_ = true;
                    }
                    if (Array_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean isArrayElementModifiable_array_isArray__2 = HostObject.IsArrayElementModifiable.doArray(arg0Value, arg1Value, this.isArray);
                        return isArrayElementModifiable_array_isArray__2;
                    }
                    boolean List_duplicateFound_ = false;
                    if ((state_0 & 0x2000) != 0 && this.isList.execute(arg0Value)) {
                        List_duplicateFound_ = true;
                    }
                    if (!List_duplicateFound_ && (isArrayElementModifiable_list_isList__2 = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x2000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode isArrayElementModifiable_list_isList___check = super.insert(isArrayElementModifiable_list_isList__2);
                            if (isArrayElementModifiable_list_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = isArrayElementModifiable_list_isList___check;
                        }
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x2000;
                        List_duplicateFound_ = true;
                    }
                    if (List_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean isArrayElementModifiable_list_isList__2 = HostObject.IsArrayElementModifiable.doList(arg0Value, arg1Value, this.isList, this.error);
                        return isArrayElementModifiable_list_isList__2;
                    }
                    boolean MapEntry_duplicateFound_ = false;
                    if ((state_0 & 0x4000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        MapEntry_duplicateFound_ = true;
                    }
                    if (!MapEntry_duplicateFound_ && (isArrayElementModifiable_mapEntry_isMapEntry__2 = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) && (state_0 & 0x4000) == 0) {
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode isArrayElementModifiable_mapEntry_isMapEntry___check = super.insert(isArrayElementModifiable_mapEntry_isMapEntry__2);
                            if (isArrayElementModifiable_mapEntry_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMapEntry(HostObject, long, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = isArrayElementModifiable_mapEntry_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= 0x4000;
                        MapEntry_duplicateFound_ = true;
                    }
                    if (MapEntry_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean isArrayElementModifiable_mapEntry_isMapEntry__2 = HostObject.IsArrayElementModifiable.doMapEntry(arg0Value, arg1Value, this.isMapEntry);
                        return isArrayElementModifiable_mapEntry_isMapEntry__2;
                    }
                    boolean NotArrayOrList_duplicateFound_ = false;
                    if (!((state_0 & 0x8000) == 0 || this.isList.execute(arg0Value) || this.isArray.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (!(NotArrayOrList_duplicateFound_ || (isArrayElementModifiable_notArrayOrList_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) || (isArrayElementModifiable_notArrayOrList_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) || (isArrayElementModifiable_notArrayOrList_isMapEntry__ = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) || (state_0 & 0x8000) != 0)) {
                        if (this.isList == null) {
                            HostObject.IsListNode isArrayElementModifiable_notArrayOrList_isList___check = super.insert(isArrayElementModifiable_notArrayOrList_isList__);
                            if (isArrayElementModifiable_notArrayOrList_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = isArrayElementModifiable_notArrayOrList_isList___check;
                        }
                        if (this.isArray == null) {
                            HostObject.IsArrayNode isArrayElementModifiable_notArrayOrList_isArray___check = super.insert(isArrayElementModifiable_notArrayOrList_isArray__);
                            if (isArrayElementModifiable_notArrayOrList_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = isArrayElementModifiable_notArrayOrList_isArray___check;
                        }
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode isArrayElementModifiable_notArrayOrList_isMapEntry___check = super.insert(isArrayElementModifiable_notArrayOrList_isMapEntry__);
                            if (isArrayElementModifiable_notArrayOrList_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = isArrayElementModifiable_notArrayOrList_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= 0x8000;
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (NotArrayOrList_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HostObject.IsArrayElementModifiable.doNotArrayOrList(arg0Value, arg1Value, this.isList, this.isArray, this.isMapEntry);
                        return bl;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xF0000) != 0) {
                    if ((state_0 & 0x10000) != 0 && this.isArray.execute(arg0Value)) {
                        HostObject.WriteArrayElement.doArray(arg0Value, arg1Value, arg2Value, this.toHost, this.isArray, this.writeArrayElement_array_arraySet_, this.error);
                        return;
                    }
                    if ((state_0 & 0x20000) != 0 && this.isList.execute(arg0Value)) {
                        HostObject.WriteArrayElement.doList(arg0Value, arg1Value, arg2Value, this.isList, this.toHost, this.error);
                        return;
                    }
                    if ((state_0 & 0x40000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        HostObject.WriteArrayElement.doMapEntry(arg0Value, arg1Value, arg2Value, this.isMapEntry, this.toHost, this.error);
                        return;
                    }
                    if (!((state_0 & 0x80000) == 0 || this.isList.execute(arg0Value) || this.isArray.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        HostObject.WriteArrayElement.doNotArrayOrList(arg0Value, arg1Value, arg2Value, this.isList, this.isArray, this.isMapEntry);
                        return;
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeArrayElementAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private void writeArrayElementAndSpecialize(HostObject arg0Value, long arg1Value, Object arg2Value) throws InvalidArrayIndexException, UnsupportedTypeException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapEntryNode writeArrayElement_notArrayOrList_isMapEntry__;
                    HostObject.IsArrayNode writeArrayElement_notArrayOrList_isArray__;
                    HostObject.IsListNode writeArrayElement_notArrayOrList_isList__;
                    HostObject.IsMapEntryNode writeArrayElement_mapEntry_isMapEntry__;
                    HostObject.IsListNode writeArrayElement_list_isList__;
                    HostObject.IsArrayNode writeArrayElement_array_isArray__;
                    int state_0 = this.state_0_;
                    boolean Array_duplicateFound_ = false;
                    if ((state_0 & 0x10000) != 0 && this.isArray.execute(arg0Value)) {
                        Array_duplicateFound_ = true;
                    }
                    if (!Array_duplicateFound_ && (writeArrayElement_array_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) && (state_0 & 0x10000) == 0) {
                        this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                        if (this.isArray == null) {
                            HostObject.IsArrayNode writeArrayElement_array_isArray___check = super.insert(writeArrayElement_array_isArray__);
                            if (writeArrayElement_array_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doArray(HostObject, long, Object, HostToTypeNode, IsArrayNode, ArraySet, BranchProfile)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = writeArrayElement_array_isArray___check;
                        }
                        this.writeArrayElement_array_arraySet_ = super.insert(HostObjectFactory.ArraySetNodeGen.create());
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x10000;
                        Array_duplicateFound_ = true;
                    }
                    if (Array_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.WriteArrayElement.doArray(arg0Value, arg1Value, arg2Value, this.toHost, this.isArray, this.writeArrayElement_array_arraySet_, this.error);
                        return;
                    }
                    boolean List_duplicateFound_ = false;
                    if ((state_0 & 0x20000) != 0 && this.isList.execute(arg0Value)) {
                        List_duplicateFound_ = true;
                    }
                    if (!List_duplicateFound_ && (writeArrayElement_list_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x20000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode writeArrayElement_list_isList___check = super.insert(writeArrayElement_list_isList__);
                            if (writeArrayElement_list_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doList(HostObject, long, Object, IsListNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = writeArrayElement_list_isList___check;
                        }
                        this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x20000;
                        List_duplicateFound_ = true;
                    }
                    if (List_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.WriteArrayElement.doList(arg0Value, arg1Value, arg2Value, this.isList, this.toHost, this.error);
                        return;
                    }
                    boolean MapEntry_duplicateFound_ = false;
                    if ((state_0 & 0x40000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        MapEntry_duplicateFound_ = true;
                    }
                    if (!MapEntry_duplicateFound_ && (writeArrayElement_mapEntry_isMapEntry__ = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) && (state_0 & 0x40000) == 0) {
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode writeArrayElement_mapEntry_isMapEntry___check = super.insert(writeArrayElement_mapEntry_isMapEntry__);
                            if (writeArrayElement_mapEntry_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMapEntry(HostObject, long, Object, IsMapEntryNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = writeArrayElement_mapEntry_isMapEntry___check;
                        }
                        this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x40000;
                        MapEntry_duplicateFound_ = true;
                    }
                    if (MapEntry_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.WriteArrayElement.doMapEntry(arg0Value, arg1Value, arg2Value, this.isMapEntry, this.toHost, this.error);
                        return;
                    }
                    boolean NotArrayOrList_duplicateFound_ = false;
                    if (!((state_0 & 0x80000) == 0 || this.isList.execute(arg0Value) || this.isArray.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (!(NotArrayOrList_duplicateFound_ || (writeArrayElement_notArrayOrList_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) || (writeArrayElement_notArrayOrList_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) || (writeArrayElement_notArrayOrList_isMapEntry__ = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) || (state_0 & 0x80000) != 0)) {
                        if (this.isList == null) {
                            HostObject.IsListNode writeArrayElement_notArrayOrList_isList___check = super.insert(writeArrayElement_notArrayOrList_isList__);
                            if (writeArrayElement_notArrayOrList_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, Object, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = writeArrayElement_notArrayOrList_isList___check;
                        }
                        if (this.isArray == null) {
                            HostObject.IsArrayNode writeArrayElement_notArrayOrList_isArray___check = super.insert(writeArrayElement_notArrayOrList_isArray__);
                            if (writeArrayElement_notArrayOrList_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, Object, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = writeArrayElement_notArrayOrList_isArray___check;
                        }
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode writeArrayElement_notArrayOrList_isMapEntry___check = super.insert(writeArrayElement_notArrayOrList_isMapEntry__);
                            if (writeArrayElement_notArrayOrList_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, Object, IsListNode, IsArrayNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = writeArrayElement_notArrayOrList_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= 0x80000;
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (NotArrayOrList_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.WriteArrayElement.doNotArrayOrList(arg0Value, arg1Value, arg2Value, this.isList, this.isArray, this.isMapEntry);
                        return;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isArrayElementRemovable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x300000) != 0) {
                    if ((state_0 & 0x100000) != 0 && this.isList.execute(arg0Value)) {
                        return HostObject.IsArrayElementRemovable.doList(arg0Value, arg1Value, this.isList, this.error);
                    }
                    if ((state_0 & 0x200000) != 0 && !this.isList.execute(arg0Value)) {
                        return HostObject.IsArrayElementRemovable.doOther(arg0Value, arg1Value, this.isList);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementRemovableAndSpecialize(arg0Value, arg1Value);
            }

            private boolean isArrayElementRemovableAndSpecialize(HostObject arg0Value, long arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsListNode isArrayElementRemovable_other_isList__;
                    HostObject.IsListNode isArrayElementRemovable_list_isList__2;
                    int state_0 = this.state_0_;
                    boolean List_duplicateFound_ = false;
                    if ((state_0 & 0x100000) != 0 && this.isList.execute(arg0Value)) {
                        List_duplicateFound_ = true;
                    }
                    if (!List_duplicateFound_ && (isArrayElementRemovable_list_isList__2 = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x100000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode isArrayElementRemovable_list_isList___check = super.insert(isArrayElementRemovable_list_isList__2);
                            if (isArrayElementRemovable_list_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = isArrayElementRemovable_list_isList___check;
                        }
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x100000;
                        List_duplicateFound_ = true;
                    }
                    if (List_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean isArrayElementRemovable_list_isList__2 = HostObject.IsArrayElementRemovable.doList(arg0Value, arg1Value, this.isList, this.error);
                        return isArrayElementRemovable_list_isList__2;
                    }
                    boolean Other_duplicateFound_ = false;
                    if ((state_0 & 0x200000) != 0 && !this.isList.execute(arg0Value)) {
                        Other_duplicateFound_ = true;
                    }
                    if (!Other_duplicateFound_ && !(isArrayElementRemovable_other_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x200000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode isArrayElementRemovable_other_isList___check = super.insert(isArrayElementRemovable_other_isList__);
                            if (isArrayElementRemovable_other_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doOther(HostObject, long, IsListNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = isArrayElementRemovable_other_isList___check;
                        }
                        this.state_0_ = state_0 |= 0x200000;
                        Other_duplicateFound_ = true;
                    }
                    if (Other_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HostObject.IsArrayElementRemovable.doOther(arg0Value, arg1Value, this.isList);
                        return bl;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xC00000) != 0) {
                    if ((state_0 & 0x400000) != 0 && this.isList.execute(arg0Value)) {
                        HostObject.RemoveArrayElement.doList(arg0Value, arg1Value, this.isList, this.error);
                        return;
                    }
                    if ((state_0 & 0x800000) != 0 && !this.isList.execute(arg0Value)) {
                        HostObject.RemoveArrayElement.doOther(arg0Value, arg1Value, this.isList);
                        return;
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeArrayElementAndSpecialize(arg0Value, arg1Value);
            }

            private void removeArrayElementAndSpecialize(HostObject arg0Value, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsListNode removeArrayElement_other_isList__;
                    HostObject.IsListNode removeArrayElement_list_isList__;
                    int state_0 = this.state_0_;
                    boolean List_duplicateFound_ = false;
                    if ((state_0 & 0x400000) != 0 && this.isList.execute(arg0Value)) {
                        List_duplicateFound_ = true;
                    }
                    if (!List_duplicateFound_ && (removeArrayElement_list_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x400000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode removeArrayElement_list_isList___check = super.insert(removeArrayElement_list_isList__);
                            if (removeArrayElement_list_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doList(HostObject, long, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = removeArrayElement_list_isList___check;
                        }
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x400000;
                        List_duplicateFound_ = true;
                    }
                    if (List_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.RemoveArrayElement.doList(arg0Value, arg1Value, this.isList, this.error);
                        return;
                    }
                    boolean Other_duplicateFound_ = false;
                    if ((state_0 & 0x800000) != 0 && !this.isList.execute(arg0Value)) {
                        Other_duplicateFound_ = true;
                    }
                    if (!Other_duplicateFound_ && !(removeArrayElement_other_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x800000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode removeArrayElement_other_isList___check = super.insert(removeArrayElement_other_isList__);
                            if (removeArrayElement_other_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doOther(HostObject, long, IsListNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = removeArrayElement_other_isList___check;
                        }
                        this.state_0_ = state_0 |= 0x800000;
                        Other_duplicateFound_ = true;
                    }
                    if (Other_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.RemoveArrayElement.doOther(arg0Value, arg1Value, this.isList);
                        return;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object readArrayElement(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidArrayIndexException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xF000000) != 0) {
                    if ((state_0 & 0x1000000) != 0 && this.isArray.execute(arg0Value)) {
                        return HostObject.ReadArrayElement.doArray(arg0Value, arg1Value, this.readArrayElement_array_arrayGet_, this.isArray, this.toGuest, this.error);
                    }
                    if ((state_0 & 0x2000000) != 0 && this.isList.execute(arg0Value)) {
                        return HostObject.ReadArrayElement.doList(arg0Value, arg1Value, this.isList, this.toGuest, this.error);
                    }
                    if ((state_0 & 0x4000000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        return HostObject.ReadArrayElement.doMapEntry(arg0Value, arg1Value, this.isMapEntry, this.toGuest, this.error);
                    }
                    if (!((state_0 & 0x8000000) == 0 || this.isArray.execute(arg0Value) || this.isList.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        return HostObject.ReadArrayElement.doNotArrayOrList(arg0Value, arg1Value, this.isArray, this.isList, this.isMapEntry);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readArrayElementAndSpecialize(arg0Value, arg1Value);
            }

            private Object readArrayElementAndSpecialize(HostObject arg0Value, long arg1Value) throws InvalidArrayIndexException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapEntryNode readArrayElement_notArrayOrList_isMapEntry__;
                    HostObject.IsListNode readArrayElement_notArrayOrList_isList__;
                    HostObject.IsArrayNode readArrayElement_notArrayOrList_isArray__;
                    Object readArrayElement_mapEntry_isMapEntry__;
                    Object readArrayElement_list_isList__;
                    Object readArrayElement_array_isArray__;
                    int state_0 = this.state_0_;
                    boolean Array_duplicateFound_ = false;
                    if ((state_0 & 0x1000000) != 0 && this.isArray.execute(arg0Value)) {
                        Array_duplicateFound_ = true;
                    }
                    if (!Array_duplicateFound_ && ((HostObject.IsArrayNode)(readArrayElement_array_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray))).execute(arg0Value) && (state_0 & 0x1000000) == 0) {
                        this.readArrayElement_array_arrayGet_ = super.insert(HostObjectFactory.ArrayGetNodeGen.create());
                        if (this.isArray == null) {
                            HostObject.IsArrayNode readArrayElement_array_isArray___check = (HostObject.IsArrayNode)super.insert(readArrayElement_array_isArray__);
                            if (readArrayElement_array_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doArray(HostObject, long, ArrayGet, IsArrayNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = readArrayElement_array_isArray___check;
                        }
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x1000000;
                        Array_duplicateFound_ = true;
                    }
                    if (Array_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        readArrayElement_array_isArray__ = HostObject.ReadArrayElement.doArray(arg0Value, arg1Value, this.readArrayElement_array_arrayGet_, this.isArray, this.toGuest, this.error);
                        return readArrayElement_array_isArray__;
                    }
                    boolean List_duplicateFound_ = false;
                    if ((state_0 & 0x2000000) != 0 && this.isList.execute(arg0Value)) {
                        List_duplicateFound_ = true;
                    }
                    if (!List_duplicateFound_ && ((HostObject.IsListNode)(readArrayElement_list_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList))).execute(arg0Value) && (state_0 & 0x2000000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode readArrayElement_list_isList___check = (HostObject.IsListNode)super.insert(readArrayElement_list_isList__);
                            if (readArrayElement_list_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doList(HostObject, long, IsListNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = readArrayElement_list_isList___check;
                        }
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x2000000;
                        List_duplicateFound_ = true;
                    }
                    if (List_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        readArrayElement_list_isList__ = HostObject.ReadArrayElement.doList(arg0Value, arg1Value, this.isList, this.toGuest, this.error);
                        return readArrayElement_list_isList__;
                    }
                    boolean MapEntry_duplicateFound_ = false;
                    if ((state_0 & 0x4000000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        MapEntry_duplicateFound_ = true;
                    }
                    if (!MapEntry_duplicateFound_ && ((HostObject.IsMapEntryNode)(readArrayElement_mapEntry_isMapEntry__ = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry))).execute(arg0Value) && (state_0 & 0x4000000) == 0) {
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode readArrayElement_mapEntry_isMapEntry___check = (HostObject.IsMapEntryNode)super.insert(readArrayElement_mapEntry_isMapEntry__);
                            if (readArrayElement_mapEntry_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMapEntry(HostObject, long, IsMapEntryNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = readArrayElement_mapEntry_isMapEntry___check;
                        }
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x4000000;
                        MapEntry_duplicateFound_ = true;
                    }
                    if (MapEntry_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        readArrayElement_mapEntry_isMapEntry__ = HostObject.ReadArrayElement.doMapEntry(arg0Value, arg1Value, this.isMapEntry, this.toGuest, this.error);
                        return readArrayElement_mapEntry_isMapEntry__;
                    }
                    boolean NotArrayOrList_duplicateFound_ = false;
                    if (!((state_0 & 0x8000000) == 0 || this.isArray.execute(arg0Value) || this.isList.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (!(NotArrayOrList_duplicateFound_ || (readArrayElement_notArrayOrList_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) || (readArrayElement_notArrayOrList_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) || (readArrayElement_notArrayOrList_isMapEntry__ = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) || (state_0 & 0x8000000) != 0)) {
                        if (this.isArray == null) {
                            HostObject.IsArrayNode readArrayElement_notArrayOrList_isArray___check = super.insert(readArrayElement_notArrayOrList_isArray__);
                            if (readArrayElement_notArrayOrList_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = readArrayElement_notArrayOrList_isArray___check;
                        }
                        if (this.isList == null) {
                            HostObject.IsListNode readArrayElement_notArrayOrList_isList___check = super.insert(readArrayElement_notArrayOrList_isList__);
                            if (readArrayElement_notArrayOrList_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = readArrayElement_notArrayOrList_isList___check;
                        }
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode readArrayElement_notArrayOrList_isMapEntry___check = super.insert(readArrayElement_notArrayOrList_isMapEntry__);
                            if (readArrayElement_notArrayOrList_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, long, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = readArrayElement_notArrayOrList_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= 0x8000000;
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (NotArrayOrList_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.ReadArrayElement.doNotArrayOrList(arg0Value, arg1Value, this.isArray, this.isList, this.isMapEntry);
                        return object;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0xF0000000) != 0) {
                    if ((state_0 & 0x10000000) != 0 && this.isArray.execute(arg0Value)) {
                        return HostObject.GetArraySize.doArray(arg0Value, this.isArray);
                    }
                    if ((state_0 & 0x20000000) != 0 && this.isList.execute(arg0Value)) {
                        return HostObject.GetArraySize.doList(arg0Value, this.isList, this.error);
                    }
                    if ((state_0 & 0x40000000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        return HostObject.GetArraySize.doMapEntry(arg0Value, this.isMapEntry);
                    }
                    if (!((state_0 & Integer.MIN_VALUE) == 0 || this.isArray.execute(arg0Value) || this.isList.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        return HostObject.GetArraySize.doNotArrayOrList(arg0Value, this.isArray, this.isList, this.isMapEntry);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getArraySizeAndSpecialize(arg0Value);
            }

            private long getArraySizeAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapEntryNode getArraySize_notArrayOrList_isMapEntry__;
                    HostObject.IsListNode getArraySize_notArrayOrList_isList__;
                    HostObject.IsArrayNode getArraySize_notArrayOrList_isArray__;
                    HostObject.IsMapEntryNode getArraySize_mapEntry_isMapEntry__2;
                    HostObject.IsListNode getArraySize_list_isList__2;
                    HostObject.IsArrayNode getArraySize_array_isArray__2;
                    int state_0 = this.state_0_;
                    boolean Array_duplicateFound_ = false;
                    if ((state_0 & 0x10000000) != 0 && this.isArray.execute(arg0Value)) {
                        Array_duplicateFound_ = true;
                    }
                    if (!Array_duplicateFound_ && (getArraySize_array_isArray__2 = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) && (state_0 & 0x10000000) == 0) {
                        if (this.isArray == null) {
                            HostObject.IsArrayNode getArraySize_array_isArray___check = super.insert(getArraySize_array_isArray__2);
                            if (getArraySize_array_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doArray(HostObject, IsArrayNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = getArraySize_array_isArray___check;
                        }
                        this.state_0_ = state_0 |= 0x10000000;
                        Array_duplicateFound_ = true;
                    }
                    if (Array_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        long getArraySize_array_isArray__2 = HostObject.GetArraySize.doArray(arg0Value, this.isArray);
                        return getArraySize_array_isArray__2;
                    }
                    boolean List_duplicateFound_ = false;
                    if ((state_0 & 0x20000000) != 0 && this.isList.execute(arg0Value)) {
                        List_duplicateFound_ = true;
                    }
                    if (!List_duplicateFound_ && (getArraySize_list_isList__2 = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) && (state_0 & 0x20000000) == 0) {
                        if (this.isList == null) {
                            HostObject.IsListNode getArraySize_list_isList___check = super.insert(getArraySize_list_isList__2);
                            if (getArraySize_list_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doList(HostObject, IsListNode, BranchProfile)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = getArraySize_list_isList___check;
                        }
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_0_ = state_0 |= 0x20000000;
                        List_duplicateFound_ = true;
                    }
                    if (List_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        long getArraySize_list_isList__2 = HostObject.GetArraySize.doList(arg0Value, this.isList, this.error);
                        return getArraySize_list_isList__2;
                    }
                    boolean MapEntry_duplicateFound_ = false;
                    if ((state_0 & 0x40000000) != 0 && this.isMapEntry.execute(arg0Value)) {
                        MapEntry_duplicateFound_ = true;
                    }
                    if (!MapEntry_duplicateFound_ && (getArraySize_mapEntry_isMapEntry__2 = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) && (state_0 & 0x40000000) == 0) {
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode getArraySize_mapEntry_isMapEntry___check = super.insert(getArraySize_mapEntry_isMapEntry__2);
                            if (getArraySize_mapEntry_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMapEntry(HostObject, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = getArraySize_mapEntry_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= 0x40000000;
                        MapEntry_duplicateFound_ = true;
                    }
                    if (MapEntry_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        long getArraySize_mapEntry_isMapEntry__2 = HostObject.GetArraySize.doMapEntry(arg0Value, this.isMapEntry);
                        return getArraySize_mapEntry_isMapEntry__2;
                    }
                    boolean NotArrayOrList_duplicateFound_ = false;
                    if (!((state_0 & Integer.MIN_VALUE) == 0 || this.isArray.execute(arg0Value) || this.isList.execute(arg0Value) || this.isMapEntry.execute(arg0Value))) {
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (!(NotArrayOrList_duplicateFound_ || (getArraySize_notArrayOrList_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) || (getArraySize_notArrayOrList_isList__ = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList)).execute(arg0Value) || (getArraySize_notArrayOrList_isMapEntry__ = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry)).execute(arg0Value) || (state_0 & Integer.MIN_VALUE) != 0)) {
                        if (this.isArray == null) {
                            HostObject.IsArrayNode getArraySize_notArrayOrList_isArray___check = super.insert(getArraySize_notArrayOrList_isArray__);
                            if (getArraySize_notArrayOrList_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = getArraySize_notArrayOrList_isArray___check;
                        }
                        if (this.isList == null) {
                            HostObject.IsListNode getArraySize_notArrayOrList_isList___check = super.insert(getArraySize_notArrayOrList_isList__);
                            if (getArraySize_notArrayOrList_isList___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isList' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isList = getArraySize_notArrayOrList_isList___check;
                        }
                        if (this.isMapEntry == null) {
                            HostObject.IsMapEntryNode getArraySize_notArrayOrList_isMapEntry___check = super.insert(getArraySize_notArrayOrList_isMapEntry__);
                            if (getArraySize_notArrayOrList_isMapEntry___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrList(HostObject, IsArrayNode, IsListNode, IsMapEntryNode)' contains a shared cache with name 'isMapEntry' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMapEntry = getArraySize_notArrayOrList_isMapEntry___check;
                        }
                        this.state_0_ = state_0 |= Integer.MIN_VALUE;
                        NotArrayOrList_duplicateFound_ = true;
                    }
                    if (NotArrayOrList_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        long l = HostObject.GetArraySize.doNotArrayOrList(arg0Value, this.isArray, this.isList, this.isMapEntry);
                        return l;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isInstantiable(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 7) != 0) {
                    if ((state_1 & 1) != 0 && !arg0Value.isClass()) {
                        return HostObject.IsInstantiable.doUnsupported(arg0Value);
                    }
                    if ((state_1 & 2) != 0 && arg0Value.isArrayClass()) {
                        return HostObject.IsInstantiable.doArrayCached(arg0Value);
                    }
                    if ((state_1 & 4) != 0 && arg0Value.isDefaultClass()) {
                        return HostObject.IsInstantiable.doObjectCached(arg0Value, this.lookupConstructor);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isInstantiableAndSpecialize(arg0Value);
            }

            private boolean isInstantiableAndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    if (!arg0Value.isClass()) {
                        this.state_1_ = state_1 |= 1;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HostObject.IsInstantiable.doUnsupported(arg0Value);
                        return bl;
                    }
                    if (arg0Value.isArrayClass()) {
                        this.state_1_ = state_1 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HostObject.IsInstantiable.doArrayCached(arg0Value);
                        return bl;
                    }
                    if (arg0Value.isDefaultClass()) {
                        this.lookupConstructor = super.insert(this.lookupConstructor == null ? HostObjectFactory.LookupConstructorNodeGen.create() : this.lookupConstructor);
                        this.state_1_ = state_1 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HostObject.IsInstantiable.doObjectCached(arg0Value, this.lookupConstructor);
                        return bl;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object instantiate(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x38) != 0) {
                    if ((state_1 & 8) != 0 && !arg0Value.isClass()) {
                        return HostObject.Instantiate.doUnsupported(arg0Value, arg1Value);
                    }
                    if ((state_1 & 0x10) != 0 && arg0Value.isArrayClass()) {
                        return HostObject.Instantiate.doArrayCached(arg0Value, arg1Value, this.instantiate_arrayCached_indexes_, this.error);
                    }
                    if ((state_1 & 0x20) != 0 && arg0Value.isDefaultClass()) {
                        return HostObject.Instantiate.doObjectCached(arg0Value, arg1Value, this.lookupConstructor, this.hostExecute, this.error);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.instantiateAndSpecialize(arg0Value, arg1Value);
            }

            private Object instantiateAndSpecialize(HostObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    if (!arg0Value.isClass()) {
                        this.state_1_ = state_1 |= 8;
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.Instantiate.doUnsupported(arg0Value, arg1Value);
                        return object;
                    }
                    if (arg0Value.isArrayClass()) {
                        this.instantiate_arrayCached_indexes_ = super.insert(INTEROP_LIBRARY_.createDispatched(1));
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.Instantiate.doArrayCached(arg0Value, arg1Value, this.instantiate_arrayCached_indexes_, this.error);
                        return object;
                    }
                    if (arg0Value.isDefaultClass()) {
                        this.lookupConstructor = super.insert(this.lookupConstructor == null ? HostObjectFactory.LookupConstructorNodeGen.create() : this.lookupConstructor);
                        this.hostExecute = super.insert(this.hostExecute == null ? HostExecuteNode.create() : this.hostExecute);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.Instantiate.doObjectCached(arg0Value, arg1Value, this.lookupConstructor, this.hostExecute, this.error);
                        return object;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x1C0) != 0) {
                    if ((state_1 & 0x40) != 0 && this.isArray.execute(arg0Value)) {
                        return HostObject.GetIterator.doArray(arg0Value, this.isArray, this.toGuest);
                    }
                    if ((state_1 & 0x80) != 0 && this.isIterable.execute(arg0Value)) {
                        return HostObject.GetIterator.doIterable(arg0Value, this.isIterable, this.toGuest, this.error);
                    }
                    if ((state_1 & 0x100) != 0 && !this.isArray.execute(arg0Value) && !this.isIterable.execute(arg0Value)) {
                        return HostObject.GetIterator.doNotArrayOrIterable(arg0Value, this.isArray, this.isIterable);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getIteratorAndSpecialize(arg0Value);
            }

            private Object getIteratorAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsIterableNode getIterator_notArrayOrIterable_isIterable__;
                    HostObject.IsArrayNode getIterator_notArrayOrIterable_isArray__;
                    Object getIterator_iterable_isIterable__;
                    Object getIterator_array_isArray__;
                    int state_1 = this.state_1_;
                    boolean Array_duplicateFound_ = false;
                    if ((state_1 & 0x40) != 0 && this.isArray.execute(arg0Value)) {
                        Array_duplicateFound_ = true;
                    }
                    if (!Array_duplicateFound_ && ((HostObject.IsArrayNode)(getIterator_array_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray))).execute(arg0Value) && (state_1 & 0x40) == 0) {
                        if (this.isArray == null) {
                            HostObject.IsArrayNode getIterator_array_isArray___check = (HostObject.IsArrayNode)super.insert(getIterator_array_isArray__);
                            if (getIterator_array_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doArray(HostObject, IsArrayNode, ToGuestValueNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = getIterator_array_isArray___check;
                        }
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.state_1_ = state_1 |= 0x40;
                        Array_duplicateFound_ = true;
                    }
                    if (Array_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        getIterator_array_isArray__ = HostObject.GetIterator.doArray(arg0Value, this.isArray, this.toGuest);
                        return getIterator_array_isArray__;
                    }
                    boolean Iterable_duplicateFound_ = false;
                    if ((state_1 & 0x80) != 0 && this.isIterable.execute(arg0Value)) {
                        Iterable_duplicateFound_ = true;
                    }
                    if (!Iterable_duplicateFound_ && ((HostObject.IsIterableNode)(getIterator_iterable_isIterable__ = super.insert(this.isIterable == null ? HostObjectFactory.IsIterableNodeGen.create() : this.isIterable))).execute(arg0Value) && (state_1 & 0x80) == 0) {
                        if (this.isIterable == null) {
                            HostObject.IsIterableNode getIterator_iterable_isIterable___check = (HostObject.IsIterableNode)super.insert(getIterator_iterable_isIterable__);
                            if (getIterator_iterable_isIterable___check == null) {
                                throw new AssertionError((Object)"Specialization 'doIterable(HostObject, IsIterableNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isIterable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isIterable = getIterator_iterable_isIterable___check;
                        }
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x80;
                        Iterable_duplicateFound_ = true;
                    }
                    if (Iterable_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        getIterator_iterable_isIterable__ = HostObject.GetIterator.doIterable(arg0Value, this.isIterable, this.toGuest, this.error);
                        return getIterator_iterable_isIterable__;
                    }
                    boolean NotArrayOrIterable_duplicateFound_ = false;
                    if ((state_1 & 0x100) != 0 && !this.isArray.execute(arg0Value) && !this.isIterable.execute(arg0Value)) {
                        NotArrayOrIterable_duplicateFound_ = true;
                    }
                    if (!(NotArrayOrIterable_duplicateFound_ || (getIterator_notArrayOrIterable_isArray__ = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray)).execute(arg0Value) || (getIterator_notArrayOrIterable_isIterable__ = super.insert(this.isIterable == null ? HostObjectFactory.IsIterableNodeGen.create() : this.isIterable)).execute(arg0Value) || (state_1 & 0x100) != 0)) {
                        if (this.isArray == null) {
                            HostObject.IsArrayNode getIterator_notArrayOrIterable_isArray___check = super.insert(getIterator_notArrayOrIterable_isArray__);
                            if (getIterator_notArrayOrIterable_isArray___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrIterable(HostObject, IsArrayNode, IsIterableNode)' contains a shared cache with name 'isArray' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isArray = getIterator_notArrayOrIterable_isArray___check;
                        }
                        if (this.isIterable == null) {
                            HostObject.IsIterableNode getIterator_notArrayOrIterable_isIterable___check = super.insert(getIterator_notArrayOrIterable_isIterable__);
                            if (getIterator_notArrayOrIterable_isIterable___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotArrayOrIterable(HostObject, IsArrayNode, IsIterableNode)' contains a shared cache with name 'isIterable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isIterable = getIterator_notArrayOrIterable_isIterable___check;
                        }
                        this.state_1_ = state_1 |= 0x100;
                        NotArrayOrIterable_duplicateFound_ = true;
                    }
                    if (NotArrayOrIterable_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.GetIterator.doNotArrayOrIterable(arg0Value, this.isArray, this.isIterable);
                        return object;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x600) != 0) {
                    if ((state_1 & 0x200) != 0 && this.isIterator.execute(arg0Value)) {
                        return HostObject.HasIteratorNextElement.doIterator(arg0Value, this.isIterator, this.error);
                    }
                    if ((state_1 & 0x400) != 0 && !this.isIterator.execute(arg0Value)) {
                        return HostObject.HasIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasIteratorNextElementAndSpecialize(arg0Value);
            }

            private boolean hasIteratorNextElementAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsIteratorNode hasIteratorNextElement_notIterator_isIterator__;
                    HostObject.IsIteratorNode hasIteratorNextElement_iterator_isIterator__2;
                    int state_1 = this.state_1_;
                    boolean Iterator_duplicateFound_ = false;
                    if ((state_1 & 0x200) != 0 && this.isIterator.execute(arg0Value)) {
                        Iterator_duplicateFound_ = true;
                    }
                    if (!Iterator_duplicateFound_ && (hasIteratorNextElement_iterator_isIterator__2 = super.insert(this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator)).execute(arg0Value) && (state_1 & 0x200) == 0) {
                        if (this.isIterator == null) {
                            HostObject.IsIteratorNode hasIteratorNextElement_iterator_isIterator___check = super.insert(hasIteratorNextElement_iterator_isIterator__2);
                            if (hasIteratorNextElement_iterator_isIterator___check == null) {
                                throw new AssertionError((Object)"Specialization 'doIterator(HostObject, IsIteratorNode, BranchProfile)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isIterator = hasIteratorNextElement_iterator_isIterator___check;
                        }
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x200;
                        Iterator_duplicateFound_ = true;
                    }
                    if (Iterator_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean hasIteratorNextElement_iterator_isIterator__2 = HostObject.HasIteratorNextElement.doIterator(arg0Value, this.isIterator, this.error);
                        return hasIteratorNextElement_iterator_isIterator__2;
                    }
                    boolean NotIterator_duplicateFound_ = false;
                    if ((state_1 & 0x400) != 0 && !this.isIterator.execute(arg0Value)) {
                        NotIterator_duplicateFound_ = true;
                    }
                    if (!NotIterator_duplicateFound_ && !(hasIteratorNextElement_notIterator_isIterator__ = super.insert(this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator)).execute(arg0Value) && (state_1 & 0x400) == 0) {
                        if (this.isIterator == null) {
                            HostObject.IsIteratorNode hasIteratorNextElement_notIterator_isIterator___check = super.insert(hasIteratorNextElement_notIterator_isIterator__);
                            if (hasIteratorNextElement_notIterator_isIterator___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotIterator(HostObject, IsIteratorNode)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isIterator = hasIteratorNextElement_notIterator_isIterator___check;
                        }
                        this.state_1_ = state_1 |= 0x400;
                        NotIterator_duplicateFound_ = true;
                    }
                    if (NotIterator_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean bl = HostObject.HasIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
                        return bl;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x1800) != 0) {
                    if ((state_1 & 0x800) != 0 && this.isIterator.execute(arg0Value)) {
                        return HostObject.GetIteratorNextElement.doIterator(arg0Value, this.isIterator, this.toGuest, this.error, this.getIteratorNextElement_iterator_stopIteration_);
                    }
                    if ((state_1 & 0x1000) != 0 && !this.isIterator.execute(arg0Value)) {
                        return HostObject.GetIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getIteratorNextElementAndSpecialize(arg0Value);
            }

            private Object getIteratorNextElementAndSpecialize(HostObject arg0Value) throws StopIterationException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsIteratorNode getIteratorNextElement_notIterator_isIterator__;
                    Object getIteratorNextElement_iterator_isIterator__;
                    int state_1 = this.state_1_;
                    boolean Iterator_duplicateFound_ = false;
                    if ((state_1 & 0x800) != 0 && this.isIterator.execute(arg0Value)) {
                        Iterator_duplicateFound_ = true;
                    }
                    if (!Iterator_duplicateFound_ && ((HostObject.IsIteratorNode)(getIteratorNextElement_iterator_isIterator__ = super.insert(this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator))).execute(arg0Value) && (state_1 & 0x800) == 0) {
                        if (this.isIterator == null) {
                            HostObject.IsIteratorNode getIteratorNextElement_iterator_isIterator___check = (HostObject.IsIteratorNode)super.insert(getIteratorNextElement_iterator_isIterator__);
                            if (getIteratorNextElement_iterator_isIterator___check == null) {
                                throw new AssertionError((Object)"Specialization 'doIterator(HostObject, IsIteratorNode, ToGuestValueNode, BranchProfile, BranchProfile)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isIterator = getIteratorNextElement_iterator_isIterator___check;
                        }
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.getIteratorNextElement_iterator_stopIteration_ = BranchProfile.create();
                        this.state_1_ = state_1 |= 0x800;
                        Iterator_duplicateFound_ = true;
                    }
                    if (Iterator_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        getIteratorNextElement_iterator_isIterator__ = HostObject.GetIteratorNextElement.doIterator(arg0Value, this.isIterator, this.toGuest, this.error, this.getIteratorNextElement_iterator_stopIteration_);
                        return getIteratorNextElement_iterator_isIterator__;
                    }
                    boolean NotIterator_duplicateFound_ = false;
                    if ((state_1 & 0x1000) != 0 && !this.isIterator.execute(arg0Value)) {
                        NotIterator_duplicateFound_ = true;
                    }
                    if (!NotIterator_duplicateFound_ && !(getIteratorNextElement_notIterator_isIterator__ = super.insert(this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator)).execute(arg0Value) && (state_1 & 0x1000) == 0) {
                        if (this.isIterator == null) {
                            HostObject.IsIteratorNode getIteratorNextElement_notIterator_isIterator___check = super.insert(getIteratorNextElement_notIterator_isIterator__);
                            if (getIteratorNextElement_notIterator_isIterator___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotIterator(HostObject, IsIteratorNode)' contains a shared cache with name 'isIterator' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isIterator = getIteratorNextElement_notIterator_isIterator___check;
                        }
                        this.state_1_ = state_1 |= 0x1000;
                        NotIterator_duplicateFound_ = true;
                    }
                    if (NotIterator_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.GetIteratorNextElement.doNotIterator(arg0Value, this.isIterator);
                        return object;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public long getHashSize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x6000) != 0) {
                    if ((state_1 & 0x2000) != 0 && this.isMap.execute(arg0Value)) {
                        return HostObject.GetHashSize.doMap(arg0Value, this.isMap, this.error);
                    }
                    if ((state_1 & 0x4000) != 0 && !this.isMap.execute(arg0Value)) {
                        return HostObject.GetHashSize.doNotMap(arg0Value, this.isMap);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getHashSizeAndSpecialize(arg0Value);
            }

            private long getHashSizeAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapNode getHashSize_notMap_isMap__;
                    HostObject.IsMapNode getHashSize_map_isMap__2;
                    int state_1 = this.state_1_;
                    boolean Map_duplicateFound_ = false;
                    if ((state_1 & 0x2000) != 0 && this.isMap.execute(arg0Value)) {
                        Map_duplicateFound_ = true;
                    }
                    if (!Map_duplicateFound_ && (getHashSize_map_isMap__2 = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x2000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode getHashSize_map_isMap___check = super.insert(getHashSize_map_isMap__2);
                            if (getHashSize_map_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMap(HostObject, IsMapNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = getHashSize_map_isMap___check;
                        }
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x2000;
                        Map_duplicateFound_ = true;
                    }
                    if (Map_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        long getHashSize_map_isMap__2 = HostObject.GetHashSize.doMap(arg0Value, this.isMap, this.error);
                        return getHashSize_map_isMap__2;
                    }
                    boolean NotMap_duplicateFound_ = false;
                    if ((state_1 & 0x4000) != 0 && !this.isMap.execute(arg0Value)) {
                        NotMap_duplicateFound_ = true;
                    }
                    if (!NotMap_duplicateFound_ && !(getHashSize_notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x4000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode getHashSize_notMap_isMap___check = super.insert(getHashSize_notMap_isMap__);
                            if (getHashSize_notMap_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotMap(HostObject, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = getHashSize_notMap_isMap___check;
                        }
                        this.state_1_ = state_1 |= 0x4000;
                        NotMap_duplicateFound_ = true;
                    }
                    if (NotMap_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        long l = HostObject.GetHashSize.doNotMap(arg0Value, this.isMap);
                        return l;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x18000) != 0) {
                    if ((state_1 & 0x8000) != 0 && this.isMap.execute(arg0Value)) {
                        return HostObject.ReadHashValue.doMap(arg0Value, arg1Value, this.isMap, this.toHost, this.toGuest, this.error);
                    }
                    if ((state_1 & 0x10000) != 0 && !this.isMap.execute(arg0Value)) {
                        return HostObject.ReadHashValue.doNotMap(arg0Value, arg1Value, this.isMap);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readHashValueAndSpecialize(arg0Value, arg1Value);
            }

            private Object readHashValueAndSpecialize(HostObject arg0Value, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapNode readHashValue_notMap_isMap__;
                    Object readHashValue_map_isMap__;
                    int state_1 = this.state_1_;
                    boolean Map_duplicateFound_ = false;
                    if ((state_1 & 0x8000) != 0 && this.isMap.execute(arg0Value)) {
                        Map_duplicateFound_ = true;
                    }
                    if (!Map_duplicateFound_ && ((HostObject.IsMapNode)(readHashValue_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap))).execute(arg0Value) && (state_1 & 0x8000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode readHashValue_map_isMap___check = (HostObject.IsMapNode)super.insert(readHashValue_map_isMap__);
                            if (readHashValue_map_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMap(HostObject, Object, IsMapNode, HostToTypeNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = readHashValue_map_isMap___check;
                        }
                        this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x8000;
                        Map_duplicateFound_ = true;
                    }
                    if (Map_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        readHashValue_map_isMap__ = HostObject.ReadHashValue.doMap(arg0Value, arg1Value, this.isMap, this.toHost, this.toGuest, this.error);
                        return readHashValue_map_isMap__;
                    }
                    boolean NotMap_duplicateFound_ = false;
                    if ((state_1 & 0x10000) != 0 && !this.isMap.execute(arg0Value)) {
                        NotMap_duplicateFound_ = true;
                    }
                    if (!NotMap_duplicateFound_ && !(readHashValue_notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x10000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode readHashValue_notMap_isMap___check = super.insert(readHashValue_notMap_isMap__);
                            if (readHashValue_notMap_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotMap(HostObject, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = readHashValue_notMap_isMap___check;
                        }
                        this.state_1_ = state_1 |= 0x10000;
                        NotMap_duplicateFound_ = true;
                    }
                    if (NotMap_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.ReadHashValue.doNotMap(arg0Value, arg1Value, this.isMap);
                        return object;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x60000) != 0) {
                    if ((state_1 & 0x20000) != 0 && this.isMap.execute(arg0Value)) {
                        HostObject.WriteHashEntry.doMap(arg0Value, arg1Value, arg2Value, this.isMap, this.toHost, this.error);
                        return;
                    }
                    if ((state_1 & 0x40000) != 0 && !this.isMap.execute(arg0Value)) {
                        HostObject.WriteHashEntry.doNotMap(arg0Value, arg1Value, arg2Value, this.isMap);
                        return;
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeHashEntryAndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            private void writeHashEntryAndSpecialize(HostObject arg0Value, Object arg1Value, Object arg2Value) throws UnsupportedTypeException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapNode writeHashEntry_notMap_isMap__;
                    HostObject.IsMapNode writeHashEntry_map_isMap__;
                    int state_1 = this.state_1_;
                    boolean Map_duplicateFound_ = false;
                    if ((state_1 & 0x20000) != 0 && this.isMap.execute(arg0Value)) {
                        Map_duplicateFound_ = true;
                    }
                    if (!Map_duplicateFound_ && (writeHashEntry_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x20000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode writeHashEntry_map_isMap___check = super.insert(writeHashEntry_map_isMap__);
                            if (writeHashEntry_map_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMap(HostObject, Object, Object, IsMapNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = writeHashEntry_map_isMap___check;
                        }
                        this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x20000;
                        Map_duplicateFound_ = true;
                    }
                    if (Map_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.WriteHashEntry.doMap(arg0Value, arg1Value, arg2Value, this.isMap, this.toHost, this.error);
                        return;
                    }
                    boolean NotMap_duplicateFound_ = false;
                    if ((state_1 & 0x40000) != 0 && !this.isMap.execute(arg0Value)) {
                        NotMap_duplicateFound_ = true;
                    }
                    if (!NotMap_duplicateFound_ && !(writeHashEntry_notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x40000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode writeHashEntry_notMap_isMap___check = super.insert(writeHashEntry_notMap_isMap__);
                            if (writeHashEntry_notMap_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotMap(HostObject, Object, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = writeHashEntry_notMap_isMap___check;
                        }
                        this.state_1_ = state_1 |= 0x40000;
                        NotMap_duplicateFound_ = true;
                    }
                    if (NotMap_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.WriteHashEntry.doNotMap(arg0Value, arg1Value, arg2Value, this.isMap);
                        return;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x180000) != 0) {
                    if ((state_1 & 0x80000) != 0 && this.isMap.execute(arg0Value)) {
                        HostObject.RemoveHashEntry.doMap(arg0Value, arg1Value, this.isMap, this.toHost, this.error);
                        return;
                    }
                    if ((state_1 & 0x100000) != 0 && !this.isMap.execute(arg0Value)) {
                        HostObject.RemoveHashEntry.doNotMap(arg0Value, arg1Value, this.isMap);
                        return;
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeHashEntryAndSpecialize(arg0Value, arg1Value);
            }

            private void removeHashEntryAndSpecialize(HostObject arg0Value, Object arg1Value) throws UnknownKeyException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapNode removeHashEntry_notMap_isMap__;
                    HostObject.IsMapNode removeHashEntry_map_isMap__;
                    int state_1 = this.state_1_;
                    boolean Map_duplicateFound_ = false;
                    if ((state_1 & 0x80000) != 0 && this.isMap.execute(arg0Value)) {
                        Map_duplicateFound_ = true;
                    }
                    if (!Map_duplicateFound_ && (removeHashEntry_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x80000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode removeHashEntry_map_isMap___check = super.insert(removeHashEntry_map_isMap__);
                            if (removeHashEntry_map_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMap(HostObject, Object, IsMapNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = removeHashEntry_map_isMap___check;
                        }
                        this.toHost = super.insert(this.toHost == null ? HostToTypeNodeGen.create() : this.toHost);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x80000;
                        Map_duplicateFound_ = true;
                    }
                    if (Map_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.RemoveHashEntry.doMap(arg0Value, arg1Value, this.isMap, this.toHost, this.error);
                        return;
                    }
                    boolean NotMap_duplicateFound_ = false;
                    if ((state_1 & 0x100000) != 0 && !this.isMap.execute(arg0Value)) {
                        NotMap_duplicateFound_ = true;
                    }
                    if (!NotMap_duplicateFound_ && !(removeHashEntry_notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x100000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode removeHashEntry_notMap_isMap___check = super.insert(removeHashEntry_notMap_isMap__);
                            if (removeHashEntry_notMap_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotMap(HostObject, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = removeHashEntry_notMap_isMap___check;
                        }
                        this.state_1_ = state_1 |= 0x100000;
                        NotMap_duplicateFound_ = true;
                    }
                    if (NotMap_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        HostObject.RemoveHashEntry.doNotMap(arg0Value, arg1Value, this.isMap);
                        return;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x600000) != 0) {
                    if ((state_1 & 0x200000) != 0 && this.isMap.execute(arg0Value)) {
                        return HostObject.GetHashEntriesIterator.doMap(arg0Value, this.isMap, this.toGuest, this.error);
                    }
                    if ((state_1 & 0x400000) != 0 && !this.isMap.execute(arg0Value)) {
                        return HostObject.GetHashEntriesIterator.doNotMap(arg0Value, this.isMap);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getHashEntriesIteratorAndSpecialize(arg0Value);
            }

            private Object getHashEntriesIteratorAndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    HostObject.IsMapNode getHashEntriesIterator_notMap_isMap__;
                    Object getHashEntriesIterator_map_isMap__;
                    int state_1 = this.state_1_;
                    boolean Map_duplicateFound_ = false;
                    if ((state_1 & 0x200000) != 0 && this.isMap.execute(arg0Value)) {
                        Map_duplicateFound_ = true;
                    }
                    if (!Map_duplicateFound_ && ((HostObject.IsMapNode)(getHashEntriesIterator_map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap))).execute(arg0Value) && (state_1 & 0x200000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode getHashEntriesIterator_map_isMap___check = (HostObject.IsMapNode)super.insert(getHashEntriesIterator_map_isMap__);
                            if (getHashEntriesIterator_map_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMap(HostObject, IsMapNode, ToGuestValueNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = getHashEntriesIterator_map_isMap___check;
                        }
                        this.toGuest = super.insert(this.toGuest == null ? HostContextFactory.ToGuestValueNodeGen.create() : this.toGuest);
                        this.error = this.error == null ? BranchProfile.create() : this.error;
                        this.state_1_ = state_1 |= 0x200000;
                        Map_duplicateFound_ = true;
                    }
                    if (Map_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        getHashEntriesIterator_map_isMap__ = HostObject.GetHashEntriesIterator.doMap(arg0Value, this.isMap, this.toGuest, this.error);
                        return getHashEntriesIterator_map_isMap__;
                    }
                    boolean NotMap_duplicateFound_ = false;
                    if ((state_1 & 0x400000) != 0 && !this.isMap.execute(arg0Value)) {
                        NotMap_duplicateFound_ = true;
                    }
                    if (!NotMap_duplicateFound_ && !(getHashEntriesIterator_notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap)).execute(arg0Value) && (state_1 & 0x400000) == 0) {
                        if (this.isMap == null) {
                            HostObject.IsMapNode getHashEntriesIterator_notMap_isMap___check = super.insert(getHashEntriesIterator_notMap_isMap__);
                            if (getHashEntriesIterator_notMap_isMap___check == null) {
                                throw new AssertionError((Object)"Specialization 'doNotMap(HostObject, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isMap = getHashEntriesIterator_notMap_isMap___check;
                        }
                        this.state_1_ = state_1 |= 0x400000;
                        NotMap_duplicateFound_ = true;
                    }
                    if (NotMap_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = HostObject.GetHashEntriesIterator.doNotMap(arg0Value, this.isMap);
                        return object;
                    }
                    throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x1800000) != 0) {
                    if ((state_1 & 0x800000) != 0 && arg1Value instanceof HostObject) {
                        HostObject arg1Value_ = (HostObject)arg1Value;
                        return HostObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                    }
                    if ((state_1 & 0x1000000) != 0 && Cached.isIdenticalOrUndefinedFallbackGuard_(state_1, arg0Value, arg1Value)) {
                        return HostObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
                    }
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private TriState isIdenticalOrUndefinedAndSpecialize(HostObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    if (arg1Value instanceof HostObject) {
                        HostObject arg1Value_ = (HostObject)arg1Value;
                        this.state_1_ = state_1 |= 0x800000;
                        lock.unlock();
                        hasLock = false;
                        TriState triState = HostObject.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
                        return triState;
                    }
                    this.state_1_ = state_1 |= 0x1000000;
                    lock.unlock();
                    hasLock = false;
                    TriState triState = HostObject.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
                    return triState;
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
                return ((HostObject)receiver).hasMembers();
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getMembers(includeInternal);
            }

            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x2000000) != 0) {
                    return arg0Value.readMember(arg1Value, this.lookupField, this.readField, this.lookupMethod, this.readMemberNode__readMember_lookupInnerClass_, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readMemberNode_AndSpecialize(HostObject arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    this.lookupField = super.insert(this.lookupField == null ? HostObjectFactory.LookupFieldNodeGen.create() : this.lookupField);
                    this.readField = super.insert(this.readField == null ? HostObjectFactory.ReadFieldNodeGen.create() : this.readField);
                    this.lookupMethod = super.insert(this.lookupMethod == null ? HostObjectFactory.LookupMethodNodeGen.create() : this.lookupMethod);
                    this.readMemberNode__readMember_lookupInnerClass_ = super.insert(HostObjectFactory.LookupInnerClassNodeGen.create());
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_1_ = state_1 |= 0x2000000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readMember(arg1Value, this.lookupField, this.readField, this.lookupMethod, this.readMemberNode__readMember_lookupInnerClass_, this.error);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isMemberInsertable(member);
            }

            @Override
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x4000000) != 0) {
                    arg0Value.writeMember(arg1Value, arg2Value, this.lookupField, this.writeMemberNode__writeMember_writeField_, this.error);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeMemberNode_AndSpecialize(HostObject arg0Value, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    this.lookupField = super.insert(this.lookupField == null ? HostObjectFactory.LookupFieldNodeGen.create() : this.lookupField);
                    this.writeMemberNode__writeMember_writeField_ = super.insert(HostObjectFactory.WriteFieldNodeGen.create());
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_1_ = state_1 |= 0x4000000;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeMember(arg1Value, arg2Value, this.lookupField, this.writeMemberNode__writeMember_writeField_, this.error);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x8000000) != 0) {
                    return arg0Value.invokeMember(arg1Value, arg2Value, this.lookupMethod, this.hostExecute, this.lookupField, this.readField, this.invokeMemberNode__invokeMember_fieldValues_, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object invokeMemberNode_AndSpecialize(HostObject arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    this.lookupMethod = super.insert(this.lookupMethod == null ? HostObjectFactory.LookupMethodNodeGen.create() : this.lookupMethod);
                    this.hostExecute = super.insert(this.hostExecute == null ? HostExecuteNode.create() : this.hostExecute);
                    this.lookupField = super.insert(this.lookupField == null ? HostObjectFactory.LookupFieldNodeGen.create() : this.lookupField);
                    this.readField = super.insert(this.readField == null ? HostObjectFactory.ReadFieldNodeGen.create() : this.readField);
                    this.invokeMemberNode__invokeMember_fieldValues_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_1_ = state_1 |= 0x8000000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.invokeMember(arg1Value, arg2Value, this.lookupMethod, this.hostExecute, this.lookupField, this.readField, this.invokeMemberNode__invokeMember_fieldValues_, this.error);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isArrayElementInsertable(Object arg0Value_, long arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x10000000) != 0) {
                    return arg0Value.isArrayElementInsertable(arg1Value, this.isList, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isArrayElementInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isArrayElementInsertableNode_AndSpecialize(HostObject arg0Value, long arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    this.isList = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_1_ = state_1 |= 0x10000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isArrayElementInsertable(arg1Value, this.isList, this.error);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasArrayElements(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x20000000) != 0) {
                    return arg0Value.hasArrayElements(this.isList, this.isArray, this.isMapEntry);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasArrayElementsNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasArrayElementsNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    this.isList = super.insert(this.isList == null ? HostObjectFactory.IsListNodeGen.create() : this.isList);
                    this.isArray = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray);
                    this.isMapEntry = super.insert(this.isMapEntry == null ? HostObjectFactory.IsMapEntryNodeGen.create() : this.isMapEntry);
                    this.state_1_ = state_1 |= 0x20000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasArrayElements(this.isList, this.isArray, this.isMapEntry);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasBufferElements(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & 0x40000000) != 0) {
                    return arg0Value.hasBufferElements(this.isBuffer);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasBufferElementsNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasBufferElementsNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.state_1_ = state_1 |= 0x40000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasBufferElements(this.isBuffer);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_1 = this.state_1_;
                if ((state_1 & Integer.MIN_VALUE) != 0) {
                    return arg0Value.isBufferWritable(this.isBuffer, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isBufferWritableNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isBufferWritableNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_1 = this.state_1_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_1_ = state_1 |= Integer.MIN_VALUE;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isBufferWritable(this.isBuffer, this.error);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public long getBufferSize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 1) != 0) {
                    return arg0Value.getBufferSize(this.isBuffer, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getBufferSizeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long getBufferSizeNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 1;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.getBufferSize(this.isBuffer, this.error);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 2) != 0) {
                    return arg0Value.readBufferByte(arg1Value, this.isBuffer, this.error, this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferByteNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private byte readBufferByteNode_AndSpecialize(HostObject arg0Value, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 2;
                    lock.unlock();
                    hasLock = false;
                    byte by = arg0Value.readBufferByte(arg1Value, this.isBuffer, this.error, this.classProfile);
                    return by;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 4) != 0) {
                    arg0Value.writeBufferByte(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferByteNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferByteNode_AndSpecialize(HostObject arg0Value, long arg1Value, byte arg2Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 4;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferByte(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 8) != 0) {
                    return arg0Value.readBufferShort(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private short readBufferShortNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 8;
                    lock.unlock();
                    hasLock = false;
                    short s = arg0Value.readBufferShort(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                    return s;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x10) != 0) {
                    arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferShortNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, short arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x20) != 0) {
                    return arg0Value.readBufferInt(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private int readBufferIntNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = arg0Value.readBufferInt(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                    return n;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x40) != 0) {
                    arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferIntNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, int arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x80) != 0) {
                    return arg0Value.readBufferLong(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long readBufferLongNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.readBufferLong(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x100) != 0) {
                    arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferLongNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, long arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x200) != 0) {
                    return arg0Value.readBufferFloat(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private float readBufferFloatNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    float f = arg0Value.readBufferFloat(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                    return f;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x400) != 0) {
                    arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferFloatNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, float arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x800) != 0) {
                    return arg0Value.readBufferDouble(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private double readBufferDoubleNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    double d = arg0Value.readBufferDouble(arg1Value, arg2Value, this.isBuffer, this.error, this.classProfile);
                    return d;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x1000) != 0) {
                    arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferDoubleNode_AndSpecialize(HostObject arg0Value, ByteOrder arg1Value, long arg2Value, double arg3Value) throws InvalidBufferOffsetException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.isBuffer = super.insert(this.isBuffer == null ? HostObjectFactory.IsBufferNodeGen.create() : this.isBuffer);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.isBuffer, this.error, this.classProfile);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isNull(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isNull();
            }

            @Override
            public boolean isExecutable(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x2000) != 0) {
                    return arg0Value.isExecutable(this.lookupFunctionalMethod);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isExecutableNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isExecutableNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.lookupFunctionalMethod = super.insert(this.lookupFunctionalMethod == null ? HostObjectFactory.LookupFunctionalMethodNodeGen.create() : this.lookupFunctionalMethod);
                    this.state_2_ = state_2 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isExecutable(this.lookupFunctionalMethod);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x4000) != 0) {
                    return arg0Value.execute(arg1Value, this.hostExecute, this.lookupFunctionalMethod, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeNode_AndSpecialize(HostObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.hostExecute = super.insert(this.hostExecute == null ? HostExecuteNode.create() : this.hostExecute);
                    this.lookupFunctionalMethod = super.insert(this.lookupFunctionalMethod == null ? HostObjectFactory.LookupFunctionalMethodNodeGen.create() : this.lookupFunctionalMethod);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.execute(arg1Value, this.hostExecute, this.lookupFunctionalMethod, this.error);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isNumber(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x8000) != 0) {
                    return arg0Value.isNumber(this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isNumberNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isNumberNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x8000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isNumber(this.classProfile);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean fitsInByte(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x10000) != 0) {
                    Cached fitsInByteNode__fitsInByte_thisLibrary__ = this;
                    return arg0Value.fitsInByte(fitsInByteNode__fitsInByte_thisLibrary__, this.numbers);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.fitsInByteNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean fitsInByteNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached fitsInByteNode__fitsInByte_thisLibrary__ = null;
                    fitsInByteNode__fitsInByte_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.state_2_ = state_2 |= 0x10000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.fitsInByte(fitsInByteNode__fitsInByte_thisLibrary__, this.numbers);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean fitsInShort(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x20000) != 0) {
                    Cached fitsInShortNode__fitsInShort_thisLibrary__ = this;
                    return arg0Value.fitsInShort(fitsInShortNode__fitsInShort_thisLibrary__, this.numbers);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.fitsInShortNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean fitsInShortNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached fitsInShortNode__fitsInShort_thisLibrary__ = null;
                    fitsInShortNode__fitsInShort_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.state_2_ = state_2 |= 0x20000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.fitsInShort(fitsInShortNode__fitsInShort_thisLibrary__, this.numbers);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean fitsInInt(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x40000) != 0) {
                    Cached fitsInIntNode__fitsInInt_thisLibrary__ = this;
                    return arg0Value.fitsInInt(fitsInIntNode__fitsInInt_thisLibrary__, this.numbers);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.fitsInIntNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean fitsInIntNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached fitsInIntNode__fitsInInt_thisLibrary__ = null;
                    fitsInIntNode__fitsInInt_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.state_2_ = state_2 |= 0x40000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.fitsInInt(fitsInIntNode__fitsInInt_thisLibrary__, this.numbers);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean fitsInLong(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x80000) != 0) {
                    Cached fitsInLongNode__fitsInLong_thisLibrary__ = this;
                    return arg0Value.fitsInLong(fitsInLongNode__fitsInLong_thisLibrary__, this.numbers);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.fitsInLongNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean fitsInLongNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached fitsInLongNode__fitsInLong_thisLibrary__ = null;
                    fitsInLongNode__fitsInLong_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.state_2_ = state_2 |= 0x80000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.fitsInLong(fitsInLongNode__fitsInLong_thisLibrary__, this.numbers);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean fitsInFloat(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x100000) != 0) {
                    Cached fitsInFloatNode__fitsInFloat_thisLibrary__ = this;
                    return arg0Value.fitsInFloat(fitsInFloatNode__fitsInFloat_thisLibrary__, this.numbers);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.fitsInFloatNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean fitsInFloatNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached fitsInFloatNode__fitsInFloat_thisLibrary__ = null;
                    fitsInFloatNode__fitsInFloat_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.state_2_ = state_2 |= 0x100000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.fitsInFloat(fitsInFloatNode__fitsInFloat_thisLibrary__, this.numbers);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean fitsInDouble(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x200000) != 0) {
                    Cached fitsInDoubleNode__fitsInDouble_thisLibrary__ = this;
                    return arg0Value.fitsInDouble(fitsInDoubleNode__fitsInDouble_thisLibrary__, this.numbers);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.fitsInDoubleNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean fitsInDoubleNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached fitsInDoubleNode__fitsInDouble_thisLibrary__ = null;
                    fitsInDoubleNode__fitsInDouble_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.state_2_ = state_2 |= 0x200000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.fitsInDouble(fitsInDoubleNode__fitsInDouble_thisLibrary__, this.numbers);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public byte asByte(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x400000) != 0) {
                    Cached asByteNode__asByte_thisLibrary__ = this;
                    return arg0Value.asByte(asByteNode__asByte_thisLibrary__, this.numbers, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asByteNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private byte asByteNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached asByteNode__asByte_thisLibrary__ = null;
                    asByteNode__asByte_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x400000;
                    lock.unlock();
                    hasLock = false;
                    byte by = arg0Value.asByte(asByteNode__asByte_thisLibrary__, this.numbers, this.error);
                    return by;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public short asShort(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x800000) != 0) {
                    Cached asShortNode__asShort_thisLibrary__ = this;
                    return arg0Value.asShort(asShortNode__asShort_thisLibrary__, this.numbers, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asShortNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private short asShortNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached asShortNode__asShort_thisLibrary__ = null;
                    asShortNode__asShort_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x800000;
                    lock.unlock();
                    hasLock = false;
                    short s = arg0Value.asShort(asShortNode__asShort_thisLibrary__, this.numbers, this.error);
                    return s;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public int asInt(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x1000000) != 0) {
                    Cached asIntNode__asInt_thisLibrary__ = this;
                    return arg0Value.asInt(asIntNode__asInt_thisLibrary__, this.numbers, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asIntNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private int asIntNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached asIntNode__asInt_thisLibrary__ = null;
                    asIntNode__asInt_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x1000000;
                    lock.unlock();
                    hasLock = false;
                    int n = arg0Value.asInt(asIntNode__asInt_thisLibrary__, this.numbers, this.error);
                    return n;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public long asLong(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x2000000) != 0) {
                    Cached asLongNode__asLong_thisLibrary__ = this;
                    return arg0Value.asLong(asLongNode__asLong_thisLibrary__, this.numbers, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asLongNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long asLongNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached asLongNode__asLong_thisLibrary__ = null;
                    asLongNode__asLong_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x2000000;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.asLong(asLongNode__asLong_thisLibrary__, this.numbers, this.error);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public float asFloat(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x4000000) != 0) {
                    Cached asFloatNode__asFloat_thisLibrary__ = this;
                    return arg0Value.asFloat(asFloatNode__asFloat_thisLibrary__, this.numbers, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asFloatNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private float asFloatNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached asFloatNode__asFloat_thisLibrary__ = null;
                    asFloatNode__asFloat_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x4000000;
                    lock.unlock();
                    hasLock = false;
                    float f = arg0Value.asFloat(asFloatNode__asFloat_thisLibrary__, this.numbers, this.error);
                    return f;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public double asDouble(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x8000000) != 0) {
                    Cached asDoubleNode__asDouble_thisLibrary__ = this;
                    return arg0Value.asDouble(asDoubleNode__asDouble_thisLibrary__, this.numbers, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asDoubleNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private double asDoubleNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached asDoubleNode__asDouble_thisLibrary__ = null;
                    asDoubleNode__asDouble_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x8000000;
                    lock.unlock();
                    hasLock = false;
                    double d = arg0Value.asDouble(asDoubleNode__asDouble_thisLibrary__, this.numbers, this.error);
                    return d;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isString(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x10000000) != 0) {
                    return arg0Value.isString(this.classProfile);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isStringNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isStringNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.classProfile = this.classProfile == null ? ValueProfile.createClassProfile() : this.classProfile;
                    this.state_2_ = state_2 |= 0x10000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isString(this.classProfile);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public String asString(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x20000000) != 0) {
                    Cached asStringNode__asString_thisLibrary__ = this;
                    return arg0Value.asString(asStringNode__asString_thisLibrary__, this.numbers, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asStringNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private String asStringNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    Cached asStringNode__asString_thisLibrary__ = null;
                    asStringNode__asString_thisLibrary__ = this;
                    this.numbers = super.insert(this.numbers == null ? INTEROP_LIBRARY_.createDispatched(5) : this.numbers);
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x20000000;
                    lock.unlock();
                    hasLock = false;
                    String string = arg0Value.asString(asStringNode__asString_thisLibrary__, this.numbers, this.error);
                    return string;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isBoolean(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isBoolean();
            }

            @Override
            public boolean asBoolean(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & 0x40000000) != 0) {
                    return arg0Value.asBoolean(this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.asBooleanNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean asBooleanNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= 0x40000000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.asBoolean(this.error);
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
                return ((HostObject)receiver).isDate();
            }

            @Override
            public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).asDate();
            }

            @Override
            public boolean isTime(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isTime();
            }

            @Override
            public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).asTime();
            }

            @Override
            public boolean isTimeZone(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isTimeZone();
            }

            @Override
            public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).asTimeZone();
            }

            @Override
            public Instant asInstant(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).asInstant();
            }

            @Override
            public boolean isDuration(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isDuration();
            }

            @Override
            public Duration asDuration(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).asDuration();
            }

            @Override
            public boolean isException(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isException();
            }

            @Override
            public ExceptionType getExceptionType(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_2 = this.state_2_;
                if ((state_2 & Integer.MIN_VALUE) != 0) {
                    return arg0Value.getExceptionType(this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getExceptionTypeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private ExceptionType getExceptionTypeNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_2 = this.state_2_;
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_2_ = state_2 |= Integer.MIN_VALUE;
                    lock.unlock();
                    hasLock = false;
                    ExceptionType exceptionType = arg0Value.getExceptionType(this.error);
                    return exceptionType;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isExceptionIncompleteSource(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 1) != 0) {
                    return arg0Value.isExceptionIncompleteSource(this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isExceptionIncompleteSourceNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isExceptionIncompleteSourceNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_3_ = state_3 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isExceptionIncompleteSource(this.error);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public int getExceptionExitStatus(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 2) != 0) {
                    return arg0Value.getExceptionExitStatus(this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getExceptionExitStatusNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private int getExceptionExitStatusNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_3_ = state_3 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = arg0Value.getExceptionExitStatus(this.error);
                    return n;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasExceptionMessage(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).hasExceptionMessage();
            }

            @Override
            public Object getExceptionMessage(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 4) != 0) {
                    return arg0Value.getExceptionMessage(this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getExceptionMessageNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getExceptionMessageNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_3_ = state_3 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getExceptionMessage(this.error);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasExceptionCause(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).hasExceptionCause();
            }

            @Override
            public Object getExceptionCause(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getExceptionCause();
            }

            @Override
            public boolean hasExceptionStackTrace(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).hasExceptionStackTrace();
            }

            @Override
            public Object getExceptionStackTrace(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getExceptionStackTrace();
            }

            @Override
            public RuntimeException throwException(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 8) != 0) {
                    return arg0Value.throwException(this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.throwExceptionNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private RuntimeException throwExceptionNode_AndSpecialize(HostObject arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_3_ = state_3 |= 8;
                    lock.unlock();
                    hasLock = false;
                    RuntimeException runtimeException = arg0Value.throwException(this.error);
                    return runtimeException;
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
                return ((HostObject)receiver).hasLanguage();
            }

            @Override
            public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getLanguage();
            }

            @Override
            public Object toDisplayString(Object receiver, boolean allowSideEffects) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).toDisplayString(allowSideEffects);
            }

            @Override
            public boolean hasIterator(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x10) != 0) {
                    return arg0Value.hasIterator(this.isIterable, this.isArray);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasIteratorNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasIteratorNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.isIterable = super.insert(this.isIterable == null ? HostObjectFactory.IsIterableNodeGen.create() : this.isIterable);
                    this.isArray = super.insert(this.isArray == null ? HostObjectFactory.IsArrayNodeGen.create() : this.isArray);
                    this.state_3_ = state_3 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasIterator(this.isIterable, this.isArray);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isIterator(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x20) != 0) {
                    return arg0Value.isIterator(this.isIterator);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isIteratorNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isIteratorNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.isIterator = super.insert(this.isIterator == null ? HostObjectFactory.IsIteratorNodeGen.create() : this.isIterator);
                    this.state_3_ = state_3 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isIterator(this.isIterator);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasHashEntries(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x40) != 0) {
                    return arg0Value.hasHashEntries(this.isMap);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasHashEntriesNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasHashEntriesNode_AndSpecialize(HostObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.isMap = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                    this.state_3_ = state_3 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasHashEntries(this.isMap);
                    return bl;
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x80) != 0) {
                    return arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryReadableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isHashEntryReadableNode_AndSpecialize(HostObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.isMap = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                    this.containsKey = super.insert(this.containsKey == null ? HostObjectFactory.ContainsKeyNodeGen.create() : this.containsKey);
                    this.state_3_ = state_3 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
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
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x80) != 0) {
                    return arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryReadableNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x80) != 0) {
                    return arg0Value.isHashEntryReadable(arg1Value, this.isMap, this.containsKey);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryReadableNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x100) != 0) {
                    return arg0Value.isHashEntryInsertable(arg1Value, this.isMap, this.containsKey);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isHashEntryInsertableNode_AndSpecialize(HostObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    this.isMap = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                    this.containsKey = super.insert(this.containsKey == null ? HostObjectFactory.ContainsKeyNodeGen.create() : this.containsKey);
                    this.state_3_ = state_3 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isHashEntryInsertable(arg1Value, this.isMap, this.containsKey);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).hasMetaObject();
            }

            @Override
            public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getMetaObject();
            }

            @Override
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).isMetaObject();
            }

            @Override
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getMetaQualifiedName();
            }

            @Override
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getMetaSimpleName();
            }

            @Override
            public boolean isMetaInstance(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostObject arg0Value = (HostObject)arg0Value_;
                int state_3 = this.state_3_;
                if ((state_3 & 0x200) != 0) {
                    Cached isMetaInstanceNode__isMetaInstance_library__ = this;
                    return arg0Value.isMetaInstance(arg1Value, isMetaInstanceNode__isMetaInstance_library__, this.error);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMetaInstanceNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMetaInstanceNode_AndSpecialize(HostObject arg0Value, Object arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_3 = this.state_3_;
                    Cached isMetaInstanceNode__isMetaInstance_library__ = null;
                    isMetaInstanceNode__isMetaInstance_library__ = this;
                    this.error = this.error == null ? BranchProfile.create() : this.error;
                    this.state_3_ = state_3 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMetaInstance(arg1Value, isMetaInstanceNode__isMetaInstance_library__, this.error);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMetaParents(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).hasMetaParents();
            }

            @Override
            public Object getMetaParents(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostObject)receiver).getMetaParents();
            }

            @Override
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return HostObject.identityHashCode((HostObject)receiver);
            }

            private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_1, HostObject arg0Value, Object arg1Value) {
                return (state_1 & 0x800000) != 0 || !(arg1Value instanceof HostObject);
            }

            @GeneratedBy(value=HostObject.class)
            private static final class IsMemberInvocableCachedData {
                @CompilerDirectives.CompilationFinal
                IsMemberInvocableCachedData next_;
                @CompilerDirectives.CompilationFinal
                boolean cachedStatic_;
                @CompilerDirectives.CompilationFinal
                Class<?> cachedClazz_;
                @CompilerDirectives.CompilationFinal
                String cachedName_;
                @CompilerDirectives.CompilationFinal
                boolean cachedInvokable_;

                IsMemberInvocableCachedData(IsMemberInvocableCachedData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=HostObject.class)
            private static final class IsMemberInternalCachedData {
                @CompilerDirectives.CompilationFinal
                IsMemberInternalCachedData next_;
                @CompilerDirectives.CompilationFinal
                boolean cachedStatic_;
                @CompilerDirectives.CompilationFinal
                Class<?> cachedClazz_;
                @CompilerDirectives.CompilationFinal
                String cachedName_;
                @CompilerDirectives.CompilationFinal
                boolean cachedInternal_;

                IsMemberInternalCachedData(IsMemberInternalCachedData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=HostObject.class)
            private static final class IsMemberModifiableCachedData {
                @CompilerDirectives.CompilationFinal
                IsMemberModifiableCachedData next_;
                @CompilerDirectives.CompilationFinal
                boolean cachedStatic_;
                @CompilerDirectives.CompilationFinal
                Class<?> cachedClazz_;
                @CompilerDirectives.CompilationFinal
                String cachedName_;
                @CompilerDirectives.CompilationFinal
                boolean cachedModifiable_;

                IsMemberModifiableCachedData(IsMemberModifiableCachedData next_) {
                    this.next_ = next_;
                }
            }

            @GeneratedBy(value=HostObject.class)
            private static final class IsMemberReadableCachedData {
                @CompilerDirectives.CompilationFinal
                IsMemberReadableCachedData next_;
                @CompilerDirectives.CompilationFinal
                boolean cachedStatic_;
                @CompilerDirectives.CompilationFinal
                Class<?> cachedClazz_;
                @CompilerDirectives.CompilationFinal
                String cachedName_;
                @CompilerDirectives.CompilationFinal
                boolean cachedReadable_;

                IsMemberReadableCachedData(IsMemberReadableCachedData next_) {
                    this.next_ = next_;
                }
            }
        }
    }
}

