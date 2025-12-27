package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.BlockNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.FrameDescriptorProvider;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.List;

@ExportLibrary(InteropLibrary.class)
final class ScopeMembers implements TruffleObject {
   private final Frame frame;
   private final Node blockOrRoot;
   private final Frame functionFrame;
   private Object[] members;

   ScopeMembers(Frame frame, Node blockOrRoot, Frame functionFrame) {
      assert ScopeVariables.isBlockScopeOrRootNode(blockOrRoot);

      this.frame = frame;
      this.blockOrRoot = blockOrRoot;
      this.functionFrame = functionFrame;
   }

   @ExportMessage
   boolean hasArrayElements() {
      return true;
   }

   @ExportMessage
   Object readArrayElement(long index) throws InvalidArrayIndexException {
      Object[] allMembers = this.getAllMembers();
      if (0L <= index && index < allMembers.length) {
         return allMembers[(int)index];
      } else {
         throw InvalidArrayIndexException.create(index);
      }
   }

   @ExportMessage
   long getArraySize() {
      return this.getAllMembers().length;
   }

   @ExportMessage
   boolean isArrayElementReadable(long index) {
      return 0L <= index && index < this.getAllMembers().length;
   }

   private Object[] getAllMembers() {
      if (CompilerDirectives.injectBranchProbability(1.0E-4, this.members == null)) {
         this.members = this.collectAllMembers();
      }

      return this.members;
   }

   @CompilerDirectives.TruffleBoundary
   private Object[] collectAllMembers() {
      final List<Object> membersList = new ArrayList<>();
      if (this.frame == null) {
         collectMembersWithoutFrame(membersList, this.blockOrRoot);
      } else {
         class SlotVisitor {
            Node descNode = ScopeMembers.this.blockOrRoot;
            int parentSlot = -1;
            boolean seenThis;

            public void accept(FrameDescriptor frameDescriptor, int slot, Frame targetFrame) {
               assert targetFrame.getFrameDescriptor() == frameDescriptor;

               Object slotName = frameDescriptor.getSlotName(slot);
               if (ScopeFrameNode.PARENT_SCOPE_IDENTIFIER.equals(slotName)) {
                  this.parentSlot = slot;
               } else if (ScopeFrameNode.EVAL_SCOPE_IDENTIFIER.equals(slotName)) {
                  JSDynamicObject evalScope = (JSDynamicObject)targetFrame.getObject(slot);
                  DynamicObjectLibrary objLib = DynamicObjectLibrary.getUncached();

                  for (Object key : objLib.getKeyArray(evalScope)) {
                     if (key instanceof TruffleString) {
                        membersList.add(new ScopeMembers.Key((TruffleString)key, this.descNode));
                     }
                  }
               } else if (JSFrameUtil.isThisSlot(frameDescriptor, slot)) {
                  membersList.add(new ScopeMembers.Key(ScopeVariables.RECEIVER_MEMBER, this.descNode, slot));
                  this.seenThis = true;
               } else if (!JSFrameUtil.isInternal(frameDescriptor, slot)) {
                  assert slotName instanceof TruffleString;

                  if (!ScopeMembers.isUnsetFrameSlot(targetFrame, slot)) {
                     membersList.add(new ScopeMembers.Key((TruffleString)slotName, this.descNode, slot));
                  }
               }
            }
         }

         SlotVisitor visitor = new SlotVisitor();
         Frame outerFrame = this.frame;
         if (this.functionFrame != null) {
            FrameDescriptor rootFrameDescriptor = this.functionFrame.getFrameDescriptor();

            while (visitor.descNode instanceof BlockScopeNode) {
               BlockScopeNode block = (BlockScopeNode)visitor.descNode;
               visitor.parentSlot = -1;
               if (block instanceof BlockScopeNode.FrameBlockScopeNode) {
                  FrameDescriptor blockFrameDescriptor = ((BlockScopeNode.FrameBlockScopeNode)block).getFrameDescriptor();
                  if (outerFrame.getFrameDescriptor() == blockFrameDescriptor) {
                     for (int i = 0; i < blockFrameDescriptor.getNumberOfSlots(); i++) {
                        visitor.accept(blockFrameDescriptor, i, outerFrame);
                     }
                  }
               }

               for (int i = block.getFrameStart(); i < block.getFrameEnd(); i++) {
                  visitor.accept(rootFrameDescriptor, i, this.functionFrame);
               }

               visitor.descNode = JavaScriptNode.findBlockScopeNode(visitor.descNode.getParent());
               if (visitor.parentSlot >= 0) {
                  Object parent = outerFrame.getObject(visitor.parentSlot);
                  if (!(parent instanceof Frame)) {
                     break;
                  }

                  outerFrame = (Frame)parent;

                  assert outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME;
               }
            }

            assert this.functionFrame.getFrameDescriptor() == rootFrameDescriptor && visitor.descNode instanceof RootNode;

            for (int slot = 0; slot < rootFrameDescriptor.getNumberOfSlots(); slot++) {
               if (!JSFrameUtil.isHoistedFromBlock(rootFrameDescriptor, slot)) {
                  visitor.accept(rootFrameDescriptor, slot, this.functionFrame);
               }
            }

            if (!visitor.seenThis) {
               membersList.add(new ScopeMembers.Key(ScopeVariables.RECEIVER_MEMBER, visitor.descNode));
            }

            outerFrame = JSArguments.getEnclosingFrame(this.frame.getArguments());
         }

         for (; outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME; outerFrame = JSArguments.getEnclosingFrame(outerFrame.getArguments())) {
            visitor.descNode = JSFunction.getFunctionData(JSFrameUtil.getFunctionObject(outerFrame)).getRootNode();
            visitor.seenThis = false;

            while (true) {
               visitor.parentSlot = -1;

               for (int slotx = 0; slotx < outerFrame.getFrameDescriptor().getNumberOfSlots(); slotx++) {
                  visitor.accept(outerFrame.getFrameDescriptor(), slotx, outerFrame);
               }

               if (visitor.parentSlot < 0) {
                  break;
               }

               Object parentx = outerFrame.getObject(visitor.parentSlot);
               if (!(parentx instanceof Frame)) {
                  break;
               }

               outerFrame = (Frame)parentx;

               assert outerFrame != JSFrameUtil.NULL_MATERIALIZED_FRAME;
            }

            if (!visitor.seenThis) {
               membersList.add(new ScopeMembers.Key(ScopeVariables.RECEIVER_MEMBER, visitor.descNode));
            }
         }
      }

      return membersList.toArray();
   }

