
package com.oracle.truffle.js.parser.internal.ir.debug;

import com.oracle.js.parser.ErrorManager;
import com.oracle.js.parser.Lexer;
import com.oracle.js.parser.Parser;
import com.oracle.js.parser.ScriptEnvironment;
import com.oracle.js.parser.Source;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.AccessNode;
import com.oracle.js.parser.ir.BinaryNode;
import com.oracle.js.parser.ir.Block;
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
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.ExpressionStatement;
import com.oracle.js.parser.ir.ForNode;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.IfNode;
import com.oracle.js.parser.ir.IndexNode;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LabelNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.ObjectNode;
import com.oracle.js.parser.ir.PropertyNode;
import com.oracle.js.parser.ir.ReturnNode;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.SwitchNode;
import com.oracle.js.parser.ir.TemplateLiteralNode;
import com.oracle.js.parser.ir.TernaryNode;
import com.oracle.js.parser.ir.ThrowNode;
import com.oracle.js.parser.ir.TryNode;
import com.oracle.js.parser.ir.UnaryNode;
import com.oracle.js.parser.ir.VarNode;
import com.oracle.js.parser.ir.WhileNode;
import com.oracle.js.parser.ir.WithNode;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.truffle.js.parser.json.JSONParserUtil;
import com.oracle.truffle.js.runtime.Strings;
import java.util.ArrayList;
import java.util.List;

