
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.CatchNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.ArrayList;
import java.util.List;

public final class TryNode
extends Statement {
    private final Block body;
    private final List<Block> catchBlocks;
    private final Block finallyBody;
    private Symbol exception;

    public TryNode(int lineNumber, long token, int finish, Block body, List<Block> catchBlocks, Block finallyBody) {
        super(lineNumber, token, finish);
        this.body = body;
        this.catchBlocks = List.copyOf(catchBlocks);
        this.finallyBody = finallyBody;
    }

    private TryNode(TryNode tryNode, Block body, List<Block> catchBlocks, Block finallyBody) {
        super(tryNode);
        this.body = body;
        this.catchBlocks = List.copyOf(catchBlocks);
        this.finallyBody = finallyBody;
        this.exception = tryNode.exception;
    }

    @Override
    public boolean isTerminal() {
        if (this.body.isTerminal()) {
            for (Block catchBlock : this.catchBlocks) {
                if (catchBlock.isTerminal()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterTryNode(this)) {
            Block newFinallyBody = this.finallyBody == null ? null : (Block)this.finallyBody.accept(visitor);
            Block newBody = (Block)this.body.accept(visitor);
            return visitor.leaveTryNode(this.setBody(newBody).setFinallyBody(newFinallyBody).setCatchBlocks(Node.accept(visitor, this.catchBlocks)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterTryNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("try ");
    }

    public Block getBody() {
        return this.body;
    }

    public TryNode setBody(Block body) {
        if (this.body == body) {
            return this;
        }
        return new TryNode(this, body, this.catchBlocks, this.finallyBody);
    }

    public List<CatchNode> getCatches() {
        ArrayList<CatchNode> catches = new ArrayList<CatchNode>(this.catchBlocks.size());
        for (Block catchBlock : this.catchBlocks) {
            catches.add(TryNode.getCatchNodeFromBlock(catchBlock));
        }
        return List.copyOf(catches);
    }

    private static CatchNode getCatchNodeFromBlock(Block catchBlock) {
        return (CatchNode)catchBlock.getLastStatement();
    }

    public List<Block> getCatchBlocks() {
        return this.catchBlocks;
    }

    public TryNode setCatchBlocks(List<Block> catchBlocks) {
        if (this.catchBlocks == catchBlocks) {
            return this;
        }
        return new TryNode(this, this.body, catchBlocks, this.finallyBody);
    }

    public Symbol getException() {
        return this.exception;
    }

    public Block getFinallyBody() {
        return this.finallyBody;
    }

    public TryNode setFinallyBody(Block finallyBody) {
        if (this.finallyBody == finallyBody) {
            return this;
        }
        return new TryNode(this, this.body, this.catchBlocks, finallyBody);
    }

    @Override
    public boolean isCompletionValueNeverEmpty() {
        return true;
    }
}

