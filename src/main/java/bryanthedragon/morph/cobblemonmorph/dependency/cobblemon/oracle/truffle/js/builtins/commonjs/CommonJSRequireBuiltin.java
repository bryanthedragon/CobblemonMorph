package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
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
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

public abstract class CommonJSRequireBuiltin extends GlobalBuiltins.JSFileLoadingOperation {
   private static final boolean LOG_REQUIRE_PATH_RESOLUTION = false;
   private static final Stack<String> requireDebugStack = null;
   private static final String MODULE_PREAMBLE_PREFIX = "(function (";
   private static final String MODULE_PREAMBLE_POST = ") {";
   private static final String MODULE_END = "});";
   private static final String MODULE_FUNCTION_ARGS = "exports, require, module, __filename, __dirname";
   public static final String UNSUPPORTED_NODE_FILE = "Unsupported .node file: ";

   public static void log(Object... message) {
   }

   private static void debugStackPush(String moduleIdentifier) {
   }

   private static void debugStackPop() {
   }

   @CompilerDirectives.TruffleBoundary
   static TruffleFile getModuleResolveCurrentWorkingDirectory(JSContext context, TruffleLanguage.Env env) {
      String currentFileNameFromStack = CommonJSResolution.getCurrentFileNameFromStack();
      if (currentFileNameFromStack != null) {
         TruffleFile truffleFile = env.getPublicTruffleFile(currentFileNameFromStack);
         if (truffleFile.isRegularFile() && truffleFile.getParent() != null) {
            return truffleFile.getParent().normalize();
         }
      }

      return getRequireCwd(context, env);
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
      log("required module '", moduleIdentifier, "' from path ", entryPath);
      if (CommonJSResolution.hasCoreModuleReplacement(this.getContext(), moduleIdentifier)) {
         String moduleReplacementName = this.getContext().getContextOptions().getCommonJSRequireBuiltins().get(moduleIdentifier);
         if (moduleReplacementName != null && !moduleReplacementName.isEmpty()) {
            log("using module replacement for module '", moduleIdentifier, "' with ", moduleReplacementName);
            return this.requireImpl(moduleReplacementName, getRequireCwd(this.getContext(), realm.getEnv()), realm);
         }
      }

      TruffleFile maybeModule = null;

      try {
         maybeModule = CommonJSResolution.resolve(realm, moduleIdentifier, entryPath);
      } catch (IllegalArgumentException | UnsupportedOperationException | SecurityException var6) {
         throw fail(moduleIdentifier, var6.getMessage());
      }

      log("module ", moduleIdentifier, " resolved to ", maybeModule);
      if (maybeModule == null) {
         TruffleFile maybeCustom = realm.getEnv().getPublicTruffleFile(moduleIdentifier);
         if (!maybeCustom.exists()) {
            throw fail(moduleIdentifier);
         }

         maybeModule = maybeCustom;
      }

      if (isJsFile(maybeModule) || isCjsFile(maybeModule)) {
         return this.evalJavaScriptFile(maybeModule, moduleIdentifier);
      } else if (isJsonFile(maybeModule)) {
         return this.evalJsonFile(maybeModule);
      } else if (isNodeBinFile(maybeModule)) {
         throw fail("Unsupported .node file: ", moduleIdentifier);
      } else if (maybeModule.exists() && !isMjsFile(maybeModule)) {
         return this.evalJavaScriptFile(maybeModule, moduleIdentifier);
      } else {
         throw fail(moduleIdentifier);
      }
   }

   private Object evalJavaScriptFile(TruffleFile modulePath, String moduleIdentifier) {
      JSRealm realm = this.getRealm();
      TruffleFile normalizedPath = modulePath.normalize();
      Map<TruffleFile, JSDynamicObject> commonJSCache = realm.getCommonJSRequireCache();
      if (commonJSCache.containsKey(normalizedPath)) {
         JSDynamicObject moduleBuiltin = commonJSCache.get(normalizedPath);
         Object cached = JSObject.get(moduleBuiltin, Strings.EXPORTS_PROPERTY_NAME);
         log("returning cached '", modulePath, cached);
         return cached;
      } else {
         Source source = this.sourceFromPath(modulePath.toString(), realm);
         TruffleString filenameBuiltin = Strings.fromJavaString(normalizedPath.toString());
         if (modulePath.getParent() == null && !modulePath.exists()) {
            throw fail(moduleIdentifier);
         } else {
            String dirnameBuiltin = modulePath.getParent() == null ? "." : modulePath.getParent().getAbsoluteFile().normalize().toString();
            JSObject exportsBuiltin = createExportsBuiltin(realm);
            JSObject moduleBuiltin = createModuleBuiltin(realm, exportsBuiltin, filenameBuiltin);
            JSObject requireBuiltin = createRequireBuiltin(realm, moduleBuiltin, filenameBuiltin);
            JSObject env = JSOrdinary.create(this.getContext(), this.getRealm());
            JSObject.set(env, Strings.ENV_PROPERTY_NAME, JSOrdinary.create(this.getContext(), this.getRealm()));
            Object moduleExecutableFunction = parseModule(realm, source);
            if (JSFunction.isJSFunction(moduleExecutableFunction)) {
               log("adding to cache ", normalizedPath);
               commonJSCache.put(normalizedPath, moduleBuiltin);

               Object e;
               try {
                  debugStackPush(moduleIdentifier);
                  log("executing '", filenameBuiltin, "' for ", moduleIdentifier);
                  JSFunction.call(
                     JSArguments.create(
                        moduleExecutableFunction,
                        moduleExecutableFunction,
                        exportsBuiltin,
                        requireBuiltin,
                        moduleBuiltin,
                        filenameBuiltin,
                        Strings.fromJavaString(dirnameBuiltin),
                        env
                     )
                  );
                  JSObject.set(moduleBuiltin, Strings.LOADED_PROPERTY_NAME, true);
                  e = JSObject.get(moduleBuiltin, Strings.EXPORTS_PROPERTY_NAME);
               } catch (Exception var20) {
                  log("EXCEPTION: '", var20.getMessage(), "'");
                  throw var20;
               } finally {
                  debugStackPop();
                  Object module = JSObject.get(moduleBuiltin, Strings.EXPORTS_PROPERTY_NAME);
                  log("done '", moduleIdentifier, "' module.exports: ", module, module);
               }

               return e;
            } else {
               return null;
            }
         }
      }
   }