public final class JSONWriter
extends NodeVisitor<LexicalContext> {
    private final StringBuilder buf = new StringBuilder();
    private final boolean includeLocation;

    public static String parse(ScriptEnvironment env, String code, String name, boolean includeLoc) {
        Parser parser = new Parser(env, Source.sourceFor(name, code), new ErrorManager.ThrowErrorManager(), env.isStrict());
        JSONWriter jsonWriter = new JSONWriter(includeLoc);
        FunctionNode functionNode = parser.parse();
        functionNode.accept(jsonWriter);
        return jsonWriter.getString();
    }

    @Override
    public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinPredecessorExpression) {
        Expression expr = joinPredecessorExpression.getExpression();
        if (expr != null) {
            expr.accept(this);
        } else {
            this.nullValue();
        }
        return false;
    }

    @Override
    protected boolean enterDefault(Node node) {
        this.objectStart();
        this.location(node);
        return true;
    }

    private boolean leave() {
        this.objectEnd();
        return false;
    }

    @Override
    protected Node leaveDefault(Node node) {
        this.objectEnd();
        return null;
    }

    @Override
    public boolean enterAccessNode(AccessNode accessNode) {
        this.enterDefault(accessNode);
        this.type("MemberExpression");
        this.comma();
        this.property("object");
        accessNode.getBase().accept(this);
        this.comma();
        this.property("property", accessNode.getProperty());
        this.comma();
        this.property("computed", false);
        return this.leave();
    }

    @Override
    public boolean enterBlock(Block block) {
        this.enterDefault(block);
        this.type("BlockStatement");
        this.comma();
        this.array("body", block.getStatements());
        return this.leave();
    }

    @Override
    public boolean enterBinaryNode(BinaryNode binaryNode) {
        this.enterDefault(binaryNode);
        String name = binaryNode.isAssignment() ? "AssignmentExpression" : (binaryNode.isLogical() ? "LogicalExpression" : "BinaryExpression");
        this.type(name);
        this.comma();
        this.property("operator", binaryNode.tokenType().getNameOrType());
        this.comma();
        this.property("left");
        binaryNode.getLhs().accept(this);
        this.comma();
        this.property("right");
        binaryNode.getRhs().accept(this);
        return this.leave();
    }

    @Override
    public boolean enterBreakNode(BreakNode breakNode) {
        this.enterDefault(breakNode);
        this.type("BreakStatement");
        this.comma();
        String label = breakNode.getLabelName();
        if (label != null) {
            this.property("label", label);
        } else {
            this.property("label");
            this.nullValue();
        }
        return this.leave();
    }

    @Override
    public boolean enterCallNode(CallNode callNode) {
        this.enterDefault(callNode);
        this.type("CallExpression");
        this.comma();
        this.property("callee");
        callNode.getFunction().accept(this);
        this.comma();
        this.array("arguments", callNode.getArgs());
        return this.leave();
    }

    @Override
    public boolean enterCaseNode(CaseNode caseNode) {
        this.enterDefault(caseNode);
        this.type("SwitchCase");
        this.comma();
        Expression test = caseNode.getTest();
        this.property("test");
        if (test != null) {
            test.accept(this);
        } else {
            this.nullValue();
        }
        this.comma();
        this.array("consequent", caseNode.getStatements());
        return this.leave();
    }

    @Override
    public boolean enterCatchNode(CatchNode catchNode) {
        Expression guard;
        this.enterDefault(catchNode);
        this.type("CatchClause");
        this.comma();
        Expression catchParameter = catchNode.getException();
        if (catchParameter != null) {
            this.property("param");
            catchParameter.accept(this);
            this.comma();
        }
        if ((guard = catchNode.getExceptionCondition()) != null) {
            this.property("guard");
            guard.accept(this);
            this.comma();
        }
        this.property("body");
        catchNode.getBody().accept(this);
        return this.leave();
    }

    @Override
    public boolean enterContinueNode(ContinueNode continueNode) {
        this.enterDefault(continueNode);
        this.type("ContinueStatement");
        this.comma();
        String label = continueNode.getLabelName();
        if (label != null) {
            this.property("label", label);
        } else {
            this.property("label");
            this.nullValue();
        }
        return this.leave();
    }

    @Override
    public boolean enterDebuggerNode(DebuggerNode debuggerNode) {
        this.enterDefault(debuggerNode);
        this.type("DebuggerStatement");
        return this.leave();
    }

    @Override
    public boolean enterEmptyNode(EmptyNode emptyNode) {
        this.enterDefault(emptyNode);
        this.type("EmptyStatement");
        return this.leave();
    }

    @Override
    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        Expression expression = expressionStatement.getExpression();
        this.enterDefault(expressionStatement);
        this.type("ExpressionStatement");
        this.comma();
        this.property("expression");
        expression.accept(this);
        return this.leave();
    }

    @Override
    public boolean enterBlockStatement(BlockStatement blockStatement) {
        if (blockStatement.isSynthetic()) {
            Block blk = blockStatement.getBlock();
            blk.getStatements().get(0).accept(this);
            return false;
        }
        this.enterDefault(blockStatement);
        this.type("BlockStatement");
        this.comma();
        this.array("body", blockStatement.getBlock().getStatements());
        return this.leave();
    }

    @Override
    public boolean enterForNode(ForNode forNode) {
        this.enterDefault(forNode);
        if (forNode.isForIn() || forNode.isForEach() && forNode.getInit() != null) {
            this.type("ForInStatement");
            this.comma();
            Expression init2 = forNode.getInit();
            assert (init2 != null);
            this.property("left");
            init2.accept(this);
            this.comma();
            JoinPredecessorExpression modify = forNode.getModify();
            assert (modify != null);
            this.property("right");
            ((Node)modify).accept(this);
            this.comma();
            this.property("body");
            forNode.getBody().accept(this);
            this.comma();
            this.property("each", forNode.isForEach());
        } else {
            this.type("ForStatement");
            this.comma();
            Expression init3 = forNode.getInit();
            this.property("init");
            if (init3 != null) {
                init3.accept(this);
            } else {
                this.nullValue();
            }
            this.comma();
            JoinPredecessorExpression test = forNode.getTest();
            this.property("test");
            if (test != null) {
                ((Node)test).accept(this);
            } else {
                this.nullValue();
            }
            this.comma();
            JoinPredecessorExpression update = forNode.getModify();
            this.property("update");
            if (update != null) {
                ((Node)update).accept(this);
            } else {
                this.nullValue();
            }
            this.comma();
            this.property("body");
            forNode.getBody().accept(this);
        }
        return this.leave();
    }

    @Override
    public boolean enterFunctionNode(FunctionNode functionNode) {
        boolean program = functionNode.isProgram();
        if (program) {
            return this.emitProgram(functionNode);
        }
        this.enterDefault(functionNode);
        String name = functionNode.isDeclared() ? "FunctionDeclaration" : "FunctionExpression";
        this.type(name);
        this.comma();
        this.property("id");
        if (functionNode.isAnonymous() || functionNode.isGetter() || functionNode.isSetter()) {
            this.nullValue();
        } else {
            functionNode.getIdent().accept(this);
        }
        this.comma();
        this.array("params", functionNode.getParameters());
        this.comma();
        this.arrayStart("defaults");
        this.arrayEnd();
        this.comma();
        this.property("rest");
        this.nullValue();
        this.comma();
        this.property("body");
        functionNode.getBody().accept(this);
        this.comma();
        this.property("generator", false);
        this.comma();
        this.property("expression", false);
        return this.leave();
    }

    @Override
    public boolean enterClassNode(ClassNode classNode) {
        ClassElement constructor;
        this.enterDefault(classNode);
        this.type("Class");
        this.comma();
        this.property("id");
        if (classNode.isAnonymous()) {
            this.nullValue();
        } else {
            classNode.getIdent().accept(this);
        }
        this.comma();
        Expression classHeritage = classNode.getClassHeritage();
        if (classHeritage != null) {
            this.property("classHeritage");
            classHeritage.accept(this);
            this.comma();
        }
        if ((constructor = classNode.getConstructor()) != null) {
            this.property("constructor");
            constructor.getValue().accept(this);
            this.comma();
        }
        this.array("classElements", classNode.getClassElements());
        return this.leave();
    }

    private boolean emitProgram(FunctionNode functionNode) {
        this.enterDefault(functionNode);
        this.type("Program");
        this.comma();
        List<Statement> stats = functionNode.getBody().getStatements();
        int size = stats.size();
        int idx = 0;
        this.arrayStart("body");
        for (Node node : stats) {
            node.accept(this);
            if (idx != size - 1) {
                this.comma();
            }
            ++idx;
        }
        this.arrayEnd();
        return this.leave();
    }

    @Override
    public boolean enterIdentNode(IdentNode identNode) {
        this.enterDefault(identNode);
        String name = identNode.getName();
        if ("this".equals(name)) {
            this.type("ThisExpression");
        } else {
            this.type("Identifier");
            this.comma();
            this.property("name", identNode.getName());
        }
        return this.leave();
    }

    @Override
    public boolean enterIfNode(IfNode ifNode) {
        this.enterDefault(ifNode);
        this.type("IfStatement");
        this.comma();
        this.property("test");
        ifNode.getTest().accept(this);
        this.comma();
        this.property("consequent");
        ifNode.getPass().accept(this);
        Block elsePart = ifNode.getFail();
        this.comma();
        this.property("alternate");
        if (elsePart != null) {
            ((Node)elsePart).accept(this);
        } else {
            this.nullValue();
        }
        return this.leave();
    }

    @Override
    public boolean enterIndexNode(IndexNode indexNode) {
        this.enterDefault(indexNode);
        this.type("MemberExpression");
        this.comma();
        this.property("object");
        indexNode.getBase().accept(this);
        this.comma();
        this.property("property");
        indexNode.getIndex().accept(this);
        this.comma();
        this.property("computed", true);
        return this.leave();
    }

    @Override
    public boolean enterLabelNode(LabelNode labelNode) {
        this.enterDefault(labelNode);
        this.type("LabeledStatement");
        this.comma();
        this.property("label", labelNode.getLabelName());
        this.comma();
        this.property("body");
        labelNode.getBody().accept(this);
        return this.leave();
    }

    @Override
    public boolean enterLiteralNode(LiteralNode literalNode) {
        this.enterDefault(literalNode);
        if (literalNode instanceof LiteralNode.ArrayLiteralNode) {
            this.type("ArrayExpression");
            this.comma();
            this.array("elements", ((LiteralNode.ArrayLiteralNode)literalNode).getElementExpressions());
        } else {
            this.type("Literal");
            this.comma();
            this.property("value");
            Object value2 = literalNode.getValue();
            if (value2 instanceof Lexer.RegexToken) {
                Lexer.RegexToken regex = (Lexer.RegexToken)value2;
                StringBuilder regexBuf = new StringBuilder();
                regexBuf.append('/');
                regexBuf.append(regex.getExpression());
                regexBuf.append('/');
                regexBuf.append(regex.getOptions());
                this.buf.append(JSONWriter.quote(regexBuf.toString()));
            } else if (value2 != null && value2.equals(Double.POSITIVE_INFINITY)) {
                this.buf.append("\"Infinity\"");
            } else {
                String str = literalNode.getString();
                this.buf.append(literalNode.isString() ? JSONWriter.quote("$" + str) : str);
            }
        }
        return this.leave();
    }

    @Override
    public boolean enterObjectNode(ObjectNode objectNode) {
        this.enterDefault(objectNode);
        this.type("ObjectExpression");
        this.comma();
        this.array("properties", objectNode.getElements());
        return this.leave();
    }

    @Override
    public boolean enterPropertyNode(PropertyNode propertyNode) {
        Expression key = propertyNode.getKey();
        FunctionNode getter = propertyNode.getGetter();
        FunctionNode setter = propertyNode.getSetter();
        if (getter == null && setter == null) {
            this.objectStart();
            this.location(propertyNode);
            this.property("key");
            key.accept(this);
            this.comma();
            Expression value2 = propertyNode.getValue();
            if (value2 != null) {
                this.property("value");
                value2.accept(this);
                this.comma();
            }
            this.property("kind", "init");
            this.objectEnd();
        } else {
            if (getter != null) {
                this.objectStart();
                this.location(propertyNode);
                this.property("key");
                key.accept(this);
                this.comma();
                this.property("value");
                ((Node)getter).accept(this);
                this.comma();
                this.property("kind", "get");
                this.objectEnd();
            }
            if (setter != null) {
                if (getter != null) {
                    this.comma();
                }
                this.objectStart();
                this.location(propertyNode);
                this.property("key");
                key.accept(this);
                this.comma();
                this.property("value");
                ((Node)setter).accept(this);
                this.comma();
                this.property("kind", "set");
                this.objectEnd();
            }
        }
        return false;
    }

    @Override
    public boolean enterReturnNode(ReturnNode returnNode) {
        this.enterDefault(returnNode);
        this.type("ReturnStatement");
        this.comma();
        Expression arg = returnNode.getExpression();
        this.property("argument");
        if (arg != null) {
            arg.accept(this);
        } else {
            this.nullValue();
        }
        return this.leave();
    }

    @Override
    public boolean enterSwitchNode(SwitchNode switchNode) {
        this.enterDefault(switchNode);
        this.type("SwitchStatement");
        this.comma();
        this.property("discriminant");
        switchNode.getExpression().accept(this);
        this.comma();
        this.array("cases", switchNode.getCases());
        return this.leave();
    }

    @Override
    public boolean enterTernaryNode(TernaryNode ternaryNode) {
        this.enterDefault(ternaryNode);
        this.type("ConditionalExpression");
        this.comma();
        this.property("test");
        ternaryNode.getTest().accept(this);
        this.comma();
        this.property("consequent");
        ternaryNode.getTrueExpression().accept(this);
        this.comma();
        this.property("alternate");
        ternaryNode.getFalseExpression().accept(this);
        return this.leave();
    }

    @Override
    public boolean enterThrowNode(ThrowNode throwNode) {
        this.enterDefault(throwNode);
        this.type("ThrowStatement");
        this.comma();
        this.property("argument");
        throwNode.getExpression().accept(this);
        return this.leave();
    }

    @Override
    public boolean enterTryNode(TryNode tryNode) {
        this.enterDefault(tryNode);
        this.type("TryStatement");
        this.comma();
        this.property("block");
        tryNode.getBody().accept(this);
        this.comma();
        List<CatchNode> catches = tryNode.getCatches();
        ArrayList<CatchNode> guarded = new ArrayList<CatchNode>();
        CatchNode unguarded = null;
        if (catches != null) {
            for (CatchNode cn : catches) {
                if (cn.getExceptionCondition() != null) {
                    guarded.add(cn);
                    continue;
                }
                assert (unguarded == null) : "too many unguarded?";
                unguarded = cn;
            }
        }
        this.array("guardedHandlers", guarded);
        this.comma();
        this.property("handler");
        if (unguarded != null) {
            unguarded.accept(this);
        } else {
            this.nullValue();
        }
        this.comma();
        this.property("finalizer");
        Block finallyNode = tryNode.getFinallyBody();
        if (finallyNode != null) {
            ((Node)finallyNode).accept(this);
        } else {
            this.nullValue();
        }
        return this.leave();
    }

    @Override
    public boolean enterUnaryNode(UnaryNode unaryNode) {
        this.enterDefault(unaryNode);
        TokenType tokenType = unaryNode.tokenType();
        if (tokenType == TokenType.NEW) {
            this.type("NewExpression");
            this.comma();
            CallNode callNode = (CallNode)unaryNode.getExpression();
            this.property("callee");
            callNode.getFunction().accept(this);
            this.comma();
            this.array("arguments", callNode.getArgs());
        } else {
            String operator;
            boolean prefix;
            switch (tokenType) {
                case INCPOSTFIX: {
                    prefix = false;
                    operator = "++";
                    break;
                }
                case DECPOSTFIX: {
                    prefix = false;
                    operator = "--";
                    break;
                }
                case INCPREFIX: {
                    operator = "++";
                    prefix = true;
                    break;
                }
                case DECPREFIX: {
                    operator = "--";
                    prefix = true;
                    break;
                }
                case SPREAD_ARGUMENT: 
                case SPREAD_ARRAY: 
                case SPREAD_OBJECT: {
                    operator = "...";
                    prefix = true;
                    break;
                }
                case YIELD_STAR: {
                    operator = "yield*";
                    prefix = true;
                    break;
                }
                default: {
                    prefix = true;
                    operator = tokenType.getName();
                }
            }
            this.type(unaryNode.isAssignment() ? "UpdateExpression" : "UnaryExpression");
            this.comma();
            this.property("operator", operator);
            this.comma();
            this.property("prefix", prefix);
            this.comma();
            this.property("argument");
            unaryNode.getExpression().accept(this);
        }
        return this.leave();
    }

    @Override
    public boolean enterVarNode(VarNode varNode) {
        Expression init2 = varNode.getInit();
        if (init2 instanceof FunctionNode && ((FunctionNode)init2).isDeclared()) {
            init2.accept(this);
            return false;
        }
        this.enterDefault(varNode);
        this.type("VariableDeclaration");
        this.comma();
        this.arrayStart("declarations");
        this.objectStart();
        this.location(varNode.getName());
        this.type("VariableDeclarator");
        this.comma();
        this.property("id");
        varNode.getName().accept(this);
        this.comma();
        this.property("init");
        if (init2 != null) {
            init2.accept(this);
        } else {
            this.nullValue();
        }
        this.objectEnd();
        this.arrayEnd();
        return this.leave();
    }

    @Override
    public boolean enterWhileNode(WhileNode whileNode) {
        this.enterDefault(whileNode);
        this.type(whileNode.isDoWhile() ? "DoWhileStatement" : "WhileStatement");
        this.comma();
        if (whileNode.isDoWhile()) {
            this.property("body");
            whileNode.getBody().accept(this);
            this.comma();
            this.property("test");
            whileNode.getTest().accept(this);
        } else {
            this.property("test");
            whileNode.getTest().accept(this);
            this.comma();
            this.property("body");
            whileNode.getBody().accept(this);
        }
        return this.leave();
    }

    @Override
    public boolean enterWithNode(WithNode withNode) {
        this.enterDefault(withNode);
        this.type("WithStatement");
        this.comma();
        this.property("object");
        withNode.getExpression().accept(this);
        this.comma();
        this.property("body");
        withNode.getBody().accept(this);
        return this.leave();
    }

    @Override
    public boolean enterTemplateLiteralNode(TemplateLiteralNode templateLiteralNode) {
        this.enterDefault(templateLiteralNode);
        this.type("TemplateLiteral");
        this.comma();
        if (templateLiteralNode instanceof TemplateLiteralNode.TaggedTemplateLiteralNode) {
            this.array("elements", ((TemplateLiteralNode.TaggedTemplateLiteralNode)templateLiteralNode).getCookedStrings());
        } else {
            this.array("elements", ((TemplateLiteralNode.UntaggedTemplateLiteralNode)templateLiteralNode).getExpressions());
        }
        return this.leave();
    }

    private JSONWriter(boolean includeLocation) {
        super(new LexicalContext());
        this.includeLocation = includeLocation;
    }

    private String getString() {
        return this.buf.toString();
    }

    private void property(String key, String value2, boolean escape) {
        this.buf.append('\"');
        this.buf.append(key);
        this.buf.append("\":");
        if (value2 != null) {
            if (escape) {
                this.buf.append('\"');
            }
            this.buf.append(value2);
            if (escape) {
                this.buf.append('\"');
            }
        }
    }

    private void property(String key, String value2) {
        this.property(key, value2, true);
    }

    private void property(String key, boolean value2) {
        this.property(key, Boolean.toString(value2), false);
    }

    private void property(String key, int value2) {
        this.property(key, Integer.toString(value2), false);
    }

    private void property(String key) {
        this.property(key, null);
    }

    private void type(String value2) {
        this.property("type", value2);
    }

    private void objectStart(String name) {
        this.buf.append('\"');
        this.buf.append(name);
        this.buf.append("\":{");
    }

    private void objectStart() {
        this.buf.append('{');
    }

    private void objectEnd() {
        this.buf.append('}');
    }

    private void array(String name, List<? extends Node> nodes) {
        int size = nodes.size();
        int idx = 0;
        this.arrayStart(name);
        for (Node node : nodes) {
            if (node != null) {
                node.accept(this);
            } else {
                this.nullValue();
            }
            if (idx != size - 1) {
                this.comma();
            }
            ++idx;
        }
        this.arrayEnd();
    }

    private void arrayStart(String name) {
        this.buf.append('\"');
        this.buf.append(name);
        this.buf.append('\"');
        this.buf.append(':');
        this.buf.append('[');
    }

    private void arrayEnd() {
        this.buf.append(']');
    }

    private void comma() {
        this.buf.append(',');
    }

    private void nullValue() {
        this.buf.append("null");
    }

    private void location(Node node) {
        if (this.includeLocation) {
            this.objectStart("loc");
            Source src = this.lc.getCurrentFunction().getSource();
            this.property("source", src.getName());
            this.comma();
            this.objectStart("start");
            int start2 = node.getStart();
            this.property("line", src.getLine(start2));
            this.comma();
            this.property("column", src.getColumn(start2));
            this.objectEnd();
            this.comma();
            this.objectStart("end");
            int end2 = node.getFinish();
            this.property("line", src.getLine(end2));
            this.comma();
            this.property("column", src.getColumn(end2));
            this.objectEnd();
            this.objectEnd();
            this.comma();
        }
    }

    private static String quote(String str) {
        return Strings.toJavaString(JSONParserUtil.quote(Strings.fromJavaString(str)));
    }
}

