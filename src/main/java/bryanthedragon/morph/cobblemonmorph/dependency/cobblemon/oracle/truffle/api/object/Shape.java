
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectFactory;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LocationFactory;
import com.oracle.truffle.api.object.LocationModifier;
import com.oracle.truffle.api.object.ObjectType;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.PropertyGetter;
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

    public static Builder newBuilder() {
        CompilerAsserts.neverPartOfCompilation();
        return new Builder();
    }

    public static DerivedBuilder newBuilder(Shape baseShape) {
        CompilerAsserts.neverPartOfCompilation();
        return new DerivedBuilder(baseShape);
    }

    protected Shape() {
    }

    public abstract Property getProperty(Object var1);

    @Deprecated(since="22.2")
    public abstract Shape addProperty(Property var1);

    @Deprecated(since="22.2")
    public abstract Shape defineProperty(Object var1, Object var2, int var3);

    @Deprecated(since="20.2")
    public abstract Shape defineProperty(Object var1, Object var2, int var3, LocationFactory var4);

    public abstract Iterable<Property> getProperties();

    public abstract List<Property> getPropertyList();

    public abstract List<Property> getPropertyListInternal(boolean var1);

    public abstract List<Object> getKeyList();

    public abstract Iterable<Object> getKeys();

    public abstract Assumption getValidAssumption();

    public abstract boolean isValid();

    public abstract Assumption getLeafAssumption();

    public abstract boolean isLeaf();

    @Deprecated(since="20.2")
    public abstract Shape getParent();

    public abstract boolean hasProperty(Object var1);

    @Deprecated(since="20.2")
    public abstract Shape removeProperty(Property var1);

    @Deprecated(since="20.2")
    public abstract Shape replaceProperty(Property var1, Property var2);

    public abstract Property getLastProperty();

    public int getFlags() {
        CompilerAsserts.neverPartOfCompilation();
        throw CompilerDirectives.shouldNotReachHere();
    }

    protected Shape setFlags(int newFlags) {
        CompilerAsserts.neverPartOfCompilation();
        throw CompilerDirectives.shouldNotReachHere();
    }

    @Deprecated(since="22.2")
    public abstract Allocator allocator();

    public abstract int getPropertyCount();

    @Deprecated(since="20.3")
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

    public abstract boolean check(DynamicObject var1);

    @Deprecated(since="21.1")
    public abstract Layout getLayout();

    public Class<? extends DynamicObject> getLayoutClass() {
        return this.getLayout().getType();
    }

    public abstract Object getSharedData();

    @Deprecated(since="22.2")
    public abstract Shape changeType(ObjectType var1);

    @Deprecated(since="22.2")
    public abstract DynamicObject newInstance();

    @Deprecated(since="22.2")
    public abstract DynamicObjectFactory createFactory();

    @Deprecated(since="22.2")
    public abstract Object getMutex();

    public abstract Shape tryMerge(Shape var1);

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
        if (property == null) {
            return null;
        }
        return new PropertyGetter(this, property);
    }

    @Deprecated(since="22.2")
    public static abstract class Allocator {
        @Deprecated(since="22.2")
        protected Allocator() {
        }

        @Deprecated(since="19.3")
        protected abstract Location locationForValue(Object var1, boolean var2, boolean var3);

        @Deprecated(since="20.2")
        public final Location locationForValue(Object value2) {
            return this.locationForValue(value2, false, value2 != null);
        }

        @Deprecated(since="19.3")
        public final Location locationForValue(Object value2, EnumSet<LocationModifier> modifiers) {
            assert (value2 != null || !modifiers.contains((Object)LocationModifier.NonNull));
            return this.locationForValue(value2, modifiers.contains((Object)LocationModifier.Final), modifiers.contains((Object)LocationModifier.NonNull));
        }

        @Deprecated(since="22.2")
        protected abstract Location locationForType(Class<?> var1, boolean var2, boolean var3);

        @Deprecated(since="22.2")
        public final Location locationForType(Class<?> type) {
            return this.locationForType(type, false, false);
        }

        @Deprecated(since="22.2")
        public final Location locationForType(Class<?> type, EnumSet<LocationModifier> modifiers) {
            return this.locationForType(type, modifiers.contains((Object)LocationModifier.Final), modifiers.contains((Object)LocationModifier.NonNull));
        }

        @Deprecated(since="22.2")
        public abstract Location constantLocation(Object var1);

        @Deprecated(since="22.2")
        public abstract Location declaredLocation(Object var1);

        @Deprecated(since="22.2")
        public abstract Allocator addLocation(Location var1);

        @Deprecated(since="22.2")
        public abstract Allocator copy();
    }

    public static final class DerivedBuilder
    extends AbstractBuilder<DerivedBuilder> {
        private final Shape baseShape;
        private Object dynamicType;
        private int shapeFlags;
        private EconomicMap<Object, Property> properties;

        DerivedBuilder(Shape baseShape) {
            this.baseShape = baseShape;
            this.dynamicType = baseShape.getDynamicType();
            this.shapeFlags = baseShape.getFlags();
        }

        @Override
        public DerivedBuilder dynamicType(Object dynamicType) {
            CompilerAsserts.neverPartOfCompilation();
            this.dynamicType = DerivedBuilder.checkDynamicType(dynamicType);
            return this;
        }

        @Override
        public DerivedBuilder shapeFlags(int flags) {
            CompilerAsserts.neverPartOfCompilation();
            this.shapeFlags = DerivedBuilder.checkShapeFlags(flags);
            return this;
        }

        @Override
        public DerivedBuilder addConstantProperty(Object key, Object value2, int flags) {
            CompilerAsserts.neverPartOfCompilation();
            Objects.requireNonNull(key, "key");
            if (this.properties == null) {
                this.properties = EconomicMap.create(Equivalence.DEFAULT);
            }
            if (this.baseShape.getProperty(key) != null || this.properties.containsKey(key)) {
                throw new IllegalArgumentException(String.format("Property already exists: %s.", key));
            }
            Location location = this.baseShape.allocator().constantLocation(value2);
            this.properties.put(key, Property.create(key, location, flags));
            return this;
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

    public static final class Builder
    extends AbstractBuilder<Builder> {
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

        public Builder layout(Class<? extends DynamicObject> layoutClass) {
            CompilerAsserts.neverPartOfCompilation();
            if (!DynamicObject.class.isAssignableFrom(layoutClass)) {
                throw new IllegalArgumentException(String.format("Expected a subclass of %s but got: %s", DynamicObject.class.getName(), layoutClass.getTypeName()));
            }
            this.layoutClass = layoutClass;
            return this;
        }

        @Override
        public Builder dynamicType(Object dynamicType) {
            CompilerAsserts.neverPartOfCompilation();
            this.dynamicType = Builder.checkDynamicType(dynamicType);
            return this;
        }

        @Override
        public Builder shapeFlags(int flags) {
            CompilerAsserts.neverPartOfCompilation();
            this.shapeFlags = Builder.checkShapeFlags(flags);
            return this;
        }

        public Builder shared(boolean isShared) {
            CompilerAsserts.neverPartOfCompilation();
            this.shared = isShared;
            return this;
        }

        public Builder propertyAssumptions(boolean enable) {
            CompilerAsserts.neverPartOfCompilation();
            this.propertyAssumptions = enable;
            return this;
        }

        public Builder sharedData(Object sharedData) {
            CompilerAsserts.neverPartOfCompilation();
            this.sharedData = sharedData;
            return this;
        }

        public Builder singleContextAssumption(Assumption assumption) {
            CompilerAsserts.neverPartOfCompilation();
            this.singleContextAssumption = assumption;
            return this;
        }

        @Override
        public Builder addConstantProperty(Object key, Object value2, int flags) {
            CompilerAsserts.neverPartOfCompilation();
            Objects.requireNonNull(key, "key");
            if (this.properties == null) {
                this.properties = EconomicMap.create(Equivalence.DEFAULT);
            }
            if (this.properties.containsKey(key)) {
                throw new IllegalArgumentException(String.format("Property already exists: %s.", key));
            }
            this.properties.put(key, Pair.create(value2, flags));
            return this;
        }

        public Builder allowImplicitCastIntToLong(boolean allow) {
            this.allowImplicitCastIntToLong = allow;
            return this;
        }

        public Builder allowImplicitCastIntToDouble(boolean allow) {
            this.allowImplicitCastIntToDouble = allow;
            return this;
        }

        public Shape build() {
            CompilerAsserts.neverPartOfCompilation();
            int flags = this.shapeFlags;
            if (this.shared) {
                flags = this.shapeFlags | 0x10000;
            }
            if (this.propertyAssumptions) {
                flags = this.shapeFlags | 0x20000;
            }
            int implicitCastFlags = (this.allowImplicitCastIntToDouble ? 1 : 0) | (this.allowImplicitCastIntToLong ? 2 : 0);
            Shape shape = Layout.getFactory().createShape(new Object[]{this.layoutClass, implicitCastFlags, this.dynamicType, this.sharedData, flags, this.properties, this.singleContextAssumption});
            assert (shape.isShared() == this.shared && shape.getFlags() == this.shapeFlags && shape.getDynamicType() == this.dynamicType);
            return shape;
        }
    }

    static abstract class AbstractBuilder<T extends AbstractBuilder<T>> {
        AbstractBuilder() {
        }

        public abstract T dynamicType(Object var1);

        public abstract T shapeFlags(int var1);

        public abstract T addConstantProperty(Object var1, Object var2, int var3);

        static Object checkDynamicType(Object dynamicType) {
            Objects.requireNonNull(dynamicType, "dynamicType");
            return dynamicType;
        }

        static int checkShapeFlags(int flags) {
            if ((flags & 0xFFFFFF00) != 0) {
                throw new IllegalArgumentException("flags must be in the range (0, 255)");
            }
            return flags;
        }
    }
}

