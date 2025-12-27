package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class AbstractWritableArray extends DynamicArray {
   protected static final AbstractWritableArray.SetSupportedProfileAccess SET_SUPPORTED_PROFILE_ACCESS = new AbstractWritableArray.SetSupportedProfileAccess() {};

   protected static final void setArrayProperties(JSDynamicObject object, Object array, long length, int usedLength, long indexOffset, int arrayOffset) {
      JSAbstractArray.arraySetArray(object, array);
      setArrayProperties(object, length, usedLength, indexOffset, arrayOffset);
   }

   protected static final void setArrayProperties(JSDynamicObject object, long length, int usedLength, long indexOffset, int arrayOffset) {
      JSAbstractArray.arraySetLength(object, length);
      JSAbstractArray.arraySetUsedLength(object, usedLength);
      JSAbstractArray.arraySetIndexOffset(object, indexOffset);
      JSAbstractArray.arraySetArrayOffset(object, arrayOffset);
   }

   protected AbstractWritableArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   abstract AbstractWritableArray sameTypeHolesArray(
      JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount
   );

   abstract void fillWithHoles(Object array, int fromIndex, int toIndex);

   @Override
   public final boolean isInBoundsFast(JSDynamicObject object, long index) {
      return this.firstElementIndex(object) <= index && index <= this.lastElementIndex(object);
   }

   protected abstract int prepareInBoundsFast(JSDynamicObject object, long index);

   public final boolean isInBounds(JSDynamicObject object, int index) {
      return this.isSupported(object, index) && this.rangeCheck(object, index);
   }

   protected abstract int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile);

   protected static void prepareInBoundsZeroBased(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      long length = JSAbstractArray.arrayGetLength(object);
      if (SET_SUPPORTED_PROFILE_ACCESS.inBoundsZeroBasedSetLength(profile, index >= length)) {
         JSAbstractArray.arraySetLength(object, length + 1L);
      }

      int usedLength = getUsedLength(object);
      if (SET_SUPPORTED_PROFILE_ACCESS.inBoundsZeroBasedSetUsedLength(profile, index >= usedLength)) {
         JSAbstractArray.arraySetUsedLength(object, usedLength + 1);
      }
   }

   Object getArrayObject(JSDynamicObject object) {
      return JSAbstractArray.arrayGetArray(object);
   }

   abstract int getArrayLength(Object array);

   protected static int getUsedLength(JSDynamicObject object) {
      return JSAbstractArray.arrayGetUsedLength(object);
   }

   protected final int prepareInBoundsContiguous(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      int internalIndex = this.ensureCapacityContiguous(object, this.prepareInBoundsFast(object, index), profile);
      this.updateContiguousState(object, internalIndex, profile);
      return internalIndex;
   }

   protected final int prepareInBoundsHoles(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      int internalIndex = this.prepareInBoundsFast(object, index);
      this.fillHoles(object, internalIndex, this.updateHolesState(object, internalIndex, profile), profile);
      return internalIndex;
   }

   private boolean rangeCheck(JSDynamicObject object, int index) {
      int internalIndex = this.prepareInBoundsFast(object, index);
      return internalIndex >= 0 && internalIndex < this.getArrayCapacity(object);
   }

   public boolean containsHoles(JSDynamicObject object, long index) {
      return false;
   }

   public abstract boolean isSupported(JSDynamicObject object, long index);

   public static boolean isSupportedZeroBased(JSDynamicObject object, int index) {
      return index >= 0 && index <= getUsedLength(object);
   }

   public final boolean isSupportedContiguous(JSDynamicObject object, long index) {
      return index >= this.firstElementIndex(object) - 1L && index <= this.lastElementIndex(object) + 1L && index < 2147483647L;
   }

   public final boolean isSupportedHoles(JSDynamicObject object, long index) {
      return index >= this.firstElementIndex(object) - 5000L && index <= this.lastElementIndex(object) + 5000L && index < 2147483647L;
   }

   protected abstract int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile);

   protected final void prepareSupportedZeroBased(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      this.ensureCapacity(object, index, 0L, profile);
      prepareInBoundsZeroBased(object, index, profile);
   }

   protected final int prepareSupportedContiguous(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      int internalIndex = this.ensureCapacityContiguous(object, this.prepareInBoundsFast(object, index), profile);
      this.updateContiguousState(object, internalIndex, profile);
      return internalIndex;
   }

   protected final int prepareSupportedHoles(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
      int internalIndex = this.prepareInBoundsFast(object, index);
      internalIndex = this.ensureCapacityContiguous(object, internalIndex, profile);
      this.fillHoles(object, internalIndex, this.updateHolesState(object, internalIndex, profile), profile);
      return internalIndex;
   }

   protected void incrementHolesCount(JSDynamicObject object, int offset) {
      throw Errors.shouldNotReachHere();
   }

   protected abstract void setHoleValue(JSDynamicObject object, int index);

   protected abstract int getArrayCapacity(JSDynamicObject object);

   protected int getArrayOffset(JSDynamicObject object) {
      return 0;
   }

   protected void setArrayOffset(JSDynamicObject object, int value) {
      throw Errors.shouldNotReachHere();
   }

   protected long getIndexOffset(JSDynamicObject object) {
      throw Errors.shouldNotReachHere();
   }

   protected void setIndexOffset(JSDynamicObject object, long value) {
      throw Errors.shouldNotReachHere();
   }

   private int ensureCapacity(JSDynamicObject object, int internalIndex, long indexOffset, ScriptArray.ProfileHolder profile) {
      assert -indexOffset <= (long)internalIndex;

      int capacity = this.getArrayCapacity(object);
      if (SET_SUPPORTED_PROFILE_ACCESS.ensureCapacityGrow(profile, internalIndex >= 0 && internalIndex < capacity)) {
         return 0;
      } else {
         long minCapacity;
         if (SET_SUPPORTED_PROFILE_ACCESS.ensureCapacityGrowLeft(profile, internalIndex < 0)) {
            minCapacity = (long)(-internalIndex) + capacity;
         } else {
            minCapacity = internalIndex + 1L;
         }

         long newCapacity = minCapacity << 1;
         if (newCapacity > 2147483639L) {
            if (2147483639L < minCapacity) {
               CompilerDirectives.transferToInterpreter();
               throw new OutOfMemoryError();
            }

            newCapacity = 2147483639L;
         }

         int offset = 0;
         if (internalIndex < 0) {
            offset = (int)newCapacity - capacity;
            if (indexOffset < offset) {
               offset = (int)indexOffset;
            }
         }

         this.resizeArray(object, (int)newCapacity, capacity, offset);
         return offset;
      }
   }

   private int ensureCapacityContiguous(JSDynamicObject object, int internalIndex, ScriptArray.ProfileHolder profile) {
      int offset = this.ensureCapacity(object, internalIndex, this.getIndexOffset(object), profile);
      if (offset != 0) {
         this.setIndexOffset(object, this.getIndexOffset(object) - offset);
         this.setArrayOffset(object, this.getArrayOffset(object) + offset);
      }

      return internalIndex + offset;
   }

   private void updateContiguousState(JSDynamicObject object, int internalIndex, ScriptArray.ProfileHolder profile) {
      int offset = this.getArrayOffset(object);
      int used = getUsedLength(object);
      if (SET_SUPPORTED_PROFILE_ACCESS.updateStatePrepend(profile, internalIndex < offset)) {
         JSAbstractArray.arraySetUsedLength(object, used + 1);
         this.setArrayOffset(object, offset - 1);
      } else if (SET_SUPPORTED_PROFILE_ACCESS.updateStateAppend(profile, internalIndex >= offset + used)) {
         JSAbstractArray.arraySetUsedLength(object, used + 1);
         long length = JSAbstractArray.arrayGetLength(object);
         long calcLength = this.getIndexOffset(object) + offset + used + 1L;
         if (SET_SUPPORTED_PROFILE_ACCESS.updateStateSetLength(profile, calcLength > length)) {
            JSAbstractArray.arraySetLength(object, calcLength);
         }
      }
   }

   private int updateHolesState(JSDynamicObject object, int internalIndex, ScriptArray.ProfileHolder profile) {
      int offset = this.getArrayOffset(object);
      int used = getUsedLength(object);
      int size;
      if (SET_SUPPORTED_PROFILE_ACCESS.updateStatePrepend(profile, internalIndex < offset)) {
         size = -(offset - internalIndex);
      } else {
         if (!SET_SUPPORTED_PROFILE_ACCESS.updateStateAppend(profile, internalIndex >= offset + used)) {
            if (SET_SUPPORTED_PROFILE_ACCESS.updateHolesStateIsHole(profile, this.isHolePrepared(object, internalIndex))) {
               this.incrementHolesCount(object, -1);
            }

            return 0;
         }

         if (used == 0) {
            offset = internalIndex;
         }

         size = internalIndex - (offset + used) + 1;
      }

      if (size < 0) {
         used -= size;
         offset += size;
      } else {
         used += size;
         long length = JSAbstractArray.arrayGetLength(object);
         long calcLength = this.getIndexOffset(object) + offset + used;
         if (SET_SUPPORTED_PROFILE_ACCESS.updateStateSetLength(profile, calcLength > length)) {
            JSAbstractArray.arraySetLength(object, calcLength);
         }
      }

      JSAbstractArray.arraySetUsedLength(object, used);
      this.setArrayOffset(object, offset);
      return size;
   }

   protected void fillHoles(JSDynamicObject object, int internalIndex, int grown, ScriptArray.ProfileHolder profile) {
      int start;
      int end;
      if (SET_SUPPORTED_PROFILE_ACCESS.fillHolesRight(profile, grown > 1)) {
         start = internalIndex - grown + 1;
         end = internalIndex;
      } else {
         if (!SET_SUPPORTED_PROFILE_ACCESS.fillHolesLeft(profile, grown < -1)) {
            return;
         }

         start = internalIndex + 1;
         end = internalIndex - grown;
      }

      this.incrementHolesCount(object, end - start);

      for (int i = start; i < end; i++) {
         this.setHoleValue(object, i);
      }
   }

   public abstract AbstractWritableArray toDouble(JSDynamicObject object, long index, double value);

   public abstract AbstractWritableArray toObject(JSDynamicObject object, long index, Object value);

   public AbstractWritableArray toContiguous(JSDynamicObject object, long index, Object value) {
      return this;
   }

   public abstract AbstractWritableArray toHoles(JSDynamicObject object, long index, Object value);

   public AbstractWritableArray toNonHoles(JSDynamicObject object, long index, Object value) {
      assert !this.isHolesType();

      return this;
   }

   public final SparseArray toSparse(JSDynamicObject object, long index, Object value) {
      SparseArray newArray = SparseArray.makeSparseArray(object, this);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   protected abstract void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset);

   public final boolean isSparse(JSDynamicObject object, long index) {
      return !this.isSupportedHoles(object, index);
   }

   @Override
   public boolean hasElement(JSDynamicObject object, long index) {
      return this.isInBoundsFast(object, index);
   }

   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      long firstI = this.firstElementIndex(object);
      if (index < firstI) {
         return firstI;
      } else {
         long lastI = this.lastElementIndex(object);
         return index + 1L > lastI ? JSRuntime.MAX_SAFE_INTEGER_LONG : index + 1L;
      }
   }

   protected abstract boolean isHolePrepared(JSDynamicObject object, int index);

   protected final long nextElementIndexHoles(JSDynamicObject object, long index0) {
      long index = index0;
      long firstIdx = this.firstElementIndex(object);
      if (index0 < firstIdx) {
         return firstIdx;
      } else {
         long lastI = this.lastElementIndex(object);

         while (++index <= lastI) {
            if (!this.isHolePrepared(object, this.prepareInBoundsFast(object, index))) {
               return index;
            }
         }

         return JSRuntime.MAX_SAFE_INTEGER_LONG;
      }
   }

   protected final long nextElementIndexZeroBased(JSDynamicObject object, long index) {
      assert index >= -1L;

      long lastI = this.lastElementIndex(object);
      return index + 1L > lastI ? JSRuntime.MAX_SAFE_INTEGER_LONG : index + 1L;
   }

   @Override
   public long previousElementIndex(JSDynamicObject object, long index) {
      long lastIdx = this.lastElementIndex(object);
      if (index > lastIdx) {
         return lastIdx;
      } else {
         return index - 1L < this.firstElementIndex(object) ? -1L : index - 1L;
      }
   }

   protected final long previousElementIndexHoles(JSDynamicObject object, long index0) {
      long index = index0;
      long lastIdx = this.lastElementIndex(object);
      if (index0 > lastIdx) {
         return lastIdx;
      } else {
         long firstIdx = this.firstElementIndex(object);

         do {
            index--;
         } while (index >= firstIdx && this.isHolePrepared(object, this.prepareInBoundsFast(object, index)));

         return index < firstIdx ? -1L : index;
      }
   }

   @Override
   public final long length(JSDynamicObject object) {
      return JSAbstractArray.arrayGetLength(object);
   }

   @Override
   public final int lengthInt(JSDynamicObject object) {
      return (int)this.length(object);
   }

   @Override
   public final ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      if (SET_LENGTH_PROFILE.lengthZero(profile, length == 0L)) {
         JSAbstractArray.arraySetLength(object, length);
         return ConstantEmptyArray.createConstantEmptyArray();
      } else {
         if (SET_LENGTH_PROFILE.lengthLess(profile, length < this.length(object))) {
            this.setLengthLess(object, length, profile);
         } else {
            JSAbstractArray.arraySetLength(object, length);
         }

         return this;
      }
   }

   protected abstract void setLengthLess(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile);

   protected void setLengthLessZeroBased(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      long oldLength = JSAbstractArray.arrayGetLength(object);
      JSAbstractArray.arraySetLength(object, length);
      if (SET_LENGTH_PROFILE.zeroBasedSetUsedLength(profile, getUsedLength(object) > length)) {
         JSAbstractArray.arraySetUsedLength(object, (int)length);
      }

      if (SET_LENGTH_PROFILE.zeroBasedClearUnusedArea(profile, length < oldLength)) {
         this.clearUnusedArea(object, (int)length, (int)oldLength, 0, profile);
      }
   }

   protected final void setLengthLessContiguous(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      long indexOffset = this.getIndexOffset(object);
      int arrayOffset = this.getArrayOffset(object);
      JSAbstractArray.arraySetLength(object, length);
      if (SET_LENGTH_PROFILE.contiguousZeroUsed(profile, length <= indexOffset)) {
         JSAbstractArray.arraySetUsedLength(object, 0);
         this.setIndexOffset(object, length - 1L);
         this.setArrayOffset(object, 0);
         long arrayCapacity = this.getArrayCapacity(object);
         this.clearUnusedArea(object, 0, (int)arrayCapacity, 0, profile);
      } else {
         int oldUsed = getUsedLength(object);
         int newUsed = Math.min(oldUsed, (int)(length - indexOffset - arrayOffset));
         int newUsedLength = (int)(this.previousElementIndex(object, indexOffset + arrayOffset + newUsed) + 1L - arrayOffset - indexOffset);
         if (SET_LENGTH_PROFILE.contiguousNegativeUsed(profile, newUsedLength < 0)) {
            newUsedLength = 0;
            this.setArrayOffset(object, 0);
            this.setIndexOffset(object, 0L);
         }

         JSAbstractArray.arraySetUsedLength(object, newUsedLength);
         if (SET_LENGTH_PROFILE.contiguousShrinkUsed(profile, newUsedLength < oldUsed)) {
            if (this.isHolesType()) {
               this.incrementHolesCount(object, -this.countHolesPrepared(object, arrayOffset + newUsedLength, arrayOffset + oldUsed));

               assert JSAbstractArray.arrayGetHoleCount(object) == this.countHoles(object);
            }

            this.clearUnusedArea(object, newUsedLength, oldUsed, arrayOffset, profile);
         }
      }
   }

   protected void clearUnusedArea(JSDynamicObject object, int startIdx, int endIdx, int arrayOffset, ScriptArray.ProfileHolder profile) {
      int arrayCapacity = this.getArrayCapacity(object);
      if (!SET_LENGTH_PROFILE.clearUnusedArea(profile, startIdx < -1 || startIdx + arrayOffset >= arrayCapacity)) {
         int start = startIdx + arrayOffset;
         int end = Math.min(endIdx + arrayOffset, arrayCapacity - 1);

         for (int i = start; i <= end; i++) {
            this.setHoleValue(object, i);
         }
      }
   }

   @Override
   public final Object getElement(JSDynamicObject object, long index) {
      return this.isInBoundsFast(object, index) ? this.getInBoundsFast(object, (int)index) : Undefined.instance;
   }

   @Override
   public final Object getElementInBounds(JSDynamicObject object, long index) {
      assert this.isInBoundsFast(object, index);

      return this.getInBoundsFast(object, (int)index);
   }

   public abstract Object getInBoundsFast(JSDynamicObject object, int index);

   public int getInBoundsFastInt(JSDynamicObject object, int index) throws UnexpectedResultException {
      Object value = this.getInBoundsFast(object, index);
      if (value instanceof Integer) {
         return (Integer)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   public double getInBoundsFastDouble(JSDynamicObject object, int index) throws UnexpectedResultException {
      Object value = this.getInBoundsFast(object, index);
      if (value instanceof Double) {
         return (Double)value;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(value);
      }
   }

   protected final ScriptArray deleteElementHoles(JSDynamicObject object, long index) {
      if (this.isInBoundsFast(object, index)) {
         int preparedindex = this.prepareInBoundsFast(object, (int)index);
         if (!this.isHolePrepared(object, preparedindex)) {
            int arrayOffset = this.getArrayOffset(object);
            if (arrayOffset == preparedindex) {
               long nextNonHoles = this.nextElementIndexHoles(object, index);
               if (nextNonHoles == JSRuntime.MAX_SAFE_INTEGER_LONG) {
                  this.setArrayOffset(object, 0);
                  JSAbstractArray.arraySetUsedLength(object, 0);
                  JSAbstractArray.arraySetHoleCount(object, 0);
               } else {
                  int preparedNextNonHoles = this.prepareInBoundsFast(object, (int)nextNonHoles);
                  int delta = preparedNextNonHoles - preparedindex;
                  this.setArrayOffset(object, preparedindex + delta);
                  JSAbstractArray.arraySetUsedLength(object, JSAbstractArray.arrayGetUsedLength(object) - delta);
                  this.incrementHolesCount(object, -this.countHolesPrepared(object, preparedindex, preparedNextNonHoles));
               }

               this.setHoleValue(object, preparedindex);
            } else if (arrayOffset + JSAbstractArray.arrayGetUsedLength(object) == preparedindex) {
               long previousNonHoles = this.previousElementIndexHoles(object, index);

               assert previousNonHoles >= 0L;

               int preparedPreviousNonHoles = this.prepareInBoundsFast(object, (int)previousNonHoles);
               JSAbstractArray.arraySetUsedLength(object, JSAbstractArray.arrayGetUsedLength(object) - preparedindex + preparedPreviousNonHoles);
               this.incrementHolesCount(object, -this.countHolesPrepared(object, preparedPreviousNonHoles, preparedindex));
               this.setHoleValue(object, preparedindex);
            } else {
               this.incrementHolesCount(object, 1);
               this.setHoleValue(object, preparedindex);
            }
         }
      }

      assert JSAbstractArray.arrayGetHoleCount(object) == this.countHoles(object);

      return this;
   }

   @CompilerDirectives.TruffleBoundary
   protected final void traceWriteValue(String access, int index, Object value) {
      traceWrite(this.getClass().getSimpleName() + "." + access, index, value);
   }

   public ScriptArray toNonContiguous(JSDynamicObject object, int index, Object value, ScriptArray.ProfileHolder profile) {
      return this;
   }

   protected abstract AbstractWritableArray withIntegrityLevel(int newIntegrityLevel);

   public abstract Object allocateArray(int length);

   ScriptArray addRangeImplContiguous(JSDynamicObject object, long offset, int size) {
      long indexOffset = this.getIndexOffset(object);
      int arrayOffset = this.getArrayOffset(object);
      if (offset <= indexOffset + arrayOffset) {
         this.setIndexOffset(object, indexOffset + size);
         return this;
      } else {
         Object array = this.getArrayObject(object);
         int usedLength = getUsedLength(object);
         int arrayLength = this.getArrayLength(array);
         if (arrayOffset + usedLength + size < arrayLength) {
            int lastIndex = arrayOffset + usedLength;
            int effectiveOffset = (int)(offset - indexOffset);
            int copySize = lastIndex - effectiveOffset;
            if (copySize > 0) {
               System.arraycopy(array, effectiveOffset, array, effectiveOffset + size, copySize);
            }

            JSAbstractArray.arraySetUsedLength(object, usedLength + size);
            return this;
         } else {
            return this.addRangeGrow(
               object, array, arrayLength, usedLength, this.lengthInt(object), (int)(offset - indexOffset), size, arrayOffset, indexOffset
            );
         }
      }
   }

   private ScriptArray addRangeGrow(
      JSDynamicObject object, Object array, int arrayLength, int usedLength, int length, int offset, int size, int arrayOffset, long indexOffset
   ) {
      Object newArray = this.allocateArray(nextPower(arrayLength + size));
      if (offset - arrayOffset > arrayLength) {
         System.arraycopy(array, arrayOffset, newArray, arrayOffset, arrayLength);
         this.fillWithHoles(newArray, usedLength, usedLength + size);
         return this.ensureHolesArray(
            object, length + size, newArray, indexOffset, arrayOffset, usedLength + size, JSAbstractArray.arrayGetHoleCount(object) + size
         );
      } else {
         System.arraycopy(array, arrayOffset, newArray, arrayOffset, offset - arrayOffset);
         int toCopy = arrayOffset + usedLength - offset;
         System.arraycopy(array, offset, newArray, offset + size, toCopy);
         JSAbstractArray.arraySetLength(object, length + size);
         JSAbstractArray.arraySetArray(object, newArray);
         JSAbstractArray.arraySetUsedLength(object, usedLength + size);
         return this;
      }
   }

   private ScriptArray ensureHolesArray(JSDynamicObject object, int length, Object newArray, long indexOffset, int arrayOffset, int usedLength, int holesCount) {
      AbstractWritableArray newArrayObject = this.sameTypeHolesArray(object, length, newArray, indexOffset, arrayOffset, usedLength, holesCount);
      if (newArrayObject != this && JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArrayObject, 0L, null);
      }

      return newArrayObject;
   }

   ScriptArray addRangeImplZeroBased(JSDynamicObject object, long offset, int size) {
      int iOffset = (int)offset;
      Object array = this.getArrayObject(object);
      int arrayLength = this.getArrayLength(array);
      int length = this.lengthInt(object);
      int usedLength = getUsedLength(object);
      if (usedLength < offset) {
         JSAbstractArray.arraySetLength(object, length + size);
         return this;
      } else if (size + usedLength <= arrayLength) {
         int toCopy = usedLength - iOffset;
         System.arraycopy(array, iOffset, array, iOffset + size, toCopy);
         JSAbstractArray.arraySetUsedLength(object, usedLength + size);
         return this;
      } else {
         return this.addRangeGrow(object, array, arrayLength, usedLength, arrayLength, iOffset, size, 0, 0L);
      }
   }

   protected final ScriptArray removeRangeContiguous(JSDynamicObject object, long start, long end) {
      assert start >= 0L && start <= end;

      int usedLength = getUsedLength(object);
      long indexOffset = this.getIndexOffset(object);
      int arrayOffset = this.getArrayOffset(object);
      int startIntl = (int)(start - indexOffset);
      int endIntl = (int)(end - indexOffset);
      int usedStartIntl = Math.max(arrayOffset, startIntl);
      int usedEndIntl = Math.min(arrayOffset + usedLength, endIntl);
      int usedDelta = usedEndIntl - usedStartIntl;
      int newUsedLength = usedLength - usedDelta;
      if (usedDelta > 0) {
         JSAbstractArray.arraySetUsedLength(object, newUsedLength);
         if (newUsedLength == 0) {
            this.setArrayOffset(object, 0);
            this.setIndexOffset(object, 0L);

            assert usedStartIntl == arrayOffset;

            assert usedEndIntl == arrayOffset + usedLength;

            this.fillWithHoles(this.getArrayObject(object), usedStartIntl, usedEndIntl);
            return this;
         }
      }

      int arrayOffsetNew = arrayOffset;
      if (startIntl < 0) {
         int indexOffsetDelta = endIntl - startIntl;
         long indexOffsetNew = Math.max(0L, indexOffset - indexOffsetDelta);
         if (endIntl > 0) {
            int length = usedLength + arrayOffset - endIntl;
            if (length > 0) {
               this.moveRangePrepared(object, endIntl, 0, length);
            }

            indexOffsetNew = start;
         }

         this.setIndexOffset(object, indexOffsetNew);
      } else {
         if (startIntl < arrayOffset) {
            arrayOffsetNew = Math.max(startIntl, arrayOffset - (endIntl - startIntl));
            this.setArrayOffset(object, arrayOffsetNew);
         }

         int length = usedLength + arrayOffset - endIntl;
         if (length > 0) {
            this.moveRangePrepared(object, endIntl, startIntl, length);
         }
      }

      if (usedDelta > 0) {
         this.fillWithHoles(this.getArrayObject(object), arrayOffsetNew + newUsedLength, arrayOffset + usedLength);
      }

      return this;
   }

   protected final ScriptArray removeRangeHoles(JSDynamicObject object, long start, long end) {
      assert this.isHolesType();

      assert start >= 0L && start <= end;

      int usedLength = getUsedLength(object);
      long indexOffset = this.getIndexOffset(object);
      int arrayOffset = this.getArrayOffset(object);
      int startIntl = (int)(start - indexOffset);
      int endIntl = (int)(end - indexOffset);
      if (endIntl > 0) {
         int actualStartIntl = Math.max(arrayOffset, startIntl);
         int actualEndIntl = Math.min(arrayOffset + usedLength, endIntl);

         for (int i = actualStartIntl; i < actualEndIntl; i++) {
            if (this.isHolePrepared(object, i)) {
               this.incrementHolesCount(object, -1);
            }
         }
      }

      this.removeRangeContiguous(object, start, end);
      return this;
   }

   protected final int countHoles(JSDynamicObject object) {
      assert this.isHolesType();

      int arrayOffset = this.getArrayOffset(object);
      return this.countHolesPrepared(object, arrayOffset, arrayOffset + getUsedLength(object));
   }

   private int countHolesPrepared(JSDynamicObject object, int start, int end) {
      assert this.isHolesType();

      int holeCount = 0;

      for (int index = start; index < end; index++) {
         if (this.isHolePrepared(object, index)) {
            holeCount++;
         }
      }

      return holeCount;
   }

   protected abstract void moveRangePrepared(JSDynamicObject object, int src, int dst, int len);

   @Override
   public ScriptArray shiftRangeImpl(JSDynamicObject object, long from) {
      if (!this.isHolesType()) {
         long indexOffset = this.getIndexOffset(object);
         int arrayOffset = this.getArrayOffset(object);
         long first = indexOffset + arrayOffset;
         if (first >= from) {
            this.setIndexOffset(object, indexOffset - from);
            return this;
         }

         long internalArrayShift = from - first;
         int usedLength = getUsedLength(object);
         if (internalArrayShift < usedLength) {
            long newLength = this.length(object) - from;
            int newUsedLength = (int)(usedLength - internalArrayShift);
            long newIndexOffset = indexOffset - from;
            int newArrayOffset = (int)(arrayOffset + internalArrayShift);
            setArrayProperties(object, newLength, newUsedLength, newIndexOffset, newArrayOffset);
            return this;
         }
      }

      return this.removeRangeImpl(object, 0L, from);
   }

   public static ScriptArray.ProfileHolder createSetSupportedProfile() {
      return ScriptArray.ProfileHolder.create(10, AbstractWritableArray.SetSupportedProfileAccess.class);
   }

   protected interface SetSupportedProfileAccess extends ScriptArray.ProfileAccess {
      default boolean ensureCapacityGrow(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 0, condition);
      }

      default boolean ensureCapacityGrowLeft(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 1, condition);
      }

      default boolean inBoundsZeroBasedSetLength(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 2, condition);
      }

      default boolean inBoundsZeroBasedSetUsedLength(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 3, condition);
      }

      default boolean updateStatePrepend(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 4, condition);
      }

      default boolean updateStateAppend(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 5, condition);
      }

      default boolean updateStateSetLength(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 6, condition);
      }

      default boolean updateHolesStateIsHole(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 7, condition);
      }

      default boolean fillHolesLeft(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 8, condition);
      }

      default boolean fillHolesRight(ScriptArray.ProfileHolder profile, boolean condition) {
         return profile.profile(this, 9, condition);
      }
   }
}
