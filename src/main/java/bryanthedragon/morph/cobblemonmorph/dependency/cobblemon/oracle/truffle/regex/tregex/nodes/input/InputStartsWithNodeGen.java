
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputStartsWithNode;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InputStartsWithNode.class)
public final class InputStartsWithNodeGen
extends InputStartsWithNode {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private TruffleString.RegionEqualByteIndexNode tString_regionEqualsNode_;
    @Node.Child
    private TruffleString.RegionEqualByteIndexNode tStringMask_regionEqualsNode_;
    @Node.Child
    private InputLengthNode truffleObjBytes_lengthNode_;
    @Node.Child
    private InputReadNode truffleObjBytes_charAtNode_;
    @Node.Child
    private InputLengthNode truffleObjBytesMask_lengthNode_;
    @Node.Child
    private InputReadNode truffleObjBytesMask_charAtNode_;
    @Node.Child
    private InputLengthNode truffleObjString_lengthNode_;
    @Node.Child
    private InputReadNode truffleObjString_charAtNode_;
    @Node.Child
    private InputLengthNode truffleObjStringMask_lengthNode_;
    @Node.Child
    private InputReadNode truffleObjStringMask_charAtNode_;

    private InputStartsWithNodeGen() {
    }

    @Override
    public boolean execute(Object arg0Value, Object arg1Value, Object arg2Value, Encodings.Encoding arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            Object arg2Value_;
            Object arg1Value_;
            Object arg0Value_;
            if ((state_0 & 3) != 0 && arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg1Value instanceof byte[]) {
                    arg1Value_ = (byte[])arg1Value;
                    if ((state_0 & 1) != 0 && arg2Value == null) {
                        return this.doBytes((byte[])arg0Value_, (byte[])arg1Value_, arg2Value, arg3Value);
                    }
                    if ((state_0 & 2) != 0 && arg2Value instanceof byte[] && (arg2Value_ = (byte[])arg2Value) != null) {
                        return this.doBytesMask((byte[])arg0Value_, (byte[])arg1Value_, (byte[])arg2Value_, arg3Value);
                    }
                }
            }
            if ((state_0 & 0xC) != 0 && arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg1Value instanceof String) {
                    arg1Value_ = (String)arg1Value;
                    if ((state_0 & 4) != 0 && arg2Value == null) {
                        return this.doString((String)arg0Value_, (String)arg1Value_, arg2Value, arg3Value);
                    }
                    if ((state_0 & 8) != 0 && arg2Value instanceof String && (arg2Value_ = (Object)((String)arg2Value)) != null) {
                        return this.doStringMask((String)arg0Value_, (String)arg1Value_, (String)arg2Value_, arg3Value);
                    }
                }
            }
            if ((state_0 & 0x30) != 0 && arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if (arg1Value instanceof TruffleString) {
                    arg1Value_ = (TruffleString)arg1Value;
                    if ((state_0 & 0x10) != 0 && arg2Value == null) {
                        return this.doTString((TruffleString)arg0Value_, (TruffleString)arg1Value_, arg2Value, arg3Value, this.tString_regionEqualsNode_);
                    }
                    if ((state_0 & 0x20) != 0 && arg2Value instanceof TruffleString.WithMask && (arg2Value_ = (Object)((TruffleString.WithMask)arg2Value)) != null) {
                        return this.doTStringMask((TruffleString)arg0Value_, (TruffleString)arg1Value_, (TruffleString.WithMask)arg2Value_, arg3Value, this.tStringMask_regionEqualsNode_);
                    }
                }
            }
            if ((state_0 & 0x3C0) != 0) {
                Object arg2Value_2;
                Object arg1Value_2;
                if ((state_0 & 0xC0) != 0 && arg1Value instanceof byte[]) {
                    arg1Value_2 = (byte[])arg1Value;
                    if ((state_0 & 0x40) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
                        return this.doTruffleObjBytes(arg0Value, (byte[])arg1Value_2, arg2Value, arg3Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_charAtNode_);
                    }
                    if ((state_0 & 0x80) != 0 && arg2Value instanceof byte[]) {
                        arg2Value_2 = (byte[])arg2Value;
                        if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_2 != null) {
                            return this.doTruffleObjBytesMask(arg0Value, (byte[])arg1Value_2, (byte[])arg2Value_2, arg3Value, this.truffleObjBytesMask_lengthNode_, this.truffleObjBytesMask_charAtNode_);
                        }
                    }
                }
                if ((state_0 & 0x300) != 0 && arg1Value instanceof String) {
                    arg1Value_2 = (String)arg1Value;
                    if ((state_0 & 0x100) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
                        return this.doTruffleObjString(arg0Value, (String)arg1Value_2, arg2Value, arg3Value, this.truffleObjString_lengthNode_, this.truffleObjString_charAtNode_);
                    }
                    if ((state_0 & 0x200) != 0 && arg2Value instanceof String) {
                        arg2Value_2 = (String)arg2Value;
                        if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_2 != null) {
                            return this.doTruffleObjStringMask(arg0Value, (String)arg1Value_2, (String)arg2Value_2, arg3Value, this.truffleObjStringMask_lengthNode_, this.truffleObjStringMask_charAtNode_);
                        }
                    }
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
    }

    private boolean executeAndSpecialize(Object arg0Value, Object arg1Value, Object arg2Value, Encodings.Encoding arg3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object arg2Value_4;
            Object arg1Value_;
            Object arg2Value_22;
            Object arg1Value_2;
            Object arg0Value_;
            int state_0 = this.state_0_;
            if (arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg1Value instanceof byte[]) {
                    arg1Value_2 = (byte[])arg1Value;
                    if (arg2Value == null) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doBytes((byte[])arg0Value_, (byte[])arg1Value_2, arg2Value, arg3Value);
                        return bl;
                    }
                    if (arg2Value instanceof byte[] && (arg2Value_22 = (byte[])arg2Value) != null) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doBytesMask((byte[])arg0Value_, (byte[])arg1Value_2, (byte[])arg2Value_22, arg3Value);
                        return bl;
                    }
                }
            }
            if (arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg1Value instanceof String) {
                    arg1Value_2 = (String)arg1Value;
                    if (arg2Value == null) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean arg2Value_22 = this.doString((String)arg0Value_, (String)arg1Value_2, arg2Value, arg3Value);
                        return arg2Value_22;
                    }
                    if (arg2Value instanceof String && (arg2Value_22 = (Object)((String)arg2Value)) != null) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doStringMask((String)arg0Value_, (String)arg1Value_2, (String)arg2Value_22, arg3Value);
                        return bl;
                    }
                }
            }
            if (arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if (arg1Value instanceof TruffleString) {
                    arg1Value_2 = (TruffleString)arg1Value;
                    if (arg2Value == null) {
                        this.tString_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        boolean arg2Value_3 = this.doTString((TruffleString)arg0Value_, (TruffleString)arg1Value_2, arg2Value, arg3Value, this.tString_regionEqualsNode_);
                        return arg2Value_3;
                    }
                    if (arg2Value instanceof TruffleString.WithMask && (arg2Value_22 = (Object)((TruffleString.WithMask)arg2Value)) != null) {
                        this.tStringMask_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doTStringMask((TruffleString)arg0Value_, (TruffleString)arg1Value_2, (TruffleString.WithMask)arg2Value_22, arg3Value, this.tStringMask_regionEqualsNode_);
                        return bl;
                    }
                }
            }
            if (arg1Value instanceof byte[]) {
                arg1Value_ = (byte[])arg1Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
                    this.truffleObjBytes_lengthNode_ = super.insert(InputLengthNode.create());
                    this.truffleObjBytes_charAtNode_ = super.insert(InputReadNode.create());
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean arg1Value_3 = this.doTruffleObjBytes(arg0Value, (byte[])arg1Value_, arg2Value, arg3Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_charAtNode_);
                    return arg1Value_3;
                }
                if (arg2Value instanceof byte[]) {
                    arg2Value_4 = (byte[])arg2Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_4 != null) {
                        this.truffleObjBytesMask_lengthNode_ = super.insert(InputLengthNode.create());
                        this.truffleObjBytesMask_charAtNode_ = super.insert(InputReadNode.create());
                        this.state_0_ = state_0 |= 0x80;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doTruffleObjBytesMask(arg0Value, (byte[])arg1Value_, (byte[])arg2Value_4, arg3Value, this.truffleObjBytesMask_lengthNode_, this.truffleObjBytesMask_charAtNode_);
                        return bl;
                    }
                }
            }
            if (arg1Value instanceof String) {
                arg1Value_ = (String)arg1Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value == null) {
                    this.truffleObjString_lengthNode_ = super.insert(InputLengthNode.create());
                    this.truffleObjString_charAtNode_ = super.insert(InputReadNode.create());
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    boolean arg2Value_4 = this.doTruffleObjString(arg0Value, (String)arg1Value_, arg2Value, arg3Value, this.truffleObjString_lengthNode_, this.truffleObjString_charAtNode_);
                    return arg2Value_4;
                }
                if (arg2Value instanceof String) {
                    arg2Value_4 = (String)arg2Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg2Value_4 != null) {
                        this.truffleObjStringMask_lengthNode_ = super.insert(InputLengthNode.create());
                        this.truffleObjStringMask_charAtNode_ = super.insert(InputReadNode.create());
                        this.state_0_ = state_0 |= 0x200;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doTruffleObjStringMask(arg0Value, (String)arg1Value_, (String)arg2Value_4, arg3Value, this.truffleObjStringMask_lengthNode_, this.truffleObjStringMask_charAtNode_);
                        return bl;
                    }
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
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

    public static InputStartsWithNode create() {
        return new InputStartsWithNodeGen();
    }
}

