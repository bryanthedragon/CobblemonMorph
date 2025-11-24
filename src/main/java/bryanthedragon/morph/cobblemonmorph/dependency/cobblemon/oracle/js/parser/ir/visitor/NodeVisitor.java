
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

    public NodeVisitor(T lc) {
        this.lc = lc;
    }

    public final T getLexicalContext() {
        return this.lc;
    }

    protected boolean enterDefault(Node node) {
        return true;
    }

    protected Node leaveDefault(Node node) {
        return node;
    }

    public boolean enterAccessNode(AccessNode accessNode) {
        return this.enterDefault(accessNode);
    }

    public Node leaveAccessNode(AccessNode accessNode) {
        return this.leaveDefault(accessNode);
    }

    public boolean enterBlock(Block block) {
        return this.enterDefault(block);
    }

    public Node leaveBlock(Block block) {
        return this.leaveDefault(block);
    }

    public boolean enterBinaryNode(BinaryNode binaryNode) {
        return this.enterDefault(binaryNode);
    }

    public Node leaveBinaryNode(BinaryNode binaryNode) {
        return this.leaveDefault(binaryNode);
    }

    public boolean enterBreakNode(BreakNode breakNode) {
        return this.enterDefault(breakNode);
    }

    public Node leaveBreakNode(BreakNode breakNode) {
        return this.leaveDefault(breakNode);
    }

    public boolean enterCallNode(CallNode callNode) {
        return this.enterDefault(callNode);
    }

    public Node leaveCallNode(CallNode callNode) {
        return this.leaveDefault(callNode);
    }

    public boolean enterCaseNode(CaseNode caseNode) {
        return this.enterDefault(caseNode);
    }

    public Node leaveCaseNode(CaseNode caseNode) {
        return this.leaveDefault(caseNode);
    }

    public boolean enterCatchNode(CatchNode catchNode) {
        return this.enterDefault(catchNode);
    }

    public Node leaveCatchNode(CatchNode catchNode) {
        return this.leaveDefault(catchNode);
    }

    public boolean enterContinueNode(ContinueNode continueNode) {
        return this.enterDefault(continueNode);
    }

    public Node leaveContinueNode(ContinueNode continueNode) {
        return this.leaveDefault(continueNode);
    }

    public boolean enterDebuggerNode(DebuggerNode debuggerNode) {
        return this.enterDefault(debuggerNode);
    }

    public Node leaveDebuggerNode(DebuggerNode debuggerNode) {
        return this.leaveDefault(debuggerNode);
    }

    public boolean enterEmptyNode(EmptyNode emptyNode) {
        return this.enterDefault(emptyNode);
    }

    public Node leaveEmptyNode(EmptyNode emptyNode) {
        return this.leaveDefault(emptyNode);
    }

    public boolean enterErrorNode(ErrorNode errorNode) {
        return this.enterDefault(errorNode);
    }

    public Node leaveErrorNode(ErrorNode errorNode) {
        return this.leaveDefault(errorNode);
    }

    public boolean enterNamedExportsNode(NamedExportsNode exportClauseNode) {
        return this.enterDefault(exportClauseNode);
    }

    public Node leaveNamedExportsNode(NamedExportsNode exportClauseNode) {
        return this.leaveDefault(exportClauseNode);
    }

    public boolean enterExportNode(ExportNode exportNode) {
        return this.enterDefault(exportNode);
    }

    public Node leaveExportNode(ExportNode exportNode) {
        return this.leaveDefault(exportNode);
    }

    public boolean enterExportSpecifierNode(ExportSpecifierNode exportSpecifierNode) {
        return this.enterDefault(exportSpecifierNode);
    }

    public Node leaveExportSpecifierNode(ExportSpecifierNode exportSpecifierNode) {
        return this.leaveDefault(exportSpecifierNode);
    }

    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        return this.enterDefault(expressionStatement);
    }

    public Node leaveExpressionStatement(ExpressionStatement expressionStatement) {
        return this.leaveDefault(expressionStatement);
    }

    public boolean enterBlockStatement(BlockStatement blockStatement) {
        return this.enterDefault(blockStatement);
    }

    public Node leaveBlockStatement(BlockStatement blockStatement) {
        return this.leaveDefault(blockStatement);
    }

    public boolean enterForNode(ForNode forNode) {
        return this.enterDefault(forNode);
    }

    public Node leaveForNode(ForNode forNode) {
        return this.leaveDefault(forNode);
    }

    public boolean enterFromNode(FromNode fromNode) {
        return this.enterDefault(fromNode);
    }

    public Node leaveFromNode(FromNode fromNode) {
        return this.leaveDefault(fromNode);
    }

    public boolean enterFunctionNode(FunctionNode functionNode) {
        return this.enterDefault(functionNode);
    }

    public Node leaveFunctionNode(FunctionNode functionNode) {
        return this.leaveDefault(functionNode);
    }

    public boolean enterIdentNode(IdentNode identNode) {
        return this.enterDefault(identNode);
    }

    public Node leaveIdentNode(IdentNode identNode) {
        return this.leaveDefault(identNode);
    }

    public boolean enterIfNode(IfNode ifNode) {
        return this.enterDefault(ifNode);
    }

    public Node leaveIfNode(IfNode ifNode) {
        return this.leaveDefault(ifNode);
    }

    public boolean enterImportClauseNode(ImportClauseNode importClauseNode) {
        return this.enterDefault(importClauseNode);
    }

    public Node leaveImportClauseNode(ImportClauseNode importClauseNode) {
        return this.leaveDefault(importClauseNode);
    }

    public boolean enterImportNode(ImportNode importNode) {
        return this.enterDefault(importNode);
    }

    public Node leaveImportNode(ImportNode importNode) {
        return this.leaveDefault(importNode);
    }

    public boolean enterImportSpecifierNode(ImportSpecifierNode importSpecifierNode) {
        return this.enterDefault(importSpecifierNode);
    }

    public Node leaveImportSpecifierNode(ImportSpecifierNode importSpecifierNode) {
        return this.leaveDefault(importSpecifierNode);
    }

    public boolean enterIndexNode(IndexNode indexNode) {
        return this.enterDefault(indexNode);
    }

    public Node leaveIndexNode(IndexNode indexNode) {
        return this.leaveDefault(indexNode);
    }

    public boolean enterLabelNode(LabelNode labelNode) {
        return this.enterDefault(labelNode);
    }

    public Node leaveLabelNode(LabelNode labelNode) {
        return this.leaveDefault(labelNode);
    }

    public boolean enterLiteralNode(LiteralNode<?> literalNode) {
        return this.enterDefault(literalNode);
    }

    public Node leaveLiteralNode(LiteralNode<?> literalNode) {
        return this.leaveDefault(literalNode);
    }

    public boolean enterNameSpaceImportNode(NameSpaceImportNode nameSpaceImportNode) {
        return this.enterDefault(nameSpaceImportNode);
    }

    public Node leaveNameSpaceImportNode(NameSpaceImportNode nameSpaceImportNode) {
        return this.leaveDefault(nameSpaceImportNode);
    }

    public boolean enterNamedImportsNode(NamedImportsNode namedImportsNode) {
        return this.enterDefault(namedImportsNode);
    }

    public Node leaveNamedImportsNode(NamedImportsNode namedImportsNode) {
        return this.leaveDefault(namedImportsNode);
    }

    public boolean enterObjectNode(ObjectNode objectNode) {
        return this.enterDefault(objectNode);
    }

    public Node leaveObjectNode(ObjectNode objectNode) {
        return this.leaveDefault(objectNode);
    }

    public boolean enterPropertyNode(PropertyNode propertyNode) {
        return this.enterDefault(propertyNode);
    }

    public Node leavePropertyNode(PropertyNode propertyNode) {
        return this.leaveDefault(propertyNode);
    }

    public boolean enterReturnNode(ReturnNode returnNode) {
        return this.enterDefault(returnNode);
    }

    public Node leaveReturnNode(ReturnNode returnNode) {
        return this.leaveDefault(returnNode);
    }

    public boolean enterSwitchNode(SwitchNode switchNode) {
        return this.enterDefault(switchNode);
    }

    public Node leaveSwitchNode(SwitchNode switchNode) {
        return this.leaveDefault(switchNode);
    }

    public boolean enterTernaryNode(TernaryNode ternaryNode) {
        return this.enterDefault(ternaryNode);
    }

    public Node leaveTernaryNode(TernaryNode ternaryNode) {
        return this.leaveDefault(ternaryNode);
    }

    public boolean enterThrowNode(ThrowNode throwNode) {
        return this.enterDefault(throwNode);
    }

    public Node leaveThrowNode(ThrowNode throwNode) {
        return this.leaveDefault(throwNode);
    }

    public boolean enterTryNode(TryNode tryNode) {
        return this.enterDefault(tryNode);
    }

    public Node leaveTryNode(TryNode tryNode) {
        return this.leaveDefault(tryNode);
    }

    public boolean enterUnaryNode(UnaryNode unaryNode) {
        return this.enterDefault(unaryNode);
    }

    public Node leaveUnaryNode(UnaryNode unaryNode) {
        return this.leaveDefault(unaryNode);
    }

    public boolean enterJoinPredecessorExpression(JoinPredecessorExpression expr) {
        return this.enterDefault(expr);
    }

    public Node leaveJoinPredecessorExpression(JoinPredecessorExpression expr) {
        return this.leaveDefault(expr);
    }

    public boolean enterVarNode(VarNode varNode) {
        return this.enterDefault(varNode);
    }

    public Node leaveVarNode(VarNode varNode) {
        return this.leaveDefault(varNode);
    }

    public boolean enterWhileNode(WhileNode whileNode) {
        return this.enterDefault(whileNode);
    }

    public Node leaveWhileNode(WhileNode whileNode) {
        return this.leaveDefault(whileNode);
    }

    public boolean enterWithNode(WithNode withNode) {
        return this.enterDefault(withNode);
    }

    public Node leaveWithNode(WithNode withNode) {
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

    public boolean enterParameterNode(ParameterNode paramNode) {
        return this.enterDefault(paramNode);
    }

    public Node leaveParameterNode(ParameterNode paramNode) {
        return this.leaveDefault(paramNode);
    }

    public boolean enterTemplateLiteralNode(TemplateLiteralNode templateLiteralNode) {
        return this.enterDefault(templateLiteralNode);
    }

    public Node leaveTemplateLiteralNode(TemplateLiteralNode templateLiteralNode) {
        return this.leaveDefault(templateLiteralNode);
    }

    public boolean enterClassElement(ClassElement element) {
        return this.enterDefault(element);
    }

    public Node leaveClassElement(ClassElement element) {
        return this.leaveDefault(element);
    }
}

