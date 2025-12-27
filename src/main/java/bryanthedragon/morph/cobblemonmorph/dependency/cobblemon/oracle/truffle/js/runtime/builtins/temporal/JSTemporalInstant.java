package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalInstantFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalInstantPrototypeBuiltins;
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

public final class JSTemporalInstant extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final JSTemporalInstant INSTANCE = new JSTemporalInstant();
   public static final TruffleString CLASS_NAME = Strings.constant("Instant");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Instant.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.Instant");

   private JSTemporalInstant() {
   }

   public static JSTemporalInstantObject create(JSContext context, BigInt nanoseconds) {
      return create(context, JSRealm.get(null), nanoseconds);
   }

   public static JSTemporalInstantObject create(JSContext context, JSRealm realm, BigInt nanoseconds) {
      assert TemporalUtil.isValidEpochNanoseconds(nanoseconds);

      JSObjectFactory factory = context.getTemporalInstantFactory();
      JSTemporalInstantObject obj = factory.initProto(new JSTemporalInstantObject(factory.getShape(realm), nanoseconds), realm);
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
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalInstantPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.EPOCH_SECONDS, realm.lookupAccessor(TemporalInstantPrototypeBuiltins.BUILTINS, TemporalConstants.EPOCH_SECONDS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.EPOCH_MILLISECONDS, realm.lookupAccessor(TemporalInstantPrototypeBuiltins.BUILTINS, TemporalConstants.EPOCH_MILLISECONDS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.EPOCH_MICROSECONDS, realm.lookupAccessor(TemporalInstantPrototypeBuiltins.BUILTINS, TemporalConstants.EPOCH_MICROSECONDS)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.EPOCH_NANOSECONDS, realm.lookupAccessor(TemporalInstantPrototypeBuiltins.BUILTINS, TemporalConstants.EPOCH_NANOSECONDS)
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
      return realm.getTemporalInstantPrototype();
   }

   @Override
   public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
      JSConstructorFactory.WithFunctionsAndSpecies.super.fillConstructor(realm, constructor);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, TemporalInstantFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSTemporalInstant(Object obj) {
      return obj instanceof JSTemporalInstantObject;
   }
}
