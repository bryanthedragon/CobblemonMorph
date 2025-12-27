package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Location;

class CoreAllocator extends ShapeImpl.BaseAllocator {
   CoreAllocator(LayoutImpl layout) {
      super(layout);
   }

   CoreAllocator(ShapeImpl shape) {
      super(shape);
   }

   private DefaultLayout getLayout() {
      return (DefaultLayout)this.layout;
   }

   public CoreLocation constantLocation(Object value) {
      return new CoreLocations.ConstantLocation(value);
   }

   public CoreLocation declaredLocation(Object value) {
      return new CoreLocations.DeclaredLocation(value);
   }

   @Override
   protected Location moveLocation(Location oldLocation) {
      if (oldLocation instanceof CoreLocations.LongLocation) {
         return this.newLongLocation(oldLocation.isFinal(), ((CoreLocations.LongLocation)oldLocation).isImplicitCastIntToLong());
      } else if (oldLocation instanceof CoreLocations.IntLocation) {
         return this.newIntLocation(oldLocation.isFinal());
      } else if (oldLocation instanceof CoreLocations.DoubleLocation) {
         return this.newDoubleLocation(oldLocation.isFinal(), ((CoreLocations.DoubleLocation)oldLocation).isImplicitCastIntToDouble());
      } else if (oldLocation instanceof CoreLocations.BooleanLocation) {
         return this.newBooleanLocation(oldLocation.isFinal());
      } else if (oldLocation instanceof CoreLocations.ObjectLocation) {
         return this.newObjectLocation(oldLocation.isFinal(), ((CoreLocations.ObjectLocation)oldLocation).isNonNull());
      } else {
         assert oldLocation instanceof CoreLocations.ValueLocation : oldLocation;

         return this.advance(oldLocation);
      }
   }

   @Override
   public Location newObjectLocation(boolean useFinal, boolean nonNull) {
      if (ObjectStorageOptions.InObjectFields) {
         int insertPos = this.objectFieldSize;
         if (insertPos + 1 <= this.getLayout().getObjectFieldCount()) {
            return this.advance((Location)this.getLayout().getObjectFieldLocation(insertPos));
         }
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
            return this.advance(
               new CoreLocations.DoubleLocationDecorator(this.getLayout().getPrimitiveFieldLocation(this.primitiveFieldSize), allowedIntToDouble)
            );
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
            return this.advance(
               (Location)CoreLocations.createLongLocation(this.getLayout().getPrimitiveFieldLocation(this.primitiveFieldSize), allowedIntToLong)
            );
         }

         if (this.getLayout().hasPrimitiveExtensionArray()) {
            return this.advance(new CoreLocations.LongArrayLocation(this.primitiveArraySize, allowedIntToLong));
         }
      }

      return this.newObjectLocation(useFinal, true);
   }

   @Override
   public Location newBooleanLocation(boolean useFinal) {
      return ObjectStorageOptions.PrimitiveLocations
            && ObjectStorageOptions.BooleanLocations
            && this.primitiveFieldSize + this.getLayout().getLongFieldSize() <= this.getLayout().getPrimitiveFieldCount()
         ? this.advance(new CoreLocations.BooleanLocationDecorator(this.getLayout().getPrimitiveFieldLocation(this.primitiveFieldSize)))
         : this.newObjectLocation(useFinal, true);
   }

   @Override
   protected Location locationForValue(Object value, boolean useFinal, boolean nonNull) {
      return this.locationForValue(value, useFinal, nonNull, 0L);
   }

   Location locationForValue(Object value, boolean useFinal, boolean nonNull, long putFlags) {
      if (Flags.isConstant(putFlags)) {
         return this.constantLocation(value);
      } else if (Flags.isDeclaration(putFlags)) {
         return this.declaredLocation(value);
      } else if (value instanceof Integer) {
         return this.newIntLocation(useFinal);
      } else if (value instanceof Double) {
         return this.newDoubleLocation(useFinal, Flags.isImplicitCastIntToDouble(putFlags) || this.layout.isAllowedIntToDouble());
      } else if (value instanceof Long) {
         return this.newLongLocation(useFinal, Flags.isImplicitCastIntToLong(putFlags) || this.layout.isAllowedIntToLong());
      } else if (value instanceof Boolean) {
         return this.newBooleanLocation(useFinal);
      } else {
         return ObjectStorageOptions.TypedObjectLocations && value != null
            ? this.newTypedObjectLocation(useFinal, value.getClass(), nonNull)
            : this.newObjectLocation(useFinal, nonNull && value != null);
      }
   }

   @Override
   protected Location locationForType(Class<?> type, boolean useFinal, boolean nonNull) {
      if (type == int.class) {
         return this.newIntLocation(useFinal);
      } else if (type == double.class) {
         return this.newDoubleLocation(useFinal);
      } else if (type == long.class) {
         return this.newLongLocation(useFinal);
      } else if (type == boolean.class) {
         return this.newBooleanLocation(useFinal);
      } else if (!ObjectStorageOptions.TypedObjectLocations || type == null || type == Object.class) {
         return this.newObjectLocation(useFinal, nonNull);
      } else {
         assert !type.isPrimitive() : "unsupported primitive type";

         return this.newTypedObjectLocation(useFinal, type, nonNull);
      }
   }

   @Override
   protected Location locationForValueUpcast(Object value, Location oldLocation, long putFlags) {
      assert !oldLocation.canStore(value);

      if (oldLocation instanceof CoreLocations.ConstantLocation && Flags.isConstant(putFlags)) {
         return this.constantLocation(value);
      } else if (oldLocation instanceof CoreLocations.ValueLocation) {
         return this.locationForValue(value, false, value != null);
      } else if (oldLocation instanceof CoreLocations.TypedLocation && ((CoreLocations.TypedLocation)oldLocation).getType().isPrimitive()) {
         if (!this.shared && ((CoreLocations.TypedLocation)oldLocation).getType() == int.class) {
            CoreLocations.LongLocation primLocation = ((CoreLocations.PrimitiveLocationDecorator)oldLocation).getInternalLongLocation();
            boolean allowedIntToLong = this.layout.isAllowedIntToLong() || Flags.isImplicitCastIntToLong(putFlags);
            boolean allowedIntToDouble = this.layout.isAllowedIntToDouble() || Flags.isImplicitCastIntToDouble(putFlags);
            if (allowedIntToLong && value instanceof Long) {
               return new CoreLocations.LongLocationDecorator(primLocation, true);
            }

            if (allowedIntToDouble && value instanceof Double) {
               return new CoreLocations.DoubleLocationDecorator(primLocation, true);
            }
         }

         return this.newObjectLocation(oldLocation.isFinal(), value != null);
      } else {
         return this.locationForValue(value, false, value != null);
      }
   }
}
