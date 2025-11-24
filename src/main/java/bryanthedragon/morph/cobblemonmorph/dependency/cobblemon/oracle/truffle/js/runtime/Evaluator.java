
package com.oracle.truffle.js.runtime;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSParserOptions;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import java.util.List;

public interface Evaluator {
    public static final String FUNCTION_SOURCE_NAME = "<function>";
    public static final String EVAL_SOURCE_NAME = "<eval>";
    public static final String EVAL_AT_SOURCE_NAME_PREFIX = "eval at ";
    public static final TruffleString TS_EVAL_SOURCE_NAME = Strings.constant("<eval>");
    public static final TruffleString TS_EVAL_AT_SOURCE_NAME_PREFIX = Strings.constant("eval at ");
    public static final TruffleString MODULE_LINK_SUFFIX = Strings.constant(":link");
    public static final TruffleString MODULE_EVAL_SUFFIX = Strings.constant(":eval");

    public ScriptNode parseEval(JSContext var1, Node var2, Source var3);

    public ScriptNode parseDirectEval(JSContext var1, Node var2, Source var3, Object var4);

    public Integer[] parseDate(JSRealm var1, String var2, boolean var3);

    public String parseToJSON(JSContext var1, String var2, String var3, boolean var4);

    public Object getDefaultNodeFactory();

    public JSModuleData parseModule(JSContext var1, Source var2);

    public JSModuleData envParseModule(JSRealm var1, Source var2);

    public JSModuleRecord parseJSONModule(JSRealm var1, Source var2);

    public JSModuleRecord hostResolveImportedModule(JSContext var1, ScriptOrModule var2, Module.ModuleRequest var3);

    public void moduleLinking(JSRealm var1, JSModuleRecord var2);

    public Object moduleEvaluation(JSRealm var1, JSModuleRecord var2);

    public JSDynamicObject getModuleNamespace(JSModuleRecord var1);

    public ExportResolution resolveExport(JSModuleRecord var1, TruffleString var2);

    public ScriptNode evalCompile(JSContext var1, String var2, String var3);

    public ScriptNode parseFunction(JSContext var1, String var2, String var3, boolean var4, boolean var5, String var6);

    default public ScriptNode parseScript(JSContext context, Source source) {
        return this.parseScript(context, source, "", "", context.getParserOptions().isStrict());
    }

    default public ScriptNode parseScript(JSContext context, Source source, String prolog, String epilog, boolean isStrict) {
        return this.parseScript(context, source, prolog, epilog, isStrict, null);
    }

    public ScriptNode parseScript(JSContext var1, Source var2, String var3, String var4, boolean var5, List<String> var6);

    public ScriptNode parseScript(JSContext var1, String var2);

    public Expression parseExpression(JSContext var1, String var2);

    public JavaScriptNode parseInlineScript(JSContext var1, Source var2, MaterializedFrame var3, boolean var4, Node var5);

    public void checkFunctionSyntax(JSContext var1, JSParserOptions var2, String var3, String var4, boolean var5, boolean var6, String var7);
}

