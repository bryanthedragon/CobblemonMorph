
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
        BuildCallGraphVisitor buildCallGraphVisitor = new BuildCallGraphVisitor(ast);
        buildCallGraphVisitor.run();
        Map<CallGraphNode, List<CallGraphNode>> callGraph = buildCallGraphVisitor.callGraph;
        Map<CallGraphNode, Integer> inDegree = buildCallGraphVisitor.inDegree;
        Group[] captureGroups = buildCallGraphVisitor.captureGroups;
        CopyVisitor copyVisitor = new CopyVisitor(ast);
        ArrayList<CallGraphNode> expansionStack = new ArrayList<CallGraphNode>(callGraph.size());
        for (CallGraphNode node : callGraph.keySet()) {
            if (inDegree.getOrDefault(node, 0) != 0) continue;
            expansionStack.add(node);
        }
        while (!expansionStack.isEmpty()) {
            CallGraphNode node = (CallGraphNode)expansionStack.remove(expansionStack.size() - 1);
            if (node instanceof SubexpressionCallNode) {
                SubexpressionCall subexpressionCall = ((SubexpressionCallNode)node).subexpressionCall;
                RubySubexpressionCalls.replace(subexpressionCall, captureGroups[subexpressionCall.getGroupNr()], copyVisitor);
            }
            if (!callGraph.containsKey(node)) continue;
            for (CallGraphNode dependent : callGraph.get(node)) {
                int dependentInDegree = inDegree.getOrDefault(dependent, 0);
                if (dependentInDegree == 1) {
                    expansionStack.add(dependent);
                    inDegree.remove(dependent);
                    continue;
                }
                inDegree.put(dependent, dependentInDegree - 1);
            }
            callGraph.remove(node);
        }
        assert (callGraph.isEmpty() == inDegree.isEmpty());
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

    private static final class BuildCallGraphVisitor
    extends DepthFirstTraversalRegexASTVisitor {
        public final Map<CallGraphNode, List<CallGraphNode>> callGraph = new HashMap<CallGraphNode, List<CallGraphNode>>();
        public final Map<CallGraphNode, Integer> inDegree = new HashMap<CallGraphNode, Integer>();
        public final Group[] captureGroups;
        private final RegexAST ast;
        private final List<Group> enclosingCaptureGroups = new ArrayList<Group>();

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
                assert (this.enclosingCaptureGroups.get(this.enclosingCaptureGroups.size() - 1) == group);
                this.enclosingCaptureGroups.remove(this.enclosingCaptureGroups.size() - 1);
            }
        }

        @Override
        protected void visit(SubexpressionCall subexpressionCall) {
            SubexpressionCallNode callNode = new SubexpressionCallNode(subexpressionCall);
            for (Group captureGroup : this.enclosingCaptureGroups) {
                this.addEdge(callNode, new CaptureGroupNode(captureGroup.getGroupNumber()));
            }
            this.addEdge(new CaptureGroupNode(subexpressionCall.getGroupNr()), callNode);
        }

        private void addEdge(CallGraphNode from, CallGraphNode to) {
            this.callGraph.computeIfAbsent(from, key -> new ArrayList());
            this.callGraph.get(from).add(to);
            this.inDegree.putIfAbsent(to, 0);
            this.inDegree.computeIfPresent(to, (key, value2) -> value2 + 1);
        }
    }

    private static final class CaptureGroupNode
    extends CallGraphNode {
        private int groupNumber;

        CaptureGroupNode(int groupNumber) {
            this.groupNumber = groupNumber;
        }

        public boolean equals(Object obj) {
            return obj instanceof CaptureGroupNode && this.groupNumber == ((CaptureGroupNode)obj).groupNumber;
        }

        public int hashCode() {
            return this.groupNumber;
        }
    }

    private static final class SubexpressionCallNode
    extends CallGraphNode {
        private final SubexpressionCall subexpressionCall;

        SubexpressionCallNode(SubexpressionCall subexpressionCall) {
            this.subexpressionCall = subexpressionCall;
        }

        public boolean equals(Object obj) {
            return obj instanceof SubexpressionCallNode && this.subexpressionCall == ((SubexpressionCallNode)obj).subexpressionCall;
        }

        public int hashCode() {
            return System.identityHashCode(this.subexpressionCall);
        }
    }

    private static abstract class CallGraphNode {
        private CallGraphNode() {
        }
    }
}

