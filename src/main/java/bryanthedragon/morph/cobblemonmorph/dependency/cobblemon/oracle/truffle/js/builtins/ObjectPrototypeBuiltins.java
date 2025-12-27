package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.EnumSet;

public final class ObjectPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<ObjectPrototypeBuiltins.ObjectPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new ObjectPrototypeBuiltins();

   protected ObjectPrototypeBuiltins() {
      super(JSOrdinary.PROTOTYPE_NAME, ObjectPrototypeBuiltins.ObjectPrototype.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ObjectPrototypeBuiltins.ObjectPrototype builtinEnum) {
      switch (builtinEnum) {
         case hasOwnProperty:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypeHasOwnPropertyNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case isPrototypeOf:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypeIsPrototypeOfNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case propertyIsEnumerable:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypePropertyIsEnumerableNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toLocaleString:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypeToLocaleStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toString:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypeToStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case valueOf:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypeValueOfNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case __defineGetter__:
         case __defineSetter__:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypeDefineGetterOrSetterNodeGen.create(
               context,
               builtin,
               builtinEnum == ObjectPrototypeBuiltins.ObjectPrototype.__defineGetter__,
               args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case __lookupGetter__:
         case __lookupSetter__:
            return ObjectPrototypeBuiltinsFactory.ObjectPrototypeLookupGetterOrSetterNodeGen.create(
               context,
               builtin,
               builtinEnum == ObjectPrototypeBuiltins.ObjectPrototype.__lookupGetter__,
               args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class FormatCacheNode extends JavaScriptBaseNode {
      public abstract TruffleString execute(TruffleString name);

      public static ObjectPrototypeBuiltins.FormatCacheNode create() {
         return ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.create();
      }

      @Specialization(guards = "stringEquals(equalsNode, cachedName, name)", limit = "10")
      protected TruffleString executeCached(
         TruffleString name,
         @Cached("name") TruffleString cachedName,
         @Cached("executeUncached(name)") TruffleString cachedResult,
         @Cached TruffleString.EqualNode equalsNode
      ) {
         return cachedResult;
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected TruffleString executeUncached(TruffleString name) {
         return Strings.concatAll(Strings.BRACKET_OBJECT_SPC, name, Strings.BRACKET_CLOSE);
      }
   }

   @ImportStatic(JSObject.class)
   public abstract static class GetBuiltinToStringTagNode extends JavaScriptBaseNode {
      public abstract TruffleString execute(JSObject object);

      public static ObjectPrototypeBuiltins.GetBuiltinToStringTagNode create() {
         return ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.create();
      }

      @Specialization(guards = {"cachedClass != null", "cachedClass.isInstance(object)"}, limit = "5")
      protected static TruffleString cached(JSObject object, @Cached("getJSClass(object)") JSClass cachedClass) {
         return cachedClass.getBuiltinToStringTag(object);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization(replaces = "cached")
      protected static TruffleString uncached(JSObject object) {
         return JSObject.getJSClass(object).getBuiltinToStringTag(object);
      }
   }

   public abstract static class ObjectOperation extends JSBuiltinNode {
      @Node.Child
      private JSToObjectNode toObjectNode;
      private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();
      private final BranchProfile notAJSObjectBranch = BranchProfile.create();

      public ObjectOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected final JSDynamicObject toJSObject(Object target) {
         return JSRuntime.expectJSObject(this.toObject(target), this.notAJSObjectBranch);
      }

      protected final Object toObject(Object target) {
         if (this.toObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toObjectNode = this.insert(JSToObjectNode.createToObject(this.getContext()));
         }

         return this.toObjectNode.execute(target);
      }

      protected final JSDynamicObject asJSObject(Object object) {
         if (this.isObject.profile(JSRuntime.isObject(object))) {
            return (JSDynamicObject)object;
         } else {
            throw this.createTypeErrorCalledOnNonObject(object);
         }
      }

      protected final JSDynamicObject toOrAsJSObject(Object thisObj) {
         return this.getContext().getEcmaScriptVersion() >= 6 ? this.toJSObject(thisObj) : this.asJSObject(thisObj);
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSException createTypeErrorCalledOnNonObject(Object value) {
         assert !JSRuntime.isObject(value);

         return Errors.createTypeErrorFormat("Object.%s called on non-object", this.getBuiltin().getName());
      }
   }

   public static enum ObjectPrototype implements BuiltinEnum<ObjectPrototypeBuiltins.ObjectPrototype> {
      hasOwnProperty(1),
      isPrototypeOf(1),
      propertyIsEnumerable(1),
      toLocaleString(0),
      toString(0),
      valueOf(0),
      __defineGetter__(2),
      __defineSetter__(2),
      __lookupGetter__(1),
      __lookupSetter__(1);

      private final int length;

      private ObjectPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isAnnexB() {
         return EnumSet.of(__defineGetter__, __defineSetter__, __lookupGetter__, __lookupSetter__).contains(this);
      }
   }

   public abstract static class ObjectPrototypeDefineGetterOrSetterNode extends ObjectPrototypeBuiltins.ObjectOperation {
      private final boolean getter;
      @Node.Child
      private IsCallableNode isCallableNode = IsCallableNode.create();
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();

      public ObjectPrototypeDefineGetterOrSetterNode(JSContext context, JSBuiltin builtin, boolean getter) {
         super(context, builtin);
         this.getter = getter;
      }

      @Specialization
      protected Object define(Object thisObj, Object prop, Object getterOrSetter) {
         JSDynamicObject object = this.toJSObject(thisObj);
         if (!this.isCallableNode.executeBoolean(getterOrSetter)) {
            throw this.createTypeErrorExpectingFunction();
         } else {
            Object key = this.toPropertyKeyNode.execute(prop);
            PropertyDescriptor desc = PropertyDescriptor.createEmpty();
            if (this.getter) {
               desc.setGet(getterOrSetter);
            } else {
               desc.setSet(getterOrSetter);
            }

            desc.setEnumerable(true);
            desc.setConfigurable(true);
            JSRuntime.definePropertyOrThrow(object, key, desc);
            return Undefined.instance;
         }
      }

      @CompilerDirectives.TruffleBoundary
      private JSException createTypeErrorExpectingFunction() {
         return Errors.createTypeErrorFormat("%s: Expecting function", this.getBuiltin().getFullName());
      }
   }

   public abstract static class ObjectPrototypeHasOwnPropertyNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private JSHasPropertyNode hasOwnPropertyNode;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode;

      public ObjectPrototypeHasOwnPropertyNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(thisObj)")
      protected boolean doJSObjectTStringKey(JSDynamicObject thisObj, TruffleString propertyName) {
         return this.getHasOwnPropertyNode().executeBoolean(thisObj, propertyName);
      }

      @Specialization(guards = "isJSObject(thisObj)")
      protected boolean doJSObjectIntKey(JSDynamicObject thisObj, int index) {
         return this.getHasOwnPropertyNode().executeBoolean(thisObj, index);
      }

      @Specialization(guards = "isJSObject(thisObj)", replaces = {"doJSObjectTStringKey", "doJSObjectIntKey"})
      protected boolean doJSObjectAnyKey(JSDynamicObject thisObj, Object propName) {
         Object key = this.getToPropertyKeyNode().execute(propName);
         return this.getHasOwnPropertyNode().executeBoolean(thisObj, key);
      }

      @Specialization(guards = "isNullOrUndefined(thisObj)")
      protected boolean hasOwnPropertyNullOrUndefined(JSDynamicObject thisObj, Object propName) {
         this.getToPropertyKeyNode().execute(propName);
         throw Errors.createTypeErrorNotObjectCoercible(thisObj, null, this.getContext());
      }

      @Specialization
      protected boolean hasOwnPropertyTString(TruffleString thisObj, Object propName) {
         return this.hasOwnPropertyPrimitive(thisObj, propName);
      }

      @Specialization(guards = "!isTruffleObject(thisObj)")
      protected boolean hasOwnPropertyPrimitive(Object thisObj, Object propName) {
         Object key = this.getToPropertyKeyNode().execute(propName);
         JSDynamicObject obj = this.toJSObject(thisObj);
         return this.getHasOwnPropertyNode().executeBoolean(obj, key);
      }

      @Specialization
      protected boolean hasOwnPropertySymbol(Symbol thisObj, Object propName) {
         return this.hasOwnPropertyPrimitive(thisObj, propName);
      }

      @Specialization
      protected boolean hasOwnPropertySafeInteger(SafeInteger thisObj, Object propName) {
         return this.hasOwnPropertyPrimitive(thisObj, propName);
      }

      @Specialization
      protected boolean hasOwnPropertyBigInt(BigInt thisObj, Object propName) {
         return this.hasOwnPropertyPrimitive(thisObj, propName);
      }

      @Specialization(guards = "isForeignObject(thisObj)")
      protected boolean hasOwnPropertyForeign(Object thisObj, Object propName) {
         return this.getHasOwnPropertyNode().executeBoolean(thisObj, propName);
      }

      public JSHasPropertyNode getHasOwnPropertyNode() {
         if (this.hasOwnPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.hasOwnPropertyNode = this.insert(JSHasPropertyNode.create(true));
         }

         return this.hasOwnPropertyNode;
      }

      protected JSToPropertyKeyNode getToPropertyKeyNode() {
         if (this.toPropertyKeyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
         }

         return this.toPropertyKeyNode;
      }
   }

   public abstract static class ObjectPrototypeIsPrototypeOfNode extends ObjectPrototypeBuiltins.ObjectOperation {
      private final ConditionProfile argIsNull = ConditionProfile.createBinaryProfile();
      private final ConditionProfile firstPrototypeFits = ConditionProfile.createBinaryProfile();

      public ObjectPrototypeIsPrototypeOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSObject(arg)")
      protected boolean isPrototypeOf(Object thisObj, JSDynamicObject arg) {
         JSDynamicObject object = this.toJSObject(thisObj);
         if (this.argIsNull.profile(arg == null)) {
            return false;
         } else {
            JSDynamicObject pobj = JSObject.getPrototype(arg);
            if (this.firstPrototypeFits.profile(pobj == object)) {
               return true;
            } else {
               int counter = 0;

               while (++counter <= this.getContext().getContextOptions().getMaxPrototypeChainLength()) {
                  pobj = JSObject.getPrototype(pobj);
                  if (pobj == object) {
                     return true;
                  }

                  if (pobj == Null.instance) {
                     return false;
                  }
               }

               throw Errors.createRangeError("prototype chain length exceeded");
            }
         }
      }

      @Specialization(guards = "!isJSObject(arg)")
      protected boolean isPrototypeOfNoObject(Object thisObj, Object arg) {
         return false;
      }
   }

   public abstract static class ObjectPrototypeLookupGetterOrSetterNode extends ObjectPrototypeBuiltins.ObjectOperation {
      private final boolean getter;
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
      @Node.Child
      private JSGetOwnPropertyNode getOwnPropertyNode = JSGetOwnPropertyNode.create();

      public ObjectPrototypeLookupGetterOrSetterNode(JSContext context, JSBuiltin builtin, boolean getter) {
         super(context, builtin);
         this.getter = getter;
      }

      @Specialization
      protected Object lookup(Object thisObj, Object prop) {
         JSDynamicObject object = this.toJSObject(thisObj);
         Object key = this.toPropertyKeyNode.execute(prop);
         JSDynamicObject current = object;

         do {
            PropertyDescriptor desc = this.getOwnPropertyNode.execute(current, key);
            if (desc != null) {
               if (desc.isAccessorDescriptor()) {
                  return this.getter ? desc.getGet() : desc.getSet();
               } else {
                  return Undefined.instance;
               }
            }

            current = JSObject.getPrototype(current);
         } while (current != Null.instance);

         return Undefined.instance;
      }
   }

   public abstract static class ObjectPrototypePropertyIsEnumerableNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode = JSToPropertyKeyNode.create();
      @Node.Child
      private JSGetOwnPropertyNode getOwnPropertyNode = JSGetOwnPropertyNode.create();
      private final ConditionProfile descNull = ConditionProfile.createBinaryProfile();

      public ObjectPrototypePropertyIsEnumerableNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean propertyIsEnumerable(Object obj, Object key) {
         Object propertyKey = this.toPropertyKeyNode.execute(key);
         JSDynamicObject thisJSObj = this.toJSObject(obj);
         PropertyDescriptor desc = this.getOwnPropertyNode.execute(thisJSObj, propertyKey);
         return this.descNull.profile(desc == null) ? false : desc.getEnumerable();
      }
   }

   public abstract static class ObjectPrototypeToLocaleStringNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private PropertyGetNode getToString;
      @Node.Child
      private JSFunctionCallNode callNode;

      public ObjectPrototypeToLocaleStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getToString = PropertyGetNode.create(Strings.TO_STRING, false, context);
         this.callNode = JSFunctionCallNode.createCall();
      }

      @Specialization
      protected Object toLocaleString(Object obj) {
         Object objConv = obj;
         if (this.getContext().getEcmaScriptVersion() < 6 || this.getContext().isOptionV8CompatibilityMode()) {
            objConv = this.toJSObject(obj);
         }

         Object toStringFn = this.getToString.getValue(objConv);
         return this.callNode.executeCall(JSArguments.createZeroArg(obj, toStringFn));
      }
   }

   public abstract static class ObjectPrototypeToStringNode extends ObjectPrototypeBuiltins.ObjectOperation {
      @Node.Child
      private PropertyGetNode getStringTagNode;
      @Node.Child
      private ObjectPrototypeBuiltins.FormatCacheNode formatCacheNode;

      public ObjectPrototypeToStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.getStringTagNode = PropertyGetNode.create(Symbol.SYMBOL_TO_STRING_TAG, false, context);
      }

      private TruffleString formatString(TruffleString name) {
         if (this.formatCacheNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.formatCacheNode = this.insert(ObjectPrototypeBuiltins.FormatCacheNode.create());
         }

         return this.formatCacheNode.execute(name);
      }

      private TruffleString getToStringTag(JSObject thisObj) {
         if (this.getContext().getEcmaScriptVersion() >= 6) {
            Object toStringTag = this.getStringTagNode.getValue(thisObj);
            if (Strings.isTString(toStringTag)) {
               return JSRuntime.toStringIsString(toStringTag);
            }
         }

         return null;
      }

      @Specialization(guards = "!isJSProxy(thisObj)")
      protected TruffleString doJSObject(
         JSObject thisObj, @Cached.Shared("builtinTag") @Cached ObjectPrototypeBuiltins.GetBuiltinToStringTagNode getBuiltinToStringTagNode
      ) {
         TruffleString toString = this.getToStringTag(thisObj);
         if (toString == null) {
            if (this.getContext().getEcmaScriptVersion() >= 6) {
               toString = getBuiltinToStringTagNode.execute(thisObj);
            } else {
               toString = JSObject.getClassName(thisObj);
            }
         }

         return this.formatString(toString);
      }

      @Specialization
      protected TruffleString doJSProxy(
         JSProxyObject thisObj, @Cached.Shared("builtinTag") @Cached("create()") ObjectPrototypeBuiltins.GetBuiltinToStringTagNode getBuiltinToStringTagNode
      ) {
         TruffleString builtinTag = getBuiltinToStringTagNode.execute(thisObj);
         TruffleString tag = this.getToStringTag(thisObj);
         if (tag == null) {
            tag = builtinTag;
         }

         return this.formatString(tag);
      }

      @Specialization(guards = "isJSNull(thisObj)")
      protected TruffleString doNull(Object thisObj) {
         return Strings.TO_STRING_VALUE_NULL;
      }

      @Specialization(guards = "isUndefined(thisObj)")
      protected TruffleString doUndefined(Object thisObj) {
         return Strings.TO_STRING_VALUE_UNDEFINED;
      }

      @Specialization(guards = "isForeignObject(thisObj)", limit = "1")
      @CompilerDirectives.TruffleBoundary
      protected TruffleString doForeignObject(Object thisObj, @CachedLibrary("thisObj") InteropLibrary interop) {
         if (interop.isNull(thisObj)) {
            return Strings.TO_STRING_VALUE_NULL;
         } else if (interop.hasArrayElements(thisObj)) {
            return Strings.TO_STRING_VALUE_ARRAY;
         } else if (interop.isExecutable(thisObj) || interop.isInstantiable(thisObj)) {
            return Strings.TO_STRING_VALUE_FUNCTION;
         } else {
            return interop.isInstant(thisObj) ? Strings.TO_STRING_VALUE_DATE : Strings.TO_STRING_VALUE_OBJECT;
         }
      }

      @Specialization
      protected TruffleString doSymbol(Symbol thisObj) {
         assert thisObj != null;

         return JSObject.defaultToString(this.toJSObject(thisObj));
      }

      @Specialization
      protected TruffleString doString(TruffleString thisObj) {
         return JSObject.defaultToString(this.toJSObject(thisObj));
      }

      @Specialization
      protected TruffleString doSafeInteger(SafeInteger thisObj) {
         return JSObject.defaultToString(this.toJSObject(thisObj));
      }

      @Specialization
      protected TruffleString doBigInt(BigInt thisObj) {
         return JSObject.defaultToString(this.toJSObject(thisObj));
      }

      @Specialization(guards = "!isTruffleObject(thisObj)")
      protected TruffleString doObject(Object thisObj) {
         assert thisObj != null;

         return JSObject.defaultToString(this.toJSObject(thisObj));
      }
   }

   @ImportStatic(JSConfig.class)
   public abstract static class ObjectPrototypeValueOfNode extends ObjectPrototypeBuiltins.ObjectOperation {
      public ObjectPrototypeValueOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSDynamicObject(thisObj)")
      protected JSDynamicObject valueOfJSObject(JSDynamicObject thisObj) {
         return this.toJSObject(thisObj);
      }

      @Specialization
      protected JSDynamicObject valueOfSymbol(Symbol thisObj) {
         return this.toJSObject(thisObj);
      }

      @Specialization
      protected JSDynamicObject valueOfLazyString(TruffleString thisObj) {
         return this.toJSObject(thisObj);
      }

      @Specialization
      protected JSDynamicObject valueOfSafeInteger(SafeInteger thisObj) {
         return this.toJSObject(thisObj);
      }

      @Specialization
      protected JSDynamicObject valueOfBigInt(BigInt thisObj) {
         return this.toJSObject(thisObj);
      }

      @Specialization(guards = "!isTruffleObject(thisObj)")
      protected JSDynamicObject valueOfOther(Object thisObj) {
         return this.toJSObject(thisObj);
      }

      @Specialization(guards = "isForeignObject(thisObj)")
      protected Object valueOfForeign(Object thisObj, @CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary interop) {
         if (interop.isNull(thisObj)) {
            throw Errors.createTypeErrorNotObjectCoercible(thisObj, null, this.getContext());
         } else {
            return thisObj;
         }
      }
   }
}
