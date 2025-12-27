package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSDictionary;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import com.oracle.truffle.js.runtime.util.UnmodifiablePropertyKeyList;

public final class JSShape {
   public static final int NOT_EXTENSIBLE_FLAG = 1;
   public static final int SEALED_FLAG = 2;
   public static final int FROZEN_FLAG = 4;
   public static final int SEALED_FLAGS = 3;
   public static final int FROZEN_FLAGS = 7;
   public static final int EXTERNAL_PROPERTIES_FLAG = 8;

   private JSShape() {
   }

   public static Shape createPrototypeShape(JSContext context, JSClass jsclass, JSDynamicObject prototype) {
      assert prototype == Null.instance || JSRuntime.isObject(prototype);

      if (context.isMultiContext()) {
         return JSObjectUtil.getProtoChildShape(null, jsclass, context);
      } else {
         return prototype == Null.instance ? context.getEmptyShapeNullPrototype() : JSObjectUtil.getProtoChildShape(prototype, jsclass, context);
      }
   }

   static Shape createObjectShape(JSContext context, JSClass jsclass, JSDynamicObject prototype) {
      Shape rootShape = newBuilder(context, jsclass, prototype).build();
      return Shape.newBuilder(rootShape).addConstantProperty(JSObject.HIDDEN_PROTO, prototype, 0).build();
   }

   public static JSClass getJSClass(Shape shape) {
      return (JSClass)shape.getDynamicType();
   }

   public static Object getJSClassNoCast(Shape shape) {
      return shape.getDynamicType();
   }

   public static JSSharedData getSharedData(Shape shape) {
      return (JSSharedData)shape.getSharedData();
   }

   public static Shape getProtoChildTree(JSDynamicObject prototype, JSClass jsclass) {
      JSPrototypeData prototypeData = JSObjectUtil.getPrototypeData(prototype);
      return prototypeData != null ? prototypeData.getProtoChildTree(jsclass) : null;
   }

   public static boolean isExtensible(Shape shape) {
      return (shape.getFlags() & 1) == 0;
   }

   public static boolean isPrototypeInShape(Shape shape) {
      return getPrototypeProperty(shape).getLocation().isConstant();
   }

   public static Property getPrototypeProperty(Shape shape) {
      return shape.getProperty(JSObject.HIDDEN_PROTO);
   }

   public static Assumption getPropertyAssumption(Shape shape, Object key) {
      return getPropertyAssumption(shape, key, false);
   }

   public static Assumption getPropertyAssumption(Shape shape, Object key, boolean prototype) {
      assert JSRuntime.isPropertyKey(key) || key instanceof HiddenKey;

      return prototype ? shape.getLeafAssumption() : shape.getPropertyAssumption(key);
   }

   public static JSContext getJSContext(Shape shape) {
      return getSharedData(shape).getContext();
   }

   public static Assumption getPrototypeAssumption(Shape shape) {
      return getSharedData(shape).getPrototypeAssumption();
   }

   public static void invalidatePrototypeAssumption(Shape shape) {
      getSharedData(shape).invalidatePrototypeAssumption();
   }

   public static UnmodifiableArrayList<Property> getProperties(Shape shape) {
      return JSShapeData.getProperties(shape);
   }

   public static <T> UnmodifiablePropertyKeyList<T> getPropertyKeyList(Shape shape, boolean strings, boolean symbols) {
      return JSShapeData.getPropertyKeyList(shape, strings, symbols);
   }

   public static UnmodifiableArrayList<TruffleString> getEnumerablePropertyNames(Shape shape) {
      return JSShapeData.getEnumerablePropertyNames(shape);
   }

   public static UnmodifiableArrayList<Property> getPropertiesIfHasEnumerablePropertyNames(Shape shape) {
      return JSShapeData.getPropertiesIfHasEnumerablePropertyNames(shape);
   }

   public static Shape makeStaticRoot(JSClass jsclass) {
      return Shape.newBuilder().layout(getLayout(jsclass)).dynamicType(jsclass).build();
   }

   public static Shape makeEmptyRoot(JSClass jsclass, JSContext context) {
      return createObjectShape(context, jsclass, Null.instance);
   }

   public static Shape createRootWithNullProto(JSContext context, JSClass jsclass) {
      return createObjectShape(context, jsclass, Null.instance);
   }

   public static Shape createRootWithProto(JSContext context, JSClass jsclass, JSDynamicObject prototype) {
      return createObjectShape(context, jsclass, prototype);
   }

   public static Shape makeEmptyRootWithInstanceProto(JSContext context, JSClass jsclass) {
      return newBuilder(context, jsclass, null).build();
   }

   public static JSSharedData makeJSSharedData(JSContext context, JSDynamicObject proto) {
      return new JSSharedData(context, proto);
   }

   public static Class<? extends JSDynamicObject> getLayout(JSClass jsclass) {
      if (jsclass == JSOrdinary.INSTANCE || jsclass == JSDictionary.INSTANCE) {
         return JSOrdinaryObject.DefaultLayout.class;
      } else if (jsclass == JSOrdinary.INTERNAL_FIELD_INSTANCE) {
         return JSOrdinaryObject.InternalFieldLayout.class;
      } else if (jsclass == JSOrdinary.OVERLOADED_OPERATORS_INSTANCE) {
         return JSOverloadedOperatorsObject.class;
      } else {
         return jsclass == JSArray.INSTANCE ? JSArrayObject.class : JSDynamicObject.class;
      }
   }

   public static Shape.Builder newBuilder(JSContext context, JSClass jsclass, JSDynamicObject proto) {
      assert !context.isMultiContext() || proto == null || proto == Null.instance;

      return Shape.newBuilder()
         .layout(getLayout(jsclass))
         .dynamicType(jsclass)
         .sharedData(makeJSSharedData(context, proto))
         .shapeFlags(getDefaultShapeFlags(jsclass))
         .allowImplicitCastIntToDouble(true)
         .propertyAssumptions(!context.isMultiContext());
   }

   public static int getDefaultShapeFlags(JSClass jsclass) {
      return jsclass == JSDictionary.INSTANCE ? 8 : 0;
   }

   public static boolean hasExternalProperties(int shapeFlags) {
      return (shapeFlags & 8) != 0;
   }
}
