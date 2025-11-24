
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeClassImpl;
import com.oracle.truffle.api.nodes.NodeIterator;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;

public abstract class NodeClass {
    private static final ClassValue<NodeClass> nodeClasses = new ClassValue<NodeClass>(){

        @Override
        protected NodeClass computeValue(final Class<?> clazz) {
            return AccessController.doPrivileged(new PrivilegedAction<NodeClass>(){

                @Override
                public NodeClass run() {
                    return new NodeClassImpl(clazz.asSubclass(Node.class));
                }
            });
        }
    };

    public static NodeClass get(Class<? extends Node> clazz) {
        return nodeClasses.get(clazz);
    }

    public static NodeClass get(Node node) {
        return node.getNodeClass();
    }

    public NodeClass(Class<? extends Node> clazz) {
    }

    Field[] getAccessedFields() {
        throw new UnsupportedOperationException();
    }

    public Iterator<Node> makeIterator(Node node) {
        return new NodeIterator(this, node, this.getNodeFieldArray());
    }

    protected abstract boolean isReplaceAllowed();

    public abstract Class<? extends Node> getType();

    protected abstract Object[] getNodeFieldArray();

    protected abstract void putFieldObject(Object var1, Node var2, Object var3);

    protected abstract Object getFieldObject(Object var1, Node var2);

    protected abstract Object getFieldValue(Object var1, Node var2);

    protected abstract Class<?> getFieldType(Object var1);

    protected abstract String getFieldName(Object var1);

    protected abstract boolean isChildField(Object var1);

    protected abstract boolean isChildrenField(Object var1);

    protected abstract boolean isCloneableField(Object var1);

    boolean nodeFieldsOrderedByKind() {
        return false;
    }
}

