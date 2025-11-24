
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.intl.ListFormatPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.cast.JSStringListFromIterableNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormatObject;
import java.util.List;

public final class ListFormatPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<ListFormatPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new ListFormatPrototypeBuiltins();

    protected ListFormatPrototypeBuiltins() {
        super(JSListFormat.PROTOTYPE_NAME, ListFormatPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ListFormatPrototype builtinEnum) {
        switch (builtinEnum) {
            case resolvedOptions: {
                return ListFormatPrototypeBuiltinsFactory.JSListFormatResolvedOptionsNodeGen.create(context, builtin, ListFormatPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case format: {
                return ListFormatPrototypeBuiltinsFactory.JSListFormatFormatNodeGen.create(context, builtin, ListFormatPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case formatToParts: {
                return ListFormatPrototypeBuiltinsFactory.JSListFormatFormatToPartsNodeGen.create(context, builtin, ListFormatPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSListFormatFormatToPartsNode
    extends JSBuiltinNode {
        public JSListFormatFormatToPartsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doFormatToParts(JSListFormatObject listFormat, Object value2, @Cached(value="create(getContext())") JSStringListFromIterableNode strListFromIterableNode) {
            List<String> list = strListFromIterableNode.executeIterable(value2);
            return JSListFormat.formatToParts(this.getContext(), this.getRealm(), listFormat, list);
        }

        @Fallback
        public Object throwTypeError(Object bummer, Object value2) {
            throw Errors.createTypeErrorTypeXExpected(JSListFormat.CLASS_NAME);
        }
    }

    public static abstract class JSListFormatFormatNode
    extends JSBuiltinNode {
        public JSListFormatFormatNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString doFormat(JSListFormatObject listFormat, Object value2, @Cached(value="create(getContext())") JSStringListFromIterableNode strListFromIterableNode) {
            List<String> list = strListFromIterableNode.executeIterable(value2);
            return JSListFormat.format(listFormat, list);
        }

        @Fallback
        public Object throwTypeError(Object bummer, Object value2) {
            throw Errors.createTypeErrorTypeXExpected(JSListFormat.CLASS_NAME);
        }
    }

    public static abstract class JSListFormatResolvedOptionsNode
    extends JSBuiltinNode {
        public JSListFormatResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doResolvedOptions(JSListFormatObject listFormat) {
            return JSListFormat.resolvedOptions(this.getContext(), this.getRealm(), listFormat);
        }

        @Fallback
        public Object throwTypeError(Object bummer) {
            throw Errors.createTypeErrorTypeXExpected(JSListFormat.CLASS_NAME);
        }
    }

    public static enum ListFormatPrototype implements BuiltinEnum<ListFormatPrototype>
    {
        resolvedOptions(0),
        format(1),
        formatToParts(1);

        private final int length;

        private ListFormatPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

