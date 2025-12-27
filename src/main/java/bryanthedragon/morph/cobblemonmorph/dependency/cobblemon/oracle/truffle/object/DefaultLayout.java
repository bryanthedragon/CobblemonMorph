package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.graalvm.nativeimage.ImageInfo;

class DefaultLayout extends LayoutImpl {
   private final CoreLocations.ObjectLocation[] objectFields;
   private final CoreLocations.LongLocation[] primitiveFields;
   static final CoreLocations.ObjectLocation[] NO_OBJECT_FIELDS = new CoreLocations.ObjectLocation[0];
   static final CoreLocations.LongLocation[] NO_LONG_FIELDS = new CoreLocations.LongLocation[0];
   private static final Map<DefaultLayout.Key, DefaultLayout> LAYOUT_MAP = new ConcurrentHashMap<>();

   DefaultLayout(
      Class<? extends DynamicObject> dynamicObjectClass,
      LayoutStrategy strategy,
      int implicitCastFlags,
      CoreLocations.ObjectLocation[] objectFields,
      CoreLocations.LongLocation[] primitiveFields
   ) {
      super(dynamicObjectClass, strategy, implicitCastFlags);
      this.objectFields = objectFields;
      this.primitiveFields = primitiveFields;
   }

   DefaultLayout(Class<? extends DynamicObject> dynamicObjectClass, LayoutStrategy strategy, int implicitCastFlags) {
      super(dynamicObjectClass, strategy, implicitCastFlags);
      if (DynamicObject.class == dynamicObjectClass) {
         this.objectFields = NO_OBJECT_FIELDS;
         this.primitiveFields = NO_LONG_FIELDS;
      } else {
         if (!DynamicObject.class.isAssignableFrom(dynamicObjectClass)) {
            throw new IllegalArgumentException(dynamicObjectClass.getName());
         }

         DefaultLayout.LayoutInfo layoutInfo = DefaultLayout.LayoutInfo.getOrCreateLayoutInfo(dynamicObjectClass);
         this.objectFields = layoutInfo.objectFields;
         this.primitiveFields = layoutInfo.primitiveFields;
      }
   }

   static LayoutImpl createCoreLayout(Class<? extends DynamicObject> type, int implicitCastFlags) {
      return getOrCreateLayout(type, implicitCastFlags);
   }

   private static DefaultLayout getOrCreateLayout(Class<? extends DynamicObject> type, int implicitCastFlags) {
      Objects.requireNonNull(type, "DynamicObject layout class");
      DefaultLayout.Key key = new DefaultLayout.Key(type, implicitCastFlags);
      DefaultLayout layout = LAYOUT_MAP.get(key);
      if (layout != null) {
         return layout;
      } else {
         DefaultLayout newLayout = new DefaultLayout(type, DefaultStrategy.SINGLETON, implicitCastFlags);
         layout = LAYOUT_MAP.putIfAbsent(key, newLayout);
         return layout == null ? newLayout : layout;
      }
   }

   static void registerLayoutClass(Class<? extends DynamicObject> type) {
      createCoreLayout(type, 0);
   }

   @Override
   protected boolean isLegacyLayout() {
      return false;
   }

   static UnsupportedOperationException unsupported() {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException("not supported by this object layout");
   }

   @Override
   protected ShapeImpl newShape(Object objectType, Object sharedData, int flags, Assumption singleContextAssumption) {
      return new ShapeBasic(this, sharedData, objectType, flags, singleContextAssumption);
   }

   @Override
   protected boolean hasObjectExtensionArray() {
      return true;
   }

   @Override
   protected boolean hasPrimitiveExtensionArray() {
      return true;
   }

   @Override
   protected int getObjectFieldCount() {
      return this.objectFields.length;
   }

   @Override
   protected int getPrimitiveFieldCount() {
      return this.primitiveFields.length;
   }

   protected CoreLocations.ObjectLocation getObjectFieldLocation(int index) {
      return this.objectFields[index];
   }

   protected CoreLocations.LongLocation getPrimitiveFieldLocation(int index) {
      return this.primitiveFields[index];
   }

   protected int getLongFieldSize() {
      return 1;
   }

   public ShapeImpl.BaseAllocator createAllocator() {
      return this.getStrategy().createAllocator(this);
   }

   static void resetNativeImageState() {
      LAYOUT_MAP.clear();
      DefaultLayout.LayoutInfo.LAYOUT_INFO_MAP.clear();
   }

   private static final class Key {
      final Class<? extends DynamicObject> type;
      final int implicitCastFlags;

