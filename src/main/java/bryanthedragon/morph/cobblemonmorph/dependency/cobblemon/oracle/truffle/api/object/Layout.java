package com.oracle.truffle.api.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import java.lang.annotation.Annotation;
import java.util.EnumSet;
import java.util.ServiceLoader;

@Deprecated(since = "21.1")
public abstract class Layout {
   public static final String OPTION_PREFIX = "truffle.object.";
   private static final LayoutFactory LAYOUT_FACTORY = loadLayoutFactory();
   static final int INT_TO_DOUBLE_FLAG = 1;
   static final int INT_TO_LONG_FLAG = 2;

   protected Layout() {
   }

   public abstract Class<? extends DynamicObject> getType();

   protected Shape buildShape(Object dynamicType, Object sharedData, int flags, Assumption singleContextAssumption) {
      throw new UnsupportedOperationException();
   }

   @Deprecated(since = "21.1")
   public abstract Shape.Allocator createAllocator();

   protected static LayoutFactory getFactory() {
      return LAYOUT_FACTORY;
   }

   private static LayoutFactory loadLayoutFactory() {
      LayoutFactory layoutFactory = Truffle.getRuntime().getCapability(LayoutFactory.class);
      if (layoutFactory == null) {
         ServiceLoader<LayoutFactory> serviceLoader = ServiceLoader.load(LayoutFactory.class, Layout.class.getClassLoader());
         layoutFactory = selectLayoutFactory(serviceLoader);
         if (layoutFactory == null) {
            throw CompilerDirectives.shouldNotReachHere("LayoutFactory not found");
         }
      }

      return layoutFactory;
   }

   private static LayoutFactory selectLayoutFactory(Iterable<LayoutFactory> availableLayoutFactories) {
      String layoutFactoryImplName = System.getProperty("truffle.object.LayoutFactory");
      LayoutFactory bestLayoutFactory = null;

      for (LayoutFactory currentLayoutFactory : availableLayoutFactories) {
         if (layoutFactoryImplName != null) {
            if (currentLayoutFactory.getClass().getName().equals(layoutFactoryImplName)) {
               return currentLayoutFactory;
            }
         } else if (bestLayoutFactory == null) {
            bestLayoutFactory = currentLayoutFactory;
         } else if (currentLayoutFactory.getPriority() >= bestLayoutFactory.getPriority()) {
            assert currentLayoutFactory.getPriority() != bestLayoutFactory.getPriority();

            bestLayoutFactory = currentLayoutFactory;
         }
      }

      return bestLayoutFactory;
   }

   static EnumSet<Layout.ImplicitCast> getAllowedImplicitCasts(Layout.Builder builder) {
      return builder.allowedImplicitCasts;
   }

   static Class<? extends DynamicObject> getType(Layout.Builder builder) {
      return builder.dynamicObjectClass;
   }

   static int implicitCastFlags(EnumSet<Layout.ImplicitCast> allowedImplicitCasts) {
      return (allowedImplicitCasts.contains(Layout.ImplicitCast.IntToDouble) ? 1 : 0) | (allowedImplicitCasts.contains(Layout.ImplicitCast.IntToLong) ? 2 : 0);
   }

   protected abstract static class Access {
      protected Access() {
         if (!this.getClass().getName().startsWith("com.oracle.truffle.object.")) {
            throw new IllegalAccessError();
         }
      }

      public final void setShape(DynamicObject object, Shape shape) {
         object.setShape(shape);
      }

      public final void setObjectArray(DynamicObject object, Object[] value) {
         object.setObjectStore(value);
      }

      public final Object[] getObjectArray(DynamicObject object) {
         return object.getObjectStore();
      }

      public final void setPrimitiveArray(DynamicObject object, int[] value) {
         object.setPrimitiveStore(value);
      }

      public final int[] getPrimitiveArray(DynamicObject object) {
         return object.getPrimitiveStore();
      }

      public final Shape getShape(DynamicObject object) {
         return object.getShape();
      }

      public final DynamicObject objectClone(DynamicObject object) {
         try {
            return object.objectClone();
         } catch (CloneNotSupportedException var3) {
            throw CompilerDirectives.shouldNotReachHere(var3);
         }
      }

      public final Class<? extends Annotation> getDynamicFieldAnnotation() {
         return DynamicObject.getDynamicFieldAnnotation();
      }
   }

   @Deprecated(since = "21.1")
   public static final class Builder {
      private EnumSet<Layout.ImplicitCast> allowedImplicitCasts = EnumSet.noneOf(Layout.ImplicitCast.class);
      private Class<? extends DynamicObject> dynamicObjectClass;

      private Builder() {
      }

      public Layout build() {
         return Layout.getFactory().createLayout(this);
      }

      public Layout.Builder setAllowedImplicitCasts(EnumSet<Layout.ImplicitCast> allowedImplicitCasts) {
         this.allowedImplicitCasts = allowedImplicitCasts.clone();
         return this;
      }

      public Layout.Builder addAllowedImplicitCast(Layout.ImplicitCast allowedImplicitCast) {
         this.allowedImplicitCasts.add(allowedImplicitCast);
         return this;
      }

      public Layout.Builder type(Class<? extends DynamicObject> layoutClass) {
         if (DynamicObject.class.isAssignableFrom(layoutClass)) {
            this.dynamicObjectClass = layoutClass;
            return this;
         } else {
            throw new IllegalArgumentException("Unsupported DynamicObject layout class: " + layoutClass.getName());
         }
      }
   }

   @Deprecated(since = "21.1")
   public static enum ImplicitCast {
      IntToDouble,
      IntToLong;
   }
}
