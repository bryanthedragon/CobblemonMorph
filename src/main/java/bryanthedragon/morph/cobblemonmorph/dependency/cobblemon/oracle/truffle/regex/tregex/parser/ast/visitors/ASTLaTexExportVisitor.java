package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.util.LaTexExport;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public final class ASTLaTexExportVisitor extends DepthFirstTraversalRegexASTVisitor {
   private final RegexAST ast;
   private final BufferedWriter writer;
   private int indent = 0;
   private final List<CharacterClass> lbEntries = new ArrayList<>();

   private ASTLaTexExportVisitor(RegexAST ast, BufferedWriter writer) {
      this.ast = ast;
      this.writer = writer;
   }

   @CompilerDirectives.TruffleBoundary
   public static void exportLatex(RegexAST ast, TruffleFile path) {
      try {
         try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            ASTLaTexExportVisitor visitor = new ASTLaTexExportVisitor(ast, writer);
            visitor.writeln("\\documentclass{standalone}");
            visitor.writeln("\\usepackage[utf8]{inputenc}");
            visitor.writeln("\\usepackage[T1]{fontenc}");
            visitor.writeln("\\usepackage[edges]{forest}");
            visitor.writeln("\\begin{document}");
            visitor.writeln("\\begin{forest}");
            visitor.writeln("for tree={draw,");
            visitor.writeln("before typesetting nodes={content=\\texttt{#1}}");
            visitor.writeln("},");
            visitor.writeln("forked edges,");
            visitor.run(ast.getWrappedRoot());
            visitor.drawLookBehindEntries();
            visitor.writeln("\\end{forest}");
            visitor.writeln("\\end{document}");
         }
      } catch (IOException var7) {
         throw new RuntimeException(var7);
      }
   }

   private void drawLookBehindEntries() {
      for (CharacterClass cc : this.lbEntries) {
         for (LookBehindAssertion lbe : cc.getLookBehindEntries()) {
            this.writeln(String.format("\\draw[->,dotted] (n%d) to[in=north] (n%d);", cc.getId(), lbe.getGroup().getId()));
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private void writeln(String s) {
      try {
         for (int i = 0; i < this.indent; i++) {
            this.writer.write(" ");
         }

         this.writer.write(s);
         this.writer.newLine();
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   private String style(RegexASTNode node) {
      if (node.isDead()) {
         return ",fill=gray";
      } else if (!(node instanceof PositionAssertion) || !this.ast.getReachableCarets().contains(node) && !this.ast.getReachableDollars().contains(node)) {
         return node == this.ast.getWrappedRoot() ? ",fill=green" : "";
      } else {
         return ",fill=cyan";
      }
   }

   private String node(RegexASTNode node) {
      return String.format("[%s%s]", label(node.toString(), node), this.style(node));
   }

   private static String label(String str, RegexASTNode node) {
      return String.format("{%s\\textsubscript{%d}},name=n%d", LaTexExport.escape(str), node.getId(), node.getId());
   }

   private void openNode(String str, RegexASTNode node) {
      this.writeln("[" + label(str, node) + this.style(node));
      this.indent += 2;
   }

   private void closeNode() {
      this.indent -= 2;
      this.writeln("]");
   }

   @Override
   protected void visit(BackReference backReference) {
      this.writeln(this.node(backReference));
   }

   @Override
   protected void visit(Group group) {
      this.openNode((group.isCapturing() ? String.format("(%d)", group.getGroupNumber()) : "(:?)") + group.loopToString(), group);
   }

   @Override
   protected void leave(Group group) {
      this.closeNode();
   }

   @Override
   protected void visit(Sequence sequence) {
      this.openNode("|", sequence);
   }

   @Override
   protected void leave(Sequence sequence) {
      this.closeNode();
   }

   @Override
   protected void visit(PositionAssertion assertion) {
      this.writeln(this.node(assertion));
   }

   @Override
   protected void visit(LookBehindAssertion assertion) {
      this.openNode(String.format("(%s)", assertion.getPrefix()), assertion);
   }

   @Override
   protected void leave(LookBehindAssertion assertion) {
      this.closeNode();
   }

   @Override
   protected void visit(LookAheadAssertion assertion) {
      this.openNode(String.format("(%s)", assertion.getPrefix()), assertion);
   }

   @Override
   protected void leave(LookAheadAssertion assertion) {
      this.closeNode();
   }

   @Override
   protected void visit(AtomicGroup atomicGroup) {
      this.openNode(String.format("(%s)", atomicGroup.getPrefix()), atomicGroup);
   }

   @Override
   protected void leave(AtomicGroup atomicGroup) {
      this.closeNode();
   }

   @Override
   protected void visit(CharacterClass characterClass) {
      this.writeln(this.node(characterClass));
      if (characterClass.hasLookBehindEntries()) {
         this.lbEntries.add(characterClass);
      }
   }

   @Override
   protected void visit(SubexpressionCall subexpressionCall) {
      this.writeln(this.node(subexpressionCall));
   }
}
