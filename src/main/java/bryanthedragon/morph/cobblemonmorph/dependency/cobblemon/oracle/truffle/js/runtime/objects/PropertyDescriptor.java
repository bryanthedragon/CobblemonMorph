
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.StringJoiner;

public final class PropertyDescriptor {
    private Object data;
    private int flags;
    private static final int ENUMERABLE = 1;
    private static final int WRITABLE = 2;
    private static final int CONFIGURABLE = 4;
    private static final int HAS_VALUE = 8;
    private static final int HAS_GET = 16;
    private static final int HAS_SET = 32;
    private static final int HAS_ENUMERABLE = 64;
    private static final int HAS_WRITABLE = 128;
    private static final int HAS_CONFIGURABLE = 256;
    public static final PropertyDescriptor undefinedDataDesc = PropertyDescriptor.createDataDefault(Undefined.instance);
    public static final PropertyDescriptor undefinedDataDescNotConfigurable = PropertyDescriptor.createData(Undefined.instance, true, true, false);

    private PropertyDescriptor() {
    }

    public static PropertyDescriptor createEmpty() {
        return new PropertyDescriptor();
    }

    public static PropertyDescriptor createData(Object value2, boolean isEnumerable, boolean isWritable, boolean isConfigurable) {
        PropertyDescriptor desc = new PropertyDescriptor();
        desc.setValue(value2);
        desc.setEnumerable(isEnumerable);
        desc.setWritable(isWritable);
        desc.setConfigurable(isConfigurable);
        return desc;
    }

    public static PropertyDescriptor createData(Object value2, int attributes) {
        return PropertyDescriptor.createData(value2, JSAttributes.isEnumerable(attributes), JSAttributes.isWritable(attributes), JSAttributes.isConfigurable(attributes));
    }

    public static PropertyDescriptor createData(Object value2) {
        PropertyDescriptor desc = new PropertyDescriptor();
        desc.setValue(value2);
        return desc;
    }

    public static PropertyDescriptor createDataDefault(Object value2) {
        PropertyDescriptor desc = new PropertyDescriptor();
        desc.setValue(value2);
        desc.flags = 463;
        return desc;
    }

    public static PropertyDescriptor createAccessor(Object getter, Object setter) {
        PropertyDescriptor desc = new PropertyDescriptor();
        if (setter != null) {
            desc.setSet(setter);
        }
        if (getter != null) {
            desc.setGet(getter);
        }
        return desc;
    }

    public static PropertyDescriptor createAccessor(Object getter, Object setter, int attributes) {
        return PropertyDescriptor.createAccessor(getter, setter, JSAttributes.isEnumerable(attributes), JSAttributes.isConfigurable(attributes));
    }

    public static PropertyDescriptor createAccessor(Object getter, Object setter, boolean isEnumerable, boolean isConfigurable) {
        PropertyDescriptor desc = PropertyDescriptor.createAccessor(getter, setter);
        desc.setEnumerable(isEnumerable);
        desc.setConfigurable(isConfigurable);
        return desc;
    }

    public Object getValue() {
        if (this.data instanceof Accessor) {
            return null;
        }
        return this.data;
    }

    public void setValue(Object value2) {
        assert (!this.isAccessorDescriptor()) : this;
        assert (value2 != null);
        this.data = value2;
        this.flags |= 8;
    }

    public Object getGet() {
        if (!(this.data instanceof Accessor)) {
            return null;
        }
        return ((Accessor)this.data).getGetter();
    }

    public void setGet(Object get) {
        this.data = this.data instanceof Accessor ? new Accessor(get, ((Accessor)this.data).getSetter()) : new Accessor(get, null);
        this.flags |= 0x10;
    }

    public Object getSet() {
        if (!(this.data instanceof Accessor)) {
            return null;
        }
        return ((Accessor)this.data).getSetter();
    }

    public void setSet(Object set2) {
        this.data = this.data instanceof Accessor ? new Accessor(((Accessor)this.data).getGetter(), set2) : new Accessor(null, set2);
        this.flags |= 0x20;
    }

