
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNodeGen;
import com.oracle.truffle.js.nodes.cast.OrdinaryToPrimitiveNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ImportStatic(value={JSConfig.class})
public abstract class JSToPrimitiveNode
extends JavaScriptBaseNode {
    @Node.Child
    private OrdinaryToPrimitiveNode ordinaryToPrimitiveNode;
    protected final Hint hint;

    protected JSToPrimitiveNode(Hint hint) {
        this.hint = hint;
    }

    public abstract Object execute(Object var1);

    public static JSToPrimitiveNode createHintDefault() {
        return JSToPrimitiveNode.create(Hint.Default);
    }

    public static JSToPrimitiveNode createHintString() {
        return JSToPrimitiveNode.create(Hint.String);
    }

    public static JSToPrimitiveNode createHintNumber() {
        return JSToPrimitiveNode.create(Hint.Number);
    }

    public static JSToPrimitiveNode create(Hint hint) {
        return JSToPrimitiveNodeGen.create(hint);
    }

    @Specialization
    protected int doInt(int value2) {
        return value2;
    }

    @Specialization
    protected SafeInteger doSafeInteger(SafeInteger value2) {
        return value2;
    }

    @Specialization
    protected long doLong(long value2) {
        return value2;
    }

    @Specialization
    protected double doDouble(double value2) {
        return value2;
    }

    @Specialization
    protected boolean doBoolean(boolean value2) {
        return value2;
    }

    @Specialization
    protected Object doString(TruffleString value2) {
        return value2;
    }

    @Specialization
    protected Symbol doSymbol(Symbol value2) {
        return value2;
    }

    @Specialization
    protected BigInt doBigInt(BigInt value2) {
        return value2;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected JSDynamicObject doNull(Object value2) {
        return Null.instance;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected JSDynamicObject doUndefined(Object value2) {
        return Undefined.instance;
    }

    @Specialization
    protected Object doJSObject(JSObject object, @Cached(value="createGetToPrimitive()") PropertyGetNode getToPrimitive, @Cached IsPrimitiveNode isPrimitive, @Cached ConditionProfile exoticToPrimProfile, @Cached(value="createCall()") JSFunctionCallNode callExoticToPrim) {
        Object exoticToPrim = getToPrimitive.getValue(object);
        if (exoticToPrimProfile.profile(!JSRuntime.isNullOrUndefined(exoticToPrim))) {
            Object result = callExoticToPrim.executeCall(JSArguments.createOneArg(object, exoticToPrim, this.hint.getHintName()));
            if (isPrimitive.executeBoolean(result)) {
                return result;
            }
            throw Errors.createTypeError("[Symbol.toPrimitive] method returned a non-primitive object", (Node)this);
        }
        return this.ordinaryToPrimitive(object);
    }

    protected final boolean isHintString() {
        return this.hint == Hint.String;
    }

    @Specialization(guards={"isForeignObject(object)"}, limit="InteropLibraryLimit")
    protected Object doForeignObject(Object object, @CachedLibrary(value="object") InteropLibrary interop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary resultInterop) {
        Object maybeResult;
        if (interop.isNull(object)) {
            return Null.instance;
        }
        try {
            if (interop.isBoolean(object)) {
                return interop.asBoolean(object);
            }
            if (interop.isString(object)) {
                return interop.asTruffleString(object);
            }
            if (interop.isNumber(object)) {
                if (interop.fitsInInt(object)) {
                    return interop.asInt(object);
                }
                if (interop.fitsInLong(object)) {
                    return interop.asLong(object);
                }
                if (interop.fitsInDouble(object)) {
                    return interop.asDouble(object);
                }
            }
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorUnboxException(object, e, this);
        }
        JSRealm realm = this.getRealm();
        TruffleLanguage.Env env = realm.getEnv();
        if (env.isHostObject(object) && (maybeResult = JSToPrimitiveNode.tryHostObjectToPrimitive(object, this.hint, interop)) != null) {
            return maybeResult;
        }
        Object result = this.ordinaryToPrimitive(object);
        assert (IsPrimitiveNode.getUncached().executeBoolean(result)) : result;
        return JSInteropUtil.toPrimitiveOrDefault(result, result, resultInterop, this);
    }

    public static Object tryHostObjectToPrimitive(Object object, Hint hint, InteropLibrary interop) {
        if (hint != Hint.String && JavaScriptLanguage.get(interop).getJSContext().isOptionNashornCompatibilityMode() && interop.isMemberInvocable(object, "doubleValue")) {
            try {
                return interop.invokeMember(object, "doubleValue", new Object[0]);
            }
            catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(object, e, "doubleValue()", interop);
            }
        }
        if (interop.isMetaObject(object)) {
            return JSToPrimitiveNode.javaClassToString(object, interop);
        }
        if (interop.isException(object)) {
            return JSToPrimitiveNode.javaExceptionToString(object, interop);
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString javaClassToString(Object object, InteropLibrary interop) {
        try {
            String qualifiedName = InteropLibrary.getUncached().asString(interop.getMetaQualifiedName(object));
            if (JavaScriptLanguage.get(interop).getJSContext().isOptionNashornCompatibilityMode() && qualifiedName.endsWith("[]")) {
                Object hostObject = JSRealm.get(interop).getEnv().asHostObject(object);
                qualifiedName = ((Class)hostObject).getName();
            }
            return Strings.fromJavaString("class " + qualifiedName);
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(object, e, "getTypeName", interop);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString javaExceptionToString(Object object, InteropLibrary interop) {
        try {
            return InteropLibrary.getUncached().asTruffleString(interop.toDisplayString(object, true));
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(object, e, "toString", interop);
        }
    }

    @Fallback
    protected TruffleString doFallback(Object value2) {
        assert (value2 != null);
        throw Errors.createTypeErrorCannotConvertToPrimitiveValue(this);
    }

    private Object ordinaryToPrimitive(Object object) {
        if (this.ordinaryToPrimitiveNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.ordinaryToPrimitiveNode = this.insert(this.createOrdinaryToPrimitive());
        }
        return this.ordinaryToPrimitiveNode.execute(object);
    }

    protected PropertyGetNode createGetToPrimitive() {
        return PropertyGetNode.createGetMethod(Symbol.SYMBOL_TO_PRIMITIVE, this.getLanguage().getJSContext());
    }

    protected OrdinaryToPrimitiveNode createOrdinaryToPrimitive() {
        return OrdinaryToPrimitiveNode.create(this.isHintString() ? Hint.String : Hint.Number);
    }

    public static enum Hint {
        Default(Strings.HINT_DEFAULT),
        Number(Strings.HINT_NUMBER),
        String(Strings.HINT_STRING);

        private final TruffleString hintName;

        private Hint(TruffleString hintName) {
            this.hintName = hintName;
        }

        public TruffleString getHintName() {
            return this.hintName;
        }
    }
}

