
package com.oracle.js.parser;

import com.oracle.js.parser.AbstractParser;
import com.oracle.js.parser.CoverExpressionError;
import com.oracle.js.parser.ECMAErrors;
import com.oracle.js.parser.ErrorManager;
import com.oracle.js.parser.JSErrorType;
import com.oracle.js.parser.Lexer;
import com.oracle.js.parser.Options;
import com.oracle.js.parser.ParserContext;
import com.oracle.js.parser.ParserContextBlockNode;
import com.oracle.js.parser.ParserContextBreakableNode;
import com.oracle.js.parser.ParserContextClassNode;
import com.oracle.js.parser.ParserContextFunctionNode;
import com.oracle.js.parser.ParserContextLabelNode;
import com.oracle.js.parser.ParserContextLoopNode;
import com.oracle.js.parser.ParserContextModuleNode;
import com.oracle.js.parser.ParserContextNode;
import com.oracle.js.parser.ParserContextScopableNode;
import com.oracle.js.parser.ParserContextSwitchNode;
import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.ParserStrings;
import com.oracle.js.parser.RecompilableScriptFunctionData;
import com.oracle.js.parser.ScriptEnvironment;
import com.oracle.js.parser.Source;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenKind;
import com.oracle.js.parser.TokenLookup;
import com.oracle.js.parser.TokenStream;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.AccessNode;
import com.oracle.js.parser.ir.BaseNode;
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
import com.oracle.js.parser.ir.ErrorNode;
import com.oracle.js.parser.ir.ExportNode;
import com.oracle.js.parser.ir.ExportSpecifierNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.ExpressionList;
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
import com.oracle.js.parser.ir.Module;
import com.oracle.js.parser.ir.NameSpaceImportNode;
import com.oracle.js.parser.ir.NamedExportsNode;
import com.oracle.js.parser.ir.NamedImportsNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.ObjectNode;
import com.oracle.js.parser.ir.ParameterNode;
import com.oracle.js.parser.ir.PropertyKey;
import com.oracle.js.parser.ir.PropertyNode;
import com.oracle.js.parser.ir.ReturnNode;
import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.SwitchNode;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.TemplateLiteralNode;
import com.oracle.js.parser.ir.TernaryNode;
import com.oracle.js.parser.ir.ThrowNode;
import com.oracle.js.parser.ir.TryNode;
import com.oracle.js.parser.ir.UnaryNode;
import com.oracle.js.parser.ir.VarNode;
import com.oracle.js.parser.ir.WhileNode;
import com.oracle.js.parser.ir.WithNode;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.graalvm.collections.Pair;

