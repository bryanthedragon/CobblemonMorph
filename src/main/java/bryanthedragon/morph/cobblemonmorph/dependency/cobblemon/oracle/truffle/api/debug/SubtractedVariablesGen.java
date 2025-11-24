
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.debug.DebugScope;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(value=DebugScope.SubtractedVariables.class)
final class SubtractedVariablesGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private SubtractedVariablesGen() {
    }

    static {
        LibraryExport.register(DebugScope.SubtractedVariables.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=DebugScope.SubtractedVariables.class)
    static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, DebugScope.SubtractedVariables.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof DebugScope.SubtractedVariables);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof DebugScope.SubtractedVariables);
            return new Cached(receiver);
        }

        @GeneratedBy(value=DebugScope.SubtractedVariables.class)
        static class Uncached
        extends InteropLibrary {
            private final Class<? extends DebugScope.SubtractedVariables> receiverClass_;

            protected Uncached(Object receiver) {
                this.receiverClass_ = ((DebugScope.SubtractedVariables)receiver).getClass();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public final boolean isAdoptable() {
                return false;
            }

            @Override
            public final NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).getMembers(includeInternal);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).isMemberReadable(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).readMember(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberModifiable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).isMemberModifiable(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).isMemberInsertable(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeMember(Object receiver, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((DebugScope.SubtractedVariables)receiver).writeMember(member, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberReadSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).hasMemberReadSideEffects(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberWriteSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((DebugScope.SubtractedVariables)receiver).hasMemberWriteSideEffects(member);
            }
        }

        @GeneratedBy(value=DebugScope.SubtractedVariables.class)
        static class Cached
        extends InteropLibrary {
            private final Class<? extends DebugScope.SubtractedVariables> receiverClass_;

            protected Cached(Object receiver) {
                DebugScope.SubtractedVariables castReceiver = (DebugScope.SubtractedVariables)receiver;
                this.receiverClass_ = castReceiver.getClass();
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (receiver.getClass() != this.receiverClass_ || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).hasMembers();
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).getMembers(includeInternal);
            }

            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).isMemberReadable(member);
            }

            @Override
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).readMember(member);
            }

            @Override
            public boolean isMemberModifiable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).isMemberModifiable(member);
            }

            @Override
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).isMemberInsertable(member);
            }

            @Override
            public void writeMember(Object receiver, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                CompilerDirectives.castExact(receiver, this.receiverClass_).writeMember(member, value2);
            }

            @Override
            public boolean hasMemberReadSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).hasMemberReadSideEffects(member);
            }

            @Override
            public boolean hasMemberWriteSideEffects(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return CompilerDirectives.castExact(receiver, this.receiverClass_).hasMemberWriteSideEffects(member);
            }
        }
    }
}

