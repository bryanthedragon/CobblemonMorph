package com.oracle.truffle.api.nodes;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import sun.misc.Unsafe;

final class NodeClassImpl extends NodeClass {
   private static final NodeClassImpl.NodeFieldData[] EMPTY_NODE_FIELD_ARRAY = new NodeClassImpl.NodeFieldData[0];
   private final NodeClassImpl.NodeFieldData[] fields;
   private final Class<? extends Node> clazz;
   private final boolean replaceAllowed;

   NodeClassImpl(Class<? extends Node> clazz) {
      super(clazz);
      if (!Node.class.isAssignableFrom(clazz)) {
         throw new IllegalArgumentException();
      } else {
         List<NodeClassImpl.NodeFieldData> fieldsList = new ArrayList<>();
         collectInstanceFields(clazz, fieldsList);
         Collections.sort(
            fieldsList,
            new Comparator<NodeClassImpl.NodeFieldData>() {
               public int compare(NodeClassImpl.NodeFieldData o1, NodeClassImpl.NodeFieldData o2) {
                  return Integer.compare(this.order(o1), this.order(o2));
               }

               private int order(NodeClassImpl.NodeFieldData nodeField) {
                  return NodeClassImpl.this.isChildField(nodeField)
                     ? 0
                     : (NodeClassImpl.this.isChildrenField(nodeField) ? 0 : (NodeClassImpl.this.isCloneableField(nodeField) ? 1 : 2));
               }
            }
         );
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
   }

   @Override
   protected boolean isReplaceAllowed() {
      return this.replaceAllowed;
   }

   private static void collectInstanceFields(Class<? extends Object> clazz, List<NodeClassImpl.NodeFieldData> fieldsList) {
      if (clazz.getSuperclass() != null) {
         collectInstanceFields((Class<? extends Object>)clazz.getSuperclass(), fieldsList);
      }

      Field[] declaredFields = clazz.getDeclaredFields();

      for (Field field : declaredFields) {
         if (!Modifier.isStatic(field.getModifiers()) && !field.isSynthetic() && (field.getDeclaringClass() != Node.class || !field.getName().equals("parent"))
            )
          {
            NodeClassImpl.NodeFieldData nodeField = createField(field);
            fieldsList.add(nodeField);
         }
      }
   }

   private static NodeClassImpl.NodeFieldData createField(Field field) {
      if (field.getAnnotation(Node.Child.class) != null) {
         checkChildField(field);
         return new NodeClassImpl.NodeFieldData(NodeClassImpl.NodeFieldKind.CHILD, field);
      } else if (field.getAnnotation(Node.Children.class) != null) {
         checkChildrenField(field);
         return new NodeClassImpl.NodeFieldData(NodeClassImpl.NodeFieldKind.CHILDREN, field);
      } else {
         return new NodeClassImpl.NodeFieldData(NodeClassImpl.NodeFieldKind.DATA, field);
      }
   }

   private static void checkChildField(Field field) {
      if (!isNodeType(field.getType())) {
         throw new AssertionError("@Child field type must be a subclass of Node or an interface extending NodeInterface (" + field + ")");
      } else if (Modifier.isFinal(field.getModifiers())) {
         throw new AssertionError("@Child field must not be final (" + field + ")");
      }
   }

   private static void checkChildrenField(Field field) {
      if (!field.getType().isArray() || !isNodeType(field.getType().getComponentType())) {
         throw new AssertionError("@Children field type must be an array of a subclass of Node or an interface extending NodeInterface (" + field + ")");
      }
   }

   @Override
   Field[] getAccessedFields() {
      Field[] reflectionFields = new Field[this.fields.length];

      for (int i = 0; i < this.fields.length; i++) {
         try {
            reflectionFields[i] = this.fields[i].declaringClass.getDeclaredField(this.fields[i].name);
         } catch (NoSuchFieldException var4) {
            throw new RuntimeException(var4);
         }
      }

      return reflectionFields;
   }

   private static boolean isNodeType(Class<?> clazz) {
      return Node.class.isAssignableFrom(clazz) || clazz.isInterface() && NodeInterface.class.isAssignableFrom(clazz);
   }

   @Override
   public int hashCode() {
      return this.clazz.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj instanceof NodeClassImpl) {
         NodeClassImpl other = (NodeClassImpl)obj;
         return this.clazz.equals(other.clazz);
      } else {
         return false;
      }
   }

