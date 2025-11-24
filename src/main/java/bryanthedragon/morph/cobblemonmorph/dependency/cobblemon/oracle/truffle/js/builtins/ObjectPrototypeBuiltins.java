
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.ObjectPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.EnumSet;

public final class ObjectPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<ObjectPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new ObjectPrototypeBuiltins();

    protected ObjectPrototypeBuiltins() {
        super(JSOrdinary.PROTOTYPE_NAME, ObjectPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ObjectPrototype builtinEnum) {
        switch (builtinEnum) {
            case hasOwnProperty: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypeHasOwnPropertyNodeGen.create(context, builtin, ObjectPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case isPrototypeOf: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypeIsPrototypeOfNodeGen.create(context, builtin, ObjectPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case propertyIsEnumerable: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypePropertyIsEnumerableNodeGen.create(context, builtin, ObjectPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypeToLocaleStringNodeGen.create(context, builtin, ObjectPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypeToStringNodeGen.create(context, builtin, ObjectPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case valueOf: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypeValueOfNodeGen.create(context, builtin, ObjectPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case __defineGetter__: 
            case __defineSetter__: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypeDefineGetterOrSetterNodeGen.create(context, builtin, builtinEnum == ObjectPrototype.__defineGetter__, ObjectPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case __lookupGetter__: 
            case __lookupSetter__: {
                return ObjectPrototypeBuiltinsFactory.ObjectPrototypeLookupGetterOrSetterNodeGen.create(context, builtin, builtinEnum == ObjectPrototype.__lookupGetter__, ObjectPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class ObjectPrototypeLookupGetterOrSetterNode
    extends ObjectOperation {
        private final boolean getter;
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
        @Node.Child
        private JSGetOwnPropertyNode getOwnPropertyNode = JSGetOwnPropertyNode.create();

        public ObjectPrototypeLookupGetterOrSetterNode(JSContext context, JSBuiltin builtin, boolean getter) {
            super(context, builtin);
            this.getter = getter;
        }

        @Specialization
        protected Object lookup(Object thisObj, Object prop) {
            JSDynamicObject object = this.toJSObject(thisObj);
            Object key = this.toPropertyKeyNode.execute(prop);
            JSDynamicObject current = object;
            do {
                PropertyDescriptor desc;
                if ((desc = this.getOwnPropertyNode.execute(current, key)) == null) continue;
                if (desc.isAccessorDescriptor()) {
                    return this.getter ? desc.getGet() : desc.getSet();
                }
                return Undefined.instance;
            } while ((current = JSObject.getPrototype(current)) != Null.instance);
            return Undefined.instance;
        }
    }

    public static abstract class ObjectPrototypeDefineGetterOrSetterNode
    extends ObjectOperation {
        private final boolean getter;
        @Node.Child
        private IsCallableNode isCallableNode = IsCallableNode.create();
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

        public ObjectPrototypeDefineGetterOrSetterNode(JSContext context, JSBuiltin builtin, boolean getter) {
            super(context, builtin);
            this.getter = getter;
        }

        @Specialization
        protected Object define(Object thisObj, Object prop, Object getterOrSetter) {
            JSDynamicObject object = this.toJSObject(thisObj);
            if (!this.isCallableNode.executeBoolean(getterOrSetter)) {
                throw this.createTypeErrorExpectingFunction();
            }
            Object key = this.toPropertyKeyNode.execute(prop);
            PropertyDescriptor desc = PropertyDescriptor.createEmpty();
            if (this.getter) {
                desc.setGet(getterOrSetter);
            } else {
                desc.setSet(getterOrSetter);
            }
            desc.setEnumerable(true);
            desc.setConfigurable(true);
            JSRuntime.definePropertyOrThrow(object, key, desc);
            return Undefined.instance;
        }

        @CompilerDirectives.TruffleBoundary
        private JSException createTypeErrorExpectingFunction() {
            return Errors.createTypeErrorFormat("%s: Expecting function", this.getBuiltin().getFullName());
        }
    }

    public static abstract class ObjectPrototypeIsPrototypeOfNode
    extends ObjectOperation {
        private final ConditionProfile argIsNull = ConditionProfile.createBinaryProfile();
        private final ConditionProfile firstPrototypeFits = ConditionProfile.createBinaryProfile();

        public ObjectPrototypeIsPrototypeOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(arg)"})
        protected boolean isPrototypeOf(Object thisObj, JSDynamicObject arg) {
            JSDynamicObject object = this.toJSObject(thisObj);
            if (this.argIsNull.profile(arg == null)) {
                return false;
            }
            JSDynamicObject pobj = JSObject.getPrototype(arg);
            if (this.firstPrototypeFits.profile(pobj == object)) {
                return true;
            }
            int counter = 0;
            do {
                if (++counter > this.getContext().getContextOptions().getMaxPrototypeChainLength()) {
                    throw Errors.createRangeError("prototype chain length exceeded");
                }
                if ((pobj = JSObject.getPrototype(pobj)) != object) continue;
                return true;
            } while (pobj != Null.instance);
            return false;
        }

        @Specialization(guards={"!isJSObject(arg)"})
        protected boolean isPrototypeOfNoObject(Object thisObj, Object arg) {
            return false;
        }
    }

    public static abstract class ObjectPrototypeHasOwnPropertyNode
    extends ObjectOperation {
        @Node.Child
        private JSHasPropertyNode hasOwnPropertyNode;
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode;

        public ObjectPrototypeHasOwnPropertyNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected boolean doJSObjectTStringKey(JSDynamicObject thisObj, TruffleString propertyName) {
            return this.getHasOwnPropertyNode().executeBoolean((Object)thisObj, propertyName);
        }

        @Specialization(guards={"isJSObject(thisObj)"})
        protected boolean doJSObjectIntKey(JSDynamicObject thisObj, int index) {
            return this.getHasOwnPropertyNode().executeBoolean((Object)thisObj, index);
        }

        @Specialization(guards={"isJSObject(thisObj)"}, replaces={"doJSObjectTStringKey", "doJSObjectIntKey"})
        protected boolean doJSObjectAnyKey(JSDynamicObject thisObj, Object propName) {
            Object key = this.getToPropertyKeyNode().execute(propName);
            return this.getHasOwnPropertyNode().executeBoolean((Object)thisObj, key);
        }

        @Specialization(guards={"isNullOrUndefined(thisObj)"})
        protected boolean hasOwnPropertyNullOrUndefined(JSDynamicObject thisObj, Object propName) {
            this.getToPropertyKeyNode().execute(propName);
            throw Errors.createTypeErrorNotObjectCoercible(thisObj, null, this.getContext());
        }

        @Specialization
        protected boolean hasOwnPropertyTString(TruffleString thisObj, Object propName) {
            return this.hasOwnPropertyPrimitive(thisObj, propName);
        }

        @Specialization(guards={"!isTruffleObject(thisObj)"})
        protected boolean hasOwnPropertyPrimitive(Object thisObj, Object propName) {
            Object key = this.getToPropertyKeyNode().execute(propName);
            JSDynamicObject obj = this.toJSObject(thisObj);
            return this.getHasOwnPropertyNode().executeBoolean((Object)obj, key);
        }

        @Specialization
        protected boolean hasOwnPropertySymbol(Symbol thisObj, Object propName) {
            return this.hasOwnPropertyPrimitive(thisObj, propName);
        }

        @Specialization
        protected boolean hasOwnPropertySafeInteger(SafeInteger thisObj, Object propName) {
            return this.hasOwnPropertyPrimitive(thisObj, propName);
        }

        @Specialization
        protected boolean hasOwnPropertyBigInt(BigInt thisObj, Object propName) {
            return this.hasOwnPropertyPrimitive(thisObj, propName);
        }

        @Specialization(guards={"isForeignObject(thisObj)"})
        protected boolean hasOwnPropertyForeign(Object thisObj, Object propName) {
            return this.getHasOwnPropertyNode().executeBoolean(thisObj, propName);
        }

        public JSHasPropertyNode getHasOwnPropertyNode() {
            if (this.hasOwnPropertyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.hasOwnPropertyNode = this.insert(JSHasPropertyNode.create(true));
            }
            return this.hasOwnPropertyNode;
        }

        protected JSToPropertyKeyNode getToPropertyKeyNode() {
            if (this.toPropertyKeyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
            }
            return this.toPropertyKeyNode;
        }
    }

    public static abstract class ObjectPrototypePropertyIsEnumerableNode
    extends ObjectOperation {
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
        @Node.Child
        private JSGetOwnPropertyNode getOwnPropertyNode = JSGetOwnPropertyNode.create();
        private final ConditionProfile descNull = ConditionProfile.createBinaryProfile();

        public ObjectPrototypePropertyIsEnumerableNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean propertyIsEnumerable(Object obj, Object key) {
            Object propertyKey = this.toPropertyKeyNode.execute(key);
            JSDynamicObject thisJSObj = this.toJSObject(obj);
            PropertyDescriptor desc = this.getOwnPropertyNode.execute(thisJSObj, propertyKey);
            if (this.descNull.profile(desc == null)) {
                return false;
            }
            return desc.getEnumerable();
        }
    }

    public static abstract class ObjectPrototypeToLocaleStringNode
    extends ObjectOperation {
        @Node.Child
        private PropertyGetNode getToString;
        @Node.Child
        private JSFunctionCallNode callNode;

        public ObjectPrototypeToLocaleStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getToString = PropertyGetNode.create(Strings.TO_STRING, false, context);
            this.callNode = JSFunctionCallNode.createCall();
        }

        @Specialization
        protected Object toLocaleString(Object obj) {
            Object objConv = obj;
            if (this.getContext().getEcmaScriptVersion() < 6 || this.getContext().isOptionV8CompatibilityMode()) {
                objConv = this.toJSObject(obj);
            }
            Object toStringFn = this.getToString.getValue(objConv);
            return this.callNode.executeCall(JSArguments.createZeroArg(obj, toStringFn));
        }
    }

    public static abstract class FormatCacheNode
    extends JavaScriptBaseNode {
        public abstract TruffleString execute(TruffleString var1);

        public static FormatCacheNode create() {
            return ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.create();
        }

        @Specialization(guards={"stringEquals(equalsNode, cachedName, name)"}, limit="10")
        protected TruffleString executeCached(TruffleString name, @Cached(value="name") TruffleString cachedName, @Cached(value="executeUncached(name)") TruffleString cachedResult, @Cached TruffleString.EqualNode equalsNode) {
            return cachedResult;
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected TruffleString executeUncached(TruffleString name) {
            return Strings.concatAll(Strings.BRACKET_OBJECT_SPC, name, Strings.BRACKET_CLOSE);
        }
    }

    @ImportStatic(value={JSObject.class})
    public static abstract class GetBuiltinToStringTagNode
    extends JavaScriptBaseNode {
        public abstract TruffleString execute(JSObject var1);

        public static GetBuiltinToStringTagNode create() {
            return ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.create();
        }

        @Specialization(guards={"cachedClass != null", "cachedClass.isInstance(object)"}, limit="5")
        protected static TruffleString cached(JSObject object, @Cached(value="getJSClass(object)") JSClass cachedClass) {
            return cachedClass.getBuiltinToStringTag(object);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(replaces={"cached"})
        protected static TruffleString uncached(JSObject object) {
            return JSObject.getJSClass(object).getBuiltinToStringTag(object);
        }
    }

    public static abstract class ObjectPrototypeToStringNode
    extends ObjectOperation {
        @Node.Child
        private PropertyGetNode getStringTagNode;
        @Node.Child
        private FormatCacheNode formatCacheNode;

        public ObjectPrototypeToStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getStringTagNode = PropertyGetNode.create(Symbol.SYMBOL_TO_STRING_TAG, false, context);
        }

        private TruffleString formatString(TruffleString name) {
            if (this.formatCacheNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.formatCacheNode = this.insert(FormatCacheNode.create());
            }
            return this.formatCacheNode.execute(name);
        }

        private TruffleString getToStringTag(JSObject thisObj) {
            Object toStringTag;
            if (this.getContext().getEcmaScriptVersion() >= 6 && Strings.isTString(toStringTag = this.getStringTagNode.getValue(thisObj))) {
                return JSRuntime.toStringIsString(toStringTag);
            }
            return null;
        }

        @Specialization(guards={"!isJSProxy(thisObj)"})
        protected TruffleString doJSObject(JSObject thisObj, @Cached.Shared(value="builtinTag") @Cached GetBuiltinToStringTagNode getBuiltinToStringTagNode) {
            TruffleString toString = this.getToStringTag(thisObj);
            if (toString == null) {
                toString = this.getContext().getEcmaScriptVersion() >= 6 ? getBuiltinToStringTagNode.execute(thisObj) : JSObject.getClassName(thisObj);
            }
            return this.formatString(toString);
        }

        @Specialization
        protected TruffleString doJSProxy(JSProxyObject thisObj, @Cached.Shared(value="builtinTag") @Cached(value="create()") GetBuiltinToStringTagNode getBuiltinToStringTagNode) {
            TruffleString builtinTag = getBuiltinToStringTagNode.execute(thisObj);
            TruffleString tag = this.getToStringTag(thisObj);
            if (tag == null) {
                tag = builtinTag;
            }
            return this.formatString(tag);
        }

        @Specialization(guards={"isJSNull(thisObj)"})
        protected TruffleString doNull(Object thisObj) {
            return Strings.TO_STRING_VALUE_NULL;
        }

        @Specialization(guards={"isUndefined(thisObj)"})
        protected TruffleString doUndefined(Object thisObj) {
            return Strings.TO_STRING_VALUE_UNDEFINED;
        }

        @Specialization(guards={"isForeignObject(thisObj)"}, limit="1")
        @CompilerDirectives.TruffleBoundary
        protected TruffleString doForeignObject(Object thisObj, @CachedLibrary(value="thisObj") InteropLibrary interop) {
            if (interop.isNull(thisObj)) {
                return Strings.TO_STRING_VALUE_NULL;
            }
            if (interop.hasArrayElements(thisObj)) {
                return Strings.TO_STRING_VALUE_ARRAY;
            }
            if (interop.isExecutable(thisObj) || interop.isInstantiable(thisObj)) {
                return Strings.TO_STRING_VALUE_FUNCTION;
            }
            if (interop.isInstant(thisObj)) {
                return Strings.TO_STRING_VALUE_DATE;
            }
            return Strings.TO_STRING_VALUE_OBJECT;
        }

        @Specialization
        protected TruffleString doSymbol(Symbol thisObj) {
            assert (thisObj != null);
            return JSObject.defaultToString(this.toJSObject(thisObj));
        }

        @Specialization
        protected TruffleString doString(TruffleString thisObj) {
            return JSObject.defaultToString(this.toJSObject(thisObj));
        }

        @Specialization
        protected TruffleString doSafeInteger(SafeInteger thisObj) {
            return JSObject.defaultToString(this.toJSObject(thisObj));
        }

        @Specialization
        protected TruffleString doBigInt(BigInt thisObj) {
            return JSObject.defaultToString(this.toJSObject(thisObj));
        }

        @Specialization(guards={"!isTruffleObject(thisObj)"})
        protected TruffleString doObject(Object thisObj) {
            assert (thisObj != null);
            return JSObject.defaultToString(this.toJSObject(thisObj));
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class ObjectPrototypeValueOfNode
    extends ObjectOperation {
        public ObjectPrototypeValueOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSDynamicObject(thisObj)"})
        protected JSDynamicObject valueOfJSObject(JSDynamicObject thisObj) {
            return this.toJSObject(thisObj);
        }

        @Specialization
        protected JSDynamicObject valueOfSymbol(Symbol thisObj) {
            return this.toJSObject(thisObj);
        }

        @Specialization
        protected JSDynamicObject valueOfLazyString(TruffleString thisObj) {
            return this.toJSObject(thisObj);
        }

        @Specialization
        protected JSDynamicObject valueOfSafeInteger(SafeInteger thisObj) {
            return this.toJSObject(thisObj);
        }

        @Specialization
        protected JSDynamicObject valueOfBigInt(BigInt thisObj) {
            return this.toJSObject(thisObj);
        }

        @Specialization(guards={"!isTruffleObject(thisObj)"})
        protected JSDynamicObject valueOfOther(Object thisObj) {
            return this.toJSObject(thisObj);
        }

        @Specialization(guards={"isForeignObject(thisObj)"})
        protected Object valueOfForeign(Object thisObj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            if (interop.isNull(thisObj)) {
                throw Errors.createTypeErrorNotObjectCoercible(thisObj, null, this.getContext());
            }
            return thisObj;
        }
    }

    public static abstract class ObjectOperation
    extends JSBuiltinNode {
        @Node.Child
        private JSToObjectNode toObjectNode;
        private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();
        private final BranchProfile notAJSObjectBranch = BranchProfile.create();

        public ObjectOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected final JSDynamicObject toJSObject(Object target) {
            return JSRuntime.expectJSObject(this.toObject(target), this.notAJSObjectBranch);
        }

        protected final Object toObject(Object target) {
            if (this.toObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toObjectNode = this.insert(JSToObjectNode.createToObject(this.getContext()));
            }
            return this.toObjectNode.execute(target);
        }

        protected final JSDynamicObject asJSObject(Object object) {
            if (this.isObject.profile(JSRuntime.isObject(object))) {
                return (JSDynamicObject)object;
            }
            throw this.createTypeErrorCalledOnNonObject(object);
        }

        protected final JSDynamicObject toOrAsJSObject(Object thisObj) {
            if (this.getContext().getEcmaScriptVersion() >= 6) {
                return this.toJSObject(thisObj);
            }
            return this.asJSObject(thisObj);
        }

        @CompilerDirectives.TruffleBoundary
        protected final JSException createTypeErrorCalledOnNonObject(Object value2) {
            assert (!JSRuntime.isObject(value2));
            return Errors.createTypeErrorFormat("Object.%s called on non-object", this.getBuiltin().getName());
        }
    }

    public static enum ObjectPrototype implements BuiltinEnum<ObjectPrototype>
    {
        hasOwnProperty(1),
        isPrototypeOf(1),
        propertyIsEnumerable(1),
        toLocaleString(0),
        toString(0),
        valueOf(0),
        __defineGetter__(2),
        __defineSetter__(2),
        __lookupGetter__(1),
        __lookupSetter__(1);

        private final int length;

        private ObjectPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isAnnexB() {
            return EnumSet.of(__defineGetter__, __defineSetter__, __lookupGetter__, __lookupSetter__).contains(this);
        }
    }
}

