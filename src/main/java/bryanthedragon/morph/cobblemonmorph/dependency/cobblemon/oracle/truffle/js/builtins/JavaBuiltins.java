
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.JavaBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectArrayNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class JavaBuiltins
extends JSBuiltinsContainer.SwitchEnum<Java> {
    public static final TruffleString SYNCHRONIZED_WRAPPER_NAME = Strings.constant("synchronizedWrapper");
    public static final JSBuiltinsContainer BUILTINS = new JavaBuiltins();
    public static final JSBuiltinsContainer BUILTINS_NASHORN_COMPAT = new JavaNashornCompatBuiltins();

    protected JavaBuiltins() {
        super(JSRealm.JAVA_CLASS_NAME, Java.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Java builtinEnum) {
        switch (builtinEnum) {
            case type: {
                return JavaBuiltinsFactory.JavaTypeNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case typeName: {
                return JavaBuiltinsFactory.JavaTypeNameNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case from: {
                return JavaBuiltinsFactory.JavaFromNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case to: {
                return JavaBuiltinsFactory.JavaToNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case isType: {
                return JavaBuiltinsFactory.JavaIsTypeNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isJavaObject: {
                return JavaBuiltinsFactory.JavaIsJavaObjectNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case addToClasspath: {
                return JavaBuiltinsFactory.JavaAddToClasspathNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case extend: {
                if (JSConfig.SubstrateVM) break;
                return JavaBuiltinsFactory.JavaExtendNodeGen.create(context, builtin, JavaBuiltins.args().varArgs().createArgumentNodes(context));
            }
            case super_: {
                if (JSConfig.SubstrateVM) break;
                return JavaBuiltinsFactory.JavaSuperNodeGen.create(context, builtin, JavaBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    static abstract class JavaAddToClasspathNode
    extends JSBuiltinNode {
        JavaAddToClasspathNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object doString(TruffleString fileName) {
            TruffleLanguage.Env env = this.getRealm().getEnv();
            try {
                TruffleFile file = env.getPublicTruffleFile(Strings.toJavaString(fileName));
                env.addToHostClassPath(file);
            }
            catch (SecurityException e) {
                throw Errors.createErrorFromException(e);
            }
            return Undefined.instance;
        }

        @Specialization(replaces={"doString"})
        protected Object doObject(Object fileName, @Cached(value="create()") JSToStringNode toStringNode) {
            return this.doString(toStringNode.executeString(fileName));
        }
    }

    static abstract class JavaSynchronizedNode
    extends JSBuiltinNode {
        JavaSynchronizedNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object doSynchronize(Object func, Object lock) {
            if (!JSFunction.isJSFunction(func)) {
                throw Errors.createTypeErrorNotAFunction(func);
            }
            JSRealm realm = this.getRealm();
            if (lock != Undefined.instance) {
                JavaSynchronizedNode.unwrapAndCheckLockObject(lock, realm.getEnv());
            }
            JSFunctionData synchronizedFunctionData = this.createSynchronizedWrapper((JSFunctionObject)func);
            JSFunctionObject synchronizedFunction = JSFunction.create(realm, synchronizedFunctionData);
            if (lock != Undefined.instance) {
                return JSFunction.bind(realm, synchronizedFunction, lock, JSArguments.EMPTY_ARGUMENTS_ARRAY);
            }
            return synchronizedFunction;
        }

        @CompilerDirectives.TruffleBoundary
        private JSFunctionData createSynchronizedWrapper(final JSFunctionObject func) {
            RootCallTarget callTarget = new JavaScriptRootNode(this.getContext().getLanguage(), null, null){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public Object execute(VirtualFrame frame) {
                    Object thisObj = JSFrameUtil.getThisObj(frame);
                    Object lock = JavaSynchronizedNode.unwrapAndCheckLockObject(thisObj, this.getRealm().getEnv());
                    Object[] arguments = JSArguments.create(thisObj, func, JSArguments.extractUserArguments(frame.getArguments()));
                    Object object = lock;
                    synchronized (object) {
                        return JSFunction.call(arguments);
                    }
                }
            }.getCallTarget();
            return JSFunctionData.createCallOnly(this.getContext(), callTarget, 0, SYNCHRONIZED_WRAPPER_NAME);
        }

        static Object unwrapJavaObject(Object object, TruffleLanguage.Env env) {
            if (env.isHostObject(object)) {
                return env.asHostObject(object);
            }
            return object;
        }

        static Object unwrapAndCheckLockObject(Object thisObj, TruffleLanguage.Env env) {
            Object lock = JavaSynchronizedNode.unwrapJavaObject(thisObj, env);
            if (JSRuntime.isJSPrimitive(lock) || lock.getClass().isArray()) {
                CompilerDirectives.transferToInterpreter();
                throw Errors.createTypeError("Locking not supported on type: " + lock.getClass().getTypeName());
            }
            return lock;
        }
    }

    static abstract class JavaIsScriptObjectNode
    extends JSBuiltinNode {
        JavaIsScriptObjectNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean isScriptObject(Object obj) {
            return JSDynamicObject.isJSDynamicObject(obj);
        }
    }

    static abstract class JavaIsScriptFunctionNode
    extends JSBuiltinNode {
        JavaIsScriptFunctionNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean isScriptFunction(Object obj) {
            return JSFunction.isJSFunction(obj);
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class JavaIsJavaFunctionNode
    extends JSBuiltinNode {
        JavaIsJavaFunctionNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean isJavaFunction(Object obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            TruffleLanguage.Env env = this.getRealm().getEnv();
            return env.isHostFunction(obj) || env.isHostObject(obj) && interop.isMetaObject(obj);
        }
    }

    static abstract class JavaIsJavaMethodNode
    extends JSBuiltinNode {
        JavaIsJavaMethodNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean isJavaMethod(Object obj) {
            TruffleLanguage.Env env = this.getRealm().getEnv();
            return env.isHostFunction(obj);
        }
    }

    static abstract class JavaIsJavaObject
    extends JSBuiltinNode {
        JavaIsJavaObject(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected final boolean isJavaObject(Object obj) {
            TruffleLanguage.Env env = this.getRealm().getEnv();
            return env.isHostObject(obj) || env.isHostFunction(obj);
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class JavaIsTypeNode
    extends JSBuiltinNode {
        JavaIsTypeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected final boolean isType(Object obj) {
            TruffleLanguage.Env env = this.getRealm().getEnv();
            return env.isHostSymbol(obj);
        }
    }

    static abstract class JavaSuperNode
    extends JSBuiltinNode {
        JavaSuperNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected Object superAdapter(Object adapter2) {
            try {
                return InteropLibrary.getUncached().readMember(adapter2, "super");
            }
            catch (UnknownIdentifierException | UnsupportedMessageException e) {
                return Undefined.instance;
            }
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class JavaToNode
    extends JSBuiltinNode {
        @Node.Child
        private JSToObjectArrayNode toObjectArrayNode;
        @Node.Child
        private ExportValueNode exportValue;
        @Node.Child
        private InteropLibrary newArray;
        @Node.Child
        private InteropLibrary arrayElements;
        @Node.Child
        private JSToStringNode toStringNode;

        JavaToNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.toObjectArrayNode = JSToObjectArrayNode.create(context);
            this.exportValue = ExportValueNode.create();
            this.newArray = InteropLibrary.getFactory().createDispatched(5);
            this.arrayElements = InteropLibrary.getFactory().createDispatched(5);
        }

        private TruffleString toString(Object target) {
            if (this.toStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toStringNode = this.insert(JSToStringNode.create());
            }
            return this.toStringNode.executeString(target);
        }

        @Specialization(guards={"isJSObject(jsObj)"})
        protected Object to(Object jsObj, Object toType, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object javaType;
            TruffleLanguage.Env env = this.getRealm().getEnv();
            boolean knownArrayClass = false;
            if (env.isHostObject(toType)) {
                javaType = toType;
            } else if (toType == Undefined.instance) {
                javaType = env.isHostLookupAllowed() ? env.lookupHostSymbol("java.lang.Object[]") : env.asGuestValue(Object[].class);
                knownArrayClass = true;
            } else {
                TruffleString className = this.toString(toType);
                javaType = JavaTypeNode.lookupJavaType(className, env);
                if (javaType == null) {
                    throw Errors.createTypeErrorClassNotFound(className);
                }
            }
            if (knownArrayClass || JavaToNode.isJavaArrayClass(javaType, env, interop)) {
                return this.toArray(jsObj, javaType);
            }
            throw Errors.createTypeErrorFormat("Unsupported type: %s", this.toString(javaType));
        }

        @Specialization(guards={"!isJSObject(obj)"}, limit="InteropLibraryLimit")
        protected Object toNonObject(Object obj, Object toType, @CachedLibrary(value="obj") InteropLibrary objInterop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary typeInterop) {
            if (objInterop.hasArrayElements(obj)) {
                return this.to(obj, toType, typeInterop);
            }
            throw Errors.createTypeErrorNotAnObject(obj);
        }

        private static boolean isJavaArrayClass(Object type, TruffleLanguage.Env env, InteropLibrary interop) {
            try {
                return env.isHostObject(type) && interop.isMetaObject(type) && interop.asString(interop.getMetaQualifiedName(type)).endsWith("[]");
            }
            catch (UnsupportedMessageException e) {
                assert (false) : e;
                return false;
            }
        }

        private Object toArray(Object jsObj, Object arrayType) {
            Object[] arr = this.toObjectArrayNode.executeObjectArray(jsObj);
            try {
                Object result = this.newArray.instantiate(arrayType, arr.length);
                for (int i = 0; i < arr.length; ++i) {
                    this.arrayElements.writeArrayElement(result, i, this.exportValue.execute(arr[i]));
                }
                return result;
            }
            catch (ArityException | InvalidArrayIndexException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeError(e, (Node)this);
            }
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class JavaFromNode
    extends JSBuiltinNode {
        JavaFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject from(Object javaArray, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached ImportValueNode importValueNode, @Cached(value="createCachedInterop()") WriteElementNode writeNode, @Cached BranchProfile errorBranch) {
            JSRealm realm = this.getRealm();
            TruffleLanguage.Env env = realm.getEnv();
            if (env.isHostObject(javaArray)) {
                try {
                    long size = interop.getArraySize(javaArray);
                    if (size < 0L || size >= Integer.MAX_VALUE) {
                        throw Errors.createRangeErrorInvalidArrayLength();
                    }
                    JSArrayObject jsArray = JSArray.createEmptyChecked(this.getContext(), realm, size);
                    int i = 0;
                    while ((long)i < size) {
                        Object element = importValueNode.executeWithTarget(interop.readArrayElement(javaArray, i));
                        writeNode.executeWithTargetAndIndexAndValue((Object)jsArray, i, element);
                        ++i;
                    }
                    return jsArray;
                }
                catch (InvalidArrayIndexException | UnsupportedMessageException interopException) {
                    // empty catch block
                }
            }
            errorBranch.enter();
            throw Errors.createTypeError("Cannot convert to JavaScript array.");
        }
    }

    static abstract class JavaExtendNode
    extends JSBuiltinNode {
        private final BranchProfile errorBranch = BranchProfile.create();

        JavaExtendNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
        protected Object extend(Object[] arguments) {
            int typesLength;
            Object classOverrides;
            if (JSConfig.SubstrateVM) {
                throw Errors.unsupported("JavaAdapter");
            }
            if (arguments.length == 0) {
                this.errorBranch.enter();
                throw Errors.createTypeError("Java.extend needs at least one argument.");
            }
            if (JSRuntime.isObject(arguments[arguments.length - 1])) {
                classOverrides = arguments[arguments.length - 1];
                typesLength = arguments.length - 1;
                if (typesLength == 0) {
                    this.errorBranch.enter();
                    throw Errors.createTypeError("Java.extend needs at least one type argument.");
                }
            } else {
                classOverrides = null;
                typesLength = arguments.length;
            }
            TruffleLanguage.Env env = this.getRealm().getEnv();
            Object[] types = new Object[typesLength];
            for (int i = 0; i < typesLength; ++i) {
                if (!JavaExtendNode.isType(arguments[i], env)) {
                    this.errorBranch.enter();
                    throw Errors.createTypeError("Java.extend needs Java types as its arguments.");
                }
                types[i] = arguments[i];
            }
            try {
                if (classOverrides != null) {
                    return env.createHostAdapterWithClassOverrides(types, classOverrides);
                }
                return env.createHostAdapter(types);
            }
            catch (Exception ex) {
                throw Errors.createTypeError(ex.getMessage(), ex, this);
            }
        }

        protected static boolean isType(Object obj, TruffleLanguage.Env env) {
            return env.isHostObject(obj) && (env.isHostSymbol(obj) || InteropLibrary.getUncached().isMetaObject(obj));
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class JavaTypeNameNode
    extends JSBuiltinNode {
        JavaTypeNameNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJavaInteropClass(type, typeInterop)"})
        protected TruffleString typeNameJavaInteropClass(Object type, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary typeInterop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary stringInterop) {
            try {
                return stringInterop.asTruffleString(typeInterop.getMetaQualifiedName(type));
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(type, e, "Java.typeName", this);
            }
        }

        @Fallback
        protected Object nonType(Object value2) {
            return Undefined.instance;
        }

        protected final boolean isJavaInteropClass(Object obj, InteropLibrary typeInterop) {
            TruffleLanguage.Env env = this.getRealm().getEnv();
            return env.isHostObject(obj) && typeInterop.isMetaObject(obj);
        }
    }

    static abstract class JavaTypeNode
    extends JSBuiltinNode {
        JavaTypeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected Object type(TruffleString name) {
            TruffleLanguage.Env env = this.getRealm().getEnv();
            Object javaType = JavaTypeNode.lookupJavaType(name, env);
            if (javaType == null) {
                throw Errors.createTypeErrorClassNotFound(name);
            }
            return javaType;
        }

        @Specialization(guards={"!isString(obj)"})
        protected Object typeNoString(Object obj) {
            throw Errors.createTypeError("Java.type expects one string argument");
        }

        @CompilerDirectives.TruffleBoundary
        static Object lookupJavaType(TruffleString name, TruffleLanguage.Env env) {
            if (env != null && env.isHostLookupAllowed()) {
                try {
                    Object found = env.lookupHostSymbol(Strings.toJavaString(name));
                    if (found != null) {
                        return found;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return JavaTypeNode.lookForSubclasses(Strings.toJavaString(name), env);
            }
            throw Errors.createTypeError("Java Interop is not available");
        }

        private static Object lookForSubclasses(String className, TruffleLanguage.Env env) {
            StringBuilder nextName = new StringBuilder(className);
            int lastDot = nextName.length();
            while ((lastDot = nextName.lastIndexOf(".", lastDot - 1)) != -1) {
                nextName.setCharAt(lastDot, '$');
                try {
                    String innerClassName = nextName.toString();
                    Object found = env.lookupHostSymbol(innerClassName);
                    if (found == null) continue;
                    return found;
                }
                catch (Exception exception) {
                    continue;
                }
                break;
            }
            return null;
        }
    }

    public static final class JavaNashornCompatBuiltins
    extends JSBuiltinsContainer.SwitchEnum<JavaNashornCompat> {
        protected JavaNashornCompatBuiltins() {
            super(JSRealm.JAVA_CLASS_NAME_NASHORN_COMPAT, JavaNashornCompat.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, JavaNashornCompat builtinEnum) {
            switch (builtinEnum) {
                case isJavaMethod: {
                    return JavaBuiltinsFactory.JavaIsJavaMethodNodeGen.create(context, builtin, JavaNashornCompatBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case isJavaFunction: {
                    return JavaBuiltinsFactory.JavaIsJavaFunctionNodeGen.create(context, builtin, JavaNashornCompatBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case isScriptFunction: {
                    return JavaBuiltinsFactory.JavaIsScriptFunctionNodeGen.create(context, builtin, JavaNashornCompatBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case isScriptObject: {
                    return JavaBuiltinsFactory.JavaIsScriptObjectNodeGen.create(context, builtin, JavaNashornCompatBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case synchronized_: {
                    if (JSConfig.SubstrateVM) break;
                    return JavaBuiltinsFactory.JavaSynchronizedNodeGen.create(context, builtin, JavaNashornCompatBuiltins.args().fixedArgs(2).createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum JavaNashornCompat implements BuiltinEnum<JavaNashornCompat>
        {
            isJavaMethod(1),
            isJavaFunction(1),
            isScriptFunction(1),
            isScriptObject(1),
            synchronized_(2){

                @Override
                public boolean isAOTSupported() {
                    return false;
                }
            };

            private final int length;

            private JavaNashornCompat(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static enum Java implements BuiltinEnum<Java>
    {
        type(1),
        from(1),
        to(2),
        isJavaObject(1),
        isType(1),
        typeName(1),
        addToClasspath(1),
        extend(1){

            @Override
            public boolean isAOTSupported() {
                return false;
            }
        }
        ,
        super_(1){

            @Override
            public boolean isAOTSupported() {
                return false;
            }
        };

        private final int length;

        private Java(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

