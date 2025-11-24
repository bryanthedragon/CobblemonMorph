
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.HashMap;

public final class JSHashMap {
    private final HashMap<Object, Node> map = new HashMap();
    private final Node head;
    private Node tail;

    @CompilerDirectives.TruffleBoundary(allowInlining=true)
    public JSHashMap() {
        Node dummy2;
        this.head = dummy2 = new Node(null, null, null, null);
        this.tail = dummy2;
    }

    @CompilerDirectives.TruffleBoundary(allowInlining=true)
    public int size() {
        return this.map.size();
    }

    @CompilerDirectives.TruffleBoundary
    public void put(Object key, Object value2) {
        Node newNode = new Node(key, value2, null, null);
        Node oldNode = this.map.putIfAbsent(key, newNode);
        if (oldNode == null) {
            newNode.setPrev(this.tail);
            this.tail.setNext(newNode);
            this.tail = newNode;
        } else {
            oldNode.setValue(value2);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public Object get(Object key) {
        Node node = this.map.get(key);
        return node == null ? null : node.getValue();
    }

    @CompilerDirectives.TruffleBoundary
    public boolean has(Object key) {
        return this.map.containsKey(key);
    }

    @CompilerDirectives.TruffleBoundary
    public boolean remove(Object key) {
        Node node = this.map.remove(key);
        if (node == null) {
            return false;
        }
        this.unlink(node);
        return true;
    }

    private void unlink(Node node) {
        Node next = node.getNext();
        Node prev = node.getPrev();
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
        for (Node current = this.head.getNext(); current != null; current = current.getNext()) {
            current.setEmpty();
        }
        this.head.setNext(null);
        this.tail = this.head;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.map.toString();
    }

    public Cursor getEntries() {
        return new CursorImpl(this.head);
    }

    private static final class Node {
        private Object key;
        private Object value;
        private Node prev;
        private Node next;

        Node(Object key, Object value2, Node prev, Node next) {
            this.key = key;
            this.value = value2;
            this.prev = prev;
            this.next = next;
        }

        Object getKey() {
            return this.key;
        }

        Object getValue() {
            return this.value;
        }

        void setValue(Object value2) {
            this.value = value2;
        }

        Node getPrev() {
            return this.prev;
        }

        void setPrev(Node prev) {
            this.prev = prev;
        }

        Node getNext() {
            return this.next;
        }

        void setNext(Node next) {
            this.next = next;
        }

        void setEmpty() {
            this.key = null;
            this.value = null;
        }

        boolean isEmpty() {
            return this.key == null;
        }

        public String toString() {
            return "Node [key=" + this.key + ", value=" + this.value + "]";
        }
    }

    private static final class CursorImpl
    implements Cursor {
        private Node current;

        CursorImpl(Node head5) {
            this.current = head5;
        }

        @Override
        public boolean advance() {
            if (this.current == null) {
                return false;
            }
            while (this.current.isEmpty() && this.current.getPrev() != null) {
                this.current = this.current.getPrev();
            }
            Node next = this.current.getNext();
            assert (next == null || next.getKey() != null);
            this.current = next;
            return next != null;
        }

        @Override
        public Object getKey() {
            Object key = this.current.getKey();
            assert (key != null);
            return key;
        }

        @Override
        public Object getValue() {
            Object value2 = this.current.getValue();
            assert (value2 != null);
            return value2;
        }

        public String toString() {
            return "Cursor [current=" + this.current + "]";
        }

        @Override
        public Cursor copy() {
            return new CursorImpl(this.current);
        }
    }

    public static interface Cursor {
        public boolean advance();

        public Object getKey();

        public Object getValue();

        public Cursor copy();
    }
}

