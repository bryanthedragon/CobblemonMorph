
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(shortName="try-finally")
public class TryFinallyNode
extends StatementNode
implements ResumableNode.WithObjectState {
    @Node.Child
    private JavaScriptNode tryBlock;
    @Node.Child
    private JavaScriptNode finallyBlock;

    TryFinallyNode(JavaScriptNode tryBlock, JavaScriptNode finallyBlock) {
        this.tryBlock = tryBlock;
        this.finallyBlock = finallyBlock;
    }

    public static JavaScriptNode create(JavaScriptNode tryBlock, JavaScriptNode finallyBlock) {
        return new TryFinallyNode(tryBlock, finallyBlock);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return TryFinallyNode.create(TryFinallyNode.cloneUninitialized(this.tryBlock, materializedTags), TryFinallyNode.cloneUninitialized(this.finallyBlock, materializedTags));
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Throwable throwable;
        Object result = null;
        try {
            result = this.tryBlock.execute(frame);
            throwable = null;
        }
        catch (ControlFlowException cfe) {
            throwable = cfe;
        }
        catch (AbstractTruffleException ex) {
            throwable = ex;
        }
        catch (StackOverflowError ste) {
            throwable = ste;
        }
        this.finallyBlock.executeVoid(frame);
        if (throwable != null) {
            throw JSRuntime.rethrow(throwable);
        }
        assert (result != null);
        return result;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        Throwable throwable;
        try {
            this.tryBlock.executeVoid(frame);
            throwable = null;
        }
        catch (ControlFlowException cfe) {
            throwable = cfe;
        }
        catch (AbstractTruffleException ex) {
            throwable = ex;
        }
        catch (StackOverflowError ste) {
            throwable = ste;
        }
        this.finallyBlock.executeVoid(frame);
        if (throwable != null) {
            throw JSRuntime.rethrow(throwable);
        }
    }

    @Override
    public Object resume(VirtualFrame frame, int stateSlot) {
        Object result = EMPTY;
        Throwable throwable = null;
        Object state = this.getStateAndReset(frame, stateSlot);
        if (state == Undefined.instance) {
            try {
                result = this.tryBlock.execute(frame);
            }
            catch (YieldException e) {
                throw e;
            }
            catch (ControlFlowException cfe) {
                throwable = cfe;
            }
            catch (AbstractTruffleException ex) {
                throwable = ex;
            }
            catch (StackOverflowError ste) {
                throwable = ste;
            }
        } else if (state instanceof Throwable) {
            throwable = (Throwable)state;
        }
        try {
            this.finallyBlock.execute(frame);
        }
        catch (YieldException e) {
            this.setState(frame, stateSlot, throwable);
            throw e;
        }
        if (throwable != null) {
            throw JSRuntime.rethrow(throwable);
        }
        return result;
    }
}

