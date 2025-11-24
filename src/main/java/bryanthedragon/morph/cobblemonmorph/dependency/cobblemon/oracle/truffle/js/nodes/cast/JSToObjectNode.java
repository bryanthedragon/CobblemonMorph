
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNodeGen;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import java.util.Set;

@ImportStatic(value={CompilerDirectives.class, JSConfig.class})
public abstract class JSToObjectNode
extends JavaScriptBaseNode {
    protected final JSContext context;
    protected final boolean checkForNullOrUndefined;
    protected final boolean fromWith;
    protected final boolean allowForeign;

    protected JSToObjectNode(JSContext context, boolean checkForNullOrUndefined, boolean fromWith, boolean allowForeign) {
        this.context = context;
        this.checkForNullOrUndefined = checkForNullOrUndefined;
        this.fromWith = fromWith;
        this.allowForeign = allowForeign;
    }

    public abstract Object execute(Object var1);

    public static JSToObjectNode createToObject(JSContext context) {
        return JSToObjectNode.createToObject(context, true, false, true);
    }

    public static JSToObjectNode createToObjectNoCheck(JSContext context) {
        return JSToObjectNode.createToObject(context, false, false, true);
    }

    public static JSToObjectNode createToObjectNoCheckNoForeign(JSContext context) {
        return JSToObjectNode.createToObject(context, false, false, false);
    }

    protected static JSToObjectNode createToObject(JSContext context, boolean checkForNullOrUndefined, boolean fromWith, boolean allowForeign) {
        return JSToObjectNodeGen.create(context, checkForNullOrUndefined, fromWith, allowForeign);
    }

    protected final JSContext getContext() {
        return this.context;
    }

    protected final boolean isCheckForNullOrUndefined() {
        return this.checkForNullOrUndefined;
    }

    protected final boolean isFromWith() {
        return this.fromWith;
    }

    protected final boolean isAllowForeign() {
        return this.allowForeign;
    }

    @CompilerDirectives.TruffleBoundary
    private JSException createTypeError(Object value2) {
        if (this.isFromWith()) {
            return Errors.createTypeError("Cannot apply \"with\" to " + JSRuntime.safeToString(value2), (Node)this);
        }
        return Errors.createTypeErrorNotObjectCoercible(value2, this, this.context);
    }

    @Specialization
    protected JSDynamicObject doBoolean(boolean value2) {
        return JSBoolean.create(this.context, this.getRealm(), value2);
    }

    @Specialization
    protected JSDynamicObject doString(TruffleString value2) {
        return JSString.create(this.getContext(), this.getRealm(), value2);
    }

    @Specialization
    protected JSDynamicObject doInt(int value2) {
        return JSNumber.create(this.getContext(), this.getRealm(), value2);
    }

    @Specialization
    protected JSDynamicObject doDouble(double value2) {
        return JSNumber.create(this.getContext(), this.getRealm(), value2);
    }

    @Specialization
    protected JSDynamicObject doBigInt(BigInt value2) {
        return JSBigInt.create(this.getContext(), this.getRealm(), value2);
    }

    @Specialization(guards={"isJavaNumber(value)"})
    protected JSDynamicObject doNumber(Object value2) {
        return JSNumber.create(this.getContext(), this.getRealm(), (Number)value2);
    }

    @Specialization
    protected JSDynamicObject doSymbol(Symbol value2) {
        return JSSymbol.create(this.getContext(), this.getRealm(), value2);
    }

    @Specialization(guards={"cachedClass != null", "isExact(object, cachedClass)"}, limit="1")
    protected static Object doJSObjectCached(Object object, @Cached(value="getClassIfObject(object)") Class<?> cachedClass) {
        return cachedClass.cast(object);
    }

    final Class<?> getClassIfObject(Object object) {
        if (this.isCheckForNullOrUndefined() && JSGuards.isJSObject(object)) {
            return object.getClass();
        }
        if (!this.isCheckForNullOrUndefined() && JSGuards.isJSDynamicObject(object)) {
            return object.getClass();
        }
        return null;
    }

    @Specialization(guards={"!isCheckForNullOrUndefined()", "isJSDynamicObject(object)"}, replaces={"doJSObjectCached"})
    protected Object doJSObjectNoCheck(Object object) {
        return object;
    }

    @Specialization(guards={"isCheckForNullOrUndefined()", "isJSObject(object)"}, replaces={"doJSObjectCached"})
    protected Object doJSObjectCheck(Object object) {
        return object;
    }

    @Specialization(guards={"isCheckForNullOrUndefined()", "isNullOrUndefined(object)"})
    protected JSDynamicObject doNullOrUndefined(Object object) {
        throw this.createTypeError(object);
    }

    @Specialization(guards={"isAllowForeign()", "isForeignObject(obj)"}, limit="InteropLibraryLimit")
    protected Object doForeignObjectAllowed(Object obj, @Cached(value="createToObject(context, checkForNullOrUndefined, fromWith, allowForeign)") JSToObjectNode toObjectNode, @CachedLibrary(value="obj") InteropLibrary interop) {
        Object unboxed;
        if (this.isFromWith() && this.context.isOptionNashornCompatibilityMode() && this.getRealm().getEnv().isHostObject(obj)) {
            this.throwWithError();
        }
        if ((unboxed = JSInteropUtil.toPrimitiveOrDefault(obj, null, interop, this)) == null) {
            return obj;
        }
        if (unboxed == Null.instance) {
            throw this.createTypeError(obj);
        }
        return toObjectNode.execute(unboxed);
    }

    @Specialization(guards={"!isAllowForeign()", "isForeignObject(obj)"})
    protected Object doForeignObjectDisallowed(Object obj) {
        throw Errors.createTypeError("Foreign TruffleObject not supported", (Node)this);
    }

    @Specialization(guards={"!isBoolean(object)", "!isNumber(object)", "!isString(object)", "!isSymbol(object)", "!isJSObject(object)", "!isForeignObject(object)"})
    protected Object doJavaGeneric(Object object) {
        assert (!JSRuntime.isJSNative(object));
        if (this.isFromWith()) {
            this.throwWithError();
        }
        return this.getRealm().getEnv().asBoxedGuestValue(object);
    }

    @CompilerDirectives.TruffleBoundary
    private void throwWithError() {
        Object message = "Cannot apply \"with\" to non script object";
        if (this.getContext().isOptionNashornCompatibilityMode()) {
            message = (String)message + ". Consider using \"with(Object.bindProperties({}, nonScriptObject))\".";
        }
        throw Errors.createTypeError((String)message, (Node)this);
    }

    public static abstract class JSToObjectWrapperNode
    extends JSUnaryNode {
        @Node.Child
        private JSToObjectNode toObjectNode;

        protected JSToObjectWrapperNode(JavaScriptNode operand, JSToObjectNode toObjectNode) {
            super(operand);
            this.toObjectNode = toObjectNode;
        }

        public static JSToObjectWrapperNode createToObject(JSContext context, JavaScriptNode child) {
            return JSToObjectNodeGen.JSToObjectWrapperNodeGen.create(child, JSToObjectNode.createToObject(context));
        }

        public static JSToObjectWrapperNode createToObjectFromWith(JSContext context, JavaScriptNode child, boolean checkForNullOrUndefined) {
            return JSToObjectNodeGen.JSToObjectWrapperNodeGen.create(child, JSToObjectNodeGen.create(context, checkForNullOrUndefined, true, true));
        }

        @Specialization
        protected Object doDefault(Object value2) {
            return this.toObjectNode.execute(value2);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            JSToObjectNode clonedToObject = JSToObjectNodeGen.create(this.toObjectNode.getContext(), this.toObjectNode.isCheckForNullOrUndefined(), this.toObjectNode.isFromWith(), this.toObjectNode.isAllowForeign());
            return JSToObjectNodeGen.JSToObjectWrapperNodeGen.create(JSToObjectWrapperNode.cloneUninitialized(this.getOperand(), materializedTags), clonedToObject);
        }
    }
}

