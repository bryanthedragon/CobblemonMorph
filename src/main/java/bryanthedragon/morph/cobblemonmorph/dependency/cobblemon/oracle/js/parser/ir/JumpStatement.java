
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Statement;

public abstract class JumpStatement
extends Statement {
    private final String labelName;

    JumpStatement(int lineNumber, long token, int finish, String labelName) {
        super(lineNumber, token, finish);
        this.labelName = labelName;
    }

    JumpStatement(JumpStatement jumpStatement) {
        super(jumpStatement);
        this.labelName = jumpStatement.labelName;
    }

    @Override
    public boolean hasGoto() {
        return true;
    }

    public String getLabelName() {
        return this.labelName;
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(this.getStatementName());
        if (this.labelName != null) {
            sb.append(' ').append(this.labelName);
        }
    }

    abstract String getStatementName();
}

