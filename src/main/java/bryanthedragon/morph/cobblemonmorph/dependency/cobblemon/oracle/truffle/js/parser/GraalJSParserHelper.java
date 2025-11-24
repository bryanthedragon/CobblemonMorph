
package com.oracle.truffle.js.parser;

import com.oracle.js.parser.ErrorManager;
import com.oracle.js.parser.JSErrorType;
import com.oracle.js.parser.Lexer;
import com.oracle.js.parser.Parser;
import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.ScriptEnvironment;
import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.Scope;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.parser.internal.ir.debug.JSONWriter;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSParserOptions;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.RegexCompilerInterface;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;

public final class GraalJSParserHelper {
    private static final String NEVER_PART_OF_COMPILATION_MESSAGE = "do not parse from compiled code";
    public static final String COLON_MODULE = ":module";

    private GraalJSParserHelper() {
    }

    public static FunctionNode parseScript(JSContext context, Source truffleSource, JSParserOptions parserOptions) {
        return GraalJSParserHelper.parseScript(context, truffleSource, parserOptions, false, false, null, "", "");
    }

    public static FunctionNode parseScript(JSContext context, Source truffleSource, JSParserOptions parserOptions, boolean eval, boolean evalInFunction, Scope evalScope, String prologue, String epilogue) {
        return GraalJSParserHelper.parseSource(context, truffleSource, parserOptions, false, eval, evalInFunction, evalScope, prologue, epilogue, null);
    }

    public static FunctionNode parseScript(JSContext context, Source truffleSource, JSParserOptions parserOptions, boolean eval, boolean evalInFunction, Scope evalScope, String prologue, String epilogue, List<String> argumentNames) {
        return GraalJSParserHelper.parseSource(context, truffleSource, parserOptions, false, eval, evalInFunction, evalScope, prologue, epilogue, argumentNames);
    }

    public static FunctionNode parseModule(JSContext context, Source truffleSource, JSParserOptions parserOptions) {
        return GraalJSParserHelper.parseSource(context, truffleSource, parserOptions, true, false, false, null, "", "", null);
    }

    private static FunctionNode parseSource(JSContext context, Source truffleSource, JSParserOptions parserOptions, boolean parseModule, boolean eval, boolean evalInFunction, Scope evalScope, String prologue, String epilogue, List<String> argumentNames) {
        CharSequence code;
        CompilerAsserts.neverPartOfCompilation(NEVER_PART_OF_COMPILATION_MESSAGE);
        if (prologue.isEmpty() && epilogue.isEmpty()) {
            code = truffleSource.getCharacters();
        } else {
            StringBuilder all = new StringBuilder();
            all.append(prologue);
            all.append(truffleSource.getCharacters());
            all.append(epilogue);
            code = all;
        }
        com.oracle.js.parser.Source source = com.oracle.js.parser.Source.sourceFor(truffleSource.getName(), code, eval);
        ScriptEnvironment env = GraalJSParserHelper.makeScriptEnvironment(parserOptions);
        ErrorManager errors = eval ? new ErrorManager.ThrowErrorManager() : new ErrorManager.StringBuilderErrorManager();
        errors.setLimit(0);
        Parser parser = GraalJSParserHelper.createParser(context, env, source, errors, parserOptions);
        FunctionNode parsed = parseModule ? parser.parseModule(COLON_MODULE) : (eval ? parser.parseEval(evalInFunction, evalScope) : (argumentNames != null ? parser.parseWithArguments(argumentNames) : parser.parse()));
        if (errors.hasErrors()) {
            GraalJSParserHelper.throwErrors(truffleSource, errors);
        }
        return parsed;
    }

    public static Expression parseExpression(JSContext context, Source truffleSource, JSParserOptions parserOptions) {
        CompilerAsserts.neverPartOfCompilation(NEVER_PART_OF_COMPILATION_MESSAGE);
        CharSequence code = truffleSource.getCharacters();
        com.oracle.js.parser.Source source = com.oracle.js.parser.Source.sourceFor(truffleSource.getName(), code, true);
        ScriptEnvironment env = GraalJSParserHelper.makeScriptEnvironment(parserOptions);
        ErrorManager.ThrowErrorManager errors = new ErrorManager.ThrowErrorManager();
        errors.setLimit(0);
        Parser parser = GraalJSParserHelper.createParser(context, env, source, errors, parserOptions);
        Expression expression = parser.parseExpression();
        if (errors.hasErrors()) {
            GraalJSParserHelper.throwErrors(truffleSource, errors);
        }
        return expression;
    }

