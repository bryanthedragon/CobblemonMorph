package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.WeakRefPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import java.lang.ref.WeakReference;

public final class JSWeakRef extends JSNonProxy implements JSConstructorFactory.Default, PrototypeSupplier {
   public static final JSWeakRef INSTANCE = new JSWeakRef();
   public static final TruffleString CLASS_NAME = Strings.constant("WeakRef");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("WeakRef.prototype");

   private JSWeakRef() {
   }

   public static JSWeakRefObject create(JSContext context, JSRealm realm, Object referent) {
      JSWeakRef.TruffleWeakReference<Object> weakReference = new JSWeakRef.TruffleWeakReference<>(referent);
      JSObjectFactory factory = context.getWeakRefFactory();
      JSWeakRefObject obj = factory.initProto(new JSWeakRefObject(factory.getShape(realm), weakReference), realm);
      context.addWeakRefTargetToSet(referent);
      return context.trackAllocation(obj);
   }

   public static JSWeakRef.TruffleWeakReference<?> getInternalWeakRef(JSDynamicObject obj) {
      assert isJSWeakRef(obj);

      return ((JSWeakRefObject)obj).getWeakReference();
   }

   @Override
   public JSDynamicObject createPrototype(final JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, WeakRefPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm);
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
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return Strings.addBrackets(this.getClassName());
   }

   public static boolean isJSWeakRef(Object obj) {
      return obj instanceof JSWeakRefObject;
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getWeakRefPrototype();
   }

   public static final class TruffleWeakReference<T> extends WeakReference<T> {
      public TruffleWeakReference(T t) {
         super(t);
      }
   }
}