      Key(Class<? extends DynamicObject> type, int implicitCastFlags) {
         this.type = type;
         this.implicitCastFlags = implicitCastFlags;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + this.implicitCastFlags;
         return 31 * result + (this.type == null ? 0 : this.type.hashCode());
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof DefaultLayout.Key)) {
            return false;
         } else {
            DefaultLayout.Key other = (DefaultLayout.Key)obj;
            return this.type == other.type && this.implicitCastFlags == other.implicitCastFlags;
         }
      }
   }

   private static final class LayoutInfo {
      final CoreLocations.ObjectLocation[] objectFields;
      final CoreLocations.LongLocation[] primitiveFields;
      private static final ConcurrentMap<Class<? extends DynamicObject>, DefaultLayout.LayoutInfo> LAYOUT_INFO_MAP = new ConcurrentHashMap<>();

      static DefaultLayout.LayoutInfo getOrCreateLayoutInfo(Class<? extends DynamicObject> dynamicObjectClass) {
         DefaultLayout.LayoutInfo layoutInfo = LAYOUT_INFO_MAP.get(dynamicObjectClass);
         if (layoutInfo != null) {
            return layoutInfo;
         } else if (ImageInfo.inImageRuntimeCode()) {
            throw new IllegalStateException("Layout not initialized ahead-of-time: " + dynamicObjectClass);
         } else {
            return createLayoutInfo(dynamicObjectClass);
         }
      }

      private static DefaultLayout.LayoutInfo createLayoutInfo(Class<? extends DynamicObject> dynamicObjectClass) {
         Class<? extends DynamicObject> subclass = dynamicObjectClass.asSubclass(DynamicObject.class);
         List<CoreLocations.ObjectLocation> objectFieldList = new ArrayList<>();
         List<CoreLocations.LongLocation> longFieldList = new ArrayList<>();
         Class<? extends DynamicObject> superclass = collectFields(subclass, objectFieldList, longFieldList);
         if (objectFieldList.size() + longFieldList.size() > 1000) {
            throw new IllegalArgumentException("Too many @DynamicField annotated fields.");
         } else {
            DefaultLayout.LayoutInfo newLayoutInfo;
            if (superclass != subclass) {
               newLayoutInfo = getOrCreateLayoutInfo(superclass);
            } else {
               newLayoutInfo = new DefaultLayout.LayoutInfo(objectFieldList, longFieldList);
            }

            DefaultLayout.LayoutInfo layoutInfo = LAYOUT_INFO_MAP.putIfAbsent(dynamicObjectClass, newLayoutInfo);
            return layoutInfo == null ? newLayoutInfo : layoutInfo;
         }
      }

      private LayoutInfo(List<CoreLocations.ObjectLocation> objectFieldList, List<CoreLocations.LongLocation> longFieldList) {
         this.objectFields = objectFieldList.toArray(DefaultLayout.NO_OBJECT_FIELDS);
         this.primitiveFields = longFieldList.toArray(DefaultLayout.NO_LONG_FIELDS);
      }

      private static Class<? extends DynamicObject> collectFields(
         Class<? extends DynamicObject> clazz, List<CoreLocations.ObjectLocation> objectFieldList, List<CoreLocations.LongLocation> primitiveFieldList
      ) {
         if (clazz == DynamicObject.class) {
            return clazz;
         } else {
            Class<? extends DynamicObject> layoutClass = collectFields(
               clazz.getSuperclass().asSubclass(DynamicObject.class), objectFieldList, primitiveFieldList
            );
            Class<? extends Annotation> dynamicFieldAnnotation = LayoutImpl.ACCESS.getDynamicFieldAnnotation();
            boolean hasDynamicFields = false;

            for (Field field : clazz.getDeclaredFields()) {
               if (!Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
                  if (field.getAnnotation(dynamicFieldAnnotation) != null) {
                     checkDynamicFieldType(field);

                     assert field.getDeclaringClass() == clazz;

                     hasDynamicFields = true;
                     if (field.getType() == Object.class) {
                        objectFieldList.add(new CoreLocations.DynamicObjectFieldLocation(objectFieldList.size(), field));
                     } else if (field.getType() == long.class) {
                        long offset = UnsafeAccess.objectFieldOffset(field);
                        if (offset % 8L == 0L) {
                           primitiveFieldList.add(new CoreLocations.DynamicLongFieldLocation(primitiveFieldList.size(), offset, clazz));
                        }
                     }
                  }
               } else {
                  assert !field.isAnnotationPresent(dynamicFieldAnnotation);
               }
            }

            if (hasDynamicFields) {
               layoutClass = clazz;
            }

            return layoutClass;
         }
      }

      private static void checkDynamicFieldType(Field field) {
         if (field.getType() != Object.class && field.getType() != int.class && field.getType() != long.class) {
            throw new IllegalArgumentException("@DynamicField annotated field type must be either Object or int or long: " + field);
         } else if (Modifier.isFinal(field.getModifiers())) {
            throw new IllegalArgumentException("@DynamicField annotated field must not be final: " + field);
         }
      }

      @Override
      public String toString() {
         return "LayoutInfo [objectFields="
            + Arrays.toString((Object[])this.objectFields)
            + ", primitiveFields="
            + Arrays.toString((Object[])this.primitiveFields)
            + "]";
      }
   }
}
