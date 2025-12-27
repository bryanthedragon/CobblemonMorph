package com.oracle.truffle.js.runtime;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import java.util.List;

public interface Evaluator {
   String FUNCTION_SOURCE_NAME = "<function>";
   String EVAL_SOURCE_NAME = "<eval>";
   String EVAL_AT_SOURCE_NAME_PREFIX = "eval at ";
   TruffleString TS_EVAL_SOURCE_NAME = Strings.constant("<eval>");
   TruffleString TS_EVAL_AT_SOURCE_NAME_PREFIX = Strings.constant("eval at ");
   TruffleString MODULE_LINK_SUFFIX = Strings.constant(":link");
   TruffleString MODULE_EVAL_SUFFIX = Strings.constant(":eval");

   ScriptNode parseEval(JSContext context, Node lastNode, Source code);

   ScriptNode parseDirectEval(JSContext context, Node lastNode, Source source, Object currEnv);

   Integer[] parseDate(JSRealm realm, String date, boolean extraLenient);

   String parseToJSON(JSContext context, String code, String name, boolean includeLoc);

   Object getDefaultNodeFactory();

   JSModuleData parseModule(JSContext context, Source source);

   JSModuleData envParseModule(JSRealm realm, Source source);

   JSModuleRecord parseJSONModule(JSRealm realm, Source source);

   JSModuleRecord hostResolveImportedModule(JSContext context, ScriptOrModule referencingScriptOrModule, Module.ModuleRequest moduleRequest);

   void moduleLinking(JSRealm realm, JSModuleRecord moduleRecord);

   Object moduleEvaluation(JSRealm realm, JSModuleRecord moduleRecord);

   JSDynamicObject getModuleNamespace(JSModuleRecord moduleRecord);

   ExportResolution resolveExport(JSModuleRecord moduleRecord, TruffleString exportName);

   ScriptNode evalCompile(JSContext context, String sourceCode, String name);

   ScriptNode parseFunction(JSContext context, String parameterList, String body, boolean generatorFunction, boolean asyncFunction, String sourceName);

   default ScriptNode parseScript(JSContext context, Source source) {
      return this.parseScript(context, source, "", "", context.getParserOptions().isStrict());
   }

   default ScriptNode parseScript(JSContext context, Source source, String prolog, String epilog, boolean isStrict) {
      return this.parseScript(context, source, prolog, epilog, isStrict, null);
   }

   ScriptNode parseScript(JSContext context, Source source, String prolog, String epilog, boolean isStrict, List<String> argumentNames);

   ScriptNode parseScript(JSContext context, String sourceString);

   Expression parseExpression(JSContext context, String sourceString);

   JavaScriptNode parseInlineScript(JSContext context, Source source, MaterializedFrame lexicalContextFrame, boolean isStrict, Node locationNode);

   void checkFunctionSyntax(
      JSContext context, JSParserOptions parserOptions, String parameterList, String body, boolean generator, boolean async, String sourceName
   );
}
