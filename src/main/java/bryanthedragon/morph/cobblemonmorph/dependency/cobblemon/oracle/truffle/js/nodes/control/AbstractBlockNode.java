
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.DualNode;
import com.oracle.truffle.js.nodes.control.DiscardResultNode;
import com.oracle.truffle.js.nodes.control.EmptyNode;
import com.oracle.truffle.js.nodes.control.ExprBlockNode;
import com.oracle.truffle.js.nodes.control.SequenceNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.VoidBlockNode;
import java.util.ArrayList;
import java.util.Arrays;

@NodeInfo(cost=NodeCost.NONE)
public abstract class AbstractBlockNode
extends StatementNode
implements SequenceNode,
BlockNode.ElementExecutor<JavaScriptNode> {
    @Node.Child
    protected BlockNode<JavaScriptNode> block;

    protected AbstractBlockNode(JavaScriptNode[] statements) {
        this.block = BlockNode.create((Node[])statements, (BlockNode.ElementExecutor)this);
    }

    @Override
    public final JavaScriptNode[] getStatements() {
        return (JavaScriptNode[])this.block.getElements();
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        this.block.executeVoid(frame, 0);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.block.executeGeneric(frame, 0);
    }

    @Override
    public void executeVoid(VirtualFrame frame, JavaScriptNode node, int index, int argument) {
        node.executeVoid(frame);
    }

    @Override
    public Object executeGeneric(VirtualFrame frame, JavaScriptNode node, int index, int argument) {
        return node.execute(frame);
    }

    protected static JavaScriptNode filterStatements(JavaScriptNode[] originalStatements, boolean exprBlock) {
        JavaScriptNode[] finalStatements;
        ArrayList<JavaScriptNode> filteredStatements = null;
        boolean returnExprBlock = exprBlock;
        for (int i = 0; i < originalStatements.length; ++i) {
            JavaScriptNode statement = originalStatements[i];
            if ((statement instanceof EmptyNode || statement instanceof AbstractBlockNode || statement instanceof DualNode || statement instanceof DiscardResultNode || statement instanceof JSConstantNode.JSConstantUndefinedNode) && !JSNodeUtil.hasImportantTag(statement)) {
                if (filteredStatements == null) {
                    filteredStatements = AbstractBlockNode.newListFromRange(originalStatements, 0, i);
                }
                if (statement instanceof AbstractBlockNode) {
                    filteredStatements.addAll(Arrays.asList(((AbstractBlockNode)statement).getStatements()));
                } else if (statement instanceof DualNode) {
                    DualNode dualNode = (DualNode)statement;
                    filteredStatements.add(dualNode.getLeft());
                    filteredStatements.add(dualNode.getRight());
                } else if (statement instanceof DiscardResultNode) {
                    DiscardResultNode voidNode = (DiscardResultNode)statement;
                    filteredStatements.add(voidNode.getOperand());
                    AbstractBlockNode.transferSourceSectionAndTags(statement, voidNode.getOperand());
                } else assert (statement instanceof EmptyNode || statement instanceof JSConstantNode.JSConstantUndefinedNode);
                if (!exprBlock || i != originalStatements.length - 1 || statement instanceof ExprBlockNode || statement instanceof DualNode || statement instanceof EmptyNode) continue;
                returnExprBlock = false;
                continue;
            }
            if (filteredStatements == null) continue;
            filteredStatements.add(statement);
        }
        JavaScriptNode[] javaScriptNodeArray = finalStatements = filteredStatements == null ? originalStatements : filteredStatements.toArray(new JavaScriptNode[filteredStatements.size()]);
        if (returnExprBlock) {
            if (finalStatements.length == 0) {
                return EmptyNode.create();
            }
            if (finalStatements.length == 1) {
                return finalStatements[0];
            }
            if (finalStatements.length == 2) {
                return DualNode.create(finalStatements[0], finalStatements[1]);
            }
            return new ExprBlockNode(finalStatements);
        }
        if (finalStatements.length == 0) {
            return EmptyNode.create();
        }
        if (finalStatements.length == 1) {
            return DiscardResultNode.create(finalStatements[0]);
        }
        return new VoidBlockNode(finalStatements);
    }

    protected static ArrayList<JavaScriptNode> newListFromRange(JavaScriptNode[] statements, int from, int to) {
        return new ArrayList<JavaScriptNode>(Arrays.asList(statements).subList(from, to));
    }
}

