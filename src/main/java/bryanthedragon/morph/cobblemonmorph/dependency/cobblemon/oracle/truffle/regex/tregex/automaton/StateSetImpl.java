
package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.util.BitSets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Set;

final class StateSetImpl<SI extends StateIndex<? super S>, S>
implements StateSet<SI, S> {
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
        return new StateSetImpl<SI, S>(this);
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
        return StateSetImpl.stateListElement(stateList >>> 32 * i);
    }

    private static int stateListElement(long stateList) {
        return (int)(stateList & 0xFFFFFFFFFFFFFFFFL);
    }

    @Override
    public boolean contains(Object o) {
        return this.contains(this.stateIndex.getId((Object)o));
    }

    @Override
    public boolean add(S e) {
        return this.add(this.stateIndex.getId(e));
    }

    @Override
    public boolean remove(Object o) {
        return this.remove(this.stateIndex.getId((Object)o));
    }

    private boolean contains(int id) {
        if (this.useBackingSet()) {
            return BitSets.get(this.backingSet, id);
        }
        long sl = this.stateList;
        for (int i = 0; i < this.size(); ++i) {
            if (StateSetImpl.stateListElement(sl) == id) {
                return true;
            }
            sl >>>= 32;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c instanceof StateSetImpl) {
            StateSetImpl o = (StateSetImpl)c;
            if (this.useBackingSet() && o.useBackingSet()) {
                return BitSets.contains(this.backingSet, o.backingSet);
            }
        }
        for (Object o : c) {
            if (this.contains(o)) continue;
            return false;
        }
        return true;
    }

    @Override
    private boolean add(int id) {
        int i;
        if (this.useBackingSet()) {
            if (BitSets.add(this.backingSet, id)) {
                ++this.size;
                return true;
            }
            return false;
        }
        long sl = this.stateList;
        for (i = 0; i < this.size(); ++i) {
            if (StateSetImpl.stateListElement(sl) == id) {
                return false;
            }
            if (StateSetImpl.stateListElement(sl) > id) break;
            sl >>>= 32;
        }
        if (this.size() == 2) {
            if (this.backingSet == null) {
                this.backingSet = BitSets.createBitSetArray(this.stateIndex.getNumberOfStates());
            }
            for (int j = 0; j < this.size(); ++j) {
                BitSets.set(this.backingSet, StateSetImpl.stateListElement(this.stateList));
                this.stateList >>>= 32;
            }
        }
        ++this.size;
        if (this.useBackingSet()) {
            BitSets.set(this.backingSet, id);
        } else {
            this.stateList = (sl << 32 | (long)id) << i * 32 | this.stateList & (-1L << i * 32 ^ 0xFFFFFFFFFFFFFFFFL);
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends S> c) {
        if (c instanceof StateSetImpl) {
            StateSetImpl o = (StateSetImpl)c;
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
            if (BitSets.remove(this.backingSet, id)) {
                --this.size;
                if (this.size == 2) {
                    assert (this.stateList == 0L);
                    int shift2 = 0;
                    PrimitiveIterator.OfInt it = BitSets.iterator(this.backingSet);
                    while (it.hasNext()) {
                        this.stateList |= (long)it.nextInt() << shift2;
                        shift2 += 32;
                    }
                    BitSets.clear(this.backingSet);
                }
                return true;
            }
            return false;
        }
        long sl = this.stateList;
        for (int i = 0; i < this.size(); ++i) {
            if (StateSetImpl.stateListElement(sl) == id) {
                this.removeStateListElement(i);
                --this.size;
                return true;
            }
            sl >>>= 32;
        }
        return false;
    }

    private void removeStateListElement(int i) {
        long maskClrLo = -1L << i * 32;
        long maskClrHi = maskClrLo ^ 0xFFFFFFFFFFFFFFFFL;
        this.stateList = this.stateList >>> 32 & maskClrLo | this.stateList & maskClrHi;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c instanceof StateSetImpl) {
            StateSetImpl o = (StateSetImpl)c;
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
            StateSetImpl o = (StateSetImpl)c;
            if (this.useBackingSet() && o.useBackingSet()) {
                int oldSize = this.size;
                this.size = BitSets.retainAll(this.backingSet, o.backingSet);
                return this.size != oldSize;
            }
        }
        boolean ret = false;
        for (S s : this) {
            if (c.contains(s)) continue;
            ret |= this.remove(s);
        }
        return ret;
    }

    @Override
    public boolean isDisjoint(StateSet<SI, ? extends S> other) {
        StateSetImpl o = (StateSetImpl)other;
        if (o.useBackingSet()) {
            if (this.useBackingSet()) {
                return BitSets.isDisjoint(this.backingSet, o.backingSet);
            }
            long sl = this.stateList;
            for (int i = 0; i < this.size(); ++i) {
                if (o.contains(StateSetImpl.stateListElement(sl))) {
                    return false;
                }
                sl >>>= 32;
            }
            return true;
        }
        long sl = o.stateList;
        for (int i = 0; i < o.size(); ++i) {
            if (this.contains(StateSetImpl.stateListElement(sl))) {
                return false;
            }
            sl >>>= 32;
        }
        return true;
    }

    private boolean stateListSorted() {
        int i;
        int last = -1;
        for (i = 0; i < this.size(); ++i) {
            if (StateSetImpl.stateListElement(this.stateList, i) <= last) {
                return false;
            }
            last = StateSetImpl.stateListElement(this.stateList, i);
        }
        for (i = this.size(); i < 2; ++i) {
            if (StateSetImpl.stateListElement(this.stateList, i) == 0) continue;
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if (this.useBackingSet()) {
            return BitSets.hashCode(this.backingSet);
        }
        assert (this.stateListSorted());
        return Long.hashCode(this.stateList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StateSetImpl) {
            StateSetImpl o = (StateSetImpl)obj;
            if (this.size() != o.size()) {
                return false;
            }
            assert (this.useBackingSet() == o.useBackingSet());
            if (this.useBackingSet()) {
                return BitSets.equals(this.backingSet, o.backingSet);
            }
            assert (this.stateListSorted());
            assert (o.stateListSorted());
            return this.stateList == o.stateList;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set o = (Set)obj;
        return this.size() == o.size() && this.containsAll(o);
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.defaultToString();
    }

    private PrimitiveIterator.OfInt intIterator() {
        if (this.useBackingSet()) {
            return BitSets.iterator(this.backingSet);
        }
        assert (this.stateListSorted());
        return new StateListIterator();
    }

    @Override
    public Iterator<S> iterator() {
        return new StateSetIterator(this.intIterator());
    }

    private final class StateSetIterator
    implements Iterator<S> {
        private final PrimitiveIterator.OfInt intIterator;

        private StateSetIterator(PrimitiveIterator.OfInt intIterator) {
            this.intIterator = intIterator;
        }

        @Override
        public boolean hasNext() {
            return this.intIterator.hasNext();
        }

        @Override
        public S next() {
            return StateSetImpl.this.getStateIndex().getState(this.intIterator.nextInt());
        }

        @Override
        public void remove() {
            this.intIterator.remove();
            --StateSetImpl.this.size;
        }
    }

    private final class StateListIterator
    implements PrimitiveIterator.OfInt {
        private int i;

        private StateListIterator() {
        }

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
}

