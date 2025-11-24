
package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.builtins.commonjs.CommonJSRequireBuiltin;
import com.oracle.truffle.js.builtins.commonjs.CommonJSResolution;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;

public abstract class CommonJSResolveBuiltin
extends GlobalBuiltins.JSFileLoadingOperation {
    CommonJSResolveBuiltin(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected TruffleString resolve(TruffleString moduleIdentifier) {
        JSRealm realm = this.getRealm();
        TruffleFile cwd = CommonJSRequireBuiltin.getModuleResolveCurrentWorkingDirectory(this.getContext(), realm.getEnv());
        TruffleFile maybeModule = CommonJSResolution.resolve(realm, moduleIdentifier.toJavaStringUncached(), cwd);
        if (maybeModule == null) {
            throw CommonJSResolveBuiltin.fail(moduleIdentifier);
        }
        return Strings.fromJavaString(maybeModule.getAbsoluteFile().normalize().toString());
    }

    @CompilerDirectives.TruffleBoundary
    private static JSException fail(TruffleString moduleIdentifier) {
        return JSException.create(JSErrorType.TypeError, "Cannot find module: '" + moduleIdentifier + "'");
    }
}

