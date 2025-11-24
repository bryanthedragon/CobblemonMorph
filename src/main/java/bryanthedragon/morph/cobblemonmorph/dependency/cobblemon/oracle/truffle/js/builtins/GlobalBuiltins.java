
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.io.TruffleProcessBuilder;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.GlobalBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.commonjs.GlobalCommonJSRequireBuiltins;
import com.oracle.truffle.js.builtins.helper.FloatParserNode;
import com.oracle.truffle.js.builtins.helper.StringEscape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.JSTrimWhitespaceNode;
import com.oracle.truffle.js.nodes.function.EvalNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSLoadNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSConsoleUtil;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSURLDecoder;
import com.oracle.truffle.js.runtime.builtins.JSURLEncoder;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.LinkOption;
import java.util.EnumSet;
import java.util.StringTokenizer;

public class GlobalBuiltins
extends JSBuiltinsContainer.SwitchEnum<Global> {
    public static final JSBuiltinsContainer GLOBAL_FUNCTIONS = new GlobalBuiltins();
    public static final JSBuiltinsContainer GLOBAL_SHELL = new GlobalShellBuiltins();
    public static final JSBuiltinsContainer GLOBAL_NASHORN_EXTENSIONS = new GlobalNashornScriptingBuiltins();
    public static final JSBuiltinsContainer GLOBAL_PRINT = new GlobalPrintBuiltins();
    public static final JSBuiltinsContainer GLOBAL_LOAD = new GlobalLoadBuiltins();
    public static final JSBuiltinsContainer GLOBAL_COMMONJS_REQUIRE_EXTENSIONS = new GlobalCommonJSRequireBuiltins();

    protected GlobalBuiltins() {
        super(Global.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Global builtinEnum) {
        switch (builtinEnum) {
            case isNaN: {
                return GlobalBuiltinsFactory.JSGlobalIsNaNNodeGen.create(context, builtin, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isFinite: {
                return GlobalBuiltinsFactory.JSGlobalIsFiniteNodeGen.create(context, builtin, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case parseFloat: {
                return GlobalBuiltinsFactory.JSGlobalParseFloatNodeGen.create(context, builtin, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case parseInt: {
                return GlobalBuiltinsFactory.JSGlobalParseIntNodeGen.create(context, builtin, GlobalBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case encodeURI: {
                return GlobalBuiltinsFactory.JSGlobalEncodeURINodeGen.create(context, builtin, true, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case encodeURIComponent: {
                return GlobalBuiltinsFactory.JSGlobalEncodeURINodeGen.create(context, builtin, false, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case decodeURI: {
                return GlobalBuiltinsFactory.JSGlobalDecodeURINodeGen.create(context, builtin, true, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case decodeURIComponent: {
                return GlobalBuiltinsFactory.JSGlobalDecodeURINodeGen.create(context, builtin, false, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case eval: {
                return GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.create(context, builtin, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case escape: {
                return GlobalBuiltinsFactory.JSGlobalUnEscapeNodeGen.create(context, builtin, false, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case unescape: {
                return GlobalBuiltinsFactory.JSGlobalUnEscapeNodeGen.create(context, builtin, true, GlobalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static TruffleFile resolveRelativeFilePath(String path, TruffleLanguage.Env env) {
        TruffleFile f;
        CompilerAsserts.neverPartOfCompilation();
        TruffleFile file = env.getPublicTruffleFile(path);
        if (!file.isAbsolute() && !file.exists(new LinkOption[0]) && (f = GlobalBuiltins.tryResolveCallerRelativeFilePath(path, env)) != null) {
            return f;
        }
        return file;
    }

    private static TruffleFile tryResolveCallerRelativeFilePath(String path, TruffleLanguage.Env env) {
        TruffleFile file;
        TruffleFile callerFile;
        String callerPath;
        CompilerAsserts.neverPartOfCompilation();
        Source callerSource = JSFunction.getCallerSource();
        if (callerSource != null && (callerPath = callerSource.getPath()) != null && (callerFile = env.getPublicTruffleFile(callerPath)).isAbsolute() && (file = callerFile.resolveSibling(path).normalize()).isRegularFile(new LinkOption[0])) {
            return file;
        }
        return null;
    }

    static TruffleFile getFileFromArgument(Object arg, TruffleLanguage.Env env) {
        CompilerAsserts.neverPartOfCompilation();
        try {
            String path = Strings.isTString(arg) ? Strings.toJavaString((TruffleString)arg) : JSRuntime.toJavaString(arg);
            TruffleFile file = GlobalBuiltins.resolveRelativeFilePath(path, env);
            if (!file.isRegularFile(new LinkOption[0])) {
                throw Errors.createNotAFileError(path);
            }
            return file;
        }
        catch (SecurityException e) {
            throw Errors.createErrorFromException(e);
        }
    }

    static abstract class JSGlobalImportScriptEngineGlobalBindingsNode
    extends JSBuiltinNode {
        JSGlobalImportScriptEngineGlobalBindingsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        final Object importGlobalContext(Object globalContextBindings) {
            this.doImport(globalContextBindings);
            return Undefined.instance;
        }

        @CompilerDirectives.TruffleBoundary
        private void doImport(Object globalContextBindings) {
            JSRealm realm = this.getRealm();
            JSDynamicObject globalObject = realm.getGlobalObject();
            InteropLibrary bindingsInterop = InteropLibrary.getUncached(globalContextBindings);
            try {
                Object members = bindingsInterop.getMembers(globalContextBindings);
                InteropLibrary membersInterop = InteropLibrary.getUncached(members);
                long size = membersInterop.getArraySize(members);
                for (long i = 0L; i < size; ++i) {
                    Object hashKey = membersInterop.readArrayElement(members, i);
                    InteropLibrary keyInterop = InteropLibrary.getUncached(hashKey);
                    if (!keyInterop.isString(hashKey)) continue;
                    TruffleString stringKey = keyInterop.asTruffleString(hashKey);
                    Object value2 = DynamicObjectLibrary.getUncached().getOrDefault(globalObject, stringKey, Undefined.instance);
                    if (value2 != Undefined.instance && (!(value2 instanceof ScriptEngineGlobalScopeBindingsPropertyProxy) || ((ScriptEngineGlobalScopeBindingsPropertyProxy)value2).get(globalObject) != Undefined.instance) || JSObject.getPrototype(globalObject).getShape().hasProperty(stringKey)) continue;
                    JSObjectUtil.defineProxyProperty(globalObject, stringKey, new ScriptEngineGlobalScopeBindingsPropertyProxy(stringKey, globalContextBindings, bindingsInterop), JSAttributes.getDefault());
                }
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(globalContextBindings, e, "importScriptEngineGlobalBindings", this);
            }
        }

        private static final class ScriptEngineGlobalScopeBindingsPropertyProxy
        extends PropertyProxy {
            private final TruffleString key;
            private final Object globalContextBindings;
            private final InteropLibrary bindingsInterop;

            ScriptEngineGlobalScopeBindingsPropertyProxy(TruffleString key, Object globalContextBindings, InteropLibrary bindingsInterop) {
                this.key = key;
                this.globalContextBindings = globalContextBindings;
                this.bindingsInterop = bindingsInterop;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object get(JSDynamicObject store) {
                return JSInteropUtil.readMemberOrDefault(this.globalContextBindings, this.key, Undefined.instance, this.bindingsInterop, ImportValueNode.getUncached(), null);
            }

            @Override
            public boolean set(JSDynamicObject store, Object value2) {
                JSObjectUtil.defineDataProperty(store, this.key, value2, JSAttributes.getDefault());
                return true;
            }
        }
    }

    public static abstract class JSGlobalReadBufferNode
    extends JSBuiltinNode {
        public JSGlobalReadBufferNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected final JSDynamicObject readbuffer(Object fileParam) {
            JSRealm realm = this.getRealm();
            TruffleFile file = GlobalBuiltins.getFileFromArgument(fileParam, realm.getEnv());
            try {
                JSArrayBufferObject arrayBuffer;
                byte[] bytes = file.readAllBytes();
                if (this.getContext().isOptionDirectByteBuffer()) {
                    ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
                    buffer.put(bytes);
                    buffer.rewind();
                    arrayBuffer = JSArrayBuffer.createDirectArrayBuffer(this.getContext(), realm, buffer);
                } else {
                    arrayBuffer = JSArrayBuffer.createArrayBuffer(this.getContext(), realm, bytes);
                }
                return arrayBuffer;
            }
            catch (Exception ex) {
                throw Errors.createErrorFromException(ex);
            }
        }
    }

    public static abstract class JSGlobalReadFullyNode
    extends JSBuiltinNode {
        private static final int BUFFER_SIZE = 2048;

        public JSGlobalReadFullyNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected TruffleString read(Object fileParam) {
            TruffleFile file = GlobalBuiltins.getFileFromArgument(fileParam, this.getRealm().getEnv());
            try {
                return JSGlobalReadFullyNode.readImpl(file.newBufferedReader());
            }
            catch (Exception ex) {
                throw Errors.createErrorFromException(ex);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private static TruffleString readImpl(BufferedReader reader) throws IOException {
            TruffleStringBuilder sb = Strings.builderCreate();
            char[] arr = new char[2048];
            try {
                int numChars;
                while ((numChars = reader.read(arr, 0, arr.length)) > 0) {
                    Strings.builderAppend(sb, new String(arr, 0, numChars));
                }
            }
            finally {
                reader.close();
            }
            return Strings.builderToString(sb);
        }
    }

    public static abstract class JSGlobalReadLineNode
    extends JSGlobalOperation {
        private final boolean returnNullWhenEmpty;

        public JSGlobalReadLineNode(JSContext context, JSBuiltin builtin, boolean returnNullWhenEmpty) {
            super(context, builtin);
            this.returnNullWhenEmpty = returnNullWhenEmpty;
        }

        @Specialization
        protected Object readLine(Object prompt) {
            TruffleString promptString = null;
            if (prompt != Undefined.instance) {
                promptString = this.toString1(prompt);
            }
            return this.doReadLine(promptString);
        }

        @CompilerDirectives.TruffleBoundary
        private Object doReadLine(TruffleString promptString) {
            if (promptString != null) {
                this.getRealm().getOutputWriter().print(Strings.toJavaString(promptString));
            }
            try {
                BufferedReader inReader = new BufferedReader(new InputStreamReader(this.getRealm().getEnv().in(), this.getContext().getCharset()));
                String result = inReader.readLine();
                return result == null ? (this.returnNullWhenEmpty ? Null.instance : Undefined.instance) : Strings.fromJavaString(result);
            }
            catch (Exception ex) {
                throw Errors.createError(ex.getMessage());
            }
        }
    }

    public static abstract class JSGlobalExitNode
    extends JSBuiltinNode {
        public JSGlobalExitNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isUndefined(arg)"})
        protected Object exit(Object arg) {
            return this.exit(0);
        }

        @Specialization
        protected Object exit(int exitCode) {
            this.getRealm().getEnv().getContext().closeExited(this, exitCode);
            return Undefined.instance;
        }

        @Specialization
        protected Object exit(Object arg, @Cached(value="create()") JSToNumberNode toNumberNode) {
            int exitCode = (int)JSRuntime.toInteger(toNumberNode.executeNumber(arg));
            return this.exit(exitCode);
        }
    }

    public static abstract class JSGlobalLoadWithNewGlobalNode
    extends JSGlobalLoadNode {
        public JSGlobalLoadWithNewGlobalNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected Object evalImpl(JSRealm realm, TruffleString fileName, TruffleString source, Object[] args) {
            JSRealm childRealm = realm.createChildRealm();
            JSRealm mainRealm = JSRealm.getMain(this);
            JSRealm prevRealm = mainRealm.enterRealm(this, childRealm);
            try {
                JSArrayObject argumentsArray = JSArray.createConstant(this.getContext(), childRealm, args);
                assert (JSObject.getPrototype(argumentsArray) == childRealm.getArrayPrototype());
                JSRuntime.createDataProperty(childRealm.getGlobalObject(), JSFunction.ARGUMENTS, argumentsArray);
                Object object = JSGlobalLoadWithNewGlobalNode.loadStringImpl(this.getContext(), fileName, source).run(childRealm);
                return object;
            }
            finally {
                mainRealm.leaveRealm(this, prevRealm);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        protected Object loadFromPath(TruffleString path, JSRealm realm, Object[] args) {
            JSRealm childRealm = realm.createChildRealm();
            JSRealm mainRealm = JSRealm.getMain(this);
            JSRealm prevRealm = mainRealm.enterRealm(this, childRealm);
            try {
                JSArrayObject argumentsArray = JSArray.createConstant(this.getContext(), childRealm, args);
                assert (JSObject.getPrototype(argumentsArray) == childRealm.getArrayPrototype());
                JSRuntime.createDataProperty(childRealm.getGlobalObject(), JSFunction.ARGUMENTS, argumentsArray);
                Source source = this.sourceFromPath(Strings.toJavaString(path), childRealm);
                Object object = this.runImpl(childRealm, source);
                return object;
            }
            finally {
                mainRealm.leaveRealm(this, prevRealm);
            }
        }
    }

    @ImportStatic(value={JSInteropUtil.class, JSConfig.class})
    public static abstract class JSGlobalLoadNode
    extends JSLoadOperation {
        public JSGlobalLoadNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object loadString(TruffleString path, Object[] args) {
            JSRealm realm = this.getRealm();
            return this.loadFromPath(path, realm, args);
        }

        protected Object loadFromPath(TruffleString path, JSRealm realm, Object[] args) {
            Source source = this.sourceFromPath(Strings.toJavaString(path), realm);
            return this.runImpl(realm, source);
        }

        @Specialization(guards={"isForeignObject(scriptObj)"})
        protected Object loadTruffleObject(Object scriptObj, Object[] args, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object unboxed;
            JSRealm realm = this.getRealm();
            TruffleLanguage.Env env = realm.getEnv();
            if (env.isHostObject(scriptObj)) {
                if (this.getContext().isOptionNashornCompatibilityMode() && env.asHostObject(scriptObj) instanceof URL) {
                    return this.loadURL(realm, (URL)env.asHostObject(scriptObj));
                }
                if (interop.isMemberInvocable(scriptObj, "getPath")) {
                    return this.loadFile(realm, this.fileGetPath(scriptObj, interop));
                }
            }
            if ((unboxed = JSInteropUtil.toPrimitiveOrDefault(scriptObj, Null.instance, interop, this)) == Null.instance) {
                throw JSGlobalLoadNode.cannotLoadScript(scriptObj);
            }
            TruffleString stringPath = this.toString1(unboxed);
            return this.loadFromPath(stringPath, realm, args);
        }

        private String fileGetPath(Object scriptObj, InteropLibrary interop) {
            try {
                return interop.asString(interop.invokeMember(scriptObj, "getPath", new Object[0]));
            }
            catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(scriptObj, e, "getPath", this);
            }
        }

        @Specialization(guards={"isJSObject(scriptObj)"})
        protected Object loadScriptObj(JSDynamicObject scriptObj, Object[] args) {
            if (JSObject.hasProperty(scriptObj, Strings.EVAL_OBJ_FILE_NAME) && JSObject.hasProperty(scriptObj, Strings.EVAL_OBJ_SOURCE)) {
                Object scriptNameObj = JSObject.get(scriptObj, Strings.EVAL_OBJ_FILE_NAME);
                Object sourceObj = JSObject.get(scriptObj, Strings.EVAL_OBJ_SOURCE);
                return this.evalObjectLiteral(scriptNameObj, sourceObj, args);
            }
            throw JSGlobalLoadNode.cannotLoadScript(scriptObj);
        }

        private Object evalObjectLiteral(Object scriptName, Object scriptSource, Object[] args) {
            JSRealm realm = this.getRealm();
            return this.evalImpl(realm, this.toString1(scriptName), this.toString1(scriptSource), args);
        }

        @Specialization(guards={"!isString(fileName)", "!isForeignObject(fileName)", "!isJSObject(fileName)"})
        protected Object loadConvertToString(Object fileName, Object[] args) {
            return this.loadString(this.toString1(fileName), args);
        }

        protected Object loadFile(JSRealm realm, String filePath) {
            return this.runImpl(realm, this.sourceFromFileName(filePath, realm));
        }

        protected Object loadURL(JSRealm realm, URL url) {
            assert (this.getContext().isOptionNashornCompatibilityMode());
            return this.runImpl(realm, this.sourceFromURL(url));
        }

        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected Object evalImpl(JSRealm realm, TruffleString fileName, TruffleString source, Object[] args) {
            return JSGlobalLoadNode.loadStringImpl(this.getContext(), fileName, source).run(realm);
        }
    }

    public static abstract class JSGlobalPrintNode
    extends JSGlobalOperation {
        private final ConditionProfile argumentsCount = ConditionProfile.createBinaryProfile();
        private final BranchProfile consoleIndentation = BranchProfile.create();
        private final boolean useErr;
        private final boolean noNewLine;

        public JSGlobalPrintNode(JSContext context, JSBuiltin builtin, boolean useErr, boolean noNewline) {
            super(context, builtin);
            this.useErr = useErr;
            this.noNewLine = noNewline;
        }

        public abstract Object executeObjectArray(Object[] var1);

        @Specialization
        protected Object print(Object[] arguments) {
            TruffleStringBuilder builder = Strings.builderCreate();
            JSConsoleUtil consoleUtil = this.getRealm().getConsoleUtil();
            if (consoleUtil.getConsoleIndentation() > 0) {
                this.consoleIndentation.enter();
                Strings.builderAppend(builder, consoleUtil.getConsoleIndentationString());
            }
            if (this.argumentsCount.profile(arguments.length == 1)) {
                Strings.builderAppend(builder, this.toString1(arguments[0]));
            } else {
                for (int i = 0; i < arguments.length; ++i) {
                    if (i != 0) {
                        Strings.builderAppend(builder, ' ');
                    }
                    Strings.builderAppend(builder, this.toString1(arguments[i]));
                }
            }
            return this.printIntl(builder);
        }

        @CompilerDirectives.TruffleBoundary
        private Object printIntl(TruffleStringBuilder builder) {
            JSRealm realm = this.getRealm();
            if (!this.noNewLine) {
                Strings.builderAppend(builder, Strings.LINE_SEPARATOR);
            }
            PrintWriter writer = this.useErr ? realm.getErrorWriter() : realm.getOutputWriter();
            writer.print(Strings.builderToString(builder));
            writer.flush();
            return Undefined.instance;
        }
    }

    public static abstract class JSGlobalUnEscapeNode
    extends JSGlobalOperation {
        private final boolean unescape;

        public JSGlobalUnEscapeNode(JSContext context, JSBuiltin builtin, boolean unescape) {
            super(context, builtin);
            this.unescape = unescape;
        }

        @Specialization
        protected TruffleString escape(Object value2) {
            TruffleString s = this.toString1(value2);
            return this.unescape ? StringEscape.unescape(s) : StringEscape.escape(s);
        }
    }

    public static abstract class JSGlobalIndirectEvalNode
    extends JSBuiltinNode {
        @Node.Child
        private IndirectCallNode callNode = IndirectCallNode.create();

        public JSGlobalIndirectEvalNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object indirectEvalString(TruffleString source) {
            JSRealm realm = this.getRealm();
            return this.parseIndirectEval(realm, Strings.toJavaString(source)).runEval(this.callNode, realm);
        }

        @Specialization(guards={"isForeignObject(source)"}, limit="3")
        protected Object indirectEvalForeignObject(Object source, @CachedLibrary(value="source") InteropLibrary interop) {
            if (interop.isString(source)) {
                try {
                    return this.indirectEvalString(interop.asTruffleString(source));
                }
                catch (UnsupportedMessageException ex) {
                    throw Errors.createTypeErrorInteropException(source, ex, "asString", this);
                }
            }
            return source;
        }

        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        private ScriptNode parseIndirectEval(JSRealm realm, String sourceCode) {
            String sourceName = null;
            if (this.isCallerSensitive()) {
                sourceName = EvalNode.findAndFormatEvalOrigin(realm.getCallNode(), realm.getContext());
            }
            if (sourceName == null) {
                sourceName = "<eval>";
            }
            Source source = Source.newBuilder("js", sourceCode, sourceName).build();
            return this.getContext().getEvaluator().parseEval(this.getContext(), this, source);
        }

        @Specialization
        protected int indirectEvalInt(int source) {
            return source;
        }

        @Specialization
        protected SafeInteger indirectEvalSafeInteger(SafeInteger source) {
            return source;
        }

        @Specialization
        protected long indirectEvalLong(long source) {
            return source;
        }

        @Specialization
        protected double indirectEvalDouble(double source) {
            return source;
        }

        @Specialization
        protected boolean indirectEvalBoolean(boolean source) {
            return source;
        }

        @Specialization
        protected Symbol indirectEvalSymbol(Symbol source) {
            return source;
        }

        @Specialization
        protected BigInt indirectEvalBigInt(BigInt source) {
            return source;
        }

        @Specialization(guards={"isJSDynamicObject(object)"})
        public JSDynamicObject indirectEvalJSType(JSDynamicObject object) {
            return object;
        }

        @Override
        public boolean isCallerSensitive() {
            return this.getContext().isOptionV8CompatibilityMode();
        }
    }

    public static abstract class JSGlobalDecodeURINode
    extends JSGlobalOperation {
        private final JSURLDecoder decoder;

        public JSGlobalDecodeURINode(JSContext context, JSBuiltin builtin, boolean isSpecial) {
            super(context, builtin);
            this.decoder = new JSURLDecoder(isSpecial);
        }

        @Specialization
        protected Object decodeURI(Object value2) {
            return this.decoder.decode(this.toString1(value2));
        }
    }

    public static abstract class JSGlobalEncodeURINode
    extends JSGlobalOperation {
        private final JSURLEncoder encoder;

        public JSGlobalEncodeURINode(JSContext context, JSBuiltin builtin, boolean isSpecial) {
            super(context, builtin);
            this.encoder = new JSURLEncoder(isSpecial);
        }

        @Specialization
        protected TruffleString encodeURI(Object value2) {
            return this.encoder.encode(this.toString1(value2));
        }
    }

    public static abstract class JSGlobalParseIntNode
    extends JSBuiltinNode {
        @Node.Child
        private JSToInt32Node toInt32Node;
        private final BranchProfile needsNaN = BranchProfile.create();

        public JSGlobalParseIntNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected int toInt32(Object target) {
            if (this.toInt32Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toInt32Node = this.insert(JSToInt32Node.create());
            }
            return this.toInt32Node.executeInt(target);
        }

        @Specialization(guards={"isUndefined(radix0)"})
        protected int parseIntNoRadix(int value2, Object radix0) {
            return value2;
        }

        @Specialization(guards={"!isUndefined(radix0)"})
        protected Object parseIntInt(int value2, Object radix0, @Cached BranchProfile needsRadixConversion) {
            int radix = this.toInt32(radix0);
            if (radix == 10 || radix == 0) {
                return value2;
            }
            if (radix < 2 || radix > 36) {
                this.needsNaN.enter();
                return Double.NaN;
            }
            needsRadixConversion.enter();
            return JSGlobalParseIntNode.convertToRadix(value2, radix);
        }

        @Specialization(guards={"hasRegularToStringInInt32Range(value)", "isUndefined(radix0)"})
        protected int parseIntDoubleToInt(double value2, Object radix0) {
            return (int)value2;
        }

        @Specialization(guards={"hasRegularToString(value)", "isUndefined(radix0)"})
        protected double parseIntDoubleNoRadix(double value2, Object radix0) {
            return JSRuntime.truncateDouble(value2);
        }

        protected static boolean hasRegularToString(double value2) {
            return -1.0E21 < value2 && value2 <= -1.0E-6 || 1.0E-6 <= value2 && value2 < 1.0E21;
        }

        protected static boolean hasRegularToStringInInt32Range(double value2) {
            return -2.147483649E9 < value2 && value2 <= -1.0 || value2 == 0.0 || 1.0E-6 <= value2 && value2 < 2.147483648E9;
        }

        @Specialization(guards={"hasRegularToString(value)"})
        protected double parseIntDouble(double value2, Object radix0, @Cached BranchProfile needsRadixConversion) {
            int radix = this.toInt32(radix0);
            if (radix == 0) {
                radix = 10;
            } else if (radix < 2 || radix > 36) {
                this.needsNaN.enter();
                return Double.NaN;
            }
            double truncated = JSRuntime.truncateDouble(value2);
            if (radix == 10) {
                return truncated;
            }
            needsRadixConversion.enter();
            return JSGlobalParseIntNode.convertToRadix(truncated, radix);
        }

        @Specialization(guards={"radix == 10", "stringLength(string) < 15"})
        protected Object parseIntStringInt10(TruffleString string, int radix, @Cached TruffleString.ReadCharUTF16Node readRawNode) {
            long signedValue;
            assert (JSGlobalParseIntNode.isShortStringInt10(string, radix));
            int pos = 0;
            int lastIdx = Strings.length(string);
            boolean negate = false;
            if (lastIdx == 0) {
                return Double.NaN;
            }
            char firstChar = Strings.charAt(readRawNode, string, pos);
            if (!JSRuntime.isAsciiDigit(firstChar)) {
                if (JSRuntime.isWhiteSpace(firstChar)) {
                    pos = JSRuntime.firstNonWhitespaceIndex(string, false, readRawNode);
                    if (Strings.length(string) <= pos) {
                        return Double.NaN;
                    }
                    firstChar = Strings.charAt(readRawNode, string, pos);
                }
                if (firstChar == '-') {
                    ++pos;
                    negate = true;
                } else if (firstChar == '+') {
                    ++pos;
                }
                if (pos >= lastIdx) {
                    return Double.NaN;
                }
            }
            int firstPos = pos;
            long value2 = 0L;
            while (pos < lastIdx) {
                char c = Strings.charAt(readRawNode, string, pos);
                int cval = JSRuntime.valueInRadix10(c);
                if (cval < 0) {
                    if (pos != firstPos) break;
                    return Double.NaN;
                }
                value2 *= 10L;
                value2 += (long)cval;
                ++pos;
            }
            if (value2 == 0L && negate) {
                return -0.0;
            }
            assert (value2 >= 0L);
            long l = signedValue = negate ? -value2 : value2;
            if (value2 <= Integer.MAX_VALUE) {
                return (int)signedValue;
            }
            return (double)signedValue;
        }

        protected static boolean isShortStringInt10(Object input, Object radix) {
            return Strings.isTString(input) && Strings.length((TruffleString)input) < 15 && radix instanceof Integer && (Integer)radix == 10;
        }

        @Specialization(guards={"!isShortStringInt10(input, radix0)"})
        protected Object parseIntGeneric(Object input, Object radix0, @Cached JSToStringNode toStringNode, @Cached BranchProfile needsRadix16, @Cached BranchProfile needsDontFitLong, @Cached TruffleString.ReadCharUTF16Node readRawNode, @Cached TruffleString.SubstringByteIndexNode substringNode) {
            TruffleString inputStr = toStringNode.executeString(input);
            int firstIdx = JSRuntime.firstNonWhitespaceIndex(inputStr, false, readRawNode);
            int lastIdx = JSRuntime.lastNonWhitespaceIndex(inputStr, false, readRawNode) + 1;
            int radix = this.toInt32(radix0);
            if (lastIdx <= firstIdx) {
                this.needsNaN.enter();
                return Double.NaN;
            }
            char firstChar = Strings.charAt(readRawNode, inputStr, firstIdx);
            boolean negate = false;
            if (firstChar == '-') {
                negate = true;
                ++firstIdx;
            } else if (firstChar == '+') {
                ++firstIdx;
            }
            if (radix == 16 || radix == 0) {
                needsRadix16.enter();
                if (JSGlobalParseIntNode.hasHexStart(readRawNode, inputStr, firstIdx, lastIdx)) {
                    firstIdx += 2;
                    radix = 16;
                } else if (radix == 0) {
                    radix = 10;
                }
            } else if (radix < 2 || radix > 36) {
                this.needsNaN.enter();
                return Double.NaN;
            }
            int lastValidIdx = JSGlobalParseIntNode.validStringLastIdx(readRawNode, inputStr, radix, firstIdx, lastIdx);
            int len = lastValidIdx - firstIdx;
            if (len <= 0) {
                this.needsNaN.enter();
                return Double.NaN;
            }
            if (radix <= 10 && len >= 18 || 10 < radix && radix <= 16 && len >= 15 || radix > 16 && len >= 12) {
                needsDontFitLong.enter();
                if (radix == 10) {
                    return JSGlobalParseIntNode.parseDouble(Strings.lazySubstring(substringNode, inputStr, firstIdx, len), negate);
                }
                return JSRuntime.parseRawDontFitLong(inputStr, radix, firstIdx, lastValidIdx, negate);
            }
            return JSRuntime.parseRawFitsLong(inputStr, radix, firstIdx, lastValidIdx, negate);
        }

        @CompilerDirectives.TruffleBoundary
        private static double parseDouble(TruffleString s, boolean negate) {
            double value2 = Double.parseDouble(Strings.toJavaString(s));
            return negate ? -value2 : value2;
        }

        private static Object convertToRadix(int inputValue, int radix) {
            assert (radix >= 2 && radix <= 36);
            boolean negative = inputValue < 0;
            long value2 = inputValue;
            if (negative) {
                value2 = -value2;
            }
            long result = 0L;
            long radixVal = 1L;
            while (value2 != 0L) {
                long digit = value2 % 10L;
                value2 /= 10L;
                if (digit >= (long)radix) {
                    if (value2 == 0L) {
                        return Double.NaN;
                    }
                    result = 0L;
                    radixVal = 1L;
                    continue;
                }
                result += digit * radixVal;
                radixVal *= (long)radix;
            }
            if (negative) {
                result = -result;
            }
            return JSRuntime.longToIntOrDouble(result);
        }

        private static double convertToRadix(double inputValue, int radix) {
            assert (radix >= 2 && radix <= 36);
            boolean negative = inputValue < 0.0;
            double result = 0.0;
            double radixVal = 1.0;
            for (double value2 = negative ? -inputValue : inputValue; value2 != 0.0; value2 /= 10.0) {
                double digit = value2 % 10.0;
                value2 -= digit;
                if (digit >= (double)radix) {
                    if (value2 == 0.0) {
                        return Double.NaN;
                    }
                    result = 0.0;
                    radixVal = 1.0;
                    continue;
                }
                result += digit * radixVal;
                radixVal *= (double)radix;
            }
            return negative ? -result : result;
        }

        private static boolean hasHexStart(TruffleString.ReadCharUTF16Node readRawNode, TruffleString inputString, int firstPos, int lastPos) {
            int length = lastPos - firstPos;
            if (length >= 2 && Strings.charAt(readRawNode, inputString, firstPos) == '0') {
                char c1 = Strings.charAt(readRawNode, inputString, firstPos + 1);
                return c1 == 'x' || c1 == 'X';
            }
            return false;
        }

        private static int validStringLastIdx(TruffleString.ReadCharUTF16Node readRawNode, TruffleString input, int radix, int firstIdx, int lastIdx) {
            char c;
            int pos;
            for (pos = firstIdx; pos < lastIdx && JSRuntime.valueInRadix(c = Strings.charAt(readRawNode, input, pos), radix) != -1; ++pos) {
            }
            return pos;
        }
    }

    public static abstract class JSGlobalParseFloatNode
    extends JSGlobalOperation {
        @Node.Child
        protected JSTrimWhitespaceNode trimWhitespaceNode;
        @Node.Child
        protected TruffleString.RegionEqualByteIndexNode regionEqualsNode;
        @Node.Child
        protected FloatParserNode floatParserNode;
        private static final int INFINITY_LENGTH = "Infinity".length();

        public JSGlobalParseFloatNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int parseFloatInt(int value2) {
            return value2;
        }

        @Specialization
        protected double parseFloatDouble(double value2, @Cached(value="createBinaryProfile()") ConditionProfile negativeZero) {
            if (negativeZero.profile(JSRuntime.isNegativeZero(value2))) {
                return 0.0;
            }
            return value2;
        }

        @Specialization
        protected double parseFloatBoolean(boolean value2) {
            return Double.NaN;
        }

        @Specialization(guards={"isUndefined(value)"})
        protected double parseFloatUndefined(Object value2) {
            return Double.NaN;
        }

        @Specialization(guards={"isJSNull(value)"})
        protected double parseFloatNull(Object value2) {
            return Double.NaN;
        }

        @Specialization
        protected double parseFloat(TruffleString value2) {
            return this.parseFloatIntl(value2);
        }

        @Specialization(guards={"!isJSNull(value)", "!isUndefined(value)", "!isString(value)"})
        protected double parseFloat(TruffleObject value2) {
            return this.parseFloatIntl(this.toString1(value2));
        }

        private double parseFloatIntl(TruffleString inputString) {
            TruffleString trimmedString = this.trimWhitespace(inputString);
            return this.parseFloatIntl2(trimmedString);
        }

        private double parseFloatIntl2(TruffleString trimmedString) {
            int trimmedLength = Strings.length(trimmedString);
            if (trimmedLength >= INFINITY_LENGTH) {
                if (this.regionEqualsNode == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.regionEqualsNode = this.insert(TruffleString.RegionEqualByteIndexNode.create());
                }
                if (Strings.startsWith(this.regionEqualsNode, trimmedString, Strings.INFINITY) || Strings.startsWith(this.regionEqualsNode, trimmedString, Strings.POSITIVE_INFINITY)) {
                    return Double.POSITIVE_INFINITY;
                }
                if (Strings.startsWith(this.regionEqualsNode, trimmedString, Strings.NEGATIVE_INFINITY)) {
                    return Double.NEGATIVE_INFINITY;
                }
            }
            if (this.floatParserNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.floatParserNode = this.insert(FloatParserNode.create());
            }
            return this.floatParserNode.parse(trimmedString);
        }

        protected TruffleString trimWhitespace(TruffleString s) {
            if (this.trimWhitespaceNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.trimWhitespaceNode = this.insert(JSTrimWhitespaceNode.create());
            }
            return this.trimWhitespaceNode.executeString(s);
        }
    }

    public static abstract class JSGlobalIsFiniteNode
    extends JSBuiltinNode {
        public JSGlobalIsFiniteNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean isFiniteInt(int value2) {
            return true;
        }

        @Specialization
        protected static boolean isFiniteDouble(double value2) {
            return !Double.isInfinite(value2) && !Double.isNaN(value2);
        }

        @Specialization(guards={"!isUndefined(value)"})
        protected static boolean isFiniteGeneric(Object value2, @Cached JSToDoubleNode toDoubleNode) {
            return JSGlobalIsFiniteNode.isFiniteDouble(toDoubleNode.executeDouble(value2));
        }

        @Specialization(guards={"isUndefined(value)"})
        protected static boolean isFiniteUndefined(Object value2) {
            return false;
        }
    }

    public static abstract class JSGlobalIsNaNNode
    extends JSBuiltinNode {
        public JSGlobalIsNaNNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean isNaNInt(int value2) {
            return false;
        }

        @Specialization
        protected static boolean isNaNDouble(double value2) {
            return Double.isNaN(value2);
        }

        @Specialization(guards={"!isUndefined(value)"})
        protected static boolean isNaNGeneric(Object value2, @Cached JSToDoubleNode toDoubleNode) {
            return JSGlobalIsNaNNode.isNaNDouble(toDoubleNode.executeDouble(value2));
        }

        @Specialization(guards={"isUndefined(value)"})
        protected static boolean isNaNUndefined(Object value2) {
            return true;
        }
    }

    public static abstract class JSLoadOperation
    extends JSFileLoadingOperation {
        @Node.Child
        private JSLoadNode loadNode;
        public static final String LOAD_CLASSPATH = "classpath:";
        public static final String LOAD_FX = "fx:";
        public static final String LOAD_NASHORN = "nashorn:";
        public static final String RESOURCES_PATH = "resources/";
        public static final String FX_RESOURCES_PATH = "resources/fx/";
        public static final String NASHORN_BASE_PATH = "jdk/nashorn/internal/runtime/";
        public static final String NASHORN_PARSER_JS = "nashorn:parser.js";
        public static final String NASHORN_MOZILLA_COMPAT_JS = "nashorn:mozilla_compat.js";

        public JSLoadOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected final Object runImpl(JSRealm realm, Source source) {
            if (this.loadNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.loadNode = this.insert(JSLoadNode.create(this.getContext()));
            }
            return this.loadNode.executeLoad(source, realm);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        protected static ScriptNode loadStringImpl(JSContext ctxt, TruffleString name, TruffleString script) {
            CompilerAsserts.neverPartOfCompilation();
            long startTime = ctxt.getContextOptions().isProfileTime() ? System.nanoTime() : 0L;
            try {
                ScriptNode scriptNode = ctxt.getEvaluator().evalCompile(ctxt, Strings.toJavaString(script), Strings.toJavaString(name));
                return scriptNode;
            }
            finally {
                if (ctxt.getContextOptions().isProfileTime()) {
                    ctxt.getTimeProfiler().printElapsed(startTime, "parsing " + name);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        protected final Source sourceFromURL(URL url) {
            assert (this.getContext().isOptionNashornCompatibilityMode() || this.getContext().isOptionLoadFromURL());
            try {
                return Source.newBuilder("js", url).name(url.getFile()).build();
            }
            catch (IOException | SecurityException e) {
                throw JSException.create(JSErrorType.EvalError, e.getMessage(), e, this);
            }
        }

        @CompilerDirectives.TruffleBoundary
        protected final Source sourceFromFileName(String fileName, JSRealm realm) {
            try {
                return Source.newBuilder("js", realm.getEnv().getPublicTruffleFile(fileName)).name(fileName).build();
            }
            catch (IOException | SecurityException e) {
                throw JSException.create(JSErrorType.EvalError, e.getMessage(), e, this);
            }
        }

        @Override
        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected Source sourceFromPath(String path, JSRealm realm) {
            Source source = null;
            JSContext ctx = this.getContext();
            if ((ctx.isOptionNashornCompatibilityMode() || ctx.isOptionLoadFromURL() || ctx.isOptionLoadFromClasspath()) && path.indexOf(58) != -1 && (source = this.sourceFromURI(path, realm)) != null) {
                return source;
            }
            try {
                TruffleFile file = GlobalBuiltins.resolveRelativeFilePath(path, realm.getEnv());
                if (file.isRegularFile(new LinkOption[0])) {
                    source = this.sourceFromTruffleFile(file);
                }
            }
            catch (SecurityException e) {
                throw Errors.createErrorFromException(e);
            }
            if (source == null) {
                throw JSLoadOperation.cannotLoadScript(path);
            }
            return source;
        }

        private Source sourceFromURI(String resource, JSRealm realm) {
            block10: {
                CompilerAsserts.neverPartOfCompilation();
                if (JSConfig.SubstrateVM) {
                    return null;
                }
                if (this.getContext().isOptionNashornCompatibilityMode() && (resource.startsWith(LOAD_NASHORN) || resource.startsWith(LOAD_CLASSPATH) || resource.startsWith(LOAD_FX)) || this.getContext().isOptionLoadFromClasspath() && resource.startsWith(LOAD_CLASSPATH)) {
                    return this.sourceFromResourceURL(resource);
                }
                if (this.getContext().isOptionNashornCompatibilityMode() || this.getContext().isOptionLoadFromURL()) {
                    try {
                        URL url = new URL(resource);
                        if ("file".equals(url.getProtocol())) {
                            String path = url.getPath();
                            if (!path.isEmpty()) {
                                TruffleLanguage.Env env = realm.getEnv();
                                if (env.getFileNameSeparator().equals("\\") && path.startsWith("/")) {
                                    path = path.substring(1);
                                }
                                try {
                                    TruffleFile file = env.getPublicTruffleFile(path);
                                    return this.sourceFromTruffleFile(file);
                                }
                                catch (SecurityException e) {
                                    throw Errors.createErrorFromException(e);
                                }
                            }
                            break block10;
                        }
                        return this.sourceFromURL(url);
                    }
                    catch (MalformedURLException malformedURLException) {
                        // empty catch block
                    }
                }
            }
            return null;
        }

        private Source sourceFromResourceURL(String resource) {
            CompilerAsserts.neverPartOfCompilation();
            assert (this.getContext().isOptionNashornCompatibilityMode() || this.getContext().isOptionLoadFromClasspath());
            InputStream stream = null;
            if (resource.startsWith(LOAD_NASHORN)) {
                if (NASHORN_PARSER_JS.equals(resource) || NASHORN_MOZILLA_COMPAT_JS.equals(resource)) {
                    stream = JSContext.class.getResourceAsStream(RESOURCES_PATH + resource.substring(LOAD_NASHORN.length()));
                }
            } else if (!JSConfig.SubstrateVM) {
                if (resource.startsWith(LOAD_CLASSPATH)) {
                    stream = ClassLoader.getSystemResourceAsStream(resource.substring(LOAD_CLASSPATH.length()));
                } else if (resource.startsWith(LOAD_FX)) {
                    stream = ClassLoader.getSystemResourceAsStream("jdk/nashorn/internal/runtime/resources/fx/" + resource.substring(LOAD_FX.length()));
                }
            }
            if (stream != null) {
                Source source;
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                try {
                    source = Source.newBuilder("js", reader, resource).build();
                }
                catch (Throwable throwable) {
                    try {
                        try {
                            ((Reader)reader).close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                        throw throwable;
                    }
                    catch (IOException | SecurityException exception) {
                        // empty catch block
                    }
                }
                ((Reader)reader).close();
                return source;
            }
            return null;
        }
    }

    public static abstract class JSFileLoadingOperation
    extends JSGlobalOperation {
        protected JSFileLoadingOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected Source sourceFromPath(String path, JSRealm realm) {
            Source source = null;
            try {
                TruffleFile file = GlobalBuiltins.resolveRelativeFilePath(path, realm.getEnv());
                if (file.isRegularFile(new LinkOption[0])) {
                    source = this.sourceFromTruffleFile(file);
                }
            }
            catch (SecurityException e) {
                throw Errors.createErrorFromException(e);
            }
            if (source == null) {
                throw JSFileLoadingOperation.cannotLoadScript(path);
            }
            return source;
        }

        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected static JSException cannotLoadScript(Object script) {
            return Errors.createTypeError("Cannot load script: " + JSRuntime.safeToString(script));
        }

        @CompilerDirectives.TruffleBoundary
        protected final Source sourceFromTruffleFile(TruffleFile file) {
            try {
                return Source.newBuilder("js", file).build();
            }
            catch (IOException | SecurityException e) {
                throw JSException.create(JSErrorType.EvalError, e.getMessage(), e, this);
            }
        }
    }

    private static abstract class JSGlobalOperation
    extends JSBuiltinNode {
        @Node.Child
        private JSToStringNode toString1Node;

        JSGlobalOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected final TruffleString toString1(Object target) {
            if (this.toString1Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toString1Node = this.insert(JSToStringNode.create());
            }
            return this.toString1Node.executeString(target);
        }
    }

    public static abstract class GlobalScriptingEXECNode
    extends JSBuiltinNode {
        public GlobalScriptingEXECNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object exec(Object cmd, Object input) {
            String cmdStr = JSRuntime.toJavaString(cmd);
            String inputStr = input != Undefined.instance ? JSRuntime.toJavaString(input) : null;
            return this.execIntl(cmdStr, inputStr);
        }

        @CompilerDirectives.TruffleBoundary
        private Object execIntl(String cmd, String input) {
            JSRealm realm = this.getRealm();
            TruffleLanguage.Env env = realm.getEnv();
            JSDynamicObject globalObj = realm.getGlobalObject();
            StringTokenizer tok = new StringTokenizer(cmd);
            String[] cmds = new String[tok.countTokens()];
            int i = 0;
            while (tok.hasMoreTokens()) {
                cmds[i] = tok.nextToken();
                ++i;
            }
            int exitCode = 0;
            String outStr = "";
            String errStr = "";
            Process process = null;
            try {
                TruffleProcessBuilder builder = env.newProcessBuilder(cmds);
                Object envObj = JSObject.get(globalObj, Strings.DOLLAR_ENV);
                if (JSGuards.isJSObject(envObj)) {
                    JSDynamicObject dynEnvObj = (JSDynamicObject)envObj;
                    Object pwd = JSObject.get(dynEnvObj, Strings.CAPS_PWD);
                    if (pwd != Undefined.instance) {
                        builder.directory(env.getPublicTruffleFile(JSRuntime.toJavaString(pwd)));
                    }
                    builder.clearEnvironment(true);
                    for (TruffleString key : JSObject.enumerableOwnNames(dynEnvObj)) {
                        builder.environment(Strings.toJavaString(key), JSRuntime.toJavaString(JSObject.get(dynEnvObj, key)));
                    }
                }
                ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
                ByteArrayOutputStream errBuffer = new ByteArrayOutputStream();
                builder.redirectOutput(builder.createRedirectToStream(outBuffer));
                builder.redirectError(builder.createRedirectToStream(errBuffer));
                process = builder.start();
                try (OutputStreamWriter outputStream = new OutputStreamWriter(process.getOutputStream(), this.getContext().getCharset());){
                    if (input != null) {
                        outputStream.write(input, 0, input.length());
                    }
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                exitCode = process.waitFor();
                outStr = outBuffer.toString();
                errStr = errBuffer.toString();
            }
            catch (InterruptedException e) {
                if (process.isAlive()) {
                    process.destroy();
                }
                if (exitCode == 0) {
                    exitCode = process.exitValue();
                }
            }
            catch (IOException | SecurityException e) {
                throw Errors.createError(e.getMessage());
            }
            TruffleString outStrTS = Strings.fromJavaString(outStr);
            JSObject.set(globalObj, Strings.$_OUT, (Object)outStrTS);
            JSObject.set(globalObj, Strings.$_ERR, (Object)Strings.fromJavaString(errStr));
            JSObject.set(globalObj, Strings.$_EXIT, (Object)exitCode);
            return outStrTS;
        }
    }

    public static abstract class GlobalNashornExtensionParseToJSONNode
    extends JSBuiltinNode {
        public GlobalNashornExtensionParseToJSONNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected TruffleString parseToJSON(Object code0, Object name0, Object location0) {
            String code = JSRuntime.toJavaString(code0);
            String name = name0 == Undefined.instance ? "<unknown>" : JSRuntime.toJavaString(name0);
            boolean location = JSRuntime.toBoolean(location0);
            return Strings.fromJavaString(this.getContext().getEvaluator().parseToJSON(this.getContext(), code, name, location));
        }
    }

    public static final class GlobalNashornScriptingBuiltins
    extends JSBuiltinsContainer.SwitchEnum<GlobalNashornScripting> {
        protected GlobalNashornScriptingBuiltins() {
            super(GlobalNashornScripting.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, GlobalNashornScripting builtinEnum) {
            switch (builtinEnum) {
                case exit: 
                case quit: {
                    return GlobalBuiltinsFactory.JSGlobalExitNodeGen.create(context, builtin, GlobalNashornScriptingBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case readLine: {
                    return GlobalBuiltinsFactory.JSGlobalReadLineNodeGen.create(context, builtin, true, GlobalNashornScriptingBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case readFully: {
                    return GlobalBuiltinsFactory.JSGlobalReadFullyNodeGen.create(context, builtin, GlobalNashornScriptingBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case parseToJSON: {
                    return GlobalBuiltinsFactory.GlobalNashornExtensionParseToJSONNodeGen.create(context, builtin, GlobalNashornScriptingBuiltins.args().fixedArgs(3).createArgumentNodes(context));
                }
                case exec: {
                    return GlobalBuiltinsFactory.GlobalScriptingEXECNodeGen.create(context, builtin, GlobalNashornScriptingBuiltins.args().fixedArgs(2).createArgumentNodes(context));
                }
                case importScriptEngineGlobalBindings: {
                    return GlobalBuiltinsFactory.JSGlobalImportScriptEngineGlobalBindingsNodeGen.create(context, builtin, GlobalNashornScriptingBuiltins.args().fixedArgs(1).varArgs().createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum GlobalNashornScripting implements BuiltinEnum<GlobalNashornScripting>
        {
            exit(1),
            quit(1),
            readLine(1),
            readFully(1),
            exec(1),
            parseToJSON(3),
            importScriptEngineGlobalBindings(1);

            private final int length;

            private GlobalNashornScripting(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static final class GlobalLoadBuiltins
    extends JSBuiltinsContainer.SwitchEnum<GlobalLoad> {
        protected GlobalLoadBuiltins() {
            super(GlobalLoad.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, GlobalLoad builtinEnum) {
            switch (builtinEnum) {
                case load: {
                    return GlobalBuiltinsFactory.JSGlobalLoadNodeGen.create(context, builtin, GlobalLoadBuiltins.args().fixedArgs(1).varArgs().createArgumentNodes(context));
                }
                case loadWithNewGlobal: {
                    return GlobalBuiltinsFactory.JSGlobalLoadWithNewGlobalNodeGen.create(context, builtin, GlobalLoadBuiltins.args().fixedArgs(1).varArgs().createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum GlobalLoad implements BuiltinEnum<GlobalLoad>
        {
            load(1),
            loadWithNewGlobal(1);

            private final int length;

            private GlobalLoad(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static final class GlobalPrintBuiltins
    extends JSBuiltinsContainer.SwitchEnum<GlobalPrint> {
        protected GlobalPrintBuiltins() {
            super(GlobalPrint.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, GlobalPrint builtinEnum) {
            boolean noNewline = context.getContextOptions().isPrintNoNewline();
            switch (builtinEnum) {
                case print: {
                    return GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, builtin, false, noNewline, GlobalPrintBuiltins.args().varArgs().createArgumentNodes(context));
                }
                case printErr: {
                    return GlobalBuiltinsFactory.JSGlobalPrintNodeGen.create(context, builtin, true, noNewline, GlobalPrintBuiltins.args().varArgs().createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum GlobalPrint implements BuiltinEnum<GlobalPrint>
        {
            print(1),
            printErr(1);

            private final int length;

            private GlobalPrint(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static final class GlobalShellBuiltins
    extends JSBuiltinsContainer.SwitchEnum<GlobalShell> {
        protected GlobalShellBuiltins() {
            super(GlobalShell.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, GlobalShell builtinEnum) {
            switch (builtinEnum) {
                case quit: {
                    return GlobalBuiltinsFactory.JSGlobalExitNodeGen.create(context, builtin, GlobalShellBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case readline: {
                    return GlobalBuiltinsFactory.JSGlobalReadLineNodeGen.create(context, builtin, false, new JavaScriptNode[]{JSConstantNode.createUndefined()});
                }
                case read: {
                    return GlobalBuiltinsFactory.JSGlobalReadFullyNodeGen.create(context, builtin, GlobalShellBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case readbuffer: {
                    return GlobalBuiltinsFactory.JSGlobalReadBufferNodeGen.create(context, builtin, GlobalShellBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum GlobalShell implements BuiltinEnum<GlobalShell>
        {
            quit(1),
            readline(1),
            read(1),
            readbuffer(1);

            private final int length;

            private GlobalShell(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static enum Global implements BuiltinEnum<Global>
    {
        isNaN(1),
        isFinite(1),
        parseFloat(1),
        parseInt(2),
        encodeURI(1),
        encodeURIComponent(1),
        decodeURI(1),
        decodeURIComponent(1),
        eval(1),
        escape(1),
        unescape(1);

        private final int length;

        private Global(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isAnnexB() {
            return EnumSet.of(escape, unescape).contains(this);
        }
    }
}

