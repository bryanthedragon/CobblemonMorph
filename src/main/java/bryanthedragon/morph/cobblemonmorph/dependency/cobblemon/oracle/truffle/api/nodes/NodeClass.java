package com.oracle.truffle.api.nodes;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;

public abstract class NodeClass {
   private static final ClassValue<NodeClass> nodeClasses = new ClassValue<NodeClass>() {
      protected NodeClass computeValue(final Class<?> clazz) {
         return AccessController.doPrivileged(new PrivilegedAction<NodeClass>() {
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

   protected abstract void putFieldObject(Object field, Node receiver, Object value);

   protected abstract Object getFieldObject(Object field, Node receiver);

   protected abstract Object getFieldValue(Object field, Node receiver);

   protected abstract Class<?> getFieldType(Object field);

   protected abstract String getFieldName(Object field);

   protected abstract boolean isChildField(Object field);

   protected abstract boolean isChildrenField(Object field);

   protected abstract boolean isCloneableField(Object field);

   boolean nodeFieldsOrderedByKind() {
      return false;
   }
}
