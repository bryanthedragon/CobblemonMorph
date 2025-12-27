package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.NumberFunctionBuiltins;
import com.oracle.truffle.js.builtins.NumberPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;

public final class JSNumber extends JSPrimitive implements JSConstructorFactory.WithFunctions {
   public static final TruffleString TYPE_NAME = Strings.HINT_NUMBER;
   public static final TruffleString CLASS_NAME = Strings.UC_NUMBER;
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Number.prototype");
   public static final JSNumber INSTANCE = new JSNumber();
   private static final double NUMBER_EPSILON = 2.220446E-16F;
   private static final double MAX_SAFE_INTEGER = 9.007199254740991E15;
   private static final double MIN_SAFE_INTEGER = -9.007199254740991E15;

   private JSNumber() {
   }

   public static JSNumberObject create(JSContext context, JSRealm realm, Number value) {
      JSNumberObject obj = JSNumberObject.create(realm, context.getNumberFactory(), value);
      return context.trackAllocation(obj);
   }

   private static Number getNumberField(JSDynamicObject obj) {
      assert isJSNumber(obj);

      return ((JSNumberObject)obj).getNumber();
   }

   public static Number valueOf(JSDynamicObject obj) {
      return getNumberField(obj);
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext context = realm.getContext();
      Shape protoShape = JSShape.createPrototypeShape(realm.getContext(), INSTANCE, realm.getObjectPrototype());
      JSObject numberPrototype = JSNumberObject.create(protoShape, 0);
      JSObjectUtil.setOrVerifyPrototype(context, numberPrototype, realm.getObjectPrototype());
      JSObjectUtil.putConstructorProperty(context, numberPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, numberPrototype, NumberPrototypeBuiltins.BUILTINS);
      return numberPrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   @Override
   public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
      JSConstructorFactory.WithFunctions.super.fillConstructor(realm, constructor);
      JSContext context = realm.getContext();
      JSObjectUtil.putDataProperty(context, constructor, Strings.NAN, Double.NaN);
      JSObjectUtil.putDataProperty(context, constructor, Strings.CAPS_POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
      JSObjectUtil.putDataProperty(context, constructor, Strings.CAPS_NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
      JSObjectUtil.putDataProperty(context, constructor, Strings.CAPS_MAX_VALUE, Double.MAX_VALUE);
      JSObjectUtil.putDataProperty(context, constructor, Strings.CAPS_MIN_VALUE, Double.MIN_VALUE);
      if (context.getEcmaScriptVersion() >= 6) {
         JSObjectUtil.putDataProperty(context, constructor, Strings.CAPS_EPSILON, 2.220446E-16F);
         JSObjectUtil.putDataProperty(context, constructor, Strings.CAPS_MAX_SAFE_INTEGER, 9.007199254740991E15);
         JSObjectUtil.putDataProperty(context, constructor, Strings.CAPS_MIN_SAFE_INTEGER, -9.007199254740991E15);
      }
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, NumberFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSNumber(Object obj) {
      return obj instanceof JSNumberObject;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   @Override
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      return this.getClassName(object);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
         return super.toDisplayStringImpl(obj, allowSideEffects, format, depth);
      } else {
         Number primitiveValue = valueOf(obj);
         return JSRuntime.objectToDisplayString(
            obj, allowSideEffects, format, depth, this.getBuiltinToStringTag(obj), new TruffleString[]{Strings.PRIMITIVE_VALUE}, new Object[]{primitiveValue}
         );
      }
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getNumberPrototype();
   }
}
