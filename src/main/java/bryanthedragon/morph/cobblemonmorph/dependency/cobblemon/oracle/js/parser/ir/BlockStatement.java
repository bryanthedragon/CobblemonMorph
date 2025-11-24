
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class BlockStatement
extends Statement {
    private final Block block;

    public BlockStatement(int lineNumber, Block block) {
        super(lineNumber, block.getToken(), block.getFinish());
        this.block = block;
    }

    private BlockStatement(BlockStatement blockStatement, Block block) {
        super(blockStatement);
        this.block = block;
    }

    @Override
    public boolean isTerminal() {
        return this.block.isTerminal();
    }

    public boolean isSynthetic() {
        return this.block.isSynthetic();
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBlockStatement(this)) {
            return visitor.leaveBlockStatement(this.setBlock((Block)this.block.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterBlockStatement(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        this.block.toString(sb, printType);
    }

    public Block getBlock() {
        return this.block;
    }

    public BlockStatement setBlock(Block block) {
        if (this.block == block) {
            return this;
        }
        return new BlockStatement(this, block);
    }
}

