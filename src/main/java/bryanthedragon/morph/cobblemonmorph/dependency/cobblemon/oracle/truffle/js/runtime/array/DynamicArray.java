package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class DynamicArray extends ScriptArray {
   protected static final int INTEGRITY_LEVEL_NONE = 0;
   protected static final int INTEGRITY_LEVEL_NONE_LENGTH_READONLY = 1;
   protected static final int INTEGRITY_LEVEL_NOT_EXTENSIBLE = 2;
   protected static final int INTEGRITY_LEVEL_NOT_EXTENSIBLE_LENGTH_READONLY = 3;
   protected static final int INTEGRITY_LEVEL_SEALED = 4;
   protected static final int INTEGRITY_LEVEL_SEALED_LENGTH_READONLY = 5;
   protected static final int INTEGRITY_LEVEL_FROZEN = 6;
   protected static final int INTEGRITY_LEVEL_FROZEN_LENGTH_READONLY = 7;
   protected static final int INTEGRITY_LEVELS = 8;
   protected static final int INTEGRITY_LEVEL_MASK = 6;
   protected static final int LENGTH_WRITABLE_MASK = 1;
   protected static final int LENGTH_NOT_WRITABLE = 1;
   protected final int integrityLevel;
   protected final DynamicArray.DynamicArrayCache cache;

   protected DynamicArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      CompilerAsserts.neverPartOfCompilation();
      this.integrityLevel = integrityLevel;
      this.cache = cache;
   }

   protected final <T extends ScriptArray> T maybePreinitializeCache() {
      assert this.integrityLevel == 0;

      if (JSConfig.SubstrateVM) {
         this.cache.withIntegrityLevel[0] = this;

         for (int level = 1; level < 8; level++) {
            if (this.cache.withIntegrityLevel[level] == null) {
               this.cache.withIntegrityLevel[level] = this.withIntegrityLevel(level);
            }
         }
      }

      return (T)this;
   }

   protected static DynamicArray.DynamicArrayCache createCache() {
      return new DynamicArray.DynamicArrayCache();
   }

   protected abstract DynamicArray withIntegrityLevel(int newIntegrityLevel);

   protected final <T extends ScriptArray> T setIntegrityLevel(int integrityLevel) {
      if (this.integrityLevel == integrityLevel) {
         return (T)this;
      } else {
         CompilerAsserts.partialEvaluationConstant(this.cache);
         DynamicArray cached = this.cache.withIntegrityLevel[integrityLevel];
         if (cached == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            DynamicArray newArray = this.withIntegrityLevel(integrityLevel);
            this.cache.withIntegrityLevel[integrityLevel] = newArray;

            assert newArray.getClass() == this.getClass();

            return (T)newArray;
         } else {
            return (T)cached;
         }
      }
   }

   @Override
   public boolean isSealed() {
      return this.integrityLevel >= 4;
   }

   @Override
   public boolean isFrozen() {
      return this.integrityLevel >= 6;
   }

   @Override
   public boolean isExtensible() {
      return this.integrityLevel < 2;
   }

   @Override
   public boolean isLengthNotWritable() {
      return (this.integrityLevel & 1) != 0;
   }

   @Override
   public ScriptArray seal() {
      return (ScriptArray)(this.isSealed() ? this : this.setIntegrityLevel(4 | this.integrityLevel & -7));
   }

   @Override
   public ScriptArray freeze() {
      return (ScriptArray)(this.isFrozen() ? this : this.setIntegrityLevel(7 | this.integrityLevel & -7));
   }

   @Override
   public ScriptArray preventExtensions() {
      return (ScriptArray)(!this.isExtensible() ? this : this.setIntegrityLevel(2 | this.integrityLevel & -7));
   }

   @Override
   public ScriptArray setLengthNotWritable() {
      return (ScriptArray)(this.isLengthNotWritable() ? this : this.setIntegrityLevel(1 | this.integrityLevel & -2));
   }

   public abstract Object cloneArray(JSDynamicObject object);

   @Override
   public String toString() {
      return super.toString() + "[integrityLevel=" + this.integrityLevel + "]";
   }

   protected static final class DynamicArrayCache {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final DynamicArray[] withIntegrityLevel = new DynamicArray[8];
   }
}
