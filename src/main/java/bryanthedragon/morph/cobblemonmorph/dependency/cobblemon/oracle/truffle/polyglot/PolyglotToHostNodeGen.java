
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

@GeneratedBy(value=PolyglotToHostNode.class)
final class PolyglotToHostNodeGen
extends PolyglotToHostNode {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private AbstractPolyglotImpl.AbstractHostLanguageService host_;
    @Node.Child
    private Node toHostNode_;

    private PolyglotToHostNodeGen() {
    }

    @Override
    Object execute(PolyglotLanguageContext arg0Value, Object arg1Value, Class<?> arg2Value, Type arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            return PolyglotToHostNode.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, this.host_, this.toHostNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Class<?> arg2Value, Type arg3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            this.host_ = arg0Value.context.engine.host;
            this.toHostNode_ = super.insert(PolyglotToHostNode.createToHostNode(this.host_));
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            Object object = PolyglotToHostNode.doDefault(arg0Value, arg1Value, arg2Value, arg3Value, this.host_, this.toHostNode_);
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

    public static PolyglotToHostNode create() {
        return new PolyglotToHostNodeGen();
    }
}

