package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class JSPrimitive extends JSNonProxy implements PrototypeSupplier {
   protected JSPrimitive() {
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final Object getHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      assert this == JSNumber.INSTANCE || this == JSString.INSTANCE || this == JSBoolean.INSTANCE || this == JSBigInt.INSTANCE;

      Object propertyValue = super.getHelper(store, thisObj, key, encapsulatingNode);
      if (Strings.isTString(key) && allowJavaMembersFor(thisObj)) {
         JSContext context = JSObject.getJSContext(store);
         if (context.isOptionNashornCompatibilityMode()) {
            JSRealm realm = JSRealm.get(null);
            if (realm.isJavaInteropEnabled() && propertyValue == null) {
               return getJavaProperty(thisObj, Strings.toJavaString((TruffleString)key), realm);
            }
         }
      }

      return propertyValue;
   }

   private static Object getJavaProperty(Object thisObj, String name, JSRealm realm) {
      String thisStr = Strings.toJavaString((TruffleString)thisObj);
      Object boxedString = realm.getEnv().asBoxedGuestValue(thisStr);

      try {
         return InteropLibrary.getUncached().readMember(boxedString, name);
      } catch (UnsupportedMessageException | UnknownIdentifierException var6) {
         return Undefined.instance;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getMethodHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      if (Strings.isTString(key) && allowJavaMembersFor(thisObj)) {
         JSContext context = JSObject.getJSContext(store);
         if (context.isOptionNashornCompatibilityMode()) {
            JSRealm realm = JSRealm.get(null);
            if (realm.isJavaInteropEnabled() && this.hasOwnProperty(store, key)) {
               Object method = getJavaMethod(thisObj, Strings.toJavaString((TruffleString)key), realm);
               if (method != null) {
                  return method;
               }
            }
         }
      }

      return super.getMethodHelper(store, thisObj, key, encapsulatingNode);
   }

   private static Object getJavaMethod(Object thisObj, String name, JSRealm realm) {
      String thisStr = Strings.toJavaString((TruffleString)thisObj);
      Object boxedString = realm.getEnv().asBoxedGuestValue(thisStr);

      try {
         return InteropLibrary.getUncached().readMember(boxedString, name);
      } catch (UnsupportedMessageException | UnknownIdentifierException var6) {
         return null;
      }
   }

   private static boolean allowJavaMembersFor(Object thisObj) {
      return thisObj instanceof TruffleString;
   }
}
