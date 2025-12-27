package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.util.CompilableBiFunction;
import java.util.Objects;

public abstract class JSObjectFactory {
   protected final JSContext context;
   private final boolean inObjectProto;
   @CompilerDirectives.CompilationFinal
   private DynamicObjectLibrary setProto;

   public static JSObjectFactory.UnboundProto createUnbound(JSContext context, Shape factory) {
      return new JSObjectFactory.UnboundProto(context, factory);
   }

   public static JSObjectFactory.BoundProto createBound(JSContext context, JSDynamicObject prototype, Shape factory) {
      return new JSObjectFactory.BoundProto(context, prototype, factory);
   }

   public static JSObjectFactory createDefault(JSContext context, PrototypeSupplier prototypeSupplier, Shape factory) {
      return new JSObjectFactory.Eager(context, prototypeSupplier, factory);
   }

   static JSObjectFactory createIntrinsic(
      JSContext context, PrototypeSupplier prototypeSupplier, CompilableBiFunction<JSContext, JSDynamicObject, Shape> shapeSupplier, int slot
   ) {
      return new JSObjectFactory.LazySupplier(context, prototypeSupplier, shapeSupplier, slot);
   }

   static <T extends JSClass & PrototypeSupplier> JSObjectFactory createIntrinsic(JSContext context, T jsclass, int slot) {
      return new JSObjectFactory.LazyJSClass(context, jsclass, slot);
   }

   static CompilableBiFunction<JSContext, JSDynamicObject, Shape> defaultShapeSupplier(JSClass jsclass) {
      return (ctx, proto) -> JSObjectUtil.getProtoChildShape(proto, jsclass, ctx);
   }

   protected JSObjectFactory(JSContext context, boolean inObjectProto) {
      this.context = context;
      this.inObjectProto = inObjectProto;
   }

   static boolean verifyPrototype(Shape shape, JSDynamicObject prototype) {
      return JSShape.getPrototypeProperty(shape).getLocation().isConstant()
         && JSShape.getPrototypeProperty(shape).getLocation().getConstantValue() == prototype;
   }

   protected abstract JSDynamicObject getPrototype(JSRealm realm);

   static boolean hasInObjectProto(Shape shape) {
      Property prototypeProperty = JSShape.getPrototypeProperty(shape);
      return prototypeProperty == null || !prototypeProperty.getLocation().isConstant();
   }

   protected abstract Shape getShape(JSRealm realm, JSDynamicObject prototype);

   public final Shape getShape(JSRealm realm) {
      return this.getShape(realm, this.getPrototype(realm));
   }

   public final <T extends JSDynamicObject> T initProto(T obj, JSRealm realm) {
      return this.initProto(obj, this.getPrototype(realm));
   }

   public final <T extends JSDynamicObject> T initProto(T obj, JSDynamicObject prototype) {
      if (this.isInObjectProto()) {
         this.setPrototype(obj, prototype);
      } else {
         assert verifyPrototype(obj.getShape(), prototype);
      }

      return obj;
   }

   protected void setPrototype(JSDynamicObject obj, JSDynamicObject prototype) {
      if (this.setProto == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.setProto = this.context.adoptNode(JSObjectUtil.createCached(JSObject.HIDDEN_PROTO, obj));
      }

      Properties.put(this.setProto, obj, JSObject.HIDDEN_PROTO, prototype);
   }

   public final <T extends JSDynamicObject> T trackAllocation(T obj) {
      return this.context.trackAllocation(obj);
   }

   protected final boolean isInObjectProto() {
      return this.inObjectProto && this.context.isMultiContext();
   }

   public static final class BoundProto extends JSObjectFactory {
      private final JSDynamicObject prototype;
      private final Shape factory;

      protected BoundProto(JSContext context, JSDynamicObject prototype, Shape factory) {
         super(context, hasInObjectProto(factory));
         this.prototype = Objects.requireNonNull(prototype);
         this.factory = factory;
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         return this.prototype;
      }

      @Override
      protected Shape getShape(JSRealm realm, JSDynamicObject proto) {
         assert proto == this.prototype;

         return this.factory;
      }
   }

   private static final class Eager extends JSObjectFactory {
      protected final PrototypeSupplier prototypeSupplier;
      protected final Shape factory;

      protected Eager(JSContext context, PrototypeSupplier prototypeSupplier, Shape factory) {
         super(context, hasInObjectProto(factory));
         this.prototypeSupplier = prototypeSupplier;
         this.factory = factory;
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         return this.prototypeSupplier.getIntrinsicDefaultProto(realm);
      }

      @Override
      protected Shape getShape(JSRealm realm, JSDynamicObject prototype) {
         return this.factory;
      }
   }

   public static final class IntrinsicBuilder {
      private final JSContext context;
      private int count;
      private boolean closed;

