
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToBigIntNode.class)
public final class JSToBigIntNodeGen
extends JSToBigIntNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToPrimitiveNode toPrimitiveNode_;
    @Node.Child
    private JSToBigIntNode.JSToBigIntInnerConversionNode innerConversionNode_;

    private JSToBigIntNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            return this.doIt(arg0Value, this.toPrimitiveNode_, this.innerConversionNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            this.toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
            this.innerConversionNode_ = super.insert(JSToBigIntNode.JSToBigIntInnerConversionNode.create());
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            Object object = this.doIt(arg0Value, this.toPrimitiveNode_, this.innerConversionNode_);
            return object;
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
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doIt";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.toPrimitiveNode_, this.innerConversionNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToBigIntNode create() {
        return new JSToBigIntNodeGen();
    }

    @GeneratedBy(value=JSToBigIntNode.JSToBigIntInnerConversionNode.class)
    public static final class JSToBigIntInnerConversionNodeGen
    extends JSToBigIntNode.JSToBigIntInnerConversionNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSToBigIntInnerConversionNodeGen() {
        }

        @Override
        public Object execute(Object arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doBoolean(arg0Value_);
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doBigInt(arg0Value_);
            }
            if ((state_0 & 4) != 0 && JSGuards.isNumber(arg0Value)) {
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doDouble(arg0Value);
            }
            if ((state_0 & 8) != 0 && arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doSymbol(arg0Value_);
            }
            if ((state_0 & 0x10) != 0 && JSGuards.isNullOrUndefined(arg0Value)) {
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doNullOrUndefined(arg0Value);
            }
            if ((state_0 & 0x20) != 0 && arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doString(arg0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
        }

        private BigInt executeAndSpecialize(Object arg0Value) {
            int state_0 = this.state_0_;
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 1;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doBoolean(arg0Value_);
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 2;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doBigInt(arg0Value_);
            }
            if (JSGuards.isNumber(arg0Value)) {
                this.state_0_ = state_0 |= 4;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doDouble(arg0Value);
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 8;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doSymbol(arg0Value_);
            }
            if (JSGuards.isNullOrUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x10;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doNullOrUndefined(arg0Value);
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                return JSToBigIntNode.JSToBigIntInnerConversionNode.doString(arg0Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            Object[] data = new Object[7];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doBoolean";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doBigInt";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "doDouble";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "doSymbol";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "doNullOrUndefined";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "doString";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            return Introspection.Provider.create(data);
        }

        public static JSToBigIntNode.JSToBigIntInnerConversionNode create() {
            return new JSToBigIntInnerConversionNodeGen();
        }
    }
}

