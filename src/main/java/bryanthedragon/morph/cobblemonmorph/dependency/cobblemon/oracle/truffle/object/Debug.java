
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.ObjectStorageOptions;
import com.oracle.truffle.object.ShapeImpl;
import com.oracle.truffle.object.ShapeProfiler;
import com.oracle.truffle.object.Transition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

class Debug {
    static final String INVALID = "!";
    static final String BRANCH = "\u2443";
    static final String LEAF = "\u22a5";
    private static Collection<ShapeImpl> allShapes;
    static final /* synthetic */ boolean $assertionsDisabled;

    Debug() {
    }

    static void trackShape(ShapeImpl newShape) {
        allShapes.add(newShape);
    }

    static void trackObject(DynamicObject obj) {
        if (!$assertionsDisabled && !ObjectStorageOptions.Profile) {
            throw new AssertionError();
        }
        ShapeProfiler.getInstance().track(obj);
    }

    static Iterable<ShapeImpl> getAllShapes() {
        return allShapes;
    }

    static String dumpObject(DynamicObject object, int level, int levelStop) {
        List<Property> properties2 = object.getShape().getPropertyListInternal(true);
        StringBuilder sb = new StringBuilder(properties2.size() * 10);
        sb.append("{\n");
        for (Property property : properties2) {
            Debug.indent(sb, level + 1);
            sb.append(property.getKey());
            sb.append('[').append(property.getLocation()).append(']');
            Object value2 = property.get(object, false);
            if (value2 instanceof DynamicObject) {
                value2 = level < levelStop ? Debug.dumpObject((DynamicObject)value2, level + 1, levelStop) : value2.toString();
            }
            sb.append(": ");
            sb.append(value2);
            if (property != properties2.get(properties2.size() - 1)) {
                sb.append(",");
            }
            sb.append("\n");
        }
        Debug.indent(sb, level);
        sb.append("}");
        return sb.toString();
    }

    private static StringBuilder indent(StringBuilder sb, int level) {
        for (int i = 0; i < level; ++i) {
            sb.append(' ');
        }
        return sb;
    }

    private static void dumpDOT() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter out = new PrintWriter(Debug.getOutputFile("dot"), "UTF-8");){
            GraphvizShapeVisitor visitor = new GraphvizShapeVisitor();
            for (ShapeImpl shape : Debug.getAllShapes()) {
                visitor.visitShape(shape);
            }
            out.println(visitor);
        }
    }

    private static File getOutputFile(String extension) {
        return Paths.get(ObjectStorageOptions.DumpShapesPath, "shapes." + extension).toFile();
    }

    static String getId(Shape shape) {
        return Integer.toHexString(shape.hashCode());
    }

    static {
        boolean bl = $assertionsDisabled = !Debug.class.desiredAssertionStatus();
        if (ObjectStorageOptions.DumpShapes) {
            allShapes = new ConcurrentLinkedQueue<ShapeImpl>();
        }
        if (ObjectStorageOptions.DumpShapes) {
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        if (ObjectStorageOptions.DumpShapesDOT) {
                            Debug.dumpDOT();
                        }
                    }
                    catch (FileNotFoundException | UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }));
        }
    }

    static class GraphvizShapeVisitor
    implements DebugShapeVisitor<GraphvizShapeVisitor> {
        private final Set<Shape> drawn;
        private final StringBuilder sb = new StringBuilder();

        GraphvizShapeVisitor() {
            this.drawn = new HashSet<Shape>();
        }

        @Override
        public GraphvizShapeVisitor visitShape(ShapeImpl shape, Map<? extends Transition, ? extends ShapeImpl> transitions) {
            if (!this.drawn.add(shape)) {
                return this;
            }
            if (shape.isLeaf() && shape.getLastProperty() == null) {
                return this;
            }
            String prefix = "s";
            this.sb.append(prefix).append(Debug.getId(shape));
            this.sb.append(" [label=\"");
            this.sb.append(Debug.getId(shape));
            this.sb.append(":");
            if (shape.getLastProperty() != null) {
                for (Property property : shape.getPropertyListInternal(true)) {
                    this.sb.append("\\n");
                    this.sb.append(GraphvizShapeVisitor.escapeString(property.toString()));
                }
            } else {
                this.sb.append("\\nROOT");
            }
            this.sb.append("\"");
            this.sb.append(", shape=\"rectangle\"");
            if (!shape.isValid()) {
                this.sb.append(", color=\"red\", style=dotted");
            }
            this.sb.append("];");
            for (Map.Entry entry : transitions.entrySet()) {
                ShapeImpl dst = (ShapeImpl)entry.getValue();
                this.visitShape(dst);
                assert (this.drawn.contains(dst));
                this.sb.append(prefix).append(Debug.getId(shape)).append("->").append(prefix).append(Debug.getId(dst));
                this.sb.append(" [label=\"").append(GraphvizShapeVisitor.escapeString(((Transition)entry.getKey()).toString())).append("\"]");
                this.sb.append(";");
            }
            return this;
        }

        private static String escapeString(String str) {
            return str.replaceAll("\\\\", "\\\\").replaceAll("\"", "\\\\\"");
        }

        public String toString() {
            return "digraph{" + this.sb + "}";
        }
    }

    static interface DebugShapeVisitor<R> {
        default public R visitShape(ShapeImpl shape) {
            return this.visitShape(shape, Collections.unmodifiableMap(shape.getTransitionMapForRead()));
        }

        public R visitShape(ShapeImpl var1, Map<? extends Transition, ? extends ShapeImpl> var2);
    }
}

