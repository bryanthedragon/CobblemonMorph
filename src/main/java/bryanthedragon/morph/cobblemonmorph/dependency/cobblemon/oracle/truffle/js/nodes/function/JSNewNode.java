
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.function.JSNewNodeGen;
import com.oracle.truffle.js.nodes.instrumentation.JSInputGeneratingNodeWrapper;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.lang.reflect.Modifier;
import java.util.Set;

@ImportStatic(value={JSConfig.class})
public abstract class JSNewNode
extends JavaScriptNode {
    @Node.Child
    @Executed
    protected JavaScriptNode targetNode;
    @Node.Child
    private AbstractFunctionArgumentsNode arguments;
    protected final JSContext context;

    protected JSNewNode(JSContext context, JavaScriptNode targetNode, AbstractFunctionArgumentsNode arguments) {
        this.context = context;
        this.targetNode = targetNode;
        this.arguments = arguments;
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ObjectAllocationTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor();
        descriptor.addProperty("isNew", (Object)true);
        descriptor.addProperty("isInvoke", (Object)false);
        return descriptor;
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (this.materializationNeeded(materializedTags)) {
            JavaScriptNode newTarget = JSInputGeneratingNodeWrapper.create(JSNewNode.cloneUninitialized(this.getTarget(), materializedTags));
            JSNewNode materialized = JSNewNodeGen.create(this.context, newTarget, AbstractFunctionArgumentsNode.cloneUninitialized(this.arguments, materializedTags));
            this.arguments.materializeInstrumentableArguments();
            JSNewNode.transferSourceSectionAndTags(this, materialized);
            return materialized;
        }
        return this;
    }

    private boolean materializationNeeded(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.ObjectAllocationTag.class)) {
            return !this.getTarget().hasSourceSection() && !JSNodeUtil.isInputGeneratingNode(this.getTarget());
        }
        return false;
    }

    public static JSNewNode create(JSContext context, JavaScriptNode function, AbstractFunctionArgumentsNode arguments) {
        return JSNewNodeGen.create(context, function, arguments);
    }

    public JavaScriptNode getTarget() {
        return this.targetNode;
    }

    @Specialization(guards={"isJSFunction(target)"})
    public Object doNewReturnThis(VirtualFrame frame, JSDynamicObject target, @Cached(value="createNew()") @Cached.Shared(value="callNew") JSFunctionCallNode callNew) {
        int userArgumentCount = this.arguments.getCount(frame);
        Object[] args = JSArguments.createInitial(JSFunction.CONSTRUCT, target, userArgumentCount);
        args = this.arguments.executeFillObjectArray(frame, args, 2);
        return callNew.executeCall(args);
    }

    @Specialization(guards={"isJSProxy(proxy)"})
    protected Object doNewJSProxy(VirtualFrame frame, JSDynamicObject proxy, @Cached(value="createNew()") @Cached.Shared(value="callNew") JSFunctionCallNode callNew) {
        return this.doNewReturnThis(frame, proxy, callNew);
    }

    @Specialization(guards={"isJSAdapter(target)"})
    public Object doJSAdapter(VirtualFrame frame, JSDynamicObject target) {
        Object[] args = this.getAbstractFunctionArguments(frame);
        Object newFunction = JSObject.get(JSAdapter.getAdaptee(target), JSAdapter.NEW);
        if (JSFunction.isJSFunction(newFunction)) {
            return JSFunction.call((JSFunctionObject)newFunction, target, args);
        }
        return Undefined.instance;
    }

    @Specialization(guards={"isJavaPackage(target)"})
    public Object createClassNotFoundError(VirtualFrame frame, JSDynamicObject target) {
        this.getAbstractFunctionArguments(frame);
        throw Errors.createTypeErrorClassNotFound(JavaPackage.getPackageName(target));
    }

    @CompilerDirectives.TruffleBoundary
    private static void throwCannotExtendError(Class<?> target) {
        throw Errors.createTypeError("new cannot be used with non-public java type " + target.getTypeName() + ".");
    }

    @Specialization(guards={"isForeignObject(target)"})
    public Object doNewForeignObject(VirtualFrame frame, Object target, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached(value="create()") ExportValueNode convert, @Cached(value="create()") ImportValueNode toJSType, @Cached(value="createBinaryProfile()") ConditionProfile isHostClassProf, @Cached(value="createBinaryProfile()") ConditionProfile isAbstractProf) {
        Object newTarget = target;
        int count = this.arguments.getCount(frame);
        Object[] args = new Object[count];
        args = this.arguments.executeFillObjectArray(frame, args, 0);
        for (int i = 0; i < args.length; ++i) {
            args[i] = convert.execute(args[i]);
        }
        if (!JSConfig.SubstrateVM && this.context.isOptionNashornCompatibilityMode()) {
            Class javaType;
            TruffleLanguage.Env env = this.getRealm().getEnv();
            if (isHostClassProf.profile(count == 1 && env.isHostObject(target) && env.asHostObject(target) instanceof Class) && isAbstractProf.profile(Modifier.isAbstract((javaType = (Class)env.asHostObject(target)).getModifiers()) && !javaType.isArray())) {
                newTarget = this.extend(javaType, env);
            }
        }
        try {
            return toJSType.executeWithTarget(interop.instantiate(newTarget, args));
        }
        catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
            throw Errors.createTypeErrorInteropException(target, e, "instantiate", this);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private Object extend(Class<?> type, TruffleLanguage.Env env) {
        assert (!JSConfig.SubstrateVM);
        if (!Modifier.isPublic(type.getModifiers())) {
            JSNewNode.throwCannotExtendError(type);
        }
        try {
            return env.createHostAdapter(new Object[]{env.asHostSymbol(type)});
        }
        catch (Exception ex) {
            throw Errors.createTypeError(ex.getMessage(), ex, this);
        }
    }

    @Specialization(guards={"!isJSFunction(target)", "!isJSAdapter(target)", "!isJSProxy(target)", "!isJavaPackage(target)", "!isForeignObject(target)"})
    public Object createFunctionTypeError(VirtualFrame frame, Object target) {
        this.getAbstractFunctionArguments(frame);
        return this.throwFunctionTypeError(target);
    }

    @CompilerDirectives.TruffleBoundary
    private Object throwFunctionTypeError(Object target) {
        Object targetForError;
        String targetStr = this.getTarget().expressionToString();
        Object object = targetForError = targetStr == null ? target : targetStr;
        if (this.context.isOptionNashornCompatibilityMode()) {
            throw Errors.createTypeErrorNotAFunction(targetForError, this);
        }
        throw Errors.createTypeErrorNotAConstructor(targetForError, this, this.context);
    }

    private Object[] getAbstractFunctionArguments(VirtualFrame frame) {
        Object[] args = new Object[this.arguments.getCount(frame)];
        args = this.arguments.executeFillObjectArray(frame, args, 0);
        return args;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSNewNodeGen.create(this.context, JSNewNode.cloneUninitialized(this.getTarget(), materializedTags), AbstractFunctionArgumentsNode.cloneUninitialized(this.arguments, materializedTags));
    }
}

