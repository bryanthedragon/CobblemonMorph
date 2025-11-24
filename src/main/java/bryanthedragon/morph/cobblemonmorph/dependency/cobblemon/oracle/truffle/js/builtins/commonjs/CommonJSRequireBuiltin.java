
package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.builtins.commonjs.CommonJSResolution;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.nio.file.LinkOption;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public abstract class CommonJSRequireBuiltin
extends GlobalBuiltins.JSFileLoadingOperation {
    private static final boolean LOG_REQUIRE_PATH_RESOLUTION = false;
    private static final Stack<String> requireDebugStack = null;
    private static final String MODULE_PREAMBLE_PREFIX = "(function (";
    private static final String MODULE_PREAMBLE_POST = ") {";
    private static final String MODULE_END = "});";
    private static final String MODULE_FUNCTION_ARGS = "exports, require, module, __filename, __dirname";
    public static final String UNSUPPORTED_NODE_FILE = "Unsupported .node file: ";

    public static void log(Object ... message) {
    }

    private static void debugStackPush(String moduleIdentifier) {
    }

    private static void debugStackPop() {
    }

    @CompilerDirectives.TruffleBoundary
    static TruffleFile getModuleResolveCurrentWorkingDirectory(JSContext context, TruffleLanguage.Env env) {
        TruffleFile truffleFile;
        String currentFileNameFromStack = CommonJSResolution.getCurrentFileNameFromStack();
        if (currentFileNameFromStack != null && (truffleFile = env.getPublicTruffleFile(currentFileNameFromStack)).isRegularFile(new LinkOption[0]) && truffleFile.getParent() != null) {
            return truffleFile.getParent().normalize();
        }
        return CommonJSRequireBuiltin.getRequireCwd(context, env);
    }

    static TruffleFile getRequireCwd(JSContext context, TruffleLanguage.Env env) {
        String cwdOption = context.getContextOptions().getRequireCwd();
        return cwdOption == null ? env.getCurrentWorkingDirectory() : env.getPublicTruffleFile(cwdOption);
    }

    CommonJSRequireBuiltin(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected Object require(JSDynamicObject currentRequire, TruffleString moduleIdentifier) {
        JSRealm realm = this.getRealm();
        TruffleLanguage.Env env = realm.getEnv();
        TruffleFile resolutionEntryPath = this.getModuleResolutionEntryPath(currentRequire, env);
        return this.requireImpl(moduleIdentifier.toJavaStringUncached(), resolutionEntryPath, realm);
    }

    @CompilerDirectives.TruffleBoundary
    private Object requireImpl(String moduleIdentifier, TruffleFile entryPath, JSRealm realm) {
        String moduleReplacementName;
        CommonJSRequireBuiltin.log("required module '", moduleIdentifier, "' from path ", entryPath);
        if (CommonJSResolution.hasCoreModuleReplacement(this.getContext(), moduleIdentifier) && (moduleReplacementName = this.getContext().getContextOptions().getCommonJSRequireBuiltins().get(moduleIdentifier)) != null && !moduleReplacementName.isEmpty()) {
            CommonJSRequireBuiltin.log("using module replacement for module '", moduleIdentifier, "' with ", moduleReplacementName);
            return this.requireImpl(moduleReplacementName, CommonJSRequireBuiltin.getRequireCwd(this.getContext(), realm.getEnv()), realm);
        }
        TruffleFile maybeModule = null;
        try {
            maybeModule = CommonJSResolution.resolve(realm, moduleIdentifier, entryPath);
        }
        catch (IllegalArgumentException | SecurityException | UnsupportedOperationException e) {
            throw CommonJSRequireBuiltin.fail(moduleIdentifier, e.getMessage());
        }
        CommonJSRequireBuiltin.log("module ", moduleIdentifier, " resolved to ", maybeModule);
        if (maybeModule == null) {
            TruffleFile maybeCustom = realm.getEnv().getPublicTruffleFile(moduleIdentifier);
            if (maybeCustom.exists(new LinkOption[0])) {
                maybeModule = maybeCustom;
            } else {
                throw CommonJSRequireBuiltin.fail(moduleIdentifier);
            }
        }
        if (CommonJSRequireBuiltin.isJsFile(maybeModule) || CommonJSRequireBuiltin.isCjsFile(maybeModule)) {
            return this.evalJavaScriptFile(maybeModule, moduleIdentifier);
        }
        if (CommonJSRequireBuiltin.isJsonFile(maybeModule)) {
            return this.evalJsonFile(maybeModule);
        }
        if (CommonJSRequireBuiltin.isNodeBinFile(maybeModule)) {
            throw CommonJSRequireBuiltin.fail(UNSUPPORTED_NODE_FILE, moduleIdentifier);
        }
        if (maybeModule.exists(new LinkOption[0]) && !CommonJSRequireBuiltin.isMjsFile(maybeModule)) {
            return this.evalJavaScriptFile(maybeModule, moduleIdentifier);
        }
        throw CommonJSRequireBuiltin.fail(moduleIdentifier);
    }

    private Object evalJavaScriptFile(TruffleFile modulePath, String moduleIdentifier) {
        JSRealm realm = this.getRealm();
        TruffleFile normalizedPath = modulePath.normalize();
        Map<TruffleFile, JSDynamicObject> commonJSCache = realm.getCommonJSRequireCache();
        if (commonJSCache.containsKey(normalizedPath)) {
            JSDynamicObject moduleBuiltin = commonJSCache.get(normalizedPath);
            Object cached = JSObject.get(moduleBuiltin, Strings.EXPORTS_PROPERTY_NAME);
            CommonJSRequireBuiltin.log("returning cached '", modulePath, cached);
            return cached;
        }
        Source source = this.sourceFromPath(modulePath.toString(), realm);
        TruffleString filenameBuiltin = Strings.fromJavaString(normalizedPath.toString());
        if (modulePath.getParent() == null && !modulePath.exists(new LinkOption[0])) {
            throw CommonJSRequireBuiltin.fail(moduleIdentifier);
        }
        String dirnameBuiltin = modulePath.getParent() == null ? "." : modulePath.getParent().getAbsoluteFile().normalize().toString();
        JSObject exportsBuiltin = CommonJSRequireBuiltin.createExportsBuiltin(realm);
        JSObject moduleBuiltin = CommonJSRequireBuiltin.createModuleBuiltin(realm, exportsBuiltin, filenameBuiltin);
        JSObject requireBuiltin = CommonJSRequireBuiltin.createRequireBuiltin(realm, moduleBuiltin, filenameBuiltin);
        JSObject env = JSOrdinary.create(this.getContext(), this.getRealm());
        JSObject.set((JSDynamicObject)env, Strings.ENV_PROPERTY_NAME, (Object)JSOrdinary.create(this.getContext(), this.getRealm()));
        Object moduleExecutableFunction = CommonJSRequireBuiltin.parseModule(realm, source);
        if (JSFunction.isJSFunction(moduleExecutableFunction)) {
            Object object;
            CommonJSRequireBuiltin.log("adding to cache ", normalizedPath);
            commonJSCache.put(normalizedPath, moduleBuiltin);
            try {
                CommonJSRequireBuiltin.debugStackPush(moduleIdentifier);
                CommonJSRequireBuiltin.log("executing '", filenameBuiltin, "' for ", moduleIdentifier);
                JSFunction.call(JSArguments.create(moduleExecutableFunction, moduleExecutableFunction, exportsBuiltin, requireBuiltin, moduleBuiltin, filenameBuiltin, Strings.fromJavaString(dirnameBuiltin), env));
                JSObject.set((JSDynamicObject)moduleBuiltin, Strings.LOADED_PROPERTY_NAME, (Object)true);
                object = JSObject.get((JSDynamicObject)moduleBuiltin, Strings.EXPORTS_PROPERTY_NAME);
            }
            catch (Exception e) {
                try {
                    CommonJSRequireBuiltin.log("EXCEPTION: '", e.getMessage(), "'");
                    throw e;
                }
                catch (Throwable throwable) {
                    CommonJSRequireBuiltin.debugStackPop();
                    Object module = JSObject.get((JSDynamicObject)moduleBuiltin, Strings.EXPORTS_PROPERTY_NAME);
                    CommonJSRequireBuiltin.log("done '", moduleIdentifier, "' module.exports: ", module, module);
                    throw throwable;
                }
            }
            CommonJSRequireBuiltin.debugStackPop();
            Object module = JSObject.get((JSDynamicObject)moduleBuiltin, Strings.EXPORTS_PROPERTY_NAME);
            CommonJSRequireBuiltin.log("done '", moduleIdentifier, "' module.exports: ", module, module);
            return object;
        }
        return null;
    }

    private static Object parseModule(JSRealm realm, Source source) {
        JSContext context = realm.getContext();
        String body = source.getCharacters() + "\n";
        context.getEvaluator().checkFunctionSyntax(context, context.getParserOptions(), MODULE_FUNCTION_ARGS, body, false, false, source.getPath());
        String characters = "(function (exports, require, module, __filename, __dirname) {" + body + MODULE_END;
        Source moduleSources = Source.newBuilder(source).content(characters).build();
        CallTarget moduleCallTarget = realm.getEnv().parsePublic(moduleSources, new String[0]);
        return moduleCallTarget.call(new Object[0]);
    }

    private JSDynamicObject evalJsonFile(TruffleFile jsonFile) {
        try {
            if (CommonJSRequireBuiltin.fileExists(jsonFile)) {
                JSRealm realm = this.getRealm();
                TruffleFile file = GlobalBuiltins.resolveRelativeFilePath(jsonFile.toString(), realm.getEnv());
                if (!file.isRegularFile(new LinkOption[0])) {
                    throw CommonJSRequireBuiltin.fail(jsonFile.toString());
                }
                Source source = this.sourceFromTruffleFile(file);
                JSFunctionObject parse2 = (JSFunctionObject)realm.getJsonParseFunctionObject();
                assert (source != null);
                TruffleString jsonString = Strings.fromJavaString(source.getCharacters().toString());
                Object jsonObj = JSFunction.call(JSArguments.create(Undefined.instance, parse2, jsonString));
                if (JSDynamicObject.isJSDynamicObject(jsonObj)) {
                    return (JSDynamicObject)jsonObj;
                }
            }
            throw CommonJSRequireBuiltin.fail(jsonFile.toString());
        }
        catch (SecurityException e) {
            throw Errors.createErrorFromException(e);
        }
    }

    static JSException fail(String moduleIdentifier) {
        return JSException.create(JSErrorType.TypeError, "Cannot load module: '" + moduleIdentifier + "'");
    }

    private static JSException fail(String moduleIdentifier, String extraMessage) {
        return JSException.create(JSErrorType.TypeError, "Cannot load module: '" + moduleIdentifier + "': " + extraMessage);
    }

    private static JSObject createModuleBuiltin(JSRealm realm, JSDynamicObject exportsBuiltin, TruffleString fileNameBuiltin) {
        JSObject module = JSOrdinary.create(realm.getContext(), realm);
        JSObject.set((JSDynamicObject)module, Strings.EXPORTS_PROPERTY_NAME, (Object)exportsBuiltin);
        JSObject.set((JSDynamicObject)module, Strings.ID_PROPERTY_NAME, (Object)fileNameBuiltin);
        JSObject.set((JSDynamicObject)module, Strings.FILENAME_PROPERTY_NAME, (Object)fileNameBuiltin);
        JSObject.set((JSDynamicObject)module, Strings.LOADED_PROPERTY_NAME, (Object)false);
        return module;
    }

    private static JSObject createRequireBuiltin(JSRealm realm, JSDynamicObject moduleBuiltin, TruffleString fileNameBuiltin) {
        JSFunctionObject mainRequire = (JSFunctionObject)realm.getCommonJSRequireFunctionObject();
        Object mainResolve = JSObject.get((JSDynamicObject)mainRequire, Strings.RESOLVE_PROPERTY_NAME);
        JSFunctionData functionData = JSFunction.getFunctionData(mainRequire);
        JSFunctionObject newRequire = JSFunction.create(realm, functionData);
        JSObject.set((JSDynamicObject)newRequire, Strings.MODULE_PROPERTY_NAME, (Object)moduleBuiltin);
        JSObject.set((JSDynamicObject)newRequire, Strings.RESOLVE_PROPERTY_NAME, mainResolve);
        JSObject.set((JSDynamicObject)newRequire, Strings.FILENAME_VAR_NAME, (Object)fileNameBuiltin);
        return newRequire;
    }

    private static JSObject createExportsBuiltin(JSRealm realm) {
        return JSOrdinary.create(realm.getContext(), realm);
    }

    private static boolean isNodeBinFile(TruffleFile maybeModule) {
        return CommonJSRequireBuiltin.hasExtension(Objects.requireNonNull(maybeModule.getName()), ".node");
    }

    private static boolean isJsFile(TruffleFile maybeModule) {
        return CommonJSRequireBuiltin.hasExtension(Objects.requireNonNull(maybeModule.getName()), ".js");
    }

    private static boolean isCjsFile(TruffleFile maybeModule) {
        return CommonJSRequireBuiltin.hasExtension(Objects.requireNonNull(maybeModule.getName()), ".cjs");
    }

    private static boolean isMjsFile(TruffleFile maybeModule) {
        return CommonJSRequireBuiltin.hasExtension(Objects.requireNonNull(maybeModule.getName()), ".mjs");
    }

    private static boolean isJsonFile(TruffleFile maybeModule) {
        return CommonJSRequireBuiltin.hasExtension(Objects.requireNonNull(maybeModule.getName()), ".json");
    }

    private static boolean fileExists(TruffleFile modulePath) {
        return modulePath.isRegularFile(new LinkOption[0]);
    }

    private TruffleFile getModuleResolutionEntryPath(JSDynamicObject currentRequire, TruffleLanguage.Env env) {
        TruffleFile maybeParent;
        String fileName;
        Object maybeFilename;
        if (JSDynamicObject.isJSDynamicObject(currentRequire) && Strings.isTString(maybeFilename = JSObject.get(currentRequire, Strings.FILENAME_VAR_NAME)) && CommonJSRequireBuiltin.isFile(env, fileName = Strings.toJavaString(JSRuntime.toStringIsString(maybeFilename))) && (maybeParent = CommonJSRequireBuiltin.getParent(env, fileName)) != null) {
            return maybeParent;
        }
        return CommonJSRequireBuiltin.getModuleResolveCurrentWorkingDirectory(this.getContext(), env);
    }

    private static TruffleFile getParent(TruffleLanguage.Env env, String fileName) {
        return env.getPublicTruffleFile(fileName).getParent();
    }

    private static boolean isFile(TruffleLanguage.Env env, String fileName) {
        return env.getPublicTruffleFile(fileName).exists(new LinkOption[0]);
    }

    private static boolean hasExtension(String fileName, String ext) {
        return fileName.endsWith(ext);
    }
}