   private static Object parseModule(JSRealm realm, Source source) {
      JSContext context = realm.getContext();
      String body = source.getCharacters() + "\n";
      context.getEvaluator()
         .checkFunctionSyntax(context, context.getParserOptions(), "exports, require, module, __filename, __dirname", body, false, false, source.getPath());
      CharSequence characters = "(function (exports, require, module, __filename, __dirname) {" + body + "});";
      Source moduleSources = Source.newBuilder(source).content(characters).build();
      CallTarget moduleCallTarget = realm.getEnv().parsePublic(moduleSources);
      return moduleCallTarget.call();
   }

   private JSDynamicObject evalJsonFile(TruffleFile jsonFile) {
      try {
         if (fileExists(jsonFile)) {
            JSRealm realm = this.getRealm();
            TruffleFile file = GlobalBuiltins.resolveRelativeFilePath(jsonFile.toString(), realm.getEnv());
            if (!file.isRegularFile()) {
               throw fail(jsonFile.toString());
            }

            Source source = this.sourceFromTruffleFile(file);
            JSFunctionObject parse = (JSFunctionObject)realm.getJsonParseFunctionObject();

            assert source != null;

            TruffleString jsonString = Strings.fromJavaString(source.getCharacters().toString());
            Object jsonObj = JSFunction.call(JSArguments.create(Undefined.instance, parse, jsonString));
            if (JSDynamicObject.isJSDynamicObject(jsonObj)) {
               return (JSDynamicObject)jsonObj;
            }
         }

         throw fail(jsonFile.toString());
      } catch (SecurityException var8) {
         throw Errors.createErrorFromException(var8);
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
      JSObject.set(module, Strings.EXPORTS_PROPERTY_NAME, exportsBuiltin);
      JSObject.set(module, Strings.ID_PROPERTY_NAME, fileNameBuiltin);
      JSObject.set(module, Strings.FILENAME_PROPERTY_NAME, fileNameBuiltin);
      JSObject.set(module, Strings.LOADED_PROPERTY_NAME, false);
      return module;
   }

   private static JSObject createRequireBuiltin(JSRealm realm, JSDynamicObject moduleBuiltin, TruffleString fileNameBuiltin) {
      JSFunctionObject mainRequire = (JSFunctionObject)realm.getCommonJSRequireFunctionObject();
      Object mainResolve = JSObject.get(mainRequire, Strings.RESOLVE_PROPERTY_NAME);
      JSFunctionData functionData = JSFunction.getFunctionData(mainRequire);
      JSObject newRequire = JSFunction.create(realm, functionData);
      JSObject.set(newRequire, Strings.MODULE_PROPERTY_NAME, moduleBuiltin);
      JSObject.set(newRequire, Strings.RESOLVE_PROPERTY_NAME, mainResolve);
      JSObject.set(newRequire, Strings.FILENAME_VAR_NAME, fileNameBuiltin);
      return newRequire;
   }

   private static JSObject createExportsBuiltin(JSRealm realm) {
      return JSOrdinary.create(realm.getContext(), realm);
   }

   private static boolean isNodeBinFile(TruffleFile maybeModule) {
      return hasExtension(Objects.requireNonNull(maybeModule.getName()), ".node");
   }

   private static boolean isJsFile(TruffleFile maybeModule) {
      return hasExtension(Objects.requireNonNull(maybeModule.getName()), ".js");
   }

   private static boolean isCjsFile(TruffleFile maybeModule) {
      return hasExtension(Objects.requireNonNull(maybeModule.getName()), ".cjs");
   }

   private static boolean isMjsFile(TruffleFile maybeModule) {
      return hasExtension(Objects.requireNonNull(maybeModule.getName()), ".mjs");
   }

   private static boolean isJsonFile(TruffleFile maybeModule) {
      return hasExtension(Objects.requireNonNull(maybeModule.getName()), ".json");
   }

   private static boolean fileExists(TruffleFile modulePath) {
      return modulePath.isRegularFile();
   }

   private TruffleFile getModuleResolutionEntryPath(JSDynamicObject currentRequire, TruffleLanguage.Env env) {
      if (JSDynamicObject.isJSDynamicObject(currentRequire)) {
         Object maybeFilename = JSObject.get(currentRequire, Strings.FILENAME_VAR_NAME);
         if (Strings.isTString(maybeFilename)) {
            String fileName = Strings.toJavaString(JSRuntime.toStringIsString(maybeFilename));
            if (isFile(env, fileName)) {
               TruffleFile maybeParent = getParent(env, fileName);
               if (maybeParent != null) {
                  return maybeParent;
               }
            }
         }
      }

      return getModuleResolveCurrentWorkingDirectory(this.getContext(), env);
   }

   private static TruffleFile getParent(TruffleLanguage.Env env, String fileName) {
      return env.getPublicTruffleFile(fileName).getParent();
   }

   private static boolean isFile(TruffleLanguage.Env env, String fileName) {
      return env.getPublicTruffleFile(fileName).exists();
   }

   private static boolean hasExtension(String fileName, String ext) {
      return fileName.endsWith(ext);
   }
}
