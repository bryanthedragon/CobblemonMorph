package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;

@GeneratedBy(JSNonProxyObject.class)
public final class JSNonProxyObjectGen {
   private JSNonProxyObjectGen() {
   }

   static {
      LibraryExport.register(JSNonProxyObject.class, new JSNonProxyObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSNonProxyObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSNonProxyObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSNonProxyObject;

         InteropLibrary uncached = new JSNonProxyObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSNonProxyObject;

         return new JSNonProxyObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSNonProxyObject.class)
      public static class Cached extends JSObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSNonProxyObject)receiver).hasMetaObject();
         }

         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSNonProxyObject)receiver).getMetaObject();
         }
      }

      @GeneratedBy(JSNonProxyObject.class)
      public static class Uncached extends JSObjectGen.InteropLibraryExports.Uncached {
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
         public boolean hasMetaObject(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSNonProxyObject)receiver).hasMetaObject();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMetaObject(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSNonProxyObject)receiver).getMetaObject();
         }
      }
   }
}
