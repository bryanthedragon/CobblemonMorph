
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.BreakableNode;
import com.oracle.js.parser.ir.CatchNode;
import com.oracle.js.parser.ir.Flags;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextScope;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.Terminal;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public class Block
extends Node
implements BreakableNode,
Terminal,
Flags<Block>,
LexicalContextScope {
    protected final List<Statement> statements;
    protected final Scope scope;
    protected final int flags;
    public static final int NEEDS_SCOPE = 1;
    public static final int IS_TERMINAL = 4;
    public static final int IS_GLOBAL_SCOPE = 8;
    public static final int IS_SYNTHETIC = 16;
    public static final int IS_BODY = 32;
    public static final int IS_PARAMETER_BLOCK = 64;
    public static final int IS_SWITCH_BLOCK = 128;
    public static final int IS_EXPRESSION_BLOCK = 256;
    public static final int IS_MODULE_BODY = 512;

    public Block(long token, int finish, int flags, Scope scope, List<Statement> statements) {
        super(token, finish);
        assert (this.start <= finish);
        this.statements = List.copyOf(statements);
        this.scope = scope;
        int len = statements.size();
        int terminalFlags = len > 0 && statements.get(len - 1).hasTerminalFlags() ? 4 : 0;
        this.flags = terminalFlags | flags;
    }

    private Block(Block block, int finish, List<Statement> statements, int flags) {
        super(block, finish);
        this.statements = statements;
        this.flags = flags;
        this.scope = block.scope;
    }

    public boolean isGlobalScope() {
        return this.getFlag(8);
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBlock(this)) {
            return visitor.leaveBlock(this.setStatements(lc, Node.accept(visitor, this.statements)));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterBlock(this);
    }

    public Iterable<Symbol> getSymbols() {
        return this.scope.getSymbols();
    }

    public Symbol getExistingSymbol(String name) {
        return this.scope.getExistingSymbol(name);
    }

    public boolean hasSymbol(String name) {
        return this.scope.hasSymbol(name);
    }

    public int getSymbolCount() {
        return this.scope.getSymbolCount();
    }

    public boolean isCatchBlock() {
        return this.getLastStatement() instanceof CatchNode;
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        for (Node node : this.statements) {
            node.toString(sb, printType);
            sb.append(';');
        }
    }

    @Override
    public int getFlags() {
        return this.flags;
    }

    @Override
    public boolean isTerminal() {
        return this.getFlag(4);
    }

    public List<Statement> getStatements() {
        return this.statements;
    }

    public int getStatementCount() {
        return this.statements.size();
    }

    public int getFirstStatementLineNumber() {
        if (this.statements.isEmpty()) {
            return -1;
        }
        return this.statements.get(0).getLineNumber();
    }

    public Statement getFirstStatement() {
        return this.statements.isEmpty() ? null : this.statements.get(0);
    }

    public Statement getLastStatement() {
        return this.statements.isEmpty() ? null : this.statements.get(this.statements.size() - 1);
    }

    public Block setStatements(LexicalContext lc, List<Statement> statements) {
        if (this.statements == statements) {
            return this;
        }
        int lastFinish = 0;
        if (!statements.isEmpty()) {
            lastFinish = statements.get(statements.size() - 1).getFinish();
        }
        return Node.replaceInLexicalContext(lc, this, new Block(this, Math.max(this.finish, lastFinish), statements, this.flags));
    }

    public boolean needsScope() {
        return (this.flags & 1) == 1;
    }

    public boolean isSynthetic() {
        return (this.flags & 0x10) == 16;
    }

    @Override
    public Block setFlags(LexicalContext lc, int flags) {
        if (this.flags == flags) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new Block(this, this.finish, this.statements, flags));
    }

    @Override
    public Block setFlag(LexicalContext lc, int flag) {
        return this.setFlags(lc, this.flags | flag);
    }

    @Override
    public boolean getFlag(int flag) {
        return (this.flags & flag) == flag;
    }

    @Override
    public boolean isBreakableWithoutLabel() {
        return false;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return BreakableNode.super.accept(visitor);
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return BreakableNode.super.accept(visitor);
    }

    @Override
    public Scope getScope() {
        return this.scope;
    }

    public boolean isFunctionBody() {
        return this.getFlag(32);
    }

    public boolean isParameterBlock() {
        return this.getFlag(64);
    }

    public boolean isSwitchBlock() {
        return this.getFlag(128);
    }

    public boolean isExpressionBlock() {
        return this.getFlag(256);
    }

    public boolean isModuleBody() {
        return this.getFlag(512);
    }
}

