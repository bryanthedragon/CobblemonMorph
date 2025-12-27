package com.oracle.truffle.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Objects;
import java.util.concurrent.Callable;

public final class CompilerDirectives {
   public static final double LIKELY_PROBABILITY = 0.75;
   public static final double UNLIKELY_PROBABILITY = 0.25;
   public static final double SLOWPATH_PROBABILITY = 1.0E-4;
   public static final double FASTPATH_PROBABILITY = 0.9999;

   private CompilerDirectives() {
   }

   public static void transferToInterpreter() {
      Truffle.getRuntime().notifyTransferToInterpreter();
   }

   public static void transferToInterpreterAndInvalidate() {
      Truffle.getRuntime().notifyTransferToInterpreter();
   }

   public static boolean inInterpreter() {
      return true;
   }

   public static boolean hasNextTier() {
      return true;
   }

   public static boolean inCompiledCode() {
      return false;
   }

   public static boolean inCompilationRoot() {
      return false;
   }

   public static boolean isCompilationConstant(Object value) {
      return inInterpreter();
   }

   public static boolean isPartialEvaluationConstant(Object value) {
      return inInterpreter();
   }

   public static void interpreterOnly(Runnable runnable) {
      runnable.run();
   }

   public static <T> T interpreterOnly(Callable<T> callable) throws Exception {
      return callable.call();
   }

   public static boolean injectBranchProbability(double probability, boolean condition) {
      assert probability >= 0.0 && probability <= 1.0;

      return condition;
   }

   public static void bailout(String reason) {
   }

   public static void materialize(Object obj) {
   }

   public static void ensureVirtualized(Object object) {
   }

   public static void ensureVirtualizedHere(Object object) {
   }

   public static void blackhole(boolean value) {
   }

   public static void blackhole(byte value) {
   }

   public static void blackhole(short value) {
   }

   public static void blackhole(char value) {
   }

   public static void blackhole(int value) {
   }

   public static void blackhole(long value) {
   }

   public static void blackhole(float value) {
   }

   public static void blackhole(double value) {
   }

   public static void blackhole(Object value) {
   }

   public static <T> T castExact(Object object, Class<T> clazz) {
      Objects.requireNonNull(clazz);
      if (object != null && object.getClass() != clazz) {
         throw new ClassCastException();
      } else {
         return (T)object;
      }
   }

   public static boolean isExact(Object object, Class<?> clazz) {
      Objects.requireNonNull(clazz);
      return object == null ? false : object.getClass() == clazz;
   }

   public static RuntimeException shouldNotReachHere() {
      transferToInterpreterAndInvalidate();
      throw shouldNotReachHere(null, null);
   }

   public static RuntimeException shouldNotReachHere(String message) {
      transferToInterpreterAndInvalidate();
      throw shouldNotReachHere(message, null);
   }

   public static RuntimeException shouldNotReachHere(Throwable cause) {
      transferToInterpreterAndInvalidate();
      throw shouldNotReachHere(null, cause);
   }

   public static RuntimeException shouldNotReachHere(String message, Throwable cause) {
      transferToInterpreterAndInvalidate();
      throw new CompilerDirectives.ShouldNotReachHere(message, cause);
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   public @interface CompilationFinal {
      int dimensions() default -1;
   }

   static final class ShouldNotReachHere extends AssertionError {
      ShouldNotReachHere(String message, Throwable cause) {
         super(message, cause);
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
   public @interface TruffleBoundary {
      boolean transferToInterpreterOnException() default true;

      boolean allowInlining() default false;
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   public @interface ValueType {
   }
}
