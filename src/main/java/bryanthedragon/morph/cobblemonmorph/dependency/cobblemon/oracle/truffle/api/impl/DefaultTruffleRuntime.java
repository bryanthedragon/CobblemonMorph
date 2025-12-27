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

public final class DefaultTruffleRuntime implements TruffleRuntime {
   private final ThreadLocal<DefaultTruffleRuntime.DefaultFrameInstance> stackTraces = new ThreadLocal<>();
   private final DefaultTVMCI tvmci = new DefaultTVMCI();
   private final TVMCI.Test<Closeable, CallTarget> testTvmci = new TVMCI.Test<Closeable, CallTarget>() {
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
      } else {
         T result = null;
         DefaultTruffleRuntime.DefaultFrameInstance frameInstance = this.getThreadLocalStackTrace();

         for (int skipCounter = skipFrames; frameInstance != null; skipCounter--) {
            if (skipCounter <= 0) {
               result = visitor.visitFrame(frameInstance);
               if (result != null) {
                  return result;
               }
            }

            frameInstance = frameInstance.callerFrame;
         }

         return result;
      }
   }

   private DefaultTruffleRuntime.DefaultFrameInstance getThreadLocalStackTrace() {
      return this.stackTraces.get();
   }

   private void setThreadLocalStackTrace(DefaultTruffleRuntime.DefaultFrameInstance topFrame) {
      this.stackTraces.set(topFrame);
   }

   DefaultTruffleRuntime.DefaultFrameInstance pushFrame(VirtualFrame frame, CallTarget target) {
      DefaultTruffleRuntime.DefaultFrameInstance callerFrame = this.getThreadLocalStackTrace();
      this.setThreadLocalStackTrace(new DefaultTruffleRuntime.DefaultFrameInstance(frame, target, null, callerFrame));
      return callerFrame;
   }

   DefaultTruffleRuntime.DefaultFrameInstance pushFrame(VirtualFrame frame, CallTarget target, Node parentCallNode) {
      DefaultTruffleRuntime.DefaultFrameInstance callerFrame = this.getThreadLocalStackTrace();
      DefaultTruffleRuntime.DefaultFrameInstance callerFrameWithCallNode = callerFrame != null ? callerFrame.withCallNode(parentCallNode) : callerFrame;
      this.setThreadLocalStackTrace(new DefaultTruffleRuntime.DefaultFrameInstance(frame, target, null, callerFrameWithCallNode));
      return callerFrame;
   }

   void popFrame(DefaultTruffleRuntime.DefaultFrameInstance callerFrame) {
      this.setThreadLocalStackTrace(callerFrame);
   }

   @Override
   public <T> T getCapability(Class<T> capability) {
      if (capability == TVMCI.Test.class) {
         return capability.cast(this.testTvmci);
      } else if (capability == TVMCI.class) {
         return capability.cast(this.tvmci);
      } else {
         Iterator<T> it = DefaultTruffleRuntime.Loader.load(capability).iterator();

         try {
            return it.hasNext() ? it.next() : null;
         } catch (ServiceConfigurationError var4) {
            return null;
         }
      }
   }

   @Override
   public void notifyTransferToInterpreter() {
   }

   @Override
   public LoopNode createLoopNode(RepeatingNode repeating) {
      if (!(repeating instanceof Node)) {
         throw new IllegalArgumentException("Repeating node must be of type Node.");
      } else {
         return new DefaultLoopNode(repeating);
      }
   }

   @Override
   public boolean isProfilingEnabled() {
      return false;
   }

   public void markFrameMaterializeCalled(FrameDescriptor descriptor) {
   }

   static final class DefaultFrameInstance implements FrameInstance {
      private final CallTarget target;
      private final VirtualFrame frame;
      private final Node callNode;
      private final DefaultTruffleRuntime.DefaultFrameInstance callerFrame;

      DefaultFrameInstance(VirtualFrame frame, CallTarget target, Node callNode, DefaultTruffleRuntime.DefaultFrameInstance callerFrame) {
         this.target = target;
         this.frame = frame;
         this.callNode = callNode;
         this.callerFrame = callerFrame;
      }

      @Override
      public Frame getFrame(FrameInstance.FrameAccess access) {
         Frame localFrame = this.frame;
         switch (access) {
            case READ_ONLY:
               return new ReadOnlyFrame(localFrame);
            case READ_WRITE:
               return localFrame;
            case MATERIALIZE:
               return localFrame.materialize();
            default:
               throw CompilerDirectives.shouldNotReachHere();
         }
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

      DefaultTruffleRuntime.DefaultFrameInstance withCallNode(Node otherCallNode) {
         return new DefaultTruffleRuntime.DefaultFrameInstance(this.frame, this.target, otherCallNode, this.callerFrame);
      }
   }

   private static final class Loader {
      private static final Method LOAD_METHOD;

      static <S> Iterable<S> load(Class<S> service) {
         Module truffleModule = DefaultTruffleRuntime.class.getModule();
         if (!truffleModule.canUse(service)) {
            truffleModule.addUses(service);
         }

         if (LOAD_METHOD != null) {
            try {
               return (Iterable<S>)LOAD_METHOD.invoke(null, service);
            } catch (Exception var4) {
               throw new InternalError(var4);
            }
         } else {
            ModuleLayer moduleLayer = truffleModule.getLayer();
            Iterable<S> services;
            if (moduleLayer != null) {
               services = ServiceLoader.load(moduleLayer, service);
            } else {
               services = ServiceLoader.load(service, DefaultTruffleRuntime.class.getClassLoader());
            }

            if (!services.iterator().hasNext()) {
               services = ServiceLoader.load(service);
            }

            return services;
         }
      }

      static {
         Method loadMethod = null;

         try {
            Class<?> servicesClass = Class.forName("jdk.vm.ci.services.Services");
            loadMethod = servicesClass.getMethod("load", Class.class);
         } catch (NoSuchMethodException | ClassNotFoundException var6) {
         }

         if (loadMethod != null) {
            try {
               try {
                  loadMethod.invoke(null, null);
               } catch (InvocationTargetException var2) {
                  throw var2.getTargetException();
               }
            } catch (NullPointerException var3) {
            } catch (IllegalAccessException var4) {
               loadMethod = null;
            } catch (Throwable var5) {
               throw new InternalError(var5);
            }
         }

         LOAD_METHOD = loadMethod;
      }
   }
}
