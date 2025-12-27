package com.oracle.truffle.regex.tregex.nodesplitter;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.buffer.ShortArrayBuffer;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAAbstractStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAInitialStateNode;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public final class DFANodeSplit implements StateIndex<GraphNode> {
   public static final int EXTRA_INITIAL_CAPACITY = 20;
   private final DFAGenerator dfaGenerator;
   private final Graph graph;
   private final DominatorTree domTree;
   private final TBitSet flagDone;
   private final TBitSet flagActive;
   private short nextId;

   private DFANodeSplit(DFAGenerator dfaGenerator, DFAAbstractStateNode[] dfa) {
      this.dfaGenerator = dfaGenerator;
      this.graph = new Graph(dfa.length + 20);
      TBitSet successorBitSet = new TBitSet(dfa.length);
      ShortArrayBuffer successorBuffer = new ShortArrayBuffer();

      for (DFAAbstractStateNode n : dfa) {
         for (int i = 0; i < n.getSuccessors().length; i++) {
            if (n.getSuccessors()[i] == -1) {
               assert n instanceof DFAInitialStateNode;
            } else {
               if (!successorBitSet.get(n.getSuccessors()[i])) {
                  successorBuffer.add((short)i);
               }

               successorBitSet.set(n.getSuccessors()[i]);
            }
         }

         GraphNode graphNode = new GraphNode(this, n, successorBuffer.toArray());
         successorBitSet.clear();
         successorBuffer.clear();
         this.graph.addGraphNode(graphNode);
      }

      this.nextId = (short)this.graph.size();
      this.flagDone = new TBitSet(this.graph.size() + 20);
      this.flagActive = new TBitSet(this.graph.size() + 20);

      for (GraphNode graphNode : this.graph.getNodes()) {
         for (GraphNode successor : graphNode.getSuccessors(this)) {
            successor.addPredecessor(graphNode);
         }
      }

      this.graph.setStart(this.graph.getNodes().get(0));
      this.domTree = new DominatorTree(this.graph);
   }

   private boolean isDone(GraphNode node) {
      return this.flagDone.get(node.getId());
   }

   private void setDone(GraphNode node) {
      this.flagDone.set(node.getId());
   }

   private void clearDone(GraphNode node) {
      this.flagDone.clear(node.getId());
   }

   private boolean isActive(GraphNode node) {
      return this.flagActive.get(node.getId());
   }

   private void setActive(GraphNode node) {
      this.flagActive.set(node.getId());
   }

   private void clearActive(GraphNode node) {
      this.flagActive.clear(node.getId());
   }

   public void addGraphNode(GraphNode graphNode) {
      this.graph.addGraphNode(graphNode);
   }

   public static DFAAbstractStateNode[] createReducibleGraph(DFAAbstractStateNode[] nodes) throws DFANodeSplitBailoutException {
      return new DFANodeSplit(null, nodes).process();
   }

   public static DFAAbstractStateNode[] createReducibleGraphAndUpdateDFAGen(DFAGenerator dfaGen, DFAAbstractStateNode[] nodes) throws DFANodeSplitBailoutException {
      return new DFANodeSplit(dfaGen, nodes).process();
   }

   @Override
   public int getNumberOfStates() {
      return this.graph.getNumberOfStates();
   }

   public int getId(GraphNode state) {
      return state.getId();
   }

   public GraphNode getState(int id) {
      return this.graph.getState(id);
   }

   private DFAAbstractStateNode[] process() throws DFANodeSplitBailoutException {
      this.domTree.createDomTree();
      this.searchBackEdges(this.graph.getStart());
      this.markUndone();
      this.splitLoops(this.graph.getStart(), Collections.emptySet());
      DFAAbstractStateNode[] ret = new DFAAbstractStateNode[this.graph.size()];

      for (GraphNode node : this.graph.getNodes()) {
         ret[node.getDfaNode().getId()] = node.getDfaNode();
      }

      if (this.dfaGenerator != null) {
         this.updateDFAGenerator();
      }

      return ret;
   }

   private void updateDFAGenerator() {
      this.dfaGenerator.nodeSplitSetNewDFASize(this.graph.size());

      for (GraphNode node : this.graph.getNodes()) {
         node.registerDuplicate(this.dfaGenerator);
      }

      for (GraphNode node : this.graph.getNodes()) {
         node.updateSuccessors(this.dfaGenerator);
      }
   }

   private boolean splitLoops(GraphNode topNode, Set<GraphNode> set) throws DFANodeSplitBailoutException {
      boolean crossEdge = false;

      for (GraphNode child : topNode.getDomChildren(this)) {
         if ((set.isEmpty() || set.contains(child)) && this.splitLoops(child, set)) {
            crossEdge = true;
         }
      }

      if (crossEdge) {
         this.handleIrChildren(topNode, set);
      }

      for (GraphNode pred : topNode.getPredecessors()) {
         if (pred.isBackEdge(topNode) && !this.domTree.dom(topNode, pred)) {
            return true;
         }
      }

      return false;
   }

   private void handleIrChildren(GraphNode topNode, Set<GraphNode> set) throws DFANodeSplitBailoutException {
      ArrayDeque<GraphNode> dfsList = new ArrayDeque<>();
      ArrayList<Set<GraphNode>> sccList = new ArrayList<>();

      for (GraphNode child : topNode.getDomChildren(this)) {
         if (!this.isDone(child) && (set.isEmpty() || set.contains(child))) {
            this.scc1(dfsList, child, set, topNode.getDomTreeDepth());
         }
      }

      for (GraphNode n : dfsList) {
         if (this.isDone(n)) {
            Set<GraphNode> scc = StateSet.create(this);
            this.scc2(scc, n, topNode.getDomTreeDepth());
            sccList.add(scc);
         }
      }

      for (Set<GraphNode> scc : sccList) {
         if (scc.size() > 1) {
            this.handleScc(topNode, scc);
         }
      }
   }

   private void scc1(ArrayDeque<GraphNode> dfsList, GraphNode curNode, Set<GraphNode> set, int level) {
      this.setDone(curNode);

      for (GraphNode child : curNode.getSuccessors(this)) {
         if (!this.isDone(child) && child.getDomTreeDepth() > level && (set.isEmpty() || set.contains(child))) {
            this.scc1(dfsList, child, set, level);
         }
      }

      dfsList.push(curNode);
   }

   private void scc2(Set<GraphNode> scc, GraphNode curNode, int level) {
      this.clearDone(curNode);

      for (GraphNode pred : curNode.getPredecessors()) {
         if (this.isDone(pred) && pred.getDomTreeDepth() > level) {
            this.scc2(scc, pred, level);
         }
      }

      scc.add(curNode);
   }

   private void handleScc(GraphNode topNode, Set<GraphNode> scc) throws DFANodeSplitBailoutException {
      StateSet<DFANodeSplit, GraphNode> msed = StateSet.create(this);

      for (GraphNode n : scc) {
         if (n.getDomTreeDepth() == topNode.getDomTreeDepth() + 1) {
            n.setWeightAndHeaders(this, n, scc);
            msed.add(n);
         }
      }

      if (msed.size() > 1) {
         this.splitSCC(chooseNode(msed), scc);
         this.domTree.createDomTree();
         this.markUndone();
         this.searchBackEdges(this.graph.getStart());
         this.markUndone();

         for (GraphNode tmp : this.findTopNodes(scc)) {
            this.splitLoops(tmp, scc);
         }
      }
   }

   private void splitSCC(GraphNode headerNode, Set<GraphNode> scc) throws DFANodeSplitBailoutException {
      for (GraphNode n : scc) {
         if (n.getHeader() != headerNode) {
            if (this.nextId == 4000) {
               CompilerDirectives.transferToInterpreter();
               throw new DFANodeSplitBailoutException();
            }

            n.createCopy(this, this.nextId++);
         }
      }

      for (GraphNode cur : scc) {
         if (cur.getHeader() != headerNode) {
            for (GraphNode suc : cur.getSuccessors(this)) {
               if (suc.getCopy() == null) {
                  suc.addPredecessor(cur.getCopy());
               } else {
                  cur.getCopy().replaceSuccessor(suc);
                  suc.getCopy().replacePredecessor(cur);
               }
            }

            Iterator<GraphNode> curPredecessors = cur.getPredecessors().iterator();

            while (curPredecessors.hasNext()) {
               GraphNode pred = curPredecessors.next();
               if (pred.getCopy() == null) {
                  if (scc.contains(pred)) {
                     pred.replaceSuccessor(cur);
                     curPredecessors.remove();
                  } else {
                     cur.getCopy().removePredecessor(pred);
                  }
               }
            }
         }
      }

      for (GraphNode g : new ArrayList<>(scc)) {
         if (g.getHeader() != headerNode) {
            scc.add(g.getCopy());
            g.clearCopy();
         }
      }
   }

   private Set<GraphNode> findTopNodes(Set<GraphNode> scc) {
      Set<GraphNode> tops = StateSet.create(this);

      for (GraphNode tmp : scc) {
         GraphNode top = this.domTree.idom(tmp);

         while (scc.contains(top)) {
            top = this.domTree.idom(top);
         }

         tops.add(top);
      }

      return tops;
   }

   private static GraphNode chooseNode(Set<GraphNode> msed) {
      int maxWeight = 0;
      GraphNode maxNode = null;

      for (GraphNode n : msed) {
         if (n.getWeight() > maxWeight) {
            maxWeight = n.getWeight();
            maxNode = n;
         }
      }

      return maxNode;
   }

   private void searchBackEdges(GraphNode cnode) {
      this.setDone(cnode);
      this.setActive(cnode);
      cnode.clearBackEdges();

      for (GraphNode child : cnode.getSuccessors(this)) {
         if (this.isActive(child)) {
            cnode.markBackEdge(child);
         } else if (!this.isDone(child)) {
            this.searchBackEdges(child);
         }
      }

      this.clearActive(cnode);
   }

   private void markUndone() {
      this.flagDone.clear();
      this.flagActive.clear();
   }
}
