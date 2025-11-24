
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeClass;
import com.oracle.truffle.api.nodes.NodeCloneable;
import com.oracle.truffle.api.nodes.NodeInterface;
import com.oracle.truffle.api.nodes.NodeIterator;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import sun.misc.Unsafe;

final class NodeClassImpl
extends NodeClass {
    private static final NodeFieldData[] EMPTY_NODE_FIELD_ARRAY = new NodeFieldData[0];
    private final NodeFieldData[] fields;
    private final Class<? extends Node> clazz;
    private final boolean replaceAllowed;

    NodeClassImpl(Class<? extends Node> clazz) {
        super(clazz);
        if (!Node.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException();
        }
        ArrayList<NodeFieldData> fieldsList = new ArrayList<NodeFieldData>();
        NodeClassImpl.collectInstanceFields(clazz, fieldsList);
        Collections.sort(fieldsList, new Comparator<NodeFieldData>(){

            @Override
            public int compare(NodeFieldData o1, NodeFieldData o2) {
                return Integer.compare(this.order(o1), this.order(o2));
            }

            private int order(NodeFieldData nodeField) {
                return NodeClassImpl.this.isChildField(nodeField) ? 0 : (NodeClassImpl.this.isChildrenField(nodeField) ? 0 : (NodeClassImpl.this.isCloneableField(nodeField) ? 1 : 2));
            }
        });
        if (clazz.getAnnotation(DenyReplace.class) != null) {
            if (!Modifier.isFinal(clazz.getModifiers())) {
                throw new IllegalStateException("@DenyReplace can only be used for final classes.");
            }
            this.replaceAllowed = false;
        } else {
            this.replaceAllowed = true;
        }
        this.fields = fieldsList.toArray(EMPTY_NODE_FIELD_ARRAY);
        this.clazz = clazz;
    }

    @Override
    protected boolean isReplaceAllowed() {
        return this.replaceAllowed;
    }

    private static void collectInstanceFields(Class<? extends Object> clazz, List<NodeFieldData> fieldsList) {
        Field[] declaredFields;
        if (clazz.getSuperclass() != null) {
            NodeClassImpl.collectInstanceFields(clazz.getSuperclass(), fieldsList);
        }
        for (Field field : declaredFields = clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic() || field.getDeclaringClass() == Node.class && field.getName().equals("parent")) continue;
            NodeFieldData nodeField = NodeClassImpl.createField(field);
            fieldsList.add(nodeField);
        }
    }

    private static NodeFieldData createField(Field field) {
        if (field.getAnnotation(Node.Child.class) != null) {
            NodeClassImpl.checkChildField(field);
            return new NodeFieldData(NodeFieldKind.CHILD, field);
        }
        if (field.getAnnotation(Node.Children.class) != null) {
            NodeClassImpl.checkChildrenField(field);
            return new NodeFieldData(NodeFieldKind.CHILDREN, field);
        }
        return new NodeFieldData(NodeFieldKind.DATA, field);
    }

    private static void checkChildField(Field field) {
        if (!NodeClassImpl.isNodeType(field.getType())) {
            throw new AssertionError((Object)("@Child field type must be a subclass of Node or an interface extending NodeInterface (" + field + ")"));
        }
        if (Modifier.isFinal(field.getModifiers())) {
            throw new AssertionError((Object)("@Child field must not be final (" + field + ")"));
        }
    }

    private static void checkChildrenField(Field field) {
        if (!field.getType().isArray() || !NodeClassImpl.isNodeType(field.getType().getComponentType())) {
            throw new AssertionError((Object)("@Children field type must be an array of a subclass of Node or an interface extending NodeInterface (" + field + ")"));
        }
    }

    @Override
    Field[] getAccessedFields() {
        Field[] reflectionFields = new Field[this.fields.length];
        for (int i = 0; i < this.fields.length; ++i) {
            try {
                reflectionFields[i] = this.fields[i].declaringClass.getDeclaredField(this.fields[i].name);
                continue;
            }
            catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        return reflectionFields;
    }

    private static boolean isNodeType(Class<?> clazz) {
        return Node.class.isAssignableFrom(clazz) || clazz.isInterface() && NodeInterface.class.isAssignableFrom(clazz);
    }

    public int hashCode() {
        return this.clazz.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof NodeClassImpl) {
            NodeClassImpl other = (NodeClassImpl)obj;
            return this.clazz.equals(other.clazz);
        }
        return false;
    }

    @Override
    public Iterator<Node> makeIterator(Node node) {
        assert (this.clazz.isInstance(node));
        return new NodeIterator(this, node, this.fields);
    }

    @Override
    public Class<? extends Node> getType() {
        return this.clazz;
    }

    @Override
    protected Object[] getNodeFieldArray() {
        return this.fields;
    }

    @Override
    protected void putFieldObject(Object field, Node receiver, Object value2) {
        ((NodeFieldData)field).putObject(receiver, value2);
    }

    @Override
    protected Object getFieldObject(Object field, Node receiver) {
        return ((NodeFieldData)field).getObject(receiver);
    }

    @Override
    protected Object getFieldValue(Object field, Node receiver) {
        return ((NodeFieldData)field).getObjectOrPrimitive(receiver);
    }

    @Override
    protected Class<?> getFieldType(Object field) {
        return ((NodeFieldData)field).type;
    }

    @Override
    protected String getFieldName(Object field) {
        return ((NodeFieldData)field).name;
    }

    @Override
    protected boolean isChildField(Object field) {
        return ((NodeFieldData)field).kind == NodeFieldKind.CHILD;
    }

    @Override
    protected boolean isChildrenField(Object field) {
        return ((NodeFieldData)field).kind == NodeFieldKind.CHILDREN;
    }

    @Override
    protected boolean isCloneableField(Object field) {
        return ((NodeFieldData)field).clonable;
    }

    @Override
    boolean nodeFieldsOrderedByKind() {
        return true;
    }

    static final class NodeFieldData {
        final NodeFieldKind kind;
        final Class<?> type;
        final String name;
        final Class<?> declaringClass;
        final long offset;
        final boolean clonable;
        private static final Unsafe UNSAFE = NodeFieldData.getUnsafe();

        NodeFieldData(NodeFieldKind kind, Field field) {
            this.kind = kind;
            this.type = field.getType();
            this.name = field.getName();
            this.declaringClass = field.getDeclaringClass();
            this.offset = UNSAFE.objectFieldOffset(field);
            this.clonable = kind == NodeFieldKind.DATA && NodeCloneable.class.isAssignableFrom(field.getType());
        }

        long getOffset() {
            return this.offset;
        }

        public void putObject(Node receiver, Object value2) {
            assert (this.validateAccess(receiver, value2));
            UNSAFE.putObject((Object)receiver, this.getOffset(), value2);
        }

        private boolean validateAccess(Node receiver, Object value2) {
            if (this.type.isPrimitive() || !this.type.isInstance(value2)) {
                throw this.illegalArgumentException(value2);
            }
            if (this.kind != NodeFieldKind.CHILD) {
                Object oldValue = this.getObject(receiver);
                if (oldValue == null || value2 == null) {
                    if (oldValue != value2) {
                        throw this.illegalArgumentException(value2);
                    }
                } else if (oldValue.getClass() != value2.getClass()) {
                    assert (!(value2 instanceof Node) || ((Node)value2).getNodeClass().isReplaceAllowed()) : "type change not allowed if replace not allowed";
                    assert (!(oldValue instanceof Node) || ((Node)oldValue).getNodeClass().isReplaceAllowed()) : "type change not allowed if replace not allowed";
                    throw this.illegalArgumentException(value2);
                }
            }
            return true;
        }

        private IllegalArgumentException illegalArgumentException(Object value2) {
            return new IllegalArgumentException("Cannot set " + this.type.getName() + " field " + this.toString() + " to " + (value2 == null ? "null" : value2.getClass().getName()));
        }

        public Object getObject(Node receiver) {
            if (!this.type.isPrimitive()) {
                return UNSAFE.getObject((Object)receiver, this.getOffset());
            }
            throw new IllegalArgumentException();
        }

        public Object getObjectOrPrimitive(Node node) {
            if (this.type == Boolean.TYPE) {
                return UNSAFE.getBoolean((Object)node, this.getOffset());
            }
            if (this.type == Byte.TYPE) {
                return UNSAFE.getByte((Object)node, this.getOffset());
            }
            if (this.type == Short.TYPE) {
                return UNSAFE.getShort((Object)node, this.getOffset());
            }
            if (this.type == Character.TYPE) {
                return Character.valueOf(UNSAFE.getChar((Object)node, this.getOffset()));
            }
            if (this.type == Integer.TYPE) {
                return UNSAFE.getInt((Object)node, this.getOffset());
            }
            if (this.type == Long.TYPE) {
                return UNSAFE.getLong((Object)node, this.getOffset());
            }
            if (this.type == Float.TYPE) {
                return Float.valueOf(UNSAFE.getFloat((Object)node, this.getOffset()));
            }
            if (this.type == Double.TYPE) {
                return UNSAFE.getDouble((Object)node, this.getOffset());
            }
            return this.getObject(node);
        }

        private static Unsafe getUnsafe() {
            try {
                return Unsafe.getUnsafe();
            }
            catch (SecurityException securityException) {
                try {
                    Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                    theUnsafeInstance.setAccessible(true);
                    return (Unsafe)theUnsafeInstance.get(Unsafe.class);
                }
                catch (Exception e) {
                    throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e);
                }
            }
        }

        public String toString() {
            return this.declaringClass.getName() + "." + this.name;
        }
    }

    static enum NodeFieldKind {
        CHILD,
        CHILDREN,
        DATA;

    }
}

