package com.oracle.truffle.api.library;

@GenerateLibrary(dynamicDispatchEnabled = false)
public abstract class DynamicDispatchLibrary extends Library {
   static final LibraryFactory<DynamicDispatchLibrary> FACTORY = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   protected DynamicDispatchLibrary() {
   }

   @GenerateLibrary.Abstract
   public Class<?> dispatch(Object receiver) {
      return null;
   }

   public abstract Object cast(Object receiver);

   public static LibraryFactory<DynamicDispatchLibrary> getFactory() {
      return FACTORY;
   }
}
