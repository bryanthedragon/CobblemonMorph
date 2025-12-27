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

@GeneratedBy(PolyglotBindings.class)
final class PolyglotBindingsGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private PolyglotBindingsGen() {
   }

   static {
      LibraryExport.register(PolyglotBindings.class, new PolyglotBindingsGen.InteropLibraryExports());
   }

   @GeneratedBy(PolyglotBindings.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final PolyglotBindingsGen.InteropLibraryExports.Uncached UNCACHED = new PolyglotBindingsGen.InteropLibraryExports.Uncached();
      private static final PolyglotBindingsGen.InteropLibraryExports.Cached CACHE = new PolyglotBindingsGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, PolyglotBindings.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof PolyglotBindings;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof PolyglotBindings;

         return CACHE;
      }

      @GeneratedBy(PolyglotBindings.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof PolyglotBindings) || PolyglotBindingsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof PolyglotBindings;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).hasMembers();
         }

         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).readMember(member);
         }

         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((PolyglotBindings)receiver).writeMember(member, value);
         }

         @Override
         public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((PolyglotBindings)receiver).removeMember(member);
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).getMembers(includeInternal);
         }

         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberExisting(member);
         }

         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberExisting(member);
         }

         @Override
         public boolean isMemberRemovable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberExisting(member);
         }

         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberInsertable(member);
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isIdenticalOrUndefined(other);
         }

         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).identityHashCode();
         }
      }

      @GeneratedBy(PolyglotBindings.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof PolyglotBindings) || PolyglotBindingsGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

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

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).readMember(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((PolyglotBindings)receiver).writeMember(member, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void removeMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((PolyglotBindings)receiver).removeMember(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberExisting(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberExisting(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberRemovable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberExisting(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isMemberInsertable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         protected TriState isIdenticalOrUndefined(Object receiver, Object other) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).isIdenticalOrUndefined(other);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((PolyglotBindings)receiver).identityHashCode();
         }
      }
   }
}