   @Override
   public Iterator<Node> makeIterator(Node node) {
      assert this.clazz.isInstance(node);

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
   protected void putFieldObject(Object field, Node receiver, Object value) {
      ((NodeClassImpl.NodeFieldData)field).putObject(receiver, value);
   }

   @Override
   protected Object getFieldObject(Object field, Node receiver) {
      return ((NodeClassImpl.NodeFieldData)field).getObject(receiver);
   }

   @Override
   protected Object getFieldValue(Object field, Node receiver) {
      return ((NodeClassImpl.NodeFieldData)field).getObjectOrPrimitive(receiver);
   }

   @Override
   protected Class<?> getFieldType(Object field) {
      return ((NodeClassImpl.NodeFieldData)field).type;
   }

   @Override
   protected String getFieldName(Object field) {
      return ((NodeClassImpl.NodeFieldData)field).name;
   }

   @Override
   protected boolean isChildField(Object field) {
      return ((NodeClassImpl.NodeFieldData)field).kind == NodeClassImpl.NodeFieldKind.CHILD;
   }

   @Override
   protected boolean isChildrenField(Object field) {
      return ((NodeClassImpl.NodeFieldData)field).kind == NodeClassImpl.NodeFieldKind.CHILDREN;
   }

   @Override
   protected boolean isCloneableField(Object field) {
      return ((NodeClassImpl.NodeFieldData)field).clonable;
   }

   @Override
   boolean nodeFieldsOrderedByKind() {
      return true;
   }

   static final class NodeFieldData {
      final NodeClassImpl.NodeFieldKind kind;
      final Class<?> type;
      final String name;
      final Class<?> declaringClass;
      final long offset;
      final boolean clonable;
      private static final Unsafe UNSAFE = getUnsafe();

      NodeFieldData(NodeClassImpl.NodeFieldKind kind, Field field) {
         this.kind = kind;
         this.type = field.getType();
         this.name = field.getName();
         this.declaringClass = field.getDeclaringClass();
         this.offset = UNSAFE.objectFieldOffset(field);
         this.clonable = kind == NodeClassImpl.NodeFieldKind.DATA && NodeCloneable.class.isAssignableFrom(field.getType());
      }

      long getOffset() {
         return this.offset;
      }

      public void putObject(Node receiver, Object value) {
         assert this.validateAccess(receiver, value);

         UNSAFE.putObject(receiver, this.getOffset(), value);
      }

      private boolean validateAccess(Node receiver, Object value) {
         if (!this.type.isPrimitive() && this.type.isInstance(value)) {
            if (this.kind != NodeClassImpl.NodeFieldKind.CHILD) {
               Object oldValue = this.getObject(receiver);
               if (oldValue != null && value != null) {
                  if (oldValue.getClass() != value.getClass()) {
                     assert !(value instanceof Node) || ((Node)value).getNodeClass().isReplaceAllowed() : "type change not allowed if replace not allowed";

                     assert !(oldValue instanceof Node) || ((Node)oldValue).getNodeClass().isReplaceAllowed() : "type change not allowed if replace not allowed";

                     throw this.illegalArgumentException(value);
                  }
               } else if (oldValue != value) {
                  throw this.illegalArgumentException(value);
               }
            }

            return true;
         } else {
            throw this.illegalArgumentException(value);
         }
      }

      private IllegalArgumentException illegalArgumentException(Object value) {
         return new IllegalArgumentException(
            "Cannot set " + this.type.getName() + " field " + this.toString() + " to " + (value == null ? "null" : value.getClass().getName())
         );
      }

      public Object getObject(Node receiver) {
         if (!this.type.isPrimitive()) {
            return UNSAFE.getObject(receiver, this.getOffset());
         } else {
            throw new IllegalArgumentException();
         }
      }

      public Object getObjectOrPrimitive(Node node) {
         if (this.type == boolean.class) {
            return UNSAFE.getBoolean(node, this.getOffset());
         } else if (this.type == byte.class) {
            return UNSAFE.getByte(node, this.getOffset());
         } else if (this.type == short.class) {
            return UNSAFE.getShort(node, this.getOffset());
         } else if (this.type == char.class) {
            return UNSAFE.getChar(node, this.getOffset());
         } else if (this.type == int.class) {
            return UNSAFE.getInt(node, this.getOffset());
         } else if (this.type == long.class) {
            return UNSAFE.getLong(node, this.getOffset());
         } else if (this.type == float.class) {
            return UNSAFE.getFloat(node, this.getOffset());
         } else {
            return this.type == double.class ? UNSAFE.getDouble(node, this.getOffset()) : this.getObject(node);
         }
      }

      private static Unsafe getUnsafe() {
         try {
            return Unsafe.getUnsafe();
         } catch (SecurityException var2) {
            try {
               Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
               theUnsafeInstance.setAccessible(true);
               return (Unsafe)theUnsafeInstance.get(Unsafe.class);
            } catch (Exception var1) {
               throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var1);
            }
         }
      }

      @Override
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
