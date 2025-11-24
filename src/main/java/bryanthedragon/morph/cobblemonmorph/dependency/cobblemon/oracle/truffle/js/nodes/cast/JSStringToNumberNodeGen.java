
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSStringToNumberNode.class)
public final class JSStringToNumberNodeGen
extends JSStringToNumberNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile infinity_endsWithInfinity_;
    @Node.Child
    private TruffleString.RegionEqualByteIndexNode infinity_regionEqualsNode_;

    private JSStringToNumberNodeGen() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected double executeNoTrim(TruffleString arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.stringLength(arg0Value) == 0) {
                return this.doLengthIsZero(arg0Value);
            }
            if ((state_0 & 2) != 0 && this.startsWithI(arg0Value)) {
                return this.doInfinity(arg0Value, this.infinity_endsWithInfinity_, this.infinity_regionEqualsNode_);
            }
            if (!((state_0 & 4) == 0 || JSGuards.stringLength(arg0Value) <= 0 || this.startsWithI(arg0Value) || this.startsWithValidDouble(arg0Value) || this.isHex(arg0Value) || this.isOctal(arg0Value) || this.isBinary(arg0Value))) {
                return this.doNaN(arg0Value);
            }
            if ((state_0 & 8) != 0 && this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) <= 15) {
                return this.doHexSafe(arg0Value);
            }
            if ((state_0 & 0x10) != 0 && this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) > 15) {
                return this.doHex(arg0Value);
            }
            if ((state_0 & 0x20) != 0 && this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) <= 19) {
                return this.doOctalSafe(arg0Value);
            }
            if ((state_0 & 0x40) != 0 && this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) > 19) {
                return this.doOctal(arg0Value);
            }
            if ((state_0 & 0x80) != 0 && this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) <= 55) {
                return this.doBinarySafe(arg0Value);
            }
            if ((state_0 & 0x100) != 0 && this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) > 55) {
                return this.doBinary(arg0Value);
            }
            if ((state_0 & 0x200) != 0 && JSGuards.stringLength(arg0Value) > 0 && JSGuards.stringLength(arg0Value) <= 9 && this.allDigits(arg0Value, 9)) {
                return this.doSmallPosInt(arg0Value);
            }
            if ((state_0 & 0x400) != 0 && JSGuards.stringLength(arg0Value) > 0 && JSGuards.stringLength(arg0Value) <= 17 && this.startsWithValidInt(arg0Value)) {
                try {
                    return this.doInteger(arg0Value);
                }
                catch (SlowPathException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    Lock lock = this.getLock();
                    lock.lock();
                    try {
                        this.exclude_ |= 2;
                        this.state_0_ &= 0xFFFFFBFF;
                    }
                    finally {
                        lock.unlock();
                    }
                    return this.executeAndSpecialize(arg0Value);
                }
            }
            if ((state_0 & 0x800) != 0 && JSGuards.stringLength(arg0Value) > 0 && this.startsWithValidDouble(arg0Value)) {
                return this.doDouble(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     */
    private double executeAndSpecialize(TruffleString arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.stringLength(arg0Value) == 0) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                double d = this.doLengthIsZero(arg0Value);
                return d;
            }
            if (this.startsWithI(arg0Value)) {
                this.infinity_endsWithInfinity_ = ConditionProfile.create();
                this.infinity_regionEqualsNode_ = super.insert(TruffleString.RegionEqualByteIndexNode.create());
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                double d = this.doInfinity(arg0Value, this.infinity_endsWithInfinity_, this.infinity_regionEqualsNode_);
                return d;
            }
            if (!(JSGuards.stringLength(arg0Value) <= 0 || this.startsWithI(arg0Value) || this.startsWithValidDouble(arg0Value) || this.isHex(arg0Value) || this.isOctal(arg0Value) || this.isBinary(arg0Value))) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                double d = this.doNaN(arg0Value);
                return d;
            }
            if (this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) <= 15) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                double d = this.doHexSafe(arg0Value);
                return d;
            }
            if (this.isHex(arg0Value) && JSGuards.stringLength(arg0Value) > 15) {
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                double d = this.doHex(arg0Value);
                return d;
            }
            if (this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) <= 19) {
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                double d = this.doOctalSafe(arg0Value);
                return d;
            }
            if (this.isOctal(arg0Value) && JSGuards.stringLength(arg0Value) > 19) {
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                double d = this.doOctal(arg0Value);
                return d;
            }
            if (this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) <= 55) {
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                double d = this.doBinarySafe(arg0Value);
                return d;
            }
            if (this.isBinary(arg0Value) && JSGuards.stringLength(arg0Value) > 55) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                double d = this.doBinary(arg0Value);
                return d;
            }
            if ((exclude & 1) == 0 && JSGuards.stringLength(arg0Value) > 0 && JSGuards.stringLength(arg0Value) <= 9 && this.allDigits(arg0Value, 9)) {
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                double d = this.doSmallPosInt(arg0Value);
                return d;
            }
            if ((exclude & 2) == 0 && JSGuards.stringLength(arg0Value) > 0 && JSGuards.stringLength(arg0Value) <= 17 && this.startsWithValidInt(arg0Value)) {
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFDFF;
                this.state_0_ = state_0 |= 0x400;
                try {
                    lock.unlock();
                    hasLock = false;
                    double d = this.doInteger(arg0Value);
                    return d;
                }
                catch (SlowPathException ex) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    lock.lock();
                    try {
                        this.exclude_ |= 2;
                        this.state_0_ &= 0xFFFFFBFF;
                    }
                    finally {
                        lock.unlock();
                    }
                    double d = this.executeAndSpecialize(arg0Value);
                    if (hasLock) {
                        lock.unlock();
                    }
                    return d;
                }
            }
            if (JSGuards.stringLength(arg0Value) > 0 && this.startsWithValidDouble(arg0Value)) {
                this.exclude_ = exclude |= 3;
                state_0 &= 0xFFFFF9FF;
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                double d = this.doDouble(arg0Value);
                return d;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            {
                catch (Throwable throwable) {
                    throw throwable;
                }
            }
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
        Object[] data = new Object[13];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doLengthIsZero";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doInfinity";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.infinity_endsWithInfinity_, this.infinity_regionEqualsNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doNaN";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doHexSafe";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doHex";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doOctalSafe";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doOctal";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doBinarySafe";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doBinary";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doSmallPosInt";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[10] = s;
        s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[11] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        return Introspection.Provider.create(data);
    }

    public static JSStringToNumberNode create() {
        return new JSStringToNumberNodeGen();
    }
}

