package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class ASTDotExportVisitor extends DepthFirstTraversalRegexASTVisitor {
   private final BufferedWriter writer;
   private final boolean showParentPointers;

   private ASTDotExportVisitor(BufferedWriter writer, boolean showParentPointers) {
      this.writer = writer;
      this.showParentPointers = showParentPointers;
   }

   @CompilerDirectives.TruffleBoundary
   public static void exportDot(RegexASTNode root, String path, boolean showParentPointers) {
      try {
         try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            ASTDotExportVisitor visitor = new ASTDotExportVisitor(writer, showParentPointers);
            writer.write("digraph ast {");
            writer.newLine();
            visitor.run(root);
            writer.write("}");
            writer.newLine();
         }
      } catch (IOException var8) {
         throw new RuntimeException(var8);
      }
   }

   private static String deadStyle(RegexASTNode node) {
      return node.isDead() ? ", style=filled, color=grey" : "";
   }

   @CompilerDirectives.TruffleBoundary
   private void writeln(String s) {
      try {
         this.writer.write(s);
         this.writer.newLine();
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   private static String nodeName(RegexASTNode node) {
      return String.format("node%d", node.getId());
   }

   private void printParentNextPrev(RegexASTNode node) {
      if (this.showParentPointers && node.getParent() != null) {
         this.writeln(String.format("%s -> %s [label=parent];", nodeName(node), nodeName(node.getParent())));
      }
   }

   @Override
   protected void visit(BackReference backReference) {
      this.writeln(
         String.format("%s [label=\"%s\", shape=box%s];", nodeName(backReference), backReference.toString().replace("\\", "\\\\"), deadStyle(backReference))
      );
      this.printParentNextPrev(backReference);
   }

   @Override
   protected void visit(Group group) {
      this.writeln(String.format("%s [label=group, shape=%s%s];", nodeName(group), group.isCapturing() ? "doublecircle" : "circle", deadStyle(group)));
      this.printParentNextPrev(group);
      int i = 0;

      for (Sequence s : group.getAlternatives()) {
         this.writeln(String.format("%s -> %s [label=alt%d];", nodeName(group), nodeName(s), i++));
      }
   }

   @Override
   protected void visit(Sequence sequence) {
      this.writeln(String.format("%s [label=seq, shape=house%s];", nodeName(sequence), deadStyle(sequence)));
      this.printParentNextPrev(sequence);
      int i = 0;

      for (Term t : sequence.getTerms()) {
         this.writeln(String.format("%s -> %s [label=seq%d];", nodeName(sequence), nodeName(t), i++));
      }
   }

   @Override
   protected void visit(PositionAssertion assertion) {
      this.writeln(String.format("%s [label=\"%s\", shape=box%s];", nodeName(assertion), assertion, deadStyle(assertion)));
      this.printParentNextPrev(assertion);
   }

   @Override
   protected void visit(LookBehindAssertion assertion) {
      this.visitSubtreeRootNode(assertion, "lb", "ass");
   }

   @Override
   protected void visit(LookAheadAssertion assertion) {
      this.visitSubtreeRootNode(assertion, "la", "ass");
   }

   @Override
   protected void visit(AtomicGroup atomicGroup) {
      this.visitSubtreeRootNode(atomicGroup, "atom", "grp");
   }

   private void visitSubtreeRootNode(RegexASTSubtreeRootNode subtreeRootNode, String nodeLabel, String edgeLabel) {
      this.writeln(String.format("%s [label=%s, shape=box%s];", nodeName(subtreeRootNode), nodeLabel, deadStyle(subtreeRootNode)));
      this.printParentNextPrev(subtreeRootNode);
      this.writeln(String.format("%s -> %s [label=%s];", nodeName(subtreeRootNode), nodeName(subtreeRootNode.getGroup()), edgeLabel));
   }

   @Override
   protected void visit(CharacterClass characterClass) {
      this.writeln(
         String.format("%s [label=\"%s\", shape=box%s];", nodeName(characterClass), characterClass.toString().replace("\\", "\\\\"), deadStyle(characterClass))
      );
      this.printParentNextPrev(characterClass);
   }

   @Override
   protected void visit(SubexpressionCall subexpressionCall) {
      this.writeln(
         String.format(
            "%s [label=\"%s\", shape=box%s];", nodeName(subexpressionCall), subexpressionCall.toString().replace("\\", "\\\\"), deadStyle(subexpressionCall)
         )
      );
      this.printParentNextPrev(subexpressionCall);
   }
}
