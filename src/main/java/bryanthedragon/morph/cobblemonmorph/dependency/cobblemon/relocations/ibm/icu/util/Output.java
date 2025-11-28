package com.cobblemon.mod.relocations.ibm.icu.util;

public class Output<T> {
   public T value;

   @Override
   public String toString() {
      return this.value == null ? "null" : this.value.toString();
   }

   public Output() {
   }

   public Output(T value) {
      this.value = value;
   }
}
