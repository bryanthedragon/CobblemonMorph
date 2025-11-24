
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;

public abstract class TypedArrayFactory
extends Enum<TypedArrayFactory>
implements PrototypeSupplier {
    public static final /* enum */ TypedArrayFactory Int8Array = new TypedArrayFactory(1){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropInt8Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectInt8Array(this, offset);
            }
            return new TypedArray.Int8Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Uint8Array = new TypedArrayFactory(1){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropUint8Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectUint8Array(this, offset);
            }
            return new TypedArray.Uint8Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Uint8ClampedArray = new TypedArrayFactory(1){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropUint8ClampedArray(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectUint8ClampedArray(this, offset);
            }
            return new TypedArray.Uint8ClampedArray(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Int16Array = new TypedArrayFactory(2){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropInt16Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectInt16Array(this, offset);
            }
            return new TypedArray.Int16Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Uint16Array = new TypedArrayFactory(2){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropUint16Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectUint16Array(this, offset);
            }
            return new TypedArray.Uint16Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Int32Array = new TypedArrayFactory(4){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropInt32Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectInt32Array(this, offset);
            }
            return new TypedArray.Int32Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Uint32Array = new TypedArrayFactory(4){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropUint32Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectUint32Array(this, offset);
            }
            return new TypedArray.Uint32Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Float32Array = new TypedArrayFactory(4){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropFloat32Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectFloat32Array(this, offset);
            }
            return new TypedArray.Float32Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory Float64Array = new TypedArrayFactory(8){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropFloat64Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectFloat64Array(this, offset);
            }
            return new TypedArray.Float64Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory BigInt64Array = new TypedArrayFactory(8){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropBigInt64Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectBigInt64Array(this, offset);
            }
            return new TypedArray.BigInt64Array(this, offset);
        }
    };
    public static final /* enum */ TypedArrayFactory BigUint64Array = new TypedArrayFactory(8){

        @Override
        TypedArray instantiateArrayType(byte bufferType, boolean offset) {
            if (bufferType == -1) {
                return new TypedArray.InteropBigUint64Array(this, offset);
            }
            if (bufferType == 1) {
                return new TypedArray.DirectBigUint64Array(this, offset);
            }
            return new TypedArray.BigUint64Array(this, offset);
        }
    };
    private final int bytesPerElement;
    private final TypedArray arrayType;
    private final TypedArray arrayTypeWithOffset;
    private final TypedArray directArrayType;
    private final TypedArray directArrayTypeWithOffset;
    private final TypedArray interopArrayType;
    private final TypedArray interopArrayTypeWithOffset;
    static final TypedArrayFactory[] FACTORIES;
    private static TypedArrayFactory[] FACTORIES_NO_BIGINT;
    private static final /* synthetic */ TypedArrayFactory[] $VALUES;

    public static TypedArrayFactory[] values() {
        return (TypedArrayFactory[])$VALUES.clone();
    }

    public static TypedArrayFactory valueOf(String name) {
        return Enum.valueOf(TypedArrayFactory.class, name);
    }

    private TypedArrayFactory(int bytesPerElement) {
        this.bytesPerElement = bytesPerElement;
        this.arrayType = this.instantiateArrayType((byte)0, false);
        this.arrayTypeWithOffset = this.instantiateArrayType((byte)0, true);
        this.directArrayType = this.instantiateArrayType((byte)1, false);
        this.directArrayTypeWithOffset = this.instantiateArrayType((byte)1, true);
        this.interopArrayType = this.instantiateArrayType((byte)-1, false);
        this.interopArrayTypeWithOffset = this.instantiateArrayType((byte)-1, true);
        assert (!this.arrayType.hasOffset() && this.arrayTypeWithOffset.hasOffset() && !this.arrayType.isDirect() && !this.arrayTypeWithOffset.isDirect() && !this.directArrayType.hasOffset() && this.directArrayTypeWithOffset.hasOffset() && this.directArrayType.isDirect() && this.directArrayTypeWithOffset.isDirect() && !this.interopArrayType.hasOffset() && this.interopArrayTypeWithOffset.hasOffset() && this.interopArrayType.isInterop() && this.interopArrayTypeWithOffset.isInterop());
    }

    public final TypedArray createArrayType(boolean direct, boolean offset) {
        return this.createArrayType(direct, offset, false);
    }

    public final TypedArray createArrayType(boolean direct, boolean offset, boolean interop) {
        if (interop) {
            if (offset) {
                return this.interopArrayTypeWithOffset;
            }
            return this.interopArrayType;
        }
        if (direct) {
            if (offset) {
                return this.directArrayTypeWithOffset;
            }
            return this.directArrayType;
        }
        if (offset) {
            return this.arrayTypeWithOffset;
        }
        return this.arrayType;
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

    abstract TypedArray instantiateArrayType(byte var1, boolean var2);

    @Override
    public final JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getArrayBufferViewPrototype(this);
    }

    public static TypedArrayFactory[] getNoBigIntFactories() {
        if (FACTORIES_NO_BIGINT == null) {
            TypedArrayFactory[] allFactories = TypedArrayFactory.values();
            ArrayList<TypedArrayFactory> noBigIntFactories = new ArrayList<TypedArrayFactory>(allFactories.length);
            for (TypedArrayFactory fact : allFactories) {
                if (JSRuntime.isTypedArrayBigIntFactory(fact)) continue;
                noBigIntFactories.add(fact);
            }
            FACTORIES_NO_BIGINT = noBigIntFactories.toArray(new TypedArrayFactory[0]);
        }
        return FACTORIES_NO_BIGINT;
    }

    static {
        $VALUES = new TypedArrayFactory[]{Int8Array, Uint8Array, Uint8ClampedArray, Int16Array, Uint16Array, Int32Array, Uint32Array, Float32Array, Float64Array, BigInt64Array, BigUint64Array};
        FACTORIES = TypedArrayFactory.values();
    }
}

