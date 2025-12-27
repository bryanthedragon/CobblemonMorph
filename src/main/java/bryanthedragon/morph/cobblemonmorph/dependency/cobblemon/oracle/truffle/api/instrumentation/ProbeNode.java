package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;

public final class ProbeNode extends Node {
   private static final int SEEN_UNWIND = 1;
   private static final int SEEN_UNWIND_NEXT = 2;
   private static final int SEEN_RETURN = 4;
   private static final int SEEN_REENTER = 8;
   public static final Object UNWIND_ACTION_REENTER = new Object();
   private static final Object UNWIND_ACTION_IGNORED = new Object();
   private final InstrumentationHandler handler;
   private volatile ProbeNode.RetiredNodeReference retiredNodeReference;
   @CompilerDirectives.CompilationFinal
   private volatile EventContext context;
   @Node.Child
   private volatile ProbeNode.EventChainNode chain;
   @CompilerDirectives.CompilationFinal
   private volatile Assumption version;
   @CompilerDirectives.CompilationFinal
   private volatile int seen = 0;

   ProbeNode(InstrumentationHandler handler, SourceSection sourceSection) {
      this.handler = handler;
      this.context = new EventContext(this, sourceSection);
   }

   ProbeNode.RetiredNodeReference getRetiredNodeReference() {
      return this.retiredNodeReference;
   }

   void clearRetiredNodeReference() {
      this.retiredNodeReference = null;
   }

   private boolean hasNewTags(Node retiredNode, Set<Class<? extends Tag>> materializeTags) {
      Set<Class<? extends Tag>> allSeenMaterializeTags = (Set<Class<? extends Tag>>)(this.retiredNodeReference.next == null
         ? this.retiredNodeReference.materializeTags
         : new HashSet<>(this.retiredNodeReference.materializeTags));

      for (ProbeNode.RetiredNodeReference nodeRef = this.retiredNodeReference; nodeRef != null; nodeRef = nodeRef.next) {
         if (allSeenMaterializeTags != nodeRef.materializeTags) {
            allSeenMaterializeTags.addAll(nodeRef.materializeTags);
         }

         Node nodeRefNode = nodeRef.getNode();

         assert nodeRefNode == null || nodeRefNode != retiredNode : "The same retired node must not be set more than once!";

         assert !nodeRef.materializeTags.equals(materializeTags) : "Retired node must be set at most once for the same set of tags!";
      }

      return !allSeenMaterializeTags.containsAll(materializeTags);
   }

   void setRetiredNode(Node retiredNode, Set<Class<? extends Tag>> materializeTags) {
      if (this.retiredNodeReference == null) {
         this.retiredNodeReference = new ProbeNode.RetiredNodeReference(retiredNode, materializeTags, null);
      } else {
         assert this.hasNewTags(retiredNode, materializeTags) : "There should always be some new materialize tag!";

         ProbeNode.RetiredNodeReference previousRetiredNodeReference = this.retiredNodeReference;
         ProbeNode.RetiredNodeReference newRetiredNodeReference = new ProbeNode.RetiredNodeReference(retiredNode, materializeTags, previousRetiredNodeReference);
         this.retiredNodeReference = newRetiredNodeReference;
      }
   }

   public void onEnter(VirtualFrame frame) {
      ProbeNode.EventChainNode localChain = this.lazyUpdate(frame);
      if (localChain != null) {
         ProbeNode.EventChainNode.onEnter(localChain, this.context, frame);
      }
   }

   public void onReturnValue(VirtualFrame frame, Object result) {
      ProbeNode.EventChainNode localChain = this.lazyUpdate(frame);

      assert this.isNullOrInteropValue(result);

      if (localChain != null) {
         ProbeNode.EventChainNode.onReturnValue(localChain, this.context, frame, result);
      }
   }

   private boolean isNullOrInteropValue(Object result) {
      if (!(this.context.getInstrumentedNode() instanceof InstrumentableNode)) {
         return true;
      } else if (result == null) {
         return true;
      } else {
         InstrumentAccessor.interopAccess().checkInteropType(result);
         return true;
      }
   }

   @Override
   public Node copy() {
      ProbeNode pn = (ProbeNode)super.copy();
      pn.context = new EventContext(pn, this.context.getInstrumentedSourceSection());
      return pn;
   }

   public Object onReturnExceptionalOrUnwind(VirtualFrame frame, Throwable exception, boolean isReturnCalled) {
      UnwindException unwind = null;
      if (exception instanceof UnwindException) {
         this.profileBranch(1);
         unwind = (UnwindException)exception;
      } else if (exception instanceof ThreadDeath) {
         throw (ThreadDeath)exception;
      }

      ProbeNode.EventChainNode localChain = this.lazyUpdate(frame);
      if (localChain != null) {
         if (!isReturnCalled) {
            try {
               ProbeNode.EventChainNode.onReturnExceptional(localChain, this.context, frame, exception);
            } catch (UnwindException var7) {
               this.profileBranch(1);
               if (unwind != null && unwind != var7) {
                  this.profileBranch(2);
                  unwind.addNext(var7);
               } else {
                  unwind = var7;
               }
            }
         }

         if (unwind != null) {
            Object ret = ProbeNode.EventChainNode.onUnwind(localChain, this.context, frame, unwind);
            if (ret == UNWIND_ACTION_REENTER) {
               this.profileBranch(8);
               return UNWIND_ACTION_REENTER;
            }

            if (ret != null && ret != UNWIND_ACTION_IGNORED) {
               this.profileBranch(4);

               assert this.isNullOrInteropValue(ret);

               return ret;
            }

            throw unwind;
         }
      }

      return null;
   }

