package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.regex.AbstractRegexObjectGen;

@GeneratedBy(TruffleReadOnlyMap.class)
final class TruffleReadOnlyMapGen {
   private TruffleReadOnlyMapGen() {
   }

   static {
      LibraryExport.register(TruffleReadOnlyMap.class, new TruffleReadOnlyMapGen.InteropLibraryExports());
   }

   @GeneratedBy(TruffleReadOnlyMap.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, TruffleReadOnlyMap.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof TruffleReadOnlyMap;

         InteropLibrary uncached = new TruffleReadOnlyMapGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof TruffleReadOnlyMap;

         return new TruffleReadOnlyMapGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(TruffleReadOnlyMap.class)
      private static final class Cached extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).hasMembers();
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).getMembers(includeInternal);
         }

         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).isMemberReadable(member);
         }

         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).readMember(member);
         }
      }

      @GeneratedBy(TruffleReadOnlyMap.class)
      @DenyReplace
      private static final class Uncached extends AbstractRegexObjectGen.InteropLibraryExports.Uncached {
         protected Uncached(Object receiver) {
            super(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return super.accepts(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).isMemberReadable(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyMap)receiver).readMember(member);
         }
      }
   }
}