    public void setAccessor(Accessor accessor) {
        assert (!this.isDataDescriptor()) : this;
        this.data = accessor;
        this.flags |= 0x30;
    }

    public boolean getEnumerable() {
        return (this.flags & 1) != 0;
    }

    public boolean getIfHasEnumerable(boolean defaultValue) {
        if (this.hasEnumerable()) {
            return this.getEnumerable();
        }
        return defaultValue;
    }

    public void setEnumerable(boolean enumerable) {
        this.flags = enumerable ? (this.flags |= 1) : (this.flags &= 0xFFFFFFFE);
        this.flags |= 0x40;
    }

    public boolean getWritable() {
        return (this.flags & 2) != 0;
    }

    public boolean getIfHasWritable(boolean defaultValue) {
        if (this.hasWritable()) {
            return this.getWritable();
        }
        return defaultValue;
    }

    public void setWritable(boolean writable) {
        this.flags = writable ? (this.flags |= 2) : (this.flags &= 0xFFFFFFFD);
        this.flags |= 0x80;
    }

    public boolean getConfigurable() {
        return (this.flags & 4) != 0;
    }

    public boolean getIfHasConfigurable(boolean defaultValue) {
        if (this.hasConfigurable()) {
            return this.getConfigurable();
        }
        return defaultValue;
    }

    public void setConfigurable(boolean configurable) {
        this.flags = configurable ? (this.flags |= 4) : (this.flags &= 0xFFFFFFFB);
        this.flags |= 0x100;
    }

    public boolean hasSet() {
        return (this.flags & 0x20) != 0;
    }

    public boolean hasGet() {
        return (this.flags & 0x10) != 0;
    }

    public boolean hasValue() {
        return (this.flags & 8) != 0;
    }

    public boolean hasEnumerable() {
        return (this.flags & 0x40) != 0;
    }

    public boolean hasWritable() {
        return (this.flags & 0x80) != 0;
    }

    public boolean hasConfigurable() {
        return (this.flags & 0x100) != 0;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "PropertyDescriptor[", "]");
        String kvsep = ": ";
        if (this.hasEnumerable()) {
            joiner.add(JSAttributes.ENUMERABLE + kvsep + this.getEnumerable());
        }
        if (this.hasConfigurable()) {
            joiner.add(JSAttributes.CONFIGURABLE + kvsep + this.getConfigurable());
        }
        if (this.hasWritable()) {
            joiner.add(JSAttributes.WRITABLE + kvsep + this.getWritable());
        }
        if (this.hasValue()) {
            assert (!(this.data instanceof Accessor));
            joiner.add(JSAttributes.VALUE + kvsep + this.data);
        }
        if (this.hasGet()) {
            assert (this.data instanceof Accessor);
            joiner.add(JSAttributes.GET + kvsep + ((Accessor)this.data).getGetter());
        }
        if (this.hasSet()) {
            assert (this.data instanceof Accessor);
            joiner.add(JSAttributes.SET + kvsep + ((Accessor)this.data).getSetter());
        }
        return joiner.toString();
    }

    public boolean isAccessorDescriptor() {
        return this.hasGet() || this.hasSet();
    }

    public boolean isDataDescriptor() {
        return this.hasValue() || this.hasWritable();
    }

    public boolean isGenericDescriptor() {
        return !this.isAccessorDescriptor() && !this.isDataDescriptor();
    }

    public boolean hasNoFields() {
        return !this.hasValue() && !this.hasGet() && !this.hasSet() && !this.hasConfigurable() && !this.hasEnumerable() && !this.hasWritable();
    }

    public boolean isFullyPopulatedPropertyDescriptor() {
        return this.hasConfigurable() && this.hasEnumerable() && (this.hasValue() && this.hasWritable() || this.hasGet() && this.hasSet());
    }

    public int getFlags() {
        return JSAttributes.fromConfigurableEnumerableWritable(this.getIfHasConfigurable(false), this.getIfHasEnumerable(false), this.getIfHasWritable(false));
    }
}