   private void profileBranch(int flag) {
      if ((this.seen & flag) == 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.seen |= flag;
      }
   }

   void onInputValue(VirtualFrame frame, EventBinding<?> targetBinding, EventContext inputContext, int inputIndex, Object inputValue) {
      ProbeNode.EventChainNode localChain = this.lazyUpdate(frame);
      if (localChain != null) {
         ProbeNode.EventChainNode.onInputValue(localChain, this.context, frame, targetBinding, inputContext, inputIndex, inputValue);
      }
   }

   EventContext getContext() {
      return this.context;
   }

   InstrumentableNode.WrapperNode findWrapper() throws AssertionError {
      Node parent = this.getParent();
      if (!(parent instanceof InstrumentableNode.WrapperNode)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         if (parent == null) {
            throw new AssertionError("Probe node disconnected from AST.");
         } else {
            throw new AssertionError("ProbeNodes must have a parent Node that implements NodeWrapper.");
         }
      } else {
         return (InstrumentableNode.WrapperNode)parent;
      }
   }

   synchronized void invalidate() {
      Assumption localVersion = this.version;
      if (localVersion != null) {
         localVersion.invalidate();
      }
   }

   ProbeNode.EventChainNode lazyUpdate(VirtualFrame frame) {
      Assumption localVersion = this.version;
      if (localVersion != null && localVersion.isValid()) {
         return this.chain;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.lazyUpdatedImpl(frame);
      }
   }

   private ProbeNode.EventChainNode lazyUpdatedImpl(VirtualFrame frame) {
      Lock lock = this.getLock();
      lock.lock();

      ProbeNode.EventChainNode oldChain;
      ProbeNode.EventChainNode nextChain;
      try {
         Assumption localVersion = this.version;
         if (localVersion != null && localVersion.isValid()) {
            return this.chain;
         }

         EventBinding.Source<?>[] executionBindingsSnapshot;
         do {
            executionBindingsSnapshot = this.handler.getExecutionBindingsSnapshot();
            nextChain = this.handler.createBindings(frame, this, executionBindingsSnapshot);
            if (nextChain == null) {
               if (this.retiredNodeReference == null) {
                  InstrumentationHandler.removeWrapper(this);
                  return null;
               }

               oldChain = this.chain;
               this.chain = null;
            } else {
               oldChain = this.chain;
               this.chain = this.insert(nextChain);
            }

            this.version = Truffle.getRuntime().createAssumption("Instruments unchanged");
         } while (executionBindingsSnapshot != this.handler.getExecutionBindingsSnapshot());

         assert this.context.validEventContextOnLazyUpdate();
      } finally {
         lock.unlock();
      }

      if (oldChain != null) {
         ProbeNode.EventChainNode.onDispose(oldChain, this.context, frame);
      }

      return nextChain;
   }

   ExecutionEventNode lookupExecutionEventNode(EventBinding<?> binding) {
      if (binding.isDisposed()) {
         return null;
      } else {
         for (ProbeNode.EventChainNode chainNode = this.chain; chainNode != null; chainNode = chainNode.next) {
            if (chainNode.binding == binding && chainNode instanceof ProbeNode.EventProviderChainNode) {
               return ((ProbeNode.EventProviderChainNode)chainNode).eventNode;
            }
         }

         return null;
      }
   }

   Iterator<ExecutionEventNode> lookupExecutionEventNodes(Collection<EventBinding<? extends ExecutionEventNodeFactory>> bindings) {
      return new Iterator<ExecutionEventNode>() {
         private ProbeNode.EventChainNode chainNode = ProbeNode.this.chain;
         private ProbeNode.EventProviderChainNode nextNode;

         @Override
         public boolean hasNext() {
            if (this.nextNode == null) {
               while (this.chainNode != null) {
                  if (this.chainNode instanceof ProbeNode.EventProviderChainNode && bindings.contains(this.chainNode.binding)) {
                     this.nextNode = (ProbeNode.EventProviderChainNode)this.chainNode;
                     this.chainNode = this.chainNode.next;
                     break;
                  }

                  this.chainNode = this.chainNode.next;
               }
            }

            return this.nextNode != null;
         }

         public ExecutionEventNode next() {
            ProbeNode.EventProviderChainNode node = this.nextNode;
            if (node == null) {
               throw new NoSuchElementException();
            } else {
               this.nextNode = null;
               return node.eventNode;
            }
         }
      };
   }

   ProbeNode.EventChainNode createParentEventChainCallback(VirtualFrame frame, EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags) {
      ProbeNode.EventChainNode parent = this.findParentChain(frame, binding);
      if (!(parent instanceof ProbeNode.EventProviderWithInputChainNode)) {
         return null;
      } else {
         EventContext parentContext = parent.findProbe().getContext();
         ProbeNode.EventProviderWithInputChainNode parentChain = (ProbeNode.EventProviderWithInputChainNode)parent;
         int index = indexOfChild(
            binding,
            rootNode,
            providedTags,
            parentContext.getInstrumentedNode(),
            parentContext.getInstrumentedSourceSection(),
            this.context.getInstrumentedNode()
         );
         if (index >= 0 && index < parentChain.inputCount) {
            ProbeNode probe = parent.findProbe();
            return new ProbeNode.InputValueChainNode(binding, probe, this.context, index);
         } else {
            assert throwIllegalASTAssertion(parentChain, parentContext, binding, rootNode, providedTags, index);

            return null;
         }
      }
   }

