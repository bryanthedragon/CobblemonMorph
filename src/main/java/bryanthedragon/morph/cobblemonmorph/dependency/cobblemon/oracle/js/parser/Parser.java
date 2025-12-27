package com.oracle.js.parser;

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

public class Parser extends AbstractParser {
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
   private final List<Object> defaultNames = new ArrayList<>();
   protected final Lexer.LineInfoReceiver lineInfoReceiver;
   private RecompilableScriptFunctionData reparsedFunction;
   private boolean isModule;
   private ParserContextFunctionNode coverArrowFunction;
   public static final boolean PROFILE_PARSING = Options.getBooleanProperty("parser.profiling", false);
   public static final boolean PROFILE_PARSING_PRINT = Options.getBooleanProperty("parser.profiling.print", true);

   public Parser(final ScriptEnvironment env, final Source source, final ErrorManager errors) {
      this(env, source, errors, env.strict);
   }

   public Parser(final ScriptEnvironment env, final Source source, final ErrorManager errors, final boolean strict) {
      this(env, source, errors, strict, 0);
   }

   public Parser(final ScriptEnvironment env, final Source source, final ErrorManager errors, final boolean strict, final int lineOffset) {
      super(source, errors, strict, lineOffset);
      this.env = env;
      this.scripting = env.scripting && env.syntaxExtensions;
      this.shebang = env.shebang || this.scripting;
      this.allowBigInt = env.allowBigInt;
      if (this.scripting) {
         this.lineInfoReceiver = new Lexer.LineInfoReceiver() {
            @Override
            public void lineInfo(final int receiverLine, final int receiverLinePosition) {
               Parser.this.line = receiverLine;
               Parser.this.linePosition = receiverLinePosition;
            }
         };
      } else {
         this.lineInfoReceiver = null;
      }
   }

   public FunctionNode parse() {
      return this.parse(PROGRAM_NAME, 0, this.source.getLength(), 0, null, null);
   }

   public void setReparsedFunction(final RecompilableScriptFunctionData reparsedFunction) {
      this.reparsedFunction = reparsedFunction;
   }

   private void scanFirstToken() {
      this.k = -1;
      this.next();
   }

   private void prepareLexer(final int startPos, final int len) {
      this.stream = new TokenStream();
      this.lexer = new Lexer(
         this.source,
         startPos,
         len,
         this.stream,
         this.scripting,
         this.env.ecmaScriptVersion,
         this.shebang,
         this.isModule,
         this.reparsedFunction != null,
         this.allowBigInt
      );
      this.lexer.line = this.lexer.pendingLine = this.lineOffset + 1;
      this.line = this.lineOffset;
   }

   private TokenType lookahead() {
      int i = 1;

      while (true) {
         TokenType t = this.T(this.k + i);
         if (t != TokenType.EOL && t != TokenType.COMMENT) {
            return t;
         }

         i++;
      }
   }

   private TokenType lookaheadNoLineTerminator() {
      int i = 1;

      while (true) {
         TokenType t = this.T(this.k + i);
         if (t != TokenType.COMMENT) {
            return t;
         }

         i++;
      }
   }

   public FunctionNode parse(
      final TruffleString scriptName, final int startPos, final int len, final int reparseFlags, Scope parentScope, List<String> argumentNames
   ) {
      long startTime = PROFILE_PARSING ? System.nanoTime() : 0L;

      Object duration;
      try {
         this.prepareLexer(startPos, len);
         this.scanFirstToken();
         return this.program(scriptName, reparseFlags, parentScope, argumentNames);
      } catch (Exception var18) {
         this.handleParseException(var18);
         duration = null;
      } finally {
         if (PROFILE_PARSING) {
            long durationx = System.nanoTime() - startTime;
            if (PROFILE_PARSING_PRINT) {
               System.out.println("Parsing: " + durationx / 1000000L);
            }
         }
      }

      return (FunctionNode)duration;
   }

   public FunctionNode parseModule(final String moduleName, final int startPos, final int len) {
      boolean oldModule = this.isModule;
      boolean oldStrictMode = this.isStrictMode;

      Object var7;
      try {
         this.isModule = true;
         this.isStrictMode = true;
         this.prepareLexer(startPos, len);
         this.scanFirstToken();
         return this.module(moduleName);
      } catch (Exception var11) {
         this.handleParseException(var11);
         var7 = null;
      } finally {
         this.isStrictMode = oldStrictMode;
         this.isModule = oldModule;
      }

      return (FunctionNode)var7;
   }

