
package com.oracle.truffle.api.impl.asm.tree;

import com.oracle.truffle.api.impl.asm.MethodVisitor;

public class ParameterNode {
    public String name;
    public int access;

    public ParameterNode(String name, int access) {
        this.name = name;
        this.access = access;
    }

    public void accept(MethodVisitor methodVisitor) {
        methodVisitor.visitParameter(this.name, this.access);
    }
}

