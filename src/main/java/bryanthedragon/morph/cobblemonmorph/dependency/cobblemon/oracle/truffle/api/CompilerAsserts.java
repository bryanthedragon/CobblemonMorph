package com.oracle.truffle.api;

public final class CompilerAsserts {
   private CompilerAsserts() {
   }

   public static void neverPartOfCompilation() {
   }

   public static void neverPartOfCompilation(String message) {
   }

   public static <T> void compilationConstant(Object value) {
      if (!CompilerDirectives.isCompilationConstant(value)) {
         neverPartOfCompilation("Value is not compilation constant");
      }
   }

   public static <T> void partialEvaluationConstant(Object value) {
   }

   public static <T> void partialEvaluationConstant(boolean value) {
   }

   public static <T> void partialEvaluationConstant(int value) {
   }

   public static <T> void partialEvaluationConstant(float value) {
   }

   public static <T> void partialEvaluationConstant(long value) {
   }

   public static <T> void partialEvaluationConstant(double value) {
   }
}
