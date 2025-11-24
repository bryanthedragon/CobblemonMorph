
package com.oracle.truffle.host;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.host.HostAccessor;
import com.oracle.truffle.host.HostContext;

abstract class GuestToHostRootNode
extends RootNode {
    protected static final int ARGUMENT_OFFSET = 2;
    private final String boundaryName;

    protected GuestToHostRootNode(Class<?> targetType, String methodName) {
        super(null);
        this.boundaryName = targetType.getName() + "." + methodName;
    }

    @Override
    protected boolean isInstrumentable() {
        return false;
    }

    @Override
    public boolean isCloningAllowed() {
        return false;
    }

    @Override
    public final String getName() {
        return this.boundaryName;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] arguments = frame.getArguments();
        try {
            return this.executeImpl(arguments[1], arguments);
        }
        catch (InteropException e) {
            throw GuestToHostRootNode.silenceException(RuntimeException.class, e);
        }
        catch (Throwable e) {
            throw ((HostContext)arguments[0]).hostToGuestException(e);
        }
    }

    static <E extends Throwable> RuntimeException silenceException(Class<E> type, Throwable ex) throws E {
        throw ex;
    }

    protected abstract Object executeImpl(Object var1, Object[] var2) throws InteropException;

    static Object guestToHostCall(Node node, CallTarget target, Object ... arguments) {
        Node encapsulatingNode = node.isAdoptable() ? node : EncapsulatingNodeReference.getCurrent().get();
        return HostAccessor.RUNTIME.callInlined(encapsulatingNode, target, arguments);
    }
}

