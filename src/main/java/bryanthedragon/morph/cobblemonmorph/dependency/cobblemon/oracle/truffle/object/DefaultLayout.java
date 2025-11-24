
package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.object.CoreLocations;
import com.oracle.truffle.object.DefaultStrategy;
import com.oracle.truffle.object.LayoutImpl;
import com.oracle.truffle.object.LayoutStrategy;
import com.oracle.truffle.object.ShapeBasic;
import com.oracle.truffle.object.ShapeImpl;
import com.oracle.truffle.object.UnsafeAccess;
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

class DefaultLayout
extends LayoutImpl {
    private final CoreLocations.ObjectLocation[] objectFields;
    private final CoreLocations.LongLocation[] primitiveFields;
    static final CoreLocations.ObjectLocation[] NO_OBJECT_FIELDS = new CoreLocations.ObjectLocation[0];
    static final CoreLocations.LongLocation[] NO_LONG_FIELDS = new CoreLocations.LongLocation[0];
    private static final Map<Key, DefaultLayout> LAYOUT_MAP = new ConcurrentHashMap<Key, DefaultLayout>();

    DefaultLayout(Class<? extends DynamicObject> dynamicObjectClass, LayoutStrategy strategy, int implicitCastFlags, CoreLocations.ObjectLocation[] objectFields, CoreLocations.LongLocation[] primitiveFields) {
        super(dynamicObjectClass, strategy, implicitCastFlags);
        this.objectFields = objectFields;
        this.primitiveFields = primitiveFields;
    }

    DefaultLayout(Class<? extends DynamicObject> dynamicObjectClass, LayoutStrategy strategy, int implicitCastFlags) {
        super(dynamicObjectClass, strategy, implicitCastFlags);
        if (DynamicObject.class == dynamicObjectClass) {
            this.objectFields = NO_OBJECT_FIELDS;
            this.primitiveFields = NO_LONG_FIELDS;
        } else if (DynamicObject.class.isAssignableFrom(dynamicObjectClass)) {
            LayoutInfo layoutInfo = LayoutInfo.getOrCreateLayoutInfo(dynamicObjectClass);
            this.objectFields = layoutInfo.objectFields;
            this.primitiveFields = layoutInfo.primitiveFields;
        } else {
            throw new IllegalArgumentException(dynamicObjectClass.getName());
        }
    }

    static LayoutImpl createCoreLayout(Class<? extends DynamicObject> type, int implicitCastFlags) {
        return DefaultLayout.getOrCreateLayout(type, implicitCastFlags);
    }

    private static DefaultLayout getOrCreateLayout(Class<? extends DynamicObject> type, int implicitCastFlags) {
        Objects.requireNonNull(type, "DynamicObject layout class");
        Key key = new Key(type, implicitCastFlags);
        DefaultLayout layout = LAYOUT_MAP.get(key);
        if (layout != null) {
            return layout;
        }
        DefaultLayout newLayout = new DefaultLayout(type, DefaultStrategy.SINGLETON, implicitCastFlags);
        layout = LAYOUT_MAP.putIfAbsent(key, newLayout);
        return layout == null ? newLayout : layout;
    }

    static void registerLayoutClass(Class<? extends DynamicObject> type) {
        DefaultLayout.createCoreLayout(type, 0);
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

    @Override
    public ShapeImpl.BaseAllocator createAllocator() {
        DefaultLayout layout = this;
        return this.getStrategy().createAllocator(layout);
    }

    static void resetNativeImageState() {
        LAYOUT_MAP.clear();
        LayoutInfo.LAYOUT_INFO_MAP.clear();
    }

    private static final class LayoutInfo {
        final CoreLocations.ObjectLocation[] objectFields;
        final CoreLocations.LongLocation[] primitiveFields;
        private static final ConcurrentMap<Class<? extends DynamicObject>, LayoutInfo> LAYOUT_INFO_MAP = new ConcurrentHashMap<Class<? extends DynamicObject>, LayoutInfo>();

        static LayoutInfo getOrCreateLayoutInfo(Class<? extends DynamicObject> dynamicObjectClass) {
            LayoutInfo layoutInfo = (LayoutInfo)LAYOUT_INFO_MAP.get(dynamicObjectClass);
            if (layoutInfo != null) {
                return layoutInfo;
            }
            if (ImageInfo.inImageRuntimeCode()) {
                throw new IllegalStateException("Layout not initialized ahead-of-time: " + dynamicObjectClass);
            }
            return LayoutInfo.createLayoutInfo(dynamicObjectClass);
        }

        private static LayoutInfo createLayoutInfo(Class<? extends DynamicObject> dynamicObjectClass) {
            Class<DynamicObject> subclass = dynamicObjectClass.asSubclass(DynamicObject.class);
            ArrayList<CoreLocations.ObjectLocation> objectFieldList = new ArrayList<CoreLocations.ObjectLocation>();
            ArrayList<CoreLocations.LongLocation> longFieldList = new ArrayList<CoreLocations.LongLocation>();
            Class<? extends DynamicObject> superclass = LayoutInfo.collectFields(subclass, objectFieldList, longFieldList);
            if (objectFieldList.size() + longFieldList.size() > 1000) {
                throw new IllegalArgumentException("Too many @DynamicField annotated fields.");
            }
            LayoutInfo newLayoutInfo = superclass != subclass ? LayoutInfo.getOrCreateLayoutInfo(superclass) : new LayoutInfo(objectFieldList, longFieldList);
            LayoutInfo layoutInfo = LAYOUT_INFO_MAP.putIfAbsent(dynamicObjectClass, newLayoutInfo);
            return layoutInfo == null ? newLayoutInfo : layoutInfo;
        }

        private LayoutInfo(List<CoreLocations.ObjectLocation> objectFieldList, List<CoreLocations.LongLocation> longFieldList) {
            this.objectFields = objectFieldList.toArray(NO_OBJECT_FIELDS);
            this.primitiveFields = longFieldList.toArray(NO_LONG_FIELDS);
        }

        private static Class<? extends DynamicObject> collectFields(Class<? extends DynamicObject> clazz, List<CoreLocations.ObjectLocation> objectFieldList, List<CoreLocations.LongLocation> primitiveFieldList) {
            if (clazz == DynamicObject.class) {
                return clazz;
            }
            Class<? extends DynamicObject> layoutClass = LayoutInfo.collectFields(clazz.getSuperclass().asSubclass(DynamicObject.class), objectFieldList, primitiveFieldList);
            Class<? extends Annotation> dynamicFieldAnnotation = LayoutImpl.ACCESS.getDynamicFieldAnnotation();
            boolean hasDynamicFields = false;
            for (Field field : clazz.getDeclaredFields()) {
                long offset;
                if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                    assert (!field.isAnnotationPresent(dynamicFieldAnnotation));
                    continue;
                }
                if (field.getAnnotation(dynamicFieldAnnotation) == null) continue;
                LayoutInfo.checkDynamicFieldType(field);
                assert (field.getDeclaringClass() == clazz);
                hasDynamicFields = true;
                if (field.getType() == Object.class) {
                    objectFieldList.add(new CoreLocations.DynamicObjectFieldLocation(objectFieldList.size(), field));
                    continue;
                }
                if (field.getType() != Long.TYPE || (offset = UnsafeAccess.objectFieldOffset(field)) % 8L != 0L) continue;
                primitiveFieldList.add(new CoreLocations.DynamicLongFieldLocation(primitiveFieldList.size(), offset, clazz));
            }
            if (hasDynamicFields) {
                layoutClass = clazz;
            }
            return layoutClass;
        }

        private static void checkDynamicFieldType(Field field) {
            if (field.getType() != Object.class && field.getType() != Integer.TYPE && field.getType() != Long.TYPE) {
                throw new IllegalArgumentException("@DynamicField annotated field type must be either Object or int or long: " + field);
            }
            if (Modifier.isFinal(field.getModifiers())) {
                throw new IllegalArgumentException("@DynamicField annotated field must not be final: " + field);
            }
        }

        public String toString() {
            return "LayoutInfo [objectFields=" + Arrays.toString(this.objectFields) + ", primitiveFields=" + Arrays.toString(this.primitiveFields) + "]";
        }
    }

    private static final class Key {
        final Class<? extends DynamicObject> type;
        final int implicitCastFlags;

        Key(Class<? extends DynamicObject> type, int implicitCastFlags) {
            this.type = type;
            this.implicitCastFlags = implicitCastFlags;
        }

        public int hashCode() {
            int prime = 31;
            int result = 1;
            result = 31 * result + this.implicitCastFlags;
            result = 31 * result + (this.type == null ? 0 : this.type.hashCode());
            return result;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Key)) {
                return false;
            }
            Key other = (Key)obj;
            return this.type == other.type && this.implicitCastFlags == other.implicitCastFlags;
        }
    }
}

