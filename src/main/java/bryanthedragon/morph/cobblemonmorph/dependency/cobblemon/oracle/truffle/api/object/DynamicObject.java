package com.oracle.truffle.api.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.TruffleObject;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public abstract class DynamicObject implements TruffleObject {
   private Shape shape;
   @DynamicObject.DynamicField
   private Object[] extRef;
   @DynamicObject.DynamicField
   private int[] extVal;
   private static final Unsafe UNSAFE = getUnsafe();
   private static final long SHAPE_OFFSET;

   protected DynamicObject(Shape shape) {
      verifyShape(shape, (Class<? extends DynamicObject>)this.getClass());
      this.shape = shape;
   }

   private static void verifyShape(Shape shape, Class<? extends DynamicObject> subclass) {
      Class<? extends DynamicObject> shapeType = shape.getLayoutClass();
      if (shapeType == subclass || shapeType.isAssignableFrom(subclass) && DynamicObject.class.isAssignableFrom(shapeType)) {
         if (shape.hasInstanceProperties()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw illegalShapeProperties();
         }
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw illegalShapeType(shapeType, subclass);
      }
   }

   @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
   private static IllegalArgumentException illegalShapeType(Class<? extends DynamicObject> shapeClass, Class<? extends DynamicObject> thisClass) {
      throw new IllegalArgumentException(
         String.format("Incompatible shape: layout class (%s) not assignable from this class (%s)", shapeClass.getName(), thisClass.getName())
      );
   }

   @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
   private static IllegalArgumentException illegalShapeProperties() {
      throw new IllegalArgumentException("Shape must not have instance properties");
   }

   public final Shape getShape() {
      return getShapeHelper(this.shape);
   }

   private static Shape getShapeHelper(Shape shape) {
      return shape;
   }

   final void setShape(Shape shape) {
      assert this.assertSetShape(shape);

      this.setShapeHelper(shape, SHAPE_OFFSET);
   }

   private boolean assertSetShape(Shape s) {
      Class<? extends DynamicObject> layoutType = s.getLayoutClass();

      assert layoutType.isInstance(this) : illegalShapeType(layoutType, (Class<? extends DynamicObject>)this.getClass());

      return true;
   }

   private void setShapeHelper(Shape shape, long shapeOffset) {
      this.shape = shape;
   }

   @Override
   protected Object clone() throws CloneNotSupportedException {
      throw cloneNotSupported();
   }

   @CompilerDirectives.TruffleBoundary
   private static CloneNotSupportedException cloneNotSupported() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   final DynamicObject objectClone() throws CloneNotSupportedException {
      return (DynamicObject)super.clone();
   }

   final Object[] getObjectStore() {
      return this.extRef;
   }

   final void setObjectStore(Object[] newArray) {
      this.extRef = newArray;
   }

   final int[] getPrimitiveStore() {
      return this.extVal;
   }

   final void setPrimitiveStore(int[] newArray) {
      this.extVal = newArray;
   }

   static Class<? extends Annotation> getDynamicFieldAnnotation() {
      return DynamicObject.DynamicField.class;
   }

   private static long getObjectFieldOffset(Field field) {
      return UNSAFE.objectFieldOffset(field);
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

   static {
      try {
         SHAPE_OFFSET = getObjectFieldOffset(DynamicObject.class.getDeclaredField("shape"));
      } catch (Exception var1) {
         throw new IllegalStateException("Could not get 'shape' field offset", var1);
      }
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.FIELD)
   protected @interface DynamicField {
   }
}
