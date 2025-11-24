
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.WeakMapPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSWeakMap;
import com.oracle.truffle.js.runtime.builtins.JSWeakMapObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.WeakMap;
import java.util.Map;
import java.util.WeakHashMap;

public final class WeakMapPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<WeakMapPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new WeakMapPrototypeBuiltins();

    protected WeakMapPrototypeBuiltins() {
        super(JSWeakMap.PROTOTYPE_NAME, WeakMapPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WeakMapPrototype builtinEnum) {
        switch (builtinEnum) {
            case delete: {
                return WeakMapPrototypeBuiltinsFactory.JSWeakMapDeleteNodeGen.create(context, builtin, WeakMapPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case set: {
                return WeakMapPrototypeBuiltinsFactory.JSWeakMapSetNodeGen.create(context, builtin, WeakMapPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case get: {
                return WeakMapPrototypeBuiltinsFactory.JSWeakMapGetNodeGen.create(context, builtin, WeakMapPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case has: {
                return WeakMapPrototypeBuiltinsFactory.JSWeakMapHasNodeGen.create(context, builtin, WeakMapPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    protected static RuntimeException typeErrorKeyIsNotObject() {
        throw Errors.createTypeError("WeakMap key must be an object");
    }

    protected static RuntimeException typeErrorWeakMapExpected() {
        throw Errors.createTypeError("WeakMap expected");
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSWeakMapHasNode
    extends JSWeakMapBaseNode {
        public JSWeakMapHasNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean has(JSWeakMapObject thisObj, JSObject key, @CachedLibrary(limit="PropertyCacheLimit") DynamicObjectLibrary invertedGetter, @Cached(value="createBinaryProfile()") ConditionProfile hasInvertedProfile) {
            WeakMap map = (WeakMap)JSWeakMap.getInternalWeakMap(thisObj);
            Object inverted = JSWeakMapHasNode.getInvertedMap(key, invertedGetter);
            if (hasInvertedProfile.profile(inverted != null)) {
                WeakHashMap<WeakMap, Object> invertedMap = JSWeakMapHasNode.castWeakHashMap(inverted);
                return JSWeakMapHasNode.mapHas(invertedMap, map);
            }
            return false;
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        private static boolean mapHas(WeakHashMap<WeakMap, Object> invertedMap, WeakMap map) {
            return invertedMap.containsKey(map);
        }

        @Specialization(guards={"!isJSObject(key)"})
        protected static boolean hasNonObjectKey(JSWeakMapObject thisObj, Object key) {
            return false;
        }

        @Specialization(guards={"!isJSWeakMap(thisObj)"})
        protected static boolean notWeakMap(Object thisObj, Object key) {
            throw WeakMapPrototypeBuiltins.typeErrorWeakMapExpected();
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSWeakMapSetNode
    extends JSWeakMapBaseNode {
        public JSWeakMapSetNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object set(JSWeakMapObject thisObj, JSObject key, Object value2, @CachedLibrary(limit="PropertyCacheLimit") DynamicObjectLibrary invertedGetter, @CachedLibrary(limit="PropertyCacheLimit") DynamicObjectLibrary invertedSetter, @Cached(value="createBinaryProfile()") ConditionProfile hasInvertedProfile) {
            WeakMap map = (WeakMap)JSWeakMap.getInternalWeakMap(thisObj);
            Map<WeakMap, Object> inverted = JSWeakMapSetNode.getInvertedMap(key, invertedGetter);
            if (hasInvertedProfile.profile(inverted != null)) {
                WeakHashMap<WeakMap, Object> invertedMap = JSWeakMapSetNode.castWeakHashMap(inverted);
                JSWeakMapSetNode.mapPut(invertedMap, map, value2);
            } else {
                inverted = map.newInvertedMapWithEntry(key, value2);
                invertedSetter.put(key, WeakMap.INVERTED_WEAK_MAP_KEY, inverted);
            }
            return thisObj;
        }

        @Specialization(guards={"!isJSObject(key)"})
        protected static Object setNonObjectKey(JSWeakMapObject thisObj, Object key, Object value2) {
            throw WeakMapPrototypeBuiltins.typeErrorKeyIsNotObject();
        }

        @Specialization(guards={"!isJSWeakMap(thisObj)"})
        protected static Object notWeakMap(Object thisObj, Object key, Object value2) {
            throw WeakMapPrototypeBuiltins.typeErrorWeakMapExpected();
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        private static Object mapPut(WeakHashMap<WeakMap, Object> invertedMap, WeakMap map, Object value2) {
            return invertedMap.put(map, value2);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSWeakMapGetNode
    extends JSWeakMapBaseNode {
        public JSWeakMapGetNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object get(JSWeakMapObject thisObj, JSObject key, @CachedLibrary(limit="PropertyCacheLimit") DynamicObjectLibrary invertedGetter, @Cached(value="createBinaryProfile()") ConditionProfile hasInvertedProfile) {
            WeakHashMap<WeakMap, Object> invertedMap;
            Object value2;
            WeakMap map = (WeakMap)JSWeakMap.getInternalWeakMap(thisObj);
            Object inverted = JSWeakMapGetNode.getInvertedMap(key, invertedGetter);
            if (hasInvertedProfile.profile(inverted != null) && (value2 = JSWeakMapGetNode.mapGet(invertedMap = JSWeakMapGetNode.castWeakHashMap(inverted), map)) != null) {
                return value2;
            }
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSObject(key)"})
        protected static Object getNonObjectKey(JSWeakMapObject thisObj, Object key) {
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSWeakMap(thisObj)"})
        protected static Object notWeakMap(Object thisObj, Object key) {
            throw WeakMapPrototypeBuiltins.typeErrorWeakMapExpected();
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        private static Object mapGet(WeakHashMap<WeakMap, Object> invertedMap, WeakMap map) {
            return invertedMap.get(map);
        }
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class JSWeakMapDeleteNode
    extends JSWeakMapBaseNode {
        public JSWeakMapDeleteNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean delete(JSWeakMapObject thisObj, JSObject key, @CachedLibrary(limit="PropertyCacheLimit") DynamicObjectLibrary invertedGetter, @Cached(value="createBinaryProfile()") ConditionProfile hasInvertedProfile) {
            WeakMap map = (WeakMap)JSWeakMap.getInternalWeakMap(thisObj);
            Object inverted = JSWeakMapDeleteNode.getInvertedMap(key, invertedGetter);
            if (hasInvertedProfile.profile(inverted != null)) {
                WeakHashMap<WeakMap, Object> invertedMap = JSWeakMapDeleteNode.castWeakHashMap(inverted);
                return Boundaries.mapRemove(invertedMap, map) != null;
            }
            return false;
        }

        @Specialization(guards={"!isJSObject(key)"})
        protected static boolean deleteNonObjectKey(JSWeakMapObject thisObj, Object key) {
            return false;
        }

        @Specialization(guards={"!isJSWeakMap(thisObj)"})
        protected static boolean notWeakMap(Object thisObj, Object key) {
            throw WeakMapPrototypeBuiltins.typeErrorWeakMapExpected();
        }
    }

    protected static abstract class JSWeakMapBaseNode
    extends JSBuiltinNode {
        protected JSWeakMapBaseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected static Object getInvertedMap(JSObject key, DynamicObjectLibrary library) {
            return library.getOrDefault(key, WeakMap.INVERTED_WEAK_MAP_KEY, null);
        }

        protected static WeakHashMap<WeakMap, Object> castWeakHashMap(Object map) {
            return CompilerDirectives.castExact(map, WeakHashMap.class);
        }
    }

    public static enum WeakMapPrototype implements BuiltinEnum<WeakMapPrototype>
    {
        delete(1),
        set(2),
        get(1),
        has(1);

        private final int length;

        private WeakMapPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

