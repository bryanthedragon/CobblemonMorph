/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Module
 *  java.lang.ModuleLayer
 */
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleRuntime;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.impl.DefaultAssumption;
import com.oracle.truffle.api.impl.DefaultDirectCallNode;
import com.oracle.truffle.api.impl.DefaultIndirectCallNode;
import com.oracle.truffle.api.impl.DefaultLoopNode;
import com.oracle.truffle.api.impl.DefaultTVMCI;
import com.oracle.truffle.api.impl.FrameWithoutBoxing;
import com.oracle.truffle.api.impl.ReadOnlyFrame;
import com.oracle.truffle.api.impl.TVMCI;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.nodes.RootNode;
import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public final class DefaultTruffleRuntime
implements TruffleRuntime {
    private final ThreadLocal<DefaultFrameInstance> stackTraces = new ThreadLocal();
    private final DefaultTVMCI tvmci = new DefaultTVMCI();
    private final TVMCI.Test<Closeable, CallTarget> testTvmci = new TVMCI.Test<Closeable, CallTarget>(){

        @Override
        protected Closeable createTestContext(String testName) {
            return null;
        }

        @Override
        public CallTarget createTestCallTarget(Closeable testContext, RootNode testNode) {
            return testNode.getCallTarget();
        }

        @Override
        public void finishWarmup(Closeable testContext, CallTarget callTarget) {
        }
    };

    static DefaultTruffleRuntime getRuntime() {
        return (DefaultTruffleRuntime)Truffle.getRuntime();
    }

    public DefaultTVMCI getTvmci() {
        return this.tvmci;
    }

    @Override
    public String getName() {
        return "Interpreted";
    }

    @Override
    public DirectCallNode createDirectCallNode(CallTarget target) {
        Objects.requireNonNull(target);
        return new DefaultDirectCallNode(target);
    }

    @Override
    public IndirectCallNode createIndirectCallNode() {
        return new DefaultIndirectCallNode();
    }

    @Override
    public VirtualFrame createVirtualFrame(Object[] arguments, FrameDescriptor frameDescriptor) {
        return new FrameWithoutBoxing(frameDescriptor, arguments);
    }

    @Override
    public MaterializedFrame createMaterializedFrame(Object[] arguments) {
        return this.createMaterializedFrame(arguments, new FrameDescriptor());
    }

    @Override
    public MaterializedFrame createMaterializedFrame(Object[] arguments, FrameDescriptor frameDescriptor) {
        return new FrameWithoutBoxing(frameDescriptor, arguments);
    }

    @Override
    public Assumption createAssumption() {
        return this.createAssumption(null);
    }

    @Override
    public Assumption createAssumption(String name) {
        return new DefaultAssumption(name);
    }

    @Override
    public <T> T iterateFrames(FrameInstanceVisitor<T> visitor) {
        return this.iterateFrames(visitor, 0);
    }

    @Override
    public <T> T iterateFrames(FrameInstanceVisitor<T> visitor, int skipFrames) {
        if (skipFrames < 0) {
            throw new IllegalArgumentException("The skipFrames parameter must be >= 0.");
        }
        T result = null;
        DefaultFrameInstance frameInstance = this.getThreadLocalStackTrace();
        int skipCounter = skipFrames;
        while (frameInstance != null) {
            if (skipCounter <= 0 && (result = (T)visitor.visitFrame(frameInstance)) != null) {
                return result;
            }
            frameInstance = frameInstance.callerFrame;
            --skipCounter;
        }
        return result;
    }

    private DefaultFrameInstance getThreadLocalStackTrace() {
        return this.stackTraces.get();
    }

    private void setThreadLocalStackTrace(DefaultFrameInstance topFrame) {
        this.stackTraces.set(topFrame);
    }

    DefaultFrameInstance pushFrame(VirtualFrame frame, CallTarget target) {
        DefaultFrameInstance callerFrame = this.getThreadLocalStackTrace();
        this.setThreadLocalStackTrace(new DefaultFrameInstance(frame, target, null, callerFrame));
        return callerFrame;
    }

    DefaultFrameInstance pushFrame(VirtualFrame frame, CallTarget target, Node parentCallNode) {
        DefaultFrameInstance callerFrame = this.getThreadLocalStackTrace();
        DefaultFrameInstance callerFrameWithCallNode = callerFrame != null ? callerFrame.withCallNode(parentCallNode) : callerFrame;
        this.setThreadLocalStackTrace(new DefaultFrameInstance(frame, target, null, callerFrameWithCallNode));
        return callerFrame;
    }

    void popFrame(DefaultFrameInstance callerFrame) {
        this.setThreadLocalStackTrace(callerFrame);
    }

    @Override
    public <T> T getCapability(Class<T> capability) {
        if (capability == TVMCI.Test.class) {
            return capability.cast(this.testTvmci);
        }
        if (capability == TVMCI.class) {
            return capability.cast(this.tvmci);
        }
        Iterator<T> it = Loader.load(capability).iterator();
        try {
            return it.hasNext() ? (T)it.next() : null;
        }
        catch (ServiceConfigurationError e) {
            return null;
        }
    }

    @Override
    public void notifyTransferToInterpreter() {
    }

    @Override
    public LoopNode createLoopNode(RepeatingNode repeating) {
        if (!(repeating instanceof Node)) {
            throw new IllegalArgumentException("Repeating node must be of type Node.");
        }
        return new DefaultLoopNode(repeating);
    }

    @Override
    public boolean isProfilingEnabled() {
        return false;
    }

    public void markFrameMaterializeCalled(FrameDescriptor descriptor) {
    }

    static final class DefaultFrameInstance
    implements FrameInstance {
        private final CallTarget target;
        private final VirtualFrame frame;
        private final Node callNode;
        private final DefaultFrameInstance callerFrame;

        DefaultFrameInstance(VirtualFrame frame, CallTarget target, Node callNode, DefaultFrameInstance callerFrame) {
            this.target = target;
            this.frame = frame;
            this.callNode = callNode;
            this.callerFrame = callerFrame;
        }

        @Override
        public Frame getFrame(FrameInstance.FrameAccess access) {
            VirtualFrame localFrame = this.frame;
            switch (access) {
                case READ_ONLY: {
                    return new ReadOnlyFrame(localFrame);
                }
                case READ_WRITE: {
                    return localFrame;
                }
                case MATERIALIZE: {
                    return localFrame.materialize();
                }
            }
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        public boolean isVirtualFrame() {
            return false;
        }

        @Override
        public CallTarget getCallTarget() {
            return this.target;
        }

        @Override
        public Node getCallNode() {
            return this.callNode;
        }

        DefaultFrameInstance withCallNode(Node otherCallNode) {
            return new DefaultFrameInstance(this.frame, this.target, otherCallNode, this.callerFrame);
        }
    }

    private static final class Loader {
        private static final Method LOAD_METHOD;

        private Loader() {
        }

        static <S> Iterable<S> load(Class<S> service2) {
            Module truffleModule = DefaultTruffleRuntime.class.getModule();
            if (!truffleModule.canUse(service2)) {
                truffleModule.addUses(service2);
            }
            if (LOAD_METHOD != null) {
                try {
                    return (Iterable)LOAD_METHOD.invoke(null, service2);
                }
                catch (Exception e) {
                    throw new InternalError(e);
                }
            }
            ModuleLayer moduleLayer = truffleModule.getLayer();
            ServiceLoader<S> services = moduleLayer != null ? ServiceLoader.load((ModuleLayer)moduleLayer, service2) : ServiceLoader.load(service2, DefaultTruffleRuntime.class.getClassLoader());
            if (!services.iterator().hasNext()) {
                services = ServiceLoader.load(service2);
            }
            return services;
        }

        static {
            Method loadMethod = null;
            try {
                Class<?> servicesClass = Class.forName("jdk.vm.ci.services.Services");
                loadMethod = servicesClass.getMethod("load", Class.class);
            }
            catch (ClassNotFoundException | NoSuchMethodException servicesClass) {
                // empty catch block
            }
            if (loadMethod != null) {
                try {
                    try {
                        loadMethod.invoke(null, new Object[]{null});
                    }
                    catch (InvocationTargetException e) {
                        throw e.getTargetException();
                    }
                }
                catch (NullPointerException e) {
                }
                catch (IllegalAccessException iae) {
                    loadMethod = null;
                }
                catch (Throwable e) {
                    throw new InternalError(e);
                }
            }
            LOAD_METHOD = loadMethod;
        }
    }
}

