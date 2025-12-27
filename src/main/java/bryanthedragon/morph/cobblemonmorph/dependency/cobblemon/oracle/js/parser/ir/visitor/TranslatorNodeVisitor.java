package com.oracle.js.parser.ir.visitor;

import com.oracle.js.parser.ir.AccessNode;
import com.oracle.js.parser.ir.BinaryNode;
import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.BlockExpression;
import com.oracle.js.parser.ir.BlockStatement;
import com.oracle.js.parser.ir.BreakNode;
import com.oracle.js.parser.ir.CallNode;
import com.oracle.js.parser.ir.CaseNode;
import com.oracle.js.parser.ir.CatchNode;
import com.oracle.js.parser.ir.ClassElement;
import com.oracle.js.parser.ir.ClassNode;
import com.oracle.js.parser.ir.ContinueNode;
import com.oracle.js.parser.ir.DebuggerNode;
import com.oracle.js.parser.ir.EmptyNode;
import com.oracle.js.parser.ir.ErrorNode;
import com.oracle.js.parser.ir.ExportNode;
import com.oracle.js.parser.ir.ExportSpecifierNode;
import com.oracle.js.parser.ir.ExpressionStatement;
import com.oracle.js.parser.ir.ForNode;
import com.oracle.js.parser.ir.FromNode;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.IfNode;
import com.oracle.js.parser.ir.ImportClauseNode;
import com.oracle.js.parser.ir.ImportNode;
import com.oracle.js.parser.ir.ImportSpecifierNode;
import com.oracle.js.parser.ir.IndexNode;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LabelNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.js.parser.ir.NameSpaceImportNode;
import com.oracle.js.parser.ir.NamedExportsNode;
import com.oracle.js.parser.ir.NamedImportsNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.ObjectNode;
import com.oracle.js.parser.ir.ParameterNode;
import com.oracle.js.parser.ir.PropertyNode;
import com.oracle.js.parser.ir.ReturnNode;
import com.oracle.js.parser.ir.SwitchNode;
import com.oracle.js.parser.ir.TemplateLiteralNode;
import com.oracle.js.parser.ir.TernaryNode;
import com.oracle.js.parser.ir.ThrowNode;
import com.oracle.js.parser.ir.TryNode;
import com.oracle.js.parser.ir.UnaryNode;
import com.oracle.js.parser.ir.VarNode;
import com.oracle.js.parser.ir.WhileNode;
import com.oracle.js.parser.ir.WithNode;

public abstract class TranslatorNodeVisitor<T extends LexicalContext, R> {
   protected final T lc;

   public TranslatorNodeVisitor(final T lc) {
      this.lc = lc;
   }

   public final T getLexicalContext() {
      return this.lc;
   }

   protected R enterDefault(final Node node) {
      throw new AssertionError(String.format("should not reach here. %s(%s)", node.getClass().getSimpleName(), node));
   }

   public R enterAccessNode(final AccessNode accessNode) {
      return this.enterDefault(accessNode);
   }

   public R enterBlock(final Block block) {
      return this.enterDefault(block);
   }

   public R enterBinaryNode(final BinaryNode binaryNode) {
      return this.enterDefault(binaryNode);
   }

   public R enterBreakNode(final BreakNode breakNode) {
      return this.enterDefault(breakNode);
   }

   public R enterCallNode(final CallNode callNode) {
      return this.enterDefault(callNode);
   }

   public R enterCaseNode(final CaseNode caseNode) {
      return this.enterDefault(caseNode);
   }

   public R enterCatchNode(final CatchNode catchNode) {
      return this.enterDefault(catchNode);
   }

   public R enterContinueNode(final ContinueNode continueNode) {
      return this.enterDefault(continueNode);
   }

   public R enterDebuggerNode(final DebuggerNode debuggerNode) {
      return this.enterDefault(debuggerNode);
   }

   public R enterEmptyNode(final EmptyNode emptyNode) {
      return this.enterDefault(emptyNode);
   }

   public R enterErrorNode(final ErrorNode errorNode) {
      return this.enterDefault(errorNode);
   }

