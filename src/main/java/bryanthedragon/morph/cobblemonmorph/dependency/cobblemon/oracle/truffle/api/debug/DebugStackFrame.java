package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Objects;

public final class DebugStackFrame {
   final SuspendedEvent event;
   private final FrameInstance currentFrame;
   private final StackTraceElement hostTraceElement;
   private final int depth;
   private final String name;
   private final DebugException nameEx;

   DebugStackFrame(SuspendedEvent session, FrameInstance instance, int depth) {
      this.event = session;
      this.currentFrame = instance;
      this.hostTraceElement = null;
      this.depth = depth;
      String frameName = null;
      DebugException frameNameEx = null;

      try {
         frameName = this.initName();
      } catch (DebugException var7) {
         frameNameEx = var7;
      }

      this.name = frameName;
      this.nameEx = frameNameEx;
   }

   DebugStackFrame(SuspendedEvent session, StackTraceElement hostElement, int depth) {
      this.event = session;
      this.currentFrame = null;
      this.hostTraceElement = hostElement;
      this.depth = depth;
      this.name = hostElement.getClassName() + "." + hostElement.getMethodName();
      this.nameEx = null;
   }

   private String initName() throws DebugException {
      this.verifyValidState(false);
      Node node;
      if (this.currentFrame == null) {
         node = this.getContext().getInstrumentedNode();
      } else {
         node = this.currentFrame.getCallNode();
         node = InstrumentableNode.findInstrumentableParent(node);
      }

      try {
         if (node != null) {
            Frame frame = this.findTruffleFrame(FrameInstance.FrameAccess.READ_ONLY);
            NodeLibrary nodeLibrary = NodeLibrary.getUncached();
            if (nodeLibrary.hasRootInstance(node, frame)) {
               Object instance = nodeLibrary.getRootInstance(node, frame);
               InteropLibrary interop = InteropLibrary.getUncached();
               if (interop.hasExecutableName(instance)) {
                  return interop.asString(interop.getExecutableName(instance));
               }
            }
         }

         RootNode root = this.findCurrentRoot();
         return root == null ? null : root.getName();
      } catch (ThreadDeath var6) {
         throw var6;
      } catch (Throwable var7) {
         RootNode root = this.findCurrentRoot();
         LanguageInfo languageInfo = root != null ? root.getLanguageInfo() : null;
         throw DebugException.create(this.event.getSession(), var7, languageInfo);
      }
   }

   public boolean isInternal() {
      this.verifyValidState(true);
      if (this.isHost()) {
         return false;
      } else {
         RootNode root = this.findCurrentRoot();
         return root == null ? true : root.isInternal();
      }
   }

   public boolean isHost() {
      return this.hostTraceElement != null;
   }

   public StackTraceElement getHostTraceElement() {
      return this.hostTraceElement;
   }

   public String getName() throws DebugException {
      this.verifyValidState(true);
      if (this.nameEx != null) {
         throw this.nameEx;
      } else {
         return this.name;
      }
   }

   public SourceSection getSourceSection() {
      this.verifyValidState(true);
      if (this.isHost()) {
         return null;
      } else if (this.currentFrame == null) {
         SuspendedContext context = this.getContext();
         return this.event.getSession().resolveSection(context.getInstrumentedSourceSection());
      } else {
         Node callNode = this.currentFrame.getCallNode();
         return callNode != null ? this.event.getSession().resolveSection(callNode) : null;
      }
   }

   public LanguageInfo getLanguage() {
      this.verifyValidState(true);
      if (this.isHost()) {
         return null;
      } else {
         RootNode root = this.findCurrentRoot();
         return root == null ? null : root.getLanguageInfo();
      }
   }

   public DebugScope getScope() throws DebugException {
      this.verifyValidState(false);
      if (this.isHost()) {
         return null;
      } else {
         SuspendedContext context = this.getContext();
         RootNode root = this.findCurrentRoot();
         if (root == null) {
            return null;
         } else {
            Node node;
            if (this.currentFrame == null) {
               node = context.getInstrumentedNode();
            } else {
               node = this.currentFrame.getCallNode();
               if (node == null) {
                  return null;
               }

               node = InstrumentableNode.findInstrumentableParent(node);
            }

            DebuggerSession session = this.event.getSession();
            Frame frame = this.findTruffleFrame(FrameInstance.FrameAccess.READ_WRITE);

            try {
               if (!NodeLibrary.getUncached().hasScope(node, frame)) {
                  return null;
               } else {
                  Object scope = NodeLibrary.getUncached().getScope(node, frame, this.isEnter());
                  return new DebugScope(scope, session, this.event, node, frame, root);
               }
            } catch (ThreadDeath var7) {
               throw var7;
            } catch (Throwable var8) {
               throw DebugException.create(session, var8, root.getLanguageInfo());
            }
         }
      }
   }

