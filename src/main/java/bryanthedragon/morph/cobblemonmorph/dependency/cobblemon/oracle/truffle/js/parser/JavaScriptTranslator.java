
package com.oracle.truffle.js.parser;

import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Scope;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode;
import com.oracle.truffle.js.parser.DirectEvalContext;
import com.oracle.truffle.js.parser.GraalJSParserHelper;
import com.oracle.truffle.js.parser.GraalJSTranslator;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.parser.env.EvalEnvironment;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import java.util.List;

public final class JavaScriptTranslator
extends GraalJSTranslator {
    private JavaScriptTranslator(LexicalContext lc, NodeFactory factory, JSContext context, Source source, List<String> argumentNames, int prologLength, Environment environment, boolean isParentStrict) {
        super(lc, factory, context, source, argumentNames, prologLength, environment, isParentStrict);
    }

    private JavaScriptTranslator(NodeFactory factory, JSContext context, Source source, int prologLength, Environment environment, boolean isParentStrict) {
        this(new LexicalContext(), factory, context, source, null, prologLength, environment, isParentStrict);
    }

    public static ScriptNode translateScript(NodeFactory factory, JSContext context, Source source, boolean isParentStrict, String prologue, String epilogue) {
        return JavaScriptTranslator.translateScript(factory, context, source, isParentStrict, prologue, epilogue, null);
    }

    public static ScriptNode translateScript(NodeFactory factory, JSContext context, Source source, boolean isParentStrict, String prologue, String epilogue, List<String> argumentNames) {
        return JavaScriptTranslator.translateScript(factory, context, null, source, isParentStrict, false, false, null, prologue, epilogue, argumentNames);
    }

    public static ScriptNode translateEvalScript(NodeFactory factory, JSContext context, Source source, boolean isParentStrict, DirectEvalContext directEval) {
        Environment parentEnv = directEval == null ? null : directEval.env;
        EvalEnvironment env = new EvalEnvironment(parentEnv, factory, context, directEval != null);
        boolean evalInFunction = parentEnv != null && (parentEnv.function() == null || !parentEnv.function().isGlobal());
        return JavaScriptTranslator.translateScript(factory, context, env, source, isParentStrict, true, evalInFunction, directEval, "", "", null);
    }

    public static ScriptNode translateInlineScript(NodeFactory factory, JSContext context, Environment env, Source source, boolean isParentStrict) {
        boolean evalInFunction = env != null && env.getParent() != null;
        return JavaScriptTranslator.translateScript(factory, context, env, source, isParentStrict, true, evalInFunction, null, "", "", null);
    }

    private static ScriptNode translateScript(NodeFactory nodeFactory, JSContext context, Environment env, Source source, boolean isParentStrict, boolean isEval, boolean evalInFunction, DirectEvalContext directEval, String prologue, String epilogue, List<String> argumentNames) {
        Scope parentScope = directEval == null ? null : directEval.scope;
        FunctionNode parserFunctionNode = GraalJSParserHelper.parseScript(context, source, context.getParserOptions().putStrict(isParentStrict), isEval, evalInFunction, parentScope, prologue, epilogue, argumentNames);
        Source src = JavaScriptTranslator.applyExplicitSourceURL(source, parserFunctionNode);
        LexicalContext lc = new LexicalContext();
        if (directEval != null && directEval.enclosingClass != null) {
            lc.push(directEval.enclosingClass);
        }
        return new JavaScriptTranslator(lc, nodeFactory, context, src, argumentNames, prologue.length(), env, isParentStrict).translateScript(parserFunctionNode);
    }

    private static Source applyExplicitSourceURL(Source source, FunctionNode parserFunctionNode) {
        String explicitURL = parserFunctionNode.getSource().getExplicitURL();
        if (explicitURL != null) {
            boolean internal;
            boolean bl = internal = source.isInternal() || explicitURL.startsWith("internal:");
            if (!explicitURL.equals(source.getName()) || internal != source.isInternal()) {
                return Source.newBuilder(source).name(explicitURL).internal(internal).build();
            }
        }
        return source;
    }

    public static ScriptNode translateFunction(NodeFactory factory, JSContext context, Environment env, Source source, boolean isParentStrict, FunctionNode rootNode) {
        return JavaScriptTranslator.translateFunction(factory, context, env, source, 0, isParentStrict, rootNode);
    }

    public static ScriptNode translateFunction(NodeFactory factory, JSContext context, Environment env, Source source, int prologLength, boolean isParentStrict, FunctionNode rootNode) {
        return new JavaScriptTranslator(factory, context, source, prologLength, env, isParentStrict).translateScript(rootNode);
    }

    public static JSModuleData translateModule(NodeFactory factory, JSContext context, Source source) {
        FunctionNode parsed = GraalJSParserHelper.parseModule(context, source, context.getParserOptions().putStrict(true));
        JavaScriptTranslator translator = new JavaScriptTranslator(factory, context, source, 0, null, true);
        JSFunctionData functionData = translator.translateModule(parsed);
        return new JSModuleData(parsed.getModule(), source, functionData, functionData.getRootNode().getFrameDescriptor());
    }

    private JSFunctionData translateModule(FunctionNode functionNode) {
        if (!functionNode.isModule()) {
            throw new IllegalArgumentException("root function node is not a module");
        }
        JSFunctionExpressionNode functionExpression = (JSFunctionExpressionNode)this.transformFunction(functionNode);
        return functionExpression.getFunctionData();
    }

    @Override
    protected GraalJSTranslator newTranslator(Environment env, LexicalContext savedLC) {
        return new JavaScriptTranslator(savedLC.copy(), this.factory, this.context, this.source, this.argumentNames, this.prologLength, env, false);
    }
}

