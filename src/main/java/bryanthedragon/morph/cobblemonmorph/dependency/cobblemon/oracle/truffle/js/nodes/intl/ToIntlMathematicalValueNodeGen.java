
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.intl.ToIntlMathematicalValue;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToIntlMathematicalValue.class)
public final class ToIntlMathematicalValueNodeGen
extends ToIntlMathematicalValue
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSToPrimitiveNode generic_toPrimitiveNode_;
    @Node.Child
    private ToIntlMathematicalValue generic_nestedToIntlMVNode_;

    private ToIntlMathematicalValueNodeGen(boolean partOfRange) {
        super(partOfRange);
    }

    @Override
    public Number executeNumber(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF00) >>> 8, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0xF00) >>> 8, arg0Value);
            return this.doDouble(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.doBoolean(arg0Value_);
        }
        if ((state_0 & 0x30) != 0) {
            if ((state_0 & 0x10) != 0 && JSGuards.isUndefined(arg0Value)) {
                return this.doUndefined(arg0Value);
            }
            if ((state_0 & 0x20) != 0 && JSGuards.isJSNull(arg0Value)) {
                return this.doNull(arg0Value);
            }
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x80) != 0) {
            return this.doGeneric(arg0Value, this.generic_toPrimitiveNode_, this.generic_nestedToIntlMVNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Number executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 8;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Number number = this.doDouble(arg0Value_);
                return number;
            }
            if ((exclude & 2) == 0 && arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Number number = this.doBigInt(arg0Value_);
                return number;
            }
            if ((exclude & 4) == 0 && arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Number number = this.doString(arg0Value_);
                return number;
            }
            if ((exclude & 8) == 0 && arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Number number = this.doBoolean(arg0Value_);
                return number;
            }
            if ((exclude & 0x10) == 0 && JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Number arg0Value_ = this.doUndefined(arg0Value);
                return arg0Value_;
            }
            if ((exclude & 0x20) == 0 && JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Number arg0Value_ = this.doNull(arg0Value);
                return arg0Value_;
            }
            if ((exclude & 0x40) == 0 && arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Number number = this.doSymbol(arg0Value_);
                return number;
            }
            this.generic_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
            this.generic_nestedToIntlMVNode_ = super.insert(ToIntlMathematicalValue.create(this.partOfRange));
            this.exclude_ = exclude |= 0x7F;
            state_0 &= 0xFFFFFF80;
            this.state_0_ = state_0 |= 0x80;
            lock.unlock();
            hasLock = false;
            Number number = this.doGeneric(arg0Value, this.generic_toPrimitiveNode_, this.generic_nestedToIntlMVNode_);
            return number;
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
        if ((state_0 & 0xFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFF & (state_0 & 0xFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[9];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[1] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[2] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 4) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : ((exclude & 8) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[4] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x10) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[5] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x20) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[6] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : ((exclude & 0x40) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[7] = s;
        s = new Object[3];
        s[0] = "doGeneric";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.generic_toPrimitiveNode_, this.generic_nestedToIntlMVNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[8] = s;
        return Introspection.Provider.create(data);
    }

    public static ToIntlMathematicalValue create(boolean partOfRange) {
        return new ToIntlMathematicalValueNodeGen(partOfRange);
    }
}

