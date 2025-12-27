package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.access.FromPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.ToPropertyDescriptorNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectArrayNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectArrayNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.List;

public class ReflectBuiltins extends JSBuiltinsContainer.SwitchEnum<ReflectBuiltins.Reflect> {
   public static final JSBuiltinsContainer BUILTINS = new ReflectBuiltins();

   protected ReflectBuiltins() {
      super(JSRealm.REFLECT_CLASS_NAME, ReflectBuiltins.Reflect.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ReflectBuiltins.Reflect builtinEnum) {
      assert context.getEcmaScriptVersion() >= 6;

      switch (builtinEnum) {
         case apply:
            return ReflectBuiltinsFactory.ReflectApplyNodeGen.create(context, builtin, args().fixedArgs(3).createArgumentNodes(context));
         case construct:
            return ReflectBuiltinsFactory.ReflectConstructNodeGen.create(context, builtin, args().fixedArgs(2).varArgs().createArgumentNodes(context));
         case defineProperty:
            return ReflectBuiltinsFactory.ReflectDefinePropertyNodeGen.create(context, builtin, args().fixedArgs(3).createArgumentNodes(context));
         case deleteProperty:
            return ReflectBuiltinsFactory.ReflectDeletePropertyNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case get:
            return ReflectBuiltinsFactory.ReflectGetNodeGen.create(context, builtin, args().fixedArgs(2).varArgs().createArgumentNodes(context));
         case getOwnPropertyDescriptor:
            return ReflectBuiltinsFactory.ReflectGetOwnPropertyDescriptorNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case getPrototypeOf:
            return ReflectBuiltinsFactory.ReflectGetPrototypeOfNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case has:
            return ReflectBuiltinsFactory.ReflectHasNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case isExtensible:
            return ReflectBuiltinsFactory.ReflectIsExtensibleNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case ownKeys:
            return ReflectBuiltinsFactory.ReflectOwnKeysNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case preventExtensions:
            return ReflectBuiltinsFactory.ReflectPreventExtensionsNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case set:
            return ReflectBuiltinsFactory.ReflectSetNodeGen.create(context, builtin, args().fixedArgs(3).varArgs().createArgumentNodes(context));
         case setPrototypeOf:
            return ReflectBuiltinsFactory.ReflectSetPrototypeOfNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum Reflect implements BuiltinEnum<ReflectBuiltins.Reflect> {
      apply(3),
      construct(2),
      defineProperty(3),
      deleteProperty(2),
      get(2),
      getOwnPropertyDescriptor(2),
      getPrototypeOf(1),
      has(2),
      isExtensible(1),
      ownKeys(1),
      preventExtensions(1),
      set(3),
      setPrototypeOf(2);

      private final int length;

      private Reflect(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class ReflectApplyNode extends JSBuiltinNode {
      @Node.Child
      private JSFunctionCallNode call = JSFunctionCallNode.createCall();
      @Node.Child
      private JSToObjectArrayNode toObjectArray = JSToObjectArrayNodeGen.create(this.getContext());

      public ReflectApplyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSFunction(target)")
      protected final Object applyFunction(JSDynamicObject target, Object thisArgument, Object argumentsList) {
         return this.apply(target, thisArgument, argumentsList);
      }

      @Specialization(guards = "isCallable.executeBoolean(target)", replaces = "applyFunction", limit = "1")
      protected final Object applyCallable(
         Object target, Object thisArgument, Object argumentsList, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable
      ) {
         return this.apply(target, thisArgument, argumentsList);
      }

      private Object apply(Object target, Object thisArgument, Object argumentsList) {
         Object[] applyUserArgs = this.toObjectArray.executeObjectArray(argumentsList);

         assert applyUserArgs.length <= this.getContext().getContextOptions().getMaxApplyArgumentLength();

         Object[] passedOnArguments = JSArguments.create(thisArgument, target, applyUserArgs);
         return this.call.executeCall(passedOnArguments);
      }

      @Specialization(guards = "!isCallable.executeBoolean(target)", limit = "1")
      protected static Object error(Object target, Object thisArgument, Object argumentsList, @Cached @Cached.Shared("isCallable") IsCallableNode isCallable) {
         throw Errors.createTypeErrorCallableExpected();
      }

      @Override
      public boolean countsTowardsStackTraceLimit() {
         return false;
      }
   }

   public abstract static class ReflectConstructNode extends ReflectBuiltins.ReflectOperation {
      @Node.Child
      private JSFunctionCallNode constructCall = JSFunctionCallNode.createNewTarget();
      @Node.Child
      private JSToObjectArrayNode toObjectArray;

      public ReflectConstructNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toObjectArray = JSToObjectArrayNodeGen.create(context);
      }

      @Specialization
      protected Object reflectConstruct(Object target, Object argumentsList, Object[] optionalArgs, @Cached IsConstructorNode isConstructorNode) {
         if (!isConstructorNode.executeBoolean(target)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorNotAConstructor(target, this.getContext());
         } else {
            Object newTarget;
            if (optionalArgs.length == 0) {
               newTarget = target;
            } else {
               newTarget = optionalArgs[0];
               if (!isConstructorNode.executeBoolean(newTarget)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorNotAConstructor(newTarget, this.getContext());
               }
            }

            if (!JSRuntime.isObject(argumentsList)) {
               throw Errors.createTypeError("Reflect.construct: Arguments list has wrong type");
            } else {
               Object[] args = this.toObjectArray.executeObjectArray(argumentsList);
               Object[] passedOnArguments = JSArguments.createWithNewTarget(JSFunction.CONSTRUCT, target, newTarget, args);
               return this.constructCall.executeCall(passedOnArguments);
            }
         }
      }

      @Override
      public boolean countsTowardsStackTraceLimit() {
         return false;
      }
   }

   public abstract static class ReflectDefinePropertyNode extends ReflectBuiltins.ReflectOperation {
      public ReflectDefinePropertyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean reflectDefineProperty(
         Object target,
         Object propertyKey,
         Object attributes,
         @Cached("create()") JSToPropertyKeyNode toPropertyKeyNode,
         @Cached("create(getContext())") ToPropertyDescriptorNode toPropertyDescriptorNode
      ) {
         this.ensureJSObject(target);
         Object key = toPropertyKeyNode.execute(propertyKey);
         PropertyDescriptor descriptor = (PropertyDescriptor)toPropertyDescriptorNode.execute(attributes);
         return JSObject.defineOwnProperty((JSDynamicObject)target, key, descriptor);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ReflectDeletePropertyNode extends ReflectBuiltins.ReflectOperation {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

      public ReflectDeletePropertyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(target)")
      protected boolean doObject(JSDynamicObject target, Object propertyKey, @Cached("create()") JSClassProfile classProfile) {
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         return JSObject.delete(target, key, false, classProfile);
      }

      @Specialization(guards = "isForeignObject(target)", limit = "InteropLibraryLimit")
      protected boolean doForeignObject(
         Object target, Object propertyKey, @CachedLibrary("target") InteropLibrary interop, @Cached TruffleString.ToJavaStringNode toJavaStringNode
      ) {
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         if (interop.hasMembers(target)) {
            if (key instanceof TruffleString) {
               String memberName = Strings.toJavaString(toJavaStringNode, (TruffleString)key);
               if (interop.isMemberRemovable(target, memberName)) {
                  try {
                     InteropLibrary.getUncached().removeMember(target, memberName);
                     return true;
                  } catch (UnknownIdentifierException | UnsupportedMessageException var8) {
                     throw Errors.createTypeErrorInteropException(target, var8, "removeMember", memberName, null);
                  }
               }
            }

            return false;
         } else {
            throw Errors.createTypeErrorCalledOnNonObject();
         }
      }

      @Specialization(guards = {"!isJSObject(target)", "!isForeignObject(target)"})
      protected boolean doNonObject(Object target, Object propertyKey) {
         throw Errors.createTypeErrorCalledOnNonObject();
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ReflectGetNode extends ReflectBuiltins.ReflectOperation {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

      public ReflectGetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(target)")
      protected Object doObject(JSDynamicObject target, Object propertyKey, Object[] optionalArgs, @Cached("create()") JSClassProfile classProfile) {
         Object receiver = JSRuntime.getArg(optionalArgs, 0, target);
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         return JSRuntime.nullToUndefined(classProfile.getJSClass(target).getHelper(target, receiver, key, this));
      }

      @Specialization(guards = "isForeignObject(target)", limit = "InteropLibraryLimit")
      protected Object doForeignObject(
         Object target,
         Object propertyKey,
         Object[] optionalArgs,
         @CachedLibrary("target") InteropLibrary interop,
         @Cached ImportValueNode importValue,
         @Cached ForeignObjectPrototypeNode foreignObjectPrototypeNode,
         @Cached JSClassProfile classProfile
      ) {
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         if (interop.hasMembers(target)) {
            Object result = JSInteropUtil.readMemberOrDefault(target, key, null, interop, importValue, this);
            if (result == null) {
               if (this.getContext().getContextOptions().hasForeignObjectPrototype()) {
                  JSDynamicObject prototype = foreignObjectPrototypeNode.execute(target);
                  result = this.doObject(prototype, key, optionalArgs, classProfile);
               } else {
                  result = Undefined.instance;
               }
            }

            return result;
         } else {
            throw Errors.createTypeErrorCalledOnNonObject();
         }
      }

      @Specialization(guards = {"!isJSObject(target)", "!isForeignObject(target)"})
      protected Object doNonObject(Object target, Object propertyKey, Object[] optionalArgs) {
         throw Errors.createTypeErrorCalledOnNonObject();
      }
   }

   public abstract static class ReflectGetOwnPropertyDescriptorNode extends ReflectBuiltins.ReflectOperation {
      public ReflectGetOwnPropertyDescriptorNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject reflectGetOwnPropertyDescriptor(
         Object target,
         Object key,
         @Cached JSToPropertyKeyNode toPropertyKeyNode,
         @Cached JSGetOwnPropertyNode getOwnPropertyNode,
         @Cached FromPropertyDescriptorNode fromPropertyDescriptorNode
      ) {
         this.ensureJSObject(target);
         Object propertyKey = toPropertyKeyNode.execute(key);
         PropertyDescriptor desc = getOwnPropertyNode.execute((JSDynamicObject)target, propertyKey);
         return fromPropertyDescriptorNode.execute(desc, this.getContext());
      }
   }

   public abstract static class ReflectGetPrototypeOfNode extends ReflectBuiltins.ReflectOperation {
      public ReflectGetPrototypeOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object reflectGetPrototypeOf(Object target) {
         this.ensureJSObject(target);
         return JSObject.getPrototype((JSDynamicObject)target);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ReflectHasNode extends ReflectBuiltins.ReflectOperation {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

      public ReflectHasNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(target)")
      protected Object doObject(JSDynamicObject target, Object propertyKey, @Cached("create()") JSClassProfile jsclassProfile) {
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         return JSObject.hasProperty(target, key, jsclassProfile);
      }

      @Specialization(guards = "isForeignObject(target)", limit = "InteropLibraryLimit")
      protected Object doForeignObject(
         Object target,
         Object propertyKey,
         @CachedLibrary("target") InteropLibrary interop,
         @Cached TruffleString.ToJavaStringNode toJavaStringNode,
         @Cached ForeignObjectPrototypeNode foreignObjectPrototypeNode,
         @Cached JSClassProfile classProfile
      ) {
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         if (interop.hasMembers(target)) {
            if (key instanceof TruffleString) {
               boolean result = interop.isMemberExisting(target, Strings.toJavaString(toJavaStringNode, (TruffleString)key));
               if (!result && this.getContext().getContextOptions().hasForeignObjectPrototype()) {
                  JSDynamicObject prototype = foreignObjectPrototypeNode.execute(target);
                  result = JSObject.hasProperty(prototype, key, classProfile);
               }

               return result;
            } else {
               return false;
            }
         } else {
            throw Errors.createTypeErrorCalledOnNonObject();
         }
      }

      @Specialization(guards = {"!isJSObject(target)", "!isForeignObject(target)"})
      protected Object doNonObject(Object target, Object propertyKey) {
         throw Errors.createTypeErrorCalledOnNonObject();
      }
   }

   public abstract static class ReflectIsExtensibleNode extends ReflectBuiltins.ReflectOperation {
      public ReflectIsExtensibleNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean reflectIsExtensible(Object target, @Cached IsExtensibleNode isExtensibleNode) {
         this.ensureJSObject(target);
         return isExtensibleNode.executeBoolean((JSDynamicObject)target);
      }
   }

   public abstract static class ReflectOperation extends JSBuiltinNode {
      protected final BranchProfile errorBranch = BranchProfile.create();

      public ReflectOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected void ensureJSObject(Object target) {
         if (!JSRuntime.isObject(target)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorCalledOnNonObject();
         }
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ReflectOwnKeysNode extends ReflectBuiltins.ReflectOperation {
      public ReflectOwnKeysNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(target)")
      protected JSDynamicObject reflectOwnKeys(Object target, @Cached JSClassProfile jsclassProfile, @Cached ListSizeNode listSize) {
         List<Object> list = JSObject.ownPropertyKeys((JSDynamicObject)target, jsclassProfile);
         return JSArray.createLazyArray(this.getContext(), this.getRealm(), list, listSize.execute(list));
      }

      @Specialization(guards = "isForeignObject(target)", limit = "InteropLibraryLimit")
      protected Object doForeignObject(Object target, @CachedLibrary("target") InteropLibrary interop) {
         if (interop.hasMembers(target)) {
            try {
               return interop.getMembers(target);
            } catch (UnsupportedMessageException var4) {
               throw Errors.createTypeErrorInteropException(target, var4, "getMembers", this);
            }
         } else {
            throw Errors.createTypeErrorCalledOnNonObject();
         }
      }

      @Specialization(guards = {"!isJSObject(target)", "!isForeignObject(target)"})
      protected Object doNonObject(Object target) {
         throw Errors.createTypeErrorCalledOnNonObject();
      }
   }

   public abstract static class ReflectPreventExtensionsNode extends ReflectBuiltins.ReflectOperation {
      public ReflectPreventExtensionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean reflectPreventExtensions(Object target) {
         this.ensureJSObject(target);
         return JSObject.preventExtensions((JSDynamicObject)target);
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ReflectSetNode extends ReflectBuiltins.ReflectOperation {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

      public ReflectSetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(target)")
      protected boolean reflectSet(JSDynamicObject target, Object propertyKey, Object value, Object[] optionalArgs, @Cached JSClassProfile jsclassProfile) {
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         Object receiver = JSRuntime.getArg(optionalArgs, 0, target);
         return JSObject.setWithReceiver(target, key, value, receiver, false, jsclassProfile, this);
      }

      @Specialization(guards = "isForeignObject(target)", limit = "InteropLibraryLimit")
      protected Object doForeignObject(
         Object target,
         Object propertyKey,
         Object value,
         Object[] optionalArgs,
         @CachedLibrary("target") InteropLibrary interop,
         @Cached ExportValueNode exportValue
      ) {
         Object key = this.toPropertyKeyNode.execute(propertyKey);
         if (interop.hasMembers(target)) {
            JSInteropUtil.writeMember(target, key, value, interop, exportValue, this);
            return true;
         } else {
            throw Errors.createTypeErrorCalledOnNonObject();
         }
      }

      @Specialization(guards = {"!isJSObject(target)", "!isForeignObject(target)"})
      protected Object doNonObject(Object target, Object propertyKey, Object value, Object[] optionalArgs) {
         throw Errors.createTypeErrorCalledOnNonObject();
      }
   }

   public abstract static class ReflectSetPrototypeOfNode extends ReflectBuiltins.ReflectOperation {
      public ReflectSetPrototypeOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean reflectSetPrototypeOf(Object target, Object proto) {
         this.ensureJSObject(target);
         if ((JSDynamicObject.isJSDynamicObject(proto) || proto == Null.instance) && proto != Undefined.instance) {
            return JSObject.setPrototype((JSDynamicObject)target, (JSDynamicObject)proto);
         } else {
            throw Errors.createTypeErrorInvalidPrototype(proto);
         }
      }
   }
}
