
package com.oracle.truffle.api.impl.asm.tree;

import com.oracle.truffle.api.impl.asm.MethodVisitor;
import com.oracle.truffle.api.impl.asm.tree.LabelNode;

public class LocalVariableNode {
    public String name;
    public String desc;
    public String signature;
    public LabelNode start;
    public LabelNode end;
    public int index;

    public LocalVariableNode(String name, String descriptor, String signature, LabelNode start2, LabelNode end2, int index) {
        this.name = name;
        this.desc = descriptor;
        this.signature = signature;
        this.start = start2;
        this.end = end2;
        this.index = index;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitLocalVariable(this.name, this.desc, this.signature, this.start.getLabel(), this.end.getLabel(), this.index);
    }
}

