package com.oracle.truffle.api.impl.asm.tree;

import com.oracle.truffle.api.impl.asm.MethodVisitor;
import java.util.Map;

public class InsnNode extends AbstractInsnNode {
   public InsnNode(int opcode) {
      super(opcode);
   }

   @Override
   public int getType() {
      return 0;
   }

   @Override
   public void accept(MethodVisitor methodVisitor) {
      methodVisitor.visitInsn(this.opcode);
      this.acceptAnnotations(methodVisitor);
   }

   @Override
   public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
      return new InsnNode(this.opcode).cloneAnnotations(this);
   }
}
