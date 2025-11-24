
package com.oracle.truffle.regex.literal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.literal.LiteralRegexExecNode;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.util.TRegexGuards;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=LiteralRegexExecNode.class)
public final class LiteralRegexExecNodeGen
extends LiteralRegexExecNode {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private TruffleString.MaterializeNode tString_materializeNode_;
    @CompilerDirectives.CompilationFinal
    private ValueProfile truffleObject_inputClassProfile_;

    private LiteralRegexExecNodeGen(RegexLanguage language, RegexAST ast, LiteralRegexExecNode.LiteralRegexExecImplNode implNode) {
        super(language, ast, implNode);
    }

    @Override
    public RegexResult execute(VirtualFrame frameValue, Object arg0Value, int arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof byte[]) {
                byte[] arg0Value_ = (byte[])arg0Value;
                return this.doByteArray(arg0Value_, arg1Value);
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                return this.doString(arg0Value_, arg1Value);
            }
            if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return this.doTString(arg0Value_, arg1Value, this.tString_materializeNode_);
            }
            if ((state_0 & 8) != 0 && TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                return this.doTruffleObject(arg0Value, arg1Value, this.truffleObject_inputClassProfile_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    private RegexResult executeAndSpecialize(Object arg0Value, int arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof byte[]) {
                byte[] arg0Value_ = (byte[])arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                RegexResult regexResult = this.doByteArray(arg0Value_, arg1Value);
                return regexResult;
            }
            if (arg0Value instanceof String) {
                String arg0Value_ = (String)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                RegexResult regexResult = this.doString(arg0Value_, arg1Value);
                return regexResult;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.tString_materializeNode_ = super.insert(TruffleString.MaterializeNode.create());
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                RegexResult regexResult = this.doTString(arg0Value_, arg1Value, this.tString_materializeNode_);
                return regexResult;
            }
            if (TRegexGuards.neitherByteArrayNorString(arg0Value)) {
                this.truffleObject_inputClassProfile_ = ValueProfile.createClassProfile();
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                RegexResult regexResult = this.doTruffleObject(arg0Value, arg1Value, this.truffleObject_inputClassProfile_);
                return regexResult;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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

    public static LiteralRegexExecNode create(RegexLanguage language, RegexAST ast, LiteralRegexExecNode.LiteralRegexExecImplNode implNode) {
        return new LiteralRegexExecNodeGen(language, ast, implNode);
    }
}

