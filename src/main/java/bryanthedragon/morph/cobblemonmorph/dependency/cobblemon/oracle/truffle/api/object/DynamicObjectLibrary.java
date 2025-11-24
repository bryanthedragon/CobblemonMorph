
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.library.GenerateLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;

@GenerateLibrary(defaultExportLookupEnabled=true, dynamicDispatchEnabled=false, pushEncapsulatingNode=false)
public abstract class DynamicObjectLibrary
extends Library {
    private static final LibraryFactory<DynamicObjectLibrary> FACTORY = LibraryFactory.resolve(DynamicObjectLibrary.class);
    private static final DynamicObjectLibrary UNCACHED = FACTORY.getUncached();

    protected DynamicObjectLibrary() {
    }

    public static LibraryFactory<DynamicObjectLibrary> getFactory() {
        return FACTORY;
    }

    public static DynamicObjectLibrary getUncached() {
        return UNCACHED;
    }

    public abstract Shape getShape(DynamicObject var1);

    public abstract Object getOrDefault(DynamicObject var1, Object var2, Object var3);

    public int getIntOrDefault(DynamicObject object, Object key, Object defaultValue) throws UnexpectedResultException {
        Object value2 = this.getOrDefault(object, key, defaultValue);
        if (value2 instanceof Integer) {
            return (Integer)value2;
        }
        throw new UnexpectedResultException(value2);
    }

    public double getDoubleOrDefault(DynamicObject object, Object key, Object defaultValue) throws UnexpectedResultException {
        Object value2 = this.getOrDefault(object, key, defaultValue);
        if (value2 instanceof Double) {
            return (Double)value2;
        }
        throw new UnexpectedResultException(value2);
    }

    public long getLongOrDefault(DynamicObject object, Object key, Object defaultValue) throws UnexpectedResultException {
        Object value2 = this.getOrDefault(object, key, defaultValue);
        if (value2 instanceof Long) {
            return (Long)value2;
        }
        throw new UnexpectedResultException(value2);
    }

    public abstract void put(DynamicObject var1, Object var2, Object var3);

    public void putInt(DynamicObject object, Object key, int value2) {
        this.put(object, key, value2);
    }

    public void putDouble(DynamicObject object, Object key, double value2) {
        this.put(object, key, value2);
    }

    public void putLong(DynamicObject object, Object key, long value2) {
        this.put(object, key, value2);
    }

    public abstract boolean putIfPresent(DynamicObject var1, Object var2, Object var3);

    public abstract void putWithFlags(DynamicObject var1, Object var2, Object var3, int var4);

    public abstract void putConstant(DynamicObject var1, Object var2, Object var3, int var4);

    public abstract boolean removeKey(DynamicObject var1, Object var2);

    public abstract boolean setDynamicType(DynamicObject var1, Object var2);

    public abstract Object getDynamicType(DynamicObject var1);

    public abstract boolean containsKey(DynamicObject var1, Object var2);

    public abstract int getShapeFlags(DynamicObject var1);

    public abstract boolean setShapeFlags(DynamicObject var1, int var2);

    public abstract Property getProperty(DynamicObject var1, Object var2);

    public final int getPropertyFlagsOrDefault(DynamicObject object, Object key, int defaultValue) {
        Property property = this.getProperty(object, key);
        return property != null ? property.getFlags() : defaultValue;
    }

    public abstract boolean setPropertyFlags(DynamicObject var1, Object var2, int var3);

    public abstract void markShared(DynamicObject var1);

    public abstract boolean isShared(DynamicObject var1);

    public abstract boolean updateShape(DynamicObject var1);

    public abstract boolean resetShape(DynamicObject var1, Shape var2);

    public abstract Object[] getKeyArray(DynamicObject var1);

    public abstract Property[] getPropertyArray(DynamicObject var1);
}

