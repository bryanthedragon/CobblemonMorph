package org.graalvm.nativeimage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.graalvm.nativeimage.impl.IsolateSupport;
import org.graalvm.word.UnsignedWord;

public final class Isolates {
   private Isolates() {
   }

   public static IsolateThread createIsolate(Isolates.CreateIsolateParameters parameters) throws Isolates.IsolateException {
      return ImageSingletons.lookup(IsolateSupport.class).createIsolate(parameters);
   }

   public static IsolateThread attachCurrentThread(Isolate isolate) throws Isolates.IsolateException {
      return ImageSingletons.lookup(IsolateSupport.class).attachCurrentThread(isolate);
   }

   public static IsolateThread getCurrentThread(Isolate isolate) throws Isolates.IsolateException {
      return ImageSingletons.lookup(IsolateSupport.class).getCurrentThread(isolate);
   }

   public static Isolate getIsolate(IsolateThread thread) throws Isolates.IsolateException {
      return ImageSingletons.lookup(IsolateSupport.class).getIsolate(thread);
   }

   public static void detachThread(IsolateThread thread) throws Isolates.IsolateException {
      ImageSingletons.lookup(IsolateSupport.class).detachThread(thread);
   }

   public static void tearDownIsolate(IsolateThread thread) throws Isolates.IsolateException {
      ImageSingletons.lookup(IsolateSupport.class).tearDownIsolate(thread);
   }

   public static final class CreateIsolateParameters {
      private static final Isolates.CreateIsolateParameters DEFAULT = new Isolates.CreateIsolateParameters.Builder().build();
      private final UnsignedWord reservedAddressSpaceSize;
      private final String auxiliaryImagePath;
      private final UnsignedWord auxiliaryImageReservedSpaceSize;
      private final List<String> arguments;
      private final Isolates.ProtectionDomain protectionDomain;

      public static Isolates.CreateIsolateParameters getDefault() {
         return DEFAULT;
      }

      private CreateIsolateParameters(
         UnsignedWord reservedAddressSpaceSize,
         String auxiliaryImagePath,
         UnsignedWord auxiliaryImageReservedSpaceSize,
         List<String> arguments,
         Isolates.ProtectionDomain protectionDomain
      ) {
         this.reservedAddressSpaceSize = reservedAddressSpaceSize;
         this.auxiliaryImagePath = auxiliaryImagePath;
         this.auxiliaryImageReservedSpaceSize = auxiliaryImageReservedSpaceSize;
         this.arguments = arguments;
         this.protectionDomain = protectionDomain;
      }

      public UnsignedWord getReservedAddressSpaceSize() {
         return this.reservedAddressSpaceSize;
      }

      public String getAuxiliaryImagePath() {
         return this.auxiliaryImagePath;
      }

      public UnsignedWord getAuxiliaryImageReservedSpaceSize() {
         return this.auxiliaryImageReservedSpaceSize;
      }

      public List<String> getArguments() {
         return Collections.unmodifiableList(this.arguments);
      }

      public Isolates.ProtectionDomain getProtectionDomain() {
         return this.protectionDomain;
      }

      public static final class Builder {
         private UnsignedWord reservedAddressSpaceSize;
         private String auxiliaryImagePath;
         private UnsignedWord auxiliaryImageReservedSpaceSize;
         private final List<String> arguments;
         private Isolates.ProtectionDomain protectionDomain = Isolates.ProtectionDomain.NO_DOMAIN;

         public Builder() {
            this.arguments = new ArrayList<>();
         }

         public Isolates.CreateIsolateParameters.Builder reservedAddressSpaceSize(UnsignedWord size) {
            this.reservedAddressSpaceSize = size;
            return this;
         }

         public Isolates.CreateIsolateParameters.Builder auxiliaryImagePath(String filePath) {
            this.auxiliaryImagePath = filePath;
            return this;
         }

         public Isolates.CreateIsolateParameters.Builder auxiliaryImageReservedSpaceSize(UnsignedWord size) {
            this.auxiliaryImageReservedSpaceSize = size;
            return this;
         }

         public Isolates.CreateIsolateParameters.Builder appendArgument(String argument) {
            this.arguments.add(argument);
            return this;
         }

         public Isolates.CreateIsolateParameters.Builder setProtectionDomain(Isolates.ProtectionDomain domain) {
            this.protectionDomain = domain;
            return this;
         }

         public Isolates.CreateIsolateParameters build() {
            return new Isolates.CreateIsolateParameters(
               this.reservedAddressSpaceSize, this.auxiliaryImagePath, this.auxiliaryImageReservedSpaceSize, this.arguments, this.protectionDomain
            );
         }
      }
   }

   public static final class IsolateException extends RuntimeException {
      private static final long serialVersionUID = 1L;

      public IsolateException(String message) {
         super(message);
      }
   }

   public interface ProtectionDomain {
      Isolates.ProtectionDomain NO_DOMAIN = new Isolates.ProtectionDomain() {};
      Isolates.ProtectionDomain NEW_DOMAIN = new Isolates.ProtectionDomain() {};
   }
}
