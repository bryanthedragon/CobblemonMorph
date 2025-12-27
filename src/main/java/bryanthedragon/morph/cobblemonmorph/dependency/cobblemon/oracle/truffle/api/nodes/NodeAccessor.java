package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.impl.Accessor;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;

final class NodeAccessor extends Accessor {
   private static final NodeAccessor ACCESSOR = new NodeAccessor();
   static final Accessor.InteropSupport INTEROP = ACCESSOR.interopSupport();
   static final Accessor.ExceptionSupport EXCEPTION = ACCESSOR.exceptionSupport();
   static final Accessor.EngineSupport ENGINE = ACCESSOR.engineSupport();
   static final Accessor.HostSupport HOST = ACCESSOR.hostSupport();
   static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
   static final Accessor.RuntimeSupport RUNTIME = ACCESSOR.runtimeSupport();
   static final Accessor.InstrumentSupport INSTRUMENT = ACCESSOR.instrumentSupport();

   private NodeAccessor() {
   }

   static final class AccessNodes extends Accessor.NodeSupport {
      @Override
      public boolean isInstrumentable(RootNode rootNode) {
         return rootNode.isInstrumentable();
      }

      @Override
      public boolean isCloneUninitializedSupported(RootNode rootNode) {
         return rootNode.isCloneUninitializedSupported();
      }

      @Override
      public RootNode cloneUninitialized(CallTarget sourceCallTarget, RootNode rootNode, RootNode uninitializedRootNode) {
         return rootNode.cloneUninitializedImpl(sourceCallTarget, uninitializedRootNode);
      }

      @Override
      public int adoptChildrenAndCount(RootNode rootNode) {
         return rootNode.adoptChildrenAndCount();
      }

      @Override
      public Object getLanguageCache(LanguageInfo languageInfo) {
         return languageInfo.getLanguageCache();
      }

      @Override
      public LanguageInfo createLanguage(
         Object cache, String id, String name, String version, String defaultMimeType, Set<String> mimeTypes, boolean internal, boolean interactive
      ) {
         return new LanguageInfo(cache, id, name, version, defaultMimeType, mimeTypes, internal, interactive);
      }

      @Override
      public void setSharingLayer(RootNode rootNode, Object layer) {
         rootNode.setSharingLayer(layer);
      }

      @Override
      public Object getSharingLayer(RootNode rootNode) {
         return rootNode.getSharingLayer();
      }

      @Override
      public TruffleLanguage<?> getLanguage(RootNode rootNode) {
         return rootNode.getLanguage();
      }

      @Override
      public List<TruffleStackTraceElement> findAsynchronousFrames(CallTarget target, Frame frame) {
         CompilerAsserts.neverPartOfCompilation();
         return ((RootCallTarget)target).getRootNode().findAsynchronousFrames(frame);
      }

      @Override
      public int getRootNodeBits(RootNode root) {
         return root.instrumentationBits;
      }

      @Override
      public void setRootNodeBits(RootNode root, int bits) {
         assert (byte)bits == bits : "root bits currently limit to a byte";

         root.instrumentationBits = (byte)bits;
      }

      @Override
      public Lock getLock(Node node) {
         return node.getLock();
      }

      @Override
      public void applySharingLayer(RootNode from, RootNode to) {
         to.applyEngineRef(from);
      }

      @Override
      public void forceAdoption(Node parent, Node child) {
         child.setParent(parent);
      }

      @Override
      public boolean isTrivial(RootNode rootNode) {
         return rootNode.isTrivial();
      }

      @Override
      public FrameDescriptor getParentFrameDescriptor(RootNode rootNode) {
         return rootNode.getParentFrameDescriptor();
      }

      @Override
      public Object translateStackTraceElement(TruffleStackTraceElement stackTraceLement) {
         return stackTraceLement.getTarget().getRootNode().translateStackTraceElement(stackTraceLement);
      }

      @Override
      public ExecutionSignature prepareForAOT(RootNode rootNode) {
         return rootNode.prepareForAOT();
      }

      @Override
      public boolean countsTowardsStackTraceLimit(RootNode rootNode) {
         return rootNode.countsTowardsStackTraceLimit();
      }

      @Override
      public CallTarget getCallTargetWithoutInitialization(RootNode root) {
         return root.getCallTargetWithoutInitialization();
      }

      @Override
      public EncapsulatingNodeReference createEncapsulatingNodeReference(Thread thread) {
         return new EncapsulatingNodeReference(thread);
      }
   }
}