   private static boolean throwIllegalASTAssertion(
      ProbeNode.EventProviderWithInputChainNode parentChain,
      EventContext parentContext,
      EventBinding.Source<?> binding,
      RootNode rootNode,
      Set<Class<?>> providedTags,
      int index
   ) {
      StringBuilder msg = new StringBuilder();

      try {
         int lookupChildrenCount = 10;
         SourceSection parentSourceSection = parentContext.getInstrumentedSourceSection();
         EventContext[] contexts = findChildContexts(
            binding,
            rootNode,
            providedTags,
            parentContext.getInstrumentedNode(),
            parentContext.getInstrumentedSourceSection(),
            Math.max(parentChain.inputCount, index + 10)
         );
         int contextCount = 0;

         for (int i = 0; i < contexts.length; i++) {
            EventContext eventContext = contexts[i];
            if (eventContext != null) {
               contextCount++;
            }
         }

         msg.append("Stable AST assumption violated.  " + parentChain.inputCount + " children expected got " + contextCount);
         msg.append("\n Parent: " + parentSourceSection);

         for (int ix = 0; ix < contexts.length; ix++) {
            EventContext eventContext = contexts[ix];
            if (eventContext != null) {
               msg.append("\nChild[" + ix + "] = " + eventContext.getInstrumentedSourceSection());
               Node node = eventContext.getInstrumentedNode();

               for (String indent = "  "; node != null; node = node.getParent()) {
                  msg.append("\n");
                  msg.append(indent);
                  if (node == parentContext.getInstrumentedNode()) {
                     msg.append("Parent");
                     break;
                  }

                  if (node.getParent() == null) {
                     msg.append("null parent = ");
                  } else {
                     String fieldName = NodeUtil.findChildFieldName(node.getParent(), node);
                     msg.append(node.getParent().getClass().getSimpleName() + "." + fieldName + " = ");
                  }

                  msg.append(node.getClass().getSimpleName() + "#" + System.identityHashCode(node));
                  indent = indent + "  ";
               }
            }
         }
      } catch (Throwable var16) {
         AssertionError error = new AssertionError("Stable AST assumption violated");
         error.addSuppressed(var16);
         throw error;
      }

      throw new AssertionError(msg.toString());
   }

   ProbeNode.EventChainNode createEventChainCallback(
      VirtualFrame frame,
      EventBinding.Source<?> binding,
      RootNode rootNode,
      Set<Class<?>> providedTags,
      Node instrumentedNode,
      SourceSection instrumentedNodeSourceSection
   ) {
      Object element = binding.getElement();
      ProbeNode.EventChainNode next;
      if (element instanceof ExecutionEventListener) {
         next = new ProbeNode.EventFilterChainNode(binding, (ExecutionEventListener)element);
      } else {
         assert element instanceof ExecutionEventNodeFactory;

         ExecutionEventNode eventNode = this.createEventNode(binding, element);
         if (eventNode == null) {
            return null;
         }

         if (binding.getInputFilter() != null) {
            ProbeNode.EventChainNode parent = this.findParentChain(frame, binding);
            ProbeNode.EventProviderWithInputChainNode parentChain = (ProbeNode.EventProviderWithInputChainNode)parent;
            int baseInput;
            if (parentChain == null) {
               baseInput = 0;
            } else {
               EventContext parentContext = parentChain.findProbe().getContext();
               int childIndex = indexOfChild(
                  binding, rootNode, providedTags, parentContext.getInstrumentedNode(), parentContext.getInstrumentedSourceSection(), instrumentedNode
               );
               int inputBaseIndex = parentChain.inputBaseIndex;
               if (childIndex < 0) {
                  baseInput = inputBaseIndex + parentChain.inputCount;
               } else {
                  baseInput = inputBaseIndex + childIndex;
               }
            }

            int inputCount = countChildren(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection);
            next = new ProbeNode.EventProviderWithInputChainNode(binding, eventNode, baseInput, inputCount);
         } else {
            next = new ProbeNode.EventProviderChainNode(binding, eventNode);
         }
      }

      return next;
   }

   static EventContext[] findChildContexts(
      EventBinding.Source<?> binding,
      RootNode rootNode,
      Set<Class<?>> providedTags,
      Node instrumentedNode,
      SourceSection instrumentedNodeSourceSection,
      int inputCount
   ) {
      ProbeNode.InputChildContextLookup visitor = new ProbeNode.InputChildContextLookup(
         binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection, inputCount
      );
      NodeUtil.forEachChild(instrumentedNode, visitor);
      return visitor.foundContexts;
   }

   private static int indexOfChild(
      EventBinding.Source<?> binding,
      RootNode rootNode,
      Set<Class<?>> providedTags,
      Node instrumentedNode,
      SourceSection instrumentedNodeSourceSection,
      Node lookupChild
   ) {
      ProbeNode.InputChildIndexLookup visitor = new ProbeNode.InputChildIndexLookup(
         binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection, lookupChild
      );
      NodeUtil.forEachChild(instrumentedNode, visitor);
      return visitor.found ? visitor.index : -1;
   }

   private static int countChildren(
      EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection
   ) {
      ProbeNode.InputChildIndexLookup visitor = new ProbeNode.InputChildIndexLookup(
         binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection, null
      );
      NodeUtil.forEachChild(instrumentedNode, visitor);
      return visitor.index;
   }

