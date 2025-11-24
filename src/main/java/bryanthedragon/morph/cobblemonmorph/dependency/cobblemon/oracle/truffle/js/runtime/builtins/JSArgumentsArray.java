
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSArgumentsArray
extends JSAbstractArgumentsArray {
    public static final JSArgumentsArray INSTANCE = new JSArgumentsArray();

    private JSArgumentsArray() {
    }

    public static JSArgumentsObject.Unmapped createUnmapped(Shape shape, Object[] elements2) {
        return new JSArgumentsObject.Unmapped(shape, ScriptArray.createConstantArray(elements2), elements2, elements2.length);
    }

    public static JSArgumentsObject.Mapped createMapped(Shape shape, Object[] elements2) {
        return new JSArgumentsObject.Mapped(shape, ScriptArray.createConstantArray(elements2), elements2, elements2.length);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSArgumentsObject createStrictSlow(JSRealm realm, Object[] elements2) {
        JSContext context = realm.getContext();
        JSObjectFactory factory = context.getStrictArgumentsFactory();
        JSArgumentsObject.Unmapped argumentsObject = JSArgumentsArray.createUnmapped(factory.getShape(realm), elements2);
        factory.initProto(argumentsObject, realm);
        JSObjectUtil.putDataProperty(context, argumentsObject, LENGTH, elements2.length, JSAttributes.configurableNotEnumerableWritable());
        JSObjectUtil.putDataProperty(context, argumentsObject, Symbol.SYMBOL_ITERATOR, realm.getArrayProtoValuesIterator(), JSAttributes.configurableNotEnumerableWritable());
        Accessor throwerAccessor = realm.getThrowerAccessor();
        JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)argumentsObject, (Object)CALLEE, throwerAccessor, JSAttributes.notConfigurableNotEnumerable());
        if (context.getEcmaScriptVersion() < 8) {
            JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)argumentsObject, (Object)CALLER, throwerAccessor, JSAttributes.notConfigurableNotEnumerable());
        }
        return context.trackAllocation(argumentsObject);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSArgumentsObject createNonStrictSlow(JSRealm realm, Object[] elements2, JSDynamicObject callee) {
        JSContext context = realm.getContext();
        JSObjectFactory factory = context.getNonStrictArgumentsFactory();
        JSArgumentsObject.Mapped argumentsObject = JSArgumentsArray.createMapped(factory.getShape(realm), elements2);
        factory.initProto(argumentsObject, realm);
        JSObjectUtil.putDataProperty(context, argumentsObject, LENGTH, elements2.length, JSAttributes.configurableNotEnumerableWritable());
        JSObjectUtil.putDataProperty(context, argumentsObject, Symbol.SYMBOL_ITERATOR, realm.getArrayProtoValuesIterator(), JSAttributes.configurableNotEnumerableWritable());
        JSObjectUtil.putDataProperty(context, argumentsObject, CALLEE, callee, JSAttributes.configurableNotEnumerableWritable());
        return context.trackAllocation(argumentsObject);
    }

    public static boolean isJSArgumentsObject(Object obj) {
        return obj instanceof JSArgumentsObject;
    }

    public static boolean isJSFastArgumentsObject(Object obj) {
        return JSArgumentsArray.isJSArgumentsObject(obj) && JSArgumentsArray.isInstance((JSArgumentsObject)obj, (JSClass)INSTANCE);
    }
}

