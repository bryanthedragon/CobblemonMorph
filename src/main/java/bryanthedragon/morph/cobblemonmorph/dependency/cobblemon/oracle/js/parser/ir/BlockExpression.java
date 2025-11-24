
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class BlockExpression
extends Expression {
    private final Block block;

    public BlockExpression(long token, int finish, Block block) {
        super(token, finish);
        this.block = block;
    }

    private BlockExpression(BlockExpression classNode, Block block) {
        super(classNode);
        this.block = block;
    }

    public Block getBlock() {
        return this.block;
    }

    private BlockExpression setBlock(Block block) {
        if (this.block == block) {
            return this;
        }
        return new BlockExpression(this, block);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBlockExpression(this)) {
            Block newBlock = (Block)this.block.accept(visitor);
            return visitor.leaveBlockExpression(this.setBlock(newBlock));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterBlockExpression(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        this.block.toString(sb, printType);
    }
}

