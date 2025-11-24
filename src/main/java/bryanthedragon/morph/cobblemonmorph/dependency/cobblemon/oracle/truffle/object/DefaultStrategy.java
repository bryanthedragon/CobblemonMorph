
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.object.CoreAllocator;
import com.oracle.truffle.object.CoreLocation;
import com.oracle.truffle.object.CoreLocations;
import com.oracle.truffle.object.DefaultLayout;
import com.oracle.truffle.object.LayoutImpl;
import com.oracle.truffle.object.LayoutStrategy;
import com.oracle.truffle.object.LocationImpl;
import com.oracle.truffle.object.ShapeImpl;
import java.util.Objects;

final class DefaultStrategy
extends LayoutStrategy {
    static final LayoutStrategy SINGLETON = new DefaultStrategy();

    private DefaultStrategy() {
    }

    @Override
    public boolean updateShape(DynamicObject object) {
        assert (object.getShape().isValid());
        return false;
    }

    @Override
    public ShapeImpl ensureValid(ShapeImpl newShape) {
        assert (newShape.isValid());
        return newShape;
    }

    private static boolean assertLocationInRange(ShapeImpl shape, Location location) {
        DefaultLayout layout = (DefaultLayout)shape.getLayout();
        assert (shape.getPrimitiveFieldSize() + ((LocationImpl)location).primitiveFieldCount() <= layout.getPrimitiveFieldCount());
        assert (shape.getObjectFieldSize() + ((LocationImpl)location).objectFieldCount() <= layout.getObjectFieldCount());
        return true;
    }

    @Override
    public ShapeImpl ensureSpace(ShapeImpl shape, Location location) {
        Objects.requireNonNull(location);
        assert (DefaultStrategy.assertLocationInRange(shape, location));
        return shape;
    }

    @Override
    public ShapeImpl.BaseAllocator createAllocator(ShapeImpl shape) {
        return new CoreAllocator(shape);
    }

    @Override
    public ShapeImpl.BaseAllocator createAllocator(LayoutImpl layout) {
        return new CoreAllocator(layout);
    }

    @Override
    protected Location createLocationForValue(ShapeImpl shape, Object value2, long putFlags) {
        return ((CoreAllocator)shape.allocator()).locationForValue(value2, true, value2 != null, putFlags);
    }

    @Override
    protected int getLocationOrdinal(Location location) {
        return CoreLocations.getLocationOrdinal((CoreLocation)location);
    }
}

