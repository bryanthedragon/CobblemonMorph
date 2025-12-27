package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.BigIntFunctionBuiltins;
import com.oracle.truffle.js.builtins.BigIntPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSBigInt extends JSPrimitive implements JSConstructorFactory.WithFunctions {
   public static final TruffleString TYPE_NAME = Strings.constant("bigint");
   public static final TruffleString CLASS_NAME = Strings.constant("BigInt");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("BigInt.prototype");
   public static final JSBigInt INSTANCE = new JSBigInt();

   private JSBigInt() {
   }

   public static JSBigIntObject create(JSContext context, JSRealm realm, BigInt value) {
      JSBigIntObject obj = JSBigIntObject.create(realm, context.getBigIntFactory(), value);
      return context.trackAllocation(obj);
   }

   private static BigInt getBigIntegerField(JSDynamicObject obj) {
      assert isJSBigInt(obj);

      return ((JSBigIntObject)obj).getBigIntValue();
   }

   public static BigInt valueOf(JSDynamicObject obj) {
      return getBigIntegerField(obj);
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext context = realm.getContext();
      JSObject bigIntPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(context, bigIntPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, bigIntPrototype, BigIntPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(bigIntPrototype, CLASS_NAME);
      return bigIntPrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, BigIntFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSBigInt(Object obj) {
      return obj instanceof JSBigIntObject;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
         return super.toDisplayStringImpl(obj, allowSideEffects, format, depth);
      } else {
         BigInt primitiveValue = valueOf(obj);
         return JSRuntime.objectToDisplayString(
            obj, allowSideEffects, format, depth, this.getBuiltinToStringTag(obj), new TruffleString[]{Strings.PRIMITIVE_VALUE}, new Object[]{primitiveValue}
         );
      }
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getBigIntPrototype();
   }
}