   private ProbeNode.EventChainNode findParentChain(VirtualFrame frame, EventBinding<?> binding) {
      Node node;
      for (node = this.getParent().getParent(); node != null; node = node.getParent()) {
         if (node instanceof InstrumentableNode.WrapperNode) {
            ProbeNode probe = ((InstrumentableNode.WrapperNode)node).getProbeNode();
            ProbeNode.EventChainNode c = probe.lazyUpdate(frame);
            if (c != null) {
               c = c.find(binding);
            }

            if (c != null) {
               return c;
            }
         } else if (node instanceof RootNode) {
            break;
         }
      }

      if (node == null) {
         throw new IllegalStateException("The AST node is not yet adopted. ");
      } else {
         return null;
      }
   }

   private ExecutionEventNode createEventNode(EventBinding.Source<?> binding, Object element) {
      try {
         ExecutionEventNode eventNode = ((ExecutionEventNodeFactory)element).create(this.context);
         if (eventNode != null && eventNode.getParent() != null) {
            throw new IllegalStateException(String.format("Returned EventNode %s was already adopted by another AST.", eventNode));
         } else {
            return eventNode;
         }
      } catch (Throwable var5) {
         exceptionEventForClientInstrument(binding, "ProbeNodeFactory.create", var5);
         return null;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static void exceptionEventForClientInstrument(EventBinding.Source<?> b, String eventName, Throwable t) {
      if (t instanceof ThreadDeath) {
         throw (ThreadDeath)t;
      } else if (!(b.getInstrumenter() instanceof InstrumentationHandler.InstrumentClientInstrumenter)) {
         throw (RuntimeException)sthrow(RuntimeException.class, t);
      } else {
         InstrumentationHandler.InstrumentClientInstrumenter instrumenter = (InstrumentationHandler.InstrumentClientInstrumenter)b.getInstrumenter();
         Object probeInstrument = instrumenter.getEnv().getPolyglotInstrument();
         if (InstrumentAccessor.engineAccess().isInstrumentExceptionsAreThrown(probeInstrument)) {
            throw (RuntimeException)sthrow(RuntimeException.class, t);
         } else {
            TruffleLogger logger = InstrumentAccessor.engineAccess().getLogger(probeInstrument, null);
            String message = String.format(
               "Event %s failed for instrument class %s and listener/factory %s.", eventName, instrumenter.getInstrumentClassName(), b.getElement()
            );
            logger.log(Level.SEVERE, message, t);
         }
      }
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.NONE;
   }

   private static boolean checkInteropType(Object value, EventBinding.Source<?> binding) {
      if (value != null && value != UNWIND_ACTION_REENTER && value != UNWIND_ACTION_IGNORED && !InstrumentAccessor.ACCESSOR.isTruffleObject(value)) {
         Class<?> clazz = value.getClass();
         if (clazz != Byte.class
            && clazz != Short.class
            && clazz != Integer.class
            && clazz != Long.class
            && clazz != Float.class
            && clazz != Double.class
            && clazz != Character.class
            && clazz != Boolean.class
            && clazz != String.class
            && clazz != TruffleString.class) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ClassCastException ccex = new ClassCastException(clazz.getName() + " isn't allowed Truffle interop type!");
            if (binding.isLanguageBinding()) {
               throw ccex;
            }

            exceptionEventForClientInstrument(binding, "onUnwind", ccex);
            return false;
         }
      }

      return true;
   }

   private static Object mergePostUnwindReturns(Object r1, Object r2) {
      if (r1 == null || r2 == null) {
         return null;
      } else if (r1 == UNWIND_ACTION_IGNORED) {
         return r2;
      } else if (r2 == UNWIND_ACTION_IGNORED) {
         return r1;
      } else {
         return r1 != UNWIND_ACTION_REENTER && r2 != UNWIND_ACTION_REENTER ? r1 : UNWIND_ACTION_REENTER;
      }
   }

   private static <T extends Throwable> T sthrow(Class<T> type, Throwable t) throws T {
      throw t;
   }

   abstract static class EventChainNode extends Node {
      private static final int SEEN_EXCEPTION_ON_ENTER = 1;
      private static final int SEEN_EXCEPTION_ON_RETURN = 2;
      private static final int SEEN_EXCEPTION_ON_RETURN_EXCEPTIONAL = 4;
      private static final int SEEN_EXCEPTION_ON_INPUT_VALUE = 8;
      private static final int SEEN_EXCEPTION_ON_UNWIND = 16;
      private static final int SEEN_EXCEPTION_HAS_NEXT = 32;
      private static final int SEEN_EXCEPTION_OTHER = 128;
      private static final int SEEN_UNWIND_ON_ENTER = 256;
      private static final int SEEN_UNWIND_ON_RETURN = 512;
      private static final int SEEN_UNWIND_ON_RETURN_EXCEPTIONAL = 1024;
      private static final int SEEN_UNWIND_ON_INPUT_VALUE = 2048;
      private static final int SEEN_UNWIND_HAS_NEXT = 4096;
      private final EventBinding.Source<?> binding;
      @Node.Child
      private ProbeNode.EventChainNode next;
      @CompilerDirectives.CompilationFinal
      private ProbeNode.EventChainNode previous;
      @CompilerDirectives.CompilationFinal
      private int seen;

      EventChainNode(EventBinding.Source<?> binding) {
         this.binding = binding;
      }

      final ProbeNode findProbe() {
         Node parent = this;

         while (parent != null && !(parent instanceof ProbeNode)) {
            parent = parent.getParent();
         }

         return (ProbeNode)parent;
      }

      final void setNext(ProbeNode.EventChainNode next) {
         this.next = this.insert(next);
         next.previous = this;
      }

      EventBinding.Source<?> getBinding() {
         return this.binding;
      }

      ProbeNode.EventChainNode getNext() {
         return this.next;
      }

      @Override
      public final NodeCost getCost() {
         return NodeCost.NONE;
      }

      final void profileBranch(int flag) {
         if ((this.seen & flag) == 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.seen |= flag;
         }
      }

      static void onDispose(ProbeNode.EventChainNode eventChain, EventContext context, VirtualFrame frame) {
         CompilerAsserts.neverPartOfCompilation();
         ProbeNode.EventChainNode chainNode = eventChain;

         RuntimeException prevError;
         for (prevError = null; chainNode != null; chainNode = chainNode.next) {
            try {
               chainNode.innerOnDispose(context, frame);
            } catch (Throwable var6) {
               prevError = chainNode.handleError("onDispose", prevError, var6);
            }
         }

         if (prevError != null) {
            throw prevError;
         }
      }

      private RuntimeException handleError(String eventName, RuntimeException previousError, Throwable newError) {
         if (this.binding.isLanguageBinding()) {
            if (previousError != null) {
               this.profileBranch(32);
               addSuppressedException(previousError, newError);
               return previousError;
            } else {
               return (RuntimeException)newError;
            }
         } else {
            this.profileBranch(128);
            ProbeNode.exceptionEventForClientInstrument(this.binding, eventName, newError);
            return previousError;
         }
      }

      @CompilerDirectives.TruffleBoundary
      private static void addSuppressedException(Throwable prev, Throwable t) {
         prev.addSuppressed(t);
      }

      protected abstract void innerOnDispose(EventContext context, VirtualFrame frame);

      @ExplodeLoop
      static void onEnter(ProbeNode.EventChainNode eventChain, EventContext context, VirtualFrame frame) {
         ProbeNode.EventChainNode current = eventChain;
         UnwindException unwind = null;

         RuntimeException prevError;
         for (prevError = null; current != null; current = current.next) {
            try {
               current.innerOnEnter(context, frame);
            } catch (UnwindException var7) {
               current.profileBranch(256);
               unwind = handleUnwind(current, unwind, var7);
            } catch (Throwable var8) {
               current.profileBranch(1);
               prevError = current.handleError("onEnter", prevError, var8);
            }
         }

         if (prevError != null) {
            throw prevError;
         } else if (unwind != null) {
            throw unwind;
         }
      }

      protected abstract void innerOnEnter(EventContext context, VirtualFrame frame);

      @ExplodeLoop
      static void onInputValue(
         ProbeNode.EventChainNode eventChain,
         EventContext context,
         VirtualFrame frame,
         EventBinding<?> inputBinding,
         EventContext inputContext,
         int inputIndex,
         Object inputValue
      ) {
         ProbeNode.EventChainNode current = eventChain.getLast();
         UnwindException unwind = null;

         RuntimeException prevError;
         for (prevError = null; current != null; current = current.previous) {
            try {
               if (current.binding == inputBinding) {
                  current.innerOnInputValue(context, frame, current.binding, inputContext, inputIndex, inputValue);
               }
            } catch (UnwindException var11) {
               current.profileBranch(2048);
               unwind = handleUnwind(current, unwind, var11);
            } catch (Throwable var12) {
               current.profileBranch(8);
               prevError = current.handleError("onInputValue", prevError, var12);
            }
         }

         if (prevError != null) {
            throw prevError;
         } else if (unwind != null) {
            throw unwind;
         }
      }

      private static UnwindException handleUnwind(ProbeNode.EventChainNode current, UnwindException unwind, UnwindException ex) {
         ex.thrownFromBinding(current.binding);
         return current.mergeUnwind(unwind, ex);
      }

      private UnwindException mergeUnwind(UnwindException unwind, UnwindException other) {
         if (unwind != null && unwind != other) {
            this.profileBranch(4096);
            unwind.addNext(other);
            return unwind;
         } else {
            return other;
         }
      }

      protected abstract void innerOnInputValue(
         EventContext context, VirtualFrame frame, EventBinding<?> targetBinding, EventContext inputContext, int inputIndex, Object inputValue
      );

      @ExplodeLoop
      private ProbeNode.EventChainNode getLast() {
         ProbeNode.EventChainNode current = this;

         while (current.next != null) {
            current = current.next;
         }

         CompilerAsserts.partialEvaluationConstant(current);
         return current;
      }

      @ExplodeLoop
      static void onReturnValue(ProbeNode.EventChainNode chain, EventContext context, VirtualFrame frame, Object result) {
         ProbeNode.EventChainNode current = chain.getLast();
         UnwindException unwind = null;

         RuntimeException prevError;
         for (prevError = null; current != null; current = current.previous) {
            try {
               current.innerOnReturnValue(context, frame, result);
            } catch (UnwindException var8) {
               current.profileBranch(512);
               unwind = handleUnwind(current, unwind, var8);
            } catch (Throwable var9) {
               current.profileBranch(2);
               prevError = current.handleError("onInputValue", prevError, var9);
            }
         }

         if (prevError != null) {
            throw prevError;
         } else if (unwind != null) {
            throw unwind;
         }
      }

      protected abstract void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result);

      @ExplodeLoop
      static void onReturnExceptional(ProbeNode.EventChainNode chainNode, EventContext context, VirtualFrame frame, Throwable exception) {
         UnwindException unwind = null;
         ProbeNode.EventChainNode current = chainNode.getLast();

         RuntimeException prevError;
         for (prevError = null; current != null; current = current.previous) {
            try {
               current.innerOnReturnExceptional(context, frame, exception);
            } catch (UnwindException var8) {
               current.profileBranch(1024);
               unwind = handleUnwind(current, unwind, var8);
            } catch (Throwable var9) {
               current.profileBranch(4);
               prevError = current.handleError("onInputValue", prevError, var9);
            }
         }

         if (prevError != null) {
            throw prevError;
         } else if (unwind != null) {
            throw unwind;
         }
      }

      protected abstract void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception);

