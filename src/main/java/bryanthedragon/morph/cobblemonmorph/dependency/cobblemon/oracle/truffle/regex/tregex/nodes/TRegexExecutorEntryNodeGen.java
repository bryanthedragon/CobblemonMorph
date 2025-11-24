/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorEntryNode;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TRegexExecutorEntryNode.class)
public final class TRegexExecutorEntryNodeGen
extends TRegexExecutorEntryNode {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private DirectCallNode byteArray_callNode_;
    @Node.Child
    private DirectCallNode stringCompact_callNode_;
    @Node.Child
    private DirectCallNode stringNonCompact_callNode_;
    @Node.Child
    private TStringData tString_cache;
    @CompilerDirectives.CompilationFinal
    private ValueProfile truffleObject_inputClassProfile_;
    @Node.Child
    private DirectCallNode truffleObject_callNode_;

    private TRegexExecutorEntryNodeGen(RegexLanguage language, TRegexExecutorNode executor) {
        super(language, executor);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue, Object arg0Value, int arg1Value, int arg2Value, int arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            Object arg0Value_;
            if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
                byte[] arg0Value_2 = (byte[])arg0Value;
                return this.doByteArray(frameValue, arg0Value_2, arg1Value, arg2Value, arg3Value, this.byteArray_callNode_);
            }
            if ((state_0 & 6) != 0 && arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if ((state_0 & 2) != 0 && TRegexExecutorEntryNode.isCompactString((String)arg0Value_)) {
                    return this.doStringCompact(frameValue, (String)arg0Value_, arg1Value, arg2Value, arg3Value, this.stringCompact_callNode_);
                }
                if ((state_0 & 4) != 0 && !TRegexExecutorEntryNode.isCompactString((String)arg0Value_)) {
                    return this.doStringNonCompact(frameValue, (String)arg0Value_, arg1Value, arg2Value, arg3Value, this.stringNonCompact_callNode_);
                }
            }
            if ((state_0 & 8) != 0 && arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                TStringData s3_ = this.tString_cache;
                while (s3_ != null) {
                    if (s3_.codeRangeEqualsNode_.execute((AbstractTruffleString)arg0Value_, s3_.cachedCodeRange_)) {
                        return this.doTString(frameValue, (TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value, s3_.materializeNode_, s3_.codeRangeNode_, s3_.codeRangeEqualsNode_, s3_.cachedCodeRange_, s3_.callNode_);
                    }
                    s3_ = s3_.next_;
                }
            }
            if ((state_0 & 0x10) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                return this.doTruffleObject(frameValue, arg0Value, arg1Value, arg2Value, arg3Value, this.truffleObject_inputClassProfile_, this.truffleObject_callNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue, arg0Value, arg1Value, arg2Value, arg3Value);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object arg0Value, int arg1Value, int arg2Value, int arg3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object arg0Value_;
            int state_0 = this.state_0_;
            if (arg0Value instanceof byte[]) {
                byte[] arg0Value_2 = (byte[])arg0Value;
                this.byteArray_callNode_ = super.insert(this.createCallTarget(TruffleString.CodeRange.BROKEN, false));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.doByteArray(frameValue, arg0Value_2, arg1Value, arg2Value, arg3Value, this.byteArray_callNode_);
                return object;
            }
            if (arg0Value instanceof String) {
                arg0Value_ = (String)arg0Value;
                if (TRegexExecutorEntryNode.isCompactString((String)arg0Value_)) {
                    this.stringCompact_callNode_ = super.insert(this.createCallTarget(TruffleString.CodeRange.LATIN_1, false));
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doStringCompact(frameValue, (String)arg0Value_, arg1Value, arg2Value, arg3Value, this.stringCompact_callNode_);
                    return object;
                }
                if (!TRegexExecutorEntryNode.isCompactString((String)arg0Value_)) {
                    this.stringNonCompact_callNode_ = super.insert(this.createCallTarget(TruffleString.CodeRange.BROKEN, false));
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doStringNonCompact(frameValue, (String)arg0Value_, arg1Value, arg2Value, arg3Value, this.stringNonCompact_callNode_);
                    return object;
                }
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString.GetCodeRangeNode codeRangeNode__;
                TruffleString.CodeRange cachedCodeRange__;
                TruffleString.CodeRangeEqualsNode codeRangeEqualsNode__;
                arg0Value_ = (TruffleString)arg0Value;
                int count3_ = 0;
                TStringData s3_ = this.tString_cache;
                if ((state_0 & 8) != 0) {
                    while (s3_ != null && !s3_.codeRangeEqualsNode_.execute((AbstractTruffleString)arg0Value_, s3_.cachedCodeRange_)) {
                        s3_ = s3_.next_;
                        ++count3_;
                    }
                }
                if (s3_ == null && (codeRangeEqualsNode__ = super.insert(TruffleString.CodeRangeEqualsNode.create())).execute((AbstractTruffleString)arg0Value_, cachedCodeRange__ = (codeRangeNode__ = super.insert(TruffleString.GetCodeRangeNode.create())).execute((AbstractTruffleString)arg0Value_, this.getExecutor().getEncoding().getTStringEncoding())) && count3_ < 5) {
                    s3_ = super.insert(new TStringData(this.tString_cache));
                    s3_.materializeNode_ = s3_.insertAccessor(TruffleString.MaterializeNode.create());
                    s3_.codeRangeNode_ = s3_.insertAccessor(codeRangeNode__);
                    s3_.codeRangeEqualsNode_ = s3_.insertAccessor(codeRangeEqualsNode__);
                    s3_.cachedCodeRange_ = cachedCodeRange__;
                    s3_.callNode_ = s3_.insertAccessor(this.createCallTarget(cachedCodeRange__, true));
                    VarHandle.storeStoreFence();
                    this.tString_cache = s3_;
                    this.state_0_ = state_0 |= 8;
                }
                if (s3_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doTString(frameValue, (TruffleString)arg0Value_, arg1Value, arg2Value, arg3Value, s3_.materializeNode_, s3_.codeRangeNode_, s3_.codeRangeEqualsNode_, s3_.cachedCodeRange_, s3_.callNode_);
                    return object;
                }
            }
            if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                this.truffleObject_inputClassProfile_ = ValueProfile.createClassProfile();
                this.truffleObject_callNode_ = super.insert(this.createCallTarget(TruffleString.CodeRange.BROKEN, false));
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object object = this.doTruffleObject(frameValue, arg0Value, arg1Value, arg2Value, arg3Value, this.truffleObject_inputClassProfile_, this.truffleObject_callNode_);
                return object;
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
        TStringData s3_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s3_ = this.tString_cache) == null || s3_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static TRegexExecutorEntryNode create(RegexLanguage language, TRegexExecutorNode executor) {
        return new TRegexExecutorEntryNodeGen(language, executor);
    }

    @GeneratedBy(value=TRegexExecutorEntryNode.class)
    private static final class TStringData
    extends Node {
        @Node.Child
        TStringData next_;
        @Node.Child
        TruffleString.MaterializeNode materializeNode_;
        @Node.Child
        TruffleString.GetCodeRangeNode codeRangeNode_;
        @Node.Child
        TruffleString.CodeRangeEqualsNode codeRangeEqualsNode_;
        @CompilerDirectives.CompilationFinal
        TruffleString.CodeRange cachedCodeRange_;
        @Node.Child
        DirectCallNode callNode_;

        TStringData(TStringData next_) {
            this.next_ = next_;
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

