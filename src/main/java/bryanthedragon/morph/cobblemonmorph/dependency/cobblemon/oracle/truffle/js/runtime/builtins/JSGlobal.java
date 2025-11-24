
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSGlobalObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;

public final class JSGlobal
extends JSNonProxy {
    public static final TruffleString CLASS_NAME = Strings.constant("global");
    public static final TruffleString EVAL_NAME = Strings.constant("eval");
    public static final JSGlobal INSTANCE = new JSGlobal();

    private JSGlobal() {
    }

    public static JSObject create(JSRealm realm, JSDynamicObject objectPrototype) {
        CompilerAsserts.neverPartOfCompilation();
        JSContext context = realm.getContext();
        JSObjectFactory factory = context.getGlobalObjectFactory();
        JSGlobalObject global = new JSGlobalObject(factory.getShape(realm));
        factory.initProto(global, objectPrototype);
        JSObjectUtil.putToStringTag(global, CLASS_NAME);
        return global;
    }

    public static Shape makeGlobalObjectShape(JSContext context, JSDynamicObject objectPrototype) {
        boolean singleContext = !context.isMultiContext();
        Shape globalObjectShape = JSShape.newBuilder(context, INSTANCE, singleContext ? objectPrototype : null).propertyAssumptions(singleContext).build();
        if (singleContext) {
            globalObjectShape = Shape.newBuilder(globalObjectShape).addConstantProperty(JSObject.HIDDEN_PROTO, objectPrototype, 0).build();
        }
        return globalObjectShape;
    }

    public static JSObject createGlobalScope(JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        return new JSGlobalObject(context.getGlobalScopeShape());
    }

    public static boolean isJSGlobalObject(Object obj) {
        return JSDynamicObject.isJSDynamicObject(obj) && JSGlobal.isJSGlobalObject((JSDynamicObject)obj);
    }

    public static boolean isJSGlobalObject(JSDynamicObject obj) {
        return JSGlobal.isInstance(obj, (JSClass)INSTANCE);
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return CLASS_NAME;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        if (JSObject.getPrototype(thisObj) == newPrototype) {
            return true;
        }
        JSObject.getJSContext(thisObj).getGlobalObjectPristineAssumption().invalidate();
        return super.setPrototypeOf(thisObj, newPrototype);
    }
}

