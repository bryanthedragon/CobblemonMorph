
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfStringNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputRegionMatchesNode;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InputIndexOfStringNode.class)
public final class InputIndexOfStringNodeGen
extends InputIndexOfStringNode {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private TruffleString.ByteIndexOfStringNode tString_indexOfStringNode_;
    @Node.Child
    private TruffleString.ByteIndexOfStringNode tStringMask_indexOfStringNode_;
    @Node.Child
    private InputLengthNode truffleObjBytes_lengthNode_;
    @Node.Child
    private InputRegionMatchesNode truffleObjBytes_regionMatchesNode_;
    @Node.Child
    private InputLengthNode truffleObjString_lengthNode_;
    @Node.Child
    private InputRegionMatchesNode truffleObjString_regionMatchesNode_;

    private InputIndexOfStringNodeGen() {
    }

    @Override
    public int execute(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Object arg4Value, Encodings.Encoding arg5Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            Object arg4Value_;
            Object arg3Value_;
            Object arg0Value_;
            if ((state_0 & 3) != 0 && arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg3Value instanceof byte[]) {
                    arg3Value_ = (byte[])arg3Value;
                    if ((state_0 & 1) != 0 && arg4Value == null) {
                        return this.doBytes((byte[])arg0Value_, arg1Value, arg2Value, (byte[])arg3Value_, arg4Value, arg5Value);
                    }
                    if ((state_0 & 2) != 0 && arg4Value instanceof byte[] && (arg4Value_ = (byte[])arg4Value) != null) {
                        return this.doBytesMask((byte[])arg0Value_, arg1Value, arg2Value, (byte[])arg3Value_, (byte[])arg4Value_, arg5Value);
                    }
                }
            }
            if ((state_0 & 0xC) != 0 && arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg3Value instanceof String) {
                    arg3Value_ = (String)arg3Value;
                    if ((state_0 & 4) != 0 && arg4Value == null) {
                        return this.doString((String)arg0Value_, arg1Value, arg2Value, (String)arg3Value_, arg4Value, arg5Value);
                    }
                    if ((state_0 & 8) != 0 && arg4Value instanceof String && (arg4Value_ = (Object)((String)arg4Value)) != null) {
                        return this.doStringMask((String)arg0Value_, arg1Value, arg2Value, (String)arg3Value_, (String)arg4Value_, arg5Value);
                    }
                }
            }
            if ((state_0 & 0x30) != 0 && arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if (arg3Value instanceof TruffleString) {
                    arg3Value_ = (TruffleString)arg3Value;
                    if ((state_0 & 0x10) != 0 && arg4Value == null) {
                        return this.doTString((TruffleString)arg0Value_, arg1Value, arg2Value, (TruffleString)arg3Value_, arg4Value, arg5Value, this.tString_indexOfStringNode_);
                    }
                    if ((state_0 & 0x20) != 0 && arg4Value instanceof TruffleString.WithMask && (arg4Value_ = (Object)((TruffleString.WithMask)arg4Value)) != null) {
                        return this.doTStringMask((TruffleString)arg0Value_, arg1Value, arg2Value, (TruffleString)arg3Value_, (TruffleString.WithMask)arg4Value_, arg5Value, this.tStringMask_indexOfStringNode_);
                    }
                }
            }
            if ((state_0 & 0xC0) != 0) {
                Object arg3Value_2;
                if ((state_0 & 0x40) != 0 && arg3Value instanceof byte[]) {
                    arg3Value_2 = (byte[])arg3Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                        return this.doTruffleObjBytes(arg0Value, arg1Value, arg2Value, (byte[])arg3Value_2, arg4Value, arg5Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_regionMatchesNode_);
                    }
                }
                if ((state_0 & 0x80) != 0 && arg3Value instanceof String) {
                    arg3Value_2 = (String)arg3Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                        return this.doTruffleObjString(arg0Value, arg1Value, arg2Value, (String)arg3Value_2, arg4Value, arg5Value, this.truffleObjString_lengthNode_, this.truffleObjString_regionMatchesNode_);
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
    }

    private int executeAndSpecialize(Object arg0Value, int arg1Value, int arg2Value, Object arg3Value, Object arg4Value, Encodings.Encoding arg5Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object arg3Value_;
            Object arg4Value_2;
            Object arg3Value_2;
            Object arg0Value_;
            int state_0 = this.state_0_;
            if (arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg3Value instanceof byte[]) {
                    arg3Value_2 = (byte[])arg3Value;
                    if (arg4Value == null) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        int n = this.doBytes((byte[])arg0Value_, arg1Value, arg2Value, (byte[])arg3Value_2, arg4Value, arg5Value);
                        return n;
                    }
                    if (arg4Value instanceof byte[] && (arg4Value_2 = (byte[])arg4Value) != null) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        int n = this.doBytesMask((byte[])arg0Value_, arg1Value, arg2Value, (byte[])arg3Value_2, (byte[])arg4Value_2, arg5Value);
                        return n;
                    }
                }
            }
            if (arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg3Value instanceof String) {
                    arg3Value_2 = (String)arg3Value;
                    if (arg4Value == null) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        int arg4Value_2 = this.doString((String)arg0Value_, arg1Value, arg2Value, (String)arg3Value_2, arg4Value, arg5Value);
                        return arg4Value_2;
                    }
                    if (arg4Value instanceof String && (arg4Value_2 = (Object)((String)arg4Value)) != null) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        int n = this.doStringMask((String)arg0Value_, arg1Value, arg2Value, (String)arg3Value_2, (String)arg4Value_2, arg5Value);
                        return n;
                    }
                }
            }
            if (arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if (arg3Value instanceof TruffleString) {
                    arg3Value_2 = (TruffleString)arg3Value;
                    if (arg4Value == null) {
                        this.tString_indexOfStringNode_ = super.insert(TruffleString.ByteIndexOfStringNode.create());
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        int arg4Value_3 = this.doTString((TruffleString)arg0Value_, arg1Value, arg2Value, (TruffleString)arg3Value_2, arg4Value, arg5Value, this.tString_indexOfStringNode_);
                        return arg4Value_3;
                    }
                    if (arg4Value instanceof TruffleString.WithMask && (arg4Value_2 = (Object)((TruffleString.WithMask)arg4Value)) != null) {
                        this.tStringMask_indexOfStringNode_ = super.insert(TruffleString.ByteIndexOfStringNode.create());
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        int n = this.doTStringMask((TruffleString)arg0Value_, arg1Value, arg2Value, (TruffleString)arg3Value_2, (TruffleString.WithMask)arg4Value_2, arg5Value, this.tStringMask_indexOfStringNode_);
                        return n;
                    }
                }
            }
            if (arg3Value instanceof byte[]) {
                arg3Value_ = (byte[])arg3Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                    this.truffleObjBytes_lengthNode_ = super.insert(InputLengthNode.create());
                    this.truffleObjBytes_regionMatchesNode_ = super.insert(InputRegionMatchesNode.create());
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doTruffleObjBytes(arg0Value, arg1Value, arg2Value, (byte[])arg3Value_, arg4Value, arg5Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_regionMatchesNode_);
                    return n;
                }
            }
            if (arg3Value instanceof String) {
                arg3Value_ = (String)arg3Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                    this.truffleObjString_lengthNode_ = super.insert(InputLengthNode.create());
                    this.truffleObjString_regionMatchesNode_ = super.insert(InputRegionMatchesNode.create());
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    int n = this.doTruffleObjString(arg0Value, arg1Value, arg2Value, (String)arg3Value_, arg4Value, arg5Value, this.truffleObjString_lengthNode_, this.truffleObjString_regionMatchesNode_);
                    return n;
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value);
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

    public static InputIndexOfStringNode create() {
        return new InputIndexOfStringNodeGen();
    }
}

