
package com.oracle.truffle.regex.tregex.nodesplitter;

import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.nodesplitter.GraphNode;
import java.util.ArrayList;

final class Graph
implements StateIndex<GraphNode> {
    private GraphNode start;
    private final ArrayList<GraphNode> nodes;

    Graph(int initialCapacity) {
        this.nodes = new ArrayList(initialCapacity);
    }

    public GraphNode getStart() {
        return this.start;
    }

    public void setStart(GraphNode start2) {
        this.start = start2;
    }

    public ArrayList<GraphNode> getNodes() {
        return this.nodes;
    }

    public GraphNode getNode(int id) {
        return this.nodes.get(id);
    }

    public void addGraphNode(GraphNode graphNode) {
        assert (graphNode.getId() == this.nodes.size());
        this.nodes.add(graphNode);
        assert (graphNode == this.nodes.get(graphNode.getId()));
    }

    public int size() {
        return this.nodes.size();
    }

    @Override
    public int getNumberOfStates() {
        return this.size();
    }

    @Override
    public int getId(GraphNode state) {
        return state.getId();
    }

    @Override
    public GraphNode getState(int id) {
        return this.getNode(id);
    }
}

