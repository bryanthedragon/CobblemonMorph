
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.SetPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSSetObject;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSHashMap;

public final class JSSet
extends JSNonProxy
implements JSConstructorFactory.Default.WithSpecies,
PrototypeSupplier {
    public static final JSSet INSTANCE = new JSSet();
    public static final TruffleString CLASS_NAME = Strings.constant("Set");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Set.prototype");
    public static final TruffleString ITERATOR_CLASS_NAME = Strings.constant("Set Iterator");
    public static final TruffleString ITERATOR_PROTOTYPE_NAME = Strings.constant("Set Iterator.prototype");
    private static final TruffleString SIZE = Strings.constant("size");
    public static final HiddenKey SET_ITERATION_KIND_ID = new HiddenKey("SetIterationKind");

    private JSSet() {
    }

    public static JSSetObject create(JSContext context, JSRealm realm) {
        JSObjectFactory factory = context.getSetFactory();
        JSSetObject obj = factory.initProto(new JSSetObject(factory.getShape(realm), new JSHashMap()), realm);
        return context.trackAllocation(obj);
    }

    public static Object normalize(Object value2) {
        if (value2 instanceof Double) {
            return JSSet.normalizeDouble((Double)value2);
        }
        return value2;
    }

    public static Object normalizeDouble(double value2) {
        if (JSRuntime.isNegativeZero(value2)) {
            return 0;
        }
        if (JSRuntime.doubleIsRepresentableAsInt(value2)) {
            return (int)value2;
        }
        return value2;
    }

    public static JSHashMap getInternalSet(JSDynamicObject obj) {
        assert (JSSet.isJSSet(obj));
        return ((JSSetObject)obj).getMap();
    }

    public static int getSetSize(JSDynamicObject obj) {
        assert (JSSet.isJSSet(obj));
        return JSSet.getInternalSet(obj).size();
    }

    private static JSDynamicObject createSizeGetterFunction(JSRealm realm) {
        JSFunctionData getterData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.SetGetSize, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                private final BranchProfile errorBranch = BranchProfile.create();

                @Override
                public Object execute(VirtualFrame frame) {
                    Object obj = frame.getArguments()[0];
                    if (JSSet.isJSSet(obj)) {
                        return JSSet.getSetSize((JSSetObject)obj);
                    }
                    this.errorBranch.enter();
                    throw Errors.createTypeErrorSetExpected();
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 0, Strings.concat(Strings.GET_SPC, SIZE));
        });
        JSFunctionObject sizeGetter = JSFunction.create(realm, getterData);
        return sizeGetter;
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
        JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)prototype, (Object)SIZE, JSSet.createSizeGetterFunction(realm), Undefined.instance);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, SetPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
        Object values = JSDynamicObject.getOrNull(prototype, Strings.VALUES);
        JSObjectUtil.putDataProperty(ctx, prototype, Strings.KEYS, values, JSAttributes.getDefaultNotEnumerable());
        JSObjectUtil.putDataProperty(ctx, prototype, Symbol.SYMBOL_ITERATOR, values, JSAttributes.getDefaultNotEnumerable());
        if (ctx.getContextOptions().isNewSetMethods()) {
            JSObjectUtil.putFunctionsFromContainer(realm, prototype, SetPrototypeBuiltins.NEW_SET_BUILTINS);
        }
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
        JSHashMap set2 = JSSet.getInternalSet(obj);
        return JSRuntime.collectionToConsoleString(obj, allowSideEffects, format, this.getClassName(obj), set2, depth);
    }

    public static boolean isJSSet(Object obj) {
        return obj instanceof JSSetObject;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getSetPrototype();
    }
}

