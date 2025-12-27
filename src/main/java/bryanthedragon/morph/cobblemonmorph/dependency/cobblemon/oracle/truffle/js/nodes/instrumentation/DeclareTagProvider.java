package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class DeclareTagProvider {
   public static JavaScriptNode createMaterializedFunctionBodyNode(JavaScriptNode original, JavaScriptNode body, FrameDescriptor frameDescriptor) {
      return new DeclareTagProvider.MaterializedFunctionBodyNode(original, body, frameDescriptor);
   }

   public static JavaScriptNode createMaterializedBlockNode(
      JavaScriptNode original,
      JavaScriptNode block,
      int blockScopeSlot,
      FrameDescriptor frameDescriptor,
      int parentSlot,
      boolean functionBlock,
      boolean captureFunctionFrame,
      boolean generatorFunctionBlock,
      boolean hasParentBlock,
      int start,
      int end
   ) {
      return new DeclareTagProvider.MaterializedFrameBlockScopeNode(
         original, block, blockScopeSlot, frameDescriptor, parentSlot, functionBlock, captureFunctionFrame, generatorFunctionBlock, hasParentBlock, start, end
      );
   }

   public static boolean isMaterializedFrameProvider(JavaScriptNode node) {
      return node instanceof DeclareTagProvider.MaterializedFrameBlockScopeNode || node instanceof DeclareTagProvider.MaterializedFunctionBodyNode;
   }

   public static NodeObjectDescriptor createDeclareNodeObject(Object name, Object type) {
      NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor();
      descriptor.addProperty("declarationName", name);
      descriptor.addProperty("declarationType", type);
      return descriptor;
   }

   private DeclareTagProvider() {
   }

   private static JavaScriptNode[] initDeclarations(FrameDescriptor frameDescriptor, JavaScriptNode locationNode) {
      assert locationNode != null;

      if (frameDescriptor == null) {
         return new JavaScriptNode[0];
      } else {
         List<Integer> slots = new ArrayList<>();

         for (int i = 0; i < frameDescriptor.getNumberOfSlots(); i++) {
            if (!JSFrameUtil.isInternal(frameDescriptor, i) && !JSFrameUtil.isHoistable(frameDescriptor, i)) {
               slots.add(i);
            }
         }

         JavaScriptNode[] declarations = new JavaScriptNode[slots.size()];

         for (int ix = 0; ix < slots.size(); ix++) {
            JavaScriptNode declaration = new DeclareTagProvider.DeclareProviderNode(frameDescriptor, slots.get(ix));
            JavaScriptNode.transferSourceSection(locationNode, declaration);
            declarations[ix] = declaration;
         }

         return declarations;
      }
   }

   private static class DeclareProviderNode extends JavaScriptNode {
      private final FrameDescriptor frameDescriptor;
      private final int slotIndex;

      DeclareProviderNode(FrameDescriptor frameDescriptor, int slotIndex) {
         this.frameDescriptor = frameDescriptor;
         this.slotIndex = slotIndex;
      }

      @Override
      public Object execute(VirtualFrame frame) {
         return Undefined.instance;
      }

      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return tag == JSTags.DeclareTag.class ? true : super.hasTag(tag);
      }

      @Override
      public boolean isInstrumentable() {
         return true;
      }

      @Override
      public Object getNodeObject() {
         String type;
         if (JSFrameUtil.isConst(this.frameDescriptor, this.slotIndex)) {
            type = "const";
         } else if (JSFrameUtil.isLet(this.frameDescriptor, this.slotIndex)) {
            type = "let";
         } else {
            type = "var";
         }

         return DeclareTagProvider.createDeclareNodeObject(this.frameDescriptor.getSlotName(this.slotIndex), type);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new DeclareTagProvider.DeclareProviderNode(this.frameDescriptor, this.slotIndex);
      }
   }

   private static class MaterializedFrameBlockScopeNode extends BlockScopeNode.FrameBlockScopeNode {
      @Node.Children
      private JavaScriptNode[] declarations;

      protected MaterializedFrameBlockScopeNode(
         JavaScriptNode original,
         JavaScriptNode block,
         int blockScopeSlot,
         FrameDescriptor frameDescriptor,
         int parentSlot,
         boolean functionBlock,
         boolean captureFunctionFrame,
         boolean generatorFunctionBlock,
         boolean hasParentBlock,
         int start,
         int end
      ) {
         this(
            block,
            blockScopeSlot,
            frameDescriptor,
            parentSlot,
            functionBlock,
            captureFunctionFrame,
            generatorFunctionBlock,
            hasParentBlock,
            start,
            end,
            DeclareTagProvider.initDeclarations(frameDescriptor, original)
         );
      }

      protected MaterializedFrameBlockScopeNode(
         JavaScriptNode block,
         int blockScopeSlot,
         FrameDescriptor frameDescriptor,
         int parentSlot,
         boolean functionBlock,
         boolean captureFunctionFrame,
         boolean generatorFunctionBlock,
         boolean hasParentBlock,
         int start,
         int end,
         JavaScriptNode[] declarations
      ) {
         super(block, blockScopeSlot, frameDescriptor, parentSlot, functionBlock, captureFunctionFrame, generatorFunctionBlock, hasParentBlock, start, end);
         this.declarations = declarations;
      }

      @ExplodeLoop
      private void executeDeclarations(VirtualFrame frame) {
         for (JavaScriptNode declaration : this.declarations) {
            declaration.execute(frame);
         }
      }

      @Override
      public Object execute(VirtualFrame frame) {
         this.executeDeclarations(frame);
         return super.execute(frame);
      }

      @Override
      public void executeVoid(VirtualFrame frame) {
         this.executeDeclarations(frame);
         super.executeVoid(frame);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new DeclareTagProvider.MaterializedFrameBlockScopeNode(
            cloneUninitialized(this.block, materializedTags),
            this.blockScopeSlot,
            this.frameDescriptor,
            this.parentSlot,
            this.functionBlock,
            this.captureFunctionFrame,
            this.generatorFunctionBlock,
            this.hasParentBlock,
            this.start,
            this.end,
            cloneUninitialized(this.declarations, materializedTags)
         );
      }
   }

   private static class MaterializedFunctionBodyNode extends FunctionBodyNode {
      @Node.Children
      private JavaScriptNode[] declarations;

      protected MaterializedFunctionBodyNode(JavaScriptNode original, JavaScriptNode body, FrameDescriptor frameDescriptor) {
         this(body, DeclareTagProvider.initDeclarations(frameDescriptor, original));
      }

      protected MaterializedFunctionBodyNode(JavaScriptNode body, JavaScriptNode[] declarations) {
         super(body);
         this.declarations = declarations;
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frame) {
         for (JavaScriptNode declaration : this.declarations) {
            declaration.execute(frame);
         }

         return super.execute(frame);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new DeclareTagProvider.MaterializedFunctionBodyNode(
            cloneUninitialized(this.getBody(), materializedTags), cloneUninitialized(this.declarations, materializedTags)
         );
      }
   }
}