      private boolean containsBinding(UnwindException unwind) {
         if (unwind.getBinding() == this.binding) {
            return true;
         } else {
            UnwindException nextUnwind = unwind.getNext();
            if (nextUnwind != null) {
               this.profileBranch(4096);
               return this.containsBindingBoundary(nextUnwind);
            } else {
               return false;
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private boolean containsBindingBoundary(UnwindException unwind) {
         return this.containsBinding(unwind);
      }

      private Object getInfo(UnwindException unwind) {
         if (unwind.getBinding() == this.binding) {
            return unwind.getInfo();
         } else {
            UnwindException nextUnwind = unwind.getNext();
            if (nextUnwind != null) {
               this.profileBranch(4096);
               return this.getInfoBoundary(nextUnwind);
            } else {
               return false;
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object getInfoBoundary(UnwindException unwind) {
         return this.getInfo(unwind);
      }

      private void reset(UnwindException unwind) {
         if (unwind.getBinding() == this.binding) {
            unwind.resetThread();
         } else {
            UnwindException nextUnwind = unwind.getNext();
            if (nextUnwind != null) {
               this.profileBranch(4096);
               unwind.resetBoundary(this.binding);
            }
         }
      }

      @ExplodeLoop
      static Object onUnwind(ProbeNode.EventChainNode eventChain, EventContext context, VirtualFrame frame, UnwindException unwind) {
         ProbeNode.EventChainNode current = eventChain;
         RuntimeException prevError = null;

         Object ret;
         for (ret = null; current != null; current = current.next) {
            Object nextRet = null;
            if (current.containsBinding(unwind)) {
               try {
                  nextRet = current.innerOnUnwind(context, frame, current.getInfo(unwind));
               } catch (Throwable var9) {
                  current.profileBranch(16);
                  prevError = current.handleError("onUnwind", prevError, var9);
               }

               if (nextRet != null) {
                  assert ProbeNode.checkInteropType(nextRet, current.binding);

                  current.reset(unwind);
               }
            } else {
               nextRet = ProbeNode.UNWIND_ACTION_IGNORED;
            }

            if (current == eventChain) {
               ret = nextRet;
            } else {
               ret = ProbeNode.mergePostUnwindReturns(ret, nextRet);
            }
         }

         if (prevError != null) {
            throw prevError;
         } else {
            return ret;
         }
      }

      protected abstract Object innerOnUnwind(EventContext context, VirtualFrame frame, Object info);

      ProbeNode.EventChainNode find(EventBinding<?> b) {
         if (this.binding == b) {
            assert this.next == null || this.next.find(b) == null : "only one chain entry per binding allowed";

            return this;
         } else {
            return this.next != null ? this.next.find(b) : null;
         }
      }
   }

   private static class EventFilterChainNode extends ProbeNode.EventChainNode {
      private final ExecutionEventListener listener;

      EventFilterChainNode(EventBinding.Source<?> binding, ExecutionEventListener listener) {
         super(binding);
         this.listener = listener;
      }

      @Override
      protected void innerOnInputValue(
         EventContext context, VirtualFrame frame, EventBinding<?> binding, EventContext inputContext, int inputIndex, Object inputValue
      ) {
      }

      @Override
      protected void innerOnEnter(EventContext context, VirtualFrame frame) {
         this.listener.onEnter(context, frame);
      }

      @Override
      protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
         this.listener.onReturnExceptional(context, frame, exception);
      }

      @Override
      protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
         this.listener.onReturnValue(context, frame, result);
      }

      @Override
      protected Object innerOnUnwind(EventContext context, VirtualFrame frame, Object info) {
         return this.listener.onUnwind(context, frame, info);
      }

      @Override
      protected void innerOnDispose(EventContext context, VirtualFrame frame) {
      }
   }

   static class EventProviderChainNode extends ProbeNode.EventChainNode {
      @Node.Child
      private ExecutionEventNode eventNode;

      EventProviderChainNode(EventBinding.Source<?> binding, ExecutionEventNode eventNode) {
         super(binding);
         this.eventNode = eventNode;
      }

      @Override
      protected final void innerOnInputValue(
         EventContext context, VirtualFrame frame, EventBinding<?> binding, EventContext inputContext, int inputIndex, Object inputValue
      ) {
         this.eventNode.onInputValue(frame, inputContext, inputIndex, inputValue);
      }

      @Override
      protected final void innerOnEnter(EventContext context, VirtualFrame frame) {
         this.eventNode.onEnter(frame);
      }

      @Override
      protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
         this.eventNode.onReturnExceptional(frame, exception);
      }

      @Override
      protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
         this.eventNode.onReturnValue(frame, result);
      }

      @Override
      protected Object innerOnUnwind(EventContext context, VirtualFrame frame, Object info) {
         return this.eventNode.onUnwind(frame, info);
      }

      @Override
      protected void innerOnDispose(EventContext context, VirtualFrame frame) {
         this.eventNode.onDispose(frame);
      }
   }

   static class EventProviderWithInputChainNode extends ProbeNode.EventProviderChainNode {
      static final Object[] EMPTY_ARRAY = new Object[0];
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private volatile int[] inputSlots;
      @CompilerDirectives.CompilationFinal
      private volatile FrameDescriptor sourceFrameDescriptor;
      final int inputBaseIndex;
      final int inputCount;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      volatile EventContext[] inputContexts;

      EventProviderWithInputChainNode(EventBinding.Source<?> binding, ExecutionEventNode eventNode, int inputBaseIndex, int inputCount) {
         super(binding, eventNode);
         this.inputBaseIndex = inputBaseIndex;
         this.inputCount = inputCount;
      }

      final int getInputCount() {
         return this.inputCount;
      }

      final EventContext getInputContext(int index) {
         EventContext[] contexts = this.inputContexts;
         if (contexts == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ProbeNode probe = this.findProbe();
            EventContext thisContext = probe.context;
            RootNode rootNode = this.getRootNode();
            Set<Class<?>> providedTags = probe.handler.getProvidedTags(rootNode);
            this.inputContexts = contexts = ProbeNode.findChildContexts(
               this.getBinding(), rootNode, providedTags, thisContext.getInstrumentedNode(), thisContext.getInstrumentedSourceSection(), this.inputCount
            );
         }

         if (contexts == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalStateException("Input event context not yet available. They are only available during event notifications.");
         } else {
            return contexts[index];
         }
      }

      final void saveInputValue(VirtualFrame frame, int inputIndex, Object value) {
         this.verifyIndex(inputIndex);
         if (this.inputSlots == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.initializeSlots(frame);
         }

         assert this.sourceFrameDescriptor == frame.getFrameDescriptor() : "Unstable frame descriptor used by the language.";

         frame.setAuxiliarySlot(this.inputSlots[inputIndex], value);
      }

      private void initializeSlots(VirtualFrame frame) {
         Lock lock = this.getLock();
         lock.lock();

         try {
            if (this.inputSlots == null) {
               if (InstrumentationHandler.TRACE) {
                  InstrumentationHandler.trace("SLOTS: Adding %s save slots for binding %s%n", this.inputCount, this.getBinding().getElement());
               }

               FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
               int[] slots = new int[this.inputCount];

               for (int i = 0; i < this.inputCount; i++) {
                  int slotIndex = this.inputBaseIndex + i;
                  slots[i] = frameDescriptor.findOrAddAuxiliarySlot(
                     new ProbeNode.EventProviderWithInputChainNode.SavedInputValueID(this.getBinding(), slotIndex)
                  );
               }

               this.sourceFrameDescriptor = frameDescriptor;
               this.inputSlots = slots;
            }
         } finally {
            lock.unlock();
         }
      }

      private void verifyIndex(int inputIndex) {
         if (inputIndex >= this.inputCount || inputIndex < 0) {
            CompilerDirectives.transferToInterpreter();
            throw new IllegalArgumentException("Invalid input index.");
         }
      }

      @Override
      protected void innerOnDispose(EventContext context, VirtualFrame frame) {
         Lock lock = this.getLock();
         lock.lock();

         label61: {
            try {
               if (this.inputSlots == null) {
                  break label61;
               }

               this.inputSlots = null;
               RootNode rootNode = context.getInstrumentedNode().getRootNode();
               if (rootNode != null) {
                  FrameDescriptor descriptor = rootNode.getFrameDescriptor();

                  assert descriptor != null;

                  int i = 0;

                  while (true) {
                     if (i >= this.inputCount) {
                        break label61;
                     }

                     int slotIndex = this.inputBaseIndex + i;
                     descriptor.disableAuxiliarySlot(new ProbeNode.EventProviderWithInputChainNode.SavedInputValueID(this.getBinding(), slotIndex));
                     i++;
                  }
               }
            } finally {
               lock.unlock();
            }

            return;
         }

         super.innerOnDispose(context, frame);
      }

      @Override
      protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
         super.innerOnReturnExceptional(context, frame, exception);
         this.clearSlots(frame);
      }

      @Override
      protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
         super.innerOnReturnValue(context, frame, result);
         this.clearSlots(frame);
      }

