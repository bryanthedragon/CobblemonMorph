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

public abstract class NodeVisitor<T extends LexicalContext> {
   protected final T lc;

   public NodeVisitor(final T lc) {
      this.lc = lc;
   }

   public final T getLexicalContext() {
      return this.lc;
   }

   protected boolean enterDefault(final Node node) {
      return true;
   }

   protected Node leaveDefault(final Node node) {
      return node;
   }

   public boolean enterAccessNode(final AccessNode accessNode) {
      return this.enterDefault(accessNode);
   }

   public Node leaveAccessNode(final AccessNode accessNode) {
      return this.leaveDefault(accessNode);
   }

   public boolean enterBlock(final Block block) {
      return this.enterDefault(block);
   }

   public Node leaveBlock(final Block block) {
      return this.leaveDefault(block);
   }

   public boolean enterBinaryNode(final BinaryNode binaryNode) {
      return this.enterDefault(binaryNode);
   }

   public Node leaveBinaryNode(final BinaryNode binaryNode) {
      return this.leaveDefault(binaryNode);
   }

   public boolean enterBreakNode(final BreakNode breakNode) {
      return this.enterDefault(breakNode);
   }

   public Node leaveBreakNode(final BreakNode breakNode) {
      return this.leaveDefault(breakNode);
   }

   public boolean enterCallNode(final CallNode callNode) {
      return this.enterDefault(callNode);
   }

   public Node leaveCallNode(final CallNode callNode) {
      return this.leaveDefault(callNode);
   }

   public boolean enterCaseNode(final CaseNode caseNode) {
      return this.enterDefault(caseNode);
   }

   public Node leaveCaseNode(final CaseNode caseNode) {
      return this.leaveDefault(caseNode);
   }

   public boolean enterCatchNode(final CatchNode catchNode) {
      return this.enterDefault(catchNode);
   }

   public Node leaveCatchNode(final CatchNode catchNode) {
      return this.leaveDefault(catchNode);
   }

   public boolean enterContinueNode(final ContinueNode continueNode) {
      return this.enterDefault(continueNode);
   }

   public Node leaveContinueNode(final ContinueNode continueNode) {
      return this.leaveDefault(continueNode);
   }

   public boolean enterDebuggerNode(final DebuggerNode debuggerNode) {
      return this.enterDefault(debuggerNode);
   }

   public Node leaveDebuggerNode(final DebuggerNode debuggerNode) {
      return this.leaveDefault(debuggerNode);
   }

   public boolean enterEmptyNode(final EmptyNode emptyNode) {
      return this.enterDefault(emptyNode);
   }

   public Node leaveEmptyNode(final EmptyNode emptyNode) {
      return this.leaveDefault(emptyNode);
   }

   public boolean enterErrorNode(final ErrorNode errorNode) {
      return this.enterDefault(errorNode);
   }

   public Node leaveErrorNode(final ErrorNode errorNode) {
      return this.leaveDefault(errorNode);
   }

   public boolean enterNamedExportsNode(final NamedExportsNode exportClauseNode) {
      return this.enterDefault(exportClauseNode);
   }

   public Node leaveNamedExportsNode(final NamedExportsNode exportClauseNode) {
      return this.leaveDefault(exportClauseNode);
   }

   public boolean enterExportNode(final ExportNode exportNode) {
      return this.enterDefault(exportNode);
   }

   public Node leaveExportNode(final ExportNode exportNode) {
      return this.leaveDefault(exportNode);
   }

   public boolean enterExportSpecifierNode(final ExportSpecifierNode exportSpecifierNode) {
      return this.enterDefault(exportSpecifierNode);
   }

   public Node leaveExportSpecifierNode(final ExportSpecifierNode exportSpecifierNode) {
      return this.leaveDefault(exportSpecifierNode);
   }

   public boolean enterExpressionStatement(final ExpressionStatement expressionStatement) {
      return this.enterDefault(expressionStatement);
   }

   public Node leaveExpressionStatement(final ExpressionStatement expressionStatement) {
      return this.leaveDefault(expressionStatement);
   }

   public boolean enterBlockStatement(final BlockStatement blockStatement) {
      return this.enterDefault(blockStatement);
   }

   public Node leaveBlockStatement(final BlockStatement blockStatement) {
      return this.leaveDefault(blockStatement);
   }

   public boolean enterForNode(final ForNode forNode) {
      return this.enterDefault(forNode);
   }

   public Node leaveForNode(final ForNode forNode) {
      return this.leaveDefault(forNode);
   }

   public boolean enterFromNode(final FromNode fromNode) {
      return this.enterDefault(fromNode);
   }

   public Node leaveFromNode(final FromNode fromNode) {
      return this.leaveDefault(fromNode);
   }

   public boolean enterFunctionNode(final FunctionNode functionNode) {
      return this.enterDefault(functionNode);
   }

   public Node leaveFunctionNode(final FunctionNode functionNode) {
      return this.leaveDefault(functionNode);
   }

   public boolean enterIdentNode(final IdentNode identNode) {
      return this.enterDefault(identNode);
   }

   public Node leaveIdentNode(final IdentNode identNode) {
      return this.leaveDefault(identNode);
   }

   public boolean enterIfNode(final IfNode ifNode) {
      return this.enterDefault(ifNode);
   }

   public Node leaveIfNode(final IfNode ifNode) {
      return this.leaveDefault(ifNode);
   }

   public boolean enterImportClauseNode(final ImportClauseNode importClauseNode) {
      return this.enterDefault(importClauseNode);
   }

   public Node leaveImportClauseNode(final ImportClauseNode importClauseNode) {
      return this.leaveDefault(importClauseNode);
   }

   public boolean enterImportNode(final ImportNode importNode) {
      return this.enterDefault(importNode);
   }

