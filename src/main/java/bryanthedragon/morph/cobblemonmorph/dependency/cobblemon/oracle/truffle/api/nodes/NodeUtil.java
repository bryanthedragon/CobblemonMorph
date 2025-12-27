package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.source.SourceSection;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class NodeUtil {
   private NodeUtil() {
   }

   public static <T extends Node> T cloneNode(T orig) {
      return (T)orig.deepCopy();
   }

   static Node deepCopyImpl(Node orig) {
      CompilerAsserts.neverPartOfCompilation("do not call Node.deepCopyImpl from compiled code");
      Node clone = orig.copy();
      if (!sameType(clone, orig)) {
         throw CompilerDirectives.shouldNotReachHere(
            String.format(
               "Invalid return type after copy(): orig.getClass() = %s, clone.getClass() = %s", orig.getClass(), clone == null ? "null" : clone.getClass()
            )
         );
      } else {
         NodeClass nodeClass = clone.getNodeClass();
         clone.setParent(null);

         for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field)) {
               Node child = (Node)nodeClass.getFieldObject(field, orig);
               if (child != null) {
                  Node clonedChild = child.deepCopy();
                  clonedChild.setParent(clone);
                  if (!sameType(child, clonedChild)) {
                     throw CompilerDirectives.shouldNotReachHere(
                        String.format(
                           "Invalid return type after deepCopy(): orig.getClass() = %s, orig.fieldName = '%s', child.getClass() = %s, clonedChild.getClass() = %s",
                           orig.getClass(),
                           nodeClass.getFieldName(field),
                           child.getClass(),
                           clonedChild.getClass()
                        )
                     );
                  }

                  nodeClass.putFieldObject(field, clone, clonedChild);
               }
            } else if (nodeClass.isChildrenField(field)) {
               Object[] children = (Object[])nodeClass.getFieldObject(field, orig);
               if (children != null) {
                  Object[] clonedChildren;
                  if (children.length > 0) {
                     clonedChildren = (Object[])Array.newInstance(children.getClass().getComponentType(), children.length);

                     for (int i = 0; i < children.length; i++) {
                        if (children[i] != null) {
                           Node clonedChild = ((Node)children[i]).deepCopy();
                           if (!sameType(children[i], clonedChild)) {
                              throw CompilerDirectives.shouldNotReachHere(
                                 String.format(
                                    "Invalid return type after deepCopy(): orig.getClass() = %s, orig.fieldName = '%s', children[i].getClass() = %s, clonedChild.getClass() = %s",
                                    orig.getClass(),
                                    nodeClass.getFieldName(field),
                                    children[i].getClass(),
                                    clonedChild == null ? "null" : clonedChild.getClass()
                                 )
                              );
                           }

                           clonedChild.setParent(clone);
                           clonedChildren[i] = clonedChild;
                        }
                     }
                  } else {
                     clonedChildren = children;
                  }

                  nodeClass.putFieldObject(field, clone, clonedChildren);
               }
            } else if (nodeClass.isCloneableField(field)) {
               Object cloneable = nodeClass.getFieldObject(field, clone);
               if (cloneable != null && cloneable == nodeClass.getFieldObject(field, orig)) {
                  Object clonedClonable = ((NodeCloneable)cloneable).clone();
                  if (!sameType(cloneable, clonedClonable)) {
                     throw CompilerDirectives.shouldNotReachHere(
                        String.format(
                           "Invalid return type after clone(): orig.getClass() = %s, orig.fieldName = '%s', cloneable.getClass() = %s, clonedCloneable.getClass() =%s",
                           orig.getClass(),
                           nodeClass.getFieldName(field),
                           cloneable.getClass(),
                           clonedClonable == null ? "null" : clonedClonable.getClass()
                        )
                     );
                  }

                  nodeClass.putFieldObject(field, clone, clonedClonable);
               }
            } else if (nodeClass.nodeFieldsOrderedByKind()) {
               break;
            }
         }

         return clone;
      }
   }

   private static boolean sameType(Object clone, Object orig) {
      return clone != null && orig != null ? clone.getClass() == orig.getClass() : clone == orig;
   }

   public static List<Node> findNodeChildren(Node node) {
      CompilerAsserts.neverPartOfCompilation("do not call Node.findNodeChildren from compiled code");
      List<Node> nodes = new ArrayList<>();
      NodeClass nodeClass = node.getNodeClass();

      for (Object nodeField : nodeClass.getNodeFieldArray()) {
         if (nodeClass.isChildField(nodeField)) {
            Object child = nodeClass.getFieldObject(nodeField, node);
            if (child != null) {
               nodes.add((Node)child);
            }
         } else if (nodeClass.isChildrenField(nodeField)) {
            Object[] children = (Object[])nodeClass.getFieldObject(nodeField, node);
            if (children != null) {
               for (Object child : children) {
                  if (child != null) {
                     nodes.add((Node)child);
                  }
               }
            }
         } else if (nodeClass.nodeFieldsOrderedByKind()) {
            break;
         }
      }

      return nodes;
   }

   public static <T extends Node> T nonAtomicReplace(Node oldNode, T newNode, CharSequence reason) {
      oldNode.replaceHelper(newNode, reason);
      return newNode;
   }

   public static boolean replaceChild(Node parent, Node oldChild, Node newChild) {
      return replaceChild(parent, oldChild, newChild, false);
   }

   static void adoptChildrenHelper(Node currentNode) {
      NodeClass clazz = currentNode.getNodeClass();

      for (Object field : clazz.getNodeFieldArray()) {
         if (clazz.isChildField(field)) {
            Object child = clazz.getFieldObject(field, currentNode);
            if (child != null) {
               Node node = (Node)child;
               if (node.getParent() != currentNode) {
                  currentNode.adoptHelper(node);
               }
            }
         } else if (clazz.isChildrenField(field)) {
            Object arrayObject = clazz.getFieldObject(field, currentNode);
            if (arrayObject != null) {
               Object[] array = (Object[])arrayObject;

               for (int i = 0; i < array.length; i++) {
                  Object child = array[i];
                  if (child != null) {
                     Node node = (Node)child;
                     if (node.getParent() != currentNode) {
                        currentNode.adoptHelper(node);
                     }
                  }
               }
            }
         } else if (clazz.nodeFieldsOrderedByKind()) {
            break;
         }
      }
   }

   static int adoptChildrenAndCountHelper(Node currentNode) {
      int count = 0;
      NodeClass clazz = currentNode.getNodeClass();

      for (Object field : clazz.getNodeFieldArray()) {
         if (clazz.isChildField(field)) {
            Object child = clazz.getFieldObject(field, currentNode);
            if (child != null) {
               Node node = (Node)child;
               count += currentNode.adoptAndCountHelper(node);
            }
         } else if (clazz.isChildrenField(field)) {
            Object arrayObject = clazz.getFieldObject(field, currentNode);
            if (arrayObject != null) {
               Object[] array = (Object[])arrayObject;

               for (int i = 0; i < array.length; i++) {
                  Object child = array[i];
                  if (child != null) {
                     Node node = (Node)child;
                     count += currentNode.adoptAndCountHelper(node);
                  }
               }
            }
         } else if (clazz.nodeFieldsOrderedByKind()) {
            break;
         }
      }

      return count;
   }

   static boolean replaceChild(Node parent, Node oldChild, Node newChild, boolean adopt) {
      CompilerAsserts.neverPartOfCompilation("do not replace Node child from compiled code");
      NodeClass nodeClass = parent.getNodeClass();
      if (!oldChild.getNodeClass().isReplaceAllowed()) {
         throw new IllegalArgumentException(String.format("Replaced node type '%s' does not allow replacement.", oldChild.getClass().getName()));
      } else if (!newChild.getNodeClass().isReplaceAllowed()) {
         throw new IllegalArgumentException(String.format("Replacing node type '%s' does not allow replacement.", newChild.getClass().getName()));
      } else {
         for (Object nodeField : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(nodeField)) {
               if (nodeClass.getFieldObject(nodeField, parent) == oldChild) {
                  if (adopt) {
                     parent.adoptHelper(newChild);
                  }

                  nodeClass.putFieldObject(nodeField, parent, newChild);
                  return true;
               }
            } else if (nodeClass.isChildrenField(nodeField)) {
               Object arrayObject = nodeClass.getFieldObject(nodeField, parent);
               if (arrayObject != null) {
                  Object[] array = (Object[])arrayObject;

                  for (int i = 0; i < array.length; i++) {
                     if (array[i] == oldChild) {
                        if (adopt) {
                           parent.adoptHelper(newChild);
                        }

                        try {
                           array[i] = newChild;
                           return true;
                        } catch (ArrayStoreException var13) {
                           throw replaceChildIllegalArgumentException(nodeField, array.getClass(), newChild);
                        }
                     }
                  }
               }
            } else if (nodeClass.nodeFieldsOrderedByKind()) {
               break;
            }
         }

         return false;
      }
   }

   private static IllegalArgumentException replaceChildIllegalArgumentException(Object nodeField, Class<?> fieldType, Node newChild) {
      return new IllegalArgumentException(
         "Cannot set element of " + fieldType.getName() + " field " + nodeField + " to " + (newChild == null ? "null" : newChild.getClass().getName())
      );
   }

   public static String findChildFieldName(Node parent, Node child) {
      return getNodeFieldName(parent, child, null);
   }

   public static List<String> collectFieldNames(Class<? extends Node> clazz) {
      NodeClass nodeClass = NodeClass.get(clazz);
      Object[] fields = nodeClass.getNodeFieldArray();
      String[] fieldNames = new String[fields.length];

      for (int i = 0; i < fields.length; i++) {
         fieldNames[i] = nodeClass.getFieldName(fields[i]);
      }

      return Arrays.asList(fieldNames);
   }

   public static Map<String, Node> collectNodeChildren(Node node) {
      LinkedHashMap<String, Node> nodes = new LinkedHashMap<>();
      NodeClass nodeClass = NodeClass.get(node);

      for (Object field : nodeClass.getNodeFieldArray()) {
         if (nodeClass.isChildField(field)) {
            Object value = nodeClass.getFieldObject(field, node);
            if (value != null) {
               nodes.put(nodeClass.getFieldName(field), (Node)value);
            }
         } else if (nodeClass.isChildrenField(field)) {
            Object value = nodeClass.getFieldObject(field, node);
            if (value != null) {
               Object[] children = (Object[])value;

               for (int i = 0; i < children.length; i++) {
                  if (children[i] != null) {
                     nodes.put(nodeClass.getFieldName(field) + "[" + i + "]", (Node)children[i]);
                  }
               }
            }
         }
      }

      return Collections.unmodifiableMap(nodes);
   }

   public static Map<String, Object> collectNodeProperties(Node node) {
      LinkedHashMap<String, Object> nodes = new LinkedHashMap<>();
      NodeClass nodeClass = NodeClass.get(node);

      for (Object field : nodeClass.getNodeFieldArray()) {
         if (!nodeClass.isChildField(field) && !nodeClass.isChildrenField(field)) {
            nodes.put(nodeClass.getFieldName(field), nodeClass.getFieldValue(field, node));
         }
      }

      return Collections.unmodifiableMap(nodes);
   }

   static Object findChildField(Node parent, Node child) {
      assert child != null;

      NodeClass parentNodeClass = parent.getNodeClass();

      for (Object field : parentNodeClass.getNodeFieldArray()) {
         if (parentNodeClass.isChildField(field)) {
            return field;
         }

         if (parentNodeClass.isChildrenField(field)) {
            Object arrayObject = parentNodeClass.getFieldValue(field, child);
            if (arrayObject != null) {
               Object[] array = (Object[])arrayObject;

               for (int i = 0; i < array.length; i++) {
                  if (array[i] == child) {
                     return field;
                  }
               }
            }
         }
      }

      return null;
   }

   public static boolean isReplacementSafe(Node parent, Node oldChild, Node newChild) {
      if (parent == null) {
         return false;
      } else if (!parent.isAdoptable()) {
         return false;
      } else {
         NodeClass nodeClass = parent.getNodeClass();

         for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field)) {
               if (nodeClass.getFieldObject(field, parent) == oldChild) {
                  if (oldChild.getNodeClass().isReplaceAllowed() && newChild.getNodeClass().isReplaceAllowed()) {
                     return nodeClass.getFieldType(field).isAssignableFrom(newChild.getClass());
                  }

                  return false;
               }
            } else if (nodeClass.isChildrenField(field)) {
               Object arrayObject = nodeClass.getFieldObject(field, parent);
               if (arrayObject != null) {
                  Object[] array = (Object[])arrayObject;

                  for (int i = 0; i < array.length; i++) {
                     if (array[i] == oldChild) {
                        if (oldChild.getNodeClass().isReplaceAllowed() && newChild.getNodeClass().isReplaceAllowed()) {
                           return nodeClass.getFieldType(field).getComponentType().isAssignableFrom(newChild.getClass());
                        }

                        return false;
                     }
                  }
               }
            } else if (nodeClass.nodeFieldsOrderedByKind()) {
               break;
            }
         }

         return true;
      }
   }

   public static boolean forEachChild(Node parent, NodeVisitor visitor) {
      CompilerAsserts.neverPartOfCompilation("do not iterate over Node children from compiled code");
      Objects.requireNonNull(visitor);
      NodeClass nodeClass = parent.getNodeClass();

      for (Object field : nodeClass.getNodeFieldArray()) {
         if (nodeClass.isChildField(field)) {
            Object child = nodeClass.getFieldObject(field, parent);
            if (child != null && !visitor.visit((Node)child)) {
               return false;
            }
         } else if (nodeClass.isChildrenField(field)) {
            Object arrayObject = nodeClass.getFieldObject(field, parent);
            if (arrayObject != null) {
               Object[] array = (Object[])arrayObject;

               for (int i = 0; i < array.length; i++) {
                  Object child = array[i];
                  if (child != null && !visitor.visit((Node)child)) {
                     return false;
                  }
               }
            }
         } else if (nodeClass.nodeFieldsOrderedByKind()) {
            break;
         }
      }

      return true;
   }

   static boolean forEachChildRecursive(Node parent, NodeVisitor visitor) {
      NodeClass nodeClass = parent.getNodeClass();

      for (Object field : nodeClass.getNodeFieldArray()) {
         if (nodeClass.isChildField(field)) {
            if (!visitChild((Node)nodeClass.getFieldObject(field, parent), visitor)) {
               return false;
            }
         } else if (nodeClass.isChildrenField(field)) {
            Object arrayObject = nodeClass.getFieldObject(field, parent);
            if (arrayObject != null) {
               Object[] array = (Object[])arrayObject;

               for (int i = 0; i < array.length; i++) {
                  if (!visitChild((Node)array[i], visitor)) {
                     return false;
                  }
               }
            }
         } else if (nodeClass.nodeFieldsOrderedByKind()) {
            break;
         }
      }

      return true;
   }

   private static boolean visitChild(Node child, NodeVisitor visitor) {
      if (child == null) {
         return true;
      } else {
         return !visitor.visit(child) ? false : forEachChildRecursive(child, visitor);
      }
   }

   public static <T> T[] concat(T[] first, T[] second) {
      T[] result = (T[])Arrays.copyOf(first, first.length + second.length);
      System.arraycopy(second, 0, result, first.length, second.length);
      return result;
   }

   public static Node getNthParent(Node node, int n) {
      Node parent = node;

      for (int i = 0; i < n; i++) {
         parent = parent.getParent();
         if (parent == null) {
            return null;
         }
      }

      return parent;
   }

   public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationClass) {
      if (clazz.<T>getAnnotation(annotationClass) != null) {
         return clazz.getAnnotation(annotationClass);
      } else {
         if (!TruffleOptions.AOT) {
            for (Class<?> intf : clazz.getInterfaces()) {
               if (intf.<T>getAnnotation(annotationClass) != null) {
                  return intf.getAnnotation(annotationClass);
               }
            }
         }

         return clazz.getSuperclass() != null ? findAnnotation(clazz.getSuperclass(), annotationClass) : null;
      }
   }

   public static <T> T findParent(Node start, Class<T> clazz) {
      Node parent = start.getParent();
      if (parent == null) {
         return null;
      } else {
         return clazz.isInstance(parent) ? clazz.cast(parent) : findParent(parent, clazz);
      }
   }

   public static <T> List<T> findAllParents(Node start, Class<T> clazz) {
      List<T> parents = new ArrayList<>();

      for (T parent = findParent(start, clazz); parent != null; parent = findParent((Node)parent, clazz)) {
         parents.add(parent);
      }

      return parents;
   }

   public static List<Node> collectNodes(Node parent, Node child) {
      List<Node> nodes = new ArrayList<>();

      for (Node current = child; current != null; current = current.getParent()) {
         nodes.add(current);
         if (current == parent) {
            return nodes;
         }
      }

      throw new IllegalArgumentException("Node " + parent + " is not a parent of " + child + ".");
   }

   public static <T> T findFirstNodeInstance(Node root, Class<T> clazz) {
      if (clazz.isInstance(root)) {
         return clazz.cast(root);
      } else {
         for (Node child : root.getChildren()) {
            T node = findFirstNodeInstance(child, clazz);
            if (node != null) {
               return node;
            }
         }

         return null;
      }
   }

   public static <T> List<T> findAllNodeInstances(final Node root, final Class<T> clazz) {
      final List<T> nodeList = new ArrayList<>();
      root.accept(new NodeVisitor() {
         @Override
         public boolean visit(Node node) {
            if (clazz.isInstance(node)) {
               nodeList.add(clazz.cast(node));
            }

            return true;
         }
      });
      return nodeList;
   }

   public static int countNodes(Node root) {
      return countNodes(root, NodeUtil.NodeCountFilter.NO_FILTER);
   }

   public static int countNodes(Node root, NodeUtil.NodeCountFilter filter) {
      NodeUtil.NodeCounter counter = new NodeUtil.NodeCounter(filter);
      root.accept(counter);
      return counter.count;
   }

   public static String printCompactTreeToString(Node node) {
      StringWriter out = new StringWriter();
      printCompactTree(new PrintWriter(out), null, node, 1);
      return out.toString();
   }

   public static void printCompactTree(OutputStream out, Node node) {
      printCompactTree(new PrintWriter(out), null, node, 1);
   }

   private static void printCompactTree(PrintWriter p, Node parent, Node node, int level) {
      if (node != null) {
         for (int i = 0; i < level; i++) {
            p.print("  ");
         }

         if (parent == null) {
            p.println(nodeName(node));
         } else {
            p.print(getNodeFieldName(parent, node, "unknownField"));
            p.print(" = ");
            p.println(nodeName(node));
         }

         for (Node child : node.getChildren()) {
            printCompactTree(p, node, child, level + 1);
         }

         p.flush();
      }
   }

   public static String printSourceAttributionTree(Node node) {
      StringWriter out = new StringWriter();
      printSourceAttributionTree(new PrintWriter(out), null, node, 1);
      return out.toString();
   }

   public static void printSourceAttributionTree(OutputStream out, Node node) {
      printSourceAttributionTree(new PrintWriter(out), null, node, 1);
   }

   public static void printSourceAttributionTree(PrintWriter out, Node node) {
      printSourceAttributionTree(out, null, node, 1);
   }

   private static void printSourceAttributionTree(PrintWriter p, Node parent, Node node, int level) {
      if (node != null) {
         if (parent == null) {
            SourceSection sourceSection = node.getSourceSection();
            if (sourceSection != null) {
               String txt = sourceSection.getSource().getCharacters().toString();
               p.println("Full source len=(" + txt.length() + ")  ___" + txt + "___");
               p.println("AST source attribution:");
            }
         }

         StringBuilder sb = new StringBuilder();

         for (int i = 0; i < level; i++) {
            sb.append("| ");
         }

         if (parent != null) {
            sb.append(getNodeFieldName(parent, node, ""));
         }

         sb.append("  (" + node.getClass().getSimpleName() + ")  ");
         sb.append(printSyntaxTags(node));
         sb.append(displaySourceAttribution(node));
         p.println(sb.toString());

         for (Node child : node.getChildren()) {
            printSourceAttributionTree(p, node, child, level + 1);
         }

         p.flush();
      }
   }

   private static String getNodeFieldName(Node parent, Node node, String defaultName) {
      NodeClass nodeClass = parent.getNodeClass();

      for (Object field : nodeClass.getNodeFieldArray()) {
         if (nodeClass.isChildField(field)) {
            if (nodeClass.getFieldObject(field, parent) == node) {
               return nodeClass.getFieldName(field);
            }
         } else if (nodeClass.isChildrenField(field)) {
            Object[] arrayNodes = (Object[])nodeClass.getFieldObject(field, parent);
            if (arrayNodes != null) {
               int index = 0;

               for (Object arrayNode : arrayNodes) {
                  if (arrayNode == node) {
                     return nodeClass.getFieldName(field) + "[" + index + "]";
                  }

                  index++;
               }
            }
         } else if (nodeClass.nodeFieldsOrderedByKind()) {
            break;
         }
      }

      return defaultName;
   }

   public static String printSyntaxTags(final Object node) {
      return node instanceof Node && ((Node)node).getSourceSection() != null ? ((Node)node).getSourceSection().toString() : "";
   }

   public static void printTree(OutputStream out, Node node) {
      printTree(new PrintWriter(out), node);
   }

   public static String printTreeToString(Node node) {
      StringWriter out = new StringWriter();
      printTree(new PrintWriter(out), node);
      return out.toString();
   }

   public static void printTree(PrintWriter p, Node node) {
      printTree(p, node, 1);
      p.println();
      p.flush();
   }

   private static void printTree(PrintWriter p, Node node, int level) {
      if (node == null) {
         p.print("null");
      } else {
         p.print(nodeName(node));
         ArrayList<Object> childFields = new ArrayList<>();
         String sep = "";
         p.print("(");
         NodeClass nodeClass = NodeClass.get(node);

         for (Object field : nodeClass.getNodeFieldArray()) {
            if (!nodeClass.isChildField(field) && !nodeClass.isChildrenField(field)) {
               p.print(sep);
               sep = ", ";
               p.print(nodeClass.getFieldName(field));
               p.print(" = ");
               p.print(nodeClass.getFieldValue(field, node));
            } else {
               childFields.add(field);
            }
         }

         p.print(")");
         if (childFields.size() != 0) {
            p.print(" {");

            for (Object fieldx : nodeClass.getNodeFieldArray()) {
               printNewLine(p, level);
               p.print(nodeClass.getFieldName(fieldx));
               Object value = nodeClass.getFieldValue(fieldx, node);
               if (value == null) {
                  p.print(" = null ");
               } else if (nodeClass.isChildField(fieldx)) {
                  p.print(" = ");
                  printTree(p, (Node)value, level + 1);
               } else if (nodeClass.isChildrenField(fieldx)) {
                  printChildren(p, level, value);
               }
            }

            printNewLine(p, level - 1);
            p.print("}");
         }
      }
   }

   private static void printChildren(PrintWriter p, int level, Object value) {
      Object[] children = (Object[])value;
      p.print(" = [");
      String sep = "";

      for (Object child : children) {
         p.print(sep);
         sep = ", ";
         printTree(p, (Node)child, level + 1);
      }

      p.print("]");
   }

   private static void printNewLine(PrintWriter p, int level) {
      p.println();

      for (int i = 0; i < level; i++) {
         p.print("    ");
      }
   }

   private static String nodeName(Node node) {
      return className(node.getClass());
   }

   static String className(Class<?> clazz) {
      String name = clazz.getName();
      return name.substring(name.lastIndexOf(46) + 1);
   }

   private static String displaySourceAttribution(Node node) {
      SourceSection section = node.getSourceSection();
      if (section == null) {
         return "";
      } else if (section.getSource() == null) {
         return "source: <unknown>";
      } else {
         String srcText = section.getCharacters().toString();
         StringBuilder sb = new StringBuilder();
         sb.append("source:");
         sb.append(" (" + section.getCharIndex() + "," + (section.getCharEndIndex() - 1) + ")");
         sb.append(" line=" + section.getStartLine());
         sb.append(" len=" + srcText.length());
         sb.append(" text=\"" + srcText + "\"");
         return sb.toString();
      }
   }

   public static boolean verify(Node root) {
      for (Node child : root.getChildren()) {
         if (child != null) {
            if (child.getParent() != root) {
               throw new AssertionError(
                  toStringWithClass(child) + ": actual parent=" + toStringWithClass(child.getParent()) + " expected parent=" + toStringWithClass(root)
               );
            }

            verify(child);
         }
      }

      return true;
   }

   private static String toStringWithClass(Object obj) {
      return obj == null ? "null" : obj + "(" + obj.getClass().getName() + ")";
   }

   static void traceRewrite(Node oldNode, Node newNode, CharSequence reason) {
      if (TruffleOptions.TraceRewritesFilterFromCost == null || !filterByKind(oldNode, TruffleOptions.TraceRewritesFilterFromCost)) {
         if (TruffleOptions.TraceRewritesFilterToCost == null || !filterByKind(newNode, TruffleOptions.TraceRewritesFilterToCost)) {
            String filter = TruffleOptions.TraceRewritesFilterClass;
            Class<? extends Node> from = (Class<? extends Node>)oldNode.getClass();
            Class<? extends Node> to = (Class<? extends Node>)newNode.getClass();
            if (filter == null || !filterByContainsClassName(from, filter) && !filterByContainsClassName(to, filter)) {
               SourceSection reportedSourceSection = oldNode.getEncapsulatingSourceSection();
               PrintStream out = System.out;
               out.printf(
                  "[truffle]   rewrite %-50s |From %-40s |To %-40s |Reason %s %s%n",
                  oldNode.toString(),
                  formatNodeInfo(oldNode),
                  formatNodeInfo(newNode),
                  reason != null && reason.length() > 0 ? reason : "unknown",
                  formatLocation(reportedSourceSection)
               );
            }
         }
      }
   }

   private static String formatLocation(SourceSection sourceSection) {
      if (sourceSection == null) {
         return "";
      } else {
         return sourceSection.getSource() == null
            ? "at <Unknown>"
            : "at " + String.format("%s:%d", sourceSection.getSource().getName(), sourceSection.getStartLine());
      }
   }

   private static String formatNodeInfo(Node node) {
      String cost = "?";
      switch (node.getCost()) {
         case NONE:
            cost = "G";
            break;
         case MONOMORPHIC:
            cost = "M";
            break;
         case POLYMORPHIC:
            cost = "P";
            break;
         case MEGAMORPHIC:
            cost = "G";
            break;
         default:
            cost = "?";
      }

      return cost + " " + nodeName(node);
   }

   private static boolean filterByKind(Node node, NodeCost cost) {
      return node.getCost() == cost;
   }

   private static boolean filterByContainsClassName(Class<? extends Node> from, String filter) {
      for (Class<?> currentFrom = from; currentFrom != null; currentFrom = currentFrom.getSuperclass()) {
         if (currentFrom.getName().contains(filter)) {
            return false;
         }
      }

      return true;
   }

   public static boolean assertRecursion(Node node, int maxRecursion) {
      if (node == null) {
         return true;
      } else {
         Node parent = node.getParent();

         for (int counter = 0; parent != null; parent = parent.getParent()) {
            if (node.getClass() == parent.getClass() && counter++ == maxRecursion) {
               throw new AssertionError(String.format("Invalid recursion detected. Path to recursion: %n%s", printRecursionPath(node, node.getClass())));
            }
         }

         return true;
      }
   }

   private static String printRecursionPath(Node node, Class<?> recursiveType) {
      StringBuilder path = new StringBuilder();
      path.append("     ").append(node.getClass().getTypeName()).append(System.lineSeparator());
      Node current = node;
      Node parent = node.getParent();

      do {
         path.append("  <- ");
         if (parent != null) {
            String fieldName = findChildFieldName(parent, current);
            path.append(parent.getClass().getTypeName());
            if (fieldName != null) {
               path.append(".");
               path.append(fieldName);
            }

            if (parent.getClass() == recursiveType) {
               path.append(" <-recursion-detected->");
            }
         }

         current = parent;
         if (parent != null) {
            parent = parent.getParent();
         }

         if (parent != null) {
            path.append(System.lineSeparator());
         }
      } while (parent != null);

      return path.toString();
   }

   public interface NodeCountFilter {
      NodeUtil.NodeCountFilter NO_FILTER = new NodeUtil.NodeCountFilter() {
         @Override
         public boolean isCounted(Node node) {
            return true;
         }
      };

      boolean isCounted(Node node);
   }

   private static final class NodeCounter implements NodeVisitor {
      public int count;
      private final NodeUtil.NodeCountFilter filter;

      NodeCounter(NodeUtil.NodeCountFilter filter) {
         this.filter = filter;
      }

      @Override
      public boolean visit(Node node) {
         if (this.filter.isCounted(node)) {
            this.count++;
         }

         return true;
      }
   }
}
