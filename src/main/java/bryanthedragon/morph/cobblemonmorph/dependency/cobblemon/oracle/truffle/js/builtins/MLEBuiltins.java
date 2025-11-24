
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.MLEBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class MLEBuiltins
extends JSBuiltinsContainer.SwitchEnum<MLE> {
    public static final JSBuiltinsContainer BUILTINS = new MLEBuiltins();

    protected MLEBuiltins() {
        super(JSRealm.MLE_CLASS_NAME, MLE.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, MLE builtinEnum) {
        if (builtinEnum == MLE.registerESMLookup) {
            return MLEBuiltinsFactory.MLERegisterEsmLookupNodeGen.create(context, builtin, MLEBuiltins.args().fixedArgs(1).createArgumentNodes(context));
        }
        return null;
    }

    public static abstract class MLERegisterEsmLookupNode
    extends JSBuiltinNode {
        protected MLERegisterEsmLookupNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected Object registerHook(Object callback) {
            if (!JSRuntime.isCallableForeign(callback)) {
                throw Errors.createError("Must provide callable foreign object!");
            }
            this.getRealm().registerCustomEsmPathMappingCallback(callback);
            return Undefined.instance;
        }
    }

    public static enum MLE implements BuiltinEnum<MLE>
    {
        registerESMLookup(1);

        private final int length;

        private MLE(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

