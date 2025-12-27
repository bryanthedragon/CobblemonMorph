package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;

@GenerateLibrary
@GenerateLibrary.DefaultExport(ReflectionLibraryDefault.class)
public abstract class ReflectionLibrary extends Library {
   private static final LibraryFactory<ReflectionLibrary> FACTORY = LibraryFactory.resolve(ReflectionLibrary.class);
   static final ReflectionLibrary UNCACHED = FACTORY.getUncached();

   protected ReflectionLibrary() {
   }

   @CompilerDirectives.TruffleBoundary
   @GenerateLibrary.Abstract
   public Object send(Object receiver, Message message, Object... args) throws Exception {
      throw new AbstractMethodError();
   }

   public static LibraryFactory<ReflectionLibrary> getFactory() {
      return FACTORY;
   }

   public static ReflectionLibrary getUncached() {
      return UNCACHED;
   }

   public static ReflectionLibrary getUncached(Object v) {
      return FACTORY.getUncached(v);
   }
}
