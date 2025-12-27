package com.oracle.truffle.api.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.Pair;

public abstract class Shape {
   static final int OBJECT_FLAGS_MASK = 255;
   static final int OBJECT_FLAGS_SHIFT = 0;
   static final int OBJECT_SHARED = 65536;
   static final int OBJECT_PROPERTY_ASSUMPTIONS = 131072;

   public static Shape.Builder newBuilder() {
      CompilerAsserts.neverPartOfCompilation();
      return new Shape.Builder();
   }

   public static Shape.DerivedBuilder newBuilder(Shape baseShape) {
      CompilerAsserts.neverPartOfCompilation();
      return new Shape.DerivedBuilder(baseShape);
   }

   protected Shape() {
   }

   public abstract Property getProperty(Object key);

   @Deprecated(since = "22.2")
   public abstract Shape addProperty(Property property);

   @Deprecated(since = "22.2")
   public abstract Shape defineProperty(Object key, Object value, int flags);

   @Deprecated(since = "20.2")
   public abstract Shape defineProperty(Object key, Object value, int flags, LocationFactory locationFactory);

   public abstract Iterable<Property> getProperties();

   public abstract List<Property> getPropertyList();

   public abstract List<Property> getPropertyListInternal(boolean ascending);

   public abstract List<Object> getKeyList();

   public abstract Iterable<Object> getKeys();

   public abstract Assumption getValidAssumption();

   public abstract boolean isValid();

   public abstract Assumption getLeafAssumption();

   public abstract boolean isLeaf();

   @Deprecated(since = "20.2")
   public abstract Shape getParent();

   public abstract boolean hasProperty(Object key);

   @Deprecated(since = "20.2")
   public abstract Shape removeProperty(Property property);

   @Deprecated(since = "20.2")
   public abstract Shape replaceProperty(Property oldProperty, Property newProperty);

   public abstract Property getLastProperty();

   public int getFlags() {
      CompilerAsserts.neverPartOfCompilation();
      throw CompilerDirectives.shouldNotReachHere();
   }

   protected Shape setFlags(int newFlags) {
      CompilerAsserts.neverPartOfCompilation();
      throw CompilerDirectives.shouldNotReachHere();
   }

   @Deprecated(since = "22.2")
   public abstract Shape.Allocator allocator();

   public abstract int getPropertyCount();

   @Deprecated(since = "20.3")
   public abstract ObjectType getObjectType();

   public Object getDynamicType() {
      CompilerAsserts.neverPartOfCompilation();
      throw CompilerDirectives.shouldNotReachHere();
   }

   protected Shape setDynamicType(Object dynamicType) {
      CompilerAsserts.neverPartOfCompilation();
      Objects.requireNonNull(dynamicType);
      throw CompilerDirectives.shouldNotReachHere();
   }

   public abstract Shape getRoot();

   public abstract boolean check(DynamicObject subject);

   @Deprecated(since = "21.1")
   public abstract Layout getLayout();

   public Class<? extends DynamicObject> getLayoutClass() {
      return this.getLayout().getType();
   }

   public abstract Object getSharedData();

   @Deprecated(since = "22.2")
   public abstract Shape changeType(ObjectType newOps);

   @Deprecated(since = "22.2")
   public abstract DynamicObject newInstance();

   @Deprecated(since = "22.2")
   public abstract DynamicObjectFactory createFactory();

   @Deprecated(since = "22.2")
   public abstract Object getMutex();

   public abstract Shape tryMerge(Shape other);

   public boolean isShared() {
      return false;
   }

   public Shape makeSharedShape() {
      CompilerAsserts.neverPartOfCompilation();
      throw CompilerDirectives.shouldNotReachHere();
   }

   protected boolean hasInstanceProperties() {
      return true;
   }

   public Assumption getPropertyAssumption(Object key) {
      return Assumption.NEVER_VALID;
   }

   public boolean allPropertiesMatch(Predicate<Property> predicate) {
      CompilerAsserts.neverPartOfCompilation();
      throw CompilerDirectives.shouldNotReachHere();
   }

