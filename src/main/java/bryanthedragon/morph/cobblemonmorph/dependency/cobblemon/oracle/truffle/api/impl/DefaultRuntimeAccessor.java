package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.BytecodeOSRNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.function.Function;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionValues;

final class DefaultRuntimeAccessor extends Accessor {
   private static final DefaultRuntimeAccessor ACCESSOR = new DefaultRuntimeAccessor();
   static final Accessor.NodeSupport NODES = ACCESSOR.nodeSupport();
   static final Accessor.SourceSupport SOURCE = ACCESSOR.sourceSupport();
   static final Accessor.InstrumentSupport INSTRUMENT = ACCESSOR.instrumentSupport();
   static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
   static final Accessor.EngineSupport ENGINE = ACCESSOR.engineSupport();
   static final Accessor.InteropSupport INTEROP = ACCESSOR.interopSupport();
   static final Accessor.FrameSupport FRAME = ACCESSOR.framesSupport();

   private DefaultRuntimeAccessor() {
   }

   static final class DefaultRuntimeSupport extends Accessor.RuntimeSupport {
      DefaultRuntimeSupport(Object permission) {
         super(permission);
      }

      @Override
      public RootCallTarget newCallTarget(CallTarget sourceCallTarget, RootNode rootNode) {
         return new DefaultCallTarget(rootNode);
      }

      @Override
      public boolean isLoaded(CallTarget callTarget) {
         return ((DefaultCallTarget)callTarget).isLoaded();
      }

      @Override
      public void notifyOnLoad(CallTarget callTarget) {
         DefaultCallTarget target = (DefaultCallTarget)callTarget;
         DefaultRuntimeAccessor.INSTRUMENT.onLoad(target.getRootNode());
         target.setLoaded();
      }

      @Override
      public ThreadLocalHandshake getThreadLocalHandshake() {
         return DefaultThreadLocalHandshake.SINGLETON;
      }

      @Override
      public void onLoopCount(Node source, int iterations) {
      }

      @Override
      public boolean pollBytecodeOSRBackEdge(BytecodeOSRNode osrNode) {
         return false;
      }

      @Override
      public Object tryBytecodeOSR(BytecodeOSRNode osrNode, int target, Object interpreterState, Runnable beforeTransfer, VirtualFrame parentFrame) {
         return null;
      }

      @Override
      public void onOSRNodeReplaced(BytecodeOSRNode osrNode, Node oldNode, Node newNode, CharSequence reason) {
      }

      @Override
      public void transferOSRFrame(BytecodeOSRNode osrNode, Frame source, Frame target, int bytecodeTarget) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void transferOSRFrame(BytecodeOSRNode osrNode, Frame source, Frame target, int bytecodeTarget, Object targetMetadata) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void restoreOSRFrame(BytecodeOSRNode osrNode, Frame source, Frame target) {
         throw new UnsupportedOperationException();
      }

      @Override
      public OptionDescriptors getEngineOptionDescriptors() {
         return OptionDescriptors.EMPTY;
      }

      @Override
      public boolean isGuestCallStackFrame(StackTraceElement e) {
         String methodName = e.getMethodName();
         return methodName.equals("callDirectOrIndirect") && e.getClassName().equals(DefaultCallTarget.class.getName());
      }

      @Override
      public void initializeProfile(CallTarget target, Class<?>[] argumentTypes) {
      }

      @Override
      public <T extends Node> BlockNode<T> createBlockNode(T[] elements, BlockNode.ElementExecutor<T> executor) {
         return new DefaultBlockNode<>(elements, executor);
      }

      @Override
      public Assumption createAlwaysValidAssumption() {
         return DefaultAssumption.createAlwaysValid();
      }

      @Override
      public void onEngineClosed(Object runtimeData) {
      }

      @Override
      public String getSavedProperty(String key) {
         return System.getProperty(key);
      }

      @Override
      public Object callInlined(Node callNode, CallTarget target, Object... arguments) {
         return ((DefaultCallTarget)target).callDirectOrIndirect(callNode, arguments);
      }

      @Override
      public Object callProfiled(CallTarget target, Object... arguments) {
         return ((DefaultCallTarget)target).call(arguments);
      }

      @Override
      public Object[] castArrayFixedLength(Object[] args, int length) {
         return args;
      }

      @Override
      public void flushCompileQueue(Object runtimeData) {
      }

      @Override
      public <T> T unsafeCast(Object value, Class<T> type, boolean condition, boolean nonNull, boolean exact) {
         return (T)value;
      }

      @Override
      public void reportPolymorphicSpecialize(Node source) {
      }

      @Override
      public Object createRuntimeData(OptionValues options, Function<String, TruffleLogger> loggerFactory) {
         return null;
      }

      @Override
      public Object tryLoadCachedEngine(OptionValues runtimeData, Function<String, TruffleLogger> loggerFactory) {
         return null;
      }

      @Override
      public void onEngineCreate(Object engine, Object runtimeData) {
      }

      @Override
      public boolean isStoreEnabled(OptionValues options) {
         return false;
      }

      @Override
      public void onEnginePatch(Object runtimeData, OptionValues options, Function<String, TruffleLogger> loggerFactory) {
      }

      @Override
      public boolean onEngineClosing(Object runtimeData) {
         return false;
      }

      @Override
      public boolean isOSRRootNode(RootNode rootNode) {
         return false;
      }

      @Override
      public int getObjectAlignment() {
         throw new UnsupportedOperationException();
      }

      @Override
      public int getArrayBaseOffset(Class<?> componentType) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int getArrayIndexScale(Class<?> componentType) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int getBaseInstanceSize(Class<?> type) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Object[] getResolvedFields(Class<?> type, boolean includePrimitive, boolean includeSuperclasses) {
         throw new UnsupportedOperationException();
      }

      @Override
      public Object getFieldValue(Object resolvedJavaField, Object obj) {
         throw new UnsupportedOperationException();
      }

      @Override
      public AbstractFastThreadLocal getContextThreadLocal() {
         return DefaultContextThreadLocal.SINGLETON;
      }
   }
}
