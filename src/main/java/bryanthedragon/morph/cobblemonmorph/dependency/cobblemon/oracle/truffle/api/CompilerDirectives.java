
package com.oracle.truffle.api;

import com.oracle.truffle.api.Truffle;
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

    public static boolean isCompilationConstant(Object value2) {
        return CompilerDirectives.inInterpreter();
    }

    public static boolean isPartialEvaluationConstant(Object value2) {
        return CompilerDirectives.inInterpreter();
    }

    public static void interpreterOnly(Runnable runnable) {
        runnable.run();
    }

    public static <T> T interpreterOnly(Callable<T> callable) throws Exception {
        return callable.call();
    }

    public static boolean injectBranchProbability(double probability, boolean condition2) {
        assert (probability >= 0.0 && probability <= 1.0);
        return condition2;
    }

    public static void bailout(String reason) {
    }

    public static void materialize(Object obj) {
    }

    public static void ensureVirtualized(Object object) {
    }

    public static void ensureVirtualizedHere(Object object) {
    }

    public static void blackhole(boolean value2) {
    }

    public static void blackhole(byte value2) {
    }

    public static void blackhole(short value2) {
    }

    public static void blackhole(char value2) {
    }

    public static void blackhole(int value2) {
    }

    public static void blackhole(long value2) {
    }

    public static void blackhole(float value2) {
    }

    public static void blackhole(double value2) {
    }

    public static void blackhole(Object value2) {
    }

    public static <T> T castExact(Object object, Class<T> clazz) {
        Objects.requireNonNull(clazz);
        if (object == null || object.getClass() == clazz) {
            return (T)object;
        }
        throw new ClassCastException();
    }

    public static boolean isExact(Object object, Class<?> clazz) {
        Objects.requireNonNull(clazz);
        if (object == null) {
            return false;
        }
        return object.getClass() == clazz;
    }

    public static RuntimeException shouldNotReachHere() {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw CompilerDirectives.shouldNotReachHere(null, null);
    }

    public static RuntimeException shouldNotReachHere(String message) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw CompilerDirectives.shouldNotReachHere(message, null);
    }

    public static RuntimeException shouldNotReachHere(Throwable cause) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw CompilerDirectives.shouldNotReachHere(null, cause);
    }

    public static RuntimeException shouldNotReachHere(String message, Throwable cause) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new ShouldNotReachHere(message, cause);
    }

    static final class ShouldNotReachHere
    extends AssertionError {
        ShouldNotReachHere(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface ValueType {
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.METHOD, ElementType.CONSTRUCTOR})
    public static @interface TruffleBoundary {
        public boolean transferToInterpreterOnException() default true;

        public boolean allowInlining() default false;
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface CompilationFinal {
        public int dimensions() default -1;
    }
}

