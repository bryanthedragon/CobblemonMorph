/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.tregex.nodes.input;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.input.InputLengthNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputReadNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputRegionMatchesNode;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InputRegionMatchesNode.class)
public final class InputRegionMatchesNodeGen
extends InputRegionMatchesNode {
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
    @Node.Child
    private TruffleObjTruffleObjData truffleObjTruffleObj_cache;

    private InputRegionMatchesNodeGen() {
    }

    @Override
    public boolean execute(Object arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, Object arg5Value, Encodings.Encoding arg6Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            Object arg5Value_;
            Object arg2Value_;
            Object arg0Value_;
            if ((state_0 & 3) != 0 && arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg2Value instanceof byte[]) {
                    arg2Value_ = (byte[])arg2Value;
                    if ((state_0 & 1) != 0 && arg5Value == null) {
                        return this.doBytes((byte[])arg0Value_, arg1Value, (byte[])arg2Value_, arg3Value, arg4Value, arg5Value, arg6Value);
                    }
                    if ((state_0 & 2) != 0 && arg5Value instanceof byte[] && (arg5Value_ = (byte[])arg5Value) != null) {
                        return this.doBytesMask((byte[])arg0Value_, arg1Value, (byte[])arg2Value_, arg3Value, arg4Value, (byte[])arg5Value_, arg6Value);
                    }
                }
            }
            if ((state_0 & 0xC) != 0 && arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg2Value instanceof String) {
                    arg2Value_ = (String)arg2Value;
                    if ((state_0 & 4) != 0 && arg5Value == null) {
                        return this.doString((String)arg0Value_, arg1Value, (String)arg2Value_, arg3Value, arg4Value, arg5Value, arg6Value);
                    }
                    if ((state_0 & 8) != 0 && arg5Value instanceof String && (arg5Value_ = (Object)((String)arg5Value)) != null) {
                        return this.doJavaStringMask((String)arg0Value_, arg1Value, (String)arg2Value_, arg3Value, arg4Value, (String)arg5Value_, arg6Value);
                    }
                }
            }
            if ((state_0 & 0x30) != 0 && arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if (arg2Value instanceof TruffleString) {
                    arg2Value_ = (TruffleString)arg2Value;
                    if ((state_0 & 0x10) != 0 && arg5Value == null) {
                        return this.doTString((TruffleString)arg0Value_, arg1Value, (TruffleString)arg2Value_, arg3Value, arg4Value, arg5Value, arg6Value, this.tString_regionEqualsNode_);
                    }
                    if ((state_0 & 0x20) != 0 && arg5Value instanceof TruffleString.WithMask && (arg5Value_ = (Object)((TruffleString.WithMask)arg5Value)) != null) {
                        return this.doTStringMask((TruffleString)arg0Value_, arg1Value, (TruffleString)arg2Value_, arg3Value, arg4Value, (TruffleString.WithMask)arg5Value_, arg6Value, this.tStringMask_regionEqualsNode_);
                    }
                }
            }
            if ((state_0 & 0x7C0) != 0) {
                TruffleObjTruffleObjData s10_;
                Object arg5Value_2;
                Object arg2Value_2;
                if ((state_0 & 0xC0) != 0 && arg2Value instanceof byte[]) {
                    arg2Value_2 = (byte[])arg2Value;
                    if ((state_0 & 0x40) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
                        return this.doTruffleObjBytes(arg0Value, arg1Value, (byte[])arg2Value_2, arg3Value, arg4Value, arg5Value, arg6Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_charAtNode_);
                    }
                    if ((state_0 & 0x80) != 0 && arg5Value instanceof byte[]) {
                        arg5Value_2 = (byte[])arg5Value;
                        if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_2 != null) {
                            return this.doTruffleObjBytesMask(arg0Value, arg1Value, (byte[])arg2Value_2, arg3Value, arg4Value, (byte[])arg5Value_2, arg6Value, this.truffleObjBytesMask_lengthNode_, this.truffleObjBytesMask_charAtNode_);
                        }
                    }
                }
                if ((state_0 & 0x300) != 0 && arg2Value instanceof String) {
                    arg2Value_2 = (String)arg2Value;
                    if ((state_0 & 0x100) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
                        return this.doTruffleObjString(arg0Value, arg1Value, (String)arg2Value_2, arg3Value, arg4Value, arg5Value, arg6Value, this.truffleObjString_lengthNode_, this.truffleObjString_charAtNode_);
                    }
                    if ((state_0 & 0x200) != 0 && arg5Value instanceof String) {
                        arg5Value_2 = (String)arg5Value;
                        if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_2 != null) {
                            return this.doTruffleObjStringMask(arg0Value, arg1Value, (String)arg2Value_2, arg3Value, arg4Value, (String)arg5Value_2, arg6Value, this.truffleObjStringMask_lengthNode_, this.truffleObjStringMask_charAtNode_);
                        }
                    }
                }
                if ((state_0 & 0x400) != 0 && (s10_ = this.truffleObjTruffleObj_cache) != null && TRegexGuards.neitherByteArrayNorString(arg0Value) && TRegexGuards.neitherByteArrayNorString(arg2Value) && arg5Value == null) {
                    return this.doTruffleObjTruffleObj(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s10_.lengthNode1_, s10_.charAtNode1_, s10_.lengthNode2_, s10_.charAtNode2_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
    }

    private boolean executeAndSpecialize(Object arg0Value, int arg1Value, Object arg2Value, int arg3Value, int arg4Value, Object arg5Value, Encodings.Encoding arg6Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object arg5Value_4;
            Object arg2Value_;
            Object arg5Value_22;
            Object arg2Value_2;
            Object arg0Value_;
            int state_0 = this.state_0_;
            if (arg0Value instanceof byte[]) {
                arg0Value_ = (byte[])arg0Value;
                if (arg2Value instanceof byte[]) {
                    arg2Value_2 = (byte[])arg2Value;
                    if (arg5Value == null) {
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doBytes((byte[])arg0Value_, arg1Value, (byte[])arg2Value_2, arg3Value, arg4Value, arg5Value, arg6Value);
                        return bl;
                    }
                    if (arg5Value instanceof byte[] && (arg5Value_22 = (byte[])arg5Value) != null) {
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doBytesMask((byte[])arg0Value_, arg1Value, (byte[])arg2Value_2, arg3Value, arg4Value, (byte[])arg5Value_22, arg6Value);
                        return bl;
                    }
                }
            }
            if (arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (arg2Value instanceof String) {
                    arg2Value_2 = (String)arg2Value;
                    if (arg5Value == null) {
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        boolean arg5Value_22 = this.doString((String)arg0Value_, arg1Value, (String)arg2Value_2, arg3Value, arg4Value, arg5Value, arg6Value);
                        return arg5Value_22;
                    }
                    if (arg5Value instanceof String && (arg5Value_22 = (Object)((String)arg5Value)) != null) {
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doJavaStringMask((String)arg0Value_, arg1Value, (String)arg2Value_2, arg3Value, arg4Value, (String)arg5Value_22, arg6Value);
                        return bl;
                    }
                }
            }
            if (arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                if (arg2Value instanceof TruffleString) {
                    arg2Value_2 = (TruffleString)arg2Value;
                    if (arg5Value == null) {
                        this.tString_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        boolean arg5Value_3 = this.doTString((TruffleString)arg0Value_, arg1Value, (TruffleString)arg2Value_2, arg3Value, arg4Value, arg5Value, arg6Value, this.tString_regionEqualsNode_);
                        return arg5Value_3;
                    }
                    if (arg5Value instanceof TruffleString.WithMask && (arg5Value_22 = (Object)((TruffleString.WithMask)arg5Value)) != null) {
                        this.tStringMask_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doTStringMask((TruffleString)arg0Value_, arg1Value, (TruffleString)arg2Value_2, arg3Value, arg4Value, (TruffleString.WithMask)arg5Value_22, arg6Value, this.tStringMask_regionEqualsNode_);
                        return bl;
                    }
                }
            }
            if (arg2Value instanceof byte[]) {
                arg2Value_ = (byte[])arg2Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
                    this.truffleObjBytes_lengthNode_ = super.insert(InputLengthNode.create());
                    this.truffleObjBytes_charAtNode_ = super.insert(InputReadNode.create());
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean arg2Value_3 = this.doTruffleObjBytes(arg0Value, arg1Value, (byte[])arg2Value_, arg3Value, arg4Value, arg5Value, arg6Value, this.truffleObjBytes_lengthNode_, this.truffleObjBytes_charAtNode_);
                    return arg2Value_3;
                }
                if (arg5Value instanceof byte[]) {
                    arg5Value_4 = (byte[])arg5Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_4 != null) {
                        this.truffleObjBytesMask_lengthNode_ = super.insert(InputLengthNode.create());
                        this.truffleObjBytesMask_charAtNode_ = super.insert(InputReadNode.create());
                        this.state_0_ = state_0 |= 0x80;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doTruffleObjBytesMask(arg0Value, arg1Value, (byte[])arg2Value_, arg3Value, arg4Value, (byte[])arg5Value_4, arg6Value, this.truffleObjBytesMask_lengthNode_, this.truffleObjBytesMask_charAtNode_);
                        return bl;
                    }
                }
            }
            if (arg2Value instanceof String) {
                arg2Value_ = (String)arg2Value;
                if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value == null) {
                    this.truffleObjString_lengthNode_ = super.insert(InputLengthNode.create());
                    this.truffleObjString_charAtNode_ = super.insert(InputReadNode.create());
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    boolean arg5Value_4 = this.doTruffleObjString(arg0Value, arg1Value, (String)arg2Value_, arg3Value, arg4Value, arg5Value, arg6Value, this.truffleObjString_lengthNode_, this.truffleObjString_charAtNode_);
                    return arg5Value_4;
                }
                if (arg5Value instanceof String) {
                    arg5Value_4 = (String)arg5Value;
                    if (TRegexGuards.neitherByteArrayNorString(arg0Value) && arg5Value_4 != null) {
                        this.truffleObjStringMask_lengthNode_ = super.insert(InputLengthNode.create());
                        this.truffleObjStringMask_charAtNode_ = super.insert(InputReadNode.create());
                        this.state_0_ = state_0 |= 0x200;
                        lock.unlock();
                        hasLock = false;
                        boolean bl = this.doTruffleObjStringMask(arg0Value, arg1Value, (String)arg2Value_, arg3Value, arg4Value, (String)arg5Value_4, arg6Value, this.truffleObjStringMask_lengthNode_, this.truffleObjStringMask_charAtNode_);
                        return bl;
                    }
                }
            }
            if (TRegexGuards.neitherByteArrayNorString(arg0Value) && TRegexGuards.neitherByteArrayNorString(arg2Value) && arg5Value == null) {
                TruffleObjTruffleObjData s10_ = super.insert(new TruffleObjTruffleObjData());
                s10_.lengthNode1_ = s10_.insertAccessor(InputLengthNode.create());
                s10_.charAtNode1_ = s10_.insertAccessor(InputReadNode.create());
                s10_.lengthNode2_ = s10_.insertAccessor(InputLengthNode.create());
                s10_.charAtNode2_ = s10_.insertAccessor(InputReadNode.create());
                VarHandle.storeStoreFence();
                this.truffleObjTruffleObj_cache = s10_;
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doTruffleObjTruffleObj(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value, s10_.lengthNode1_, s10_.charAtNode1_, s10_.lengthNode2_, s10_.charAtNode2_);
                return bl;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value, arg5Value, arg6Value);
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

    public static InputRegionMatchesNode create() {
        return new InputRegionMatchesNodeGen();
    }

    @GeneratedBy(value=InputRegionMatchesNode.class)
    private static final class TruffleObjTruffleObjData
    extends Node {
        @Node.Child
        InputLengthNode lengthNode1_;
        @Node.Child
        InputReadNode charAtNode1_;
        @Node.Child
        InputLengthNode lengthNode2_;
        @Node.Child
        InputReadNode charAtNode2_;

        TruffleObjTruffleObjData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }
}