   private static void collectMembersWithoutFrame(List<Object> membersList, Node blockOrRootNode) {
      for (Node descNode = blockOrRootNode;
         descNode != null && descNode instanceof FrameDescriptorProvider;
         descNode = JavaScriptNode.findBlockScopeNode(descNode.getParent())
      ) {
         FrameDescriptor desc = ((FrameDescriptorProvider)descNode).getFrameDescriptor();

         for (int slot = 0; slot < desc.getNumberOfSlots(); slot++) {
            if (!JSFrameUtil.isInternal(desc, slot)) {
               Object slotName = desc.getSlotName(slot);
               membersList.add(new ScopeMembers.Key((TruffleString)slotName, descNode, slot));
            }
         }
      }
   }

   static boolean isUnsetFrameSlot(Frame frame, int slot) {
      if (frame != null) {
         byte tag = frame.getTag(slot);
         if (tag == FrameSlotKind.Illegal.tag) {
            return true;
         }

         if (tag == FrameSlotKind.Object.tag) {
            Object value = frame.getObject(slot);
            if (value == null || value == Dead.instance() || value instanceof Frame) {
               return true;
            }
         }
      }

      return false;
   }

   @ExportLibrary(InteropLibrary.class)
   static final class Key implements TruffleObject {
      private final TruffleString name;
      private final Node blockOrRoot;
      private final int slot;
      private SourceSection sourceLocation;

      Key(TruffleString name, Node blockOrRoot) {
         this(name, blockOrRoot, -1);
      }

      Key(TruffleString name, Node blockOrRoot, int slot) {
         this.name = name;
         this.slot = slot;
         this.blockOrRoot = blockOrRoot;
      }

      @ExportMessage
      boolean isString() {
         return true;
      }

      @ExportMessage
      TruffleString asTruffleString() {
         return this.name;
      }

      @ExportMessage
      String asString() {
         return Strings.toJavaString(this.name);
      }

      @Override
      public String toString() {
         return this.asString();
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      boolean hasSourceLocation() {
         return this.getOrFindSourceLocation().isAvailable();
      }

      @ExportMessage
      @CompilerDirectives.TruffleBoundary
      SourceSection getSourceLocation() throws UnsupportedMessageException {
         if (!this.hasSourceLocation()) {
            throw UnsupportedMessageException.create();
         } else {
            return this.sourceLocation;
         }
      }

      private SourceSection getOrFindSourceLocation() {
         CompilerAsserts.neverPartOfCompilation();
         if (this.sourceLocation == null && this.blockOrRoot != null) {
            this.sourceLocation = this.findSourceLocation();
         }

         if (this.sourceLocation == null) {
            this.sourceLocation = JSBuiltin.createSourceSection();
         }

         return this.sourceLocation;
      }

      private SourceSection findSourceLocation() {
         if (this.hasSlot()) {
            class DeclarationFinder implements NodeVisitor {
               JavaScriptNode found;

               @Override
               public boolean visit(Node node) {
                  if (node instanceof JavaScriptNode) {
                     if (node instanceof JSWriteFrameSlotNode) {
                        JSWriteFrameSlotNode write = (JSWriteFrameSlotNode)node;
                        if (write.getSlotIndex() == Key.this.slot && write.hasSourceSection()) {
                           this.found = write;
                           return false;
                        }
                     }

                     return true;
                  } else if (node == Key.this.blockOrRoot) {
                     return true;
                  } else {
                     return node instanceof BlockNode ? true : node instanceof ScopeFrameNode;
                  }
               }
            }

            DeclarationFinder finder = new DeclarationFinder();
            this.blockOrRoot.accept(finder);
            if (finder.found != null) {
               return finder.found.getSourceSection();
            }
         }

         return this.blockOrRoot.getEncapsulatingSourceSection();
      }

      private boolean hasSlot() {
         return this.slot >= 0;
      }
   }
}
