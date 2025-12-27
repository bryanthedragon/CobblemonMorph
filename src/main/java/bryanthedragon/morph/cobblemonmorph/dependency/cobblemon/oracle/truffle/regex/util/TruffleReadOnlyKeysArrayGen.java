package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.regex.AbstractRegexObjectGen;

@GeneratedBy(TruffleReadOnlyKeysArray.class)
public final class TruffleReadOnlyKeysArrayGen {
   private TruffleReadOnlyKeysArrayGen() {
   }

   static {
      LibraryExport.register(TruffleReadOnlyKeysArray.class, new TruffleReadOnlyKeysArrayGen.InteropLibraryExports());
   }

   @GeneratedBy(TruffleReadOnlyKeysArray.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, TruffleReadOnlyKeysArray.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof TruffleReadOnlyKeysArray;

         InteropLibrary uncached = new TruffleReadOnlyKeysArrayGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof TruffleReadOnlyKeysArray;

         return new TruffleReadOnlyKeysArrayGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(TruffleReadOnlyKeysArray.class)
      public static class Cached extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyKeysArray)receiver).hasArrayElements();
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyKeysArray)receiver).isArrayElementReadable(index);
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyKeysArray)receiver).getArraySize();
         }

         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyKeysArray)receiver).readArrayElement(index);
         }
      }

      @GeneratedBy(TruffleReadOnlyKeysArray.class)
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

            return ((TruffleReadOnlyKeysArray)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyKeysArray)receiver).isArrayElementReadable(index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyKeysArray)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((TruffleReadOnlyKeysArray)receiver).readArrayElement(index);
         }
      }
   }
}
