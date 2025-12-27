package com.oracle.truffle.js.parser.internal.ir.debug;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.BinaryNode;
import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.BlockStatement;
import com.oracle.js.parser.ir.BreakNode;
import com.oracle.js.parser.ir.CaseNode;
import com.oracle.js.parser.ir.CatchNode;
import com.oracle.js.parser.ir.ContinueNode;
import com.oracle.js.parser.ir.ExpressionStatement;
import com.oracle.js.parser.ir.ForNode;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.IfNode;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LabelNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.SwitchNode;
import com.oracle.js.parser.ir.ThrowNode;
import com.oracle.js.parser.ir.TryNode;
import com.oracle.js.parser.ir.UnaryNode;
import com.oracle.js.parser.ir.VarNode;
import com.oracle.js.parser.ir.WhileNode;
import com.oracle.js.parser.ir.WithNode;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import java.util.List;

public final class PrintVisitor extends NodeVisitor<LexicalContext> {
   private static final int TABWIDTH = 4;
   private final StringBuilder sb;
   private int indent;
   private static final String EOLN = "\n";
   private final boolean printLineNumbers;
   private final boolean printTypes;
   private int lastLineNumber = -1;

   public PrintVisitor() {
      this(true, true);
   }

   public PrintVisitor(final boolean printLineNumbers, final boolean printTypes) {
      super(new LexicalContext());
      this.sb = new StringBuilder();
      this.printLineNumbers = printLineNumbers;
      this.printTypes = printTypes;
   }

   public PrintVisitor(final Node root) {
      this(root, true, true);
   }

   public PrintVisitor(final Node root, final boolean printLineNumbers, final boolean printTypes) {
      this(printLineNumbers, printTypes);
      this.visit(root);
   }

   private void visit(final Node root) {
      root.accept(this);
   }

   @Override
   public String toString() {
      return this.sb.append("\n").toString();
   }

   private void indent() {
      for (int i = this.indent; i > 0; i--) {
         this.sb.append(' ');
      }
   }

   @Override
   public boolean enterDefault(final Node node) {
      node.toString(this.sb, this.printTypes);
      return false;
   }

   @Override
   public boolean enterContinueNode(final ContinueNode node) {
      node.toString(this.sb, this.printTypes);
      return false;
   }

   @Override
   public boolean enterBreakNode(final BreakNode node) {
      node.toString(this.sb, this.printTypes);
      return false;
   }

   @Override
   public boolean enterThrowNode(final ThrowNode node) {
      node.toString(this.sb, this.printTypes);
      return false;
   }

   @Override
   public boolean enterBlock(final Block block) {
      this.sb.append(' ');
      this.sb.append('{');
      this.indent += 4;
      List<Statement> statements = block.getStatements();
      this.printStatements(statements);
      this.indent -= 4;
      this.sb.append("\n");
      this.indent();
      this.sb.append('}');
      return false;
   }

   private void printStatements(final List<Statement> statements) {
      for (Statement statement : statements) {
         if (this.printLineNumbers) {
            int lineNumber = statement.getLineNumber();
            this.sb.append("\n");
            if (lineNumber != this.lastLineNumber) {
               this.indent();
               this.sb.append("[|").append(lineNumber).append("|];").append("\n");
            }

            this.lastLineNumber = lineNumber;
         }

         this.indent();
         statement.accept(this);
         int lastIndex = this.sb.length() - 1;
         char lastChar = this.sb.charAt(lastIndex);

         while (Character.isWhitespace(lastChar) && lastIndex >= 0) {
            lastChar = this.sb.charAt(--lastIndex);
         }

         if (lastChar != '}' && lastChar != ';') {
            this.sb.append(';');
         }

         if (statement.hasGoto()) {
            this.sb.append(" [GOTO]");
         }

         if (statement.isTerminal()) {
            this.sb.append(" [TERMINAL]");
         }
      }
   }

   @Override
   public boolean enterBlockStatement(final BlockStatement statement) {
      statement.getBlock().accept(this);
      return false;
   }

   @Override
   public boolean enterBinaryNode(final BinaryNode binaryNode) {
      binaryNode.getLhs().accept(this);
      this.sb.append(' ');
      this.sb.append(binaryNode.tokenType());
      this.sb.append(' ');
      binaryNode.getRhs().accept(this);
      return false;
   }

   @Override
   public boolean enterJoinPredecessorExpression(final JoinPredecessorExpression expr) {
      expr.getExpression().accept(this);
      return false;
   }

