package com.oracle.truffle.js.runtime.objects;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSContextOptions;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.UserScriptException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.nio.file.InvalidPathException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultESModuleLoader implements JSModuleLoader {
   public static final String DOT = ".";
   public static final String SLASH = "/";
   public static final String DOT_SLASH = "./";
   public static final String DOT_DOT_SLASH = "../";
   private static final int JS_MODULE_TYPE = 1;
   private static final int JSON_MODULE_TYPE = 2;
   protected final JSRealm realm;
   protected final Map<String, JSModuleRecord> moduleMap = new HashMap<>();

   public static DefaultESModuleLoader create(JSRealm realm) {
      return new DefaultESModuleLoader(realm);
   }

   protected DefaultESModuleLoader(JSRealm realm) {
      this.realm = realm;
   }

   protected URI asURI(String specifier) {
      assert specifier != null;

      if (specifier.indexOf(58) == -1) {
         return null;
      } else {
         try {
            URI uri = new URI(specifier);
            return uri.getScheme() != null ? uri : null;
         } catch (URISyntaxException var3) {
            return null;
         }
      }
   }

   @Override
   public JSModuleRecord resolveImportedModule(ScriptOrModule referrer, Module.ModuleRequest moduleRequest) {
      String refPath = referrer == null ? null : referrer.getSource().getPath();

      try {
         TruffleString specifierTS = moduleRequest.getSpecifier();
         String specifier = Strings.toJavaString(specifierTS);
         URI maybeUri = this.asURI(specifier);
         TruffleString maybeCustomPath = this.realm.getCustomEsmPathMapping(Strings.fromJavaString(refPath), specifierTS);
         String canonicalPath;
         TruffleFile moduleFile;
         if (maybeCustomPath != null) {
            canonicalPath = maybeCustomPath.toJavaStringUncached();
            moduleFile = this.realm.getEnv().getPublicTruffleFile(canonicalPath).getCanonicalFile();
         } else {
            if (refPath == null) {
               if (maybeUri != null) {
                  moduleFile = this.realm.getEnv().getPublicTruffleFile(maybeUri);
               } else {
                  moduleFile = this.realm.getEnv().getPublicTruffleFile(specifier);
               }
            } else {
               TruffleFile refFile = this.realm.getEnv().getPublicTruffleFile(refPath);
               if (maybeUri != null) {
                  String uriFile = this.realm.getEnv().getPublicTruffleFile(maybeUri).getCanonicalFile().getPath();
                  moduleFile = refFile.resolveSibling(uriFile);
               } else if (this.bareSpecifierDirectLookup(specifier)) {
                  moduleFile = this.realm.getEnv().getPublicTruffleFile(specifier);
               } else {
                  moduleFile = refFile.resolveSibling(specifier);
               }
            }

            canonicalPath = null;
         }

         return this.loadModuleFromUrl(referrer, moduleRequest, moduleFile, canonicalPath);
      } catch (FileSystemException var12) {
         String fileName = var12.getFile();
         if (Objects.equals(var12.getMessage(), fileName)) {
            String message = "Error reading: " + fileName;
            if (this.realm.getContext().isOptionV8CompatibilityMode()) {
               throw UserScriptException.create(Strings.fromJavaString(message));
            } else {
               throw Errors.createError(message);
            }
         } else {
            throw Errors.createErrorFromException(var12);
         }
      } catch (UnsupportedOperationException | SecurityException | InvalidPathException | IOException var13) {
         throw Errors.createErrorFromException(var13);
      }
   }

   private boolean bareSpecifierDirectLookup(String specifier) {
      JSContextOptions options = this.realm.getContext().getContextOptions();
      return options.isEsmBareSpecifierRelativeLookup() ? false : !specifier.startsWith("/") && !specifier.startsWith("./") && !specifier.startsWith("../");
   }

   protected JSModuleRecord loadModuleFromUrl(
      ScriptOrModule referrer, Module.ModuleRequest moduleRequest, TruffleFile maybeModuleFile, String maybeCanonicalPath
   ) throws IOException {
      TruffleFile moduleFile;
      String canonicalPath;
      if (maybeCanonicalPath == null) {
         if (!maybeModuleFile.exists()) {
            canonicalPath = maybeModuleFile.getPath();
            JSModuleRecord existingModule = this.moduleMap.get(canonicalPath);
            if (existingModule != null) {
               return existingModule;
            }
         }

         moduleFile = maybeModuleFile.getCanonicalFile();
         canonicalPath = moduleFile.getPath();
      } else {
         moduleFile = maybeModuleFile;
         canonicalPath = maybeCanonicalPath;
      }

      JSModuleRecord existingModule = this.moduleMap.get(canonicalPath);
      if (existingModule != null) {
         return existingModule;
      } else {
         Source source = Source.newBuilder("js", moduleFile)
            .name(Strings.toJavaString(moduleRequest.getSpecifier()))
            .mimeType("application/javascript+module")
            .build();
         Map<TruffleString, TruffleString> assertions = moduleRequest.getAssertions();
         int moduleType = this.getModuleType(moduleFile.getName());
         TruffleString assertedType = assertions.get(JSContext.getTypeImportAssertion());
         if (!doesModuleTypeMatchAssertionType(assertedType, moduleType)) {
            throw Errors.createTypeError("Invalid module type was asserted");
         } else {
            JSModuleRecord newModule;
            if (isModuleType(moduleType, 2)) {
               newModule = this.realm.getContext().getEvaluator().parseJSONModule(this.realm, source);
            } else {
               JSModuleData parsedModule = this.realm.getContext().getEvaluator().envParseModule(this.realm, source);
               newModule = new JSModuleRecord(parsedModule, this);
            }

            this.moduleMap.put(canonicalPath, newModule);
            if (referrer instanceof JSModuleRecord) {
               ((JSModuleRecord)referrer).getModuleData().rememberImportedModuleSource(moduleRequest.getSpecifier(), source);
            }

            return newModule;
         }
      }
   }

   private static boolean doesModuleTypeMatchAssertionType(TruffleString assertedType, int moduleType) {
      if (assertedType == null) {
         return true;
      } else {
         return Strings.equals(Strings.JSON, assertedType) ? isModuleType(moduleType, 2) : false;
      }
   }

   private int getModuleType(String moduleName) {
      return this.realm.getContext().getContextOptions().isJsonModules() && moduleName.endsWith(".json") ? 2 : 1;
   }

   private static boolean isModuleType(int moduleType, int expectedType) {
      return (moduleType & expectedType) != 0;
   }

   @Override
   public JSModuleRecord loadModule(Source source, JSModuleData moduleData) {
      String canonicalPath = this.getCanonicalPath(source);
      return this.moduleMap.computeIfAbsent(canonicalPath, key -> new JSModuleRecord(moduleData, this));
   }

   private String getCanonicalPath(Source source) {
      String path = source.getPath();
      String canonicalPath;
      if (path == null) {
         canonicalPath = source.getName();
      } else {
         try {
            if (this.realm.getEnv().getFileNameSeparator().equals("\\") && path.startsWith("/")) {
               path = path.substring(1);
            }

            TruffleFile moduleFile = this.realm.getEnv().getPublicTruffleFile(path);
            if (moduleFile.exists()) {
               canonicalPath = moduleFile.getCanonicalFile().getPath();
            } else {
               canonicalPath = path;
            }
         } catch (SecurityException | IOException var5) {
            throw Errors.createErrorFromException(var5);
         }
      }

      return canonicalPath;
   }
}
