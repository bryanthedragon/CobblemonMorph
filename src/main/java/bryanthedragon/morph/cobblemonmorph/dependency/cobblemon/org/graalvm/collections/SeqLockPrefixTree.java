package org.graalvm.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class SeqLockPrefixTree {
   private static final int INITIAL_LINEAR_NODE_SIZE = 3;
   private static final int INITIAL_HASH_NODE_SIZE = 16;
   private static final int MAX_LINEAR_NODE_SIZE = 6;
   private static final long EMPTY_KEY = 0L;
   private static final double HASH_NODE_LOAD_FACTOR = 0.5;
   private final SeqLockPrefixTree.Node root = new SeqLockPrefixTree.Node();

   public SeqLockPrefixTree.Node root() {
      return this.root;
   }

   public static final class Node extends AtomicLong {
      private static final long serialVersionUID = -1L;
      private volatile long seqlock = 0L;
      private volatile long[] keys = null;
      private volatile SeqLockPrefixTree.Node[] children = null;
      private volatile int arity = 0;

      private Node() {
      }

      public long value() {
         return this.get();
      }

      public long incValue() {
         return this.incrementAndGet();
      }

      public void setValue(long value) {
         this.set(value);
      }

      public SeqLockPrefixTree.Node at(long key) {
         if (key == 0L) {
            throw new IllegalArgumentException("Key in the prefix tree cannot be 0.");
         } else {
            SeqLockPrefixTree.Node child = this.findChildLockFree(key);
            return child != null ? child : this.tryAddChild(key);
         }
      }

      public long seqlockValue() {
         return this.seqlock;
      }

      private SeqLockPrefixTree.Node findChildLockFree(long key) {
         long seqlockStart = this.seqlock;
         if ((seqlockStart & 1L) == 1L) {
            return null;
         } else {
            long[] keysSnapshot = this.keys;
            SeqLockPrefixTree.Node[] childrenSnapshot = this.children;
            SeqLockPrefixTree.Node child = findChild(keysSnapshot, childrenSnapshot, key);
            long seqlockEnd = this.seqlock;
            return seqlockStart != seqlockEnd ? null : child;
         }
      }

      private static SeqLockPrefixTree.Node findChild(long[] keysSnapshot, SeqLockPrefixTree.Node[] childrenSnapshot, long key) {
         if (keysSnapshot != null && childrenSnapshot != null) {
            if (keysSnapshot.length != childrenSnapshot.length) {
               return null;
            } else {
               if (keysSnapshot.length <= 6) {
                  for (int i = 0; i < keysSnapshot.length; i++) {
                     long curkey = keysSnapshot[i];
                     if (curkey == key) {
                        return childrenSnapshot[i];
                     }

                     if (curkey == 0L) {
                        break;
                     }
                  }
               } else {
                  int index = hash(key) % keysSnapshot.length;

                  while (true) {
                     long curkeyx = keysSnapshot[index];
                     if (curkeyx == key) {
                        return childrenSnapshot[index];
                     }

                     if (curkeyx == 0L) {
                        break;
                     }

                     index = (index + 1) % keysSnapshot.length;
                  }
               }

               return null;
            }
         } else {
            return null;
         }
      }

      @SuppressFBWarnings(value = "VO_VOLATILE_INCREMENT", justification = "in synchronized method")
      private synchronized SeqLockPrefixTree.Node tryAddChild(long key) {
         if (this.keys != null) {
            SeqLockPrefixTree.Node child = findChild(this.keys, this.children, key);
            if (child != null) {
               return child;
            }
         }

         this.seqlock++;

         SeqLockPrefixTree.Node var4;
         try {
            if (this.keys == null) {
               this.keys = new long[3];
               this.children = new SeqLockPrefixTree.Node[3];
            }

            SeqLockPrefixTree.Node child = new SeqLockPrefixTree.Node();
            if (this.keys.length <= 6) {
               this.addChildToLinearNode(key, child);
            } else {
               this.addChildToHashNode(key, child);
            }

            var4 = child;
         } finally {
            this.seqlock++;
         }

         return var4;
      }

      @SuppressFBWarnings(value = "VO_VOLATILE_INCREMENT", justification = "called from synchronized tryAddChild")
      private void addChildToLinearNode(long key, SeqLockPrefixTree.Node child) {
         if (this.arity == this.keys.length) {
            if (this.arity == 6) {
               this.convertToHashNode();
               this.addChildToHashNode(key, child);
               return;
            }

            long[] nkeys = new long[2 * this.keys.length];
            SeqLockPrefixTree.Node[] nchildren = new SeqLockPrefixTree.Node[2 * this.children.length];

            for (int i = 0; i < this.keys.length; i++) {
               nkeys[i] = this.keys[i];
               nchildren[i] = this.children[i];
            }

            this.keys = nkeys;
            this.children = nchildren;
         }

         this.keys[this.arity] = key;
         this.children[this.arity] = child;
         this.arity++;
      }

      private void convertToHashNode() {
         long[] oldKeys = this.keys;
         SeqLockPrefixTree.Node[] oldChildren = this.children;
         int oldArity = this.arity;
         this.keys = new long[16];
         this.children = new SeqLockPrefixTree.Node[16];
         this.arity = 0;

         for (int i = 0; i < oldArity; i++) {
            this.addChildToHashNode(oldKeys[i], oldChildren[i]);
         }
      }

      private void addChildToHashNode(long key, SeqLockPrefixTree.Node child) {
         if (this.mustGrowHash()) {
            this.growHash();
         }

         this.addChildToNonFullHashNode(key, child);
      }

      @SuppressFBWarnings(value = "VO_VOLATILE_INCREMENT", justification = "called indirectly from synchronized tryAddChild")
      private void addChildToNonFullHashNode(long key, SeqLockPrefixTree.Node child) {
         int index = hash(key) % this.keys.length;

         while (this.keys[index] != 0L) {
            index = (index + 1) % this.keys.length;
         }

         this.keys[index] = key;
         this.children[index] = child;
         this.arity++;
      }

      private boolean mustGrowHash() {
         return (double)this.arity / this.keys.length > 0.5;
      }

      private void growHash() {
         long[] oldKeys = this.keys;
         SeqLockPrefixTree.Node[] oldChildren = this.children;
         this.keys = new long[2 * oldKeys.length];
         this.children = new SeqLockPrefixTree.Node[2 * oldChildren.length];
         this.arity = 0;

         for (int i = 0; i < oldKeys.length; i++) {
            long key = oldKeys[i];
            if (key != 0L) {
               SeqLockPrefixTree.Node child = oldChildren[i];
               this.addChildToNonFullHashNode(key, child);
            }
         }
      }

      private static int hash(long key) {
         long v = key * -7046033566014671411L;
         v = Long.reverseBytes(v);
         v *= -7046033566014671411L;
         return 2147483647 & (int)(v ^ v >> 32);
      }

      private synchronized <R> R bottomUp(SeqLockPrefixTree.Visitor<R> visitor) {
         List<R> results = new ArrayList<>();
         SeqLockPrefixTree.Node[] childrenSnapshot = this.children;

         for (int i = 0; i < childrenSnapshot.length; i++) {
            if (childrenSnapshot[i] != null) {
               results.add(childrenSnapshot[i].bottomUp(visitor));
            }
         }

         return visitor.visit(this, results);
      }

      public synchronized <C> void topDown(C currentContext, BiFunction<C, Long, C> createContext, BiConsumer<C, Long> consumeValue) {
         SeqLockPrefixTree.Node[] childrenSnapshot = this.children;
         long[] keysSnapshot = this.keys;
         consumeValue.accept(currentContext, this.get());
         if (childrenSnapshot != null) {
            for (int i = 0; i < childrenSnapshot.length; i++) {
               SeqLockPrefixTree.Node child = childrenSnapshot[i];
               if (child != null) {
                  long key = keysSnapshot[i];
                  C extendedContext = createContext.apply(currentContext, key);
                  child.topDown(extendedContext, createContext, consumeValue);
               }
            }
         }
      }

      @Override
      public String toString() {
         return "Node<" + this.value() + ">";
      }
   }

   interface Visitor<R> {
      R visit(SeqLockPrefixTree.Node n, List<R> childResults);
   }
}
