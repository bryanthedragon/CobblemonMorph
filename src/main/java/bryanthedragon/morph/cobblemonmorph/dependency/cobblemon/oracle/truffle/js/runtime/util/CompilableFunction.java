
package com.oracle.truffle.js.runtime.util;

import java.util.function.Function;

@FunctionalInterface
public interface CompilableFunction<T, R>
extends Function<T, R> {
}

