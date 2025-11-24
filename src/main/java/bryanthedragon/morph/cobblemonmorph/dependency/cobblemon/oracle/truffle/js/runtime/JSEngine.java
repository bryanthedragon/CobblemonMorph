
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Evaluator;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSContextOptions;
import java.util.ServiceLoader;

public final class JSEngine {
    private static final JSEngine INSTANCE = new JSEngine();
    private final Evaluator parser;

    private JSEngine() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        this.parser = ServiceLoader.load(Evaluator.class, classLoader).iterator().next();
    }

    public static JSEngine getInstance() {
        return INSTANCE;
    }

    public Evaluator getEvaluator() {
        return this.parser;
    }

    public Evaluator getParser() {
        return this.parser;
    }

    private JSContext createContext(JavaScriptLanguage language, TruffleLanguage.Env env) {
        JSContextOptions contextOptions = JSContextOptions.fromOptionValues(env.getOptions());
        return JSContext.createContext(this.parser, contextOptions, language, env);
    }

    public JSContext createContext(JavaScriptLanguage language, JSContextOptions contextOptions, TruffleLanguage.Env env) {
        return JSContext.createContext(this.parser, contextOptions, language, env);
    }

    public static JSContext createJSContext(JavaScriptLanguage language, TruffleLanguage.Env env) {
        return JSEngine.getInstance().createContext(language, env);
    }
}

