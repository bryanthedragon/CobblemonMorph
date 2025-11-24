
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.AsyncFromSyncIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.AsyncGeneratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.EnumerateIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.ForInIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.FunctionPrototypeBuiltins;
import com.oracle.truffle.js.builtins.GeneratorPrototypeBuiltins;
import com.oracle.truffle.js.nodes.binary.InstanceofNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Nullish;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;

public final class JSFunction
extends JSNonProxy {
    public static final TruffleString TYPE_NAME = Strings.constant("function");
    public static final TruffleString CLASS_NAME = Strings.constant("Function");
    public static final TruffleString CLASS_NAME_NASHORN_COMPAT = Strings.constant("FunctionNashornCompat");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Function.prototype");
    public static final TruffleString GENERATOR_FUNCTION_NAME = Strings.constant("GeneratorFunction");
    public static final TruffleString GENERATOR_NAME = Strings.constant("Generator");
    public static final TruffleString GENERATOR_PROTOTYPE_NAME = Strings.constant("Generator.prototype");
    public static final TruffleString ASYNC_FUNCTION_NAME = Strings.constant("AsyncFunction");
    public static final TruffleString ASYNC_GENERATOR_FUNCTION_NAME = Strings.constant("AsyncGeneratorFunction");
    public static final TruffleString ASYNC_GENERATOR_NAME = Strings.constant("AsyncGenerator");
    public static final TruffleString ASYNC_GENERATOR_PROTOTYPE_NAME = Strings.constant("AsyncGenerator.prototype");
    public static final TruffleString ENUMERATE_ITERATOR_PROTOTYPE_NAME = Strings.constant("[[Enumerate]].prototype");
    public static final TruffleString FOR_IN_ITERATOR_PROTOYPE_NAME = Strings.constant("%ForInIteratorPrototype%");
    public static final TruffleString CALLER = Strings.constant("caller");
    public static final TruffleString ARGUMENTS = Strings.constant("arguments");
    public static final TruffleString LENGTH = Strings.constant("length");
    public static final TruffleString NAME = Strings.constant("name");
    public static final TruffleString ORDINARY_HAS_INSTANCE = Strings.constant("OrdinaryHasInstance");
    public static final String PROGRAM_FUNCTION_NAME = ":program";
    public static final String BUILTIN_SOURCE_NAME = "<builtin>";
    public static final TruffleString TS_BUILTIN_SOURCE_NAME = Strings.constant("<builtin>");
    public static final SourceSection BUILTIN_SOURCE_SECTION = JSFunction.createBuiltinSourceSection("<builtin>");
    public static final HiddenKey ASYNC_FROM_SYNC_ITERATOR_KEY = new HiddenKey("SyncIterator");
    public static final TruffleString ASYNC_FROM_SYNC_ITERATOR_PROTOTYPE_NAME = Strings.constant("%AsyncFromSyncIteratorPrototype%");
    public static final PropertyProxy PROTOTYPE_PROXY = new ClassPrototypeProxyProperty();
    public static final PropertyProxy LENGTH_PROXY = new FunctionLengthPropertyProxy();
    public static final PropertyProxy NAME_PROXY = new FunctionNamePropertyProxy();
    public static final PropertyProxy ARGUMENTS_PROXY = new ArgumentsProxyProperty();
    public static final PropertyProxy CALLER_PROXY = new CallerProxyProperty();
    public static final Object CLASS_PROTOTYPE_PLACEHOLDER = new Object();
    public static final JSFunction INSTANCE = new JSFunction();
    public static final HiddenKey HOME_OBJECT_ID = new HiddenKey("HomeObject");
    public static final HiddenKey CLASS_FIELDS_ID = new HiddenKey("Fields");
    public static final HiddenKey CLASS_INITIALIZERS_ID = new HiddenKey("Initializers");
    public static final HiddenKey PRIVATE_BRAND_ID = new HiddenKey("PrivateBrand");
    public static final HiddenKey GENERATOR_STATE_ID = new HiddenKey("GeneratorState");
    public static final HiddenKey GENERATOR_CONTEXT_ID = new HiddenKey("GeneratorContext");
    public static final HiddenKey GENERATOR_TARGET_ID = new HiddenKey("GeneratorTarget");
    public static final HiddenKey ASYNC_GENERATOR_STATE_ID = new HiddenKey("AsyncGeneratorState");
    public static final HiddenKey ASYNC_GENERATOR_CONTEXT_ID = new HiddenKey("AsyncGeneratorContext");
    public static final HiddenKey ASYNC_GENERATOR_QUEUE_ID = new HiddenKey("AsyncGeneratorQueue");
    public static final HiddenKey ASYNC_GENERATOR_TARGET_ID = new HiddenKey("AsyncGeneratorTarget");
    private static final HiddenKey GENERATOR_FUNCTION_MARKER_ID = new HiddenKey("generator function");
    private static final HiddenKey ASYNC_GENERATOR_FUNCTION_MARKER_ID = new HiddenKey("async generator function");
    public static final HiddenKey DEBUG_SCOPE_ID = new HiddenKey("Scope");
    public static final JSDynamicObject CONSTRUCT = new Nullish();

    private JSFunction() {
    }

    public static CallTarget getCallTarget(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).getCallTarget();
    }

    public static MaterializedFrame getEnclosingFrame(JSDynamicObject obj) {
        assert (JSFunction.isJSFunction(obj));
        return ((JSFunctionObject)obj).getEnclosingFrame();
    }

    public static JSFunctionData getFunctionData(JSDynamicObject obj) {
        assert (JSFunction.isJSFunction(obj));
        return ((JSFunctionObject)obj).getFunctionData();
    }

    public static JSFunctionData getFunctionData(JSFunctionObject obj) {
        return obj.getFunctionData();
    }

    private static Object getClassPrototypeField(JSDynamicObject obj) {
        assert (JSFunction.isJSFunction(obj));
        return ((JSFunctionObject)obj).getClassPrototype();
    }

    private static void setClassPrototypeField(JSDynamicObject obj, Object classPrototype) {
        assert (JSFunction.isJSFunction(obj));
        ((JSFunctionObject)obj).setClassPrototype(classPrototype);
    }

    public static JSRealm getRealm(JSDynamicObject obj) {
        assert (JSFunction.isJSFunction(obj));
        return ((JSFunctionObject)obj).getRealm();
    }

    public static JSRealm getRealm(JSFunctionObject obj) {
        return obj.getRealm();
    }

    public static JSRealm getRealm(JSFunctionObject functionObj, JSContext context, Node node) {
        JSRealm realm;
        assert (JSFunction.isJSFunction(functionObj));
        if (context.isSingleRealm()) {
            realm = JSRealm.get(node);
            assert (realm == JSFunction.getRealm(functionObj));
        } else {
            realm = JSFunction.getRealm(functionObj);
        }
        return realm;
    }

    public static JSFunctionObject create(JSRealm realm, JSFunctionData functionData) {
        return JSFunction.create(realm, functionData, JSFrameUtil.NULL_MATERIALIZED_FRAME);
    }

    public static JSFunctionObject create(JSRealm realm, JSFunctionData functionData, MaterializedFrame enclosingFrame) {
        return JSFunction.createDefault(functionData, enclosingFrame, CLASS_PROTOTYPE_PLACEHOLDER, realm);
    }

    public static JSFunctionObject createWithPrototype(JSFunctionFactory factory, JSRealm realm, JSFunctionData functionData, MaterializedFrame enclosingFrame, JSDynamicObject prototype) {
        return JSFunction.createWithPrototype(factory, functionData, enclosingFrame, CLASS_PROTOTYPE_PLACEHOLDER, realm, prototype);
    }

    public static JSFunctionObject createLexicalThis(JSRealm realm, JSFunctionData functionData, MaterializedFrame enclosingFrame, Object lexicalThis) {
        return JSFunction.createDefault(functionData, enclosingFrame, lexicalThis, realm);
    }

    private static JSFunctionObject createDefault(JSFunctionData functionData, MaterializedFrame enclosingFrame, Object classPrototype, JSRealm realm) {
        JSFunctionFactory factory = JSFunction.initialFactory(functionData);
        return factory.create(functionData, enclosingFrame, classPrototype, realm);
    }

    private static JSFunctionObject createWithPrototype(JSFunctionFactory factory, JSFunctionData functionData, MaterializedFrame enclosingFrame, Object classPrototype, JSRealm realm, JSDynamicObject prototype) {
        return factory.createWithPrototype(functionData, enclosingFrame, classPrototype, realm, prototype);
    }

    public static JSFunctionObject createBound(JSContext context, JSRealm realm, JSFunctionData functionData, JSDynamicObject boundTargetFunction, Object boundThis, Object[] boundArguments) {
        assert (functionData != null);
        JSFunctionFactory factory = context.getBoundFunctionFactory(functionData);
        return factory.createBound(functionData, CLASS_PROTOTYPE_PLACEHOLDER, realm, boundTargetFunction, boundThis, boundArguments);
    }

    private static JSFunctionFactory initialFactory(JSFunctionData functionData) {
        return functionData.getContext().getFunctionFactory(functionData);
    }

    public static TruffleString getName(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).getName();
    }

    public static Object call(JSFunctionObject functionObject, Object thisObject, Object[] argumentValues) {
        assert (JSFunction.isJSFunction(functionObject));
        assert (thisObject != null);
        Object[] arguments = JSArguments.create(thisObject, functionObject, argumentValues);
        return JSFunction.getCallTarget(functionObject).call(arguments);
    }

    public static Object call(Object[] jsArguments) {
        assert (JSFunction.isJSFunction(JSArguments.getFunctionObject(jsArguments)));
        assert (JSArguments.getThisObject(jsArguments) != null);
        return JSFunction.getCallTarget((JSFunctionObject)JSArguments.getFunctionObject(jsArguments)).call(jsArguments);
    }

    public static Object construct(JSFunctionObject functionObject, Object[] argumentValues) {
        assert (JSFunction.isJSFunction(functionObject) && JSFunction.isConstructor(functionObject));
        Object[] arguments = JSArguments.create(CONSTRUCT, functionObject, argumentValues);
        return JSFunction.getConstructTarget(functionObject).call(arguments);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject bind(JSRealm realm, JSFunctionObject thisFnObj, Object thisArg, Object[] boundArguments) {
        TruffleString targetName;
        Object targetLen;
        assert (JSFunction.isJSFunction(thisFnObj));
        JSContext context = realm.getContext();
        JSDynamicObject proto = JSObject.getPrototype(thisFnObj);
        JSDynamicObject boundFunction = JSFunction.boundFunctionCreate(context, thisFnObj, thisArg, boundArguments, proto, ConditionProfile.getUncached(), ConditionProfile.getUncached(), ConditionProfile.getUncached(), null);
        long length = 0L;
        boolean targetHasLength = JSObject.hasOwnProperty((JSDynamicObject)thisFnObj, LENGTH);
        boolean mustSetLength = true;
        if (targetHasLength && JSRuntime.isNumber(targetLen = JSObject.get((JSDynamicObject)thisFnObj, LENGTH))) {
            long targetLenInt = JSRuntime.toInteger(targetLen);
            length = Math.max(0L, targetLenInt - (long)boundArguments.length);
            if (targetLenInt == (long)JSFunction.getLength(thisFnObj)) {
                mustSetLength = false;
            }
        }
        if (mustSetLength) {
            JSFunction.setFunctionLength(boundFunction, JSRuntime.longToIntOrDouble(length));
        }
        if (!(targetName = JSFunction.getFunctionName(thisFnObj)).equals(JSFunction.getName(thisFnObj))) {
            JSFunction.setBoundFunctionName(boundFunction, targetName);
        }
        return boundFunction;
    }

    public static JSDynamicObject boundFunctionCreate(JSContext context, JSFunctionObject boundTargetFunction, Object boundThis, Object[] boundArguments, JSDynamicObject proto, ConditionProfile isConstructorProfile, ConditionProfile isAsyncProfile, ConditionProfile setProtoProfile, Node node) {
        boolean needSetProto;
        CompilerAsserts.partialEvaluationConstant(context);
        JSFunctionData targetFunctionData = JSFunction.getFunctionData(boundTargetFunction);
        boolean constructor = isConstructorProfile.profile(targetFunctionData.isConstructor());
        boolean isAsync = isAsyncProfile.profile(targetFunctionData.isAsync());
        JSFunctionData boundFunctionData = context.getBoundFunctionData(constructor, isAsync);
        JSRealm realm = JSFunction.getRealm(boundTargetFunction, context, node);
        JSFunctionObject boundFunction = JSFunction.createBound(context, realm, boundFunctionData, boundTargetFunction, boundThis, boundArguments);
        boolean bl = needSetProto = proto != realm.getFunctionPrototype();
        if (setProtoProfile.profile(needSetProto)) {
            JSObject.setPrototype(boundFunction, proto);
        }
        assert (JSObject.getPrototype(boundFunction) == proto);
        return boundFunction;
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString getFunctionName(JSDynamicObject thisFnObj) {
        Object name = JSObject.get(thisFnObj, NAME);
        if (!Strings.isTString(name)) {
            name = Strings.EMPTY_STRING;
        }
        return (TruffleString)name;
    }

    @CompilerDirectives.TruffleBoundary
    public static void setFunctionLength(JSDynamicObject functionObj, Number length) {
        JSObject.defineOwnProperty(functionObj, LENGTH, PropertyDescriptor.createData(length, false, false, true));
    }

    @CompilerDirectives.TruffleBoundary
    public static void setBoundFunctionName(JSDynamicObject boundFunction, TruffleString targetName) {
        JSObject.defineOwnProperty(boundFunction, NAME, PropertyDescriptor.createData(Strings.concat(Strings.BOUND_SPC, targetName), false, false, true));
    }

    public static boolean isStrict(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).isStrict();
    }

    public static boolean isBuiltin(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).isBuiltin();
    }

    public static boolean isConstructor(JSDynamicObject obj) {
        assert (JSFunction.isJSFunction(obj));
        return JSFunction.getFunctionData(obj).isConstructor();
    }

    public static boolean isConstructor(Object obj) {
        return JSFunction.isJSFunction(obj) && JSFunction.getFunctionData((JSDynamicObject)obj).isConstructor();
    }

    public static boolean isGenerator(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).isGenerator();
    }

    public static boolean needsParentFrame(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).needsParentFrame();
    }

    public static int getLength(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).getLength();
    }

    public static boolean isClassPrototypeInitialized(JSDynamicObject thisObj) {
        return JSFunction.getClassPrototypeField(thisObj) != CLASS_PROTOTYPE_PLACEHOLDER;
    }

    public static boolean isBoundFunction(JSDynamicObject function) {
        return JSFunction.isJSFunction(function) && JSFunction.getFunctionData(function).isBound();
    }

    public static boolean isAsyncFunction(JSDynamicObject function) {
        return JSFunction.isJSFunction(function) && JSFunction.getFunctionData(function).isAsync();
    }

    public static Object getBoundThis(JSDynamicObject function) {
        assert (JSFunction.isBoundFunction(function));
        return ((JSFunctionObject.Bound)function).getBoundThis();
    }

    public static JSDynamicObject getBoundTargetFunction(JSDynamicObject function) {
        assert (JSFunction.isBoundFunction(function));
        return ((JSFunctionObject.Bound)function).getBoundTargetFunction();
    }

    public static Object[] getBoundArguments(JSDynamicObject function) {
        assert (JSFunction.isBoundFunction(function));
        return ((JSFunctionObject.Bound)function).getBoundArguments();
    }

    public static Object getLexicalThis(JSDynamicObject thisObj) {
        return JSFunction.getClassPrototypeInitialized(thisObj);
    }

    public static Object getClassPrototypeInitialized(JSDynamicObject thisObj) {
        Object classPrototype = JSFunction.getClassPrototypeField(thisObj);
        assert (classPrototype != CLASS_PROTOTYPE_PLACEHOLDER);
        return classPrototype;
    }

    public static Object getClassPrototype(JSDynamicObject thisObj) {
        Object classPrototype = JSFunction.getClassPrototypeField(thisObj);
        if (CompilerDirectives.injectBranchProbability(1.0E-4, classPrototype == CLASS_PROTOTYPE_PLACEHOLDER)) {
            JSFunction.initializeClassPrototype(thisObj);
            classPrototype = JSFunction.getClassPrototypeField(thisObj);
        }
        return classPrototype;
    }

    private static void initializeClassPrototype(JSDynamicObject thisObj) {
        assert (!JSFunction.isClassPrototypeInitialized(thisObj));
        JSFunction.setClassPrototypeField(thisObj, JSFunction.createPrototype(thisObj));
    }

    @CompilerDirectives.TruffleBoundary
    private static JSDynamicObject createPrototype(JSDynamicObject constructor) {
        JSFunctionData functionData = JSFunction.getFunctionData(constructor);
        JSRealm realm = JSFunction.getRealm(constructor);
        JSContext context = functionData.getContext();
        if (!functionData.isGenerator()) {
            JSObject prototype = JSOrdinary.create(context, realm);
            JSObjectUtil.putConstructorProperty(context, prototype, constructor);
            return prototype;
        }
        assert (functionData.isGenerator());
        if (functionData.isAsync()) {
            return JSOrdinary.createWithRealm(context, context.getAsyncGeneratorObjectFactory(), realm);
        }
        return JSOrdinary.createWithRealm(context, context.getGeneratorObjectFactory(), realm);
    }

    public static void setClassPrototype(JSDynamicObject thisObj, Object value2) {
        assert (value2 != null);
        JSFunction.setClassPrototypeField(thisObj, value2);
    }

    public static RootNode createBoundRootNode(JSContext context, boolean construct, boolean newTarget) {
        if (newTarget) {
            return new BoundConstructNewTargetRootNode(context);
        }
        if (construct) {
            return new BoundConstructRootNode(context);
        }
        return new BoundRootNode(context);
    }

    public static JSFunctionObject createFunctionPrototype(JSRealm realm, JSDynamicObject objectPrototype) {
        JSContext context = realm.getContext();
        Shape protoShape = JSShape.createPrototypeShape(context, INSTANCE, objectPrototype);
        JSFunctionObject proto = JSFunctionObject.create(protoShape, JSFunction.createEmptyFunctionData(context), JSFrameUtil.NULL_MATERIALIZED_FRAME, realm, CLASS_PROTOTYPE_PLACEHOLDER);
        JSObjectUtil.setOrVerifyPrototype(context, proto, objectPrototype);
        JSObjectUtil.putDataProperty(context, proto, LENGTH, 0, JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putDataProperty(context, proto, NAME, Strings.EMPTY_STRING, JSAttributes.configurableNotEnumerableNotWritable());
        return proto;
    }

    public static void addRestrictedFunctionProperties(JSRealm realm, JSDynamicObject obj) {
        JSObjectUtil.putBuiltinAccessorProperty(obj, (Object)CALLER, realm.getThrowerFunction(), realm.getThrowerFunction());
        JSObjectUtil.putBuiltinAccessorProperty(obj, (Object)ARGUMENTS, realm.getThrowerFunction(), realm.getThrowerFunction());
    }

    public static JSFunctionData createNamedEmptyFunctionData(JSContext context, TruffleString name) {
        return context.getNamedEmptyFunctionData(name);
    }

    public static JSFunctionData createEmptyFunctionData(JSContext context) {
        return JSFunction.createNamedEmptyFunctionData(context, Strings.EMPTY_STRING);
    }

    public static JSFunctionObject createNamedEmptyFunction(JSRealm realm, TruffleString name) {
        return JSFunction.create(realm, JSFunction.createNamedEmptyFunctionData(realm.getContext(), name));
    }

    public static JSFunctionObject createEmptyFunction(JSRealm realm) {
        return JSFunction.create(realm, JSFunction.createEmptyFunctionData(realm.getContext()));
    }

    public static void fillFunctionPrototype(JSRealm realm) {
        JSContext ctx = realm.getContext();
        JSObjectUtil.putConstructorProperty(ctx, realm.getFunctionPrototype(), realm.getFunctionConstructor());
        JSObjectUtil.putFunctionsFromContainer(realm, realm.getFunctionPrototype(), FunctionPrototypeBuiltins.BUILTINS);
        if (ctx.getEcmaScriptVersion() >= 6) {
            JSFunction.addRestrictedFunctionProperties(realm, realm.getFunctionPrototype());
        }
        if (ctx.isOptionNashornCompatibilityMode()) {
            JSObjectUtil.putFunctionsFromContainer(realm, realm.getFunctionPrototype(), FunctionPrototypeBuiltins.BUILTINS_NASHORN_COMPAT);
        }
    }

    public static Shape makeFunctionShape(JSContext context, JSDynamicObject prototype, boolean isGenerator, boolean isAsync) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
        if (isGenerator) {
            initialShape = Shape.newBuilder(initialShape).addConstantProperty(isAsync ? ASYNC_GENERATOR_FUNCTION_MARKER_ID : GENERATOR_FUNCTION_MARKER_ID, null, 0).build();
        }
        return initialShape;
    }

    public static JSFunctionObject createFunctionConstructor(JSRealm realm) {
        JSContext ctx = realm.getContext();
        JSFunctionObject functionConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, CLASS_NAME);
        JSObjectUtil.putDataProperty(ctx, functionConstructor, JSObject.PROTOTYPE, realm.getFunctionPrototype(), JSAttributes.notConfigurableNotEnumerableNotWritable());
        return functionConstructor;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return this.getClassName(object);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        RootNode rn = ((RootCallTarget)JSFunction.getCallTarget(obj)).getRootNode();
        SourceSection ssect = rn.getSourceSection();
        TruffleString source = ssect == null || !ssect.isAvailable() || ssect.getSource().isInternal() ? Strings.concatAll(Strings.FUNCTION_SPC, JSFunction.getName(obj), Strings.FUNCTION_NATIVE_CODE_BODY) : (depth >= format.getMaxDepth() ? Strings.concatAll(Strings.FUNCTION_SPC, JSFunction.getName(obj), Strings.FUNCTION_BODY_DOTS) : (ssect.getCharacters().length() > 200 ? Strings.concat(Strings.fromCharSequence(ssect.getCharacters().subSequence(0, 195)), Strings.FUNCTION_BODY_OMITTED) : Strings.fromCharSequence(ssect.getCharacters())));
        return source;
    }

    @Override
    public boolean hasOnlyShapeProperties(JSDynamicObject obj) {
        return true;
    }

    public static CallTarget getConstructTarget(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).getConstructTarget();
    }

    public static CallTarget getConstructNewTarget(JSDynamicObject obj) {
        return JSFunction.getFunctionData(obj).getConstructNewTarget();
    }

    public static boolean isJSFunction(Object obj) {
        return obj instanceof JSFunctionObject;
    }

    public static JSObject createGeneratorFunctionPrototype(JSRealm realm, JSDynamicObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, realm.getFunctionPrototype());
        JSObjectUtil.putDataProperty(ctx, prototype, JSObject.CONSTRUCTOR, constructor, JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putDataProperty(ctx, prototype, JSObject.PROTOTYPE, JSFunction.createGeneratorPrototype(realm, prototype), JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putToStringTag(prototype, GENERATOR_FUNCTION_NAME);
        return prototype;
    }

    private static JSObject createGeneratorPrototype(JSRealm realm, JSDynamicObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject generatorPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, realm.getIteratorPrototype());
        JSObjectUtil.putFunctionsFromContainer(realm, generatorPrototype, GeneratorPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putDataProperty(ctx, generatorPrototype, JSObject.CONSTRUCTOR, constructor, JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putToStringTag(generatorPrototype, GENERATOR_NAME);
        return generatorPrototype;
    }

    public static JSConstructor createGeneratorFunctionConstructor(JSRealm realm) {
        JSContext ctx = realm.getContext();
        JSFunctionObject constructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, GENERATOR_FUNCTION_NAME);
        JSObject.setPrototype(constructor, realm.getFunctionConstructor());
        JSObject prototype = JSFunction.createGeneratorFunctionPrototype(realm, constructor);
        JSObjectUtil.putDataProperty(ctx, constructor, JSObject.PROTOTYPE, prototype, JSAttributes.notConfigurableNotEnumerableNotWritable());
        return new JSConstructor(constructor, prototype);
    }

    public static JSObject createAsyncFunctionPrototype(JSRealm realm, JSDynamicObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, realm.getFunctionPrototype());
        JSObjectUtil.putDataProperty(ctx, prototype, JSObject.CONSTRUCTOR, constructor, JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putToStringTag(prototype, ASYNC_FUNCTION_NAME);
        return prototype;
    }

    public static JSConstructor createAsyncFunctionConstructor(JSRealm realm) {
        JSContext ctx = realm.getContext();
        JSFunctionObject constructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, ASYNC_FUNCTION_NAME);
        JSObject.setPrototype(constructor, realm.getFunctionConstructor());
        JSObject prototype = JSFunction.createAsyncFunctionPrototype(realm, constructor);
        JSObjectUtil.putDataProperty(ctx, constructor, JSObject.PROTOTYPE, prototype, JSAttributes.notConfigurableNotEnumerableNotWritable());
        return new JSConstructor(constructor, prototype);
    }

    public static JSObject createAsyncIteratorPrototype(JSRealm realm) {
        JSContext context = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSFunctionData functionData = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.FunctionAsyncIterator, c -> JSFunctionData.createCallOnly(context, new JavaScriptRootNode(context.getLanguage(), null, null){

            @Override
            public Object execute(VirtualFrame frame) {
                return JSFrameUtil.getThisObj(frame);
            }
        }.getCallTarget(), 0, Symbol.SYMBOL_ASYNC_ITERATOR.toFunctionNameString()));
        JSFunctionObject asyncIterator = JSFunction.create(realm, functionData);
        JSObjectUtil.putDataProperty(context, prototype, Symbol.SYMBOL_ASYNC_ITERATOR, asyncIterator, JSAttributes.getDefaultNotEnumerable());
        return prototype;
    }

    public static JSObject createAsyncFromSyncIteratorPrototype(JSRealm realm) {
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, AsyncFromSyncIteratorPrototypeBuiltins.BUILTINS);
        return prototype;
    }

    public static JSObject createAsyncGeneratorFunctionPrototype(JSRealm realm, JSDynamicObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, realm.getFunctionPrototype());
        JSObjectUtil.putDataProperty(ctx, prototype, JSObject.CONSTRUCTOR, constructor, JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putDataProperty(ctx, prototype, JSObject.PROTOTYPE, JSFunction.createAsyncGeneratorPrototype(realm, prototype), JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putToStringTag(prototype, ASYNC_GENERATOR_FUNCTION_NAME);
        return prototype;
    }

    private static JSObject createAsyncGeneratorPrototype(JSRealm realm, JSDynamicObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, realm.getAsyncIteratorPrototype());
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, AsyncGeneratorPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putDataProperty(ctx, prototype, JSObject.CONSTRUCTOR, constructor, JSAttributes.configurableNotEnumerableNotWritable());
        JSObjectUtil.putToStringTag(prototype, ASYNC_GENERATOR_NAME);
        return prototype;
    }

    public static JSConstructor createAsyncGeneratorFunctionConstructor(JSRealm realm) {
        JSContext ctx = realm.getContext();
        JSFunctionObject constructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, ASYNC_GENERATOR_FUNCTION_NAME);
        JSObject.setPrototype(constructor, realm.getFunctionConstructor());
        JSObject prototype = JSFunction.createAsyncGeneratorFunctionPrototype(realm, constructor);
        JSObjectUtil.putDataProperty(ctx, constructor, JSObject.PROTOTYPE, prototype, JSAttributes.notConfigurableNotEnumerableNotWritable());
        return new JSConstructor(constructor, prototype);
    }

    public static JSDynamicObject createEnumerateIteratorPrototype(JSRealm realm) {
        JSDynamicObject iteratorPrototype = realm.getIteratorPrototype();
        JSObject enumerateIteratorPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, iteratorPrototype);
        JSObjectUtil.putFunctionsFromContainer(realm, enumerateIteratorPrototype, EnumerateIteratorPrototypeBuiltins.BUILTINS);
        return enumerateIteratorPrototype;
    }

    public static Shape makeInitialEnumerateIteratorShape(JSContext context, JSDynamicObject enumerateIteratorPrototype) {
        return JSObjectUtil.getProtoChildShape(enumerateIteratorPrototype, JSOrdinary.INSTANCE, context);
    }

    public static JSDynamicObject createForInIteratorPrototype(JSRealm realm) {
        JSDynamicObject iteratorPrototype = realm.getIteratorPrototype();
        JSObject enumerateIteratorPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, iteratorPrototype);
        JSObjectUtil.putFunctionsFromContainer(realm, enumerateIteratorPrototype, ForInIteratorPrototypeBuiltins.BUILTINS);
        return enumerateIteratorPrototype;
    }

    public static Shape makeInitialForInIteratorShape(JSContext context, JSDynamicObject iteratorPrototype) {
        return JSObjectUtil.getProtoChildShape(iteratorPrototype, JSOrdinary.INSTANCE, context);
    }

    public static JSDynamicObject createOrdinaryHasInstanceFunction(JSRealm realm) {
        JSContext ctx = realm.getContext();
        return JSFunction.create(realm, ctx.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.OrdinaryHasInstance, c -> JSFunctionData.createCallOnly(c, new InstanceofNode.OrdinaryHasInstanceRootNode((JSContext)c).getCallTarget(), 1, ORDINARY_HAS_INSTANCE)));
    }

    public static RootNode getFrameRootNode(FrameInstance frameInstance) {
        Node callNode = frameInstance.getCallNode();
        if (callNode != null) {
            return callNode.getRootNode();
        }
        CallTarget callTarget = frameInstance.getCallTarget();
        if (callTarget instanceof RootCallTarget) {
            return ((RootCallTarget)callTarget).getRootNode();
        }
        return null;
    }

    public static SourceSection createBuiltinSourceSection(String name) {
        return Source.newBuilder("js", "", name).internal(true).build().createUnavailableSection();
    }

    public static boolean isBuiltinSourceSection(SourceSection sourceSection) {
        return sourceSection == BUILTIN_SOURCE_SECTION;
    }

    public static boolean isBuiltinThatShouldNotAppearInStackTrace(JSRealm realm, JSDynamicObject function) {
        return function == realm.getApplyFunctionObject() || function == realm.getCallFunctionObject() || function == realm.getReflectApplyFunctionObject() || function == realm.getReflectConstructFunctionObject();
    }

    public static boolean isStrictBuiltin(JSDynamicObject function, JSRealm realm) {
        JSFunctionData functionData = JSFunction.getFunctionData(function);
        PropertyDescriptor desc = JSObject.getOwnProperty(realm.getArrayPrototype(), functionData.getName());
        return desc != null && desc.isDataDescriptor() && desc.getValue() == function;
    }

    public static Source getCallerSource() {
        SourceSection callerSourceSection;
        RootNode callerRootNode = Truffle.getRuntime().iterateFrames(JSFunction::getFrameRootNode, 1);
        if (callerRootNode != null && (callerSourceSection = callerRootNode.getSourceSection()) != null && callerSourceSection.isAvailable()) {
            return callerSourceSection.getSource();
        }
        return null;
    }

    public static final class CallerProxyProperty
    extends PropertyProxy {
        private CallerProxyProperty() {
        }

        @Override
        public Object get(JSDynamicObject thiz) {
            JSFunctionObject thisFunction = (JSFunctionObject)thiz;
            assert (!JSFunction.getFunctionData(thisFunction).hasStrictFunctionProperties() && JSFunction.getFunctionData(thisFunction).getContext().isOptionV8CompatibilityMode());
            return JSRuntime.toJSNull(CallerProxyProperty.findCaller(thisFunction));
        }

        @CompilerDirectives.TruffleBoundary
        private static Object findCaller(final JSFunctionObject thisFunction) {
            final JSFunctionData thisFunctionData = JSFunction.getFunctionData(thisFunction);
            return Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<Object>(){
                private boolean seenThisFunction = false;

                @Override
                public Object visitFrame(FrameInstance frameInstance) {
                    CompilerAsserts.neverPartOfCompilation();
                    RootNode rootNode = JSFunction.getFrameRootNode(frameInstance);
                    if (rootNode instanceof FunctionRootNode) {
                        Frame frame;
                        Object function;
                        if (this.seenThisFunction) {
                            Frame frame2 = frameInstance.getFrame(FrameInstance.FrameAccess.READ_WRITE);
                            Object function2 = JSArguments.getFunctionObject(frame2.getArguments());
                            if (!JSFunction.isJSFunction(function2)) {
                                return null;
                            }
                            JSFunctionObject callerFunction = (JSFunctionObject)function2;
                            SourceSection ss = rootNode.getSourceSection();
                            if (ss == null) {
                                return null;
                            }
                            if (ss.getSource().isInternal() && !JSFunction.isBuiltinSourceSection(ss)) {
                                return null;
                            }
                            JSFunctionData functionData = JSFunction.getFunctionData(callerFunction);
                            if (JSFunction.isBuiltinSourceSection(ss)) {
                                JSRealm realm = JSRealm.get(null);
                                if (callerFunction == realm.getEvalFunctionObject()) {
                                    return null;
                                }
                                if (JSFunction.isBuiltinThatShouldNotAppearInStackTrace(realm, callerFunction)) {
                                    return null;
                                }
                                if (Strings.startsWith(functionData.getName(), Strings.BRACKET_SYMBOL_DOT)) {
                                    return null;
                                }
                                if (JSFunction.isStrictBuiltin(callerFunction, realm)) {
                                    return Null.instance;
                                }
                            } else if (functionData.isStrict()) {
                                return Null.instance;
                            }
                            if (!JSFunction.PROGRAM_FUNCTION_NAME.equals(rootNode.getName())) {
                                return callerFunction;
                            }
                        } else if (((FunctionRootNode)rootNode).getFunctionData() == thisFunctionData && (function = JSArguments.getFunctionObject((frame = frameInstance.getFrame(FrameInstance.FrameAccess.READ_WRITE)).getArguments())) == thisFunction) {
                            this.seenThisFunction = true;
                        }
                    }
                    return null;
                }
            });
        }
    }

    public static final class ArgumentsProxyProperty
    extends PropertyProxy {
        private ArgumentsProxyProperty() {
        }

        @Override
        public Object get(JSDynamicObject thiz) {
            JSFunctionObject thisFunction = (JSFunctionObject)thiz;
            assert (!JSFunction.getFunctionData(thisFunction).hasStrictFunctionProperties() && JSFunction.getFunctionData(thisFunction).getContext().isOptionV8CompatibilityMode());
            return JSRuntime.toJSNull(ArgumentsProxyProperty.createArguments(thisFunction));
        }

        @CompilerDirectives.TruffleBoundary
        private static Object createArguments(final JSFunctionObject thisFunction) {
            final JSFunctionData thisFunctionData = JSFunction.getFunctionData(thisFunction);
            return Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<Object>(){

                @Override
                public Object visitFrame(FrameInstance frameInstance) {
                    Frame frame;
                    Object function;
                    CompilerAsserts.neverPartOfCompilation();
                    RootNode rootNode = JSFunction.getFrameRootNode(frameInstance);
                    if (rootNode instanceof FunctionRootNode && ((FunctionRootNode)rootNode).getFunctionData() == thisFunctionData && (function = JSArguments.getFunctionObject((frame = frameInstance.getFrame(FrameInstance.FrameAccess.READ_WRITE)).getArguments())) == thisFunction) {
                        JSRealm realm = JSRealm.get(null);
                        Object[] userArguments = JSArguments.extractUserArguments(frame.getArguments());
                        return JSArgumentsArray.createNonStrictSlow(realm, userArguments, (JSFunctionObject)function);
                    }
                    return null;
                }
            });
        }
    }

    static final class BoundConstructNewTargetRootNode
    extends BoundRootNode {
        BoundConstructNewTargetRootNode(JSContext context) {
            super(context);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object[] originalArguments = frame.getArguments();
            JSDynamicObject boundFunction = BoundConstructNewTargetRootNode.castBoundFunction(JSArguments.getFunctionObject(originalArguments));
            JSDynamicObject boundTargetFunction = JSFunction.getBoundTargetFunction(boundFunction);
            Object[] boundArguments = JSFunction.getBoundArguments(boundFunction);
            Object[] argumentValues = JSArguments.extractUserArguments(originalArguments, 1);
            Object[] arguments = BoundConstructNewTargetRootNode.prependBoundArguments(boundArguments, argumentValues);
            Object originalThis = JSArguments.getThisObject(originalArguments);
            Object newTarget = JSArguments.getNewTarget(originalArguments);
            if (newTarget == boundFunction) {
                newTarget = boundTargetFunction;
            }
            Object[] newArguments = JSArguments.createWithNewTarget(originalThis, boundTargetFunction, newTarget, arguments);
            return this.callNode.call(JSFunction.getFunctionData(boundTargetFunction).getConstructNewTarget(this.initProfile), newArguments);
        }
    }

    static final class BoundConstructRootNode
    extends BoundRootNode {
        BoundConstructRootNode(JSContext context) {
            super(context);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object[] originalArguments = frame.getArguments();
            JSDynamicObject boundFunction = BoundConstructRootNode.castBoundFunction(JSArguments.getFunctionObject(originalArguments));
            JSDynamicObject boundTargetFunction = JSFunction.getBoundTargetFunction(boundFunction);
            Object[] boundArguments = JSFunction.getBoundArguments(boundFunction);
            Object[] argumentValues = JSArguments.extractUserArguments(originalArguments);
            Object[] arguments = BoundConstructRootNode.prependBoundArguments(boundArguments, argumentValues);
            Object originalThis = JSArguments.getThisObject(originalArguments);
            Object[] newArguments = JSArguments.create(originalThis, boundTargetFunction, arguments);
            return this.callNode.call(JSFunction.getFunctionData(boundTargetFunction).getConstructTarget(this.initProfile), newArguments);
        }
    }

    static class BoundRootNode
    extends JavaScriptRootNode {
        private static final SourceSection SOURCE_SECTION = JSFunction.createBuiltinSourceSection("bound function");
        @Node.Child
        protected IndirectCallNode callNode;
        protected final BranchProfile initProfile = BranchProfile.create();

        BoundRootNode(JSContext context) {
            super(context.getLanguage(), SOURCE_SECTION, null);
            this.callNode = Truffle.getRuntime().createIndirectCallNode();
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object[] originalArguments = frame.getArguments();
            JSDynamicObject boundFunction = BoundRootNode.castBoundFunction(JSArguments.getFunctionObject(originalArguments));
            JSDynamicObject boundTargetFunction = JSFunction.getBoundTargetFunction(boundFunction);
            Object[] boundArguments = JSFunction.getBoundArguments(boundFunction);
            Object boundThis = JSFunction.getBoundThis(boundFunction);
            Object[] argumentValues = JSArguments.extractUserArguments(originalArguments);
            Object[] arguments = BoundRootNode.prependBoundArguments(boundArguments, argumentValues);
            Object[] newArguments = JSArguments.create(boundThis, boundTargetFunction, arguments);
            return this.callNode.call(JSFunction.getFunctionData(boundTargetFunction).getCallTarget(this.initProfile), newArguments);
        }

        protected static Object[] prependBoundArguments(Object[] boundArguments, Object[] argumentValues) {
            Object[] arguments = new Object[boundArguments.length + argumentValues.length];
            System.arraycopy(boundArguments, 0, arguments, 0, boundArguments.length);
            System.arraycopy(argumentValues, 0, arguments, boundArguments.length, argumentValues.length);
            return arguments;
        }

        protected static JSDynamicObject castBoundFunction(Object functionObj) {
            JSDynamicObject boundFunction = (JSDynamicObject)functionObj;
            if (!JSFunction.isBoundFunction(boundFunction)) {
                throw Errors.shouldNotReachHere();
            }
            return boundFunction;
        }
    }

    public static final class ClassPrototypeProxyProperty
    extends PropertyProxy {
        private ClassPrototypeProxyProperty() {
        }

        @Override
        public boolean set(JSDynamicObject store, Object value2) {
            assert (JSFunction.isJSFunction(store));
            JSFunction.setClassPrototype(store, value2);
            return true;
        }

        @Override
        public Object get(JSDynamicObject store) {
            assert (JSFunction.isJSFunction(store));
            return JSFunction.getClassPrototype(store);
        }
    }

    public static enum AsyncGeneratorState {
        SuspendedStart,
        SuspendedYield,
        Executing,
        AwaitingReturn,
        Completed;

    }

    public static enum GeneratorState {
        SuspendedStart,
        SuspendedYield,
        Executing,
        Completed;

    }

    public static final class FunctionNamePropertyProxy
    extends PropertyProxy {
        @Override
        public TruffleString get(JSDynamicObject store) {
            assert (JSFunction.isJSFunction(store));
            if (JSFunction.isBoundFunction(store)) {
                return ((JSFunctionObject.Bound)store).getBoundName();
            }
            return JSFunction.getName(store);
        }

        public static Object getProfiled(JSDynamicObject store, BranchProfile isBoundBranch) {
            assert (JSFunction.isJSFunction(store));
            if (JSFunction.isBoundFunction(store)) {
                isBoundBranch.enter();
                return ((JSFunctionObject.Bound)store).getBoundName();
            }
            return JSFunction.getName(store);
        }
    }

    public static final class FunctionLengthPropertyProxy
    extends PropertyProxy {
        @Override
        public Object get(JSDynamicObject store) {
            assert (JSFunction.isJSFunction(store));
            if (JSFunction.isBoundFunction(store)) {
                return ((JSFunctionObject.Bound)store).getBoundLength();
            }
            return JSFunction.getLength(store);
        }

        public static int getProfiled(JSDynamicObject store, BranchProfile isBoundBranch) {
            assert (JSFunction.isJSFunction(store));
            if (JSFunction.isBoundFunction(store)) {
                isBoundBranch.enter();
                return ((JSFunctionObject.Bound)store).getBoundLength();
            }
            return JSFunction.getLength(store);
        }
    }
}