public class Parser
extends AbstractParser {
    static final TruffleString ARGUMENTS_NAME = ParserStrings.constant("arguments");
    private static final String EVAL_NAME = "eval";
    private static final TruffleString CONSTRUCTOR_NAME = ParserStrings.constant("constructor");
    private static final String PRIVATE_CONSTRUCTOR_NAME = "#constructor";
    private static final String PROTO_NAME = "__proto__";
    static final TruffleString NEW_TARGET_NAME = ParserStrings.constant("new.target");
    private static final TruffleString IMPORT_META_NAME = ParserStrings.constant("import.meta");
    private static final String PROTOTYPE_NAME = "prototype";
    private static final String APPLY_NAME = "apply";
    private static final TruffleString EXEC_NAME = ParserStrings.constant("$EXEC");
    private static final TruffleString ANONYMOUS_FUNCTION_NAME = ParserStrings.constant(":anonymous");
    private static final TruffleString PROGRAM_NAME = ParserStrings.constant(":program");
    private static final TruffleString ERROR_BINDING_NAME = ParserStrings.constant(":error");
    private static final TruffleString SWITCH_BINDING_NAME = ParserStrings.constant(":switch");
    private static final TruffleString ARROW_FUNCTION_NAME = ParserStrings.constant(":=>");
    private static final TruffleString INITIALIZER_FUNCTION_NAME = ParserStrings.constant(":initializer");
    private static final boolean ES6_FOR_OF = Options.getBooleanProperty("parser.for.of", true);
    private static final boolean ES6_CLASS = Options.getBooleanProperty("parser.class", true);
    private static final boolean ES6_ARROW_FUNCTION = Options.getBooleanProperty("parser.arrow.function", true);
    private static final boolean ES6_REST_PARAMETER = Options.getBooleanProperty("parser.rest.parameter", true);
    private static final boolean ES6_SPREAD_ARGUMENT = Options.getBooleanProperty("parser.spread.argument", true);
    private static final boolean ES6_GENERATOR_FUNCTION = Options.getBooleanProperty("parser.generator.function", true);
    private static final boolean ES6_DESTRUCTURING = Options.getBooleanProperty("parser.destructuring", true);
    private static final boolean ES6_SPREAD_ARRAY = Options.getBooleanProperty("parser.spread.array", true);
    private static final boolean ES6_COMPUTED_PROPERTY_NAME = Options.getBooleanProperty("parser.computed.property.name", true);
    private static final boolean ES6_DEFAULT_PARAMETER = Options.getBooleanProperty("parser.default.parameter", true);
    private static final boolean ES6_NEW_TARGET = Options.getBooleanProperty("parser.new.target", true);
    private static final boolean ES8_TRAILING_COMMA = Options.getBooleanProperty("parser.trailing.comma", true);
    private static final boolean ES8_ASYNC_FUNCTION = Options.getBooleanProperty("parser.async.function", true);
    private static final boolean ES8_REST_SPREAD_PROPERTY = Options.getBooleanProperty("parser.rest.spread.property", true);
    private static final boolean ES8_FOR_AWAIT_OF = Options.getBooleanProperty("parser.for.await.of", true);
    private static final boolean ES2019_OPTIONAL_CATCH_BINDING = Options.getBooleanProperty("parser.optional.catch.binding", true);
    private static final boolean ES2020_CLASS_FIELDS = Options.getBooleanProperty("parser.class.fields", true);
    private static final boolean ES2022_TOP_LEVEL_AWAIT = Options.getBooleanProperty("parser.top.level.await", true);
    private static final int REPARSE_IS_PROPERTY_ACCESSOR = 1;
    private static final int REPARSE_IS_METHOD = 2;
    private static final int PARSE_EVAL = 4;
    private static final int PARSE_FUNCTION_CONTEXT_EVAL = 8;
    private static final String USE_STRICT = "use strict";
    private static final TruffleString ARGS = ParserStrings.constant("args");
    private static final String GET_SPC = "get ";
    private static final String SET_SPC = "set ";
    private static final String META = "meta";
    private static final TruffleString TARGET = ParserStrings.constant("target");
    private static final String CONTEXT_ASSIGNMENT_TARGET = "assignment target";
    private static final String CONTEXT_ASYNC_FUNCTION_DECLARATION = "async function declaration";
    private static final String CONTEXT_CATCH_PARAMETER = "catch parameter";
    private static final String CONTEXT_CLASS_DECLARATION = "class declaration";
    private static final String CONTEXT_CLASS_NAME = "class name";
    private static final String CONTEXT_CONST_DECLARATION = "const declaration";
    private static final String CONTEXT_FOR_IN_ITERATOR = "for-in iterator";
    private static final String CONTEXT_FOR_OF_ITERATOR = "for-of iterator";
    private static final String CONTEXT_FUNCTION_DECLARATION = "function declaration";
    private static final String CONTEXT_FUNCTION_NAME = "function name";
    private static final String CONTEXT_FUNCTION_PARAMETER = "function parameter";
    private static final String CONTEXT_GENERATOR_FUNCTION_DECLARATION = "generator function declaration";
    private static final String CONTEXT_IDENTIFIER_REFERENCE = "IdentifierReference";
    private static final String CONTEXT_IMPORTED_BINDING = "imported binding";
    private static final String CONTEXT_IN = "in";
    private static final String CONTEXT_LABEL_IDENTIFIER = "LabelIdentifier";
    private static final String CONTEXT_LET_DECLARATION = "let declaration";
    private static final String CONTEXT_OF = "of";
    private static final String CONTEXT_OPERAND_FOR_DEC_OPERATOR = "operand for -- operator";
    private static final String CONTEXT_OPERAND_FOR_INC_OPERATOR = "operand for ++ operator";
    private static final String CONTEXT_VARIABLE_NAME = "variable name";
    private static final String MSG_ACCESSOR_CONSTRUCTOR = "accessor.constructor";
    private static final String MSG_ARGUMENTS_IN_FIELD_INITIALIZER = "arguments.in.field.initializer";
    private static final String MSG_ASYNC_CONSTRUCTOR = "async.constructor";
    private static final String MSG_CONSTRUCTOR_FIELD = "constructor.field";
    private static final String MSG_DUPLICATE_DEFAULT_IN_SWITCH = "duplicate.default.in.switch";
    private static final String MSG_DUPLICATE_IMPORT_ASSERTION = "duplicate.import.assertion";
    private static final String MSG_DUPLICATE_LABEL = "duplicate.label";
    private static final String MSG_ESCAPED_KEYWORD = "escaped.keyword";
    private static final String MSG_EXPECTED_ARROW_PARAMETER = "expected.arrow.parameter";
    private static final String MSG_EXPECTED_BINDING = "expected.binding";
    private static final String MSG_EXPECTED_BINDING_IDENTIFIER = "expected.binding.identifier";
    private static final String MSG_EXPECTED_COMMA = "expected.comma";
    private static final String MSG_EXPECTED_IMPORT = "expected.import";
    private static final String MSG_EXPECTED_NAMED_IMPORT = "expected.named.import";
    private static final String MSG_EXPECTED_OPERAND = "expected.operand";
    private static final String MSG_EXPECTED_PROPERTY_ID = "expected.property.id";
    private static final String MSG_EXPECTED_STMT = "expected.stmt";
    private static final String MSG_EXPECTED_TARGET = "expected.target";
    private static final String MSG_FOR_EACH_WITHOUT_IN = "for.each.without.in";
    private static final String MSG_FOR_IN_LOOP_INITIALIZER = "for.in.loop.initializer";
    private static final String MSG_GENERATOR_CONSTRUCTOR = "generator.constructor";
    private static final String MSG_ILLEGAL_BREAK_STMT = "illegal.break.stmt";
    private static final String MSG_ILLEGAL_CONTINUE_STMT = "illegal.continue.stmt";
    private static final String MSG_INVALID_ARROW_PARAMETER = "invalid.arrow.parameter";
    private static final String MSG_INVALID_EXPORT = "invalid.export";
    private static final String MSG_INVALID_FOR_AWAIT_OF = "invalid.for.await.of";
    private static final String MSG_INVALID_LVALUE = "invalid.lvalue";
    private static final String MSG_INVALID_PRIVATE_IDENT = "invalid.private.ident";
    private static final String MSG_INVALID_PROPERTY_INITIALIZER = "invalid.property.initializer";
    private static final String MSG_INVALID_RETURN = "invalid.return";
    private static final String MSG_INVALID_SUPER = "invalid.super";
    private static final String MSG_LET_LEXICAL_BINDING = "let.lexical.binding";
    private static final String MSG_MANY_VARS_IN_FOR_IN_LOOP = "many.vars.in.for.in.loop";
    private static final String MSG_MISSING_CATCH_OR_FINALLY = "missing.catch.or.finally";
    private static final String MSG_MISSING_CONST_ASSIGNMENT = "missing.const.assignment";
    private static final String MSG_MISSING_DESTRUCTURING_ASSIGNMENT = "missing.destructuring.assignment";
    private static final String MSG_MULTIPLE_CONSTRUCTORS = "multiple.constructors";
    private static final String MSG_MULTIPLE_PROTO_KEY = "multiple.proto.key";
    private static final String MSG_NEW_TARGET_IN_FUNCTION = "new.target.in.function";
    private static final String MSG_NO_FUNC_DECL_HERE = "no.func.decl.here";
    private static final String MSG_NO_FUNC_DECL_HERE_WARN = "no.func.decl.here.warn";
    private static final String MSG_NOT_LVALUE_FOR_IN_LOOP = "not.lvalue.for.in.loop";
    private static final String MSG_OPTIONAL_CHAIN_TEMPLATE = "optional.chain.template";
    private static final String MSG_PRIVATE_CONSTRUCTOR_METHOD = "private.constructor.method";
    private static final String MSG_PROPERTY_REDEFINITON = "property.redefinition";
    private static final String MSG_STATIC_PROTOTYPE_FIELD = "static.prototype.field";
    private static final String MSG_STATIC_PROTOTYPE_METHOD = "static.prototype.method";
    private static final String MSG_STRICT_CANT_DELETE_IDENT = "strict.cant.delete.ident";
    private static final String MSG_STRICT_CANT_DELETE_PRIVATE = "strict.cant.delete.private";
    private static final String MSG_STRICT_NAME = "strict.name";
    private static final String MSG_STRICT_NO_FUNC_DECL_HERE = "strict.no.func.decl.here";
    private static final String MSG_STRICT_NO_NONOCTALDECIMAL = "strict.no.nonoctaldecimal";
    private static final String MSG_STRICT_NO_OCTAL = "strict.no.octal";
    private static final String MSG_STRICT_NO_WITH = "strict.no.with";
    private static final String MSG_STRICT_PARAM_REDEFINITION = "strict.param.redefinition";
    private static final String MSG_SYNTAX_ERROR_REDECLARE_VARIABLE = "syntax.error.redeclare.variable";
    private static final String MSG_UNDEFINED_LABEL = "undefined.label";
    private static final String MSG_UNEXPECTED_IDENT = "unexpected.ident";
    private static final String MSG_UNEXPECTED_IMPORT_META = "unexpected.import.meta";
    private static final String MSG_UNEXPECTED_TOKEN = "unexpected.token";
    private static final String MSG_UNTERMINATED_TEMPLATE_EXPRESSION = "unterminated.template.expression";
    private static final String MSG_USE_STRICT_NON_SIMPLE_PARAM = "use.strict.non.simple.param";
    private static final String MSG_DECORATED_CONSTRUCTOR = "decorated.constructor";
    private static final String MSG_DECORATED_STATIC_BLOCK = "decorated.static.block";
    private static final String MSG_AUTO_ACCESSOR_NOT_FIELD = "auto.accessor.not.field";
    private final ScriptEnvironment env;
    private final boolean scripting;
    private final boolean shebang;
    private final boolean allowBigInt;
    private List<Statement> functionDeclarations;
    private final ParserContext lc = new ParserContext();
    private final List<Object> defaultNames = new ArrayList<Object>();
    protected final Lexer.LineInfoReceiver lineInfoReceiver;
    private RecompilableScriptFunctionData reparsedFunction;
    private boolean isModule;
    private ParserContextFunctionNode coverArrowFunction;
    public static final boolean PROFILE_PARSING = Options.getBooleanProperty("parser.profiling", false);
    public static final boolean PROFILE_PARSING_PRINT = Options.getBooleanProperty("parser.profiling.print", true);

    public Parser(ScriptEnvironment env, Source source, ErrorManager errors) {
        this(env, source, errors, env.strict);
    }

    public Parser(ScriptEnvironment env, Source source, ErrorManager errors, boolean strict) {
        this(env, source, errors, strict, 0);
    }

    public Parser(ScriptEnvironment env, Source source, ErrorManager errors, boolean strict, int lineOffset) {
        super(source, errors, strict, lineOffset);
        this.env = env;
        this.scripting = env.scripting && env.syntaxExtensions;
        this.shebang = env.shebang || this.scripting;
        this.allowBigInt = env.allowBigInt;
        this.lineInfoReceiver = this.scripting ? new Lexer.LineInfoReceiver(){

            @Override
            public void lineInfo(int receiverLine, int receiverLinePosition) {
                Parser.this.line = receiverLine;
                Parser.this.linePosition = receiverLinePosition;
            }
        } : null;
    }

    public FunctionNode parse() {
        return this.parse(PROGRAM_NAME, 0, this.source.getLength(), 0, null, null);
    }

    public void setReparsedFunction(RecompilableScriptFunctionData reparsedFunction) {
        this.reparsedFunction = reparsedFunction;
    }

    private void scanFirstToken() {
        this.k = -1;
        this.next();
    }

    private void prepareLexer(int startPos, int len) {
        this.stream = new TokenStream();
        this.lexer = new Lexer(this.source, startPos, len, this.stream, this.scripting, this.env.ecmaScriptVersion, this.shebang, this.isModule, this.reparsedFunction != null, this.allowBigInt);
        this.lexer.line = this.lexer.pendingLine = this.lineOffset + 1;
        this.line = this.lineOffset;
    }

    private TokenType lookahead() {
        int i = 1;
        TokenType t;
        while ((t = this.T(this.k + i)) == TokenType.EOL || t == TokenType.COMMENT) {
            ++i;
        }
        return t;
    }

    private TokenType lookaheadNoLineTerminator() {
        int i = 1;
        TokenType t;
        while ((t = this.T(this.k + i)) == TokenType.COMMENT) {
            ++i;
        }
        return t;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public FunctionNode parse(TruffleString scriptName, int startPos, int len, int reparseFlags, Scope parentScope, List<String> argumentNames) {
        long startTime = PROFILE_PARSING ? System.nanoTime() : 0L;
        try {
            this.prepareLexer(startPos, len);
            this.scanFirstToken();
            FunctionNode functionNode = this.program(scriptName, reparseFlags, parentScope, argumentNames);
            return functionNode;
        }
        catch (Exception e) {
            this.handleParseException(e);
            FunctionNode functionNode = null;
            return functionNode;
        }
        finally {
            if (PROFILE_PARSING) {
                long duration = System.nanoTime() - startTime;
                if (PROFILE_PARSING_PRINT) {
                    System.out.println("Parsing: " + duration / 1000000L);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public FunctionNode parseModule(String moduleName, int startPos, int len) {
        boolean oldModule = this.isModule;
        boolean oldStrictMode = this.isStrictMode;
        try {
            this.isModule = true;
            this.isStrictMode = true;
            this.prepareLexer(startPos, len);
            this.scanFirstToken();
            FunctionNode functionNode = this.module(moduleName);
            return functionNode;
        }
        catch (Exception e) {
            this.handleParseException(e);
            FunctionNode functionNode = null;
            return functionNode;
        }
        finally {
            this.isStrictMode = oldStrictMode;
            this.isModule = oldModule;
        }
    }

    public FunctionNode parseModule(String moduleName) {
        return this.parseModule(moduleName, 0, this.source.getLength());
    }

    public FunctionNode parseEval(boolean functionContext, Scope parentScope) {
        return this.parse(PROGRAM_NAME, 0, this.source.getLength(), 4 | (functionContext ? 8 : 0), parentScope, null);
    }

    public FunctionNode parseWithArguments(List<String> argumentNames) {
        return this.parse(PROGRAM_NAME, 0, this.source.getLength(), 0, null, argumentNames);
    }

    public void parseFormalParameterList() {
        try {
            this.stream = new TokenStream();
            this.lexer = new Lexer(this.source, this.stream, this.scripting, this.env.ecmaScriptVersion, this.shebang, this.isModule, this.allowBigInt);
            this.scanFirstToken();
            assert (this.lc.getCurrentScope() == null);
            this.formalParameterList(TokenType.EOF, false, false);
        }
        catch (Exception e) {
            this.handleParseException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public FunctionNode parseFunctionBody(boolean generator, boolean async) {
        try {
            this.stream = new TokenStream();
            this.lexer = new Lexer(this.source, this.stream, this.scripting, this.env.ecmaScriptVersion, this.shebang, this.isModule, this.allowBigInt);
            int functionLine = this.line;
            this.scanFirstToken();
            long functionToken = Token.toDesc(TokenType.FUNCTION, 0, this.source.getLength());
            IdentNode ident = new IdentNode(functionToken, Token.descPosition(functionToken), this.lexer.stringIntern(PROGRAM_NAME));
            int functionFlags = (generator ? 0x1000000 : 0) | (async ? 0x2000000 : 0);
            ParserContextFunctionNode function = this.createParserContextFunctionNode(ident, functionToken, functionFlags, functionLine, List.of(), 0);
            function.clearFlag(8192);
            assert (this.lc.getCurrentScope() == null);
            this.lc.push(function);
            ParserContextBlockNode body = this.newBlock(function.createBodyScope(this.lexer::stringIntern));
            this.functionDeclarations = new ArrayList<Statement>();
            try {
                this.sourceElements(generator, async, 0);
                this.addFunctionDeclarations(function);
            }
            finally {
                this.functionDeclarations = null;
                function.finishBodyScope(this.lexer::stringIntern);
                this.restoreBlock(body);
                this.lc.pop(function);
            }
            body.setFlag(1);
            Block functionBody = new Block(functionToken, this.finish, body.getFlags() | 0x10 | 0x20, body.getScope(), body.getStatements());
            this.expect(TokenType.EOF);
            FunctionNode functionNode = this.createFunctionNode(function, functionToken, ident, functionLine, functionBody);
            return functionNode;
        }
        catch (Exception e) {
            this.handleParseException(e);
            return null;
        }
    }

    private void handleParseException(Exception e) {
        if (e instanceof ParserException) {
            this.errors.error((ParserException)e);
        } else {
            String message = e.getMessage();
            if (message == null) {
                message = e.toString();
            }
            this.errors.error(message);
        }
        if (this.env.dumpOnError) {
            e.printStackTrace(this.env.getErr());
        }
    }

    private void recover(Exception e) {
        if (e != null) {
            if (e instanceof ParserException) {
                this.errors.error((ParserException)e);
            } else {
                String message = e.getMessage();
                if (message == null) {
                    message = e.toString();
                }
                this.errors.error(message);
            }
            if (this.env.dumpOnError) {
                e.printStackTrace(this.env.getErr());
            }
        }
        block4: while (true) {
            switch (this.type) {
                case EOF: {
                    break block4;
                }
                case EOL: 
                case SEMICOLON: 
                case RBRACE: {
                    this.next();
                    break block4;
                }
                default: {
                    this.nextOrEOL();
                    continue block4;
                }
            }
            break;
        }
    }

    private ParserContextBlockNode newBlock() {
        Scope scope = Scope.createBlock(this.lc.getCurrentScope());
        return this.newBlock(scope);
    }

    private ParserContextBlockNode newBlock(Scope scope) {
        return this.lc.push(new ParserContextBlockNode(this.token, scope));
    }

    private ParserContextFunctionNode createParserContextFunctionNode(IdentNode ident, long functionToken, int functionFlags, int functionLine) {
        return this.createParserContextFunctionNode(ident, functionToken, functionFlags, functionLine, null, 0);
    }

    private ParserContextFunctionNode createParserContextFunctionNode(IdentNode ident, long functionToken, int functionFlags, int functionLine, List<IdentNode> parameters, int functionLength) {
        return this.createParserContextFunctionNode(ident, functionToken, functionFlags, functionLine, parameters, functionLength, null);
    }

    private ParserContextFunctionNode createParserContextFunctionNode(IdentNode ident, long functionToken, int functionFlags, int functionLine, List<IdentNode> parameters, int functionLength, Scope functionTopScope) {
        ParserContextFunctionNode parentFunction = this.lc.getCurrentFunction();
        TruffleString name = ident == null ? TruffleString.Encoding.UTF_16.getEmpty() : ident.getNameTS();
        int flags = functionFlags;
        if (this.isStrictMode) {
            flags |= 4;
        }
        if (parentFunction == null) {
            flags |= 0x2000;
            flags |= 1;
        }
        Scope parentScope = this.lc.getCurrentScope();
        return new ParserContextFunctionNode(functionToken, ident, name, functionLine, flags, parameters, functionLength, parentScope, functionTopScope);
    }

    private FunctionNode createFunctionNode(ParserContextFunctionNode function, long startToken, IdentNode ident, int functionLine, Block body) {
        assert (body.isFunctionBody() || body.isParameterBlock() && ((BlockStatement)body.getLastStatement()).getBlock().isFunctionBody());
        VarNode varNode = function.verifyHoistedVarDeclarations();
        if (varNode != null) {
            throw this.error(ECMAErrors.getMessage(MSG_SYNTAX_ERROR_REDECLARE_VARIABLE, varNode.getName().getName()), varNode.getToken());
        }
        long lastTokenWithDelimiter = Token.withDelimiter(function.getLastToken());
        int lastTokenFinish = Token.descPosition(lastTokenWithDelimiter) + (Token.descType(lastTokenWithDelimiter) == TokenType.EOL ? 0 : Token.descLength(lastTokenWithDelimiter));
        FunctionNode functionNode = new FunctionNode(this.source, functionLine, body.getToken(), lastTokenFinish, startToken, function.getLastToken(), ident, function.getNameTS(), function.getLength(), function.getParameterCount(), function.getParameters(), function.getFlags(), body, function.getEndParserState(), function.getModule(), function.getInternalNameTS());
        return functionNode;
    }

    private ParserContextBlockNode restoreBlock(ParserContextBlockNode block) {
        block.getScope().close();
        return this.lc.pop(block);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Block getBlock(boolean yield, boolean await2, boolean needsBraces) {
        int realFinish;
        long blockToken = this.token;
        ParserContextBlockNode newBlock = this.newBlock();
        try {
            if (needsBraces) {
                this.expect(TokenType.LBRACE);
            }
            this.statementList(yield, await2);
        }
        finally {
            this.restoreBlock(newBlock);
        }
        if (needsBraces) {
            this.expectDontAdvance(TokenType.RBRACE);
            realFinish = Token.descPosition(this.token) + Token.descLength(this.token);
            this.expect(TokenType.RBRACE);
        } else {
            realFinish = this.finish;
        }
        int flags = newBlock.getFlags() | (needsBraces ? 0 : 16);
        return new Block(blockToken, Math.max(realFinish, Token.descPosition(blockToken)), flags, newBlock.getScope(), newBlock.getStatements());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Statement> caseStatementList(boolean yield, boolean await2) {
        ParserContextBlockNode newBlock = this.newBlock(this.lc.getCurrentScope());
        try {
            this.statementList(yield, await2);
        }
        finally {
            this.lc.pop(newBlock);
        }
        return newBlock.getStatements();
    }

    private Block getStatement(boolean yield, boolean await2) {
        return this.getStatement(yield, await2, false, false);
    }

    private Block getStatement(boolean yield, boolean await2, boolean labelledStatement, boolean mayBeFunctionDeclaration) {
        return this.getStatement(yield, await2, labelledStatement, mayBeFunctionDeclaration, mayBeFunctionDeclaration);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Block getStatement(boolean yield, boolean await2, boolean labelledStatement, boolean mayBeFunctionDeclaration, boolean mayBeLabeledFunctionDeclaration) {
        if (this.type == TokenType.LBRACE) {
            return this.getBlock(yield, await2, true);
        }
        ParserContextBlockNode newBlock = this.newBlock();
        newBlock.setFlag(16);
        try {
            this.statement(yield, await2, false, 0, true, labelledStatement, mayBeFunctionDeclaration, mayBeLabeledFunctionDeclaration);
        }
        finally {
            this.restoreBlock(newBlock);
        }
        return new Block(newBlock.getToken(), this.finish, newBlock.getFlags(), newBlock.getScope(), newBlock.getStatements());
    }

    private IdentNode detectSpecialProperty(IdentNode ident) {
        if (Parser.isArguments(ident)) {
            return this.markArguments(ident);
        }
        return ident;
    }

    private IdentNode markArguments(IdentNode ident) {
        Scope currentScope = this.lc.getCurrentScope();
        if (currentScope.inClassFieldInitializer()) {
            throw this.error(AbstractParser.message(MSG_ARGUMENTS_IN_FIELD_INITIALIZER, new String[0]), ident.getToken());
        }
        if (currentScope.isGlobalScope()) {
            return ident;
        }
        this.lc.getCurrentNonArrowFunction().setFlag(8);
        return ident.setIsArguments();
    }

    private boolean useBlockScope() {
        return this.isES6();
    }

    private boolean isES6() {
        return this.env.ecmaScriptVersion >= 6;
    }

    private boolean isES2017() {
        return this.env.ecmaScriptVersion >= 8;
    }

    private boolean isES2020() {
        return this.env.ecmaScriptVersion >= 11;
    }

    private boolean isES2021() {
        return this.env.ecmaScriptVersion >= 12;
    }

    private boolean isES2022() {
        return this.env.ecmaScriptVersion >= 13;
    }

    private boolean isES2023() {
        return this.env.ecmaScriptVersion >= 14;
    }

    private boolean isClassFields() {
        return ES2020_CLASS_FIELDS && this.env.classFields;
    }

    static boolean isArguments(TruffleString name) {
        return ARGUMENTS_NAME.equals(name);
    }

    static boolean isArguments(IdentNode ident) {
        return Parser.isArguments(ident.getNameTS());
    }

    private static boolean checkIdentLValue(IdentNode ident) {
        return ident.tokenType().getKind() != TokenKind.KEYWORD;
    }

    private Expression verifyAssignment(long op, Expression lhs, Expression rhs, boolean inPatternPosition) {
        TokenType opType = Token.descType(op);
        Expression rhsExpr = rhs;
        switch (opType) {
            case ASSIGN: 
            case ASSIGN_INIT: 
            case ASSIGN_ADD: 
            case ASSIGN_BIT_AND: 
            case ASSIGN_BIT_OR: 
            case ASSIGN_BIT_XOR: 
            case ASSIGN_DIV: 
            case ASSIGN_MOD: 
            case ASSIGN_MUL: 
            case ASSIGN_EXP: 
            case ASSIGN_SAR: 
            case ASSIGN_SHL: 
            case ASSIGN_SHR: 
            case ASSIGN_SUB: 
            case ASSIGN_AND: 
            case ASSIGN_OR: 
            case ASSIGN_NULLCOAL: {
                if (lhs instanceof IdentNode) {
                    IdentNode ident = (IdentNode)lhs;
                    if (!Parser.checkIdentLValue(ident) || ident.isMetaProperty()) {
                        throw this.invalidLHSError(lhs);
                    }
                    this.verifyStrictIdent(ident, CONTEXT_ASSIGNMENT_TARGET);
                    if (lhs.isParenthesized() || !Parser.isAnonymousFunctionDefinition(rhsExpr)) break;
                    rhsExpr = this.setAnonymousFunctionName(rhsExpr, ident.getNameTS());
                    break;
                }
                if (lhs instanceof AccessNode || lhs instanceof IndexNode) {
                    if (!((BaseNode)lhs).isOptional()) break;
                    throw this.invalidLHSError(lhs);
                }
                if (!(opType != TokenType.ASSIGN && opType != TokenType.ASSIGN_INIT || !this.isDestructuringLhs(lhs) || !inPatternPosition && lhs.isParenthesized())) {
                    this.verifyDestructuringAssignmentPattern(lhs, CONTEXT_ASSIGNMENT_TARGET);
                    break;
                }
                throw this.invalidLHSError(lhs);
            }
        }
        assert (!BinaryNode.isLogical(opType));
        return new BinaryNode(op, lhs, rhsExpr);
    }

    private boolean isDestructuringLhs(Expression lhs) {
        if (lhs instanceof ObjectNode || lhs instanceof LiteralNode.ArrayLiteralNode) {
            return ES6_DESTRUCTURING && this.isES6();
        }
        return false;
    }

    private void verifyDestructuringAssignmentPattern(Expression pattern, final String contextString) {
        assert (pattern instanceof ObjectNode || pattern instanceof LiteralNode.ArrayLiteralNode);
        pattern.accept(new VerifyDestructuringPatternNodeVisitor(new LexicalContext()){

            @Override
            protected void verifySpreadElement(Expression lvalue) {
                if (!Parser.this.checkValidLValue(lvalue, contextString)) {
                    throw Parser.this.error(AbstractParser.message(Parser.MSG_INVALID_LVALUE, new String[0]), lvalue.getToken());
                }
                lvalue.accept(this);
            }

            @Override
            public boolean enterIdentNode(IdentNode identNode) {
                if (!Parser.checkIdentLValue(identNode) || identNode.isMetaProperty()) {
                    throw Parser.this.error(AbstractParser.message(Parser.MSG_INVALID_LVALUE, new String[0]), identNode.getToken());
                }
                Parser.this.verifyStrictIdent(identNode, contextString);
                return false;
            }

            @Override
            public boolean enterAccessNode(AccessNode accessNode) {
                if (accessNode.isOptional()) {
                    throw Parser.this.error(AbstractParser.message(Parser.MSG_INVALID_LVALUE, new String[0]), accessNode.getToken());
                }
                return false;
            }

            @Override
            public boolean enterIndexNode(IndexNode indexNode) {
                if (indexNode.isOptional()) {
                    throw Parser.this.error(AbstractParser.message(Parser.MSG_INVALID_LVALUE, new String[0]), indexNode.getToken());
                }
                return false;
            }

            @Override
            protected boolean enterDefault(Node node) {
                throw Parser.this.error(String.format("unexpected node in AssignmentPattern: %s", node));
            }
        });
    }

    private Expression newBinaryExpression(long op, Expression lhs, Expression rhs) {
        TokenType opType = Token.descType(op);
        if (BinaryNode.isLogical(opType)) {
            if (Parser.forbiddenNullishCoalescingUsage(opType, lhs, rhs)) {
                throw this.error(String.format("nullish coalescing operator cannot immediately contain, or be contained within, an && or || operation", new Object[0]));
            }
            return new BinaryNode(op, (Expression)new JoinPredecessorExpression(lhs), (Expression)new JoinPredecessorExpression(rhs));
        }
        return new BinaryNode(op, lhs, rhs);
    }

    private static boolean forbiddenNullishCoalescingUsage(TokenType opType, Expression lhs, Expression rhs) {
        if (opType == TokenType.NULLISHCOALESC) {
            return Parser.forbiddenNullishCoalescingChaining(lhs) || Parser.forbiddenNullishCoalescingChaining(rhs);
        }
        assert (opType == TokenType.AND || opType == TokenType.OR);
        return !lhs.isParenthesized() && lhs.isTokenType(TokenType.NULLISHCOALESC) || !rhs.isParenthesized() && rhs.isTokenType(TokenType.NULLISHCOALESC);
    }

    private static boolean forbiddenNullishCoalescingChaining(Expression expression) {
        return !expression.isParenthesized() && (expression.isTokenType(TokenType.AND) || expression.isTokenType(TokenType.OR));
    }

    private static UnaryNode incDecExpression(long firstToken, TokenType tokenType, Expression expression, boolean isPostfix) {
        assert (tokenType == TokenType.INCPREFIX || tokenType == TokenType.DECPREFIX);
        if (isPostfix) {
            long postfixToken = Token.recast(firstToken, tokenType == TokenType.DECPREFIX ? TokenType.DECPOSTFIX : TokenType.INCPOSTFIX);
            return new UnaryNode(postfixToken, expression.getStart(), Token.descPosition(firstToken) + Token.descLength(firstToken), expression);
        }
        return new UnaryNode(firstToken, expression);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FunctionNode program(TruffleString scriptName, int parseFlags, Scope parentScope, List<String> argumentNames) {
        int functionStart = Math.min(Token.descPosition(Token.withDelimiter(this.token)), this.finish);
        long functionToken = Token.toDesc(TokenType.FUNCTION, functionStart, this.source.getLength() - functionStart);
        int functionLine = this.line;
        Scope topScope = (parseFlags & 4) != 0 ? this.createEvalScope(parseFlags, parentScope) : Scope.createGlobal();
        topScope = this.applyArgumentsToScope(topScope, argumentNames);
        IdentNode ident = null;
        List<IdentNode> parameters = this.createFunctionNodeParameters(argumentNames);
        ParserContextFunctionNode script = this.createParserContextFunctionNode(ident, functionToken, 1024, functionLine, parameters, parameters.size(), topScope);
        script.setInternalName(this.lexer.stringIntern(scriptName));
        this.lc.push(script);
        ParserContextBlockNode body = this.newBlock(topScope);
        this.functionDeclarations = new ArrayList<Statement>();
        try {
            this.sourceElements(false, false, parseFlags);
            this.addFunctionDeclarations(script);
        }
        finally {
            this.functionDeclarations = null;
            script.finishBodyScope(this.lexer::stringIntern);
            this.restoreBlock(body);
            this.lc.pop(script);
        }
        body.setFlag(1);
        Block programBody = new Block(functionToken, this.finish, body.getFlags() | 0x10 | 0x20, body.getScope(), body.getStatements());
        script.setLastToken(this.token);
        this.expect(TokenType.EOF);
        return this.createFunctionNode(script, functionToken, ident, functionLine, programBody);
    }

    private Scope applyArgumentsToScope(Scope scope, List<String> argumentNames) {
        if (argumentNames == null) {
            return scope;
        }
        Scope body = Scope.createFunctionBody(scope);
        for (String argument : argumentNames) {
            body.putSymbol(new Symbol(this.lexer.stringIntern(argument), 20));
        }
        return body;
    }

    private List<IdentNode> createFunctionNodeParameters(List<String> argumentNames) {
        if (argumentNames == null) {
            return List.of();
        }
        ArrayList<IdentNode> list = new ArrayList<IdentNode>();
        for (String argumentName : argumentNames) {
            list.add(new IdentNode(0L, 0, this.lexer.stringIntern(argumentName)));
        }
        return list;
    }

    private Scope createEvalScope(int parseFlags, Scope parentScope) {
        assert ((parseFlags & 4) != 0);
        if (this.isStrictMode || (parseFlags & 8) != 0) {
            return Scope.createEval(parentScope, this.isStrictMode);
        }
        return Scope.createGlobal();
    }

    private static boolean isDirective(Node stmt) {
        Expression expr;
        if (stmt instanceof ExpressionStatement && (expr = ((ExpressionStatement)stmt).getExpression()) instanceof LiteralNode) {
            LiteralNode lit = (LiteralNode)expr;
            long litToken = lit.getToken();
            TokenType tt = Token.descType(litToken);
            return tt == TokenType.STRING || tt == TokenType.ESCSTRING;
        }
        return false;
    }

    private boolean isUseStrictDirective(Node stmt) {
        assert (Parser.isDirective(stmt));
        Expression exp = ((ExpressionStatement)stmt).getExpression();
        return this.source.getContent().regionMatches(exp.getStart() + 1, USE_STRICT, 0, Token.descLength(exp.getToken()) - 2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void sourceElements(boolean yield, boolean await2, int parseFlags) {
        boolean checkDirective = true;
        int functionFlags = parseFlags;
        boolean oldStrictMode = this.isStrictMode;
        try {
            while (this.type != TokenType.EOF) {
                TokenType elementType = this.type;
                if (elementType == TokenType.RBRACE) {
                    break;
                }
                try {
                    Statement lastStatement;
                    this.statement(yield, await2, true, functionFlags, false, false, true);
                    functionFlags = 0;
                    if (checkDirective && (checkDirective = Parser.isDirective(lastStatement = elementType == TokenType.STRING || elementType == TokenType.ESCSTRING ? this.lc.getLastStatement() : null)) && elementType == TokenType.STRING && this.isUseStrictDirective(lastStatement)) {
                        ParserContextFunctionNode function = this.lc.getCurrentFunction();
                        if (!function.isSimpleParameterList()) {
                            throw this.error(AbstractParser.message(MSG_USE_STRICT_NON_SIMPLE_PARAM, new String[0]), lastStatement.getToken());
                        }
                        if (!oldStrictMode) {
                            function.setFlag(4);
                            this.isStrictMode = true;
                            this.verifyUseStrict(function, parseFlags);
                        } else assert (function.isStrict());
                    }
                }
                catch (Exception e) {
                    int errorLine = this.line;
                    long errorToken = this.token;
                    this.recover(e);
                    ErrorNode errorExpr = new ErrorNode(errorToken, this.finish);
                    ExpressionStatement expressionStatement = new ExpressionStatement(errorLine, errorToken, this.finish, errorExpr);
                    this.appendStatement(expressionStatement);
                }
                this.stream.commit(this.k);
            }
        }
        finally {
            this.isStrictMode = oldStrictMode;
        }
    }

    private void verifyUseStrict(ParserContextFunctionNode function, int parseFlags) {
        for (Node node : this.lc.peek().getStatements()) {
            this.getValue(node.getToken());
        }
        if (function.getIdent() != null) {
            this.verifyStrictIdent(function.getIdent(), CONTEXT_FUNCTION_NAME);
        }
        for (IdentNode identNode : function.getParameters()) {
            this.verifyStrictIdent(identNode, CONTEXT_FUNCTION_PARAMETER);
        }
        if ((parseFlags & 4) != 0) {
            this.setupStrictEvalScope();
        }
    }

    private void setupStrictEvalScope() {
        ParserContextBlockNode body = this.lc.getCurrentBlock();
        assert (body.getScope().getSymbolCount() == 0);
        if (body.getScope().isGlobalScope()) {
            Scope evalScope = Scope.createEval(body.getScope(), true);
            body.setScope(evalScope);
            ParserContextFunctionNode function = this.lc.getCurrentFunction();
            function.replaceBodyScope(evalScope);
            assert (function.getBodyScope() == evalScope);
        }
    }

    private void statement(boolean yield, boolean await2) {
        this.statement(yield, await2, false, 0, false, false, false);
    }

    private void statement(boolean yield, boolean await2, boolean topLevel, int reparseFlags, boolean singleStatement, boolean labelledStatement, boolean mayBeFunctionDeclaration) {
        this.statement(yield, await2, topLevel, reparseFlags, singleStatement, labelledStatement, mayBeFunctionDeclaration, mayBeFunctionDeclaration);
    }

    private void statement(boolean yield, boolean await2, boolean topLevel, int reparseFlags, boolean singleStatement, boolean labelledStatement, boolean mayBeFunctionDeclaration, boolean mayBeLabeledFunctionDeclaration) {
        switch (this.type) {
            case LBRACE: {
                this.block(yield, await2);
                return;
            }
            case VAR: {
                this.variableStatement(this.type, yield, await2);
                return;
            }
            case SEMICOLON: {
                this.emptyStatement();
                return;
            }
            case IF: {
                this.ifStatement(yield, await2);
                return;
            }
            case FOR: {
                this.forStatement(yield, await2);
                return;
            }
            case WHILE: {
                this.whileStatement(yield, await2);
                return;
            }
            case DO: {
                this.doStatement(yield, await2);
                return;
            }
            case CONTINUE: {
                this.continueStatement(yield, await2);
                return;
            }
            case BREAK: {
                this.breakStatement(yield, await2);
                return;
            }
            case RETURN: {
                this.returnStatement(yield, await2);
                return;
            }
            case WITH: {
                this.withStatement(yield, await2);
                return;
            }
            case SWITCH: {
                this.switchStatement(yield, await2);
                return;
            }
            case THROW: {
                this.throwStatement(yield, await2);
                return;
            }
            case TRY: {
                this.tryStatement(yield, await2);
                return;
            }
            case DEBUGGER: {
                this.debuggerStatement();
                return;
            }
            case EOF: 
            case RPAREN: 
            case RBRACKET: {
                this.expect(TokenType.SEMICOLON);
                return;
            }
            case FUNCTION: {
                if (singleStatement && (this.isStrictMode || !mayBeFunctionDeclaration)) {
                    throw this.error(AbstractParser.message(MSG_EXPECTED_STMT, CONTEXT_FUNCTION_DECLARATION), this.token);
                }
                this.functionDeclaration(true, topLevel || labelledStatement, singleStatement, yield, await2, false);
                return;
            }
            case LET: {
                TokenType lookahead;
                if (!this.useBlockScope() || (lookahead = this.lookaheadOfLetDeclaration()) == null) break;
                if (singleStatement) {
                    if (lookahead != TokenType.LBRACKET && this.T(this.k + 1) != TokenType.IDENT) break;
                    throw this.error(AbstractParser.message(MSG_EXPECTED_STMT, CONTEXT_LET_DECLARATION), this.token);
                }
                this.variableStatement(this.type, yield, await2);
                return;
            }
            case CONST: {
                if (this.useBlockScope()) {
                    if (singleStatement) {
                        throw this.error(AbstractParser.message(MSG_EXPECTED_STMT, CONTEXT_CONST_DECLARATION), this.token);
                    }
                    this.variableStatement(this.type, yield, await2);
                    return;
                }
                if (!this.env.constAsVar) break;
                this.variableStatement(TokenType.VAR, yield, await2);
                return;
            }
            case CLASS: 
            case AT: {
                if (!ES6_CLASS || !this.isES6()) break;
                if (singleStatement) {
                    throw this.error(AbstractParser.message(MSG_EXPECTED_STMT, CONTEXT_CLASS_DECLARATION), this.token);
                }
                this.classDeclaration(yield, await2, false);
                return;
            }
            case ASYNC: {
                if (!this.isAsync() || !this.lookaheadIsAsyncFunction()) break;
                if (singleStatement) {
                    throw this.error(AbstractParser.message(MSG_EXPECTED_STMT, CONTEXT_ASYNC_FUNCTION_DECLARATION), this.token);
                }
                this.asyncFunctionDeclaration(true, topLevel || labelledStatement, yield, await2, false);
                return;
            }
        }
        if (this.isBindingIdentifier()) {
            if (!(this.T(this.k + 1) != TokenType.COLON || this.type == TokenType.YIELD && yield || this.isAwait() && await2)) {
                this.labelStatement(yield, await2, mayBeLabeledFunctionDeclaration);
                return;
            }
            if (reparseFlags != 0 && this.reparseFunctionStatement(reparseFlags)) {
                return;
            }
        }
        this.expressionStatement(yield, await2);
    }

    private boolean reparseFunctionStatement(int reparseFlags) {
        boolean isES6Method;
        boolean allowPropertyFunction = (reparseFlags & 1) != 0;
        boolean bl = isES6Method = (reparseFlags & 2) != 0;
        if (allowPropertyFunction) {
            long propertyToken = this.token;
            int propertyLine = this.line;
            if (this.type == TokenType.GET) {
                this.next();
                this.addPropertyFunctionStatement(this.propertyGetterFunction(propertyToken, propertyLine, false, false, false));
                return true;
            }
            if (this.type == TokenType.SET) {
                this.next();
                this.addPropertyFunctionStatement(this.propertySetterFunction(propertyToken, propertyLine, false, false, false));
                return true;
            }
        } else if (isES6Method) {
            TruffleString ident = (TruffleString)this.getValue();
            IdentNode identNode = this.createIdentNode(this.token, this.finish, ident).setIsPropertyName();
            long propertyToken = this.token;
            int propertyLine = this.line;
            this.next();
            int flags = CONSTRUCTOR_NAME.equals(ident) ? 0x200000 : 0x100000;
            this.addPropertyFunctionStatement(this.propertyMethodFunction(identNode, propertyToken, propertyLine, false, flags, false, false));
            return true;
        }
        return false;
    }

    private void addPropertyFunctionStatement(PropertyFunction propertyFunction) {
        FunctionNode fn = propertyFunction.functionNode;
        this.functionDeclarations.add(new ExpressionStatement(fn.getLineNumber(), fn.getToken(), this.finish, fn));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ClassNode classDeclaration(boolean yield, boolean await2, boolean defaultExport) {
        assert (this.type == TokenType.CLASS || this.type == TokenType.AT);
        List<Expression> classDecorators = null;
        if (this.type == TokenType.AT) {
            assert (this.isES2023());
            classDecorators = this.decoratorList(yield, await2);
        }
        int classLineNumber = this.line;
        long classToken = this.token;
        this.next();
        boolean oldStrictMode = this.isStrictMode;
        this.isStrictMode = true;
        try {
            IdentNode className = null;
            if (!defaultExport || this.isBindingIdentifier()) {
                className = this.bindingIdentifier(yield, await2, CONTEXT_CLASS_NAME);
            }
            ClassNode classExpression = this.classTail(classLineNumber, classToken, className, yield, await2, classDecorators);
            if (!defaultExport) {
                VarNode classVar = new VarNode(classLineNumber, Token.recast(classExpression.getToken(), TokenType.LET), classExpression.getFinish(), className, classExpression, 1);
                this.appendStatement(classVar);
                this.declareVar(this.lc.getCurrentScope(), classVar);
            }
            ClassNode classNode = classExpression;
            return classNode;
        }
        finally {
            this.isStrictMode = oldStrictMode;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ClassNode classExpression(boolean yield, boolean await2) {
        assert (this.type == TokenType.CLASS || this.type == TokenType.AT);
        List<Expression> classDecorators = null;
        if (this.type == TokenType.AT) {
            assert (this.isES2023());
            classDecorators = this.decoratorList(yield, await2);
        }
        int classLineNumber = this.line;
        long classToken = this.token;
        this.next();
        boolean oldStrictMode = this.isStrictMode;
        this.isStrictMode = true;
        try {
            IdentNode className = null;
            if (this.isBindingIdentifier()) {
                className = this.bindingIdentifier(yield, await2, CONTEXT_CLASS_NAME);
            }
            ClassNode classNode = this.classTail(classLineNumber, classToken, className, yield, await2, classDecorators);
            return classNode;
        }
        finally {
            this.isStrictMode = oldStrictMode;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ClassNode classTail(int classLineNumber, long classToken, IdentNode className, boolean yield, boolean await2, List<Expression> classDecorators) {
        assert (this.isStrictMode);
        Scope classHeadScope = Scope.createClassHead(this.lc.getCurrentScope());
        if (className != null) {
            classHeadScope.putSymbol(new Symbol(className.getNameTS(), 2));
        }
        ParserContextClassNode classNode = new ParserContextClassNode(classHeadScope);
        this.lc.push(classNode);
        try {
            Expression classHeritage = null;
            if (this.type == TokenType.EXTENDS) {
                this.next();
                classHeritage = this.leftHandSideExpression(yield, await2, CoverExpressionError.DENY);
                IdentNode invalidPrivateIdent = classNode.verifyAllPrivateIdentifiersValid(this.lc);
                if (invalidPrivateIdent != null) {
                    throw this.error(AbstractParser.message(MSG_INVALID_PRIVATE_IDENT, new String[0]), invalidPrivateIdent.getToken());
                }
            }
            this.expect(TokenType.LBRACE);
            Scope classScope = Scope.createClassBody(classHeadScope);
            classNode.setScope(classScope);
            PropertyNode constructor = null;
            ArrayList<ClassElement> classElements = new ArrayList<ClassElement>();
            HashMap<String, Integer> privateNameToAccessorIndexMap = new HashMap<String, Integer>();
            int instanceFieldCount = 0;
            int staticElementCount = 0;
            boolean hasPrivateMethods = false;
            boolean hasPrivateInstanceMethods = false;
            while (true) {
                ClassElement classElement;
                long classElementToken;
                block39: {
                    boolean isStatic;
                    block40: {
                        Integer existing;
                        block41: {
                            Expression classElementName;
                            boolean computed;
                            TokenType nameTokenType;
                            boolean generator;
                            boolean async;
                            int classElementLine;
                            boolean isAutoAccessor;
                            List<Expression> classElementDecorators;
                            block38: {
                                TokenType nextToken;
                                if (this.type == TokenType.SEMICOLON) {
                                    this.next();
                                    continue;
                                }
                                if (this.type == TokenType.RBRACE) break;
                                classElementDecorators = null;
                                if (this.type == TokenType.AT) {
                                    classElementDecorators = this.decoratorList(yield, await2);
                                }
                                isAutoAccessor = false;
                                if (this.isES2023() && this.type == TokenType.ACCESSOR && (nextToken = this.lookaheadNoLineTerminator()) != TokenType.LPAREN && nextToken != TokenType.ASSIGN && nextToken != TokenType.SEMICOLON && nextToken != TokenType.RBRACE && nextToken != TokenType.EOL) {
                                    isAutoAccessor = true;
                                    this.next();
                                }
                                isStatic = false;
                                if (this.type == TokenType.STATIC) {
                                    TokenType nextToken2 = this.lookahead();
                                    if (this.isES2023() && nextToken2 == TokenType.ACCESSOR) {
                                        this.next();
                                        nextToken2 = this.lookaheadNoLineTerminator();
                                        if (nextToken2 != TokenType.LPAREN && nextToken2 != TokenType.ASSIGN && nextToken2 != TokenType.SEMICOLON && nextToken2 != TokenType.RBRACE && nextToken2 != TokenType.EOL) {
                                            isStatic = true;
                                            isAutoAccessor = true;
                                            this.next();
                                        } else {
                                            isStatic = true;
                                        }
                                    } else if (nextToken2 != TokenType.LPAREN && nextToken2 != TokenType.ASSIGN && nextToken2 != TokenType.SEMICOLON && nextToken2 != TokenType.RBRACE) {
                                        isStatic = true;
                                        int staticLine = this.line;
                                        long staticToken = this.token;
                                        this.next();
                                        if (this.type == TokenType.LBRACE && this.isES2022()) {
                                            if (classElementDecorators != null && classElementDecorators.size() != 0) {
                                                throw this.error(AbstractParser.message(MSG_DECORATED_STATIC_BLOCK, new String[0]), staticToken);
                                            }
                                            ClassElement staticInit = this.staticInitializer(staticLine, staticToken);
                                            ++staticElementCount;
                                            classElements.add(staticInit);
                                            continue;
                                        }
                                    }
                                }
                                classElementToken = this.token;
                                classElementLine = this.line;
                                async = false;
                                if (this.isAsync() && this.lookaheadIsAsyncMethod(true)) {
                                    async = true;
                                    this.next();
                                }
                                generator = false;
                                if (this.type == TokenType.MUL && ES6_GENERATOR_FUNCTION && this.isES6()) {
                                    generator = true;
                                    this.next();
                                }
                                computed = (nameTokenType = this.type) == TokenType.LBRACKET;
                                classElementName = this.classElementName(yield, await2, true);
                                if (generator || async || !this.isClassFieldDefinition(nameTokenType)) break block38;
                                classElement = this.fieldDefinition(classElementName, isStatic, isAutoAccessor, classElementToken, computed, classElementDecorators);
                                if (isStatic) {
                                    ++staticElementCount;
                                } else {
                                    ++instanceFieldCount;
                                }
                                break block39;
                            }
                            if (isAutoAccessor) {
                                throw this.error(AbstractParser.message(MSG_AUTO_ACCESSOR_NOT_FIELD, new String[0]));
                            }
                            classElement = this.methodDefinition(classElementName, isStatic, classHeritage != null, generator, async, classElementToken, classElementLine, yield, await2, nameTokenType, computed, classElementDecorators);
                            if (classElement.isComputed() || !classElement.isAccessor()) break block39;
                            if (!classElement.isPrivate()) break block40;
                            String privateName = classElement.getPrivateName();
                            existing = (Integer)privateNameToAccessorIndexMap.get(privateName);
                            if (existing != null) break block41;
                            privateNameToAccessorIndexMap.put(privateName, classElements.size());
                            break block39;
                        }
                        ClassElement otherAccessor = (ClassElement)classElements.get(existing);
                        if (isStatic != otherAccessor.isStatic()) break block39;
                        if (otherAccessor.getGetter() == null && classElement.getGetter() != null) {
                            classElements.set(existing, otherAccessor.setGetter(classElement.getGetter()));
                            continue;
                        }
                        if (otherAccessor.getSetter() == null && classElement.getSetter() != null) {
                            classElements.set(existing, otherAccessor.setSetter(classElement.getSetter()));
                            continue;
                        }
                        break block39;
                    }
                    if (!classElements.isEmpty()) {
                        ClassElement lastElement = (ClassElement)classElements.get(classElements.size() - 1);
                        if (classElement.getDecorators() == null && lastElement.getDecorators() == null && !lastElement.isComputed() && lastElement.isAccessor() && isStatic == lastElement.isStatic() && !lastElement.isPrivate() && classElement.getKeyName().equals(lastElement.getKeyName())) {
                            ClassElement merged = classElement.getGetter() != null ? lastElement.setGetter(classElement.getGetter()) : lastElement.setSetter(classElement.getSetter());
                            classElements.set(classElements.size() - 1, merged);
                            continue;
                        }
                    }
                }
                if (classElement.isPrivate()) {
                    hasPrivateMethods = hasPrivateMethods || !classElement.isClassField();
                    hasPrivateInstanceMethods = hasPrivateInstanceMethods || !classElement.isClassField() && !classElement.isStatic();
                    this.declarePrivateName(classScope, classElement);
                }
                if (!classElement.isStatic() && !classElement.isComputed() && classElement.getKeyNameTS().equals(CONSTRUCTOR_NAME)) {
                    assert (!classElement.isClassField());
                    if (constructor == null) {
                        if (classElement.getDecorators() != null && classElement.getDecorators().size() > 0) {
                            throw this.error(AbstractParser.message(MSG_DECORATED_CONSTRUCTOR, new String[0]));
                        }
                        constructor = classElement;
                        continue;
                    }
                    throw this.error(AbstractParser.message(MSG_MULTIPLE_CONSTRUCTORS, new String[0]), classElementToken);
                }
                classElements.add(classElement);
            }
            long lastToken = this.token;
            this.expect(TokenType.RBRACE);
            int classFinish = Token.descPosition(lastToken) + Token.descLength(lastToken);
            if (constructor == null) {
                constructor = this.createDefaultClassConstructor(classLineNumber, classToken, lastToken, className, classHeritage != null);
            } else {
                FunctionNode ctor = (FunctionNode)constructor.getValue();
                int flags = ctor.getFlags();
                if (className == null) {
                    flags |= 1;
                }
                constructor = ((ClassElement)constructor).setValue(new FunctionNode(ctor.getSource(), ctor.getLineNumber(), ctor.getToken(), classFinish, classToken, lastToken, className, className == null ? TruffleString.Encoding.UTF_16.getEmpty() : className.getNameTS(), ctor.getLength(), ctor.getNumOfParams(), ctor.getParameters(), flags, ctor.getBody(), ctor.getEndParserState(), ctor.getModule(), ctor.getInternalNameTS()));
            }
            IdentNode invalidPrivateIdent = classNode.verifyAllPrivateIdentifiersValid(this.lc);
            if (invalidPrivateIdent != null) {
                throw this.error(AbstractParser.message(MSG_INVALID_PRIVATE_IDENT, new String[0]), invalidPrivateIdent.getToken());
            }
            if (hasPrivateMethods) {
                classScope.putSymbol(new Symbol(this.lexer.stringIntern(ClassNode.PRIVATE_CONSTRUCTOR_BINDING_NAME), 132098));
            }
            classScope.close();
            classHeadScope.close();
            ClassNode classNode2 = new ClassNode(classToken, classFinish, className, classHeritage, (ClassElement)constructor, classElements, classDecorators, classScope, instanceFieldCount, staticElementCount, hasPrivateMethods, hasPrivateInstanceMethods);
            return classNode2;
        }
        finally {
            this.lc.pop(classNode);
        }
    }

    private Expression classElementName(boolean yield, boolean await2, boolean allowPrivate) {
        if (allowPrivate && this.type == TokenType.PRIVATE_IDENT) {
            return this.privateIdentifierDeclaration();
        }
        return this.propertyName(yield, await2);
    }

    private IdentNode parsePrivateIdentifier() {
        assert (this.type == TokenType.PRIVATE_IDENT);
        if (!this.isClassFields() && !this.isES2021()) {
            throw this.error(AbstractParser.message(MSG_UNEXPECTED_TOKEN, this.type.getNameOrType()));
        }
        long identToken = this.token;
        TruffleString name = (TruffleString)this.getValue(identToken);
        this.next();
        return this.createIdentNode(identToken, this.finish, name).setIsPrivate();
    }

    private IdentNode privateIdentifierDeclaration() {
        IdentNode privateIdent = this.parsePrivateIdentifier();
        ParserContextClassNode currentClass = this.lc.getCurrentClass();
        if (currentClass == null) {
            throw this.error(AbstractParser.message(MSG_INVALID_PRIVATE_IDENT, new String[0]), privateIdent.getToken());
        }
        return privateIdent;
    }

    private void declarePrivateName(Scope classScope, ClassElement classElement) {
        int privateFlags;
        int n = privateFlags = classElement.isStatic() ? 262144 : 0;
        if (!classElement.isClassField()) {
            privateFlags |= classElement.isAccessor() ? 0x100000 : 524288;
        }
        if (!classScope.addPrivateName(classElement.getPrivateNameTS(), privateFlags)) {
            throw this.error(ECMAErrors.getMessage(MSG_SYNTAX_ERROR_REDECLARE_VARIABLE, classElement.getPrivateName()), classElement.getKey().getToken());
        }
    }

    private IdentNode privateIdentifierUse() {
        IdentNode privateIdent = this.parsePrivateIdentifier();
        Scope currentScope = this.lc.getCurrentScope();
        ParserContextClassNode currentClass = this.lc.getCurrentClass();
        if (currentClass != null) {
            currentClass.usePrivateName(privateIdent);
        } else if (!currentScope.findPrivateName(privateIdent.getName())) {
            throw this.error(AbstractParser.message(MSG_INVALID_PRIVATE_IDENT, new String[0]), privateIdent.getToken());
        }
        currentScope.addIdentifierReference(privateIdent.getName());
        return privateIdent;
    }

    private boolean isClassFieldDefinition(TokenType nameTokenType) {
        if (!this.isClassFields()) {
            return false;
        }
        switch (this.type) {
            case SEMICOLON: 
            case RBRACE: 
            case ASSIGN: {
                return true;
            }
            case LPAREN: {
                return false;
            }
        }
        if (nameTokenType == TokenType.GET || nameTokenType == TokenType.SET) {
            return false;
        }
        return this.last == TokenType.EOL;
    }

    private ClassElement createDefaultClassConstructor(int classLineNumber, long classToken, long lastToken, IdentNode className, boolean derived) {
        List parameters;
        List statements;
        int ctorFinish = this.finish;
        long identToken = Token.recast(classToken, TokenType.IDENT);
        if (derived) {
            IdentNode superIdent = new IdentNode(identToken, ctorFinish, this.lexer.stringIntern(TokenType.SUPER.getNameTS())).setIsDirectSuper();
            IdentNode argsIdent = new IdentNode(identToken, ctorFinish, this.lexer.stringIntern(ARGS)).setIsRestParameter();
            UnaryNode spreadArgs = new UnaryNode(Token.recast(classToken, TokenType.SPREAD_ARGUMENT), (Expression)argsIdent);
            Expression superCall = CallNode.forCall(classLineNumber, classToken, Token.descPosition(classToken), ctorFinish, superIdent, List.of((Object)spreadArgs), false, false, false, false, true);
            statements = List.of((Object)new ExpressionStatement(classLineNumber, classToken, ctorFinish, superCall));
            parameters = List.of((Object)argsIdent);
        } else {
            statements = List.of();
            parameters = List.of();
        }
        int functionFlags = 0x300000;
        ParserContextFunctionNode function = this.createParserContextFunctionNode(className, classToken, functionFlags, classLineNumber, parameters, 0);
        function.setLastToken(lastToken);
        Scope scope = function.createBodyScope(this.lexer::stringIntern);
        scope.close();
        Block body = new Block(classToken, ctorFinish, 32, scope, statements);
        if (derived) {
            function.setFlag(0x400000);
            function.setFlag(262144);
        }
        if (className == null) {
            function.setFlag(1);
            function.setInternalName(this.lexer.stringIntern(CONSTRUCTOR_NAME));
        }
        this.lc.setCurrentFunctionFlag(16384);
        return ClassElement.createDefaultConstructor(classToken, ctorFinish, new IdentNode(identToken, ctorFinish, CONSTRUCTOR_NAME), this.createFunctionNode(function, classToken, className, classLineNumber, body));
    }

    private ClassElement methodDefinition(Expression propertyName, boolean isStatic, boolean derived, boolean generator, boolean async, long startToken, int methodLine, boolean yield, boolean await2, TokenType nameTokenType, boolean computed, List<Expression> classElementDecorators) {
        int flags = 0x100000;
        boolean isPrivate = false;
        if (!computed) {
            String name = ((PropertyKey)((Object)propertyName)).getPropertyName();
            if (!generator && nameTokenType == TokenType.GET && this.type != TokenType.LPAREN) {
                PropertyFunction methodDefinition = this.propertyGetterFunction(startToken, methodLine, yield, await2, true);
                this.verifyAllowedMethodName(methodDefinition.key, isStatic, methodDefinition.computed, generator, true, async);
                return ClassElement.createAccessor(startToken, this.finish, methodDefinition.key, methodDefinition.functionNode, null, classElementDecorators, isPrivate, isStatic, methodDefinition.computed);
            }
            if (!generator && nameTokenType == TokenType.SET && this.type != TokenType.LPAREN) {
                PropertyFunction methodDefinition = this.propertySetterFunction(startToken, methodLine, yield, await2, true);
                this.verifyAllowedMethodName(methodDefinition.key, isStatic, methodDefinition.computed, generator, true, async);
                return ClassElement.createAccessor(startToken, this.finish, methodDefinition.key, null, methodDefinition.functionNode, classElementDecorators, isPrivate, isStatic, methodDefinition.computed);
            }
            if (!isStatic && !generator && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
                flags |= 0x200000;
                if (derived) {
                    flags |= 0x400000;
                }
            }
            this.verifyAllowedMethodName(propertyName, isStatic, computed, generator, false, async);
        }
        PropertyFunction methodDefinition = this.propertyMethodFunction(propertyName, startToken, methodLine, generator, flags, computed, async);
        return ClassElement.createMethod(startToken, this.finish, methodDefinition.key, methodDefinition.functionNode, classElementDecorators, isStatic, computed);
    }

    private void verifyAllowedMethodName(Expression key, boolean isStatic, boolean computed, boolean generator, boolean accessor, boolean async) {
        if (!computed) {
            String name = ((PropertyKey)((Object)key)).getPropertyName();
            if (!isStatic && generator && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
                throw this.error(AbstractParser.message(MSG_GENERATOR_CONSTRUCTOR, new String[0]), key.getToken());
            }
            if (!isStatic && accessor && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
                throw this.error(AbstractParser.message(MSG_ACCESSOR_CONSTRUCTOR, new String[0]), key.getToken());
            }
            if (!isStatic && async && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
                throw this.error(AbstractParser.message(MSG_ASYNC_CONSTRUCTOR, new String[0]), key.getToken());
            }
            if (isStatic && name.equals(PROTOTYPE_NAME)) {
                throw this.error(AbstractParser.message(MSG_STATIC_PROTOTYPE_METHOD, new String[0]), key.getToken());
            }
            if (name.equals(PRIVATE_CONSTRUCTOR_NAME)) {
                throw this.error(AbstractParser.message(MSG_PRIVATE_CONSTRUCTOR_METHOD, new String[0]), key.getToken());
            }
        }
    }

    private ClassElement fieldDefinition(Expression propertyName, boolean isStatic, boolean isAutoAccessor, long startToken, boolean computed, List<Expression> classElementDecorators) {
        if (!computed && propertyName instanceof PropertyKey) {
            String name = ((PropertyKey)((Object)propertyName)).getPropertyName();
            if (CONSTRUCTOR_NAME.toJavaStringUncached().equals(name) || PRIVATE_CONSTRUCTOR_NAME.equals(name)) {
                throw this.error(AbstractParser.message(MSG_CONSTRUCTOR_FIELD, new String[0]), startToken);
            }
            if (isStatic && PROTOTYPE_NAME.equals(name)) {
                throw this.error(AbstractParser.message(MSG_STATIC_PROTOTYPE_FIELD, new String[0]), startToken);
            }
        }
        FunctionNode initializer = null;
        boolean isAnonymousFunctionDefinition = false;
        if (this.type == TokenType.ASSIGN) {
            this.next();
            Pair<FunctionNode, Boolean> pair = this.fieldInitializer(this.line, startToken, propertyName, computed);
            initializer = pair.getLeft();
            isAnonymousFunctionDefinition = pair.getRight();
            this.endOfLine();
        }
        if (isAutoAccessor) {
            return ClassElement.createAutoAccessor(startToken, this.finish, propertyName, initializer, classElementDecorators, isStatic, computed, isAnonymousFunctionDefinition);
        }
        return ClassElement.createField(startToken, this.finish, propertyName, initializer, classElementDecorators, isStatic, computed, isAnonymousFunctionDefinition);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Pair<FunctionNode, Boolean> fieldInitializer(int lineNumber, long fieldToken, Expression propertyName, boolean computed) {
        Expression initializer;
        int functionFlags = 0x40100001;
        ParserContextFunctionNode function = this.createParserContextFunctionNode(null, fieldToken, functionFlags, lineNumber, List.of(), 0);
        function.setInternalName(this.lexer.stringIntern(INITIALIZER_FUNCTION_NAME));
        this.lc.push(function);
        ParserContextBlockNode body = this.newBlock(function.createBodyScope(this.lexer::stringIntern));
        try {
            initializer = this.assignmentExpression(true, false, false);
        }
        finally {
            function.finishBodyScope(this.lexer::stringIntern);
            this.restoreBlock(body);
            this.lc.propagateFunctionFlags();
            this.lc.pop(function);
        }
        assert (function.getFlag(8) == 0);
        function.setLastToken(this.token);
        boolean isAnonymousFunctionDefinition = false;
        if (Parser.isAnonymousFunctionDefinition(initializer)) {
            if (!computed && propertyName instanceof PropertyKey) {
                initializer = this.setAnonymousFunctionName(initializer, ((PropertyKey)((Object)propertyName)).getPropertyNameTS());
            } else {
                isAnonymousFunctionDefinition = true;
                initializer = new UnaryNode(Token.recast(initializer.getToken(), TokenType.NAMEDEVALUATION), initializer);
            }
        }
        this.lc.setCurrentFunctionFlag(16384);
        List statements = List.of((Object)new ReturnNode(lineNumber, fieldToken, this.finish, initializer));
        Block bodyBlock = new Block(fieldToken, this.finish, 48, body.getScope(), statements);
        return Pair.create(this.createFunctionNode(function, fieldToken, null, lineNumber, bodyBlock), isAnonymousFunctionDefinition);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ClassElement staticInitializer(int lineNumber, long staticToken) {
        Block bodyBlock;
        assert (this.type == TokenType.LBRACE);
        int functionFlags = 0x40100001;
        ParserContextFunctionNode function = this.createParserContextFunctionNode(null, staticToken, functionFlags, lineNumber, List.of(), 0);
        function.setInternalName(this.lexer.stringIntern(INITIALIZER_FUNCTION_NAME));
        this.lc.push(function);
        try {
            bodyBlock = this.functionBody(function);
        }
        finally {
            this.lc.pop(function);
        }
        assert (function.getFlag(8) == 0);
        this.lc.setCurrentFunctionFlag(16384);
        FunctionNode functionNode = this.createFunctionNode(function, staticToken, null, lineNumber, bodyBlock);
        return ClassElement.createStaticInitializer(staticToken, this.finish, functionNode);
    }

    private boolean isPropertyName(long currentToken) {
        TokenType currentType = Token.descType(currentToken);
        if (ES6_COMPUTED_PROPERTY_NAME && currentType == TokenType.LBRACKET && this.isES6()) {
            return true;
        }
        switch (currentType) {
            case IDENT: {
                return true;
            }
            case NON_OCTAL_DECIMAL: 
            case OCTAL_LEGACY: {
                if (this.isStrictMode) {
                    return false;
                }
            }
            case STRING: 
            case ESCSTRING: 
            case DECIMAL: 
            case HEXADECIMAL: 
            case OCTAL: 
            case BINARY_NUMBER: 
            case BIGINT: 
            case FLOATING: {
                return true;
            }
        }
        return this.isIdentifierName(currentToken);
    }

    private void block(boolean yield, boolean await2) {
        this.appendStatement(new BlockStatement(this.line, this.getBlock(yield, await2, true)));
    }

    private void statementList(boolean yield, boolean await2) {
        block3: while (this.type != TokenType.EOF) {
            switch (this.type) {
                case EOF: 
                case RBRACE: 
                case CASE: 
                case DEFAULT: {
                    break block3;
                }
                default: {
                    this.statement(yield, await2);
                    continue block3;
                }
            }
        }
    }

    private void verifyIdent(IdentNode ident, boolean yield, boolean await2) {
        boolean awaitOrModule;
        if (this.isES6()) {
            if (Parser.isEscapedIdent(ident) && Parser.isReservedWordSequence(ident.getName())) {
                throw this.error(AbstractParser.message(MSG_ESCAPED_KEYWORD, ident), ident.getToken());
            }
            assert (!Parser.isReservedWordSequence(ident.getName())) : ident.getName();
        }
        if (yield) {
            if (ident.isTokenType(TokenType.YIELD)) {
                throw this.error(this.expectMessage(TokenType.IDENT, ident.getToken()), ident.getToken());
            }
            if (Parser.isEscapedIdent(ident) && TokenType.YIELD.getName().equals(ident.getName())) {
                throw this.error(AbstractParser.message(MSG_ESCAPED_KEYWORD, ident), ident.getToken());
            }
            assert (!TokenType.YIELD.getName().equals(ident.getName()));
        }
        boolean bl = awaitOrModule = await2 || this.isModule;
        if (ident.isTokenType(TokenType.AWAIT)) {
            if (awaitOrModule) {
                throw this.error(this.expectMessage(TokenType.IDENT, ident.getToken()), ident.getToken());
            }
            this.recordYieldOrAwait(ident);
        } else if (Parser.isEscapedIdent(ident) && TokenType.AWAIT.getName().equals(ident.getName())) {
            if (awaitOrModule) {
                throw this.error(AbstractParser.message(MSG_ESCAPED_KEYWORD, ident), ident.getToken());
            }
            this.recordYieldOrAwait(ident);
        } else assert (!TokenType.AWAIT.getName().equals(ident.getName()));
    }

    private static boolean isEscapedIdent(IdentNode ident) {
        return ident.getName().length() != Token.descLength(ident.getToken());
    }

    private static boolean isReservedWordSequence(String name) {
        TokenType tokenType = TokenLookup.lookupKeyword(name, 0, name.length());
        return tokenType != TokenType.IDENT && !tokenType.isContextualKeyword() && !tokenType.isFutureStrict();
    }

    private void verifyStrictIdent(IdentNode ident, String contextString, boolean bindingIdentifier) {
        if (this.isStrictMode && !Parser.isValidStrictIdent(ident, bindingIdentifier)) {
            throw this.error(AbstractParser.message(MSG_STRICT_NAME, ident.getName(), contextString), ident.getToken());
        }
    }

    private void verifyStrictIdent(IdentNode ident, String contextString) {
        this.verifyStrictIdent(ident, contextString, true);
    }

    private static boolean isValidStrictIdent(IdentNode ident, boolean bindingIdentifier) {
        if (bindingIdentifier && (EVAL_NAME.equals(ident.getName()) || ARGUMENTS_NAME.equals(ident.getNameTS()))) {
            return false;
        }
        return !Parser.isFutureStrictName(ident);
    }

    private static boolean isFutureStrictName(IdentNode ident) {
        if (ident.tokenType().isFutureStrict()) {
            return true;
        }
        if (Parser.isEscapedIdent(ident)) {
            TokenType tokenType = TokenLookup.lookupKeyword(ident.getName(), 0, ident.getName().length());
            return tokenType != TokenType.IDENT && tokenType.isFutureStrict();
        }
        return false;
    }

    private void variableStatement(TokenType varType, boolean yield, boolean await2) {
        this.variableDeclarationList(varType, true, yield, await2, -1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ForVariableDeclarationListResult variableDeclarationList(final TokenType varType, boolean isStatement, boolean yield, boolean await2, final int sourceOrder) {
        int varStart = Token.descPosition(this.token);
        assert (varType == TokenType.VAR || varType == TokenType.LET || varType == TokenType.CONST);
        this.next();
        int varFlags = 0;
        if (varType == TokenType.LET) {
            varFlags |= 1;
        } else if (varType == TokenType.CONST) {
            varFlags |= 2;
        }
        ForVariableDeclarationListResult forResult = isStatement ? null : new ForVariableDeclarationListResult();
        final Scope scope = this.lc.getCurrentScope();
        while (true) {
            boolean isDestructuring;
            final int varLine = this.line;
            final long varToken = Token.recast(this.token, varType);
            Expression binding = this.bindingIdentifierOrPattern(yield, await2, CONTEXT_VARIABLE_NAME);
            boolean bl = isDestructuring = !(binding instanceof IdentNode);
            if (isDestructuring) {
                final int finalVarFlags = varFlags | 0x10;
                this.verifyDestructuringBindingPattern(binding, new Consumer<IdentNode>(){

                    @Override
                    public void accept(IdentNode identNode) {
                        Parser.this.verifyStrictIdent(identNode, Parser.CONTEXT_VARIABLE_NAME);
                        if (varType != TokenType.VAR && identNode.getName().equals(TokenType.LET.getName())) {
                            throw Parser.this.error(AbstractParser.message(Parser.MSG_LET_LEXICAL_BINDING, new String[0]));
                        }
                        VarNode var = new VarNode(varLine, varToken, sourceOrder, identNode.getFinish(), identNode.setIsDeclaredHere(), null, finalVarFlags);
                        Parser.this.appendStatement(var);
                        Parser.this.declareVar(scope, var);
                    }
                });
            }
            Expression init2 = null;
            if (this.type == TokenType.ASSIGN) {
                if (!isStatement) {
                    forResult.recordDeclarationWithInitializer(varToken);
                }
                this.next();
                if (!isDestructuring) {
                    this.pushDefaultName(binding);
                }
                try {
                    init2 = this.assignmentExpression(isStatement, yield, await2);
                }
                finally {
                    if (!isDestructuring) {
                        this.popDefaultName();
                    }
                }
            } else if (isStatement) {
                if (isDestructuring) {
                    throw this.error(AbstractParser.message(MSG_MISSING_DESTRUCTURING_ASSIGNMENT, new String[0]), this.token);
                }
                if (varType == TokenType.CONST) {
                    throw this.error(AbstractParser.message(MSG_MISSING_CONST_ASSIGNMENT, ((IdentNode)binding).getName()));
                }
            }
            if (!isDestructuring) {
                assert (init2 != null || varType != TokenType.CONST || !isStatement);
                IdentNode ident = (IdentNode)binding;
                if (varType != TokenType.VAR && ident.getName().equals(TokenType.LET.getName())) {
                    throw this.error(AbstractParser.message(MSG_LET_LEXICAL_BINDING, new String[0]));
                }
                if (!isStatement) {
                    if (init2 == null && varType == TokenType.CONST) {
                        forResult.recordMissingAssignment(binding);
                    }
                    forResult.addBinding(binding);
                }
                if (Parser.isAnonymousFunctionDefinition(init2)) {
                    init2 = this.setAnonymousFunctionName(init2, ident.getNameTS());
                }
                VarNode var = new VarNode(varLine, varToken, sourceOrder, varStart, this.finish, ident.setIsDeclaredHere(), init2, varFlags);
                this.appendStatement(var);
                this.declareVar(scope, var);
            } else {
                assert (init2 != null || !isStatement);
                if (init2 != null) {
                    Expression assignment = this.verifyAssignment(Token.recast(varToken, TokenType.ASSIGN_INIT), binding, init2, true);
                    if (isStatement) {
                        this.appendStatement(new ExpressionStatement(varLine, assignment.getToken(), this.finish, assignment));
                    } else {
                        forResult.addAssignment(assignment);
                        forResult.addBinding(assignment);
                    }
                } else if (!isStatement) {
                    forResult.recordMissingAssignment(binding);
                    forResult.addBinding(binding);
                }
            }
            if (this.type != TokenType.COMMARIGHT) break;
            this.next();
        }
        if (isStatement) {
            this.endOfLine();
        }
        return forResult;
    }

    private void declareVar(Scope scope, VarNode varNode) {
        String name = varNode.getName().getName();
        if (this.detectVarNameConflict(scope, varNode)) {
            throw this.error(ECMAErrors.getMessage(MSG_SYNTAX_ERROR_REDECLARE_VARIABLE, name), varNode.getToken());
        }
        if (varNode.isBlockScoped()) {
            int symbolFlags = varNode.getSymbolFlags() | (scope.isSwitchBlockScope() ? 8192 : 0) | (varNode.isFunctionDeclaration() ? 65536 : 0);
            Symbol existing = scope.putSymbol(new Symbol(varNode.getName().getNameTS(), symbolFlags));
            assert (existing == null || existing.isBlockFunctionDeclaration() && varNode.isFunctionDeclaration()) : existing;
            if (varNode.isFunctionDeclaration() && this.isAnnexB()) {
                ParserContextFunctionNode function = this.lc.getCurrentFunction();
                Scope varScope = function.getBodyScope();
                if (!function.isStrict() && scope != varScope) {
                    assert (!scope.isFunctionBodyScope() && !scope.isFunctionParameterScope());
                    if (varScope.getExistingSymbol(name) == null && !scope.getParent().isLexicallyDeclaredName(name, true, true)) {
                        function.recordHoistableBlockFunctionDeclaration(varNode, scope);
                    }
                }
            }
        } else {
            ParserContextFunctionNode function = this.lc.getCurrentFunction();
            Scope varScope = function.getBodyScope();
            int symbolFlags = varNode.getSymbolFlags() | (varNode.isHoistableDeclaration() ? 256 : 0) | (varScope.isGlobalScope() ? 8 : 0);
            if (function.hasParameterExpressions() && function.getParameterBlock().getScope().hasSymbol(name)) {
                symbolFlags |= 0x1000;
            }
            varScope.putSymbol(new Symbol(varNode.getName().getNameTS(), symbolFlags));
            if (scope != varScope) {
                assert (scope.isBlockScope());
                function.recordHoistedVarDeclaration(varNode, scope);
            }
        }
    }

    private boolean detectVarNameConflict(Scope scope, VarNode varNode) {
        String varName = varNode.getName().getName();
        if (varNode.isBlockScoped()) {
            Scope currentScope = scope;
            Symbol existingSymbol = currentScope.getExistingSymbol(varName);
            if (existingSymbol != null) {
                return !existingSymbol.isBlockFunctionDeclaration() || this.isStrictMode || !this.isAnnexB() || !varNode.isFunctionDeclaration();
            }
            Scope parentScope = scope.getParent();
            return parentScope != null && (parentScope.isCatchParameterScope() || parentScope.isFunctionParameterScope()) && (existingSymbol = parentScope.getExistingSymbol(varName)) != null && !existingSymbol.isArguments();
        }
        return scope.isLexicallyDeclaredName(varName, this.isAnnexB(), false);
    }

    private boolean isAnnexB() {
        return this.env.annexB;
    }

    private boolean isIdentifier() {
        return this.type == TokenType.IDENT || this.type.isContextualKeyword() || this.isNonStrictModeIdent();
    }

    private IdentNode identifier(boolean yield, boolean await2, String contextString, boolean bindingIdentifier) {
        IdentNode ident = this.getIdent();
        this.verifyIdent(ident, yield, await2);
        this.verifyStrictIdent(ident, contextString, bindingIdentifier);
        return ident;
    }

    private IdentNode identifierReference(boolean yield, boolean await2) {
        IdentNode ident = this.identifier(yield, await2, CONTEXT_IDENTIFIER_REFERENCE, false);
        this.addIdentifierReference(ident.getName());
        return ident;
    }

    private IdentNode labelIdentifier(boolean yield, boolean await2) {
        return this.identifier(yield, await2, CONTEXT_LABEL_IDENTIFIER, false);
    }

    private boolean isBindingIdentifier() {
        return this.type == TokenType.IDENT || this.type.isContextualKeyword() || this.isNonStrictModeIdent();
    }

    private IdentNode bindingIdentifier(boolean yield, boolean await2, String contextString) {
        IdentNode ident = this.identifier(yield, await2, contextString, true);
        this.addIdentifierReference(ident.getName());
        return ident;
    }

    private void addIdentifierReference(String name) {
        Scope currentScope = this.lc.getCurrentScope();
        if (currentScope != null) {
            currentScope.addIdentifierReference(name);
        }
    }

    private Expression bindingPattern(boolean yield, boolean await2) {
        if (this.type == TokenType.LBRACKET) {
            return this.arrayLiteral(yield, await2, CoverExpressionError.IGNORE);
        }
        if (this.type == TokenType.LBRACE) {
            return this.objectLiteral(yield, await2, CoverExpressionError.IGNORE);
        }
        throw this.error(AbstractParser.message(MSG_EXPECTED_BINDING, new String[0]));
    }

    private Expression bindingIdentifierOrPattern(boolean yield, boolean await2, String contextString) {
        if (this.isBindingIdentifier() || !ES6_DESTRUCTURING || !this.isES6()) {
            return this.bindingIdentifier(yield, await2, contextString);
        }
        return this.bindingPattern(yield, await2);
    }

    private void verifyDestructuringBindingPattern(Expression pattern, final Consumer<IdentNode> identifierCallback) {
        assert (pattern instanceof ObjectNode || pattern instanceof LiteralNode.ArrayLiteralNode);
        pattern.accept(new VerifyDestructuringPatternNodeVisitor(new LexicalContext()){

            @Override
            protected void verifySpreadElement(Expression lvalue) {
                if (lvalue instanceof IdentNode) {
                    this.enterIdentNode((IdentNode)lvalue);
                } else if (Parser.this.isDestructuringLhs(lvalue)) {
                    Parser.this.verifyDestructuringBindingPattern(lvalue, identifierCallback);
                } else {
                    throw Parser.this.error("Expected a valid binding identifier", lvalue.getToken());
                }
            }

            @Override
            public boolean enterIdentNode(IdentNode identNode) {
                if (identNode.isParenthesized()) {
                    throw Parser.this.error("Expected a valid binding identifier", identNode.getToken());
                }
                identifierCallback.accept(identNode);
                return false;
            }

            @Override
            protected boolean enterDefault(Node node) {
                throw Parser.this.error(String.format("unexpected node in BindingPattern: %s", node));
            }
        });
    }

    private void emptyStatement() {
        if (this.env.emptyStatements) {
            this.appendStatement(new EmptyNode(this.line, this.token, Token.descPosition(this.token) + Token.descLength(this.token)));
        }
        this.next();
    }

    private void expressionStatement(boolean yield, boolean await2) {
        int expressionLine = this.line;
        long expressionToken = this.token;
        Expression expression = this.expression(yield, await2);
        if (expression != null) {
            this.endOfLine();
            ExpressionStatement expressionStatement = new ExpressionStatement(expressionLine, expressionToken, this.finish, expression);
            this.appendStatement(expressionStatement);
        } else {
            this.expect(null);
            this.endOfLine();
        }
    }

    private void ifStatement(boolean yield, boolean await2) {
        int ifLine = this.line;
        long ifToken = this.token;
        this.next();
        this.expect(TokenType.LPAREN);
        Expression test = this.expression(yield, await2);
        this.expect(TokenType.RPAREN);
        Block pass = this.getStatement(yield, await2, false, true, false);
        Node fail = null;
        if (this.type == TokenType.ELSE) {
            this.next();
            fail = this.getStatement(yield, await2, false, true, false);
        }
        this.appendStatement(new IfNode(ifLine, ifToken, fail != null ? fail.getFinish() : pass.getFinish(), test, pass, (Block)fail));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void forStatement(boolean yield, boolean await2) {
        boolean skipVars;
        ParserContextBlockNode outer;
        long forToken = this.token;
        int forLine = this.line;
        int forStart = Token.descPosition(forToken);
        if (this.useBlockScope()) {
            outer = this.newBlock();
            outer.setFlag(16);
        } else {
            outer = null;
        }
        ParserContextLoopNode forNode = new ParserContextLoopNode();
        this.lc.push(forNode);
        Node body = null;
        Expression init2 = null;
        JoinPredecessorExpression test = null;
        JoinPredecessorExpression modify = null;
        ForVariableDeclarationListResult varDeclList = null;
        CoverExpressionError initCoverExpr = CoverExpressionError.DENY;
        int flags = 0;
        boolean isForOf = false;
        boolean isForAwaitOf = false;
        boolean initStartsWithLet = false;
        boolean initStartsWithAsyncOf = false;
        try {
            this.next();
            if (this.env.syntaxExtensions && this.type == TokenType.IDENT && this.lexer.checkIdentForKeyword(this.token, "each")) {
                flags |= 2;
                this.next();
            } else if (ES8_FOR_AWAIT_OF && this.type == TokenType.AWAIT) {
                if (!await2) {
                    throw this.error(AbstractParser.message(MSG_INVALID_FOR_AWAIT_OF, new String[0]), this.token);
                }
                isForAwaitOf = true;
                this.next();
            }
            this.expect(TokenType.LPAREN);
            TokenType varType = null;
            switch (this.type) {
                case VAR: {
                    varType = this.type;
                    varDeclList = this.variableDeclarationList(varType, false, yield, await2, forStart);
                    break;
                }
                case SEMICOLON: {
                    break;
                }
                default: {
                    if (this.useBlockScope() && (this.type == TokenType.LET && this.lookaheadIsLetDeclaration() || this.type == TokenType.CONST)) {
                        varType = this.type;
                        varDeclList = this.variableDeclarationList(varType, false, yield, await2, forStart);
                        if (varType != TokenType.LET || forNode.getStatements().isEmpty()) break;
                        flags |= 4;
                        break;
                    }
                    if (this.env.constAsVar && this.type == TokenType.CONST) {
                        varType = TokenType.VAR;
                        varDeclList = this.variableDeclarationList(varType, false, yield, await2, forStart);
                        break;
                    }
                    initStartsWithLet = this.type == TokenType.LET;
                    initStartsWithAsyncOf = this.type == TokenType.ASYNC && !isForAwaitOf && this.lookaheadIsOf();
                    initCoverExpr = new CoverExpressionError();
                    init2 = this.expression(false, yield, await2, initCoverExpr);
                }
            }
            switch (this.type) {
                case SEMICOLON: {
                    if (varDeclList != null) {
                        assert (init2 == null);
                        init2 = varDeclList.init;
                        if (varDeclList.missingAssignment != null) {
                            if (varDeclList.missingAssignment instanceof IdentNode) {
                                throw this.error(AbstractParser.message(MSG_MISSING_CONST_ASSIGNMENT, ((IdentNode)varDeclList.missingAssignment).getName()));
                            }
                            throw this.error(AbstractParser.message(MSG_MISSING_DESTRUCTURING_ASSIGNMENT, new String[0]), varDeclList.missingAssignment.getToken());
                        }
                    } else if (init2 != null) {
                        this.verifyExpression(initCoverExpr);
                    }
                    if ((flags & 2) != 0) {
                        throw this.error(AbstractParser.message(MSG_FOR_EACH_WITHOUT_IN, new String[0]), this.token);
                    }
                    this.expect(TokenType.SEMICOLON);
                    if (this.type != TokenType.SEMICOLON) {
                        test = this.joinPredecessorExpression(yield, await2);
                    }
                    this.expect(TokenType.SEMICOLON);
                    if (this.type == TokenType.RPAREN) break;
                    modify = this.joinPredecessorExpression(yield, await2);
                    break;
                }
                case OF: {
                    if (!ES8_FOR_AWAIT_OF || !isForAwaitOf || initStartsWithLet) {
                        if (ES6_FOR_OF && !initStartsWithLet && !initStartsWithAsyncOf) {
                            isForOf = true;
                        } else {
                            this.expect(TokenType.SEMICOLON);
                            break;
                        }
                    }
                }
                case IN: {
                    if (isForAwaitOf) {
                        this.expectDontAdvance(TokenType.OF);
                        flags |= 0x10;
                    } else {
                        flags |= isForOf ? 8 : 1;
                    }
                    test = new JoinPredecessorExpression();
                    if (varDeclList != null) {
                        if (varDeclList.secondBinding != null) {
                            throw this.error(AbstractParser.message(MSG_MANY_VARS_IN_FOR_IN_LOOP, isForOf || isForAwaitOf ? CONTEXT_OF : CONTEXT_IN), varDeclList.secondBinding.getToken());
                        }
                        if (varDeclList.declarationWithInitializerToken != 0L && (this.isStrictMode || this.type != TokenType.IN || varType != TokenType.VAR || varDeclList.init != null)) {
                            throw this.error(AbstractParser.message(MSG_FOR_IN_LOOP_INITIALIZER, isForOf || isForAwaitOf ? CONTEXT_OF : CONTEXT_IN), varDeclList.declarationWithInitializerToken);
                        }
                        init2 = varDeclList.firstBinding;
                        assert (init2 instanceof IdentNode || this.isDestructuringLhs(init2));
                        if (varType == TokenType.CONST || varType == TokenType.LET) {
                            flags |= 4;
                        }
                    } else {
                        assert (init2 != null) : "for..in/of init expression can not be null here";
                        if (!this.checkValidLValue(init2, isForOf || isForAwaitOf ? CONTEXT_FOR_OF_ITERATOR : CONTEXT_FOR_IN_ITERATOR)) {
                            throw this.error(AbstractParser.message(MSG_NOT_LVALUE_FOR_IN_LOOP, isForOf || isForAwaitOf ? CONTEXT_OF : CONTEXT_IN), init2.getToken());
                        }
                    }
                    this.next();
                    modify = isForOf || isForAwaitOf ? new JoinPredecessorExpression(this.assignmentExpression(true, yield, await2)) : this.joinPredecessorExpression(yield, await2);
                    break;
                }
                default: {
                    this.expect(TokenType.SEMICOLON);
                }
            }
            this.expect(TokenType.RPAREN);
            body = this.getStatement(yield, await2);
            this.lc.pop(forNode);
        }
        catch (Throwable throwable) {
            boolean skipVars2;
            this.lc.pop(forNode);
            boolean bl = skipVars2 = (flags & 4) != 0 && (isForOf || isForAwaitOf || (flags & 1) != 0);
            if (!skipVars2) {
                for (Statement var : forNode.getStatements()) {
                    assert (var instanceof VarNode);
                    this.appendStatement(var);
                }
            }
            if (body != null) {
                this.appendStatement(new ForNode(forLine, forToken, body.getFinish(), (Block)body, forNode.getFlags() | flags, init2, test, modify));
            }
            if (outer != null) {
                this.restoreBlock(outer);
                if (body != null) {
                    this.appendStatement(new BlockStatement(forLine, new Block(outer.getToken(), body.getFinish(), 0, outer.getScope(), outer.getStatements())));
                }
            }
            throw throwable;
        }
        boolean bl = skipVars = (flags & 4) != 0 && (isForOf || isForAwaitOf || (flags & 1) != 0);
        if (!skipVars) {
            for (Statement var : forNode.getStatements()) {
                assert (var instanceof VarNode);
                this.appendStatement(var);
            }
        }
        if (body != null) {
            this.appendStatement(new ForNode(forLine, forToken, body.getFinish(), (Block)body, forNode.getFlags() | flags, init2, test, modify));
        }
        if (outer != null) {
            this.restoreBlock(outer);
            if (body != null) {
                this.appendStatement(new BlockStatement(forLine, new Block(outer.getToken(), body.getFinish(), 0, outer.getScope(), outer.getStatements())));
            }
        }
    }

    private boolean checkValidLValue(Expression init2, String contextString) {
        if (init2 instanceof IdentNode) {
            IdentNode ident = (IdentNode)init2;
            if (!Parser.checkIdentLValue(ident)) {
                return false;
            }
            if (ident.isMetaProperty()) {
                return false;
            }
            this.verifyStrictIdent(ident, contextString);
            return true;
        }
        if (init2 instanceof AccessNode || init2 instanceof IndexNode) {
            return !((BaseNode)init2).isOptional();
        }
        if (this.isDestructuringLhs(init2)) {
            this.verifyDestructuringAssignmentPattern(init2, contextString);
            return true;
        }
        return false;
    }

    private boolean lookaheadIsLetDeclaration() {
        return this.lookaheadOfLetDeclaration() != null;
    }

    private TokenType lookaheadOfLetDeclaration() {
        assert (this.type == TokenType.LET);
        int i = 1;
        while (true) {
            TokenType t = this.T(this.k + i);
            switch (t) {
                case EOL: 
                case COMMENT: {
                    break;
                }
                case LBRACE: 
                case IDENT: 
                case OF: 
                case LBRACKET: {
                    return t;
                }
                default: {
                    if (t.isContextualKeyword() || !this.isStrictMode && t.isFutureStrict()) {
                        return t;
                    }
                    return null;
                }
            }
            ++i;
        }
    }

    private boolean lookaheadIsOf() {
        int i = 1;
        while (true) {
            TokenType t = this.T(this.k + i);
            switch (t) {
                case EOL: 
                case COMMENT: {
                    break;
                }
                case OF: {
                    return true;
                }
                default: {
                    return false;
                }
            }
            ++i;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void whileStatement(boolean yield, boolean await2) {
        long whileToken = this.token;
        int whileLine = this.line;
        this.next();
        ParserContextLoopNode whileNode = new ParserContextLoopNode();
        this.lc.push(whileNode);
        JoinPredecessorExpression test = null;
        Block body = null;
        try {
            this.expect(TokenType.LPAREN);
            test = this.joinPredecessorExpression(yield, await2);
            this.expect(TokenType.RPAREN);
            body = this.getStatement(yield, await2);
        }
        finally {
            this.lc.pop(whileNode);
        }
        if (body != null) {
            this.appendStatement(new WhileNode(whileLine, whileToken, body.getFinish(), false, test, body));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doStatement(boolean yield, boolean await2) {
        long doToken = this.token;
        int doLine = 0;
        this.next();
        ParserContextLoopNode doWhileNode = new ParserContextLoopNode();
        this.lc.push(doWhileNode);
        Block body = null;
        JoinPredecessorExpression test = null;
        try {
            body = this.getStatement(yield, await2);
            this.expect(TokenType.WHILE);
            this.expect(TokenType.LPAREN);
            doLine = this.line;
            test = this.joinPredecessorExpression(yield, await2);
            this.expect(TokenType.RPAREN);
            if (this.type == TokenType.SEMICOLON) {
                this.endOfLine();
            }
        }
        finally {
            this.lc.pop(doWhileNode);
        }
        this.appendStatement(new WhileNode(doLine, doToken, this.finish, true, test, body));
    }

    private void continueStatement(boolean yield, boolean await2) {
        boolean seenEOL;
        int continueLine = this.line;
        long continueToken = this.token;
        this.nextOrEOL();
        boolean bl = seenEOL = this.type == TokenType.EOL;
        if (seenEOL) {
            this.next();
        }
        ParserContextLabelNode labelNode = null;
        switch (this.type) {
            case EOF: 
            case SEMICOLON: 
            case RBRACE: {
                break;
            }
            default: {
                IdentNode ident;
                if (seenEOL || (labelNode = this.lc.findLabel((ident = this.labelIdentifier(yield, await2)).getName())) != null) break;
                throw this.error(AbstractParser.message(MSG_UNDEFINED_LABEL, ident), ident.getToken());
            }
        }
        String labelName = labelNode == null ? null : labelNode.getLabelName();
        ParserContextLoopNode targetNode = this.lc.getContinueTo(labelName);
        if (targetNode == null) {
            throw this.error(AbstractParser.message(MSG_ILLEGAL_CONTINUE_STMT, new String[0]), continueToken);
        }
        this.endOfLine();
        this.appendStatement(new ContinueNode(continueLine, continueToken, this.finish, labelName));
    }

    private void breakStatement(boolean yield, boolean await2) {
        boolean seenEOL;
        int breakLine = this.line;
        long breakToken = this.token;
        this.nextOrEOL();
        boolean bl = seenEOL = this.type == TokenType.EOL;
        if (seenEOL) {
            this.next();
        }
        ParserContextLabelNode labelNode = null;
        switch (this.type) {
            case EOF: 
            case SEMICOLON: 
            case RBRACE: {
                break;
            }
            default: {
                IdentNode ident;
                if (seenEOL || (labelNode = this.lc.findLabel((ident = this.labelIdentifier(yield, await2)).getName())) != null) break;
                throw this.error(AbstractParser.message(MSG_UNDEFINED_LABEL, ident), ident.getToken());
            }
        }
        String labelName = labelNode == null ? null : labelNode.getLabelName();
        ParserContextBreakableNode targetNode = this.lc.getBreakable(labelName);
        if (targetNode == null) {
            throw this.error(AbstractParser.message(MSG_ILLEGAL_BREAK_STMT, new String[0]), breakToken);
        }
        this.endOfLine();
        this.appendStatement(new BreakNode(breakLine, breakToken, this.finish, labelName));
    }

    private void returnStatement(boolean yield, boolean await2) {
        boolean seenEOL;
        ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
        if (currentFunction.isScriptOrModule() || currentFunction.isClassStaticBlock()) {
            throw this.error(AbstractParser.message(MSG_INVALID_RETURN, new String[0]));
        }
        int returnLine = this.line;
        long returnToken = this.token;
        this.nextOrEOL();
        boolean bl = seenEOL = this.type == TokenType.EOL;
        if (seenEOL) {
            this.next();
        }
        Expression expression = null;
        switch (this.type) {
            case EOF: 
            case SEMICOLON: 
            case RBRACE: {
                break;
            }
            default: {
                if (seenEOL) break;
                expression = this.expression(yield, await2);
            }
        }
        this.endOfLine();
        this.appendStatement(new ReturnNode(returnLine, returnToken, this.finish, expression));
    }

    private Expression yieldExpression(boolean in, boolean await2) {
        assert (this.isES6());
        long yieldToken = this.token;
        assert (this.type == TokenType.YIELD);
        if (this.inFormalParameterList()) {
            throw this.error(AbstractParser.message(MSG_UNEXPECTED_TOKEN, this.type.getNameOrType()));
        }
        this.recordYieldOrAwait();
        this.nextOrEOL();
        Expression expression = null;
        boolean yieldAsterisk = false;
        if (this.type == TokenType.MUL) {
            yieldAsterisk = true;
            yieldToken = Token.recast(yieldToken, TokenType.YIELD_STAR);
            this.next();
        }
        switch (this.type) {
            case EOF: 
            case EOL: 
            case SEMICOLON: 
            case RBRACE: 
            case RPAREN: 
            case RBRACKET: 
            case COMMARIGHT: 
            case COLON: {
                if (!yieldAsterisk) {
                    expression = Parser.newUndefinedLiteral(yieldToken, this.finish);
                    if (this.type != TokenType.EOL) break;
                    this.next();
                    break;
                }
            }
            default: {
                expression = this.assignmentExpression(in, true, await2);
            }
        }
        return new UnaryNode(yieldToken, expression);
    }

    private Expression awaitExpression(boolean yield) {
        assert (this.isAwait());
        long awaitToken = this.token;
        ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
        if (currentFunction.isClassStaticBlock() || this.inFormalParameterList()) {
            throw this.error(AbstractParser.message(MSG_UNEXPECTED_TOKEN, this.type.getNameOrType()));
        }
        this.recordYieldOrAwait();
        this.next();
        Expression expression = this.unaryExpression(yield, true, CoverExpressionError.DENY);
        if (this.isModule && currentFunction.isModule()) {
            currentFunction.setFlag(0x2000000);
        }
        return new UnaryNode(Token.recast(awaitToken, TokenType.AWAIT), expression);
    }

    private static UnaryNode newUndefinedLiteral(long token, int finish) {
        return new UnaryNode(Token.recast(token, TokenType.VOID), LiteralNode.newInstance(token, finish, 0));
    }

    private void recordYieldOrAwait() {
        long yieldOrAwaitToken = this.token;
        assert (Token.descType(yieldOrAwaitToken) == TokenType.YIELD || Token.descType(yieldOrAwaitToken) == TokenType.AWAIT);
        this.recordYieldOrAwait(yieldOrAwaitToken, false);
    }

    private void recordYieldOrAwait(IdentNode ident) {
        this.recordYieldOrAwait(ident.getToken(), true);
    }

    private void recordYieldOrAwait(long yieldOrAwaitToken, boolean ident) {
        ParserContextFunctionNode fn;
        Iterator<ParserContextFunctionNode> iterator = this.lc.getFunctions();
        while (iterator.hasNext() && (fn = iterator.next()).isCoverArrowHead()) {
            if (ident && !fn.isAsync() || fn.getYieldOrAwaitInParameters() != 0L) continue;
            fn.setYieldOrAwaitInParameters(yieldOrAwaitToken);
        }
    }

    private void withStatement(boolean yield, boolean await2) {
        int withLine = this.line;
        long withToken = this.token;
        this.next();
        if (this.isStrictMode) {
            throw this.error(AbstractParser.message(MSG_STRICT_NO_WITH, new String[0]), withToken);
        }
        this.expect(TokenType.LPAREN);
        Expression expression = this.expression(yield, await2);
        this.expect(TokenType.RPAREN);
        Block body = this.getStatement(yield, await2);
        this.appendStatement(new WithNode(withLine, withToken, this.finish, expression, body));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void switchStatement(boolean yield, boolean await2) {
        ParserContextBlockNode outerBlock;
        int switchLine = this.line;
        long switchToken = this.token;
        if (this.useBlockScope()) {
            outerBlock = this.newBlock();
            outerBlock.setFlag(16);
        } else {
            outerBlock = null;
        }
        ParserContextBlockNode switchBlock = this.newBlock(Scope.createSwitchBlock(this.lc.getCurrentScope()));
        switchBlock.setFlag(144);
        this.next();
        ParserContextSwitchNode switchNode = new ParserContextSwitchNode();
        this.lc.push(switchNode);
        int defaultCaseIndex = -1;
        ArrayList<CaseNode> cases = new ArrayList<CaseNode>();
        Node switchStatement = null;
        try {
            this.expect(TokenType.LPAREN);
            int expressionLine = this.line;
            Expression expression = this.expression(yield, await2);
            this.expect(TokenType.RPAREN);
            this.expect(TokenType.LBRACE);
            if (this.useBlockScope()) {
                IdentNode switchExprName = new IdentNode(Token.recast(expression.getToken(), TokenType.IDENT), expression.getFinish(), this.lexer.stringIntern(SWITCH_BINDING_NAME));
                VarNode varNode = new VarNode(expressionLine, Token.recast(expression.getToken(), TokenType.LET), expression.getFinish(), switchExprName, expression, 1);
                outerBlock.appendStatement(varNode);
                this.declareVar(outerBlock.getScope(), varNode);
                expression = switchExprName;
            }
            while (this.type != TokenType.RBRACE) {
                Expression caseExpression = null;
                long caseToken = this.token;
                switch (this.type) {
                    case CASE: {
                        this.next();
                        caseExpression = this.expression(yield, await2);
                        break;
                    }
                    case DEFAULT: {
                        if (defaultCaseIndex != -1) {
                            throw this.error(AbstractParser.message(MSG_DUPLICATE_DEFAULT_IN_SWITCH, new String[0]));
                        }
                        this.next();
                        break;
                    }
                    default: {
                        this.expect(TokenType.CASE);
                    }
                }
                this.expect(TokenType.COLON);
                List<Statement> statements = this.caseStatementList(yield, await2);
                CaseNode caseNode = new CaseNode(caseToken, this.finish, caseExpression, statements);
                if (caseExpression == null) {
                    assert (defaultCaseIndex == -1);
                    defaultCaseIndex = cases.size();
                }
                cases.add(caseNode);
            }
            this.next();
            switchStatement = new SwitchNode(switchLine, switchToken, this.finish, expression, cases, defaultCaseIndex);
        }
        finally {
            this.lc.pop(switchNode);
            this.restoreBlock(switchBlock);
            if (switchStatement != null) {
                this.appendStatement(new BlockStatement(switchLine, new Block(switchToken, switchStatement.getFinish(), switchBlock.getFlags(), switchBlock.getScope(), List.of((Object)switchStatement))));
            }
            if (outerBlock != null) {
                this.restoreBlock(outerBlock);
                if (switchStatement != null) {
                    this.appendStatement(new BlockStatement(switchLine, new Block(switchToken, switchStatement.getFinish(), outerBlock.getFlags(), outerBlock.getScope(), outerBlock.getStatements())));
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void labelStatement(boolean yield, boolean await2, boolean mayBeFunctionDeclaration) {
        long labelToken = this.token;
        IdentNode ident = this.labelIdentifier(yield, await2);
        this.expect(TokenType.COLON);
        if (this.lc.findLabel(ident.getName()) != null) {
            throw this.error(AbstractParser.message(MSG_DUPLICATE_LABEL, ident), labelToken);
        }
        ParserContextLabelNode labelNode = new ParserContextLabelNode(ident.getName());
        Block body = null;
        try {
            this.lc.push(labelNode);
            body = this.getStatement(yield, await2, true, mayBeFunctionDeclaration);
        }
        finally {
            this.lc.pop(labelNode);
        }
        this.appendStatement(new LabelNode(this.line, labelToken, this.finish, ident.getName(), body));
    }

    private void throwStatement(boolean yield, boolean await2) {
        int throwLine = this.line;
        long throwToken = this.token;
        this.nextOrEOL();
        Expression expression = null;
        switch (this.type) {
            case EOL: 
            case SEMICOLON: 
            case RBRACE: {
                break;
            }
            default: {
                expression = this.expression(yield, await2);
            }
        }
        if (expression == null) {
            throw this.error(AbstractParser.message(MSG_EXPECTED_OPERAND, this.type.getNameOrType()));
        }
        this.endOfLine();
        this.appendStatement(new ThrowNode(throwLine, throwToken, this.finish, expression, false));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void tryStatement(boolean yield, boolean await2) {
        int tryLine = this.line;
        long tryToken = this.token;
        this.next();
        int startLine = this.line;
        ParserContextBlockNode outer = this.newBlock();
        try {
            Block tryBody = this.getBlock(yield, await2, true);
            ArrayList<Block> catchBlocks = new ArrayList<Block>();
            while (this.type == TokenType.CATCH) {
                Expression ifExpression;
                boolean optionalCatchBinding;
                int catchLine = this.line;
                long catchToken = this.token;
                this.next();
                boolean bl = optionalCatchBinding = this.type == TokenType.LBRACE && ES2019_OPTIONAL_CATCH_BINDING;
                if (!optionalCatchBinding) {
                    this.expect(TokenType.LPAREN);
                }
                ParserContextBlockNode catchBlock = this.newBlock(Scope.createCatchParameter(this.lc.getCurrentScope()));
                try {
                    Expression pattern;
                    IdentNode exception;
                    if (optionalCatchBinding) {
                        exception = null;
                        pattern = null;
                        ifExpression = null;
                    } else {
                        if (this.isBindingIdentifier() || !ES6_DESTRUCTURING || !this.isES6()) {
                            pattern = null;
                            IdentNode catchParameter = this.bindingIdentifier(yield, await2, CONTEXT_CATCH_PARAMETER);
                            exception = catchParameter.setIsCatchParameter();
                        } else {
                            pattern = this.bindingPattern(yield, await2);
                            exception = new IdentNode(Token.recast(pattern.getToken(), TokenType.IDENT), pattern.getFinish(), this.lexer.stringIntern(ERROR_BINDING_NAME)).setIsCatchParameter();
                        }
                        if (this.env.syntaxExtensions && this.type == TokenType.IF) {
                            this.next();
                            ifExpression = this.expression(yield, await2);
                        } else {
                            ifExpression = null;
                        }
                        this.expect(TokenType.RPAREN);
                    }
                    CatchNode catchNode = this.catchBody(yield, await2, catchToken, catchLine, exception, pattern, ifExpression);
                    this.appendStatement(catchNode);
                }
                finally {
                    this.restoreBlock(catchBlock);
                }
                int catchFinish = Math.max(this.finish, Token.descPosition(catchBlock.getToken()));
                Block catchBlockNode = new Block(catchBlock.getToken(), catchFinish, catchBlock.getFlags() | 0x10, catchBlock.getScope(), catchBlock.getStatements());
                catchBlocks.add(catchBlockNode);
                if (ifExpression != null) continue;
                break;
            }
            Block finallyStatements = null;
            if (this.type == TokenType.FINALLY) {
                this.next();
                finallyStatements = this.getBlock(yield, await2, true);
            }
            if (catchBlocks.isEmpty() && finallyStatements == null) {
                throw this.error(AbstractParser.message(MSG_MISSING_CATCH_OR_FINALLY, new String[0]), tryToken);
            }
            TryNode tryNode = new TryNode(tryLine, tryToken, this.finish, tryBody, catchBlocks, finallyStatements);
            assert (this.lc.peek() == outer);
            this.appendStatement(tryNode);
        }
        finally {
            this.restoreBlock(outer);
        }
        this.appendStatement(new BlockStatement(startLine, new Block(tryToken, this.finish, outer.getFlags() | 0x10, outer.getScope(), outer.getStatements())));
    }

    private CatchNode catchBody(boolean yield, boolean await2, long catchToken, final int catchLine, IdentNode exception, Expression pattern, Expression ifExpression) {
        if (exception != null) {
            final Scope catchScope = this.lc.getCurrentScope();
            assert (catchScope.isCatchParameterScope());
            VarNode exceptionVar = new VarNode(catchLine, Token.recast(exception.getToken(), TokenType.LET), exception.getFinish(), exception.setIsDeclaredHere(), null, 1);
            this.appendStatement(exceptionVar);
            this.declareVar(catchScope, exceptionVar);
            if (pattern != null) {
                this.verifyDestructuringBindingPattern(pattern, new Consumer<IdentNode>(){

                    @Override
                    public void accept(IdentNode identNode) {
                        Parser.this.verifyStrictIdent(identNode, Parser.CONTEXT_CATCH_PARAMETER);
                        int varFlags = 17;
                        VarNode var = new VarNode(catchLine, Token.recast(identNode.getToken(), TokenType.LET), identNode.getFinish(), identNode.setIsDeclaredHere(), null, 17);
                        Parser.this.appendStatement(var);
                        Parser.this.declareVar(catchScope, var);
                    }
                });
            }
        }
        Block catchBody = this.getBlock(yield, await2, true);
        return new CatchNode(catchLine, catchToken, this.finish, exception, pattern, ifExpression, catchBody, false);
    }

    private void debuggerStatement() {
        int debuggerLine = this.line;
        long debuggerToken = this.token;
        this.next();
        this.endOfLine();
        this.appendStatement(new DebuggerNode(debuggerLine, debuggerToken, this.finish));
    }

    private Expression primaryExpression(boolean yield, boolean await2, CoverExpressionError coverExpression) {
        int primaryLine = this.line;
        long primaryToken = this.token;
        switch (this.type) {
            case THIS: {
                TruffleString name = this.type.getNameTS();
                this.next();
                this.markThis();
                return new IdentNode(primaryToken, this.finish, this.lexer.stringIntern(name)).setIsThis();
            }
            case IDENT: {
                IdentNode ident = this.identifierReference(yield, await2);
                if (ident == null) break;
                return this.detectSpecialProperty(ident);
            }
            case NON_OCTAL_DECIMAL: {
                if (this.isStrictMode) {
                    throw this.error(AbstractParser.message(MSG_STRICT_NO_NONOCTALDECIMAL, new String[0]), this.token);
                }
            }
            case OCTAL_LEGACY: {
                if (this.isStrictMode) {
                    throw this.error(AbstractParser.message(MSG_STRICT_NO_OCTAL, new String[0]), this.token);
                }
            }
            case STRING: 
            case ESCSTRING: 
            case DECIMAL: 
            case HEXADECIMAL: 
            case OCTAL: 
            case BINARY_NUMBER: 
            case BIGINT: 
            case FLOATING: 
            case REGEX: 
            case XML: {
                return this.getLiteral();
            }
            case EXECSTRING: {
                return this.execString(primaryLine, primaryToken);
            }
            case FALSE: {
                this.next();
                return LiteralNode.newInstance(primaryToken, this.finish, false);
            }
            case TRUE: {
                this.next();
                return LiteralNode.newInstance(primaryToken, this.finish, true);
            }
            case NULL: {
                this.next();
                return LiteralNode.newInstance(primaryToken, this.finish);
            }
            case LBRACKET: {
                return this.arrayLiteral(yield, await2, coverExpression);
            }
            case LBRACE: {
                return this.objectLiteral(yield, await2, coverExpression);
            }
            case LPAREN: {
                return this.parenthesizedExpressionAndArrowParameterList(yield, await2);
            }
            case TEMPLATE: 
            case TEMPLATE_HEAD: {
                return this.templateLiteral(yield, await2);
            }
            default: {
                if (this.lexer.scanLiteral(primaryToken, this.type, this.lineInfoReceiver)) {
                    this.next();
                    return this.getLiteral();
                }
                if (!this.type.isContextualKeyword() && !this.isNonStrictModeIdent()) break;
                return this.identifierReference(yield, await2);
            }
        }
        throw this.error(AbstractParser.message(MSG_EXPECTED_OPERAND, this.type.getNameOrType()));
    }

    private boolean isPrivateFieldsIn() {
        return this.env.privateFieldsIn;
    }

    private Expression execString(int primaryLine, long primaryToken) {
        IdentNode execIdent = new IdentNode(primaryToken, this.finish, this.lexer.stringIntern(EXEC_NAME));
        this.next();
        this.expect(TokenType.LBRACE);
        List arguments = List.of((Object)this.expression(false, false));
        this.expect(TokenType.RBRACE);
        long tokenWithDelimiter = Token.withDelimiter(primaryToken);
        return CallNode.forCall(primaryLine, tokenWithDelimiter, Token.descPosition(tokenWithDelimiter), this.finish, execIdent, arguments);
    }

    private LiteralNode<Expression[]> arrayLiteral(boolean yield, boolean await2, CoverExpressionError coverExpression) {
        long arrayToken = this.token;
        this.next();
        ArrayList<Expression> elements2 = new ArrayList<Expression>();
        boolean elision = true;
        boolean hasSpread = false;
        block5: while (true) {
            long spreadToken = 0L;
            switch (this.type) {
                case RBRACKET: {
                    this.next();
                    break block5;
                }
                case COMMARIGHT: {
                    this.next();
                    if (elision) {
                        elements2.add(null);
                    }
                    elision = true;
                    continue block5;
                }
                case ELLIPSIS: {
                    if (ES6_SPREAD_ARRAY) {
                        hasSpread = true;
                        spreadToken = this.token;
                        this.next();
                    }
                }
                default: {
                    if (!elision) {
                        throw this.error(AbstractParser.message(MSG_EXPECTED_COMMA, this.type.getNameOrType()));
                    }
                    Expression expression = this.assignmentExpression(true, yield, await2, coverExpression);
                    if (expression != null) {
                        if (spreadToken != 0L) {
                            expression = new UnaryNode(Token.recast(spreadToken, TokenType.SPREAD_ARRAY), expression);
                        }
                        elements2.add(expression);
                    } else {
                        this.expect(TokenType.RBRACKET);
                    }
                    elision = false;
                    continue block5;
                }
            }
            break;
        }
        return LiteralNode.newInstance(arrayToken, this.finish, elements2, hasSpread, elision);
    }

    private ObjectNode objectLiteral(boolean yield, boolean await2, CoverExpressionError coverExpression) {
        long objectToken = this.token;
        this.next();
        ArrayList<PropertyNode> elements2 = new ArrayList<PropertyNode>();
        HashMap<String, PropertyNode> propertyNameMapES5 = this.isES6() ? null : new HashMap<String, PropertyNode>();
        boolean commaSeen = true;
        boolean hasDuplicateProto = false;
        boolean hasProto = false;
        block4: while (true) {
            switch (this.type) {
                case RBRACE: {
                    this.next();
                    break block4;
                }
                case COMMARIGHT: {
                    if (commaSeen) {
                        throw this.error(AbstractParser.message(MSG_EXPECTED_PROPERTY_ID, this.type.getNameOrType()));
                    }
                    this.next();
                    commaSeen = true;
                    continue block4;
                }
                default: {
                    if (!commaSeen) {
                        throw this.error(AbstractParser.message(MSG_EXPECTED_COMMA, this.type.getNameOrType()));
                    }
                    commaSeen = false;
                    PropertyNode property = this.propertyDefinition(yield, await2, coverExpression);
                    elements2.add(property);
                    hasDuplicateProto = hasProto && property.isProto();
                    boolean bl = hasProto = hasProto || property.isProto();
                    if (property.isComputed() || property.getKey().isTokenType(TokenType.SPREAD_OBJECT)) continue block4;
                    if (this.isES6()) {
                        if (!hasDuplicateProto) continue block4;
                        this.recordOrThrowExpressionError(MSG_MULTIPLE_PROTO_KEY, property.getToken(), coverExpression);
                        continue block4;
                    }
                    this.checkES5PropertyDefinition(property, propertyNameMapES5);
                    continue block4;
                }
            }
            break;
        }
        return new ObjectNode(objectToken, this.finish, elements2);
    }

    private void checkES5PropertyDefinition(PropertyNode property, Map<String, PropertyNode> map) {
        String key = property.getKeyName();
        PropertyNode existingProperty = map.get(key);
        if (existingProperty == null) {
            map.put(key, property);
        } else {
            Expression value2 = property.getValue();
            FunctionNode getter = property.getGetter();
            FunctionNode setter = property.getSetter();
            Expression prevValue = existingProperty.getValue();
            FunctionNode prevGetter = existingProperty.getGetter();
            FunctionNode prevSetter = existingProperty.getSetter();
            this.checkPropertyRedefinition(property, value2, getter, setter, prevValue, prevGetter, prevSetter);
            if (value2 == null && prevValue == null) {
                if (getter != null) {
                    assert (prevGetter != null || prevSetter != null);
                    map.put(key, existingProperty.setGetter(getter));
                } else if (setter != null) {
                    assert (prevGetter != null || prevSetter != null);
                    map.put(key, existingProperty.setSetter(setter));
                }
            }
        }
    }

    private void checkPropertyRedefinition(PropertyNode property, Expression value2, FunctionNode getter, FunctionNode setter, Expression prevValue, FunctionNode prevGetter, FunctionNode prevSetter) {
        boolean isAccessor;
        if (this.isStrictMode && value2 != null && prevValue != null) {
            throw this.error(AbstractParser.message(MSG_PROPERTY_REDEFINITON, property.getKeyName()), property.getToken());
        }
        boolean isPrevAccessor = prevGetter != null || prevSetter != null;
        boolean bl = isAccessor = getter != null || setter != null;
        if (prevValue != null && isAccessor) {
            throw this.error(AbstractParser.message(MSG_PROPERTY_REDEFINITON, property.getKeyName()), property.getToken());
        }
        if (isPrevAccessor && value2 != null) {
            throw this.error(AbstractParser.message(MSG_PROPERTY_REDEFINITON, property.getKeyName()), property.getToken());
        }
        if (isAccessor && isPrevAccessor && (getter != null && prevGetter != null || setter != null && prevSetter != null)) {
            throw this.error(AbstractParser.message(MSG_PROPERTY_REDEFINITON, property.getKeyName()), property.getToken());
        }
    }

    private PropertyKey literalPropertyName() {
        switch (this.type) {
            case IDENT: {
                return this.getIdent().setIsPropertyName();
            }
            case NON_OCTAL_DECIMAL: {
                if (this.isStrictMode) {
                    throw this.error(AbstractParser.message(MSG_STRICT_NO_NONOCTALDECIMAL, new String[0]), this.token);
                }
            }
            case OCTAL_LEGACY: {
                if (this.isStrictMode) {
                    throw this.error(AbstractParser.message(MSG_STRICT_NO_OCTAL, new String[0]), this.token);
                }
            }
            case STRING: 
            case ESCSTRING: 
            case DECIMAL: 
            case HEXADECIMAL: 
            case OCTAL: 
            case BINARY_NUMBER: 
            case BIGINT: 
            case FLOATING: {
                return (PropertyKey)((Object)this.getLiteral());
            }
        }
        return this.getIdentifierName().setIsPropertyName();
    }

    private Expression computedPropertyName(boolean yield, boolean await2) {
        this.expect(TokenType.LBRACKET);
        Expression expression = this.assignmentExpression(true, yield, await2);
        this.expect(TokenType.RBRACKET);
        return expression;
    }

    private Expression propertyName(boolean yield, boolean await2) {
        if (ES6_COMPUTED_PROPERTY_NAME && this.type == TokenType.LBRACKET && this.isES6()) {
            return this.computedPropertyName(yield, await2);
        }
        return (Expression)((Object)this.literalPropertyName());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PropertyNode propertyDefinition(boolean yield, boolean await2, CoverExpressionError coverExpression) {
        Expression propertyValue;
        Expression propertyName;
        boolean isIdentifier;
        boolean computed;
        long propertyToken = this.token;
        int functionLine = this.line;
        boolean async = false;
        if (this.isAsync() && this.lookaheadIsAsyncMethod(false)) {
            async = true;
            this.next();
        }
        boolean generator = false;
        if (this.type == TokenType.MUL && ES6_GENERATOR_FUNCTION && this.isES6()) {
            generator = true;
            this.next();
        }
        boolean bl = computed = this.type == TokenType.LBRACKET;
        if (this.type == TokenType.IDENT || this.isIdentifier() && this.type != TokenType.GET && this.type != TokenType.SET) {
            isIdentifier = true;
            propertyName = this.getIdent().setIsPropertyName();
        } else if (this.type == TokenType.GET || this.type == TokenType.SET) {
            TokenType getOrSet = this.type;
            this.next();
            if (this.type != TokenType.COLON && this.type != TokenType.COMMARIGHT && this.type != TokenType.RBRACE && (this.type != TokenType.ASSIGN && this.type != TokenType.LPAREN || !this.isES6())) {
                long getOrSetToken = propertyToken;
                if (getOrSet == TokenType.GET) {
                    PropertyFunction getter = this.propertyGetterFunction(getOrSetToken, functionLine, yield, await2, false);
                    return new PropertyNode(propertyToken, this.finish, getter.key, null, getter.functionNode, null, false, getter.computed, false, false);
                }
                if (getOrSet == TokenType.SET) {
                    PropertyFunction setter = this.propertySetterFunction(getOrSetToken, functionLine, yield, await2, false);
                    return new PropertyNode(propertyToken, this.finish, setter.key, null, null, setter.functionNode, false, setter.computed, false, false);
                }
            }
            isIdentifier = true;
            propertyName = new IdentNode(propertyToken, this.finish, this.lexer.stringIntern(getOrSet.getNameTS())).setIsPropertyName();
        } else {
            if (this.type == TokenType.ELLIPSIS && ES8_REST_SPREAD_PROPERTY && this.isES2017() && !generator && !async) {
                long spreadToken = Token.recast(propertyToken, TokenType.SPREAD_OBJECT);
                this.next();
                Expression assignmentExpression = this.assignmentExpression(true, yield, await2);
                UnaryNode spread = new UnaryNode(spreadToken, assignmentExpression);
                return new PropertyNode(propertyToken, this.finish, spread, null, null, null, false, false, false, false);
            }
            isIdentifier = false;
            propertyName = this.propertyName(yield, await2);
        }
        if (generator || async) {
            this.expectDontAdvance(TokenType.LPAREN);
        }
        boolean coverInitializedName = false;
        boolean proto = false;
        boolean isAnonymousFunctionDefinition = false;
        if (this.type == TokenType.LPAREN && this.isES6()) {
            propertyValue = this.propertyMethodFunction((Expression)propertyName, (long)propertyToken, (int)functionLine, (boolean)generator, (int)0x100000, (boolean)computed, (boolean)async).functionNode;
        } else if (isIdentifier && (this.type == TokenType.COMMARIGHT || this.type == TokenType.RBRACE || this.type == TokenType.ASSIGN) && this.isES6()) {
            Expression ident = propertyName;
            this.verifyIdent((IdentNode)ident, yield, await2);
            ident = this.createIdentNode(propertyToken, this.finish, ((IdentNode)ident).getPropertyNameTS());
            if (this.type == TokenType.ASSIGN && ES6_DESTRUCTURING) {
                long assignToken = this.token;
                this.recordOrThrowExpressionError(MSG_INVALID_PROPERTY_INITIALIZER, assignToken, coverExpression);
                coverInitializedName = true;
                this.next();
                Expression rhs = this.assignmentExpression(true, yield, await2);
                propertyValue = this.verifyAssignment(assignToken, ident, rhs, true);
            } else {
                propertyValue = this.detectSpecialProperty((IdentNode)ident);
            }
            this.addIdentifierReference(((IdentNode)ident).getName());
        } else {
            this.expect(TokenType.COLON);
            if (!computed && PROTO_NAME.equals(((PropertyKey)((Object)propertyName)).getPropertyName())) {
                proto = true;
            }
            this.pushDefaultName(propertyName);
            try {
                propertyValue = this.assignmentExpression(true, yield, await2, coverExpression);
            }
            finally {
                this.popDefaultName();
            }
            if (!proto && Parser.isAnonymousFunctionDefinition(propertyValue)) {
                if (!computed && propertyName instanceof PropertyKey) {
                    propertyValue = this.setAnonymousFunctionName(propertyValue, ((PropertyKey)((Object)propertyName)).getPropertyNameTS());
                } else {
                    isAnonymousFunctionDefinition = true;
                }
            }
        }
        return new PropertyNode(propertyToken, this.finish, propertyName, propertyValue, null, null, false, computed, coverInitializedName, proto, false, isAnonymousFunctionDefinition);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PropertyFunction propertyGetterFunction(long getSetToken, int functionLine, boolean yield, boolean await2, boolean allowPrivate) {
        Block functionBody;
        boolean computed = this.type == TokenType.LBRACKET;
        Expression propertyName = this.classElementName(yield, await2, allowPrivate);
        IdentNode getterName = computed ? null : this.createMethodNameIdent(propertyName, GET_SPC);
        this.expect(TokenType.LPAREN);
        this.expect(TokenType.RPAREN);
        int functionFlags = 0x100800 | (computed ? 1 : 0);
        ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(getterName, getSetToken, functionFlags, functionLine, List.of(), 0);
        this.lc.push(functionNode);
        try {
            functionBody = this.functionBody(functionNode);
        }
        finally {
            this.lc.pop(functionNode);
        }
        FunctionNode function = this.createFunctionNode(functionNode, getSetToken, getterName, functionLine, functionBody);
        return new PropertyFunction(propertyName, function, computed);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PropertyFunction propertySetterFunction(long getSetToken, int functionLine, boolean yield, boolean await2, boolean allowPrivate) {
        Block functionBody;
        boolean computed = this.type == TokenType.LBRACKET;
        Expression propertyName = this.classElementName(yield, await2, allowPrivate);
        IdentNode setterName = computed ? null : this.createMethodNameIdent(propertyName, SET_SPC);
        this.expect(TokenType.LPAREN);
        int functionFlags = 0x101000 | (computed ? 1 : 0);
        ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(setterName, getSetToken, functionFlags, functionLine);
        this.lc.push(functionNode);
        try {
            ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
            this.lc.push(parameterBlock);
            try {
                if (!this.env.syntaxExtensions || this.type != TokenType.RPAREN) {
                    this.formalParameter(false, false);
                }
                this.expect(TokenType.RPAREN);
                functionBody = this.functionBody(functionNode);
            }
            finally {
                this.restoreBlock(parameterBlock);
            }
            if (parameterBlock != null) {
                functionBody = Parser.wrapParameterBlock(parameterBlock, functionBody);
            }
        }
        finally {
            this.lc.pop(functionNode);
        }
        FunctionNode function = this.createFunctionNode(functionNode, getSetToken, setterName, functionLine, functionBody);
        return new PropertyFunction(propertyName, function, computed);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PropertyFunction propertyMethodFunction(Expression key, long methodToken, int methodLine, boolean generator, int flags, boolean computed, boolean async) {
        IdentNode methodNameNode = computed ? null : this.createMethodNameIdent(key, "");
        this.expect(TokenType.LPAREN);
        int functionFlags = flags | (computed ? 1 : 0) | (generator ? 0x1000000 : 0) | (async ? 0x2000000 : 0);
        ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(methodNameNode, methodToken, functionFlags, methodLine);
        this.lc.push(functionNode);
        try {
            Block functionBody;
            ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
            this.lc.push(parameterBlock);
            try {
                this.formalParameterList(generator, async);
                this.expect(TokenType.RPAREN);
                functionBody = this.functionBody(functionNode);
            }
            finally {
                this.restoreBlock(parameterBlock);
            }
            this.verifyParameterList(functionNode);
            if (parameterBlock != null) {
                functionBody = Parser.wrapParameterBlock(parameterBlock, functionBody);
            }
            FunctionNode function = this.createFunctionNode(functionNode, methodToken, methodNameNode, methodLine, functionBody);
            PropertyFunction propertyFunction = new PropertyFunction(key, function, computed);
            return propertyFunction;
        }
        finally {
            this.lc.pop(functionNode);
        }
    }

    private IdentNode createMethodNameIdent(Expression propertyKey, String prefix) {
        TruffleString methodName;
        if (propertyKey instanceof IdentNode) {
            methodName = ((IdentNode)propertyKey).getPropertyNameTS();
        } else if (propertyKey instanceof PropertyKey) {
            methodName = this.lexer.stringIntern(((PropertyKey)((Object)propertyKey)).getPropertyNameTS());
        } else {
            return null;
        }
        if (!prefix.isEmpty()) {
            methodName = this.lexer.stringIntern(prefix + methodName.toJavaStringUncached());
        }
        return this.createIdentNode(propertyKey.getToken(), propertyKey.getFinish(), methodName);
    }

    private static boolean isAnonymousFunctionDefinition(Expression expression) {
        if (expression instanceof FunctionNode && ((FunctionNode)expression).isAnonymous()) {
            return true;
        }
        return expression instanceof ClassNode && ((ClassNode)expression).isAnonymous();
    }

    private Expression setAnonymousFunctionName(Expression expression, TruffleString functionName) {
        if (!this.isES6()) {
            return expression;
        }
        if (expression instanceof FunctionNode && ((FunctionNode)expression).isAnonymous()) {
            return ((FunctionNode)expression).setName(null, functionName);
        }
        if (expression instanceof ClassNode && ((ClassNode)expression).isAnonymous()) {
            ClassNode classNode = (ClassNode)expression;
            FunctionNode constructorFunction = (FunctionNode)classNode.getConstructor().getValue();
            return classNode.setConstructor(classNode.getConstructor().setValue(constructorFunction.setName(null, functionName)));
        }
        return expression;
    }

    private Expression leftHandSideExpression(boolean yield, boolean await2, CoverExpressionError coverExpression) {
        List<Expression> arguments;
        int callLine = this.line;
        long callToken = this.token;
        Expression lhs = this.memberExpression(yield, await2, coverExpression);
        if (this.type == TokenType.LPAREN) {
            boolean async = ES8_ASYNC_FUNCTION && this.isES2017() && lhs.isTokenType(TokenType.ASYNC) && this.lookbehindNoLineTerminatorAfterAsync();
            arguments = this.argumentList(yield, await2, async, callToken, callLine);
            if (async && this.type == TokenType.ARROW && this.lookbehindNoLineTerminatorBeforeArrow()) {
                return new ExpressionList(callToken, callLine, arguments);
            }
            boolean eval = false;
            boolean applyArguments = false;
            if (lhs instanceof IdentNode) {
                IdentNode ident = (IdentNode)lhs;
                String name = ident.getName();
                if (EVAL_NAME.equals(name)) {
                    this.markEval();
                    eval = true;
                } else if (TokenType.SUPER.getName().equals(name)) {
                    assert (ident.isDirectSuper());
                    this.markSuperCall();
                }
            } else if (lhs instanceof AccessNode && !((AccessNode)lhs).isPrivate() && arguments.size() == 2 && arguments.get(1) instanceof IdentNode && ((IdentNode)arguments.get(1)).isArguments() && APPLY_NAME.equals(((AccessNode)lhs).getProperty()) && Parser.markApplyArgumentsCall(this.lc, arguments)) {
                applyArguments = true;
            }
            lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, arguments, false, false, eval, applyArguments, false);
        }
        boolean optionalChain = false;
        block11: while (true) {
            callLine = this.line;
            callToken = this.token;
            switch (this.type) {
                case LPAREN: {
                    arguments = this.argumentList(yield, await2);
                    lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, arguments, false, optionalChain);
                    continue block11;
                }
                case LBRACKET: {
                    this.next();
                    Expression rhs = this.expression(true, yield, await2);
                    this.expect(TokenType.RBRACKET);
                    lhs = new IndexNode(callToken, this.finish, lhs, rhs, false, false, optionalChain);
                    continue block11;
                }
                case PERIOD: {
                    this.next();
                    boolean isPrivate = this.type == TokenType.PRIVATE_IDENT;
                    IdentNode property = isPrivate ? this.privateIdentifierUse() : this.getIdentifierName();
                    lhs = new AccessNode(callToken, this.finish, lhs, property.getNameTS(), false, isPrivate, false, optionalChain);
                    continue block11;
                }
                case TEMPLATE: 
                case TEMPLATE_HEAD: {
                    if (optionalChain) {
                        throw this.error(AbstractParser.message(MSG_OPTIONAL_CHAIN_TEMPLATE, new String[0]));
                    }
                    arguments = this.templateLiteralArgumentList(yield, await2);
                    lhs = CallNode.forTaggedTemplateLiteral(callLine, callToken, lhs.getStart(), this.finish, lhs, arguments);
                    continue block11;
                }
                case OPTIONAL_CHAIN: {
                    Expression rhs;
                    this.next();
                    optionalChain = true;
                    switch (this.type) {
                        case LPAREN: {
                            arguments = this.argumentList(yield, await2);
                            lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, arguments, true, optionalChain);
                            continue block11;
                        }
                        case LBRACKET: {
                            this.next();
                            rhs = this.expression(true, yield, await2);
                            this.expect(TokenType.RBRACKET);
                            lhs = new IndexNode(callToken, this.finish, lhs, rhs, false, true, optionalChain);
                            continue block11;
                        }
                    }
                    boolean isPrivate = this.type == TokenType.PRIVATE_IDENT;
                    IdentNode property = isPrivate ? this.privateIdentifierUse() : this.getIdentifierName();
                    lhs = new AccessNode(callToken, this.finish, lhs, property.getNameTS(), false, isPrivate, true, optionalChain);
                    continue block11;
                }
            }
            break;
        }
        return lhs;
    }

    private Expression newExpression(boolean yield, boolean await2) {
        ArrayList<Expression> arguments;
        long newToken = this.token;
        assert (this.type == TokenType.NEW);
        this.next();
        if (ES6_NEW_TARGET && this.type == TokenType.PERIOD && this.isES6()) {
            this.next();
            if (this.type == TokenType.IDENT && TARGET.equals(this.getValueNoEscape())) {
                this.next();
                this.markNewTarget();
                return new IdentNode(newToken, this.finish, this.lexer.stringIntern(NEW_TARGET_NAME)).setIsNewTarget();
            }
            throw this.error(AbstractParser.message(MSG_EXPECTED_TARGET, new String[0]), this.token);
        }
        if (this.type == TokenType.IMPORT && this.isES2020() && this.lookahead() == TokenType.LPAREN) {
            throw this.error(AbstractParser.message(MSG_EXPECTED_OPERAND, TokenType.IMPORT.getName()), this.token);
        }
        int callLine = this.line;
        Expression constructor = this.memberExpression(yield, await2, CoverExpressionError.DENY);
        if (this.type == TokenType.LPAREN) {
            arguments = this.argumentList(yield, await2);
        } else {
            arguments = new ArrayList();
            if (this.type == TokenType.OPTIONAL_CHAIN) {
                throw this.error(AbstractParser.message(MSG_UNEXPECTED_TOKEN, this.type.getNameOrType()));
            }
        }
        if (this.env.syntaxExtensions && this.type == TokenType.LBRACE) {
            arguments.add(this.objectLiteral(yield, await2, CoverExpressionError.DENY));
        }
        Expression callNode = CallNode.forNew(callLine, newToken, Token.descPosition(newToken), this.finish, constructor, arguments);
        return new UnaryNode(newToken, callNode);
    }

    private Expression memberExpression(boolean yield, boolean await2, CoverExpressionError coverExpression) {
        Expression lhs;
        boolean isSuper = false;
        block0 : switch (this.type) {
            case NEW: {
                lhs = this.newExpression(yield, await2);
                break;
            }
            case FUNCTION: {
                lhs = this.functionExpression();
                break;
            }
            case CLASS: 
            case AT: {
                if (ES6_CLASS && this.isES6()) {
                    lhs = this.classExpression(yield, await2);
                    break;
                }
            }
            case SUPER: {
                Scope scope;
                if (ES6_CLASS && this.isES6() && (scope = this.lc.getCurrentScope()).inMethod()) {
                    long identToken = Token.recast(this.token, TokenType.IDENT);
                    this.next();
                    lhs = new IdentNode(identToken, this.finish, this.lexer.stringIntern(TokenType.SUPER.getNameTS())).setIsSuper();
                    switch (this.type) {
                        case LBRACKET: 
                        case PERIOD: {
                            this.markSuperProperty();
                            isSuper = true;
                            break block0;
                        }
                        case LPAREN: {
                            if (!scope.inDerivedConstructor()) break;
                            lhs = ((IdentNode)lhs).setIsDirectSuper();
                            break block0;
                        }
                    }
                    throw this.error(AbstractParser.message(MSG_INVALID_SUPER, new String[0]), identToken);
                }
            }
            case ASYNC: {
                if (this.isAsync() && this.lookaheadIsAsyncFunction()) {
                    lhs = this.asyncFunctionExpression();
                    break;
                }
            }
            case IMPORT: {
                if (this.isES2020() && this.type == TokenType.IMPORT) {
                    lhs = this.importExpression(yield, await2);
                    break;
                }
            }
            default: {
                lhs = this.primaryExpression(yield, await2, coverExpression);
                this.verifyPrimaryExpression(lhs, coverExpression);
            }
        }
        block17: while (true) {
            long callToken = this.token;
            switch (this.type) {
                case LBRACKET: {
                    this.next();
                    Expression index = this.expression(true, yield, await2);
                    this.expect(TokenType.RBRACKET);
                    lhs = new IndexNode(callToken, this.finish, lhs, index, isSuper, false, false);
                    if (!isSuper) continue block17;
                    isSuper = false;
                    continue block17;
                }
                case PERIOD: {
                    this.next();
                    boolean isPrivate = this.type == TokenType.PRIVATE_IDENT;
                    IdentNode property = !isSuper && isPrivate ? this.privateIdentifierUse() : this.getIdentifierName();
                    lhs = new AccessNode(callToken, this.finish, lhs, property.getNameTS(), isSuper, isPrivate, false, false);
                    if (!isSuper) continue block17;
                    isSuper = false;
                    continue block17;
                }
                case TEMPLATE: 
                case TEMPLATE_HEAD: {
                    int callLine = this.line;
                    List<Expression> arguments = this.templateLiteralArgumentList(yield, await2);
                    lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, arguments, false, false);
                    continue block17;
                }
            }
            break;
        }
        return lhs;
    }

    private void verifyPrimaryExpression(Expression lhs, CoverExpressionError coverExpression) {
        if (coverExpression != CoverExpressionError.DENY && coverExpression.hasError() && this.isDestructuringLhs(lhs)) {
            switch (this.type) {
                case LPAREN: 
                case LBRACKET: 
                case TEMPLATE: 
                case TEMPLATE_HEAD: 
                case PERIOD: 
                case OPTIONAL_CHAIN: {
                    this.verifyExpression(coverExpression);
                }
            }
        }
    }

    private Expression importExpression(boolean yield, boolean await2) {
        long importToken = this.token;
        int importLine = this.line;
        int importStart = this.start;
        assert (this.type == TokenType.IMPORT);
        this.next();
        if (this.type == TokenType.PERIOD) {
            this.next();
            this.expectDontAdvance(TokenType.IDENT);
            String meta = ((TruffleString)this.getValueNoEscape()).toJavaStringUncached();
            if (META.equals(meta)) {
                if (!this.isModule) {
                    throw this.error(AbstractParser.message(MSG_UNEXPECTED_IMPORT_META, new String[0]), importToken);
                }
                this.next();
                return new IdentNode(importToken, this.finish, this.lexer.stringIntern(IMPORT_META_NAME)).setIsImportMeta();
            }
            throw this.error(AbstractParser.message(MSG_UNEXPECTED_IDENT, meta), this.token);
        }
        if (this.type == TokenType.LPAREN) {
            this.next();
            ArrayList<Expression> arguments = new ArrayList<Expression>();
            arguments.add(this.assignmentExpression(true, yield, await2));
            if (this.env.importAssertions && this.type == TokenType.COMMARIGHT) {
                this.next();
                if (this.type != TokenType.RPAREN) {
                    arguments.add(this.assignmentExpression(true, yield, await2));
                    if (this.type == TokenType.COMMARIGHT) {
                        this.next();
                    }
                }
            }
            this.expect(TokenType.RPAREN);
            IdentNode importIdent = new IdentNode(importToken, Token.descPosition(importToken) + Token.descLength(importToken), this.lexer.stringIntern(TokenType.IMPORT.getNameTS()));
            return CallNode.forImport(importLine, importToken, importStart, this.finish, importIdent, arguments);
        }
        throw this.error(AbstractParser.message(MSG_EXPECTED_OPERAND, TokenType.IMPORT.getName()), importToken);
    }

    private ArrayList<Expression> argumentList(boolean yield, boolean await2) {
        return this.argumentList(yield, await2, false, 0L, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ArrayList<Expression> argumentList(boolean yield, boolean await2, boolean coverAsyncArrow, long startToken, int startLine) {
        assert (this.type == TokenType.LPAREN);
        this.next();
        ArrayList<Expression> nodeList = new ArrayList<Expression>();
        boolean first = true;
        ParserContextFunctionNode coverFunction = null;
        ParserContextBlockNode parameterBlock = null;
        CoverExpressionError coverExpression = CoverExpressionError.DENY;
        if (coverAsyncArrow) {
            coverFunction = this.createParserContextArrowFunctionNode(startToken, startLine, true, true);
            parameterBlock = coverFunction.createParameterBlock();
            coverExpression = new CoverExpressionError();
            this.lc.push(coverFunction);
            this.lc.push(parameterBlock);
        }
        try {
            while (this.type != TokenType.RPAREN) {
                if (!first) {
                    this.expect(TokenType.COMMARIGHT);
                    if (ES8_TRAILING_COMMA && this.isES2017() && this.type == TokenType.RPAREN) {
                        break;
                    }
                } else {
                    first = false;
                }
                long spreadToken = 0L;
                if (ES6_SPREAD_ARGUMENT && this.type == TokenType.ELLIPSIS && this.isES6()) {
                    spreadToken = this.token;
                    this.next();
                }
                Expression expression = this.assignmentExpression(true, yield, await2, coverExpression);
                if (spreadToken != 0L) {
                    expression = new UnaryNode(Token.recast(spreadToken, TokenType.SPREAD_ARGUMENT), expression);
                }
                nodeList.add(expression);
            }
        }
        finally {
            if (coverAsyncArrow) {
                this.lc.pop(parameterBlock);
                this.lc.pop(coverFunction);
            }
        }
        this.expect(TokenType.RPAREN);
        if (coverAsyncArrow) {
            if (this.type == TokenType.ARROW && this.lookbehindNoLineTerminatorBeforeArrow()) {
                this.commitArrowHead(coverFunction);
            } else {
                this.revertArrowHead(coverFunction);
                this.verifyExpression(coverExpression);
            }
        }
        return nodeList;
    }

    private long expectAsyncFunction() {
        assert (this.isAsync() && this.lookaheadIsAsyncFunction());
        long asyncToken = this.token;
        this.nextOrEOL();
        return Token.recast(asyncToken, TokenType.FUNCTION);
    }

    private Expression asyncFunctionDeclaration(boolean isStatement, boolean topLevel, boolean yield, boolean await2, boolean isDefault) {
        long functionToken = this.expectAsyncFunction();
        return this.functionDeclarationOrExpression(functionToken, isStatement, topLevel, true, false, true, yield, await2, isDefault);
    }

    private Expression asyncFunctionExpression() {
        long functionToken = this.expectAsyncFunction();
        return this.functionDeclarationOrExpression(functionToken, false, false, true, false, false, false, true, true);
    }

    private Expression functionDeclaration(boolean isStatement, boolean topLevel, boolean expressionStatement, boolean yield, boolean await2, boolean isDefault) {
        return this.functionDeclarationOrExpression(this.token, isStatement, topLevel, false, expressionStatement, true, yield, await2, isDefault);
    }

    private Expression functionExpression() {
        return this.functionDeclarationOrExpression(this.token, false, false, false, false, false, false, false, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Expression functionDeclarationOrExpression(long functionToken, boolean isStatement, boolean topLevel, boolean async, boolean expressionStatement, boolean isDeclaration, boolean isYield, boolean isAwait, boolean isDefault) {
        Block functionBody;
        boolean isAnonymous;
        int functionLine = this.line;
        assert (this.type == TokenType.FUNCTION);
        this.next();
        boolean generator = false;
        if (this.type == TokenType.MUL && ES6_GENERATOR_FUNCTION && this.isES6()) {
            if (expressionStatement) {
                throw this.error(AbstractParser.message(MSG_EXPECTED_STMT, CONTEXT_GENERATOR_FUNCTION_DECLARATION), this.token);
            }
            generator = true;
            this.next();
        }
        assert (!isDeclaration || isDefault || isStatement);
        IdentNode name = null;
        boolean declared = isDeclaration;
        if (this.isBindingIdentifier()) {
            boolean yield = !isDeclaration && generator || isDeclaration && isYield;
            boolean await2 = !isDeclaration && async || isDeclaration && isAwait;
            name = this.bindingIdentifier(yield, await2, CONTEXT_FUNCTION_NAME);
        } else if (isDeclaration && !isDefault) {
            if (this.env.syntaxExtensions) {
                declared = false;
            } else if (this.reparsedFunction == null) {
                this.expect(TokenType.IDENT);
            }
        }
        this.expect(TokenType.LPAREN);
        boolean bl = isAnonymous = name == null;
        assert (!declared || !isAnonymous || isDefault);
        int functionFlags = (generator ? 0x1000000 : 0) | (async ? 0x2000000 : 0) | (isAnonymous ? 1 : 0) | (declared ? 2 : 0) | (isStatement && !isAnonymous ? 16 : 0);
        ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(name, functionToken, functionFlags, functionLine);
        if (isAnonymous) {
            functionNode.setInternalName(this.getDefaultFunctionName());
        }
        this.lc.push(functionNode);
        this.hideDefaultName();
        try {
            ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
            this.lc.push(parameterBlock);
            try {
                this.formalParameterList(generator, async);
                this.expect(TokenType.RPAREN);
                functionBody = this.functionBody(functionNode);
            }
            finally {
                this.restoreBlock(parameterBlock);
            }
            if (parameterBlock != null) {
                functionBody = Parser.wrapParameterBlock(parameterBlock, functionBody);
            }
        }
        finally {
            this.popDefaultName();
            this.lc.pop(functionNode);
        }
        if (!(!isStatement || isAnonymous || topLevel || this.useBlockScope() || !this.isStrictMode && this.env.functionStatement == ScriptEnvironment.FunctionStatementBehavior.ACCEPT)) {
            this.reportIllegalES5BlockLevelFunctionDeclaration(functionToken);
        }
        this.verifyParameterList(functionNode);
        FunctionNode function = this.createFunctionNode(functionNode, functionToken, name, functionLine, functionBody);
        if (isStatement) {
            if (isAnonymous) {
                this.appendStatement(new ExpressionStatement(functionLine, functionToken, this.finish, function));
                return function;
            }
            Scope scope = this.lc.getCurrentScope();
            int varFlags = topLevel && !scope.isModuleScope() || !this.useBlockScope() ? 0 : 1;
            VarNode varNode = new VarNode(functionLine, functionToken, this.finish, name, function, varFlags);
            this.declareVar(scope, varNode);
            if (topLevel) {
                this.functionDeclarations.add(varNode);
            } else {
                this.appendStatement(varNode);
            }
        }
        return function;
    }

    private static Block wrapParameterBlock(ParserContextBlockNode parameterBlock, Block functionBody) {
        assert (parameterBlock.getFlag(64) != 0 && functionBody.isFunctionBody());
        if (parameterBlock.getStatements().isEmpty()) {
            return functionBody;
        }
        parameterBlock.getStatements().add(new BlockStatement(functionBody.getFirstStatementLineNumber(), functionBody));
        return new Block(parameterBlock.getToken(), functionBody.getFinish(), parameterBlock.getFlags(), parameterBlock.getScope(), parameterBlock.getStatements());
    }

    private void verifyParameterList(ParserContextFunctionNode functionNode) {
        IdentNode duplicateParameter = functionNode.getDuplicateParameterBinding();
        if (duplicateParameter != null) {
            if (functionNode.isStrict() || functionNode.isMethod() || functionNode.isArrow() || !functionNode.isSimpleParameterList()) {
                throw this.error(AbstractParser.message(MSG_STRICT_PARAM_REDEFINITION, duplicateParameter.getName()), duplicateParameter.getToken());
            }
            List<IdentNode> parameters = functionNode.getParameters();
            int arity = parameters.size();
            HashSet<String> parametersSet = new HashSet<String>(arity);
            for (int i = arity - 1; i >= 0; --i) {
                IdentNode parameter = parameters.get(i);
                String parameterName = parameter.getName();
                if (parametersSet.contains(parameterName)) {
                    parameters.set(i, parameter.setIsIgnoredParameter());
                    continue;
                }
                parametersSet.add(parameterName);
            }
        }
    }

    private void reportIllegalES5BlockLevelFunctionDeclaration(long functionToken) {
        assert (!this.isES6());
        if (this.isStrictMode) {
            throw this.error(JSErrorType.SyntaxError, AbstractParser.message(MSG_STRICT_NO_FUNC_DECL_HERE, new String[0]), functionToken);
        }
        if (this.env.functionStatement == ScriptEnvironment.FunctionStatementBehavior.ERROR) {
            throw this.error(JSErrorType.SyntaxError, AbstractParser.message(MSG_NO_FUNC_DECL_HERE, new String[0]), functionToken);
        }
        if (this.env.functionStatement == ScriptEnvironment.FunctionStatementBehavior.WARNING) {
            this.warning(JSErrorType.SyntaxError, AbstractParser.message(MSG_NO_FUNC_DECL_HERE_WARN, new String[0]), functionToken);
        }
    }

    private void pushDefaultName(Expression nameExpr) {
        this.defaultNames.add(nameExpr);
    }

    private Object popDefaultName() {
        return this.defaultNames.remove(this.defaultNames.size() - 1);
    }

    private TruffleString getDefaultFunctionName() {
        if (!this.defaultNames.isEmpty()) {
            Object nameExpr = this.defaultNames.get(this.defaultNames.size() - 1);
            if (nameExpr instanceof PropertyKey) {
                this.markDefaultNameUsed();
                return ((PropertyKey)nameExpr).getPropertyNameTS();
            }
            if (nameExpr instanceof AccessNode) {
                AccessNode accessNode = (AccessNode)nameExpr;
                this.markDefaultNameUsed();
                if (accessNode.getBase() instanceof AccessNode) {
                    AccessNode base = (AccessNode)accessNode.getBase();
                    if (base.getBase() instanceof IdentNode && !base.isPrivate() && base.getProperty().equals(PROTOTYPE_NAME)) {
                        return this.lexer.stringIntern(((IdentNode)base.getBase()).getName() + "." + accessNode.getProperty());
                    }
                } else if (accessNode.getBase() instanceof IdentNode) {
                    return this.lexer.stringIntern(((IdentNode)accessNode.getBase()).getName() + "." + accessNode.getProperty());
                }
                return accessNode.getPropertyTS();
            }
        }
        return this.lexer.stringIntern(ANONYMOUS_FUNCTION_NAME);
    }

    private void markDefaultNameUsed() {
        this.popDefaultName();
        this.hideDefaultName();
    }

    private void hideDefaultName() {
        this.defaultNames.add("");
    }

    private void formalParameterList(boolean yield, boolean async) {
        this.formalParameterList(TokenType.RPAREN, yield, async);
    }

    private boolean inFormalParameterList() {
        Iterator<ParserContextNode> iterator = this.lc.getAllNodes();
        while (iterator.hasNext()) {
            ParserContextNode node = iterator.next();
            if (!(node instanceof ParserContextScopableNode)) continue;
            Scope scope = ((ParserContextScopableNode)node).getScope();
            if (scope.isFunctionBodyScope()) {
                return false;
            }
            if (!scope.isFunctionParameterScope() || scope.isArrowFunctionParameterScope()) continue;
            return true;
        }
        return false;
    }

    private void formalParameter(boolean yield, boolean await2) {
        if (this.type == TokenType.YIELD && yield || this.isAwait() && await2) {
            throw this.error(this.expectMessage(TokenType.IDENT));
        }
        ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
        long paramToken = this.token;
        int paramLine = this.line;
        if (this.isBindingIdentifier() || !ES6_DESTRUCTURING || !this.isES6()) {
            IdentNode ident = this.bindingIdentifier(yield, await2, CONTEXT_FUNCTION_PARAMETER);
            if (this.type == TokenType.ASSIGN && ES6_DEFAULT_PARAMETER && this.isES6()) {
                this.next();
                Expression initializer = this.assignmentExpression(true, yield, await2);
                if (Parser.isAnonymousFunctionDefinition(initializer)) {
                    initializer = this.setAnonymousFunctionName(initializer, ident.getNameTS());
                }
                if (currentFunction != null) {
                    Parser.addDefaultParameter(paramToken, this.finish, paramLine, ident, initializer, currentFunction);
                }
            } else if (currentFunction != null) {
                currentFunction.addParameter(ident);
            }
        } else {
            Expression pattern = this.bindingPattern(yield, await2);
            this.verifyDestructuringParameterBindingPattern(pattern, paramToken, paramLine);
            Expression initializer = null;
            if (this.type == TokenType.ASSIGN) {
                this.next();
                initializer = this.assignmentExpression(true, yield, await2);
            }
            if (currentFunction != null) {
                this.addDestructuringParameter(paramToken, this.finish, paramLine, pattern, initializer, currentFunction, false);
            }
        }
    }

    private void functionRestParameter(TokenType endType, boolean yield, boolean await2) {
        long paramToken = this.token;
        int paramLine = this.line;
        ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
        Expression pattern = this.bindingIdentifierOrPattern(yield, await2, CONTEXT_FUNCTION_PARAMETER);
        if (pattern instanceof IdentNode) {
            IdentNode ident = ((IdentNode)pattern).setIsRestParameter();
            if (currentFunction != null) {
                currentFunction.addParameter(ident);
            }
        } else {
            this.verifyDestructuringParameterBindingPattern(pattern, paramToken, paramLine);
            if (currentFunction != null) {
                this.addDestructuringParameter(paramToken, this.finish, paramLine, pattern, null, currentFunction, true);
            }
        }
        this.expectDontAdvance(endType);
    }

    private void formalParameterList(TokenType endType, boolean yield, boolean await2) {
        boolean first = true;
        while (this.type != endType) {
            if (!first) {
                this.expect(TokenType.COMMARIGHT);
                if (ES8_TRAILING_COMMA && this.isES2017() && this.type == endType) {
                    break;
                }
            } else {
                first = false;
            }
            if (ES6_REST_PARAMETER && this.type == TokenType.ELLIPSIS && this.isES6()) {
                this.next();
                this.functionRestParameter(endType, yield, await2);
                break;
            }
            this.formalParameter(yield, await2);
        }
    }

    private static void addDefaultParameter(long paramToken, int paramFinish, int paramLine, IdentNode target, Expression initializer, ParserContextFunctionNode function) {
        assert (target != null && initializer != null);
        int paramIndex = function.getParameterCount();
        ParameterNode param = new ParameterNode(paramToken, paramFinish, paramIndex);
        BinaryNode test = new BinaryNode(Token.recast(paramToken, TokenType.EQ_STRICT), (Expression)param, (Expression)Parser.newUndefinedLiteral(paramToken, paramFinish));
        TernaryNode value2 = new TernaryNode(Token.recast(paramToken, TokenType.TERNARY), (Expression)test, new JoinPredecessorExpression(initializer), new JoinPredecessorExpression(param));
        VarNode varNode = new VarNode(paramLine, Token.recast(paramToken, TokenType.LET), paramFinish, target, value2, 1);
        function.addDefaultParameter(varNode);
    }

    private void addDestructuringParameter(long paramToken, int paramFinish, int paramLine, Expression target, Expression initializer, ParserContextFunctionNode function, boolean isRest) {
        Expression value2;
        assert (this.isDestructuringLhs(target));
        int paramIndex = function.getParameterCount();
        ParameterNode param = new ParameterNode(paramToken, paramFinish, paramIndex, isRest);
        if (initializer == null) {
            value2 = param;
        } else {
            BinaryNode test = new BinaryNode(Token.recast(paramToken, TokenType.EQ_STRICT), (Expression)param, (Expression)Parser.newUndefinedLiteral(paramToken, paramFinish));
            value2 = new TernaryNode(Token.recast(paramToken, TokenType.TERNARY), (Expression)test, new JoinPredecessorExpression(initializer), new JoinPredecessorExpression(param));
        }
        BinaryNode assignment = new BinaryNode(Token.recast(paramToken, TokenType.ASSIGN_INIT), target, value2);
        function.addParameterInitialization(paramLine, assignment, initializer != null, isRest);
    }

    private void verifyDestructuringParameterBindingPattern(final Expression pattern, final long paramToken, final int paramLine) {
        this.verifyDestructuringBindingPattern(pattern, new Consumer<IdentNode>(){

            @Override
            public void accept(IdentNode identNode) {
                Parser.this.verifyStrictIdent(identNode, Parser.CONTEXT_FUNCTION_PARAMETER);
                ParserContextFunctionNode currentFunction = Parser.this.lc.getCurrentFunction();
                if (currentFunction != null) {
                    VarNode declaration = new VarNode(paramLine, Token.recast(paramToken, TokenType.LET), pattern.getFinish(), identNode, null, 17);
                    currentFunction.addParameterBindingDeclaration(declaration);
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Block functionBody(ParserContextFunctionNode functionNode) {
        RecompilableScriptFunctionData data;
        int bodyFinish;
        boolean parseBody;
        ParserContextBlockNode body;
        ParserState endParserState;
        long bodyToken;
        block16: {
            boolean yield = functionNode.isGenerator();
            boolean await2 = functionNode.isAsync() || this.isTopLevelAwait() && this.isModule && functionNode.isModule() || functionNode.isClassStaticBlock();
            bodyToken = this.token;
            endParserState = null;
            body = this.newBlock(functionNode.createBodyScope(this.lexer::stringIntern));
            try {
                int functionId = functionNode.getId();
                boolean bl = parseBody = this.reparsedFunction == null || functionId <= this.reparsedFunction.getFunctionNodeId();
                if ((this.env.syntaxExtensions || functionNode.isArrow()) && this.type != TokenType.LBRACE) {
                    Expression expr = this.assignmentExpression(true, yield, await2);
                    long lastToken = this.previousToken;
                    functionNode.setLastToken(this.previousToken);
                    assert (this.lc.getCurrentBlock().getScope().isFunctionBodyScope());
                    int lastFinish = Token.descPosition(lastToken) + (Token.descType(lastToken) == TokenType.EOL ? 0 : Token.descLength(lastToken));
                    if (parseBody) {
                        ReturnNode returnNode = new ReturnNode(functionNode.getLineNumber(), expr.getToken(), lastFinish, expr);
                        this.appendStatement(returnNode);
                    }
                    bodyFinish = this.finish;
                    break block16;
                }
                this.expectDontAdvance(TokenType.LBRACE);
                if (parseBody || !this.skipFunctionBody(functionNode)) {
                    this.next();
                    List<Statement> prevFunctionDecls = this.functionDeclarations;
                    this.functionDeclarations = new ArrayList<Statement>();
                    try {
                        this.sourceElements(yield, await2, 0);
                        this.addFunctionDeclarations(functionNode);
                    }
                    finally {
                        this.functionDeclarations = prevFunctionDecls;
                    }
                    if (parseBody) {
                        endParserState = new ParserState(Token.descPosition(this.token), this.line, this.linePosition);
                    }
                }
                bodyFinish = Token.descPosition(this.token) + Token.descLength(this.token);
                functionNode.setLastToken(this.token);
                this.expect(TokenType.RBRACE);
            }
            catch (Throwable throwable) {
                functionNode.finishBodyScope(this.lexer::stringIntern);
                this.restoreBlock(body);
                this.lc.propagateFunctionFlags();
                throw throwable;
            }
        }
        functionNode.finishBodyScope(this.lexer::stringIntern);
        this.restoreBlock(body);
        this.lc.propagateFunctionFlags();
        if (parseBody) {
            functionNode.setEndParserState(endParserState);
        } else if (!body.getStatements().isEmpty()) {
            body.setStatements(List.of());
        }
        if (this.reparsedFunction != null && (data = this.reparsedFunction.getScriptFunctionData(functionNode.getId())) != null) {
            functionNode.setFlag(data.getFunctionFlags());
            if (functionNode.hasNestedEval()) {
                assert (functionNode.hasScopeBlock());
                body.setFlag(1);
            }
        }
        return new Block(bodyToken, bodyFinish, body.getFlags() | 0x20, body.getScope(), body.getStatements());
    }

    private boolean skipFunctionBody(ParserContextFunctionNode functionNode) {
        if (this.reparsedFunction == null) {
            return false;
        }
        RecompilableScriptFunctionData data = this.reparsedFunction.getScriptFunctionData(functionNode.getId());
        if (data == null) {
            return false;
        }
        ParserState parserState = (ParserState)data.getEndParserState();
        assert (parserState != null);
        if (this.k < this.stream.last() && this.start < parserState.position && parserState.position <= Token.descPosition(this.stream.get(this.stream.last()))) {
            while (this.k < this.stream.last()) {
                long nextToken = this.stream.get(this.k + 1);
                if (Token.descPosition(nextToken) == parserState.position && Token.descType(nextToken) == TokenType.RBRACE) {
                    this.token = this.stream.get(this.k);
                    this.type = Token.descType(this.token);
                    this.next();
                    assert (this.type == TokenType.RBRACE && this.start == parserState.position);
                    return true;
                }
                ++this.k;
            }
        }
        this.stream.reset();
        this.lexer = parserState.createLexer(this.source, this.lexer, this.stream, this.scripting, this.env.ecmaScriptVersion, this.shebang, this.isModule, this.allowBigInt);
        this.line = parserState.line;
        this.linePosition = parserState.linePosition;
        this.type = TokenType.SEMICOLON;
        this.scanFirstToken();
        return true;
    }

    private void addFunctionDeclarations(ParserContextFunctionNode functionNode) {
        VarNode lastDecl = null;
        for (int i = this.functionDeclarations.size() - 1; i >= 0; --i) {
            Statement decl = this.functionDeclarations.get(i);
            if (lastDecl == null && decl instanceof VarNode) {
                lastDecl = ((VarNode)decl).setFlag(4);
                decl = lastDecl;
                functionNode.setFlag(0x10000000);
            }
            this.prependStatement(decl);
        }
    }

    private ParserException invalidLHSError(Expression lhs) {
        JSErrorType errorType = this.isES2020() ? JSErrorType.SyntaxError : JSErrorType.ReferenceError;
        return this.error(errorType, AbstractParser.message(MSG_INVALID_LVALUE, new String[0]), lhs.getToken());
    }

    private Expression unaryExpression(boolean yield, boolean await2, CoverExpressionError coverExpression) {
        long unaryToken = this.token;
        switch (this.type) {
            case DELETE: {
                this.next();
                Expression expr = this.unaryExpression(yield, await2, CoverExpressionError.DENY);
                if (this.type == TokenType.EXP) {
                    throw this.error(AbstractParser.message(MSG_UNEXPECTED_TOKEN, this.type.getNameOrType()));
                }
                return this.verifyDeleteExpression(unaryToken, expr);
            }
            case VOID: 
            case TYPEOF: 
            case ADD: 
            case SUB: 
            case BIT_NOT: 
            case NOT: {
                this.next();
                Expression expr = this.unaryExpression(yield, await2, CoverExpressionError.DENY);
                if (this.type == TokenType.EXP) {
                    throw this.error(AbstractParser.message(MSG_UNEXPECTED_TOKEN, this.type.getNameOrType()));
                }
                return new UnaryNode(unaryToken, expr);
            }
            case INCPREFIX: 
            case DECPREFIX: {
                TokenType opType = this.type;
                this.next();
                Expression lhs = this.unaryExpression(yield, await2, CoverExpressionError.DENY);
                return this.verifyIncDecExpression(unaryToken, opType, lhs, false);
            }
        }
        if (this.isAwait() && await2) {
            return this.awaitExpression(yield);
        }
        Expression expression = this.leftHandSideExpression(yield, await2, coverExpression);
        if (this.last != TokenType.EOL) {
            switch (this.type) {
                case INCPREFIX: 
                case DECPREFIX: {
                    long opToken = this.token;
                    TokenType opType = this.type;
                    Expression lhs = expression;
                    this.next();
                    return this.verifyIncDecExpression(opToken, opType, lhs, true);
                }
            }
        }
        return expression;
    }

    private Expression verifyDeleteExpression(long unaryToken, Expression expr) {
        if ((expr instanceof BaseNode || expr instanceof IdentNode) && this.isStrictMode) {
            if (expr instanceof IdentNode) {
                IdentNode ident = (IdentNode)expr;
                if (!ident.isThis() && !ident.isMetaProperty()) {
                    throw this.error(AbstractParser.message(MSG_STRICT_CANT_DELETE_IDENT, ident), unaryToken);
                }
            } else if (expr instanceof AccessNode && ((AccessNode)expr).isPrivate()) {
                throw this.error(AbstractParser.message(MSG_STRICT_CANT_DELETE_PRIVATE, new String[0]), unaryToken);
            }
        }
        return new UnaryNode(unaryToken, expr);
    }

    private Expression verifyIncDecExpression(long unaryToken, TokenType opType, Expression lhs, boolean isPostfix) {
        assert (lhs != null);
        if (lhs instanceof IdentNode) {
            IdentNode ident = (IdentNode)lhs;
            if (!Parser.checkIdentLValue(ident) || ident.isMetaProperty()) {
                throw this.invalidLHSError(lhs);
            }
            assert (opType == TokenType.INCPREFIX || opType == TokenType.DECPREFIX);
            String contextString = opType == TokenType.INCPREFIX ? CONTEXT_OPERAND_FOR_INC_OPERATOR : CONTEXT_OPERAND_FOR_DEC_OPERATOR;
            this.verifyStrictIdent((IdentNode)lhs, contextString);
        } else if (!(lhs instanceof AccessNode) && !(lhs instanceof IndexNode) || ((BaseNode)lhs).isOptional()) {
            throw this.invalidLHSError(lhs);
        }
        return Parser.incDecExpression(unaryToken, opType, lhs, isPostfix);
    }

    private Expression expression(boolean in, boolean yield, boolean await2) {
        return this.expression(in, yield, await2, CoverExpressionError.DENY);
    }

    private Expression expression(boolean yield, boolean await2) {
        return this.expression(true, yield, await2);
    }

    private Expression expression(boolean in, boolean yield, boolean await2, CoverExpressionError coverExpression) {
        Expression assignmentExpression = this.assignmentExpression(in, yield, await2, coverExpression);
        while (this.type == TokenType.COMMARIGHT) {
            long commaToken = this.token;
            this.next();
            Expression rhs = this.assignmentExpression(in, yield, await2);
            assignmentExpression = new BinaryNode(commaToken, assignmentExpression, rhs);
        }
        return assignmentExpression;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Expression parenthesizedExpressionAndArrowParameterList(boolean yield, boolean await2) {
        long primaryToken = this.token;
        int startLine = this.line;
        assert (this.type == TokenType.LPAREN);
        this.next();
        boolean canBeArrowParameterList = true;
        if (ES6_ARROW_FUNCTION && this.isES6() && this.type == TokenType.RPAREN) {
            this.nextOrEOL();
            this.expectDontAdvance(TokenType.ARROW);
            return new ExpressionList(primaryToken, this.finish, List.of());
        }
        if (this.type == TokenType.FUNCTION || this.type == TokenType.LPAREN) {
            canBeArrowParameterList = false;
        }
        Expression assignmentExpression = null;
        boolean hasRestParameter = false;
        long commaToken = 0L;
        ParserContextFunctionNode coverFunction = null;
        ParserContextBlockNode parameterBlock = null;
        CoverExpressionError coverExpression = CoverExpressionError.DENY;
        if (canBeArrowParameterList) {
            coverFunction = this.lc.push(this.createParserContextArrowFunctionNode(primaryToken, startLine, false, true));
            parameterBlock = this.lc.push(coverFunction.createParameterBlock());
            coverExpression = new CoverExpressionError();
        }
        try {
            while (true) {
                if (ES6_ARROW_FUNCTION && ES6_REST_PARAMETER && this.isES6() && this.type == TokenType.ELLIPSIS) {
                    assignmentExpression = this.arrowFunctionRestParameter(assignmentExpression, commaToken, yield, await2);
                    hasRestParameter = true;
                    break;
                }
                if (ES6_ARROW_FUNCTION && ES8_TRAILING_COMMA && this.isES2017() && this.type == TokenType.RPAREN && this.lookaheadIsArrow()) {
                    break;
                }
                Expression rhs = this.assignmentExpression(true, yield, await2, coverExpression);
                if (assignmentExpression == null) {
                    assignmentExpression = rhs;
                } else {
                    assert (Token.descType(commaToken) == TokenType.COMMARIGHT);
                    assignmentExpression = new BinaryNode(commaToken, assignmentExpression, rhs);
                }
                if (this.type != TokenType.COMMARIGHT) {
                    break;
                }
                commaToken = this.token;
                this.next();
            }
        }
        finally {
            if (canBeArrowParameterList) {
                this.lc.pop(parameterBlock);
                this.lc.pop(coverFunction);
            }
        }
        boolean arrowAhead = this.lookaheadIsArrow();
        if (canBeArrowParameterList && (this.type != TokenType.RPAREN || !arrowAhead)) {
            this.verifyExpression(coverExpression);
        }
        if (hasRestParameter) {
            this.expectDontAdvance(TokenType.RPAREN);
            this.nextOrEOL();
            this.expectDontAdvance(TokenType.ARROW);
        } else {
            this.expect(TokenType.RPAREN);
        }
        if (canBeArrowParameterList) {
            if (arrowAhead) {
                this.commitArrowHead(coverFunction);
            } else {
                assignmentExpression.makeParenthesized(Token.descPosition(primaryToken), this.finish);
                this.revertArrowHead(coverFunction);
            }
        }
        return assignmentExpression;
    }

    private void commitArrowHead(ParserContextFunctionNode cover) {
        assert (this.coverArrowFunction == null);
        if (cover.getYieldOrAwaitInParameters() != 0L) {
            throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), cover.getYieldOrAwaitInParameters());
        }
        this.coverArrowFunction = cover;
    }

    private void revertArrowHead(ParserContextFunctionNode cover) {
        cover.getParameterScope().kill();
        this.lc.setCurrentFunctionFlag(cover.getFlags() & 0x808C0E8);
    }

    private Expression arrowFunctionRestParameter(Expression paramListExpr, long commaToken, boolean yield, boolean await2) {
        long ellipsisToken = this.token;
        assert (this.type == TokenType.ELLIPSIS);
        this.next();
        Expression pattern = this.bindingIdentifierOrPattern(yield, await2, CONTEXT_FUNCTION_PARAMETER);
        Expression restParam = pattern instanceof IdentNode ? ((IdentNode)pattern).setIsRestParameter() : new UnaryNode(Token.recast(ellipsisToken, TokenType.SPREAD_ARGUMENT), pattern);
        if (paramListExpr == null) {
            return restParam;
        }
        assert (Token.descType(commaToken) == TokenType.COMMARIGHT);
        return new BinaryNode(commaToken, paramListExpr, restParam);
    }

    private Expression expression(int minPrecedence, boolean in, boolean yield, boolean await2, CoverExpressionError coverExpression) {
        Expression lhs = in && this.type == TokenType.PRIVATE_IDENT && this.isPrivateFieldsIn() && this.lookahead() == TokenType.IN ? this.privateIdentifierUse().setIsPrivateInCheck() : this.unaryExpression(yield, await2, coverExpression);
        return this.expression(lhs, minPrecedence, in, yield, await2);
    }

    private JoinPredecessorExpression joinPredecessorExpression(boolean yield, boolean await2) {
        return new JoinPredecessorExpression(this.expression(yield, await2));
    }

    private Expression expression(Expression exprLhs, int minPrecedence, boolean in, boolean yield, boolean await2) {
        int precedence = this.type.getPrecedence();
        Expression lhs = exprLhs;
        while (this.type.isOperator(in) && precedence >= minPrecedence) {
            long op = this.token;
            if (this.type == TokenType.TERNARY) {
                this.next();
                Expression trueExpr = this.assignmentExpression(true, yield, await2);
                this.expect(TokenType.COLON);
                Expression falseExpr = this.assignmentExpression(in, yield, await2);
                lhs = new TernaryNode(op, lhs, new JoinPredecessorExpression(trueExpr), new JoinPredecessorExpression(falseExpr));
            } else {
                Expression rhs;
                TokenType opType = this.type;
                this.next();
                assert (!Token.descType(op).isAssignment());
                if (in && this.type == TokenType.PRIVATE_IDENT && this.isPrivateFieldsIn() && this.lookahead() == TokenType.IN && precedence < TokenType.IN.getPrecedence()) {
                    assert (opType != TokenType.IN);
                    rhs = this.privateIdentifierUse().setIsPrivateInCheck();
                } else {
                    rhs = this.unaryExpression(yield, await2, CoverExpressionError.DENY);
                }
                int nextPrecedence = this.type.getPrecedence();
                while (this.type.isOperator(in) && (nextPrecedence > precedence || nextPrecedence == precedence && !this.type.isLeftAssociative())) {
                    rhs = this.expression(rhs, nextPrecedence, in, yield, await2);
                    nextPrecedence = this.type.getPrecedence();
                }
                lhs = this.newBinaryExpression(op, lhs, rhs);
            }
            precedence = this.type.getPrecedence();
        }
        return lhs;
    }

    private boolean isStartOfAssignmentPattern() {
        return this.type == TokenType.LBRACKET || this.type == TokenType.LBRACE;
    }

    private Expression assignmentExpression(boolean in, boolean yield, boolean await2) {
        return this.assignmentExpression(in, yield, await2, CoverExpressionError.DENY);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Expression assignmentExpression(boolean in, boolean yield, boolean await2, CoverExpressionError coverExpression) {
        if (this.type == TokenType.YIELD && yield) {
            return this.yieldExpression(in, await2);
        }
        boolean asyncArrow = this.isAsync() && this.lookaheadIsAsyncArrowParameterListStart();
        long startToken = this.token;
        int startLine = this.line;
        boolean canBeAssignmentPattern = this.isStartOfAssignmentPattern();
        CoverExpressionError coverExprLhs = canBeAssignmentPattern ? new CoverExpressionError() : CoverExpressionError.DENY;
        Expression exprLhs = this.conditionalExpression(in, yield, await2, coverExprLhs);
        if (asyncArrow && exprLhs instanceof IdentNode && this.isBindingIdentifier() && this.lookaheadIsArrow() && (exprLhs = this.primaryExpression(yield, await2, CoverExpressionError.DENY)) instanceof IdentNode && TokenType.AWAIT.getName().equals(((IdentNode)exprLhs).getName())) {
            throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), exprLhs.getToken());
        }
        if (ES6_ARROW_FUNCTION && this.type == TokenType.ARROW && this.isES6() && this.lookbehindNoLineTerminatorBeforeArrow()) {
            return this.arrowFunction(startToken, startLine, exprLhs, asyncArrow);
        }
        assert (!(exprLhs instanceof ExpressionList));
        if (this.type.isAssignment()) {
            boolean isAssign;
            if (canBeAssignmentPattern && !this.isDestructuringLhs(exprLhs)) {
                this.verifyExpression(coverExprLhs);
            }
            boolean bl = isAssign = this.type == TokenType.ASSIGN;
            if (isAssign) {
                this.pushDefaultName(exprLhs);
            }
            try {
                long assignToken = this.token;
                this.next();
                Expression exprRhs = this.assignmentExpression(in, yield, await2);
                Expression expression = this.verifyAssignment(assignToken, exprLhs, exprRhs, coverExpression != CoverExpressionError.DENY);
                return expression;
            }
            finally {
                if (isAssign) {
                    this.popDefaultName();
                }
            }
        }
        if (canBeAssignmentPattern) {
            if (coverExpression != CoverExpressionError.DENY) {
                coverExpression.recordErrorFrom(coverExprLhs);
            } else {
                this.verifyExpression(coverExprLhs);
            }
        }
        return exprLhs;
    }

    private Expression conditionalExpression(boolean in, boolean yield, boolean await2, CoverExpressionError coverExpression) {
        return this.expression(TokenType.TERNARY.getPrecedence(), in, yield, await2, coverExpression);
    }

    private void verifyExpression(CoverExpressionError coverExpression) {
        if (coverExpression.hasError()) {
            this.throwExpressionError(coverExpression);
        }
    }

    private void throwExpressionError(CoverExpressionError coverExpression) {
        assert (coverExpression.hasError());
        throw this.error(AbstractParser.message(coverExpression.getErrorMessage(), new String[0]), coverExpression.getErrorToken());
    }

    private void recordOrThrowExpressionError(String msgId, long assignToken, CoverExpressionError coverExpression) {
        if (coverExpression == CoverExpressionError.DENY) {
            throw this.error(AbstractParser.message(msgId, new String[0]), assignToken);
        }
        coverExpression.recordExpressionError(msgId, assignToken);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Expression arrowFunction(long startToken, int functionLine, Expression paramListExpr, boolean async) {
        ParserContextFunctionNode functionNode;
        assert (this.type != TokenType.ARROW || this.lookbehindNoLineTerminatorBeforeArrow());
        this.expect(TokenType.ARROW);
        if (this.coverArrowFunction == null) {
            functionNode = this.createParserContextArrowFunctionNode(startToken, functionLine, async, false);
        } else {
            functionNode = this.coverArrowFunction;
            functionNode.setCoverArrowHead(false);
            this.coverArrowFunction = null;
        }
        assert (functionNode.isArrow() && !functionNode.isCoverArrowHead());
        functionNode.setInternalName(this.lexer.stringIntern(ARROW_FUNCTION_NAME));
        functionNode.setFlag(1);
        this.lc.push(functionNode);
        try {
            FunctionNode function;
            Block functionBody;
            ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
            this.lc.push(parameterBlock);
            try {
                this.convertArrowFunctionParameterList(paramListExpr, functionNode);
                assert (functionNode.isAsync() == async);
                functionBody = this.functionBody(functionNode);
            }
            finally {
                this.restoreBlock(parameterBlock);
            }
            this.verifyParameterList(functionNode);
            if (parameterBlock != null) {
                functionBody = Parser.wrapParameterBlock(parameterBlock, functionBody);
            }
            FunctionNode functionNode2 = function = this.createFunctionNode(functionNode, functionNode.getFirstToken(), functionNode.getIdent(), functionLine, functionBody);
            return functionNode2;
        }
        finally {
            this.lc.pop(functionNode);
        }
    }

    private ParserContextFunctionNode createParserContextArrowFunctionNode(long startToken, int startLine, boolean async, boolean cover) {
        long functionToken = Token.recast(startToken, TokenType.ARROW);
        IdentNode name = null;
        ParserContextFunctionNode function = this.createParserContextFunctionNode(name, functionToken, 65536, startLine);
        if (async) {
            function.setFlag(0x2000000);
        }
        if (cover) {
            function.setCoverArrowHead(true);
            assert (this.coverArrowFunction == null);
        }
        return function;
    }

    private static Expression convertExpressionListToExpression(ExpressionList exprList) {
        if (exprList.getExpressions().isEmpty()) {
            return null;
        }
        if (exprList.getExpressions().size() == 1) {
            return exprList.getExpressions().get(0);
        }
        long recastToken = Token.recast(exprList.getToken(), TokenType.COMMARIGHT);
        Expression result = null;
        for (Expression expression : exprList.getExpressions()) {
            result = result == null ? expression : new BinaryNode(recastToken, result, expression);
        }
        return result;
    }

    private void convertArrowFunctionParameterList(Expression paramList, ParserContextFunctionNode function) {
        Expression paramListExpr = paramList;
        if (paramListExpr instanceof ExpressionList) {
            paramListExpr = Parser.convertExpressionListToExpression((ExpressionList)paramListExpr);
        }
        if (paramListExpr == null) {
            return;
        }
        int functionLine = function.getLineNumber();
        if (paramListExpr instanceof IdentNode || paramListExpr.isTokenType(TokenType.ASSIGN) || this.isDestructuringLhs(paramListExpr) || paramListExpr.isTokenType(TokenType.SPREAD_ARGUMENT)) {
            this.convertArrowParameter(paramListExpr, 0, functionLine, function);
        } else if (paramListExpr instanceof BinaryNode && Token.descType(paramListExpr.getToken()) == TokenType.COMMARIGHT) {
            ArrayList<Expression> params = new ArrayList<Expression>();
            Expression car = paramListExpr;
            do {
                Expression cdr = ((BinaryNode)car).getRhs();
                params.add(cdr);
            } while ((car = ((BinaryNode)car).getLhs()) instanceof BinaryNode && Token.descType(car.getToken()) == TokenType.COMMARIGHT);
            params.add(car);
            int i = params.size() - 1;
            int pos = 0;
            while (i >= 0) {
                Expression param = (Expression)params.get(i);
                if (i != 0 && param.isTokenType(TokenType.SPREAD_ARGUMENT)) {
                    throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), param.getToken());
                }
                this.convertArrowParameter(param, pos, functionLine, function);
                --i;
                ++pos;
            }
        } else {
            throw this.error(AbstractParser.message(MSG_EXPECTED_ARROW_PARAMETER, new String[0]), paramListExpr.getToken());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void convertArrowParameter(Expression param, int index, int paramLine, ParserContextFunctionNode currentFunction) {
        assert (index == currentFunction.getParameterCount());
        if (param instanceof IdentNode) {
            IdentNode ident = (IdentNode)param;
            this.verifyStrictIdent(ident, CONTEXT_FUNCTION_PARAMETER);
            if (ident.isParenthesized()) {
                throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), param.getToken());
            }
            assert (!currentFunction.isAsync() || !TokenType.AWAIT.getName().equals(ident.getName()));
            currentFunction.addParameter(ident);
            return;
        }
        if (param.isTokenType(TokenType.ASSIGN)) {
            Expression lhs = ((BinaryNode)param).getLhs();
            long paramToken = lhs.getToken();
            Expression initializer = ((BinaryNode)param).getRhs();
            assert (!(initializer instanceof IdentNode && currentFunction.isAsync() && TokenType.AWAIT.getName().equals(((IdentNode)initializer).getName())));
            if (lhs instanceof IdentNode && !lhs.isParenthesized()) {
                IdentNode ident = (IdentNode)lhs;
                if (Parser.isAnonymousFunctionDefinition(initializer)) {
                    initializer = this.setAnonymousFunctionName(initializer, ident.getNameTS());
                }
                Parser.addDefaultParameter(paramToken, param.getFinish(), paramLine, ident, initializer, currentFunction);
                return;
            }
            if (!this.isDestructuringLhs(lhs)) throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), paramToken);
            this.verifyDestructuringParameterBindingPattern(lhs, paramToken, paramLine);
            this.addDestructuringParameter(paramToken, param.getFinish(), paramLine, lhs, initializer, currentFunction, false);
            return;
        } else if (this.isDestructuringLhs(param)) {
            long paramToken = param.getToken();
            this.verifyDestructuringParameterBindingPattern(param, paramToken, paramLine);
            this.addDestructuringParameter(paramToken, param.getFinish(), paramLine, param, null, currentFunction, false);
            return;
        } else {
            if (!param.isTokenType(TokenType.SPREAD_ARGUMENT)) throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), param.getToken());
            if (this.lookbehindIsTrailingCommaInArrowParameters()) {
                throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), param.getToken());
            }
            Expression restParam = ((UnaryNode)param).getExpression();
            if (restParam instanceof IdentNode) {
                IdentNode ident = ((IdentNode)restParam).setIsRestParameter();
                this.convertArrowParameter(ident, index, paramLine, currentFunction);
                return;
            } else {
                if (!this.isDestructuringLhs(restParam)) throw this.error(AbstractParser.message(MSG_INVALID_ARROW_PARAMETER, new String[0]), param.getToken());
                this.verifyDestructuringParameterBindingPattern(restParam, restParam.getToken(), paramLine);
                this.addDestructuringParameter(restParam.getToken(), restParam.getFinish(), paramLine, restParam, null, currentFunction, true);
            }
        }
    }

    private boolean lookbehindIsTrailingCommaInArrowParameters() {
        int idx = this.k - 1;
        block4: while (true) {
            TokenType t = this.T(--idx);
            switch (t) {
                case EOL: 
                case RPAREN: 
                case COMMENT: 
                case ARROW: {
                    continue block4;
                }
                case COMMARIGHT: {
                    return true;
                }
            }
            break;
        }
        return false;
    }

    private boolean lookbehindNoLineTerminatorBeforeArrow() {
        assert (this.type == TokenType.ARROW);
        if (this.last == TokenType.RPAREN) {
            return true;
        }
        if (this.last == TokenType.IDENT) {
            return true;
        }
        block5: for (int i = this.k - 1; i >= 0; --i) {
            TokenType t = this.T(i);
            switch (t) {
                case RPAREN: 
                case IDENT: {
                    return true;
                }
                case EOL: {
                    return false;
                }
                case COMMENT: {
                    continue block5;
                }
                default: {
                    return t.isContextualKeyword() || t.isFutureStrict();
                }
            }
        }
        return false;
    }

    private boolean lookbehindNoLineTerminatorAfterAsync() {
        assert (this.type == TokenType.LPAREN);
        return this.last == TokenType.ASYNC;
    }

    private boolean lookaheadIsArrow() {
        TokenType t;
        int i = 1;
        while ((t = this.T(this.k + i++)) != TokenType.ARROW) {
            if (t == TokenType.COMMENT) continue;
            return false;
        }
        return true;
    }

    private void endOfLine() {
        switch (this.type) {
            case EOL: 
            case SEMICOLON: {
                this.next();
                break;
            }
            case EOF: 
            case RBRACE: 
            case RPAREN: 
            case RBRACKET: {
                break;
            }
            default: {
                if (this.last == TokenType.EOL) break;
                this.expect(TokenType.SEMICOLON);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Expression templateLiteral(boolean yield, boolean await2) {
        assert (this.type == TokenType.TEMPLATE || this.type == TokenType.TEMPLATE_HEAD);
        boolean noSubstitutionTemplate = this.type == TokenType.TEMPLATE;
        long startToken = this.token;
        boolean previousPauseOnRightBrace = this.lexer.pauseOnRightBrace;
        try {
            TokenType lastLiteralType;
            this.lexer.pauseOnRightBrace = true;
            LiteralNode<?> literal = this.getLiteral();
            if (noSubstitutionTemplate) {
                LiteralNode<?> literalNode = literal;
                return literalNode;
            }
            ArrayList<Expression> expressions = new ArrayList<Expression>();
            expressions.add(literal);
            do {
                Expression expression = this.templateLiteralExpression(yield, await2);
                expressions.add(expression);
                lastLiteralType = this.type;
                literal = this.getLiteral();
                expressions.add(literal);
            } while (lastLiteralType == TokenType.TEMPLATE_MIDDLE);
            TemplateLiteralNode templateLiteralNode = TemplateLiteralNode.newUntagged(startToken, literal.getFinish(), expressions);
            return templateLiteralNode;
        }
        finally {
            this.lexer.pauseOnRightBrace = previousPauseOnRightBrace;
        }
    }

    private Expression templateLiteralExpression(boolean yield, boolean await2) {
        assert (this.lexer.pauseOnRightBrace);
        Expression expression = this.expression(true, yield, await2);
        if (this.type != TokenType.RBRACE) {
            throw this.error(AbstractParser.message(MSG_UNTERMINATED_TEMPLATE_EXPRESSION, new String[0]), this.token);
        }
        this.lexer.scanTemplateSpan();
        this.next();
        assert (this.type == TokenType.TEMPLATE_MIDDLE || this.type == TokenType.TEMPLATE_TAIL);
        return expression;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private List<Expression> templateLiteralArgumentList(boolean yield, boolean await2) {
        assert (this.type == TokenType.TEMPLATE || this.type == TokenType.TEMPLATE_HEAD);
        ArrayList<Expression> argumentList = new ArrayList<Expression>();
        ArrayList<Expression> rawStrings = new ArrayList<Expression>();
        ArrayList<Expression> cookedStrings = new ArrayList<Expression>();
        argumentList.add(null);
        long templateToken = this.token;
        boolean hasSubstitutions = this.type == TokenType.TEMPLATE_HEAD;
        boolean previousPauseOnRightBrace = this.lexer.pauseOnRightBrace;
        try {
            this.lexer.pauseOnRightBrace = true;
            this.addTemplateLiteralString(rawStrings, cookedStrings);
            if (hasSubstitutions) {
                TokenType lastLiteralType;
                do {
                    Expression expression = this.templateLiteralExpression(yield, await2);
                    argumentList.add(expression);
                    lastLiteralType = this.type;
                    this.addTemplateLiteralString(rawStrings, cookedStrings);
                } while (lastLiteralType == TokenType.TEMPLATE_MIDDLE);
            }
            TemplateLiteralNode templateObject = TemplateLiteralNode.newTagged(templateToken, rawStrings.get(rawStrings.size() - 1).getFinish(), rawStrings, cookedStrings);
            argumentList.set(0, templateObject);
            ArrayList<Expression> arrayList = argumentList;
            return arrayList;
        }
        finally {
            this.lexer.pauseOnRightBrace = previousPauseOnRightBrace;
        }
    }

    private void addTemplateLiteralString(ArrayList<Expression> rawStrings, ArrayList<Expression> cookedStrings) {
        long stringToken = this.token;
        TruffleString rawString = this.lexer.valueOfRawString(stringToken);
        TruffleString cookedString = this.lexer.valueOfTaggedTemplateString(stringToken);
        this.next();
        Expression cookedExpression = cookedString == null ? Parser.newUndefinedLiteral(stringToken, this.finish) : LiteralNode.newInstance(stringToken, cookedString);
        rawStrings.add(LiteralNode.newInstance(stringToken, rawString));
        cookedStrings.add(cookedExpression);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private FunctionNode module(String moduleName) {
        int functionStart = Math.min(Token.descPosition(Token.withDelimiter(this.token)), this.finish);
        long functionToken = Token.toDesc(TokenType.FUNCTION, functionStart, this.source.getLength() - functionStart);
        int functionLine = this.line;
        Scope moduleScope = Scope.createModule();
        IdentNode ident = null;
        ParserContextFunctionNode script = this.createParserContextFunctionNode(ident, functionToken, 131072, functionLine, List.of(), 0, moduleScope);
        script.setInternalName(this.lexer.stringIntern(moduleName));
        this.lc.push(script);
        ParserContextModuleNode module = new ParserContextModuleNode(moduleName, moduleScope, this);
        ParserContextBlockNode body = this.newBlock(moduleScope);
        this.functionDeclarations = new ArrayList<Statement>();
        try {
            this.moduleBody(module);
            long yieldToken = Token.toDesc(TokenType.YIELD, functionStart, 0);
            this.prependStatement(new ExpressionStatement(functionLine, yieldToken, functionLine, new UnaryNode(yieldToken, (Expression)Parser.newUndefinedLiteral(yieldToken, this.finish))));
            script.setFlag(0x1000000);
            this.addFunctionDeclarations(script);
        }
        finally {
            this.functionDeclarations = null;
            this.restoreBlock(body);
            this.lc.pop(script);
        }
        body.setFlag(1);
        Block programBody = new Block(functionToken, this.finish, body.getFlags() | 0x10 | 0x20 | 0x200, body.getScope(), body.getStatements());
        script.setLastToken(this.token);
        this.expect(TokenType.EOF);
        script.setModule(module.createModule());
        return this.createFunctionNode(script, functionToken, ident, functionLine, programBody);
    }

    private void moduleBody(ParserContextModuleNode module) {
        block5: while (this.type != TokenType.EOF) {
            switch (this.type) {
                case EOF: {
                    break block5;
                }
                case EXPORT: {
                    this.exportDeclaration(module);
                    continue block5;
                }
                case IMPORT: {
                    if (!this.isImportExpression()) {
                        this.importDeclaration(module);
                        continue block5;
                    }
                }
                default: {
                    boolean await2 = this.isTopLevelAwait();
                    this.statement(false, await2, true, 0, false, false, false);
                    continue block5;
                }
            }
        }
    }

    private boolean isTopLevelAwait() {
        return ES2022_TOP_LEVEL_AWAIT && this.env.topLevelAwait;
    }

    private boolean isImportExpression() {
        assert (this.type == TokenType.IMPORT);
        if (!this.isES2020()) {
            return false;
        }
        TokenType la = this.lookahead();
        return la == TokenType.PERIOD || la == TokenType.LPAREN;
    }

    private void declareImportBinding(IdentNode ident, boolean star) {
        Scope moduleScope = this.lc.getCurrentBlock().getScope();
        assert (moduleScope.isModuleScope());
        if (moduleScope.hasSymbol(ident.getName())) {
            throw this.error(ECMAErrors.getMessage(MSG_SYNTAX_ERROR_REDECLARE_VARIABLE, ident.getName()), ident.getToken());
        }
        moduleScope.putSymbol(new Symbol(ident.getNameTS(), 0x402 | (star ? 0 : 16384)));
    }

    private void declareImportBinding(IdentNode ident) {
        this.declareImportBinding(ident, false);
    }

    private void declareImportStarBinding(IdentNode ident) {
        this.declareImportBinding(ident, true);
    }

    private IdentNode importedBindingIdentifier() {
        return this.bindingIdentifier(false, this.isTopLevelAwait(), CONTEXT_IMPORTED_BINDING);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void importDeclaration(ParserContextModuleNode module) {
        long importToken = this.token;
        this.expect(TokenType.IMPORT);
        if (this.type == TokenType.STRING || this.type == TokenType.ESCSTRING) {
            TruffleString moduleSpecifier = (TruffleString)this.getValue();
            long specifierToken = this.token;
            this.next();
            LiteralNode<TruffleString> specifier = LiteralNode.newInstance(specifierToken, moduleSpecifier);
            Map<TruffleString, TruffleString> assertions = Map.of();
            if (this.env.importAssertions && this.type == TokenType.ASSERT && this.last != TokenType.EOL) {
                assertions = this.assertClause();
            }
            module.addModuleRequest(Module.ModuleRequest.create(moduleSpecifier, assertions));
            module.addImport(new ImportNode(importToken, Token.descPosition(importToken), this.finish, specifier));
        } else {
            ImportClauseNode importClause;
            ArrayList<Module.ImportEntry> importEntries = new ArrayList<Module.ImportEntry>();
            long startToken = this.token;
            if (this.type == TokenType.MUL) {
                NameSpaceImportNode namespaceNode = this.nameSpaceImport();
                importClause = new ImportClauseNode(startToken, Token.descPosition(startToken), this.finish, namespaceNode);
                importEntries.add(Module.ImportEntry.importStarAsNameSpaceFrom(namespaceNode.getBindingIdentifier().getNameTS()));
            } else if (this.type == TokenType.LBRACE) {
                NamedImportsNode namedImportsNode = this.namedImports(importEntries);
                importClause = new ImportClauseNode(startToken, Token.descPosition(startToken), this.finish, namedImportsNode);
            } else {
                if (!this.isBindingIdentifier()) throw this.error(AbstractParser.message(MSG_EXPECTED_IMPORT, new String[0]));
                IdentNode importedDefaultBinding = this.importedBindingIdentifier();
                this.declareImportBinding(importedDefaultBinding);
                Module.ImportEntry defaultImport = Module.ImportEntry.importDefault(importedDefaultBinding.getNameTS());
                importEntries.add(defaultImport);
                if (this.type == TokenType.COMMARIGHT) {
                    this.next();
                    if (this.type == TokenType.MUL) {
                        NameSpaceImportNode namespaceNode = this.nameSpaceImport();
                        importClause = new ImportClauseNode(startToken, Token.descPosition(startToken), this.finish, importedDefaultBinding, namespaceNode);
                        importEntries.add(Module.ImportEntry.importStarAsNameSpaceFrom(namespaceNode.getBindingIdentifier().getNameTS()));
                    } else {
                        if (this.type != TokenType.LBRACE) throw this.error(AbstractParser.message(MSG_EXPECTED_NAMED_IMPORT, new String[0]));
                        NamedImportsNode namedImportsNode = this.namedImports(importEntries);
                        importClause = new ImportClauseNode(startToken, Token.descPosition(startToken), this.finish, importedDefaultBinding, namedImportsNode);
                    }
                } else {
                    importClause = new ImportClauseNode(startToken, Token.descPosition(startToken), this.finish, importedDefaultBinding);
                }
            }
            FromNode fromNode = this.fromClause();
            Map<TruffleString, TruffleString> assertions = Map.of();
            if (this.env.importAssertions && this.type == TokenType.ASSERT && this.last != TokenType.EOL) {
                assertions = this.assertClause();
            }
            module.addImport(new ImportNode(importToken, Token.descPosition(importToken), this.finish, importClause, fromNode));
            TruffleString moduleSpecifier = fromNode.getModuleSpecifier().getValue();
            Module.ModuleRequest moduleRequest = Module.ModuleRequest.create(moduleSpecifier, assertions);
            module.addModuleRequest(moduleRequest);
            for (int i = 0; i < importEntries.size(); ++i) {
                module.addImportEntry(((Module.ImportEntry)importEntries.get(i)).withFrom(moduleRequest));
            }
        }
        this.endOfLine();
    }

    private Map<TruffleString, TruffleString> assertClause() {
        assert (this.type == TokenType.ASSERT);
        this.next();
        this.expect(TokenType.LBRACE);
        Map<TruffleString, TruffleString> assertions = this.assertEntries();
        this.expect(TokenType.RBRACE);
        return assertions;
    }

    private Map<TruffleString, TruffleString> assertEntries() {
        LinkedHashMap<TruffleString, TruffleString> assertions = new LinkedHashMap<TruffleString, TruffleString>();
        while (this.type != TokenType.RBRACE) {
            TruffleString assertionKey;
            long errorToken = this.token;
            if (this.type == TokenType.STRING || this.type == TokenType.ESCSTRING) {
                assertionKey = (TruffleString)this.getValue();
                this.next();
            } else {
                assertionKey = this.getIdentifierName().getNameTS();
            }
            this.expect(TokenType.COLON);
            TruffleString value2 = null;
            if (this.type == TokenType.STRING || this.type == TokenType.ESCSTRING) {
                value2 = (TruffleString)this.getValue();
                this.next();
            } else {
                this.expect(TokenType.STRING);
            }
            if (assertions.containsKey(assertionKey)) {
                throw this.error(AbstractParser.message(MSG_DUPLICATE_IMPORT_ASSERTION, assertionKey.toJavaStringUncached()), errorToken);
            }
            assertions.put(assertionKey, value2);
            if (this.type != TokenType.COMMARIGHT) break;
            this.next();
        }
        return assertions;
    }

    private NameSpaceImportNode nameSpaceImport() {
        long startToken = this.token;
        assert (this.type == TokenType.MUL);
        this.next();
        this.expect(TokenType.AS);
        IdentNode localNameSpace = this.importedBindingIdentifier();
        this.declareImportStarBinding(localNameSpace);
        return new NameSpaceImportNode(startToken, Token.descPosition(startToken), this.finish, localNameSpace);
    }

    private NamedImportsNode namedImports(List<Module.ImportEntry> importEntries) {
        long startToken = this.token;
        assert (this.type == TokenType.LBRACE);
        this.next();
        ArrayList<ImportSpecifierNode> importSpecifiers = new ArrayList<ImportSpecifierNode>();
        while (this.type != TokenType.RBRACE) {
            boolean bindingIdentifier = this.isBindingIdentifier();
            long nameToken = this.token;
            IdentNode importName = this.getIdentifierName();
            if (this.type == TokenType.AS) {
                this.next();
                IdentNode localName = this.importedBindingIdentifier();
                importSpecifiers.add(new ImportSpecifierNode(nameToken, Token.descPosition(nameToken), this.finish, localName, importName));
                this.declareImportBinding(localName);
                importEntries.add(Module.ImportEntry.importSpecifier(importName.getNameTS(), localName.getNameTS()));
            } else if (bindingIdentifier) {
                this.verifyIdent(importName, false, false);
                this.verifyStrictIdent(importName, CONTEXT_IMPORTED_BINDING);
                importSpecifiers.add(new ImportSpecifierNode(nameToken, Token.descPosition(nameToken), this.finish, importName, null));
                this.declareImportBinding(importName);
                importEntries.add(Module.ImportEntry.importSpecifier(importName.getNameTS()));
            } else {
                throw this.error(AbstractParser.message(MSG_EXPECTED_BINDING_IDENTIFIER, new String[0]), nameToken);
            }
            if (this.type != TokenType.COMMARIGHT) break;
            this.next();
        }
        this.expect(TokenType.RBRACE);
        return new NamedImportsNode(startToken, Token.descPosition(startToken), this.finish, importSpecifiers);
    }

    private FromNode fromClause() {
        int fromStart = this.start;
        long fromToken = this.token;
        this.expect(TokenType.FROM);
        if (this.type == TokenType.STRING || this.type == TokenType.ESCSTRING) {
            TruffleString moduleSpecifier = (TruffleString)this.getValue();
            long specifierToken = this.token;
            this.next();
            LiteralNode<TruffleString> specifier = LiteralNode.newInstance(specifierToken, moduleSpecifier);
            return new FromNode(fromToken, fromStart, this.finish, specifier);
        }
        throw this.error(this.expectMessage(TokenType.STRING));
    }

    private void exportDeclaration(ParserContextModuleNode module) {
        long exportToken = this.token;
        Map<TruffleString, TruffleString> assertions = Map.of();
        this.expect(TokenType.EXPORT);
        boolean yield = false;
        boolean await2 = this.isTopLevelAwait();
        switch (this.type) {
            case MUL: {
                this.next();
                IdentNode exportName = null;
                if (this.type == TokenType.AS && this.isES2020()) {
                    this.next();
                    exportName = this.getIdentifierName();
                }
                FromNode from = this.fromClause();
                if (this.env.importAssertions && this.type == TokenType.ASSERT && this.last != TokenType.EOL) {
                    assertions = this.assertClause();
                }
                TruffleString moduleRequest = from.getModuleSpecifier().getValue();
                module.addModuleRequest(Module.ModuleRequest.create(moduleRequest, assertions));
                module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, exportName, from, assertions));
                this.endOfLine();
                break;
            }
            case LBRACE: {
                NamedExportsNode exportClause = this.namedExports();
                FromNode from = null;
                if (this.type == TokenType.FROM) {
                    from = this.fromClause();
                    if (this.env.importAssertions && this.type == TokenType.ASSERT && this.last != TokenType.EOL) {
                        assertions = this.assertClause();
                    }
                    TruffleString moduleRequest = from.getModuleSpecifier().getValue();
                    module.addModuleRequest(Module.ModuleRequest.create(moduleRequest, assertions));
                }
                module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, exportClause, from, assertions));
                this.endOfLine();
                break;
            }
            case DEFAULT: {
                Expression assignmentExpression;
                this.next();
                IdentNode ident = null;
                int lineNumber = this.line;
                long rhsToken = this.token;
                boolean hoistableDeclaration = false;
                switch (this.type) {
                    case FUNCTION: {
                        assignmentExpression = this.functionDeclaration(false, true, false, false, await2, true);
                        hoistableDeclaration = true;
                        break;
                    }
                    case CLASS: 
                    case AT: {
                        assignmentExpression = this.classDeclaration(false, await2, true);
                        ident = assignmentExpression.getIdent();
                        break;
                    }
                    default: {
                        if (this.isAsync() && this.lookaheadIsAsyncFunction()) {
                            assignmentExpression = this.asyncFunctionDeclaration(false, true, false, await2, true);
                            hoistableDeclaration = true;
                            break;
                        }
                        assignmentExpression = this.assignmentExpression(true, false, await2);
                        this.endOfLine();
                    }
                }
                if (hoistableDeclaration) {
                    FunctionNode functionNode = (FunctionNode)assignmentExpression;
                    assert (functionNode.isDeclared());
                    if (!functionNode.isAnonymous()) {
                        ident = functionNode.getIdent();
                    }
                }
                if (ident == null) {
                    ident = new IdentNode(Token.recast(rhsToken, TokenType.IDENT), this.finish, Module.DEFAULT_EXPORT_BINDING_NAME);
                    if (Parser.isAnonymousFunctionDefinition(assignmentExpression)) {
                        assignmentExpression = this.setAnonymousFunctionName(assignmentExpression, Module.DEFAULT_NAME);
                    }
                }
                VarNode varNode = new VarNode(lineNumber, Token.recast(rhsToken, hoistableDeclaration ? TokenType.VAR : TokenType.LET), this.finish, ident, assignmentExpression, (hoistableDeclaration ? 0 : 1) | 8);
                this.declareVar(this.lc.getCurrentScope(), varNode);
                if (hoistableDeclaration) {
                    this.functionDeclarations.add(varNode);
                } else {
                    this.lc.appendStatementToCurrentNode(varNode);
                }
                module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, ident, assignmentExpression, true));
                break;
            }
            case VAR: 
            case LET: 
            case CONST: {
                List<Statement> statements = this.lc.getCurrentBlock().getStatements();
                int previousEnd = statements.size();
                this.variableStatement(this.type, false, await2);
                for (Statement statement : statements.subList(previousEnd, statements.size())) {
                    if (!(statement instanceof VarNode)) continue;
                    VarNode varNode = (VarNode)statement;
                    module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, varNode.getName(), varNode));
                }
                break;
            }
            case CLASS: 
            case AT: {
                ClassNode classDeclaration = this.classDeclaration(false, await2, false);
                module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, classDeclaration.getIdent(), classDeclaration, false));
                break;
            }
            case FUNCTION: {
                FunctionNode functionDeclaration = (FunctionNode)this.functionDeclaration(true, true, false, false, await2, false);
                module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, functionDeclaration.getIdent(), functionDeclaration, false));
                break;
            }
            default: {
                if (this.isAsync() && this.lookaheadIsAsyncFunction()) {
                    FunctionNode functionDeclaration = (FunctionNode)this.asyncFunctionDeclaration(true, true, false, await2, false);
                    module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, functionDeclaration.getIdent(), functionDeclaration, false));
                    break;
                }
                throw this.error(AbstractParser.message(MSG_INVALID_EXPORT, new String[0]), this.token);
            }
        }
    }

    private NamedExportsNode namedExports() {
        long startToken = this.token;
        assert (this.type == TokenType.LBRACE);
        this.next();
        ArrayList<ExportSpecifierNode> exports = new ArrayList<ExportSpecifierNode>();
        long reservedWordToken = 0L;
        while (this.type != TokenType.RBRACE) {
            long nameToken = this.token;
            TokenType nameType = this.type;
            IdentNode localName = this.getIdentifierName();
            if ((Parser.isReservedWord(nameType) || Parser.isEscapedIdent(localName) && (Parser.isReservedWordSequence(localName.getName()) || Parser.isFutureStrictName(localName))) && reservedWordToken == 0L) {
                reservedWordToken = nameToken;
            }
            if (this.type == TokenType.AS) {
                this.next();
                IdentNode exportName = this.getIdentifierName();
                exports.add(new ExportSpecifierNode(nameToken, Token.descPosition(nameToken), this.finish, localName, exportName));
            } else {
                exports.add(new ExportSpecifierNode(nameToken, Token.descPosition(nameToken), this.finish, localName, null));
            }
            if (this.type != TokenType.COMMARIGHT) break;
            this.next();
        }
        this.expect(TokenType.RBRACE);
        if (reservedWordToken != 0L && this.type != TokenType.FROM) {
            throw this.error(this.expectMessage(TokenType.IDENT, reservedWordToken), reservedWordToken);
        }
        return new NamedExportsNode(startToken, Token.descPosition(startToken), this.finish, exports);
    }

    private static boolean isReservedWord(TokenType type) {
        return type.getKind() == TokenKind.KEYWORD || type.getKind() == TokenKind.FUTURE || type.getKind() == TokenKind.FUTURESTRICT;
    }

    public String toString() {
        return "'JavaScript Parsing'";
    }

    private void markEval() {
        this.lc.setCurrentFunctionFlag(160);
        this.lc.getCurrentScope().setHasEval();
    }

    private void prependStatement(Statement statement) {
        this.lc.prependStatementToCurrentNode(statement);
    }

    private void appendStatement(Statement statement) {
        this.lc.appendStatementToCurrentNode(statement);
    }

    private void markSuperProperty() {
        ParserContextFunctionNode currentFunction = this.lc.getCurrentNonArrowFunction();
        if (currentFunction.isMethod()) {
            currentFunction.setFlag(524288);
            this.addIdentifierReference(TokenType.SUPER.getName());
            this.addIdentifierReference(TokenType.THIS.getName());
        }
    }

    private void markSuperCall() {
        ParserContextFunctionNode fn = this.lc.getCurrentNonArrowFunction();
        if (!fn.isProgram()) {
            assert (fn.isDerivedConstructor());
            fn.setFlag(262144);
        }
    }

    private void markThis() {
        this.lc.setCurrentFunctionFlag(32768);
        this.addIdentifierReference(TokenType.THIS.getName());
    }

    private void markNewTarget() {
        if (!this.lc.getCurrentScope().inFunction()) {
            throw this.error(AbstractParser.message(MSG_NEW_TARGET_IN_FUNCTION, new String[0]), this.token);
        }
        ParserContextFunctionNode fn = this.lc.getCurrentNonArrowFunction();
        if (!fn.isProgram()) {
            fn.setFlag(0x800000);
        }
        this.addIdentifierReference(NEW_TARGET_NAME.toJavaStringUncached());
    }

    private static boolean markApplyArgumentsCall(ParserContext lc, List<Expression> arguments) {
        assert (arguments.size() == 2 && arguments.get(1) instanceof IdentNode && ((IdentNode)arguments.get(1)).isArguments());
        ParserContextFunctionNode currentFunction = lc.getCurrentFunction();
        if (!currentFunction.isArrow()) {
            currentFunction.setFlag(0x20000000);
            arguments.set(1, ((IdentNode)arguments.get(1)).setIsApplyArguments());
            return true;
        }
        return false;
    }

    private boolean isAwait() {
        return ES8_ASYNC_FUNCTION && this.isES2017() && this.type == TokenType.AWAIT;
    }

    private boolean isAsync() {
        return ES8_ASYNC_FUNCTION && this.isES2017() && this.type == TokenType.ASYNC;
    }

    private boolean lookaheadIsAsyncArrowParameterListStart() {
        TokenType t;
        assert (this.isAsync());
        int i = 1;
        while ((t = this.T(this.k + i++)) != TokenType.LPAREN && t != TokenType.IDENT && !t.isContextualKeyword()) {
            if (t == TokenType.COMMENT) continue;
            return false;
        }
        return true;
    }

    private boolean lookaheadIsAsyncFunction() {
        assert (this.isAsync());
        int i = 1;
        while (true) {
            long currentToken = this.getToken(this.k + i);
            TokenType t = Token.descType(currentToken);
            switch (t) {
                case COMMENT: {
                    break;
                }
                case FUNCTION: {
                    return true;
                }
                default: {
                    return false;
                }
            }
            ++i;
        }
    }

    private boolean lookaheadIsAsyncMethod(boolean allowPrivate) {
        assert (this.isAsync());
        int i = 1;
        long currentToken;
        TokenType t;
        while ((t = Token.descType(currentToken = this.getToken(this.k + i))) == TokenType.COMMENT) {
            ++i;
        }
        return this.isPropertyName(currentToken) || t == TokenType.MUL || allowPrivate && t == TokenType.PRIVATE_IDENT;
    }

    public List<Expression> decoratorList(boolean yield, boolean await2) {
        assert (this.isES2023());
        ArrayList<Expression> decoratorList = new ArrayList<Expression>();
        while (this.type == TokenType.AT) {
            Expression decoratorExpression;
            this.next();
            if (this.type == TokenType.LPAREN) {
                this.next();
                decoratorExpression = this.expression(true, yield, await2);
                this.expect(TokenType.RPAREN);
                decoratorList.add(decoratorExpression);
                continue;
            }
            decoratorExpression = this.type == TokenType.PRIVATE_IDENT ? this.privateIdentifierUse() : this.identifierReference(yield, await2);
            long callToken = this.token;
            while (this.type == TokenType.PERIOD) {
                this.next();
                IdentNode property = this.getIdentifierName();
                assert (property != null);
                decoratorExpression = new AccessNode(callToken, this.finish, decoratorExpression, property.getNameTS());
            }
            if (this.type == TokenType.LPAREN) {
                int callLine = this.line;
                callToken = this.token;
                ArrayList<Expression> arguments = this.argumentList(yield, await2);
                decoratorExpression = CallNode.forCall(callLine, callToken, decoratorExpression.getStart(), this.finish, decoratorExpression, arguments);
            }
            decoratorList.add(decoratorExpression);
        }
        return decoratorList;
    }

    public Expression parseExpression() {
        try {
            this.prepareLexer(0, this.source.getLength());
            this.scanFirstToken();
            return this.expression(false, false);
        }
        catch (Exception e) {
            this.handleParseException(e);
            return null;
        }
    }

    private static class ParserState {
        private final int position;
        private final int line;
        private final int linePosition;

        ParserState(int position, int line, int linePosition) {
            this.position = position;
            this.line = line;
            this.linePosition = linePosition;
        }

        Lexer createLexer(Source source, Lexer lexer, TokenStream stream, boolean scripting, int ecmaScriptVersion, boolean shebang, boolean isModule, boolean allowBigInt) {
            Lexer newLexer = new Lexer(source, this.position, lexer.limit - this.position, stream, scripting, ecmaScriptVersion, shebang, isModule, true, allowBigInt);
            newLexer.restoreState(new Lexer.State(this.position, Integer.MAX_VALUE, this.line, -1, this.linePosition, TokenType.SEMICOLON));
            return newLexer;
        }
    }

    private static final class PropertyFunction {
        final Expression key;
        final FunctionNode functionNode;
        final boolean computed;

        PropertyFunction(Expression key, FunctionNode function, boolean computed) {
            this.key = key;
            this.functionNode = function;
            this.computed = computed;
        }
    }

    private abstract class VerifyDestructuringPatternNodeVisitor
    extends NodeVisitor<LexicalContext> {
        VerifyDestructuringPatternNodeVisitor(LexicalContext lc) {
            super(lc);
        }

        @Override
        public boolean enterLiteralNode(LiteralNode<?> literalNode) {
            if (literalNode.isArray()) {
                if (literalNode.isParenthesized()) {
                    throw Parser.this.error(AbstractParser.message(Parser.MSG_INVALID_LVALUE, new String[0]), literalNode.getToken());
                }
                if (((LiteralNode.ArrayLiteralNode)literalNode).hasSpread() && ((LiteralNode.ArrayLiteralNode)literalNode).hasTrailingComma()) {
                    throw Parser.this.error("Rest element must be last", literalNode.getElementExpressions().get(literalNode.getElementExpressions().size() - 1).getToken());
                }
                boolean restElement = false;
                for (Expression element : literalNode.getElementExpressions()) {
                    if (element == null) continue;
                    if (restElement) {
                        throw Parser.this.error("Unexpected element after rest element", element.getToken());
                    }
                    if (element.isTokenType(TokenType.SPREAD_ARRAY)) {
                        restElement = true;
                        Expression lvalue = ((UnaryNode)element).getExpression();
                        this.verifySpreadElement(lvalue);
                        continue;
                    }
                    element.accept(this);
                }
                return false;
            }
            return this.enterDefault(literalNode);
        }

        protected abstract void verifySpreadElement(Expression var1);

        @Override
        public boolean enterObjectNode(ObjectNode objectNode) {
            if (objectNode.isParenthesized()) {
                throw Parser.this.error(AbstractParser.message(Parser.MSG_INVALID_LVALUE, new String[0]), objectNode.getToken());
            }
            boolean restElement = false;
            for (PropertyNode property : objectNode.getElements()) {
                if (property == null) continue;
                if (restElement) {
                    throw Parser.this.error("Unexpected element after rest element", property.getToken());
                }
                Expression key = property.getKey();
                if (key.isTokenType(TokenType.SPREAD_OBJECT)) {
                    restElement = true;
                    Expression lvalue = ((UnaryNode)key).getExpression();
                    this.verifySpreadElement(lvalue);
                    continue;
                }
                property.accept(this);
            }
            return false;
        }

        @Override
        public boolean enterPropertyNode(PropertyNode propertyNode) {
            if (propertyNode.getValue() != null) {
                propertyNode.getValue().accept(this);
                return false;
            }
            return this.enterDefault(propertyNode);
        }

        @Override
        public boolean enterBinaryNode(BinaryNode binaryNode) {
            if (binaryNode.isTokenType(TokenType.ASSIGN)) {
                binaryNode.getLhs().accept(this);
                return false;
            }
            return this.enterDefault(binaryNode);
        }
    }

    private static final class ForVariableDeclarationListResult {
        Expression missingAssignment;
        long declarationWithInitializerToken;
        Expression init;
        Expression firstBinding;
        Expression secondBinding;

        private ForVariableDeclarationListResult() {
        }

        void recordMissingAssignment(Expression binding) {
            if (this.missingAssignment == null) {
                this.missingAssignment = binding;
            }
        }

        void recordDeclarationWithInitializer(long token) {
            if (this.declarationWithInitializerToken == 0L) {
                this.declarationWithInitializerToken = token;
            }
        }

        void addBinding(Expression binding) {
            if (this.firstBinding == null) {
                this.firstBinding = binding;
            } else if (this.secondBinding == null) {
                this.secondBinding = binding;
            }
        }

        void addAssignment(Expression assignment) {
            this.init = this.init == null ? assignment : new BinaryNode(Token.recast(this.init.getToken(), TokenType.COMMARIGHT), this.init, assignment);
        }
    }
}

