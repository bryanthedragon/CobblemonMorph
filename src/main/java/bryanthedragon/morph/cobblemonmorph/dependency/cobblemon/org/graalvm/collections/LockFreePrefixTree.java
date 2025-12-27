package org.graalvm.collections;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class LockFreePrefixTree {
   private LockFreePrefixTree.Node root = new LockFreePrefixTree.Node(0L);

   public LockFreePrefixTree.Node root() {
      return this.root;
   }

   public <C> void topDown(C initialContext, BiFunction<C, Long, C> createContext, BiConsumer<C, Long> consumeValue) {
      this.root.topDown(initialContext, createContext, consumeValue);
   }

   public static class Node extends AtomicLong {
      private static final long serialVersionUID = -1L;
      private static final LockFreePrefixTree.Node.FrozenNode FROZEN_NODE = new LockFreePrefixTree.Node.FrozenNode();
      private static final int INITIAL_LINEAR_NODE_SIZE = 2;
      private static final int INITIAL_HASH_NODE_SIZE = 16;
      private static final int MAX_LINEAR_NODE_SIZE = 8;
      private static final int MAX_HASH_SKIPS = 10;
      private static final AtomicReferenceFieldUpdater<LockFreePrefixTree.Node, AtomicReferenceArray> CHILDREN_UPDATER = AtomicReferenceFieldUpdater.newUpdater(
         LockFreePrefixTree.Node.class, AtomicReferenceArray.class, "children"
      );
      private final long key;
      private volatile AtomicReferenceArray<LockFreePrefixTree.Node> children;

      private Node(long key) {
         this.key = key;
      }

      public long value() {
         return this.get();
      }

      private long getKey() {
         return this.key;
      }

      public void setValue(long value) {
         this.set(value);
      }

      public long incValue() {
         return this.incrementAndGet();
      }

      public LockFreePrefixTree.Node at(long childKey) {
         this.ensureChildren();

         while (true) {
            AtomicReferenceArray<LockFreePrefixTree.Node> children0 = this.readChildren();
            if (children0 instanceof LockFreePrefixTree.Node.LinearChildren) {
               LockFreePrefixTree.Node newChild = getOrAddLinear(childKey, children0);
               if (newChild != null) {
                  return newChild;
               }

               this.tryResizeLinear(children0);
            } else {
               LockFreePrefixTree.Node newChild = getOrAddHash(childKey, children0);
               if (newChild != null) {
                  return newChild;
               }

               this.tryResizeHash(children0);
            }
         }
      }

      private static LockFreePrefixTree.Node getOrAddLinear(long childKey, AtomicReferenceArray<LockFreePrefixTree.Node> childrenArray) {
         for (int i = 0; i < childrenArray.length(); i++) {
            LockFreePrefixTree.Node child = read(childrenArray, i);
            if (child == null) {
               LockFreePrefixTree.Node newChild = new LockFreePrefixTree.Node(childKey);
               if (cas(childrenArray, i, null, newChild)) {
                  return newChild;
               }

               LockFreePrefixTree.Node child1 = read(childrenArray, i);
               if (child1.getKey() == childKey) {
                  return child1;
               }
            } else if (child.getKey() == childKey) {
               return child;
            }
         }

         return null;
      }

      private void tryResizeLinear(AtomicReferenceArray<LockFreePrefixTree.Node> childrenArray) {
         AtomicReferenceArray<LockFreePrefixTree.Node> newChildrenArray;
         if (childrenArray.length() < 8) {
            newChildrenArray = new LockFreePrefixTree.Node.LinearChildren(2 * childrenArray.length());

            for (int i = 0; i < childrenArray.length(); i++) {
               LockFreePrefixTree.Node toCopy = read(childrenArray, i);
               write(newChildrenArray, i, toCopy);
            }
         } else {
            newChildrenArray = new LockFreePrefixTree.Node.HashChildren(16);

            for (int i = 0; i < childrenArray.length(); i++) {
               LockFreePrefixTree.Node toCopy = read(childrenArray, i);
               addChildToLocalHash(toCopy, newChildrenArray);
            }
         }

         CHILDREN_UPDATER.compareAndSet(this, childrenArray, newChildrenArray);
      }

      private static LockFreePrefixTree.Node getOrAddHash(long childKey, AtomicReferenceArray<LockFreePrefixTree.Node> hashTable) {
         int index = hash(childKey) % hashTable.length();
         int skips = 0;

         while (true) {
            LockFreePrefixTree.Node node0 = read(hashTable, index);
            if (node0 == null) {
               LockFreePrefixTree.Node newNode = new LockFreePrefixTree.Node(childKey);
               if (cas(hashTable, index, null, newNode)) {
                  return newNode;
               }
            } else {
               if (node0 != FROZEN_NODE && node0.getKey() == childKey) {
                  return node0;
               }

               index = (index + 1) % hashTable.length();
               if (++skips > 10) {
                  return null;
               }
            }
         }
      }

      private static void addChildToLocalHash(LockFreePrefixTree.Node node, AtomicReferenceArray<LockFreePrefixTree.Node> hashTable) {
         int index = hash(node.getKey()) % hashTable.length();

         while (read(hashTable, index) != null) {
            index = (index + 1) % hashTable.length();
         }

         write(hashTable, index, node);
      }

      private void tryResizeHash(AtomicReferenceArray<LockFreePrefixTree.Node> children0) {
         freezeHash(children0);
         AtomicReferenceArray<LockFreePrefixTree.Node> newChildrenHash = new LockFreePrefixTree.Node.HashChildren(2 * children0.length());

         for (int i = 0; i < children0.length(); i++) {
            LockFreePrefixTree.Node toCopy = read(children0, i);
            if (toCopy != FROZEN_NODE) {
               addChildToLocalHash(toCopy, newChildrenHash);
            }
         }

         this.casChildren(children0, newChildrenHash);
      }

      private static void freezeHash(AtomicReferenceArray<LockFreePrefixTree.Node> childrenHash) {
         for (int i = 0; i < childrenHash.length(); i++) {
            if (read(childrenHash, i) == null) {
               cas(childrenHash, i, null, FROZEN_NODE);
            }
         }
      }

      private static boolean cas(
         AtomicReferenceArray<LockFreePrefixTree.Node> childrenArray, int i, LockFreePrefixTree.Node expected, LockFreePrefixTree.Node updated
      ) {
         return childrenArray.compareAndSet(i, expected, updated);
      }

      private static LockFreePrefixTree.Node read(AtomicReferenceArray<LockFreePrefixTree.Node> childrenArray, int i) {
         return childrenArray.get(i);
      }

      private static void write(AtomicReferenceArray<LockFreePrefixTree.Node> childrenArray, int i, LockFreePrefixTree.Node newNode) {
         childrenArray.set(i, newNode);
      }

      private void ensureChildren() {
         if (this.readChildren() == null) {
            AtomicReferenceArray<LockFreePrefixTree.Node> newChildren = new LockFreePrefixTree.Node.LinearChildren(2);
            this.casChildren(null, newChildren);
         }
      }

      private boolean casChildren(AtomicReferenceArray<LockFreePrefixTree.Node> expected, AtomicReferenceArray<LockFreePrefixTree.Node> updated) {
         return CHILDREN_UPDATER.compareAndSet(this, expected, updated);
      }

      private AtomicReferenceArray<LockFreePrefixTree.Node> readChildren() {
         return this.children;
      }

      private static int hash(long key) {
         long v = key * -7046033566014671411L;
         v = Long.reverseBytes(v);
         v *= -7046033566014671411L;
         return 2147483647 & (int)(v ^ v >> 32);
      }

      private <C> void topDown(C currentContext, BiFunction<C, Long, C> createContext, BiConsumer<C, Long> consumeValue) {
         AtomicReferenceArray<LockFreePrefixTree.Node> childrenSnapshot = this.readChildren();
         consumeValue.accept(currentContext, this.get());
         if (childrenSnapshot != null) {
            for (int i = 0; i < childrenSnapshot.length(); i++) {
               LockFreePrefixTree.Node child = read(childrenSnapshot, i);
               if (child != null && child != FROZEN_NODE) {
                  long childKey = child.getKey();
                  C extendedContext = createContext.apply(currentContext, childKey);
                  child.topDown(extendedContext, createContext, consumeValue);
               }
            }
         }
      }

      @Override
      public String toString() {
         return "Node<" + this.value() + ">";
      }

      private static final class FrozenNode extends LockFreePrefixTree.Node {
         private static final long serialVersionUID = -1L;

         FrozenNode() {
            super(-1L);
         }
      }

      private static final class HashChildren extends AtomicReferenceArray<LockFreePrefixTree.Node> {
         private static final long serialVersionUID = -1L;

         HashChildren(int length) {
            super(length);
         }
      }

      private static final class LinearChildren extends AtomicReferenceArray<LockFreePrefixTree.Node> {
         private static final long serialVersionUID = -1L;

         LinearChildren(int length) {
            super(length);
         }
      }
   }
}