    private static Parser createParser(final JSContext context, ScriptEnvironment env, com.oracle.js.parser.Source source, ErrorManager errors, final JSParserOptions parserOptions) {
        return new Parser(env, source, errors){

            @Override
            protected void validateLexerToken(Lexer.LexerToken lexerToken) {
                if (lexerToken instanceof Lexer.RegexToken) {
                    Lexer.RegexToken regex = (Lexer.RegexToken)lexerToken;
                    if (context.getContextOptions().isValidateRegExpLiterals()) {
                        try {
                            RegexCompilerInterface.validate(context, regex.getExpression(), regex.getOptions(), parserOptions.getEcmaScriptVersion());
                        }
                        catch (JSException e) {
                            throw this.error(e.getRawMessage());
                        }
                    }
                }
            }

            @Override
            protected Function<Number, TruffleString> getNumberToStringConverter() {
                return JSRuntime::numberToString;
            }
        };
    }

    private static ScriptEnvironment makeScriptEnvironment(JSParserOptions parserOptions) {
        ScriptEnvironment.Builder builder = ScriptEnvironment.builder();
        builder.strict(parserOptions.isStrict());
        builder.ecmaScriptVersion(parserOptions.getEcmaScriptVersion());
        builder.emptyStatements(parserOptions.isEmptyStatements());
        builder.syntaxExtensions(parserOptions.isSyntaxExtensions());
        builder.scripting(parserOptions.isScripting());
        builder.shebang(parserOptions.isShebang());
        builder.constAsVar(parserOptions.isConstAsVar());
        builder.allowBigInt(parserOptions.isAllowBigInt());
        builder.annexB(parserOptions.isAnnexB());
        builder.classFields(parserOptions.isClassFields());
        builder.importAssertions(parserOptions.isImportAssertions());
        builder.privateFieldsIn(parserOptions.isPrivateFieldsIn());
        builder.topLevelAwait(parserOptions.isTopLevelAwait());
        if (parserOptions.isFunctionStatementError()) {
            builder.functionStatementBehavior(ScriptEnvironment.FunctionStatementBehavior.ERROR);
        } else {
            builder.functionStatementBehavior(ScriptEnvironment.FunctionStatementBehavior.ACCEPT);
        }
        if (parserOptions.isDumpOnError()) {
            builder.dumpOnError(new PrintWriter(System.err, true));
        }
        return builder.build();
    }

    public static void checkFunctionSyntax(JSContext context, JSParserOptions parserOptions, String parameterList, String body, boolean generator, boolean async, String sourceName) {
        CompilerAsserts.neverPartOfCompilation(NEVER_PART_OF_COMPILATION_MESSAGE);
        ScriptEnvironment env = GraalJSParserHelper.makeScriptEnvironment(parserOptions);
        ErrorManager.ThrowErrorManager errors = new ErrorManager.ThrowErrorManager();
        Parser parser = GraalJSParserHelper.createParser(context, env, com.oracle.js.parser.Source.sourceFor(sourceName, parameterList), errors, parserOptions);
        parser.parseFormalParameterList();
        parser = GraalJSParserHelper.createParser(context, env, com.oracle.js.parser.Source.sourceFor(sourceName, body), errors, parserOptions);
        parser.parseFunctionBody(generator, async);
    }

    private static void throwErrors(Source source, ErrorManager errors) {
        ParserException parserException = errors.getParserException();
        SourceSection sourceLocation = null;
        boolean isIncompleteSource = false;
        if (parserException != null) {
            isIncompleteSource = parserException.isIncompleteSource();
            if (parserException.getPosition() >= 0) {
                int length = Token.descType(parserException.getToken()) == TokenType.EOL ? 0 : Token.descLength(parserException.getToken());
                sourceLocation = source.createSection(parserException.getPosition(), length);
            }
            if (parserException.getErrorType() == JSErrorType.ReferenceError) {
                throw Errors.createReferenceError(parserException.getMessage(), sourceLocation);
            }
            assert (parserException.getErrorType() == JSErrorType.SyntaxError);
        }
        throw Errors.createSyntaxError(((ErrorManager.StringBuilderErrorManager)errors).getOutput(), sourceLocation, isIncompleteSource);
    }

    public static String parseToJSON(String code, String name, boolean includeLoc, JSParserOptions parserOptions) {
        CompilerAsserts.neverPartOfCompilation(NEVER_PART_OF_COMPILATION_MESSAGE);
        ScriptEnvironment env = GraalJSParserHelper.makeScriptEnvironment(parserOptions);
        try {
            return JSONWriter.parse(env, code, name, includeLoc);
        }
        catch (ParserException e) {
            throw Errors.createSyntaxError(e.getMessage());
        }
    }
}

