
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.BaseNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.OptionalExpression;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class CallNode
extends OptionalExpression {
    private final Expression function;
    private final List<Expression> args;
    private static final int IS_NEW = 1;
    private static final int IS_EVAL = 2;
    private static final int IS_IMPORT = 4;
    private static final int IS_APPLY_ARGUMENTS = 8;
    private static final int IS_OPTIONAL = 16;
    private static final int IS_OPTIONAL_CHAIN = 32;
    private static final int IS_TAGGED_TEMPLATE_LITERAL = 64;
    private static final int IS_DEFAULT_DERIVED_CONSTRUCTOR_SUPER_CALL = 128;
    private final int flags;
    private final int lineNumber;

    public static Expression forNew(int lineNumber, long token, int start2, int finish, Expression function, List<Expression> args) {
        return new CallNode(lineNumber, token, start2, finish, function, args, 1);
    }

    public static Expression forCall(int lineNumber, long token, int start2, int finish, Expression function, List<Expression> args) {
        return CallNode.forCall(lineNumber, token, start2, finish, function, args, false, false, false, false, false);
    }

    public static Expression forCall(int lineNumber, long token, int start2, int finish, Expression function, List<Expression> args, boolean optional, boolean optionalChain) {
        return CallNode.forCall(lineNumber, token, start2, finish, function, args, optional, optionalChain, false, false, false);
    }

    public static Expression forCall(int lineNumber, long token, int start2, int finish, Expression function, List<Expression> args, boolean optional, boolean optionalChain, boolean isEval, boolean isApplyArguments, boolean isDefaultDerivedConstructorSuperCall) {
        return CallNode.create(lineNumber, token, start2, finish, function, args, optional, optionalChain, isEval, isApplyArguments, isDefaultDerivedConstructorSuperCall, false);
    }

    public static Expression forTaggedTemplateLiteral(int lineNumber, long token, int start2, int finish, Expression function, List<Expression> args) {
        return CallNode.create(lineNumber, token, start2, finish, function, args, false, false, false, false, false, true);
    }

    private static Expression create(int lineNumber, long token, int start2, int finish, Expression function, List<Expression> args, boolean optional, boolean optionalChain, boolean isEval, boolean isApplyArguments, boolean isDefaultDerivedConstructorSuperCall, boolean isTaggedTemplateLiteral) {
        return new CallNode(lineNumber, token, start2, finish, CallNode.setIsFunction(function), args, (optional ? 16 : 0) | (optionalChain ? 32 : 0) | (isEval ? 2 : 0) | (isApplyArguments ? 8 : 0) | (isTaggedTemplateLiteral ? 64 : 0) | (isDefaultDerivedConstructorSuperCall ? 128 : 0));
    }

    public static Expression forImport(int lineNumber, long token, int start2, int finish, IdentNode importIdent, List<Expression> args) {
        return new CallNode(lineNumber, token, start2, finish, importIdent, args, 4);
    }

    private CallNode(int lineNumber, long token, int start2, int finish, Expression function, List<Expression> args, int flags) {
        super(token, start2, finish);
        this.function = function;
        this.args = List.copyOf(args);
        this.flags = flags;
        this.lineNumber = lineNumber;
    }

    private CallNode(CallNode callNode, Expression function, List<Expression> args, int flags) {
        super(callNode);
        this.lineNumber = callNode.lineNumber;
        this.function = function;
        this.args = List.copyOf(args);
        this.flags = flags;
    }

    private static Expression setIsFunction(Expression function) {
        return function instanceof BaseNode ? ((BaseNode)function).setIsFunction() : function;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterCallNode(this)) {
            return visitor.leaveCallNode(this.setFunction((Expression)this.function.accept(visitor)).setArgs(Node.accept(visitor, this.args)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterCallNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        StringBuilder fsb = new StringBuilder();
        this.function.toString(fsb, printType);
        sb.append((CharSequence)fsb);
        if (this.isOptional()) {
            sb.append('?').append('.');
        }
        sb.append('(');
        boolean first = true;
        for (Node node : this.args) {
            if (!first) {
                sb.append(", ");
            } else {
                first = false;
            }
            node.toString(sb, printType);
        }
        sb.append(')');
    }

    public List<Expression> getArgs() {
        return this.args;
    }

    public CallNode setArgs(List<Expression> args) {
        if (this.args == args) {
            return this;
        }
        return new CallNode(this, this.function, args, this.flags);
    }

    public boolean isEval() {
        return (this.flags & 2) != 0;
    }

    public Expression getFunction() {
        return this.function;
    }

    public CallNode setFunction(Expression function) {
        if (this.function == function) {
            return this;
        }
        return new CallNode(this, function, this.args, this.flags);
    }

    public boolean isNew() {
        return (this.flags & 1) != 0;
    }

    public boolean isImport() {
        return (this.flags & 4) != 0;
    }

    public boolean isApplyArguments() {
        return (this.flags & 8) != 0;
    }

    @Override
    public boolean isOptional() {
        return (this.flags & 0x10) != 0;
    }

    @Override
    public boolean isOptionalChain() {
        return (this.flags & 0x20) != 0;
    }

    public boolean isTaggedTemplateLiteral() {
        return (this.flags & 0x40) != 0;
    }

    public boolean isDefaultDerivedConstructorSuperCall() {
        return (this.flags & 0x80) != 0;
    }
}

