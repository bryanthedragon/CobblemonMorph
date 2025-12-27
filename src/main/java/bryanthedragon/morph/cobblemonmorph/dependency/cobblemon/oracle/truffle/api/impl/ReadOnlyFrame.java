package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotTypeException;
import com.oracle.truffle.api.frame.MaterializedFrame;

class ReadOnlyFrame implements Frame {
   private final Frame delegate;

   ReadOnlyFrame(Frame delegate) {
      this.delegate = delegate;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public FrameDescriptor getFrameDescriptor() {
      return this.delegate.getFrameDescriptor();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object[] getArguments() {
      return (Object[])this.delegate.getArguments().clone();
   }

   private static AssertionError newReadonlyAssertionError() {
      return new AssertionError("Unexpected write access.");
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public MaterializedFrame materialize() {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getObject(int slot) throws FrameSlotTypeException {
      return this.delegate.getObject(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setObject(int slot, Object value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public byte getByte(int slot) throws FrameSlotTypeException {
      return this.delegate.getByte(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setByte(int slot, byte value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean getBoolean(int slot) throws FrameSlotTypeException {
      return this.delegate.getBoolean(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setBoolean(int slot, boolean value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int getInt(int slot) throws FrameSlotTypeException {
      return this.delegate.getInt(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setInt(int slot, int value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public long getLong(int slot) throws FrameSlotTypeException {
      return this.delegate.getLong(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setLong(int slot, long value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public float getFloat(int slot) throws FrameSlotTypeException {
      return this.delegate.getFloat(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setFloat(int slot, float value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public double getDouble(int slot) throws FrameSlotTypeException {
      return this.delegate.getDouble(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setDouble(int slot, double value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getValue(int slot) {
      return this.delegate.getValue(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void copy(int srcSlot, int destSlot) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public byte getTag(int slot) {
      return this.delegate.getTag(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isObject(int slot) {
      return this.delegate.isObject(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isByte(int slot) {
      return this.delegate.isByte(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isBoolean(int slot) {
      return this.delegate.isBoolean(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isInt(int slot) {
      return this.delegate.isInt(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isLong(int slot) {
      return this.delegate.isLong(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isFloat(int slot) {
      return this.delegate.isFloat(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isDouble(int slot) {
      return this.delegate.isDouble(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean isStatic(int slot) {
      return this.delegate.isStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void clear(int slot) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getAuxiliarySlot(int slot) {
      return this.delegate.getAuxiliarySlot(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setAuxiliarySlot(int slot, Object value) {
      throw newReadonlyAssertionError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getObjectStatic(int slot) {
      return this.delegate.getObjectStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setObjectStatic(int slot, Object value) {
      this.delegate.setObjectStatic(slot, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public byte getByteStatic(int slot) {
      return this.delegate.getByteStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setByteStatic(int slot, byte value) {
      this.delegate.setByteStatic(slot, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean getBooleanStatic(int slot) {
      return this.delegate.getBooleanStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setBooleanStatic(int slot, boolean value) {
      this.delegate.setBooleanStatic(slot, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public int getIntStatic(int slot) {
      return this.delegate.getIntStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setIntStatic(int slot, int value) {
      this.delegate.setIntStatic(slot, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public long getLongStatic(int slot) {
      return this.delegate.getLongStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setLongStatic(int slot, long value) {
      this.delegate.setLongStatic(slot, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public float getFloatStatic(int slot) {
      return this.delegate.getFloatStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setFloatStatic(int slot, float value) {
      this.delegate.setFloatStatic(slot, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public double getDoubleStatic(int slot) {
      return this.delegate.getDoubleStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void setDoubleStatic(int slot, double value) {
      this.delegate.setDoubleStatic(slot, value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void copyPrimitiveStatic(int srcSlot, int destSlot) {
      this.delegate.copyPrimitiveStatic(srcSlot, destSlot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void copyObjectStatic(int srcSlot, int destSlot) {
      this.delegate.copyObjectStatic(srcSlot, destSlot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void copyStatic(int srcSlot, int destSlot) {
      this.delegate.copyStatic(srcSlot, destSlot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void swapPrimitiveStatic(int first, int second) {
      this.delegate.swapPrimitiveStatic(first, second);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void swapObjectStatic(int first, int second) {
      this.delegate.swapObjectStatic(first, second);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void swapStatic(int first, int second) {
      this.delegate.swapStatic(first, second);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void clearPrimitiveStatic(int slot) {
      this.delegate.clearPrimitiveStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void clearObjectStatic(int slot) {
      this.delegate.clearObjectStatic(slot);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void clearStatic(int slot) {
      this.delegate.clearStatic(slot);
   }
}
