
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.WeakRefPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSWeakRef;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class WeakRefPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<WeakRefPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new WeakRefPrototypeBuiltins();

    protected WeakRefPrototypeBuiltins() {
        super(JSWeakRef.PROTOTYPE_NAME, WeakRefPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WeakRefPrototype builtinEnum) {
        switch (builtinEnum) {
            case deref: {
                return WeakRefPrototypeBuiltinsFactory.JSWeakRefDerefNodeGen.create(context, builtin, WeakRefPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSWeakRefDerefNode
    extends JSWeakRefOperation {
        public JSWeakRefDerefNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSWeakRef(thisObj)"})
        protected JSDynamicObject deref(JSDynamicObject thisObj) {
            Object referent = JSWeakRef.getInternalWeakRef(thisObj).get();
            if (referent != null) {
                this.getContext().addWeakRefTargetToSet(referent);
                return (JSDynamicObject)referent;
            }
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSWeakRef(thisObj)"})
        protected static JSDynamicObject notWeakRef(Object thisObj) {
            throw Errors.createTypeError("WeakRef expected");
        }
    }

    public static abstract class JSWeakRefOperation
    extends JSBuiltinNode {
        public JSWeakRefOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }
    }

    public static enum WeakRefPrototype implements BuiltinEnum<WeakRefPrototype>
    {
        deref(0);

        private final int length;

        private WeakRefPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

