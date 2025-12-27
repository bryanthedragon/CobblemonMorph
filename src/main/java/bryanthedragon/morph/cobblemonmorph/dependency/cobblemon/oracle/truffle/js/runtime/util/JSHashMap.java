package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.HashMap;

public final class JSHashMap {
   private final HashMap<Object, JSHashMap.Node> map = new HashMap<>();
   private final JSHashMap.Node head;
   private JSHashMap.Node tail;

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   public JSHashMap() {
      JSHashMap.Node dummy = new JSHashMap.Node(null, null, null, null);
      this.head = dummy;
      this.tail = dummy;
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   public int size() {
      return this.map.size();
   }

   @CompilerDirectives.TruffleBoundary
   public void put(Object key, Object value) {
      JSHashMap.Node newNode = new JSHashMap.Node(key, value, null, null);
      JSHashMap.Node oldNode = this.map.putIfAbsent(key, newNode);
      if (oldNode == null) {
         newNode.setPrev(this.tail);
         this.tail.setNext(newNode);
         this.tail = newNode;
      } else {
         oldNode.setValue(value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public Object get(Object key) {
      JSHashMap.Node node = this.map.get(key);
      return node == null ? null : node.getValue();
   }

   @CompilerDirectives.TruffleBoundary
   public boolean has(Object key) {
      return this.map.containsKey(key);
   }

   @CompilerDirectives.TruffleBoundary
   public boolean remove(Object key) {
      JSHashMap.Node node = this.map.remove(key);
      if (node == null) {
         return false;
      } else {
         this.unlink(node);
         return true;
      }
   }

   private void unlink(JSHashMap.Node node) {
      JSHashMap.Node next = node.getNext();
      JSHashMap.Node prev = node.getPrev();
      prev.setNext(next);
      if (next != null) {
         next.setPrev(prev);
      } else {
         this.tail = prev;
      }

      node.setEmpty();
   }

   @CompilerDirectives.TruffleBoundary
   public void clear() {
      this.map.clear();

      for (JSHashMap.Node current = this.head.getNext(); current != null; current = current.getNext()) {
         current.setEmpty();
      }

      this.head.setNext(null);
      this.tail = this.head;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.map.toString();
   }

   public JSHashMap.Cursor getEntries() {
      return new JSHashMap.CursorImpl(this.head);
   }

   public interface Cursor {
      boolean advance();

      Object getKey();

      Object getValue();

      JSHashMap.Cursor copy();
   }

   private static final class CursorImpl implements JSHashMap.Cursor {
      private JSHashMap.Node current;

      CursorImpl(JSHashMap.Node head) {
         this.current = head;
      }

      @Override
      public boolean advance() {
         if (this.current == null) {
            return false;
         } else {
            while (this.current.isEmpty() && this.current.getPrev() != null) {
               this.current = this.current.getPrev();
            }

            JSHashMap.Node next = this.current.getNext();

            assert next == null || next.getKey() != null;

            this.current = next;
            return next != null;
         }
      }

      @Override
      public Object getKey() {
         Object key = this.current.getKey();

         assert key != null;

         return key;
      }

      @Override
      public Object getValue() {
         Object value = this.current.getValue();

         assert value != null;

         return value;
      }

      @Override
      public String toString() {
         return "Cursor [current=" + this.current + "]";
      }

      @Override
      public JSHashMap.Cursor copy() {
         return new JSHashMap.CursorImpl(this.current);
      }
   }

   private static final class Node {
      private Object key;
      private Object value;
      private JSHashMap.Node prev;
      private JSHashMap.Node next;

      Node(Object key, Object value, JSHashMap.Node prev, JSHashMap.Node next) {
         this.key = key;
         this.value = value;
         this.prev = prev;
         this.next = next;
      }

      Object getKey() {
         return this.key;
      }

      Object getValue() {
         return this.value;
      }

      void setValue(Object value) {
         this.value = value;
      }

      JSHashMap.Node getPrev() {
         return this.prev;
      }

      void setPrev(JSHashMap.Node prev) {
         this.prev = prev;
      }

      JSHashMap.Node getNext() {
         return this.next;
      }

      void setNext(JSHashMap.Node next) {
         this.next = next;
      }

      void setEmpty() {
         this.key = null;
         this.value = null;
      }

      boolean isEmpty() {
         return this.key == null;
      }

      @Override
      public String toString() {
         return "Node [key=" + this.key + ", value=" + this.value + "]";
      }
   }
}
