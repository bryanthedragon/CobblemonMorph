package com.cobblemon.mod.relocations.ibm.icu.util;

public interface Freezable<T> extends Cloneable {
   boolean isFrozen();

   T freeze();

   T cloneAsThawed();
}
