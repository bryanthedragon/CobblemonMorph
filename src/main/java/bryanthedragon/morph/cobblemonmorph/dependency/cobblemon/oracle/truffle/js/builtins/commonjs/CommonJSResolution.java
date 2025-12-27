package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class CommonJSResolution {
   public static final String FILE = "file";
   public static final String NODE_MODULES = "node_modules";
   public static final String PACKAGE_JSON = "package.json";
   public static final String INDEX_JS = "index.js";
   public static final String INDEX_JSON = "index.json";
   public static final String INDEX_NODE = "index.node";
   public static final String JS_EXT = ".js";
   public static final String CJS_EXT = ".cjs";
   public static final String MJS_EXT = ".mjs";
   public static final String JSON_EXT = ".json";
   public static final String NODE_EXT = ".node";

   private CommonJSResolution() {
   }

   public static boolean hasCoreModuleReplacement(JSContext context, String moduleIdentifier) {
      return context.getContextOptions().getCommonJSRequireBuiltins().containsKey(moduleIdentifier);
   }

   static String getCurrentFileNameFromStack() {
      Source callerSource = JSFunction.getCallerSource();
      return callerSource != null ? callerSource.getPath() : null;
   }

   @CompilerDirectives.TruffleBoundary
   static TruffleFile resolve(JSRealm realm, String moduleIdentifier, TruffleFile entryPath) {
      if (moduleIdentifier.isEmpty()) {
         return null;
      } else {
         TruffleLanguage.Env env = realm.getEnv();
         TruffleFile currentWorkingPath = entryPath;
         if (moduleIdentifier.charAt(0) == '/') {
            currentWorkingPath = getFileSystemRootPath(env);
         }

         if (isPathFileName(moduleIdentifier)) {
            TruffleFile module = loadAsFileOrDirectory(realm, joinPaths(currentWorkingPath, moduleIdentifier));
            if (module != null) {
               return module;
            }
         }

         return loadNodeModulesOrSelfReference(realm, moduleIdentifier, currentWorkingPath);
      }
   }

   private static TruffleFile loadNodeModulesOrSelfReference(JSRealm realm, String moduleIdentifier, TruffleFile startFolder) {
      for (TruffleFile s : getNodeModulesPaths(startFolder)) {
         TruffleFile module = loadAsFileOrDirectory(realm, joinPaths(s, moduleIdentifier));
         if (module != null) {
            return module;
         }
      }

      return null;
   }

   public static TruffleFile loadIndex(TruffleFile modulePath) {
      TruffleFile indexJs = joinPaths(modulePath, "index.js");
      if (fileExists(indexJs)) {
         return indexJs;
      } else {
         TruffleFile indexJson = joinPaths(modulePath, "index.json");
         if (fileExists(indexJson)) {
            return indexJson;
         } else {
            return fileExists(joinPaths(modulePath, "index.node")) ? null : null;
         }
      }
   }

   static TruffleFile loadAsFile(TruffleLanguage.Env env, TruffleFile modulePath) {
      if (fileExists(modulePath)) {
         return modulePath;
      } else {
         TruffleFile moduleJs = env.getPublicTruffleFile(modulePath.toString() + Strings.JS_EXT);
         if (fileExists(moduleJs)) {
            return moduleJs;
         } else {
            TruffleFile moduleJson = env.getPublicTruffleFile(modulePath.toString() + Strings.JSON_EXT);
            if (fileExists(moduleJson)) {
               return moduleJson;
            } else {
               return fileExists(env.getPublicTruffleFile(modulePath.toString() + Strings.NODE_EXT)) ? null : null;
            }
         }
      }
   }

   public static List<TruffleFile> getNodeModulesPaths(TruffleFile path) {
      List<TruffleFile> list = new ArrayList<>();

      for (TruffleFile p : getAllParentPaths(path)) {
         if (p.endsWith("node_modules")) {
            list.add(p);
         } else {
            TruffleFile truffleFile = p.resolve("node_modules");
            list.add(truffleFile);
         }
      }

      return list;
   }

   private static TruffleFile loadAsFileOrDirectory(JSRealm realm, TruffleFile modulePath) {
      TruffleFile maybeFile = loadAsFile(realm.getEnv(), modulePath);
      return maybeFile == null ? loadAsDirectory(realm, modulePath) : maybeFile;
   }

   private static List<TruffleFile> getAllParentPaths(TruffleFile from) {
      List<TruffleFile> paths = new ArrayList<>();

      for (TruffleFile p = from; p != null; p = p.getParent()) {
         paths.add(p);
      }

      return paths;
   }

   private static TruffleFile loadAsDirectory(JSRealm realm, TruffleFile modulePath) {
      TruffleFile packageJson = joinPaths(modulePath, "package.json");
      if (fileExists(packageJson)) {
         JSDynamicObject jsonObj = loadJsonObject(packageJson, realm);
         if (JSDynamicObject.isJSDynamicObject(jsonObj)) {
            Object main = JSObject.get(jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME);
            if (!Strings.isTString(main)) {
               return loadIndex(modulePath);
            } else {
               TruffleFile module = joinPaths(modulePath, JSRuntime.safeToString(main).toJavaStringUncached());
               TruffleFile asFile = loadAsFile(realm.getEnv(), module);
               return asFile != null ? asFile : loadIndex(module);
            }
         } else {
            return null;
         }
      } else {
         return loadIndex(modulePath);
      }
   }

   public static JSDynamicObject loadJsonObject(TruffleFile jsonFile, JSRealm realm) {
      try {
         if (fileExists(jsonFile)) {
            Source source = null;
            TruffleFile file = GlobalBuiltins.resolveRelativeFilePath(jsonFile.toString(), realm.getEnv());
            if (file.isRegularFile()) {
               source = sourceFromTruffleFile(file);
            }

            if (source == null) {
               return null;
            }

            JSFunctionObject parse = (JSFunctionObject)realm.getJsonParseFunctionObject();
            TruffleString jsonString = Strings.fromJavaString(source.getCharacters().toString());
            Object jsonObj = JSFunction.call(JSArguments.create(Undefined.instance, parse, jsonString));
            if (JSDynamicObject.isJSDynamicObject(jsonObj)) {
               return (JSDynamicObject)jsonObj;
            }
         }

         return null;
      } catch (IllegalArgumentException | UnsupportedOperationException | SecurityException var7) {
         throw Errors.createErrorFromException(var7);
      }
   }

   private static Source sourceFromTruffleFile(TruffleFile file) {
      try {
         return Source.newBuilder("js", file).build();
      } catch (SecurityException | IllegalArgumentException | UnsupportedOperationException | IOException var2) {
         return null;
      }
   }

   public static boolean fileExists(TruffleFile modulePath) {
      return modulePath.exists() && modulePath.isRegularFile();
   }

   private static boolean isPathFileName(String moduleIdentifier) {
      return moduleIdentifier.startsWith("/") || moduleIdentifier.startsWith("./") || moduleIdentifier.startsWith("../");
   }

   public static TruffleFile joinPaths(TruffleFile p1, String p2) {
      Objects.requireNonNull(p1);

      try {
         return p1.resolve(p2).getAbsoluteFile().normalize();
      } catch (InvalidPathException var3) {
         throw CommonJSRequireBuiltin.fail(p2);
      }
   }

   private static TruffleFile getFileSystemRootPath(TruffleLanguage.Env env) {
      TruffleFile root = env.getCurrentWorkingDirectory();

      TruffleFile last;
      for (last = root; root != null; root = root.getParent()) {
         last = root;
      }

      return last;
   }
}
