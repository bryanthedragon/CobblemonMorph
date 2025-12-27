package org.graalvm.polyglot.io;

import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.PrimitiveIterator.OfInt;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public interface ByteSequence {
   int length();

   byte byteAt(int index);

   default ByteSequence subSequence(int startIndex, int endIndex) {
      final int l = endIndex - startIndex;
      if (l < 0) {
         throw new IndexOutOfBoundsException(String.valueOf(l));
      } else if (startIndex < 0) {
         throw new IndexOutOfBoundsException(String.valueOf(startIndex));
      } else if (startIndex + l > this.length()) {
         throw new IndexOutOfBoundsException(String.valueOf(startIndex + l));
      } else {
         return new ByteSequence() {
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
   }

   default byte[] toByteArray() {
      byte[] b = new byte[this.length()];

      for (int i = 0; i < b.length; i++) {
         b[i] = this.byteAt(i);
      }

      return b;
   }

   default IntStream bytes() {
      class ByteIterator implements OfInt {
         int cur = 0;

         @Override
         public boolean hasNext() {
            return this.cur < ByteSequence.this.length();
         }

         @Override
         public int nextInt() {
            if (this.hasNext()) {
               return ByteSequence.this.byteAt(this.cur++) & 0xFF;
            } else {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void forEachRemaining(IntConsumer block) {
            while (this.cur < ByteSequence.this.length()) {
               block.accept(ByteSequence.this.byteAt(this.cur) & 255);
               this.cur++;
            }
         }
      }

      return StreamSupport.intStream(() -> Spliterators.spliterator(new ByteIterator(), (long)this.length(), 16), 16464, false);
   }

   static ByteSequence create(byte[] buffer) {
      return new ByteArraySequence(buffer, 0, buffer.length);
   }
}
