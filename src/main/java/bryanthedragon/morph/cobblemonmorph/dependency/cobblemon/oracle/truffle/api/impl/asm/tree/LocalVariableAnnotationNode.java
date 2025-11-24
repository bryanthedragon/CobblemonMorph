
package com.oracle.truffle.api.impl.asm.tree;

import com.oracle.truffle.api.impl.asm.Label;
import com.oracle.truffle.api.impl.asm.MethodVisitor;
import com.oracle.truffle.api.impl.asm.TypePath;
import com.oracle.truffle.api.impl.asm.tree.LabelNode;
import com.oracle.truffle.api.impl.asm.tree.TypeAnnotationNode;
import com.oracle.truffle.api.impl.asm.tree.Util;
import java.util.List;

public class LocalVariableAnnotationNode
extends TypeAnnotationNode {
    public List<LabelNode> start;
    public List<LabelNode> end;
    public List<Integer> index;

    public LocalVariableAnnotationNode(int typeRef, TypePath typePath, LabelNode[] start2, LabelNode[] end2, int[] index, String descriptor) {
        this(589824, typeRef, typePath, start2, end2, index, descriptor);
    }

    public LocalVariableAnnotationNode(int api, int typeRef, TypePath typePath, LabelNode[] start2, LabelNode[] end2, int[] index, String descriptor) {
        super(api, typeRef, typePath, descriptor);
        this.start = Util.asArrayList(start2);
        this.end = Util.asArrayList(end2);
        this.index = Util.asArrayList(index);
    }

    public void accept(MethodVisitor methodVisitor, boolean visible) {
        Label[] startLabels = new Label[this.start.size()];
        Label[] endLabels = new Label[this.end.size()];
        int[] indices = new int[this.index.size()];
        int n = startLabels.length;
        for (int i = 0; i < n; ++i) {
            startLabels[i] = this.start.get(i).getLabel();
            endLabels[i] = this.end.get(i).getLabel();
            indices[i] = this.index.get(i);
        }
        this.accept(methodVisitor.visitLocalVariableAnnotation(this.typeRef, this.typePath, startLabels, endLabels, indices, this.desc, visible));
    }
}

