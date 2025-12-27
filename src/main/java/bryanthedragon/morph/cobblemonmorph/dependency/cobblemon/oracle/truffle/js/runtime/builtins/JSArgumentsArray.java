package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSArgumentsArray extends JSAbstractArgumentsArray {
   public static final JSArgumentsArray INSTANCE = new JSArgumentsArray();

   private JSArgumentsArray() {
   }

   public static JSArgumentsObject.Unmapped createUnmapped(Shape shape, Object[] elements) {
      return new JSArgumentsObject.Unmapped(shape, ScriptArray.createConstantArray(elements), elements, elements.length);
   }

   public static JSArgumentsObject.Mapped createMapped(Shape shape, Object[] elements) {
      return new JSArgumentsObject.Mapped(shape, ScriptArray.createConstantArray(elements), elements, elements.length);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSArgumentsObject createStrictSlow(JSRealm realm, Object[] elements) {
      JSContext context = realm.getContext();
      JSObjectFactory factory = context.getStrictArgumentsFactory();
      JSArgumentsObject argumentsObject = createUnmapped(factory.getShape(realm), elements);
      factory.initProto(argumentsObject, realm);
      JSObjectUtil.putDataProperty(context, argumentsObject, LENGTH, elements.length, JSAttributes.configurableNotEnumerableWritable());
      JSObjectUtil.putDataProperty(
         context, argumentsObject, Symbol.SYMBOL_ITERATOR, realm.getArrayProtoValuesIterator(), JSAttributes.configurableNotEnumerableWritable()
      );
      Accessor throwerAccessor = realm.getThrowerAccessor();
      JSObjectUtil.putBuiltinAccessorProperty(argumentsObject, CALLEE, throwerAccessor, JSAttributes.notConfigurableNotEnumerable());
      if (context.getEcmaScriptVersion() < 8) {
         JSObjectUtil.putBuiltinAccessorProperty(argumentsObject, CALLER, throwerAccessor, JSAttributes.notConfigurableNotEnumerable());
      }

      return context.trackAllocation(argumentsObject);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSArgumentsObject createNonStrictSlow(JSRealm realm, Object[] elements, JSDynamicObject callee) {
      JSContext context = realm.getContext();
      JSObjectFactory factory = context.getNonStrictArgumentsFactory();
      JSArgumentsObject argumentsObject = createMapped(factory.getShape(realm), elements);
      factory.initProto(argumentsObject, realm);
      JSObjectUtil.putDataProperty(context, argumentsObject, LENGTH, elements.length, JSAttributes.configurableNotEnumerableWritable());
      JSObjectUtil.putDataProperty(
         context, argumentsObject, Symbol.SYMBOL_ITERATOR, realm.getArrayProtoValuesIterator(), JSAttributes.configurableNotEnumerableWritable()
      );
      JSObjectUtil.putDataProperty(context, argumentsObject, CALLEE, callee, JSAttributes.configurableNotEnumerableWritable());
      return context.trackAllocation(argumentsObject);
   }

   public static boolean isJSArgumentsObject(Object obj) {
      return obj instanceof JSArgumentsObject;
   }

   public static boolean isJSFastArgumentsObject(Object obj) {
      return isJSArgumentsObject(obj) && isInstance((JSArgumentsObject)obj, INSTANCE);
   }
}
