package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.CompilerDirectives;

public interface Frame {
   FrameDescriptor getFrameDescriptor();

   Object[] getArguments();

   MaterializedFrame materialize();

   default Object getObject(int slot) throws FrameSlotTypeException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setObject(int slot, Object value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default byte getByte(int slot) throws FrameSlotTypeException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setByte(int slot, byte value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean getBoolean(int slot) throws FrameSlotTypeException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setBoolean(int slot, boolean value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default int getInt(int slot) throws FrameSlotTypeException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setInt(int slot, int value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default long getLong(int slot) throws FrameSlotTypeException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setLong(int slot, long value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default float getFloat(int slot) throws FrameSlotTypeException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setFloat(int slot, float value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default double getDouble(int slot) throws FrameSlotTypeException {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setDouble(int slot, double value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default Object getValue(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void copy(int srcSlot, int destSlot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void swap(int first, int second) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default byte getTag(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isObject(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isByte(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isBoolean(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isInt(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isLong(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isFloat(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isDouble(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean isStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void clear(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default Object getAuxiliarySlot(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setAuxiliarySlot(int slot, Object value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default Object getObjectStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setObjectStatic(int slot, Object value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default byte getByteStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setByteStatic(int slot, byte value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default boolean getBooleanStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setBooleanStatic(int slot, boolean value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default int getIntStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setIntStatic(int slot, int value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default long getLongStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setLongStatic(int slot, long value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default float getFloatStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setFloatStatic(int slot, float value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default double getDoubleStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void setDoubleStatic(int slot, double value) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void copyPrimitiveStatic(int srcSlot, int destSlot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void copyObjectStatic(int srcSlot, int destSlot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void copyStatic(int srcSlot, int destSlot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void swapPrimitiveStatic(int first, int second) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void swapObjectStatic(int first, int second) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void swapStatic(int first, int second) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void clearPrimitiveStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void clearObjectStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }

   default void clearStatic(int slot) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      throw new UnsupportedOperationException();
   }
}
