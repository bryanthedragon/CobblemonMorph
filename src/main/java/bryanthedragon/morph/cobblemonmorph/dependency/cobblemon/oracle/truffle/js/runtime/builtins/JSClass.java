
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public abstract class JSClass {
    protected JSClass() {
    }

    @CompilerDirectives.TruffleBoundary
    public abstract JSDynamicObject getPrototypeOf(JSDynamicObject var1);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean setPrototypeOf(JSDynamicObject var1, JSDynamicObject var2);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean isExtensible(JSDynamicObject var1);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean preventExtensions(JSDynamicObject var1, boolean var2);

    @CompilerDirectives.TruffleBoundary
    public abstract PropertyDescriptor getOwnProperty(JSDynamicObject var1, Object var2);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean defineOwnProperty(JSDynamicObject var1, Object var2, PropertyDescriptor var3, boolean var4);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean hasProperty(JSDynamicObject var1, Object var2);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean hasProperty(JSDynamicObject var1, long var2);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean hasOwnProperty(JSDynamicObject var1, Object var2);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean hasOwnProperty(JSDynamicObject var1, long var2);

    public final Object get(JSDynamicObject thisObj, Object key) {
        Object value2 = this.getHelper(thisObj, (Object)thisObj, key, null);
        assert (!(value2 instanceof String));
        return JSRuntime.nullToUndefined(value2);
    }

    public Object get(JSDynamicObject thisObj, long index) {
        Object value2 = this.getHelper(thisObj, (Object)thisObj, index, (Node)null);
        assert (!(value2 instanceof String));
        return JSRuntime.nullToUndefined(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public abstract Object getHelper(JSDynamicObject var1, Object var2, Object var3, Node var4);

    @CompilerDirectives.TruffleBoundary
    public abstract Object getHelper(JSDynamicObject var1, Object var2, long var3, Node var5);

    @CompilerDirectives.TruffleBoundary
    public abstract Object getOwnHelper(JSDynamicObject var1, Object var2, Object var3, Node var4);

    @CompilerDirectives.TruffleBoundary
    public abstract Object getOwnHelper(JSDynamicObject var1, Object var2, long var3, Node var5);

    @CompilerDirectives.TruffleBoundary
    public abstract Object getMethodHelper(JSDynamicObject var1, Object var2, Object var3, Node var4);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean set(JSDynamicObject var1, Object var2, Object var3, Object var4, boolean var5, Node var6);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean set(JSDynamicObject var1, long var2, Object var4, Object var5, boolean var6, Node var7);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean delete(JSDynamicObject var1, Object var2, boolean var3);

    @CompilerDirectives.TruffleBoundary
    public abstract boolean delete(JSDynamicObject var1, long var2, boolean var4);

    public final List<Object> ownPropertyKeys(JSDynamicObject obj) {
        return this.getOwnPropertyKeys(obj, true, true);
    }

    @CompilerDirectives.TruffleBoundary
    public abstract List<Object> getOwnPropertyKeys(JSDynamicObject var1, boolean var2, boolean var3);

    @CompilerDirectives.TruffleBoundary
    public static List<Object> filterOwnPropertyKeys(List<Object> ownPropertyKeys, boolean strings, boolean symbols) {
        if (strings && symbols) {
            return ownPropertyKeys;
        }
        ArrayList<Object> names = new ArrayList<Object>();
        for (Object key : ownPropertyKeys) {
            if (!symbols && key instanceof Symbol || !strings && Strings.isTString(key)) continue;
            names.add(key);
        }
        return names;
    }

    @CompilerDirectives.TruffleBoundary
    public abstract boolean hasOnlyShapeProperties(JSDynamicObject var1);

    @CompilerDirectives.TruffleBoundary
    public abstract TruffleString getClassName(JSDynamicObject var1);

    @CompilerDirectives.TruffleBoundary
    public abstract String toString();

    @CompilerDirectives.TruffleBoundary
    public TruffleString defaultToString(JSDynamicObject object) {
        JSContext context = JSObject.getJSContext(object);
        if (context.getEcmaScriptVersion() <= 5) {
            return this.formatToString(this.getClassName(object));
        }
        TruffleString result = this.getToStringTag(object);
        return this.formatToString(result);
    }

    protected TruffleString getToStringTag(JSDynamicObject object) {
        Object toStringTag;
        TruffleString result = this.getBuiltinToStringTag(object);
        if (JSRuntime.isObject(object) && Strings.isTString(toStringTag = JSObject.get(object, Symbol.SYMBOL_TO_STRING_TAG))) {
            result = JSRuntime.toStringIsString(toStringTag);
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return this.getClassName(object);
    }

    @CompilerDirectives.TruffleBoundary
    protected TruffleString formatToString(TruffleString object) {
        return Strings.concatAll(Strings.BRACKET_OBJECT_SPC, object, Strings.BRACKET_CLOSE);
    }

    @CompilerDirectives.TruffleBoundary
    public abstract TruffleString toDisplayStringImpl(JSDynamicObject var1, boolean var2, ToDisplayStringFormat var3, int var4);

    public final boolean isInstance(JSDynamicObject object) {
        return JSClass.isInstance(object, this);
    }

    public final boolean isInstance(Object object) {
        return JSClass.isInstance(object, this);
    }

    public static boolean isInstance(Object object, JSClass jsclass) {
        return JSDynamicObject.isJSDynamicObject(object) && JSClass.isInstance((JSDynamicObject)object, jsclass);
    }

    public static boolean isInstance(JSDynamicObject object, JSClass jsclass) {
        return object.getShape().getDynamicType() == jsclass;
    }

    @CompilerDirectives.TruffleBoundary
    public boolean testIntegrityLevel(JSDynamicObject obj, boolean frozen) {
        return this.testIntegrityLevelDefault(obj, frozen);
    }

    @CompilerDirectives.TruffleBoundary
    protected final boolean testIntegrityLevelDefault(JSDynamicObject obj, boolean frozen) {
        assert (JSRuntime.isObject(obj));
        boolean status = this.isExtensible(obj);
        if (status) {
            return false;
        }
        for (Object key : JSObject.ownPropertyKeys(obj)) {
            PropertyDescriptor desc = JSObject.getOwnProperty(obj, key);
            if (desc == null) continue;
            if (desc.getConfigurable()) {
                return false;
            }
            if (!frozen || !desc.isDataDescriptor() || !desc.getWritable()) continue;
            return false;
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    public boolean setIntegrityLevel(JSDynamicObject obj, boolean freeze, boolean doThrow) {
        return this.setIntegrityLevelDefault(obj, freeze, doThrow);
    }

    @CompilerDirectives.TruffleBoundary
    private boolean setIntegrityLevelDefault(JSDynamicObject obj, boolean freeze, boolean doThrow) {
        assert (JSRuntime.isObject(obj));
        if (!this.preventExtensions(obj, doThrow)) {
            return false;
        }
        List<Object> keys = JSObject.ownPropertyKeys(obj);
        if (freeze) {
            for (Object t : keys) {
                PropertyDescriptor currentDesc = JSObject.getOwnProperty(obj, t);
                if (currentDesc == null) continue;
                PropertyDescriptor newDesc = null;
                newDesc = currentDesc.isAccessorDescriptor() ? FreezeHolder.FREEZE_ACC_DESC : FreezeHolder.FREEZE_DATA_DESC;
                JSRuntime.definePropertyOrThrow(obj, t, newDesc);
            }
        } else {
            for (Object t : keys) {
                JSRuntime.definePropertyOrThrow(obj, t, FreezeHolder.FREEZE_ACC_DESC);
            }
        }
        return true;
    }

    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        throw Errors.shouldNotReachHere(this.getClass().getName());
    }

    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        throw Errors.shouldNotReachHere(this.getClass().getName());
    }

    public abstract boolean usesOrdinaryGetOwnProperty();

    public abstract boolean usesOrdinaryIsExtensible();

    private static final class FreezeHolder {
        private static final PropertyDescriptor FREEZE_ACC_DESC = PropertyDescriptor.createEmpty();
        private static final PropertyDescriptor FREEZE_DATA_DESC;

        private FreezeHolder() {
        }

        static {
            FREEZE_ACC_DESC.setConfigurable(false);
            FREEZE_DATA_DESC = PropertyDescriptor.createEmpty();
            FREEZE_DATA_DESC.setConfigurable(false);
            FREEZE_DATA_DESC.setWritable(false);
        }
    }
}

