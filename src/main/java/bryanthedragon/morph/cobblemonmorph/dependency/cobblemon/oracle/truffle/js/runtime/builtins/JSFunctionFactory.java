
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.function.InitFunctionNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class JSFunctionFactory {
    protected final JSContext context;
    protected final JSObjectFactory objectFactory;

    public static JSFunctionFactory create(JSContext context, JSDynamicObject prototype) {
        Shape initialShape = JSFunction.makeFunctionShape(context, prototype, false, false);
        JSObjectFactory factory = prototype == null ? JSObjectFactory.createUnbound(context, initialShape) : JSObjectFactory.createBound(context, prototype, initialShape);
        return new Default(context, factory);
    }

    static JSFunctionFactory createIntrinsic(JSContext context, JSObjectFactory objectFactory, boolean isStrict, boolean isConstructor, boolean isGenerator, boolean isBound, boolean isAsync) {
        return new Intrinsic(context, objectFactory, isStrict, isConstructor, isGenerator, isBound, isAsync);
    }

    static Shape makeShape(JSContext context, JSDynamicObject prototype, boolean isStrict, boolean isAnonymous, boolean isConstructor, boolean isGenerator, boolean isBound, boolean isAsync) {
        return JSFunction.makeFunctionShape(context, prototype, isGenerator, isAsync);
    }

    protected JSFunctionFactory(JSContext context, JSObjectFactory objectFactory) {
        this.context = context;
        this.objectFactory = objectFactory;
    }

    public final JSFunctionObject create(JSFunctionData functionData, MaterializedFrame enclosingFrame, Object classPrototype, JSRealm realm) {
        return this.createWithPrototype(functionData, enclosingFrame, classPrototype, realm, this.getPrototype(realm));
    }

    public final JSFunctionObject createWithPrototype(JSFunctionData functionData, MaterializedFrame enclosingFrame, Object classPrototype, JSRealm realm, JSDynamicObject prototype) {
        Shape shape = this.getShape(realm, prototype);
        assert (functionData != null);
        assert (enclosingFrame != null);
        assert (shape.getDynamicType() == JSFunction.INSTANCE);
        JSFunctionObject obj = JSFunctionObject.create(shape, functionData, enclosingFrame, realm, classPrototype);
        this.objectFactory.initProto(obj, prototype);
        this.initProperties(obj, functionData);
        if (this.context.getEcmaScriptVersion() < 6 && functionData.hasStrictFunctionProperties()) {
            JSFunctionFactory.initES5StrictProperties(obj, realm);
        }
        return obj;
    }

    protected abstract void initProperties(JSFunctionObject var1, JSFunctionData var2);

    public final JSFunctionObject createBound(JSFunctionData functionData, Object classPrototype, JSRealm realm, JSDynamicObject boundTargetFunction, Object boundThis, Object[] boundArguments) {
        Shape shape = this.objectFactory.getShape(realm);
        assert (functionData != null);
        assert (shape.getDynamicType() == JSFunction.INSTANCE);
        assert (functionData.hasStrictFunctionProperties());
        if (this.context.getEcmaScriptVersion() < 6) {
            return this.createBoundES5(shape, functionData, classPrototype, realm, boundTargetFunction, boundThis, boundArguments);
        }
        JSFunctionObject obj = JSFunctionObject.createBound(shape, functionData, realm, classPrototype, boundTargetFunction, boundThis, boundArguments);
        this.objectFactory.initProto(obj, realm);
        this.initProperties(obj, functionData);
        return obj;
    }

    private JSFunctionObject createBoundES5(Shape shape, JSFunctionData functionData, Object classPrototype, JSRealm realm, JSDynamicObject boundTargetFunction, Object boundThis, Object[] boundArguments) {
        JSFunctionObject obj = JSFunctionObject.createBound(shape, functionData, realm, classPrototype, boundTargetFunction, boundThis, boundArguments);
        this.objectFactory.initProto(obj, realm);
        this.initProperties(obj, functionData);
        JSFunctionFactory.initES5StrictProperties(obj, realm);
        return obj;
    }

    private static void initES5StrictProperties(JSDynamicObject obj, JSRealm realm) {
        int propertyFlags = JSAttributes.notConfigurableNotEnumerable() | 8;
        Accessor throwerAccessor = realm.getThrowerAccessor();
        DynamicObjectLibrary lib = DynamicObjectLibrary.getUncached();
        Properties.putWithFlags(lib, obj, JSFunction.ARGUMENTS, throwerAccessor, propertyFlags);
        Properties.putWithFlags(lib, obj, JSFunction.CALLER, throwerAccessor, propertyFlags);
    }

    protected abstract JSDynamicObject getPrototype(JSRealm var1);

    protected abstract Shape getShape(JSRealm var1, JSDynamicObject var2);

    protected abstract boolean isInObjectProto();

    private static final class Intrinsic
    extends JSFunctionFactory {
        private final InitFunctionNode initFunctionNode;

        protected Intrinsic(JSContext context, JSObjectFactory objectFactory, boolean isStrict, boolean isConstructor, boolean isGenerator, boolean isBound, boolean isAsync) {
            super(context, objectFactory);
            this.initFunctionNode = context.adoptNode(InitFunctionNode.create(context, isStrict, isConstructor, isBound, isGenerator, false));
        }

        @Override
        protected JSDynamicObject getPrototype(JSRealm realm) {
            return this.objectFactory.getPrototype(realm);
        }

        @Override
        protected Shape getShape(JSRealm realm, JSDynamicObject prototype) {
            return this.objectFactory.getShape(realm, prototype);
        }

        @Override
        protected boolean isInObjectProto() {
            return this.objectFactory.isInObjectProto();
        }

        @Override
        protected void initProperties(JSFunctionObject obj, JSFunctionData functionData) {
            this.initFunctionNode.execute(obj, functionData);
        }
    }

    private static final class Default
    extends JSFunctionFactory {
        protected Default(JSContext context, JSObjectFactory objectFactory) {
            super(context, objectFactory);
        }

        @Override
        protected JSDynamicObject getPrototype(JSRealm realm) {
            return realm.getFunctionPrototype();
        }

        @Override
        protected Shape getShape(JSRealm realm, JSDynamicObject prototype) {
            return this.objectFactory.getShape(realm, prototype);
        }

        @Override
        protected boolean isInObjectProto() {
            return this.objectFactory.isInObjectProto();
        }

        @Override
        protected void initProperties(JSFunctionObject obj, JSFunctionData functionData) {
        }
    }
}

