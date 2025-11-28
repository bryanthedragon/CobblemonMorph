package com.cobblemon.mod.relocations.ibm.icu.impl;

public interface ICUCache<K, V> {
   int SOFT = 0;
   int WEAK = 1;
   Object NULL = new Object();

   void clear();

   void put(K var1, V var2);

   V get(Object var1);
}
