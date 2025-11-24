
package com.oracle.truffle.api;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.InvalidAssumptionException;

public interface Assumption {
    public static final Assumption ALWAYS_VALID = Assumption.createAlwaysValid();
    public static final Assumption NEVER_VALID = Assumption.createNeverValid();

    private static Assumption createNeverValid() {
        Assumption assumption = Assumption.create("<never valid>");
        assumption.invalidate();
        return assumption;
    }

    private static Assumption createAlwaysValid() {
        return LanguageAccessor.RUNTIME.createAlwaysValidAssumption();
    }

    public void check() throws InvalidAssumptionException;

    public boolean isValid();

    public void invalidate();

    default public void invalidate(String message) {
        this.invalidate();
    }

    public String getName();

    public static boolean isValidAssumption(Assumption assumption) {
        return assumption != null && assumption.isValid();
    }

    @ExplodeLoop
    public static boolean isValidAssumption(Assumption[] assumptions) {
        CompilerAsserts.partialEvaluationConstant(assumptions);
        if (assumptions == null) {
            return false;
        }
        for (Assumption assumption : assumptions) {
            if (Assumption.isValidAssumption(assumption)) continue;
            return false;
        }
        return true;
    }

    public static Assumption create() {
        return Truffle.getRuntime().createAssumption();
    }

    public static Assumption create(String name) {
        return Truffle.getRuntime().createAssumption(name);
    }
}

