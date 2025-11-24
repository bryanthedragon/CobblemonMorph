
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.intl.CollatorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollatorObject;

public final class CollatorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<CollatorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new CollatorPrototypeBuiltins();

    protected CollatorPrototypeBuiltins() {
        super(JSCollator.PROTOTYPE_NAME, CollatorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, CollatorPrototype builtinEnum) {
        switch (builtinEnum) {
            case resolvedOptions: {
                return CollatorPrototypeBuiltinsFactory.JSCollatorResolvedOptionsNodeGen.create(context, builtin, CollatorPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSCollatorResolvedOptionsNode
    extends JSBuiltinNode {
        public JSCollatorResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doResolvedOptions(JSCollatorObject collator) {
            return JSCollator.resolvedOptions(this.getContext(), this.getRealm(), collator);
        }

        @Fallback
        public Object doResolvedOptions(Object bummer) {
            throw Errors.createTypeError("Collator object expected.");
        }
    }

    public static enum CollatorPrototype implements BuiltinEnum<CollatorPrototype>
    {
        resolvedOptions(0);

        private final int length;

        private CollatorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

