package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.List;

public enum TypedArrayFactory implements PrototypeSupplier {
   Int8Array(1) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropInt8Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectInt8Array(this, offset) : new TypedArray.Int8Array(this, offset));
         }
      }
   },
   Uint8Array(1) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropUint8Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectUint8Array(this, offset) : new TypedArray.Uint8Array(this, offset));
         }
      }
   },
   Uint8ClampedArray(1) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropUint8ClampedArray(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectUint8ClampedArray(this, offset) : new TypedArray.Uint8ClampedArray(this, offset));
         }
      }
   },
   Int16Array(2) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropInt16Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectInt16Array(this, offset) : new TypedArray.Int16Array(this, offset));
         }
      }
   },
   Uint16Array(2) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropUint16Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectUint16Array(this, offset) : new TypedArray.Uint16Array(this, offset));
         }
      }
   },
   Int32Array(4) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropInt32Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectInt32Array(this, offset) : new TypedArray.Int32Array(this, offset));
         }
      }
   },
   Uint32Array(4) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropUint32Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectUint32Array(this, offset) : new TypedArray.Uint32Array(this, offset));
         }
      }
   },
   Float32Array(4) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropFloat32Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectFloat32Array(this, offset) : new TypedArray.Float32Array(this, offset));
         }
      }
   },
   Float64Array(8) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropFloat64Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectFloat64Array(this, offset) : new TypedArray.Float64Array(this, offset));
         }
      }
   },
   BigInt64Array(8) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropBigInt64Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectBigInt64Array(this, offset) : new TypedArray.BigInt64Array(this, offset));
         }
      }
   },
   BigUint64Array(8) {
      @Override
      TypedArray instantiateArrayType(byte bufferType, boolean offset) {
         if (bufferType == -1) {
            return new TypedArray.InteropBigUint64Array(this, offset);
         } else {
            return (TypedArray)(bufferType == 1 ? new TypedArray.DirectBigUint64Array(this, offset) : new TypedArray.BigUint64Array(this, offset));
         }
      }
   };

   private final int bytesPerElement;
   private final TypedArray arrayType;
   private final TypedArray arrayTypeWithOffset;
   private final TypedArray directArrayType;
   private final TypedArray directArrayTypeWithOffset;
   private final TypedArray interopArrayType;
   private final TypedArray interopArrayTypeWithOffset;
   static final TypedArrayFactory[] FACTORIES = values();
   private static TypedArrayFactory[] FACTORIES_NO_BIGINT;

   private TypedArrayFactory(int bytesPerElement) {
      this.bytesPerElement = bytesPerElement;
      this.arrayType = this.instantiateArrayType((byte)0, false);
      this.arrayTypeWithOffset = this.instantiateArrayType((byte)0, true);
      this.directArrayType = this.instantiateArrayType((byte)1, false);
      this.directArrayTypeWithOffset = this.instantiateArrayType((byte)1, true);
      this.interopArrayType = this.instantiateArrayType((byte)-1, false);
      this.interopArrayTypeWithOffset = this.instantiateArrayType((byte)-1, true);

      assert !this.arrayType.hasOffset()
         && this.arrayTypeWithOffset.hasOffset()
         && !this.arrayType.isDirect()
         && !this.arrayTypeWithOffset.isDirect()
         && !this.directArrayType.hasOffset()
         && this.directArrayTypeWithOffset.hasOffset()
         && this.directArrayType.isDirect()
         && this.directArrayTypeWithOffset.isDirect()
         && !this.interopArrayType.hasOffset()
         && this.interopArrayTypeWithOffset.hasOffset()
         && this.interopArrayType.isInterop()
         && this.interopArrayTypeWithOffset.isInterop();
   }

   public final TypedArray createArrayType(boolean direct, boolean offset) {
      return this.createArrayType(direct, offset, false);
   }

   public final TypedArray createArrayType(boolean direct, boolean offset, boolean interop) {
      if (interop) {
         return offset ? this.interopArrayTypeWithOffset : this.interopArrayType;
      } else if (direct) {
         return offset ? this.directArrayTypeWithOffset : this.directArrayType;
      } else {
         return offset ? this.arrayTypeWithOffset : this.arrayType;
      }
   }

   public final int getBytesPerElement() {
      return this.bytesPerElement;
   }

   public final int getFactoryIndex() {
      return this.ordinal();
   }

   public final TruffleString getName() {
      return Strings.fromJavaString(this.name());
   }

   abstract TypedArray instantiateArrayType(byte bufferType, boolean offset);

   @Override
   public final JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getArrayBufferViewPrototype(this);
   }

   public static TypedArrayFactory[] getNoBigIntFactories() {
      if (FACTORIES_NO_BIGINT == null) {
         TypedArrayFactory[] allFactories = values();
         List<TypedArrayFactory> noBigIntFactories = new ArrayList<>(allFactories.length);

         for (TypedArrayFactory fact : allFactories) {
            if (!JSRuntime.isTypedArrayBigIntFactory(fact)) {
               noBigIntFactories.add(fact);
            }
         }

         FACTORIES_NO_BIGINT = noBigIntFactories.toArray(new TypedArrayFactory[0]);
      }

      return FACTORIES_NO_BIGINT;
   }
}
