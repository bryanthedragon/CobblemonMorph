
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.nodes.RootNode;
import java.io.Closeable;

public abstract class TVMCI {
    protected TVMCI() {
        assert (this.checkCaller());
    }

    private boolean checkCaller() {
        String packageName = this.getClass().getPackage().getName();
        assert (packageName.equals("org.graalvm.compiler.truffle.runtime") || packageName.equals("org.graalvm.graal.truffle") || packageName.equals("com.oracle.graal.truffle") || packageName.equals("com.oracle.truffle.api.impl")) : TVMCI.class.getName() + " subclass is not in trusted package: " + this.getClass().getName();
        return true;
    }

    protected abstract Accessor.RuntimeSupport createRuntimeSupport(Object var1);

    public static class TestAccessor<C extends Closeable, T extends CallTarget> {
        private final Test<C, T> testTvmci;

        protected TestAccessor(Test<C, T> testTvmci) {
            if (!this.getClass().getPackage().getName().equals("com.oracle.truffle.tck")) {
                throw new IllegalStateException();
            }
            this.testTvmci = testTvmci;
        }

        protected final C createTestContext(String testName) {
            return this.testTvmci.createTestContext(testName);
        }

        protected final T createTestCallTarget(C testContext, RootNode testNode) {
            return this.testTvmci.createTestCallTarget(testContext, testNode);
        }

        protected final void finishWarmup(C testContext, T callTarget) {
            this.testTvmci.finishWarmup(testContext, callTarget);
        }
    }

    public static abstract class Test<C extends Closeable, T extends CallTarget> {
        protected abstract C createTestContext(String var1);

        protected abstract T createTestCallTarget(C var1, RootNode var2);

        protected abstract void finishWarmup(C var1, T var2);
    }
}

