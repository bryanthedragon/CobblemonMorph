/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.CoreLayoutFactory;
import com.oracle.truffle.object.DynamicObjectSupport;
import com.oracle.truffle.object.LayoutStrategy;
import com.oracle.truffle.object.ShapeImpl;
import java.lang.invoke.VarHandle;
import java.util.Map;
import java.util.Objects;

public abstract class LayoutImpl
extends Layout {
    private static final int INT_TO_DOUBLE_FLAG = 1;
    private static final int INT_TO_LONG_FLAG = 2;
    protected final LayoutStrategy strategy;
    protected final Class<? extends DynamicObject> clazz;
    private final int allowedImplicitCasts;
    static final CoreAccess ACCESS = new CoreAccess();

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

    protected abstract ShapeImpl newShape(Object var1, Object var2, int var3, Assumption var4);

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

    public String toString() {
        return "Layout[" + this.clazz.getName() + "]";
    }

    static void resetNativeImageState() {
        assert (TruffleOptions.AOT) : "Only supported during image generation";
        ((CoreLayoutFactory)LayoutImpl.getFactory()).resetNativeImageState();
    }

    static void initializeDynamicObjectLayout(Class<?> dynamicObjectClass) {
        assert (TruffleOptions.AOT) : "Only supported during image generation";
        ((CoreLayoutFactory)LayoutImpl.getFactory()).registerLayoutClass(dynamicObjectClass.asSubclass(DynamicObject.class));
    }

    static final class CoreAccess
    extends Support {
        private CoreAccess() {
        }
    }

    protected static abstract class Support
    extends Layout.Access {
        protected Support() {
        }

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

