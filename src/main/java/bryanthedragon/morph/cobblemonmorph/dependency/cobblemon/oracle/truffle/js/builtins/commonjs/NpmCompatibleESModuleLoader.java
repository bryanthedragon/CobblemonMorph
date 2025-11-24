
package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.commonjs.CommonJSRequireBuiltin;
import com.oracle.truffle.js.builtins.commonjs.CommonJSResolution;
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
import java.nio.file.LinkOption;
import java.util.List;

public final class NpmCompatibleESModuleLoader
extends DefaultESModuleLoader {
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

    @Override
    @CompilerDirectives.TruffleBoundary
    public JSModuleRecord resolveImportedModule(ScriptOrModule referencingModule, Module.ModuleRequest moduleRequest) {
        String specifier = moduleRequest.getSpecifier().toJavaStringUncached();
        CommonJSRequireBuiltin.log("IMPORT resolve ", specifier);
        if (CommonJSResolution.hasCoreModuleReplacement(this.realm.getContext(), specifier)) {
            return this.loadCoreModuleReplacement(referencingModule, moduleRequest);
        }
        try {
            TruffleLanguage.Env env = this.realm.getEnv();
            URI parentURL = this.getFullPath(referencingModule).toUri();
            URI resolution = this.esmResolve(specifier, parentURL, env);
            if (resolution == TryCommonJS) {
                return this.tryLoadingAsCommonjsModule(specifier);
            }
            if (resolution == TryCustomESM) {
                TruffleFile maybeFile = env.getPublicTruffleFile(specifier);
                if (maybeFile.exists(new LinkOption[0]) && !maybeFile.isDirectory(new LinkOption[0])) {
                    return this.loadModuleFromUrl(referencingModule, moduleRequest, maybeFile, maybeFile.getPath());
                }
            } else if (resolution != null) {
                TruffleFile file = env.getPublicTruffleFile(resolution);
                return this.loadModuleFromUrl(referencingModule, moduleRequest, file, file.getPath());
            }
            throw NpmCompatibleESModuleLoader.fail(MODULE_NOT_FOUND, specifier);
        }
        catch (IOException e) {
            CommonJSRequireBuiltin.log("IMPORT resolve ", specifier, " FAILED ", e.getMessage());
            throw Errors.createErrorFromException(e);
        }
    }

    private JSModuleRecord loadCoreModuleReplacement(ScriptOrModule referencingModule, Module.ModuleRequest moduleRequest) {
        Source src;
        String specifier = moduleRequest.getSpecifier().toJavaStringUncached();
        CommonJSRequireBuiltin.log("IMPORT resolve built-in ", specifier);
        JSModuleRecord existingModule = (JSModuleRecord)this.moduleMap.get(specifier);
        if (existingModule != null) {
            CommonJSRequireBuiltin.log("IMPORT resolve built-in from cache ", specifier);
            return existingModule;
        }
        String moduleReplacementName = this.realm.getContext().getContextOptions().getCommonJSRequireBuiltins().get(specifier);
        if (moduleReplacementName != null && moduleReplacementName.endsWith(".mjs")) {
            URI maybeUri = this.asURI(moduleReplacementName);
            if (maybeUri != null) {
                TruffleLanguage.Env env = this.realm.getEnv();
                URI parentURL = this.getFullPath(referencingModule).toUri();
                URI resolution = this.esmResolve(moduleReplacementName, parentURL, env);
                assert (resolution != null);
                try {
                    TruffleFile file = env.getPublicTruffleFile(resolution);
                    return this.loadModuleFromUrl(referencingModule, moduleRequest, file, file.getPath());
                }
                catch (IOException e) {
                    throw NpmCompatibleESModuleLoader.fail(FAILED_BUILTIN, specifier);
                }
            }
            try {
                String cwdOption = this.realm.getContext().getContextOptions().getRequireCwd();
                TruffleFile cwd = cwdOption == null ? this.realm.getEnv().getCurrentWorkingDirectory() : this.realm.getEnv().getPublicTruffleFile(cwdOption);
                TruffleFile modulePath = CommonJSResolution.joinPaths(cwd, moduleReplacementName);
                src = Source.newBuilder("js", modulePath).build();
            }
            catch (IOException | SecurityException e) {
                throw NpmCompatibleESModuleLoader.fail(FAILED_BUILTIN, specifier);
            }
        } else {
            return this.tryLoadingAsCommonjsModule(moduleRequest.getSpecifier().toJavaStringUncached());
        }
        JSModuleData parsedModule = this.realm.getContext().getEvaluator().envParseModule(this.realm, src);
        JSModuleRecord record = new JSModuleRecord(parsedModule, this);
        this.moduleMap.put(specifier, record);
        return record;
    }

    private JSModuleRecord tryLoadingAsCommonjsModule(String specifier) {
        JSModuleRecord existingModule = (JSModuleRecord)this.moduleMap.get(specifier);
        if (existingModule != null) {
            CommonJSRequireBuiltin.log("IMPORT resolve built-in from cache ", specifier);
            return existingModule;
        }
        JSFunctionObject require = (JSFunctionObject)this.realm.getCommonJSRequireFunctionObject();
        Object maybeModule = JSFunction.call(JSArguments.create(Undefined.instance, require, Strings.fromJavaString(specifier)));
        if (maybeModule == Undefined.instance || !JSDynamicObject.isJSDynamicObject(maybeModule)) {
            throw NpmCompatibleESModuleLoader.fail(FAILED_BUILTIN, specifier);
        }
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
    }

    private URI esmResolve(String specifier, URI parentURL, TruffleLanguage.Env env) {
        URI resolved = this.asURI(specifier);
        if (resolved == null) {
            if (!specifier.isEmpty() && specifier.charAt(0) == '/' || NpmCompatibleESModuleLoader.isRelativePathFileName(specifier)) {
                resolved = NpmCompatibleESModuleLoader.resolveRelativeToParent(specifier, parentURL);
            } else {
                if (!specifier.isEmpty() && specifier.charAt(0) == '#') {
                    throw NpmCompatibleESModuleLoader.fail(UNSUPPORTED_PACKAGE_IMPORTS, specifier);
                }
                resolved = this.packageResolve(specifier, parentURL, env);
            }
        }
        if (resolved == null) {
            return TryCommonJS;
        }
        if (resolved == TryCommonJS || resolved == TryCustomESM) {
            return resolved;
        }
        Format format = null;
        if (NpmCompatibleESModuleLoader.isFileURI(resolved)) {
            if (resolved.toString().toUpperCase().contains("%2F") || resolved.toString().toUpperCase().contains("%5C")) {
                throw NpmCompatibleESModuleLoader.fail(INVALID_MODULE_SPECIFIER, specifier);
            }
            if (NpmCompatibleESModuleLoader.isDirectory(resolved, env)) {
                throw NpmCompatibleESModuleLoader.fail(UNSUPPORTED_DIRECTORY_IMPORT, specifier);
            }
            if (!NpmCompatibleESModuleLoader.fileExists(resolved, env)) {
                throw NpmCompatibleESModuleLoader.fail(MODULE_NOT_FOUND, specifier);
            }
            resolved = resolved.normalize();
            format = this.esmFileFormat(resolved, env);
        } else {
            format = NpmCompatibleESModuleLoader.getAssociatedDefaultFormat(resolved);
        }
        if (format == Format.CommonJS) {
            return TryCommonJS;
        }
        return resolved;
    }

    private Format esmFileFormat(URI url, TruffleLanguage.Env env) {
        assert (NpmCompatibleESModuleLoader.fileExists(url, env));
        if (url.getPath().endsWith(".mjs")) {
            return Format.ESM;
        }
        if (url.getPath().endsWith(".cjs")) {
            return Format.CommonJS;
        }
        if (url.getPath().endsWith(".json")) {
            throw NpmCompatibleESModuleLoader.failMessage(UNSUPPORTED_JSON);
        }
        URI packageUri = this.lookupPackageScope(url, env);
        if (packageUri != null) {
            PackageJson pjson = this.readPackageJson(packageUri, env);
            if (pjson != null && pjson.hasTypeModule() && url.getPath().endsWith(".js")) {
                return Format.ESM;
            }
        } else if (url.getPath().endsWith(".js")) {
            return Format.CommonJS;
        }
        throw NpmCompatibleESModuleLoader.fail(UNSUPPORTED_FILE_EXTENSION, url.toString());
    }

    private URI packageResolve(String packageSpecifier, URI parentURL, TruffleLanguage.Env env) {
        String packageName = null;
        if (packageSpecifier.isEmpty()) {
            throw NpmCompatibleESModuleLoader.fail(INVALID_MODULE_SPECIFIER, packageSpecifier);
        }
        int packageSpecifierSeparator = packageSpecifier.indexOf(47);
        if (packageSpecifier.charAt(0) != '@') {
            packageName = packageSpecifierSeparator != -1 ? packageSpecifier.substring(0, packageSpecifierSeparator) : packageSpecifier;
        } else {
            if (packageSpecifierSeparator == -1) {
                throw NpmCompatibleESModuleLoader.fail(INVALID_MODULE_SPECIFIER, packageSpecifier);
            }
            int secondSeparator = packageSpecifier.indexOf(47, packageSpecifierSeparator + 1);
            packageName = secondSeparator != -1 ? packageSpecifier.substring(0, secondSeparator) : packageSpecifier;
        }
        if (packageName.charAt(0) == '.' || packageName.indexOf(92) >= 0 || packageName.indexOf(37) >= 0) {
            throw NpmCompatibleESModuleLoader.fail(INVALID_MODULE_SPECIFIER, packageSpecifier);
        }
        String packageSpecifierSub = packageSpecifier.substring(packageName.length());
        String packageSubpath = "." + packageSpecifierSub;
        if (packageSubpath.endsWith("/")) {
            throw NpmCompatibleESModuleLoader.fail(INVALID_MODULE_SPECIFIER, packageSpecifier);
        }
        URI selfUrl = this.packageSelfResolve(packageName, parentURL, env);
        if (selfUrl != null) {
            return selfUrl;
        }
        for (TruffleFile currentParentUrl = env.getPublicTruffleFile(parentURL); currentParentUrl != null && !NpmCompatibleESModuleLoader.isRoot(currentParentUrl); currentParentUrl = currentParentUrl.getParent()) {
            URI packageUrl = NpmCompatibleESModuleLoader.getPackageUrl(packageName, currentParentUrl);
            TruffleFile maybeFolder = packageUrl != null ? env.getPublicTruffleFile(packageUrl) : null;
            if (maybeFolder == null || !maybeFolder.exists(new LinkOption[0]) || !maybeFolder.isDirectory(new LinkOption[0])) continue;
            PackageJson pjson = this.readPackageJson(packageUrl, env);
            if (pjson != null && pjson.hasExportsProperty()) {
                throw NpmCompatibleESModuleLoader.fail(UNSUPPORTED_PACKAGE_EXPORTS, packageSpecifier);
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
        return TryCustomESM;
    }

    private static boolean isRoot(TruffleFile file) {
        if (file.isDirectory(new LinkOption[0]) && file.isAbsolute()) {
            return file.getParent() == null;
        }
        return false;
    }

    private URI packageSelfResolve(String packageName, URI parentURL, TruffleLanguage.Env env) {
        URI packageUrl = this.lookupPackageScope(parentURL, env);
        if (packageUrl == null) {
            return null;
        }
        PackageJson pjson = this.readPackageJson(packageUrl, env);
        if (pjson == null || !pjson.hasExportsProperty()) {
            return null;
        }
        if (pjson.namePropertyEquals(packageName)) {
            throw NpmCompatibleESModuleLoader.failMessage(UNSUPPORTED_PACKAGE_EXPORTS);
        }
        return null;
    }

    private URI lookupPackageScope(URI url, TruffleLanguage.Env env) {
        URI scopeUrl = url;
        while (scopeUrl != null && (scopeUrl = NpmCompatibleESModuleLoader.getParentUrl(scopeUrl, env)) != null) {
            if (scopeUrl.toString().endsWith("node_modules")) {
                return null;
            }
            if (this.readPackageJson(scopeUrl, env) == null) continue;
            return scopeUrl;
        }
        return null;
    }

    private PackageJson readPackageJson(URI packageUrl, TruffleLanguage.Env env) {
        URI pjsonUrl = packageUrl.resolve("package.json");
        if (!NpmCompatibleESModuleLoader.fileExists(pjsonUrl, env)) {
            return null;
        }
        JSDynamicObject jsonObj = CommonJSResolution.loadJsonObject(env.getPublicTruffleFile(pjsonUrl), this.realm);
        if (!JSDynamicObject.isJSDynamicObject(jsonObj)) {
            throw NpmCompatibleESModuleLoader.failMessage(INVALID_PACKAGE_CONFIGURATION);
        }
        return new PackageJson(jsonObj);
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
        }
        catch (URISyntaxException uRISyntaxException) {
            return null;
        }
    }

    private static URI getParentUrl(URI scopeUrl, TruffleLanguage.Env env) {
        TruffleFile asFile = env.getPublicTruffleFile(scopeUrl);
        if (asFile.getParent() != null) {
            return asFile.getParent().toUri();
        }
        return null;
    }

    private static Format getAssociatedDefaultFormat(URI resolved) {
        assert (resolved.getPath() != null);
        if (resolved.getPath().endsWith(".mjs")) {
            return Format.ESM;
        }
        return Format.CommonJS;
    }

    private static boolean isDirectory(URI resolved, TruffleLanguage.Env env) {
        return env.getPublicTruffleFile(resolved).isDirectory(new LinkOption[0]);
    }

    private static URI resolveRelativeToParent(String specifier, URI parentURL) {
        return parentURL.resolve(specifier);
    }

    private TruffleFile getFullPath(ScriptOrModule referencingModule) {
        String refPath;
        String string = refPath = referencingModule == null ? null : referencingModule.getSource().getPath();
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
        return NpmCompatibleESModuleLoader.failMessage(errorType + moduleIdentifier + Strings.SINGLE_QUOTE);
    }

    private static boolean isRelativePathFileName(String moduleIdentifier) {
        return moduleIdentifier.startsWith("./") || moduleIdentifier.startsWith("../");
    }

    private static class PackageJson {
        private final JSDynamicObject jsonObj;

        PackageJson(JSDynamicObject jsonObj) {
            assert (jsonObj != null);
            assert (JSObject.isJSObject(jsonObj));
            this.jsonObj = jsonObj;
        }

        boolean hasTypeModule() {
            Object nameValue;
            if (PackageJson.hasNonNullProperty(this.jsonObj, Strings.TYPE) && Strings.isTString(nameValue = JSObject.get(this.jsonObj, Strings.TYPE))) {
                return Strings.equals(Strings.MODULE, (TruffleString)nameValue);
            }
            return false;
        }

        private static boolean hasNonNullProperty(JSDynamicObject object, TruffleString keyName) {
            if (JSObject.hasProperty(object, keyName)) {
                Object value2 = JSObject.get(object, keyName);
                return value2 != Null.instance && value2 != Undefined.instance;
            }
            return false;
        }

        public boolean hasExportsProperty() {
            return PackageJson.hasNonNullProperty(this.jsonObj, Strings.EXPORTS_PROPERTY_NAME);
        }

        public boolean hasMainProperty() {
            if (JSObject.hasProperty(this.jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME)) {
                Object value2 = JSObject.get(this.jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME);
                return Strings.isTString(value2);
            }
            return false;
        }

        public TruffleString getMainProperty() {
            assert (this.hasMainProperty());
            Object value2 = JSObject.get(this.jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME);
            return (TruffleString)value2;
        }

        public boolean namePropertyEquals(String name) {
            Object nameValue;
            TruffleString packageName = Strings.fromJavaString(name);
            if (PackageJson.hasNonNullProperty(this.jsonObj, Strings.NAME) && Strings.isTString(nameValue = JSObject.get(this.jsonObj, Strings.NAME))) {
                return Strings.equals(packageName, (TruffleString)nameValue);
            }
            return false;
        }
    }

    private static enum Format {
        CommonJS,
        ESM;

    }
}

