package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(DebugScope.SubtractedVariables.class)
final class SubtractedVariablesGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private SubtractedVariablesGen() {
   }

   static {
      LibraryExport.register(DebugScope.SubtractedVariables.class, new SubtractedVariablesGen.InteropLibraryExports());
   }

   @GeneratedBy(DebugScope.SubtractedVariables.class)
   static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, DebugScope.SubtractedVariables.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DebugScope.SubtractedVariables;

         InteropLibrary uncached = new SubtractedVariablesGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DebugScope.SubtractedVariables;

         return new SubtractedVariablesGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(DebugScope.SubtractedVariables.class)
      static class Cached extends InteropLibrary {
         private final Class<? extends DebugScope.SubtractedVariables> receiverClass_;

         protected Cached(Object receiver) {
            DebugScope.SubtractedVariables castReceiver = (DebugScope.SubtractedVariables)receiver;
            this.receiverClass_ = (Class<? extends DebugScope.SubtractedVariables>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || SubtractedVariablesGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).hasMembers();
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).getMembers(includeInternal);
         }

         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).isMemberReadable(member);
         }

         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).readMember(member);
         }

         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).isMemberModifiable(member);
         }

         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).isMemberInsertable(member);
         }

         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            CompilerDirectives.castExact(receiver, this.receiverClass_).writeMember(member, value);
         }

         @Override
         public boolean hasMemberReadSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).hasMemberReadSideEffects(member);
         }

         @Override
         public boolean hasMemberWriteSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return CompilerDirectives.castExact(receiver, this.receiverClass_).hasMemberWriteSideEffects(member);
         }
      }

      @GeneratedBy(DebugScope.SubtractedVariables.class)
      static class Uncached extends InteropLibrary {
         private final Class<? extends DebugScope.SubtractedVariables> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends DebugScope.SubtractedVariables>)((DebugScope.SubtractedVariables)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || SubtractedVariablesGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

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

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).isMemberReadable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).readMember(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberModifiable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).isMemberModifiable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberInsertable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).isMemberInsertable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public void writeMember(Object receiver, String member, Object value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            ((DebugScope.SubtractedVariables)receiver).writeMember(member, value);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberReadSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).hasMemberReadSideEffects(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMemberWriteSideEffects(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedVariables)receiver).hasMemberWriteSideEffects(member);
         }
      }
   }
}
