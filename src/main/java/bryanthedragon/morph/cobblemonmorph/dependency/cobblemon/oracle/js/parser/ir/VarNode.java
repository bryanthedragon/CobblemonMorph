
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Assignment;
import com.oracle.js.parser.ir.ClassNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class VarNode
extends Statement
implements Assignment<IdentNode> {
    private final IdentNode name;
    private final Expression init;
    private final int flags;
    private final int sourceOrder;
    public static final int IS_LET = 1;
    public static final int IS_CONST = 2;
    public static final int IS_LAST_FUNCTION_DECLARATION = 4;
    public static final int IS_EXPORT = 8;
    public static final int IS_DESTRUCTURING = 16;
    public static final int IS_ANNEXB_BLOCK_TO_FUNCTION_TRANSFER = 32;

    public VarNode(int lineNumber, long token, int finish, IdentNode name, Expression init2) {
        this(lineNumber, token, finish, name, init2, 0);
    }

    private VarNode(VarNode varNode, IdentNode name, Expression init2, int flags) {
        super(varNode);
        this.sourceOrder = -1;
        this.name = init2 == null ? name : name.setIsInitializedHere();
        this.init = init2;
        this.flags = flags;
    }

    public VarNode(int lineNumber, long token, int finish, IdentNode name, Expression init2, int flags) {
        this(lineNumber, token, -1, finish, name, init2, flags);
    }

    public VarNode(int lineNumber, long token, int sourceOrder, int finish, IdentNode name, Expression init2, int flags) {
        super(lineNumber, token, finish);
        this.sourceOrder = sourceOrder;
        this.name = init2 == null ? name : name.setIsInitializedHere();
        this.init = init2;
        this.flags = flags;
    }

    public VarNode(int lineNumber, long token, int sourceOrder, int start2, int finish, IdentNode name, Expression init2, int flags) {
        super(lineNumber, token, start2, finish);
        this.sourceOrder = sourceOrder;
        this.name = init2 == null ? name : name.setIsInitializedHere();
        this.init = init2;
        this.flags = flags;
    }

    @Override
    public int getSourceOrder() {
        return this.sourceOrder == -1 ? super.getSourceOrder() : this.sourceOrder;
    }

    @Override
    public boolean isAssignment() {
        return this.hasInit();
    }

    @Override
    public IdentNode getAssignmentDest() {
        return this.isAssignment() ? this.name : null;
    }

    @Override
    public Expression getAssignmentSource() {
        return this.isAssignment() ? this.getInit() : null;
    }

    public boolean isBlockScoped() {
        return this.getFlag(1) || this.getFlag(2);
    }

    public boolean isLet() {
        return this.getFlag(1);
    }

    public boolean isConst() {
        return this.getFlag(2);
    }

    public int getSymbolFlags() {
        if (this.isLet()) {
            if (this.getName().isCatchParameter()) {
                return 33793;
            }
            return 1;
        }
        if (this.isConst()) {
            return 2;
        }
        return 4;
    }

    public boolean hasInit() {
        return this.init != null;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterVarNode(this)) {
            Expression newInit = this.init == null ? null : (Expression)this.init.accept(visitor);
            IdentNode newName = (IdentNode)this.name.accept(visitor);
            VarNode newThis = this.name != newName || this.init != newInit ? new VarNode(this, newName, newInit, this.flags) : this;
            return visitor.leaveVarNode(newThis);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterVarNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(this.tokenType().getName()).append(' ');
        this.name.toString(sb, printType);
        if (this.init != null) {
            sb.append(" = ");
            this.init.toString(sb, printType);
        }
    }

    public Expression getInit() {
        return this.init;
    }

    public IdentNode getName() {
        return this.name;
    }

    private VarNode setFlags(int flags) {
        if (this.flags == flags) {
            return this;
        }
        return new VarNode(this, this.name, this.init, flags);
    }

    public boolean getFlag(int flag) {
        return (this.flags & flag) == flag;
    }

    public VarNode setFlag(int flag) {
        return this.setFlags(this.flags | flag);
    }

    public boolean isHoistableDeclaration() {
        return this.init instanceof FunctionNode && ((FunctionNode)this.init).isDeclared();
    }

    public boolean isFunctionDeclaration() {
        return this.init instanceof FunctionNode && ((FunctionNode)this.init).isFunctionDeclaration();
    }

    public boolean isExport() {
        return this.getFlag(8);
    }

    public boolean isDestructuring() {
        return this.getFlag(16);
    }

    public boolean isClassDeclaration() {
        return this.init instanceof ClassNode;
    }
}

