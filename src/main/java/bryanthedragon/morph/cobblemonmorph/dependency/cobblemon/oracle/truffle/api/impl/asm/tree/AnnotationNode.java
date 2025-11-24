
package com.oracle.truffle.api.impl.asm.tree;

import com.oracle.truffle.api.impl.asm.AnnotationVisitor;
import com.oracle.truffle.api.impl.asm.tree.Util;
import java.util.ArrayList;
import java.util.List;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class AnnotationNode
extends AnnotationVisitor {
    public String desc;
    public List<Object> values;

    public AnnotationNode(String descriptor) {
        this(589824, descriptor);
        if (this.getClass() != AnnotationNode.class) {
            throw new IllegalStateException();
        }
    }

    public AnnotationNode(int api, String descriptor) {
        super(api);
        this.desc = descriptor;
    }

    AnnotationNode(List<Object> values) {
        super(589824);
        this.values = values;
    }

    @Override
    public void visit(String name, Object value2) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        if (value2 instanceof byte[]) {
            this.values.add(Util.asArrayList((byte[])value2));
        } else if (value2 instanceof boolean[]) {
            this.values.add(Util.asArrayList((boolean[])value2));
        } else if (value2 instanceof short[]) {
            this.values.add(Util.asArrayList((short[])value2));
        } else if (value2 instanceof char[]) {
            this.values.add(Util.asArrayList((char[])value2));
        } else if (value2 instanceof int[]) {
            this.values.add(Util.asArrayList((int[])value2));
        } else if (value2 instanceof long[]) {
            this.values.add(Util.asArrayList((long[])value2));
        } else if (value2 instanceof float[]) {
            this.values.add(Util.asArrayList((float[])value2));
        } else if (value2 instanceof double[]) {
            this.values.add(Util.asArrayList((double[])value2));
        } else {
            this.values.add(value2);
        }
    }

    @Override
    public void visitEnum(String name, String descriptor, String value2) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        this.values.add(new String[]{descriptor, value2});
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        AnnotationNode annotation = new AnnotationNode(descriptor);
        this.values.add(annotation);
        return annotation;
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        if (this.values == null) {
            this.values = new ArrayList<Object>(this.desc != null ? 2 : 1);
        }
        if (this.desc != null) {
            this.values.add(name);
        }
        ArrayList<Object> array = new ArrayList<Object>();
        this.values.add(array);
        return new AnnotationNode(array);
    }

    @Override
    public void visitEnd() {
    }

    public void check(int api) {
    }

    public void accept(AnnotationVisitor annotationVisitor) {
        if (annotationVisitor != null) {
            if (this.values != null) {
                int n = this.values.size();
                for (int i = 0; i < n; i += 2) {
                    String name = (String)this.values.get(i);
                    Object value2 = this.values.get(i + 1);
                    AnnotationNode.accept(annotationVisitor, name, value2);
                }
            }
            annotationVisitor.visitEnd();
        }
    }

    static void accept(AnnotationVisitor annotationVisitor, String name, Object value2) {
        if (annotationVisitor != null) {
            if (value2 instanceof String[]) {
                String[] typeValue = (String[])value2;
                annotationVisitor.visitEnum(name, typeValue[0], typeValue[1]);
            } else if (value2 instanceof AnnotationNode) {
                AnnotationNode annotationValue = (AnnotationNode)value2;
                annotationValue.accept(annotationVisitor.visitAnnotation(name, annotationValue.desc));
            } else if (value2 instanceof List) {
                AnnotationVisitor arrayAnnotationVisitor = annotationVisitor.visitArray(name);
                if (arrayAnnotationVisitor != null) {
                    List arrayValue = (List)value2;
                    int n = arrayValue.size();
                    for (int i = 0; i < n; ++i) {
                        AnnotationNode.accept(arrayAnnotationVisitor, null, arrayValue.get(i));
                    }
                    arrayAnnotationVisitor.visitEnd();
                }
            } else {
                annotationVisitor.visit(name, value2);
            }
        }
    }
}

