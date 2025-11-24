
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class DynamicArray
extends ScriptArray {
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
    protected final DynamicArrayCache cache;

    protected DynamicArray(int integrityLevel, DynamicArrayCache cache) {
        CompilerAsserts.neverPartOfCompilation();
        this.integrityLevel = integrityLevel;
        this.cache = cache;
    }

    protected final <T extends ScriptArray> T maybePreinitializeCache() {
        assert (this.integrityLevel == 0);
        if (JSConfig.SubstrateVM) {
            this.cache.withIntegrityLevel[0] = this;
            for (int level = 1; level < 8; ++level) {
                if (this.cache.withIntegrityLevel[level] != null) continue;
                this.cache.withIntegrityLevel[level] = this.withIntegrityLevel(level);
            }
        }
        return (T)this;
    }

    protected static DynamicArrayCache createCache() {
        return new DynamicArrayCache();
    }

    protected abstract DynamicArray withIntegrityLevel(int var1);

    protected final <T extends ScriptArray> T setIntegrityLevel(int integrityLevel) {
        if (this.integrityLevel == integrityLevel) {
            return (T)this;
        }
        CompilerAsserts.partialEvaluationConstant(this.cache);
        DynamicArray cached = this.cache.withIntegrityLevel[integrityLevel];
        if (cached == null) {
            DynamicArray newArray;
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.cache.withIntegrityLevel[integrityLevel] = newArray = this.withIntegrityLevel(integrityLevel);
            assert (newArray.getClass() == this.getClass());
            return (T)newArray;
        }
        return (T)cached;
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
        return this.isSealed() ? this : this.setIntegrityLevel(4 | this.integrityLevel & 0xFFFFFFF9);
    }

    @Override
    public ScriptArray freeze() {
        return this.isFrozen() ? this : this.setIntegrityLevel(7 | this.integrityLevel & 0xFFFFFFF9);
    }

    @Override
    public ScriptArray preventExtensions() {
        return !this.isExtensible() ? this : this.setIntegrityLevel(2 | this.integrityLevel & 0xFFFFFFF9);
    }

    @Override
    public ScriptArray setLengthNotWritable() {
        return this.isLengthNotWritable() ? this : this.setIntegrityLevel(1 | this.integrityLevel & 0xFFFFFFFE);
    }

    public abstract Object cloneArray(JSDynamicObject var1);

    public String toString() {
        return super.toString() + "[integrityLevel=" + this.integrityLevel + "]";
    }

    protected static final class DynamicArrayCache {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final DynamicArray[] withIntegrityLevel = new DynamicArray[8];

        protected DynamicArrayCache() {
        }
    }
}

