
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.impl.DefaultRuntimeAccessor;
import com.oracle.truffle.api.impl.ImplAccessor;
import java.lang.reflect.Field;
import java.util.Arrays;
import sun.misc.Unsafe;

public final class FrameWithoutBoxing
implements VirtualFrame,
MaterializedFrame {
    private static final String UNEXPECTED_STATIC_WRITE = "Unexpected static write of non-static frame slot";
    private static final String UNEXPECTED_NON_STATIC_READ = "Unexpected non-static read of static frame slot";
    private static final String UNEXPECTED_NON_STATIC_WRITE = "Unexpected non-static write of static frame slot";
    private static final boolean ASSERTIONS_ENABLED;
    private final FrameDescriptor descriptor;
    private final Object[] arguments;
    private final Object[] indexedLocals;
    private final long[] indexedPrimitiveLocals;
    private final byte[] indexedTags;
    private Object[] auxiliarySlots;
    private static final Object OBJECT_LOCATION;
    private static final Object PRIMITIVE_LOCATION;
    private static final long INT_MASK = 0xFFFFFFFFL;
    public static final byte OBJECT_TAG = 0;
    public static final byte LONG_TAG = 1;
    public static final byte INT_TAG = 2;
    public static final byte DOUBLE_TAG = 3;
    public static final byte FLOAT_TAG = 4;
    public static final byte BOOLEAN_TAG = 5;
    public static final byte BYTE_TAG = 6;
    public static final byte ILLEGAL_TAG = 7;
    public static final byte STATIC_TAG = 8;
    private static final byte STATIC_OBJECT_TAG = 8;
    private static final byte STATIC_LONG_TAG = 9;
    private static final byte STATIC_INT_TAG = 10;
    private static final byte STATIC_DOUBLE_TAG = 11;
    private static final byte STATIC_FLOAT_TAG = 12;
    private static final byte STATIC_BOOLEAN_TAG = 13;
    private static final byte STATIC_BYTE_TAG = 14;
    private static final byte STATIC_ILLEGAL_TAG = 15;
    private static final Object[] EMPTY_OBJECT_ARRAY;
    private static final long[] EMPTY_LONG_ARRAY;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final Unsafe UNSAFE;

    private static Unsafe initUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException se) {
            try {
                Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafe.setAccessible(true);
                return (Unsafe)theUnsafe.get(Unsafe.class);
            }
            catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe", e);
            }
        }
    }

    public FrameWithoutBoxing(FrameDescriptor descriptor, Object[] arguments) {
        byte[] indexedTagsArray;
        long[] indexedPrimitiveLocalsArray;
        Object[] indexedLocalsArray;
        int indexedSize = descriptor.getNumberOfSlots();
        int auxiliarySize = descriptor.getNumberOfAuxiliarySlots();
        Object defaultValue = descriptor.getDefaultValue();
        Accessor.FrameSupport frameSupport = DefaultRuntimeAccessor.FRAME;
        if (indexedSize == 0) {
            indexedLocalsArray = EMPTY_OBJECT_ARRAY;
            indexedPrimitiveLocalsArray = EMPTY_LONG_ARRAY;
            indexedTagsArray = EMPTY_BYTE_ARRAY;
        } else {
            indexedLocalsArray = new Object[indexedSize];
            if (defaultValue != null) {
                Arrays.fill(indexedLocalsArray, defaultValue);
            }
            indexedPrimitiveLocalsArray = new long[indexedSize];
            indexedTagsArray = new byte[indexedSize];
            if (frameSupport.usesAllStaticMode(descriptor)) {
                Arrays.fill(indexedTagsArray, (byte)8);
            } else if (frameSupport.usesMixedStaticMode(descriptor)) {
                for (int slot = 0; slot < indexedTagsArray.length; ++slot) {
                    if (descriptor.getSlotKind(slot) != FrameSlotKind.Static) continue;
                    indexedTagsArray[slot] = 8;
                }
            }
        }
        Object[] auxiliarySlotsArray = auxiliarySize == 0 ? EMPTY_OBJECT_ARRAY : new Object[auxiliarySize];
        this.descriptor = descriptor;
        this.arguments = arguments;
        this.indexedLocals = indexedLocalsArray;
        this.indexedPrimitiveLocals = indexedPrimitiveLocalsArray;
        this.indexedTags = indexedTagsArray;
        this.auxiliarySlots = auxiliarySlotsArray;
    }

    @Override
    public Object[] getArguments() {
        return FrameWithoutBoxing.unsafeCast(this.arguments, Object[].class, true, true, true);
    }

    @Override
    public MaterializedFrame materialize() {
        ImplAccessor.frameSupportAccessor().markMaterializeCalled(this.descriptor);
        return this;
    }

    private static long extend(int value2) {
        return (long)value2 & 0xFFFFFFFFL;
    }

    @Override
    public FrameDescriptor getFrameDescriptor() {
        return FrameWithoutBoxing.unsafeCast(this.descriptor, FrameDescriptor.class, true, true, false);
    }

    private static FrameSlotTypeException frameSlotTypeException() throws FrameSlotTypeException {
        CompilerAsserts.neverPartOfCompilation();
        throw new FrameSlotTypeException();
    }

    private static long getPrimitiveOffset(int slotIndex) {
        return (long)Unsafe.ARRAY_LONG_BASE_OFFSET + (long)slotIndex * (long)Unsafe.ARRAY_LONG_INDEX_SCALE;
    }

    @Override
    public byte getTag(int slotIndex) {
        try {
            byte tag = this.getIndexedTags()[slotIndex];
            return tag < 8 ? tag : (byte)8;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw CompilerDirectives.shouldNotReachHere("invalid indexed slot", e);
        }
    }

    private static <T> T unsafeCast(Object value2, Class<T> type, boolean condition2, boolean nonNull, boolean exact) {
        return (T)value2;
    }

    private static long unsafeGetLong(Object receiver, long offset, boolean condition2, Object locationIdentity) {
        return UNSAFE.getLong(receiver, offset);
    }

    private static Object unsafeGetObject(Object receiver, long offset, boolean condition2, Object locationIdentity) {
        return UNSAFE.getObject(receiver, offset);
    }

    private static void unsafePutLong(Object receiver, long offset, long value2, Object locationIdentity) {
        UNSAFE.putLong(receiver, offset, value2);
    }

    private static void unsafePutObject(Object receiver, long offset, Object value2, Object locationIdentity) {
        UNSAFE.putObject(receiver, offset, value2);
    }

    @Override
    public Object getValue(int slot) {
        byte tag = this.getTag(slot);
        assert ((this.indexedTags[slot] & 8) == 0) : "Unexpected non-static read of static frame slot";
        switch (tag) {
            case 5: {
                return this.getBoolean(slot);
            }
            case 6: {
                return this.getByte(slot);
            }
            case 2: {
                return this.getInt(slot);
            }
            case 3: {
                return this.getDouble(slot);
            }
            case 1: {
                return this.getLong(slot);
            }
            case 4: {
                return Float.valueOf(this.getFloat(slot));
            }
            case 0: {
                return this.getObject(slot);
            }
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    private Object[] getIndexedLocals() {
        return FrameWithoutBoxing.unsafeCast(this.indexedLocals, Object[].class, true, true, true);
    }

    private long[] getIndexedPrimitiveLocals() {
        return FrameWithoutBoxing.unsafeCast(this.indexedPrimitiveLocals, long[].class, true, true, true);
    }

    private byte[] getIndexedTags() {
        return FrameWithoutBoxing.unsafeCast(this.indexedTags, byte[].class, true, true, true);
    }

    @Override
    public Object getObject(int slot) throws FrameSlotTypeException {
        boolean condition2 = this.verifyIndexedGet(slot, (byte)0);
        return FrameWithoutBoxing.unsafeGetObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)slot * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, condition2, OBJECT_LOCATION);
    }

    @Override
    public void setObject(int slot, Object value2) {
        this.verifyIndexedSet(slot, (byte)0);
        FrameWithoutBoxing.unsafePutObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)slot * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, value2, OBJECT_LOCATION);
    }

    @Override
    public byte getByte(int slot) throws FrameSlotTypeException {
        boolean condition2 = this.verifyIndexedGet(slot, (byte)6);
        return (byte)FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), condition2, PRIMITIVE_LOCATION);
    }

    @Override
    public void setByte(int slot, byte value2) {
        this.verifyIndexedSet(slot, (byte)6);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), FrameWithoutBoxing.extend(value2), PRIMITIVE_LOCATION);
    }

    @Override
    public boolean getBoolean(int slot) throws FrameSlotTypeException {
        boolean condition2 = this.verifyIndexedGet(slot, (byte)5);
        return (int)FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), condition2, PRIMITIVE_LOCATION) != 0;
    }

    @Override
    public void setBoolean(int slot, boolean value2) {
        this.verifyIndexedSet(slot, (byte)5);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), value2 ? 1L : 0L, PRIMITIVE_LOCATION);
    }

    @Override
    public float getFloat(int slot) throws FrameSlotTypeException {
        boolean condition2 = this.verifyIndexedGet(slot, (byte)4);
        return Float.intBitsToFloat((int)FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), condition2, PRIMITIVE_LOCATION));
    }

    @Override
    public void setFloat(int slot, float value2) {
        this.verifyIndexedSet(slot, (byte)4);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), FrameWithoutBoxing.extend(Float.floatToRawIntBits(value2)), PRIMITIVE_LOCATION);
    }

    @Override
    public long getLong(int slot) throws FrameSlotTypeException {
        boolean condition2 = this.verifyIndexedGet(slot, (byte)1);
        return FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), condition2, PRIMITIVE_LOCATION);
    }

    @Override
    public void setLong(int slot, long value2) {
        this.verifyIndexedSet(slot, (byte)1);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), value2, PRIMITIVE_LOCATION);
    }

    @Override
    public int getInt(int slot) throws FrameSlotTypeException {
        boolean condition2 = this.verifyIndexedGet(slot, (byte)2);
        return (int)FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), condition2, PRIMITIVE_LOCATION);
    }

    @Override
    public void setInt(int slot, int value2) {
        this.verifyIndexedSet(slot, (byte)2);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), FrameWithoutBoxing.extend(value2), PRIMITIVE_LOCATION);
    }

    @Override
    public double getDouble(int slot) throws FrameSlotTypeException {
        boolean condition2 = this.verifyIndexedGet(slot, (byte)3);
        return Double.longBitsToDouble(FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), condition2, PRIMITIVE_LOCATION));
    }

    @Override
    public void setDouble(int slot, double value2) {
        this.verifyIndexedSet(slot, (byte)3);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), Double.doubleToRawLongBits(value2), PRIMITIVE_LOCATION);
    }

    @Override
    public void copy(int srcSlot, int destSlot) {
        byte tag = this.getIndexedTagChecked(srcSlot);
        Object value2 = FrameWithoutBoxing.unsafeGetObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)srcSlot * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, true, OBJECT_LOCATION);
        this.verifyIndexedSet(destSlot, tag);
        FrameWithoutBoxing.unsafePutObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)destSlot * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, value2, OBJECT_LOCATION);
        long primitiveValue = FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(srcSlot), true, PRIMITIVE_LOCATION);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(destSlot), primitiveValue, PRIMITIVE_LOCATION);
    }

    @Override
    public void swap(int first, int second) {
        byte firstTag = this.getIndexedTagChecked(first);
        Object firstValue = FrameWithoutBoxing.unsafeGetObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)first * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, true, OBJECT_LOCATION);
        long firstPrimitiveValue = FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(first), true, PRIMITIVE_LOCATION);
        byte secondTag = this.getIndexedTagChecked(second);
        Object secondValue = FrameWithoutBoxing.unsafeGetObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)second * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, true, OBJECT_LOCATION);
        long secondPrimitiveValue = FrameWithoutBoxing.unsafeGetLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(second), true, PRIMITIVE_LOCATION);
        this.verifyIndexedSet(first, secondTag);
        this.verifyIndexedSet(second, firstTag);
        FrameWithoutBoxing.unsafePutObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)first * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, secondValue, OBJECT_LOCATION);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(first), secondPrimitiveValue, PRIMITIVE_LOCATION);
        FrameWithoutBoxing.unsafePutObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)second * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, firstValue, OBJECT_LOCATION);
        FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(second), firstPrimitiveValue, PRIMITIVE_LOCATION);
    }

    private void verifyIndexedSet(int slot, byte tag) {
        assert ((this.indexedTags[slot] & 8) == 0) : "Unexpected non-static write of static frame slot";
        this.getIndexedTags()[slot] = tag;
    }

    private boolean verifyIndexedGet(int slot, byte expectedTag) throws FrameSlotTypeException {
        boolean condition2;
        byte actualTag = this.getIndexedTagChecked(slot);
        boolean bl = condition2 = actualTag == expectedTag;
        if (!condition2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw FrameWithoutBoxing.frameSlotTypeException();
        }
        return condition2;
    }

    private byte getIndexedTagChecked(int slot) {
        byte tag = this.getIndexedTags()[slot];
        assert ((tag & 8) == 0) : "Unexpected non-static read of static frame slot";
        return tag;
    }

    @Override
    public boolean isObject(int slot) {
        return this.getTag(slot) == 0;
    }

    @Override
    public boolean isByte(int slot) {
        return this.getTag(slot) == 6;
    }

    @Override
    public boolean isBoolean(int slot) {
        return this.getTag(slot) == 5;
    }

    @Override
    public boolean isInt(int slot) {
        return this.getTag(slot) == 2;
    }

    @Override
    public boolean isLong(int slot) {
        return this.getTag(slot) == 1;
    }

    @Override
    public boolean isFloat(int slot) {
        return this.getTag(slot) == 4;
    }

    @Override
    public boolean isDouble(int slot) {
        return this.getTag(slot) == 3;
    }

    @Override
    public boolean isStatic(int slot) {
        return this.getTag(slot) == 8;
    }

    @Override
    public void clear(int slot) {
        this.verifyIndexedSet(slot, (byte)7);
        FrameWithoutBoxing.unsafePutObject(this.getIndexedLocals(), (long)Unsafe.ARRAY_OBJECT_BASE_OFFSET + (long)slot * (long)Unsafe.ARRAY_OBJECT_INDEX_SCALE, null, OBJECT_LOCATION);
        if (CompilerDirectives.inCompiledCode()) {
            FrameWithoutBoxing.unsafePutLong(this.getIndexedPrimitiveLocals(), FrameWithoutBoxing.getPrimitiveOffset(slot), 0L, PRIMITIVE_LOCATION);
        }
    }

    @Override
    public void setAuxiliarySlot(int slot, Object value2) {
        if (this.auxiliarySlots.length <= slot) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.auxiliarySlots = Arrays.copyOf(this.auxiliarySlots, this.descriptor.getNumberOfAuxiliarySlots());
        }
        this.auxiliarySlots[slot] = value2;
    }

    @Override
    public Object getAuxiliarySlot(int slot) {
        return slot < this.auxiliarySlots.length ? this.auxiliarySlots[slot] : null;
    }

    @Override
    public Object getObjectStatic(int slot) {
        assert (this.indexedTags[slot] == 8) : "Unexpected read of static object value";
        return this.indexedLocals[slot];
    }

    @Override
    public void setObjectStatic(int slot, Object value2) {
        assert ((this.indexedTags[slot] & 8) != 0) : "Unexpected static write of non-static frame slot";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 8;
        }
        this.indexedLocals[slot] = value2;
    }

    @Override
    public byte getByteStatic(int slot) {
        assert (this.indexedTags[slot] == 14) : "Unexpected read of static byte value";
        return (byte)this.indexedPrimitiveLocals[slot];
    }

    @Override
    public void setByteStatic(int slot, byte value2) {
        assert ((this.indexedTags[slot] & 8) != 0) : "Unexpected static write of non-static frame slot";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 14;
        }
        this.indexedPrimitiveLocals[slot] = FrameWithoutBoxing.extend(value2);
    }

    @Override
    public boolean getBooleanStatic(int slot) {
        assert (this.indexedTags[slot] == 13) : "Unexpected read of static boolean value";
        return (int)this.indexedPrimitiveLocals[slot] != 0;
    }

    @Override
    public void setBooleanStatic(int slot, boolean value2) {
        assert ((this.indexedTags[slot] & 8) != 0) : "Unexpected static write of non-static frame slot";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 13;
        }
        this.indexedPrimitiveLocals[slot] = value2 ? 1L : 0L;
    }

    @Override
    public int getIntStatic(int slot) {
        assert (this.indexedTags[slot] == 10) : "Unexpected read of static int value";
        return (int)this.indexedPrimitiveLocals[slot];
    }

    @Override
    public void setIntStatic(int slot, int value2) {
        assert ((this.indexedTags[slot] & 8) != 0) : "Unexpected static write of non-static frame slot";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 10;
        }
        this.indexedPrimitiveLocals[slot] = FrameWithoutBoxing.extend(value2);
    }

    @Override
    public long getLongStatic(int slot) {
        assert (this.indexedTags[slot] == 9) : "Unexpected read of static long value";
        return this.indexedPrimitiveLocals[slot];
    }

    @Override
    public void setLongStatic(int slot, long value2) {
        assert ((this.indexedTags[slot] & 8) != 0) : "Unexpected static write of non-static frame slot";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 9;
        }
        this.indexedPrimitiveLocals[slot] = value2;
    }

    @Override
    public float getFloatStatic(int slot) {
        assert (this.indexedTags[slot] == 12) : "Unexpected read of static float value";
        return Float.intBitsToFloat((int)this.indexedPrimitiveLocals[slot]);
    }

    @Override
    public void setFloatStatic(int slot, float value2) {
        assert ((this.indexedTags[slot] & 8) != 0) : "Unexpected static write of non-static frame slot";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 12;
        }
        this.indexedPrimitiveLocals[slot] = FrameWithoutBoxing.extend(Float.floatToRawIntBits(value2));
    }

    @Override
    public double getDoubleStatic(int slot) {
        assert (this.indexedTags[slot] == 11) : "Unexpected read of static double value";
        return Double.longBitsToDouble(this.indexedPrimitiveLocals[slot]);
    }

    @Override
    public void setDoubleStatic(int slot, double value2) {
        assert ((this.indexedTags[slot] & 8) != 0) : "Unexpected static write of non-static frame slot";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 11;
        }
        this.indexedPrimitiveLocals[slot] = Double.doubleToRawLongBits(value2);
    }

    @Override
    public void copyPrimitiveStatic(int srcSlot, int destSlot) {
        assert (this.indexedTags[srcSlot] > 8 && (this.indexedTags[destSlot] & 8) != 0) : "Unexpected copy of static primitive value ";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[destSlot] = this.indexedTags[srcSlot];
        }
        this.indexedPrimitiveLocals[destSlot] = this.indexedPrimitiveLocals[srcSlot];
    }

    @Override
    public void copyObjectStatic(int srcSlot, int destSlot) {
        assert ((this.indexedTags[srcSlot] == 8 || this.indexedTags[srcSlot] == 15) && (this.indexedTags[destSlot] & 8) != 0) : "Unexpected copy of static object value";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[destSlot] = this.indexedTags[srcSlot];
        }
        this.indexedLocals[destSlot] = this.indexedLocals[srcSlot];
    }

    @Override
    public void copyStatic(int srcSlot, int destSlot) {
        assert (this.indexedTags[srcSlot] >= 8 && this.indexedTags[destSlot] >= 8) : "Unexpected copy of static value";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[destSlot] = this.indexedTags[srcSlot];
        }
        this.indexedLocals[destSlot] = this.indexedLocals[srcSlot];
        this.indexedPrimitiveLocals[destSlot] = this.indexedPrimitiveLocals[srcSlot];
    }

    @Override
    public void swapPrimitiveStatic(int first, int second) {
        long secondValue;
        assert (this.indexedTags[first] > 8 && this.indexedTags[second] > 8) : "Unexpected swap of static primitive value";
        if (ASSERTIONS_ENABLED) {
            byte swapTag = this.indexedTags[first];
            this.indexedTags[first] = this.indexedTags[second];
            this.indexedTags[second] = swapTag;
        }
        long firstValue = this.indexedPrimitiveLocals[first];
        this.indexedPrimitiveLocals[first] = secondValue = this.indexedPrimitiveLocals[second];
        this.indexedPrimitiveLocals[second] = firstValue;
    }

    @Override
    public void swapObjectStatic(int first, int second) {
        Object secondValue;
        assert (!(this.indexedTags[first] != 8 && this.indexedTags[first] != 15 || this.indexedTags[second] != 8 && this.indexedTags[second] != 15)) : "Unexpected swap of static object value";
        if (ASSERTIONS_ENABLED) {
            byte swapTag = this.indexedTags[first];
            this.indexedTags[first] = this.indexedTags[second];
            this.indexedTags[second] = swapTag;
        }
        Object firstValue = this.indexedLocals[first];
        this.indexedLocals[first] = secondValue = this.indexedLocals[second];
        this.indexedLocals[second] = firstValue;
    }

    @Override
    public void swapStatic(int first, int second) {
        assert (this.indexedTags[first] >= 8 && this.indexedTags[second] >= 8) : "Unexpected swap of static value";
        if (ASSERTIONS_ENABLED) {
            byte swapTag = this.indexedTags[first];
            this.indexedTags[first] = this.indexedTags[second];
            this.indexedTags[second] = swapTag;
        }
        Object firstValue = this.indexedLocals[first];
        Object secondValue = this.indexedLocals[second];
        long firstPrimitiveValue = this.indexedPrimitiveLocals[first];
        long secondPrimitiveValue = this.indexedPrimitiveLocals[second];
        this.indexedLocals[first] = secondValue;
        this.indexedLocals[second] = firstValue;
        this.indexedPrimitiveLocals[first] = secondPrimitiveValue;
        this.indexedPrimitiveLocals[second] = firstPrimitiveValue;
    }

    @Override
    public void clearPrimitiveStatic(int slot) {
        assert (this.indexedTags[slot] > 8) : "Unexpected clear of static primitive value";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 15;
        }
        if (CompilerDirectives.inCompiledCode()) {
            this.indexedPrimitiveLocals[slot] = 0L;
        }
    }

    @Override
    public void clearObjectStatic(int slot) {
        assert (this.indexedTags[slot] == 8 || this.indexedTags[slot] == 15) : "Unexpected clear of static object value";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 15;
        }
        this.indexedLocals[slot] = null;
    }

    @Override
    public void clearStatic(int slot) {
        assert (this.indexedTags[slot] >= 8) : "Unexpected clear of static value";
        if (ASSERTIONS_ENABLED) {
            this.indexedTags[slot] = 15;
        }
        if (CompilerDirectives.inCompiledCode()) {
            this.indexedPrimitiveLocals[slot] = 0L;
        }
        this.indexedLocals[slot] = null;
    }

    void transferOSRStaticSlot(FrameWithoutBoxing target, int slot) {
        if (ASSERTIONS_ENABLED) {
            byte tag = this.indexedTags[slot];
            this.indexedTags[slot] = 8;
            target.setObjectStatic(slot, this.getObjectStatic(slot));
            this.indexedTags[slot] = 9;
            target.setLongStatic(slot, this.getLongStatic(slot));
            this.indexedTags[slot] = tag;
            target.setStaticSlotTag(slot, tag);
        } else {
            target.setObjectStatic(slot, this.getObjectStatic(slot));
            target.setLongStatic(slot, this.getLongStatic(slot));
        }
    }

    private void setStaticSlotTag(int slot, byte tag) {
        this.indexedTags[slot] = tag;
    }

    static {
        OBJECT_LOCATION = new Object();
        PRIMITIVE_LOCATION = new Object();
        EMPTY_OBJECT_ARRAY = new Object[0];
        EMPTY_LONG_ARRAY = new long[0];
        EMPTY_BYTE_ARRAY = new byte[0];
        UNSAFE = FrameWithoutBoxing.initUnsafe();
        assert (0 == FrameSlotKind.Object.tag);
        assert (7 == FrameSlotKind.Illegal.tag);
        assert (1 == FrameSlotKind.Long.tag);
        assert (2 == FrameSlotKind.Int.tag);
        assert (3 == FrameSlotKind.Double.tag);
        assert (4 == FrameSlotKind.Float.tag);
        assert (5 == FrameSlotKind.Boolean.tag);
        assert (6 == FrameSlotKind.Byte.tag);
        assert (8 == FrameSlotKind.Static.tag);
        boolean enabled = false;
        if (!$assertionsDisabled) {
            enabled = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        ASSERTIONS_ENABLED = enabled;
    }
}

