package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Shape;
import java.lang.invoke.VarHandle;
import java.util.Map;
import java.util.Objects;

public abstract class LayoutImpl extends Layout {
   private static final int INT_TO_DOUBLE_FLAG = 1;
   private static final int INT_TO_LONG_FLAG = 2;
   protected final LayoutStrategy strategy;
   protected final Class<? extends DynamicObject> clazz;
   private final int allowedImplicitCasts;
   static final LayoutImpl.CoreAccess ACCESS = new LayoutImpl.CoreAccess();

   protected LayoutImpl(Class<? extends DynamicObject> clazz, LayoutStrategy strategy, int implicitCastFlags) {
      this.strategy = strategy;
      this.clazz = Objects.requireNonNull(clazz);
      this.allowedImplicitCasts = implicitCastFlags;
   }

   protected abstract boolean isLegacyLayout();

   @Override
   public Class<? extends DynamicObject> getType() {
      return this.clazz;
   }

   @Override
   protected final Shape buildShape(Object dynamicType, Object sharedData, int flags, Assumption singleContextAssumption) {
      return this.newShape(dynamicType, sharedData, flags, null);
   }

   protected abstract ShapeImpl newShape(Object objectType, Object sharedData, int flags, Assumption singleContextAssumption);

   public boolean isAllowedIntToDouble() {
      return (this.allowedImplicitCasts & 1) != 0;
   }

   public boolean isAllowedIntToLong() {
      return (this.allowedImplicitCasts & 2) != 0;
   }

   protected abstract boolean hasObjectExtensionArray();

   protected abstract boolean hasPrimitiveExtensionArray();

   protected abstract int getObjectFieldCount();

   protected abstract int getPrimitiveFieldCount();

   @Override
   public abstract Shape.Allocator createAllocator();

   public LayoutStrategy getStrategy() {
      return this.strategy;
   }

   @Override
   public String toString() {
      return "Layout[" + this.clazz.getName() + "]";
   }

   static void resetNativeImageState() {
      assert TruffleOptions.AOT : "Only supported during image generation";

      ((CoreLayoutFactory)getFactory()).resetNativeImageState();
   }

   static void initializeDynamicObjectLayout(Class<?> dynamicObjectClass) {
      assert TruffleOptions.AOT : "Only supported during image generation";

      ((CoreLayoutFactory)getFactory()).registerLayoutClass(dynamicObjectClass.asSubclass(DynamicObject.class));
   }

   static final class CoreAccess extends LayoutImpl.Support {
      private CoreAccess() {
      }
   }

   protected abstract static class Support extends Layout.Access {
      public final void setShapeWithStoreFence(DynamicObject object, Shape shape) {
         if (shape.isShared()) {
            VarHandle.storeStoreFence();
         }

         super.setShape(object, shape);
      }

      public final void grow(DynamicObject object, Shape thisShape, Shape otherShape) {
         DynamicObjectSupport.grow(object, thisShape, otherShape);
      }

      public final void resize(DynamicObject object, Shape thisShape, Shape otherShape) {
         DynamicObjectSupport.resize(object, thisShape, otherShape);
      }

      public final void invalidateAllPropertyAssumptions(Shape shape) {
         DynamicObjectSupport.invalidateAllPropertyAssumptions(shape);
      }

      public final void trimToSize(DynamicObject object, Shape thisShape, Shape otherShape) {
         DynamicObjectSupport.trimToSize(object, thisShape, otherShape);
      }

      public final Map<Object, Object> archive(DynamicObject object) {
         return DynamicObjectSupport.archive(object);
      }

      public final boolean verifyValues(DynamicObject object, Map<Object, Object> archive) {
         return DynamicObjectSupport.verifyValues(object, archive);
      }

      protected void arrayCopy(Object[] from, Object[] to, int length) {
         System.arraycopy(from, 0, to, 0, length);
      }

      protected void arrayCopy(int[] from, int[] to, int length) {
         System.arraycopy(from, 0, to, 0, length);
      }
   }
}
