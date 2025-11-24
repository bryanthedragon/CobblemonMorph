
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.impl.DefaultRuntimeAccessor;
import com.oracle.truffle.api.impl.DefaultTruffleRuntime;
import com.oracle.truffle.api.impl.FrameWithoutBoxing;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

public final class DefaultCallTarget
implements RootCallTarget {
    public static final String CALL_BOUNDARY_METHOD = "callDirectOrIndirect";
    private final RootNode rootNode;
    private volatile boolean initialized;
    private volatile boolean loaded;

    DefaultCallTarget(RootNode function) {
        this.rootNode = function;
        this.rootNode.adoptChildren();
    }

    public String toString() {
        return this.rootNode.toString();
    }

    @Override
    public RootNode getRootNode() {
        return this.rootNode;
    }

    Object callDirectOrIndirect(Node callNode, Object ... args) {
        if (!this.initialized) {
            this.initialize();
        }
        FrameWithoutBoxing frame = new FrameWithoutBoxing(this.rootNode.getFrameDescriptor(), args);
        DefaultTruffleRuntime.DefaultFrameInstance callerFrame = DefaultTruffleRuntime.getRuntime().pushFrame(frame, this, callNode);
        try {
            Object toRet = this.rootNode.execute(frame);
            TruffleSafepoint.poll(this.rootNode);
            Object object = toRet;
            return object;
        }
        catch (Throwable t) {
            DefaultRuntimeAccessor.LANGUAGE.onThrowable(callNode, this, t, frame);
            throw t;
        }
        finally {
            DefaultTruffleRuntime.getRuntime().popFrame(callerFrame);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object call(Object ... args) {
        EncapsulatingNodeReference encapsulating = EncapsulatingNodeReference.getCurrent();
        Node parent = encapsulating.set(null);
        try {
            Object object = this.callDirectOrIndirect(parent, args);
            return object;
        }
        finally {
            encapsulating.set(parent);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void initialize() {
        DefaultCallTarget defaultCallTarget = this;
        synchronized (defaultCallTarget) {
            if (!this.initialized) {
                DefaultRuntimeAccessor.INSTRUMENT.onFirstExecution(this.getRootNode(), true);
                this.initialized = true;
            }
        }
    }

    boolean isLoaded() {
        return this.loaded;
    }

    void setLoaded() {
        this.loaded = true;
    }
}

