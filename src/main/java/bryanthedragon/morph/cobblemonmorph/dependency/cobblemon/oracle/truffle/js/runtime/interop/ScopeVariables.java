package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.FrameDescriptorProvider;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.module.ReadImportBindingNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.OptionalInt;

@ExportLibrary(InteropLibrary.class)
public final class ScopeVariables implements TruffleObject {
   public static final TruffleString RECEIVER_MEMBER = Strings.THIS;
   static final int LIMIT = 4;
   final Frame frame;
   final boolean nodeEnter;
   final Node blockOrRoot;
   final Frame functionFrame;
   private ScopeMembers members;

   private ScopeVariables(Frame frame, boolean nodeEnter, Node blockOrRoot, Frame functionFrame) {
      assert isBlockScopeOrRootNode(blockOrRoot);

      this.frame = frame;
      this.nodeEnter = nodeEnter;
      this.blockOrRoot = blockOrRoot;
      this.functionFrame = functionFrame;
   }

   static boolean isBlockScopeOrRootNode(Node blockOrRoot) {
      return blockOrRoot instanceof BlockScopeNode || blockOrRoot instanceof RootNode;
   }

   public static ScopeVariables create(Frame frame, boolean nodeEnter, Node blockOrRoot, Frame functionFrame) {
      return new ScopeVariables(frame, nodeEnter, blockOrRoot, functionFrame);
   }

   @ExportMessage
   boolean accepts(@Cached(value = "this.blockOrRoot", adopt = false) Node cachedNode, @Cached("this.nodeEnter") boolean cachedNodeEnter) {
      return this.blockOrRoot == cachedNode && this.nodeEnter == cachedNodeEnter;
   }

   @ExportMessage
   boolean isScope() {
      return true;
   }

   @ExportMessage
   boolean hasLanguage() {
      return true;
   }

