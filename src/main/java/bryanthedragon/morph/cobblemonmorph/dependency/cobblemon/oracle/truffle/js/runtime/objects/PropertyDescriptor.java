package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
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
   public static final PropertyDescriptor undefinedDataDesc = createDataDefault(Undefined.instance);
   public static final PropertyDescriptor undefinedDataDescNotConfigurable = createData(Undefined.instance, true, true, false);

   private PropertyDescriptor() {
   }

   public static PropertyDescriptor createEmpty() {
      return new PropertyDescriptor();
   }

   public static PropertyDescriptor createData(Object value, boolean isEnumerable, boolean isWritable, boolean isConfigurable) {
      PropertyDescriptor desc = new PropertyDescriptor();
      desc.setValue(value);
      desc.setEnumerable(isEnumerable);
      desc.setWritable(isWritable);
      desc.setConfigurable(isConfigurable);
      return desc;
   }

   public static PropertyDescriptor createData(Object value, int attributes) {
      return createData(value, JSAttributes.isEnumerable(attributes), JSAttributes.isWritable(attributes), JSAttributes.isConfigurable(attributes));
   }

   public static PropertyDescriptor createData(Object value) {
      PropertyDescriptor desc = new PropertyDescriptor();
      desc.setValue(value);
      return desc;
   }

   public static PropertyDescriptor createDataDefault(Object value) {
      PropertyDescriptor desc = new PropertyDescriptor();
      desc.setValue(value);
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
      return createAccessor(getter, setter, JSAttributes.isEnumerable(attributes), JSAttributes.isConfigurable(attributes));
   }

   public static PropertyDescriptor createAccessor(Object getter, Object setter, boolean isEnumerable, boolean isConfigurable) {
      PropertyDescriptor desc = createAccessor(getter, setter);
      desc.setEnumerable(isEnumerable);
      desc.setConfigurable(isConfigurable);
      return desc;
   }

   public Object getValue() {
      return this.data instanceof Accessor ? null : this.data;
   }

   public void setValue(Object value) {
      assert !this.isAccessorDescriptor() : this;

      assert value != null;

      this.data = value;
      this.flags |= 8;
   }

   public Object getGet() {
      return !(this.data instanceof Accessor) ? null : ((Accessor)this.data).getGetter();
   }

   public void setGet(Object get) {
      if (this.data instanceof Accessor) {
         this.data = new Accessor(get, ((Accessor)this.data).getSetter());
      } else {
         this.data = new Accessor(get, null);
      }

      this.flags |= 16;
   }

   public Object getSet() {
      return !(this.data instanceof Accessor) ? null : ((Accessor)this.data).getSetter();
   }

   public void setSet(Object set) {
      if (this.data instanceof Accessor) {
         this.data = new Accessor(((Accessor)this.data).getGetter(), set);
      } else {
         this.data = new Accessor(null, set);
      }

      this.flags |= 32;
   }

   public void setAccessor(Accessor accessor) {
      assert !this.isDataDescriptor() : this;

      this.data = accessor;
      this.flags |= 48;
   }

   public boolean getEnumerable() {
      return (this.flags & 1) != 0;
   }

   public boolean getIfHasEnumerable(boolean defaultValue) {
      return this.hasEnumerable() ? this.getEnumerable() : defaultValue;
   }

   public void setEnumerable(boolean enumerable) {
      if (enumerable) {
         this.flags |= 1;
      } else {
         this.flags &= -2;
      }

      this.flags |= 64;
   }

   public boolean getWritable() {
      return (this.flags & 2) != 0;
   }

   public boolean getIfHasWritable(boolean defaultValue) {
      return this.hasWritable() ? this.getWritable() : defaultValue;
   }

   public void setWritable(boolean writable) {
      if (writable) {
         this.flags |= 2;
      } else {
         this.flags &= -3;
      }

      this.flags |= 128;
   }

   public boolean getConfigurable() {
      return (this.flags & 4) != 0;
   }

   public boolean getIfHasConfigurable(boolean defaultValue) {
      return this.hasConfigurable() ? this.getConfigurable() : defaultValue;
   }

   public void setConfigurable(boolean configurable) {
      if (configurable) {
         this.flags |= 4;
      } else {
         this.flags &= -5;
      }

      this.flags |= 256;
   }

   public boolean hasSet() {
      return (this.flags & 32) != 0;
   }

   public boolean hasGet() {
      return (this.flags & 16) != 0;
   }

   public boolean hasValue() {
      return (this.flags & 8) != 0;
   }

   public boolean hasEnumerable() {
      return (this.flags & 64) != 0;
   }

   public boolean hasWritable() {
      return (this.flags & 128) != 0;
   }

   public boolean hasConfigurable() {
      return (this.flags & 256) != 0;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
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
         assert !(this.data instanceof Accessor);

         joiner.add(JSAttributes.VALUE + kvsep + this.data);
      }

      if (this.hasGet()) {
         assert this.data instanceof Accessor;

         joiner.add(JSAttributes.GET + kvsep + ((Accessor)this.data).getGetter());
      }

      if (this.hasSet()) {
         assert this.data instanceof Accessor;

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
