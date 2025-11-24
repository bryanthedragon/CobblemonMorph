
package com.oracle.truffle.js.runtime.java;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.java.JavaPackageObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JavaPackage
extends JSNonProxy {
    public static final TruffleString TYPE_NAME = Strings.constant("object");
    public static final TruffleString CLASS_NAME = Strings.constant("JavaPackage");
    public static final TruffleString SYMBOL_TO_PRIMITIVE_NAME = Strings.constant("[Symbol.toPrimitive]");
    public static final JavaPackage INSTANCE = new JavaPackage();

    private JavaPackage() {
    }

    public static JavaPackageObject create(JSContext context, JSRealm realm, TruffleString packageName) {
        JavaPackageObject obj = JavaPackage.createInstance(context, realm, packageName);
        return context.trackAllocation(obj);
    }

    public static JavaPackageObject createInit(JSRealm realm, TruffleString packageName) {
        CompilerAsserts.neverPartOfCompilation();
        JSContext context = realm.getContext();
        return JavaPackage.createInstance(context, realm, packageName);
    }

    private static JavaPackageObject createInstance(JSContext context, JSRealm realm, TruffleString packageName) {
        JSObjectFactory factory = context.getJavaPackageFactory();
        JavaPackageObject obj = new JavaPackageObject(factory.getShape(realm), packageName);
        factory.initProto(obj, realm);
        JSObjectUtil.putDataProperty(context, obj, Symbol.SYMBOL_TO_PRIMITIVE, realm.getJavaPackageToPrimitiveFunction(), JSAttributes.notConfigurableNotEnumerableNotWritable());
        return obj;
    }

    public static boolean isJavaPackage(Object obj) {
        return obj instanceof JavaPackageObject;
    }

    public static TruffleString getPackageName(JSDynamicObject obj) {
        assert (JavaPackage.isJavaPackage(obj));
        return ((JavaPackageObject)obj).getPackageName();
    }

    @CompilerDirectives.TruffleBoundary
    public static Object lookupClass(JSRealm realm, JSDynamicObject thisObj, TruffleString className) {
        Object javaType;
        TruffleLanguage.Env env = realm.getEnv();
        assert (env.isHostLookupAllowed());
        TruffleString qualifiedName = JavaPackage.prependPackageName(thisObj, className);
        try {
            javaType = env.lookupHostSymbol(Strings.toJavaString(qualifiedName));
        }
        catch (Exception e) {
            return null;
        }
        if (javaType == null) {
            return null;
        }
        if (env.isHostObject(javaType) && InteropLibrary.getUncached().isMetaObject(javaType)) {
            return javaType;
        }
        return null;
    }

    public static JSDynamicObject subpackage(JSContext context, JSRealm realm, JSDynamicObject thisObj, TruffleString name) {
        return JavaPackage.create(context, realm, JavaPackage.prependPackageName(thisObj, name));
    }

    public static Object getJavaClassOrConstructorOrSubPackage(JSContext context, JSDynamicObject thisObj, TruffleString name) {
        int openParen;
        JSRealm realm = JSRealm.get(null);
        if (context.isOptionNashornCompatibilityMode() && Strings.endsWith(name, Strings.PAREN_CLOSE) && (openParen = Strings.indexOf(name, '(')) != -1) {
            TruffleString className = Strings.substring(context, name, 0, openParen);
            Object javaClass = JavaPackage.lookupClass(realm, thisObj, className);
            if (javaClass != null) {
                return javaClass;
            }
            throw Errors.createTypeErrorFormat("No such Java class: %s", JavaPackage.prependPackageName(thisObj, className));
        }
        return JavaPackage.getJavaClassOrSubPackage(context, realm, thisObj, name);
    }

    private static Object getJavaClassOrSubPackage(JSContext context, JSRealm realm, JSDynamicObject thisObj, TruffleString name) {
        Object javaClass = JavaPackage.lookupClass(realm, thisObj, name);
        if (javaClass != null) {
            return javaClass;
        }
        return JavaPackage.subpackage(context, realm, thisObj, name);
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString prependPackageName(JSDynamicObject thisObj, TruffleString className) {
        TruffleString packageName = JavaPackage.getPackageName(thisObj);
        return !Strings.isEmpty(packageName) ? Strings.concatAll(packageName, Strings.DOT, className) : className;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return this.getClassName(object);
    }

    @CompilerDirectives.TruffleBoundary
    public static Object toPrimitiveString(JSDynamicObject obj) {
        return Strings.concatAll(Strings.BRACKET_OPEN, CLASS_NAME, Strings.SPACE, JavaPackage.getPackageName(obj), Strings.BRACKET_CLOSE);
    }

    public static JSFunctionObject createToPrimitiveFunction(JSContext context, JSRealm realm) {
        JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.JavaPackageToPrimitive, JavaPackage::createToPrimitiveFunctionImpl);
        return JSFunction.create(realm, functionData);
    }

    private static JSFunctionData createToPrimitiveFunctionImpl(JSContext context) {
        RootCallTarget callTarget = new JavaScriptRootNode(context.getLanguage(), null, null){

            @Override
            public Object execute(VirtualFrame frame) {
                Object hint;
                Object[] arguments = frame.getArguments();
                Object obj = JSArguments.getThisObject(arguments);
                Object object = hint = JSArguments.getUserArgumentCount(arguments) > 0 ? JSArguments.getUserArgument(arguments, 0) : null;
                if (!JSRuntime.isObject(obj)) {
                    throw Errors.createTypeError("cannot call JavaPackage[@@toPrimitive] with non-object argument");
                }
                if (Strings.HINT_STRING.equals(hint)) {
                    return JavaPackage.toPrimitiveString((JSDynamicObject)obj);
                }
                if (Strings.HINT_DEFAULT.equals(hint) || Strings.HINT_NUMBER.equals(hint)) {
                    return JSObject.ordinaryToPrimitive((JSDynamicObject)obj, JSToPrimitiveNode.Hint.Number);
                }
                throw Errors.createTypeError("invalid hint");
            }
        }.getCallTarget();
        return JSFunctionData.createCallOnly(context, callTarget, 1, SYMBOL_TO_PRIMITIVE_NAME);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getHelper(JSDynamicObject store, Object thisObj, Object name, Node encapsulatingNode) {
        Object propertyValue = super.getHelper(store, thisObj, name, encapsulatingNode);
        if (propertyValue != null) {
            return propertyValue;
        }
        if (Strings.isTString(name)) {
            return JavaPackage.getJavaClassOrConstructorOrSubPackage(JSObject.getJSContext(store), store, (TruffleString)name);
        }
        return null;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject objectPrototype) {
        return JSObjectUtil.getProtoChildShape(objectPrototype, INSTANCE, context);
    }
}

