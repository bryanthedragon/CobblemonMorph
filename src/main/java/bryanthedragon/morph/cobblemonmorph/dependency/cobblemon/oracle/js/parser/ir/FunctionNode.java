
package com.oracle.js.parser.ir;

import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.BlockStatement;
import com.oracle.js.parser.ir.Flags;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextExpression;
import com.oracle.js.parser.ir.Module;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class FunctionNode
extends LexicalContextExpression
implements Flags<FunctionNode> {
    private final Source source;
    private final Object endParserState;
    private final IdentNode ident;
    private final Block body;
    private final TruffleString name;
    private final List<IdentNode> parameters;
    private final long firstToken;
    private final long lastToken;
    private final int flags;
    private final int lineNumber;
    private final int numOfParams;
    private final int length;
    private final Module module;
    private final TruffleString internalName;
    private boolean usesAncestorScope;
    public static final int IS_ANONYMOUS = 1;
    public static final int IS_DECLARED = 2;
    public static final int IS_STRICT = 4;
    public static final int USES_ARGUMENTS = 8;
    public static final int IS_STATEMENT = 16;
    public static final int HAS_EVAL = 32;
    public static final int HAS_NESTED_EVAL = 64;
    public static final int HAS_SCOPE_BLOCK = 128;
    public static final int DEFINES_ARGUMENTS = 256;
    public static final int USES_ANCESTOR_SCOPE = 512;
    public static final int IS_SCRIPT = 1024;
    public static final int IS_GETTER = 2048;
    public static final int IS_SETTER = 4096;
    public static final int IS_PROGRAM = 8192;
    public static final int HAS_CLOSURES = 16384;
    public static final int USES_THIS = 32768;
    private static final int HAS_DEEP_EVAL = 96;
    private static final int MAYBE_NEEDS_ARGUMENTS = 40;
    public static final int NEEDS_PARENT_SCOPE = 8800;
    public static final int IS_ARROW = 65536;
    public static final int IS_MODULE = 131072;
    public static final int HAS_DIRECT_SUPER = 262144;
    public static final int USES_SUPER = 524288;
    public static final int IS_METHOD = 0x100000;
    public static final int NO_FUNCTION_SELF = 1056771;
    public static final int IS_CLASS_CONSTRUCTOR = 0x200000;
    public static final int IS_DERIVED_CONSTRUCTOR = 0x400000;
    public static final int USES_NEW_TARGET = 0x800000;
    public static final int IS_GENERATOR = 0x1000000;
    public static final int IS_ASYNC = 0x2000000;
    public static final int HAS_NON_SIMPLE_PARAMETER_LIST = 0x4000000;
    public static final int HAS_ARROW_EVAL = 0x8000000;
    public static final int HAS_FUNCTION_DECLARATIONS = 0x10000000;
    public static final int HAS_APPLY_ARGUMENTS_CALL = 0x20000000;
    public static final int IS_CLASS_FIELD_INITIALIZER = 0x40000000;
    public static final int ARROW_HEAD_FLAGS = 134791400;

    public FunctionNode(Source source, int lineNumber, long token, int finish, long firstToken, long lastToken, IdentNode ident, TruffleString name, int length, int numOfParams, List<IdentNode> parameters, int flags, Block body, Object endParserState, Module module, TruffleString internalName) {
        super(token, Token.descPosition(firstToken), finish);
        this.source = source;
        this.lineNumber = lineNumber;
        this.ident = ident;
        this.name = Objects.requireNonNull(name);
        this.length = length;
        this.numOfParams = numOfParams;
        this.parameters = List.copyOf(parameters);
        this.firstToken = firstToken;
        this.lastToken = lastToken;
        this.flags = flags;
        this.body = body;
        this.endParserState = endParserState;
        this.module = module;
        this.internalName = internalName;
    }

    private FunctionNode(FunctionNode functionNode, long lastToken, Object endParserState, int flags, TruffleString name, Block body, List<IdentNode> parameters, Source source) {
        super(functionNode);
        this.endParserState = endParserState;
        this.lineNumber = functionNode.lineNumber;
        this.flags = flags;
        this.name = Objects.requireNonNull(name);
        this.lastToken = lastToken;
        this.body = body;
        this.parameters = parameters;
        this.source = source;
        this.ident = functionNode.ident;
        this.firstToken = functionNode.firstToken;
        this.length = functionNode.length;
        this.numOfParams = functionNode.numOfParams;
        this.module = functionNode.module;
        this.internalName = functionNode.internalName;
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterFunctionNode(this)) {
            return visitor.leaveFunctionNode(this.setBody(lc, (Block)this.body.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterFunctionNode(this);
    }

    public Source getSource() {
        return this.source;
    }

    public int getId() {
        return this.isProgram() ? -1 : Token.descPosition(this.firstToken);
    }

    public String getSourceName() {
        return FunctionNode.getSourceName(this.source);
    }

    public static String getSourceName(Source source) {
        String explicitURL = source.getExplicitURL();
        return explicitURL != null ? explicitURL : source.getName();
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public void toString(StringBuilder sb, boolean printTypes) {
        if (this.isAsync()) {
            sb.append("async ");
        }
        sb.append("function");
        if (this.isGenerator()) {
            sb.append('*');
        }
        if (this.ident != null) {
            sb.append(' ');
            this.ident.toString(sb, printTypes);
        } else if (!this.name.isEmpty()) {
            sb.append(' ').append(this.name);
        } else if (this.internalName != null && !this.internalName.isEmpty()) {
            sb.append(' ').append(this.internalName);
        }
        this.toStringTail(sb, printTypes);
    }

    void toStringTail(StringBuilder sb, boolean printTypes) {
        sb.append('(');
        Iterator<IdentNode> iter = this.parameters.iterator();
        while (iter.hasNext()) {
            IdentNode parameter = iter.next();
            parameter.toString(sb, printTypes);
            if (!iter.hasNext()) continue;
            sb.append(", ");
        }
        sb.append(')');
    }

    @Override
    public int getFlags() {
        return this.flags;
    }

    @Override
    public boolean getFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    @Override
    public FunctionNode setFlags(LexicalContext lc, int flags) {
        if (this.flags == flags) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, flags, this.name, this.body, this.parameters, this.source));
    }

    @Override
    public FunctionNode setFlag(LexicalContext lc, int flag) {
        return this.setFlags(lc, this.flags | flag);
    }

    public boolean isProgram() {
        return this.getFlag(8192);
    }

    public boolean hasEval() {
        return this.getFlag(32);
    }

    public long getFirstToken() {
        return this.firstToken;
    }

    public boolean usesThis() {
        return this.getFlag(32768);
    }

    public IdentNode getIdent() {
        return this.ident;
    }

    public Block getBody() {
        return this.body;
    }

    public Block getVarDeclarationBlock() {
        if (this.body.isParameterBlock()) {
            return ((BlockStatement)this.body.getLastStatement()).getBlock();
        }
        return this.body;
    }

    public FunctionNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags | (body.needsScope() ? 128 : 0), this.name, body, this.parameters, this.source));
    }

    public boolean needsDynamicScope() {
        return this.hasEval() && !this.isStrict();
    }

    public boolean needsArguments() {
        return this.getFlag(40) && !this.getFlag(0x40010100) && !this.isProgram();
    }

    public long getLastToken() {
        return this.lastToken;
    }

    public Object getEndParserState() {
        return this.endParserState;
    }

    public String getName() {
        if (!this.isAnonymous()) {
            return this.getIdent().getName();
        }
        return this.name.toJavaStringUncached();
    }

    public TruffleString getNameTS() {
        return this.name;
    }

    public FunctionNode setName(LexicalContext lc, TruffleString name) {
        if (this.name.equals(name)) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, name, this.body, this.parameters, this.source));
    }

    public String getInternalName() {
        return this.internalName.toJavaStringUncached();
    }

    public TruffleString getInternalNameTS() {
        return this.internalName;
    }

    public List<IdentNode> getParameters() {
        return this.parameters;
    }

    public int getNumOfParams() {
        return this.numOfParams;
    }

    public int getLength() {
        return this.length;
    }

    public boolean isDeclared() {
        return this.getFlag(2);
    }

    public boolean isAnonymous() {
        return this.getFlag(1);
    }

    public boolean isNamedFunctionExpression() {
        return !this.getFlag(1056771);
    }

    public boolean isStrict() {
        return this.getFlag(4);
    }

    public boolean isMethod() {
        return this.getFlag(0x100000);
    }

    public boolean usesSuper() {
        return this.getFlag(524288);
    }

    public boolean hasDirectSuper() {
        return this.getFlag(262144);
    }

    public boolean isClassConstructor() {
        return this.getFlag(0x200000);
    }

    public boolean isDerivedConstructor() {
        return this.getFlag(0x400000);
    }

    public boolean usesNewTarget() {
        return this.getFlag(0x800000);
    }

    public boolean isScript() {
        return this.getFlag(1024);
    }

    public boolean isGetter() {
        return this.getFlag(2048);
    }

    public boolean isSetter() {
        return this.getFlag(4096);
    }

    public boolean isArrow() {
        return this.getFlag(65536);
    }

    public boolean isGenerator() {
        return this.getFlag(0x1000000);
    }

    public boolean isModule() {
        return this.getFlag(131072);
    }

    public Module getModule() {
        return this.module;
    }

    public boolean isStatement() {
        return this.getFlag(16);
    }

    public boolean isAsync() {
        return this.getFlag(0x2000000);
    }

    public boolean hasSimpleParameterList() {
        return !this.getFlag(0x4000000);
    }

    public boolean usesAncestorScope() {
        return this.usesAncestorScope;
    }

    public void setUsesAncestorScope(boolean usesAncestorScope) {
        this.usesAncestorScope = usesAncestorScope;
    }

    public boolean isNormal() {
        return !this.getFlag(51584000);
    }

    boolean isFunctionDeclaration() {
        return this.isDeclared() && this.isNormal();
    }

    public boolean hasApplyArgumentsCall() {
        return this.getFlag(0x20000000);
    }

    public boolean hasArrowEval() {
        return this.getFlag(0x8000000);
    }

    public boolean needsThis() {
        return this.usesThis() || this.hasDirectSuper() || this.hasEval() || this.hasArrowEval();
    }

    public boolean needsNewTarget() {
        return this.usesNewTarget() || this.hasDirectSuper() || !this.isArrow() && !this.isProgram() && (this.hasEval() || this.hasArrowEval());
    }

    public boolean needsSuper() {
        return this.usesSuper() || this.isMethod() && (this.hasEval() || this.hasArrowEval());
    }

    public boolean isClassFieldInitializer() {
        return this.getFlag(0x40000000);
    }

    public boolean hasClosures() {
        return this.getFlag(16384);
    }
}

