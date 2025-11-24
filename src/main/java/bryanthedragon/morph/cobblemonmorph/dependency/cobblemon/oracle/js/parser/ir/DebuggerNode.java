
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class DebuggerNode
extends Statement {
    public DebuggerNode(int lineNumber, long token, int finish) {
        super(lineNumber, token, finish);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterDebuggerNode(this)) {
            return visitor.leaveDebuggerNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterDebuggerNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("debugger");
    }
}

