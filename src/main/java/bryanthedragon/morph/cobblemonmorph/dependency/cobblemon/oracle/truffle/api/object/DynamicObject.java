
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.object.Shape;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import sun.misc.Unsafe;

public abstract class DynamicObject
implements TruffleObject {
    private Shape shape;
    @DynamicField
    private Object[] extRef;
    @DynamicField
    private int[] extVal;
    private static final Unsafe UNSAFE = DynamicObject.getUnsafe();
    private static final long SHAPE_OFFSET;

    protected DynamicObject(Shape shape) {
        DynamicObject.verifyShape(shape, this.getClass());
        this.shape = shape;
    }

    private static void verifyShape(Shape shape, Class<? extends DynamicObject> subclass) {
        Class<? extends DynamicObject> shapeType = shape.getLayoutClass();
        if (!(shapeType == subclass || shapeType.isAssignableFrom(subclass) && DynamicObject.class.isAssignableFrom(shapeType))) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw DynamicObject.illegalShapeType(shapeType, subclass);
        }
        if (shape.hasInstanceProperties()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw DynamicObject.illegalShapeProperties();
        }
    }

    @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
    private static IllegalArgumentException illegalShapeType(Class<? extends DynamicObject> shapeClass, Class<? extends DynamicObject> thisClass) {
        throw new IllegalArgumentException(String.format("Incompatible shape: layout class (%s) not assignable from this class (%s)", shapeClass.getName(), thisClass.getName()));
    }

    @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException=false)
    private static IllegalArgumentException illegalShapeProperties() {
        throw new IllegalArgumentException("Shape must not have instance properties");
    }

    public final Shape getShape() {
        return DynamicObject.getShapeHelper(this.shape);
    }

    private static Shape getShapeHelper(Shape shape) {
        return shape;
    }

    final void setShape(Shape shape) {
        assert (this.assertSetShape(shape));
        this.setShapeHelper(shape, SHAPE_OFFSET);
    }

    private boolean assertSetShape(Shape s) {
        Class<? extends DynamicObject> layoutType = s.getLayoutClass();
        assert (layoutType.isInstance(this)) : DynamicObject.illegalShapeType(layoutType, this.getClass());
        return true;
    }

    private void setShapeHelper(Shape shape, long shapeOffset) {
        this.shape = shape;
    }

    protected Object clone() throws CloneNotSupportedException {
        throw DynamicObject.cloneNotSupported();
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
        return DynamicField.class;
    }

    private static long getObjectFieldOffset(Field field) {
        return UNSAFE.objectFieldOffset(field);
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

    static {
        try {
            SHAPE_OFFSET = DynamicObject.getObjectFieldOffset(DynamicObject.class.getDeclaredField("shape"));
        }
        catch (Exception e) {
            throw new IllegalStateException("Could not get 'shape' field offset", e);
        }
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    protected static @interface DynamicField {
    }
}

