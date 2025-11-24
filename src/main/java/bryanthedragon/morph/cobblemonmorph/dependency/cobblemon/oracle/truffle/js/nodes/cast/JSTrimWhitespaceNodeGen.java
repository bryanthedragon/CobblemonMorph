
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSTrimWhitespaceNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSTrimWhitespaceNode.class)
public final class JSTrimWhitespaceNodeGen
extends JSTrimWhitespaceNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private TruffleString.ReadCharUTF16Node readChar;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile isFastNonWhitespace;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile isFastWhitespace;
    @Node.Child
    private TruffleString.SubstringByteIndexNode string_substringNode_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile string_startsWithWhitespaceBranch_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile string_endsWithWhitespaceBranch_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile string_isEmpty_;

    private JSTrimWhitespaceNodeGen() {
    }

    @Override
    public TruffleString executeString(TruffleString arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.stringLength(arg0Value) == 0) {
                return JSTrimWhitespaceNode.doStringZero(arg0Value);
            }
            if ((state_0 & 2) != 0 && JSGuards.stringLength(arg0Value) > 0 && !this.startsOrEndsWithWhitespace(this.readChar, arg0Value, this.isFastNonWhitespace, this.isFastWhitespace)) {
                return JSTrimWhitespaceNode.doStringNoWhitespace(arg0Value, this.readChar, this.isFastNonWhitespace, this.isFastWhitespace);
            }
            if ((state_0 & 4) != 0 && JSGuards.stringLength(arg0Value) > 0 && this.startsOrEndsWithWhitespace(this.readChar, arg0Value, this.isFastNonWhitespace, this.isFastWhitespace)) {
                return this.doString(arg0Value, this.readChar, this.isFastNonWhitespace, this.isFastWhitespace, this.string_substringNode_, this.string_startsWithWhitespaceBranch_, this.string_endsWithWhitespaceBranch_, this.string_isEmpty_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private TruffleString executeAndSpecialize(TruffleString arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object stringNoWhitespace_readRawNode__;
            int state_0 = this.state_0_;
            if (JSGuards.stringLength(arg0Value) == 0) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = JSTrimWhitespaceNode.doStringZero(arg0Value);
                return truffleString;
            }
            boolean StringNoWhitespace_duplicateFound_ = false;
            if ((state_0 & 2) != 0 && JSGuards.stringLength(arg0Value) > 0 && !this.startsOrEndsWithWhitespace(this.readChar, arg0Value, this.isFastNonWhitespace, this.isFastWhitespace)) {
                StringNoWhitespace_duplicateFound_ = true;
            }
            if (!StringNoWhitespace_duplicateFound_ && JSGuards.stringLength(arg0Value) > 0) {
                ConditionProfile stringNoWhitespace_isFastWhitespace__;
                stringNoWhitespace_readRawNode__ = super.insert(this.readChar == null ? TruffleString.ReadCharUTF16Node.create() : this.readChar);
                ConditionProfile stringNoWhitespace_isFastNonWhitespace__ = this.isFastNonWhitespace == null ? ConditionProfile.create() : this.isFastNonWhitespace;
                ConditionProfile conditionProfile = stringNoWhitespace_isFastWhitespace__ = this.isFastWhitespace == null ? ConditionProfile.create() : this.isFastWhitespace;
                if (!this.startsOrEndsWithWhitespace((TruffleString.ReadCharUTF16Node)stringNoWhitespace_readRawNode__, arg0Value, stringNoWhitespace_isFastNonWhitespace__, stringNoWhitespace_isFastWhitespace__) && (state_0 & 2) == 0) {
                    if (this.readChar == null) {
                        TruffleString.ReadCharUTF16Node stringNoWhitespace_readRawNode___check = (TruffleString.ReadCharUTF16Node)super.insert(stringNoWhitespace_readRawNode__);
                        if (stringNoWhitespace_readRawNode___check == null) {
                            throw new AssertionError((Object)"Specialization 'doStringNoWhitespace(TruffleString, ReadCharUTF16Node, ConditionProfile, ConditionProfile)' contains a shared cache with name 'readRawNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.readChar = stringNoWhitespace_readRawNode___check;
                    }
                    if (this.isFastNonWhitespace == null) {
                        ConditionProfile stringNoWhitespace_isFastNonWhitespace___check = stringNoWhitespace_isFastNonWhitespace__;
                        if (stringNoWhitespace_isFastNonWhitespace___check == null) {
                            throw new AssertionError((Object)"Specialization 'doStringNoWhitespace(TruffleString, ReadCharUTF16Node, ConditionProfile, ConditionProfile)' contains a shared cache with name 'isFastNonWhitespace' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isFastNonWhitespace = stringNoWhitespace_isFastNonWhitespace___check;
                    }
                    if (this.isFastWhitespace == null) {
                        ConditionProfile stringNoWhitespace_isFastWhitespace___check = stringNoWhitespace_isFastWhitespace__;
                        if (stringNoWhitespace_isFastWhitespace___check == null) {
                            throw new AssertionError((Object)"Specialization 'doStringNoWhitespace(TruffleString, ReadCharUTF16Node, ConditionProfile, ConditionProfile)' contains a shared cache with name 'isFastWhitespace' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isFastWhitespace = stringNoWhitespace_isFastWhitespace___check;
                    }
                    this.state_0_ = state_0 |= 2;
                    StringNoWhitespace_duplicateFound_ = true;
                }
            }
            if (StringNoWhitespace_duplicateFound_) {
                lock.unlock();
                hasLock = false;
                stringNoWhitespace_readRawNode__ = JSTrimWhitespaceNode.doStringNoWhitespace(arg0Value, this.readChar, this.isFastNonWhitespace, this.isFastWhitespace);
                return stringNoWhitespace_readRawNode__;
            }
            boolean String_duplicateFound_ = false;
            if ((state_0 & 4) != 0 && JSGuards.stringLength(arg0Value) > 0 && this.startsOrEndsWithWhitespace(this.readChar, arg0Value, this.isFastNonWhitespace, this.isFastWhitespace)) {
                String_duplicateFound_ = true;
            }
            if (!String_duplicateFound_ && JSGuards.stringLength(arg0Value) > 0) {
                ConditionProfile string_isFastWhitespace__;
                TruffleString.ReadCharUTF16Node string_readRawNode__ = super.insert(this.readChar == null ? TruffleString.ReadCharUTF16Node.create() : this.readChar);
                ConditionProfile string_isFastNonWhitespace__ = this.isFastNonWhitespace == null ? ConditionProfile.create() : this.isFastNonWhitespace;
                ConditionProfile conditionProfile = string_isFastWhitespace__ = this.isFastWhitespace == null ? ConditionProfile.create() : this.isFastWhitespace;
                if (this.startsOrEndsWithWhitespace(string_readRawNode__, arg0Value, string_isFastNonWhitespace__, string_isFastWhitespace__) && (state_0 & 4) == 0) {
                    if (this.readChar == null) {
                        TruffleString.ReadCharUTF16Node string_readRawNode___check = super.insert(string_readRawNode__);
                        if (string_readRawNode___check == null) {
                            throw new AssertionError((Object)"Specialization 'doString(TruffleString, ReadCharUTF16Node, ConditionProfile, ConditionProfile, SubstringByteIndexNode, BranchProfile, BranchProfile, ConditionProfile)' contains a shared cache with name 'readRawNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.readChar = string_readRawNode___check;
                    }
                    if (this.isFastNonWhitespace == null) {
                        ConditionProfile string_isFastNonWhitespace___check = string_isFastNonWhitespace__;
                        if (string_isFastNonWhitespace___check == null) {
                            throw new AssertionError((Object)"Specialization 'doString(TruffleString, ReadCharUTF16Node, ConditionProfile, ConditionProfile, SubstringByteIndexNode, BranchProfile, BranchProfile, ConditionProfile)' contains a shared cache with name 'isFastNonWhitespace' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isFastNonWhitespace = string_isFastNonWhitespace___check;
                    }
                    if (this.isFastWhitespace == null) {
                        ConditionProfile string_isFastWhitespace___check = string_isFastWhitespace__;
                        if (string_isFastWhitespace___check == null) {
                            throw new AssertionError((Object)"Specialization 'doString(TruffleString, ReadCharUTF16Node, ConditionProfile, ConditionProfile, SubstringByteIndexNode, BranchProfile, BranchProfile, ConditionProfile)' contains a shared cache with name 'isFastWhitespace' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isFastWhitespace = string_isFastWhitespace___check;
                    }
                    this.string_substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
                    this.string_startsWithWhitespaceBranch_ = BranchProfile.create();
                    this.string_endsWithWhitespaceBranch_ = BranchProfile.create();
                    this.string_isEmpty_ = ConditionProfile.create();
                    this.state_0_ = state_0 |= 4;
                    String_duplicateFound_ = true;
                }
            }
            if (String_duplicateFound_) {
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doString(arg0Value, this.readChar, this.isFastNonWhitespace, this.isFastWhitespace, this.string_substringNode_, this.string_startsWithWhitespaceBranch_, this.string_endsWithWhitespaceBranch_, this.string_isEmpty_);
                return truffleString;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doStringZero";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doStringNoWhitespace";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.readChar, this.isFastNonWhitespace, this.isFastWhitespace));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.readChar, this.isFastNonWhitespace, this.isFastWhitespace, this.string_substringNode_, this.string_startsWithWhitespaceBranch_, this.string_endsWithWhitespaceBranch_, this.string_isEmpty_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSTrimWhitespaceNode create() {
        return new JSTrimWhitespaceNodeGen();
    }
}

