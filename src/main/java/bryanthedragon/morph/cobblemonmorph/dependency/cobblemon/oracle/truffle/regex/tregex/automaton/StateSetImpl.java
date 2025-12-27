package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.util.BitSets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.PrimitiveIterator.OfInt;

final class StateSetImpl<SI extends StateIndex<? super S>, S> implements StateSet<SI, S> {
   private static final int ELEMENT_SIZE = 32;
   private static final int SWITCH_TO_BACKING_SET_THRESHOLD = 2;
   private static final long ELEMENT_MASK = -1L;
   private final SI stateIndex;
   private long[] backingSet;
   private int size = 0;
   private long stateList = 0L;

   StateSetImpl(SI stateIndex) {
      this.stateIndex = stateIndex;
   }

   StateSetImpl(StateSetImpl<SI, S> copy) {
      this.stateIndex = copy.stateIndex;
      this.size = copy.size;
      this.backingSet = copy.backingSet == null ? null : Arrays.copyOf(copy.backingSet, copy.backingSet.length);
      this.stateList = copy.stateList;
   }

   @Override
   public SI getStateIndex() {
      return this.stateIndex;
   }

   @Override
   public StateSet<SI, S> copy() {
      return new StateSetImpl<>(this);
   }

   @Override
   public boolean isEmpty() {
      return this.size() == 0;
   }

   @Override
   public int size() {
      return this.size;
   }

   private boolean useBackingSet() {
      return this.size > 2;
   }

   private static int stateListElement(long stateList, int i) {
      return stateListElement(stateList >>> 32 * i);
   }

   private static int stateListElement(long stateList) {
      return (int)(stateList & -1L);
   }

   @Override
   public boolean contains(Object o) {
      return this.contains(this.stateIndex.getId((S)o));
   }

   @Override
   public boolean add(S e) {
      return this.add(this.stateIndex.getId(e));
   }

   @Override
   public boolean remove(Object o) {
      return this.remove(this.stateIndex.getId((S)o));
   }

   private boolean contains(int id) {
      if (this.useBackingSet()) {
         return BitSets.get(this.backingSet, id);
      } else {
         long sl = this.stateList;

         for (int i = 0; i < this.size(); i++) {
            if (stateListElement(sl) == id) {
               return true;
            }

            sl >>>= 32;
         }

         return false;
      }
   }

   @Override
   public boolean containsAll(Collection<?> c) {
      if (c instanceof StateSetImpl) {
         StateSetImpl<?, ?> o = (StateSetImpl<?, ?>)c;
         if (this.useBackingSet() && o.useBackingSet()) {
            return BitSets.contains(this.backingSet, o.backingSet);
         }
      }

      for (Object o : c) {
         if (!this.contains(o)) {
            return false;
         }
      }

      return true;
   }

   private boolean add(int id) {
      if (this.useBackingSet()) {
         if (BitSets.add(this.backingSet, id)) {
            this.size++;
            return true;
         } else {
            return false;
         }
      } else {
         long sl = this.stateList;

         int i;
         for (i = 0; i < this.size(); i++) {
            if (stateListElement(sl) == id) {
               return false;
            }

            if (stateListElement(sl) > id) {
               break;
            }

            sl >>>= 32;
         }

         if (this.size() == 2) {
            if (this.backingSet == null) {
               this.backingSet = BitSets.createBitSetArray(this.stateIndex.getNumberOfStates());
            }

            for (int j = 0; j < this.size(); j++) {
               BitSets.set(this.backingSet, stateListElement(this.stateList));
               this.stateList >>>= 32;
            }
         }

         this.size++;
         if (this.useBackingSet()) {
            BitSets.set(this.backingSet, id);
         } else {
            this.stateList = (sl << 32 | id) << i * 32 | this.stateList & ~(-1L << i * 32);
         }

         return true;
      }
   }

   @Override
   public boolean addAll(Collection<? extends S> c) {
      if (c instanceof StateSetImpl) {
         StateSetImpl<?, ?> o = (StateSetImpl<?, ?>)c;
         if (this.useBackingSet() && o.useBackingSet()) {
            int oldSize = this.size;
            this.size = BitSets.addAll(this.backingSet, o.backingSet);
            return this.size != oldSize;
         }
      }

      boolean ret = false;

      for (S s : c) {
         ret |= this.add(s);
      }

      return ret;
   }

   private boolean remove(int id) {
      if (this.useBackingSet()) {
         if (!BitSets.remove(this.backingSet, id)) {
            return false;
         } else {
            this.size--;
            if (this.size == 2) {
               assert this.stateList == 0L;

               int shift = 0;

               for (OfInt it = BitSets.iterator(this.backingSet); it.hasNext(); shift += 32) {
                  this.stateList = this.stateList | (long)it.nextInt() << shift;
               }

               BitSets.clear(this.backingSet);
            }

            return true;
         }
      } else {
         long sl = this.stateList;

         for (int i = 0; i < this.size(); i++) {
            if (stateListElement(sl) == id) {
               this.removeStateListElement(i);
               this.size--;
               return true;
            }

            sl >>>= 32;
         }

         return false;
      }
   }

