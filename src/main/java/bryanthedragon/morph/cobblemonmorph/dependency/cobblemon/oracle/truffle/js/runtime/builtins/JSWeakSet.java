package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.WeakSetPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import java.util.Map;
import java.util.WeakHashMap;

public final class JSWeakSet extends JSNonProxy implements JSConstructorFactory.Default, PrototypeSupplier {
   public static final JSWeakSet INSTANCE = new JSWeakSet();
   public static final TruffleString CLASS_NAME = Strings.constant("WeakSet");
   public static final TruffleString PROTOTYPE_NAME = Strings.concat(CLASS_NAME, Strings.DOT_PROTOTYPE);

   private JSWeakSet() {
   }

   public static JSWeakSetObject create(JSContext context, JSRealm realm) {
      JSObjectFactory factory = context.getWeakSetFactory();
      JSWeakSetObject obj = factory.initProto(new JSWeakSetObject(factory.getShape(realm), newWeakHashMap()), realm);
      return context.trackAllocation(obj);
   }

   @CompilerDirectives.TruffleBoundary
   private static Map<Object, Object> newWeakHashMap() {
      return new WeakHashMap<>();
   }

   public static Map<Object, Object> getInternalWeakMap(JSDynamicObject obj) {
      assert isJSWeakSet(obj);

      return ((JSWeakSetObject)obj).getWeakHashMap();
   }

   @Override
   public JSDynamicObject createPrototype(final JSRealm realm, JSFunctionObject ctor) {
      JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, WeakSetPrototypeBuiltins.BUILTINS);
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

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode() ? Strings.addBrackets(this.getClassName()) : this.getClassName();
   }

   public static boolean isJSWeakSet(Object obj) {
      return obj instanceof JSWeakSetObject;
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getWeakSetPrototype();
   }
}
