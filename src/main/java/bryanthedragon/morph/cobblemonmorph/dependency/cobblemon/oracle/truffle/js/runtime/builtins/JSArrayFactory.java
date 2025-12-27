package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.CompilableBiFunction;

public abstract class JSArrayFactory {
   protected final JSContext context;
   @CompilerDirectives.CompilationFinal
   private DynamicObjectLibrary setProto;

   static JSArrayFactory create(JSContext context, Shape shape, PrototypeSupplier prototypeSupplier) {
      return new JSArrayFactory.WithShape(context, shape, prototypeSupplier);
   }

   static JSArrayFactory forArray(JSObjectFactory.IntrinsicBuilder builder) {
      return new JSArrayFactory.Intrinsic(builder.getContext(), JSArray.INSTANCE::makeInitialShape, builder.nextIndex());
   }

   static JSArrayFactory forArgumentsObject(JSObjectFactory.IntrinsicBuilder builder, boolean mapped) {
      return new JSArrayFactory.ArgumentsObject(builder.getContext(), builder.nextIndex(), mapped);
   }

   protected JSArrayFactory(JSContext context) {
      this.context = context;
   }

   public final JSDynamicObject createWithRealm(
      JSRealm realm,
      ScriptArray arrayType,
      Object array,
      ArrayAllocationSite site,
      long length,
      int usedLength,
      int indexOffset,
      int arrayOffset,
      int holeCount
   ) {
      return this.createWithPrototype(realm, this.getPrototype(realm), arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
   }

   public final JSDynamicObject createWithPrototype(
      JSRealm realm,
      JSDynamicObject prototype,
      ScriptArray arrayType,
      Object array,
      ArrayAllocationSite site,
      long length,
      int usedLength,
      int indexOffset,
      int arrayOffset,
      int holeCount
   ) {
      assert prototype != null;

      Shape shape = this.getShape(realm, prototype);
      if (this.isInObjectProto()) {
         JSDynamicObject obj = this.newInstance(shape, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
         this.setPrototype(obj, prototype);
         return obj;
      } else {
         assert JSObjectFactory.verifyPrototype(shape, prototype);

         return this.newInstance(shape, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
      }
   }

   protected JSDynamicObject newInstance(
      Shape shape, ScriptArray arrayType, Object array, ArrayAllocationSite site, long length, int usedLength, int indexOffset, int arrayOffset, int holeCount
   ) {
      return JSArrayObject.create(shape, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
   }

   protected abstract JSDynamicObject getPrototype(JSRealm realm);

   protected abstract Shape getShape(JSRealm realm, JSDynamicObject prototype);

   protected final void setPrototype(JSDynamicObject obj, JSDynamicObject prototype) {
      if (this.setProto == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.setProto = this.context.adoptNode(JSObjectUtil.createCached(JSObject.HIDDEN_PROTO, obj));
      }

      Properties.put(this.setProto, obj, JSObject.HIDDEN_PROTO, prototype);
   }

   protected final boolean isInObjectProto() {
      return this.context.isMultiContext();
   }

   private static final class ArgumentsObject extends JSArrayFactory.Intrinsic {
      private final boolean mapped;

      protected ArgumentsObject(JSContext context, int slot, boolean mapped) {
         super(context, JSObjectFactory.defaultShapeSupplier(JSArgumentsArray.INSTANCE), slot);
         this.mapped = mapped;
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         return realm.getObjectPrototype();
      }

      @Override
      protected JSDynamicObject newInstance(
         Shape shape,
         ScriptArray arrayType,
         Object array,
         ArrayAllocationSite site,
         long length,
         int usedLength,
         int indexOffset,
         int arrayOffset,
         int holeCount
      ) {
         Object[] elements = (Object[])array;
         return (JSDynamicObject)(this.mapped ? JSArgumentsArray.createMapped(shape, elements) : JSArgumentsArray.createUnmapped(shape, elements));
      }
   }

   private static class Intrinsic extends JSArrayFactory {
      private final int slot;
      private final CompilableBiFunction<JSContext, JSDynamicObject, Shape> shapeSupplier;
      @CompilerDirectives.CompilationFinal
      private Shape shape;

      protected Intrinsic(JSContext context, CompilableBiFunction<JSContext, JSDynamicObject, Shape> shapeSupplier, int slot) {
         super(context);
         this.shapeSupplier = shapeSupplier;
         this.slot = slot;
      }

      @Override
      protected final Shape getShape(JSRealm realm, JSDynamicObject prototype) {
         if (this.context.isMultiContext()) {
            if (this.shape == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.shape = this.shapeSupplier.apply(this.context, null);

               assert this.isInObjectProto() == JSObjectFactory.hasInObjectProto(this.shape);
            }

            return this.shape;
         } else {
            Shape realmShape = realm.getObjectFactories().shapes[this.slot];
            if (realmShape == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Shape newShape = this.shapeSupplier.apply(this.context, prototype);
               realmShape = realm.getObjectFactories().shapes[this.slot] = newShape;

               assert this.isInObjectProto() == JSObjectFactory.hasInObjectProto(realmShape);
            }

            return realmShape;
         }
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         return realm.getArrayPrototype();
      }
   }

   private static final class WithShape extends JSArrayFactory {
      private final Shape shape;
      private final PrototypeSupplier prototypeSupplier;

      protected WithShape(JSContext context, Shape shape, PrototypeSupplier prototypeSupplier) {
         super(context);
         this.shape = shape;
         this.prototypeSupplier = prototypeSupplier;
      }

      @Override
      protected JSDynamicObject getPrototype(JSRealm realm) {
         return this.prototypeSupplier.getIntrinsicDefaultProto(realm);
      }

      @Override
      protected Shape getShape(JSRealm realm, JSDynamicObject prototype) {
         return this.shape;
      }
   }
}