   private void removeStateListElement(int i) {
      long maskClrLo = -1L << i * 32;
      long maskClrHi = ~maskClrLo;
      this.stateList = this.stateList >>> 32 & maskClrLo | this.stateList & maskClrHi;
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      if (c instanceof StateSetImpl) {
         StateSetImpl<?, ?> o = (StateSetImpl<?, ?>)c;
         if (this.useBackingSet() && o.useBackingSet()) {
            int oldSize = this.size;
            this.size = BitSets.removeAll(this.backingSet, o.backingSet);
            return this.size != oldSize;
         }
      }

      boolean ret = false;

      for (Object s : c) {
         ret |= this.remove(s);
      }

      return ret;
   }

   @Override
   public void clear() {
      this.stateList = 0L;
      if (this.useBackingSet()) {
         BitSets.clear(this.backingSet);
      }

      this.size = 0;
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      if (c instanceof StateSetImpl) {
         StateSetImpl<?, ?> o = (StateSetImpl<?, ?>)c;
         if (this.useBackingSet() && o.useBackingSet()) {
            int oldSize = this.size;
            this.size = BitSets.retainAll(this.backingSet, o.backingSet);
            return this.size != oldSize;
         }
      }

      boolean ret = false;

      for (S s : this) {
         if (!c.contains(s)) {
            ret |= this.remove(s);
         }
      }

      return ret;
   }

   @Override
   public boolean isDisjoint(StateSet<SI, ? extends S> other) {
      StateSetImpl<SI, S> o = (StateSetImpl<SI, S>)other;
      if (o.useBackingSet()) {
         if (this.useBackingSet()) {
            return BitSets.isDisjoint(this.backingSet, o.backingSet);
         } else {
            long sl = this.stateList;

            for (int i = 0; i < this.size(); i++) {
               if (o.contains(stateListElement(sl))) {
                  return false;
               }

               sl >>>= 32;
            }

            return true;
         }
      } else {
         long sl = o.stateList;

         for (int i = 0; i < o.size(); i++) {
            if (this.contains(stateListElement(sl))) {
               return false;
            }

            sl >>>= 32;
         }

         return true;
      }
   }

   private boolean stateListSorted() {
      int last = -1;

      for (int i = 0; i < this.size(); i++) {
         if (stateListElement(this.stateList, i) <= last) {
            return false;
         }

         last = stateListElement(this.stateList, i);
      }

      for (int i = this.size(); i < 2; i++) {
         if (stateListElement(this.stateList, i) != 0) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int hashCode() {
      if (this.useBackingSet()) {
         return BitSets.hashCode(this.backingSet);
      } else {
         assert this.stateListSorted();

         return Long.hashCode(this.stateList);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof StateSetImpl) {
         StateSetImpl<SI, S> o = (StateSetImpl<SI, S>)obj;
         if (this.size() != o.size()) {
            return false;
         } else {
            assert this.useBackingSet() == o.useBackingSet();

            if (this.useBackingSet()) {
               return BitSets.equals(this.backingSet, o.backingSet);
            } else {
               assert this.stateListSorted();

               assert o.stateListSorted();

               return this.stateList == o.stateList;
            }
         }
      } else if (!(obj instanceof Set)) {
         return false;
      } else {
         Set<S> o = (Set<S>)obj;
         return this.size() == o.size() && this.containsAll(o);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.defaultToString();
   }

   private OfInt intIterator() {
      if (this.useBackingSet()) {
         return BitSets.iterator(this.backingSet);
      } else {
         assert this.stateListSorted();

         return new StateSetImpl.StateListIterator();
      }
   }

   @Override
   public Iterator<S> iterator() {
      return new StateSetImpl.StateSetIterator(this.intIterator());
   }

   private final class StateListIterator implements OfInt {
      private int i;

      @Override
      public int nextInt() {
         return StateSetImpl.stateListElement(StateSetImpl.this.stateList, this.i++);
      }

      @Override
      public boolean hasNext() {
         return this.i < StateSetImpl.this.size;
      }

      @Override
      public void remove() {
         StateSetImpl.this.removeStateListElement(--this.i);
      }
   }

   private final class StateSetIterator implements Iterator<S> {
      private final OfInt intIterator;

      private StateSetIterator(OfInt intIterator) {
         this.intIterator = intIterator;
      }

      @Override
      public boolean hasNext() {
         return this.intIterator.hasNext();
      }

      @Override
      public S next() {
         return (S)StateSetImpl.this.getStateIndex().getState(this.intIterator.nextInt());
      }

      @Override
      public void remove() {
         this.intIterator.remove();
         StateSetImpl.this.size--;
      }
   }
}
