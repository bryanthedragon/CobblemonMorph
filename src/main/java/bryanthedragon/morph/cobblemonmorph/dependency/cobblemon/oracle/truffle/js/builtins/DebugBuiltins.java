
package com.oracle.truffle.js.builtins;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.DebugBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.helper.GCNodeGen;
import com.oracle.truffle.js.builtins.helper.HeapDump;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Evaluator;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleLoader;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DebugCounter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DebugBuiltins
extends JSBuiltinsContainer.SwitchEnum<Debug> {
    public static final TruffleString NOT_AN_ARRAY = Strings.constant("NOT_AN_ARRAY");
    public static final TruffleString NOT_AN_OBJECT = Strings.constant("not_an_object");
    public static final TruffleString ASYNC_GENERATOR = Strings.constant("Async Generator");
    public static final TruffleString GENERATOR = Strings.constant("Generator");
    public static final TruffleString SPC_ITERATOR = Strings.constant(" Iterator");
    public static final JSBuiltinsContainer BUILTINS = new DebugBuiltins();

    protected DebugBuiltins() {
        super(JSRealm.DEBUG_CLASS_NAME, Debug.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Debug builtinEnum) {
        switch (builtinEnum) {
            case class_: {
                return DebugBuiltinsFactory.DebugClassNodeGen.create(context, builtin, true, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case getClass: {
                return DebugBuiltinsFactory.DebugClassNodeGen.create(context, builtin, false, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case className: {
                return DebugBuiltinsFactory.DebugClassNameNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case shape: {
                return DebugBuiltinsFactory.DebugShapeNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case dumpCounters: {
                return DebugBuiltinsFactory.DebugDumpCountersNodeGen.create(context, builtin, DebugBuiltins.args().createArgumentNodes(context));
            }
            case dumpFunctionTree: {
                return DebugBuiltinsFactory.DebugDumpFunctionTreeNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case printObject: {
                return DebugBuiltinsFactory.DebugPrintObjectNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case toJavaString: {
                return DebugBuiltinsFactory.DebugToJavaStringNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case srcattr: {
                return DebugBuiltinsFactory.DebugPrintSourceAttributionNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case arraytype: {
                return DebugBuiltinsFactory.DebugArrayTypeNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case assertInt: {
                return DebugBuiltinsFactory.DebugAssertIntNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case continueInInterpreter: {
                return DebugBuiltinsFactory.DebugContinueInInterpreterNodeGen.create(context, builtin, false, DebugBuiltins.args().createArgumentNodes(context));
            }
            case stringCompare: {
                return DebugBuiltinsFactory.DebugStringCompareNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case isHolesArray: {
                return DebugBuiltinsFactory.DebugIsHolesArrayNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case jsStack: {
                return DebugBuiltinsFactory.DebugJSStackNodeGen.create(context, builtin, DebugBuiltins.args().createArgumentNodes(context));
            }
            case loadModule: {
                return DebugBuiltinsFactory.DebugLoadModuleNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case systemGC: {
                return GCNodeGen.create(context, builtin, DebugBuiltins.args().createArgumentNodes(context));
            }
            case systemProperty: {
                return DebugBuiltinsFactory.DebugSystemPropertyNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case systemProperties: {
                return DebugBuiltinsFactory.DebugSystemPropertiesNodeGen.create(context, builtin, DebugBuiltins.args().createArgumentNodes(context));
            }
            case neverPartOfCompilation: {
                return DebugBuiltinsFactory.DebugNeverPartOfCompilationNodeGen.create(context, builtin, DebugBuiltins.args().createArgumentNodes(context));
            }
            case typedArrayDetachBuffer: {
                return DebugBuiltinsFactory.DebugTypedArrayDetachBufferNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case createSafeInteger: {
                return DebugBuiltinsFactory.DebugCreateSafeIntegerNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case dumpHeap: {
                return DebugBuiltinsFactory.DebugHeapDumpNodeGen.create(context, builtin, DebugBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class DebugNeverPartOfCompilationNode
    extends JSBuiltinNode
    implements JSBuiltinNode.Inlineable,
    JSBuiltinNode.Inlined {
        public DebugNeverPartOfCompilationNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static Object neverPartOfCompilation() {
            if (!CompilerDirectives.inCompilationRoot()) {
                DebugNeverPartOfCompilationNode.fail();
            }
            return Undefined.instance;
        }

        protected static void fail() {
            CompilerDirectives.bailout("Debug.neverPartOfCompilation()");
        }

        @Override
        public Object callInlined(Object[] arguments) {
            DebugNeverPartOfCompilationNode.fail();
            return Undefined.instance;
        }

        @Override
        public JSBuiltinNode.Inlined createInlined() {
            return DebugBuiltinsFactory.DebugNeverPartOfCompilationNodeGen.create(this.getContext(), this.getBuiltin(), this.getArguments());
        }
    }

    public static abstract class DebugCreateSafeInteger
    extends JSBuiltinNode {
        public DebugCreateSafeInteger(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static SafeInteger createSafeInteger(int a) {
            return SafeInteger.valueOf(a);
        }

        @Specialization
        protected static SafeInteger createSafeInteger(SafeInteger a) {
            return a;
        }

        @Specialization
        protected static SafeInteger createSafeInteger(Object a) {
            long integer = JSRuntime.toInteger(a);
            integer = Math.max(integer, JSRuntime.MIN_SAFE_INTEGER_LONG);
            integer = Math.min(integer, JSRuntime.MAX_SAFE_INTEGER_LONG);
            return SafeInteger.valueOf(integer);
        }
    }

    public static abstract class DebugTypedArrayDetachBufferNode
    extends JSBuiltinNode {
        public DebugTypedArrayDetachBufferNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected static Object detachBuffer(Object obj) {
            if (!(JSArrayBuffer.isJSHeapArrayBuffer(obj) || JSArrayBuffer.isJSDirectArrayBuffer(obj) || JSArrayBuffer.isJSInteropArrayBuffer(obj))) {
                throw Errors.createTypeError("ArrayBuffer expected");
            }
            JSArrayBuffer.detachArrayBuffer((JSDynamicObject)obj);
            return Undefined.instance;
        }
    }

    public static abstract class DebugSystemProperty
    extends JSBuiltinNode {
        public DebugSystemProperty(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected static Object systemProperty(Object name) {
            String key = JSRuntime.toJavaString(name);
            String value2 = System.getProperty(key);
            return value2 == null ? Undefined.instance : Strings.fromJavaString(value2);
        }
    }

    public static abstract class DebugSystemProperties
    extends JSBuiltinNode {
        public DebugSystemProperties(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object systemProperties() {
            JSObject result = JSOrdinary.create(this.getContext(), this.getRealm());
            for (Map.Entry entry : System.getProperties().entrySet()) {
                Object key = entry.getKey();
                Object value2 = entry.getValue();
                if (!(key instanceof String) || !(value2 instanceof String)) continue;
                JSObject.set((JSDynamicObject)result, Strings.fromJavaString((String)key), (Object)Strings.fromJavaString((String)value2));
            }
            return result;
        }
    }

    public static abstract class DebugLoadModuleNode
    extends JSBuiltinNode {
        public DebugLoadModuleNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected TruffleString loadModule(Object nameObj, Object modulesSourceMapObj) {
            final TruffleString name = JSRuntime.toString(nameObj);
            final JSDynamicObject modulesSourceMap = (JSDynamicObject)modulesSourceMapObj;
            final Evaluator evaluator = this.getContext().getEvaluator();
            JSModuleLoader moduleLoader = new JSModuleLoader(){
                private final Map<TruffleString, JSModuleRecord> moduleMap = new HashMap<TruffleString, JSModuleRecord>();

                private Source resolveModuleSource(ScriptOrModule referencingModule, TruffleString specifier) {
                    Object moduleEntry = JSObject.get(modulesSourceMap, specifier);
                    if (moduleEntry == Undefined.instance) {
                        throw Errors.createSyntaxError(String.format("Could not find imported module %s", specifier));
                    }
                    String code = JSRuntime.toJavaString(moduleEntry);
                    return Source.newBuilder("js", code, Strings.toJavaString(name)).mimeType("application/javascript+module").build();
                }

                @Override
                public JSModuleRecord resolveImportedModule(ScriptOrModule referencingModule, Module.ModuleRequest moduleRequest) {
                    return this.moduleMap.computeIfAbsent(moduleRequest.getSpecifier(), key -> new JSModuleRecord(evaluator.envParseModule(JSRealm.get(null), this.resolveModuleSource(referencingModule, (TruffleString)key)), this));
                }

                @Override
                public JSModuleRecord loadModule(Source moduleSource, JSModuleData moduleData) {
                    throw new UnsupportedOperationException();
                }
            };
            JSModuleRecord module = moduleLoader.resolveImportedModule(null, Module.ModuleRequest.create(name));
            JSRealm realm = this.getRealm();
            evaluator.moduleLinking(realm, module);
            evaluator.moduleEvaluation(realm, module);
            return Strings.fromJavaString(String.valueOf(module));
        }
    }

    public static abstract class DebugJSStackNode
    extends JSBuiltinNode {
        public DebugJSStackNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object printJSStack() {
            JSException.printJSStackTrace(this.getParent());
            return Undefined.instance;
        }
    }

    public static abstract class DebugIsHolesArrayNode
    extends JSBuiltinNode {
        @Node.Child
        private JSToObjectNode toObjectNode = JSToObjectNode.createToObject(this.getContext());
        private final ValueProfile arrayType = ValueProfile.createClassProfile();
        private final ConditionProfile isArray = ConditionProfile.createBinaryProfile();

        public DebugIsHolesArrayNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        public abstract boolean executeBoolean(Object var1);

        @Specialization
        protected boolean isHolesArray(Object arr) {
            Object obj = this.toObjectNode.execute(arr);
            if (this.isArray.profile(JSArray.isJSArray(obj))) {
                JSDynamicObject dynObj = (JSDynamicObject)obj;
                return this.arrayType.profile(JSObject.getArray(dynObj)).hasHoles(dynObj);
            }
            return false;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return DebugBuiltinsFactory.DebugIsHolesArrayNodeGen.create(this.getContext(), this.getBuiltin(), DebugIsHolesArrayNode.cloneUninitialized(this.getArguments(), materializedTags));
        }
    }

    public static abstract class DebugStringCompareNode
    extends JSBuiltinNode {
        public DebugStringCompareNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int stringCompare(Object a, Object b, @Cached TruffleString.CompareCharsUTF16Node compareNode) {
            TruffleString str2;
            TruffleString str1 = JSRuntime.toString(a);
            int result = Strings.compareTo(compareNode, str1, str2 = JSRuntime.toString(b));
            if (result == 0) {
                return 0;
            }
            if (result < 0) {
                return -1;
            }
            return 1;
        }
    }

    public static abstract class DebugHeapDumpNode
    extends JSBuiltinNode {
        public DebugHeapDumpNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected TruffleString heapDump(Object fileName0, Object live0) {
            String fileName = fileName0 == Undefined.instance ? HeapDump.defaultDumpName() : Strings.toJavaString(JSRuntime.toString(fileName0));
            boolean live = live0 == Undefined.instance || JSRuntime.toBoolean(live0);
            try {
                HeapDump.dump(fileName, live);
            }
            catch (IOException e) {
                throw JSException.create(JSErrorType.Error, e.getMessage(), e, this);
            }
            catch (IllegalArgumentException e) {
                throw JSException.create(JSErrorType.Error, this.getBuiltin().getFullName() + " unsupported", e, this);
            }
            return Strings.fromJavaString(fileName);
        }
    }

    public static abstract class DebugAssertIntNode
    extends JSBuiltinNode {
        public DebugAssertIntNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object assertInt(Object value2, Object message) {
            if (!(value2 instanceof Integer)) {
                throw Errors.createTypeError("assert: expected integer here, got " + value2.getClass().getSimpleName() + ", message: " + JSRuntime.toString(message));
            }
            return Undefined.instance;
        }
    }

    public static abstract class DebugArrayTypeNode
    extends JSBuiltinNode {
        public DebugArrayTypeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected TruffleString arraytype(Object array) {
            if (!JSDynamicObject.isJSDynamicObject(array) || !JSObject.hasArray(array)) {
                return NOT_AN_ARRAY;
            }
            return Strings.fromJavaString(JSObject.getArray((JSDynamicObject)array).getClass().getSimpleName());
        }
    }

    public static abstract class DebugPrintSourceAttribution
    extends JSBuiltinNode {
        public DebugPrintSourceAttribution(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object printSourceAttribution(JSFunctionObject function) {
            CallTarget callTarget = JSFunction.getCallTarget(function);
            if (callTarget instanceof RootCallTarget) {
                return Strings.fromJavaString(NodeUtil.printSourceAttributionTree(((RootCallTarget)callTarget).getRootNode()));
            }
            return Undefined.instance;
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object printSourceAttribution(TruffleString code) {
            ScriptNode scriptNode = this.getContext().getEvaluator().evalCompile(this.getContext(), Strings.toJavaString(code), "<eval>");
            return Strings.fromJavaString(NodeUtil.printSourceAttributionTree(scriptNode.getRootNode()));
        }

        @CompilerDirectives.TruffleBoundary
        @Fallback
        protected Object illegalArgument(Object unused) {
            throw Errors.createTypeError("argument must be a function or a string");
        }
    }

    public static abstract class DebugToJavaStringNode
    extends JSBuiltinNode {
        public DebugToJavaStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected static Object toJavaString(Object thing) {
            return Strings.fromJavaString(String.valueOf(thing));
        }
    }

    public static abstract class DebugPrintObjectNode
    extends JSBuiltinNode {
        public DebugPrintObjectNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object printObject(JSDynamicObject object, Object level0) {
            int level = level0 == Undefined.instance ? 1 : JSRuntime.toInt32(level0);
            this.getRealm().getOutputWriter().println(this.debugPrint(object, 0, level));
            return Undefined.instance;
        }

        @CompilerDirectives.TruffleBoundary
        protected TruffleString debugPrint(JSDynamicObject object, int level, int levelStop) {
            List<TruffleString> properties2 = JSObject.enumerableOwnNames(object);
            TruffleStringBuilder sb = Strings.builderCreate(properties2.size() * 10);
            Strings.builderAppend(sb, "{\n");
            for (TruffleString key : properties2) {
                DebugPrintObjectNode.indent(sb, level + 1);
                PropertyDescriptor desc = JSObject.getOwnProperty(object, key);
                Strings.builderAppend(sb, key);
                if (desc.isDataDescriptor()) {
                    Object value2 = JSObject.get(object, key);
                    if (JSDynamicObject.isJSDynamicObject(value2)) {
                        value2 = (JSGuards.isJSOrdinaryObject(value2) || JSGlobal.isJSGlobalObject(value2)) && !key.equals(JSObject.CONSTRUCTOR) ? (level < levelStop && !key.equals(JSObject.CONSTRUCTOR) ? this.debugPrint((JSDynamicObject)value2, level + 1, levelStop) : Strings.EMPTY_OBJECT_DOTS) : JSObject.getJSClass((JSDynamicObject)value2);
                    }
                    Strings.builderAppend(sb, Strings.COLON_SPACE);
                    Strings.builderAppend(sb, Strings.fromObject(value2));
                }
                if (!key.equals(properties2.get(properties2.size() - 1))) {
                    Strings.builderAppend(sb, ',');
                }
                Strings.builderAppend(sb, '\n');
            }
            DebugPrintObjectNode.indent(sb, level);
            Strings.builderAppend(sb, '}');
            return Strings.builderToString(sb);
        }

        private static void indent(TruffleStringBuilder sb, int level) {
            for (int i = 0; i < level; ++i) {
                Strings.builderAppend(sb, ' ');
            }
        }
    }

    public static abstract class DebugDumpFunctionTreeNode
    extends JSBuiltinNode {
        public DebugDumpFunctionTreeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object dumpFunctionTree(Object functionObj) {
            if (JSFunction.isJSFunction(functionObj)) {
                CallTarget target = JSFunction.getCallTarget((JSFunctionObject)functionObj);
                if (target instanceof RootCallTarget) {
                    NodeUtil.printTree(this.getRealm().getOutputWriter(), (Node)((RootCallTarget)target).getRootNode());
                    return Undefined.instance;
                }
                throw Errors.shouldNotReachHere();
            }
            return Undefined.instance;
        }
    }

    public static abstract class DebugDumpCountersNode
    extends JSBuiltinNode {
        public DebugDumpCountersNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected static Object dumpCounters() {
            com.oracle.truffle.object.DebugCounter.dumpCounters();
            DebugCounter.dumpCounters();
            return Undefined.instance;
        }
    }

    public static abstract class DebugShapeNode
    extends JSBuiltinNode {
        public DebugShapeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected static Object shape(Object obj) {
            if (obj instanceof JSDynamicObject) {
                return Strings.fromJavaString(((JSDynamicObject)obj).getShape().toString());
            }
            return Undefined.instance;
        }
    }

    public static abstract class DebugClassNameNode
    extends JSBuiltinNode {
        public DebugClassNameNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected static Object clazz(Object obj) {
            if (obj instanceof Symbol) {
                return Null.instance;
            }
            if (JSDynamicObject.isJSDynamicObject(obj)) {
                JSDynamicObject jsObj = (JSDynamicObject)obj;
                if (JSObjectUtil.hasHiddenProperty(jsObj, JSRuntime.ITERATED_OBJECT_ID)) {
                    JSDynamicObject iteratedObj = (JSDynamicObject)JSObjectUtil.getHiddenProperty(jsObj, JSRuntime.ITERATED_OBJECT_ID);
                    return Strings.concat(JSObject.getClassName(iteratedObj), SPC_ITERATOR);
                }
                if (JSObjectUtil.hasHiddenProperty(jsObj, JSFunction.GENERATOR_STATE_ID)) {
                    return GENERATOR;
                }
                if (JSObjectUtil.hasHiddenProperty(jsObj, JSFunction.ASYNC_GENERATOR_STATE_ID)) {
                    return ASYNC_GENERATOR;
                }
                if (JSProxy.isJSProxy(jsObj)) {
                    return DebugClassNameNode.clazz(JSProxy.getTarget(jsObj));
                }
                return JSObject.getClassName(jsObj);
            }
            return NOT_AN_OBJECT;
        }
    }

    public static abstract class DebugClassNode
    extends JSBuiltinNode {
        private final boolean getName;

        public DebugClassNode(JSContext context, JSBuiltin builtin, boolean getName) {
            super(context, builtin);
            this.getName = getName;
        }

        @Specialization
        protected Object clazz(Object obj) {
            if (obj == null) {
                return Null.instance;
            }
            if (this.getName) {
                return Strings.fromJavaString(obj.getClass().getName());
            }
            return this.getRealm().getEnv().asGuestValue(obj.getClass());
        }
    }

    public static abstract class DebugContinueInInterpreter
    extends JSBuiltinNode {
        private final boolean invalidate;

        public DebugContinueInInterpreter(JSContext context, JSBuiltin builtin, boolean invalidate) {
            super(context, builtin);
            this.invalidate = invalidate;
        }

        @Specialization
        protected Object continueInInterpreter() {
            if (this.invalidate) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
            } else {
                CompilerDirectives.transferToInterpreter();
            }
            return Undefined.instance;
        }
    }

    public static enum Debug implements BuiltinEnum<Debug>
    {
        class_(1),
        getClass(1),
        className(1),
        shape(1),
        dumpCounters(0),
        dumpFunctionTree(1),
        printObject(1),
        toJavaString(1),
        srcattr(1),
        arraytype(1),
        assertInt(2),
        continueInInterpreter(0),
        stringCompare(2),
        isHolesArray(1),
        jsStack(0),
        loadModule(2),
        createSafeInteger(1),
        typedArrayDetachBuffer(1),
        systemGC(0),
        systemProperty(1),
        systemProperties(0),
        neverPartOfCompilation(0),
        dumpHeap(2);

        private final int length;

        private Debug(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

