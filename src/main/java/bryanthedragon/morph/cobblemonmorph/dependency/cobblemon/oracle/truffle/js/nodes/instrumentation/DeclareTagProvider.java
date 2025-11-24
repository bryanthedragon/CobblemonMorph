
package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.Set;

public final class DeclareTagProvider {
    public static JavaScriptNode createMaterializedFunctionBodyNode(JavaScriptNode original, JavaScriptNode body, FrameDescriptor frameDescriptor) {
        return new MaterializedFunctionBodyNode(original, body, frameDescriptor);
    }

    public static JavaScriptNode createMaterializedBlockNode(JavaScriptNode original, JavaScriptNode block, int blockScopeSlot, FrameDescriptor frameDescriptor, int parentSlot, boolean functionBlock, boolean captureFunctionFrame, boolean generatorFunctionBlock, boolean hasParentBlock, int start2, int end2) {
        return new MaterializedFrameBlockScopeNode(original, block, blockScopeSlot, frameDescriptor, parentSlot, functionBlock, captureFunctionFrame, generatorFunctionBlock, hasParentBlock, start2, end2);
    }

    public static boolean isMaterializedFrameProvider(JavaScriptNode node) {
        return node instanceof MaterializedFrameBlockScopeNode || node instanceof MaterializedFunctionBodyNode;
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
        assert (locationNode != null);
        if (frameDescriptor != null) {
            ArrayList<Integer> slots = new ArrayList<Integer>();
            for (int i = 0; i < frameDescriptor.getNumberOfSlots(); ++i) {
                if (JSFrameUtil.isInternal(frameDescriptor, i) || JSFrameUtil.isHoistable(frameDescriptor, i)) continue;
                slots.add(i);
            }
            JavaScriptNode[] declarations = new JavaScriptNode[slots.size()];
            for (int i = 0; i < slots.size(); ++i) {
                DeclareProviderNode declaration = new DeclareProviderNode(frameDescriptor, (Integer)slots.get(i));
                JavaScriptNode.transferSourceSection(locationNode, declaration);
                declarations[i] = declaration;
            }
            return declarations;
        }
        return new JavaScriptNode[0];
    }

    private static class DeclareProviderNode
    extends JavaScriptNode {
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
            if (tag == JSTags.DeclareTag.class) {
                return true;
            }
            return super.hasTag(tag);
        }

        @Override
        public boolean isInstrumentable() {
            return true;
        }

        @Override
        public Object getNodeObject() {
            String type = JSFrameUtil.isConst(this.frameDescriptor, this.slotIndex) ? "const" : (JSFrameUtil.isLet(this.frameDescriptor, this.slotIndex) ? "let" : "var");
            return DeclareTagProvider.createDeclareNodeObject(this.frameDescriptor.getSlotName(this.slotIndex), type);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new DeclareProviderNode(this.frameDescriptor, this.slotIndex);
        }
    }

    private static class MaterializedFunctionBodyNode
    extends FunctionBodyNode {
        @Node.Children
        private JavaScriptNode[] declarations;

        protected MaterializedFunctionBodyNode(JavaScriptNode original, JavaScriptNode body, FrameDescriptor frameDescriptor) {
            this(body, DeclareTagProvider.initDeclarations(frameDescriptor, original));
        }

        protected MaterializedFunctionBodyNode(JavaScriptNode body, JavaScriptNode[] declarations) {
            super(body);
            this.declarations = declarations;
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frame) {
            for (JavaScriptNode declaration : this.declarations) {
                declaration.execute(frame);
            }
            return super.execute(frame);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new MaterializedFunctionBodyNode(MaterializedFunctionBodyNode.cloneUninitialized(this.getBody(), materializedTags), MaterializedFunctionBodyNode.cloneUninitialized(this.declarations, materializedTags));
        }
    }

    private static class MaterializedFrameBlockScopeNode
    extends BlockScopeNode.FrameBlockScopeNode {
        @Node.Children
        private JavaScriptNode[] declarations;

        protected MaterializedFrameBlockScopeNode(JavaScriptNode original, JavaScriptNode block, int blockScopeSlot, FrameDescriptor frameDescriptor, int parentSlot, boolean functionBlock, boolean captureFunctionFrame, boolean generatorFunctionBlock, boolean hasParentBlock, int start2, int end2) {
            this(block, blockScopeSlot, frameDescriptor, parentSlot, functionBlock, captureFunctionFrame, generatorFunctionBlock, hasParentBlock, start2, end2, DeclareTagProvider.initDeclarations(frameDescriptor, original));
        }

        protected MaterializedFrameBlockScopeNode(JavaScriptNode block, int blockScopeSlot, FrameDescriptor frameDescriptor, int parentSlot, boolean functionBlock, boolean captureFunctionFrame, boolean generatorFunctionBlock, boolean hasParentBlock, int start2, int end2, JavaScriptNode[] declarations) {
            super(block, blockScopeSlot, frameDescriptor, parentSlot, functionBlock, captureFunctionFrame, generatorFunctionBlock, hasParentBlock, start2, end2);
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
            return new MaterializedFrameBlockScopeNode(MaterializedFrameBlockScopeNode.cloneUninitialized(this.block, materializedTags), this.blockScopeSlot, this.frameDescriptor, this.parentSlot, this.functionBlock, this.captureFunctionFrame, this.generatorFunctionBlock, this.hasParentBlock, this.start, this.end, MaterializedFrameBlockScopeNode.cloneUninitialized(this.declarations, materializedTags));
        }
    }
}

