
package com.oracle.truffle.api;

import com.oracle.truffle.api.CompilerDirectives;

public final class CompilerAsserts {
    private CompilerAsserts() {
    }

    public static void neverPartOfCompilation() {
    }

    public static void neverPartOfCompilation(String message) {
    }

    public static <T> void compilationConstant(Object value2) {
        if (!CompilerDirectives.isCompilationConstant(value2)) {
            CompilerAsserts.neverPartOfCompilation("Value is not compilation constant");
        }
    }

    public static <T> void partialEvaluationConstant(Object value2) {
    }

    public static <T> void partialEvaluationConstant(boolean value2) {
    }

    public static <T> void partialEvaluationConstant(int value2) {
    }

    public static <T> void partialEvaluationConstant(float value2) {
    }

    public static <T> void partialEvaluationConstant(long value2) {
    }

    public static <T> void partialEvaluationConstant(double value2) {
    }
}

