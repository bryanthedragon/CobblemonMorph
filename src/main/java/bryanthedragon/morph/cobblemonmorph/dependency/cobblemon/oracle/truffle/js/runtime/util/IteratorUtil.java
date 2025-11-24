
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.Errors;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public final class IteratorUtil {
    private IteratorUtil() {
    }

    public static <S, T> Iterable<T> convertIterable(Iterable<S> source, Function<S, T> converter) {
        return new ConvertIterable<S, T>(source, converter);
    }

    public static <S, T> Iterator<T> convertIterator(Iterator<S> source, Function<S, T> converter) {
        return new ConvertIterator<S, T>(source, converter);
    }

    public static <S, T> List<T> convertList(List<S> source, Function<S, T> converter) {
        return new ConvertList<S, T>(source, converter);
    }

    public static <T> Iterable<T> concatIterables(final Iterable<T> first, final Iterable<T> second) {
        return new Iterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>(){
                    private final Iterator<T> firstIterator;
                    private final Iterator<T> secondIterator;
                    {
                        this.firstIterator = first.iterator();
                        this.secondIterator = second.iterator();
                    }

                    @Override
                    public T next() {
                        if (this.firstIterator.hasNext()) {
                            return this.firstIterator.next();
                        }
                        if (this.secondIterator.hasNext()) {
                            return this.secondIterator.next();
                        }
                        CompilerDirectives.transferToInterpreter();
                        throw new NoSuchElementException();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.firstIterator.hasNext() || this.secondIterator.hasNext();
                    }
                };
            }
        };
    }

    public static <T> List<T> concatLists(final List<T> list0, final List<T> list1) {
        int size1;
        final int size0 = list0.size();
        final int size = size0 + (size1 = list1.size());
        if (size < 0) {
            throw Errors.createRangeErrorInvalidArrayLength();
        }
        if (size0 == 0) {
            return list1;
        }
        if (size1 == 0) {
            return list0;
        }
        return new AbstractList<T>(){

            @Override
            public T get(int index) {
                if (index >= 0 && index < size0) {
                    return list0.get(index);
                }
                if (index >= 0 && index < size) {
                    return list1.get(index - size0);
                }
                throw this.outOfBounds(index);
            }

            @Override
            public int size() {
                return size;
            }

            @CompilerDirectives.TruffleBoundary
            private IndexOutOfBoundsException outOfBounds(int index) {
                return new IndexOutOfBoundsException("Index: " + index + " Size: " + this.size());
            }
        };
    }

    public static <T> Iterable<T> concatIterablesDistinct(Iterable<T> first, Iterable<T> second, BiPredicate<T, T> comparator) {
        if (!second.iterator().hasNext()) {
            return first;
        }
        return new DistinctConcatIterable<T>(first, second, comparator);
    }

    public static <T> Iterable<T> filterIterable(Iterable<T> iterable, Predicate<T> filter) {
        return () -> IteratorUtil.filterIterator(iterable.iterator(), filter);
    }

    public static <T> Iterator<T> filterIterator(Iterator<T> iterator, Predicate<T> filter) {
        return new FilteredIterator<T>(iterator, filter);
    }

    public static <T> Iterator<T> simpleArrayIterator(final T[] array) {
        return new Iterator<T>(){
            private int cursor;

            @Override
            public boolean hasNext() {
                return this.cursor < array.length;
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    return array[this.cursor++];
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static <T> Iterator<T> simpleListIterator(final List<T> list) {
        return new Iterator<T>(){
            private int cursor;

            @Override
            public boolean hasNext() {
                return this.cursor < list.size();
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    return list.get(this.cursor++);
                }
                throw new NoSuchElementException();
            }
        };
    }

    public static Iterator<Integer> rangeIterator(int length) {
        return new RangeIterator(length);
    }

    public static Iterable<Integer> rangeIterable(int length) {
        return () -> IteratorUtil.rangeIterator(length);
    }

    private static final class RangeIterator
    implements Iterator<Integer> {
        private final int length;
        private int index;

        RangeIterator(int length) {
            this.length = length;
        }

        @Override
        public boolean hasNext() {
            return this.index < this.length;
        }

        @Override
        public Integer next() {
            if (this.index < this.length) {
                return this.index++;
            }
            throw new NoSuchElementException();
        }
    }

    private static final class FilteredIterator<T>
    implements Iterator<T> {
        private final Iterator<T> iterator;
        private final Predicate<T> filter;
        private T next;

        FilteredIterator(Iterator<T> iterator, Predicate<T> filter) {
            this.iterator = iterator;
            this.filter = filter;
            this.next = this.forward();
        }

        @Override
        public T next() {
            if (this.next != null) {
                try {
                    T t = this.next;
                    return t;
                }
                finally {
                    this.next = this.forward();
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        private T forward() {
            while (this.iterator.hasNext()) {
                T item = this.iterator.next();
                if (!this.filter.test(item)) continue;
                return item;
            }
            return null;
        }
    }

    private static final class DistinctConcatIterable<T>
    implements Iterable<T> {
        private final Iterable<T> first;
        private final Iterable<T> second;
        private final BiPredicate<T, T> comparator;

        DistinctConcatIterable(Iterable<T> first, Iterable<T> second, BiPredicate<T, T> comparator) {
            this.first = first;
            this.second = second;
            this.comparator = comparator;
        }

        @Override
        public Iterator<T> iterator() {
            return new DistinctConcatIterator();
        }

        private final class DistinctConcatIterator
        implements Iterator<T> {
            private final Iterator<T> firstIterator;
            private final Iterator<T> secondIterator;
            private T next;

            private DistinctConcatIterator() {
                this.firstIterator = DistinctConcatIterable.this.first.iterator();
                this.secondIterator = DistinctConcatIterable.this.second.iterator();
                this.next = this.forward();
            }

            @Override
            public T next() {
                if (this.next != null) {
                    try {
                        Object t = this.next;
                        return t;
                    }
                    finally {
                        this.next = this.forward();
                    }
                }
                CompilerDirectives.transferToInterpreter();
                throw new NoSuchElementException();
            }

            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            private T forward() {
                if (this.firstIterator.hasNext()) {
                    return this.firstIterator.next();
                }
                block0: while (this.secondIterator.hasNext()) {
                    Object item = this.secondIterator.next();
                    for (Object visitedItem : DistinctConcatIterable.this.first) {
                        if (!DistinctConcatIterable.this.comparator.test(item, visitedItem)) continue;
                        continue block0;
                    }
                    return item;
                }
                return null;
            }
        }
    }

    private static final class ConvertList<S, T>
    extends AbstractList<T> {
        private final List<S> source;
        private final Function<S, T> converter;

        ConvertList(List<S> source, Function<S, T> converter) {
            this.source = source;
            this.converter = converter;
        }

        @Override
        public Iterator<T> iterator() {
            return new ConvertIterator<S, T>(this.source.iterator(), this.converter);
        }

        @Override
        public T get(int index) {
            return this.converter.apply(this.source.get(index));
        }

        @Override
        public int size() {
            return this.source.size();
        }
    }

    private static final class ConvertIterator<S, T>
    implements Iterator<T> {
        private final Iterator<S> nested;
        private final Function<S, T> converter;

        ConvertIterator(Iterator<S> nested, Function<S, T> converter) {
            this.nested = nested;
            this.converter = converter;
        }

        @Override
        public T next() {
            return this.converter.apply(this.nested.next());
        }

        @Override
        public boolean hasNext() {
            return this.nested.hasNext();
        }
    }

    private static final class ConvertIterable<S, T>
    implements Iterable<T> {
        private final Iterable<S> source;
        private final Function<S, T> converter;

        ConvertIterable(Iterable<S> source, Function<S, T> converter) {
            this.source = source;
            this.converter = converter;
        }

        @Override
        public Iterator<T> iterator() {
            return new ConvertIterator<S, T>(this.source.iterator(), this.converter);
        }
    }
}

