
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.unary.JSIsArrayNodeGen;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ImportStatic(value={CompilerDirectives.class})
public abstract class JSIsArrayNode
extends JavaScriptBaseNode {
    final boolean jsType;

    protected JSIsArrayNode(boolean jsType) {
        this.jsType = jsType;
    }

    public abstract boolean execute(Object var1);

    @Specialization(guards={"!cachedIsProxy", "cachedClass != null", "isExact(object, cachedClass)"}, limit="1")
    protected static boolean doIsArrayClass(Object object, @Cached(value="getClassIfJSDynamicObject(object)") Class<?> cachedClass, @Cached(value="isJSArray(object)") boolean cachedIsArray, @Cached(value="isJSProxy(object)") boolean cachedIsProxy) {
        return cachedIsArray;
    }

    @Specialization(guards={"isJSArray(object)"}, replaces={"doIsArrayClass"})
    protected boolean doJSArray(Object object) {
        return true;
    }

    @Specialization(guards={"isJSProxy(object)"})
    protected boolean doJSProxy(JSDynamicObject object) {
        return JSRuntime.isProxyAnArray(object);
    }

    @Specialization(guards={"!isJSArray(object)", "!isJSProxy(object)", "isJSDynamicObject(object)"}, replaces={"doIsArrayClass"})
    protected boolean doJSObject(Object object) {
        assert (!JSRuntime.isArray(object));
        return false;
    }

    @Specialization(guards={"!isJSDynamicObject(object)", "jsType"})
    protected boolean doNotObject(Object object) {
        assert (!JSRuntime.isArray(object) || JSRuntime.isForeignObject(object));
        return false;
    }

    @Specialization(guards={"!isJSDynamicObject(object)", "!jsType"})
    protected boolean doPrimitiveOrForeign(Object object, @CachedLibrary(limit="6") InteropLibrary interop) {
        return interop.hasArrayElements(object);
    }

    public static JSIsArrayNode createIsArrayLike() {
        return JSIsArrayNodeGen.create(false);
    }

    public static JSIsArrayNode createIsArray() {
        return JSIsArrayNodeGen.create(true);
    }
}