      @ExplodeLoop
      private void clearSlots(VirtualFrame frame) {
         int[] slots = this.inputSlots;
         if (slots != null && frame.getFrameDescriptor() == this.sourceFrameDescriptor) {
            for (int slot : slots) {
               frame.setAuxiliarySlot(slot, null);
            }
         }
      }

      protected final Object getSavedInputValue(VirtualFrame frame, int inputIndex) {
         this.verifyIndex(inputIndex);
         return this.inputSlots == null ? null : frame.getAuxiliarySlot(this.inputSlots[inputIndex]);
      }

      @ExplodeLoop
      protected final Object[] getSavedInputValues(VirtualFrame frame) {
         int[] slots = this.inputSlots;
         if (slots == null) {
            return EMPTY_ARRAY;
         } else {
            Object[] inputValues;
            if (frame.getFrameDescriptor() == this.sourceFrameDescriptor) {
               inputValues = new Object[slots.length];

               for (int i = 0; i < slots.length; i++) {
                  inputValues[i] = frame.getAuxiliarySlot(slots[i]);
               }
            } else {
               inputValues = new Object[this.inputSlots.length];
            }

            return inputValues;
         }
      }

      static final class SavedInputValueID {
         private final EventBinding<?> binding;
         private final int index;

         SavedInputValueID(EventBinding<?> binding, int index) {
            this.binding = binding;
            this.index = index;
         }

