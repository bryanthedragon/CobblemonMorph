package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;

public abstract class JSOrdinaryObject extends JSNonProxyObject implements JSCopyableObject {
   protected JSOrdinaryObject(Shape shape) {
      super(shape);
   }

   public static JSOrdinaryObject create(Shape shape) {
      Class<? extends DynamicObject> layout = shape.getLayoutClass();
      if (layout == JSOrdinaryObject.DefaultLayout.class) {
         return new JSOrdinaryObject.DefaultLayout(shape);
      } else {
         return (JSOrdinaryObject)(layout == JSOrdinaryObject.InternalFieldLayout.class
            ? new JSOrdinaryObject.InternalFieldLayout(shape)
            : new JSOrdinaryObject.BareLayout(shape));
      }
   }

   @Override
   public TruffleString getClassName() {
      return JSOrdinary.CLASS_NAME;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getValue(long index) {
      return this.getValue(Strings.fromLong(index));
   }

   @Override
   public final boolean hasOnlyShapeProperties() {
      return true;
   }

   public static final class BareLayout extends JSOrdinaryObject {
      protected BareLayout(Shape shape) {
         super(shape);
      }

      @Override
      protected JSObject copyWithoutProperties(Shape shape) {
         return new JSOrdinaryObject.BareLayout(shape);
      }
   }

   public static final class DefaultLayout extends JSOrdinaryObject {
      @DynamicObject.DynamicField
      Object o0;
      @DynamicObject.DynamicField
      Object o1;
      @DynamicObject.DynamicField
      Object o2;
      @DynamicObject.DynamicField
      Object o3;
      @DynamicObject.DynamicField
      long p0;
      @DynamicObject.DynamicField
      long p1;
      @DynamicObject.DynamicField
      long p2;

      protected DefaultLayout(Shape shape) {
         super(shape);
      }

      @Override
      protected JSObject copyWithoutProperties(Shape shape) {
         return new JSOrdinaryObject.DefaultLayout(shape);
      }
   }

   public static final class InternalFieldLayout extends JSOrdinaryObject {
      @DynamicObject.DynamicField
      Object o0;
      @DynamicObject.DynamicField
      Object o1;
      @DynamicObject.DynamicField
      Object o2;
      private static final long[] EMPTY_LONG_ARRAY = new long[0];
      private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
      private long[] internalPointerFields = EMPTY_LONG_ARRAY;
      private Object[] internalObjectFields = EMPTY_OBJECT_ARRAY;

      protected InternalFieldLayout(Shape shape) {
         super(shape);
      }

      @Override
      protected JSObject copyWithoutProperties(Shape shape) {
         return new JSOrdinaryObject.InternalFieldLayout(shape);
      }

      public long getInternalFieldPointer(int index) {
         return this.internalPointerFields[index];
      }

      public void setInternalFieldPointer(int index, long value) {
         this.internalPointerFields[index] = value;
      }

      public Object getInternalFieldObject(int index) {
         return index >= this.internalObjectFields.length ? null : this.internalObjectFields[index];
      }

      public void setInternalFieldObject(int index, Object value) {
         if (this.internalObjectFields.length == 0) {
            this.internalObjectFields = new Object[this.getInternalFieldCount()];
         }

         this.internalObjectFields[index] = value;
      }

      public int getInternalFieldCount() {
         return this.internalPointerFields.length;
      }

      public void setInternalFieldCount(int internalFieldCount) {
         assert this.getInternalFieldCount() == 0;

         this.internalPointerFields = new long[internalFieldCount];
      }
   }
}
