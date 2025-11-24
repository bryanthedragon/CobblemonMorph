
package com.oracle.truffle.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LongLocation;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.LayoutImpl;
import java.util.Objects;

public abstract class LocationImpl
extends Location {
    protected LocationImpl() {
    }

    @Override
    public void set(DynamicObject store, Object value2, Shape shape) throws IncompatibleLocationException {
        this.set(store, value2, LocationImpl.checkShape(store, shape), false);
    }

    @Override
    public void set(DynamicObject store, Object value2, Shape oldShape, Shape newShape) throws IncompatibleLocationException {
        if (this.canStore(value2)) {
            LayoutImpl.ACCESS.grow(store, oldShape, newShape);
            try {
                this.setInternal(store, value2);
            }
            catch (IncompatibleLocationException ex) {
                throw LocationImpl.shouldNotHappen(ex);
            }
        } else {
            throw LocationImpl.incompatibleLocation();
        }
        LayoutImpl.ACCESS.setShapeWithStoreFence(store, newShape);
    }

    @Override
    protected final Object getInternal(DynamicObject store) {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract Object get(DynamicObject var1, boolean var2);

    @Override
    protected long getLong(DynamicObject store, boolean guard) throws UnexpectedResultException {
        return LocationImpl.expectLong(this.get(store, guard));
    }

    @Override
    protected int getInt(DynamicObject store, boolean guard) throws UnexpectedResultException {
        return LocationImpl.expectInteger(this.get(store, guard));
    }

    @Override
    protected double getDouble(DynamicObject store, boolean guard) throws UnexpectedResultException {
        return LocationImpl.expectDouble(this.get(store, guard));
    }

    protected boolean getBoolean(DynamicObject store, boolean guard) throws UnexpectedResultException {
        return LocationImpl.expectBoolean(this.get(store, guard));
    }

    protected abstract void set(DynamicObject var1, Object var2, boolean var3, boolean var4) throws IncompatibleLocationException;

    protected void setInt(DynamicObject store, int value2, boolean guard, boolean init2) throws IncompatibleLocationException {
        this.set(store, (Object)value2, guard, init2);
    }

    protected void setLong(DynamicObject store, long value2, boolean guard, boolean init2) throws IncompatibleLocationException {
        this.set(store, (Object)value2, guard, init2);
    }

    protected void setDouble(DynamicObject store, double value2, boolean guard, boolean init2) throws IncompatibleLocationException {
        this.set(store, (Object)value2, guard, init2);
    }

    protected static final boolean checkShape(DynamicObject store, Shape shape) {
        return store.getShape() == shape;
    }

    @Override
    protected final void setInternal(DynamicObject store, Object value2) throws IncompatibleLocationException {
        this.set(store, value2, false, true);
    }

    @Override
    public boolean canSet(DynamicObject store, Object value2) {
        return this.canStore(value2) && this.canStoreFinal(store, value2);
    }

    @Override
    public boolean canSet(Object value2) {
        return this.canSet(null, value2);
    }

    @Override
    public boolean canStore(Object value2) {
        return true;
    }

    protected boolean canStoreFinal(DynamicObject store, Object value2) {
        return true;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean isConstant() {
        return false;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.isFinal() ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    public String toString() {
        String finalString = this.isFinal() ? "f" : "";
        String typeString = ((Class)Objects.requireNonNullElse(this.getType(), Object.class)).getSimpleName();
        return finalString + typeString + this.getWhereString();
    }

    protected String getWhereString() {
        return "";
    }

    public int objectArrayCount() {
        return 0;
    }

    public int objectFieldCount() {
        return 0;
    }

    public int primitiveFieldCount() {
        return 0;
    }

    public int primitiveArrayCount() {
        return 0;
    }

    public abstract void accept(LocationVisitor var1);

    protected LocationImpl getInternalLocation() {
        return this;
    }

    static boolean isSameLocation(LocationImpl loc1, LocationImpl loc2) {
        return loc1 == loc2 || loc1.getInternalLocation().equals(loc2.getInternalLocation());
    }

    protected final void setSafe(DynamicObject store, Object value2, boolean guard, boolean init2) {
        try {
            this.set(store, value2, guard, init2);
        }
        catch (IncompatibleLocationException e) {
            throw LocationImpl.shouldNotHappen(e);
        }
    }

    protected final void setIntSafe(DynamicObject store, int value2, boolean guard, boolean init2) {
        try {
            this.setInt(store, value2, guard, init2);
        }
        catch (IncompatibleLocationException e) {
            throw LocationImpl.shouldNotHappen(e);
        }
    }

    protected final void setLongSafe(DynamicObject store, long value2, boolean guard, boolean init2) {
        try {
            this.setLong(store, value2, guard, init2);
        }
        catch (IncompatibleLocationException e) {
            throw LocationImpl.shouldNotHappen(e);
        }
    }

    protected final void setDoubleSafe(DynamicObject store, double value2, boolean guard, boolean init2) {
        try {
            this.setDouble(store, value2, guard, init2);
        }
        catch (IncompatibleLocationException e) {
            throw LocationImpl.shouldNotHappen(e);
        }
    }

    protected boolean isIntLocation() {
        return false;
    }

    protected boolean isLongLocation() {
        return false;
    }

    protected boolean isDoubleLocation() {
        return false;
    }

    protected boolean isImplicitCastIntToLong() {
        return false;
    }

    protected boolean isImplicitCastIntToDouble() {
        return false;
    }

    protected boolean isObjectLocation() {
        return false;
    }

    static boolean expectBoolean(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Boolean) {
            return (Boolean)value2;
        }
        throw new UnexpectedResultException(value2);
    }

    static int expectInteger(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Integer) {
            return (Integer)value2;
        }
        throw new UnexpectedResultException(value2);
    }

    static double expectDouble(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Double) {
            return (Double)value2;
        }
        throw new UnexpectedResultException(value2);
    }

    static long expectLong(Object value2) throws UnexpectedResultException {
        if (value2 instanceof Long) {
            return (Long)value2;
        }
        throw new UnexpectedResultException(value2);
    }

    public Class<?> getType() {
        return null;
    }

    protected void clear(DynamicObject store) {
    }

    @Override
    public Assumption getFinalAssumption() {
        return Assumption.NEVER_VALID;
    }

    protected static RuntimeException shouldNotHappen(Exception e) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new IllegalStateException(e);
    }

    public static interface LocationVisitor {
        public void visitObjectField(int var1, int var2);

        public void visitObjectArray(int var1, int var2);

        public void visitPrimitiveField(int var1, int var2);

        public void visitPrimitiveArray(int var1, int var2);
    }

    public static interface InternalLongLocation
    extends LongLocation {
        public void setLongInternal(DynamicObject var1, long var2);

        public String getWhereString();
    }
}

