package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(DefaultNodeExports.DefaultScope.class)
final class DefaultScopeGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultScopeGen() {
   }

   static {
      LibraryExport.register(DefaultNodeExports.DefaultScope.class, new DefaultScopeGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultNodeExports.DefaultScope.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultScopeGen.InteropLibraryExports.Uncached UNCACHED = new DefaultScopeGen.InteropLibraryExports.Uncached();
      private static final DefaultScopeGen.InteropLibraryExports.Cached CACHE = new DefaultScopeGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, DefaultNodeExports.DefaultScope.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DefaultNodeExports.DefaultScope;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DefaultNodeExports.DefaultScope;

         return CACHE;
      }

      @GeneratedBy(DefaultNodeExports.DefaultScope.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultNodeExports.DefaultScope) || DefaultScopeGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultNodeExports.DefaultScope;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).getLanguage();
         }

         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isScope();
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).hasMembers();
         }

         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).readMember(member);
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).getMembers(includeInternal);
         }

         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isMemberReadable(member);
         }

         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isMemberModifiable(member);
         }

         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((DefaultNodeExports.DefaultScope)receiver).writeMember(member, value);
         }

         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isMemberInsertable(member);
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).hasSourceLocation();
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).getSourceLocation();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).toDisplayString(allowSideEffects);
         }
      }

      @GeneratedBy(DefaultNodeExports.DefaultScope.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultNodeExports.DefaultScope) || DefaultScopeGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultNodeExports.DefaultScope;
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
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isScope(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isScope();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).readMember(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isMemberReadable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isMemberModifiable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((DefaultNodeExports.DefaultScope)receiver).writeMember(member, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).isMemberInsertable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).hasSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).getSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScope)receiver).toDisplayString(allowSideEffects);
         }
      }
   }
}
