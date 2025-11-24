
package com.oracle.js.parser;

import com.oracle.js.parser.Parser;
import com.oracle.js.parser.ParserContextBaseNode;
import com.oracle.js.parser.ParserContextBlockNode;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.ExpressionStatement;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.Module;
import com.oracle.js.parser.ir.ParameterNode;
import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.VarNode;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class ParserContextFunctionNode
extends ParserContextBaseNode {
    private final TruffleString name;
    private final IdentNode ident;
    private final int line;
    private final Scope parentScope;
    private List<IdentNode> parameters;
    private ParserContextBlockNode parameterBlock;
    private Scope bodyScope;
    private final long token;
    private long lastToken;
    private Object endParserState;
    private int length;
    private int parameterCount;
    private IdentNode duplicateParameterBinding;
    private boolean simpleParameterList = true;
    private boolean hasParameterExpressions;
    private boolean containsDefaultParameter;
    private boolean coverArrowHead;
    private long yieldOrAwaitInParameters;
    private Module module;
    private TruffleString internalName;
    private List<Map.Entry<VarNode, Scope>> hoistedVarDeclarations;
    private List<Map.Entry<VarNode, Scope>> hoistableBlockFunctionDeclarations;

    ParserContextFunctionNode(long token, IdentNode ident, TruffleString name, int line, int flags, List<IdentNode> parameters, int length, Scope parentScope, Scope functionScope) {
        super(flags);
        this.ident = ident;
        this.line = line;
        this.name = name;
        this.parameters = parameters;
        this.token = token;
        this.length = length;
        this.parentScope = parentScope;
        this.bodyScope = functionScope;
        int n = this.parameterCount = parameters == null ? 0 : parameters.size();
        assert (ParserContextFunctionNode.calculateLength(parameters) == length);
        assert (functionScope == null || functionScope.isFunctionTopScope() || functionScope.isEvalScope()) : functionScope;
    }

    public String getName() {
        return this.name.toJavaStringUncached();
    }

    public TruffleString getNameTS() {
        return this.name;
    }

    public IdentNode getIdent() {
        return this.ident;
    }

    public boolean isProgram() {
        return this.getFlag(8192) != 0;
    }

    public boolean isStrict() {
        return this.getFlag(4) != 0;
    }

    public boolean isModule() {
        return this.getFlag(131072) != 0;
    }

    public boolean hasEval() {
        return this.getFlag(32) != 0;
    }

    public boolean hasNestedEval() {
        return this.getFlag(64) != 0;
    }

    public boolean hasArrowEval() {
        return this.getFlag(0x8000000) != 0;
    }

    public boolean usesThis() {
        return this.getFlag(32768) != 0;
    }

    public boolean usesSuper() {
        return this.getFlag(524288) != 0;
    }

    public boolean usesNewTarget() {
        return this.getFlag(0x800000) != 0;
    }

    public boolean hasScopeBlock() {
        return this.getFlag(128) != 0;
    }

    public int getLineNumber() {
        return this.line;
    }

    public List<IdentNode> getParameters() {
        if (this.parameters == null) {
            return List.of();
        }
        return this.parameters;
    }

    void setParameters(List<IdentNode> parameters) {
        this.parameters = parameters;
    }

    public long getFirstToken() {
        return this.token;
    }

    public long getLastToken() {
        return this.lastToken;
    }

    public void setLastToken(long token) {
        this.lastToken = token;
    }

    public Object getEndParserState() {
        return this.endParserState;
    }

    public void setEndParserState(Object endParserState) {
        this.endParserState = endParserState;
    }

    public int getId() {
        return this.isProgram() ? -1 : Token.descPosition(this.token);
    }

    public boolean isMethod() {
        return this.getFlag(0x100000) != 0;
    }

    public boolean isClassConstructor() {
        return this.getFlag(0x200000) != 0;
    }

    public boolean isDerivedConstructor() {
        return this.getFlag(0x400000) != 0;
    }

    public int getLength() {
        return this.length;
    }

    public int getParameterCount() {
        return this.parameterCount;
    }

    public void addParameter(IdentNode param) {
        this.addParameterBinding(param);
        if (this.hasParameterExpressions()) {
            this.addParameterInit(param, this.getParameterCount());
        } else {
            if (this.parameters == null) {
                this.parameters = new ArrayList<IdentNode>();
            }
            this.parameters.add(param);
        }
        this.recordParameter(false, param.isRestParameter(), false);
    }

    public boolean hasParameterExpressions() {
        return this.hasParameterExpressions;
    }

    private void recordParameter(boolean isDefault, boolean isRest, boolean isPattern) {
        if (!isDefault && !isRest) {
            if (!this.containsDefaultParameter) {
                ++this.length;
            }
        } else {
            this.containsDefaultParameter = true;
        }
        if ((isDefault || isRest || isPattern) && this.simpleParameterList) {
            this.recordNonSimpleParameterList();
        }
        ++this.parameterCount;
    }

    private void recordNonSimpleParameterList() {
        this.simpleParameterList = false;
        this.setFlag(0x4000000);
    }

    public boolean isSimpleParameterList() {
        return this.simpleParameterList;
    }

    private boolean addParameterBinding(IdentNode bindingIdentifier) {
        if (Parser.isArguments(bindingIdentifier)) {
            this.setFlag(256);
        }
        boolean tdz = this.hasParameterExpressions();
        Symbol paramSymbol = new Symbol(bindingIdentifier.getNameTS(), 0x11 | (!tdz ? 1024 : 0));
        if (this.getParameterScope().putSymbol(paramSymbol) == null) {
            return true;
        }
        if (this.duplicateParameterBinding == null) {
            this.duplicateParameterBinding = bindingIdentifier;
        }
        return false;
    }

    public IdentNode getDuplicateParameterBinding() {
        return this.duplicateParameterBinding;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public boolean isAsync() {
        return this.getFlag(0x2000000) != 0;
    }

    public boolean isArrow() {
        return this.getFlag(65536) != 0;
    }

    public boolean isGenerator() {
        return this.getFlag(0x1000000) != 0;
    }

    public boolean isScriptOrModule() {
        return this.getFlag(132096) != 0;
    }

    public ParserContextBlockNode getParameterBlock() {
        return this.parameterBlock;
    }

    public void addDefaultParameter(VarNode varNode) {
        this.ensureParameterBlock();
        this.parameterBlock.appendStatement(varNode);
        this.addParameterBinding(varNode.getName());
        this.recordParameter(true, false, false);
    }

    public void addParameterBindingDeclaration(VarNode varNode) {
        this.ensureParameterBlock();
        this.parameterBlock.appendStatement(varNode);
        this.addParameterBinding(varNode.getName());
    }

    public void addParameterInitialization(int lineNumber, Expression assignment, boolean isDefault, boolean isRest) {
        this.ensureParameterBlock();
        this.parameterBlock.appendStatement(new ExpressionStatement(lineNumber, assignment.getToken(), assignment.getFinish(), assignment));
        this.recordParameter(isDefault, isRest, true);
    }

    private void ensureParameterBlock() {
        if (!this.hasParameterExpressions()) {
            this.hasParameterExpressions = true;
            this.initParameterBlock();
        }
    }

    private void initParameterBlock() {
        this.createParameterBlock();
        if (this.parameters != null) {
            for (int i = 0; i < this.parameters.size(); ++i) {
                IdentNode paramIdent = this.parameters.get(i);
                this.addParameterInit(paramIdent, i);
            }
        }
        this.parameters = List.of();
    }

    public ParserContextBlockNode createParameterBlock() {
        assert (this.bodyScope == null) : "parameter block must be created before body block";
        assert (!this.isScriptOrModule());
        if (this.parameterBlock != null) {
            return this.parameterBlock;
        }
        this.parameterBlock = new ParserContextBlockNode(this.token, Scope.createFunctionParameter(this.parentScope, this.getFlags()));
        this.parameterBlock.setFlag(80);
        return this.parameterBlock;
    }

    private void addParameterInit(IdentNode param, int index) {
        long paramToken = param.getToken();
        int paramFinish = param.getFinish();
        ParameterNode paramValue = param.isRestParameter() ? new ParameterNode(paramToken, paramFinish, index, true) : new ParameterNode(paramToken, paramFinish, index);
        this.parameterBlock.appendStatement(new VarNode(this.line, Token.recast(paramToken, TokenType.LET), paramFinish, param, paramValue, 1));
        assert (this.hasParameterExpressions() && this.getParameterScope().hasSymbol(param.getName()));
    }

    public Scope createBodyScope(Function<TruffleString, TruffleString> stringIntern) {
        Scope parent;
        assert (!this.isScriptOrModule());
        if (this.hasParameterExpressions()) {
            parent = this.getParameterScope();
            if (this.needsArguments()) {
                assert (!parent.hasSymbol(Parser.ARGUMENTS_NAME.toJavaStringUncached()));
                parent.putSymbol(new Symbol(stringIntern.apply(Parser.ARGUMENTS_NAME), 2098177));
            }
            this.parameters = List.of();
        } else {
            parent = this.parentScope;
        }
        Scope scope = Scope.createFunctionBody(parent, this.getFlags(), !this.hasParameterExpressions());
        if (!this.hasParameterExpressions() && this.parameters != null) {
            for (int i = 0; i < this.parameters.size(); ++i) {
                IdentNode parameter = this.parameters.get(i);
                scope.putSymbol(new Symbol(parameter.getNameTS(), 20));
            }
        }
        return this.initBodyScope(scope);
    }

    private Scope initBodyScope(Scope scope) {
        assert (this.bodyScope == null && scope != null);
        this.bodyScope = scope;
        return scope;
    }

    public Scope getBodyScope() {
        return this.bodyScope;
    }

    public void replaceBodyScope(Scope scope) {
        assert (this.bodyScope != null && this.bodyScope.getSymbolCount() == 0 && scope != null);
        this.bodyScope = scope;
    }

    public Scope getParameterScope() {
        return this.parameterBlock.getScope();
    }

    private boolean needsArguments() {
        return this.getFlag(40) != 0 && this.getFlag(0x40010100) == 0;
    }

    private boolean hasFunctionSelf() {
        return this.getFlag(1056771) == 0 && !this.name.isEmpty();
    }

    private void putFunctionSymbolIfAbsent(TruffleString bindingName, int symbolFlags) {
        if (this.hasParameterExpressions()) {
            Scope parameterScope = this.getParameterScope();
            if (!parameterScope.hasSymbol(bindingName.toJavaStringUncached()) && !this.bodyScope.hasSymbol(bindingName.toJavaStringUncached())) {
                parameterScope.putSymbol(new Symbol(bindingName, 1 | symbolFlags | 0x400));
            }
        } else if (!this.bodyScope.hasSymbol(bindingName.toJavaStringUncached())) {
            this.bodyScope.putSymbol(new Symbol(bindingName, 4 | symbolFlags | 0x400));
        }
    }

    public void finishBodyScope(Function<TruffleString, TruffleString> stringIntern) {
        if (this.hoistableBlockFunctionDeclarations != null) {
            this.declareHoistedBlockFunctionDeclarations();
        }
        if (this.isScriptOrModule()) {
            return;
        }
        if (this.needsArguments()) {
            this.putFunctionSymbolIfAbsent(stringIntern.apply(Parser.ARGUMENTS_NAME), 0x200000);
        }
        if (this.hasFunctionSelf()) {
            this.putFunctionSymbolIfAbsent(this.getNameTS(), 128);
        }
        if (!this.isArrow()) {
            boolean needsThisForEval;
            boolean bl = needsThisForEval = this.hasEval() || this.hasArrowEval();
            if (this.usesThis() || this.usesSuper() || needsThisForEval || this.getFlag(262144) != 0) {
                this.putFunctionSymbolIfAbsent(stringIntern.apply(TokenType.THIS.getNameTS()), 32);
            }
            if (this.usesSuper() || this.isMethod() && needsThisForEval) {
                this.putFunctionSymbolIfAbsent(stringIntern.apply(TokenType.SUPER.getNameTS()), 0x2000000);
            }
            if (this.usesNewTarget() || needsThisForEval) {
                this.putFunctionSymbolIfAbsent(stringIntern.apply(Parser.NEW_TARGET_NAME), 0x4000000);
            }
        }
        if (this.hasParameterExpressions()) {
            this.bodyScope.close();
            this.getParameterScope().close();
        }
    }

    public String getInternalName() {
        return this.internalName.toJavaStringUncached();
    }

    public TruffleString getInternalNameTS() {
        return this.internalName;
    }

    public void setInternalName(TruffleString internalName) {
        this.internalName = internalName;
    }

    public boolean isClassStaticBlock() {
        return this.getFlag(0x40000000) != 0;
    }

    public boolean isCoverArrowHead() {
        return this.coverArrowHead;
    }

    public void setCoverArrowHead(boolean coverArrowHead) {
        this.coverArrowHead = coverArrowHead;
    }

    public void setYieldOrAwaitInParameters(long yieldOrAwaitInParameters) {
        assert (this.yieldOrAwaitInParameters == 0L);
        this.yieldOrAwaitInParameters = yieldOrAwaitInParameters;
    }

    public long getYieldOrAwaitInParameters() {
        return this.yieldOrAwaitInParameters;
    }

    public void recordHoistedVarDeclaration(VarNode varDecl, Scope scope) {
        assert (!varDecl.isBlockScoped());
        assert (scope.isBlockScope());
        if (this.hoistedVarDeclarations == null) {
            this.hoistedVarDeclarations = new ArrayList<Map.Entry<VarNode, Scope>>();
        }
        this.hoistedVarDeclarations.add(new AbstractMap.SimpleImmutableEntry<VarNode, Scope>(varDecl, scope));
    }

    public VarNode verifyHoistedVarDeclarations() {
        if (!this.hasHoistedVarDeclarations()) {
            return null;
        }
        for (Map.Entry<VarNode, Scope> entry : this.hoistedVarDeclarations) {
            VarNode varDecl = entry.getKey();
            Scope declScope = entry.getValue();
            String varName = varDecl.getName().getName();
            for (Scope current = declScope; current != this.bodyScope; current = current.getParent()) {
                Symbol existing = current.getExistingSymbol(varName);
                if (existing == null || !existing.isBlockScoped() || existing.isCatchParameter()) continue;
                return varDecl;
            }
        }
        return null;
    }

    public boolean hasHoistedVarDeclarations() {
        return this.hoistedVarDeclarations != null;
    }

    public void recordHoistableBlockFunctionDeclaration(VarNode functionDeclaration, Scope scope) {
        assert (functionDeclaration.isFunctionDeclaration() && functionDeclaration.isBlockScoped());
        if (this.hoistableBlockFunctionDeclarations == null) {
            this.hoistableBlockFunctionDeclarations = new ArrayList<Map.Entry<VarNode, Scope>>();
        }
        this.hoistableBlockFunctionDeclarations.add(new AbstractMap.SimpleImmutableEntry<VarNode, Scope>(functionDeclaration, scope));
    }

    public void declareHoistedBlockFunctionDeclarations() {
        if (this.hoistableBlockFunctionDeclarations == null) {
            return;
        }
        block0: for (Map.Entry<VarNode, Scope> entry : this.hoistableBlockFunctionDeclarations) {
            VarNode functionDecl = entry.getKey();
            Scope functionDeclScope = entry.getValue();
            String varName = functionDecl.getName().getName();
            for (Scope current = functionDeclScope.getParent(); current != null; current = current.getParent()) {
                Symbol existing = current.getExistingSymbol(varName);
                if (existing != null && existing.isBlockScoped() && !existing.isCatchParameter()) continue block0;
                if (current.isFunctionBodyScope()) break;
            }
            if (this.bodyScope.getExistingSymbol(varName) == null) {
                this.bodyScope.putSymbol(new Symbol(functionDecl.getName().getNameTS(), 4 | (this.bodyScope.isGlobalScope() ? 8 : 0)));
            }
            functionDeclScope.getExistingSymbol(varName).setHoistedBlockFunctionDeclaration();
        }
    }

    public void propagateFlagsToParent(ParserContextFunctionNode parent) {
        if (this.hasEval() || this.hasNestedEval()) {
            parent.setFlag(192);
        }
        if (this.isArrow()) {
            if (this.hasEval() || this.hasArrowEval()) {
                parent.setFlag(0x8000000);
            }
            if (this.usesThis()) {
                parent.setFlag(32768);
            }
        }
        parent.setFlag(16384);
    }

    private static int calculateLength(List<IdentNode> parameters) {
        int length = 0;
        if (parameters != null) {
            for (IdentNode param : parameters) {
                if (param.isRestParameter()) break;
                ++length;
            }
        }
        return length;
    }
}