         @Override
         public int hashCode() {
            return 31 * this.binding.hashCode() * 31 + this.index;
         }

         @Override
         public String toString() {
            return "SavedInputValue(binding=" + this.binding.hashCode() + ":" + this.index + ")";
         }

         @Override
         public boolean equals(Object obj) {
            if (this == obj) {
               return true;
            } else if (obj != null && this.getClass() == obj.getClass()) {
               ProbeNode.EventProviderWithInputChainNode.SavedInputValueID other = (ProbeNode.EventProviderWithInputChainNode.SavedInputValueID)obj;
               return this.binding == other.binding && this.index == other.index;
            } else {
               return false;
            }
         }
      }
   }

   private static class InputChildContextLookup extends ProbeNode.InstrumentableChildVisitor {
      EventContext[] foundContexts;
      int index;

      InputChildContextLookup(
         EventBinding.Source<?> binding,
         RootNode rootNode,
         Set<Class<?>> providedTags,
         Node instrumentedNode,
         SourceSection instrumentedNodeSourceSection,
         int childrenCount
      ) {
         super(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection);
         this.foundContexts = new EventContext[childrenCount];
      }

      @Override
      protected boolean visitChild(Node child) {
         Node parent = child.getParent();
         if (parent instanceof InstrumentableNode.WrapperNode) {
            ProbeNode probe = ((InstrumentableNode.WrapperNode)parent).getProbeNode();
            if (this.index < this.foundContexts.length) {
               this.foundContexts[this.index] = probe.context;
               this.index++;
               return true;
            } else {
               assert false;

               this.foundContexts = null;
               return false;
            }
         } else {
            assert false;

            this.foundContexts = null;
            return false;
         }
      }
   }

   private static class InputChildIndexLookup extends ProbeNode.InstrumentableChildVisitor {
      private final Node lookupNode;
      boolean found = false;
      int index;

      InputChildIndexLookup(
         EventBinding.Source<?> binding,
         RootNode rootNode,
         Set<Class<?>> providedTags,
         Node instrumentedNode,
         SourceSection instrumentedNodeSourceSection,
         Node lookupNode
      ) {
         super(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection);
         this.lookupNode = lookupNode;
      }

      @Override
      protected boolean visitChild(Node child) {
         if (this.found) {
            return false;
         } else if (this.lookupNode == child) {
            this.found = true;
            return false;
         } else {
            this.index++;
            return true;
         }
      }
   }

   private static class InputValueChainNode extends ProbeNode.EventChainNode {
      private final EventBinding<?> targetBinding;
      private final ProbeNode parentProbe;
      private final int inputIndex;
      private final EventContext inputContext;

      InputValueChainNode(EventBinding.Source<?> binding, ProbeNode parentProbe, EventContext inputContext, int inputIndex) {
         super(binding);
         this.targetBinding = binding;
         this.parentProbe = parentProbe;
         this.inputContext = inputContext;
         this.inputIndex = inputIndex;
      }

      @Override
      ProbeNode.EventChainNode find(EventBinding<?> b) {
         ProbeNode.EventChainNode next = this.getNext();
         return next == null ? null : next.find(b);
      }

      @Override
      protected Object innerOnUnwind(EventContext context, VirtualFrame frame, Object info) {
         return ProbeNode.UNWIND_ACTION_IGNORED;
      }

      @Override
      protected void innerOnInputValue(
         EventContext context, VirtualFrame frame, EventBinding<?> binding, EventContext inputContext, int inputIndex, Object inputValue
      ) {
      }

      @Override
      protected void innerOnEnter(EventContext context, VirtualFrame frame) {
      }

      @Override
      protected void innerOnDispose(EventContext context, VirtualFrame frame) {
      }

      @Override
      protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
         this.parentProbe.onInputValue(frame, this.targetBinding, this.inputContext, this.inputIndex, result);
      }

      @Override
      protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
      }
   }

   private abstract static class InstrumentableChildVisitor implements NodeVisitor {
      private final EventBinding.Source<?> binding;
      private final Set<Class<?>> providedTags;
      private final RootNode rootNode;
      private final Node instrumentedNode;
      private final SourceSection instrumentedNodeSourceSection;

      InstrumentableChildVisitor(
         EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection
      ) {
         this.binding = binding;
         this.providedTags = providedTags;
         this.rootNode = rootNode;
         this.instrumentedNode = instrumentedNode;
         this.instrumentedNodeSourceSection = instrumentedNodeSourceSection;
      }

      @Override
      public final boolean visit(Node node) {
         SourceSection sourceSection = node.getSourceSection();
         if (InstrumentationHandler.isInstrumentableNode(node)) {
            return !this.binding
                  .isChildInstrumentedFull(this.providedTags, this.rootNode, this.instrumentedNode, this.instrumentedNodeSourceSection, node, sourceSection)
               || this.visitChild(node);
         } else {
            NodeUtil.forEachChild(node, this);
            return true;
         }
      }

      protected abstract boolean visitChild(Node child);
   }

   static class RetiredNodeReference {
      private final WeakReference<Node> node;
      private final Set<Class<? extends Tag>> materializeTags;
      final ProbeNode.RetiredNodeReference next;

      RetiredNodeReference(Node node, Set<Class<? extends Tag>> materializeTags, ProbeNode.RetiredNodeReference next) {
         this.node = new WeakReference<>(node);
         this.materializeTags = materializeTags;
         this.next = next;
      }

      Node getNode() {
         return this.node.get();
      }
   }
}
