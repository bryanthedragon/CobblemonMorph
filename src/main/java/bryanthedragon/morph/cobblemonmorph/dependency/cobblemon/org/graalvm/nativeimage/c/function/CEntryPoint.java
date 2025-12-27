package org.graalvm.nativeimage.c.function;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.function.BooleanSupplier;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CEntryPoint {
   String name() default "";

   String[] documentation() default {""};

   Class<? extends CEntryPoint.ExceptionHandler> exceptionHandler() default CEntryPoint.FatalExceptionHandler.class;

   CEntryPoint.Builtin builtin() default CEntryPoint.Builtin.NO_BUILTIN;

   Class<? extends BooleanSupplier> include() default CEntryPoint.AlwaysIncluded.class;

   CEntryPoint.Publish publishAs() default CEntryPoint.Publish.SymbolAndHeader;

   public static final class AlwaysIncluded implements BooleanSupplier {
      private AlwaysIncluded() {
      }

      @Override
      public boolean getAsBoolean() {
         return true;
      }
   }

   public static enum Builtin {
      NO_BUILTIN,
      CREATE_ISOLATE,
      ATTACH_THREAD,
      GET_CURRENT_THREAD,
      GET_ISOLATE,
      DETACH_THREAD,
      TEAR_DOWN_ISOLATE;
   }

   public interface ExceptionHandler {
   }

   public static final class FatalExceptionHandler implements CEntryPoint.ExceptionHandler {
      private FatalExceptionHandler() {
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   public @interface IsolateContext {
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.PARAMETER)
   public @interface IsolateThreadContext {
   }

   public static final class NotIncludedAutomatically implements BooleanSupplier {
      private NotIncludedAutomatically() {
      }

      @Override
      public boolean getAsBoolean() {
         return false;
      }
   }

   public static enum Publish {
      NotPublished,
      SymbolOnly,
      SymbolAndHeader;
   }
}
