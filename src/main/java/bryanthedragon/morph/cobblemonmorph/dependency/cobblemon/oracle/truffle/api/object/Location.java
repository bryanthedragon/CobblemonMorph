
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.BooleanLocation;
import com.oracle.truffle.api.object.DoubleLocation;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.IntLocation;
import com.oracle.truffle.api.object.LongLocation;
import com.oracle.truffle.api.object.Shape;

public abstract class Location {
    protected Location() {
    }

    @Deprecated(since="22.2")
    protected static IncompatibleLocationException incompatibleLocation() throws IncompatibleLocationException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw IncompatibleLocationException.instance();
    }

    @Deprecated(since="22.2")
    protected static FinalLocationException finalLocation() throws FinalLocationException {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw FinalLocationException.instance();
    }

    @Deprecated(since="22.2")
    public final Object get(DynamicObject store, Shape shape) {
        return this.get(store, store.getShape() == shape);
    }

    @Deprecated(since="22.2")
    public Object get(DynamicObject store, boolean condition2) {
        return this.getInternal(store);
    }

    @Deprecated(since="22.2")
    public final Object get(DynamicObject store) {
        return this.get(store, false);
    }

    protected int getInt(DynamicObject store, boolean guard) throws UnexpectedResultException {
        throw CompilerDirectives.shouldNotReachHere();
    }

    protected long getLong(DynamicObject store, boolean guard) throws UnexpectedResultException {
        throw CompilerDirectives.shouldNotReachHere();
    }

    protected double getDouble(DynamicObject store, boolean guard) throws UnexpectedResultException {
        throw CompilerDirectives.shouldNotReachHere();
    }

    @Deprecated(since="22.2")
    public void set(DynamicObject store, Object value2, Shape shape) throws IncompatibleLocationException, FinalLocationException {
        this.setInternal(store, value2);
    }

    @Deprecated(since="22.2")
    public void set(DynamicObject store, Object value2, Shape oldShape, Shape newShape) throws IncompatibleLocationException {
        throw Location.incompatibleLocation();
    }

    @Deprecated(since="22.2")
    public final void set(DynamicObject store, Object value2) throws IncompatibleLocationException, FinalLocationException {
        this.set(store, value2, null);
    }

    @Deprecated(since="22.2")
    protected abstract Object getInternal(DynamicObject var1);

    @Deprecated(since="22.2")
    protected abstract void setInternal(DynamicObject var1, Object var2) throws IncompatibleLocationException;

    @Deprecated(since="22.2")
    public boolean canSet(DynamicObject store, Object value2) {
        return this.canStore(value2);
    }

    @Deprecated(since="22.2")
    public boolean canSet(Object value2) {
        return this.canStore(value2);
    }

    public boolean canStore(Object value2) {
        return true;
    }

    public boolean isFinal() {
        return false;
    }

    public boolean isConstant() {
        return false;
    }

    public abstract int hashCode();

    public abstract boolean equals(Object var1);

    @Deprecated(since="22.2")
    public boolean isDeclared() {
        return false;
    }

    public boolean isValue() {
        return false;
    }

    public boolean isAssumedFinal() {
        return false;
    }

    public Assumption getFinalAssumption() {
        return Assumption.NEVER_VALID;
    }

    @Deprecated(since="22.2")
    protected static boolean checkShape(DynamicObject store, Shape shape) {
        return store.getShape() == shape;
    }

    public boolean isPrimitive() {
        return this instanceof DoubleLocation || this instanceof IntLocation || this instanceof LongLocation || this instanceof BooleanLocation;
    }

    public Object getConstantValue() {
        if (this.isConstant()) {
            return this.get(null);
        }
        return null;
    }
}

