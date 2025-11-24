
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.WeakSetPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSWeakSet;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class WeakSetPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<WeakSetPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new WeakSetPrototypeBuiltins();
    protected static final Object PRESENT = new Object();

    protected WeakSetPrototypeBuiltins() {
        super(JSWeakSet.PROTOTYPE_NAME, WeakSetPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WeakSetPrototype builtinEnum) {
        switch (builtinEnum) {
            case delete: {
                return WeakSetPrototypeBuiltinsFactory.JSWeakSetDeleteNodeGen.create(context, builtin, WeakSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case add: {
                return WeakSetPrototypeBuiltinsFactory.JSWeakSetAddNodeGen.create(context, builtin, WeakSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case has: {
                return WeakSetPrototypeBuiltinsFactory.JSWeakSetHasNodeGen.create(context, builtin, WeakSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    protected static RuntimeException typeErrorKeyIsNotObject() {
        throw Errors.createTypeError("WeakSet key must be an object");
    }

    protected static RuntimeException typeErrorWeakSetExpected() {
        throw Errors.createTypeError("WeakSet expected");
    }

    public static abstract class JSWeakSetHasNode
    extends JSBuiltinNode {
        public JSWeakSetHasNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSWeakSet(thisObj)", "isJSObject(key)"})
        protected static boolean has(JSDynamicObject thisObj, JSDynamicObject key) {
            return Boundaries.mapContainsKey(JSWeakSet.getInternalWeakMap(thisObj), key);
        }

        @Specialization(guards={"isJSWeakSet(thisObj)", "!isJSObject(key)"})
        protected static boolean hasNonObjectKey(Object thisObj, Object key) {
            return false;
        }

        @Specialization(guards={"!isJSWeakSet(thisObj)"})
        protected static boolean notWeakSet(Object thisObj, Object key) {
            throw WeakSetPrototypeBuiltins.typeErrorWeakSetExpected();
        }
    }

    public static abstract class JSWeakSetAddNode
    extends JSBuiltinNode {
        public JSWeakSetAddNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSWeakSet(thisObj)", "isJSObject(key)"})
        protected static JSDynamicObject add(JSDynamicObject thisObj, JSDynamicObject key) {
            Boundaries.mapPut(JSWeakSet.getInternalWeakMap(thisObj), key, PRESENT);
            return thisObj;
        }

        @Specialization(guards={"isJSWeakSet(thisObj)", "!isJSObject(key)"})
        protected static JSDynamicObject addNonObjectKey(Object thisObj, Object key) {
            throw WeakSetPrototypeBuiltins.typeErrorKeyIsNotObject();
        }

        @Specialization(guards={"!isJSWeakSet(thisObj)"})
        protected static JSDynamicObject notWeakSet(Object thisObj, Object key) {
            throw WeakSetPrototypeBuiltins.typeErrorWeakSetExpected();
        }
    }

    public static abstract class JSWeakSetDeleteNode
    extends JSBuiltinNode {
        public JSWeakSetDeleteNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSWeakSet(thisObj)", "isJSObject(key)"})
        protected static boolean delete(JSDynamicObject thisObj, JSDynamicObject key) {
            return Boundaries.mapRemove(JSWeakSet.getInternalWeakMap(thisObj), key) != null;
        }

        @Specialization(guards={"isJSWeakSet(thisObj)", "!isJSObject(key)"})
        protected static boolean deleteNonObjectKey(Object thisObj, Object key) {
            return false;
        }

        @Specialization(guards={"!isJSWeakSet(thisObj)"})
        protected static boolean notWeakSet(Object thisObj, Object key) {
            throw WeakSetPrototypeBuiltins.typeErrorWeakSetExpected();
        }
    }

    public static enum WeakSetPrototype implements BuiltinEnum<WeakSetPrototype>
    {
        delete(1),
        add(1),
        has(1);

        private final int length;

        private WeakSetPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