   @ExportMessage
   Class<? extends TruffleLanguage<?>> getLanguage() {
      return JavaScriptLanguage.class;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasScopeParent() {
      if (this.blockOrRoot instanceof BlockScopeNode) {
         BlockScopeNode blockScopeNode = (BlockScopeNode)this.blockOrRoot;

         Node parentBlock;
         while ((parentBlock = JavaScriptNode.findBlockScopeNode(blockScopeNode.getParent())) != null) {
            if (this.frame == null) {
               return true;
            }

            if (!(blockScopeNode instanceof BlockScopeNode.FrameBlockScopeNode) || !blockScopeNode.isFunctionBlock()) {
               if (parentBlock instanceof BlockScopeNode) {
                  return true;
               }

               if (parentBlock instanceof RootNode && this.functionFrame != null) {
                  return true;
               }
               break;
            }

            if (!(parentBlock instanceof BlockScopeNode)) {
               break;
            }

            blockScopeNode = (BlockScopeNode)parentBlock;
         }
      } else {
         assert this.blockOrRoot instanceof RootNode;

         if (this.frame != null && ScopeFrameNode.isBlockScopeFrame(this.frame) && this.getParentFrame() != null) {
            return true;
         }
      }

      if (this.frame == null) {
         return false;
      } else {
         Frame parentFrame = JSFrameUtil.getParentFrame(this.frame);
         return parentFrame != null && parentFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME;
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getScopeParent() throws UnsupportedMessageException {
      label64:
      if (this.blockOrRoot instanceof BlockScopeNode) {
         BlockScopeNode blockScopeNode = (BlockScopeNode)this.blockOrRoot;
         Frame enclosingFrame = this.frame;

         Node parentBlock;
         while (true) {
            if ((parentBlock = JavaScriptNode.findBlockScopeNode(blockScopeNode.getParent())) == null) {
               break label64;
            }

            if (this.frame == null) {
               return new ScopeVariables(null, true, parentBlock, null);
            }

            if (!(blockScopeNode instanceof BlockScopeNode.FrameBlockScopeNode)) {
               break;
            }

            enclosingFrame = this.getParentFrame();
            if (!blockScopeNode.isFunctionBlock()) {
               break;
            }

            if (!(parentBlock instanceof BlockScopeNode)) {
               break label64;
            }

            blockScopeNode = (BlockScopeNode)parentBlock;

            assert enclosingFrame != null;
         }

         if (parentBlock instanceof BlockScopeNode) {
            return new ScopeVariables(enclosingFrame, true, parentBlock, this.functionFrame);
         }

         if (parentBlock instanceof RootNode && this.functionFrame != null) {
            return new ScopeVariables(this.functionFrame, true, parentBlock, this.functionFrame);
         }
      } else {
         assert this.blockOrRoot instanceof RootNode;

         if (this.frame != null && ScopeFrameNode.isBlockScopeFrame(this.frame)) {
            Frame parentBlockScope = this.getParentFrame();
            if (parentBlockScope != null) {
               return new ScopeVariables(parentBlockScope, true, this.blockOrRoot, null);
            }
         }
      }

      if (this.frame != null) {
         Frame parentFrame = JSFrameUtil.getParentFrame(this.frame);
         if (parentFrame != null && parentFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME) {
            RootNode rootNode = ((RootCallTarget)JSFunction.getCallTarget(JSFrameUtil.getFunctionObject(parentFrame))).getRootNode();
            return new ScopeVariables(parentFrame, true, rootNode, null);
         }
      }

      throw UnsupportedMessageException.create();
   }

   @CompilerDirectives.TruffleBoundary
   private Frame getParentFrame() {
      OptionalInt parentSlot = JSFrameUtil.findOptionalFrameSlotIndex(this.frame.getFrameDescriptor(), ScopeFrameNode.PARENT_SCOPE_IDENTIFIER);
      if (parentSlot.isPresent()) {
         Object parent = this.frame.getObject(parentSlot.getAsInt());
         if (parent instanceof Frame) {
            return (Frame)parent;
         }
      }

      return null;
   }

   @ExportMessage
   boolean hasMembers() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMembers(boolean includeInternal) {
      ScopeMembers m = this.members;
      if (m == null) {
         m = new ScopeMembers(this.frame, this.blockOrRoot, this.functionFrame);
         this.members = m;
      }

      return m;
   }

   @ExportMessage
   boolean isMemberInsertable(String member) {
      return false;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   boolean hasSourceLocation() {
      return this.blockOrRoot.getEncapsulatingSourceSection() != null;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   SourceSection getSourceLocation() throws UnsupportedMessageException {
      Node sourceSectionProvider = this.blockOrRoot;
      if (sourceSectionProvider instanceof BlockScopeNode && ((BlockScopeNode)sourceSectionProvider).isFunctionBlock()) {
         sourceSectionProvider = sourceSectionProvider.getRootNode();
      }

      SourceSection sourceLocation = sourceSectionProvider.getEncapsulatingSourceSection();
      if (sourceLocation == null) {
         throw UnsupportedMessageException.create();
      } else {
         return sourceLocation;
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object toDisplayString(boolean allowSideEffects) {
      RootNode root;
      if (this.blockOrRoot instanceof BlockScopeNode) {
         if (!((BlockScopeNode)this.blockOrRoot).isFunctionBlock()) {
            return "block";
         }

         root = this.blockOrRoot.getRootNode();
      } else {
         root = (RootNode)this.blockOrRoot;
      }

      String name = root.getName();
      return name == null ? "" : name;
   }

   static ScopeVariables.ResolvedSlot findSlot(String memberString, ScopeVariables receiver) {
      CompilerAsserts.neverPartOfCompilation();
      final TruffleString member = Strings.fromJavaString(memberString);
      if (receiver.frame == null) {
         return findSlotWithoutFrame(member, receiver.blockOrRoot);
      } else {
         class SlotVisitor {
            Node descNode = receiver.blockOrRoot;
            int parentSlot = -1;
            int frameLevel = 0;
            int scopeLevel = 0;

            public ScopeVariables.ResolvedSlot accept(FrameDescriptor frameDescriptor, int slot, Frame targetFrame) {
               assert targetFrame.getFrameDescriptor() == frameDescriptor;

               int effectiveScopeLevel = this.scopeLevel;
               if (targetFrame == receiver.functionFrame) {
                  assert receiver.functionFrame.getFrameDescriptor() == frameDescriptor;

                  effectiveScopeLevel = -1;
               }

               Object slotName = frameDescriptor.getSlotName(slot);
               if (ScopeFrameNode.PARENT_SCOPE_IDENTIFIER.equals(slotName)) {
                  this.parentSlot = slot;
               } else if (ScopeFrameNode.EVAL_SCOPE_IDENTIFIER.equals(slotName)) {
                  JSDynamicObject evalScope = (JSDynamicObject)targetFrame.getObject(slot);
                  if (JSRuntime.isObject(evalScope) && DynamicObjectLibrary.getUncached().containsKey(evalScope, member)) {
                     return new ScopeVariables.DynamicScopeResolvedSlot(member, slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                  }
               } else {
                  if (JSFrameUtil.isThisSlot(frameDescriptor, slot) && ScopeVariables.RECEIVER_MEMBER.equals(member)) {
                     return new ScopeVariables.ResolvedThisSlot(slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                  }

                  if (!JSFrameUtil.isInternal(frameDescriptor, slot) && member.equals(slotName)) {
                     if (JSFrameUtil.isImportBinding(frameDescriptor, slot)) {
                        return new ScopeVariables.ResolvedImportSlot(slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                     }

                     return new ScopeVariables.ResolvedSlot(slot, this.frameLevel, effectiveScopeLevel, frameDescriptor);
                  }
               }

               return null;
            }
         }

         SlotVisitor visitor = new SlotVisitor();
         Frame outerFrame = receiver.frame;
         if (receiver.functionFrame != null) {
            FrameDescriptor rootFrameDescriptor = receiver.functionFrame.getFrameDescriptor();

            while (true) {
               if (visitor.descNode instanceof BlockScopeNode) {
                  BlockScopeNode block = (BlockScopeNode)visitor.descNode;
                  visitor.parentSlot = -1;
                  if (block instanceof BlockScopeNode.FrameBlockScopeNode) {
                     FrameDescriptor blockFrameDescriptor = ((BlockScopeNode.FrameBlockScopeNode)block).getFrameDescriptor();

                     assert outerFrame.getFrameDescriptor() == blockFrameDescriptor || block == receiver.blockOrRoot;

                     if (outerFrame.getFrameDescriptor() == blockFrameDescriptor) {
                        for (int i = 0; i < blockFrameDescriptor.getNumberOfSlots(); i++) {
                           ScopeVariables.ResolvedSlot resolvedSlot = visitor.accept(blockFrameDescriptor, i, outerFrame);
                           if (resolvedSlot != null) {
                              return resolvedSlot;
                           }
                        }
                     }
                  }

                  for (int ix = block.getFrameStart(); ix < block.getFrameEnd(); ix++) {
                     ScopeVariables.ResolvedSlot resolvedSlot = visitor.accept(rootFrameDescriptor, ix, receiver.functionFrame);
                     if (resolvedSlot != null) {
                        return resolvedSlot;
                     }
                  }

                  visitor.descNode = JavaScriptNode.findBlockScopeNode(visitor.descNode.getParent());
                  if (visitor.parentSlot < 0) {
                     continue;
                  }

                  Object parent = outerFrame.getObject(visitor.parentSlot);
                  if (parent instanceof Frame) {
                     outerFrame = (Frame)parent;

                     assert outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME;

                     visitor.scopeLevel++;
                     continue;
                  }
               }

               assert receiver.functionFrame.getFrameDescriptor() == rootFrameDescriptor && visitor.frameLevel == 0;

               visitor.scopeLevel = -1;

               for (int slot = 0; slot < rootFrameDescriptor.getNumberOfSlots(); slot++) {
                  if (!JSFrameUtil.isHoistedFromBlock(rootFrameDescriptor, slot)) {
                     ScopeVariables.ResolvedSlot resolvedSlot = visitor.accept(rootFrameDescriptor, slot, receiver.functionFrame);
                     if (resolvedSlot != null) {
                        return resolvedSlot;
                     }
                  }
               }

               outerFrame = JSArguments.getEnclosingFrame(receiver.frame.getArguments());
               visitor.frameLevel = 1;
               break;
            }
         }

         while (outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME) {
            visitor.descNode = JSFunction.getFunctionData(JSFrameUtil.getFunctionObject(outerFrame)).getRootNode();
            visitor.scopeLevel = 0;

            while (true) {
               visitor.parentSlot = -1;

               for (int slotx = 0; slotx < outerFrame.getFrameDescriptor().getNumberOfSlots(); slotx++) {
                  ScopeVariables.ResolvedSlot resolvedSlot = visitor.accept(outerFrame.getFrameDescriptor(), slotx, outerFrame);
                  if (resolvedSlot != null) {
                     return resolvedSlot;
                  }
               }

               if (visitor.parentSlot < 0) {
                  break;
               }

               Object parent = outerFrame.getObject(visitor.parentSlot);
               if (!(parent instanceof Frame)) {
                  break;
               }

               outerFrame = (Frame)parent;

               assert outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME;

               visitor.scopeLevel++;
            }

            outerFrame = JSArguments.getEnclosingFrame(outerFrame.getArguments());
            visitor.frameLevel++;
         }

         return receiver.frame != null && RECEIVER_MEMBER.equals(member) ? new ScopeVariables.ResolvedThisSlot() : null;
      }
   }

   private static ScopeVariables.ResolvedSlot findSlotWithoutFrame(TruffleString member, Node blockOrRootNode) {
      CompilerAsserts.neverPartOfCompilation();

      for (Node descNode = blockOrRootNode;
         descNode != null && descNode instanceof FrameDescriptorProvider;
         descNode = JavaScriptNode.findBlockScopeNode(descNode.getParent())
      ) {
         FrameDescriptor desc = ((FrameDescriptorProvider)descNode).getFrameDescriptor();
         OptionalInt slot = JSFrameUtil.findOptionalFrameSlotIndex(desc, member);
         if (slot.isPresent()) {
            if (JSFrameUtil.isInternal(desc, slot.getAsInt())) {
               return null;
            }

            return new ScopeVariables.ResolvedSlot();
         }
      }

      return null;
   }

   static boolean hasSlot(String member, ScopeVariables receiver) {
      return findSlot(member, receiver) != null;
   }

   static JavaScriptNode findReadNode(ScopeVariables.ResolvedSlot slot) {
      return slot != null ? slot.createReadNode() : null;
   }

   static WriteNode findWriteNode(ScopeVariables.ResolvedSlot slot) {
      return slot != null && slot.isModifiable() ? slot.createWriteNode() : null;
   }

   static Object thisFromFunctionOrArguments(Object[] args) {
      Object function = JSArguments.getFunctionObject(args);
      if (JSFunction.isJSFunction(function)) {
         JSDynamicObject jsFunction = (JSDynamicObject)function;
         return isArrowFunctionWithThisCaptured(jsFunction) ? JSFunction.getLexicalThis(jsFunction) : thisFromArguments(args);
      } else {
         return Undefined.instance;
      }
   }

   static Object thisFromArguments(Object[] args) {
      Object thisObject = JSArguments.getThisObject(args);
      Object function = JSArguments.getFunctionObject(args);
      if (JSFunction.isJSFunction(function) && !JSFunction.isStrict((JSDynamicObject)function)) {
         JSRealm realm = JavaScriptLanguage.getCurrentJSRealm();
         if (thisObject != Undefined.instance && thisObject != Null.instance) {
            thisObject = JSRuntime.toObject(realm.getContext(), thisObject);
         } else {
            thisObject = realm.getGlobalObject();
         }
      }

      return thisObject;
   }

   private static boolean isArrowFunctionWithThisCaptured(JSDynamicObject function) {
      return !JSFunction.isConstructor(function) && JSFunction.isClassPrototypeInitialized(function);
   }

   static class DynamicScopeResolvedSlot extends ScopeVariables.ResolvedSlot {
      final Object key;

      DynamicScopeResolvedSlot(Object key, int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
         super(slot, frameLevel, scopeLevel, descriptor);
         this.key = key;
      }

      @Override
      JavaScriptNode createReadNode() {
         final JavaScriptNode readDynamicScope = super.createReadNode();

         class EvalRead extends JavaScriptNode {
            @Node.Child
            JavaScriptNode getDynamicScope = readDynamicScope;
            @Node.Child
            DynamicObjectLibrary objectLibrary;

            @Override
            public Object execute(VirtualFrame frame) {
               JSDynamicObject scope = (JSDynamicObject)this.getDynamicScope.execute(frame);
               if (!JSRuntime.isObject(scope)) {
                  return Undefined.instance;
               } else {
                  DynamicObjectLibrary lib = this.objectLibrary;
                  if (lib == null) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     if (this.getParent() != null) {
                        lib = this.insert(DynamicObjectLibrary.getFactory().createDispatched(5));
                     } else {
                        lib = DynamicObjectLibrary.getUncached();
                     }

                     this.objectLibrary = lib;
                  }

                  return Properties.getOrDefault(lib, scope, DynamicScopeResolvedSlot.this.key, Undefined.instance);
               }
            }
         }

         return new EvalRead();
      }

      @Override
      WriteNode createWriteNode() {
         final JavaScriptNode readDynamicScope = super.createReadNode();

         class EvalWrite extends JavaScriptNode implements WriteNode {
            @Node.Child
            JavaScriptNode getDynamicScope = readDynamicScope;
            @Node.Child
            DynamicObjectLibrary objectLibrary;

            @Override
            public Object execute(VirtualFrame frame) {
               throw CompilerDirectives.shouldNotReachHere();
            }

            @Override
            public void executeWrite(VirtualFrame frame, Object value) {
               JSDynamicObject scope = (JSDynamicObject)this.getDynamicScope.execute(frame);
               if (JSRuntime.isObject(scope)) {
                  DynamicObjectLibrary lib = this.objectLibrary;
                  if (lib == null) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     if (this.getParent() != null) {
                        lib = this.insert(DynamicObjectLibrary.getFactory().createDispatched(5));
                     } else {
                        lib = DynamicObjectLibrary.getUncached();
                     }

                     this.objectLibrary = lib;
                  }

                  lib.putIfPresent(scope, DynamicScopeResolvedSlot.this.key, value);
               }
            }

            @Override
            public JavaScriptNode getRhs() {
               return null;
            }
         }

         return new EvalWrite();
      }
   }

   @ExportMessage
   static final class IsMemberModifiable {
      @Specialization(guards = "cachedMember.equals(member)", limit = "LIMIT")
      static boolean doCached(
         ScopeVariables receiver, String member, @Cached("member") String cachedMember, @Cached("doGeneric(receiver, member)") boolean cachedResult
      ) {
         assert cachedResult == doGeneric(receiver, member);

         return cachedResult;
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      static boolean doGeneric(ScopeVariables receiver, String member) {
         ScopeVariables.ResolvedSlot slot = ScopeVariables.findSlot(member, receiver);
         return slot != null && slot.isModifiable();
      }
   }

   @ExportMessage
   static final class IsMemberReadable {
      @Specialization(guards = "cachedMember.equals(member)", limit = "LIMIT")
      static boolean doCached(
         ScopeVariables receiver, String member, @Cached("member") String cachedMember, @Cached("doGeneric(receiver, member)") boolean cachedResult
      ) {
         assert cachedResult == doGeneric(receiver, member);

         return cachedResult;
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      static boolean doGeneric(ScopeVariables receiver, String member) {
         return ScopeVariables.hasSlot(member, receiver);
      }
   }

   @ExportMessage
   static final class ReadMember {
      @Specialization(guards = "cachedMember.equals(member)", limit = "LIMIT")
      static Object doCached(
         ScopeVariables receiver,
         String member,
         @Cached("member") String cachedMember,
         @Cached("findSlot(member, receiver)") ScopeVariables.ResolvedSlot resolvedSlot,
         @Cached("findReadNode(resolvedSlot)") JavaScriptNode readNode
      ) throws UnknownIdentifierException {
         return doRead(receiver, cachedMember, readNode, resolvedSlot);
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      static Object doGeneric(ScopeVariables receiver, String member) throws UnknownIdentifierException {
         ScopeVariables.ResolvedSlot resolvedSlot = ScopeVariables.findSlot(member, receiver);
         JavaScriptNode readNode = ScopeVariables.findReadNode(resolvedSlot);
         return doRead(receiver, member, readNode, resolvedSlot);
      }

      private static Object doRead(ScopeVariables receiver, String member, JavaScriptNode readNode, ScopeVariables.ResolvedSlot resolvedSlot) throws UnknownIdentifierException {
         if (readNode == null) {
            throw UnknownIdentifierException.create(member);
         } else {
            Frame frame = resolvedSlot.isFunctionFrame() ? receiver.functionFrame : receiver.frame;
            return frame == null ? Undefined.instance : readNode.execute((VirtualFrame)frame);
         }
      }
   }

   static final class ReadThisNode extends JavaScriptNode {
      @Node.Child
      JavaScriptNode readThis;

      ReadThisNode(JavaScriptNode readThis) {
         this.readThis = readThis;
      }

      @Override
      public Object execute(VirtualFrame frame) {
         if (this.readThis == null) {
            return ScopeVariables.thisFromArguments(frame.getArguments());
         } else {
            Object thisValue = this.readThis.execute(frame);
            return thisValue == Undefined.instance ? ScopeVariables.thisFromFunctionOrArguments(frame.getArguments()) : thisValue;
         }
      }
   }

   static class ResolvedImportSlot extends ScopeVariables.ResolvedSlot {
      ResolvedImportSlot(int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
         super(slot, frameLevel, scopeLevel, descriptor);
      }

      @Override
      JavaScriptNode createReadNode() {
         return (JavaScriptNode)(!this.hasSlot() ? JSConstantNode.createUndefined() : ReadImportBindingNode.create(super.createReadNode()));
      }
   }

   static class ResolvedSlot {
      final int slot;
      final int frameLevel;
      final int scopeLevel;
      final FrameDescriptor descriptor;

      ResolvedSlot(int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
         this.slot = slot;
         this.frameLevel = frameLevel;
         this.scopeLevel = scopeLevel;
         this.descriptor = descriptor;
      }

      ResolvedSlot() {
         this(-1, -1, -1, null);
      }

      JavaScriptNode createReadNode() {
         if (!this.hasSlot()) {
            return JSConstantNode.createUndefined();
         } else {
            ScopeFrameNode scopeFrameNode = this.createScopeFrameNode();
            return JSReadFrameSlotNode.create(
               JSFrameSlot.fromIndexedFrameSlot(this.descriptor, this.slot), scopeFrameNode, JSFrameUtil.hasTemporalDeadZone(this.descriptor, this.slot)
            );
         }
      }

      WriteNode createWriteNode() {
         if (!this.hasSlot()) {
            return null;
         } else {
            ScopeFrameNode scopeFrameNode = this.createScopeFrameNode();
            return JSWriteFrameSlotNode.create(
               JSFrameSlot.fromIndexedFrameSlot(this.descriptor, this.slot), scopeFrameNode, null, JSFrameUtil.hasTemporalDeadZone(this.descriptor, this.slot)
            );
         }
      }

      ScopeFrameNode createScopeFrameNode() {
         return this.isFunctionFrame() ? ScopeFrameNode.createCurrent() : ScopeFrameNode.create(this.frameLevel, this.scopeLevel, null);
      }

      boolean isModifiable() {
         return this.hasSlot()
            && !JSFrameUtil.isConst(this.descriptor, this.slot)
            && !JSFrameUtil.isThisSlot(this.descriptor, this.slot)
            && !JSFrameUtil.isImportBinding(this.descriptor, this.slot);
      }

      boolean hasSlot() {
         return this.slot >= 0;
      }

      boolean isFunctionFrame() {
         return this.scopeLevel < 0;
      }

      @Override
      public String toString() {
         return this.hasSlot()
            ? this.getClass().getSimpleName()
               + "("
               + this.descriptor.getSlotName(this.slot)
               + ", #"
               + this.slot
               + ", "
               + this.frameLevel
               + "/"
               + this.scopeLevel
               + ")"
            : super.toString();
      }
   }

   static class ResolvedThisSlot extends ScopeVariables.ResolvedSlot {
      ResolvedThisSlot(int slot, int frameLevel, int scopeLevel, FrameDescriptor descriptor) {
         super(slot, frameLevel, scopeLevel, descriptor);
      }

      ResolvedThisSlot() {
      }

      @Override
      JavaScriptNode createReadNode() {
         return new ScopeVariables.ReadThisNode(this.hasSlot() ? super.createReadNode() : null);
      }
   }

   @ExportMessage
   static final class WriteMember {
      @Specialization(guards = "cachedMember.equals(member)", limit = "LIMIT")
      static void doCached(
         ScopeVariables receiver,
         String member,
         Object value,
         @Cached("member") String cachedMember,
         @Cached("findSlot(member, receiver)") ScopeVariables.ResolvedSlot resolvedSlot,
         @Cached("findWriteNode(resolvedSlot)") WriteNode writeNode
      ) throws UnknownIdentifierException {
         doWrite(receiver, cachedMember, value, writeNode, resolvedSlot);
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      static void doGeneric(ScopeVariables receiver, String member, Object value) throws UnknownIdentifierException {
         ScopeVariables.ResolvedSlot resolvedSlot = ScopeVariables.findSlot(member, receiver);
         WriteNode writeNode = ScopeVariables.findWriteNode(resolvedSlot);
         doWrite(receiver, member, value, writeNode, resolvedSlot);
      }

      private static void doWrite(ScopeVariables receiver, String member, Object value, WriteNode writeNode, ScopeVariables.ResolvedSlot resolvedSlot) throws UnknownIdentifierException {
         if (writeNode == null) {
            throw UnknownIdentifierException.create(member);
         } else {
            Frame frame = resolvedSlot.isFunctionFrame() ? receiver.functionFrame : receiver.frame;
            if (frame == null) {
               throw UnknownIdentifierException.create(member);
            } else {
               writeNode.executeWrite((VirtualFrame)frame, value);
            }
         }
      }
   }
}
