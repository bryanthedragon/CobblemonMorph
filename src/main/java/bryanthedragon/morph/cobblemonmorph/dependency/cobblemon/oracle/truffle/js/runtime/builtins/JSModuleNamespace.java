
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespaceObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class JSModuleNamespace
extends JSNonProxy {
    public static final JSModuleNamespace INSTANCE = new JSModuleNamespace();
    public static final TruffleString CLASS_NAME = Strings.constant("Module");

    private JSModuleNamespace() {
    }

    public static JSModuleRecord getModule(JSDynamicObject obj) {
        assert (JSModuleNamespace.isJSModuleNamespace(obj));
        return ((JSModuleNamespaceObject)obj).getModule();
    }

    public static Map<TruffleString, ExportResolution> getExports(JSDynamicObject obj) {
        assert (JSModuleNamespace.isJSModuleNamespace(obj));
        return ((JSModuleNamespaceObject)obj).getExports();
    }

    public static JSModuleNamespaceObject create(JSContext context, JSRealm realm, JSModuleRecord module, Map<TruffleString, ExportResolution> exports) {
        JSObjectFactory.BoundProto factory = context.getModuleNamespaceFactory();
        JSModuleNamespaceObject obj = JSModuleNamespaceObject.create(realm, factory, module, exports);
        assert (!JSObject.isExtensible(obj));
        return context.trackAllocation(obj);
    }

    public static Shape makeInitialShape(JSContext context) {
        Shape initialShape = JSShape.newBuilder(context, INSTANCE, Null.instance).shapeFlags(1).build();
        initialShape = Shape.newBuilder(initialShape).addConstantProperty(JSObject.HIDDEN_PROTO, Null.instance, 0).addConstantProperty(Symbol.SYMBOL_TO_STRING_TAG, CLASS_NAME, JSAttributes.notConfigurableNotEnumerableNotWritable()).build();
        assert (!JSShape.isExtensible(initialShape));
        return initialShape;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return CLASS_NAME;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        return Strings.addBrackets(CLASS_NAME);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
        if (!Strings.isTString(key)) {
            return super.getOwnHelper(store, thisObj, key, encapsulatingNode);
        }
        Map<TruffleString, ExportResolution> exports = JSModuleNamespace.getExports(store);
        ExportResolution binding = exports.get(key);
        if (binding != null) {
            return JSModuleNamespace.getBindingValue(binding);
        }
        return null;
    }

    static Object getBindingValue(ExportResolution binding) {
        int slot;
        TruffleString bindingName = binding.getBindingName();
        JSModuleRecord targetModule = binding.getModule();
        MaterializedFrame targetEnv = targetModule.getEnvironment();
        if (targetEnv == null) {
            throw Errors.createReferenceErrorNotDefined(bindingName, null);
        }
        if (binding.isNamespace()) {
            return targetModule.getContext().getEvaluator().getModuleNamespace(targetModule);
        }
        FrameDescriptor targetEnvDesc = targetEnv.getFrameDescriptor();
        if (JSFrameUtil.hasTemporalDeadZone(targetEnvDesc, slot = JSFrameUtil.findRequiredFrameSlotIndex(targetEnvDesc, bindingName)) && targetEnv.getTag(slot) == FrameSlotKind.Illegal.tag) {
            throw Errors.createReferenceErrorNotDefined(bindingName, null);
        }
        return targetEnv.getValue(slot);
    }

    @Override
    public boolean hasProperty(JSDynamicObject thisObj, Object key) {
        if (!Strings.isTString(key)) {
            return super.hasProperty(thisObj, key);
        }
        Map<TruffleString, ExportResolution> exports = JSModuleNamespace.getExports(thisObj);
        return Boundaries.mapContainsKey(exports, (TruffleString)key);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
        if (!Strings.isTString(key)) {
            return super.hasOwnProperty(thisObj, key);
        }
        Map<TruffleString, ExportResolution> exports = JSModuleNamespace.getExports(thisObj);
        ExportResolution binding = exports.get(key);
        if (binding != null) {
            JSModuleNamespace.getBindingValue(binding);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
        return true;
    }

    @Override
    public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
        if (!Strings.isTString(key)) {
            return super.delete(thisObj, key, isStrict);
        }
        if (Boundaries.mapContainsKey(JSModuleNamespace.getExports(thisObj), (TruffleString)key)) {
            if (isStrict) {
                throw Errors.createTypeErrorNotConfigurableProperty(key);
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
        return newPrototype == Null.instance;
    }

    @Override
    public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
        if (!Strings.isTString(key)) {
            return super.defineOwnProperty(thisObj, key, desc, doThrow);
        }
        PropertyDescriptor current = this.getOwnProperty(thisObj, key);
        if (current != null && !desc.isAccessorDescriptor() && desc.getIfHasWritable(true) && desc.getIfHasEnumerable(true) && !desc.getIfHasConfigurable(false) && (!desc.hasValue() || JSRuntime.isSameValue(desc.getValue(), current.getValue()))) {
            return true;
        }
        return DefinePropertyUtil.reject(doThrow, "not allowed to defineProperty on a namespace object");
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
        if (!Strings.isTString(key)) {
            return super.getOwnProperty(thisObj, key);
        }
        Map<TruffleString, ExportResolution> exports = JSModuleNamespace.getExports(thisObj);
        ExportResolution binding = exports.get(key);
        if (binding != null) {
            Object value2 = JSModuleNamespace.getBindingValue(binding);
            return PropertyDescriptor.createData(value2, true, true, false);
        }
        return null;
    }

    public static boolean isJSModuleNamespace(Object obj) {
        return obj instanceof JSModuleNamespaceObject;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
        List<Object> symbolKeys;
        List<Object> list = symbolKeys = symbols ? JSModuleNamespace.symbolKeys(thisObj) : Collections.emptyList();
        if (!strings) {
            return symbolKeys;
        }
        Map<TruffleString, ExportResolution> exports = JSModuleNamespace.getExports(thisObj);
        ArrayList<Object> keys = new ArrayList<Object>(exports.size() + symbolKeys.size());
        keys.addAll(exports.keySet());
        keys.addAll(symbolKeys);
        return keys;
    }

    private static List<Object> symbolKeys(JSDynamicObject thisObj) {
        return thisObj.getShape().getKeyList();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean setIntegrityLevel(JSDynamicObject obj, boolean freeze, boolean doThrow) {
        if (freeze) {
            Map<TruffleString, ExportResolution> exports = JSModuleNamespace.getExports(obj);
            if (!exports.isEmpty()) {
                ExportResolution firstBinding = exports.values().iterator().next();
                JSModuleNamespace.getBindingValue(firstBinding);
                throw Errors.createTypeError("not allowed to freeze a namespace object");
            }
        } else {
            for (ExportResolution binding : JSModuleNamespace.getExports(obj).values()) {
                JSModuleNamespace.getBindingValue(binding);
            }
        }
        return true;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, Object key, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        if (isStrict) {
            throw Errors.createTypeErrorNotExtensible(thisObj, key);
        }
        return false;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean set(JSDynamicObject thisObj, long index, Object value2, Object receiver, boolean isStrict, Node encapsulatingNode) {
        if (isStrict) {
            throw Errors.createTypeErrorNotExtensible(thisObj, Strings.fromLong(index));
        }
        return false;
    }

    @Override
    public boolean usesOrdinaryGetOwnProperty() {
        return false;
    }
}

