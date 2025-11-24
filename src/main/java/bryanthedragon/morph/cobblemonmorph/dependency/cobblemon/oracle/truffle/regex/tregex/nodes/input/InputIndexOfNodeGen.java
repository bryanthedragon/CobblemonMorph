
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNode;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InputIndexOfNode.class)
public final class InputIndexOfNodeGen
extends InputIndexOfNode {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private TruffleString.ByteIndexOfAnyByteNode tStringBytes_indexOfRawValueNode_;
    @Node.Child
    private TruffleString.CharIndexOfAnyCharUTF16Node tStringChars_indexOfRawValueNode_;
    @Node.Child
    private TruffleString.IntIndexOfAnyIntUTF32Node tStringInts_indexOfRawValueNode_;
    @Node.Child
    private InputReadNode truffleObjBytes_charAtNode_;
    @Node.Child
    private InputReadNode truffleObjChars_charAtNode_;

    private InputIndexOfNodeGen() {
    }

    @Override
    public int execute(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Encodings.Encoding arg4Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            Object arg0Value_;
            if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg3Value instanceof byte[]) {
                    byte[] arg3Value_ = (byte[])arg3Value;
                    return this.doBytes((byte[])arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value);
                }
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg3Value instanceof char[]) {
                    char[] arg3Value_ = (char[])arg3Value;
                    return this.doChars((String)arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value);
                }
            }
            if ((state_0 & 0x1C) != 0 && arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if ((state_0 & 4) != 0 && arg3Value instanceof byte[]) {
                    byte[] arg3Value_ = (byte[])arg3Value;
                    return this.doTStringBytes((TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringBytes_indexOfRawValueNode_);
                }
                if ((state_0 & 8) != 0 && arg3Value instanceof char[]) {
                    char[] arg3Value_ = (char[])arg3Value;
                    return this.doTStringChars((TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringChars_indexOfRawValueNode_);
                }
                if ((state_0 & 0x10) != 0 && arg3Value instanceof int[]) {
                    int[] arg3Value_ = (int[])arg3Value;
                    return this.doTStringInts((TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value_, arg4Value, this.tStringInts_indexOfRawValueNode_);
                }
            }
            if ((state_0 & 0x60) != 0) {
                Object[] arg3Value_;
                if ((state_0 & 0x20) != 0 && arg3Value instanceof byte[]) {
                    arg3Value_ = (byte[])arg3Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                        return this.doTruffleObjBytes(arg0Value, arg1Value, arg2Value, (byte[])arg3Value_, arg4Value, this.truffleObjBytes_charAtNode_);
                    }
                }
                if ((state_0 & 0x40) != 0 && arg3Value instanceof char[]) {
                    arg3Value_ = (char[])arg3Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                        return this.doTruffleObjChars(arg0Value, arg1Value, arg2Value, (char[])arg3Value_, arg4Value, this.truffleObjChars_charAtNode_);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
    }

    private int executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Encodings.Encoding arg4Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object[] arg3Value_;
            Object arg0Value_;
            int state_0 = this.state_0_;
            if (arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg3Value instanceof byte[]) {
                    byte[] arg3Value_2 = (byte[])arg3Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doBytes((byte[])arg0Value_, arg1Value, arg2Value, arg3Value_2, arg4Value);
                    return n;
                }
            }
            if (arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg3Value instanceof char[]) {
                    char[] arg3Value_3 = (char[])arg3Value;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doChars((String)arg0Value_, arg1Value, arg2Value, arg3Value_3, arg4Value);
                    return n;
                }
            }
            if (arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if (arg3Value instanceof byte[]) {
                    byte[] arg3Value_4 = (byte[])arg3Value;
                    this.tStringBytes_indexOfRawValueNode_ = super.insert(TruffleString.ByteIndexOfAnyByteNode.create());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doTStringBytes((TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value_4, arg4Value, this.tStringBytes_indexOfRawValueNode_);
                    return n;
                }
                if (arg3Value instanceof char[]) {
                    char[] arg3Value_5 = (char[])arg3Value;
                    this.tStringChars_indexOfRawValueNode_ = super.insert(TruffleString.CharIndexOfAnyCharUTF16Node.create());
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doTStringChars((TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value_5, arg4Value, this.tStringChars_indexOfRawValueNode_);
                    return n;
                }
                if (arg3Value instanceof int[]) {
                    int[] arg3Value_6 = (int[])arg3Value;
                    this.tStringInts_indexOfRawValueNode_ = super.insert(TruffleString.IntIndexOfAnyIntUTF32Node.create());
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doTStringInts((TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value_6, arg4Value, this.tStringInts_indexOfRawValueNode_);
                    return n;
                }
            }
            if (arg3Value instanceof byte[]) {
                arg3Value_ = (byte[])arg3Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                    this.truffleObjBytes_charAtNode_ = super.insert(InputReadNode.create());
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doTruffleObjBytes(arg0Value, arg1Value, arg2Value, (byte[])arg3Value_, arg4Value, this.truffleObjBytes_charAtNode_);
                    return n;
                }
            }
            if (arg3Value instanceof char[]) {
                arg3Value_ = (char[])arg3Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                    this.truffleObjChars_charAtNode_ = super.insert(InputReadNode.create());
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doTruffleObjChars(arg0Value, arg1Value, arg2Value, (char[])arg3Value_, arg4Value, this.truffleObjChars_charAtNode_);
                    return n;
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static InputIndexOfNode create() {
        return new InputIndexOfNodeGen();
    }
}

