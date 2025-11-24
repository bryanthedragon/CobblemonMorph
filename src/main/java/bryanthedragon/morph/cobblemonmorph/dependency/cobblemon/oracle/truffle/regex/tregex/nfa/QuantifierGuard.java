
package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.parser.Token;
import java.util.Objects;

public final class QuantifierGuard {
    public static final QuantifierGuard[] NO_GUARDS = new QuantifierGuard[0];
    private final Kind kind;
    private final Token.Quantifier quantifier;
    private final int index;

    private QuantifierGuard(Kind kind, Token.Quantifier quantifier) {
        this.kind = kind;
        this.quantifier = quantifier;
        this.index = -1;
    }

    private QuantifierGuard(Kind kind, int index) {
        this.kind = kind;
        this.quantifier = null;
        this.index = index;
    }

    public static QuantifierGuard createEnter(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.enter, quantifier);
    }

    public static QuantifierGuard createLoop(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.loop, quantifier);
    }

    public static QuantifierGuard createLoopInc(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.loopInc, quantifier);
    }

    public static QuantifierGuard createExit(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.exit, quantifier);
    }

    public static QuantifierGuard createClear(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.exitReset, quantifier);
    }

    public static QuantifierGuard createEnterZeroWidth(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.enterZeroWidth, quantifier);
    }

    public static QuantifierGuard createExitZeroWidth(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.exitZeroWidth, quantifier);
    }

    public static QuantifierGuard createEscapeZeroWidth(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.escapeZeroWidth, quantifier);
    }

    public static QuantifierGuard createEnterEmptyMatch(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.enterEmptyMatch, quantifier);
    }

    public static QuantifierGuard createExitEmptyMatch(Token.Quantifier quantifier) {
        return new QuantifierGuard(Kind.exitEmptyMatch, quantifier);
    }

    public static QuantifierGuard createUpdateCG(int index) {
        return new QuantifierGuard(Kind.updateCG, index);
    }

    public Kind getKind() {
        return this.kind;
    }

    public Kind getKindReverse() {
        switch (this.kind) {
            case enter: {
                return this.quantifier.getMin() > 0 ? Kind.exit : Kind.exitReset;
            }
            case loop: 
            case loopInc: {
                return this.kind;
            }
            case exit: 
            case exitReset: {
                return Kind.enter;
            }
            case enterZeroWidth: {
                return Kind.exitZeroWidth;
            }
            case exitZeroWidth: 
            case escapeZeroWidth: {
                return Kind.enterZeroWidth;
            }
            case enterEmptyMatch: {
                return Kind.exitEmptyMatch;
            }
            case exitEmptyMatch: {
                return Kind.enterEmptyMatch;
            }
            case updateCG: {
                return Kind.updateCG;
            }
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    public Token.Quantifier getQuantifier() {
        return this.quantifier;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof QuantifierGuard)) {
            return false;
        }
        QuantifierGuard other = (QuantifierGuard)obj;
        return this.kind == other.kind && Objects.equals(this.quantifier, other.quantifier) && this.index == other.index;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.kind, this.quantifier, this.index});
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        if (this.quantifier != null) {
            return this.kind + " " + this.quantifier;
        }
        return this.kind + " " + this.index;
    }

    public static enum Kind {
        enter,
        loop,
        loopInc,
        exit,
        exitReset,
        enterZeroWidth,
        exitZeroWidth,
        escapeZeroWidth,
        enterEmptyMatch,
        exitEmptyMatch,
        updateCG;

    }
}

