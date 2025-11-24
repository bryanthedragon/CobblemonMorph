
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.FinalizationRegistryPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistry;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistryObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class FinalizationRegistryPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<FinalizationRegistryPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new FinalizationRegistryPrototypeBuiltins();

    protected FinalizationRegistryPrototypeBuiltins() {
        super(JSFinalizationRegistry.PROTOTYPE_NAME, FinalizationRegistryPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, FinalizationRegistryPrototype builtinEnum) {
        switch (builtinEnum) {
            case register: {
                return FinalizationRegistryPrototypeBuiltinsFactory.JSFinalizationRegistryRegisterNodeGen.create(context, builtin, FinalizationRegistryPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case unregister: {
                return FinalizationRegistryPrototypeBuiltinsFactory.JSFinalizationRegistryUnregisterNodeGen.create(context, builtin, FinalizationRegistryPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case cleanupSome: {
                return FinalizationRegistryPrototypeBuiltinsFactory.JSFinalizationRegistryCleanupSomeNodeGen.create(context, builtin, FinalizationRegistryPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSFinalizationRegistryCleanupSomeNode
    extends FinalizationRegistryOperation {
        @Node.Child
        protected IsCallableNode isCallableNode = IsCallableNode.create();

        public JSFinalizationRegistryCleanupSomeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject cleanupSome(JSFinalizationRegistryObject thisObj, Object callback) {
            if (callback != Undefined.instance && !this.isCallableNode.executeBoolean(callback)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("FinalizationRegistry: cleanup must be callable");
            }
            JSFinalizationRegistry.cleanupFinalizationRegistry(thisObj, callback);
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSFinalizationRegistry(thisObj)"})
        protected static JSDynamicObject notFinalizationRegistry(Object thisObj, Object callback) {
            throw Errors.createTypeErrorFinalizationRegistryExpected();
        }
    }

    public static abstract class JSFinalizationRegistryUnregisterNode
    extends FinalizationRegistryOperation {
        @Node.Child
        protected IsObjectNode isObjectNode = IsObjectNode.create();

        public JSFinalizationRegistryUnregisterNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean unregister(JSFinalizationRegistryObject thisObj, Object unregisterToken) {
            if (!this.isObjectNode.executeBoolean(unregisterToken)) {
                this.invalidUnregisterToken(unregisterToken);
            }
            return JSFinalizationRegistry.removeFromCells(thisObj, unregisterToken);
        }

        @Specialization(guards={"!isJSFinalizationRegistry(thisObj)"})
        protected static boolean notFinalizationRegistry(Object thisObj, Object unregisterToken) {
            throw Errors.createTypeErrorFinalizationRegistryExpected();
        }
    }

    public static abstract class JSFinalizationRegistryRegisterNode
    extends FinalizationRegistryOperation {
        @Node.Child
        protected JSIdenticalNode sameValueNode = JSIdenticalNode.createSameValue();
        @Node.Child
        protected IsObjectNode isObjectNode = IsObjectNode.create();

        public JSFinalizationRegistryRegisterNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject register(JSFinalizationRegistryObject thisObj, Object target, Object holdings, Object unregisterTokenArg) {
            if (!this.isObjectNode.executeBoolean(target)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("FinalizationRegistry.prototype.register: target must be an object");
            }
            if (this.sameValueNode.executeBoolean(target, holdings)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("FinalizationRegistry.prototype.register: target and holdings must not be same");
            }
            Object unregisterToken = unregisterTokenArg;
            if (!this.isObjectNode.executeBoolean(unregisterToken)) {
                if (unregisterToken != Undefined.instance) {
                    this.invalidUnregisterToken(unregisterToken);
                }
                unregisterToken = Undefined.instance;
            }
            JSFinalizationRegistry.appendToCells(thisObj, target, holdings, unregisterToken);
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSFinalizationRegistry(thisObj)"})
        protected static JSDynamicObject notFinalizationRegistry(Object thisObj, Object target, Object holdings, Object unregisterToken) {
            throw Errors.createTypeErrorFinalizationRegistryExpected();
        }
    }

    public static abstract class FinalizationRegistryOperation
    extends JSBuiltinNode {
        protected final BranchProfile errorBranch = BranchProfile.create();

        public FinalizationRegistryOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected void invalidUnregisterToken(Object token) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorFormat("unregisterToken ('%s') must be an object", JSRuntime.safeToString(token));
        }
    }

    public static enum FinalizationRegistryPrototype implements BuiltinEnum<FinalizationRegistryPrototype>
    {
        register(2),
        unregister(1),
        cleanupSome(0);

        private final int length;

        private FinalizationRegistryPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

