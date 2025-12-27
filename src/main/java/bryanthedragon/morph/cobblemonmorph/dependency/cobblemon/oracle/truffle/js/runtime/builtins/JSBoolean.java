package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.BooleanPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;

public final class JSBoolean extends JSPrimitive implements JSConstructorFactory.Default {
   public static final TruffleString TYPE_NAME = Strings.LC_BOOLEAN;
   public static final TruffleString CLASS_NAME = Strings.UC_BOOLEAN;
   public static final TruffleString PROTOTYPE_NAME = Strings.BOOLEAN_PROTOTYPE;
   public static final TruffleString TRUE_NAME = Strings.TRUE;
   public static final TruffleString FALSE_NAME = Strings.FALSE;
   public static final JSBoolean INSTANCE = new JSBoolean();

   private JSBoolean() {
   }

   public static JSBooleanObject create(JSContext context, JSRealm realm, boolean value) {
      JSBooleanObject obj = JSBooleanObject.create(realm, context.getBooleanFactory(), value);
      return context.trackAllocation(obj);
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      Shape protoShape = JSShape.createPrototypeShape(realm.getContext(), INSTANCE, realm.getObjectPrototype());
      JSObject booleanPrototype = JSBooleanObject.create(protoShape, false);
      JSObjectUtil.setOrVerifyPrototype(ctx, booleanPrototype, realm.getObjectPrototype());
      JSObjectUtil.putConstructorProperty(ctx, booleanPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, booleanPrototype, BooleanPrototypeBuiltins.BUILTINS);
      return booleanPrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm);
   }

   public static boolean valueOf(JSDynamicObject obj) {
      assert isJSBoolean(obj);

      return ((JSBooleanObject)obj).getBooleanValue();
   }

   public static boolean isJSBoolean(Object obj) {
      return obj instanceof JSBooleanObject;
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
   public static JSException noBooleanError() {
      throw Errors.createTypeError("not a Boolean object");
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
         return Strings.concatAll(Strings.BRACKET_BOOLEAN_SPC, Strings.fromBoolean(valueOf(obj)), Strings.BRACKET_CLOSE);
      } else {
         boolean primitiveValue = valueOf(obj);
         return JSRuntime.objectToDisplayString(
            obj, allowSideEffects, format, depth, this.getBuiltinToStringTag(obj), new TruffleString[]{Strings.PRIMITIVE_VALUE}, new Object[]{primitiveValue}
         );
      }
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getBooleanPrototype();
   }
}
