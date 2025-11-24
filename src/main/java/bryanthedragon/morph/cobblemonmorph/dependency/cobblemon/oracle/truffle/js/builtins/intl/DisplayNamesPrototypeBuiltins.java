
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.intl.DisplayNamesPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNamesObject;

public final class DisplayNamesPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<DisplayNamesPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new DisplayNamesPrototypeBuiltins();

    protected DisplayNamesPrototypeBuiltins() {
        super(JSDisplayNames.PROTOTYPE_NAME, DisplayNamesPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DisplayNamesPrototype builtinEnum) {
        switch (builtinEnum) {
            case resolvedOptions: {
                return DisplayNamesPrototypeBuiltinsFactory.JSDisplayNamesResolvedOptionsNodeGen.create(context, builtin, DisplayNamesPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case of: {
                return DisplayNamesPrototypeBuiltinsFactory.JSDisplayNamesOfNodeGen.create(context, builtin, DisplayNamesPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSDisplayNamesOfNode
    extends JSBuiltinNode {
        @Node.Child
        JSToStringNode toStringNode = JSToStringNode.create();

        public JSDisplayNamesOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doDisplayNames(JSDisplayNamesObject displayNames, Object code) {
            TruffleString codeString = this.toStringNode.executeString(code);
            return JSDisplayNames.of(displayNames, Strings.toJavaString(codeString));
        }

        @Specialization(guards={"!isJSDisplayNames(bummer)"})
        public Object doOther(Object bummer, Object code) {
            throw Errors.createTypeErrorDisplayNamesExpected();
        }
    }

    public static abstract class JSDisplayNamesResolvedOptionsNode
    extends JSBuiltinNode {
        public JSDisplayNamesResolvedOptionsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doDisplayNames(JSDisplayNamesObject displayNames) {
            return JSDisplayNames.resolvedOptions(this.getContext(), this.getRealm(), displayNames);
        }

        @Specialization(guards={"!isJSDisplayNames(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorDisplayNamesExpected();
        }
    }

    public static enum DisplayNamesPrototype implements BuiltinEnum<DisplayNamesPrototype>
    {
        resolvedOptions(0),
        of(1);

        private final int length;

        private DisplayNamesPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