   private boolean isEnter() {
      return this.depth == 0 && SuspendAnchor.BEFORE.equals(this.event.getSuspendAnchor());
   }

   public Node getRawNode(Class<? extends TruffleLanguage<?>> languageClass) {
      Objects.requireNonNull(languageClass);
      RootNode rootNode = this.findCurrentRoot();
      if (rootNode == null) {
         return null;
      } else {
         TruffleLanguage<?> language = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
         return language != null && language.getClass() == languageClass ? this.getCurrentNode() : null;
      }
   }

   public Frame getRawFrame(Class<? extends TruffleLanguage<?>> languageClass, FrameInstance.FrameAccess access) {
      Objects.requireNonNull(languageClass);
      RootNode rootNode = this.findCurrentRoot();
      if (rootNode == null) {
         return null;
      } else {
         TruffleLanguage<?> language = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
         return language != null && language.getClass() == languageClass ? this.findTruffleFrame(access) : null;
      }
   }

   DebugValue wrapHeapValue(Object result) {
      assert !this.isHost() : "Can not wrap values in host frames.";

      RootNode root = this.findCurrentRoot();
      LanguageInfo language;
      if (root != null) {
         language = root.getLanguageInfo();
      } else {
         language = null;
      }

      return new DebugValue.HeapValue(this.event.getSession(), language, null, result);
   }

   public DebugValue eval(String code) throws DebugException {
      this.verifyValidState(false);
      if (this.isHost()) {
         throw new IllegalStateException("Can not evaluate code in host frames.");
      } else {
         Object result = DebuggerSession.evalInContext(this.event, code, this.currentFrame);
         return this.wrapHeapValue(result);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof DebugStackFrame)) {
         return false;
      } else {
         DebugStackFrame other = (DebugStackFrame)obj;
         return this.event == other.event
            && this.hostTraceElement == other.hostTraceElement
            && (
               this.currentFrame == other.currentFrame
                  || this.currentFrame != null
                     && other.currentFrame != null
                     && this.currentFrame.getFrame(FrameInstance.FrameAccess.READ_ONLY) == other.currentFrame.getFrame(FrameInstance.FrameAccess.READ_ONLY)
            );
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.event, this.currentFrame);
   }

   Frame findTruffleFrame(FrameInstance.FrameAccess access) {
      assert !this.isHost() : "No Truffle frame in host stack frame";

      return (Frame)(this.currentFrame == null ? this.event.getMaterializedFrame() : this.currentFrame.getFrame(access));
   }

   int getDepth() {
      return this.depth;
   }

   private SuspendedContext getContext() {
      SuspendedContext context = this.event.getContext();
      if (context == null) {
         this.verifyValidState(true);

         assert false : "should not be reachable";
      }

      return context;
   }

   RootNode findCurrentRoot() {
      if (this.isHost()) {
         return null;
      } else {
         SuspendedContext context = this.getContext();
         return this.currentFrame == null ? context.getInstrumentedNode().getRootNode() : ((RootCallTarget)this.currentFrame.getCallTarget()).getRootNode();
      }
   }

   RootCallTarget getCallTarget() {
      if (this.isHost()) {
         return null;
      } else {
         SuspendedContext context = this.getContext();
         return this.currentFrame == null ? context.getInstrumentedNode().getRootNode().getCallTarget() : (RootCallTarget)this.currentFrame.getCallTarget();
      }
   }

   Node getCurrentNode() {
      if (this.isHost()) {
         return null;
      } else if (this.currentFrame == null) {
         return this.getContext().getInstrumentedNode();
      } else {
         Node callNode = this.currentFrame.getCallNode();
         if (callNode != null) {
            return callNode;
         } else {
            CallTarget target = this.currentFrame.getCallTarget();
            return target instanceof RootCallTarget ? ((RootCallTarget)target).getRootNode() : null;
         }
      }
   }

   void verifyValidState(boolean allowDifferentThread) {
      this.event.verifyValidState(allowDifferentThread);
   }
}
