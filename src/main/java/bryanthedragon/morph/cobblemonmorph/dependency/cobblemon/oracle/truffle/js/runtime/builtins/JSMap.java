
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.MapPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSMapObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSHashMap;

public final class JSMap
extends JSNonProxy
implements JSConstructorFactory.Default.WithSpecies,
PrototypeSupplier {
    public static final JSMap INSTANCE = new JSMap();
    public static final TruffleString CLASS_NAME = Strings.constant("Map");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Map.prototype");
    public static final TruffleString ITERATOR_CLASS_NAME = Strings.constant("Map Iterator");
    public static final TruffleString ITERATOR_PROTOTYPE_NAME = Strings.constant("Map Iterator.prototype");
    private static final TruffleString SIZE = Strings.constant("size");
    public static final HiddenKey MAP_ITERATION_KIND_ID = new HiddenKey("MapIterationKind");

    private JSMap() {
    }

    public static JSMapObject create(JSContext context, JSRealm realm) {
        JSObjectFactory factory = context.getMapFactory();
        JSMapObject obj = factory.initProto(new JSMapObject(factory.getShape(realm), new JSHashMap()), realm);
        return context.trackAllocation(obj);
    }

    public static JSHashMap getInternalMap(JSDynamicObject obj) {
        assert (JSMap.isJSMap(obj));
        return ((JSMapObject)obj).getMap();
    }

    public static int getMapSize(JSDynamicObject obj) {
        assert (JSMap.isJSMap(obj));
        return JSMap.getInternalMap(obj).size();
    }

    private static JSFunctionObject createSizeGetterFunction(JSRealm realm) {
        JSFunctionData getterData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.MapGetSize, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                private final BranchProfile errorBranch = BranchProfile.create();

                @Override
                public Object execute(VirtualFrame frame) {
                    Object obj = frame.getArguments()[0];
                    if (JSMap.isJSMap(obj)) {
                        return JSMap.getMapSize((JSMapObject)obj);
                    }
                    this.errorBranch.enter();
                    throw Errors.createTypeErrorMapExpected();
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 0, Strings.concat(Strings.GET_SPC, SIZE));
        });
        return JSFunction.create(realm, getterData);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
        JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)prototype, (Object)SIZE, JSMap.createSizeGetterFunction(realm), Undefined.instance);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, MapPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
        Object entriesFunction = JSDynamicObject.getOrNull(prototype, JSArray.ENTRIES);
        JSObjectUtil.putDataProperty(ctx, prototype, Symbol.SYMBOL_ITERATOR, entriesFunction, JSAttributes.getDefaultNotEnumerable());
        return prototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return Strings.concatAll(Strings.BRACKET_OPEN, this.getClassName(), Strings.BRACKET_CLOSE);
        }
        JSHashMap map = JSMap.getInternalMap(obj);
        return JSRuntime.collectionToConsoleString(obj, allowSideEffects, format, this.getClassName(obj), map, depth);
    }

    public static boolean isJSMap(Object obj) {
        return obj instanceof JSMapObject;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getMapPrototype();
    }
}

