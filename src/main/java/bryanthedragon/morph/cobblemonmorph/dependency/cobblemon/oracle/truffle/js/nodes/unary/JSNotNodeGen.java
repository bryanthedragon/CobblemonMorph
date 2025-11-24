
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.unary.JSNotNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSNotNode.class)
public final class JSNotNodeGen
extends JSNotNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToBooleanNode nonBoolean_toBooleanNode_;

    private JSNotNodeGen(JavaScriptNode operand) {
        super(operand);
    }

    @Override
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_);
        }
        if ((state_0 & 2) != 0) {
            return this.doNonBoolean(operandNodeValue, this.nonBoolean_toBooleanNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 2) == 0 && state_0 != 0) {
            return this.execute_boolean0(state_0, frameValue);
        }
        return this.execute_generic1(state_0, frameValue);
    }

    private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doBoolean(operandNodeValue_);
    }

    private Object execute_generic1(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 2) != 0) {
            return this.doNonBoolean(operandNodeValue_, this.nonBoolean_toBooleanNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 2) == 0 && state_0 != 0) {
            return this.executeBoolean_boolean2(state_0, frameValue);
        }
        return this.executeBoolean_generic3(state_0, frameValue);
    }

    private boolean executeBoolean_boolean2(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doBoolean(operandNodeValue_);
    }

    private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 2) != 0) {
            return this.doNonBoolean(operandNodeValue_, this.nonBoolean_toBooleanNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (operandNodeValue instanceof Boolean) {
                boolean operandNodeValue_ = (Boolean)operandNodeValue;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doBoolean(operandNodeValue_);
                return bl;
            }
            this.nonBoolean_toBooleanNode_ = super.insert(JSToBooleanNode.create());
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            boolean bl = this.doNonBoolean(operandNodeValue, this.nonBoolean_toBooleanNode_);
            return bl;
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
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doNonBoolean";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSToBooleanNode>> cached = new ArrayList<List<JSToBooleanNode>>();
            cached.add(Arrays.asList(this.nonBoolean_toBooleanNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static JSNotNode create(JavaScriptNode operand) {
        return new JSNotNodeGen(operand);
    }
}

