package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.CopyVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.DepthFirstTraversalRegexASTVisitor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RubySubexpressionCalls {
   public static void expandNonRecursiveSubexpressionCalls(RegexAST ast) {
      RubySubexpressionCalls.BuildCallGraphVisitor buildCallGraphVisitor = new RubySubexpressionCalls.BuildCallGraphVisitor(ast);
      buildCallGraphVisitor.run();
      Map<RubySubexpressionCalls.CallGraphNode, List<RubySubexpressionCalls.CallGraphNode>> callGraph = buildCallGraphVisitor.callGraph;
      Map<RubySubexpressionCalls.CallGraphNode, Integer> inDegree = buildCallGraphVisitor.inDegree;
      Group[] captureGroups = buildCallGraphVisitor.captureGroups;
      CopyVisitor copyVisitor = new CopyVisitor(ast);
      ArrayList<RubySubexpressionCalls.CallGraphNode> expansionStack = new ArrayList<>(callGraph.size());

      for (RubySubexpressionCalls.CallGraphNode node : callGraph.keySet()) {
         if (inDegree.getOrDefault(node, 0) == 0) {
            expansionStack.add(node);
         }
      }

      while (!expansionStack.isEmpty()) {
         RubySubexpressionCalls.CallGraphNode nodex = expansionStack.remove(expansionStack.size() - 1);
         if (nodex instanceof RubySubexpressionCalls.SubexpressionCallNode) {
            SubexpressionCall subexpressionCall = ((RubySubexpressionCalls.SubexpressionCallNode)nodex).subexpressionCall;
            replace(subexpressionCall, captureGroups[subexpressionCall.getGroupNr()], copyVisitor);
         }

         if (callGraph.containsKey(nodex)) {
            for (RubySubexpressionCalls.CallGraphNode dependent : callGraph.get(nodex)) {
               int dependentInDegree = inDegree.getOrDefault(dependent, 0);
               if (dependentInDegree == 1) {
                  expansionStack.add(dependent);
                  inDegree.remove(dependent);
               } else {
                  inDegree.put(dependent, dependentInDegree - 1);
               }
            }

            callGraph.remove(nodex);
         }
      }

      assert callGraph.isEmpty() == inDegree.isEmpty();

      if (!callGraph.isEmpty()) {
         throw new UnsupportedRegexException("recursive subexpression calls are not supported");
      }
   }

   private static void replace(SubexpressionCall caller, Group callee, CopyVisitor copyVisitor) {
      Group copy = (Group)copyVisitor.copy(callee);
      copy.setQuantifier(caller.getQuantifier());
      Sequence callerSeq = caller.getParent();
      int callerSeqIndex = caller.getSeqIndex();
      callerSeq.replace(callerSeqIndex, copy);
   }

   private static final class BuildCallGraphVisitor extends DepthFirstTraversalRegexASTVisitor {
      public final Map<RubySubexpressionCalls.CallGraphNode, List<RubySubexpressionCalls.CallGraphNode>> callGraph = new HashMap<>();
      public final Map<RubySubexpressionCalls.CallGraphNode, Integer> inDegree = new HashMap<>();
      public final Group[] captureGroups;
      private final RegexAST ast;
      private final List<Group> enclosingCaptureGroups = new ArrayList<>();

      BuildCallGraphVisitor(RegexAST ast) {
         this.ast = ast;
         this.captureGroups = new Group[ast.getNumberOfCaptureGroups()];
      }

      public void run() {
         this.run(this.ast.getRoot());
      }

      @Override
      protected void visit(Group group) {
         if (group.isCapturing()) {
            this.captureGroups[group.getGroupNumber()] = group;
            this.enclosingCaptureGroups.add(group);
         }
      }

      @Override
      protected void leave(Group group) {
         if (group.isCapturing()) {
            assert this.enclosingCaptureGroups.get(this.enclosingCaptureGroups.size() - 1) == group;

            this.enclosingCaptureGroups.remove(this.enclosingCaptureGroups.size() - 1);
         }
      }

      @Override
      protected void visit(SubexpressionCall subexpressionCall) {
         RubySubexpressionCalls.CallGraphNode callNode = new RubySubexpressionCalls.SubexpressionCallNode(subexpressionCall);

         for (Group captureGroup : this.enclosingCaptureGroups) {
            this.addEdge(callNode, new RubySubexpressionCalls.CaptureGroupNode(captureGroup.getGroupNumber()));
         }

         this.addEdge(new RubySubexpressionCalls.CaptureGroupNode(subexpressionCall.getGroupNr()), callNode);
      }

      private void addEdge(RubySubexpressionCalls.CallGraphNode from, RubySubexpressionCalls.CallGraphNode to) {
         this.callGraph.computeIfAbsent(from, key -> new ArrayList<>());
         this.callGraph.get(from).add(to);
         this.inDegree.putIfAbsent(to, 0);
         this.inDegree.computeIfPresent(to, (key, value) -> value + 1);
      }
   }

   private abstract static class CallGraphNode {
   }

   private static final class CaptureGroupNode extends RubySubexpressionCalls.CallGraphNode {
      private int groupNumber;

      CaptureGroupNode(int groupNumber) {
         this.groupNumber = groupNumber;
      }

      @Override
      public boolean equals(Object obj) {
         return obj instanceof RubySubexpressionCalls.CaptureGroupNode && this.groupNumber == ((RubySubexpressionCalls.CaptureGroupNode)obj).groupNumber;
      }

      @Override
      public int hashCode() {
         return this.groupNumber;
      }
   }

   private static final class SubexpressionCallNode extends RubySubexpressionCalls.CallGraphNode {
      private final SubexpressionCall subexpressionCall;

      SubexpressionCallNode(SubexpressionCall subexpressionCall) {
         this.subexpressionCall = subexpressionCall;
      }

      @Override
      public boolean equals(Object obj) {
         return obj instanceof RubySubexpressionCalls.SubexpressionCallNode
            && this.subexpressionCall == ((RubySubexpressionCalls.SubexpressionCallNode)obj).subexpressionCall;
      }

      @Override
      public int hashCode() {
         return System.identityHashCode(this.subexpressionCall);
      }
   }
}
