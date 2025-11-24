
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeClass;
import com.oracle.truffle.api.nodes.NodeCloneable;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeVisitor;
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
        if (!NodeUtil.sameType(clone, orig)) {
            throw CompilerDirectives.shouldNotReachHere(String.format("Invalid return type after copy(): orig.getClass() = %s, clone.getClass() = %s", orig.getClass(), clone == null ? "null" : clone.getClass()));
        }
        NodeClass nodeClass = clone.getNodeClass();
        clone.setParent(null);
        for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field)) {
                Node child = (Node)nodeClass.getFieldObject(field, orig);
                if (child == null) continue;
                Node clonedChild = child.deepCopy();
                clonedChild.setParent(clone);
                if (!NodeUtil.sameType(child, clonedChild)) {
                    throw CompilerDirectives.shouldNotReachHere(String.format("Invalid return type after deepCopy(): orig.getClass() = %s, orig.fieldName = '%s', child.getClass() = %s, clonedChild.getClass() = %s", orig.getClass(), nodeClass.getFieldName(field), child.getClass(), clonedChild.getClass()));
                }
                nodeClass.putFieldObject(field, clone, clonedChild);
                continue;
            }
            if (nodeClass.isChildrenField(field)) {
                Object[] clonedChildren;
                Object[] children = (Object[])nodeClass.getFieldObject(field, orig);
                if (children == null) continue;
                if (children.length > 0) {
                    clonedChildren = (Object[])Array.newInstance(children.getClass().getComponentType(), children.length);
                    for (int i = 0; i < children.length; ++i) {
                        if (children[i] == null) continue;
                        Node clonedChild = ((Node)children[i]).deepCopy();
                        if (!NodeUtil.sameType(children[i], clonedChild)) {
                            throw CompilerDirectives.shouldNotReachHere(String.format("Invalid return type after deepCopy(): orig.getClass() = %s, orig.fieldName = '%s', children[i].getClass() = %s, clonedChild.getClass() = %s", orig.getClass(), nodeClass.getFieldName(field), children[i].getClass(), clonedChild == null ? "null" : clonedChild.getClass()));
                        }
                        clonedChild.setParent(clone);
                        clonedChildren[i] = clonedChild;
                    }
                } else {
                    clonedChildren = children;
                }
                nodeClass.putFieldObject(field, clone, clonedChildren);
                continue;
            }
            if (nodeClass.isCloneableField(field)) {
                Object cloneable = nodeClass.getFieldObject(field, clone);
                if (cloneable == null || cloneable != nodeClass.getFieldObject(field, orig)) continue;
                Object clonedClonable = ((NodeCloneable)cloneable).clone();
                if (!NodeUtil.sameType(cloneable, clonedClonable)) {
                    throw CompilerDirectives.shouldNotReachHere(String.format("Invalid return type after clone(): orig.getClass() = %s, orig.fieldName = '%s', cloneable.getClass() = %s, clonedCloneable.getClass() =%s", orig.getClass(), nodeClass.getFieldName(field), cloneable.getClass(), clonedClonable == null ? "null" : clonedClonable.getClass()));
                }
                nodeClass.putFieldObject(field, clone, clonedClonable);
                continue;
            }
            if (nodeClass.nodeFieldsOrderedByKind()) break;
        }
        return clone;
    }

    private static boolean sameType(Object clone, Object orig) {
        if (clone == null || orig == null) {
            return clone == orig;
        }
        return clone.getClass() == orig.getClass();
    }

    public static List<Node> findNodeChildren(Node node) {
        CompilerAsserts.neverPartOfCompilation("do not call Node.findNodeChildren from compiled code");
        ArrayList<Node> nodes = new ArrayList<Node>();
        NodeClass nodeClass = node.getNodeClass();
        for (Object nodeField : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(nodeField)) {
                Object child = nodeClass.getFieldObject(nodeField, node);
                if (child == null) continue;
                nodes.add((Node)child);
                continue;
            }
            if (nodeClass.isChildrenField(nodeField)) {
                Object[] children = (Object[])nodeClass.getFieldObject(nodeField, node);
                if (children == null) continue;
                for (Object child : children) {
                    if (child == null) continue;
                    nodes.add((Node)child);
                }
                continue;
            }
            if (nodeClass.nodeFieldsOrderedByKind()) break;
        }
        return nodes;
    }

    public static <T extends Node> T nonAtomicReplace(Node oldNode, T newNode, CharSequence reason) {
        oldNode.replaceHelper(newNode, reason);
        return newNode;
    }

    public static boolean replaceChild(Node parent, Node oldChild, Node newChild) {
        return NodeUtil.replaceChild(parent, oldChild, newChild, false);
    }

    static void adoptChildrenHelper(Node currentNode) {
        NodeClass clazz = currentNode.getNodeClass();
        for (Object field : clazz.getNodeFieldArray()) {
            if (clazz.isChildField(field)) {
                Node node;
                Object child = clazz.getFieldObject(field, currentNode);
                if (child == null || (node = (Node)child).getParent() == currentNode) continue;
                currentNode.adoptHelper(node);
                continue;
            }
            if (clazz.isChildrenField(field)) {
                Object arrayObject = clazz.getFieldObject(field, currentNode);
                if (arrayObject == null) continue;
                Object[] array = (Object[])arrayObject;
                for (int i = 0; i < array.length; ++i) {
                    Node node;
                    Object child = array[i];
                    if (child == null || (node = (Node)child).getParent() == currentNode) continue;
                    currentNode.adoptHelper(node);
                }
                continue;
            }
            if (clazz.nodeFieldsOrderedByKind()) break;
        }
    }

    static int adoptChildrenAndCountHelper(Node currentNode) {
        int count = 0;
        NodeClass clazz = currentNode.getNodeClass();
        for (Object field : clazz.getNodeFieldArray()) {
            if (clazz.isChildField(field)) {
                Object child = clazz.getFieldObject(field, currentNode);
                if (child == null) continue;
                Node node = (Node)child;
                count += currentNode.adoptAndCountHelper(node);
                continue;
            }
            if (clazz.isChildrenField(field)) {
                Object arrayObject = clazz.getFieldObject(field, currentNode);
                if (arrayObject == null) continue;
                Object[] array = (Object[])arrayObject;
                for (int i = 0; i < array.length; ++i) {
                    Object child = array[i];
                    if (child == null) continue;
                    Node node = (Node)child;
                    count += currentNode.adoptAndCountHelper(node);
                }
                continue;
            }
            if (clazz.nodeFieldsOrderedByKind()) break;
        }
        return count;
    }

    static boolean replaceChild(Node parent, Node oldChild, Node newChild, boolean adopt) {
        CompilerAsserts.neverPartOfCompilation("do not replace Node child from compiled code");
        NodeClass nodeClass = parent.getNodeClass();
        if (!oldChild.getNodeClass().isReplaceAllowed()) {
            throw new IllegalArgumentException(String.format("Replaced node type '%s' does not allow replacement.", oldChild.getClass().getName()));
        }
        if (!newChild.getNodeClass().isReplaceAllowed()) {
            throw new IllegalArgumentException(String.format("Replacing node type '%s' does not allow replacement.", newChild.getClass().getName()));
        }
        for (Object nodeField : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(nodeField)) {
                if (nodeClass.getFieldObject(nodeField, parent) != oldChild) continue;
                if (adopt) {
                    parent.adoptHelper(newChild);
                }
                nodeClass.putFieldObject(nodeField, parent, newChild);
                return true;
            }
            if (nodeClass.isChildrenField(nodeField)) {
                Object arrayObject = nodeClass.getFieldObject(nodeField, parent);
                if (arrayObject == null) continue;
                Object[] array = (Object[])arrayObject;
                for (int i = 0; i < array.length; ++i) {
                    if (array[i] != oldChild) continue;
                    if (adopt) {
                        parent.adoptHelper(newChild);
                    }
                    try {
                        array[i] = newChild;
                    }
                    catch (ArrayStoreException e) {
                        throw NodeUtil.replaceChildIllegalArgumentException(nodeField, array.getClass(), newChild);
                    }
                    return true;
                }
                continue;
            }
            if (nodeClass.nodeFieldsOrderedByKind()) break;
        }
        return false;
    }

    private static IllegalArgumentException replaceChildIllegalArgumentException(Object nodeField, Class<?> fieldType, Node newChild) {
        return new IllegalArgumentException("Cannot set element of " + fieldType.getName() + " field " + nodeField + " to " + (newChild == null ? "null" : newChild.getClass().getName()));
    }

    public static String findChildFieldName(Node parent, Node child) {
        return NodeUtil.getNodeFieldName(parent, child, null);
    }

    public static List<String> collectFieldNames(Class<? extends Node> clazz) {
        NodeClass nodeClass = NodeClass.get(clazz);
        Object[] fields = nodeClass.getNodeFieldArray();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            fieldNames[i] = nodeClass.getFieldName(fields[i]);
        }
        return Arrays.asList(fieldNames);
    }

    public static Map<String, Node> collectNodeChildren(Node node) {
        LinkedHashMap<Object, Node> nodes = new LinkedHashMap<Object, Node>();
        NodeClass nodeClass = NodeClass.get(node);
        for (Object field : nodeClass.getNodeFieldArray()) {
            Object value2;
            if (nodeClass.isChildField(field)) {
                value2 = nodeClass.getFieldObject(field, node);
                if (value2 == null) continue;
                nodes.put(nodeClass.getFieldName(field), (Node)value2);
                continue;
            }
            if (!nodeClass.isChildrenField(field) || (value2 = nodeClass.getFieldObject(field, node)) == null) continue;
            Object[] children = (Object[])value2;
            for (int i = 0; i < children.length; ++i) {
                if (children[i] == null) continue;
                nodes.put(nodeClass.getFieldName(field) + "[" + i + "]", (Node)children[i]);
            }
        }
        return Collections.unmodifiableMap(nodes);
    }

    public static Map<String, Object> collectNodeProperties(Node node) {
        LinkedHashMap<String, Object> nodes = new LinkedHashMap<String, Object>();
        NodeClass nodeClass = NodeClass.get(node);
        for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field) || nodeClass.isChildrenField(field)) continue;
            nodes.put(nodeClass.getFieldName(field), nodeClass.getFieldValue(field, node));
        }
        return Collections.unmodifiableMap(nodes);
    }

    static Object findChildField(Node parent, Node child) {
        assert (child != null);
        NodeClass parentNodeClass = parent.getNodeClass();
        for (Object field : parentNodeClass.getNodeFieldArray()) {
            Object arrayObject;
            if (parentNodeClass.isChildField(field)) {
                return field;
            }
            if (!parentNodeClass.isChildrenField(field) || (arrayObject = parentNodeClass.getFieldValue(field, child)) == null) continue;
            Object[] array = (Object[])arrayObject;
            for (int i = 0; i < array.length; ++i) {
                if (array[i] != child) continue;
                return field;
            }
        }
        return null;
    }

    public static boolean isReplacementSafe(Node parent, Node oldChild, Node newChild) {
        if (parent != null) {
            if (!parent.isAdoptable()) {
                return false;
            }
            NodeClass nodeClass = parent.getNodeClass();
            for (Object field : nodeClass.getNodeFieldArray()) {
                if (nodeClass.isChildField(field)) {
                    if (nodeClass.getFieldObject(field, parent) != oldChild) continue;
                    if (!oldChild.getNodeClass().isReplaceAllowed() || !newChild.getNodeClass().isReplaceAllowed()) {
                        return false;
                    }
                    return nodeClass.getFieldType(field).isAssignableFrom(newChild.getClass());
                }
                if (nodeClass.isChildrenField(field)) {
                    Object arrayObject = nodeClass.getFieldObject(field, parent);
                    if (arrayObject == null) continue;
                    Object[] array = (Object[])arrayObject;
                    for (int i = 0; i < array.length; ++i) {
                        if (array[i] != oldChild) continue;
                        if (!oldChild.getNodeClass().isReplaceAllowed() || !newChild.getNodeClass().isReplaceAllowed()) {
                            return false;
                        }
                        return nodeClass.getFieldType(field).getComponentType().isAssignableFrom(newChild.getClass());
                    }
                    continue;
                }
                if (nodeClass.nodeFieldsOrderedByKind()) break;
            }
            return true;
        }
        return false;
    }

    public static boolean forEachChild(Node parent, NodeVisitor visitor) {
        CompilerAsserts.neverPartOfCompilation("do not iterate over Node children from compiled code");
        Objects.requireNonNull(visitor);
        NodeClass nodeClass = parent.getNodeClass();
        for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field)) {
                Object child = nodeClass.getFieldObject(field, parent);
                if (child == null || visitor.visit((Node)child)) continue;
                return false;
            }
            if (nodeClass.isChildrenField(field)) {
                Object arrayObject = nodeClass.getFieldObject(field, parent);
                if (arrayObject == null) continue;
                Object[] array = (Object[])arrayObject;
                for (int i = 0; i < array.length; ++i) {
                    Object child = array[i];
                    if (child == null || visitor.visit((Node)child)) continue;
                    return false;
                }
                continue;
            }
            if (nodeClass.nodeFieldsOrderedByKind()) break;
        }
        return true;
    }

    static boolean forEachChildRecursive(Node parent, NodeVisitor visitor) {
        NodeClass nodeClass = parent.getNodeClass();
        for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field)) {
                if (NodeUtil.visitChild((Node)nodeClass.getFieldObject(field, parent), visitor)) continue;
                return false;
            }
            if (nodeClass.isChildrenField(field)) {
                Object arrayObject = nodeClass.getFieldObject(field, parent);
                if (arrayObject == null) continue;
                Object[] array = (Object[])arrayObject;
                for (int i = 0; i < array.length; ++i) {
                    if (NodeUtil.visitChild((Node)array[i], visitor)) continue;
                    return false;
                }
                continue;
            }
            if (nodeClass.nodeFieldsOrderedByKind()) break;
        }
        return true;
    }

    private static boolean visitChild(Node child, NodeVisitor visitor) {
        if (child == null) {
            return true;
        }
        if (!visitor.visit(child)) {
            return false;
        }
        return NodeUtil.forEachChildRecursive(child, visitor);
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static Node getNthParent(Node node, int n) {
        Node parent = node;
        for (int i = 0; i < n; ++i) {
            if ((parent = parent.getParent()) != null) continue;
            return null;
        }
        return parent;
    }

    public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationClass) {
        if (clazz.getAnnotation(annotationClass) != null) {
            return clazz.getAnnotation(annotationClass);
        }
        if (!TruffleOptions.AOT) {
            for (Class<?> intf : clazz.getInterfaces()) {
                if (intf.getAnnotation(annotationClass) == null) continue;
                return intf.getAnnotation(annotationClass);
            }
        }
        if (clazz.getSuperclass() != null) {
            return NodeUtil.findAnnotation(clazz.getSuperclass(), annotationClass);
        }
        return null;
    }

    public static <T> T findParent(Node start2, Class<T> clazz) {
        Node parent = start2.getParent();
        if (parent == null) {
            return null;
        }
        if (clazz.isInstance(parent)) {
            return clazz.cast(parent);
        }
        return NodeUtil.findParent(parent, clazz);
    }

    public static <T> List<T> findAllParents(Node start2, Class<T> clazz) {
        ArrayList<T> parents = new ArrayList<T>();
        T parent = NodeUtil.findParent(start2, clazz);
        while (parent != null) {
            parents.add(parent);
            parent = NodeUtil.findParent((Node)parent, clazz);
        }
        return parents;
    }

    public static List<Node> collectNodes(Node parent, Node child) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (Node current = child; current != null; current = current.getParent()) {
            nodes.add(current);
            if (current != parent) continue;
            return nodes;
        }
        throw new IllegalArgumentException("Node " + parent + " is not a parent of " + child + ".");
    }

    public static <T> T findFirstNodeInstance(Node root, Class<T> clazz) {
        if (clazz.isInstance(root)) {
            return clazz.cast(root);
        }
        for (Node child : root.getChildren()) {
            T node = NodeUtil.findFirstNodeInstance(child, clazz);
            if (node == null) continue;
            return node;
        }
        return null;
    }

    public static <T> List<T> findAllNodeInstances(Node root, final Class<T> clazz) {
        final ArrayList nodeList = new ArrayList();
        root.accept(new NodeVisitor(){

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
        return NodeUtil.countNodes(root, NodeCountFilter.NO_FILTER);
    }

    public static int countNodes(Node root, NodeCountFilter filter) {
        NodeCounter counter = new NodeCounter(filter);
        root.accept(counter);
        return counter.count;
    }

    public static String printCompactTreeToString(Node node) {
        StringWriter out = new StringWriter();
        NodeUtil.printCompactTree(new PrintWriter(out), null, node, 1);
        return out.toString();
    }

    public static void printCompactTree(OutputStream out, Node node) {
        NodeUtil.printCompactTree(new PrintWriter(out), null, node, 1);
    }

    private static void printCompactTree(PrintWriter p, Node parent, Node node, int level) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < level; ++i) {
            p.print("  ");
        }
        if (parent == null) {
            p.println(NodeUtil.nodeName(node));
        } else {
            p.print(NodeUtil.getNodeFieldName(parent, node, "unknownField"));
            p.print(" = ");
            p.println(NodeUtil.nodeName(node));
        }
        for (Node child : node.getChildren()) {
            NodeUtil.printCompactTree(p, node, child, level + 1);
        }
        p.flush();
    }

    public static String printSourceAttributionTree(Node node) {
        StringWriter out = new StringWriter();
        NodeUtil.printSourceAttributionTree(new PrintWriter(out), null, node, 1);
        return out.toString();
    }

    public static void printSourceAttributionTree(OutputStream out, Node node) {
        NodeUtil.printSourceAttributionTree(new PrintWriter(out), null, node, 1);
    }

    public static void printSourceAttributionTree(PrintWriter out, Node node) {
        NodeUtil.printSourceAttributionTree(out, null, node, 1);
    }

    private static void printSourceAttributionTree(PrintWriter p, Node parent, Node node, int level) {
        SourceSection sourceSection;
        if (node == null) {
            return;
        }
        if (parent == null && (sourceSection = node.getSourceSection()) != null) {
            String txt = sourceSection.getSource().getCharacters().toString();
            p.println("Full source len=(" + txt.length() + ")  ___" + txt + "___");
            p.println("AST source attribution:");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; ++i) {
            sb.append("| ");
        }
        if (parent != null) {
            sb.append(NodeUtil.getNodeFieldName(parent, node, ""));
        }
        sb.append("  (" + node.getClass().getSimpleName() + ")  ");
        sb.append(NodeUtil.printSyntaxTags(node));
        sb.append(NodeUtil.displaySourceAttribution(node));
        p.println(sb.toString());
        for (Node child : node.getChildren()) {
            NodeUtil.printSourceAttributionTree(p, node, child, level + 1);
        }
        p.flush();
    }

    private static String getNodeFieldName(Node parent, Node node, String defaultName) {
        NodeClass nodeClass = parent.getNodeClass();
        for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field)) {
                if (nodeClass.getFieldObject(field, parent) != node) continue;
                return nodeClass.getFieldName(field);
            }
            if (nodeClass.isChildrenField(field)) {
                Object[] arrayNodes = (Object[])nodeClass.getFieldObject(field, parent);
                if (arrayNodes == null) continue;
                int index = 0;
                for (Object arrayNode : arrayNodes) {
                    if (arrayNode == node) {
                        return nodeClass.getFieldName(field) + "[" + index + "]";
                    }
                    ++index;
                }
                continue;
            }
            if (nodeClass.nodeFieldsOrderedByKind()) break;
        }
        return defaultName;
    }

    public static String printSyntaxTags(Object node) {
        if (node instanceof Node && ((Node)node).getSourceSection() != null) {
            return ((Node)node).getSourceSection().toString();
        }
        return "";
    }

    public static void printTree(OutputStream out, Node node) {
        NodeUtil.printTree(new PrintWriter(out), node);
    }

    public static String printTreeToString(Node node) {
        StringWriter out = new StringWriter();
        NodeUtil.printTree(new PrintWriter(out), node);
        return out.toString();
    }

    public static void printTree(PrintWriter p, Node node) {
        NodeUtil.printTree(p, node, 1);
        p.println();
        p.flush();
    }

    private static void printTree(PrintWriter p, Node node, int level) {
        if (node == null) {
            p.print("null");
            return;
        }
        p.print(NodeUtil.nodeName(node));
        ArrayList<Object> childFields = new ArrayList<Object>();
        String sep = "";
        p.print("(");
        NodeClass nodeClass = NodeClass.get(node);
        for (Object field : nodeClass.getNodeFieldArray()) {
            if (nodeClass.isChildField(field) || nodeClass.isChildrenField(field)) {
                childFields.add(field);
                continue;
            }
            p.print(sep);
            sep = ", ";
            p.print(nodeClass.getFieldName(field));
            p.print(" = ");
            p.print(nodeClass.getFieldValue(field, node));
        }
        p.print(")");
        if (childFields.size() != 0) {
            p.print(" {");
            for (Object field : nodeClass.getNodeFieldArray()) {
                NodeUtil.printNewLine(p, level);
                p.print(nodeClass.getFieldName(field));
                Object value2 = nodeClass.getFieldValue(field, node);
                if (value2 == null) {
                    p.print(" = null ");
                    continue;
                }
                if (nodeClass.isChildField(field)) {
                    p.print(" = ");
                    NodeUtil.printTree(p, (Node)value2, level + 1);
                    continue;
                }
                if (!nodeClass.isChildrenField(field)) continue;
                NodeUtil.printChildren(p, level, value2);
            }
            NodeUtil.printNewLine(p, level - 1);
            p.print("}");
        }
    }

    private static void printChildren(PrintWriter p, int level, Object value2) {
        Object[] children = (Object[])value2;
        p.print(" = [");
        String sep = "";
        for (Object child : children) {
            p.print(sep);
            sep = ", ";
            NodeUtil.printTree(p, (Node)child, level + 1);
        }
        p.print("]");
    }

    private static void printNewLine(PrintWriter p, int level) {
        p.println();
        for (int i = 0; i < level; ++i) {
            p.print("    ");
        }
    }

    private static String nodeName(Node node) {
        return NodeUtil.className(node.getClass());
    }

    static String className(Class<?> clazz) {
        String name = clazz.getName();
        return name.substring(name.lastIndexOf(46) + 1);
    }

    private static String displaySourceAttribution(Node node) {
        SourceSection section = node.getSourceSection();
        if (section == null) {
            return "";
        }
        if (section.getSource() == null) {
            return "source: <unknown>";
        }
        String srcText = section.getCharacters().toString();
        StringBuilder sb = new StringBuilder();
        sb.append("source:");
        sb.append(" (" + section.getCharIndex() + "," + (section.getCharEndIndex() - 1) + ")");
        sb.append(" line=" + section.getStartLine());
        sb.append(" len=" + srcText.length());
        sb.append(" text=\"" + srcText + "\"");
        return sb.toString();
    }

    public static boolean verify(Node root) {
        Iterable<Node> children = root.getChildren();
        for (Node child : children) {
            if (child == null) continue;
            if (child.getParent() != root) {
                throw new AssertionError((Object)(NodeUtil.toStringWithClass(child) + ": actual parent=" + NodeUtil.toStringWithClass(child.getParent()) + " expected parent=" + NodeUtil.toStringWithClass(root)));
            }
            NodeUtil.verify(child);
        }
        return true;
    }

    private static String toStringWithClass(Object obj) {
        return obj == null ? "null" : obj + "(" + obj.getClass().getName() + ")";
    }

    static void traceRewrite(Node oldNode, Node newNode, CharSequence reason) {
        if (TruffleOptions.TraceRewritesFilterFromCost != null && NodeUtil.filterByKind(oldNode, TruffleOptions.TraceRewritesFilterFromCost)) {
            return;
        }
        if (TruffleOptions.TraceRewritesFilterToCost != null && NodeUtil.filterByKind(newNode, TruffleOptions.TraceRewritesFilterToCost)) {
            return;
        }
        String filter = TruffleOptions.TraceRewritesFilterClass;
        Class<?> from = oldNode.getClass();
        Class<?> to = newNode.getClass();
        if (filter != null && (NodeUtil.filterByContainsClassName(from, filter) || NodeUtil.filterByContainsClassName(to, filter))) {
            return;
        }
        SourceSection reportedSourceSection = oldNode.getEncapsulatingSourceSection();
        PrintStream out = System.out;
        out.printf("[truffle]   rewrite %-50s |From %-40s |To %-40s |Reason %s %s%n", oldNode.toString(), NodeUtil.formatNodeInfo(oldNode), NodeUtil.formatNodeInfo(newNode), reason != null && reason.length() > 0 ? reason : "unknown", NodeUtil.formatLocation(reportedSourceSection));
    }

    private static String formatLocation(SourceSection sourceSection) {
        if (sourceSection == null) {
            return "";
        }
        if (sourceSection.getSource() == null) {
            return "at <Unknown>";
        }
        return "at " + String.format("%s:%d", sourceSection.getSource().getName(), sourceSection.getStartLine());
    }

    private static String formatNodeInfo(Node node) {
        String cost = "?";
        switch (node.getCost()) {
            case NONE: {
                cost = "G";
                break;
            }
            case MONOMORPHIC: {
                cost = "M";
                break;
            }
            case POLYMORPHIC: {
                cost = "P";
                break;
            }
            case MEGAMORPHIC: {
                cost = "G";
                break;
            }
            default: {
                cost = "?";
            }
        }
        return cost + " " + NodeUtil.nodeName(node);
    }

    private static boolean filterByKind(Node node, NodeCost cost) {
        return node.getCost() == cost;
    }

    private static boolean filterByContainsClassName(Class<? extends Node> from, String filter) {
        for (Class<? extends Node> currentFrom = from; currentFrom != null; currentFrom = currentFrom.getSuperclass()) {
            if (!currentFrom.getName().contains(filter)) continue;
            return false;
        }
        return true;
    }

    public static boolean assertRecursion(Node node, int maxRecursion) {
        if (node == null) {
            return true;
        }
        int counter = 0;
        for (Node parent = node.getParent(); parent != null; parent = parent.getParent()) {
            if (node.getClass() == parent.getClass() && counter++ == maxRecursion) {
                throw new AssertionError((Object)String.format("Invalid recursion detected. Path to recursion: %n%s", NodeUtil.printRecursionPath(node, node.getClass())));
            }
        }
        return true;
    }

    private static String printRecursionPath(Node node, Class<?> recursiveType) {
        StringBuilder path = new StringBuilder();
        path.append("     ").append(node.getClass().getTypeName()).append(System.lineSeparator());
        Node current = node;
        Node parent = node.getParent();
        do {
            path.append("  <- ");
            if (parent != null) {
                String fieldName = NodeUtil.findChildFieldName(parent, current);
                path.append(parent.getClass().getTypeName());
                if (fieldName != null) {
                    path.append(".");
                    path.append(fieldName);
                }
                if (parent.getClass() == recursiveType) {
                    path.append(" <-recursion-detected->");
                }
            }
            if ((current = parent) != null) {
                parent = current.getParent();
            }
            if (parent == null) continue;
            path.append(System.lineSeparator());
        } while (parent != null);
        return path.toString();
    }

    private static final class NodeCounter
    implements NodeVisitor {
        public int count;
        private final NodeCountFilter filter;

        NodeCounter(NodeCountFilter filter) {
            this.filter = filter;
        }

        @Override
        public boolean visit(Node node) {
            if (this.filter.isCounted(node)) {
                ++this.count;
            }
            return true;
        }
    }

    public static interface NodeCountFilter {
        public static final NodeCountFilter NO_FILTER = new NodeCountFilter(){

            @Override
            public boolean isCounted(Node node) {
                return true;
            }
        };

        public boolean isCounted(Node var1);
    }
}

