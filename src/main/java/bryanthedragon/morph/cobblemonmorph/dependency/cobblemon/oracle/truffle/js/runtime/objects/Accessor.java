package com.oracle.truffle.js.runtime.objects;

import java.util.Objects;

public final class Accessor {
   private final Object getter;
   private final Object setter;

   public Accessor(Object getter, Object setter) {
      this.getter = getter == null ? Undefined.instance : getter;
      this.setter = setter == null ? Undefined.instance : setter;
   }

   public Object getGetter() {
      return this.getter;
   }

   public Object getSetter() {
      return this.setter;
   }

   public boolean hasGetter() {
      return this.getter != Undefined.instance;
   }

   public boolean hasSetter() {
      return this.setter != Undefined.instance;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Accessor)) {
         return false;
      } else {
         Accessor other = (Accessor)obj;
         return Objects.equals(this.getter, other.getter) && Objects.equals(this.setter, other.setter);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getter, this.setter);
   }
}