   public FunctionNode parseModule(final String moduleName) {
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

         assert this.lc.getCurrentScope() == null;

         this.formalParameterList(TokenType.EOF, false, false);
      } catch (Exception var2) {
         this.handleParseException(var2);
      }
   }

   public FunctionNode parseFunctionBody(boolean generator, boolean async) {
      try {
         this.stream = new TokenStream();
         this.lexer = new Lexer(this.source, this.stream, this.scripting, this.env.ecmaScriptVersion, this.shebang, this.isModule, this.allowBigInt);
         int functionLine = this.line;
         this.scanFirstToken();
         long functionToken = Token.toDesc(TokenType.FUNCTION, 0, this.source.getLength());
         IdentNode ident = new IdentNode(functionToken, Token.descPosition(functionToken), this.lexer.stringIntern(PROGRAM_NAME));
         int functionFlags = (generator ? 16777216 : 0) | (async ? 33554432 : 0);
         ParserContextFunctionNode function = this.createParserContextFunctionNode(ident, functionToken, functionFlags, functionLine, List.of(), 0);
         function.clearFlag(8192);

         assert this.lc.getCurrentScope() == null;

         this.lc.push(function);
         ParserContextBlockNode body = this.newBlock(function.createBodyScope(this.lexer::stringIntern));
         this.functionDeclarations = new ArrayList<>();

         try {
            this.sourceElements(generator, async, 0);
            this.addFunctionDeclarations(function);
         } finally {
            this.functionDeclarations = null;
            function.finishBodyScope(this.lexer::stringIntern);
            this.restoreBlock(body);
            this.lc.pop(function);
         }

         body.setFlag(1);
         Block functionBody = new Block(functionToken, this.finish, body.getFlags() | 16 | 32, body.getScope(), body.getStatements());
         this.expect(TokenType.EOF);
         return this.createFunctionNode(function, functionToken, ident, functionLine, functionBody);
      } catch (Exception var15) {
         this.handleParseException(var15);
         return null;
      }
   }

   private void handleParseException(final Exception e) {
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

   private void recover(final Exception e) {
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

      while (true) {
         switch (this.type) {
            case EOF:
               return;
            case EOL:
            case SEMICOLON:
            case RBRACE:
               this.next();
               return;
            default:
               this.nextOrEOL();
         }
      }
   }

   private ParserContextBlockNode newBlock() {
      Scope scope = Scope.createBlock(this.lc.getCurrentScope());
      return this.newBlock(scope);
   }

   private ParserContextBlockNode newBlock(Scope scope) {
      return this.lc.push(new ParserContextBlockNode(this.token, scope));
   }

   private ParserContextFunctionNode createParserContextFunctionNode(
      final IdentNode ident, final long functionToken, final int functionFlags, final int functionLine
   ) {
      return this.createParserContextFunctionNode(ident, functionToken, functionFlags, functionLine, null, 0);
   }

   private ParserContextFunctionNode createParserContextFunctionNode(
      final IdentNode ident, final long functionToken, final int functionFlags, final int functionLine, final List<IdentNode> parameters, int functionLength
   ) {
      return this.createParserContextFunctionNode(ident, functionToken, functionFlags, functionLine, parameters, functionLength, null);
   }

   private ParserContextFunctionNode createParserContextFunctionNode(
      final IdentNode ident,
      final long functionToken,
      final int functionFlags,
      final int functionLine,
      final List<IdentNode> parameters,
      int functionLength,
      Scope functionTopScope
   ) {
      ParserContextFunctionNode parentFunction = this.lc.getCurrentFunction();
      TruffleString name = ident == null ? TruffleString.Encoding.UTF_16.getEmpty() : ident.getNameTS();
      int flags = functionFlags;
      if (this.isStrictMode) {
         flags = functionFlags | 4;
      }

      if (parentFunction == null) {
         flags |= 8192;
         flags |= 1;
      }

      Scope parentScope = this.lc.getCurrentScope();
      return new ParserContextFunctionNode(functionToken, ident, name, functionLine, flags, parameters, functionLength, parentScope, functionTopScope);
   }

   private FunctionNode createFunctionNode(
      final ParserContextFunctionNode function, final long startToken, final IdentNode ident, final int functionLine, final Block body
   ) {
      assert body.isFunctionBody() || body.isParameterBlock() && ((BlockStatement)body.getLastStatement()).getBlock().isFunctionBody();

      VarNode varNode = function.verifyHoistedVarDeclarations();
      if (varNode != null) {
         throw this.error(ECMAErrors.getMessage("syntax.error.redeclare.variable", varNode.getName().getName()), varNode.getToken());
      } else {
         long lastTokenWithDelimiter = Token.withDelimiter(function.getLastToken());
         int lastTokenFinish = Token.descPosition(lastTokenWithDelimiter)
            + (Token.descType(lastTokenWithDelimiter) == TokenType.EOL ? 0 : Token.descLength(lastTokenWithDelimiter));
         return new FunctionNode(
            this.source,
            functionLine,
            body.getToken(),
            lastTokenFinish,
            startToken,
            function.getLastToken(),
            ident,
            function.getNameTS(),
            function.getLength(),
            function.getParameterCount(),
            function.getParameters(),
            function.getFlags(),
            body,
            function.getEndParserState(),
            function.getModule(),
            function.getInternalNameTS()
         );
      }
   }

   private ParserContextBlockNode restoreBlock(final ParserContextBlockNode block) {
      block.getScope().close();
      return this.lc.pop(block);
   }

   private Block getBlock(boolean yield, boolean await, boolean needsBraces) {
      long blockToken = this.token;
      ParserContextBlockNode newBlock = this.newBlock();

      try {
         if (needsBraces) {
            this.expect(TokenType.LBRACE);
         }

         this.statementList(yield, await);
      } finally {
         this.restoreBlock(newBlock);
      }

      int realFinish;
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

   private List<Statement> caseStatementList(boolean yield, boolean await) {
      ParserContextBlockNode newBlock = this.newBlock(this.lc.getCurrentScope());

      try {
         this.statementList(yield, await);
      } finally {
         this.lc.pop(newBlock);
      }

      return newBlock.getStatements();
   }

   private Block getStatement(boolean yield, boolean await) {
      return this.getStatement(yield, await, false, false);
   }

   private Block getStatement(boolean yield, boolean await, boolean labelledStatement, boolean mayBeFunctionDeclaration) {
      return this.getStatement(yield, await, labelledStatement, mayBeFunctionDeclaration, mayBeFunctionDeclaration);
   }

   private Block getStatement(
      boolean yield, boolean await, boolean labelledStatement, boolean mayBeFunctionDeclaration, boolean mayBeLabeledFunctionDeclaration
   ) {
      if (this.type == TokenType.LBRACE) {
         return this.getBlock(yield, await, true);
      } else {
         ParserContextBlockNode newBlock = this.newBlock();
         newBlock.setFlag(16);

         try {
            this.statement(yield, await, false, 0, true, labelledStatement, mayBeFunctionDeclaration, mayBeLabeledFunctionDeclaration);
         } finally {
            this.restoreBlock(newBlock);
         }

         return new Block(newBlock.getToken(), this.finish, newBlock.getFlags(), newBlock.getScope(), newBlock.getStatements());
      }
   }

   private IdentNode detectSpecialProperty(final IdentNode ident) {
      return isArguments(ident) ? this.markArguments(ident) : ident;
   }

   private IdentNode markArguments(final IdentNode ident) {
      Scope currentScope = this.lc.getCurrentScope();
      if (currentScope.inClassFieldInitializer()) {
         throw this.error(AbstractParser.message("arguments.in.field.initializer"), ident.getToken());
      } else if (currentScope.isGlobalScope()) {
         return ident;
      } else {
         this.lc.getCurrentNonArrowFunction().setFlag(8);
         return ident.setIsArguments();
      }
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

   static boolean isArguments(final TruffleString name) {
      return ARGUMENTS_NAME.equals(name);
   }

   static boolean isArguments(final IdentNode ident) {
      return isArguments(ident.getNameTS());
   }

   private static boolean checkIdentLValue(final IdentNode ident) {
      return ident.tokenType().getKind() != TokenKind.KEYWORD;
   }

   private Expression verifyAssignment(final long op, final Expression lhs, final Expression rhs, boolean inPatternPosition) {
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
         case ASSIGN_NULLCOAL:
            if (lhs instanceof IdentNode) {
               IdentNode ident = (IdentNode)lhs;
               if (!checkIdentLValue(ident) || ident.isMetaProperty()) {
                  throw this.invalidLHSError(lhs);
               }

               this.verifyStrictIdent(ident, "assignment target");
               if (!lhs.isParenthesized() && isAnonymousFunctionDefinition(rhs)) {
                  rhsExpr = this.setAnonymousFunctionName(rhs, ident.getNameTS());
               }
            } else if (!(lhs instanceof AccessNode) && !(lhs instanceof IndexNode)) {
               if (opType != TokenType.ASSIGN && opType != TokenType.ASSIGN_INIT
                  || !this.isDestructuringLhs(lhs)
                  || !inPatternPosition && lhs.isParenthesized()) {
                  throw this.invalidLHSError(lhs);
               }

               this.verifyDestructuringAssignmentPattern(lhs, "assignment target");
            } else if (((BaseNode)lhs).isOptional()) {
               throw this.invalidLHSError(lhs);
            }
      }

      assert !BinaryNode.isLogical(opType);

      return new BinaryNode(op, lhs, rhsExpr);
   }

   private boolean isDestructuringLhs(Expression lhs) {
      return !(lhs instanceof ObjectNode) && !(lhs instanceof LiteralNode.ArrayLiteralNode) ? false : ES6_DESTRUCTURING && this.isES6();
   }

   private void verifyDestructuringAssignmentPattern(Expression pattern, String contextString) {
      assert pattern instanceof ObjectNode || pattern instanceof LiteralNode.ArrayLiteralNode;

      pattern.accept(new Parser.VerifyDestructuringPatternNodeVisitor(new LexicalContext()) {
         @Override
         protected void verifySpreadElement(Expression lvalue) {
            if (!Parser.this.checkValidLValue(lvalue, contextString)) {
               throw Parser.this.error(AbstractParser.message("invalid.lvalue"), lvalue.getToken());
            } else {
               lvalue.accept(this);
            }
         }

         @Override
         public boolean enterIdentNode(IdentNode identNode) {
            if (Parser.checkIdentLValue(identNode) && !identNode.isMetaProperty()) {
               Parser.this.verifyStrictIdent(identNode, contextString);
               return false;
            } else {
               throw Parser.this.error(AbstractParser.message("invalid.lvalue"), identNode.getToken());
            }
         }

         @Override
         public boolean enterAccessNode(AccessNode accessNode) {
            if (accessNode.isOptional()) {
               throw Parser.this.error(AbstractParser.message("invalid.lvalue"), accessNode.getToken());
            } else {
               return false;
            }
         }

         @Override
         public boolean enterIndexNode(IndexNode indexNode) {
            if (indexNode.isOptional()) {
               throw Parser.this.error(AbstractParser.message("invalid.lvalue"), indexNode.getToken());
            } else {
               return false;
            }
         }

         @Override
         protected boolean enterDefault(Node node) {
            throw Parser.this.error(String.format("unexpected node in AssignmentPattern: %s", node));
         }
      });
   }

   private Expression newBinaryExpression(final long op, final Expression lhs, final Expression rhs) {
      TokenType opType = Token.descType(op);
      if (BinaryNode.isLogical(opType)) {
         if (forbiddenNullishCoalescingUsage(opType, lhs, rhs)) {
            throw this.error(String.format("nullish coalescing operator cannot immediately contain, or be contained within, an && or || operation"));
         } else {
            return new BinaryNode(op, new JoinPredecessorExpression(lhs), new JoinPredecessorExpression(rhs));
         }
      } else {
         return new BinaryNode(op, lhs, rhs);
      }
   }

   private static boolean forbiddenNullishCoalescingUsage(TokenType opType, Expression lhs, Expression rhs) {
      if (opType == TokenType.NULLISHCOALESC) {
         return forbiddenNullishCoalescingChaining(lhs) || forbiddenNullishCoalescingChaining(rhs);
      } else {
         assert opType == TokenType.AND || opType == TokenType.OR;

         return !lhs.isParenthesized() && lhs.isTokenType(TokenType.NULLISHCOALESC) || !rhs.isParenthesized() && rhs.isTokenType(TokenType.NULLISHCOALESC);
      }
   }

   private static boolean forbiddenNullishCoalescingChaining(Expression expression) {
      return !expression.isParenthesized() && (expression.isTokenType(TokenType.AND) || expression.isTokenType(TokenType.OR));
   }

   private static UnaryNode incDecExpression(final long firstToken, final TokenType tokenType, final Expression expression, final boolean isPostfix) {
      assert tokenType == TokenType.INCPREFIX || tokenType == TokenType.DECPREFIX;

      if (isPostfix) {
         long postfixToken = Token.recast(firstToken, tokenType == TokenType.DECPREFIX ? TokenType.DECPOSTFIX : TokenType.INCPOSTFIX);
         return new UnaryNode(postfixToken, expression.getStart(), Token.descPosition(firstToken) + Token.descLength(firstToken), expression);
      } else {
         return new UnaryNode(firstToken, expression);
      }
   }

   private FunctionNode program(final TruffleString scriptName, final int parseFlags, final Scope parentScope, final List<String> argumentNames) {
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
      this.functionDeclarations = new ArrayList<>();

      try {
         this.sourceElements(false, false, parseFlags);
         this.addFunctionDeclarations(script);
      } finally {
         this.functionDeclarations = null;
         script.finishBodyScope(this.lexer::stringIntern);
         this.restoreBlock(body);
         this.lc.pop(script);
      }

      body.setFlag(1);
      Block programBody = new Block(functionToken, this.finish, body.getFlags() | 16 | 32, body.getScope(), body.getStatements());
      script.setLastToken(this.token);
      this.expect(TokenType.EOF);
      return this.createFunctionNode(script, functionToken, ident, functionLine, programBody);
   }

   private Scope applyArgumentsToScope(Scope scope, List<String> argumentNames) {
      if (argumentNames == null) {
         return scope;
      } else {
         Scope body = Scope.createFunctionBody(scope);

         for (String argument : argumentNames) {
            body.putSymbol(new Symbol(this.lexer.stringIntern(argument), 20));
         }

         return body;
      }
   }

   private List<IdentNode> createFunctionNodeParameters(List<String> argumentNames) {
      if (argumentNames == null) {
         return List.of();
      } else {
         List<IdentNode> list = new ArrayList<>();

         for (String argumentName : argumentNames) {
            list.add(new IdentNode(0L, 0, this.lexer.stringIntern(argumentName)));
         }

         return list;
      }
   }

   private Scope createEvalScope(final int parseFlags, Scope parentScope) {
      assert (parseFlags & 4) != 0;

      return !this.isStrictMode && (parseFlags & 8) == 0 ? Scope.createGlobal() : Scope.createEval(parentScope, this.isStrictMode);
   }

   private static boolean isDirective(final Node stmt) {
      if (stmt instanceof ExpressionStatement) {
         Node expr = ((ExpressionStatement)stmt).getExpression();
         if (expr instanceof LiteralNode) {
            LiteralNode<?> lit = (LiteralNode<?>)expr;
            long litToken = lit.getToken();
            TokenType tt = Token.descType(litToken);
            return tt == TokenType.STRING || tt == TokenType.ESCSTRING;
         }
      }

      return false;
   }

   private boolean isUseStrictDirective(final Node stmt) {
      assert isDirective(stmt);

      Expression exp = ((ExpressionStatement)stmt).getExpression();
      return this.source.getContent().regionMatches(exp.getStart() + 1, "use strict", 0, Token.descLength(exp.getToken()) - 2);
   }

   private void sourceElements(boolean yield, boolean await, int parseFlags) {
      boolean checkDirective = true;
      int functionFlags = parseFlags;
      boolean oldStrictMode = this.isStrictMode;

      try {
         for (; this.type != TokenType.EOF; this.stream.commit(this.k)) {
            TokenType elementType = this.type;
            if (elementType == TokenType.RBRACE) {
               break;
            }

            try {
               this.statement(yield, await, true, functionFlags, false, false, true);
               functionFlags = 0;
               if (checkDirective) {
                  Statement lastStatement = elementType != TokenType.STRING && elementType != TokenType.ESCSTRING ? null : this.lc.getLastStatement();
                  checkDirective = isDirective(lastStatement);
                  if (checkDirective && elementType == TokenType.STRING && this.isUseStrictDirective(lastStatement)) {
                     ParserContextFunctionNode function = this.lc.getCurrentFunction();
                     if (!function.isSimpleParameterList()) {
                        throw this.error(AbstractParser.message("use.strict.non.simple.param"), lastStatement.getToken());
                     }

                     if (!oldStrictMode) {
                        function.setFlag(4);
                        this.isStrictMode = true;
                        this.verifyUseStrict(function, parseFlags);
                     } else {
                        assert function.isStrict();
                     }
                  }
               }
            } catch (Exception var17) {
               int errorLine = this.line;
               long errorToken = this.token;
               this.recover(var17);
               ErrorNode errorExpr = new ErrorNode(errorToken, this.finish);
               ExpressionStatement expressionStatement = new ExpressionStatement(errorLine, errorToken, this.finish, errorExpr);
               this.appendStatement(expressionStatement);
            }
         }
      } finally {
         this.isStrictMode = oldStrictMode;
      }
   }

   private void verifyUseStrict(final ParserContextFunctionNode function, final int parseFlags) {
      for (Node statement : this.lc.peek().getStatements()) {
         this.getValue(statement.getToken());
      }

      if (function.getIdent() != null) {
         this.verifyStrictIdent(function.getIdent(), "function name");
      }

      for (IdentNode param : function.getParameters()) {
         this.verifyStrictIdent(param, "function parameter");
      }

      if ((parseFlags & 4) != 0) {
         this.setupStrictEvalScope();
      }
   }

   private void setupStrictEvalScope() {
      ParserContextBlockNode body = this.lc.getCurrentBlock();

      assert body.getScope().getSymbolCount() == 0;

      if (body.getScope().isGlobalScope()) {
         Scope evalScope = Scope.createEval(body.getScope(), true);
         body.setScope(evalScope);
         ParserContextFunctionNode function = this.lc.getCurrentFunction();
         function.replaceBodyScope(evalScope);

         assert function.getBodyScope() == evalScope;
      }
   }

   private void statement(boolean yield, boolean await) {
      this.statement(yield, await, false, 0, false, false, false);
   }

   private void statement(
      boolean yield, boolean await, boolean topLevel, int reparseFlags, boolean singleStatement, boolean labelledStatement, boolean mayBeFunctionDeclaration
   ) {
      this.statement(yield, await, topLevel, reparseFlags, singleStatement, labelledStatement, mayBeFunctionDeclaration, mayBeFunctionDeclaration);
   }

   private void statement(
      boolean yield,
      boolean await,
      boolean topLevel,
      int reparseFlags,
      boolean singleStatement,
      boolean labelledStatement,
      boolean mayBeFunctionDeclaration,
      boolean mayBeLabeledFunctionDeclaration
   ) {
      switch (this.type) {
         case EOF:
         case RPAREN:
         case RBRACKET:
            this.expect(TokenType.SEMICOLON);
            return;
         case EOL:
         case RBRACE:
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
         case ASSIGN_NULLCOAL:
         default:
            break;
         case SEMICOLON:
            this.emptyStatement();
            return;
         case LBRACE:
            this.block(yield, await);
            return;
         case VAR:
            this.variableStatement(this.type, yield, await);
            return;
         case IF:
            this.ifStatement(yield, await);
            return;
         case FOR:
            this.forStatement(yield, await);
            return;
         case WHILE:
            this.whileStatement(yield, await);
            return;
         case DO:
            this.doStatement(yield, await);
            return;
         case CONTINUE:
            this.continueStatement(yield, await);
            return;
         case BREAK:
            this.breakStatement(yield, await);
            return;
         case RETURN:
            this.returnStatement(yield, await);
            return;
         case WITH:
            this.withStatement(yield, await);
            return;
         case SWITCH:
            this.switchStatement(yield, await);
            return;
         case THROW:
            this.throwStatement(yield, await);
            return;
         case TRY:
            this.tryStatement(yield, await);
            return;
         case DEBUGGER:
            this.debuggerStatement();
            return;
         case FUNCTION:
            if (!singleStatement || !this.isStrictMode && mayBeFunctionDeclaration) {
               this.functionDeclaration(true, topLevel || labelledStatement, singleStatement, yield, await, false);
               return;
            }

            throw this.error(AbstractParser.message("expected.stmt", "function declaration"), this.token);
         case LET:
            if (this.useBlockScope()) {
               TokenType lookahead = this.lookaheadOfLetDeclaration();
               if (lookahead != null) {
                  if (!singleStatement) {
                     this.variableStatement(this.type, yield, await);
                     return;
                  }

                  if (lookahead == TokenType.LBRACKET || this.T(this.k + 1) == TokenType.IDENT) {
                     throw this.error(AbstractParser.message("expected.stmt", "let declaration"), this.token);
                  }
               }
            }
            break;
         case CONST:
            if (this.useBlockScope()) {
               if (singleStatement) {
                  throw this.error(AbstractParser.message("expected.stmt", "const declaration"), this.token);
               }

               this.variableStatement(this.type, yield, await);
               return;
            }

            if (this.env.constAsVar) {
               this.variableStatement(TokenType.VAR, yield, await);
               return;
            }
            break;
         case CLASS:
         case AT:
            if (ES6_CLASS && this.isES6()) {
               if (singleStatement) {
                  throw this.error(AbstractParser.message("expected.stmt", "class declaration"), this.token);
               }

               this.classDeclaration(yield, await, false);
               return;
            }
            break;
         case ASYNC:
            if (this.isAsync() && this.lookaheadIsAsyncFunction()) {
               if (singleStatement) {
                  throw this.error(AbstractParser.message("expected.stmt", "async function declaration"), this.token);
               }

               this.asyncFunctionDeclaration(true, topLevel || labelledStatement, yield, await, false);
               return;
            }
      }

      if (this.isBindingIdentifier()) {
         if (this.T(this.k + 1) == TokenType.COLON && (this.type != TokenType.YIELD || !yield) && (!this.isAwait() || !await)) {
            this.labelStatement(yield, await, mayBeLabeledFunctionDeclaration);
            return;
         }

         if (reparseFlags != 0 && this.reparseFunctionStatement(reparseFlags)) {
            return;
         }
      }

      this.expressionStatement(yield, await);
   }

   private boolean reparseFunctionStatement(final int reparseFlags) {
      boolean allowPropertyFunction = (reparseFlags & 1) != 0;
      boolean isES6Method = (reparseFlags & 2) != 0;
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
         long propertyTokenx = this.token;
         int propertyLinex = this.line;
         this.next();
         int flags = CONSTRUCTOR_NAME.equals(ident) ? 2097152 : 1048576;
         this.addPropertyFunctionStatement(this.propertyMethodFunction(identNode, propertyTokenx, propertyLinex, false, flags, false, false));
         return true;
      }

      return false;
   }

   private void addPropertyFunctionStatement(final Parser.PropertyFunction propertyFunction) {
      FunctionNode fn = propertyFunction.functionNode;
      this.functionDeclarations.add(new ExpressionStatement(fn.getLineNumber(), fn.getToken(), this.finish, fn));
   }

   private ClassNode classDeclaration(boolean yield, boolean await, boolean defaultExport) {
      assert this.type == TokenType.CLASS || this.type == TokenType.AT;

      List<Expression> classDecorators = null;
      if (this.type == TokenType.AT) {
         assert this.isES2023();

         classDecorators = this.decoratorList(yield, await);
      }

      int classLineNumber = this.line;
      long classToken = this.token;
      this.next();
      boolean oldStrictMode = this.isStrictMode;
      this.isStrictMode = true;

      ClassNode var15;
      try {
         IdentNode className = null;
         if (!defaultExport || this.isBindingIdentifier()) {
            className = this.bindingIdentifier(yield, await, "class name");
         }

         ClassNode classExpression = this.classTail(classLineNumber, classToken, className, yield, await, classDecorators);
         if (!defaultExport) {
            VarNode classVar = new VarNode(
               classLineNumber, Token.recast(classExpression.getToken(), TokenType.LET), classExpression.getFinish(), className, classExpression, 1
            );
            this.appendStatement(classVar);
            this.declareVar(this.lc.getCurrentScope(), classVar);
         }

         var15 = classExpression;
      } finally {
         this.isStrictMode = oldStrictMode;
      }

      return var15;
   }

   private ClassNode classExpression(boolean yield, boolean await) {
      assert this.type == TokenType.CLASS || this.type == TokenType.AT;

      List<Expression> classDecorators = null;
      if (this.type == TokenType.AT) {
         assert this.isES2023();

         classDecorators = this.decoratorList(yield, await);
      }

      int classLineNumber = this.line;
      long classToken = this.token;
      this.next();
      boolean oldStrictMode = this.isStrictMode;
      this.isStrictMode = true;

      ClassNode var9;
      try {
         IdentNode className = null;
         if (this.isBindingIdentifier()) {
            className = this.bindingIdentifier(yield, await, "class name");
         }

         var9 = this.classTail(classLineNumber, classToken, className, yield, await, classDecorators);
      } finally {
         this.isStrictMode = oldStrictMode;
      }

      return var9;
   }

   private ClassNode classTail(int classLineNumber, long classToken, IdentNode className, boolean yield, boolean await, List<Expression> classDecorators) {
      assert this.isStrictMode;

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
            classHeritage = this.leftHandSideExpression(yield, await, CoverExpressionError.DENY);
            IdentNode invalidPrivateIdent = classNode.verifyAllPrivateIdentifiersValid(this.lc);
            if (invalidPrivateIdent != null) {
               throw this.error(AbstractParser.message("invalid.private.ident"), invalidPrivateIdent.getToken());
            }
         }

         this.expect(TokenType.LBRACE);
         Scope classScope = Scope.createClassBody(classHeadScope);
         classNode.setScope(classScope);
         ClassElement constructor = null;
         List<ClassElement> classElements = new ArrayList<>();
         Map<String, Integer> privateNameToAccessorIndexMap = new HashMap<>();
         int instanceFieldCount = 0;
         int staticElementCount = 0;
         boolean hasPrivateMethods = false;
         boolean hasPrivateInstanceMethods = false;

         while (true) {
            while (this.type == TokenType.SEMICOLON) {
               this.next();
            }

            if (this.type == TokenType.RBRACE) {
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

                  constructor = constructor.setValue(
                     new FunctionNode(
                        ctor.getSource(),
                        ctor.getLineNumber(),
                        ctor.getToken(),
                        classFinish,
                        classToken,
                        lastToken,
                        className,
                        className == null ? TruffleString.Encoding.UTF_16.getEmpty() : className.getNameTS(),
                        ctor.getLength(),
                        ctor.getNumOfParams(),
                        ctor.getParameters(),
                        flags,
                        ctor.getBody(),
                        ctor.getEndParserState(),
                        ctor.getModule(),
                        ctor.getInternalNameTS()
                     )
                  );
               }

               IdentNode invalidPrivateIdent = classNode.verifyAllPrivateIdentifiersValid(this.lc);
               if (invalidPrivateIdent != null) {
                  throw this.error(AbstractParser.message("invalid.private.ident"), invalidPrivateIdent.getToken());
               }

               if (hasPrivateMethods) {
                  classScope.putSymbol(new Symbol(this.lexer.stringIntern(ClassNode.PRIVATE_CONSTRUCTOR_BINDING_NAME), 132098));
               }

               classScope.close();
               classHeadScope.close();
               return new ClassNode(
                  classToken,
                  classFinish,
                  className,
                  classHeritage,
                  constructor,
                  classElements,
                  classDecorators,
                  classScope,
                  instanceFieldCount,
                  staticElementCount,
                  hasPrivateMethods,
                  hasPrivateInstanceMethods
               );
            }

            List<Expression> classElementDecorators = null;
            if (this.type == TokenType.AT) {
               classElementDecorators = this.decoratorList(yield, await);
            }

            boolean isAutoAccessor = false;
            if (this.isES2023() && this.type == TokenType.ACCESSOR) {
               TokenType nextToken = this.lookaheadNoLineTerminator();
               if (nextToken != TokenType.LPAREN
                  && nextToken != TokenType.ASSIGN
                  && nextToken != TokenType.SEMICOLON
                  && nextToken != TokenType.RBRACE
                  && nextToken != TokenType.EOL) {
                  isAutoAccessor = true;
                  this.next();
               }
            }

            boolean isStatic = false;
            if (this.type == TokenType.STATIC) {
               TokenType nextToken = this.lookahead();
               if (this.isES2023() && nextToken == TokenType.ACCESSOR) {
                  this.next();
                  nextToken = this.lookaheadNoLineTerminator();
                  if (nextToken != TokenType.LPAREN
                     && nextToken != TokenType.ASSIGN
                     && nextToken != TokenType.SEMICOLON
                     && nextToken != TokenType.RBRACE
                     && nextToken != TokenType.EOL) {
                     isStatic = true;
                     isAutoAccessor = true;
                     this.next();
                  } else {
                     isStatic = true;
                  }
               } else if (nextToken != TokenType.LPAREN && nextToken != TokenType.ASSIGN && nextToken != TokenType.SEMICOLON && nextToken != TokenType.RBRACE) {
                  isStatic = true;
                  int staticLine = this.line;
                  long staticToken = this.token;
                  this.next();
                  if (this.type == TokenType.LBRACE && this.isES2022()) {
                     if (classElementDecorators != null && classElementDecorators.size() != 0) {
                        throw this.error(AbstractParser.message("decorated.static.block"), staticToken);
                     }

                     ClassElement staticInit = this.staticInitializer(staticLine, staticToken);
                     staticElementCount++;
                     classElements.add(staticInit);
                     continue;
                  }
               }
            }

            long classElementToken = this.token;
            int classElementLine = this.line;
            boolean async = false;
            if (this.isAsync() && this.lookaheadIsAsyncMethod(true)) {
               async = true;
               this.next();
            }

            boolean generator = false;
            if (this.type == TokenType.MUL && ES6_GENERATOR_FUNCTION && this.isES6()) {
               generator = true;
               this.next();
            }

            TokenType nameTokenType = this.type;
            boolean computed = nameTokenType == TokenType.LBRACKET;
            Expression classElementName = this.classElementName(yield, await, true);
            ClassElement classElement;
            if (!generator && !async && this.isClassFieldDefinition(nameTokenType)) {
               classElement = this.fieldDefinition(classElementName, isStatic, isAutoAccessor, classElementToken, computed, classElementDecorators);
               if (isStatic) {
                  staticElementCount++;
               } else {
                  instanceFieldCount++;
               }
            } else {
               if (isAutoAccessor) {
                  throw this.error(AbstractParser.message("auto.accessor.not.field"));
               }

               classElement = this.methodDefinition(
                  classElementName,
                  isStatic,
                  classHeritage != null,
                  generator,
                  async,
                  classElementToken,
                  classElementLine,
                  yield,
                  await,
                  nameTokenType,
                  computed,
                  classElementDecorators
               );
               if (!classElement.isComputed() && classElement.isAccessor()) {
                  if (classElement.isPrivate()) {
                     String privateName = classElement.getPrivateName();
                     Integer existing = privateNameToAccessorIndexMap.get(privateName);
                     if (existing == null) {
                        privateNameToAccessorIndexMap.put(privateName, classElements.size());
                     } else {
                        ClassElement otherAccessor = classElements.get(existing);
                        if (isStatic == otherAccessor.isStatic()) {
                           if (otherAccessor.getGetter() == null && classElement.getGetter() != null) {
                              classElements.set(existing, otherAccessor.setGetter(classElement.getGetter()));
                              continue;
                           }

                           if (otherAccessor.getSetter() == null && classElement.getSetter() != null) {
                              classElements.set(existing, otherAccessor.setSetter(classElement.getSetter()));
                              continue;
                           }
                        }
                     }
                  } else if (!classElements.isEmpty()) {
                     ClassElement lastElement = classElements.get(classElements.size() - 1);
                     if (classElement.getDecorators() == null
                        && lastElement.getDecorators() == null
                        && !lastElement.isComputed()
                        && lastElement.isAccessor()
                        && isStatic == lastElement.isStatic()
                        && !lastElement.isPrivate()
                        && classElement.getKeyName().equals(lastElement.getKeyName())) {
                        ClassElement merged = classElement.getGetter() != null
                           ? lastElement.setGetter(classElement.getGetter())
                           : lastElement.setSetter(classElement.getSetter());
                        classElements.set(classElements.size() - 1, merged);
                        continue;
                     }
                  }
               }
            }

            if (classElement.isPrivate()) {
               hasPrivateMethods = hasPrivateMethods || !classElement.isClassField();
               hasPrivateInstanceMethods = hasPrivateInstanceMethods || !classElement.isClassField() && !classElement.isStatic();
               this.declarePrivateName(classScope, classElement);
            }

            if (!classElement.isStatic() && !classElement.isComputed() && classElement.getKeyNameTS().equals(CONSTRUCTOR_NAME)) {
               assert !classElement.isClassField();

               if (constructor != null) {
                  throw this.error(AbstractParser.message("multiple.constructors"), classElementToken);
               }

               if (classElement.getDecorators() != null && classElement.getDecorators().size() > 0) {
                  throw this.error(AbstractParser.message("decorated.constructor"));
               }

               constructor = classElement;
            } else {
               classElements.add(classElement);
            }
         }
      } finally {
         this.lc.pop(classNode);
      }
   }

   private Expression classElementName(boolean yield, boolean await, boolean allowPrivate) {
      return (Expression)(allowPrivate && this.type == TokenType.PRIVATE_IDENT ? this.privateIdentifierDeclaration() : this.propertyName(yield, await));
   }

   private IdentNode parsePrivateIdentifier() {
      assert this.type == TokenType.PRIVATE_IDENT;

      if (!this.isClassFields() && !this.isES2021()) {
         throw this.error(AbstractParser.message("unexpected.token", this.type.getNameOrType()));
      } else {
         long identToken = this.token;
         TruffleString name = (TruffleString)this.getValue(identToken);
         this.next();
         return this.createIdentNode(identToken, this.finish, name).setIsPrivate();
      }
   }

   private IdentNode privateIdentifierDeclaration() {
      IdentNode privateIdent = this.parsePrivateIdentifier();
      ParserContextClassNode currentClass = this.lc.getCurrentClass();
      if (currentClass == null) {
         throw this.error(AbstractParser.message("invalid.private.ident"), privateIdent.getToken());
      } else {
         return privateIdent;
      }
   }

   private void declarePrivateName(Scope classScope, ClassElement classElement) {
      int privateFlags = classElement.isStatic() ? 262144 : 0;
      if (!classElement.isClassField()) {
         privateFlags |= classElement.isAccessor() ? 1048576 : 524288;
      }

      if (!classScope.addPrivateName(classElement.getPrivateNameTS(), privateFlags)) {
         throw this.error(ECMAErrors.getMessage("syntax.error.redeclare.variable", classElement.getPrivateName()), classElement.getKey().getToken());
      }
   }

   private IdentNode privateIdentifierUse() {
      IdentNode privateIdent = this.parsePrivateIdentifier();
      Scope currentScope = this.lc.getCurrentScope();
      ParserContextClassNode currentClass = this.lc.getCurrentClass();
      if (currentClass != null) {
         currentClass.usePrivateName(privateIdent);
      } else if (!currentScope.findPrivateName(privateIdent.getName())) {
         throw this.error(AbstractParser.message("invalid.private.ident"), privateIdent.getToken());
      }

      currentScope.addIdentifierReference(privateIdent.getName());
      return privateIdent;
   }

   private boolean isClassFieldDefinition(final TokenType nameTokenType) {
      if (!this.isClassFields()) {
         return false;
      } else {
         switch (this.type) {
            case SEMICOLON:
            case RBRACE:
            case ASSIGN:
               return true;
            case LPAREN:
               return false;
            default:
               return nameTokenType == TokenType.GET || nameTokenType == TokenType.SET ? false : this.last == TokenType.EOL;
         }
      }
   }

   private ClassElement createDefaultClassConstructor(int classLineNumber, long classToken, long lastToken, IdentNode className, boolean derived) {
      int ctorFinish = this.finish;
      long identToken = Token.recast(classToken, TokenType.IDENT);
      List<Statement> statements;
      List<IdentNode> parameters;
      if (derived) {
         IdentNode superIdent = new IdentNode(identToken, ctorFinish, this.lexer.stringIntern(TokenType.SUPER.getNameTS())).setIsDirectSuper();
         IdentNode argsIdent = new IdentNode(identToken, ctorFinish, this.lexer.stringIntern(ARGS)).setIsRestParameter();
         Expression spreadArgs = new UnaryNode(Token.recast(classToken, TokenType.SPREAD_ARGUMENT), argsIdent);
         Expression superCall = CallNode.forCall(
            classLineNumber, classToken, Token.descPosition(classToken), ctorFinish, superIdent, List.of(spreadArgs), false, false, false, false, true
         );
         statements = List.of(new ExpressionStatement(classLineNumber, classToken, ctorFinish, superCall));
         parameters = List.of(argsIdent);
      } else {
         statements = List.of();
         parameters = List.of();
      }

      int functionFlags = 3145728;
      ParserContextFunctionNode function = this.createParserContextFunctionNode(className, classToken, functionFlags, classLineNumber, parameters, 0);
      function.setLastToken(lastToken);
      Scope scope = function.createBodyScope(this.lexer::stringIntern);
      scope.close();
      Block body = new Block(classToken, ctorFinish, 32, scope, statements);
      if (derived) {
         function.setFlag(4194304);
         function.setFlag(262144);
      }

      if (className == null) {
         function.setFlag(1);
         function.setInternalName(this.lexer.stringIntern(CONSTRUCTOR_NAME));
      }

      this.lc.setCurrentFunctionFlag(16384);
      return ClassElement.createDefaultConstructor(
         classToken,
         ctorFinish,
         new IdentNode(identToken, ctorFinish, CONSTRUCTOR_NAME),
         this.createFunctionNode(function, classToken, className, classLineNumber, body)
      );
   }

   private ClassElement methodDefinition(
      Expression propertyName,
      boolean isStatic,
      boolean derived,
      boolean generator,
      boolean async,
      long startToken,
      int methodLine,
      boolean yield,
      boolean await,
      TokenType nameTokenType,
      boolean computed,
      List<Expression> classElementDecorators
   ) {
      int flags = 1048576;
      boolean isPrivate = false;
      if (!computed) {
         String name = ((PropertyKey)propertyName).getPropertyName();
         if (!generator && nameTokenType == TokenType.GET && this.type != TokenType.LPAREN) {
            Parser.PropertyFunction methodDefinition = this.propertyGetterFunction(startToken, methodLine, yield, await, true);
            this.verifyAllowedMethodName(methodDefinition.key, isStatic, methodDefinition.computed, generator, true, async);
            return ClassElement.createAccessor(
               startToken,
               this.finish,
               methodDefinition.key,
               methodDefinition.functionNode,
               null,
               classElementDecorators,
               isPrivate,
               isStatic,
               methodDefinition.computed
            );
         }

         if (!generator && nameTokenType == TokenType.SET && this.type != TokenType.LPAREN) {
            Parser.PropertyFunction methodDefinition = this.propertySetterFunction(startToken, methodLine, yield, await, true);
            this.verifyAllowedMethodName(methodDefinition.key, isStatic, methodDefinition.computed, generator, true, async);
            return ClassElement.createAccessor(
               startToken,
               this.finish,
               methodDefinition.key,
               null,
               methodDefinition.functionNode,
               classElementDecorators,
               isPrivate,
               isStatic,
               methodDefinition.computed
            );
         }

         if (!isStatic && !generator && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
            flags |= 2097152;
            if (derived) {
               flags |= 4194304;
            }
         }

         this.verifyAllowedMethodName(propertyName, isStatic, computed, generator, false, async);
      }

      Parser.PropertyFunction methodDefinition = this.propertyMethodFunction(propertyName, startToken, methodLine, generator, flags, computed, async);
      return ClassElement.createMethod(startToken, this.finish, methodDefinition.key, methodDefinition.functionNode, classElementDecorators, isStatic, computed);
   }

   private void verifyAllowedMethodName(Expression key, boolean isStatic, boolean computed, boolean generator, boolean accessor, boolean async) {
      if (!computed) {
         String name = ((PropertyKey)key).getPropertyName();
         if (!isStatic && generator && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
            throw this.error(AbstractParser.message("generator.constructor"), key.getToken());
         }

         if (!isStatic && accessor && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
            throw this.error(AbstractParser.message("accessor.constructor"), key.getToken());
         }

         if (!isStatic && async && name.equals(CONSTRUCTOR_NAME.toJavaStringUncached())) {
            throw this.error(AbstractParser.message("async.constructor"), key.getToken());
         }

         if (isStatic && name.equals("prototype")) {
            throw this.error(AbstractParser.message("static.prototype.method"), key.getToken());
         }

         if (name.equals("#constructor")) {
            throw this.error(AbstractParser.message("private.constructor.method"), key.getToken());
         }
      }
   }

   private ClassElement fieldDefinition(
      Expression propertyName, boolean isStatic, boolean isAutoAccessor, long startToken, boolean computed, List<Expression> classElementDecorators
   ) {
      if (!computed && propertyName instanceof PropertyKey) {
         String name = ((PropertyKey)propertyName).getPropertyName();
         if (CONSTRUCTOR_NAME.toJavaStringUncached().equals(name) || "#constructor".equals(name)) {
            throw this.error(AbstractParser.message("constructor.field"), startToken);
         }

         if (isStatic && "prototype".equals(name)) {
            throw this.error(AbstractParser.message("static.prototype.field"), startToken);
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

      return isAutoAccessor
         ? ClassElement.createAutoAccessor(
            startToken, this.finish, propertyName, initializer, classElementDecorators, isStatic, computed, isAnonymousFunctionDefinition
         )
         : ClassElement.createField(
            startToken, this.finish, propertyName, initializer, classElementDecorators, isStatic, computed, isAnonymousFunctionDefinition
         );
   }

   private Pair<FunctionNode, Boolean> fieldInitializer(int lineNumber, long fieldToken, Expression propertyName, boolean computed) {
      int functionFlags = 1074790401;
      ParserContextFunctionNode function = this.createParserContextFunctionNode(null, fieldToken, functionFlags, lineNumber, List.of(), 0);
      function.setInternalName(this.lexer.stringIntern(INITIALIZER_FUNCTION_NAME));
      this.lc.push(function);
      ParserContextBlockNode body = this.newBlock(function.createBodyScope(this.lexer::stringIntern));

      Expression initializer;
      try {
         initializer = this.assignmentExpression(true, false, false);
      } finally {
         function.finishBodyScope(this.lexer::stringIntern);
         this.restoreBlock(body);
         this.lc.propagateFunctionFlags();
         this.lc.pop(function);
      }

      assert function.getFlag(8) == 0;

      function.setLastToken(this.token);
      boolean isAnonymousFunctionDefinition = false;
      if (isAnonymousFunctionDefinition(initializer)) {
         if (!computed && propertyName instanceof PropertyKey) {
            initializer = this.setAnonymousFunctionName(initializer, ((PropertyKey)propertyName).getPropertyNameTS());
         } else {
            isAnonymousFunctionDefinition = true;
            initializer = new UnaryNode(Token.recast(initializer.getToken(), TokenType.NAMEDEVALUATION), initializer);
         }
      }

      this.lc.setCurrentFunctionFlag(16384);
      List<Statement> statements = List.of(new ReturnNode(lineNumber, fieldToken, this.finish, initializer));
      Block bodyBlock = new Block(fieldToken, this.finish, 48, body.getScope(), statements);
      return Pair.create(this.createFunctionNode(function, fieldToken, null, lineNumber, bodyBlock), isAnonymousFunctionDefinition);
   }

   private ClassElement staticInitializer(int lineNumber, long staticToken) {
      assert this.type == TokenType.LBRACE;

      int functionFlags = 1074790401;
      ParserContextFunctionNode function = this.createParserContextFunctionNode(null, staticToken, functionFlags, lineNumber, List.of(), 0);
      function.setInternalName(this.lexer.stringIntern(INITIALIZER_FUNCTION_NAME));
      this.lc.push(function);

      Block bodyBlock;
      try {
         bodyBlock = this.functionBody(function);
      } finally {
         this.lc.pop(function);
      }

      assert function.getFlag(8) == 0;

      this.lc.setCurrentFunctionFlag(16384);
      FunctionNode functionNode = this.createFunctionNode(function, staticToken, null, lineNumber, bodyBlock);
      return ClassElement.createStaticInitializer(staticToken, this.finish, functionNode);
   }

   private boolean isPropertyName(long currentToken) {
      TokenType currentType = Token.descType(currentToken);
      if (ES6_COMPUTED_PROPERTY_NAME && currentType == TokenType.LBRACKET && this.isES6()) {
         return true;
      } else {
         switch (currentType) {
            case IDENT:
               return true;
            case NON_OCTAL_DECIMAL:
            case OCTAL_LEGACY:
               if (this.isStrictMode) {
                  return false;
               }
            case STRING:
            case ESCSTRING:
            case DECIMAL:
            case HEXADECIMAL:
            case OCTAL:
            case BINARY_NUMBER:
            case BIGINT:
            case FLOATING:
               return true;
            default:
               return this.isIdentifierName(currentToken);
         }
      }
   }

   private void block(boolean yield, boolean await) {
      this.appendStatement(new BlockStatement(this.line, this.getBlock(yield, await, true)));
   }

   private void statementList(boolean yield, boolean await) {
      while (this.type != TokenType.EOF) {
         switch (this.type) {
            case EOF:
            case RBRACE:
            case CASE:
            case DEFAULT:
               return;
            default:
               this.statement(yield, await);
         }
      }
   }

   private void verifyIdent(final IdentNode ident, final boolean yield, final boolean await) {
      if (this.isES6()) {
         if (isEscapedIdent(ident) && isReservedWordSequence(ident.getName())) {
            throw this.error(AbstractParser.message("escaped.keyword", ident), ident.getToken());
         }

         assert !isReservedWordSequence(ident.getName()) : ident.getName();
      }

      if (yield) {
         if (ident.isTokenType(TokenType.YIELD)) {
            throw this.error(this.expectMessage(TokenType.IDENT, ident.getToken()), ident.getToken());
         }

         if (isEscapedIdent(ident) && TokenType.YIELD.getName().equals(ident.getName())) {
            throw this.error(AbstractParser.message("escaped.keyword", ident), ident.getToken());
         }

         assert !TokenType.YIELD.getName().equals(ident.getName());
      }

      boolean awaitOrModule = await || this.isModule;
      if (ident.isTokenType(TokenType.AWAIT)) {
         if (awaitOrModule) {
            throw this.error(this.expectMessage(TokenType.IDENT, ident.getToken()), ident.getToken());
         }

         this.recordYieldOrAwait(ident);
      } else if (isEscapedIdent(ident) && TokenType.AWAIT.getName().equals(ident.getName())) {
         if (awaitOrModule) {
            throw this.error(AbstractParser.message("escaped.keyword", ident), ident.getToken());
         }

         this.recordYieldOrAwait(ident);
      } else {
         assert !TokenType.AWAIT.getName().equals(ident.getName());
      }
   }

   private static boolean isEscapedIdent(final IdentNode ident) {
      return ident.getName().length() != Token.descLength(ident.getToken());
   }

   private static boolean isReservedWordSequence(final String name) {
      TokenType tokenType = TokenLookup.lookupKeyword(name, 0, name.length());
      return tokenType != TokenType.IDENT && !tokenType.isContextualKeyword() && !tokenType.isFutureStrict();
   }

   private void verifyStrictIdent(final IdentNode ident, final String contextString, final boolean bindingIdentifier) {
      if (this.isStrictMode && !isValidStrictIdent(ident, bindingIdentifier)) {
         throw this.error(AbstractParser.message("strict.name", ident.getName(), contextString), ident.getToken());
      }
   }

   private void verifyStrictIdent(final IdentNode ident, final String contextString) {
      this.verifyStrictIdent(ident, contextString, true);
   }

   private static boolean isValidStrictIdent(final IdentNode ident, final boolean bindingIdentifier) {
      return !bindingIdentifier || !"eval".equals(ident.getName()) && !ARGUMENTS_NAME.equals(ident.getNameTS()) ? !isFutureStrictName(ident) : false;
   }

   private static boolean isFutureStrictName(final IdentNode ident) {
      if (ident.tokenType().isFutureStrict()) {
         return true;
      } else if (!isEscapedIdent(ident)) {
         return false;
      } else {
         TokenType tokenType = TokenLookup.lookupKeyword(ident.getName(), 0, ident.getName().length());
         return tokenType != TokenType.IDENT && tokenType.isFutureStrict();
      }
   }

   private void variableStatement(final TokenType varType, boolean yield, boolean await) {
      this.variableDeclarationList(varType, true, yield, await, -1);
   }

   private Parser.ForVariableDeclarationListResult variableDeclarationList(
      TokenType varType, boolean isStatement, boolean yield, boolean await, int sourceOrder
   ) {
      int varStart = Token.descPosition(this.token);

      assert varType == TokenType.VAR || varType == TokenType.LET || varType == TokenType.CONST;

      this.next();
      int varFlags = 0;
      if (varType == TokenType.LET) {
         varFlags |= 1;
      } else if (varType == TokenType.CONST) {
         varFlags |= 2;
      }

      Parser.ForVariableDeclarationListResult forResult = isStatement ? null : new Parser.ForVariableDeclarationListResult();
      final Scope scope = this.lc.getCurrentScope();

      while (true) {
         final int varLine = this.line;
         final long varToken = Token.recast(this.token, varType);
         Expression binding = this.bindingIdentifierOrPattern(yield, await, "variable name");
         boolean isDestructuring = !(binding instanceof IdentNode);
         if (isDestructuring) {
            final int finalVarFlags = varFlags | 16;
            this.verifyDestructuringBindingPattern(binding, new Consumer<IdentNode>() {
               public void accept(IdentNode identNode) {
                  Parser.this.verifyStrictIdent(identNode, "variable name");
                  if (varType != TokenType.VAR && identNode.getName().equals(TokenType.LET.getName())) {
                     throw Parser.this.error(AbstractParser.message("let.lexical.binding"));
                  } else {
                     VarNode var = new VarNode(varLine, varToken, sourceOrder, identNode.getFinish(), identNode.setIsDeclaredHere(), null, finalVarFlags);
                     Parser.this.appendStatement(var);
                     Parser.this.declareVar(scope, var);
                  }
               }
            });
         }

         Expression init = null;
         if (this.type == TokenType.ASSIGN) {
            if (!isStatement) {
               forResult.recordDeclarationWithInitializer(varToken);
            }

            this.next();
            if (!isDestructuring) {
               this.pushDefaultName(binding);
            }

            try {
               init = this.assignmentExpression(isStatement, yield, await);
            } finally {
               if (!isDestructuring) {
                  this.popDefaultName();
               }
            }
         } else if (isStatement) {
            if (isDestructuring) {
               throw this.error(AbstractParser.message("missing.destructuring.assignment"), this.token);
            }

            if (varType == TokenType.CONST) {
               throw this.error(AbstractParser.message("missing.const.assignment", ((IdentNode)binding).getName()));
            }
         }

         if (!isDestructuring) {
            assert init != null || varType != TokenType.CONST || !isStatement;

            IdentNode ident = (IdentNode)binding;
            if (varType != TokenType.VAR && ident.getName().equals(TokenType.LET.getName())) {
               throw this.error(AbstractParser.message("let.lexical.binding"));
            }

            if (!isStatement) {
               if (init == null && varType == TokenType.CONST) {
                  forResult.recordMissingAssignment(binding);
               }

               forResult.addBinding(binding);
            }

            if (isAnonymousFunctionDefinition(init)) {
               init = this.setAnonymousFunctionName(init, ident.getNameTS());
            }

            VarNode var = new VarNode(varLine, varToken, sourceOrder, varStart, this.finish, ident.setIsDeclaredHere(), init, varFlags);
            this.appendStatement(var);
            this.declareVar(scope, var);
         } else {
            assert init != null || !isStatement;

            if (init != null) {
               Expression assignment = this.verifyAssignment(Token.recast(varToken, TokenType.ASSIGN_INIT), binding, init, true);
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

         if (this.type != TokenType.COMMARIGHT) {
            if (isStatement) {
               this.endOfLine();
            }

            return forResult;
         }

         this.next();
      }
   }

   private void declareVar(Scope scope, VarNode varNode) {
      String name = varNode.getName().getName();
      if (this.detectVarNameConflict(scope, varNode)) {
         throw this.error(ECMAErrors.getMessage("syntax.error.redeclare.variable", name), varNode.getToken());
      } else {
         if (varNode.isBlockScoped()) {
            int symbolFlags = varNode.getSymbolFlags() | (scope.isSwitchBlockScope() ? 8192 : 0) | (varNode.isFunctionDeclaration() ? 65536 : 0);
            Symbol existing = scope.putSymbol(new Symbol(varNode.getName().getNameTS(), symbolFlags));

            assert existing == null || existing.isBlockFunctionDeclaration() && varNode.isFunctionDeclaration() : existing;

            if (varNode.isFunctionDeclaration() && this.isAnnexB()) {
               ParserContextFunctionNode function = this.lc.getCurrentFunction();
               Scope varScope = function.getBodyScope();
               if (!function.isStrict() && scope != varScope) {
                  assert !scope.isFunctionBodyScope() && !scope.isFunctionParameterScope();

                  if (varScope.getExistingSymbol(name) == null && !scope.getParent().isLexicallyDeclaredName(name, true, true)) {
                     function.recordHoistableBlockFunctionDeclaration(varNode, scope);
                  }
               }
            }
         } else {
            ParserContextFunctionNode function = this.lc.getCurrentFunction();
            Scope varScope = function.getBodyScope();
            int symbolFlagsx = varNode.getSymbolFlags() | (varNode.isHoistableDeclaration() ? 256 : 0) | (varScope.isGlobalScope() ? 8 : 0);
            if (function.hasParameterExpressions() && function.getParameterBlock().getScope().hasSymbol(name)) {
               symbolFlagsx |= 4096;
            }

            varScope.putSymbol(new Symbol(varNode.getName().getNameTS(), symbolFlagsx));
            if (scope != varScope) {
               assert scope.isBlockScope();

               function.recordHoistedVarDeclaration(varNode, scope);
            }
         }
      }
   }

   private boolean detectVarNameConflict(Scope scope, VarNode varNode) {
      String varName = varNode.getName().getName();
      if (varNode.isBlockScoped()) {
         Symbol existingSymbol = scope.getExistingSymbol(varName);
         if (existingSymbol != null) {
            return !existingSymbol.isBlockFunctionDeclaration() || this.isStrictMode || !this.isAnnexB() || !varNode.isFunctionDeclaration();
         } else {
            Scope parentScope = scope.getParent();
            if (parentScope != null && (parentScope.isCatchParameterScope() || parentScope.isFunctionParameterScope())) {
               existingSymbol = parentScope.getExistingSymbol(varName);
               if (existingSymbol != null && !existingSymbol.isArguments()) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return scope.isLexicallyDeclaredName(varName, this.isAnnexB(), false);
      }
   }

   private boolean isAnnexB() {
      return this.env.annexB;
   }

   private boolean isIdentifier() {
      return this.type == TokenType.IDENT || this.type.isContextualKeyword() || this.isNonStrictModeIdent();
   }

   private IdentNode identifier(boolean yield, boolean await, String contextString, boolean bindingIdentifier) {
      IdentNode ident = this.getIdent();
      this.verifyIdent(ident, yield, await);
      this.verifyStrictIdent(ident, contextString, bindingIdentifier);
      return ident;
   }

   private IdentNode identifierReference(boolean yield, boolean await) {
      IdentNode ident = this.identifier(yield, await, "IdentifierReference", false);
      this.addIdentifierReference(ident.getName());
      return ident;
   }

   private IdentNode labelIdentifier(boolean yield, boolean await) {
      return this.identifier(yield, await, "LabelIdentifier", false);
   }

   private boolean isBindingIdentifier() {
      return this.type == TokenType.IDENT || this.type.isContextualKeyword() || this.isNonStrictModeIdent();
   }

   private IdentNode bindingIdentifier(boolean yield, boolean await, String contextString) {
      IdentNode ident = this.identifier(yield, await, contextString, true);
      this.addIdentifierReference(ident.getName());
      return ident;
   }

   private void addIdentifierReference(String name) {
      Scope currentScope = this.lc.getCurrentScope();
      if (currentScope != null) {
         currentScope.addIdentifierReference(name);
      }
   }

   private Expression bindingPattern(boolean yield, boolean await) {
      if (this.type == TokenType.LBRACKET) {
         return this.arrayLiteral(yield, await, CoverExpressionError.IGNORE);
      } else if (this.type == TokenType.LBRACE) {
         return this.objectLiteral(yield, await, CoverExpressionError.IGNORE);
      } else {
         throw this.error(AbstractParser.message("expected.binding"));
      }
   }

   private Expression bindingIdentifierOrPattern(boolean yield, boolean await, String contextString) {
      return (Expression)(!this.isBindingIdentifier() && ES6_DESTRUCTURING && this.isES6()
         ? this.bindingPattern(yield, await)
         : this.bindingIdentifier(yield, await, contextString));
   }

   private void verifyDestructuringBindingPattern(Expression pattern, Consumer<IdentNode> identifierCallback) {
      assert pattern instanceof ObjectNode || pattern instanceof LiteralNode.ArrayLiteralNode;

      pattern.accept(new Parser.VerifyDestructuringPatternNodeVisitor(new LexicalContext()) {
         @Override
         protected void verifySpreadElement(Expression lvalue) {
            if (lvalue instanceof IdentNode) {
               this.enterIdentNode((IdentNode)lvalue);
            } else {
               if (!Parser.this.isDestructuringLhs(lvalue)) {
                  throw Parser.this.error("Expected a valid binding identifier", lvalue.getToken());
               }

               Parser.this.verifyDestructuringBindingPattern(lvalue, identifierCallback);
            }
         }

         @Override
         public boolean enterIdentNode(IdentNode identNode) {
            if (identNode.isParenthesized()) {
               throw Parser.this.error("Expected a valid binding identifier", identNode.getToken());
            } else {
               identifierCallback.accept(identNode);
               return false;
            }
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

   private void expressionStatement(boolean yield, boolean await) {
      int expressionLine = this.line;
      long expressionToken = this.token;
      Expression expression = this.expression(yield, await);
      if (expression != null) {
         this.endOfLine();
         ExpressionStatement expressionStatement = new ExpressionStatement(expressionLine, expressionToken, this.finish, expression);
         this.appendStatement(expressionStatement);
      } else {
         this.expect(null);
         this.endOfLine();
      }
   }

   private void ifStatement(boolean yield, boolean await) {
      int ifLine = this.line;
      long ifToken = this.token;
      this.next();
      this.expect(TokenType.LPAREN);
      Expression test = this.expression(yield, await);
      this.expect(TokenType.RPAREN);
      Block pass = this.getStatement(yield, await, false, true, false);
      Block fail = null;
      if (this.type == TokenType.ELSE) {
         this.next();
         fail = this.getStatement(yield, await, false, true, false);
      }

      this.appendStatement(new IfNode(ifLine, ifToken, fail != null ? fail.getFinish() : pass.getFinish(), test, pass, fail));
   }

   private void forStatement(boolean yield, boolean await) {
      long forToken = this.token;
      int forLine = this.line;
      int forStart = Token.descPosition(forToken);
      ParserContextBlockNode outer;
      if (this.useBlockScope()) {
         outer = this.newBlock();
         outer.setFlag(16);
      } else {
         outer = null;
      }

      ParserContextLoopNode forNode = new ParserContextLoopNode();
      this.lc.push(forNode);
      Block body = null;
      Expression init = null;
      JoinPredecessorExpression test = null;
      JoinPredecessorExpression modify = null;
      Parser.ForVariableDeclarationListResult varDeclList = null;
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
            if (!await) {
               throw this.error(AbstractParser.message("invalid.for.await.of"), this.token);
            }

            isForAwaitOf = true;
            this.next();
         }

         TokenType varType;
         label563: {
            this.expect(TokenType.LPAREN);
            varType = null;
            switch (this.type) {
               case SEMICOLON:
                  break label563;
               case VAR:
                  varType = this.type;
                  varDeclList = this.variableDeclarationList(varType, false, yield, await, forStart);
                  break label563;
            }

            if (!this.useBlockScope() || (this.type != TokenType.LET || !this.lookaheadIsLetDeclaration()) && this.type != TokenType.CONST) {
               if (this.env.constAsVar && this.type == TokenType.CONST) {
                  varType = TokenType.VAR;
                  varDeclList = this.variableDeclarationList(varType, false, yield, await, forStart);
               } else {
                  initStartsWithLet = this.type == TokenType.LET;
                  initStartsWithAsyncOf = this.type == TokenType.ASYNC && !isForAwaitOf && this.lookaheadIsOf();
                  initCoverExpr = new CoverExpressionError();
                  init = this.expression(false, yield, await, initCoverExpr);
               }
            } else {
               varType = this.type;
               varDeclList = this.variableDeclarationList(varType, false, yield, await, forStart);
               if (varType == TokenType.LET && !forNode.getStatements().isEmpty()) {
                  flags |= 4;
               }
            }
         }

         label572: {
            switch (this.type) {
               case SEMICOLON:
                  if (varDeclList != null) {
                     assert init == null;

                     init = varDeclList.init;
                     if (varDeclList.missingAssignment != null) {
                        if (varDeclList.missingAssignment instanceof IdentNode) {
                           throw this.error(AbstractParser.message("missing.const.assignment", ((IdentNode)varDeclList.missingAssignment).getName()));
                        }

                        throw this.error(AbstractParser.message("missing.destructuring.assignment"), varDeclList.missingAssignment.getToken());
                     }
                  } else if (init != null) {
                     this.verifyExpression(initCoverExpr);
                  }

                  if ((flags & 2) != 0) {
                     throw this.error(AbstractParser.message("for.each.without.in"), this.token);
                  }

                  this.expect(TokenType.SEMICOLON);
                  if (this.type != TokenType.SEMICOLON) {
                     test = this.joinPredecessorExpression(yield, await);
                  }

                  this.expect(TokenType.SEMICOLON);
                  if (this.type != TokenType.RPAREN) {
                     modify = this.joinPredecessorExpression(yield, await);
                  }
                  break label572;
               case OF:
                  if (!ES8_FOR_AWAIT_OF || !isForAwaitOf || initStartsWithLet) {
                     if (!ES6_FOR_OF || initStartsWithLet || initStartsWithAsyncOf) {
                        this.expect(TokenType.SEMICOLON);
                        break label572;
                     }

                     isForOf = true;
                  }
               case IN:
                  break;
               default:
                  this.expect(TokenType.SEMICOLON);
                  break label572;
            }

            if (isForAwaitOf) {
               this.expectDontAdvance(TokenType.OF);
               flags |= 16;
            } else {
               flags |= isForOf ? 8 : 1;
            }

            test = new JoinPredecessorExpression();
            if (varDeclList != null) {
               if (varDeclList.secondBinding != null) {
                  throw this.error(
                     AbstractParser.message("many.vars.in.for.in.loop", !isForOf && !isForAwaitOf ? "in" : "of"), varDeclList.secondBinding.getToken()
                  );
               }

               if (varDeclList.declarationWithInitializerToken != 0L
                  && (this.isStrictMode || this.type != TokenType.IN || varType != TokenType.VAR || varDeclList.init != null)) {
                  throw this.error(
                     AbstractParser.message("for.in.loop.initializer", !isForOf && !isForAwaitOf ? "in" : "of"), varDeclList.declarationWithInitializerToken
                  );
               }

               init = varDeclList.firstBinding;

               assert init instanceof IdentNode || this.isDestructuringLhs(init);

               if (varType == TokenType.CONST || varType == TokenType.LET) {
                  flags |= 4;
               }
            } else {
               assert init != null : "for..in/of init expression can not be null here";

               if (!this.checkValidLValue(init, !isForOf && !isForAwaitOf ? "for-in iterator" : "for-of iterator")) {
                  throw this.error(AbstractParser.message("not.lvalue.for.in.loop", !isForOf && !isForAwaitOf ? "in" : "of"), init.getToken());
               }
            }

            this.next();
            modify = !isForOf && !isForAwaitOf
               ? this.joinPredecessorExpression(yield, await)
               : new JoinPredecessorExpression(this.assignmentExpression(true, yield, await));
         }

         this.expect(TokenType.RPAREN);
         body = this.getStatement(yield, await);
      } finally {
         this.lc.pop(forNode);
         boolean skipVars = (flags & 4) != 0 && (isForOf || isForAwaitOf || (flags & 1) != 0);
         if (!skipVars) {
            for (Statement var : forNode.getStatements()) {
               assert var instanceof VarNode;

               this.appendStatement(var);
            }
         }

         if (body != null) {
            this.appendStatement(new ForNode(forLine, forToken, body.getFinish(), body, forNode.getFlags() | flags, init, test, modify));
         }

         if (outer != null) {
            this.restoreBlock(outer);
            if (body != null) {
               this.appendStatement(new BlockStatement(forLine, new Block(outer.getToken(), body.getFinish(), 0, outer.getScope(), outer.getStatements())));
            }
         }
      }
   }

   private boolean checkValidLValue(Expression init, String contextString) {
      if (init instanceof IdentNode) {
         IdentNode ident = (IdentNode)init;
         if (!checkIdentLValue(ident)) {
            return false;
         } else if (ident.isMetaProperty()) {
            return false;
         } else {
            this.verifyStrictIdent(ident, contextString);
            return true;
         }
      } else if (init instanceof AccessNode || init instanceof IndexNode) {
         return !((BaseNode)init).isOptional();
      } else if (this.isDestructuringLhs(init)) {
         this.verifyDestructuringAssignmentPattern(init, contextString);
         return true;
      } else {
         return false;
      }
   }

   private boolean lookaheadIsLetDeclaration() {
      return this.lookaheadOfLetDeclaration() != null;
   }

   private TokenType lookaheadOfLetDeclaration() {
      assert this.type == TokenType.LET;

      int i = 1;

      while (true) {
         TokenType t = this.T(this.k + i);
         switch (t) {
            case EOL:
            case COMMENT:
               i++;
               break;
            case LBRACE:
            case IDENT:
            case OF:
            case LBRACKET:
               return t;
            default:
               if (!t.isContextualKeyword() && (this.isStrictMode || !t.isFutureStrict())) {
                  return null;
               }

               return t;
         }
      }
   }

   private boolean lookaheadIsOf() {
      int i = 1;

      while (true) {
         TokenType t = this.T(this.k + i);
         switch (t) {
            case EOL:
            case COMMENT:
               i++;
               break;
            case OF:
               return true;
            default:
               return false;
         }
      }
   }

   private void whileStatement(boolean yield, boolean await) {
      long whileToken = this.token;
      int whileLine = this.line;
      this.next();
      ParserContextLoopNode whileNode = new ParserContextLoopNode();
      this.lc.push(whileNode);
      JoinPredecessorExpression test = null;
      Block body = null;

      try {
         this.expect(TokenType.LPAREN);
         test = this.joinPredecessorExpression(yield, await);
         this.expect(TokenType.RPAREN);
         body = this.getStatement(yield, await);
      } finally {
         this.lc.pop(whileNode);
      }

      if (body != null) {
         this.appendStatement(new WhileNode(whileLine, whileToken, body.getFinish(), false, test, body));
      }
   }

   private void doStatement(boolean yield, boolean await) {
      long doToken = this.token;
      int doLine = 0;
      this.next();
      ParserContextLoopNode doWhileNode = new ParserContextLoopNode();
      this.lc.push(doWhileNode);
      Block body = null;
      JoinPredecessorExpression test = null;

      try {
         body = this.getStatement(yield, await);
         this.expect(TokenType.WHILE);
         this.expect(TokenType.LPAREN);
         doLine = this.line;
         test = this.joinPredecessorExpression(yield, await);
         this.expect(TokenType.RPAREN);
         if (this.type == TokenType.SEMICOLON) {
            this.endOfLine();
         }
      } finally {
         this.lc.pop(doWhileNode);
      }

      this.appendStatement(new WhileNode(doLine, doToken, this.finish, true, test, body));
   }

   private void continueStatement(boolean yield, boolean await) {
      int continueLine = this.line;
      long continueToken = this.token;
      this.nextOrEOL();
      boolean seenEOL = this.type == TokenType.EOL;
      if (seenEOL) {
         this.next();
      }

      ParserContextLabelNode labelNode = null;
      switch (this.type) {
         case EOL:
         default:
            if (!seenEOL) {
               IdentNode ident = this.labelIdentifier(yield, await);
               labelNode = this.lc.findLabel(ident.getName());
               if (labelNode == null) {
                  throw this.error(AbstractParser.message("undefined.label", ident), ident.getToken());
               }
            }
         case EOF:
         case SEMICOLON:
         case RBRACE:
            String labelName = labelNode == null ? null : labelNode.getLabelName();
            ParserContextLoopNode targetNode = this.lc.getContinueTo(labelName);
            if (targetNode == null) {
               throw this.error(AbstractParser.message("illegal.continue.stmt"), continueToken);
            } else {
               this.endOfLine();
               this.appendStatement(new ContinueNode(continueLine, continueToken, this.finish, labelName));
            }
      }
   }

   private void breakStatement(boolean yield, boolean await) {
      int breakLine = this.line;
      long breakToken = this.token;
      this.nextOrEOL();
      boolean seenEOL = this.type == TokenType.EOL;
      if (seenEOL) {
         this.next();
      }

      ParserContextLabelNode labelNode = null;
      switch (this.type) {
         case EOL:
         default:
            if (!seenEOL) {
               IdentNode ident = this.labelIdentifier(yield, await);
               labelNode = this.lc.findLabel(ident.getName());
               if (labelNode == null) {
                  throw this.error(AbstractParser.message("undefined.label", ident), ident.getToken());
               }
            }
         case EOF:
         case SEMICOLON:
         case RBRACE:
            String labelName = labelNode == null ? null : labelNode.getLabelName();
            ParserContextBreakableNode targetNode = this.lc.getBreakable(labelName);
            if (targetNode == null) {
               throw this.error(AbstractParser.message("illegal.break.stmt"), breakToken);
            } else {
               this.endOfLine();
               this.appendStatement(new BreakNode(breakLine, breakToken, this.finish, labelName));
            }
      }
   }

   private void returnStatement(boolean yield, boolean await) {
      ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
      if (!currentFunction.isScriptOrModule() && !currentFunction.isClassStaticBlock()) {
         int returnLine = this.line;
         long returnToken = this.token;
         this.nextOrEOL();
         boolean seenEOL = this.type == TokenType.EOL;
         if (seenEOL) {
            this.next();
         }

         Expression expression = null;
         switch (this.type) {
            case EOL:
            default:
               if (!seenEOL) {
                  expression = this.expression(yield, await);
               }
            case EOF:
            case SEMICOLON:
            case RBRACE:
               this.endOfLine();
               this.appendStatement(new ReturnNode(returnLine, returnToken, this.finish, expression));
         }
      } else {
         throw this.error(AbstractParser.message("invalid.return"));
      }
   }

   private Expression yieldExpression(boolean in, boolean await) {
      assert this.isES6();

      long yieldToken = this.token;

      assert this.type == TokenType.YIELD;

      if (this.inFormalParameterList()) {
         throw this.error(AbstractParser.message("unexpected.token", this.type.getNameOrType()));
      } else {
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
            case COLON:
               if (!yieldAsterisk) {
                  expression = newUndefinedLiteral(yieldToken, this.finish);
                  if (this.type == TokenType.EOL) {
                     this.next();
                  }
                  break;
               }
            default:
               expression = this.assignmentExpression(in, true, await);
         }

         return new UnaryNode(yieldToken, expression);
      }
   }

   private Expression awaitExpression(boolean yield) {
      assert this.isAwait();

      long awaitToken = this.token;
      ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
      if (!currentFunction.isClassStaticBlock() && !this.inFormalParameterList()) {
         this.recordYieldOrAwait();
         this.next();
         Expression expression = this.unaryExpression(yield, true, CoverExpressionError.DENY);
         if (this.isModule && currentFunction.isModule()) {
            currentFunction.setFlag(33554432);
         }

         return new UnaryNode(Token.recast(awaitToken, TokenType.AWAIT), expression);
      } else {
         throw this.error(AbstractParser.message("unexpected.token", this.type.getNameOrType()));
      }
   }

   private static UnaryNode newUndefinedLiteral(long token, int finish) {
      return new UnaryNode(Token.recast(token, TokenType.VOID), LiteralNode.newInstance(token, finish, 0));
   }

   private void recordYieldOrAwait() {
      long yieldOrAwaitToken = this.token;

      assert Token.descType(yieldOrAwaitToken) == TokenType.YIELD || Token.descType(yieldOrAwaitToken) == TokenType.AWAIT;

      this.recordYieldOrAwait(yieldOrAwaitToken, false);
   }

   private void recordYieldOrAwait(IdentNode ident) {
      this.recordYieldOrAwait(ident.getToken(), true);
   }

   private void recordYieldOrAwait(long yieldOrAwaitToken, boolean ident) {
      Iterator<ParserContextFunctionNode> iterator = this.lc.getFunctions();

      while (iterator.hasNext()) {
         ParserContextFunctionNode fn = iterator.next();
         if (!fn.isCoverArrowHead()) {
            break;
         }

         if ((!ident || fn.isAsync()) && fn.getYieldOrAwaitInParameters() == 0L) {
            fn.setYieldOrAwaitInParameters(yieldOrAwaitToken);
         }
      }
   }

   private void withStatement(boolean yield, boolean await) {
      int withLine = this.line;
      long withToken = this.token;
      this.next();
      if (this.isStrictMode) {
         throw this.error(AbstractParser.message("strict.no.with"), withToken);
      } else {
         this.expect(TokenType.LPAREN);
         Expression expression = this.expression(yield, await);
         this.expect(TokenType.RPAREN);
         Block body = this.getStatement(yield, await);
         this.appendStatement(new WithNode(withLine, withToken, this.finish, expression, body));
      }
   }

   private void switchStatement(boolean yield, boolean await) {
      int switchLine = this.line;
      long switchToken = this.token;
      ParserContextBlockNode outerBlock;
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
      ArrayList<CaseNode> cases = new ArrayList<>();
      SwitchNode switchStatement = null;

      try {
         this.expect(TokenType.LPAREN);
         int expressionLine = this.line;
         Expression expression = this.expression(yield, await);
         this.expect(TokenType.RPAREN);
         this.expect(TokenType.LBRACE);
         if (this.useBlockScope()) {
            IdentNode switchExprName = new IdentNode(
               Token.recast(expression.getToken(), TokenType.IDENT), expression.getFinish(), this.lexer.stringIntern(SWITCH_BINDING_NAME)
            );
            VarNode varNode = new VarNode(
               expressionLine, Token.recast(expression.getToken(), TokenType.LET), expression.getFinish(), switchExprName, expression, 1
            );
            outerBlock.appendStatement(varNode);
            this.declareVar(outerBlock.getScope(), varNode);
            expression = switchExprName;
         }

         while (this.type != TokenType.RBRACE) {
            Expression caseExpression = null;
            long caseToken = this.token;
            switch (this.type) {
               case CASE:
                  this.next();
                  caseExpression = this.expression(yield, await);
                  break;
               case DEFAULT:
                  if (defaultCaseIndex != -1) {
                     throw this.error(AbstractParser.message("duplicate.default.in.switch"));
                  }

                  this.next();
                  break;
               default:
                  this.expect(TokenType.CASE);
            }

            this.expect(TokenType.COLON);
            List<Statement> statements = this.caseStatementList(yield, await);
            CaseNode caseNode = new CaseNode(caseToken, this.finish, caseExpression, statements);
            if (caseExpression == null) {
               assert defaultCaseIndex == -1;

               defaultCaseIndex = cases.size();
            }

            cases.add(caseNode);
         }

         this.next();
         switchStatement = new SwitchNode(switchLine, switchToken, this.finish, expression, cases, defaultCaseIndex);
      } finally {
         this.lc.pop(switchNode);
         this.restoreBlock(switchBlock);
         if (switchStatement != null) {
            this.appendStatement(
               new BlockStatement(
                  switchLine, new Block(switchToken, switchStatement.getFinish(), switchBlock.getFlags(), switchBlock.getScope(), List.of(switchStatement))
               )
            );
         }

         if (outerBlock != null) {
            this.restoreBlock(outerBlock);
            if (switchStatement != null) {
               this.appendStatement(
                  new BlockStatement(
                     switchLine, new Block(switchToken, switchStatement.getFinish(), outerBlock.getFlags(), outerBlock.getScope(), outerBlock.getStatements())
                  )
               );
            }
         }
      }
   }

   private void labelStatement(boolean yield, boolean await, boolean mayBeFunctionDeclaration) {
      long labelToken = this.token;
      IdentNode ident = this.labelIdentifier(yield, await);
      this.expect(TokenType.COLON);
      if (this.lc.findLabel(ident.getName()) != null) {
         throw this.error(AbstractParser.message("duplicate.label", ident), labelToken);
      } else {
         ParserContextLabelNode labelNode = new ParserContextLabelNode(ident.getName());
         Block body = null;

         try {
            this.lc.push(labelNode);
            body = this.getStatement(yield, await, true, mayBeFunctionDeclaration);
         } finally {
            this.lc.pop(labelNode);
         }

         this.appendStatement(new LabelNode(this.line, labelToken, this.finish, ident.getName(), body));
      }
   }

   private void throwStatement(boolean yield, boolean await) {
      int throwLine = this.line;
      long throwToken = this.token;
      this.nextOrEOL();
      Expression expression = null;
      switch (this.type) {
         default:
            expression = this.expression(yield, await);
         case EOL:
         case SEMICOLON:
         case RBRACE:
            if (expression == null) {
               throw this.error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
            } else {
               this.endOfLine();
               this.appendStatement(new ThrowNode(throwLine, throwToken, this.finish, expression, false));
            }
      }
   }

   private void tryStatement(boolean yield, boolean await) {
      int tryLine = this.line;
      long tryToken = this.token;
      this.next();
      int startLine = this.line;
      ParserContextBlockNode outer = this.newBlock();

      try {
         Block tryBody = this.getBlock(yield, await, true);
         ArrayList<Block> catchBlocks = new ArrayList<>();

         while (this.type == TokenType.CATCH) {
            int catchLine = this.line;
            long catchToken = this.token;
            this.next();
            boolean optionalCatchBinding = this.type == TokenType.LBRACE && ES2019_OPTIONAL_CATCH_BINDING;
            if (!optionalCatchBinding) {
               this.expect(TokenType.LPAREN);
            }

            ParserContextBlockNode catchBlock = this.newBlock(Scope.createCatchParameter(this.lc.getCurrentScope()));

            Expression ifExpression;
            try {
               IdentNode exception;
               Expression pattern;
               if (optionalCatchBinding) {
                  exception = null;
                  pattern = null;
                  ifExpression = null;
               } else {
                  if (!this.isBindingIdentifier() && ES6_DESTRUCTURING && this.isES6()) {
                     pattern = this.bindingPattern(yield, await);
                     exception = new IdentNode(
                           Token.recast(pattern.getToken(), TokenType.IDENT), pattern.getFinish(), this.lexer.stringIntern(ERROR_BINDING_NAME)
                        )
                        .setIsCatchParameter();
                  } else {
                     pattern = null;
                     IdentNode catchParameter = this.bindingIdentifier(yield, await, "catch parameter");
                     exception = catchParameter.setIsCatchParameter();
                  }

                  if (this.env.syntaxExtensions && this.type == TokenType.IF) {
                     this.next();
                     ifExpression = this.expression(yield, await);
                  } else {
                     ifExpression = null;
                  }

                  this.expect(TokenType.RPAREN);
               }

               CatchNode catchNode = this.catchBody(yield, await, catchToken, catchLine, exception, pattern, ifExpression);
               this.appendStatement(catchNode);
            } finally {
               this.restoreBlock(catchBlock);
            }

            int var29 = Math.max(this.finish, Token.descPosition(catchBlock.getToken()));
            Block var30 = new Block(catchBlock.getToken(), var29, catchBlock.getFlags() | 16, catchBlock.getScope(), catchBlock.getStatements());
            catchBlocks.add(var30);
            if (ifExpression == null) {
               break;
            }
         }

         Block finallyStatements = null;
         if (this.type == TokenType.FINALLY) {
            this.next();
            finallyStatements = this.getBlock(yield, await, true);
         }

         if (catchBlocks.isEmpty() && finallyStatements == null) {
            throw this.error(AbstractParser.message("missing.catch.or.finally"), tryToken);
         }

         TryNode tryNode = new TryNode(tryLine, tryToken, this.finish, tryBody, catchBlocks, finallyStatements);

         assert this.lc.peek() == outer;

         this.appendStatement(tryNode);
      } finally {
         this.restoreBlock(outer);
      }

      this.appendStatement(new BlockStatement(startLine, new Block(tryToken, this.finish, outer.getFlags() | 16, outer.getScope(), outer.getStatements())));
   }

   private CatchNode catchBody(boolean yield, boolean await, long catchToken, int catchLine, IdentNode exception, Expression pattern, Expression ifExpression) {
      if (exception != null) {
         final Scope catchScope = this.lc.getCurrentScope();

         assert catchScope.isCatchParameterScope();

         VarNode exceptionVar = new VarNode(
            catchLine, Token.recast(exception.getToken(), TokenType.LET), exception.getFinish(), exception.setIsDeclaredHere(), null, 1
         );
         this.appendStatement(exceptionVar);
         this.declareVar(catchScope, exceptionVar);
         if (pattern != null) {
            this.verifyDestructuringBindingPattern(
               pattern,
               new Consumer<IdentNode>() {
                  public void accept(IdentNode identNode) {
                     Parser.this.verifyStrictIdent(identNode, "catch parameter");
                     int varFlags = 17;
                     VarNode var = new VarNode(
                        catchLine, Token.recast(identNode.getToken(), TokenType.LET), identNode.getFinish(), identNode.setIsDeclaredHere(), null, 17
                     );
                     Parser.this.appendStatement(var);
                     Parser.this.declareVar(catchScope, var);
                  }
               }
            );
         }
      }

      Block catchBody = this.getBlock(yield, await, true);
      return new CatchNode(catchLine, catchToken, this.finish, exception, pattern, ifExpression, catchBody, false);
   }

   private void debuggerStatement() {
      int debuggerLine = this.line;
      long debuggerToken = this.token;
      this.next();
      this.endOfLine();
      this.appendStatement(new DebuggerNode(debuggerLine, debuggerToken, this.finish));
   }

   private Expression primaryExpression(boolean yield, boolean await, CoverExpressionError coverExpression) {
      int primaryLine = this.line;
      long primaryToken = this.token;
      switch (this.type) {
         case LBRACE:
            return this.objectLiteral(yield, await, coverExpression);
         case VAR:
         case IF:
         case FOR:
         case WHILE:
         case DO:
         case CONTINUE:
         case BREAK:
         case RETURN:
         case WITH:
         case SWITCH:
         case THROW:
         case TRY:
         case DEBUGGER:
         case RPAREN:
         case RBRACKET:
         case FUNCTION:
         case LET:
         case CONST:
         case CLASS:
         case AT:
         case ASYNC:
         case CASE:
         case DEFAULT:
         case OF:
         case IN:
         case COMMENT:
         case COMMARIGHT:
         case COLON:
         default:
            if (this.lexer.scanLiteral(primaryToken, this.type, this.lineInfoReceiver)) {
               this.next();
               return this.getLiteral();
            }

            if (this.type.isContextualKeyword() || this.isNonStrictModeIdent()) {
               return this.identifierReference(yield, await);
            }
            break;
         case LPAREN:
            return this.parenthesizedExpressionAndArrowParameterList(yield, await);
         case IDENT:
            IdentNode ident = this.identifierReference(yield, await);
            if (ident != null) {
               return this.detectSpecialProperty(ident);
            }
            break;
         case NON_OCTAL_DECIMAL:
            if (this.isStrictMode) {
               throw this.error(AbstractParser.message("strict.no.nonoctaldecimal"), this.token);
            }
         case OCTAL_LEGACY:
            if (this.isStrictMode) {
               throw this.error(AbstractParser.message("strict.no.octal"), this.token);
            }

            return this.getLiteral();
         case STRING:
         case ESCSTRING:
         case DECIMAL:
         case HEXADECIMAL:
         case OCTAL:
         case BINARY_NUMBER:
         case BIGINT:
         case FLOATING:
         case REGEX:
         case XML:
            return this.getLiteral();
         case LBRACKET:
            return this.arrayLiteral(yield, await, coverExpression);
         case THIS:
            TruffleString name = this.type.getNameTS();
            this.next();
            this.markThis();
            return new IdentNode(primaryToken, this.finish, this.lexer.stringIntern(name)).setIsThis();
         case EXECSTRING:
            return this.execString(primaryLine, primaryToken);
         case FALSE:
            this.next();
            return LiteralNode.newInstance(primaryToken, this.finish, false);
         case TRUE:
            this.next();
            return LiteralNode.newInstance(primaryToken, this.finish, true);
         case NULL:
            this.next();
            return LiteralNode.newInstance(primaryToken, this.finish);
         case TEMPLATE:
         case TEMPLATE_HEAD:
            return this.templateLiteral(yield, await);
      }

      throw this.error(AbstractParser.message("expected.operand", this.type.getNameOrType()));
   }

   private boolean isPrivateFieldsIn() {
      return this.env.privateFieldsIn;
   }

   private Expression execString(final int primaryLine, final long primaryToken) {
      IdentNode execIdent = new IdentNode(primaryToken, this.finish, this.lexer.stringIntern(EXEC_NAME));
      this.next();
      this.expect(TokenType.LBRACE);
      List<Expression> arguments = List.of(this.expression(false, false));
      this.expect(TokenType.RBRACE);
      long tokenWithDelimiter = Token.withDelimiter(primaryToken);
      return CallNode.forCall(primaryLine, tokenWithDelimiter, Token.descPosition(tokenWithDelimiter), this.finish, execIdent, arguments);
   }

   private LiteralNode<Expression[]> arrayLiteral(boolean yield, boolean await, CoverExpressionError coverExpression) {
      long arrayToken = this.token;
      this.next();
      ArrayList<Expression> elements = new ArrayList<>();
      boolean elision = true;
      boolean hasSpread = false;

      while (true) {
         long spreadToken = 0L;
         switch (this.type) {
            case RBRACKET:
               this.next();
               return LiteralNode.newInstance(arrayToken, this.finish, elements, hasSpread, elision);
            case COMMARIGHT:
               this.next();
               if (elision) {
                  elements.add(null);
               }

               elision = true;
               break;
            case ELLIPSIS:
               if (ES6_SPREAD_ARRAY) {
                  hasSpread = true;
                  spreadToken = this.token;
                  this.next();
               }
            default:
               if (!elision) {
                  throw this.error(AbstractParser.message("expected.comma", this.type.getNameOrType()));
               }

               Expression expression = this.assignmentExpression(true, yield, await, coverExpression);
               if (expression != null) {
                  if (spreadToken != 0L) {
                     expression = new UnaryNode(Token.recast(spreadToken, TokenType.SPREAD_ARRAY), expression);
                  }

                  elements.add(expression);
               } else {
                  this.expect(TokenType.RBRACKET);
               }

               elision = false;
         }
      }
   }

   private ObjectNode objectLiteral(boolean yield, boolean await, CoverExpressionError coverExpression) {
      long objectToken = this.token;
      this.next();
      ArrayList<PropertyNode> elements = new ArrayList<>();
      Map<String, PropertyNode> propertyNameMapES5 = this.isES6() ? null : new HashMap<>();
      boolean commaSeen = true;
      boolean hasDuplicateProto = false;
      boolean hasProto = false;

      while (true) {
         switch (this.type) {
            case RBRACE:
               this.next();
               return new ObjectNode(objectToken, this.finish, elements);
            case COMMARIGHT:
               if (commaSeen) {
                  throw this.error(AbstractParser.message("expected.property.id", this.type.getNameOrType()));
               }

               this.next();
               commaSeen = true;
               break;
            default:
               if (!commaSeen) {
                  throw this.error(AbstractParser.message("expected.comma", this.type.getNameOrType()));
               }

               commaSeen = false;
               PropertyNode property = this.propertyDefinition(yield, await, coverExpression);
               elements.add(property);
               hasDuplicateProto = hasProto && property.isProto();
               hasProto = hasProto || property.isProto();
               if (!property.isComputed() && !property.getKey().isTokenType(TokenType.SPREAD_OBJECT)) {
                  if (this.isES6()) {
                     if (hasDuplicateProto) {
                        this.recordOrThrowExpressionError("multiple.proto.key", property.getToken(), coverExpression);
                     }
                  } else {
                     this.checkES5PropertyDefinition(property, propertyNameMapES5);
                  }
               }
         }
      }
   }

   private void checkES5PropertyDefinition(PropertyNode property, Map<String, PropertyNode> map) {
      String key = property.getKeyName();
      PropertyNode existingProperty = map.get(key);
      if (existingProperty == null) {
         map.put(key, property);
      } else {
         Expression value = property.getValue();
         FunctionNode getter = property.getGetter();
         FunctionNode setter = property.getSetter();
         Expression prevValue = existingProperty.getValue();
         FunctionNode prevGetter = existingProperty.getGetter();
         FunctionNode prevSetter = existingProperty.getSetter();
         this.checkPropertyRedefinition(property, value, getter, setter, prevValue, prevGetter, prevSetter);
         if (value == null && prevValue == null) {
            if (getter != null) {
               assert prevGetter != null || prevSetter != null;

               map.put(key, existingProperty.setGetter(getter));
            } else if (setter != null) {
               assert prevGetter != null || prevSetter != null;

               map.put(key, existingProperty.setSetter(setter));
            }
         }
      }
   }

   private void checkPropertyRedefinition(
      final PropertyNode property,
      final Expression value,
      final FunctionNode getter,
      final FunctionNode setter,
      final Expression prevValue,
      final FunctionNode prevGetter,
      final FunctionNode prevSetter
   ) {
      if (this.isStrictMode && value != null && prevValue != null) {
         throw this.error(AbstractParser.message("property.redefinition", property.getKeyName()), property.getToken());
      } else {
         boolean isPrevAccessor = prevGetter != null || prevSetter != null;
         boolean isAccessor = getter != null || setter != null;
         if (prevValue != null && isAccessor) {
            throw this.error(AbstractParser.message("property.redefinition", property.getKeyName()), property.getToken());
         } else if (isPrevAccessor && value != null) {
            throw this.error(AbstractParser.message("property.redefinition", property.getKeyName()), property.getToken());
         } else if (isAccessor && isPrevAccessor && (getter != null && prevGetter != null || setter != null && prevSetter != null)) {
            throw this.error(AbstractParser.message("property.redefinition", property.getKeyName()), property.getToken());
         }
      }
   }

   private PropertyKey literalPropertyName() {
      switch (this.type) {
         case IDENT:
            return this.getIdent().setIsPropertyName();
         case NON_OCTAL_DECIMAL:
            if (this.isStrictMode) {
               throw this.error(AbstractParser.message("strict.no.nonoctaldecimal"), this.token);
            }
         case OCTAL_LEGACY:
            if (this.isStrictMode) {
               throw this.error(AbstractParser.message("strict.no.octal"), this.token);
            }
         case STRING:
         case ESCSTRING:
         case DECIMAL:
         case HEXADECIMAL:
         case OCTAL:
         case BINARY_NUMBER:
         case BIGINT:
         case FLOATING:
            return (PropertyKey)this.getLiteral();
         default:
            return this.getIdentifierName().setIsPropertyName();
      }
   }

   private Expression computedPropertyName(boolean yield, boolean await) {
      this.expect(TokenType.LBRACKET);
      Expression expression = this.assignmentExpression(true, yield, await);
      this.expect(TokenType.RBRACKET);
      return expression;
   }

   private Expression propertyName(boolean yield, boolean await) {
      return ES6_COMPUTED_PROPERTY_NAME && this.type == TokenType.LBRACKET && this.isES6()
         ? this.computedPropertyName(yield, await)
         : (Expression)this.literalPropertyName();
   }

   private PropertyNode propertyDefinition(boolean yield, boolean await, CoverExpressionError coverExpression) {
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

      boolean computed = this.type == TokenType.LBRACKET;
      Expression propertyName;
      boolean isIdentifier;
      if (this.type != TokenType.IDENT && (!this.isIdentifier() || this.type == TokenType.GET || this.type == TokenType.SET)) {
         if (this.type != TokenType.GET && this.type != TokenType.SET) {
            if (this.type == TokenType.ELLIPSIS && ES8_REST_SPREAD_PROPERTY && this.isES2017() && !generator && !async) {
               long spreadToken = Token.recast(propertyToken, TokenType.SPREAD_OBJECT);
               this.next();
               Expression assignmentExpression = this.assignmentExpression(true, yield, await);
               Expression spread = new UnaryNode(spreadToken, assignmentExpression);
               return new PropertyNode(propertyToken, this.finish, spread, null, null, null, false, false, false, false);
            }

            isIdentifier = false;
            propertyName = this.propertyName(yield, await);
         } else {
            TokenType getOrSet = this.type;
            this.next();
            if (this.type != TokenType.COLON
               && this.type != TokenType.COMMARIGHT
               && this.type != TokenType.RBRACE
               && (this.type != TokenType.ASSIGN && this.type != TokenType.LPAREN || !this.isES6())) {
               if (getOrSet == TokenType.GET) {
                  Parser.PropertyFunction getter = this.propertyGetterFunction(propertyToken, functionLine, yield, await, false);
                  return new PropertyNode(propertyToken, this.finish, getter.key, null, getter.functionNode, null, false, getter.computed, false, false);
               }

               if (getOrSet == TokenType.SET) {
                  Parser.PropertyFunction setter = this.propertySetterFunction(propertyToken, functionLine, yield, await, false);
                  return new PropertyNode(propertyToken, this.finish, setter.key, null, null, setter.functionNode, false, setter.computed, false, false);
               }
            }

            isIdentifier = true;
            propertyName = new IdentNode(propertyToken, this.finish, this.lexer.stringIntern(getOrSet.getNameTS())).setIsPropertyName();
         }
      } else {
         isIdentifier = true;
         propertyName = this.getIdent().setIsPropertyName();
      }

      if (generator || async) {
         this.expectDontAdvance(TokenType.LPAREN);
      }

      boolean coverInitializedName = false;
      boolean proto = false;
      boolean isAnonymousFunctionDefinition = false;
      Expression propertyValue;
      if (this.type == TokenType.LPAREN && this.isES6()) {
         propertyValue = this.propertyMethodFunction(propertyName, propertyToken, functionLine, generator, 1048576, computed, async).functionNode;
      } else if (isIdentifier && (this.type == TokenType.COMMARIGHT || this.type == TokenType.RBRACE || this.type == TokenType.ASSIGN) && this.isES6()) {
         IdentNode ident = (IdentNode)propertyName;
         this.verifyIdent(ident, yield, await);
         ident = this.createIdentNode(propertyToken, this.finish, ident.getPropertyNameTS());
         if (this.type == TokenType.ASSIGN && ES6_DESTRUCTURING) {
            long assignToken = this.token;
            this.recordOrThrowExpressionError("invalid.property.initializer", assignToken, coverExpression);
            coverInitializedName = true;
            this.next();
            Expression rhs = this.assignmentExpression(true, yield, await);
            propertyValue = this.verifyAssignment(assignToken, ident, rhs, true);
         } else {
            propertyValue = this.detectSpecialProperty(ident);
         }

         this.addIdentifierReference(ident.getName());
      } else {
         this.expect(TokenType.COLON);
         if (!computed && "__proto__".equals(((PropertyKey)propertyName).getPropertyName())) {
            proto = true;
         }

         this.pushDefaultName(propertyName);

         try {
            propertyValue = this.assignmentExpression(true, yield, await, coverExpression);
         } finally {
            this.popDefaultName();
         }

         if (!proto && isAnonymousFunctionDefinition(propertyValue)) {
            if (!computed && propertyName instanceof PropertyKey) {
               propertyValue = this.setAnonymousFunctionName(propertyValue, ((PropertyKey)propertyName).getPropertyNameTS());
            } else {
               isAnonymousFunctionDefinition = true;
            }
         }
      }

      return new PropertyNode(
         propertyToken,
         this.finish,
         propertyName,
         propertyValue,
         null,
         null,
         false,
         computed,
         coverInitializedName,
         proto,
         false,
         isAnonymousFunctionDefinition
      );
   }

   private Parser.PropertyFunction propertyGetterFunction(long getSetToken, int functionLine, boolean yield, boolean await, boolean allowPrivate) {
      boolean computed = this.type == TokenType.LBRACKET;
      Expression propertyName = this.classElementName(yield, await, allowPrivate);
      IdentNode getterName = computed ? null : this.createMethodNameIdent(propertyName, "get ");
      this.expect(TokenType.LPAREN);
      this.expect(TokenType.RPAREN);
      int functionFlags = 1050624 | (computed ? 1 : 0);
      ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(getterName, getSetToken, functionFlags, functionLine, List.of(), 0);
      this.lc.push(functionNode);

      Block functionBody;
      try {
         functionBody = this.functionBody(functionNode);
      } finally {
         this.lc.pop(functionNode);
      }

      FunctionNode function = this.createFunctionNode(functionNode, getSetToken, getterName, functionLine, functionBody);
      return new Parser.PropertyFunction(propertyName, function, computed);
   }

   private Parser.PropertyFunction propertySetterFunction(long getSetToken, int functionLine, boolean yield, boolean await, boolean allowPrivate) {
      boolean computed = this.type == TokenType.LBRACKET;
      Expression propertyName = this.classElementName(yield, await, allowPrivate);
      IdentNode setterName = computed ? null : this.createMethodNameIdent(propertyName, "set ");
      this.expect(TokenType.LPAREN);
      int functionFlags = 1052672 | (computed ? 1 : 0);
      ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(setterName, getSetToken, functionFlags, functionLine);
      this.lc.push(functionNode);

      Block functionBody;
      try {
         ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
         this.lc.push(parameterBlock);

         try {
            if (!this.env.syntaxExtensions || this.type != TokenType.RPAREN) {
               this.formalParameter(false, false);
            }

            this.expect(TokenType.RPAREN);
            functionBody = this.functionBody(functionNode);
         } finally {
            this.restoreBlock(parameterBlock);
         }

         if (parameterBlock != null) {
            functionBody = wrapParameterBlock(parameterBlock, functionBody);
         }
      } finally {
         this.lc.pop(functionNode);
      }

      FunctionNode var22 = this.createFunctionNode(functionNode, getSetToken, setterName, functionLine, functionBody);
      return new Parser.PropertyFunction(propertyName, var22, computed);
   }

   private Parser.PropertyFunction propertyMethodFunction(
      Expression key, final long methodToken, final int methodLine, final boolean generator, final int flags, boolean computed, boolean async
   ) {
      IdentNode methodNameNode = computed ? null : this.createMethodNameIdent(key, "");
      this.expect(TokenType.LPAREN);
      int functionFlags = flags | (computed ? 1 : 0) | (generator ? 16777216 : 0) | (async ? 33554432 : 0);
      ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(methodNameNode, methodToken, functionFlags, methodLine);
      this.lc.push(functionNode);

      Parser.PropertyFunction var15;
      try {
         ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
         this.lc.push(parameterBlock);

         Block functionBody;
         try {
            this.formalParameterList(generator, async);
            this.expect(TokenType.RPAREN);
            functionBody = this.functionBody(functionNode);
         } finally {
            this.restoreBlock(parameterBlock);
         }

         this.verifyParameterList(functionNode);
         if (parameterBlock != null) {
            functionBody = wrapParameterBlock(parameterBlock, functionBody);
         }

         FunctionNode function = this.createFunctionNode(functionNode, methodToken, methodNameNode, methodLine, functionBody);
         var15 = new Parser.PropertyFunction(key, function, computed);
      } finally {
         this.lc.pop(functionNode);
      }

      return var15;
   }

   private IdentNode createMethodNameIdent(Expression propertyKey, String prefix) {
      TruffleString methodName;
      if (propertyKey instanceof IdentNode) {
         methodName = ((IdentNode)propertyKey).getPropertyNameTS();
      } else {
         if (!(propertyKey instanceof PropertyKey)) {
            return null;
         }

         methodName = this.lexer.stringIntern(((PropertyKey)propertyKey).getPropertyNameTS());
      }

      if (!prefix.isEmpty()) {
         methodName = this.lexer.stringIntern(prefix + methodName.toJavaStringUncached());
      }

      return this.createIdentNode(propertyKey.getToken(), propertyKey.getFinish(), methodName);
   }

   private static boolean isAnonymousFunctionDefinition(Expression expression) {
      return expression instanceof FunctionNode && ((FunctionNode)expression).isAnonymous()
         ? true
         : expression instanceof ClassNode && ((ClassNode)expression).isAnonymous();
   }

   private Expression setAnonymousFunctionName(Expression expression, TruffleString functionName) {
      if (!this.isES6()) {
         return expression;
      } else if (expression instanceof FunctionNode && ((FunctionNode)expression).isAnonymous()) {
         return ((FunctionNode)expression).setName(null, functionName);
      } else if (expression instanceof ClassNode && ((ClassNode)expression).isAnonymous()) {
         ClassNode classNode = (ClassNode)expression;
         FunctionNode constructorFunction = (FunctionNode)classNode.getConstructor().getValue();
         return classNode.setConstructor(classNode.getConstructor().setValue(constructorFunction.setName(null, functionName)));
      } else {
         return expression;
      }
   }

   private Expression leftHandSideExpression(boolean yield, boolean await, CoverExpressionError coverExpression) {
      int callLine = this.line;
      long callToken = this.token;
      Expression lhs = this.memberExpression(yield, await, coverExpression);
      if (this.type == TokenType.LPAREN) {
         boolean async = ES8_ASYNC_FUNCTION && this.isES2017() && lhs.isTokenType(TokenType.ASYNC) && this.lookbehindNoLineTerminatorAfterAsync();
         List<Expression> arguments = this.argumentList(yield, await, async, callToken, callLine);
         if (async && this.type == TokenType.ARROW && this.lookbehindNoLineTerminatorBeforeArrow()) {
            return new ExpressionList(callToken, callLine, arguments);
         }

         boolean eval = false;
         boolean applyArguments = false;
         if (lhs instanceof IdentNode) {
            IdentNode ident = (IdentNode)lhs;
            String name = ident.getName();
            if ("eval".equals(name)) {
               this.markEval();
               eval = true;
            } else if (TokenType.SUPER.getName().equals(name)) {
               assert ident.isDirectSuper();

               this.markSuperCall();
            }
         } else if (lhs instanceof AccessNode
            && !((AccessNode)lhs).isPrivate()
            && arguments.size() == 2
            && arguments.get(1) instanceof IdentNode
            && ((IdentNode)arguments.get(1)).isArguments()
            && "apply".equals(((AccessNode)lhs).getProperty())
            && markApplyArgumentsCall(this.lc, arguments)) {
            applyArguments = true;
         }

         lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, arguments, false, false, eval, applyArguments, false);
      }

      boolean optionalChain = false;

      while (true) {
         callLine = this.line;
         callToken = this.token;
         switch (this.type) {
            case LPAREN:
               List<Expression> argumentsx = this.argumentList(yield, await);
               lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, argumentsx, false, optionalChain);
               break;
            case LBRACKET:
               this.next();
               Expression rhs = this.expression(true, yield, await);
               this.expect(TokenType.RBRACKET);
               lhs = new IndexNode(callToken, this.finish, lhs, rhs, false, false, optionalChain);
               break;
            case TEMPLATE:
            case TEMPLATE_HEAD:
               if (optionalChain) {
                  throw this.error(AbstractParser.message("optional.chain.template"));
               }

               List<Expression> argumentsxx = this.templateLiteralArgumentList(yield, await);
               lhs = CallNode.forTaggedTemplateLiteral(callLine, callToken, lhs.getStart(), this.finish, lhs, argumentsxx);
               break;
            case PERIOD:
               this.next();
               boolean isPrivate = this.type == TokenType.PRIVATE_IDENT;
               IdentNode property;
               if (isPrivate) {
                  property = this.privateIdentifierUse();
               } else {
                  property = this.getIdentifierName();
               }

               lhs = new AccessNode(callToken, this.finish, lhs, property.getNameTS(), false, isPrivate, false, optionalChain);
               break;
            case OPTIONAL_CHAIN:
               this.next();
               optionalChain = true;
               switch (this.type) {
                  case LPAREN:
                     List<Expression> argumentsxx = this.argumentList(yield, await);
                     lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, argumentsxx, true, optionalChain);
                     continue;
                  case LBRACKET:
                     this.next();
                     Expression rhsx = this.expression(true, yield, await);
                     this.expect(TokenType.RBRACKET);
                     lhs = new IndexNode(callToken, this.finish, lhs, rhsx, false, true, optionalChain);
                     continue;
                  default:
                     boolean isPrivate = this.type == TokenType.PRIVATE_IDENT;
                     IdentNode property;
                     if (isPrivate) {
                        property = this.privateIdentifierUse();
                     } else {
                        property = this.getIdentifierName();
                     }

                     lhs = new AccessNode(callToken, this.finish, lhs, property.getNameTS(), false, isPrivate, true, optionalChain);
                     continue;
               }
            default:
               return lhs;
         }
      }
   }

   private Expression newExpression(boolean yield, boolean await) {
      long newToken = this.token;

      assert this.type == TokenType.NEW;

      this.next();
      if (ES6_NEW_TARGET && this.type == TokenType.PERIOD && this.isES6()) {
         this.next();
         if (this.type == TokenType.IDENT && TARGET.equals(this.getValueNoEscape())) {
            this.next();
            this.markNewTarget();
            return new IdentNode(newToken, this.finish, this.lexer.stringIntern(NEW_TARGET_NAME)).setIsNewTarget();
         } else {
            throw this.error(AbstractParser.message("expected.target"), this.token);
         }
      } else if (this.type == TokenType.IMPORT && this.isES2020() && this.lookahead() == TokenType.LPAREN) {
         throw this.error(AbstractParser.message("expected.operand", TokenType.IMPORT.getName()), this.token);
      } else {
         int callLine = this.line;
         Expression constructor = this.memberExpression(yield, await, CoverExpressionError.DENY);
         List<Expression> arguments;
         if (this.type == TokenType.LPAREN) {
            arguments = this.argumentList(yield, await);
         } else {
            arguments = new ArrayList<>();
            if (this.type == TokenType.OPTIONAL_CHAIN) {
               throw this.error(AbstractParser.message("unexpected.token", this.type.getNameOrType()));
            }
         }

         if (this.env.syntaxExtensions && this.type == TokenType.LBRACE) {
            arguments.add(this.objectLiteral(yield, await, CoverExpressionError.DENY));
         }

         Expression callNode = CallNode.forNew(callLine, newToken, Token.descPosition(newToken), this.finish, constructor, arguments);
         return new UnaryNode(newToken, callNode);
      }
   }

   private Expression memberExpression(boolean yield, boolean await, CoverExpressionError coverExpression) {
      Expression lhs;
      boolean isSuper;
      isSuper = false;
      label70:
      switch (this.type) {
         case FUNCTION:
            lhs = this.functionExpression();
            break;
         case CLASS:
         case AT:
            if (ES6_CLASS && this.isES6()) {
               lhs = this.classExpression(yield, await);
               break;
            }
         case SUPER:
            if (ES6_CLASS && this.isES6()) {
               Scope scope = this.lc.getCurrentScope();
               if (scope.inMethod()) {
                  long identToken = Token.recast(this.token, TokenType.IDENT);
                  this.next();
                  lhs = new IdentNode(identToken, this.finish, this.lexer.stringIntern(TokenType.SUPER.getNameTS())).setIsSuper();
                  switch (this.type) {
                     case LPAREN:
                        if (!scope.inDerivedConstructor()) {
                           throw this.error(AbstractParser.message("invalid.super"), identToken);
                        }

                        lhs = ((IdentNode)lhs).setIsDirectSuper();
                        break label70;
                     case LBRACKET:
                     case PERIOD:
                        this.markSuperProperty();
                        isSuper = true;
                        break label70;
                     default:
                        throw this.error(AbstractParser.message("invalid.super"), identToken);
                  }
               }
            }
         case ASYNC:
            if (this.isAsync() && this.lookaheadIsAsyncFunction()) {
               lhs = this.asyncFunctionExpression();
               break;
            }
         case IMPORT:
            if (this.isES2020() && this.type == TokenType.IMPORT) {
               lhs = this.importExpression(yield, await);
               break;
            }
         default:
            lhs = this.primaryExpression(yield, await, coverExpression);
            this.verifyPrimaryExpression(lhs, coverExpression);
            break;
         case NEW:
            lhs = this.newExpression(yield, await);
      }

      while (true) {
         long callToken = this.token;
         switch (this.type) {
            case LBRACKET:
               this.next();
               Expression index = this.expression(true, yield, await);
               this.expect(TokenType.RBRACKET);
               lhs = new IndexNode(callToken, this.finish, lhs, index, isSuper, false, false);
               if (isSuper) {
                  isSuper = false;
               }
               break;
            case TEMPLATE:
            case TEMPLATE_HEAD:
               int callLine = this.line;
               List<Expression> arguments = this.templateLiteralArgumentList(yield, await);
               lhs = CallNode.forCall(callLine, callToken, lhs.getStart(), this.finish, lhs, arguments, false, false);
               break;
            case PERIOD:
               this.next();
               boolean isPrivate = this.type == TokenType.PRIVATE_IDENT;
               IdentNode property;
               if (!isSuper && isPrivate) {
                  property = this.privateIdentifierUse();
               } else {
                  property = this.getIdentifierName();
               }

               lhs = new AccessNode(callToken, this.finish, lhs, property.getNameTS(), isSuper, isPrivate, false, false);
               if (isSuper) {
                  isSuper = false;
               }
               break;
            default:
               return lhs;
         }
      }
   }

   private void verifyPrimaryExpression(Expression lhs, CoverExpressionError coverExpression) {
      if (coverExpression != CoverExpressionError.DENY && coverExpression.hasError() && this.isDestructuringLhs(lhs)) {
         switch (this.type) {
            case LPAREN:
            case LBRACKET:
            case TEMPLATE:
            case TEMPLATE_HEAD:
            case PERIOD:
            case OPTIONAL_CHAIN:
               this.verifyExpression(coverExpression);
         }
      }
   }

   private Expression importExpression(boolean yield, boolean await) {
      long importToken = this.token;
      int importLine = this.line;
      int importStart = this.start;

      assert this.type == TokenType.IMPORT;

      this.next();
      if (this.type == TokenType.PERIOD) {
         this.next();
         this.expectDontAdvance(TokenType.IDENT);
         String meta = ((TruffleString)this.getValueNoEscape()).toJavaStringUncached();
         if ("meta".equals(meta)) {
            if (!this.isModule) {
               throw this.error(AbstractParser.message("unexpected.import.meta"), importToken);
            } else {
               this.next();
               return new IdentNode(importToken, this.finish, this.lexer.stringIntern(IMPORT_META_NAME)).setIsImportMeta();
            }
         } else {
            throw this.error(AbstractParser.message("unexpected.ident", meta), this.token);
         }
      } else if (this.type == TokenType.LPAREN) {
         this.next();
         List<Expression> arguments = new ArrayList<>();
         arguments.add(this.assignmentExpression(true, yield, await));
         if (this.env.importAssertions && this.type == TokenType.COMMARIGHT) {
            this.next();
            if (this.type != TokenType.RPAREN) {
               arguments.add(this.assignmentExpression(true, yield, await));
               if (this.type == TokenType.COMMARIGHT) {
                  this.next();
               }
            }
         }

         this.expect(TokenType.RPAREN);
         IdentNode importIdent = new IdentNode(
            importToken, Token.descPosition(importToken) + Token.descLength(importToken), this.lexer.stringIntern(TokenType.IMPORT.getNameTS())
         );
         return CallNode.forImport(importLine, importToken, importStart, this.finish, importIdent, arguments);
      } else {
         throw this.error(AbstractParser.message("expected.operand", TokenType.IMPORT.getName()), importToken);
      }
   }

   private ArrayList<Expression> argumentList(boolean yield, boolean await) {
      return this.argumentList(yield, await, false, 0L, 0);
   }

   private ArrayList<Expression> argumentList(boolean yield, boolean await, boolean coverAsyncArrow, long startToken, int startLine) {
      assert this.type == TokenType.LPAREN;

      this.next();
      ArrayList<Expression> nodeList = new ArrayList<>();
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

            Expression expression = this.assignmentExpression(true, yield, await, coverExpression);
            if (spreadToken != 0L) {
               expression = new UnaryNode(Token.recast(spreadToken, TokenType.SPREAD_ARGUMENT), expression);
            }

            nodeList.add(expression);
         }
      } finally {
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
      assert this.isAsync() && this.lookaheadIsAsyncFunction();

      long asyncToken = this.token;
      this.nextOrEOL();
      return Token.recast(asyncToken, TokenType.FUNCTION);
   }

   private Expression asyncFunctionDeclaration(final boolean isStatement, final boolean topLevel, boolean yield, boolean await, boolean isDefault) {
      long functionToken = this.expectAsyncFunction();
      return this.functionDeclarationOrExpression(functionToken, isStatement, topLevel, true, false, true, yield, await, isDefault);
   }

   private Expression asyncFunctionExpression() {
      long functionToken = this.expectAsyncFunction();
      return this.functionDeclarationOrExpression(functionToken, false, false, true, false, false, false, true, true);
   }

   private Expression functionDeclaration(
      final boolean isStatement, final boolean topLevel, final boolean expressionStatement, boolean yield, boolean await, boolean isDefault
   ) {
      return this.functionDeclarationOrExpression(this.token, isStatement, topLevel, false, expressionStatement, true, yield, await, isDefault);
   }

   private Expression functionExpression() {
      return this.functionDeclarationOrExpression(this.token, false, false, false, false, false, false, false, true);
   }

   private Expression functionDeclarationOrExpression(
      long functionToken,
      boolean isStatement,
      boolean topLevel,
      boolean async,
      boolean expressionStatement,
      boolean isDeclaration,
      boolean isYield,
      boolean isAwait,
      boolean isDefault
   ) {
      int functionLine = this.line;

      assert this.type == TokenType.FUNCTION;

      this.next();
      boolean generator = false;
      if (this.type == TokenType.MUL && ES6_GENERATOR_FUNCTION && this.isES6()) {
         if (expressionStatement) {
            throw this.error(AbstractParser.message("expected.stmt", "generator function declaration"), this.token);
         }

         generator = true;
         this.next();
      }

      assert !isDeclaration || isDefault || isStatement;

      IdentNode name = null;
      boolean declared = isDeclaration;
      if (this.isBindingIdentifier()) {
         boolean yield = !isDeclaration && generator || isDeclaration && isYield;
         boolean await = !isDeclaration && async || isDeclaration && isAwait;
         name = this.bindingIdentifier(yield, await, "function name");
      } else if (isDeclaration && !isDefault) {
         if (this.env.syntaxExtensions) {
            declared = false;
         } else if (this.reparsedFunction == null) {
            this.expect(TokenType.IDENT);
         }
      }

      this.expect(TokenType.LPAREN);
      boolean isAnonymous = name == null;

      assert !declared || !isAnonymous || isDefault;

      int functionFlags = (generator ? 16777216 : 0)
         | (async ? 33554432 : 0)
         | (isAnonymous ? 1 : 0)
         | (declared ? 2 : 0)
         | (isStatement && !isAnonymous ? 16 : 0);
      ParserContextFunctionNode functionNode = this.createParserContextFunctionNode(name, functionToken, functionFlags, functionLine);
      if (isAnonymous) {
         functionNode.setInternalName(this.getDefaultFunctionName());
      }

      this.lc.push(functionNode);
      this.hideDefaultName();

      Block functionBody;
      try {
         ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
         this.lc.push(parameterBlock);

         try {
            this.formalParameterList(generator, async);
            this.expect(TokenType.RPAREN);
            functionBody = this.functionBody(functionNode);
         } finally {
            this.restoreBlock(parameterBlock);
         }

         if (parameterBlock != null) {
            functionBody = wrapParameterBlock(parameterBlock, functionBody);
         }
      } finally {
         this.popDefaultName();
         this.lc.pop(functionNode);
      }

      if (isStatement
         && !isAnonymous
         && !topLevel
         && !this.useBlockScope()
         && (this.isStrictMode || this.env.functionStatement != ScriptEnvironment.FunctionStatementBehavior.ACCEPT)) {
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
         int varFlags = (!topLevel || scope.isModuleScope()) && this.useBlockScope() ? 1 : 0;
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
      assert parameterBlock.getFlag(64) != 0 && functionBody.isFunctionBody();

      if (parameterBlock.getStatements().isEmpty()) {
         return functionBody;
      } else {
         parameterBlock.getStatements().add(new BlockStatement(functionBody.getFirstStatementLineNumber(), functionBody));
         return new Block(
            parameterBlock.getToken(), functionBody.getFinish(), parameterBlock.getFlags(), parameterBlock.getScope(), parameterBlock.getStatements()
         );
      }
   }

   private void verifyParameterList(final ParserContextFunctionNode functionNode) {
      IdentNode duplicateParameter = functionNode.getDuplicateParameterBinding();
      if (duplicateParameter != null) {
         if (functionNode.isStrict() || functionNode.isMethod() || functionNode.isArrow() || !functionNode.isSimpleParameterList()) {
            throw this.error(AbstractParser.message("strict.param.redefinition", duplicateParameter.getName()), duplicateParameter.getToken());
         }

         List<IdentNode> parameters = functionNode.getParameters();
         int arity = parameters.size();
         HashSet<String> parametersSet = new HashSet<>(arity);

         for (int i = arity - 1; i >= 0; i--) {
            IdentNode parameter = parameters.get(i);
            String parameterName = parameter.getName();
            if (parametersSet.contains(parameterName)) {
               parameters.set(i, parameter.setIsIgnoredParameter());
            } else {
               parametersSet.add(parameterName);
            }
         }
      }
   }

   private void reportIllegalES5BlockLevelFunctionDeclaration(long functionToken) {
      assert !this.isES6();

      if (this.isStrictMode) {
         throw this.error(JSErrorType.SyntaxError, AbstractParser.message("strict.no.func.decl.here"), functionToken);
      } else if (this.env.functionStatement == ScriptEnvironment.FunctionStatementBehavior.ERROR) {
         throw this.error(JSErrorType.SyntaxError, AbstractParser.message("no.func.decl.here"), functionToken);
      } else {
         if (this.env.functionStatement == ScriptEnvironment.FunctionStatementBehavior.WARNING) {
            this.warning(JSErrorType.SyntaxError, AbstractParser.message("no.func.decl.here.warn"), functionToken);
         }
      }
   }

   private void pushDefaultName(final Expression nameExpr) {
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
               if (base.getBase() instanceof IdentNode && !base.isPrivate() && base.getProperty().equals("prototype")) {
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

   private void formalParameterList(final boolean yield, final boolean async) {
      this.formalParameterList(TokenType.RPAREN, yield, async);
   }

   private boolean inFormalParameterList() {
      Iterator<ParserContextNode> iterator = this.lc.getAllNodes();

      while (iterator.hasNext()) {
         ParserContextNode node = iterator.next();
         if (node instanceof ParserContextScopableNode) {
            Scope scope = ((ParserContextScopableNode)node).getScope();
            if (scope.isFunctionBodyScope()) {
               return false;
            }

            if (scope.isFunctionParameterScope() && !scope.isArrowFunctionParameterScope()) {
               return true;
            }
         }
      }

      return false;
   }

   private void formalParameter(final boolean yield, final boolean await) {
      if ((this.type != TokenType.YIELD || !yield) && (!this.isAwait() || !await)) {
         ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
         long paramToken = this.token;
         int paramLine = this.line;
         if (!this.isBindingIdentifier() && ES6_DESTRUCTURING && this.isES6()) {
            Expression pattern = this.bindingPattern(yield, await);
            this.verifyDestructuringParameterBindingPattern(pattern, paramToken, paramLine);
            Expression initializer = null;
            if (this.type == TokenType.ASSIGN) {
               this.next();
               initializer = this.assignmentExpression(true, yield, await);
            }

            if (currentFunction != null) {
               this.addDestructuringParameter(paramToken, this.finish, paramLine, pattern, initializer, currentFunction, false);
            }
         } else {
            IdentNode ident = this.bindingIdentifier(yield, await, "function parameter");
            if (this.type == TokenType.ASSIGN && ES6_DEFAULT_PARAMETER && this.isES6()) {
               this.next();
               Expression initializerx = this.assignmentExpression(true, yield, await);
               if (isAnonymousFunctionDefinition(initializerx)) {
                  initializerx = this.setAnonymousFunctionName(initializerx, ident.getNameTS());
               }

               if (currentFunction != null) {
                  addDefaultParameter(paramToken, this.finish, paramLine, ident, initializerx, currentFunction);
               }
            } else if (currentFunction != null) {
               currentFunction.addParameter(ident);
            }
         }
      } else {
         throw this.error(this.expectMessage(TokenType.IDENT));
      }
   }

   private void functionRestParameter(final TokenType endType, final boolean yield, final boolean await) {
      long paramToken = this.token;
      int paramLine = this.line;
      ParserContextFunctionNode currentFunction = this.lc.getCurrentFunction();
      Expression pattern = this.bindingIdentifierOrPattern(yield, await, "function parameter");
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

   private void formalParameterList(final TokenType endType, final boolean yield, final boolean await) {
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
            this.functionRestParameter(endType, yield, await);
            break;
         }

         this.formalParameter(yield, await);
      }
   }

   private static void addDefaultParameter(
      long paramToken, int paramFinish, int paramLine, IdentNode target, Expression initializer, ParserContextFunctionNode function
   ) {
      assert target != null && initializer != null;

      int paramIndex = function.getParameterCount();
      ParameterNode param = new ParameterNode(paramToken, paramFinish, paramIndex);
      BinaryNode test = new BinaryNode(Token.recast(paramToken, TokenType.EQ_STRICT), param, newUndefinedLiteral(paramToken, paramFinish));
      Expression value = new TernaryNode(
         Token.recast(paramToken, TokenType.TERNARY), test, new JoinPredecessorExpression(initializer), new JoinPredecessorExpression(param)
      );
      VarNode varNode = new VarNode(paramLine, Token.recast(paramToken, TokenType.LET), paramFinish, target, value, 1);
      function.addDefaultParameter(varNode);
   }

   private void addDestructuringParameter(
      long paramToken, int paramFinish, int paramLine, Expression target, Expression initializer, ParserContextFunctionNode function, boolean isRest
   ) {
      assert this.isDestructuringLhs(target);

      int paramIndex = function.getParameterCount();
      ParameterNode param = new ParameterNode(paramToken, paramFinish, paramIndex, isRest);
      Expression value;
      if (initializer == null) {
         value = param;
      } else {
         BinaryNode test = new BinaryNode(Token.recast(paramToken, TokenType.EQ_STRICT), param, newUndefinedLiteral(paramToken, paramFinish));
         value = new TernaryNode(
            Token.recast(paramToken, TokenType.TERNARY), test, new JoinPredecessorExpression(initializer), new JoinPredecessorExpression(param)
         );
      }

      BinaryNode assignment = new BinaryNode(Token.recast(paramToken, TokenType.ASSIGN_INIT), target, value);
      function.addParameterInitialization(paramLine, assignment, initializer != null, isRest);
   }

   private void verifyDestructuringParameterBindingPattern(final Expression pattern, final long paramToken, final int paramLine) {
      this.verifyDestructuringBindingPattern(pattern, new Consumer<IdentNode>() {
         public void accept(IdentNode identNode) {
            Parser.this.verifyStrictIdent(identNode, "function parameter");
            ParserContextFunctionNode currentFunction = Parser.this.lc.getCurrentFunction();
            if (currentFunction != null) {
               VarNode declaration = new VarNode(paramLine, Token.recast(paramToken, TokenType.LET), pattern.getFinish(), identNode, null, 17);
               currentFunction.addParameterBindingDeclaration(declaration);
            }
         }
      });
   }

   private Block functionBody(final ParserContextFunctionNode functionNode) {
      boolean yield = functionNode.isGenerator();
      boolean await = functionNode.isAsync() || this.isTopLevelAwait() && this.isModule && functionNode.isModule() || functionNode.isClassStaticBlock();
      long bodyToken = this.token;
      Object endParserState = null;
      ParserContextBlockNode body = this.newBlock(functionNode.createBodyScope(this.lexer::stringIntern));

      int bodyFinish;
      boolean parseBody;
      try {
         int functionId = functionNode.getId();
         parseBody = this.reparsedFunction == null || functionId <= this.reparsedFunction.getFunctionNodeId();
         if ((this.env.syntaxExtensions || functionNode.isArrow()) && this.type != TokenType.LBRACE) {
            Expression expr = this.assignmentExpression(true, yield, await);
            long lastToken = this.previousToken;
            functionNode.setLastToken(this.previousToken);

            assert this.lc.getCurrentBlock().getScope().isFunctionBodyScope();

            int lastFinish = Token.descPosition(lastToken) + (Token.descType(lastToken) == TokenType.EOL ? 0 : Token.descLength(lastToken));
            if (parseBody) {
               ReturnNode returnNode = new ReturnNode(functionNode.getLineNumber(), expr.getToken(), lastFinish, expr);
               this.appendStatement(returnNode);
            }

            bodyFinish = this.finish;
         } else {
            this.expectDontAdvance(TokenType.LBRACE);
            if (parseBody || !this.skipFunctionBody(functionNode)) {
               this.next();
               List<Statement> prevFunctionDecls = this.functionDeclarations;
               this.functionDeclarations = new ArrayList<>();

               try {
                  this.sourceElements(yield, await, 0);
                  this.addFunctionDeclarations(functionNode);
               } finally {
                  this.functionDeclarations = prevFunctionDecls;
               }

               if (parseBody) {
                  endParserState = new Parser.ParserState(Token.descPosition(this.token), this.line, this.linePosition);
               }
            }

            bodyFinish = Token.descPosition(this.token) + Token.descLength(this.token);
            functionNode.setLastToken(this.token);
            this.expect(TokenType.RBRACE);
         }
      } finally {
         functionNode.finishBodyScope(this.lexer::stringIntern);
         this.restoreBlock(body);
         this.lc.propagateFunctionFlags();
      }

      if (parseBody) {
         functionNode.setEndParserState(endParserState);
      } else if (!body.getStatements().isEmpty()) {
         body.setStatements(List.of());
      }

      if (this.reparsedFunction != null) {
         RecompilableScriptFunctionData data = this.reparsedFunction.getScriptFunctionData(functionNode.getId());
         if (data != null) {
            functionNode.setFlag(data.getFunctionFlags());
            if (functionNode.hasNestedEval()) {
               assert functionNode.hasScopeBlock();

               body.setFlag(1);
            }
         }
      }

      return new Block(bodyToken, bodyFinish, body.getFlags() | 32, body.getScope(), body.getStatements());
   }

   private boolean skipFunctionBody(final ParserContextFunctionNode functionNode) {
      if (this.reparsedFunction == null) {
         return false;
      } else {
         RecompilableScriptFunctionData data = this.reparsedFunction.getScriptFunctionData(functionNode.getId());
         if (data == null) {
            return false;
         } else {
            Parser.ParserState parserState = (Parser.ParserState)data.getEndParserState();

            assert parserState != null;

            if (this.k < this.stream.last()
               && this.start < parserState.position
               && parserState.position <= Token.descPosition(this.stream.get(this.stream.last()))) {
               while (this.k < this.stream.last()) {
                  long nextToken = this.stream.get(this.k + 1);
                  if (Token.descPosition(nextToken) == parserState.position && Token.descType(nextToken) == TokenType.RBRACE) {
                     this.token = this.stream.get(this.k);
                     this.type = Token.descType(this.token);
                     this.next();
                     if ($assertionsDisabled || this.type == TokenType.RBRACE && this.start == parserState.position) {
                        return true;
                     }

                     throw new AssertionError();
                  }

                  this.k++;
               }
            }

            this.stream.reset();
            this.lexer = parserState.createLexer(
               this.source, this.lexer, this.stream, this.scripting, this.env.ecmaScriptVersion, this.shebang, this.isModule, this.allowBigInt
            );
            this.line = parserState.line;
            this.linePosition = parserState.linePosition;
            this.type = TokenType.SEMICOLON;
            this.scanFirstToken();
            return true;
         }
      }
   }

   private void addFunctionDeclarations(final ParserContextFunctionNode functionNode) {
      VarNode lastDecl = null;

      for (int i = this.functionDeclarations.size() - 1; i >= 0; i--) {
         Statement decl = this.functionDeclarations.get(i);
         if (lastDecl == null && decl instanceof VarNode) {
            decl = lastDecl = ((VarNode)decl).setFlag(4);
            functionNode.setFlag(268435456);
         }

         this.prependStatement(decl);
      }
   }

   private ParserException invalidLHSError(final Expression lhs) {
      JSErrorType errorType = this.isES2020() ? JSErrorType.SyntaxError : JSErrorType.ReferenceError;
      return this.error(errorType, AbstractParser.message("invalid.lvalue"), lhs.getToken());
   }

   private Expression unaryExpression(boolean yield, boolean await, CoverExpressionError coverExpression) {
      long unaryToken = this.token;
      switch (this.type) {
         case DELETE:
            this.next();
            Expression expr = this.unaryExpression(yield, await, CoverExpressionError.DENY);
            if (this.type == TokenType.EXP) {
               throw this.error(AbstractParser.message("unexpected.token", this.type.getNameOrType()));
            }

            return this.verifyDeleteExpression(unaryToken, expr);
         case VOID:
         case TYPEOF:
         case ADD:
         case SUB:
         case BIT_NOT:
         case NOT:
            this.next();
            Expression expr = this.unaryExpression(yield, await, CoverExpressionError.DENY);
            if (this.type == TokenType.EXP) {
               throw this.error(AbstractParser.message("unexpected.token", this.type.getNameOrType()));
            }

            return new UnaryNode(unaryToken, expr);
         case INCPREFIX:
         case DECPREFIX:
            TokenType opType = this.type;
            this.next();
            Expression lhs = this.unaryExpression(yield, await, CoverExpressionError.DENY);
            return this.verifyIncDecExpression(unaryToken, opType, lhs, false);
         default:
            if (this.isAwait() && await) {
               return this.awaitExpression(yield);
            } else {
               Expression expression = this.leftHandSideExpression(yield, await, coverExpression);
               if (this.last != TokenType.EOL) {
                  switch (this.type) {
                     case INCPREFIX:
                     case DECPREFIX:
                        long opToken = this.token;
                        TokenType opTypex = this.type;
                        this.next();
                        return this.verifyIncDecExpression(opToken, opTypex, expression, true);
                  }
               }

               return expression;
            }
      }
   }

   private Expression verifyDeleteExpression(final long unaryToken, final Expression expr) {
      if ((expr instanceof BaseNode || expr instanceof IdentNode) && this.isStrictMode) {
         if (expr instanceof IdentNode) {
            IdentNode ident = (IdentNode)expr;
            if (!ident.isThis() && !ident.isMetaProperty()) {
               throw this.error(AbstractParser.message("strict.cant.delete.ident", ident), unaryToken);
            }
         } else if (expr instanceof AccessNode && ((AccessNode)expr).isPrivate()) {
            throw this.error(AbstractParser.message("strict.cant.delete.private"), unaryToken);
         }
      }

      return new UnaryNode(unaryToken, expr);
   }

   private Expression verifyIncDecExpression(final long unaryToken, final TokenType opType, final Expression lhs, final boolean isPostfix) {
      assert lhs != null;

      if (lhs instanceof IdentNode) {
         IdentNode ident = (IdentNode)lhs;
         if (!checkIdentLValue(ident) || ident.isMetaProperty()) {
            throw this.invalidLHSError(lhs);
         }

         assert opType == TokenType.INCPREFIX || opType == TokenType.DECPREFIX;

         String contextString = opType == TokenType.INCPREFIX ? "operand for ++ operator" : "operand for -- operator";
         this.verifyStrictIdent((IdentNode)lhs, contextString);
      } else if (!(lhs instanceof AccessNode) && !(lhs instanceof IndexNode) || ((BaseNode)lhs).isOptional()) {
         throw this.invalidLHSError(lhs);
      }

      return incDecExpression(unaryToken, opType, lhs, isPostfix);
   }

   private Expression expression(boolean in, boolean yield, boolean await) {
      return this.expression(in, yield, await, CoverExpressionError.DENY);
   }

   private Expression expression(boolean yield, boolean await) {
      return this.expression(true, yield, await);
   }

   private Expression expression(boolean in, boolean yield, boolean await, CoverExpressionError coverExpression) {
      Expression assignmentExpression = this.assignmentExpression(in, yield, await, coverExpression);

      while (this.type == TokenType.COMMARIGHT) {
         long commaToken = this.token;
         this.next();
         Expression rhs = this.assignmentExpression(in, yield, await);
         assignmentExpression = new BinaryNode(commaToken, assignmentExpression, rhs);
      }

      return assignmentExpression;
   }

   private Expression parenthesizedExpressionAndArrowParameterList(boolean yield, boolean await) {
      long primaryToken = this.token;
      int startLine = this.line;

      assert this.type == TokenType.LPAREN;

      this.next();
      boolean canBeArrowParameterList = true;
      if (ES6_ARROW_FUNCTION && this.isES6() && this.type == TokenType.RPAREN) {
         this.nextOrEOL();
         this.expectDontAdvance(TokenType.ARROW);
         return new ExpressionList(primaryToken, this.finish, List.of());
      } else {
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
                  assignmentExpression = this.arrowFunctionRestParameter(assignmentExpression, commaToken, yield, await);
                  hasRestParameter = true;
                  break;
               }

               if (ES6_ARROW_FUNCTION && ES8_TRAILING_COMMA && this.isES2017() && this.type == TokenType.RPAREN && this.lookaheadIsArrow()) {
                  break;
               }

               Expression rhs = this.assignmentExpression(true, yield, await, coverExpression);
               if (assignmentExpression == null) {
                  assignmentExpression = rhs;
               } else {
                  assert Token.descType(commaToken) == TokenType.COMMARIGHT;

                  assignmentExpression = new BinaryNode(commaToken, assignmentExpression, rhs);
               }

               if (this.type != TokenType.COMMARIGHT) {
                  break;
               }

               commaToken = this.token;
               this.next();
            }
         } finally {
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
   }

   private void commitArrowHead(ParserContextFunctionNode cover) {
      assert this.coverArrowFunction == null;

      if (cover.getYieldOrAwaitInParameters() != 0L) {
         throw this.error(AbstractParser.message("invalid.arrow.parameter"), cover.getYieldOrAwaitInParameters());
      } else {
         this.coverArrowFunction = cover;
      }
   }

   private void revertArrowHead(ParserContextFunctionNode cover) {
      cover.getParameterScope().kill();
      this.lc.setCurrentFunctionFlag(cover.getFlags() & 134791400);
   }

   private Expression arrowFunctionRestParameter(Expression paramListExpr, long commaToken, final boolean yield, final boolean await) {
      long ellipsisToken = this.token;

      assert this.type == TokenType.ELLIPSIS;

      this.next();
      Expression pattern = this.bindingIdentifierOrPattern(yield, await, "function parameter");
      Expression restParam;
      if (pattern instanceof IdentNode) {
         restParam = ((IdentNode)pattern).setIsRestParameter();
      } else {
         restParam = new UnaryNode(Token.recast(ellipsisToken, TokenType.SPREAD_ARGUMENT), pattern);
      }

      if (paramListExpr == null) {
         return restParam;
      } else {
         assert Token.descType(commaToken) == TokenType.COMMARIGHT;

         return new BinaryNode(commaToken, paramListExpr, restParam);
      }
   }

   private Expression expression(int minPrecedence, boolean in, boolean yield, boolean await, CoverExpressionError coverExpression) {
      Expression lhs;
      if (in && this.type == TokenType.PRIVATE_IDENT && this.isPrivateFieldsIn() && this.lookahead() == TokenType.IN) {
         lhs = this.privateIdentifierUse().setIsPrivateInCheck();
      } else {
         lhs = this.unaryExpression(yield, await, coverExpression);
      }

      return this.expression(lhs, minPrecedence, in, yield, await);
   }

   private JoinPredecessorExpression joinPredecessorExpression(boolean yield, boolean await) {
      return new JoinPredecessorExpression(this.expression(yield, await));
   }

   private Expression expression(Expression exprLhs, int minPrecedence, boolean in, boolean yield, boolean await) {
      int precedence = this.type.getPrecedence();

      Expression lhs;
      for (lhs = exprLhs; this.type.isOperator(in) && precedence >= minPrecedence; precedence = this.type.getPrecedence()) {
         long op = this.token;
         if (this.type == TokenType.TERNARY) {
            this.next();
            Expression trueExpr = this.assignmentExpression(true, yield, await);
            this.expect(TokenType.COLON);
            Expression falseExpr = this.assignmentExpression(in, yield, await);
            lhs = new TernaryNode(op, lhs, new JoinPredecessorExpression(trueExpr), new JoinPredecessorExpression(falseExpr));
         } else {
            TokenType opType = this.type;
            this.next();

            assert !Token.descType(op).isAssignment();

            Expression rhs;
            if (in
               && this.type == TokenType.PRIVATE_IDENT
               && this.isPrivateFieldsIn()
               && this.lookahead() == TokenType.IN
               && precedence < TokenType.IN.getPrecedence()) {
               assert opType != TokenType.IN;

               rhs = this.privateIdentifierUse().setIsPrivateInCheck();
            } else {
               rhs = this.unaryExpression(yield, await, CoverExpressionError.DENY);
            }

            for (int nextPrecedence = this.type.getPrecedence();
               this.type.isOperator(in) && (nextPrecedence > precedence || nextPrecedence == precedence && !this.type.isLeftAssociative());
               nextPrecedence = this.type.getPrecedence()
            ) {
               rhs = this.expression(rhs, nextPrecedence, in, yield, await);
            }

            lhs = this.newBinaryExpression(op, lhs, rhs);
         }
      }

      return lhs;
   }

   private boolean isStartOfAssignmentPattern() {
      return this.type == TokenType.LBRACKET || this.type == TokenType.LBRACE;
   }

   private Expression assignmentExpression(boolean in, boolean yield, boolean await) {
      return this.assignmentExpression(in, yield, await, CoverExpressionError.DENY);
   }

   private Expression assignmentExpression(boolean in, boolean yield, boolean await, CoverExpressionError coverExpression) {
      if (this.type == TokenType.YIELD && yield) {
         return this.yieldExpression(in, await);
      } else {
         boolean asyncArrow = this.isAsync() && this.lookaheadIsAsyncArrowParameterListStart();
         long startToken = this.token;
         int startLine = this.line;
         boolean canBeAssignmentPattern = this.isStartOfAssignmentPattern();
         CoverExpressionError coverExprLhs = canBeAssignmentPattern ? new CoverExpressionError() : CoverExpressionError.DENY;
         Expression exprLhs = this.conditionalExpression(in, yield, await, coverExprLhs);
         if (asyncArrow && exprLhs instanceof IdentNode && this.isBindingIdentifier() && this.lookaheadIsArrow()) {
            exprLhs = this.primaryExpression(yield, await, CoverExpressionError.DENY);
            if (exprLhs instanceof IdentNode && TokenType.AWAIT.getName().equals(((IdentNode)exprLhs).getName())) {
               throw this.error(AbstractParser.message("invalid.arrow.parameter"), exprLhs.getToken());
            }
         }

         if (ES6_ARROW_FUNCTION && this.type == TokenType.ARROW && this.isES6() && this.lookbehindNoLineTerminatorBeforeArrow()) {
            return this.arrowFunction(startToken, startLine, exprLhs, asyncArrow);
         } else {
            assert !(exprLhs instanceof ExpressionList);

            if (!this.type.isAssignment()) {
               if (canBeAssignmentPattern) {
                  if (coverExpression != CoverExpressionError.DENY) {
                     coverExpression.recordErrorFrom(coverExprLhs);
                  } else {
                     this.verifyExpression(coverExprLhs);
                  }
               }

               return exprLhs;
            } else {
               if (canBeAssignmentPattern && !this.isDestructuringLhs(exprLhs)) {
                  this.verifyExpression(coverExprLhs);
               }

               boolean isAssign = this.type == TokenType.ASSIGN;
               if (isAssign) {
                  this.pushDefaultName(exprLhs);
               }

               Expression var16;
               try {
                  long assignToken = this.token;
                  this.next();
                  Expression exprRhs = this.assignmentExpression(in, yield, await);
                  var16 = this.verifyAssignment(assignToken, exprLhs, exprRhs, coverExpression != CoverExpressionError.DENY);
               } finally {
                  if (isAssign) {
                     this.popDefaultName();
                  }
               }

               return var16;
            }
         }
      }
   }

   private Expression conditionalExpression(boolean in, boolean yield, boolean await, CoverExpressionError coverExpression) {
      return this.expression(TokenType.TERNARY.getPrecedence(), in, yield, await, coverExpression);
   }

   private void verifyExpression(CoverExpressionError coverExpression) {
      if (coverExpression.hasError()) {
         this.throwExpressionError(coverExpression);
      }
   }

   private void throwExpressionError(CoverExpressionError coverExpression) {
      assert coverExpression.hasError();

      throw this.error(AbstractParser.message(coverExpression.getErrorMessage()), coverExpression.getErrorToken());
   }

   private void recordOrThrowExpressionError(String msgId, long assignToken, CoverExpressionError coverExpression) {
      if (coverExpression != CoverExpressionError.DENY) {
         coverExpression.recordExpressionError(msgId, assignToken);
      } else {
         throw this.error(AbstractParser.message(msgId), assignToken);
      }
   }

   private Expression arrowFunction(final long startToken, final int functionLine, final Expression paramListExpr, boolean async) {
      assert this.type != TokenType.ARROW || this.lookbehindNoLineTerminatorBeforeArrow();

      this.expect(TokenType.ARROW);
      ParserContextFunctionNode functionNode;
      if (this.coverArrowFunction == null) {
         functionNode = this.createParserContextArrowFunctionNode(startToken, functionLine, async, false);
      } else {
         functionNode = this.coverArrowFunction;
         functionNode.setCoverArrowHead(false);
         this.coverArrowFunction = null;
      }

      assert functionNode.isArrow() && !functionNode.isCoverArrowHead();

      functionNode.setInternalName(this.lexer.stringIntern(ARROW_FUNCTION_NAME));
      functionNode.setFlag(1);
      this.lc.push(functionNode);

      FunctionNode var10;
      try {
         ParserContextBlockNode parameterBlock = functionNode.createParameterBlock();
         this.lc.push(parameterBlock);

         Block functionBody;
         try {
            this.convertArrowFunctionParameterList(paramListExpr, functionNode);

            assert functionNode.isAsync() == async;

            functionBody = this.functionBody(functionNode);
         } finally {
            this.restoreBlock(parameterBlock);
         }

         this.verifyParameterList(functionNode);
         if (parameterBlock != null) {
            functionBody = wrapParameterBlock(parameterBlock, functionBody);
         }

         FunctionNode function = this.createFunctionNode(functionNode, functionNode.getFirstToken(), functionNode.getIdent(), functionLine, functionBody);
         var10 = function;
      } finally {
         this.lc.pop(functionNode);
      }

      return var10;
   }

   private ParserContextFunctionNode createParserContextArrowFunctionNode(long startToken, int startLine, boolean async, boolean cover) {
      long functionToken = Token.recast(startToken, TokenType.ARROW);
      IdentNode name = null;
      ParserContextFunctionNode function = this.createParserContextFunctionNode(name, functionToken, 65536, startLine);
      if (async) {
         function.setFlag(33554432);
      }

      if (cover) {
         function.setCoverArrowHead(true);

         assert this.coverArrowFunction == null;
      }

      return function;
   }

   private static Expression convertExpressionListToExpression(ExpressionList exprList) {
      if (exprList.getExpressions().isEmpty()) {
         return null;
      } else if (exprList.getExpressions().size() == 1) {
         return exprList.getExpressions().get(0);
      } else {
         long recastToken = Token.recast(exprList.getToken(), TokenType.COMMARIGHT);
         Expression result = null;

         for (Expression expression : exprList.getExpressions()) {
            result = (Expression)(result == null ? expression : new BinaryNode(recastToken, result, expression));
         }

         return result;
      }
   }

   private void convertArrowFunctionParameterList(Expression paramList, ParserContextFunctionNode function) {
      Expression paramListExpr = paramList;
      if (paramList instanceof ExpressionList) {
         paramListExpr = convertExpressionListToExpression((ExpressionList)paramList);
      }

      if (paramListExpr != null) {
         int functionLine = function.getLineNumber();
         if (!(paramListExpr instanceof IdentNode)
            && !paramListExpr.isTokenType(TokenType.ASSIGN)
            && !this.isDestructuringLhs(paramListExpr)
            && !paramListExpr.isTokenType(TokenType.SPREAD_ARGUMENT)) {
            if (!(paramListExpr instanceof BinaryNode) || Token.descType(paramListExpr.getToken()) != TokenType.COMMARIGHT) {
               throw this.error(AbstractParser.message("expected.arrow.parameter"), paramListExpr.getToken());
            }

            List<Expression> params = new ArrayList<>();
            Expression car = paramListExpr;

            do {
               Expression cdr = ((BinaryNode)car).getRhs();
               params.add(cdr);
               car = ((BinaryNode)car).getLhs();
            } while (car instanceof BinaryNode && Token.descType(car.getToken()) == TokenType.COMMARIGHT);

            params.add(car);
            int i = params.size() - 1;

            for (int pos = 0; i >= 0; pos++) {
               Expression param = params.get(i);
               if (i != 0 && param.isTokenType(TokenType.SPREAD_ARGUMENT)) {
                  throw this.error(AbstractParser.message("invalid.arrow.parameter"), param.getToken());
               }

               this.convertArrowParameter(param, pos, functionLine, function);
               i--;
            }
         } else {
            this.convertArrowParameter(paramListExpr, 0, functionLine, function);
         }
      }
   }

   private void convertArrowParameter(Expression param, int index, int paramLine, ParserContextFunctionNode currentFunction) {
      assert index == currentFunction.getParameterCount();

      if (param instanceof IdentNode) {
         IdentNode ident = (IdentNode)param;
         this.verifyStrictIdent(ident, "function parameter");
         if (ident.isParenthesized()) {
            throw this.error(AbstractParser.message("invalid.arrow.parameter"), param.getToken());
         } else {
            assert !currentFunction.isAsync() || !TokenType.AWAIT.getName().equals(ident.getName());

            currentFunction.addParameter(ident);
         }
      } else {
         if (param.isTokenType(TokenType.ASSIGN)) {
            Expression lhs = ((BinaryNode)param).getLhs();
            long paramToken = lhs.getToken();
            Expression initializer = ((BinaryNode)param).getRhs();

            assert !(initializer instanceof IdentNode) || !currentFunction.isAsync() || !TokenType.AWAIT.getName().equals(((IdentNode)initializer).getName());

            if (lhs instanceof IdentNode && !lhs.isParenthesized()) {
               IdentNode ident = (IdentNode)lhs;
               if (isAnonymousFunctionDefinition(initializer)) {
                  initializer = this.setAnonymousFunctionName(initializer, ident.getNameTS());
               }

               addDefaultParameter(paramToken, param.getFinish(), paramLine, ident, initializer, currentFunction);
               return;
            }

            if (!this.isDestructuringLhs(lhs)) {
               throw this.error(AbstractParser.message("invalid.arrow.parameter"), paramToken);
            }

            this.verifyDestructuringParameterBindingPattern(lhs, paramToken, paramLine);
            this.addDestructuringParameter(paramToken, param.getFinish(), paramLine, lhs, initializer, currentFunction, false);
         } else if (this.isDestructuringLhs(param)) {
            long paramTokenx = param.getToken();
            this.verifyDestructuringParameterBindingPattern(param, paramTokenx, paramLine);
            this.addDestructuringParameter(paramTokenx, param.getFinish(), paramLine, param, null, currentFunction, false);
         } else {
            if (!param.isTokenType(TokenType.SPREAD_ARGUMENT)) {
               throw this.error(AbstractParser.message("invalid.arrow.parameter"), param.getToken());
            }

            if (this.lookbehindIsTrailingCommaInArrowParameters()) {
               throw this.error(AbstractParser.message("invalid.arrow.parameter"), param.getToken());
            }

            Expression restParam = ((UnaryNode)param).getExpression();
            if (restParam instanceof IdentNode) {
               IdentNode ident = ((IdentNode)restParam).setIsRestParameter();
               this.convertArrowParameter(ident, index, paramLine, currentFunction);
            } else {
               if (!this.isDestructuringLhs(restParam)) {
                  throw this.error(AbstractParser.message("invalid.arrow.parameter"), param.getToken());
               }

               this.verifyDestructuringParameterBindingPattern(restParam, restParam.getToken(), paramLine);
               this.addDestructuringParameter(restParam.getToken(), restParam.getFinish(), paramLine, restParam, null, currentFunction, true);
            }
         }
      }
   }

   private boolean lookbehindIsTrailingCommaInArrowParameters() {
      int idx = this.k - 1;

      while (true) {
         TokenType t = this.T(--idx);
         switch (t) {
            case EOL:
            case RPAREN:
            case COMMENT:
            case ARROW:
               break;
            case COMMARIGHT:
               return true;
            default:
               return false;
         }
      }
   }

   private boolean lookbehindNoLineTerminatorBeforeArrow() {
      assert this.type == TokenType.ARROW;

      if (this.last == TokenType.RPAREN) {
         return true;
      } else if (this.last == TokenType.IDENT) {
         return true;
      } else {
         int i = this.k - 1;

         while (i >= 0) {
            TokenType t = this.T(i);
            switch (t) {
               case EOL:
                  return false;
               case RPAREN:
               case IDENT:
                  return true;
               case COMMENT:
                  i--;
                  break;
               default:
                  return t.isContextualKeyword() || t.isFutureStrict();
            }
         }

         return false;
      }
   }

   private boolean lookbehindNoLineTerminatorAfterAsync() {
      assert this.type == TokenType.LPAREN;

      return this.last == TokenType.ASYNC;
   }

   private boolean lookaheadIsArrow() {
      int i = 1;

      TokenType t;
      do {
         t = this.T(this.k + i++);
         if (t == TokenType.ARROW) {
            return true;
         }
      } while (t == TokenType.COMMENT);

      return false;
   }

   private void endOfLine() {
      switch (this.type) {
         case EOF:
         case RBRACE:
         case RPAREN:
         case RBRACKET:
            break;
         case EOL:
         case SEMICOLON:
            this.next();
            break;
         default:
            if (this.last != TokenType.EOL) {
               this.expect(TokenType.SEMICOLON);
            }
      }
   }

   private Expression templateLiteral(boolean yield, boolean await) {
      assert this.type == TokenType.TEMPLATE || this.type == TokenType.TEMPLATE_HEAD;

      boolean noSubstitutionTemplate = this.type == TokenType.TEMPLATE;
      long startToken = this.token;
      boolean previousPauseOnRightBrace = this.lexer.pauseOnRightBrace;

      LiteralNode expressions;
      try {
         this.lexer.pauseOnRightBrace = true;
         LiteralNode<?> literal = this.getLiteral();
         if (!noSubstitutionTemplate) {
            List<Expression> expressionsx = new ArrayList<>();
            expressionsx.add(literal);

            TokenType lastLiteralType;
            do {
               Expression expression = this.templateLiteralExpression(yield, await);
               expressionsx.add(expression);
               lastLiteralType = this.type;
               literal = this.getLiteral();
               expressionsx.add(literal);
            } while (lastLiteralType == TokenType.TEMPLATE_MIDDLE);

            return TemplateLiteralNode.newUntagged(startToken, literal.getFinish(), expressionsx);
         }

         expressions = literal;
      } finally {
         this.lexer.pauseOnRightBrace = previousPauseOnRightBrace;
      }

      return expressions;
   }

   private Expression templateLiteralExpression(boolean yield, boolean await) {
      assert this.lexer.pauseOnRightBrace;

      Expression expression = this.expression(true, yield, await);
      if (this.type != TokenType.RBRACE) {
         throw this.error(AbstractParser.message("unterminated.template.expression"), this.token);
      } else {
         this.lexer.scanTemplateSpan();
         this.next();

         assert this.type == TokenType.TEMPLATE_MIDDLE || this.type == TokenType.TEMPLATE_TAIL;

         return expression;
      }
   }

   private List<Expression> templateLiteralArgumentList(boolean yield, boolean await) {
      assert this.type == TokenType.TEMPLATE || this.type == TokenType.TEMPLATE_HEAD;

      ArrayList<Expression> argumentList = new ArrayList<>();
      ArrayList<Expression> rawStrings = new ArrayList<>();
      ArrayList<Expression> cookedStrings = new ArrayList<>();
      argumentList.add(null);
      long templateToken = this.token;
      boolean hasSubstitutions = this.type == TokenType.TEMPLATE_HEAD;
      boolean previousPauseOnRightBrace = this.lexer.pauseOnRightBrace;

      ArrayList var16;
      try {
         this.lexer.pauseOnRightBrace = true;
         this.addTemplateLiteralString(rawStrings, cookedStrings);
         TokenType lastLiteralType;
         if (hasSubstitutions) {
            do {
               Expression expression = this.templateLiteralExpression(yield, await);
               argumentList.add(expression);
               lastLiteralType = this.type;
               this.addTemplateLiteralString(rawStrings, cookedStrings);
            } while (lastLiteralType == TokenType.TEMPLATE_MIDDLE);
         }

         lastLiteralType = TemplateLiteralNode.newTagged(templateToken, rawStrings.get(rawStrings.size() - 1).getFinish(), rawStrings, cookedStrings);
         argumentList.set(0, lastLiteralType);
         var16 = argumentList;
      } finally {
         this.lexer.pauseOnRightBrace = previousPauseOnRightBrace;
      }

      return var16;
   }

   private void addTemplateLiteralString(final ArrayList<Expression> rawStrings, final ArrayList<Expression> cookedStrings) {
      long stringToken = this.token;
      TruffleString rawString = this.lexer.valueOfRawString(stringToken);
      TruffleString cookedString = this.lexer.valueOfTaggedTemplateString(stringToken);
      this.next();
      Expression cookedExpression;
      if (cookedString == null) {
         cookedExpression = newUndefinedLiteral(stringToken, this.finish);
      } else {
         cookedExpression = LiteralNode.newInstance(stringToken, cookedString);
      }

      rawStrings.add(LiteralNode.newInstance(stringToken, rawString));
      cookedStrings.add(cookedExpression);
   }

   private FunctionNode module(final String moduleName) {
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
      this.functionDeclarations = new ArrayList<>();

      try {
         this.moduleBody(module);
         long yieldToken = Token.toDesc(TokenType.YIELD, functionStart, 0);
         this.prependStatement(
            new ExpressionStatement(functionLine, yieldToken, functionLine, new UnaryNode(yieldToken, newUndefinedLiteral(yieldToken, this.finish)))
         );
         script.setFlag(16777216);
         this.addFunctionDeclarations(script);
      } finally {
         this.functionDeclarations = null;
         this.restoreBlock(body);
         this.lc.pop(script);
      }

      body.setFlag(1);
      Block var16 = new Block(functionToken, this.finish, body.getFlags() | 16 | 32 | 512, body.getScope(), body.getStatements());
      script.setLastToken(this.token);
      this.expect(TokenType.EOF);
      script.setModule(module.createModule());
      return this.createFunctionNode(script, functionToken, ident, functionLine, var16);
   }

   private void moduleBody(ParserContextModuleNode module) {
      while (this.type != TokenType.EOF) {
         switch (this.type) {
            case EOF:
               return;
            case IMPORT:
               if (!this.isImportExpression()) {
                  this.importDeclaration(module);
                  break;
               }
            default:
               boolean await = this.isTopLevelAwait();
               this.statement(false, await, true, 0, false, false, false);
               break;
            case EXPORT:
               this.exportDeclaration(module);
         }
      }
   }

   private boolean isTopLevelAwait() {
      return ES2022_TOP_LEVEL_AWAIT && this.env.topLevelAwait;
   }

   private boolean isImportExpression() {
      assert this.type == TokenType.IMPORT;

      if (!this.isES2020()) {
         return false;
      } else {
         TokenType la = this.lookahead();
         return la == TokenType.PERIOD || la == TokenType.LPAREN;
      }
   }

   private void declareImportBinding(IdentNode ident, boolean star) {
      Scope moduleScope = this.lc.getCurrentBlock().getScope();

      assert moduleScope.isModuleScope();

      if (moduleScope.hasSymbol(ident.getName())) {
         throw this.error(ECMAErrors.getMessage("syntax.error.redeclare.variable", ident.getName()), ident.getToken());
      } else {
         moduleScope.putSymbol(new Symbol(ident.getNameTS(), 1026 | (star ? 0 : 16384)));
      }
   }

   private void declareImportBinding(IdentNode ident) {
      this.declareImportBinding(ident, false);
   }

   private void declareImportStarBinding(IdentNode ident) {
      this.declareImportBinding(ident, true);
   }

   private IdentNode importedBindingIdentifier() {
      return this.bindingIdentifier(false, this.isTopLevelAwait(), "imported binding");
   }

   private void importDeclaration(ParserContextModuleNode module) {
      long importToken = this.token;
      this.expect(TokenType.IMPORT);
      if (this.type != TokenType.STRING && this.type != TokenType.ESCSTRING) {
         List<Module.ImportEntry> importEntries = new ArrayList<>();
         long startToken = this.token;
         ImportClauseNode importClause;
         if (this.type == TokenType.MUL) {
            NameSpaceImportNode namespaceNode = this.nameSpaceImport();
            importClause = new ImportClauseNode(startToken, Token.descPosition(startToken), this.finish, namespaceNode);
            importEntries.add(Module.ImportEntry.importStarAsNameSpaceFrom(namespaceNode.getBindingIdentifier().getNameTS()));
         } else if (this.type == TokenType.LBRACE) {
            NamedImportsNode namedImportsNode = this.namedImports(importEntries);
            importClause = new ImportClauseNode(startToken, Token.descPosition(startToken), this.finish, namedImportsNode);
         } else {
            if (!this.isBindingIdentifier()) {
               throw this.error(AbstractParser.message("expected.import"));
            }

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
                  if (this.type != TokenType.LBRACE) {
                     throw this.error(AbstractParser.message("expected.named.import"));
                  }

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

         for (int i = 0; i < importEntries.size(); i++) {
            module.addImportEntry(importEntries.get(i).withFrom(moduleRequest));
         }
      } else {
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
      }

      this.endOfLine();
   }

   private Map<TruffleString, TruffleString> assertClause() {
      assert this.type == TokenType.ASSERT;

      this.next();
      this.expect(TokenType.LBRACE);
      Map<TruffleString, TruffleString> assertions = this.assertEntries();
      this.expect(TokenType.RBRACE);
      return assertions;
   }

   private Map<TruffleString, TruffleString> assertEntries() {
      Map<TruffleString, TruffleString> assertions = new LinkedHashMap<>();

      while (this.type != TokenType.RBRACE) {
         long errorToken = this.token;
         TruffleString assertionKey;
         if (this.type != TokenType.STRING && this.type != TokenType.ESCSTRING) {
            assertionKey = this.getIdentifierName().getNameTS();
         } else {
            assertionKey = (TruffleString)this.getValue();
            this.next();
         }

         this.expect(TokenType.COLON);
         TruffleString value = null;
         if (this.type != TokenType.STRING && this.type != TokenType.ESCSTRING) {
            this.expect(TokenType.STRING);
         } else {
            value = (TruffleString)this.getValue();
            this.next();
         }

         if (assertions.containsKey(assertionKey)) {
            throw this.error(AbstractParser.message("duplicate.import.assertion", assertionKey.toJavaStringUncached()), errorToken);
         }

         assertions.put(assertionKey, value);
         if (this.type != TokenType.COMMARIGHT) {
            break;
         }

         this.next();
      }

      return assertions;
   }

   private NameSpaceImportNode nameSpaceImport() {
      long startToken = this.token;

      assert this.type == TokenType.MUL;

      this.next();
      this.expect(TokenType.AS);
      IdentNode localNameSpace = this.importedBindingIdentifier();
      this.declareImportStarBinding(localNameSpace);
      return new NameSpaceImportNode(startToken, Token.descPosition(startToken), this.finish, localNameSpace);
   }

   private NamedImportsNode namedImports(List<Module.ImportEntry> importEntries) {
      long startToken = this.token;

      assert this.type == TokenType.LBRACE;

      this.next();
      List<ImportSpecifierNode> importSpecifiers = new ArrayList<>();

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
         } else {
            if (!bindingIdentifier) {
               throw this.error(AbstractParser.message("expected.binding.identifier"), nameToken);
            }

            this.verifyIdent(importName, false, false);
            this.verifyStrictIdent(importName, "imported binding");
            importSpecifiers.add(new ImportSpecifierNode(nameToken, Token.descPosition(nameToken), this.finish, importName, null));
            this.declareImportBinding(importName);
            importEntries.add(Module.ImportEntry.importSpecifier(importName.getNameTS()));
         }

         if (this.type != TokenType.COMMARIGHT) {
            break;
         }

         this.next();
      }

      this.expect(TokenType.RBRACE);
      return new NamedImportsNode(startToken, Token.descPosition(startToken), this.finish, importSpecifiers);
   }

   private FromNode fromClause() {
      int fromStart = this.start;
      long fromToken = this.token;
      this.expect(TokenType.FROM);
      if (this.type != TokenType.STRING && this.type != TokenType.ESCSTRING) {
         throw this.error(this.expectMessage(TokenType.STRING));
      } else {
         TruffleString moduleSpecifier = (TruffleString)this.getValue();
         long specifierToken = this.token;
         this.next();
         LiteralNode<TruffleString> specifier = LiteralNode.newInstance(specifierToken, moduleSpecifier);
         return new FromNode(fromToken, fromStart, this.finish, specifier);
      }
   }

   private void exportDeclaration(ParserContextModuleNode module) {
      long exportToken = this.token;
      Map<TruffleString, TruffleString> assertions = Map.of();
      this.expect(TokenType.EXPORT);
      boolean yield = false;
      boolean await = this.isTopLevelAwait();
      switch (this.type) {
         case LBRACE:
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
         case VAR:
         case LET:
         case CONST:
            List<Statement> statements = this.lc.getCurrentBlock().getStatements();
            int previousEnd = statements.size();
            this.variableStatement(this.type, false, await);

            for (Statement statement : statements.subList(previousEnd, statements.size())) {
               if (statement instanceof VarNode) {
                  VarNode varNode = (VarNode)statement;
                  module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, varNode.getName(), varNode));
               }
            }
            break;
         case FUNCTION: {
            FunctionNode functionDeclaration = (FunctionNode)this.functionDeclaration(true, true, false, false, await, false);
            module.addExport(
               new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, functionDeclaration.getIdent(), functionDeclaration, false)
            );
            break;
         }
         case CLASS:
         case AT:
            ClassNode classDeclaration = this.classDeclaration(false, await, false);
            module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, classDeclaration.getIdent(), classDeclaration, false));
            break;
         case DEFAULT:
            this.next();
            IdentNode ident = null;
            int lineNumber = this.line;
            long rhsToken = this.token;
            boolean hoistableDeclaration = false;
            Expression assignmentExpression;
            switch (this.type) {
               case FUNCTION:
                  assignmentExpression = this.functionDeclaration(false, true, false, false, await, true);
                  hoistableDeclaration = true;
                  break;
               case LET:
               case CONST:
               default:
                  if (this.isAsync() && this.lookaheadIsAsyncFunction()) {
                     assignmentExpression = this.asyncFunctionDeclaration(false, true, false, await, true);
                     hoistableDeclaration = true;
                     break;
                  }

                  assignmentExpression = this.assignmentExpression(true, false, await);
                  this.endOfLine();
                  break;
               case CLASS:
               case AT:
                  assignmentExpression = this.classDeclaration(false, await, true);
                  ident = ((ClassNode)assignmentExpression).getIdent();
            }

            if (hoistableDeclaration) {
               FunctionNode functionNode = (FunctionNode)assignmentExpression;

               assert functionNode.isDeclared();

               if (!functionNode.isAnonymous()) {
                  ident = functionNode.getIdent();
               }
            }

            if (ident == null) {
               ident = new IdentNode(Token.recast(rhsToken, TokenType.IDENT), this.finish, Module.DEFAULT_EXPORT_BINDING_NAME);
               if (isAnonymousFunctionDefinition(assignmentExpression)) {
                  assignmentExpression = this.setAnonymousFunctionName(assignmentExpression, Module.DEFAULT_NAME);
               }
            }

            VarNode varNode = new VarNode(
               lineNumber,
               Token.recast(rhsToken, hoistableDeclaration ? TokenType.VAR : TokenType.LET),
               this.finish,
               ident,
               assignmentExpression,
               (hoistableDeclaration ? 0 : 1) | 8
            );
            this.declareVar(this.lc.getCurrentScope(), varNode);
            if (hoistableDeclaration) {
               this.functionDeclarations.add(varNode);
            } else {
               this.lc.appendStatementToCurrentNode(varNode);
            }

            module.addExport(new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, ident, assignmentExpression, true));
            break;
         case MUL:
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
         default: {
            if (!this.isAsync() || !this.lookaheadIsAsyncFunction()) {
               throw this.error(AbstractParser.message("invalid.export"), this.token);
            }

            FunctionNode functionDeclaration = (FunctionNode)this.asyncFunctionDeclaration(true, true, false, await, false);
            module.addExport(
               new ExportNode(exportToken, Token.descPosition(exportToken), this.finish, functionDeclaration.getIdent(), functionDeclaration, false)
            );
         }
      }
   }

   private NamedExportsNode namedExports() {
      long startToken = this.token;

      assert this.type == TokenType.LBRACE;

      this.next();
      List<ExportSpecifierNode> exports = new ArrayList<>();
      long reservedWordToken = 0L;

      while (this.type != TokenType.RBRACE) {
         long nameToken = this.token;
         TokenType nameType = this.type;
         IdentNode localName = this.getIdentifierName();
         if ((isReservedWord(nameType) || isEscapedIdent(localName) && (isReservedWordSequence(localName.getName()) || isFutureStrictName(localName)))
            && reservedWordToken == 0L) {
            reservedWordToken = nameToken;
         }

         if (this.type == TokenType.AS) {
            this.next();
            IdentNode exportName = this.getIdentifierName();
            exports.add(new ExportSpecifierNode(nameToken, Token.descPosition(nameToken), this.finish, localName, exportName));
         } else {
            exports.add(new ExportSpecifierNode(nameToken, Token.descPosition(nameToken), this.finish, localName, null));
         }

         if (this.type != TokenType.COMMARIGHT) {
            break;
         }

         this.next();
      }

      this.expect(TokenType.RBRACE);
      if (reservedWordToken != 0L && this.type != TokenType.FROM) {
         throw this.error(this.expectMessage(TokenType.IDENT, reservedWordToken), reservedWordToken);
      } else {
         return new NamedExportsNode(startToken, Token.descPosition(startToken), this.finish, exports);
      }
   }

   private static boolean isReservedWord(TokenType type) {
      return type.getKind() == TokenKind.KEYWORD || type.getKind() == TokenKind.FUTURE || type.getKind() == TokenKind.FUTURESTRICT;
   }

   @Override
   public String toString() {
      return "'JavaScript Parsing'";
   }

   private void markEval() {
      this.lc.setCurrentFunctionFlag(160);
      this.lc.getCurrentScope().setHasEval();
   }

   private void prependStatement(final Statement statement) {
      this.lc.prependStatementToCurrentNode(statement);
   }

   private void appendStatement(final Statement statement) {
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
         assert fn.isDerivedConstructor();

         fn.setFlag(262144);
      }
   }

   private void markThis() {
      this.lc.setCurrentFunctionFlag(32768);
      this.addIdentifierReference(TokenType.THIS.getName());
   }

   private void markNewTarget() {
      if (!this.lc.getCurrentScope().inFunction()) {
         throw this.error(AbstractParser.message("new.target.in.function"), this.token);
      } else {
         ParserContextFunctionNode fn = this.lc.getCurrentNonArrowFunction();
         if (!fn.isProgram()) {
            fn.setFlag(8388608);
         }

         this.addIdentifierReference(NEW_TARGET_NAME.toJavaStringUncached());
      }
   }

   private static boolean markApplyArgumentsCall(final ParserContext lc, List<Expression> arguments) {
      assert arguments.size() == 2 && arguments.get(1) instanceof IdentNode && ((IdentNode)arguments.get(1)).isArguments();

      ParserContextFunctionNode currentFunction = lc.getCurrentFunction();
      if (!currentFunction.isArrow()) {
         currentFunction.setFlag(536870912);
         arguments.set(1, ((IdentNode)arguments.get(1)).setIsApplyArguments());
         return true;
      } else {
         return false;
      }
   }

   private boolean isAwait() {
      return ES8_ASYNC_FUNCTION && this.isES2017() && this.type == TokenType.AWAIT;
   }

   private boolean isAsync() {
      return ES8_ASYNC_FUNCTION && this.isES2017() && this.type == TokenType.ASYNC;
   }

   private boolean lookaheadIsAsyncArrowParameterListStart() {
      assert this.isAsync();

      int i = 1;

      TokenType t;
      do {
         t = this.T(this.k + i++);
         if (t == TokenType.LPAREN || t == TokenType.IDENT || t.isContextualKeyword()) {
            return true;
         }
      } while (t == TokenType.COMMENT);

      return false;
   }

   private boolean lookaheadIsAsyncFunction() {
      assert this.isAsync();

      int i = 1;

      while (true) {
         long currentToken = this.getToken(this.k + i);
         TokenType t = Token.descType(currentToken);
         switch (t) {
            case FUNCTION:
               return true;
            case COMMENT:
               i++;
               break;
            default:
               return false;
         }
      }
   }

   private boolean lookaheadIsAsyncMethod(boolean allowPrivate) {
      assert this.isAsync();

      int i = 1;

      while (true) {
         long currentToken = this.getToken(this.k + i);
         TokenType t = Token.descType(currentToken);
         if (t != TokenType.COMMENT) {
            return this.isPropertyName(currentToken) || t == TokenType.MUL || allowPrivate && t == TokenType.PRIVATE_IDENT;
         }

         i++;
      }
   }

   public List<Expression> decoratorList(boolean yield, boolean await) {
      assert this.isES2023();

      List<Expression> decoratorList = new ArrayList<>();

      while (this.type == TokenType.AT) {
         this.next();
         if (this.type == TokenType.LPAREN) {
            this.next();
            Expression decoratorExpression = this.expression(true, yield, await);
            this.expect(TokenType.RPAREN);
            decoratorList.add(decoratorExpression);
         } else {
            Expression decoratorExpression;
            if (this.type == TokenType.PRIVATE_IDENT) {
               decoratorExpression = this.privateIdentifierUse();
            } else {
               decoratorExpression = this.identifierReference(yield, await);
            }

            long callToken = this.token;

            while (this.type == TokenType.PERIOD) {
               this.next();
               IdentNode property = this.getIdentifierName();

               assert property != null;

               decoratorExpression = new AccessNode(callToken, this.finish, decoratorExpression, property.getNameTS());
            }

            if (this.type == TokenType.LPAREN) {
               int callLine = this.line;
               callToken = this.token;
               List<Expression> arguments = this.argumentList(yield, await);
               decoratorExpression = CallNode.forCall(callLine, callToken, decoratorExpression.getStart(), this.finish, decoratorExpression, arguments);
            }

            decoratorList.add(decoratorExpression);
         }
      }

      return decoratorList;
   }

   public Expression parseExpression() {
      try {
         this.prepareLexer(0, this.source.getLength());
         this.scanFirstToken();
         return this.expression(false, false);
      } catch (Exception var2) {
         this.handleParseException(var2);
         return null;
      }
   }

   private static final class ForVariableDeclarationListResult {
      Expression missingAssignment;
      long declarationWithInitializerToken;
      Expression init;
      Expression firstBinding;
      Expression secondBinding;

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
         if (this.init == null) {
            this.init = assignment;
         } else {
            this.init = new BinaryNode(Token.recast(this.init.getToken(), TokenType.COMMARIGHT), this.init, assignment);
         }
      }
   }

   private static class ParserState {
      private final int position;
      private final int line;
      private final int linePosition;

      ParserState(final int position, final int line, final int linePosition) {
         this.position = position;
         this.line = line;
         this.linePosition = linePosition;
      }

      Lexer createLexer(
         final Source source,
         final Lexer lexer,
         final TokenStream stream,
         final boolean scripting,
         final int ecmaScriptVersion,
         final boolean shebang,
         final boolean isModule,
         final boolean allowBigInt
      ) {
         Lexer newLexer = new Lexer(
            source, this.position, lexer.limit - this.position, stream, scripting, ecmaScriptVersion, shebang, isModule, true, allowBigInt
         );
         newLexer.restoreState(new Lexer.State(this.position, Integer.MAX_VALUE, this.line, -1, this.linePosition, TokenType.SEMICOLON));
         return newLexer;
      }
   }

   private static final class PropertyFunction {
      final Expression key;
      final FunctionNode functionNode;
      final boolean computed;

      PropertyFunction(final Expression key, final FunctionNode function, final boolean computed) {
         this.key = key;
         this.functionNode = function;
         this.computed = computed;
      }
   }

   private abstract class VerifyDestructuringPatternNodeVisitor extends NodeVisitor<LexicalContext> {
      VerifyDestructuringPatternNodeVisitor(LexicalContext lc) {
         super(lc);
      }

      @Override
      public boolean enterLiteralNode(LiteralNode<?> literalNode) {
         if (literalNode.isArray()) {
            if (literalNode.isParenthesized()) {
               throw Parser.this.error(AbstractParser.message("invalid.lvalue"), literalNode.getToken());
            } else if (((LiteralNode.ArrayLiteralNode)literalNode).hasSpread() && ((LiteralNode.ArrayLiteralNode)literalNode).hasTrailingComma()) {
               throw Parser.this.error(
                  "Rest element must be last", literalNode.getElementExpressions().get(literalNode.getElementExpressions().size() - 1).getToken()
               );
            } else {
               boolean restElement = false;

               for (Expression element : literalNode.getElementExpressions()) {
                  if (element != null) {
                     if (restElement) {
                        throw Parser.this.error("Unexpected element after rest element", element.getToken());
                     }

                     if (element.isTokenType(TokenType.SPREAD_ARRAY)) {
                        restElement = true;
                        Expression lvalue = ((UnaryNode)element).getExpression();
                        this.verifySpreadElement(lvalue);
                     } else {
                        element.accept(this);
                     }
                  }
               }

               return false;
            }
         } else {
            return this.enterDefault(literalNode);
         }
      }

      protected abstract void verifySpreadElement(Expression lvalue);

      @Override
      public boolean enterObjectNode(ObjectNode objectNode) {
         if (objectNode.isParenthesized()) {
            throw Parser.this.error(AbstractParser.message("invalid.lvalue"), objectNode.getToken());
         } else {
            boolean restElement = false;

            for (PropertyNode property : objectNode.getElements()) {
               if (property != null) {
                  if (restElement) {
                     throw Parser.this.error("Unexpected element after rest element", property.getToken());
                  }

                  Expression key = property.getKey();
                  if (key.isTokenType(TokenType.SPREAD_OBJECT)) {
                     restElement = true;
                     Expression lvalue = ((UnaryNode)key).getExpression();
                     this.verifySpreadElement(lvalue);
                  } else {
                     property.accept(this);
                  }
               }
            }

            return false;
         }
      }

      @Override
      public boolean enterPropertyNode(PropertyNode propertyNode) {
         if (propertyNode.getValue() != null) {
            propertyNode.getValue().accept(this);
            return false;
         } else {
            return this.enterDefault(propertyNode);
         }
      }

      @Override
      public boolean enterBinaryNode(BinaryNode binaryNode) {
         if (binaryNode.isTokenType(TokenType.ASSIGN)) {
            binaryNode.getLhs().accept(this);
            return false;
         } else {
            return this.enterDefault(binaryNode);
         }
      }
   }
}
