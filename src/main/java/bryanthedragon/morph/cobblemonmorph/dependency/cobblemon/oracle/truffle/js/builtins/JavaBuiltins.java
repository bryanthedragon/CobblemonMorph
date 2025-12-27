package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
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
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class JavaBuiltins extends JSBuiltinsContainer.SwitchEnum<JavaBuiltins.Java> {
   public static final TruffleString SYNCHRONIZED_WRAPPER_NAME = Strings.constant("synchronizedWrapper");
   public static final JSBuiltinsContainer BUILTINS = new JavaBuiltins();
   public static final JSBuiltinsContainer BUILTINS_NASHORN_COMPAT = new JavaBuiltins.JavaNashornCompatBuiltins();

   protected JavaBuiltins() {
      super(JSRealm.JAVA_CLASS_NAME, JavaBuiltins.Java.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, JavaBuiltins.Java builtinEnum) {
      switch (builtinEnum) {
         case type:
            return JavaBuiltinsFactory.JavaTypeNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case typeName:
            return JavaBuiltinsFactory.JavaTypeNameNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case from:
            return JavaBuiltinsFactory.JavaFromNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case to:
            return JavaBuiltinsFactory.JavaToNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case isType:
            return JavaBuiltinsFactory.JavaIsTypeNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case isJavaObject:
            return JavaBuiltinsFactory.JavaIsJavaObjectNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case addToClasspath:
            return JavaBuiltinsFactory.JavaAddToClasspathNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case extend:
            if (!JSConfig.SubstrateVM) {
               return JavaBuiltinsFactory.JavaExtendNodeGen.create(context, builtin, args().varArgs().createArgumentNodes(context));
            }
            break;
         case super_:
            if (!JSConfig.SubstrateVM) {
               return JavaBuiltinsFactory.JavaSuperNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
            }
      }

      return null;
   }

   public static enum Java implements BuiltinEnum<JavaBuiltins.Java> {
      type(1),
      from(1),
      to(2),
      isJavaObject(1),
      isType(1),
      typeName(1),
      addToClasspath(1),
      extend(1) {
         @Override
         public boolean isAOTSupported() {
            return false;
         }
      },
      super_(1) {
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

   abstract static class JavaAddToClasspathNode extends JSBuiltinNode {
      JavaAddToClasspathNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object doString(TruffleString fileName) {
         TruffleLanguage.Env env = this.getRealm().getEnv();

         try {
            TruffleFile file = env.getPublicTruffleFile(Strings.toJavaString(fileName));
            env.addToHostClassPath(file);
         } catch (SecurityException var4) {
            throw Errors.createErrorFromException(var4);
         }

         return Undefined.instance;
      }

      @Specialization(replaces = "doString")
      protected Object doObject(Object fileName, @Cached("create()") JSToStringNode toStringNode) {
         return this.doString(toStringNode.executeString(fileName));
      }
   }

   abstract static class JavaExtendNode extends JSBuiltinNode {
      private final BranchProfile errorBranch = BranchProfile.create();

      JavaExtendNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
      protected Object extend(Object[] arguments) {
         if (JSConfig.SubstrateVM) {
            throw Errors.unsupported("JavaAdapter");
         } else if (arguments.length == 0) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Java.extend needs at least one argument.");
         } else {
            int typesLength;
            Object classOverrides;
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

            for (int i = 0; i < typesLength; i++) {
               if (!isType(arguments[i], env)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("Java.extend needs Java types as its arguments.");
               }

               types[i] = arguments[i];
            }

            try {
               return classOverrides != null ? env.createHostAdapterWithClassOverrides(types, classOverrides) : env.createHostAdapter(types);
            } catch (Exception var7) {
               throw Errors.createTypeError(var7.getMessage(), var7, this);
            }
         }
      }

      protected static boolean isType(Object obj, TruffleLanguage.Env env) {
         return env.isHostObject(obj) && (env.isHostSymbol(obj) || InteropLibrary.getUncached().isMetaObject(obj));
      }
   }

   @ImportStatic(JSConfig.class)
   abstract static class JavaFromNode extends JSBuiltinNode {
      JavaFromNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject from(
         Object javaArray,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop,
         @Cached ImportValueNode importValueNode,
         @Cached("createCachedInterop()") WriteElementNode writeNode,
         @Cached BranchProfile errorBranch
      ) {
         JSRealm realm = this.getRealm();
         TruffleLanguage.Env env = realm.getEnv();
         if (env.isHostObject(javaArray)) {
            try {
               long size = interop.getArraySize(javaArray);
               if (size >= 0L && size < 2147483647L) {
                  JSDynamicObject jsArray = JSArray.createEmptyChecked(this.getContext(), realm, size);

                  for (int i = 0; i < size; i++) {
                     Object element = importValueNode.executeWithTarget(interop.readArrayElement(javaArray, i));
                     writeNode.executeWithTargetAndIndexAndValue(jsArray, i, element);
                  }

                  return jsArray;
               }

               throw Errors.createRangeErrorInvalidArrayLength();
            } catch (InvalidArrayIndexException | UnsupportedMessageException var13) {
            }
         }

         errorBranch.enter();
         throw Errors.createTypeError("Cannot convert to JavaScript array.");
      }
   }

   @ImportStatic(JSConfig.class)
   abstract static class JavaIsJavaFunctionNode extends JSBuiltinNode {
      JavaIsJavaFunctionNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean isJavaFunction(Object obj, @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         return env.isHostFunction(obj) || env.isHostObject(obj) && interop.isMetaObject(obj);
      }
   }

   abstract static class JavaIsJavaMethodNode extends JSBuiltinNode {
      JavaIsJavaMethodNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean isJavaMethod(Object obj) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         return env.isHostFunction(obj);
      }
   }

   abstract static class JavaIsJavaObject extends JSBuiltinNode {
      JavaIsJavaObject(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected final boolean isJavaObject(Object obj) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         return env.isHostObject(obj) || env.isHostFunction(obj);
      }
   }

   abstract static class JavaIsScriptFunctionNode extends JSBuiltinNode {
      JavaIsScriptFunctionNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected static boolean isScriptFunction(Object obj) {
         return JSFunction.isJSFunction(obj);
      }
   }

   abstract static class JavaIsScriptObjectNode extends JSBuiltinNode {
      JavaIsScriptObjectNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected static boolean isScriptObject(Object obj) {
         return JSDynamicObject.isJSDynamicObject(obj);
      }
   }

   @ImportStatic(JSConfig.class)
   abstract static class JavaIsTypeNode extends JSBuiltinNode {
      JavaIsTypeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected final boolean isType(Object obj) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         return env.isHostSymbol(obj);
      }
   }

   public static final class JavaNashornCompatBuiltins extends JSBuiltinsContainer.SwitchEnum<JavaBuiltins.JavaNashornCompatBuiltins.JavaNashornCompat> {
      protected JavaNashornCompatBuiltins() {
         super(JSRealm.JAVA_CLASS_NAME_NASHORN_COMPAT, JavaBuiltins.JavaNashornCompatBuiltins.JavaNashornCompat.class);
      }

      protected Object createNode(
         JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, JavaBuiltins.JavaNashornCompatBuiltins.JavaNashornCompat builtinEnum
      ) {
         switch (builtinEnum) {
            case isJavaMethod:
               return JavaBuiltinsFactory.JavaIsJavaMethodNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
            case isJavaFunction:
               return JavaBuiltinsFactory.JavaIsJavaFunctionNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
            case isScriptFunction:
               return JavaBuiltinsFactory.JavaIsScriptFunctionNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
            case isScriptObject:
               return JavaBuiltinsFactory.JavaIsScriptObjectNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
            case synchronized_:
               if (!JSConfig.SubstrateVM) {
                  return JavaBuiltinsFactory.JavaSynchronizedNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
               }
            default:
               return null;
         }
      }

      public static enum JavaNashornCompat implements BuiltinEnum<JavaBuiltins.JavaNashornCompatBuiltins.JavaNashornCompat> {
         isJavaMethod(1),
         isJavaFunction(1),
         isScriptFunction(1),
         isScriptObject(1),
         synchronized_(2) {
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

   abstract static class JavaSuperNode extends JSBuiltinNode {
      JavaSuperNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      @CompilerDirectives.TruffleBoundary
      protected Object superAdapter(Object adapter) {
         try {
            return InteropLibrary.getUncached().readMember(adapter, "super");
         } catch (UnknownIdentifierException | UnsupportedMessageException var3) {
            return Undefined.instance;
         }
      }
   }

   abstract static class JavaSynchronizedNode extends JSBuiltinNode {
      JavaSynchronizedNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object doSynchronize(Object func, Object lock) {
         if (!JSFunction.isJSFunction(func)) {
            throw Errors.createTypeErrorNotAFunction(func);
         } else {
            JSRealm realm = this.getRealm();
            if (lock != Undefined.instance) {
               unwrapAndCheckLockObject(lock, realm.getEnv());
            }

            JSFunctionData synchronizedFunctionData = this.createSynchronizedWrapper((JSFunctionObject)func);
            JSFunctionObject synchronizedFunction = JSFunction.create(realm, synchronizedFunctionData);
            return lock != Undefined.instance ? JSFunction.bind(realm, synchronizedFunction, lock, JSArguments.EMPTY_ARGUMENTS_ARRAY) : synchronizedFunction;
         }
      }

      @CompilerDirectives.TruffleBoundary
      private JSFunctionData createSynchronizedWrapper(JSFunctionObject func) {
         CallTarget callTarget = (new JavaScriptRootNode(this.getContext().getLanguage(), null, null) {
            @Override
            public Object execute(VirtualFrame frame) {
               Object thisObj = JSFrameUtil.getThisObj(frame);
               Object lock = JavaBuiltins.JavaSynchronizedNode.unwrapAndCheckLockObject(thisObj, this.getRealm().getEnv());
               Object[] arguments = JSArguments.create(thisObj, func, JSArguments.extractUserArguments(frame.getArguments()));
               synchronized (lock) {
                  return JSFunction.call(arguments);
               }
            }
         }).getCallTarget();
         return JSFunctionData.createCallOnly(this.getContext(), callTarget, 0, JavaBuiltins.SYNCHRONIZED_WRAPPER_NAME);
      }

      static Object unwrapJavaObject(Object object, TruffleLanguage.Env env) {
         return env.isHostObject(object) ? env.asHostObject(object) : object;
      }

      static Object unwrapAndCheckLockObject(Object thisObj, TruffleLanguage.Env env) {
         Object lock = unwrapJavaObject(thisObj, env);
         if (!JSRuntime.isJSPrimitive(lock) && !lock.getClass().isArray()) {
            return lock;
         } else {
            CompilerDirectives.transferToInterpreter();
            throw Errors.createTypeError("Locking not supported on type: " + lock.getClass().getTypeName());
         }
      }
   }

   @ImportStatic(JSConfig.class)
   abstract static class JavaToNode extends JSBuiltinNode {
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

      @Specialization(guards = "isJSObject(jsObj)")
      protected Object to(Object jsObj, Object toType, @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         boolean knownArrayClass = false;
         Object javaType;
         if (env.isHostObject(toType)) {
            javaType = toType;
         } else if (toType == Undefined.instance) {
            if (env.isHostLookupAllowed()) {
               javaType = env.lookupHostSymbol("java.lang.Object[]");
            } else {
               javaType = env.asGuestValue(Object[].class);
            }

            knownArrayClass = true;
         } else {
            TruffleString className = this.toString(toType);
            javaType = JavaBuiltins.JavaTypeNode.lookupJavaType(className, env);
            if (javaType == null) {
               throw Errors.createTypeErrorClassNotFound(className);
            }
         }

         if (!knownArrayClass && !isJavaArrayClass(javaType, env, interop)) {
            throw Errors.createTypeErrorFormat("Unsupported type: %s", this.toString(javaType));
         } else {
            return this.toArray(jsObj, javaType);
         }
      }

      @Specialization(guards = "!isJSObject(obj)", limit = "InteropLibraryLimit")
      protected Object toNonObject(
         Object obj, Object toType, @CachedLibrary("obj") InteropLibrary objInterop, @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary typeInterop
      ) {
         if (objInterop.hasArrayElements(obj)) {
            return this.to(obj, toType, typeInterop);
         } else {
            throw Errors.createTypeErrorNotAnObject(obj);
         }
      }

      private static boolean isJavaArrayClass(Object type, TruffleLanguage.Env env, InteropLibrary interop) {
         try {
            return env.isHostObject(type) && interop.isMetaObject(type) && interop.asString(interop.getMetaQualifiedName(type)).endsWith("[]");
         } catch (UnsupportedMessageException var4) {
            assert false : var4;

            return false;
         }
      }

      private Object toArray(Object jsObj, Object arrayType) {
         Object[] arr = this.toObjectArrayNode.executeObjectArray(jsObj);

         try {
            Object result = this.newArray.instantiate(arrayType, arr.length);

            for (int i = 0; i < arr.length; i++) {
               this.arrayElements.writeArrayElement(result, i, this.exportValue.execute(arr[i]));
            }

            return result;
         } catch (ArityException | UnsupportedMessageException | InvalidArrayIndexException | UnsupportedTypeException var6) {
            throw Errors.createTypeError(var6, this);
         }
      }
   }

   @ImportStatic(JSConfig.class)
   abstract static class JavaTypeNameNode extends JSBuiltinNode {
      JavaTypeNameNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJavaInteropClass(type, typeInterop)")
      protected TruffleString typeNameJavaInteropClass(
         Object type,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary typeInterop,
         @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary stringInterop
      ) {
         try {
            return stringInterop.asTruffleString(typeInterop.getMetaQualifiedName(type));
         } catch (UnsupportedMessageException var5) {
            throw Errors.createTypeErrorInteropException(type, var5, "Java.typeName", this);
         }
      }

      @Fallback
      protected Object nonType(Object value) {
         return Undefined.instance;
      }

      protected final boolean isJavaInteropClass(Object obj, InteropLibrary typeInterop) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         return env.isHostObject(obj) && typeInterop.isMetaObject(obj);
      }
   }

   abstract static class JavaTypeNode extends JSBuiltinNode {
      JavaTypeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      @CompilerDirectives.TruffleBoundary
      protected Object type(TruffleString name) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         Object javaType = lookupJavaType(name, env);
         if (javaType == null) {
            throw Errors.createTypeErrorClassNotFound(name);
         } else {
            return javaType;
         }
      }

      @Specialization(guards = "!isString(obj)")
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
            } catch (Exception var3) {
            }

            return lookForSubclasses(Strings.toJavaString(name), env);
         } else {
            throw Errors.createTypeError("Java Interop is not available");
         }
      }

      private static Object lookForSubclasses(String className, TruffleLanguage.Env env) {
         StringBuilder nextName = new StringBuilder(className);
         int lastDot = nextName.length();

         while (true) {
            lastDot = nextName.lastIndexOf(".", lastDot - 1);
            if (lastDot == -1) {
               return null;
            }

            nextName.setCharAt(lastDot, '$');

            try {
               String innerClassName = nextName.toString();
               Object found = env.lookupHostSymbol(innerClassName);
               if (found != null) {
                  return found;
               }
            } catch (Exception var6) {
            }
         }
      }
   }
}
