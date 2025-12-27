package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.DefaultESModuleLoader;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public final class NpmCompatibleESModuleLoader extends DefaultESModuleLoader {
   private static final URI TryCommonJS = URI.create("custom:///try-common-js-token");
   private static final URI TryCustomESM = URI.create("custom:///try-custom-esm-token");
   private static final String MODULE_NOT_FOUND = "Module not found: '";
   private static final String UNSUPPORTED_JSON = "JSON packages not supported.";
   private static final String FAILED_BUILTIN = "Failed to load built-in ES module: '";
   private static final String INVALID_MODULE_SPECIFIER = "Invalid module specifier: '";
   private static final String UNSUPPORTED_FILE_EXTENSION = "Unsupported file extension: '";
   private static final String UNSUPPORTED_PACKAGE_EXPORTS = "Unsupported package exports: '";
   private static final String UNSUPPORTED_PACKAGE_IMPORTS = "Unsupported package imports: '";
   private static final String UNSUPPORTED_DIRECTORY_IMPORT = "Unsupported directory import: '";
   private static final String INVALID_PACKAGE_CONFIGURATION = "Invalid package configuration: '";

   public static NpmCompatibleESModuleLoader create(JSRealm realm) {
      return new NpmCompatibleESModuleLoader(realm);
   }

   private NpmCompatibleESModuleLoader(JSRealm realm) {
      super(realm);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JSModuleRecord resolveImportedModule(ScriptOrModule referencingModule, Module.ModuleRequest moduleRequest) {
      String specifier = moduleRequest.getSpecifier().toJavaStringUncached();
      CommonJSRequireBuiltin.log("IMPORT resolve ", specifier);
      if (CommonJSResolution.hasCoreModuleReplacement(this.realm.getContext(), specifier)) {
         return this.loadCoreModuleReplacement(referencingModule, moduleRequest);
      } else {
         try {
            TruffleLanguage.Env env = this.realm.getEnv();
            URI parentURL = this.getFullPath(referencingModule).toUri();
            URI resolution = this.esmResolve(specifier, parentURL, env);
            if (resolution == TryCommonJS) {
               return this.tryLoadingAsCommonjsModule(specifier);
            } else {
               if (resolution == TryCustomESM) {
                  TruffleFile maybeFile = env.getPublicTruffleFile(specifier);
                  if (maybeFile.exists() && !maybeFile.isDirectory()) {
                     return this.loadModuleFromUrl(referencingModule, moduleRequest, maybeFile, maybeFile.getPath());
                  }
               } else if (resolution != null) {
                  TruffleFile file = env.getPublicTruffleFile(resolution);
                  return this.loadModuleFromUrl(referencingModule, moduleRequest, file, file.getPath());
               }

               throw fail("Module not found: '", specifier);
            }
         } catch (IOException var8) {
            CommonJSRequireBuiltin.log("IMPORT resolve ", specifier, " FAILED ", var8.getMessage());
            throw Errors.createErrorFromException(var8);
         }
      }
   }

   private JSModuleRecord loadCoreModuleReplacement(ScriptOrModule referencingModule, Module.ModuleRequest moduleRequest) {
      String specifier = moduleRequest.getSpecifier().toJavaStringUncached();
      CommonJSRequireBuiltin.log("IMPORT resolve built-in ", specifier);
      JSModuleRecord existingModule = this.moduleMap.get(specifier);
      if (existingModule != null) {
         CommonJSRequireBuiltin.log("IMPORT resolve built-in from cache ", specifier);
         return existingModule;
      } else {
         String moduleReplacementName = this.realm.getContext().getContextOptions().getCommonJSRequireBuiltins().get(specifier);
         if (moduleReplacementName != null && moduleReplacementName.endsWith(".mjs")) {
            URI maybeUri = this.asURI(moduleReplacementName);
            if (maybeUri != null) {
               TruffleLanguage.Env env = this.realm.getEnv();
               URI parentURL = this.getFullPath(referencingModule).toUri();
               URI resolution = this.esmResolve(moduleReplacementName, parentURL, env);

               assert resolution != null;

               try {
                  TruffleFile file = env.getPublicTruffleFile(resolution);
                  return this.loadModuleFromUrl(referencingModule, moduleRequest, file, file.getPath());
               } catch (IOException var12) {
                  throw fail("Failed to load built-in ES module: '", specifier);
               }
            } else {
               Source src;
               try {
                  String cwdOption = this.realm.getContext().getContextOptions().getRequireCwd();
                  TruffleFile cwd = cwdOption == null ? this.realm.getEnv().getCurrentWorkingDirectory() : this.realm.getEnv().getPublicTruffleFile(cwdOption);
                  TruffleFile modulePath = CommonJSResolution.joinPaths(cwd, moduleReplacementName);
                  src = Source.newBuilder("js", modulePath).build();
               } catch (SecurityException | IOException var13) {
                  throw fail("Failed to load built-in ES module: '", specifier);
               }

               JSModuleData parsedModule = this.realm.getContext().getEvaluator().envParseModule(this.realm, src);
               JSModuleRecord record = new JSModuleRecord(parsedModule, this);
               this.moduleMap.put(specifier, record);
               return record;
            }
         } else {
            return this.tryLoadingAsCommonjsModule(moduleRequest.getSpecifier().toJavaStringUncached());
         }
      }
   }

   private JSModuleRecord tryLoadingAsCommonjsModule(String specifier) {
      JSModuleRecord existingModule = this.moduleMap.get(specifier);
      if (existingModule != null) {
         CommonJSRequireBuiltin.log("IMPORT resolve built-in from cache ", specifier);
         return existingModule;
      } else {
         JSFunctionObject require = (JSFunctionObject)this.realm.getCommonJSRequireFunctionObject();
         Object maybeModule = JSFunction.call(JSArguments.create(Undefined.instance, require, Strings.fromJavaString(specifier)));
         if (maybeModule != Undefined.instance && JSDynamicObject.isJSDynamicObject(maybeModule)) {
            JSDynamicObject module = (JSDynamicObject)maybeModule;
            List<TruffleString> exportedValues = JSObject.enumerableOwnNames(module);
            TruffleStringBuilder moduleBody = Strings.builderCreate();
            Strings.builderAppend(moduleBody, "const builtinModule = require('");
            Strings.builderAppend(moduleBody, specifier);
            Strings.builderAppend(moduleBody, "');\n");

            for (TruffleString s : exportedValues) {
               Strings.builderAppend(moduleBody, "export const ");
               Strings.builderAppend(moduleBody, s);
               Strings.builderAppend(moduleBody, " = builtinModule.");
               Strings.builderAppend(moduleBody, s);
               Strings.builderAppend(moduleBody, ";\n");
            }

            Strings.builderAppend(moduleBody, "export default builtinModule;");
            Source src = Source.newBuilder("js", Strings.builderToJavaString(moduleBody), specifier + "-internal.mjs").build();
            JSModuleData parsedModule = this.realm.getContext().getEvaluator().envParseModule(this.realm, src);
            JSModuleRecord record = new JSModuleRecord(parsedModule, this);
            this.moduleMap.put(specifier, record);
            return record;
         } else {
            throw fail("Failed to load built-in ES module: '", specifier);
         }
      }
   }

   private URI esmResolve(String specifier, URI parentURL, TruffleLanguage.Env env) {
      URI resolved = this.asURI(specifier);
      if (resolved == null) {
         if ((specifier.isEmpty() || specifier.charAt(0) != '/') && !isRelativePathFileName(specifier)) {
            if (!specifier.isEmpty() && specifier.charAt(0) == '#') {
               throw fail("Unsupported package imports: '", specifier);
            }

            resolved = this.packageResolve(specifier, parentURL, env);
         } else {
            resolved = resolveRelativeToParent(specifier, parentURL);
         }
      }

      if (resolved == null) {
         return TryCommonJS;
      } else if (resolved != TryCommonJS && resolved != TryCustomESM) {
         NpmCompatibleESModuleLoader.Format format = null;
         if (isFileURI(resolved)) {
            if (resolved.toString().toUpperCase().contains("%2F") || resolved.toString().toUpperCase().contains("%5C")) {
               throw fail("Invalid module specifier: '", specifier);
            }

            if (isDirectory(resolved, env)) {
               throw fail("Unsupported directory import: '", specifier);
            }

            if (!fileExists(resolved, env)) {
               throw fail("Module not found: '", specifier);
            }

            resolved = resolved.normalize();
            format = this.esmFileFormat(resolved, env);
         } else {
            format = getAssociatedDefaultFormat(resolved);
         }

         return format == NpmCompatibleESModuleLoader.Format.CommonJS ? TryCommonJS : resolved;
      } else {
         return resolved;
      }
   }

   private NpmCompatibleESModuleLoader.Format esmFileFormat(URI url, TruffleLanguage.Env env) {
      assert fileExists(url, env);

      if (url.getPath().endsWith(".mjs")) {
         return NpmCompatibleESModuleLoader.Format.ESM;
      } else if (url.getPath().endsWith(".cjs")) {
         return NpmCompatibleESModuleLoader.Format.CommonJS;
      } else if (url.getPath().endsWith(".json")) {
         throw failMessage("JSON packages not supported.");
      } else {
         URI packageUri = this.lookupPackageScope(url, env);
         if (packageUri != null) {
            NpmCompatibleESModuleLoader.PackageJson pjson = this.readPackageJson(packageUri, env);
            if (pjson != null && pjson.hasTypeModule() && url.getPath().endsWith(".js")) {
               return NpmCompatibleESModuleLoader.Format.ESM;
            }
         } else if (url.getPath().endsWith(".js")) {
            return NpmCompatibleESModuleLoader.Format.CommonJS;
         }

         throw fail("Unsupported file extension: '", url.toString());
      }
   }

   private URI packageResolve(String packageSpecifier, URI parentURL, TruffleLanguage.Env env) {
      String packageName = null;
      if (packageSpecifier.isEmpty()) {
         throw fail("Invalid module specifier: '", packageSpecifier);
      } else {
         int packageSpecifierSeparator = packageSpecifier.indexOf(47);
         if (packageSpecifier.charAt(0) != '@') {
            if (packageSpecifierSeparator != -1) {
               packageName = packageSpecifier.substring(0, packageSpecifierSeparator);
            } else {
               packageName = packageSpecifier;
            }
         } else {
            if (packageSpecifierSeparator == -1) {
               throw fail("Invalid module specifier: '", packageSpecifier);
            }

            int secondSeparator = packageSpecifier.indexOf(47, packageSpecifierSeparator + 1);
            if (secondSeparator != -1) {
               packageName = packageSpecifier.substring(0, secondSeparator);
            } else {
               packageName = packageSpecifier;
            }
         }

         if (packageName.charAt(0) != '.' && packageName.indexOf(92) < 0 && packageName.indexOf(37) < 0) {
            String packageSpecifierSub = packageSpecifier.substring(packageName.length());
            String packageSubpath = "." + packageSpecifierSub;
            if (packageSubpath.endsWith("/")) {
               throw fail("Invalid module specifier: '", packageSpecifier);
            } else {
               URI selfUrl = this.packageSelfResolve(packageName, parentURL, env);
               if (selfUrl != null) {
                  return selfUrl;
               } else {
                  TruffleFile currentParentUrl = env.getPublicTruffleFile(parentURL);

                  while (currentParentUrl != null && !isRoot(currentParentUrl)) {
                     URI packageUrl = getPackageUrl(packageName, currentParentUrl);
                     currentParentUrl = currentParentUrl.getParent();
                     TruffleFile maybeFolder = packageUrl != null ? env.getPublicTruffleFile(packageUrl) : null;
                     if (maybeFolder != null && maybeFolder.exists() && maybeFolder.isDirectory()) {
                        NpmCompatibleESModuleLoader.PackageJson pjson = this.readPackageJson(packageUrl, env);
                        if (pjson != null && pjson.hasExportsProperty()) {
                           throw fail("Unsupported package exports: '", packageSpecifier);
                        }

                        if (packageSubpath.equals(".")) {
                           if (pjson != null && pjson.hasMainProperty()) {
                              TruffleString main = pjson.getMainProperty();
                              return packageUrl.resolve(main.toString());
                           }

                           return TryCommonJS;
                        }

                        return packageUrl.resolve(packageSubpath);
                     }
                  }

                  return TryCustomESM;
               }
            }
         } else {
            throw fail("Invalid module specifier: '", packageSpecifier);
         }
      }
   }

   private static boolean isRoot(TruffleFile file) {
      return file.isDirectory() && file.isAbsolute() ? file.getParent() == null : false;
   }

   private URI packageSelfResolve(String packageName, URI parentURL, TruffleLanguage.Env env) {
      URI packageUrl = this.lookupPackageScope(parentURL, env);
      if (packageUrl == null) {
         return null;
      } else {
         NpmCompatibleESModuleLoader.PackageJson pjson = this.readPackageJson(packageUrl, env);
         if (pjson == null || !pjson.hasExportsProperty()) {
            return null;
         } else if (pjson.namePropertyEquals(packageName)) {
            throw failMessage("Unsupported package exports: '");
         } else {
            return null;
         }
      }
   }

   private URI lookupPackageScope(URI url, TruffleLanguage.Env env) {
      URI scopeUrl = url;

      while (scopeUrl != null) {
         scopeUrl = getParentUrl(scopeUrl, env);
         if (scopeUrl == null) {
            break;
         }

         if (scopeUrl.toString().endsWith("node_modules")) {
            return null;
         }

         if (this.readPackageJson(scopeUrl, env) != null) {
            return scopeUrl;
         }
      }

      return null;
   }

   private NpmCompatibleESModuleLoader.PackageJson readPackageJson(URI packageUrl, TruffleLanguage.Env env) {
      URI pjsonUrl = packageUrl.resolve("package.json");
      if (!fileExists(pjsonUrl, env)) {
         return null;
      } else {
         JSDynamicObject jsonObj = CommonJSResolution.loadJsonObject(env.getPublicTruffleFile(pjsonUrl), this.realm);
         if (!JSDynamicObject.isJSDynamicObject(jsonObj)) {
            throw failMessage("Invalid package configuration: '");
         } else {
            return new NpmCompatibleESModuleLoader.PackageJson(jsonObj);
         }
      }
   }

   private static boolean fileExists(URI url, TruffleLanguage.Env env) {
      return CommonJSResolution.fileExists(env.getPublicTruffleFile(url));
   }

   private static boolean isFileURI(URI maybe) {
      return maybe != null && maybe.getScheme().equals("file");
   }

   private static URI getPackageUrl(String packageSpecifier, TruffleFile parentURL) {
      try {
         URI combined = new URI("./node_modules/" + packageSpecifier);
         TruffleFile resolved = parentURL.resolve(String.valueOf(combined));
         return resolved.toUri();
      } catch (URISyntaxException var4) {
         return null;
      }
   }

   private static URI getParentUrl(URI scopeUrl, TruffleLanguage.Env env) {
      TruffleFile asFile = env.getPublicTruffleFile(scopeUrl);
      return asFile.getParent() != null ? asFile.getParent().toUri() : null;
   }

   private static NpmCompatibleESModuleLoader.Format getAssociatedDefaultFormat(URI resolved) {
      assert resolved.getPath() != null;

      return resolved.getPath().endsWith(".mjs") ? NpmCompatibleESModuleLoader.Format.ESM : NpmCompatibleESModuleLoader.Format.CommonJS;
   }

   private static boolean isDirectory(URI resolved, TruffleLanguage.Env env) {
      return env.getPublicTruffleFile(resolved).isDirectory();
   }

   private static URI resolveRelativeToParent(String specifier, URI parentURL) {
      return parentURL.resolve(specifier);
   }

   private TruffleFile getFullPath(ScriptOrModule referencingModule) {
      String refPath = referencingModule == null ? null : referencingModule.getSource().getPath();
      if (refPath == null) {
         refPath = this.realm.getContext().getContextOptions().getRequireCwd();
      }

      return this.realm.getEnv().getPublicTruffleFile(refPath);
   }

   @CompilerDirectives.TruffleBoundary
   private static JSException failMessage(String message) {
      return JSException.create(JSErrorType.TypeError, message);
   }

   @CompilerDirectives.TruffleBoundary
   private static JSException fail(String errorType, String moduleIdentifier) {
      return failMessage(errorType + moduleIdentifier + Strings.SINGLE_QUOTE);
   }

   private static boolean isRelativePathFileName(String moduleIdentifier) {
      return moduleIdentifier.startsWith("./") || moduleIdentifier.startsWith("../");
   }

   private static enum Format {
      CommonJS,
      ESM;
   }

   private static class PackageJson {
      private final JSDynamicObject jsonObj;

      PackageJson(JSDynamicObject jsonObj) {
         assert jsonObj != null;

         assert JSObject.isJSObject(jsonObj);

         this.jsonObj = jsonObj;
      }

      boolean hasTypeModule() {
         if (hasNonNullProperty(this.jsonObj, Strings.TYPE)) {
            Object nameValue = JSObject.get(this.jsonObj, Strings.TYPE);
            if (Strings.isTString(nameValue)) {
               return Strings.equals(Strings.MODULE, (TruffleString)nameValue);
            }
         }

         return false;
      }

      private static boolean hasNonNullProperty(JSDynamicObject object, TruffleString keyName) {
         if (!JSObject.hasProperty(object, keyName)) {
            return false;
         } else {
            Object value = JSObject.get(object, keyName);
            return value != Null.instance && value != Undefined.instance;
         }
      }

      public boolean hasExportsProperty() {
         return hasNonNullProperty(this.jsonObj, Strings.EXPORTS_PROPERTY_NAME);
      }

      public boolean hasMainProperty() {
         if (JSObject.hasProperty(this.jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME)) {
            Object value = JSObject.get(this.jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME);
            return Strings.isTString(value);
         } else {
            return false;
         }
      }

      public TruffleString getMainProperty() {
         assert this.hasMainProperty();

         Object value = JSObject.get(this.jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME);
         return (TruffleString)value;
      }

      public boolean namePropertyEquals(String name) {
         TruffleString packageName = Strings.fromJavaString(name);
         if (hasNonNullProperty(this.jsonObj, Strings.NAME)) {
            Object nameValue = JSObject.get(this.jsonObj, Strings.NAME);
            if (Strings.isTString(nameValue)) {
               return Strings.equals(packageName, (TruffleString)nameValue);
            }
         }

         return false;
      }
   }
}