   @CompilerDirectives.TruffleBoundary
   public PropertyGetter makePropertyGetter(Object key) {
      Property property = this.getProperty(key);
      return property == null ? null : new PropertyGetter(this, property);
   }

   abstract static class AbstractBuilder<T extends Shape.AbstractBuilder<T>> {
      public abstract T dynamicType(Object dynamicType);

      public abstract T shapeFlags(int flags);

      public abstract T addConstantProperty(Object key, Object value, int flags);

      static Object checkDynamicType(Object dynamicType) {
         Objects.requireNonNull(dynamicType, "dynamicType");
         return dynamicType;
      }

      static int checkShapeFlags(int flags) {
         if ((flags & -256) != 0) {
            throw new IllegalArgumentException("flags must be in the range (0, 255)");
         } else {
            return flags;
         }
      }
   }

   @Deprecated(since = "22.2")
   public abstract static class Allocator {
      @Deprecated(since = "22.2")
      protected Allocator() {
      }

      @Deprecated(since = "19.3")
      protected abstract Location locationForValue(Object value, boolean useFinal, boolean nonNull);

      @Deprecated(since = "20.2")
      public final Location locationForValue(Object value) {
         return this.locationForValue(value, false, value != null);
      }

      @Deprecated(since = "19.3")
      public final Location locationForValue(Object value, EnumSet<LocationModifier> modifiers) {
         assert value != null || !modifiers.contains(LocationModifier.NonNull);

         return this.locationForValue(value, modifiers.contains(LocationModifier.Final), modifiers.contains(LocationModifier.NonNull));
      }

      @Deprecated(since = "22.2")
      protected abstract Location locationForType(Class<?> type, boolean useFinal, boolean nonNull);

      @Deprecated(since = "22.2")
      public final Location locationForType(Class<?> type) {
         return this.locationForType(type, false, false);
      }

      @Deprecated(since = "22.2")
      public final Location locationForType(Class<?> type, EnumSet<LocationModifier> modifiers) {
         return this.locationForType(type, modifiers.contains(LocationModifier.Final), modifiers.contains(LocationModifier.NonNull));
      }

      @Deprecated(since = "22.2")
      public abstract Location constantLocation(Object value);

      @Deprecated(since = "22.2")
      public abstract Location declaredLocation(Object value);

      @Deprecated(since = "22.2")
      public abstract Shape.Allocator addLocation(Location location);

      @Deprecated(since = "22.2")
      public abstract Shape.Allocator copy();
   }

   public static final class Builder extends Shape.AbstractBuilder<Shape.Builder> {
      private Class<? extends DynamicObject> layoutClass = DynamicObject.class;
      private Object dynamicType = ObjectType.DEFAULT;
      private int shapeFlags;
      private boolean allowImplicitCastIntToDouble;
      private boolean allowImplicitCastIntToLong;
      private boolean shared;
      private boolean propertyAssumptions;
      private Object sharedData;
      private Assumption singleContextAssumption;
      private EconomicMap<Object, Pair<Object, Integer>> properties;

      Builder() {
      }

      public Shape.Builder layout(Class<? extends DynamicObject> layoutClass) {
         CompilerAsserts.neverPartOfCompilation();
         if (!DynamicObject.class.isAssignableFrom(layoutClass)) {
            throw new IllegalArgumentException(String.format("Expected a subclass of %s but got: %s", DynamicObject.class.getName(), layoutClass.getTypeName()));
         } else {
            this.layoutClass = layoutClass;
            return this;
         }
      }

      public Shape.Builder dynamicType(Object dynamicType) {
         CompilerAsserts.neverPartOfCompilation();
         this.dynamicType = checkDynamicType(dynamicType);
         return this;
      }

      public Shape.Builder shapeFlags(int flags) {
         CompilerAsserts.neverPartOfCompilation();
         this.shapeFlags = checkShapeFlags(flags);
         return this;
      }

      public Shape.Builder shared(boolean isShared) {
         CompilerAsserts.neverPartOfCompilation();
         this.shared = isShared;
         return this;
      }

      public Shape.Builder propertyAssumptions(boolean enable) {
         CompilerAsserts.neverPartOfCompilation();
         this.propertyAssumptions = enable;
         return this;
      }

