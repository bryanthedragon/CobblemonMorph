package com.oracle.truffle.js.runtime.util;

import java.util.function.BiFunction;

@FunctionalInterface
public interface CompilableBiFunction<T, U, R> extends BiFunction<T, U, R> {
}
