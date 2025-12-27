package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalTimeZoneFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalTimeZonePrototypeBuiltins;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public final class JSTemporalTimeZone extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final JSTemporalTimeZone INSTANCE = new JSTemporalTimeZone();
   public static final TruffleString CLASS_NAME = Strings.constant("TimeZone");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("TimeZone.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.TimeZone");

   private JSTemporalTimeZone() {
   }

   public static JSTemporalTimeZoneObject create(JSContext context, BigInt nanoseconds, TruffleString identifier) {
      JSRealm realm = JSRealm.get(null);
      return create(context, realm, nanoseconds, identifier);
   }

   public static JSTemporalTimeZoneObject create(JSContext context, JSRealm realm, BigInt nanoseconds, TruffleString identifier) {
      assert TemporalUtil.isValidEpochNanoseconds(nanoseconds);

      JSObjectFactory factory = context.getTemporalTimeZoneFactory();
      JSTemporalTimeZoneObject obj = factory.initProto(new JSTemporalTimeZoneObject(factory.getShape(realm), nanoseconds, identifier), realm);
      return context.trackAllocation(obj);
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return TO_STRING_TAG;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
      JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalTimeZonePrototypeBuiltins.BUILTINS);
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.ID, realm.lookupAccessor(TemporalTimeZonePrototypeBuiltins.BUILTINS, TemporalConstants.ID)
      );
      JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getTemporalTimeZonePrototype();
   }

   @Override
   public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
      JSConstructorFactory.WithFunctionsAndSpecies.super.fillConstructor(realm, constructor);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, TemporalTimeZoneFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSTemporalTimeZone(Object obj) {
      return obj instanceof JSTemporalTimeZoneObject;
   }
}
