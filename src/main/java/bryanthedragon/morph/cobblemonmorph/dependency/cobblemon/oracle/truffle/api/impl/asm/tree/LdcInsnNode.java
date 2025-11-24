
package com.oracle.truffle.api.impl.asm.tree;

import com.oracle.truffle.api.impl.asm.MethodVisitor;
import com.oracle.truffle.api.impl.asm.tree.AbstractInsnNode;
import com.oracle.truffle.api.impl.asm.tree.LabelNode;
import java.util.Map;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class LdcInsnNode
extends AbstractInsnNode {
    public Object cst;

    public LdcInsnNode(Object value2) {
        super(18);
        this.cst = value2;
    }

    @Override
    public int getType() {
        return 9;
    }

    @Override
    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitLdcInsn(this.cst);
        this.acceptAnnotations(methodVisitor);
    }

    @Override
    public AbstractInsnNode clone(Map<LabelNode, LabelNode> clonedLabels) {
        return new LdcInsnNode(this.cst).cloneAnnotations(this);
    }
}