   public Node leaveImportNode(final ImportNode importNode) {
      return this.leaveDefault(importNode);
   }

   public boolean enterImportSpecifierNode(final ImportSpecifierNode importSpecifierNode) {
      return this.enterDefault(importSpecifierNode);
   }

   public Node leaveImportSpecifierNode(final ImportSpecifierNode importSpecifierNode) {
      return this.leaveDefault(importSpecifierNode);
   }

   public boolean enterIndexNode(final IndexNode indexNode) {
      return this.enterDefault(indexNode);
   }

   public Node leaveIndexNode(final IndexNode indexNode) {
      return this.leaveDefault(indexNode);
   }

   public boolean enterLabelNode(final LabelNode labelNode) {
      return this.enterDefault(labelNode);
   }

   public Node leaveLabelNode(final LabelNode labelNode) {
      return this.leaveDefault(labelNode);
   }

   public boolean enterLiteralNode(final LiteralNode<?> literalNode) {
      return this.enterDefault(literalNode);
   }

   public Node leaveLiteralNode(final LiteralNode<?> literalNode) {
      return this.leaveDefault(literalNode);
   }

   public boolean enterNameSpaceImportNode(final NameSpaceImportNode nameSpaceImportNode) {
      return this.enterDefault(nameSpaceImportNode);
   }

   public Node leaveNameSpaceImportNode(final NameSpaceImportNode nameSpaceImportNode) {
      return this.leaveDefault(nameSpaceImportNode);
   }

   public boolean enterNamedImportsNode(final NamedImportsNode namedImportsNode) {
      return this.enterDefault(namedImportsNode);
   }

   public Node leaveNamedImportsNode(final NamedImportsNode namedImportsNode) {
      return this.leaveDefault(namedImportsNode);
   }

   public boolean enterObjectNode(final ObjectNode objectNode) {
      return this.enterDefault(objectNode);
   }

   public Node leaveObjectNode(final ObjectNode objectNode) {
      return this.leaveDefault(objectNode);
   }

   public boolean enterPropertyNode(final PropertyNode propertyNode) {
      return this.enterDefault(propertyNode);
   }

   public Node leavePropertyNode(final PropertyNode propertyNode) {
      return this.leaveDefault(propertyNode);
   }

   public boolean enterReturnNode(final ReturnNode returnNode) {
      return this.enterDefault(returnNode);
   }

   public Node leaveReturnNode(final ReturnNode returnNode) {
      return this.leaveDefault(returnNode);
   }

   public boolean enterSwitchNode(final SwitchNode switchNode) {
      return this.enterDefault(switchNode);
   }

   public Node leaveSwitchNode(final SwitchNode switchNode) {
      return this.leaveDefault(switchNode);
   }

   public boolean enterTernaryNode(final TernaryNode ternaryNode) {
      return this.enterDefault(ternaryNode);
   }

   public Node leaveTernaryNode(final TernaryNode ternaryNode) {
      return this.leaveDefault(ternaryNode);
   }

   public boolean enterThrowNode(final ThrowNode throwNode) {
      return this.enterDefault(throwNode);
   }

   public Node leaveThrowNode(final ThrowNode throwNode) {
      return this.leaveDefault(throwNode);
   }

   public boolean enterTryNode(final TryNode tryNode) {
      return this.enterDefault(tryNode);
   }

   public Node leaveTryNode(final TryNode tryNode) {
      return this.leaveDefault(tryNode);
   }

   public boolean enterUnaryNode(final UnaryNode unaryNode) {
      return this.enterDefault(unaryNode);
   }

   public Node leaveUnaryNode(final UnaryNode unaryNode) {
      return this.leaveDefault(unaryNode);
   }

   public boolean enterJoinPredecessorExpression(final JoinPredecessorExpression expr) {
      return this.enterDefault(expr);
   }

   public Node leaveJoinPredecessorExpression(final JoinPredecessorExpression expr) {
      return this.leaveDefault(expr);
   }

   public boolean enterVarNode(final VarNode varNode) {
      return this.enterDefault(varNode);
   }

   public Node leaveVarNode(final VarNode varNode) {
      return this.leaveDefault(varNode);
   }

   public boolean enterWhileNode(final WhileNode whileNode) {
      return this.enterDefault(whileNode);
   }

   public Node leaveWhileNode(final WhileNode whileNode) {
      return this.leaveDefault(whileNode);
   }

   public boolean enterWithNode(final WithNode withNode) {
      return this.enterDefault(withNode);
   }

   public Node leaveWithNode(final WithNode withNode) {
      return this.leaveDefault(withNode);
   }

   public boolean enterClassNode(ClassNode classNode) {
      return this.enterDefault(classNode);
   }

   public Node leaveClassNode(ClassNode classNode) {
      return this.leaveDefault(classNode);
   }

   public boolean enterBlockExpression(BlockExpression blockExpression) {
      return this.enterDefault(blockExpression);
   }

   public Node leaveBlockExpression(BlockExpression blockExpression) {
      return this.leaveDefault(blockExpression);
   }

   public boolean enterParameterNode(final ParameterNode paramNode) {
      return this.enterDefault(paramNode);
   }

   public Node leaveParameterNode(final ParameterNode paramNode) {
      return this.leaveDefault(paramNode);
   }

   public boolean enterTemplateLiteralNode(final TemplateLiteralNode templateLiteralNode) {
      return this.enterDefault(templateLiteralNode);
   }

   public Node leaveTemplateLiteralNode(final TemplateLiteralNode templateLiteralNode) {
      return this.leaveDefault(templateLiteralNode);
   }

   public boolean enterClassElement(final ClassElement element) {
      return this.enterDefault(element);
   }

   public Node leaveClassElement(final ClassElement element) {
      return this.leaveDefault(element);
   }
}
