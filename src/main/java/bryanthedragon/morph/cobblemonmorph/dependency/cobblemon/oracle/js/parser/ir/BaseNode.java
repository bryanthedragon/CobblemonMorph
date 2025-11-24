
package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FunctionCall;
import com.oracle.js.parser.ir.OptionalExpression;

public abstract class BaseNode
extends OptionalExpression
implements FunctionCall {
    protected final Expression base;
    private final boolean isSuper;
    private final boolean optional;
    private final boolean optionalChain;
    private boolean isFunction;

    public BaseNode(long token, int finish, Expression base, boolean isSuper, boolean optional, boolean optionalChain) {
        super(token, base.getStart(), finish);
        this.base = base;
        this.isSuper = isSuper;
        this.optional = optional;
        this.optionalChain = optionalChain;
        assert (!(isSuper && optional || optional && !optionalChain));
    }

    protected BaseNode(BaseNode baseNode, Expression base, boolean isSuper, boolean optional, boolean optionalChain) {
        super(baseNode);
        this.base = base;
        this.isSuper = isSuper;
        this.optional = optional;
        this.optionalChain = optionalChain;
        this.isFunction = baseNode.isFunction;
    }

    public Expression getBase() {
        return this.base;
    }

    @Override
    public boolean isFunction() {
        return this.isFunction;
    }

    public boolean isSuper() {
        return this.isSuper;
    }

    public boolean isIndex() {
        return this.isTokenType(TokenType.LBRACKET);
    }

    final BaseNode setIsFunction() {
        this.isFunction = true;
        return this;
    }

    public abstract BaseNode setIsSuper();

    @Override
    public final boolean isOptional() {
        return this.optional;
    }

    @Override
    public final boolean isOptionalChain() {
        return this.optionalChain;
    }
}