      public Shape.Builder sharedData(Object sharedData) {
         CompilerAsserts.neverPartOfCompilation();
         this.sharedData = sharedData;
         return this;
      }

      public Shape.Builder singleContextAssumption(Assumption assumption) {
         CompilerAsserts.neverPartOfCompilation();
         this.singleContextAssumption = assumption;
         return this;
      }

      public Shape.Builder addConstantProperty(Object key, Object value, int flags) {
         CompilerAsserts.neverPartOfCompilation();
         Objects.requireNonNull(key, "key");
         if (this.properties == null) {
            this.properties = EconomicMap.create(Equivalence.DEFAULT);
         }

         if (this.properties.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Property already exists: %s.", key));
         } else {
            this.properties.put(key, Pair.create(value, flags));
            return this;
         }
      }

      public Shape.Builder allowImplicitCastIntToLong(boolean allow) {
         this.allowImplicitCastIntToLong = allow;
         return this;
      }

      public Shape.Builder allowImplicitCastIntToDouble(boolean allow) {
         this.allowImplicitCastIntToDouble = allow;
         return this;
      }

      public Shape build() {
         CompilerAsserts.neverPartOfCompilation();
         int flags = this.shapeFlags;
         if (this.shared) {
            flags = this.shapeFlags | 65536;
         }

         if (this.propertyAssumptions) {
            flags = this.shapeFlags | 131072;
         }

         int implicitCastFlags = (this.allowImplicitCastIntToDouble ? 1 : 0) | (this.allowImplicitCastIntToLong ? 2 : 0);
         Shape shape = Layout.getFactory()
            .createShape(
               new Object[]{this.layoutClass, implicitCastFlags, this.dynamicType, this.sharedData, flags, this.properties, this.singleContextAssumption}
            );

         assert shape.isShared() == this.shared && shape.getFlags() == this.shapeFlags && shape.getDynamicType() == this.dynamicType;

         return shape;
      }
   }

   public static final class DerivedBuilder extends Shape.AbstractBuilder<Shape.DerivedBuilder> {
      private final Shape baseShape;
      private Object dynamicType;
      private int shapeFlags;
      private EconomicMap<Object, Property> properties;

      DerivedBuilder(Shape baseShape) {
         this.baseShape = baseShape;
         this.dynamicType = baseShape.getDynamicType();
         this.shapeFlags = baseShape.getFlags();
      }

      public Shape.DerivedBuilder dynamicType(Object dynamicType) {
         CompilerAsserts.neverPartOfCompilation();
         this.dynamicType = checkDynamicType(dynamicType);
         return this;
      }

      public Shape.DerivedBuilder shapeFlags(int flags) {
         CompilerAsserts.neverPartOfCompilation();
         this.shapeFlags = checkShapeFlags(flags);
         return this;
      }

      public Shape.DerivedBuilder addConstantProperty(Object key, Object value, int flags) {
         CompilerAsserts.neverPartOfCompilation();
         Objects.requireNonNull(key, "key");
         if (this.properties == null) {
            this.properties = EconomicMap.create(Equivalence.DEFAULT);
         }

         if (this.baseShape.getProperty(key) == null && !this.properties.containsKey(key)) {
            Location location = this.baseShape.allocator().constantLocation(value);
            this.properties.put(key, Property.create(key, location, flags));
            return this;
         } else {
            throw new IllegalArgumentException(String.format("Property already exists: %s.", key));
         }
      }

      public Shape build() {
         CompilerAsserts.neverPartOfCompilation();
         Shape derivedShape = this.baseShape;
         if (this.dynamicType != derivedShape.getDynamicType()) {
            derivedShape = derivedShape.setDynamicType(this.dynamicType);
         }

         if (this.shapeFlags != derivedShape.getFlags()) {
            derivedShape = derivedShape.setFlags(this.shapeFlags);
         }

         if (this.properties != null) {
            for (Property property : this.properties.getValues()) {
               derivedShape = derivedShape.addProperty(property);
            }
         }

         return derivedShape;
      }
   }
}
