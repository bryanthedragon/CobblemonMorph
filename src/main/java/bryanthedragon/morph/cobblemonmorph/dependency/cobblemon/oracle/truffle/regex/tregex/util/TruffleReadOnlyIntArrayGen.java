package com.oracle.truffle.regex.tregex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.regex.AbstractRegexObjectGen;

@GeneratedBy(TruffleReadOnlyIntArray.class)
public final class TruffleReadOnlyIntArrayGen {
   private TruffleReadOnlyIntArrayGen() {
   }

   static {
      LibraryExport.register(TruffleReadOnlyIntArray.class, new TruffleReadOnlyIntArrayGen.InteropLibraryExports());
   }

   @GeneratedBy(TruffleReadOnlyIntArray.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, TruffleReadOnlyIntArray.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof TruffleReadOnlyIntArray;

         InteropLibrary uncached = new TruffleReadOnlyIntArrayGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof TruffleReadOnlyIntArray;

         return new TruffleReadOnlyIntArrayGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(TruffleReadOnlyIntArray.class)
      public static class Cached extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).hasArrayElements();
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).isArrayElementReadable(index);
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).getArraySize();
         }

         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).readArrayElement(index);
         }
      }

      @GeneratedBy(TruffleReadOnlyIntArray.class)
      public static class Uncached extends AbstractRegexObjectGen.InteropLibraryExports.Uncached {
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
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).isArrayElementReadable(index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyIntArray)receiver).readArrayElement(index);
         }
      }
   }
}
