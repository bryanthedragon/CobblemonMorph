
package com.oracle.truffle.regex.tregex.nodesplitter;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAAbstractStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAInitialStateNode;
import com.oracle.truffle.regex.tregex.nodesplitter.DFANodeSplit;
import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

final class GraphNode
implements Comparable<GraphNode> {
    private static final int[] NO_DOM_CHILDREN = EmptyArrays.INT;
    private DFAAbstractStateNode originalDfaNode;
    private DFAAbstractStateNode dfaNode;
    private boolean dfaNodeCopied = false;
    private final short[] successorSet;
    private final StateSet<DFANodeSplit, GraphNode> predecessorSet;
    private final StateSet<DFANodeSplit, GraphNode> backEdges;
    private int[] domChildren;
    private int nDomChildren;
    private int domTreeDepth;
    private int postOrderIndex;
    private GraphNode header;
    private int weight;
    private GraphNode copy;

    GraphNode(DFANodeSplit graph, DFAAbstractStateNode dfaNode, short[] successorSet) {
        this.dfaNode = dfaNode;
        this.successorSet = successorSet;
        this.predecessorSet = StateSet.create(graph);
        this.backEdges = StateSet.create(graph);
        this.domChildren = NO_DOM_CHILDREN;
    }

    private GraphNode(GraphNode cpy, short dfaNodeId) {
        this.originalDfaNode = cpy.dfaNode;
        this.dfaNode = cpy.dfaNode.createNodeSplitCopy(dfaNodeId);
        this.dfaNodeCopied = true;
        this.successorSet = cpy.successorSet;
        this.predecessorSet = cpy.predecessorSet.copy();
        this.backEdges = cpy.backEdges.copy();
        this.domChildren = cpy.domChildren == NO_DOM_CHILDREN ? NO_DOM_CHILDREN : Arrays.copyOf(cpy.domChildren, cpy.domChildren.length);
        this.nDomChildren = cpy.nDomChildren;
        this.header = cpy.header;
        this.postOrderIndex = cpy.postOrderIndex;
        this.domTreeDepth = cpy.domTreeDepth;
        this.weight = cpy.weight;
    }

    void createCopy(DFANodeSplit graph, short dfaNodeId) {
        if (this.getId() == 0) {
            assert (this.dfaNode instanceof DFAInitialStateNode);
            throw CompilerDirectives.shouldNotReachHere();
        }
        this.copy = new GraphNode(this, dfaNodeId);
        graph.addGraphNode(this.copy);
    }

    public DFAAbstractStateNode getDfaNode() {
        return this.dfaNode;
    }

    @Override
    public int compareTo(GraphNode o) {
        return this.getId() - o.getId();
    }

    public int getId() {
        return this.dfaNode.getId();
    }

    void markBackEdge(GraphNode node) {
        this.backEdges.add(node);
    }

    boolean isBackEdge(GraphNode node) {
        return this.backEdges.contains(node);
    }

    void clearBackEdges() {
        this.backEdges.clear();
    }

    private int nodeWeight() {
        return this.dfaNode.getSuccessors().length;
    }

    void setWeightAndHeaders(StateIndex<GraphNode> index, GraphNode headerNode, Set<GraphNode> scc) {
        this.weight = this.nodeWeight();
        for (GraphNode child : this.getDomChildren(index)) {
            if (!scc.contains(child)) continue;
            child.setWeightAndHeaders(index, headerNode, scc);
            this.weight += child.weight;
        }
        this.header = headerNode;
    }

    void replaceSuccessor(GraphNode suc) {
        if (!this.dfaNodeCopied) {
            this.dfaNode = this.dfaNode.createNodeSplitCopy(this.dfaNode.getId());
            this.dfaNodeCopied = true;
        }
        short[] dfaSuccessors = this.dfaNode.getSuccessors();
        for (int i = 0; i < dfaSuccessors.length; ++i) {
            if (dfaSuccessors[i] != suc.dfaNode.getId()) continue;
            dfaSuccessors[i] = suc.copy.dfaNode.getId();
        }
    }

    boolean hasPredecessor(GraphNode pre) {
        return this.predecessorSet.contains(pre);
    }

    void addPredecessor(GraphNode pre) {
        this.predecessorSet.add(pre);
    }

    void replacePredecessor(GraphNode pre) {
        this.predecessorSet.remove(pre);
        this.predecessorSet.add(pre.copy);
    }

    void removePredecessor(GraphNode pre) {
        this.predecessorSet.remove(pre);
    }

    void addDomChild(GraphNode child) {
        if (this.nDomChildren == this.domChildren.length) {
            this.domChildren = this.domChildren == NO_DOM_CHILDREN ? new int[10] : Arrays.copyOf(this.domChildren, this.domChildren.length * 2);
        }
        this.domChildren[this.nDomChildren++] = child.getId();
    }

    void clearDomChildren() {
        this.nDomChildren = 0;
    }

    public int getPostOrderIndex() {
        return this.postOrderIndex;
    }

    public void setPostOrderIndex(int postOrderIndex) {
        this.postOrderIndex = postOrderIndex;
    }

    Iterable<GraphNode> getSuccessors(final StateIndex<GraphNode> index) {
        return () -> new Iterator<GraphNode>(){
            private int i = 0;

            @Override
            public boolean hasNext() {
                return this.i < GraphNode.this.successorSet.length;
            }

            @Override
            public GraphNode next() {
                return (GraphNode)index.getState(GraphNode.this.dfaNode.getSuccessors()[GraphNode.this.successorSet[this.i++]]);
            }
        };
    }

    Iterable<GraphNode> getPredecessors() {
        return this.predecessorSet;
    }

    Iterable<GraphNode> getDomChildren(final StateIndex<GraphNode> index) {
        return () -> new Iterator<GraphNode>(){
            private int i = 0;

            @Override
            public boolean hasNext() {
                return this.i < GraphNode.this.nDomChildren;
            }

            @Override
            public GraphNode next() {
                return (GraphNode)index.getState(GraphNode.this.domChildren[this.i++]);
            }
        };
    }

    public int getDomTreeDepth() {
        return this.domTreeDepth;
    }

    public void setDomTreeDepth(int domTreeDepth) {
        this.domTreeDepth = domTreeDepth;
    }

    public GraphNode getHeader() {
        return this.header;
    }

    public int getWeight() {
        return this.weight;
    }

    public GraphNode getCopy() {
        return this.copy;
    }

    public void clearCopy() {
        this.copy = null;
    }

    public void registerDuplicate(DFAGenerator dfaGenerator) {
        if (this.originalDfaNode != null) {
            dfaGenerator.nodeSplitRegisterDuplicateState(this.originalDfaNode.getId(), this.dfaNode.getId());
        }
    }

    public void updateSuccessors(DFAGenerator dfaGenerator) {
        if (this.dfaNodeCopied) {
            dfaGenerator.nodeSplitUpdateSuccessors(this.dfaNode.getId(), this.dfaNode.getSuccessors());
        }
    }
}

