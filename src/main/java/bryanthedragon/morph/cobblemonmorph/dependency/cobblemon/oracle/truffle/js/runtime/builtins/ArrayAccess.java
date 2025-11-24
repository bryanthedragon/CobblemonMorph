
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import java.util.TreeMap;

public class ArrayAccess {
    public static final ArrayAccess SINGLETON = new ArrayAccess();

    protected ArrayAccess() {
    }

    public ScriptArray getArrayType(Object thisObj) {
        return ((JSArrayBase)thisObj).getArrayType();
    }

    public long getLength(Object thisObj) {
        return Integer.toUnsignedLong(((JSArrayBase)thisObj).length);
    }

    public int getUsedLength(Object thisObj) {
        return ((JSArrayBase)thisObj).usedLength;
    }

    public long getIndexOffset(Object thisObj) {
        return ((JSArrayBase)thisObj).indexOffset;
    }

    public int getArrayOffset(Object thisObj) {
        return ((JSArrayBase)thisObj).arrayOffset;
    }

    public void setArrayType(Object thisObj, ScriptArray arrayType) {
        ((JSArrayBase)thisObj).setArrayType(arrayType);
    }

    public void setLength(Object thisObj, long length) {
        assert (JSRuntime.isRepresentableAsUnsignedInt(length));
        ((JSArrayBase)thisObj).length = (int)length;
    }

    public void setUsedLength(Object thisObj, int usedLength) {
        assert (usedLength >= 0);
        ((JSArrayBase)thisObj).usedLength = usedLength;
    }

    public void setIndexOffset(Object thisObj, long indexOffset) {
        ((JSArrayBase)thisObj).indexOffset = (int)indexOffset;
    }

    public void setArrayOffset(Object thisObj, int arrayOffset) {
        assert (arrayOffset >= 0);
        ((JSArrayBase)thisObj).arrayOffset = arrayOffset;
    }

    public Object getArray(Object thisObj) {
        return ((JSArrayBase)thisObj).getArray();
    }

    public void setArray(Object thisObj, Object array) {
        assert (array != null && (array.getClass().isArray() || array instanceof TreeMap));
        ((JSArrayBase)thisObj).setArray(array);
    }

    public int getHoleCount(Object thisObj) {
        return ((JSArrayBase)thisObj).holeCount;
    }

    public void setHoleCount(Object thisObj, int holeCount) {
        assert (holeCount >= 0);
        ((JSArrayBase)thisObj).holeCount = holeCount;
    }

    public ArrayAllocationSite getAllocationSite(Object thisObj) {
        return ((JSArrayBase)thisObj).allocationSite;
    }
}