   @Override
   public boolean enterIdentNode(final IdentNode identNode) {
      identNode.toString(this.sb, this.printTypes);
      return true;
   }

   @Override
   public boolean enterUnaryNode(final UnaryNode unaryNode) {
      TokenType tokenType = unaryNode.tokenType();
      String name = tokenType.getName();
      boolean isPostfix = tokenType == TokenType.DECPOSTFIX || tokenType == TokenType.INCPOSTFIX;
      boolean rhsParen = tokenType.needsParens(unaryNode.getExpression().tokenType(), false);
      if (!isPostfix) {
         if (name == null) {
            this.sb.append(tokenType.name());
            rhsParen = true;
         } else {
            this.sb.append(name);
            if (tokenType.ordinal() > TokenType.BIT_NOT.ordinal()) {
               this.sb.append(' ');
            }
         }
      }

      if (rhsParen) {
         this.sb.append('(');
      }

      unaryNode.getExpression().toString(this.sb, this.printTypes);
      if (rhsParen) {
         this.sb.append(')');
      }

      if (isPostfix) {
         this.sb.append(tokenType == TokenType.DECPOSTFIX ? "--" : "++");
      }

      return false;
   }

   @Override
   public boolean enterExpressionStatement(final ExpressionStatement expressionStatement) {
      expressionStatement.getExpression().accept(this);
      return false;
   }

   @Override
   public boolean enterForNode(final ForNode forNode) {
      forNode.toString(this.sb, this.printTypes);
      forNode.getBody().accept(this);
      return false;
   }

   @Override
   public boolean enterFunctionNode(final FunctionNode functionNode) {
      functionNode.toString(this.sb, this.printTypes);
      this.enterBlock(functionNode.getBody());
      return false;
   }

   @Override
   public boolean enterIfNode(final IfNode ifNode) {
      ifNode.toString(this.sb, this.printTypes);
      ifNode.getPass().accept(this);
      Block fail = ifNode.getFail();
      if (fail != null) {
         this.sb.append(" else ");
         fail.accept(this);
      }

      return false;
   }

   @Override
   public boolean enterLabelNode(final LabelNode labeledNode) {
      this.indent -= 4;
      this.indent();
      this.indent += 4;
      labeledNode.toString(this.sb, this.printTypes);
      labeledNode.getBody().accept(this);
      return false;
   }

   @Override
   public boolean enterSwitchNode(final SwitchNode switchNode) {
      switchNode.toString(this.sb, this.printTypes);
      this.sb.append(" {");

      for (CaseNode caseNode : switchNode.getCases()) {
         this.sb.append("\n");
         this.indent();
         caseNode.toString(this.sb, this.printTypes);
         this.indent += 4;
         this.printStatements(caseNode.getStatements());
         this.indent -= 4;
      }

      this.sb.append("\n");
      this.indent();
      this.sb.append("}");
      return false;
   }

   @Override
   public boolean enterTryNode(final TryNode tryNode) {
      tryNode.toString(this.sb, this.printTypes);
      tryNode.getBody().accept(this);

      for (CatchNode catchNode : tryNode.getCatches()) {
         catchNode.toString(this.sb, this.printTypes);
         catchNode.getBody().accept(this);
      }

      Block finallyBody = tryNode.getFinallyBody();
      if (finallyBody != null) {
         this.sb.append(" finally ");
         finallyBody.accept(this);
      }

      return false;
   }

   @Override
   public boolean enterVarNode(final VarNode varNode) {
      this.sb.append(varNode.tokenType().getName()).append(' ');
      varNode.getName().toString(this.sb, this.printTypes);
      Node init = varNode.getInit();
      if (init != null) {
         this.sb.append(" = ");
         init.accept(this);
      }

      return false;
   }

   @Override
   public boolean enterWhileNode(final WhileNode whileNode) {
      if (whileNode.isDoWhile()) {
         this.sb.append("do");
         whileNode.getBody().accept(this);
         this.sb.append(' ');
         whileNode.toString(this.sb, this.printTypes);
      } else {
         whileNode.toString(this.sb, this.printTypes);
         whileNode.getBody().accept(this);
      }

      return false;
   }

   @Override
   public boolean enterWithNode(final WithNode withNode) {
      withNode.toString(this.sb, this.printTypes);
      withNode.getBody().accept(this);
      return false;
   }
}
