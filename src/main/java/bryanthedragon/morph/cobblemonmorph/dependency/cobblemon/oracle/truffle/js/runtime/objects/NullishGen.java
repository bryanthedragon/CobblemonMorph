package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;

@GeneratedBy(Nullish.class)
final class NullishGen {
   private NullishGen() {
   }

   static {
      LibraryExport.register(Nullish.class, new NullishGen.InteropLibraryExports());
   }

   @GeneratedBy(Nullish.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, Nullish.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof Nullish;

         InteropLibrary uncached = new NullishGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof Nullish;

         return new NullishGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(Nullish.class)
      private static final class Cached extends JSDynamicObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Nullish)receiver).isNull();
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Nullish)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Nullish)receiver).getLanguage();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Nullish)receiver).toDisplayString(allowSideEffects);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Nullish)receiver).hasMetaObject();
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((Nullish)receiver).getMetaObject();
         }
      }

      @GeneratedBy(Nullish.class)
      @DenyReplace
      private static final class Uncached extends JSDynamicObjectGen.InteropLibraryExports.Uncached {
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
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Nullish)receiver).isNull();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Nullish)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Nullish)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Nullish)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Nullish)receiver).hasMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((Nullish)receiver).getMetaObject();
         }
      }
   }
}
