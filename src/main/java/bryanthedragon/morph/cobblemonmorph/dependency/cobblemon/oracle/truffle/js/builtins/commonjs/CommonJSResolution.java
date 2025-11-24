
package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.builtins.commonjs.CommonJSRequireBuiltin;
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
import java.nio.file.LinkOption;
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
        if (callerSource != null) {
            return callerSource.getPath();
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    static TruffleFile resolve(JSRealm realm, String moduleIdentifier, TruffleFile entryPath) {
        TruffleFile module;
        if (moduleIdentifier.isEmpty()) {
            return null;
        }
        TruffleLanguage.Env env = realm.getEnv();
        TruffleFile currentWorkingPath = entryPath;
        if (moduleIdentifier.charAt(0) == '/') {
            currentWorkingPath = CommonJSResolution.getFileSystemRootPath(env);
        }
        if (CommonJSResolution.isPathFileName(moduleIdentifier) && (module = CommonJSResolution.loadAsFileOrDirectory(realm, CommonJSResolution.joinPaths(currentWorkingPath, moduleIdentifier))) != null) {
            return module;
        }
        return CommonJSResolution.loadNodeModulesOrSelfReference(realm, moduleIdentifier, currentWorkingPath);
    }

    private static TruffleFile loadNodeModulesOrSelfReference(JSRealm realm, String moduleIdentifier, TruffleFile startFolder) {
        List<TruffleFile> nodeModulesPaths = CommonJSResolution.getNodeModulesPaths(startFolder);
        for (TruffleFile s : nodeModulesPaths) {
            TruffleFile module = CommonJSResolution.loadAsFileOrDirectory(realm, CommonJSResolution.joinPaths(s, moduleIdentifier));
            if (module == null) continue;
            return module;
        }
        return null;
    }

    public static TruffleFile loadIndex(TruffleFile modulePath) {
        TruffleFile indexJs = CommonJSResolution.joinPaths(modulePath, INDEX_JS);
        if (CommonJSResolution.fileExists(indexJs)) {
            return indexJs;
        }
        TruffleFile indexJson = CommonJSResolution.joinPaths(modulePath, INDEX_JSON);
        if (CommonJSResolution.fileExists(indexJson)) {
            return indexJson;
        }
        if (CommonJSResolution.fileExists(CommonJSResolution.joinPaths(modulePath, INDEX_NODE))) {
            return null;
        }
        return null;
    }

    static TruffleFile loadAsFile(TruffleLanguage.Env env, TruffleFile modulePath) {
        if (CommonJSResolution.fileExists(modulePath)) {
            return modulePath;
        }
        TruffleFile moduleJs = env.getPublicTruffleFile(modulePath.toString() + Strings.JS_EXT);
        if (CommonJSResolution.fileExists(moduleJs)) {
            return moduleJs;
        }
        TruffleFile moduleJson = env.getPublicTruffleFile(modulePath.toString() + Strings.JSON_EXT);
        if (CommonJSResolution.fileExists(moduleJson)) {
            return moduleJson;
        }
        if (CommonJSResolution.fileExists(env.getPublicTruffleFile(modulePath.toString() + Strings.NODE_EXT))) {
            return null;
        }
        return null;
    }

    public static List<TruffleFile> getNodeModulesPaths(TruffleFile path) {
        ArrayList<TruffleFile> list = new ArrayList<TruffleFile>();
        List<TruffleFile> paths = CommonJSResolution.getAllParentPaths(path);
        for (TruffleFile p : paths) {
            if (p.endsWith(NODE_MODULES)) {
                list.add(p);
                continue;
            }
            TruffleFile truffleFile = p.resolve(NODE_MODULES);
            list.add(truffleFile);
        }
        return list;
    }

    private static TruffleFile loadAsFileOrDirectory(JSRealm realm, TruffleFile modulePath) {
        TruffleFile maybeFile = CommonJSResolution.loadAsFile(realm.getEnv(), modulePath);
        if (maybeFile == null) {
            return CommonJSResolution.loadAsDirectory(realm, modulePath);
        }
        return maybeFile;
    }

    private static List<TruffleFile> getAllParentPaths(TruffleFile from) {
        ArrayList<TruffleFile> paths = new ArrayList<TruffleFile>();
        for (TruffleFile p = from; p != null; p = p.getParent()) {
            paths.add(p);
        }
        return paths;
    }

    private static TruffleFile loadAsDirectory(JSRealm realm, TruffleFile modulePath) {
        TruffleFile packageJson = CommonJSResolution.joinPaths(modulePath, PACKAGE_JSON);
        if (CommonJSResolution.fileExists(packageJson)) {
            JSDynamicObject jsonObj = CommonJSResolution.loadJsonObject(packageJson, realm);
            if (JSDynamicObject.isJSDynamicObject(jsonObj)) {
                Object main = JSObject.get(jsonObj, Strings.PACKAGE_JSON_MAIN_PROPERTY_NAME);
                if (!Strings.isTString(main)) {
                    return CommonJSResolution.loadIndex(modulePath);
                }
                TruffleFile module = CommonJSResolution.joinPaths(modulePath, JSRuntime.safeToString(main).toJavaStringUncached());
                TruffleFile asFile = CommonJSResolution.loadAsFile(realm.getEnv(), module);
                if (asFile != null) {
                    return asFile;
                }
                return CommonJSResolution.loadIndex(module);
            }
        } else {
            return CommonJSResolution.loadIndex(modulePath);
        }
        return null;
    }

    public static JSDynamicObject loadJsonObject(TruffleFile jsonFile, JSRealm realm) {
        try {
            if (CommonJSResolution.fileExists(jsonFile)) {
                Source source = null;
                TruffleFile file = GlobalBuiltins.resolveRelativeFilePath(jsonFile.toString(), realm.getEnv());
                if (file.isRegularFile(new LinkOption[0])) {
                    source = CommonJSResolution.sourceFromTruffleFile(file);
                }
                if (source == null) {
                    return null;
                }
                JSFunctionObject parse2 = (JSFunctionObject)realm.getJsonParseFunctionObject();
                TruffleString jsonString = Strings.fromJavaString(source.getCharacters().toString());
                Object jsonObj = JSFunction.call(JSArguments.create(Undefined.instance, parse2, jsonString));
                if (JSDynamicObject.isJSDynamicObject(jsonObj)) {
                    return (JSDynamicObject)jsonObj;
                }
            }
            return null;
        }
        catch (IllegalArgumentException | SecurityException | UnsupportedOperationException e) {
            throw Errors.createErrorFromException(e);
        }
    }

    private static Source sourceFromTruffleFile(TruffleFile file) {
        try {
            return Source.newBuilder("js", file).build();
        }
        catch (IOException | IllegalArgumentException | SecurityException | UnsupportedOperationException e) {
            return null;
        }
    }

    public static boolean fileExists(TruffleFile modulePath) {
        return modulePath.exists(new LinkOption[0]) && modulePath.isRegularFile(new LinkOption[0]);
    }

    private static boolean isPathFileName(String moduleIdentifier) {
        return moduleIdentifier.startsWith("/") || moduleIdentifier.startsWith("./") || moduleIdentifier.startsWith("../");
    }

    public static TruffleFile joinPaths(TruffleFile p1, String p2) {
        Objects.requireNonNull(p1);
        try {
            return p1.resolve(p2).getAbsoluteFile().normalize();
        }
        catch (InvalidPathException e) {
            throw CommonJSRequireBuiltin.fail(p2);
        }
    }

    private static TruffleFile getFileSystemRootPath(TruffleLanguage.Env env) {
        TruffleFile root;
        TruffleFile last = root = env.getCurrentWorkingDirectory();
        while (root != null) {
            last = root;
            root = root.getParent();
        }
        return last;
    }
}

