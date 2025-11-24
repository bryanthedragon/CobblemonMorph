
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.RealmFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class RealmFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<RealmFunction> {
    public static final JSBuiltinsContainer BUILTINS = new RealmFunctionBuiltins();

    protected RealmFunctionBuiltins() {
        super(JSRealm.REALM_BUILTIN_CLASS_NAME, RealmFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RealmFunction builtinEnum) {
        switch (builtinEnum) {
            case create: 
            case createAllowCrossRealmAccess: {
                return RealmFunctionBuiltinsFactory.RealmCreateNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case global: {
                return RealmFunctionBuiltinsFactory.RealmGlobalNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case dispose: {
                return RealmFunctionBuiltinsFactory.RealmDisposeNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case current: {
                return RealmFunctionBuiltinsFactory.RealmCurrentNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(0).createArgumentNodes(context));
            }
            case eval: {
                return RealmFunctionBuiltinsFactory.RealmEvalNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case owner: {
                return RealmFunctionBuiltinsFactory.RealmOwnerNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case detachGlobal: {
                return RealmFunctionBuiltinsFactory.RealmDetachGlobalNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case navigate: {
                return RealmFunctionBuiltinsFactory.RealmNavigateNodeGen.create(context, builtin, RealmFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    protected static JSRealm topLevelRealm(Node node) {
        return JSRealm.getMain(node);
    }

    protected static int toRealmIndexOrThrow(JSRealm topLevelRealm, Object index) {
        int realmIdx = JSRuntime.intValue(JSRuntime.toNumber(index));
        if (realmIdx < 0) {
            throw Errors.createTypeError("Invalid realm index");
        }
        JSRealm jsrealm = topLevelRealm.getFromRealmList(realmIdx);
        if (jsrealm == null) {
            throw Errors.createTypeError("Invalid realm index");
        }
        return realmIdx;
    }

    public static abstract class RealmNavigateNode
    extends JSBuiltinNode {
        public RealmNavigateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object navigate(Object index) {
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
            JSRealm realm = topLevelRealm.getFromRealmList(realmIndex);
            JSObject.setPrototype(realm.getGlobalObject(), Null.instance);
            realm.setGlobalObject(Undefined.instance);
            JSRealm newRealm = topLevelRealm.createChildRealm();
            int tempIdx = topLevelRealm.getIndexFromRealmList(newRealm);
            topLevelRealm.removeFromRealmList(tempIdx);
            topLevelRealm.setInRealmList(tempIdx, newRealm);
            return Undefined.instance;
        }
    }

    public static abstract class RealmDetachGlobalNode
    extends JSBuiltinNode {
        public RealmDetachGlobalNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object detachGlobal(Object index) {
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
            JSRealm realm = topLevelRealm.getFromRealmList(realmIndex);
            JSObject.setPrototype(realm.getGlobalObject(), Null.instance);
            realm.setGlobalObject(Undefined.instance);
            return Undefined.instance;
        }
    }

    public static abstract class RealmOwnerNode
    extends JSBuiltinNode {
        public RealmOwnerNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object owner(Object object) {
            JSRealm realm = null;
            if (!JSObject.isJSObject(object)) {
                throw Errors.createError("Invalid argument");
            }
            realm = RealmOwnerNode.creationRealm((JSObject)object);
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            int index = topLevelRealm.getIndexFromRealmList(realm);
            return index == -1 ? Undefined.instance : Integer.valueOf(index);
        }

        private static JSRealm creationRealm(JSObject object) {
            if (JSFunction.isJSFunction(object)) {
                return JSFunction.getRealm(object);
            }
            return RealmOwnerNode.creationRealmFromConstructor(object);
        }

        private static JSRealm creationRealmFromConstructor(JSObject object) {
            Object constructor;
            JSDynamicObject prototype;
            Object nonProxy = JSProxy.getTargetNonProxy(object);
            if (nonProxy instanceof JSObject && (prototype = JSObject.getPrototype((JSDynamicObject)nonProxy)) != Null.instance && JSFunction.isJSFunction(constructor = JSRuntime.getDataProperty(prototype, JSObject.CONSTRUCTOR))) {
                return JSFunction.getRealm((JSFunctionObject)constructor);
            }
            return null;
        }
    }

    public static abstract class RealmEvalNode
    extends JSBuiltinNode {
        public RealmEvalNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object eval(Object index, Object code) {
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
            JSRealm selectedRealm = topLevelRealm.getFromRealmList(realmIndex);
            String sourceText = JSRuntime.toJavaString(code);
            Source source = Source.newBuilder("js", sourceText, "<eval>").build();
            JSRealm currentV8Realm = topLevelRealm.getCurrentV8Realm();
            topLevelRealm.setCurrentV8Realm(selectedRealm);
            try {
                ScriptNode script = this.getContext().getEvaluator().parseEval(this.getContext(), this, source);
                Object object = script.runEval(IndirectCallNode.getUncached(), selectedRealm);
                return object;
            }
            finally {
                topLevelRealm.setCurrentV8Realm(currentV8Realm);
            }
        }
    }

    public static abstract class RealmCurrentNode
    extends JSBuiltinNode {
        public RealmCurrentNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object current() {
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            JSRealm currentRealm = topLevelRealm.getCurrentV8Realm();
            return topLevelRealm.getIndexFromRealmList(currentRealm);
        }
    }

    public static abstract class RealmGlobalNode
    extends JSBuiltinNode {
        public RealmGlobalNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object global(Object index) {
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
            JSRealm jsrealm = topLevelRealm.getFromRealmList(realmIndex);
            return jsrealm.getGlobalObject();
        }
    }

    public static abstract class RealmDisposeNode
    extends JSBuiltinNode {
        public RealmDisposeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object dispose(Object index) {
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
            topLevelRealm.removeFromRealmList(realmIndex);
            return Undefined.instance;
        }
    }

    public static abstract class RealmCreateNode
    extends JSBuiltinNode {
        public RealmCreateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object createRealm() {
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            JSRealm newRealm = topLevelRealm.createChildRealm();
            return topLevelRealm.getIndexFromRealmList(newRealm);
        }
    }

    public static enum RealmFunction implements BuiltinEnum<RealmFunction>
    {
        create(0),
        createAllowCrossRealmAccess(0),
        global(1),
        dispose(1),
        current(0),
        eval(2),
        owner(1),
        detachGlobal(1),
        navigate(1);

        private final int length;

        private RealmFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