   public R enterNamedExportsNode(final NamedExportsNode exportClauseNode) {
      return this.enterDefault(exportClauseNode);
   }

   public R enterExportNode(final ExportNode exportNode) {
      return this.enterDefault(exportNode);
   }

   public R enterExportSpecifierNode(final ExportSpecifierNode exportSpecifierNode) {
      return this.enterDefault(exportSpecifierNode);
   }

   public R enterExpressionStatement(final ExpressionStatement expressionStatement) {
      return this.enterDefault(expressionStatement);
   }

   public R enterBlockStatement(final BlockStatement blockStatement) {
      return this.enterDefault(blockStatement);
   }

   public R enterForNode(final ForNode forNode) {
      return this.enterDefault(forNode);
   }

   public R enterFromNode(final FromNode fromNode) {
      return this.enterDefault(fromNode);
   }

   public R enterFunctionNode(final FunctionNode functionNode) {
      return this.enterDefault(functionNode);
   }

   public R enterIdentNode(final IdentNode identNode) {
      return this.enterDefault(identNode);
   }

   public R enterIfNode(final IfNode ifNode) {
      return this.enterDefault(ifNode);
   }

   public R enterImportClauseNode(final ImportClauseNode importClauseNode) {
      return this.enterDefault(importClauseNode);
   }

   public R enterImportNode(final ImportNode importNode) {
      return this.enterDefault(importNode);
   }

   public R enterImportSpecifierNode(final ImportSpecifierNode importSpecifierNode) {
      return this.enterDefault(importSpecifierNode);
   }

   public R enterIndexNode(final IndexNode indexNode) {
      return this.enterDefault(indexNode);
   }

   public R enterLabelNode(final LabelNode labelNode) {
      return this.enterDefault(labelNode);
   }

   public R enterLiteralNode(final LiteralNode<?> literalNode) {
      return this.enterDefault(literalNode);
   }

   public R enterNameSpaceImportNode(final NameSpaceImportNode nameSpaceImportNode) {
      return this.enterDefault(nameSpaceImportNode);
   }

   public R enterNamedImportsNode(final NamedImportsNode namedImportsNode) {
      return this.enterDefault(namedImportsNode);
   }

   public R enterObjectNode(final ObjectNode objectNode) {
      return this.enterDefault(objectNode);
   }

   public R enterPropertyNode(final PropertyNode propertyNode) {
      return this.enterDefault(propertyNode);
   }

   public R enterReturnNode(final ReturnNode returnNode) {
      return this.enterDefault(returnNode);
   }

   public R enterSwitchNode(final SwitchNode switchNode) {
      return this.enterDefault(switchNode);
   }

   public R enterTernaryNode(final TernaryNode ternaryNode) {
      return this.enterDefault(ternaryNode);
   }

   public R enterThrowNode(final ThrowNode throwNode) {
      return this.enterDefault(throwNode);
   }

   public R enterTryNode(final TryNode tryNode) {
      return this.enterDefault(tryNode);
   }

   public R enterUnaryNode(final UnaryNode unaryNode) {
      return this.enterDefault(unaryNode);
   }

   public R enterJoinPredecessorExpression(final JoinPredecessorExpression expr) {
      return this.enterDefault(expr);
   }

   public R enterVarNode(final VarNode varNode) {
      return this.enterDefault(varNode);
   }

   public R enterWhileNode(final WhileNode whileNode) {
      return this.enterDefault(whileNode);
   }

   public R enterWithNode(final WithNode withNode) {
      return this.enterDefault(withNode);
   }

   public R enterClassNode(ClassNode classNode) {
      return this.enterDefault(classNode);
   }

   public R enterBlockExpression(BlockExpression blockExpression) {
      return this.enterDefault(blockExpression);
   }

   public R enterParameterNode(final ParameterNode paramNode) {
      return this.enterDefault(paramNode);
   }

   public R enterTemplateLiteralNode(final TemplateLiteralNode templateLiteralNode) {
      return this.enterDefault(templateLiteralNode);
   }

   public R enterClassElement(final ClassElement element) {
      return this.enterDefault(element);
   }
}
