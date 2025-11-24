
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class JSPrimitive
extends JSNonProxy
implements PrototypeSupplier {
    protected JSPrimitive() {
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public final Object getHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        JSRealm realm;
        JSContext context;
        assert (this == JSNumber.INSTANCE || this == JSString.INSTANCE || this == JSBoolean.INSTANCE || this == JSBigInt.INSTANCE);
        Object propertyValue = super.getHelper(store, thisObj, key, encapsulatingNode);
        if (Strings.isTString(key) && JSPrimitive.allowJavaMembersFor(thisObj) && (context = JSObject.getJSContext(store)).isOptionNashornCompatibilityMode() && (realm = JSRealm.get(null)).isJavaInteropEnabled() && propertyValue == null) {
            return JSPrimitive.getJavaProperty(thisObj, Strings.toJavaString((TruffleString)key), realm);
        }
        return propertyValue;
    }

    private static Object getJavaProperty(Object thisObj, String name, JSRealm realm) {
        String thisStr = Strings.toJavaString((TruffleString)thisObj);
        Object boxedString = realm.getEnv().asBoxedGuestValue(thisStr);
        try {
            return InteropLibrary.getUncached().readMember(boxedString, name);
        }
        catch (UnknownIdentifierException | UnsupportedMessageException e) {
            return Undefined.instance;
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getMethodHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        Object method;
        JSRealm realm;
        JSContext context;
        if (Strings.isTString(key) && JSPrimitive.allowJavaMembersFor(thisObj) && (context = JSObject.getJSContext(store)).isOptionNashornCompatibilityMode() && (realm = JSRealm.get(null)).isJavaInteropEnabled() && this.hasOwnProperty(store, key) && (method = JSPrimitive.getJavaMethod(thisObj, Strings.toJavaString((TruffleString)key), realm)) != null) {
            return method;
        }
        return super.getMethodHelper(store, thisObj, key, encapsulatingNode);
    }

    private static Object getJavaMethod(Object thisObj, String name, JSRealm realm) {
        String thisStr = Strings.toJavaString((TruffleString)thisObj);
        Object boxedString = realm.getEnv().asBoxedGuestValue(thisStr);
        try {
            return InteropLibrary.getUncached().readMember(boxedString, name);
        }
        catch (UnknownIdentifierException | UnsupportedMessageException e) {
            return null;
        }
    }

    private static boolean allowJavaMembersFor(Object thisObj) {
        return thisObj instanceof TruffleString;
    }
}

