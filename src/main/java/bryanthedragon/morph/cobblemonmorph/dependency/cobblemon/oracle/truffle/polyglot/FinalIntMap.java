
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;

final class FinalIntMap {
    @CompilerDirectives.CompilationFinal
    Entry first;

    FinalIntMap() {
    }

    @ExplodeLoop
    int get(Object key) {
        Entry current = this.first;
        while (current != null) {
            if (current.key == key) {
                return current.value;
            }
            current = current.next;
        }
        return -1;
    }

    void put(Object key, int value2) {
        CompilerAsserts.neverPartOfCompilation();
        assert (this.get(key) == -1) : "replace not supported by this map implementation";
        assert (value2 >= 0) : "only positive integers supported";
        Entry prev = null;
        Entry current = this.first;
        while (current != null) {
            prev = current;
            current = current.next;
        }
        Entry entry = new Entry(key, value2);
        if (prev == null) {
            assert (current == this.first);
            this.first = entry;
        } else {
            prev.next = entry;
        }
    }

    static final class Entry {
        final Object key;
        final int value;
        @CompilerDirectives.CompilationFinal
        Entry next;

        Entry(Object key, int value2) {
            this.key = key;
            this.value = value2;
        }
    }
}