      public IntrinsicBuilder(JSContext context) {
         this.context = context;
      }

      public JSObjectFactory create(PrototypeSupplier prototypeSupplier, CompilableBiFunction<JSContext, JSDynamicObject, Shape> shapeSupplier) {
         int index = this.nextIndex();
         return JSObjectFactory.createIntrinsic(this.context, prototypeSupplier, shapeSupplier, index);
      }

      public JSObjectFactory create(PrototypeSupplier prototypeSupplier, JSClass jsclass) {
         int index = this.nextIndex();
         return JSObjectFactory.createIntrinsic(this.context, prototypeSupplier, JSObjectFactory.defaultShapeSupplier(jsclass), index);
      }

      public <T extends JSClass & PrototypeSupplier> JSObjectFactory create(T jsclass) {
         int index = this.nextIndex();
         return JSObjectFactory.createIntrinsic(this.context, jsclass, index);
      }

      public JSFunctionFactory function(
         PrototypeSupplier intrinsicDefaultProto, boolean isStrict, boolean isConstructor, boolean isGenerator, boolean isBound, boolean isAsync
      ) {
         JSObjectFactory objectFactory = this.create(
            intrinsicDefaultProto,
            (ctx, prototype) -> JSFunctionFactory.makeShape(ctx, prototype, isStrict, false, isConstructor, isGenerator, isBound, isAsync)
         );
         return JSFunctionFactory.createIntrinsic(this.context, objectFactory, isStrict, isConstructor, isGenerator, isBound, isAsync);
      }

      int nextIndex() {
         assert !this.closed;

         return this.count++;
      }

      public int finish() {
         assert !this.closed;

         this.closed = true;
         return this.count;
      }

      JSContext getContext() {
         return this.context;
      }
   }

   private abstract static class Lazy extends JSObjectFactory {
      @CompilerDirectives.CompilationFinal
      private Shape factory;
      private final int slot;

      protected Lazy(JSContext context, int slot) {
         super(context, context.isMultiContext());
         this.slot = slot;
      }

      @Override
      protected final Shape getShape(JSRealm realm, JSDynamicObject prototype) {
         CompilerAsserts.partialEvaluationConstant(this);
         if (this.context.isMultiContext()) {
            if (this.factory == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.factory = this.makeInitialShape(this.isInObjectProto() ? null : prototype);

               assert this.isInObjectProto() == hasInObjectProto(this.factory);
            }

            return this.factory;
         } else {
            Shape realmFactory = realm.getObjectFactories().shapes[this.slot];
            if (realmFactory == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Shape newFactory = this.makeInitialShape(prototype);
               realmFactory = realm.getObjectFactories().shapes[this.slot] = newFactory;

               assert this.isInObjectProto() == hasInObjectProto(realmFactory);
            }

            return realmFactory;
         }
      }

      protected abstract Shape makeInitialShape(JSDynamicObject prototype);
   }

   private static final class LazyJSClass<T extends JSClass & PrototypeSupplier> extends JSObjectFactory.Lazy {
      protected final T jsclass;

      protected LazyJSClass(JSContext context, T jsclass, int slot) {
         super(context, slot);
         this.jsclass = jsclass;
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         return this.jsclass.getIntrinsicDefaultProto(realm);
      }

      @Override
      protected Shape makeInitialShape(JSDynamicObject prototype) {
         return this.jsclass.makeInitialShape(this.context, prototype);
      }
   }

   private static final class LazySupplier extends JSObjectFactory.Lazy {
      protected final PrototypeSupplier prototypeSupplier;
      protected final CompilableBiFunction<JSContext, JSDynamicObject, Shape> shapeSupplier;

      protected LazySupplier(
         JSContext context, PrototypeSupplier prototypeSupplier, CompilableBiFunction<JSContext, JSDynamicObject, Shape> shapeSupplier, int slot
      ) {
         super(context, slot);
         this.prototypeSupplier = prototypeSupplier;
         this.shapeSupplier = shapeSupplier;
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         return this.prototypeSupplier.getIntrinsicDefaultProto(realm);
      }

      @Override
      protected Shape makeInitialShape(JSDynamicObject prototype) {
         return this.shapeSupplier.apply(this.context, prototype);
      }
   }

   public static final class RealmData {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final Shape[] shapes;

      public RealmData(int count) {
         this.shapes = new Shape[count];
      }
   }

   public static final class UnboundProto extends JSObjectFactory {
      private final Shape factory;

      protected UnboundProto(JSContext context, Shape factory) {
         super(context, hasInObjectProto(factory));
         this.factory = factory;
      }

      @Override
      protected Shape getShape(JSRealm realm, JSDynamicObject proto) {
         return this.factory;
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         throw Errors.shouldNotReachHere();
      }
   }
}
