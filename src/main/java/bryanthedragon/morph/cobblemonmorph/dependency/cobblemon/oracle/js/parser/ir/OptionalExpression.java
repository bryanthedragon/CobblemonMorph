
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;

public abstract class OptionalExpression
extends Expression {
    public OptionalExpression(long token, int start2, int finish) {
        super(token, start2, finish);
    }

    public OptionalExpression(long token, int finish) {
        super(token, finish);
    }

    protected OptionalExpression(OptionalExpression baseNode) {
        super(baseNode);
    }

    public abstract boolean isOptional();

    public abstract boolean isOptionalChain();
}

