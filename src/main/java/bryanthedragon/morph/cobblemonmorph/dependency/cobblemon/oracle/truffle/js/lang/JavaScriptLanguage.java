
package com.oracle.truffle.js.lang;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.debug.DebuggerTags;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.lang.JSFileTypeDetector;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.access.InitErrorObjectNodeFactory;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSAgent;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSContextOptions;
import com.oracle.truffle.js.runtime.JSEngine;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSErrorObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.interop.JavaScriptLanguageView;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.Context;

@ProvidedTags(value={StandardTags.StatementTag.class, StandardTags.RootTag.class, StandardTags.RootBodyTag.class, StandardTags.ExpressionTag.class, StandardTags.CallTag.class, StandardTags.ReadVariableTag.class, StandardTags.WriteVariableTag.class, StandardTags.TryBlockTag.class, DebuggerTags.AlwaysHalt.class, JSTags.ObjectAllocationTag.class, JSTags.BinaryOperationTag.class, JSTags.UnaryOperationTag.class, JSTags.WriteVariableTag.class, JSTags.ReadElementTag.class, JSTags.WriteElementTag.class, JSTags.ReadPropertyTag.class, JSTags.WritePropertyTag.class, JSTags.ReadVariableTag.class, JSTags.LiteralTag.class, JSTags.FunctionCallTag.class, JSTags.BuiltinRootTag.class, JSTags.EvalCallTag.class, JSTags.ControlFlowRootTag.class, JSTags.ControlFlowBlockTag.class, JSTags.ControlFlowBranchTag.class, JSTags.DeclareTag.class, JSTags.InputNodeTag.class})
@TruffleLanguage.Registration(id="js", name="JavaScript", implementationName="GraalVM JavaScript", characterMimeTypes={"application/javascript", "text/javascript", "application/javascript+module"}, defaultMimeType="application/javascript", contextPolicy=TruffleLanguage.ContextPolicy.SHARED, dependentLanguages={"regex"}, fileTypeDetectors={JSFileTypeDetector.class}, website="https://www.graalvm.org/javascript/")
public final class JavaScriptLanguage
extends TruffleLanguage<JSRealm> {
    public static final String TEXT_MIME_TYPE = "text/javascript";
    public static final String APPLICATION_MIME_TYPE = "application/javascript";
    public static final String MODULE_MIME_TYPE = "application/javascript+module";
    public static final String JSON_MIME_TYPE = "application/json";
    public static final String SCRIPT_SOURCE_NAME_SUFFIX = ".js";
    public static final String MODULE_SOURCE_NAME_SUFFIX = ".mjs";
    public static final String JSON_SOURCE_NAME_SUFFIX = ".json";
    public static final String INTERNAL_SOURCE_URL_PREFIX = "internal:";
    public static final String NODE_ENV_PARSE_TOKEN = "%NODE_ENV_PARSE_TOKEN%";
    public static final String NAME = "JavaScript";
    public static final String IMPLEMENTATION_NAME = "GraalVM JavaScript";
    public static final String ID = "js";
    @CompilerDirectives.CompilationFinal
    private volatile JSContext languageContext;
    private volatile boolean multiContext;
    private final Assumption promiseJobsQueueEmptyAssumption = Truffle.getRuntime().createAssumption("PromiseJobsQueueEmpty");
    private static final TruffleLanguage.LanguageReference<JavaScriptLanguage> REFERENCE = TruffleLanguage.LanguageReference.create(JavaScriptLanguage.class);
    public static final OptionDescriptors OPTION_DESCRIPTORS;
    private static final OptionKey<?>[] PREINIT_CONTEXT_PATCHABLE_OPTIONS;

    @Override
    protected void finalizeContext(JSRealm realm) {
        realm.getAgent().terminate();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public CallTarget parse(TruffleLanguage.ParsingRequest parsingRequest) {
        ScriptNode program;
        Source source = parsingRequest.getSource();
        List<String> argumentNames = parsingRequest.getArgumentNames();
        JSContext context = this.getJSContext();
        assert (argumentNames != null);
        if (argumentNames.size() == 4 && argumentNames.get(0).equals(NODE_ENV_PARSE_TOKEN)) {
            String prolog = argumentNames.get(1);
            String epilog = argumentNames.get(2);
            boolean strict = Boolean.parseBoolean(argumentNames.get(3));
            program = JavaScriptLanguage.parseScript(context, source, prolog, epilog, strict, new ArrayList<String>());
        } else {
            program = JavaScriptLanguage.parseScript(context, source, "", "", context.getParserOptions().isStrict(), argumentNames);
        }
        if (context.isOptionParseOnly()) {
            return JavaScriptLanguage.createEmptyScript(context).getCallTarget();
        }
        return new ParsedProgramRoot(this, context, program).getCallTarget();
    }

    public static CallTarget getParsedProgramCallTarget(RootNode rootNode) {
        return ((ParsedProgramRoot)rootNode).directCallNode.getCallTarget();
    }

    @CompilerDirectives.TruffleBoundary
    private static ScriptNode createEmptyScript(JSContext context) {
        return ScriptNode.fromFunctionData(context, JSFunction.createEmptyFunctionData(context));
    }

    @Override
    protected ExecutableNode parse(TruffleLanguage.InlineParsingRequest request) throws Exception {
        final Source source = request.getSource();
        final MaterializedFrame requestFrame = request.getFrame();
        final JSContext context = this.getJSContext();
        final Node locationNode = request.getLocation();
        final boolean strict = JavaScriptLanguage.isStrictLocation(locationNode);
        ExecutableNode executableNode = new ExecutableNode(this){
            @Node.Child
            private JavaScriptNode expression;
            @Node.Child
            private ExportValueNode exportValueNode;
            {
                super(language);
                this.expression = this.insert(JavaScriptLanguage.parseInlineScript(context, source, requestFrame, strict, locationNode));
                this.exportValueNode = ExportValueNode.create();
            }

            @Override
            public Object execute(VirtualFrame frame) {
                assert (JavaScriptLanguage.get(this).getJSContext() == context) : "unexpected JSContext";
                Object result = this.expression.execute(frame);
                return this.exportValueNode.execute(result);
            }
        };
        return executableNode;
    }

    private static boolean isStrictLocation(Node location) {
        RootNode rootNode;
        if (location != null && (rootNode = location.getRootNode()) instanceof FunctionRootNode) {
            return ((FunctionRootNode)rootNode).getFunctionData().isStrict();
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private static ScriptNode parseScript(JSContext context, Source code, String prolog, String epilog, boolean strict, List<String> argumentNames) {
        boolean profileTime = context.getContextOptions().isProfileTime();
        long startTime = profileTime ? System.nanoTime() : 0L;
        try {
            ScriptNode scriptNode = context.getEvaluator().parseScript(context, code, prolog, epilog, strict, argumentNames.isEmpty() ? null : argumentNames);
            return scriptNode;
        }
        finally {
            if (profileTime) {
                context.getTimeProfiler().printElapsed(startTime, "parsing " + code.getName());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    protected static JavaScriptNode parseInlineScript(JSContext context, Source code, MaterializedFrame lexicalContextFrame, boolean strict, Node locationNode) {
        boolean profileTime = context.getContextOptions().isProfileTime();
        long startTime = profileTime ? System.nanoTime() : 0L;
        try {
            JavaScriptNode javaScriptNode = context.getEvaluator().parseInlineScript(context, code, lexicalContextFrame, strict, locationNode);
            return javaScriptNode;
        }
        finally {
            if (profileTime) {
                context.getTimeProfiler().printElapsed(startTime, "parsing " + code.getName());
            }
        }
    }

    @Override
    protected JSRealm createContext(TruffleLanguage.Env env) {
        CompilerAsserts.neverPartOfCompilation();
        JSContext context = this.languageContext;
        if (context == null) {
            context = this.initLanguageContext(env);
        }
        JSRealm realm = context.createRealm(env);
        context.clearInitialEnvironment();
        return realm;
    }

    private synchronized JSContext initLanguageContext(TruffleLanguage.Env env) {
        JSContext newContext;
        CompilerAsserts.neverPartOfCompilation();
        JSContext curContext = this.languageContext;
        if (curContext != null) {
            assert (curContext.getContextOptions().equals(JSContextOptions.fromOptionValues(env.getOptions())));
            return curContext;
        }
        this.languageContext = newContext = this.newJSContext(env);
        return newContext;
    }

    private JSContext newJSContext(TruffleLanguage.Env env) {
        return JSEngine.createJSContext(this, env);
    }

    @Override
    protected void initializeContext(JSRealm realm) {
        realm.initialize();
    }

    @Override
    protected boolean patchContext(JSRealm realm, TruffleLanguage.Env newEnv) {
        CompilerAsserts.neverPartOfCompilation();
        assert (realm.getContext().getLanguage() == this);
        if (JavaScriptLanguage.optionsAllowPreInitializedContext(realm.getEnv(), newEnv) && realm.patchContext(newEnv)) {
            return true;
        }
        this.languageContext = null;
        return false;
    }

    private static boolean optionsAllowPreInitializedContext(TruffleLanguage.Env preinitEnv, TruffleLanguage.Env env) {
        OptionValues preinitOptions = preinitEnv.getOptions();
        OptionValues options = env.getOptions();
        if (!preinitOptions.hasSetOptions() && !options.hasSetOptions()) {
            return true;
        }
        if (preinitOptions.equals(options)) {
            return true;
        }
        assert (preinitOptions.getDescriptors().equals(options.getDescriptors()));
        List<OptionKey<?>> ignoredOptions = Arrays.asList(PREINIT_CONTEXT_PATCHABLE_OPTIONS);
        for (OptionDescriptor descriptor : options.getDescriptors()) {
            OptionKey<?> key = descriptor.getKey();
            if (!preinitOptions.hasBeenSet(key) && !options.hasBeenSet(key) || ignoredOptions.contains(key) || preinitOptions.get(key).equals(options.get(key))) continue;
            return false;
        }
        return true;
    }

    @Override
    protected void disposeContext(JSRealm realm) {
        CompilerAsserts.neverPartOfCompilation();
        JSContext context = realm.getContext();
        JSContextOptions options = context.getContextOptions();
        if (options.isProfileTime() && options.isProfileTimePrintCumulative()) {
            context.getTimeProfiler().printCumulative();
        }
        realm.dispose();
    }

    @Override
    protected void initializeMultipleContexts() {
        this.multiContext = true;
    }

    public boolean isMultiContext() {
        return this.multiContext;
    }

    @Override
    protected boolean areOptionsCompatible(OptionValues firstOptions, OptionValues newOptions) {
        if (!firstOptions.hasSetOptions() && !newOptions.hasSetOptions()) {
            return true;
        }
        if (firstOptions.equals(newOptions)) {
            assert (JSContextOptions.fromOptionValues(firstOptions).equals(JSContextOptions.fromOptionValues(newOptions)));
            return true;
        }
        return false;
    }

    @Override
    protected OptionDescriptors getOptionDescriptors() {
        return OPTION_DESCRIPTORS;
    }

    @Override
    protected boolean isVisible(JSRealm realm, Object value2) {
        return value2 != Undefined.instance;
    }

    @Override
    protected Object getLanguageView(JSRealm context, Object value2) {
        return JavaScriptLanguageView.create(value2);
    }

    @Override
    protected Object getScope(JSRealm context) {
        return context.getTopScopeObject();
    }

    public static JSRealm getCurrentJSRealm() {
        return JSRealm.get(null);
    }

    public static JavaScriptLanguage getCurrentLanguage() {
        return JavaScriptLanguage.get(null);
    }

    public static TruffleLanguage.Env getCurrentEnv() {
        return JavaScriptLanguage.getCurrentJSRealm().getEnv();
    }

    public String getTruffleLanguageHome() {
        return this.getLanguageHome();
    }

    public static JSContext getJSContext(Context context) {
        return JavaScriptLanguage.getJSRealm(context).getContext();
    }

    public static JSRealm getJSRealm(Context context) {
        context.enter();
        try {
            context.initialize(ID);
            JSRealm jSRealm = JSRealm.get(null);
            return jSRealm;
        }
        finally {
            context.leave();
        }
    }

    public void interopBoundaryEnter(JSRealm realm) {
        realm.getAgent().interopBoundaryEnter();
    }

    public void interopBoundaryExit(JSRealm realm) {
        JSAgent agent = realm.getAgent();
        if (agent.interopBoundaryExit()) {
            if (!this.promiseJobsQueueEmptyAssumption.isValid()) {
                agent.processAllPromises(true);
            }
            if (this.getJSContext().getContextOptions().isTestV8Mode()) {
                JavaScriptLanguage.processTimeoutCallbacks(realm);
            }
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static void processTimeoutCallbacks(JSRealm realm) {
        List callbackList;
        JSAgent agent = realm.getAgent();
        while ((callbackList = (List)realm.getEmbedderData()) != null && !callbackList.isEmpty()) {
            realm.setEmbedderData(null);
            for (Object callback : callbackList) {
                JSRuntime.call(callback, Undefined.instance, JSArguments.EMPTY_ARGUMENTS_ARRAY);
            }
            agent.processAllPromises(true);
        }
    }

    public Assumption getPromiseJobsQueueEmptyAssumption() {
        return this.promiseJobsQueueEmptyAssumption;
    }

    public JSContext getJSContext() {
        return Objects.requireNonNull(this.languageContext);
    }

    public static JavaScriptLanguage get(Node node) {
        return REFERENCE.get(node);
    }

    public boolean bindMemberFunctions() {
        return this.getJSContext().getContextOptions().bindMemberFunctions();
    }

    public int getAsyncStackDepth() {
        return super.getAsynchronousStackDepth();
    }

    private static void ensureErrorClassesInitialized() {
        if (JSConfig.SubstrateVM) {
            return;
        }
        try {
            JSException.ensureInitialized();
            JSErrorObject.ensureInitialized();
            Class.forName(Errors.class.getName());
            Class.forName(TruffleStackTrace.class.getName());
            Class.forName(TruffleStackTraceElement.class.getName());
            Class.forName(InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.class.getName());
            Class.forName(TryCatchNode.GetErrorObjectNode.class.getName());
        }
        catch (ClassNotFoundException ex) {
            throw Errors.shouldNotReachHere(ex);
        }
    }

    static {
        ArrayList<OptionDescriptor> options = new ArrayList<OptionDescriptor>();
        JSContextOptions.describeOptions(options);
        OPTION_DESCRIPTORS = OptionDescriptors.create(options);
        JavaScriptLanguage.ensureErrorClassesInitialized();
        PREINIT_CONTEXT_PATCHABLE_OPTIONS = new OptionKey[]{JSContextOptions.TIMER_RESOLUTION, JSContextOptions.SHELL, JSContextOptions.V8_COMPATIBILITY_MODE, JSContextOptions.GLOBAL_PROPERTY, JSContextOptions.GLOBAL_ARGUMENTS, JSContextOptions.SCRIPTING, JSContextOptions.DIRECT_BYTE_BUFFER, JSContextOptions.INTL_402, JSContextOptions.LOAD, JSContextOptions.PRINT, JSContextOptions.CONSOLE, JSContextOptions.PERFORMANCE, JSContextOptions.CLASS_FIELDS, JSContextOptions.REGEXP_STATIC_RESULT, JSContextOptions.TIME_ZONE};
    }

    private final class ParsedProgramRoot
    extends RootNode {
        private final JSContext context;
        private final ScriptNode program;
        @Node.Child
        private DirectCallNode directCallNode;
        @Node.Child
        private ExportValueNode exportValueNode;
        @Node.Child
        private ImportValueNode importValueNode;

        private ParsedProgramRoot(TruffleLanguage<?> language, JSContext context, ScriptNode program) {
            super(language);
            this.exportValueNode = ExportValueNode.create();
            this.importValueNode = ImportValueNode.create();
            this.context = context;
            this.program = program;
            this.directCallNode = DirectCallNode.create(program.getCallTarget());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object execute(VirtualFrame frame) {
            JSRealm realm = JSRealm.get(this);
            assert (realm.getContext() == this.context) : "unexpected JSContext";
            try {
                JavaScriptLanguage.this.interopBoundaryEnter(realm);
                Object[] arguments = frame.getArguments();
                for (int i = 0; i < arguments.length; ++i) {
                    arguments[i] = this.importValueNode.executeWithTarget(arguments[i]);
                }
                arguments = this.program.argumentsToRunWithArguments(realm, arguments);
                Object result = this.directCallNode.call(arguments);
                Object object = this.exportValueNode.execute(result);
                return object;
            }
            finally {
                JavaScriptLanguage.this.interopBoundaryExit(realm);
            }
        }

        @Override
        public boolean isInternal() {
            return true;
        }

        @Override
        protected boolean isInstrumentable() {
            return false;
        }
    }
}

