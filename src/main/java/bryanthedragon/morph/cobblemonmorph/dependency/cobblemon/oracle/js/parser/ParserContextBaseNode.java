
package com.oracle.js.parser;

import com.oracle.js.parser.ParserContextNode;
import com.oracle.js.parser.ir.Statement;
import java.util.ArrayList;
import java.util.List;

abstract class ParserContextBaseNode
implements ParserContextNode {
    protected int flags;
    private List<Statement> statements;

    ParserContextBaseNode() {
        this(0);
    }

    ParserContextBaseNode(int flags) {
        this.flags = flags;
        this.statements = new ArrayList<Statement>();
    }

    @Override
    public int getFlags() {
        return this.flags;
    }

    @Override
    public int getFlag(int flag) {
        return this.flags & flag;
    }

    @Override
    public int setFlag(int flag) {
        this.flags |= flag;
        return this.flags;
    }

    public int clearFlag(int flag) {
        this.flags &= ~flag;
        return this.flags;
    }

    @Override
    public List<Statement> getStatements() {
        return this.statements;
    }

    @Override
    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void appendStatement(Statement statement) {
        this.statements.add(statement);
    }

    @Override
    public void prependStatement(Statement statement) {
        this.statements.add(0, statement);
    }
}

