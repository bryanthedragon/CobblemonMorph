
package com.oracle.truffle.api.impl.asm.commons;

import com.oracle.truffle.api.impl.asm.AnnotationVisitor;
import com.oracle.truffle.api.impl.asm.RecordComponentVisitor;
import com.oracle.truffle.api.impl.asm.TypePath;
import com.oracle.truffle.api.impl.asm.commons.AnnotationRemapper;
import com.oracle.truffle.api.impl.asm.commons.Remapper;

public class RecordComponentRemapper
extends RecordComponentVisitor {
    protected final Remapper remapper;

    public RecordComponentRemapper(RecordComponentVisitor recordComponentVisitor, Remapper remapper) {
        this(589824, recordComponentVisitor, remapper);
    }

    protected RecordComponentRemapper(int api, RecordComponentVisitor recordComponentVisitor, Remapper remapper) {
        super(api, recordComponentVisitor);
        this.remapper = remapper;
    }

    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitAnnotation(this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        AnnotationVisitor annotationVisitor = super.visitTypeAnnotation(typeRef, typePath, this.remapper.mapDesc(descriptor), visible);
        return annotationVisitor == null ? null : this.createAnnotationRemapper(descriptor, annotationVisitor);
    }

    @Deprecated
    protected AnnotationVisitor createAnnotationRemapper(AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, null, annotationVisitor, this.remapper);
    }

    protected AnnotationVisitor createAnnotationRemapper(String descriptor, AnnotationVisitor annotationVisitor) {
        return new AnnotationRemapper(this.api, descriptor, annotationVisitor, this.remapper).orDeprecatedValue(this.createAnnotationRemapper(annotationVisitor));
    }
}

