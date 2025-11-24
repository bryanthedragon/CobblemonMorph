
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.object.CoreLocation;
import com.oracle.truffle.object.CoreLocations;
import com.oracle.truffle.object.DefaultLayout;
import com.oracle.truffle.object.Flags;
import com.oracle.truffle.object.LayoutImpl;
import com.oracle.truffle.object.ObjectStorageOptions;
import com.oracle.truffle.object.ShapeImpl;

class CoreAllocator
extends ShapeImpl.BaseAllocator {
    CoreAllocator(LayoutImpl layout) {
        super(layout);
    }

    CoreAllocator(ShapeImpl shape) {
        super(shape);
    }

    private DefaultLayout getLayout() {
        return (DefaultLayout)this.layout;
    }

    @Override
    public CoreLocation constantLocation(Object value2) {
        return new CoreLocations.ConstantLocation(value2);
    }

    @Override
    public CoreLocation declaredLocation(Object value2) {
        return new CoreLocations.DeclaredLocation(value2);
    }

    @Override
    protected Location moveLocation(Location oldLocation) {
        if (oldLocation instanceof CoreLocations.LongLocation) {
            return this.newLongLocation(oldLocation.isFinal(), ((CoreLocations.LongLocation)((Object)oldLocation)).isImplicitCastIntToLong());
        }
        if (oldLocation instanceof CoreLocations.IntLocation) {
            return this.newIntLocation(oldLocation.isFinal());
        }
        if (oldLocation instanceof CoreLocations.DoubleLocation) {
            return this.newDoubleLocation(oldLocation.isFinal(), ((CoreLocations.DoubleLocation)((Object)oldLocation)).isImplicitCastIntToDouble());
        }
        if (oldLocation instanceof CoreLocations.BooleanLocation) {
            return this.newBooleanLocation(oldLocation.isFinal());
        }
        if (oldLocation instanceof CoreLocations.ObjectLocation) {
            return this.newObjectLocation(oldLocation.isFinal(), ((CoreLocations.ObjectLocation)((Object)oldLocation)).isNonNull());
        }
        assert (oldLocation instanceof CoreLocations.ValueLocation) : oldLocation;
        return this.advance(oldLocation);
    }

    @Override
    public Location newObjectLocation(boolean useFinal, boolean nonNull) {
        int insertPos;
        if (ObjectStorageOptions.InObjectFields && (insertPos = this.objectFieldSize) + 1 <= this.getLayout().getObjectFieldCount()) {
            return this.advance((Location)((Object)this.getLayout().getObjectFieldLocation(insertPos)));
        }
        return this.newObjectArrayLocation(useFinal, nonNull);
    }

    private Location newObjectArrayLocation(boolean useFinal, boolean nonNull) {
        return this.advance(new CoreLocations.ObjectArrayLocation(this.objectArraySize));
    }

    @Override
    public Location newTypedObjectLocation(boolean useFinal, Class<?> type, boolean nonNull) {
        return this.newObjectLocation(useFinal, nonNull);
    }

    @Override
    protected Location newIntLocation(boolean useFinal) {
        if (ObjectStorageOptions.PrimitiveLocations && ObjectStorageOptions.IntegerLocations) {
            if (ObjectStorageOptions.InObjectFields && this.primitiveFieldSize + this.getLayout().getLongFieldSize() <= this.getLayout().getPrimitiveFieldCount()) {
                return this.advance(new CoreLocations.IntLocationDecorator(this.getLayout().getPrimitiveFieldLocation(this.primitiveFieldSize)));
            }
            if (this.getLayout().hasPrimitiveExtensionArray()) {
                return this.advance(new CoreLocations.IntLocationDecorator(new CoreLocations.LongArrayLocation(this.primitiveArraySize)));
            }
        }
        return this.newObjectLocation(useFinal, true);
    }

    @Override
    public Location newDoubleLocation(boolean useFinal) {
        return this.newDoubleLocation(useFinal, this.getLayout().isAllowedIntToDouble());
    }

    Location newDoubleLocation(boolean useFinal, boolean allowedIntToDouble) {
        if (ObjectStorageOptions.PrimitiveLocations && ObjectStorageOptions.DoubleLocations) {
            if (ObjectStorageOptions.InObjectFields && this.primitiveFieldSize + this.getLayout().getLongFieldSize() <= this.getLayout().getPrimitiveFieldCount()) {
                return this.advance(new CoreLocations.DoubleLocationDecorator(this.getLayout().getPrimitiveFieldLocation(this.primitiveFieldSize), allowedIntToDouble));
            }
            if (this.getLayout().hasPrimitiveExtensionArray()) {
                return this.advance(new CoreLocations.DoubleLocationDecorator(new CoreLocations.LongArrayLocation(this.primitiveArraySize), allowedIntToDouble));
            }
        }
        return this.newObjectLocation(useFinal, true);
    }

    @Override
    public Location newLongLocation(boolean useFinal) {
        return this.newLongLocation(useFinal, this.getLayout().isAllowedIntToLong());
    }

    Location newLongLocation(boolean useFinal, boolean allowedIntToLong) {
        if (ObjectStorageOptions.PrimitiveLocations && ObjectStorageOptions.LongLocations) {
            if (ObjectStorageOptions.InObjectFields && this.primitiveFieldSize + this.getLayout().getLongFieldSize() <= this.getLayout().getPrimitiveFieldCount()) {
                return this.advance((Location)((Object)CoreLocations.createLongLocation(this.getLayout().getPrimitiveFieldLocation(this.primitiveFieldSize), allowedIntToLong)));
            }
            if (this.getLayout().hasPrimitiveExtensionArray()) {
                return this.advance(new CoreLocations.LongArrayLocation(this.primitiveArraySize, allowedIntToLong));
            }
        }
        return this.newObjectLocation(useFinal, true);
    }

    @Override
    public Location newBooleanLocation(boolean useFinal) {
        if (ObjectStorageOptions.PrimitiveLocations && ObjectStorageOptions.BooleanLocations && this.primitiveFieldSize + this.getLayout().getLongFieldSize() <= this.getLayout().getPrimitiveFieldCount()) {
            return this.advance(new CoreLocations.BooleanLocationDecorator(this.getLayout().getPrimitiveFieldLocation(this.primitiveFieldSize)));
        }
        return this.newObjectLocation(useFinal, true);
    }

    @Override
    protected Location locationForValue(Object value2, boolean useFinal, boolean nonNull) {
        return this.locationForValue(value2, useFinal, nonNull, 0L);
    }

    Location locationForValue(Object value2, boolean useFinal, boolean nonNull, long putFlags) {
        if (Flags.isConstant(putFlags)) {
            return this.constantLocation(value2);
        }
        if (Flags.isDeclaration(putFlags)) {
            return this.declaredLocation(value2);
        }
        if (value2 instanceof Integer) {
            return this.newIntLocation(useFinal);
        }
        if (value2 instanceof Double) {
            return this.newDoubleLocation(useFinal, Flags.isImplicitCastIntToDouble(putFlags) || this.layout.isAllowedIntToDouble());
        }
        if (value2 instanceof Long) {
            return this.newLongLocation(useFinal, Flags.isImplicitCastIntToLong(putFlags) || this.layout.isAllowedIntToLong());
        }
        if (value2 instanceof Boolean) {
            return this.newBooleanLocation(useFinal);
        }
        if (ObjectStorageOptions.TypedObjectLocations && value2 != null) {
            return this.newTypedObjectLocation(useFinal, value2.getClass(), nonNull);
        }
        return this.newObjectLocation(useFinal, nonNull && value2 != null);
    }

    @Override
    protected Location locationForType(Class<?> type, boolean useFinal, boolean nonNull) {
        if (type == Integer.TYPE) {
            return this.newIntLocation(useFinal);
        }
        if (type == Double.TYPE) {
            return this.newDoubleLocation(useFinal);
        }
        if (type == Long.TYPE) {
            return this.newLongLocation(useFinal);
        }
        if (type == Boolean.TYPE) {
            return this.newBooleanLocation(useFinal);
        }
        if (ObjectStorageOptions.TypedObjectLocations && type != null && type != Object.class) {
            assert (!type.isPrimitive()) : "unsupported primitive type";
            return this.newTypedObjectLocation(useFinal, type, nonNull);
        }
        return this.newObjectLocation(useFinal, nonNull);
    }

    @Override
    protected Location locationForValueUpcast(Object value2, Location oldLocation, long putFlags) {
        assert (!oldLocation.canStore(value2));
        if (oldLocation instanceof CoreLocations.ConstantLocation && Flags.isConstant(putFlags)) {
            return this.constantLocation(value2);
        }
        if (oldLocation instanceof CoreLocations.ValueLocation) {
            return this.locationForValue(value2, false, value2 != null);
        }
        if (oldLocation instanceof CoreLocations.TypedLocation && ((CoreLocations.TypedLocation)((Object)oldLocation)).getType().isPrimitive()) {
            if (!this.shared && ((CoreLocations.TypedLocation)((Object)oldLocation)).getType() == Integer.TYPE) {
                boolean allowedIntToDouble;
                CoreLocations.LongLocation primLocation = ((CoreLocations.PrimitiveLocationDecorator)oldLocation).getInternalLongLocation();
                boolean allowedIntToLong = this.layout.isAllowedIntToLong() || Flags.isImplicitCastIntToLong(putFlags);
                boolean bl = allowedIntToDouble = this.layout.isAllowedIntToDouble() || Flags.isImplicitCastIntToDouble(putFlags);
                if (allowedIntToLong && value2 instanceof Long) {
                    return new CoreLocations.LongLocationDecorator(primLocation, true);
                }
                if (allowedIntToDouble && value2 instanceof Double) {
                    return new CoreLocations.DoubleLocationDecorator(primLocation, true);
                }
            }
            return this.newObjectLocation(oldLocation.isFinal(), value2 != null);
        }
        return this.locationForValue(value2, false, value2 != null);
    }
}

