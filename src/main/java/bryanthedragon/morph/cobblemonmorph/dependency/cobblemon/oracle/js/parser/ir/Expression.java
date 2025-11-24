
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Node;

public abstract class Expression
extends Node {
    private boolean parenthesized;
    private int parensStart;
    private int parensFinish;

    Expression(long token, int start2, int finish) {
        super(token, start2, finish);
    }

    Expression(long token, int finish) {
        super(token, finish);
    }

    Expression(Expression expr) {
        super(expr);
    }

    public boolean isSelfModifying() {
        return false;
    }

    public boolean isAlwaysFalse() {
        return false;
    }

    public boolean isAlwaysTrue() {
        return false;
    }

    public final boolean isParenthesized() {
        return this.parenthesized;
    }

    public final void makeParenthesized(int parenStart, int parenFinish) {
        assert (!this.parenthesized ? parenStart <= super.getStart() && super.getFinish() <= parenFinish : parenStart <= this.parensStart && this.parensFinish <= parenFinish);
        this.parenthesized = true;
        this.parensStart = parenStart;
        this.parensFinish = parenFinish;
    }

    @Override
    public int getStart() {
        return this.parenthesized ? this.parensStart : super.getStart();
    }

    public final int getStartWithoutParens() {
        return super.getStart();
    }

    @Override
    public int getFinish() {
        return this.parenthesized ? this.parensFinish : super.getFinish();
    }

    public final int getFinishWithoutParens() {
        return super.getFinish();
    }
}

