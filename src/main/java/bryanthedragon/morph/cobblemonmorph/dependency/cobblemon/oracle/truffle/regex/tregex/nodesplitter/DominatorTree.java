
package com.oracle.truffle.regex.tregex.nodesplitter;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.nodesplitter.Graph;
import com.oracle.truffle.regex.tregex.nodesplitter.GraphNode;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.ArrayList;
import java.util.Arrays;

final class DominatorTree {
    private final Graph graph;
    private int nextPostOrderIndex;
    private final ArrayList<GraphNode> postOrder;
    private int[] doms;
    private final TBitSet flagTraversed;

    DominatorTree(Graph graph) {
        this.graph = graph;
        this.flagTraversed = new TBitSet(graph.size() + 20);
        this.postOrder = new ArrayList(graph.size() + 20);
        this.doms = new int[graph.size() + 20];
    }

    private boolean isTraversed(GraphNode node) {
        return this.flagTraversed.get(node.getId());
    }

    private void setTraversed(GraphNode node) {
        this.flagTraversed.set(node.getId());
    }

    void createDomTree() {
        this.buildPostOrder();
        this.buildDominatorTree();
        this.initDomTreeDepth(this.graph.getStart(), 1);
    }

    GraphNode idom(GraphNode n) {
        return this.postOrder.get(this.doms[n.getPostOrderIndex()]);
    }

    boolean dom(GraphNode a, GraphNode b) {
        int dom = this.doms[b.getPostOrderIndex()];
        while (a.getPostOrderIndex() != dom) {
            if (dom == this.doms[dom]) {
                return false;
            }
            dom = this.doms[dom];
        }
        return true;
    }

    private boolean graphIsConsistent() {
        for (GraphNode n : this.graph.getNodes()) {
            for (GraphNode s : n.getSuccessors(this.graph)) {
                if (s.hasPredecessor(n)) continue;
                return false;
            }
        }
        return true;
    }

    private void buildPostOrder() {
        assert (this.graphIsConsistent());
        this.flagTraversed.clear();
        this.nextPostOrderIndex = 0;
        this.postOrder.clear();
        this.traversePostOrder(this.graph.getStart());
        assert (this.allNodesTraversed());
    }

    private void traversePostOrder(GraphNode cur) {
        this.setTraversed(cur);
        for (GraphNode n : cur.getSuccessors(this.graph)) {
            if (this.isTraversed(n)) continue;
            this.traversePostOrder(n);
        }
        cur.setPostOrderIndex(this.nextPostOrderIndex++);
        this.postOrder.add(cur);
        assert (this.postOrder.get(cur.getPostOrderIndex()) == cur);
    }

    private boolean allNodesTraversed() {
        for (GraphNode n : this.graph.getNodes()) {
            if (this.isTraversed(n)) continue;
            return false;
        }
        return true;
    }

    private void buildDominatorTree() {
        if (this.doms.length < this.graph.size()) {
            int newLength;
            for (newLength = this.doms.length * 2; newLength < this.graph.size(); newLength *= 2) {
            }
            this.doms = new int[newLength];
        }
        Arrays.fill(this.doms, -1);
        this.doms[this.graph.getStart().getPostOrderIndex()] = this.graph.getStart().getPostOrderIndex();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (int i = this.postOrder.size() - 1; i >= 0; --i) {
                GraphNode b = this.postOrder.get(i);
                if (b == this.graph.getStart()) continue;
                GraphNode selectedPredecessor = null;
                for (GraphNode p : b.getPredecessors()) {
                    if (p.getPostOrderIndex() <= i) continue;
                    selectedPredecessor = p;
                    break;
                }
                if (selectedPredecessor == null) {
                    throw CompilerDirectives.shouldNotReachHere();
                }
                int newIDom = selectedPredecessor.getPostOrderIndex();
                for (GraphNode p : b.getPredecessors()) {
                    if (p == selectedPredecessor || this.doms[p.getPostOrderIndex()] == -1) continue;
                    newIDom = this.intersect(p.getPostOrderIndex(), newIDom);
                }
                if (this.doms[b.getPostOrderIndex()] == newIDom) continue;
                this.doms[b.getPostOrderIndex()] = newIDom;
                changed = true;
            }
        }
        for (GraphNode n : this.graph.getNodes()) {
            n.clearDomChildren();
        }
        for (int i = 0; i < this.graph.size(); ++i) {
            GraphNode child;
            GraphNode dominator = this.postOrder.get(this.doms[i]);
            if (dominator == (child = this.postOrder.get(i))) continue;
            dominator.addDomChild(child);
        }
    }

    private int intersect(int b1, int b2) {
        int finger1 = b1;
        int finger2 = b2;
        while (finger1 != finger2) {
            while (finger1 < finger2) {
                finger1 = this.doms[finger1];
            }
            while (finger2 < finger1) {
                finger2 = this.doms[finger2];
            }
        }
        return finger1;
    }

    private void initDomTreeDepth(GraphNode curNode, int depth) {
        curNode.setDomTreeDepth(depth);
        for (GraphNode child : curNode.getDomChildren(this.graph)) {
            this.initDomTreeDepth(child, depth + 1);
        }
    }
}

