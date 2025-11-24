
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Terminal;

public abstract class Statement
extends Node
implements Terminal {
    private final int lineNumber;

    public Statement(int lineNumber, long token, int finish) {
        super(token, finish);
        this.lineNumber = lineNumber;
    }

    public Statement(int lineNumber, long token, int start2, int finish) {
        super(token, start2, finish);
        this.lineNumber = lineNumber;
    }

    protected Statement(Statement node) {
        super(node);
        this.lineNumber = node.lineNumber;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public boolean isTerminal() {
        return false;
    }

    public boolean hasGoto() {
        return false;
    }

    public final boolean hasTerminalFlags() {
        return this.isTerminal() || this.hasGoto();
    }

    public boolean isCompletionValueNeverEmpty() {
        return false;
    }
}

