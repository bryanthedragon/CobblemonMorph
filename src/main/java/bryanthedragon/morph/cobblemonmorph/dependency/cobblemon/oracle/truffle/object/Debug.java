package com.oracle.truffle.object;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

class Debug {
   static final String INVALID = "!";
   static final String BRANCH = "⑃";
   static final String LEAF = "⊥";
   private static Collection<ShapeImpl> allShapes;

   static void trackShape(ShapeImpl newShape) {
      allShapes.add(newShape);
   }

   static void trackObject(DynamicObject obj) {
      assert ObjectStorageOptions.Profile;

      ShapeProfiler.getInstance().track(obj);
   }

   static Iterable<ShapeImpl> getAllShapes() {
      return allShapes;
   }

   static String dumpObject(DynamicObject object, int level, int levelStop) {
      List<Property> properties = object.getShape().getPropertyListInternal(true);
      StringBuilder sb = new StringBuilder(properties.size() * 10);
      sb.append("{\n");

      for (Property property : properties) {
         indent(sb, level + 1);
         sb.append(property.getKey());
         sb.append('[').append(property.getLocation()).append(']');
         Object value = property.get(object, false);
         if (value instanceof DynamicObject) {
            if (level < levelStop) {
               value = dumpObject((DynamicObject)value, level + 1, levelStop);
            } else {
               value = value.toString();
            }
         }

         sb.append(": ");
         sb.append(value);
         if (property != properties.get(properties.size() - 1)) {
            sb.append(",");
         }

         sb.append("\n");
      }

      indent(sb, level);
      sb.append("}");
      return sb.toString();
   }

   private static StringBuilder indent(StringBuilder sb, int level) {
      for (int i = 0; i < level; i++) {
         sb.append(' ');
      }

      return sb;
   }

   private static void dumpDOT() throws FileNotFoundException, UnsupportedEncodingException {
      try (PrintWriter out = new PrintWriter(getOutputFile("dot"), "UTF-8")) {
         Debug.GraphvizShapeVisitor visitor = new Debug.GraphvizShapeVisitor();

         for (ShapeImpl shape : getAllShapes()) {
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
      if (ObjectStorageOptions.DumpShapes) {
         allShapes = new ConcurrentLinkedQueue<>();
      }

      if (ObjectStorageOptions.DumpShapes) {
         Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
               try {
                  if (ObjectStorageOptions.DumpShapesDOT) {
                     Debug.dumpDOT();
                  }
               } catch (UnsupportedEncodingException | FileNotFoundException var2) {
                  throw new RuntimeException(var2);
               }
            }
         }));
      }
   }

   interface DebugShapeVisitor<R> {
      default R visitShape(ShapeImpl shape) {
         return this.visitShape(shape, Collections.unmodifiableMap(shape.getTransitionMapForRead()));
      }

      R visitShape(ShapeImpl shape, Map<? extends Transition, ? extends ShapeImpl> transitions);
   }

   static class GraphvizShapeVisitor implements Debug.DebugShapeVisitor<Debug.GraphvizShapeVisitor> {
      private final Set<Shape> drawn;
      private final StringBuilder sb = new StringBuilder();

      GraphvizShapeVisitor() {
         this.drawn = new HashSet<>();
      }

      public Debug.GraphvizShapeVisitor visitShape(ShapeImpl shape, Map<? extends Transition, ? extends ShapeImpl> transitions) {
         if (!this.drawn.add(shape)) {
            return this;
         } else if (shape.isLeaf() && shape.getLastProperty() == null) {
            return this;
         } else {
            String prefix = "s";
            this.sb.append(prefix).append(Debug.getId(shape));
            this.sb.append(" [label=\"");
            this.sb.append(Debug.getId(shape));
            this.sb.append(":");
            if (shape.getLastProperty() != null) {
               for (Property property : shape.getPropertyListInternal(true)) {
                  this.sb.append("\\n");
                  this.sb.append(escapeString(property.toString()));
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

            for (Entry<? extends Transition, ? extends ShapeImpl> entry : transitions.entrySet()) {
               ShapeImpl dst = entry.getValue();
               this.visitShape(dst);

               assert this.drawn.contains(dst);

               this.sb.append(prefix).append(Debug.getId(shape)).append("->").append(prefix).append(Debug.getId(dst));
               this.sb.append(" [label=\"").append(escapeString(entry.getKey().toString())).append("\"]");
               this.sb.append(";");
            }

            return this;
         }
      }

      private static String escapeString(String str) {
         return str.replaceAll("\\\\", "\\\\").replaceAll("\"", "\\\\\"");
      }

      @Override
      public String toString() {
         return new StringBuilder("digraph{").append((CharSequence)this.sb).append("}").toString();
      }
   }
}
