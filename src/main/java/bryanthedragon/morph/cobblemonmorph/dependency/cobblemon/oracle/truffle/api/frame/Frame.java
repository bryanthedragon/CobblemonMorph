
package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;

public interface Frame {
    public FrameDescriptor getFrameDescriptor();

    public Object[] getArguments();

    public MaterializedFrame materialize();

    default public Object getObject(int slot) throws FrameSlotTypeException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setObject(int slot, Object value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public byte getByte(int slot) throws FrameSlotTypeException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setByte(int slot, byte value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean getBoolean(int slot) throws FrameSlotTypeException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setBoolean(int slot, boolean value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public int getInt(int slot) throws FrameSlotTypeException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setInt(int slot, int value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public long getLong(int slot) throws FrameSlotTypeException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setLong(int slot, long value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public float getFloat(int slot) throws FrameSlotTypeException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setFloat(int slot, float value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public double getDouble(int slot) throws FrameSlotTypeException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setDouble(int slot, double value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public Object getValue(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void copy(int srcSlot, int destSlot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void swap(int first, int second) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public byte getTag(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isObject(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isByte(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isBoolean(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isInt(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isLong(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isFloat(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isDouble(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean isStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void clear(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public Object getAuxiliarySlot(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setAuxiliarySlot(int slot, Object value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public Object getObjectStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setObjectStatic(int slot, Object value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public byte getByteStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setByteStatic(int slot, byte value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public boolean getBooleanStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setBooleanStatic(int slot, boolean value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public int getIntStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setIntStatic(int slot, int value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public long getLongStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setLongStatic(int slot, long value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public float getFloatStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setFloatStatic(int slot, float value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public double getDoubleStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void setDoubleStatic(int slot, double value2) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void copyPrimitiveStatic(int srcSlot, int destSlot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void copyObjectStatic(int srcSlot, int destSlot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void copyStatic(int srcSlot, int destSlot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void swapPrimitiveStatic(int first, int second) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void swapObjectStatic(int first, int second) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void swapStatic(int first, int second) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void clearPrimitiveStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void clearObjectStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }

    default public void clearStatic(int slot) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException();
    }
}

