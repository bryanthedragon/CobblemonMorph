
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.BreakableStatement;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LexicalContext;

public abstract class LoopNode
extends BreakableStatement {
    protected final JoinPredecessorExpression test;
    protected final Block body;
    protected final boolean controlFlowEscapes;

    protected LoopNode(int lineNumber, long token, int finish, Block body, JoinPredecessorExpression test, boolean controlFlowEscapes) {
        super(lineNumber, token, finish);
        this.body = body;
        this.controlFlowEscapes = controlFlowEscapes;
        this.test = test;
    }

    protected LoopNode(LoopNode loopNode, JoinPredecessorExpression test, Block body, boolean controlFlowEscapes) {
        super(loopNode);
        this.test = test;
        this.body = body;
        this.controlFlowEscapes = controlFlowEscapes;
    }

    @Override
    public boolean isTerminal() {
        if (!this.mustEnter()) {
            return false;
        }
        if (this.controlFlowEscapes) {
            return false;
        }
        if (this.body.isTerminal()) {
            return true;
        }
        return this.test == null;
    }

    public abstract boolean mustEnter();

    @Override
    public boolean isLoop() {
        return true;
    }

    public abstract Block getBody();

    public abstract LoopNode setBody(LexicalContext var1, Block var2);

    public final JoinPredecessorExpression getTest() {
        return this.test;
    }

    public abstract LoopNode setTest(LexicalContext var1, JoinPredecessorExpression var2);

    public abstract LoopNode setControlFlowEscapes(LexicalContext var1, boolean var2);

    public abstract boolean hasPerIterationScope();

    @Override
    public boolean isCompletionValueNeverEmpty() {
        return true;
    }
}

