package com.oracle.truffle.api.library;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.utilities.FinalBitSet;

public abstract class LibraryExport<T extends Library> {
   static final String GENERATED_CLASS_SUFFIX = "Gen";
   private final Class<?> receiverClass;
   private final Class<T> library;
   private final boolean defaultExport;
   private final boolean aot;
   final int aotPriority;
   Class<?> registerClass;

   protected LibraryExport(Class<? extends T> library, Class<?> receiverClass, boolean defaultExport) {
      this(library, receiverClass, defaultExport, false, 0);
   }

   protected LibraryExport(Class<? extends T> library, Class<?> receiverClass, boolean defaultExport, boolean aot, int aotPriority) {
      this.library = (Class<T>)library;
      this.receiverClass = receiverClass;
      this.defaultExport = defaultExport;
      this.aot = aot;
      this.aotPriority = aotPriority;
   }

   protected abstract T createUncached(Object receiver);

   protected abstract T createCached(Object receiver);

   final boolean isAOT() {
      return this.aot;
   }

   final boolean isDefaultExport() {
      return this.defaultExport;
   }

   final Class<?> getReceiverClass() {
      return this.receiverClass;
   }

   final Class<T> getLibrary() {
      return this.library;
   }

   protected static <T extends Library> T createDelegate(LibraryFactory<T> factory, T delegate) {
      T parent = factory.createDelegate(delegate);
      if (!delegate.isAdoptable()) {
         LibraryAccessor.nodeAccessor().forceAdoption(parent, delegate);
      }

      return parent;
   }

   protected static FinalBitSet createMessageBitSet(LibraryFactory<?> factory, String... messageNames) {
      Message[] messages = new Message[messageNames.length];

      for (int i = 0; i < messageNames.length; i++) {
         messages[i] = factory.nameToMessages.get(messageNames[i]);
      }

      return factory.createMessageBitSet(messages);
   }

   @Override
   public final String toString() {
      return "LibraryExport[" + this.getClass().getAnnotation(GeneratedBy.class).value().getName() + "]";
   }

   public static <T extends Library> void register(Class<?> receiverClass, LibraryExport<?>... libs) {
      LibraryFactory.ResolvedDispatch.register(receiverClass, libs);
   }

   protected interface DelegateExport {
      Object readDelegateExport(Object receiver);

      FinalBitSet getDelegateExportMessages();

      Library getDelegateExportLibrary(Object delegate);
   }
}
