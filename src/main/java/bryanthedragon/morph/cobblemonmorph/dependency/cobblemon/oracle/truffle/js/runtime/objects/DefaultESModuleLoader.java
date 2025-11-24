
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
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleLoader;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemException;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultESModuleLoader
implements JSModuleLoader {
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String DOT_SLASH = "./";
    public static final String DOT_DOT_SLASH = "../";
    private static final int JS_MODULE_TYPE = 1;
    private static final int JSON_MODULE_TYPE = 2;
    protected final JSRealm realm;
    protected final Map<String, JSModuleRecord> moduleMap = new HashMap<String, JSModuleRecord>();

    public static DefaultESModuleLoader create(JSRealm realm) {
        return new DefaultESModuleLoader(realm);
    }

    protected DefaultESModuleLoader(JSRealm realm) {
        this.realm = realm;
    }

    protected URI asURI(String specifier) {
        assert (specifier != null);
        if (specifier.indexOf(58) == -1) {
            return null;
        }
        try {
            URI uri = new URI(specifier);
            return uri.getScheme() != null ? uri : null;
        }
        catch (URISyntaxException e) {
            return null;
        }
    }

    @Override
    public JSModuleRecord resolveImportedModule(ScriptOrModule referrer, Module.ModuleRequest moduleRequest) {
        String refPath = referrer == null ? null : referrer.getSource().getPath();
        try {
            TruffleFile moduleFile;
            String canonicalPath;
            TruffleString specifierTS = moduleRequest.getSpecifier();
            String specifier = Strings.toJavaString(specifierTS);
            URI maybeUri = this.asURI(specifier);
            TruffleString maybeCustomPath = this.realm.getCustomEsmPathMapping(Strings.fromJavaString(refPath), specifierTS);
            if (maybeCustomPath != null) {
                canonicalPath = maybeCustomPath.toJavaStringUncached();
                moduleFile = this.realm.getEnv().getPublicTruffleFile(canonicalPath).getCanonicalFile(new LinkOption[0]);
            } else {
                if (refPath == null) {
                    moduleFile = maybeUri != null ? this.realm.getEnv().getPublicTruffleFile(maybeUri) : this.realm.getEnv().getPublicTruffleFile(specifier);
                } else {
                    TruffleFile refFile = this.realm.getEnv().getPublicTruffleFile(refPath);
                    if (maybeUri != null) {
                        String uriFile = this.realm.getEnv().getPublicTruffleFile(maybeUri).getCanonicalFile(new LinkOption[0]).getPath();
                        moduleFile = refFile.resolveSibling(uriFile);
                    } else {
                        moduleFile = this.bareSpecifierDirectLookup(specifier) ? this.realm.getEnv().getPublicTruffleFile(specifier) : refFile.resolveSibling(specifier);
                    }
                }
                canonicalPath = null;
            }
            return this.loadModuleFromUrl(referrer, moduleRequest, moduleFile, canonicalPath);
        }
        catch (FileSystemException fsex) {
            String fileName = fsex.getFile();
            if (Objects.equals(fsex.getMessage(), fileName)) {
                String message = "Error reading: " + fileName;
                if (this.realm.getContext().isOptionV8CompatibilityMode()) {
                    throw UserScriptException.create(Strings.fromJavaString(message));
                }
                throw Errors.createError(message);
            }
            throw Errors.createErrorFromException(fsex);
        }
        catch (IOException | SecurityException | UnsupportedOperationException | InvalidPathException e) {
            throw Errors.createErrorFromException(e);
        }
    }

    private boolean bareSpecifierDirectLookup(String specifier) {
        JSContextOptions options = this.realm.getContext().getContextOptions();
        if (options.isEsmBareSpecifierRelativeLookup()) {
            return false;
        }
        return !specifier.startsWith(SLASH) && !specifier.startsWith(DOT_SLASH) && !specifier.startsWith(DOT_DOT_SLASH);
    }

    protected JSModuleRecord loadModuleFromUrl(ScriptOrModule referrer, Module.ModuleRequest moduleRequest, TruffleFile maybeModuleFile, String maybeCanonicalPath) throws IOException {
        JSModuleRecord newModule;
        TruffleFile moduleFile;
        String canonicalPath;
        JSModuleRecord existingModule;
        if (maybeCanonicalPath == null) {
            if (!maybeModuleFile.exists(new LinkOption[0]) && (existingModule = this.moduleMap.get(canonicalPath = maybeModuleFile.getPath())) != null) {
                return existingModule;
            }
            moduleFile = maybeModuleFile.getCanonicalFile(new LinkOption[0]);
            canonicalPath = moduleFile.getPath();
        } else {
            moduleFile = maybeModuleFile;
            canonicalPath = maybeCanonicalPath;
        }
        existingModule = this.moduleMap.get(canonicalPath);
        if (existingModule != null) {
            return existingModule;
        }
        Source source = Source.newBuilder("js", moduleFile).name(Strings.toJavaString(moduleRequest.getSpecifier())).mimeType("application/javascript+module").build();
        Map<TruffleString, TruffleString> assertions = moduleRequest.getAssertions();
        int moduleType = this.getModuleType(moduleFile.getName());
        TruffleString assertedType = assertions.get(JSContext.getTypeImportAssertion());
        if (!DefaultESModuleLoader.doesModuleTypeMatchAssertionType(assertedType, moduleType)) {
            throw Errors.createTypeError("Invalid module type was asserted");
        }
        if (DefaultESModuleLoader.isModuleType(moduleType, 2)) {
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

    private static boolean doesModuleTypeMatchAssertionType(TruffleString assertedType, int moduleType) {
        if (assertedType == null) {
            return true;
        }
        if (Strings.equals(Strings.JSON, assertedType)) {
            return DefaultESModuleLoader.isModuleType(moduleType, 2);
        }
        return false;
    }

    private int getModuleType(String moduleName) {
        if (this.realm.getContext().getContextOptions().isJsonModules() && moduleName.endsWith(".json")) {
            return 2;
        }
        return 1;
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
        String canonicalPath;
        String path = source.getPath();
        if (path == null) {
            canonicalPath = source.getName();
        } else {
            try {
                TruffleFile moduleFile;
                if (this.realm.getEnv().getFileNameSeparator().equals("\\") && path.startsWith(SLASH)) {
                    path = path.substring(1);
                }
                canonicalPath = (moduleFile = this.realm.getEnv().getPublicTruffleFile(path)).exists(new LinkOption[0]) ? moduleFile.getCanonicalFile(new LinkOption[0]).getPath() : path;
            }
            catch (IOException | SecurityException e) {
                throw Errors.createErrorFromException(e);
            }
        }
        return canonicalPath;
    }
}

