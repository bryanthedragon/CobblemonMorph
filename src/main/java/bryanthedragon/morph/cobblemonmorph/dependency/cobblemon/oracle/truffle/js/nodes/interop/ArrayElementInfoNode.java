
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;

@GenerateUncached
public abstract class ArrayElementInfoNode
extends JavaScriptBaseNode {
    public static final int READABLE = 1;
    public static final int MODIFIABLE = 2;
    public static final int INSERTABLE = 4;
    public static final int REMOVABLE = 8;
    public static final int WRITABLE = 6;

    ArrayElementInfoNode() {
    }

    public abstract TriState execute(JSArrayBase var1, long var2, int var4);

    public final boolean executeBoolean(JSArrayBase receiver, long index, int query2) {
        return this.execute(receiver, index, query2) == TriState.TRUE;
    }

    public final void executeCheck(JSArrayBase receiver, long index, int query2) throws UnsupportedMessageException, InvalidArrayIndexException {
        TriState result = this.execute(receiver, index, query2);
        if (result != TriState.TRUE) {
            if (result == TriState.UNDEFINED) {
                throw UnsupportedMessageException.create();
            }
            throw InvalidArrayIndexException.create(index);
        }
    }

    @Specialization(guards={"arrayType.isInstance(target.getArrayType())"}, limit="5")
    static TriState doCached(JSArrayBase target, long index, int query2, @Cached(value="target.getArrayType()") ScriptArray arrayType) {
        if ((query2 & 0xE) != 0 && arrayType.isFrozen()) {
            return TriState.UNDEFINED;
        }
        if (index >= 0L && index < arrayType.length(target)) {
            if ((query2 & 1) != 0) {
                return TriState.TRUE;
            }
            if ((query2 & 2) != 0) {
                assert (!arrayType.isFrozen());
                return TriState.TRUE;
            }
            if ((query2 & 8) != 0 && !arrayType.isSealed() && !arrayType.isLengthNotWritable()) {
                return TriState.TRUE;
            }
            return TriState.FALSE;
        }
        if ((query2 & 4) != 0 && JSRuntime.isArrayIndex(index) && !arrayType.isSealed() && !arrayType.isLengthNotWritable()) {
            return TriState.TRUE;
        }
        return TriState.FALSE;
    }

    @Specialization(replaces={"doCached"})
    static TriState doUncached(JSArrayBase target, long index, int query2) {
        return ArrayElementInfoNode.doCached(target, index, query2, target.getArrayType());
    }
}

