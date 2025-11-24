
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.SymbolFunctionBuiltins;
import com.oracle.truffle.js.builtins.SymbolPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
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
import com.oracle.truffle.js.runtime.builtins.JSSymbolObject;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class JSSymbol
extends JSNonProxy
implements JSConstructorFactory.WithFunctions,
PrototypeSupplier {
    public static final JSSymbol INSTANCE = new JSSymbol();
    public static final TruffleString TYPE_NAME = Strings.SYMBOL;
    public static final TruffleString CLASS_NAME = Strings.UC_SYMBOL;
    public static final TruffleString PROTOTYPE_NAME = Strings.concat(CLASS_NAME, Strings.DOT_PROTOTYPE);
    public static final TruffleString DESCRIPTION = Strings.constant("description");

    private JSSymbol() {
    }

    public static JSSymbolObject create(JSContext context, JSRealm realm, Symbol symbol) {
        JSSymbolObject mapObj = JSSymbolObject.create(realm, context.getSymbolFactory(), symbol);
        return context.trackAllocation(mapObj);
    }

    public static Symbol getSymbolData(JSDynamicObject symbolWrapper) {
        assert (JSSymbol.isJSSymbol(symbolWrapper));
        return ((JSSymbolObject)symbolWrapper).getSymbol();
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, SymbolPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
        if (ctx.getContextOptions().getEcmaScriptVersion() >= 10) {
            JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)prototype, (Object)DESCRIPTION, JSSymbol.createDescriptionGetterFunction(realm), Undefined.instance);
        }
        return prototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, SymbolFunctionBuiltins.BUILTINS);
    }

    private static JSDynamicObject createDescriptionGetterFunction(JSRealm realm) {
        JSFunctionData getterData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.SymbolGetDescription, c -> {
            RootCallTarget callTarget = new JavaScriptRootNode(c.getLanguage(), null, null){
                private final ConditionProfile isSymbolProfile = ConditionProfile.createBinaryProfile();
                private final ConditionProfile isJSSymbolProfile = ConditionProfile.createBinaryProfile();

                @Override
                public Object execute(VirtualFrame frame) {
                    Object obj = frame.getArguments()[0];
                    if (this.isSymbolProfile.profile(obj instanceof Symbol)) {
                        return ((Symbol)obj).getDescription();
                    }
                    if (this.isJSSymbolProfile.profile(JSSymbol.isJSSymbol(obj))) {
                        return JSSymbol.getSymbolData((JSDynamicObject)obj).getDescription();
                    }
                    throw Errors.createTypeErrorSymbolExpected();
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(c, callTarget, 0, Strings.concat(Strings.GET_SPC, DESCRIPTION));
        });
        return JSFunction.create(realm, getterData);
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
        return Strings.addBrackets(CLASS_NAME);
    }

    public static boolean isJSSymbol(Object obj) {
        return obj instanceof JSSymbolObject;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getSymbolPrototype();
    }
}

