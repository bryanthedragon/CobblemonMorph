
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropException;

public final class ArityException
extends InteropException {
    private static final long serialVersionUID = 1857745390734085182L;
    private final int expectedMinArity;
    private final int expectedMaxArity;
    private final int actualArity;

    private ArityException(int expectedMinArity, int expectedMaxArity, int actualArity, Throwable cause) {
        super(null, cause);
        this.expectedMinArity = expectedMinArity;
        this.expectedMaxArity = expectedMaxArity;
        this.actualArity = actualArity;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String getMessage() {
        String given = this.actualArity < 0 ? "unknown" : String.valueOf(this.actualArity);
        Object expected = this.expectedMinArity == this.expectedMaxArity ? String.valueOf(this.expectedMinArity) : (this.expectedMaxArity < 0 ? this.expectedMinArity + "+" : this.expectedMinArity + "-" + this.expectedMaxArity);
        return String.format("Arity error - expected: %s actual: %s", expected, given);
    }

    public int getExpectedMinArity() {
        return this.expectedMinArity;
    }

    public int getExpectedMaxArity() {
        return this.expectedMaxArity;
    }

    public int getActualArity() {
        return this.actualArity;
    }

    public static ArityException create(int expectedMinArity, int expectedMaxArity, int actualArity) {
        assert (ArityException.validateArity(expectedMinArity, expectedMaxArity, actualArity));
        return new ArityException(expectedMinArity, expectedMaxArity, actualArity, null);
    }

    public static ArityException create(int expectedMinArity, int expectedMaxArity, int actualArity, Throwable cause) {
        assert (ArityException.validateArity(expectedMinArity, expectedMaxArity, actualArity));
        return new ArityException(expectedMinArity, expectedMaxArity, actualArity, cause);
    }

    private static boolean validateArity(int expectedMinArity, int expectedMaxArity, int actualArity) {
        if (expectedMinArity < 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Expected min arity must be greater or equal to zero.");
        }
        if (expectedMaxArity >= 0) {
            if (expectedMaxArity < expectedMinArity) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalArgumentException("Expected max arity must be greater or equal to min arity.");
            }
            if (actualArity >= 0 && actualArity >= expectedMinArity && actualArity <= expectedMaxArity) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalArgumentException("Actual arity is in valid arity range.");
            }
        } else if (actualArity >= 0 && actualArity >= expectedMinArity) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Actual arity is in valid arity range.");
        }
        return true;
    }
}

