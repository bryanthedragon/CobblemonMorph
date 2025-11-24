
package org.graalvm.polyglot.io;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import org.graalvm.polyglot.io.ByteArraySequence;

public interface ByteSequence {
    public int length();

    public byte byteAt(int var1);

    default public ByteSequence subSequence(final int startIndex, int endIndex) {
        final int l = endIndex - startIndex;
        if (l < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }
        if (startIndex < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(startIndex));
        }
        if (startIndex + l > this.length()) {
            throw new IndexOutOfBoundsException(String.valueOf(startIndex + l));
        }
        return new ByteSequence(){

            @Override
            public int length() {
                return l;
            }

            @Override
            public byte byteAt(int index) {
                return ByteSequence.this.byteAt(startIndex + index);
            }
        };
    }

    default public byte[] toByteArray() {
        byte[] b = new byte[this.length()];
        for (int i = 0; i < b.length; ++i) {
            b[i] = this.byteAt(i);
        }
        return b;
    }

    default public IntStream bytes() {
        return StreamSupport.intStream(() -> {
            class ByteIterator
            implements PrimitiveIterator.OfInt {
                int cur = 0;

                ByteIterator() {
                }

                @Override
                public boolean hasNext() {
                    return this.cur < ByteSequence.this.length();
                }

                @Override
                public int nextInt() {
                    if (this.hasNext()) {
                        return ByteSequence.this.byteAt(this.cur++) & 0xFF;
                    }
                    throw new NoSuchElementException();
                }

                @Override
                public void forEachRemaining(IntConsumer block) {
                    while (this.cur < ByteSequence.this.length()) {
                        block.accept(ByteSequence.this.byteAt(this.cur) & 0xFF);
                        ++this.cur;
                    }
                }
            }
            return Spliterators.spliterator(new ByteIterator(), (long)this.length(), 16);
        }, 16464, false);
    }

    public static ByteSequence create(byte[] buffer) {
        return new ByteArraySequence(buffer, 0, buffer.length);
    }
}

