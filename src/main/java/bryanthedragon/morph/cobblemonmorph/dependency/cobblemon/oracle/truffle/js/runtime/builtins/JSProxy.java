package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.ProxyFunctionBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.JSProxyCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class JSProxy extends AbstractJSClass implements PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("Proxy");
   public static final JSProxy INSTANCE = new JSProxy();
   public static final TruffleString GET_PROTOTYPE_OF = Strings.constant("getPrototypeOf");
   public static final TruffleString SET_PROTOTYPE_OF = Strings.constant("setPrototypeOf");
   public static final TruffleString IS_EXTENSIBLE = Strings.constant("isExtensible");
   public static final TruffleString PREVENT_EXTENSIONS = Strings.constant("preventExtensions");
   public static final TruffleString GET_OWN_PROPERTY_DESCRIPTOR = Strings.constant("getOwnPropertyDescriptor");
   public static final TruffleString HAS = Strings.constant("has");
   public static final TruffleString GET = Strings.constant("get");
   public static final TruffleString SET = Strings.constant("set");
   public static final TruffleString DELETE_PROPERTY = Strings.constant("deleteProperty");
   public static final TruffleString DEFINE_PROPERTY = Strings.constant("defineProperty");
   public static final TruffleString OWN_KEYS = Strings.constant("ownKeys");
   public static final TruffleString APPLY = Strings.constant("apply");
   public static final TruffleString CONSTRUCT = Strings.constant("construct");
   public static final TruffleString FOREIGN = Strings.constant("Foreign");
   public static final TruffleString PROXY_CALL = Strings.constant("ProxyCall");
   public static final HiddenKey REVOCABLE_PROXY = new HiddenKey("RevocableProxy");

   @CompilerDirectives.TruffleBoundary
   public static boolean checkPropertyIsSettable(Object truffleTarget, Object key) {
      assert JSRuntime.isPropertyKey(key);

      if (!JSDynamicObject.isJSDynamicObject(truffleTarget)) {
         return true;
      } else {
         JSDynamicObject target = (JSDynamicObject)truffleTarget;
         PropertyDescriptor desc = JSObject.getOwnProperty(target, key);
         if (desc != null) {
            if (!desc.getConfigurable()) {
               return false;
            }

            if (!JSObject.isExtensible(target)) {
               return false;
            }
         }

         return true;
      }
   }

   private JSProxy() {
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return CLASS_NAME;
   }

   @Override
   public String toString() {
      return Strings.toJavaString(CLASS_NAME);
   }

   public static JSProxyObject create(JSContext context, JSRealm realm, Object target, JSDynamicObject handler) {
      return JSProxyObject.create(realm, context.getProxyFactory(), target, handler);
   }

   public static Object getTarget(JSDynamicObject obj) {
      assert isJSProxy(obj);

      return ((JSProxyObject)obj).getProxyTarget();
   }

   public static Object getTargetNonProxy(JSDynamicObject thisObj) {
      Object obj = thisObj;

      while (isJSProxy(obj)) {
         obj = getTarget((JSDynamicObject)obj);
      }

      return obj;
   }

   public static JSDynamicObject getHandler(JSDynamicObject obj) {
      assert isJSProxy(obj);

      return ((JSProxyObject)obj).getProxyHandler();
   }

   public static JSDynamicObject getHandlerChecked(JSDynamicObject obj) {
      JSDynamicObject handler = getHandler(obj);
      if (handler == Null.instance) {
         throw Errors.createTypeErrorProxyRevoked();
      } else {
         return handler;
      }
   }

   public static JSDynamicObject getHandlerChecked(JSDynamicObject obj, BranchProfile errorBranch) {
      JSDynamicObject handler = getHandler(obj);
      if (handler == Null.instance) {
         errorBranch.enter();
         throw Errors.createTypeErrorProxyRevoked();
      } else {
         return handler;
      }
   }

   public static boolean isJSProxy(Object obj) {
      return obj instanceof JSProxyObject;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object receiver, Object key, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      return proxyGetHelper(store, key, receiver, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object receiver, long index, Node encapsulatingNode) {
      assert JSRuntime.isSafeInteger(index);

      return proxyGetHelper(store, Strings.fromLong(index), receiver, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object proxyGetHelper(JSDynamicObject proxy, Object key, Object receiver, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      JSDynamicObject handler = getHandlerChecked(proxy);
      Object target = getTarget(proxy);
      Object trap = getTrapFromObject(handler, GET);
      if (trap == Undefined.instance) {
         if (JSDynamicObject.isJSDynamicObject(target)) {
            JSDynamicObject jsobj = (JSDynamicObject)target;
            return JSObject.getJSClass(jsobj).getHelper(jsobj, receiver, key, encapsulatingNode);
         } else {
            Object result = JSInteropUtil.readMemberOrDefault(target, key, null);
            if (result == null && JavaScriptLanguage.get(encapsulatingNode).getJSContext().getContextOptions().hasForeignObjectPrototype()) {
               JSDynamicObject prototype = ForeignObjectPrototypeNode.getUncached().execute(target);
               result = JSObject.getJSClass(prototype).getHelper(prototype, receiver, key, encapsulatingNode);
            }

            return result;
         }
      } else {
         Object trapResult = JSRuntime.call(trap, handler, new Object[]{target, key, receiver}, encapsulatingNode);
         if (!(handler instanceof JSUncheckedProxyHandlerObject)) {
            checkProxyGetTrapInvariants(target, key, trapResult);
         }

         return trapResult;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static void checkProxyGetTrapInvariants(Object truffleTarget, Object key, Object trapResult) {
      assert JSRuntime.isPropertyKey(key);

      if (JSDynamicObject.isJSDynamicObject(truffleTarget)) {
         JSDynamicObject target = (JSDynamicObject)truffleTarget;
         PropertyDescriptor targetDesc = JSObject.getOwnProperty(target, key);
         if (targetDesc != null) {
            if (targetDesc.isDataDescriptor() && !targetDesc.getConfigurable() && !targetDesc.getWritable()) {
               Object targetValue = targetDesc.getValue();
               if (!JSRuntime.isSameValue(trapResult, targetValue)) {
                  throw Errors.createTypeErrorProxyGetInvariantViolated(key, targetValue, trapResult);
               }
            }

            if (targetDesc.isAccessorDescriptor()
               && !targetDesc.getConfigurable()
               && targetDesc.getGet() == Undefined.instance
               && trapResult != Undefined.instance) {
               throw Errors.createTypeError(
                  "Trap result must be undefined since the proxy target has a corresponding non-configurable own accessor property with undefined getter"
               );
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      return proxySet(thisObj, key, value, receiver, isStrict, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      return proxySet(thisObj, Strings.fromLong(index), value, receiver, isStrict, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean proxySet(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object trap = getTrapFromObject(handler, SET);
      if (trap == Undefined.instance) {
         if (JSDynamicObject.isJSDynamicObject(target)) {
            JSDynamicObject jsobj = (JSDynamicObject)target;
            return JSObject.getJSClass(jsobj).set(jsobj, key, value, receiver, isStrict, encapsulatingNode);
         } else {
            JSInteropUtil.writeMember(target, key, value);
            return true;
         }
      } else {
         Object trapResult = JSRuntime.call(trap, handler, new Object[]{target, key, value, receiver}, encapsulatingNode);
         boolean booleanTrapResult = JSRuntime.toBoolean(trapResult);
         if (!booleanTrapResult) {
            if (isStrict) {
               throw Errors.createTypeErrorTrapReturnedFalsish(SET, key);
            } else {
               return false;
            }
         } else {
            return handler instanceof JSUncheckedProxyHandlerObject ? true : checkProxySetTrapInvariants(thisObj, key, value);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean checkProxySetTrapInvariants(JSDynamicObject proxy, Object key, Object value) {
      assert isJSProxy(proxy) && !isRevoked(proxy);

      assert JSRuntime.isPropertyKey(key);

      Object target = getTarget(proxy);
      if (!JSDynamicObject.isJSDynamicObject(target)) {
         return true;
      } else {
         PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
         if (targetDesc != null) {
            if (targetDesc.isDataDescriptor() && !targetDesc.getConfigurable() && !targetDesc.getWritable()) {
               if (!JSRuntime.isSameValue(value, targetDesc.getValue())) {
                  throw Errors.createTypeError("Cannot change the value of a non-writable, non-configurable own data property");
               }
            } else if (targetDesc.isAccessorDescriptor() && !targetDesc.getConfigurable() && targetDesc.getSet() == Undefined.instance) {
               throw Errors.createTypeError("Cannot set the value of a non-configurable own accessor property with undefined setter");
            }
         }

         return true;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
      return this.hasOwnProperty(thisObj, JSRuntime.toString(index));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isObject(thisObj);

      assert JSRuntime.isPropertyKey(key);

      PropertyDescriptor desc = JSObject.getOwnProperty(thisObj, key);
      return desc != null;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasProperty(JSDynamicObject thisObj, long index) {
      return this.hasProperty(thisObj, JSRuntime.toString(index));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object trap = getTrapFromObject(handler, HAS);
      if (trap == Undefined.instance) {
         if (JSDynamicObject.isJSDynamicObject(target)) {
            return JSObject.hasProperty((JSDynamicObject)target, key);
         } else {
            boolean result = JSInteropUtil.hasProperty(target, key);
            if (!result && JavaScriptLanguage.get(null).getJSContext().getContextOptions().hasForeignObjectPrototype()) {
               JSDynamicObject prototype = ForeignObjectPrototypeNode.getUncached().execute(target);
               result = JSObject.hasProperty(prototype, key);
            }

            return result;
         }
      } else {
         boolean trapResult = JSRuntime.toBoolean(JSRuntime.call(trap, handler, new Object[]{target, key}));
         if (!trapResult && !checkPropertyIsSettable(target, key)) {
            throw Errors.createTypeErrorConfigurableExpected();
         } else {
            return trapResult;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      return this.delete(thisObj, Strings.fromLong(index), isStrict);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      assert JSRuntime.isPropertyKey(key);

      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object deleteFn = getTrapFromObject(handler, DELETE_PROPERTY);
      if (deleteFn == Undefined.instance) {
         return JSDynamicObject.isJSDynamicObject(target) ? JSObject.delete((JSDynamicObject)target, key, isStrict) : JSInteropUtil.remove(target, key);
      } else {
         Object trapResult = JSRuntime.call(deleteFn, handler, new Object[]{target, key});
         boolean booleanTrapResult = JSRuntime.toBoolean(trapResult);
         if (!booleanTrapResult) {
            if (isStrict) {
               throw Errors.createTypeErrorTrapReturnedFalsish(DELETE_PROPERTY, key);
            } else {
               return false;
            }
         } else if (!JSDynamicObject.isJSDynamicObject(target)) {
            return true;
         } else {
            PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
            if (targetDesc == null) {
               return true;
            } else if (targetDesc.hasConfigurable() && !targetDesc.getConfigurable()) {
               throw Errors.createTypeErrorConfigurableExpected();
            } else {
               JSContext context = JSObject.getJSContext(thisObj);
               if (context.getEcmaScriptVersion() >= 11) {
                  boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
                  if (!extensibleTarget) {
                     throw Errors.createTypeErrorProxyTargetNotExtensible();
                  }
               }

               return true;
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
      assert JSRuntime.isPropertyKey(key);

      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object definePropertyFn = getTrapFromObject(handler, DEFINE_PROPERTY);
      if (definePropertyFn == Undefined.instance) {
         if (JSDynamicObject.isJSDynamicObject(target)) {
            return JSObject.defineOwnProperty((JSDynamicObject)target, key, desc, doThrow);
         } else {
            JSInteropUtil.writeMember(target, key, Null.instance);
            return true;
         }
      } else {
         JSContext context = JSObject.getJSContext(thisObj);
         JSDynamicObject descObj = JSRuntime.fromPropertyDescriptor(desc, context);
         boolean trapResult = JSRuntime.toBoolean(JSRuntime.call(definePropertyFn, handler, new Object[]{target, key, descObj}));
         if (!trapResult) {
            if (doThrow) {
               if (handler instanceof JSUncheckedProxyHandlerObject) {
                  throw Errors.createTypeErrorCannotRedefineProperty(key);
               } else {
                  throw Errors.createTypeErrorTrapReturnedFalsish(DEFINE_PROPERTY, key);
               }
            } else {
               return false;
            }
         } else if (JSDynamicObject.isJSDynamicObject(target) && !(handler instanceof JSUncheckedProxyHandlerObject)) {
            PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
            boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
            boolean settingConfigFalse = desc.hasConfigurable() && !desc.getConfigurable();
            if (targetDesc == null) {
               if (!extensibleTarget) {
                  throw Errors.createTypeError("'defineProperty' on proxy: trap returned truish for adding property to the non-extensible proxy target");
               }

               if (settingConfigFalse) {
                  throw Errors.createTypeError(
                     "'defineProperty' on proxy: trap returned truish for defining non-configurable property which is non-existant in the proxy target"
                  );
               }
            } else {
               if (!isCompatiblePropertyDescriptor(extensibleTarget, desc, targetDesc)) {
                  throw Errors.createTypeError(
                     "'defineProperty' on proxy: trap returned truish for adding property that is incompatible with the existing property in the proxy target"
                  );
               }

               if (settingConfigFalse && targetDesc.getConfigurable()) {
                  throw Errors.createTypeError(
                     "'defineProperty' on proxy: trap returned truish for defining non-configurable property which is configurable in the proxy target"
                  );
               }

               if (context.getEcmaScriptVersion() >= 11
                  && targetDesc.isDataDescriptor()
                  && !targetDesc.getConfigurable()
                  && targetDesc.getWritable()
                  && desc.hasWritable()
                  && !desc.getWritable()) {
                  throw Errors.createTypeError(
                     "'defineProperty' on proxy: trap returned truish for defining non-configurable property which cannot be non-writable, unless there exists a corresponding non-configurable, non-writable own property of the proxy target"
                  );
               }
            }

            return true;
         } else {
            return true;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static PropertyDescriptor completePropertyDescriptor(PropertyDescriptor desc) {
      if (!desc.isGenericDescriptor() && !desc.isDataDescriptor()) {
         if (!desc.hasGet()) {
            desc.setGet(null);
         }

         if (!desc.hasSet()) {
            desc.setSet(null);
         }
      } else {
         if (!desc.hasValue()) {
            desc.setValue(Undefined.instance);
         }

         if (!desc.hasWritable()) {
            desc.setWritable(false);
         }
      }

      if (!desc.hasEnumerable()) {
         desc.setEnumerable(false);
      }

      if (!desc.hasConfigurable()) {
         desc.setConfigurable(false);
      }

      return desc;
   }

   private static boolean isCompatiblePropertyDescriptor(boolean extensibleTarget, PropertyDescriptor desc, PropertyDescriptor current) {
      return DefinePropertyUtil.isCompatiblePropertyDescriptor(extensibleTarget, desc, current);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object preventExtensionsFn = getTrapFromObject(handler, PREVENT_EXTENSIONS);
      if (preventExtensionsFn == Undefined.instance) {
         return JSDynamicObject.isJSDynamicObject(target) ? JSObject.preventExtensions((JSDynamicObject)target, doThrow) : true;
      } else {
         Object returnValue = JSRuntime.call(preventExtensionsFn, handler, new Object[]{target});
         boolean booleanTrapResult = JSRuntime.toBoolean(returnValue);
         if (booleanTrapResult && JSDynamicObject.isJSDynamicObject(target)) {
            boolean targetIsExtensible = JSObject.isExtensible((JSDynamicObject)target);
            if (targetIsExtensible) {
               throw Errors.createTypeError("target is extensible");
            }
         }

         if (doThrow && !booleanTrapResult) {
            throw Errors.createTypeError("'preventExtensions' on proxy: trap returned falsish");
         } else {
            return booleanTrapResult;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isExtensible(JSDynamicObject thisObj) {
      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object isExtensibleFn = getTrapFromObject(handler, IS_EXTENSIBLE);
      if (isExtensibleFn == Undefined.instance) {
         return JSDynamicObject.isJSDynamicObject(target) ? JSObject.isExtensible((JSDynamicObject)target) : true;
      } else {
         Object returnValue = JSRuntime.call(isExtensibleFn, handler, new Object[]{target});
         boolean booleanTrapResult = JSRuntime.toBoolean(returnValue);
         if (!JSDynamicObject.isJSDynamicObject(target)) {
            return booleanTrapResult;
         } else {
            boolean targetResult = JSObject.isExtensible((JSDynamicObject)target);
            if (booleanTrapResult != targetResult) {
               throw Errors.createTypeErrorSameResultExpected();
            } else {
               return booleanTrapResult;
            }
         }
      }
   }

   @Override
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      Object targetNonProxy = getTargetNonProxy(object);
      if (JSDynamicObject.isJSDynamicObject(targetNonProxy)) {
         if (JSArray.isJSArray(targetNonProxy)) {
            return JSArray.CLASS_NAME;
         } else {
            return JSFunction.isJSFunction(targetNonProxy) ? JSFunction.CLASS_NAME : Strings.UC_OBJECT;
         }
      } else {
         InteropLibrary interop = InteropLibrary.getUncached(targetNonProxy);
         if (interop.hasArrayElements(targetNonProxy)) {
            return JSArray.CLASS_NAME;
         } else {
            return !interop.isExecutable(targetNonProxy) && !interop.isInstantiable(targetNonProxy) ? Strings.UC_OBJECT : JSFunction.CLASS_NAME;
         }
      }
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
         return this.defaultToString(obj);
      } else {
         Object target = getTarget(obj);
         Object handler = getHandler(obj);
         return Strings.concatAll(
            Strings.PROXY_PAREN,
            JSRuntime.toDisplayStringInner(target, allowSideEffects, format, depth, obj),
            Strings.COMMA_SPC,
            JSRuntime.toDisplayStringInner(handler, allowSideEffects, format, depth, obj),
            Strings.PAREN_CLOSE
         );
      }
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      JSFunctionObject proxyConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, CLASS_NAME);
      JSObjectUtil.putFunctionsFromContainer(realm, proxyConstructor, ProxyFunctionBuiltins.BUILTINS);
      JSObject dummyPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      return new JSConstructor(proxyConstructor, dummyPrototype);
   }

   public static Object getTrapFromObject(JSDynamicObject maybeHandler, TruffleString trapName) {
      Object method = JSObject.get(maybeHandler, trapName);
      if (method == Undefined.instance || method == Null.instance) {
         return Undefined.instance;
      } else if (!JSRuntime.isCallable(method)) {
         throw Errors.createTypeErrorNotAFunction(method);
      } else {
         return method;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JSDynamicObject getPrototypeOf(JSDynamicObject thisObj) {
      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object getPrototypeOfFn = getTrapFromObject(handler, GET_PROTOTYPE_OF);
      if (getPrototypeOfFn == Undefined.instance) {
         return JSDynamicObject.isJSDynamicObject(target) ? JSObject.getPrototype((JSDynamicObject)target) : Null.instance;
      } else {
         Object handlerProto = JSRuntime.call(getPrototypeOfFn, handler, new Object[]{target});
         if (JSDynamicObject.isJSDynamicObject(handlerProto) && handlerProto != Undefined.instance) {
            JSDynamicObject handlerProtoObj = (JSDynamicObject)handlerProto;
            if (!JSDynamicObject.isJSDynamicObject(target)) {
               return handlerProtoObj;
            } else {
               boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
               if (extensibleTarget) {
                  return handlerProtoObj;
               } else {
                  JSDynamicObject targetProtoObj = JSObject.getPrototype((JSDynamicObject)target);
                  if (handlerProtoObj != targetProtoObj) {
                     throw Errors.createTypeErrorSameResultExpected();
                  } else {
                     return handlerProtoObj;
                  }
               }
            }
         } else {
            throw Errors.createTypeError("object or null expected");
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
      assert JSObjectUtil.isValidPrototype(newPrototype);

      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object setPrototypeOfFn = getTrapFromObject(handler, SET_PROTOTYPE_OF);
      if (setPrototypeOfFn == Undefined.instance) {
         return JSDynamicObject.isJSDynamicObject(target) ? JSObject.setPrototype((JSDynamicObject)target, newPrototype) : true;
      } else {
         Object returnValue = JSRuntime.call(setPrototypeOfFn, handler, new Object[]{target, newPrototype});
         boolean booleanTrapResult = JSRuntime.toBoolean(returnValue);
         if (!booleanTrapResult) {
            return false;
         } else if (!JSDynamicObject.isJSDynamicObject(target)) {
            return true;
         } else {
            boolean targetIsExtensible = JSObject.isExtensible((JSDynamicObject)target);
            if (targetIsExtensible) {
               return true;
            } else {
               Object targetProto = JSObject.getPrototype((JSDynamicObject)target);
               if (newPrototype != targetProto) {
                  throw Errors.createTypeErrorSameResultExpected();
               } else {
                  return true;
               }
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      return filterOwnPropertyKeys(ownPropertyKeysProxy(thisObj), strings, symbols);
   }

   private static List<Object> ownPropertyKeysProxy(JSDynamicObject thisObj) {
      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object ownKeysFn = getTrapFromObject(handler, OWN_KEYS);
      if (ownKeysFn == Undefined.instance) {
         return JSDynamicObject.isJSDynamicObject(target) ? JSObject.ownPropertyKeys((JSDynamicObject)target) : JSInteropUtil.keys(target);
      } else {
         Object trapResultArray = JSRuntime.call(ownKeysFn, handler, new Object[]{target});
         List<Object> trapResult = JSRuntime.createListFromArrayLikeAllowSymbolString(trapResultArray);
         if (!JSDynamicObject.isJSDynamicObject(target)) {
            List<Object> uncheckedResultKeys = new ArrayList<>();
            Boundaries.listAddAll(uncheckedResultKeys, trapResult);
            return uncheckedResultKeys;
         } else if (handler instanceof JSUncheckedProxyHandlerObject) {
            return trapResult;
         } else {
            JSContext context = JSObject.getJSContext(thisObj);
            if (context.getEcmaScriptVersion() >= 9 && containsDuplicateEntries(trapResult)) {
               throw Errors.createTypeError("trap result contains duplicate entries");
            } else {
               boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
               Iterable<Object> targetKeys = JSObject.ownPropertyKeys((JSDynamicObject)target);
               List<Object> targetConfigurableKeys = new ArrayList<>();
               List<Object> targetNonconfigurableKeys = new ArrayList<>();

               for (Object key : targetKeys) {
                  PropertyDescriptor desc = JSObject.getOwnProperty((JSDynamicObject)target, key);
                  if (desc != null && !desc.getConfigurable()) {
                     Boundaries.listAdd(targetNonconfigurableKeys, key);
                  } else {
                     Boundaries.listAdd(targetConfigurableKeys, key);
                  }
               }

               if (extensibleTarget && targetNonconfigurableKeys.isEmpty()) {
                  return trapResult;
               } else {
                  List<Object> uncheckedResultKeys = new ArrayList<>();
                  Boundaries.listAddAll(uncheckedResultKeys, trapResult);

                  assert trapResult.size() == uncheckedResultKeys.size();

                  for (Object keyx : targetNonconfigurableKeys) {
                     if (!uncheckedResultKeys.contains(keyx)) {
                        throw Errors.createTypeErrorOwnKeysTrapMissingKey(keyx);
                     }

                     while (uncheckedResultKeys.remove(keyx)) {
                     }
                  }

                  if (extensibleTarget) {
                     return trapResult;
                  } else {
                     for (Object keyx : targetConfigurableKeys) {
                        if (!uncheckedResultKeys.contains(keyx)) {
                           throw Errors.createTypeErrorOwnKeysTrapMissingKey(keyx);
                        }

                        while (uncheckedResultKeys.remove(keyx)) {
                        }
                     }

                     if (!uncheckedResultKeys.isEmpty()) {
                        throw Errors.createTypeError("'ownKeys' on proxy: trap returned extra keys but proxy target is non-extensible");
                     } else {
                        return trapResult;
                     }
                  }
               }
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean containsDuplicateEntries(List<Object> trapResult) {
      Set<Object> set = new HashSet<>();

      for (Object entry : trapResult) {
         if (!set.add(entry)) {
            return true;
         }
      }

      return false;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      JSDynamicObject handler = getHandlerChecked(thisObj);
      Object target = getTarget(thisObj);
      Object getOwnPropertyFn = getTrapFromObject(handler, GET_OWN_PROPERTY_DESCRIPTOR);
      if (getOwnPropertyFn == Undefined.instance) {
         if (JSDynamicObject.isJSDynamicObject(target)) {
            return JSObject.getOwnProperty((JSDynamicObject)target, key);
         } else if (Strings.isTString(key)) {
            return JSInteropUtil.getOwnProperty(target, (TruffleString)key);
         } else {
            assert key instanceof Symbol;

            return null;
         }
      } else {
         Object trapResultObj = checkTrapReturnValue(JSRuntime.call(getOwnPropertyFn, handler, new Object[]{target, key}));
         if (!JSDynamicObject.isJSDynamicObject(target)) {
            return JSRuntime.toPropertyDescriptor(trapResultObj);
         } else {
            PropertyDescriptor targetDesc = JSObject.getOwnProperty((JSDynamicObject)target, key);
            if (trapResultObj == Undefined.instance) {
               if (targetDesc == null) {
                  return null;
               } else if (targetDesc.hasConfigurable() && !targetDesc.getConfigurable()) {
                  throw Errors.createTypeErrorConfigurableExpected();
               } else {
                  boolean isExtensible = JSObject.isExtensible((JSDynamicObject)target);
                  if (!isExtensible) {
                     throw Errors.createTypeErrorProxyTargetNotExtensible();
                  } else {
                     return null;
                  }
               }
            } else {
               boolean extensibleTarget = JSObject.isExtensible((JSDynamicObject)target);
               PropertyDescriptor resultDesc = JSRuntime.toPropertyDescriptor(trapResultObj);
               completePropertyDescriptor(resultDesc);
               if (handler instanceof JSUncheckedProxyHandlerObject) {
                  return resultDesc;
               } else {
                  boolean valid = isCompatiblePropertyDescriptor(extensibleTarget, resultDesc, targetDesc);
                  if (!valid) {
                     throw Errors.createTypeError("not a valid descriptor");
                  } else {
                     if (!resultDesc.getConfigurable()) {
                        if (targetDesc == null || targetDesc.hasConfigurable() && targetDesc.getConfigurable()) {
                           throw Errors.createTypeErrorFormat(
                              "'getOwnPropertyDescriptor' on proxy: trap reported non-configurability for property '%s' which is either non-existent or configurable in the proxy target",
                              key
                           );
                        }

                        JSContext context = JSObject.getJSContext(thisObj);
                        if (context.getEcmaScriptVersion() >= 11 && resultDesc.hasWritable() && !resultDesc.getWritable() && targetDesc.getWritable()) {
                           throw Errors.createTypeError("target is missing the corresponding non-configurable and non-writable own property");
                        }
                     }

                     return resultDesc;
                  }
               }
            }
         }
      }
   }

   public static boolean isRevoked(JSDynamicObject proxy) {
      assert isJSProxy(proxy) : "Only proxy objects can be revoked";

      return getHandler(proxy) == Null.instance;
   }

   public static Object checkTrapReturnValue(Object trapResult) {
      if (!JSDynamicObject.isJSDynamicObject(trapResult) && trapResult != Undefined.instance) {
         throw Errors.createTypeError("proxy must return an object");
      } else {
         return trapResult;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object call(JSDynamicObject proxyObj, Object holder, Object[] arguments) {
      JSDynamicObject handler = getHandlerChecked(proxyObj);
      Object target = getTarget(proxyObj);
      Object trap = getTrapFromObject(handler, APPLY);
      if (trap == Undefined.instance) {
         return JSRuntime.call(target, holder, arguments);
      } else {
         JSContext ctx = JSObject.getJSContext(proxyObj);
         return JSRuntime.call(trap, handler, new Object[]{target, holder, JSArray.createConstant(ctx, JSRealm.get(null), arguments)});
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object construct(JSDynamicObject proxyObj, Object[] arguments) {
      if (!JSRuntime.isConstructorProxy(proxyObj)) {
         throw Errors.createTypeErrorNotAFunction(proxyObj);
      } else {
         JSDynamicObject handler = getHandlerChecked(proxyObj);
         Object target = getTarget(proxyObj);
         Object trap = getTrapFromObject(handler, CONSTRUCT);
         if (trap == Undefined.instance) {
            return JSRuntime.construct(target, arguments);
         } else {
            JSContext ctx = JSObject.getJSContext(proxyObj);
            Object result = JSRuntime.call(trap, handler, new Object[]{target, JSArray.createConstant(ctx, JSRealm.get(null), arguments), proxyObj});
            if (!JSRuntime.isObject(result)) {
               throw Errors.createTypeErrorNotAnObject(result);
            } else {
               return result;
            }
         }
      }
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getProxyPrototype();
   }

   public static JSFunctionData createProxyCallFunctionData(JSContext ctx) {
      return ctx.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.ProxyCall, c -> {
         RootCallTarget callTarget = new JSProxy.ProxyCallRootNode(c, false, false).getCallTarget();
         RootCallTarget constructTarget = new JSProxy.ProxyCallRootNode(c, true, false).getCallTarget();
         RootCallTarget constructNewTarget = new JSProxy.ProxyCallRootNode(c, true, true).getCallTarget();
         return JSFunctionData.create(c, callTarget, constructTarget, constructNewTarget, 0, PROXY_CALL, 0);
      });
   }

   private static final class ProxyCallRootNode extends JavaScriptRootNode {
      @Node.Child
      JSProxyCallNode proxyCallNode;

      ProxyCallRootNode(JSContext context, boolean isNew, boolean isNewTarget) {
         this.proxyCallNode = JSProxyCallNode.create(context, isNew, isNewTarget);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         return this.proxyCallNode.execute(frame.getArguments());
      }

      @Override
      public boolean isInternal() {
         return true;
      }
   }
}
