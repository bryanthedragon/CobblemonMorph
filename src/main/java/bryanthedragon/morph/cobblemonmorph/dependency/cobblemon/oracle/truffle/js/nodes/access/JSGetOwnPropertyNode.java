
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNodeGen;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

@ImportStatic(value={JSRuntime.class})
public abstract class JSGetOwnPropertyNode
extends JavaScriptBaseNode {
    private final boolean needValue;
    private final boolean needEnumerability;
    private final boolean needConfigurability;
    private final boolean needWritability;
    final boolean allowCaching;
    @CompilerDirectives.CompilationFinal
    private boolean seenNonArrayIndex;
    private final ConditionProfile hasPropertyBranch = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isDataPropertyBranch = ConditionProfile.createBinaryProfile();
    private final ConditionProfile isAccessorPropertyBranch = ConditionProfile.createBinaryProfile();
    @Node.Child
    private GetPropertyProxyValueNode getPropertyProxyValueNode;

    protected JSGetOwnPropertyNode(boolean needValue, boolean needEnumerability, boolean needConfigurability, boolean needWritability, boolean allowCaching) {
        this.needValue = needValue;
        this.needEnumerability = needEnumerability;
        this.needConfigurability = needConfigurability;
        this.needWritability = needWritability;
        this.allowCaching = allowCaching;
    }

    public static JSGetOwnPropertyNode create() {
        return JSGetOwnPropertyNode.create(true);
    }

    public static JSGetOwnPropertyNode create(boolean needValue) {
        return JSGetOwnPropertyNode.create(needValue, true, true, true, true);
    }

    public static JSGetOwnPropertyNode create(boolean needValue, boolean needEnumerability, boolean needConfigurability, boolean needWritability, boolean allowCaching) {
        return JSGetOwnPropertyNodeGen.create(needValue, needEnumerability, needConfigurability, needWritability, allowCaching);
    }

    public abstract PropertyDescriptor execute(JSDynamicObject var1, Object var2);

    @Specialization(guards={"isJSArray(thisObj)"})
    PropertyDescriptor array(JSDynamicObject thisObj, Object propertyKey, @Cached ToArrayIndexNode toArrayIndexNode, @Cached BranchProfile noSuchElementBranch, @Cached(value="createIdentityProfile()") ValueProfile typeProfile) {
        ScriptArray array;
        assert (JSRuntime.isPropertyKey(propertyKey));
        long idx = this.toArrayIndex(propertyKey, toArrayIndexNode);
        if (JSRuntime.isArrayIndex(idx) && (array = typeProfile.profile(JSAbstractArray.arrayGetArrayType(thisObj))).hasElement(thisObj, idx)) {
            PropertyDescriptor desc = PropertyDescriptor.createEmpty();
            if (this.needEnumerability) {
                desc.setEnumerable(true);
            }
            if (this.needConfigurability) {
                desc.setConfigurable(!array.isSealed());
            }
            if (this.needWritability) {
                desc.setWritable(!array.isFrozen());
            }
            if (this.needValue) {
                desc.setValue(array.getElement(thisObj, idx));
            }
            return desc;
        }
        noSuchElementBranch.enter();
        Property prop = thisObj.getShape().getProperty(propertyKey);
        return this.ordinaryGetOwnProperty(thisObj, prop);
    }

    @Specialization(guards={"isJSString(thisObj)"})
    protected PropertyDescriptor getOwnPropertyString(JSDynamicObject thisObj, Object key, @Cached(value="createBinaryProfile()") ConditionProfile stringCaseProfile) {
        assert (JSRuntime.isPropertyKey(key));
        Property prop = thisObj.getShape().getProperty(key);
        PropertyDescriptor desc = this.ordinaryGetOwnProperty(thisObj, prop);
        if (stringCaseProfile.profile(desc == null)) {
            return JSString.stringGetIndexProperty(thisObj, key);
        }
        return desc;
    }

    @Specialization(guards={"allowCaching", "cachedJSClass != null", "propertyKeyEquals(equalsNode, cachedPropertyKey, propertyKey)", "cachedShape == thisObj.getShape()"}, assumptions={"cachedShape.getValidAssumption()"}, limit="3")
    PropertyDescriptor cachedOrdinary(JSDynamicObject thisObj, Object propertyKey, @Cached(value="getJSClassIfOrdinary(thisObj)") JSClass cachedJSClass, @Cached(value="thisObj.getShape()") Shape cachedShape, @Cached(value="propertyKey") Object cachedPropertyKey, @Cached(value="cachedShape.getProperty(propertyKey)") Property cachedProperty, @Cached TruffleString.EqualNode equalsNode) {
        assert (JSRuntime.isPropertyKey(propertyKey) && JSObject.getJSClass(thisObj).usesOrdinaryGetOwnProperty());
        return this.ordinaryGetOwnProperty(thisObj, cachedProperty);
    }

    @Specialization(guards={"usesOrdinaryGetOwnProperty.execute(thisObj)"}, replaces={"cachedOrdinary"}, limit="1")
    PropertyDescriptor uncachedOrdinary(JSDynamicObject thisObj, Object propertyKey, @Cached @Cached.Shared(value="usesOrdinaryGetOwnProperty") UsesOrdinaryGetOwnPropertyNode usesOrdinaryGetOwnProperty) {
        assert (JSRuntime.isPropertyKey(propertyKey) && JSObject.getJSClass(thisObj).usesOrdinaryGetOwnProperty());
        Property prop = thisObj.getShape().getProperty(propertyKey);
        return this.ordinaryGetOwnProperty(thisObj, prop);
    }

    static JSClass getJSClassIfOrdinary(JSDynamicObject obj) {
        JSClass jsclass = JSObject.getJSClass(obj);
        if (jsclass.usesOrdinaryGetOwnProperty()) {
            return jsclass;
        }
        return null;
    }

    private PropertyDescriptor ordinaryGetOwnProperty(JSDynamicObject thisObj, Property prop) {
        assert (!JSProxy.isJSProxy(thisObj));
        if (this.hasPropertyBranch.profile(prop == null)) {
            return null;
        }
        PropertyDescriptor d = PropertyDescriptor.createEmpty();
        if (this.isDataPropertyBranch.profile(JSProperty.isData(prop))) {
            if (this.needWritability) {
                d.setWritable(JSProperty.isWritable(prop));
            }
            if (this.needValue) {
                d.setValue(this.getDataPropertyValue(thisObj, prop));
            }
        } else if (this.isAccessorPropertyBranch.profile(JSProperty.isAccessor(prop)) && this.needValue) {
            d.setAccessor((Accessor)prop.getLocation().get(thisObj));
        }
        if (this.needEnumerability) {
            d.setEnumerable(JSProperty.isEnumerable(prop));
        }
        if (this.needConfigurability) {
            d.setConfigurable(JSProperty.isConfigurable(prop));
        }
        return d;
    }

    private Object getDataPropertyValue(JSDynamicObject thisObj, Property prop) {
        assert (JSProperty.isData(prop));
        Object value2 = prop.getLocation().get(thisObj);
        if (JSProperty.isProxy(prop)) {
            return this.getPropertyProxyValue(thisObj, value2);
        }
        return value2;
    }

    private Object getPropertyProxyValue(JSDynamicObject obj, Object propertyProxy) {
        if (this.getPropertyProxyValueNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getPropertyProxyValueNode = this.insert(JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.create());
        }
        return this.getPropertyProxyValueNode.execute(obj, propertyProxy);
    }

    @Specialization(guards={"!usesOrdinaryGetOwnProperty.execute(thisObj)", "!isJSArray(thisObj)", "!isJSString(thisObj)"}, limit="1")
    static PropertyDescriptor generic(JSDynamicObject thisObj, Object key, @Cached(value="create()") JSClassProfile jsclassProfile, @Cached @Cached.Shared(value="usesOrdinaryGetOwnProperty") UsesOrdinaryGetOwnPropertyNode usesOrdinaryGetOwnProperty) {
        assert (!JSObject.getJSClass(thisObj).usesOrdinaryGetOwnProperty());
        return JSObject.getOwnProperty(thisObj, key, jsclassProfile);
    }

    private long toArrayIndex(Object propertyKey, ToArrayIndexNode toArrayIndexNode) {
        if (this.seenNonArrayIndex) {
            Object result = toArrayIndexNode.execute(propertyKey);
            return result instanceof Long ? (Long)result : -1L;
        }
        try {
            return toArrayIndexNode.executeLong(propertyKey);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.seenNonArrayIndex = true;
            return -1L;
        }
    }

    public static abstract class GetPropertyProxyValueNode
    extends JavaScriptBaseNode {
        protected GetPropertyProxyValueNode() {
        }

        public abstract Object execute(JSDynamicObject var1, Object var2);

        @Specialization(guards={"propertyProxy.getClass() == cachedClass"}, limit="5")
        static Object doCached(JSDynamicObject obj, Object propertyProxy, @Cached(value="propertyProxy.getClass()") Class<?> cachedClass) {
            return ((PropertyProxy)cachedClass.cast(propertyProxy)).get(obj);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(replaces={"doCached"})
        static Object doUncached(JSDynamicObject obj, Object propertyProxy) {
            return ((PropertyProxy)propertyProxy).get(obj);
        }
    }

    public static abstract class UsesOrdinaryGetOwnPropertyNode
    extends JavaScriptBaseNode {
        protected UsesOrdinaryGetOwnPropertyNode() {
        }

        public static UsesOrdinaryGetOwnPropertyNode create() {
            return JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.create();
        }

        public final boolean execute(JSDynamicObject object) {
            return this.execute(JSShape.getJSClassNoCast(object.getShape()));
        }

        public abstract boolean execute(Object var1);

        @Specialization(guards={"isReferenceEquals(jsclass, cachedJSClass)"}, limit="7")
        static boolean doCached(Object jsclass, @Cached(value="asJSClass(jsclass)") JSClass cachedJSClass) {
            return cachedJSClass.usesOrdinaryGetOwnProperty();
        }

        @Specialization(replaces={"doCached"})
        static boolean doObjectPrototype(Object jsclass) {
            return UsesOrdinaryGetOwnPropertyNode.asJSClass(jsclass).usesOrdinaryGetOwnProperty();
        }

        static JSClass asJSClass(Object jsclass) {
            return (JSClass)jsclass;
        }
    }
}

