
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.polyglot.PolyglotBindings;

@GeneratedBy(value=PolyglotBindings.class)
final class PolyglotBindingsGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private PolyglotBindingsGen() {
    }

    static {
        LibraryExport.register(PolyglotBindings.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=PolyglotBindings.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private static final Uncached UNCACHED = new Uncached();
        private static final Cached CACHE = new Cached();

        private InteropLibraryExports() {
            super(InteropLibrary.class, PolyglotBindings.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof PolyglotBindings);
            Uncached uncached = UNCACHED;
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof PolyglotBindings);
            return CACHE;
        }

        @GeneratedBy(value=PolyglotBindings.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof PolyglotBindings) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof PolyglotBindings;
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
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).readMember(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeMember(Object receiver, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((PolyglotBindings)receiver).writeMember(member, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((PolyglotBindings)receiver).removeMember(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).getMembers(includeInternal);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberExisting(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberModifiable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberExisting(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberRemovable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberExisting(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberInsertable(member);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isIdenticalOrUndefined(other);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).identityHashCode();
            }
        }

        @GeneratedBy(value=PolyglotBindings.class)
        private static final class Cached
        extends InteropLibrary {
            protected Cached() {
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof PolyglotBindings) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof PolyglotBindings;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).hasMembers();
            }

            @Override
            public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).readMember(member);
            }

            @Override
            public void writeMember(Object receiver, String member, Object value2) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((PolyglotBindings)receiver).writeMember(member, value2);
            }

            @Override
            public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                ((PolyglotBindings)receiver).removeMember(member);
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).getMembers(includeInternal);
            }

            @Override
            public boolean isMemberReadable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberExisting(member);
            }

            @Override
            public boolean isMemberModifiable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberExisting(member);
            }

            @Override
            public boolean isMemberRemovable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberExisting(member);
            }

            @Override
            public boolean isMemberInsertable(Object receiver, String member) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isMemberInsertable(member);
            }

            @Override
            protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).isIdenticalOrUndefined(other);
            }

            @Override
            public int identityHashCode(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((PolyglotBindings)receiver).identityHashCode();
            }
        }
    }
}

