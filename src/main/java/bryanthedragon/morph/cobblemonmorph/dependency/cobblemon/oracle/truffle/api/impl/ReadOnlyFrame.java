
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;

class ReadOnlyFrame
implements Frame {
    private final Frame delegate;

    ReadOnlyFrame(Frame delegate) {
        this.delegate = delegate;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public FrameDescriptor getFrameDescriptor() {
        return this.delegate.getFrameDescriptor();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object[] getArguments() {
        return (Object[])this.delegate.getArguments().clone();
    }

    private static AssertionError newReadonlyAssertionError() {
        return new AssertionError((Object)"Unexpected write access.");
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public MaterializedFrame materialize() {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getObject(int slot) throws FrameSlotTypeException {
        return this.delegate.getObject(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setObject(int slot, Object value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public byte getByte(int slot) throws FrameSlotTypeException {
        return this.delegate.getByte(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setByte(int slot, byte value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean getBoolean(int slot) throws FrameSlotTypeException {
        return this.delegate.getBoolean(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setBoolean(int slot, boolean value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int getInt(int slot) throws FrameSlotTypeException {
        return this.delegate.getInt(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setInt(int slot, int value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public long getLong(int slot) throws FrameSlotTypeException {
        return this.delegate.getLong(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setLong(int slot, long value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public float getFloat(int slot) throws FrameSlotTypeException {
        return this.delegate.getFloat(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setFloat(int slot, float value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public double getDouble(int slot) throws FrameSlotTypeException {
        return this.delegate.getDouble(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setDouble(int slot, double value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getValue(int slot) {
        return this.delegate.getValue(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void copy(int srcSlot, int destSlot) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public byte getTag(int slot) {
        return this.delegate.getTag(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isObject(int slot) {
        return this.delegate.isObject(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isByte(int slot) {
        return this.delegate.isByte(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isBoolean(int slot) {
        return this.delegate.isBoolean(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isInt(int slot) {
        return this.delegate.isInt(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isLong(int slot) {
        return this.delegate.isLong(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isFloat(int slot) {
        return this.delegate.isFloat(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isDouble(int slot) {
        return this.delegate.isDouble(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean isStatic(int slot) {
        return this.delegate.isStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void clear(int slot) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getAuxiliarySlot(int slot) {
        return this.delegate.getAuxiliarySlot(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setAuxiliarySlot(int slot, Object value2) {
        throw ReadOnlyFrame.newReadonlyAssertionError();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getObjectStatic(int slot) {
        return this.delegate.getObjectStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setObjectStatic(int slot, Object value2) {
        this.delegate.setObjectStatic(slot, value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public byte getByteStatic(int slot) {
        return this.delegate.getByteStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setByteStatic(int slot, byte value2) {
        this.delegate.setByteStatic(slot, value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean getBooleanStatic(int slot) {
        return this.delegate.getBooleanStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setBooleanStatic(int slot, boolean value2) {
        this.delegate.setBooleanStatic(slot, value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int getIntStatic(int slot) {
        return this.delegate.getIntStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setIntStatic(int slot, int value2) {
        this.delegate.setIntStatic(slot, value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public long getLongStatic(int slot) {
        return this.delegate.getLongStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setLongStatic(int slot, long value2) {
        this.delegate.setLongStatic(slot, value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public float getFloatStatic(int slot) {
        return this.delegate.getFloatStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setFloatStatic(int slot, float value2) {
        this.delegate.setFloatStatic(slot, value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public double getDoubleStatic(int slot) {
        return this.delegate.getDoubleStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void setDoubleStatic(int slot, double value2) {
        this.delegate.setDoubleStatic(slot, value2);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void copyPrimitiveStatic(int srcSlot, int destSlot) {
        this.delegate.copyPrimitiveStatic(srcSlot, destSlot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void copyObjectStatic(int srcSlot, int destSlot) {
        this.delegate.copyObjectStatic(srcSlot, destSlot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void copyStatic(int srcSlot, int destSlot) {
        this.delegate.copyStatic(srcSlot, destSlot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void swapPrimitiveStatic(int first, int second) {
        this.delegate.swapPrimitiveStatic(first, second);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void swapObjectStatic(int first, int second) {
        this.delegate.swapObjectStatic(first, second);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void swapStatic(int first, int second) {
        this.delegate.swapStatic(first, second);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void clearPrimitiveStatic(int slot) {
        this.delegate.clearPrimitiveStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void clearObjectStatic(int slot) {
        this.delegate.clearObjectStatic(slot);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void clearStatic(int slot) {
        this.delegate.clearStatic(slot);
    }
}

