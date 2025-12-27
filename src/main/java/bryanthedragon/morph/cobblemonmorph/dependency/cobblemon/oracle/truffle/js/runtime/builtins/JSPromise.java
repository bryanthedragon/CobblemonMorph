package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.PromiseFunctionBuiltins;
import com.oracle.truffle.js.builtins.PromisePrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class JSPromise extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("Promise");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Promise.prototype");
   public static final JSPromise INSTANCE = new JSPromise();
   public static final TruffleString RESOLVE = Strings.constant("resolve");
   public static final TruffleString THEN = Strings.constant("then");
   public static final HiddenKey PROMISE_RESULT = new HiddenKey("PromiseResult");
   public static final HiddenKey PROMISE_IS_HANDLED = new HiddenKey("PromiseIsHandled");
   public static final HiddenKey PROMISE_FULFILL_REACTIONS = new HiddenKey("PromiseFulfillReactions");
   public static final HiddenKey PROMISE_REJECT_REACTIONS = new HiddenKey("PromiseRejectReactions");
   public static final HiddenKey PROMISE_ON_FINALLY = new HiddenKey("OnFinally");
   public static final HiddenKey PROMISE_FINALLY_CONSTRUCTOR = new HiddenKey("Constructor");
   public static final int PENDING = 0;
   public static final int FULFILLED = 1;
   public static final int REJECTED = 2;
   public static final int REJECTION_TRACKER_OPERATION_REJECT = 0;
   public static final int REJECTION_TRACKER_OPERATION_HANDLE = 1;
   public static final int REJECTION_TRACKER_OPERATION_REJECT_AFTER_RESOLVED = 2;
   public static final int REJECTION_TRACKER_OPERATION_RESOLVE_AFTER_RESOLVED = 3;

   private JSPromise() {
   }

   public static JSPromiseObject create(JSContext context, JSRealm realm) {
      return context.trackAllocation(JSPromiseObject.create(realm, context.getPromiseFactory(), 0));
   }

   public static JSPromiseObject create(JSContext context, Shape shape) {
      JSPromiseObject promise = JSPromiseObject.create(shape, 0);
      return context.trackAllocation(promise);
   }

   public static JSPromiseObject createWithoutPrototype(JSContext context) {
      Shape shape = context.getPromiseShapePrototypeInObject();
      return JSPromiseObject.create(shape, 0);
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return CLASS_NAME;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   public static boolean isJSPromise(Object obj) {
      return obj instanceof JSPromiseObject;
   }

   public static boolean isRejected(JSDynamicObject promise) {
      return 2 == getPromiseState(promise);
   }

   public static boolean isPending(JSDynamicObject promise) {
      return 0 == getPromiseState(promise);
   }

   public static boolean isFulfilled(JSDynamicObject promise) {
      return 1 == getPromiseState(promise);
   }

   public static int getPromiseState(JSDynamicObject promise) {
      assert isJSPromise(promise);

      return ((JSPromiseObject)promise).getPromiseState();
   }

   public static void setPromiseState(JSDynamicObject promise, int promiseState) {
      assert isJSPromise(promise);

      ((JSPromiseObject)promise).setPromiseState(promiseState);
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return JSRuntime.objectToDisplayString(
         obj,
         allowSideEffects,
         format,
         depth,
         CLASS_NAME,
         new TruffleString[]{Strings.PROMISE_STATUS, Strings.PROMISE_VALUE},
         new Object[]{getStatus(obj), getValue(obj)}
      );
   }

   private static TruffleString getStatus(JSDynamicObject obj) {
      if (isFulfilled(obj)) {
         return Strings.RESOLVED;
      } else if (isRejected(obj)) {
         return Strings.REJECTED;
      } else {
         assert isPending(obj);

         return Strings.PENDING;
      }
   }

   private static Object getValue(JSDynamicObject obj) {
      return JSDynamicObject.getOrDefault(obj, PROMISE_RESULT, Undefined.instance);
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
      JSContext context = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(context, prototype, constructor);
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, PromisePrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
      return prototype;
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, PromiseFunctionBuiltins.BUILTINS);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getPromisePrototype();
   }
}
