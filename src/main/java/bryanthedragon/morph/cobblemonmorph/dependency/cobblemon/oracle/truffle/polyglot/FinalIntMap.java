package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;

final class FinalIntMap {
   @CompilerDirectives.CompilationFinal
   FinalIntMap.Entry first;

   @ExplodeLoop
   int get(Object key) {
      for (FinalIntMap.Entry current = this.first; current != null; current = current.next) {
         if (current.key == key) {
            return current.value;
         }
      }

      return -1;
   }

   void put(Object key, int value) {
      CompilerAsserts.neverPartOfCompilation();

      assert this.get(key) == -1 : "replace not supported by this map implementation";

      assert value >= 0 : "only positive integers supported";

      FinalIntMap.Entry prev = null;

      FinalIntMap.Entry current;
      for (current = this.first; current != null; current = current.next) {
         prev = current;
      }

      FinalIntMap.Entry entry = new FinalIntMap.Entry(key, value);
      if (prev == null) {
         assert current == this.first;

         this.first = entry;
      } else {
         prev.next = entry;
      }
   }

   static final class Entry {
      final Object key;
      final int value;
      @CompilerDirectives.CompilationFinal
      FinalIntMap.Entry next;

      Entry(Object key, int value) {
         this.key = key;
         this.value = value;
      }
   }
}
